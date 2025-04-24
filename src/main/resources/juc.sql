drop database if exists JohnsUsedCars;
create database JohnsUsedCars;
use JohnsUsedCars;

create table Customer(
cID int primary key AUTO_INCREMENT,
cName varchar(50),
cPhone varchar(50),
cEmail varchar(250)
);

create table Car(
Vin varchar(50) primary key,
Make varchar(50),
Model varchar(50),
Year int,
MSRP int,
cID int null,
foreign key (cID) references Customer(cID) 
ON DELETE SET NULL
);

create table Inventory(
Vin varchar(50),
StockStatus bool,
ParkingSpot varchar(25),
ParkingLot varchar(25),
foreign key (Vin) references Car(Vin)
    ON DELETE CASCADE
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

INSERT INTO Car (Vin, Make, Model, Year, MSRP, cID)
VALUES
(1001, 'Toyota', 'Camry', 2022, 25000, 1),
(1002, 'Honda', 'Civic', 2021, 22000, 2),
(1003, 'Ford', 'Mustang', 2023, 35000, 3);

INSERT INTO Inventory (Vin, StockStatus, ParkingSpot, ParkingLot)
VALUES
(1001, TRUE, 'A1', 'Lot A'),
(1002, FALSE, 'B2', 'Lot B'),
(1003, TRUE, 'C3', 'Lot A');

INSERT INTO Salesperson (SalespersonID, sUsername, sPassword, sName, sPhone, sEmail)
VALUES
(501, 'Danny', 'password1', 'Daniel Rivera', '456-789-0123', 'daniel.rivera@dealership.com'),
(502, 'Emmy', 'password2', 'Emily Chen', '567-890-1234', 'emily.chen@dealership.com');
 
INSERT INTO Sale (sID, sDate, sPrice, SalespersonID, Vin)
VALUES
(2001, '2024-12-15', 24000, 501, 1001),
(2002, '2025-01-20', 21500, 502, 1002);

INSERT INTO Financing (cID, InterestRate, MonthlyPayment)
VALUES
(1, 5, 450),
(2, 4, 420);
