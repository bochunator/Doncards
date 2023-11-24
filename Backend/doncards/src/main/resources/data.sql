-- Dodajmy kolejne rekordy do tabeli ENGLISH
INSERT INTO english(id, content) VALUES
(1, 'present'),
(2, 'apple'),
(3, 'banana'),
(4, 'car'),
(5, 'house');

-- Dodajmy kolejne rekordy do tabeli POLISH
INSERT INTO polish(id, content) VALUES
(1, 'obecny'),
(2, 'jabłko'),
(3, 'banan'),
(4, 'samochód'),
(5, 'dom');

-- Dodajmy kolejne rekordy do tabeli ENGLISH_POLISH
INSERT INTO english_polish(id, english_id, polish_id) VALUES
(1, 1, 1),
(2, 2, 2),
(3, 3, 3),
(4, 4, 4),
(5, 5, 5);