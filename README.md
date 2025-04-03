# John's Used Cars

This is Group 9's project for COSC 3318-01: Data Base Management Systems. It is
based on [Professor Van Vung Pham's tutorials](https://www.youtube.com/playlist?list=PLxyOsIbHSrxkAbUOm6C1CJyD-zIf5cbsO).

## Getting Started

This project is written in Java using the Netbeans IDE and assumes you followed
the instructions for the class on Blackboard. If you open it in Netbeans, all
you need to do is set up the database. To do that...

1. Run a SQL server in MAMP, making sure the MySQL port is set to 3306
2. Open MySQLWorkbench and connect to the server, which should be on
   localhost:3306
3. Run src/main/resources/juc.sql in MySQLWorkbench to create the database

## Structure

- src/main/java
  - bo: Java classes representing database relations (e.g. Car and Salesperson)
  - dao: Classes that interact directly with the database (e.g. CarHandler and
    SalespersonHandler)
  - jcd: Main application files, including Swing-based GUI forms
  - utils: General utility functions (e.g. connecting to database and running
    queries)

General flow:

1. Create a handler object, like CarHandler ch = CarHandler.new()
2. Call a method from the handler to run a SQL command (e.g. ch.getCars() will
   return a list of Car objects with each car relation's attributes as
   properties)

## Tips

- NetBeans handles a lot of the code for Swing form generation (Java files
  starting with "Frm"). When you edit the code for those forms, don't be alarmed
  if NetBeans prevents you from editing certain sections (grayed out). It can be
  kind of funky sometimes.

## Contributors

- Austin Gatchell
- Please add your name here if you're a contributor!
