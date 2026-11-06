WITH new_deck AS (
INSERT INTO deck (
    name,
    description,
    created_at,
    view_count,
    app_user_id
)
SELECT
    'b',
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
        ('dziecko', 'baby'),
        ('plecak', 'backpack'),
        ('plecak', 'rucksack'),
        ('zły', 'bad'),
        ('niedobry', 'bad'),
        ('torba', 'bag'),
        ('upiec', 'bake'),
        ('piłka', 'ball'),
        ('balon', 'balloon'),
        ('zespół muzyczny', 'band'),
        ('grill', 'barbecue'),
        ('koszyk', 'basket'),
        ('kąpać się', 'bathe'),
        ('być', 'be'),
        ('plaża', 'beach'),
        ('broda', 'beard'),
        ('uderzyć', 'beat'),
        ('piękny', 'beautiful'),
        ('bóbr', 'beaver'),
        ('bo', 'because'),
        ('ponieważ', 'because'),
        ('stać się', 'become'),
        ('przed', 'before'),
        ('za', 'behind'),
        ('z tyłu', 'behind'),
        ('dzwonek', 'bell'),
        ('pomiędzy', 'between'),
        ('duży', 'big'),
        ('rachunek', 'bill'),
        ('ptak', 'bird'),
        ('urodziny', 'birthday'),
        ('tablica', 'blackboard'),
        ('krew', 'blood'),
        ('czerwienić się', 'blush'),
        ('łódź', 'boat'),
        ('zagotować', 'boil'),
        ('kość', 'bone'),
        ('ognisko', 'bonfire'),
        ('książka', 'book'),
        ('znudzony', 'bored'),
        ('szef', 'boss'),
        ('butelka', 'bottle'),
        ('pudełko', 'box'),
        ('chłopiec', 'boy'),
        ('mózg', 'brain'),
        ('gałąź', 'branch'),
        ('stłuc', 'break'),
        ('złamać', 'break'),
        ('most', 'bridge'),
        ('jasny', 'bright'),
        ('miotła', 'broom'),
        ('szczotka', 'brush'),
        ('budować', 'build'),
        ('palić', 'burn'),
        ('autobus', 'bus'),
        ('zajęty', 'busy'),
        ('ale', 'but'),
        ('motyl', 'butterfly'),
        ('guzik', 'button'),
        ('kupować', 'buy')
) AS words(term, translation);
