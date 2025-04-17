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
cID int AUTO_INCREMENT,
InterestRate int,
MonthlyPayment int,
foreign key (cID) references Customer(cID)
);

USE JohnsUsedCars;

INSERT INTO Customer (cID, cName, cPhone, cEmail) VALUES
    (1, 'Daniel Driver', 5552849, 'danieldriver@googlymail.con'),
    (2, 'Victoria Vroom', 5553030, 'vicvroom@coldmail.con');

INSERT INTO Car (Vin, Make, Model, Year, MSRP) VALUES
    (1234, 'Toyoda', 'ForceRunner', 1977, 2000),
    (2345, 'Hondi', 'Fat', 2019, 12000),
    (0987, 'Fort', 'F-360', 2015, 20000);

INSERT INTO Salesperson (SalespersonID, sUsername, sPassword, sName, sPhone, sEmail) VALUES
    (123, 'EarlGreyTea', 'password123', 'Earl E. Crook', 5552002, 'earl@juc.con'),
    (321, 'GreatestSalesman', 'password1234', 'Bob Carguy', 5554120, 'bob@juc.con');

    