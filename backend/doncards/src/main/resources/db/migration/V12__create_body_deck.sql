WITH new_deck AS (
INSERT INTO deck (
    name,
    description,
    created_at,
    view_count,
    app_user_id
)
SELECT
    'body',
    'Example set',
    CURRENT_TIMESTAMP,
    0,
    id
FROM app_user
WHERE username = 'default words'
    RETURNING id
)
INSERT INTO card (term, translation, deck_id)
SELECT words.term, words.translation, new_deck.id
FROM new_deck
         CROSS JOIN (
    VALUES
        ('kostka', 'ankle'),
        ('łydka', 'calf'),
        ('pupa', 'bottom'),
        ('brzuch', 'stomach'),
        ('biodro', 'hip'),
        ('talia', 'waist'),
        ('plecy', 'back'),
        ('ramię', 'arm'),
        ('bark', 'shoulder'),
        ('szyja', 'neck'),
        ('podbródek', 'chin'),
        ('ucho', 'ear'),
        ('włosy', 'hair'),
        ('oko', 'eye'),
        ('głowa', 'head'),
        ('nose', 'nose'),
        ('usta', 'mouth'),
        ('kciuk', 'thumb'),
        ('dłoń', 'hand'),
        ('palec', 'finger'),
        ('nadgarstek', 'wrist'),
        ('łokieć', 'elbow'),
        ('klatka piersiowa', 'chest'),
        ('kolano', 'knee'),
        ('palec u nogi', 'toe'),
        ('stopa', 'foot'),
        ('pięta', 'heel'),
        ('noga', 'leg'),
        ('udo', 'thigh'),
        ('goleń', 'shin')
) AS words(term, translation);
