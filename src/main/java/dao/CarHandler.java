package dao;

import bo.Car;
import bo.Inventory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.SQLUtil;

/**
 * Handles interactions between the Java code and the database relations for Car
 *
 */
public class CarHandler {
    private SQLUtil sqlUtil;
    private InventoryHandler ih;
    
    public CarHandler() {
        // Create a new SQLUtil to maintain a connection to the database
        sqlUtil = new SQLUtil();

        // Also create an InventoryHandler
        ih = new InventoryHandler();
    }
    
    /**
     * Create a new Car relation in the database
     * 
     * @param vin   The car's VIN number (primary key)
     * @param make  The car's make
     * @param model The car's model
     * @param year  The car's year
     * @param msrp  The car's MSRP
     * @return Number of rows affected
     */
    public int addCar(String vin, String make, String model, int year, int msrp) {
        // We're creating a pretty long SQL query with lots of data, so let's
        // use a string template to make things a little easier
       try{
        PreparedStatement pst = sqlUtil.prepareStatement("INSERT INTO Car VALUES(?, ?, ?, ?, ?, NULL);");
        pst.setString(1, vin);
        pst.setString(2, make);
        pst.setString(3, model);
        pst.setInt(4, year);
        pst.setInt(5, msrp);
        // Add the values to the string template with String.format (this will
        // fill the template with the given data for each of the %d's and %s's,
        // in order)
        return pst.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(CarHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
    return -1;
    }

    /**
     * Create new Car and Inventory relations in the database
     * 
     * @param vin           The car's VIN number (primary key)
     * @param make          The car's make
     * @param model         The car's model
     * @param year          The car's year
     * @param msrp          The car's MSRP
     * @param stockStatus   The car's availability
     * @param parkingSpot   The car's parking spot
     * @param lotId    The car's parking lot
     * @return Number of rows affected
     */
    public int addCar(String vin, String make, String model, int year, int msrp,
            boolean stockStatus, String parkingSpot, String lotId) {
        PreparedStatement pst1, pst2;
        try {
            // Create the inventory
            pst2 = sqlUtil.prepareStatement(
                "INSERT INTO Inventory VALUES(?, ?, ?)"
            );
            pst2.setBoolean(1, stockStatus);
            pst2.setString(2, parkingSpot);
            pst2.setString(3, lotId);
            pst2.executeUpdate();

            // Add the car
            pst1 = sqlUtil.prepareStatement(
                "INSERT INTO Car(Vin, Make, Model, Year, MSRP, " +
                "lotID, ParkingSpot) VALUES(?, ?, ?, ?, ?, ?, ?)"
            );
            pst1.setString(1, vin);
            pst1.setString(2, make);
            pst1.setString(3, model);
            pst1.setInt(4, year);
            pst1.setInt(5, msrp);
            pst1.setString(6, lotId);
            pst1.setString(7, parkingSpot);

            return pst1.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(CarHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

    /**
     * Delete a car from the database using the given VIN
     * 
     * @param vin   The VIN number of the car
     * @return Number of rows affected
     */
    public int deleteCar(String vin) {
    try {
        PreparedStatement pst = sqlUtil.prepareStatement("DELETE FROM Car WHERE vin = ?");
        pst.setString(1, vin);
        return pst.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(CarHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
    return -1;
}

    /**
     * Update a car by VIN with the given values
     * 
     * @param vin   VIN number of the car to update
     * @param make  Car's make
     * @param model Car's model
     * @param year  Car's year
     * @param msrp  Car's MSRP
     * @param stockStatus Stock status of the car
     * @param parkingSpot Parking spot location
     * @param lotId Parking lot the car is in
     * @return Number of rows affected
     */
    public int updateCar(String vin, String make, String model, int year,
            int msrp, boolean stockStatus, String parkingSpot,
            String lotId) {
        PreparedStatement pst1, pst2;
        try {
            // Add the car
            pst1 = sqlUtil.prepareStatement(
                "UPDATE Car SET Vin=?, Make=?, Model=?, Year=?, Msrp=? " +
                "WHERE Vin=?"
            );
            pst1.setString(1, vin);
            pst1.setString(2, make);
            pst1.setString(3, model);
            pst1.setInt(4, year);
            pst1.setInt(5, msrp);
            pst1.setString(6, vin);
            int ret = pst1.executeUpdate();

            Inventory inv = new InventoryHandler().findInventories(vin)
                    .getFirst();

            if (inv != null) {
                pst2 = sqlUtil.prepareStatement(
                    "UPDATE Inventory " +
                    "SET StockStatus=?, ParkingSpot=?, lotId=? " +
                    "WHERE ParkingSpot=? AND lotId=?"
                );
                pst2.setBoolean(1, stockStatus);
                pst2.setString(2, parkingSpot);
                pst2.setString(3, lotId);
                pst2.setString(4, parkingSpot);
                pst2.setString(5, lotId);
                ret += pst2.executeUpdate();
            }

            return ret;
        } catch (SQLException ex) {
            Logger.getLogger(CarHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }
    
    /**
     * Update a car's cID and inventory status by VIN
     * 
     * @param vin           VIN number of the car to update
     * @param cid           Customer's ID number
     * @param stockStatus   Inventory stock status
     * @return Number of rows affected
     */
    public int updateCar(String vin, int cid, boolean stockStatus) {
        PreparedStatement pst1, pst2;
        try {
            // Add the car
            pst1 = sqlUtil.prepareStatement(
                "UPDATE Car SET cID=? WHERE Vin=?"
            );
            pst1.setString(2, vin);
            pst1.setInt(1, cid);

            int ret = pst1.executeUpdate();
            Inventory inv = new InventoryHandler().findInventories(vin)
                    .getFirst();

            if (inv != null) {
                pst2 = sqlUtil.prepareStatement(
                    "UPDATE Inventory i JOIN Car c " +
                        "ON i.ParkingSpot = c.ParkingSpot " +
                            "AND i.lotID = c.lotID " +
                    "SET i.StockStatus=?" +
                    "WHERE c.Vin = ?"
                );
                pst2.setString(2, vin);
                pst2.setBoolean(1, stockStatus);
                ret += pst2.executeUpdate();
            }

            return ret;
        } catch (SQLException ex) {
            Logger.getLogger(CarHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }
    
    /**
     * Find a car by its VIN
     * 
     * @param vin The VIN of the desired car
     * @return A Car object if found, null otherwise
     */
    public Car findCar(String vin) {
        Car foundCar = null;
        
        String cmdTemplate =
                "SELECT Vin, Make, Model, Year, MSRP FROM Car WHERE Vin = '%s'";
        String cmd = String.format(cmdTemplate, vin);
        
        ResultSet rs = sqlUtil.executeQuery(cmd);
        
        try {
            if (rs.next()) {
                // Get each relevant attribute from the relation
                String make =  rs.getString("Make");
                String model = rs.getString("Model");
                int year = rs.getInt("Year");
                int msrp = rs.getInt("MSRP");
                
                foundCar = new Car(vin, make, model, year, msrp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return foundCar;
    }
    
    /**
     * Get a list of all the Car relations in the database
     * 
     * @return List of all the Car relations
     */
    public List<Car> getCars() {
        List<Car> results = new ArrayList<Car>();

        // Put the query in a string
        String cmd = "SELECT * FROM Car;";

        // Run the query (you could also just put the string directly in the
        // query, but putting it in a string might make things easier to expand
        // on later)
        ResultSet rs = sqlUtil.executeQuery(cmd);
            
        try {
            // Go to the next row (will continue until there are no more rows
            // and rs.next() returns false)
            while (rs.next()) {
                // Get each relevant attribute from the relation
                String vin = rs.getString("Vin");
                String make =  rs.getString("Make");
                String model = rs.getString("Model");
                int year = rs.getInt("Year");
                int msrp = rs.getInt("MSRP");

                // Create a new Car object from the relation data
                Car c = new Car(vin, make, model, year, msrp);

                // Add the newly-created Car object to the list
                results.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CarHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Return the list of Car objects
        return results;
    }
}
