WITH new_deck AS (
INSERT INTO deck (
    name,
    description,
    created_at,
    view_count,
    app_user_id
)
SELECT
    'bicycyle/bike',
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
        ('rower', 'bicycle'),
        ('rower', 'bike'),
        ('siodełko', 'seat'),
        ('hamulec', 'brake'),
        ('dzwonek', 'bell'),
        ('kierownica', 'handlebars'),
        ('światło', 'light'),
        ('pompka', 'pump'),
        ('błotnik', 'fender'),
        ('koło', 'wheal'),
        ('pedał', 'pedal'),
        ('łańcuch', 'chain'),
        ('przerzutka', 'gearshift'),
        ('podpórka', 'kickstand'),
        ('szprychy', 'spokes'),
        ('opona', 'tyre'),
        ('bagażnik', 'carrier')
) AS words(term, translation);
