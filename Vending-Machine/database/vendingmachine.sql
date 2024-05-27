BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, roles, drink, flavor, cup, payment, drink_inventory, flavor_inventory, cup_inventory;

CREATE TABLE users (
    username varchar(50) PRIMARY KEY,
    password varchar(50) NOT NULL,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    email varchar(50) NOT NULL
);

CREATE TABLE roles (
    username varchar(50) REFERENCES users,
    role varchar(50) NOT NULL,
    PRIMARY KEY (username, role)
);

CREATE TABLE drink (
    drink_id serial PRIMARY KEY,
    name varchar(50) UNIQUE NOT NULL,
    type varchar(50) NOT NULL,
    cost decimal (10, 2) NOT NULL,
    canBeFlavored varchar(10) NOT NULL
);

CREATE TABLE flavor (
    flavor_id serial PRIMARY KEY,
    name varchar(50) UNIQUE NOT NULL
);

CREATE TABLE cup(
    cup_id serial PRIMARY KEY,
    size varchar(50) UNIQUE NOT NULL,
    capacity int NOT NULL,
    cost decimal (10, 2) NOT NULL
);

CREATE TABLE payment (
    payment_id serial PRIMARY KEY,
    name varchar(50) UNIQUE NOT NULL,
    type varchar(50) NOT NULL
);

CREATE TABLE drink_inventory (
    drink_inventory_id serial PRIMARY KEY,
    drink_id INT NOT NULL,
    quantity INT NOT NULL, --Measured in OZ, 1 5-gallon bag = 640oz
    CONSTRAINT FK_drink_inventory_drink_id FOREIGN KEY (drink_id) REFERENCES drink(drink_id)
);

CREATE TABLE flavor_inventory (
    flavor_inventory_id serial PRIMARY KEY,
    flavor_id INT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT FK_flavor_inventory_flavor_id FOREIGN KEY (flavor_id) REFERENCES flavor(flavor_id)
);

CREATE TABLE cup_inventory (
    cup_inventory_id serial PRIMARY KEY,
    cup_id INT NOT NULL,
    quantity INT NOT NULL,
    CONSTRAINT FK_cup_inventory_cup_id FOREIGN KEY (cup_id) REFERENCES cup(cup_id)
);

INSERT INTO users(username, password, first_name, last_name, email)
VALUES
('admin', '', 'Stephen', 'Buhrts', 'test@test.com');

INSERT INTO roles(username, role)
VALUES
('admin', 'ADMIN');

INSERT INTO drink(name, type, cost, canBeFlavored)
VALUES
('Coca-Cola', 'Soda', 1.50, 'True'),
('Diet Coke', 'Soda', 1.50, 'True'),
('Coca-Cola Zero Sugar', 'Soda', 1.50, 'True'),
('Diet Coke Caffeine Free', 'Soda', 1.50, 'True'),
('Sprite', 'Soda', 1.50, 'True'),
('Sprite Zero Sugar', 'Soda', 1.50, 'True'),
('Pibb Xtra', 'Soda', 1.50, 'True'),
('Pibb Zero', 'Soda', 1.50, 'True'),
('Barqs Root Beer', 'Soda', 1.50, 'True'),
('Barqs Zero Sugar', 'Soda', 1.50, 'True'),
('Fanta', 'Soda', 1.50, 'True'),
('Fanta Zero', 'Soda', 1.50, 'True'),
('Dr.Pepper', 'Soda', 1.50, 'True'),
('Dr.pepper Diet', 'Soda', 1.50, 'True'),
('Mello Yello', 'Soda', 1.50, 'True'),
('Mello yello Zero Sugar', 'Soda', 1.50, 'True'),
('Vitamin Water', 'Water', 1.50, 'True'),
('Vitamin Water Zero Sugar', 'Water', 1.50, 'True'),
('Fuze Iced Tea', 'Tea', 1.50, 'True'),
('Zero Sugar Fuze Iced Tea', 'Tea', 1.50, 'True'),
('Minute Maid Aguas Frescas', 'Juice', 1.50, 'True'),
('Minute Maid Lemonades', 'Juice', 1.50, 'True'),
('Minute Maid Lemonades Zero Sugar', 'Juice', 1.50, 'True'),
('Minute Maid Sparkling', 'Juice', 1.50, 'True'),
('Minute Maid Zero Sugar', 'Juice', 1.50, 'True'),
('Minute Maid', 'Juice', 1.50, 'True'),
('Barqs Zero Sugar Creme Soda', 'Soda', 1.50, 'True'),
('Barqs Creme Soda', 'Soda', 1.50, 'True'),
('Seagrams Ginger ale', 'Soda', 1.50, 'True'),
('Seagrams Zero Sugar Ginger Ale', 'Soda', 1.50, 'True'),
('Hi-C', 'Juice', 1.50, 'True'),
('Powerade', 'Juice', 1.50, 'True'),
('Powerade Zero Sugar', 'Juice', 1.50, 'True'),
('AHA Sparkling Water', 'Water', 1.50, 'True');

INSERT INTO flavor(name)
VALUES
('Creme Soda'),
('Cherry'),
('Cherry Vanilla'),
('Lemon'),
('Lime'),
('Orange'),
('Raspberry'),
('Vanilla'),
('Orange Vanilla'),
('Ginger Lime'),
('Ginger Lemon'),
('Grape'),
('Peach'),
('Strawberry'),
('Ginger'),
('Blueberry Pomegranate'),
('Raspberry Vanilla'),
('Fruit Punch'),
('Limeade'),
('Citrus Twist'),
('Tropical Punch'),
('Pink Lemonade'),
('Lemonade');

INSERT INTO cup(size, capacity, cost)
VALUES
('Large', 30, 50);
	
INSERT INTO payment(name, type)
VALUES
('American Express', 'Credit Card'),
('Bank Checking Card', 'Debit'),
('Discover Credit Card', 'Credit Card'),
('Capital One Checking Card', 'Debit'),
('Capital One Credit Card', 'Credit Card');

INSERT INTO drink_inventory(drink_id, quantity)
VALUES
(1, 640),
(2, 640),
(3, 640),
(4, 640),
(5, 640),
(6, 640),
(7, 640),
(8, 640),
(9, 640),
(10, 640),
(11, 640),
(12, 640),
(13, 640),
(14, 640),
(15, 640),
(16, 640),
(17, 640),
(18, 640),
(19, 640),
(20, 640),
(21, 640),
(22, 640),
(23, 640),
(24, 640),
(25, 640),
(26, 640),
(27, 640),
(28, 640),
(29, 640),
(30, 640),
(31, 640),
(32, 640),
(33, 640),
(34, 640);

INSERT INTO flavor_inventory(flavor_id, quantity)
VALUES
(1, 640),
(2, 640),
(3, 640),
(4, 640),
(5, 640),
(6, 640),
(7, 640),
(8, 640),
(9, 640),
(10, 640),
(11, 640),
(12, 640),
(13, 640),
(14, 640),
(15, 640),
(16, 640),
(17, 640),
(18, 640),
(19, 640),
(20, 640),
(21, 640),
(22, 640),
(23, 640);

INSERT INTO cup_inventory(cup_id, quantity)
VALUES
(1, 40);

COMMIT TRANSACTION;


--Keep for later
--('Pepsi', 'Soda', 'True'),
--('Green Tea', 'Tea', 'True'),
--('Apple Juice', 'Juice', 'False');