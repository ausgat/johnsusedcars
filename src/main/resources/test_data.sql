USE JohnsUsedCars;

INSERT INTO Customer (cID, cName, cPhone, cEmail) VALUES
    (1, 'Daniel Driver', 5552849, 'danieldriver@googlymail.con'),
    (2, 'Victoria Vroom', 5553030, 'vicvroom@coldmail.con');

INSERT INTO Car (Vin, Make, Model, Year, MSRP) VALUES
    (1234, 'Toyoda', 'ForceRunner', 1977, 2000),
    (2345, 'Hondi', 'Fat', 2019, 12000),
    (0987, 'Fort', 'F-360', 2015, 20000);

INSERT INTO Salesperson (SalespersonID, sName, sPhone, sEmail) VALUES
    (123, 'Earl E. Crook', 5552002, 'earl@juc.con'),
    (321, 'Bob Carguy', 5554120, 'bob@juc.con');
