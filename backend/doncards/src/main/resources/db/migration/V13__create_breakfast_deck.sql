WITH new_deck AS (
INSERT INTO deck (
    name,
    description,
    created_at,
    view_count,
    app_user_id
)
SELECT
    'breakfast',
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
        ('śniadanie', 'breakfast'),
        ('kiełbaska', 'sausage'),
        ('cukier', 'sugar'),
        ('kawa', 'coffee'),
        ('mleko', 'milk'),
        ('szynka', 'ham'),
        ('bułka', 'roll'),
        ('miód', 'honey'),
        ('jajko', 'egg'),
        ('grzanka', 'toast'),
        ('masło', 'butter'),
        ('dżem', 'jam'),
        ('płatki zbożowe', 'cereal'),
        ('rogal', 'croissant'),
        ('chleb', 'bread'),
        ('herbata', 'tea'),
        ('herbatnik', 'biscuit')
) AS words(term, translation);
