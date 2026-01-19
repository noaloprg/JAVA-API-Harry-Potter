INSERT INTO casas (escudo_imagen, fundador, nombre) VALUES
('leon.png', 'Godric Gryffindor', 'Gryffindor'),
('serpiente.png', 'Salazar Slytherin', 'Slytherin'),
('aguila.png', 'Rowena Ravenclaw', 'Ravenclaw'),
('tejon.png', 'Helga Hufflepuff', 'Hufflepuff');

INSERT INTO personajes (nombre, sangre, casa) VALUES
('Harry Potter', 'MESTIZA', 1),
('Hermione Granger', 'MUGGLE', 1),
('Draco Malfoy', 'PURA', 2),
('Cedric Diggory', 'PURA', 3),
('Albus Dumbledore', 'MESTIZA', 1),
('Bellatrix Lestrange', 'PURA', 2),
('Neville Longbottom', 'PURA', 1);

INSERT INTO varitas (longitud, madera, nucleo, rota, personaje) VALUES
(27.50, 'Acebo', 'Pluma de Fenix', false, 1),
(27.30, 'Vid', 'Fibra de Corazon de Dragon', false, 2),
(31.00, 'Roble', 'Pelo de Unicornio', false, 4),
(23.34, 'Acebo', 'Pluma de Fenix', true, 1),
(32.40, 'Nogal', 'Fibra de Corazón de Dragón', true, null),
(38.10, 'Sauco', 'Pelo de cola de Thestral', false, 5),
(33.00, 'Cerezo', 'Pelo de unicornio',false, 7);
