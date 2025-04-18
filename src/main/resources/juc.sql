drop database if exists JohnsUsedCars;
create database JohnsUsedCars;
use JohnsUsedCars;

create table Customer(
cID int primary key AUTO_INCREMENT,
cName varchar(50),
cPhone int,
cEmail varchar(250)
);

create table Car(
Vin int primary key,
Make varchar(50),
Model varchar(50),
Year int,
MSRP int,
cID int,
foreign key (cID) references Customer(cID)
);

create table Inventory(
Vin int,
StockStatus bool,
ParkingSpot varchar(25),
ParkingLot varchar(25),
foreign key (Vin) references Car(Vin)
);

create table Salesperson(
SalespersonID int primary key AUTO_INCREMENT,
sUsername varchar(250),
sPassword varchar(100),
sName varchar(50),
sPhone varchar(250),
sEmail varchar(250)
);



create table Sale(
sID int primary key AUTO_INCREMENT,
sDate date,
sPrice int,
SalespersonID int,
Vin int,
foreign key (SalespersonID) references Salesperson(SalespersonID),
foreign key (Vin) references Car(Vin)
);

create table Financing(
cID int,
sID int,
InterestRate int,
MonthlyPayment int,
foreign key (cID) references Customer(cID),
foreign key (sID) references Sale(sID),
primary key (cID, sID)
);

INSERT INTO Customer (cID, cName, cPhone, cEmail) VALUES
    (1, 'Daniel Driver', 5552849, 'danieldriver@googlymail.con'),
    (2, 'Victoria Vroom', 5553030, 'vicvroom@coldmail.con'),
    (3, 'Dwight Tower', 6779999, 'dwight73@email.com');


INSERT INTO Car (Vin, Make, Model, Year, MSRP, cID) VALUES
    (1001, 'Toyoda', 'ForceRunner', 1977, 2000, 1),
    (1002, 'Hondi', 'Fat', 2019, 12000, 2),
    (1003, 'Fort', 'F-360', 2015, 20000, 3);

INSERT INTO Salesperson (SalespersonID, sUsername, sPassword, sName, sPhone, sEmail) VALUES
    (123, 'EarlGreyTea', 'password123', 'Earl E. Crook', 5552002, 'earl@juc.con'),
    (321, 'GreatestSalesman', 'password1234', 'Bob Carguy', 5554120, 'bob@juc.con');

INSERT INTO Inventory (Vin, StockStatus, ParkingSpot, ParkingLot) VALUES
    (1001, TRUE, 'A1', 'Lot A'),
    (1002, FALSE, 'B2', 'Lot B'),
    (1003, TRUE, 'C3', 'Lot A');

INSERT INTO Sale (sID, sDate, sPrice, SalespersonID, Vin) VALUES
    (2001, '2024-12-15', 24000, 123, 1001),
    (2002, '2025-01-20', 21500, 321, 1002);

INSERT INTO Financing (cID, sID, InterestRate, MonthlyPayment) VALUES
    (1, 2001, 5, 450),
    (2, 2002, 4, 420);