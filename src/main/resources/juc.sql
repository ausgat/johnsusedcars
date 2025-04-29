drop database if exists JohnsUsedCars;
create database JohnsUsedCars;
use JohnsUsedCars;

create table Customer(
cID int primary key AUTO_INCREMENT,
cName varchar(50),
cPhone varchar(50),
cEmail varchar(250)
);

CREATE TABLE Inventory(
    ParkingSpot varchar(25),
    ParkingLot varchar(25),
    StockStatus boolean,
    PRIMARY KEY (ParkingSpot, ParkingLot)
);

create table Car(
Vin varchar(50) primary key,
Make varchar(50),
Model varchar(50),
Year int,
MSRP int,
cID int null,
ParkingSpot varchar(25),
ParkingLot varchar(25),
foreign key (cID) references Customer(cID), 
foreign key (ParkingSpot, ParkingLot) references Inventory(ParkingSpot, ParkingLot)
ON DELETE SET NULL
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
foreign key (SalespersonID) references Salesperson(SalespersonID)
    ON DELETE CASCADE,
foreign key (Vin) references Car(Vin)
    ON DELETE CASCADE
);

create table Financing(
cID int,
vin varchar(50),
InterestRate int,
MonthlyPayment int,
foreign key (cID) references Customer(cID)
	ON DELETE CASCADE
);


INSERT INTO Customer (cID, cName, cPhone, cEmail)
VALUES
(1, 'Alice Johnson', '123-456-7890', 'alice.johnson@example.com'),
(2, 'Bob Smith', '234-567-8901', 'bob.smith@example.com'),
(3, 'Clara Lee', '345-678-9012', 'clara.lee@example.com');

INSERT INTO Inventory (ParkingLot, ParkingSpot, StockStatus)
VALUES
('Lot A', 'A1', TRUE),
('Lot B', 'B2', FALSE),
('Lot A', 'C3', TRUE);

INSERT INTO Car (Vin, Make, Model, Year, MSRP, cID, ParkingLot, ParkingSpot)
VALUES
('1001', 'Toyota', 'Camry', 2022, 25000, 1, 'Lot A', 'A1'),
('1002', 'Honda', 'Civic', 2021, 22000, 2, 'Lot B', 'B2'),
('1003', 'Ford', 'Mustang', 2023, 35000, 3, 'Lot A', 'C3');

INSERT INTO Salesperson (SalespersonID, sUsername, sPassword, sName, sPhone, sEmail)
VALUES
(501, 'Danny', 'CxTVAaWURCoBxoWVQbyz6BZNGD0yk3uFGDVEL2nVyU4=', 'Daniel Rivera', '456-789-0123', 'daniel.rivera@dealership.com'),
(502, 'Emmy', 'bPYV1byqx3g1Ko8fM2DSPwLzTsGC4lmJf9bOSF14cNQ=', 'Emily Chen', '567-890-1234', 'emily.chen@dealership.com');

INSERT INTO Sale (sID, sDate, sPrice, SalespersonID, Vin)
VALUES
(2001, '2024-12-15', 24000, 501, '1001'),
(2002, '2025-01-20', 21500, 502, '1002');

INSERT INTO Financing (cID, Vin, InterestRate, MonthlyPayment)
VALUES
(1, '1001', 5, 450),
(2, '1002', 4, 420);