WITH new_deck AS (
INSERT INTO deck (
    name,
    description,
    created_at,
    view_count,
    app_user_id
)
SELECT
    'bathroom',
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
        ('łazienka', 'bathroom'),
        ('ręcznik', 'towel'),
        ('wieszak na ręcznik', 'towel rail'),
        ('półka', 'shelf'),
        ('lustro', 'mirror'),
        ('papier toaletowy', 'toilet paper'),
        ('prysznic', 'shower'),
        ('wanna', 'bath'),
        ('waga łazienkowa', 'bathroom scales'),
        ('gąbka', 'sponge'),
        ('umywalka', 'washbasin'),
        ('muszla klozetowa', 'toilet')
) AS words(term, translation);
