WITH new_deck AS (
INSERT INTO deck (
    name,
    description,
    created_at,
    view_count,
    app_user_id
)
SELECT
    'a',
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
        ('przedimki nieokreślone', 'a/an'),
        ('nad/powyżej', 'above'),
        ('za granicą', 'abroad'),
        ('nieobecny', 'absent'),
        ('wypadek', 'accident'),
        ('żołądź', 'acorn'),
        ('przez', 'across'),
        ('ogłoszenie', 'ad'),
        ('dodawać', 'add'),
        ('adres', 'address'),
        ('podziwiać', 'admire'),
        ('dorosły', 'adult'),
        ('przewaga', 'advantage'),
        ('przygoda', 'adventure'),
        ('rada', 'advice'),
        ('antena', 'aerial'),
        ('antena', 'satellite dish'),
        ('bać się', 'be afraid'),
        ('po', 'after'),
        ('przeciw', 'against'),
        ('wiek', 'age'),
        ('zgadzać', 'agree'),
        ('powietrze', 'air'),
        ('samolot', 'airplane'),
        ('lotnisko', 'airport'),
        ('budzik', 'alarm clock'),
        ('kosmita', 'alien'),
        ('żywy', 'alive'),
        ('wszystko', 'all'),
        ('alergia', 'allergy'),
        ('sam', 'alone'),
        ('także', 'also'),
        ('zawsze', 'always'),
        ('karetka', 'ambulance'),
        ('wśród', 'among'),
        ('park rozrywki', 'amusement park'),
        ('kotwica', 'anchor'),
        ('i', 'and'),
        ('anioł', 'angel'),
        ('zły', 'angry'),
        ('gniewny', 'angry'),
        ('odpowiedź', 'answer'),
        ('akwarium', 'aquarium'),
        ('salon gier', 'arcade'),
        ('strzała', 'arrow'),
        ('pytać', 'ask'),
        ('astronauta', 'astronaut'),
        ('sportowiec', 'athlete'),
        ('obudzony', 'awake')
) AS words(term, translation);
