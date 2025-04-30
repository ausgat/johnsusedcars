
drop database if exists JohnsUsedCars;
create database JohnsUsedCars;
use JohnsUsedCars;

create table Customer(
    cID int primary key AUTO_INCREMENT,
    cName varchar(50),
    cPhone varchar(50),
    cEmail varchar(250)
);

create table Inventory (
    StockStatus bool,
    ParkingSpot varchar(25),
    ParkingLot varchar(25),
    primary key (ParkingSpot, ParkingLot)
);

create table Car(
    Vin varchar(50) primary key,
    Make varchar(50),
    Model varchar(50),
    Year int,
    MSRP int,
    cID int null,
    ParkingLot varchar(25),
    ParkingSpot varchar(25),
    foreign key (cID) references Customer(cID),
    foreign key (ParkingSpot, ParkingLot) references Inventory(ParkingSpot, ParkingLot)
);

create table Salesperson(
    SalespersonID int primary key AUTO_INCREMENT,
    sUsername varchar(100),
    sPassword varchar(100),
    sName varchar(100),
    sPhone varchar(50),
    sEmail varchar(250)
);

create table Sale(
    sID int primary key AUTO_INCREMENT,
    sDate date,
    sPrice int,
    SalespersonID int,
    Vin varchar(50),
    foreign key (SalespersonID) references Salesperson(SalespersonID),
    foreign key (vin) references Car(vin)
);

create table Financing(
    cID int,
    InterestRate int,
    MonthlyPayment int,
    foreign key (cID) references Customer(cID)
);

-- Sample data
INSERT INTO Customer (cID, cName, cPhone, cEmail)
VALUES
(1, 'Alice Johnson', '123-456-7890', 'alice.johnson@example.com'),
(2, 'Bob Smith', '234-567-8901', 'bob.smith@example.com'),
(3, 'Clara Lee', '345-678-9012', 'clara.lee@example.com');

INSERT INTO Inventory (StockStatus, ParkingSpot, ParkingLot)
VALUES 
  (true, 'A1', 'Lot1'),
  (true, 'B2', 'Lot2'),
  (false, 'C3', 'Lot3'); 

-- VIN values are now lowercase
INSERT INTO Car (vin, Make, Model, Year, MSRP, cID, ParkingSpot, ParkingLot)
VALUES
('1001', 'Toyota', 'Camry', 2022, 25000, 1, 'A1', 'Lot1'),
('1002', 'Honda', 'Civic', 2021, 22000, 2, 'B2', 'Lot2'),
('1003', 'Ford', 'Mustang', 2023, 35000, 3, 'C3', 'Lot3');

INSERT INTO Salesperson (SalespersonID, sUsername, sPassword, sName, sPhone, sEmail)
VALUES
(501, 'Danny', 'CxTVAaWURCoBxoWVQbyz6BZNGD0yk3uFGDVEL2nVyU4=', 'Daniel Rivera', '456-789-0123', 'daniel.rivera@dealership.com'),
(502, 'Emmy', 'bPYV1byqx3g1Ko8fM2DSPwLzTsGC4lmJf9bOSF14cNQ=', 'Emily Chen', '567-890-1234', 'emily.chen@dealership.com');

INSERT INTO Sale (sID, sDate, sPrice, SalespersonID, vin)
VALUES
(2001, '2024-12-15', 24000, 501, '1001'),
(2002, '2025-01-20', 21500, 502, '1002');

INSERT INTO Financing (cID, InterestRate, MonthlyPayment)
VALUES
(1, 5, 450),
(2, 4, 420);

-- View all customers and their cars
SELECT c.cName, car.Make, car.Model, car.Year
FROM Customer c
JOIN Car car ON c.cID = car.cID;

-- Show inventory cars with their status
SELECT car.vin, car.Make, car.Model, inv.ParkingSpot, inv.ParkingLot
FROM Car car
JOIN Inventory inv 
  ON car.ParkingSpot = inv.ParkingSpot AND car.ParkingLot = inv.ParkingLot
WHERE inv.StockStatus = TRUE;

-- View all sales and who made them
SELECT s.sID, s.sDate, s.sPrice, sp.sName AS Salesperson, car.Make, car.Model, cust.cName AS Customer
FROM Sale s
JOIN Salesperson sp ON s.SalespersonID = sp.SalespersonID
JOIN Car car ON s.vin = car.vin
JOIN Customer cust ON car.cID = cust.cID;

-- Show customers with financing details
SELECT c.cName, f.InterestRate, f.MonthlyPayment
FROM Customer c
JOIN Financing f ON c.cID = f.cID;

-- Show cars still in stock
SELECT car.vin, car.Make, car.Model, inv.ParkingSpot, inv.ParkingLot
FROM Car car
JOIN Inventory inv 
  ON car.ParkingSpot = inv.ParkingSpot 
  AND car.ParkingLot = inv.ParkingLot
WHERE inv.StockStatus = TRUE;

-- Find total sales per salesperson
SELECT sp.sName, COUNT(s.sID) AS TotalSales, SUM(s.sPrice) AS TotalRevenue
FROM Salesperson sp
JOIN Sale s ON sp.SalespersonID = s.SalespersonID
GROUP BY sp.sName;

-- Sales made after January 1, 2025
SELECT s.sID, s.sDate, s.sPrice, sp.sName, car.Make, car.Model
FROM Sale s
JOIN Salesperson sp ON s.SalespersonID = sp.SalespersonID
JOIN Car car ON s.vin = car.vin
WHERE s.sDate > '2025-01-01';

