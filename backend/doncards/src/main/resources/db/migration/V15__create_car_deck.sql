WITH new_deck AS (
INSERT INTO deck (
    name,
    description,
    created_at,
    view_count,
    app_user_id
)
SELECT
    'car',
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
        ('samochód', 'car'),
        ('bagażnik', 'boot'),
        ('siedzenie', 'seat'),
        ('sprzęgło', 'clutch'),
        ('hamulec', 'brake'),
        ('klakson', 'horn'),
        ('licznik', 'speedometer'),
        ('kierownica', 'steering wheel'),
        ('szyba przednia', 'windscreen'),
        ('maska', 'bonnet'),
        ('wycieraczka', 'wiper'),
        ('silnik', 'engine'),
        ('zderzak', 'bumper'),
        ('lusterko', 'mirror'),
        ('pedał gazu', 'accelerator'),
        ('pasy', 'seat belts'),
        ('biegi', 'gears'),
        ('opona', 'tyre'),
        ('zbiornik paliwa', 'fuel tank'),
        ('kierunkowskaz', 'indicator'),
        ('tablica rejestracyjna', 'number plate')
) AS words(term, translation);
