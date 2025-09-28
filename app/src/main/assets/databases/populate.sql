
INSERT INTO category(name) VALUES
('Nourriture'),
('Transport'),
('Logement'),
('Loisirs'),
('Santé'),
('Courses'),
('Abonnements'),
('Autre');




INSERT INTO spend(amount, categoryId, createdAt) VALUES
(12.50, (SELECT id FROM category WHERE name='Nourriture'), datetime('now','-1 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(3.20,  (SELECT id FROM category WHERE name='Transport'),  datetime('now','-2 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(29.90, (SELECT id FROM category WHERE name='Courses'),    datetime('now','-3 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(8.99,  (SELECT id FROM category WHERE name='Loisirs'),    datetime('now','-4 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(15.00, (SELECT id FROM category WHERE name='Abonnements'),datetime('now','-5 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(6.70,  (SELECT id FROM category WHERE name='Santé'),      datetime('now','-6 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(45.00, (SELECT id FROM category WHERE name='Nourriture'), datetime('now','-7 day'));


INSERT INTO spend(amount, categoryId, createdAt) VALUES
(2.80,  (SELECT id FROM category WHERE name='Transport'),  datetime('now','-9 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(64.30, (SELECT id FROM category WHERE name='Courses'),    datetime('now','-12 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(18.00, (SELECT id FROM category WHERE name='Loisirs'),    datetime('now','-14 day'));


INSERT INTO spend(amount, categoryId, createdAt) VALUES
(520.00,(SELECT id FROM category WHERE name='Logement'),   datetime('now','-1 month','-2 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(37.40, (SELECT id FROM category WHERE name='Nourriture'), datetime('now','-1 month','-3 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(12.00, (SELECT id FROM category WHERE name='Transport'),  datetime('now','-1 month','-6 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(21.99, (SELECT id FROM category WHERE name='Loisirs'),    datetime('now','-1 month','-8 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(74.10, (SELECT id FROM category WHERE name='Courses'),    datetime('now','-1 month','-10 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(9.99,  (SELECT id FROM category WHERE name='Abonnements'),datetime('now','-1 month','-12 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(14.50, (SELECT id FROM category WHERE name='Santé'),      datetime('now','-1 month','-15 day'));


INSERT INTO spend(amount, categoryId, createdAt) VALUES
(520.00,(SELECT id FROM category WHERE name='Logement'),   datetime('now','-2 month','-3 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(42.75, (SELECT id FROM category WHERE name='Nourriture'), datetime('now','-2 month','-5 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(7.60,  (SELECT id FROM category WHERE name='Transport'),  datetime('now','-2 month','-6 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(55.20, (SELECT id FROM category WHERE name='Courses'),    datetime('now','-2 month','-9 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(16.90, (SELECT id FROM category WHERE name='Loisirs'),    datetime('now','-2 month','-12 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(4.99,  (SELECT id FROM category WHERE name='Abonnements'),datetime('now','-2 month','-14 day'));


INSERT INTO spend(amount, categoryId, createdAt) VALUES
(520.00,(SELECT id FROM category WHERE name='Logement'),   datetime('now','-3 month','-2 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(35.10, (SELECT id FROM category WHERE name='Nourriture'), datetime('now','-3 month','-4 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(11.30, (SELECT id FROM category WHERE name='Transport'),  datetime('now','-3 month','-6 day'));
INSERT INTO spend(amount, categoryId, createdAt) VALUES
(62.80, (SELECT id FROM category WHERE name='Courses'),    datetime('now','-3 month','-9 day'));
