drop database if exists JohnsUsedCars;
create database JohnsUsedCars;
use JohnsUsedCars;

create table Customer(
cID int primary key AUTO_INCREMENT,
cName varchar(50),
cPhone varchar(50),
cEmail varchar(250)
);

create table Inventory(
iID int primary key,
ParkingLot varchar(25)
);

create table Car(
Vin varchar(50) primary key,
Make varchar(50),
Model varchar(50),
Year int,
MSRP int,
StockStatus bool,
ParkingSpot varchar(25),
iID int null,
cID int null,
foreign key (iID) references Inventory(iID)
    ON DELETE SET NULL,
foreign key (cID) references Customer(cID)
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

INSERT INTO Inventory (iID, ParkingLot)
VALUES
(101, 'Lot A'),
(102, 'Lot B');

INSERT INTO Car (Vin, Make, Model, Year, MSRP, StockStatus, ParkingSpot, cID, iID)
VALUES
(1001, 'Toyota', 'Camry', 2022, 25000, false, 'A1', 1, 101),
(1002, 'Honda', 'Civic', 2021, 22000, false, 'B2', 2, 101),
(1003, 'Ford', 'Mustang', 2023, 35000, false, 'C3', 3, 102);

# Passwords: password1, password2
INSERT INTO Salesperson (SalespersonID, sUsername, sPassword, sName, sPhone, sEmail)
VALUES
(501, 'Danny', 'CxTVAaWURCoBxoWVQbyz6BZNGD0yk3uFGDVEL2nVyU4=', 'Daniel Rivera', '456-789-0123', 'daniel.rivera@dealership.com'),
(502, 'Emmy', 'bPYV1byqx3g1Ko8fM2DSPwLzTsGC4lmJf9bOSF14cNQ=', 'Emily Chen', '567-890-1234', 'emily.chen@dealership.com');
 
INSERT INTO Sale (sID, sDate, sPrice, SalespersonID, Vin)
VALUES
(2001, '2024-12-15', 24000, 501, 1001),
(2002, '2025-01-20', 21500, 502, 1002);

INSERT INTO Financing (cID, InterestRate, MonthlyPayment)
VALUES
(1, 5, 450),
(2, 4, 420);
