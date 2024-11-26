CREATE DATABASE torinopizza;
USE torinopizza;

-- Menu
CREATE TABLE Menu(
    PizzaID INT AUTO_INCREMENT PRIMARY KEY,
    Navn VARCHAR(100) NOT NULL,
    Beskrivelse TEXT,
    Pris DECIMAL(10, 2) NOT NULL
);
-- Inventar
CREATE TABLE Inventar ( 
InventarID INT auto_increment primary KEY,
InventarNavn VARCHAR(100),
InventarAntal INT,
ReorderLevel INT
);

-- Kunder--
CREATE TABLE Kunde (
KundeID INT auto_increment primary Key, 
Kundenavn Varchar(100) NOT NULL,
Addresse Varchar (250), 
Telefon varchar (15) 
);

-- PizzaIngrediens
CREATE TABLE PizzaIngrediens (
PizzaIngrediensID INT PRIMARY KEY AUTO_INCREMENT,
PizzaID INT,
InventarID INT,
AntalBrug INT NOT NULL,
FOREIGN KEY (PizzaID) REFERENCES Menu(PizzaID),
FOREIGN KEY (InventarID) REFERENCES Inventar(InventarID)
);

-- Ordre:
CREATE TABLE Ordre(
OrdreID INT PRIMARY KEY AUTO_INCREMENT,
KundeID INT,
Ordredato DATETIME NOT NULL,
TotalPris DECIMAL (10,2) NOT NULL,
LeveringsStatus ENUM ('Leveret', 'Under Levering', 'Ordre modtaget') NOT NULL DEFAULT 'Ordre modtaget',
FOREIGN KEY (KundeID) REFERENCES Kunde(KundeID)
);

-- Ordredetalje:
CREATE TABLE OrdreDetalje (
OrdreDetaljeID INT PRIMARY KEY AUTO_INCREMENT,
OrdreID INT,
PizzaID INT,
Antal INT NOT NULL, 
Subtotal DECIMAL(10,2) NOT NULL, 
FOREIGN KEY (OrdreID) REFERENCES Ordre(OrdreID),
FOREIGN KEY (PizzaID) REFERENCES Menu(PizzaID)
);


-- Ekstraordre: 
CREATE TABLE OrdreExtra (
    OrdreExtraID INT PRIMARY KEY AUTO_INCREMENT,
    OrdreDetaljeID INT,
    InventarID INT,
    Antal INT NOT NULL,
    ExtraPris DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (OrdreDetaljeID) REFERENCES OrdreDetalje(OrdreDetaljeID), 
    FOREIGN KEY (InventarID) REFERENCES Inventar(InventarID) 
);

-- LeveringsTabel:-- 
CREATE TABLE Levering (
    LeveringsID INT PRIMARY KEY AUTO_INCREMENT, 
    OrdreID INT, 
    LeveringNavn VARCHAR(100) NOT NULL, 
    LeveringsTid DATETIME NOT NULL, 
    LeveringsAddresse VARCHAR(255) NOT NULL, 
    FOREIGN KEY (OrdreID) REFERENCES Ordre(OrdreID) 
);







INSERT INTO Menu (Navn, Beskrivelse, Pris) VALUES
('Margherita', 'En klassisk pizza med tomatsauce, mozzarella og basilikum', 75.00),
('Pepperoni', 'Pizza med tomatsauce, mozzarella og pepperoni', 85.00),
('Vegetariana', 'Pizza med tomatsauce, mozzarella, og grøntsager', 80.00),
('Hawaii', 'Pizza med tomatsauce, mozzarella, skinke og ananas', 90.00),
('Calzone', 'En fyldt pizza med tomatsauce, mozzarella, skinke og champignon', 95.00);

INSERT INTO Inventar (InventarNavn, InventarAntal, ReorderLevel) VALUES
('Tomatsauce', 50, 10),
('Mozzarella', 30, 5),
('Pepperoni', 20, 3),
('Skinke', 25, 5),
('Ananas', 40, 8),
('Champignon', 30, 7),
('Basilikum', 15, 3);

INSERT INTO Kunde (Kundenavn, Addresse, Telefon) VALUES
('John Doe', 'Vesterbrogade 45, 1620 København V', '12345678'),
('Jane Smith', 'Østerbrogade 10, 2100 København Ø', '87654321'),
('Michael Jensen', 'Amagerbrogade 23, 2300 København S', '11223344'),
('Sarah Andersen', 'Nørrebrogade 5, 2200 København N', '44332211');

-- PizzaIngrediens for Margherita
INSERT INTO PizzaIngrediens (PizzaID, InventarID, AntalBrug) VALUES
(1, 1, 1),  -- Tomatsauce
(1, 2, 2),  -- Mozzarella
(1, 7, 1);  -- Basilikum

-- PizzaIngrediens for Pepperoni
INSERT INTO PizzaIngrediens (PizzaID, InventarID, AntalBrug) VALUES
(2, 1, 1),  -- Tomatsauce
(2, 2, 2),  -- Mozzarella
(2, 3, 1);  -- Pepperoni

-- PizzaIngrediens for Vegetariana
INSERT INTO PizzaIngrediens (PizzaID, InventarID, AntalBrug) VALUES
(3, 1, 1),  -- Tomatsauce
(3, 2, 2),  -- Mozzarella
(3, 6, 1),  -- Champignon
(3, 7, 1);  -- Basilikum

-- PizzaIngrediens for Hawaii
INSERT INTO PizzaIngrediens (PizzaID, InventarID, AntalBrug) VALUES
(4, 1, 1),  -- Tomatsauce
(4, 2, 2),  -- Mozzarella
(4, 4, 1),  -- Skinke
(4, 5, 1);  -- Ananas

-- PizzaIngrediens for Calzone
INSERT INTO PizzaIngrediens (PizzaID, InventarID, AntalBrug) VALUES
(5, 1, 1),  -- Tomatsauce
(5, 2, 2),  -- Mozzarella
(5, 4, 1),  -- Skinke
(5, 6, 1);  -- Champignon

INSERT INTO Ordre (KundeID, Ordredato, TotalPris, LeveringsStatus) VALUES
(1, '2024-11-19 12:30:00', 75.00, 'Leveret'),
(2, '2024-11-19 14:00:00', 170.00, 'Under Levering'),
(3, '2024-11-19 15:00:00', 180.00, 'Ordre modtaget'),
(4, '2024-11-19 16:30:00', 265.00, 'Leveret');

-- Ordredetaljer for OrdreID 1 (John Doe)
INSERT INTO OrdreDetalje (OrdreID, PizzaID, Antal, Subtotal) VALUES
(1, 1, 1, 75.00);

-- Ordredetaljer for OrdreID 2 (Jane Smith)
INSERT INTO OrdreDetalje (OrdreID, PizzaID, Antal, Subtotal) VALUES
(2, 2, 1, 85.00),
(2, 4, 1, 90.00);

-- Ordredetaljer for OrdreID 3 (Michael Jensen)
INSERT INTO OrdreDetalje (OrdreID, PizzaID, Antal, Subtotal) VALUES
(3, 2, 1, 85.00),
(3, 3, 1, 80.00),
(3, 5, 1, 95.00);

-- Ordredetaljer for OrdreID 4 (Sarah Andersen)
INSERT INTO OrdreDetalje (OrdreID, PizzaID, Antal, Subtotal) VALUES
(4, 4, 1, 90.00),
(4, 5, 1, 95.00),
(4, 1, 1, 75.00);
-- Ekstraordre for OrdreDetaljeID 1 (John Doe)
INSERT INTO OrdreExtra (OrdreDetaljeID, InventarID, Antal, ExtraPris) VALUES
(1, 5, 1, 5.00);  -- Ananas topping for Margherita

-- Ekstraordre for OrdreDetaljeID 2 (Jane Smith)
INSERT INTO OrdreExtra (OrdreDetaljeID, InventarID, Antal, ExtraPris) VALUES
(2, 3, 1, 5.00);  -- Pepperoni topping for Pepperoni

-- Ekstraordre for OrdreDetaljeID 3 (Michael Jensen)
INSERT INTO OrdreExtra (OrdreDetaljeID, InventarID, Antal, ExtraPris) VALUES
(3, 7, 1, 5.00);   -- Basilikum topping for Vegetariana

-- Ekstraordre for OrdreDetaljeID 4 (Sarah Andersen)
INSERT INTO OrdreExtra (OrdreDetaljeID, InventarID, Antal, ExtraPris) VALUES
(4, 6, 1, 5.00);   -- Champignon topping for Calzone

INSERT INTO Levering (OrdreID, LeveringNavn, LeveringsTid, LeveringsAddresse) VALUES
(1, 'John Doe', '2024-11-19 13:00:00', 'Vesterbrogade 45, 1620 København V'),
(2, 'Jane Smith', '2024-11-19 14:30:00', 'Østerbrogade 10, 2100 København Ø'),
(3, 'Michael Jensen', '2024-11-19 15:30:00', 'Amagerbrogade 23, 2300 København S'),
(4, 'Sarah Andersen', '2024-11-19 17:00:00', 'Nørrebrogade 5, 2200 København N');

