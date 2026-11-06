WITH new_deck AS (
INSERT INTO deck (
    name,
    description,
    created_at,
    view_count,
    app_user_id
)
SELECT
    'animals',
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
        ('zwierzęta', 'animals'),
        ('pies', 'dog'),
        ('kot', 'cat'),
        ('kogut', 'cock'),
        ('kogut', 'rooster'),
        ('królik', 'rabbit'),
        ('kura', 'hen'),
        ('mysz', 'mouse'),
        ('kaczka', 'duck'),
        ('osioł', 'donkey'),
        ('owca', 'sheep'),
        ('gęś', 'goose'),
        ('świnia', 'pig'),
        ('koń', 'horse'),
        ('krowa', 'cow'),
        ('wielbłąd', 'camel'),
        ('żaba', 'frog'),
        ('tygrys', 'tiger'),
        ('niedźwiedź', 'bear'),
        ('żyrafa', 'giraffe'),
        ('lew', 'lion'),
        ('wąż', 'snake'),
        ('wilk', 'wolf'),
        ('lis', 'fox'),
        ('wiewiórka', 'squirrel'),
        ('małpa', 'monkey'),
        ('kangur', 'kangaroo'),
        ('słoń', 'elephant')
) AS words(term, translation);
