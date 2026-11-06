WITH new_deck AS (
INSERT INTO deck (
    name,
    description,
    created_at,
    view_count,
    app_user_id
)
SELECT
    'bedroom',
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
        ('sypialnia', 'bedroom'),
        ('lampa', 'lamp'),
        ('budzik', 'alarm clock'),
        ('poduszka', 'pillow'),
        ('komoda', 'chest of drawers'),
        ('szafa', 'wardrobe'),
        ('toaletka', 'dressing table'),
        ('koc', 'blanket'),
        ('piżama', 'pyjamas'),
        ('łóżko', 'bed'),
        ('kołdra', 'duvet'),
        ('stolik nocny', 'bedside table'),
        ('krzesło', 'chair')
) AS words(term, translation);
