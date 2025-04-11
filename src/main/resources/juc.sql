drop database if exists JohnsUsedCars;
create database JohnsUsedCars;
use JohnsUsedCars;

create table Customer(
cID int primary key,
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
sUsername varchar(250) primary key,
sPassword varchar(100)
sPhone int,
sEmail varchar(250)
);

create table Sale(
sID int primary key,
sDate date,
sPrice int,
sUsername varchar(250),
Vin int,
foreign key (sUsername) references Salesperson(sUsername),
foreign key (Vin) references Car(Vin)
);

create table Financing(
cID int,
InterestRate int,
MonthlyPayment int,
foreign key (cID) references Customer(cID)
);
