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
        String cmdTemplate = "INSERT INTO Car VALUES('%s', '%s', '%s', %d, %d, NULL);";

        // Add the values to the string template with String.format (this will
        // fill the template with the given data for each of the %d's and %s's,
        // in order)
        String cmd = String.format(cmdTemplate, vin, make, model, year, msrp);

        // Run the SQL command and return its value
        return sqlUtil.executeUpdate(cmd);
    }

    /**
     * Create new Car and Inventory relations to the database
     * 
     * @param vin           The car's VIN number (primary key)
     * @param make          The car's make
     * @param model         The car's model
     * @param year          The car's year
     * @param msrp          The car's MSRP
     * @param stockStatus   The car's availability
     * @param parkingSpot   The car's parking spot
     * @param parkingLot    The car's parking lot
     * @return Number of rows affected
     */
    public int addCar(String vin, String make, String model, int year, int msrp,
            boolean stockStatus, String parkingSpot, String parkingLot) {
        PreparedStatement pst1, pst2;
        try {
            // Add the car
            pst1 = sqlUtil.prepareStatement(
                "INSERT INTO Car VALUES(?, ?, ?, ?, ?, NULL)"
            );
            pst1.setString(1, vin);
            pst1.setString(2, make);
            pst1.setString(3, model);
            pst1.setInt(4, year);
            pst1.setInt(5, msrp);
            int ret1 = pst1.executeUpdate();

            pst2 = sqlUtil.prepareStatement(
                "INSERT INTO Inventory VALUES(?, ?, ?, ?)"
            );
            pst2.setString(1, vin);
            pst2.setBoolean(2, stockStatus);
            pst2.setString(3, parkingSpot);
            pst2.setString(4, parkingLot);

            return ret1 + pst2.executeUpdate();
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
        String cmdTemplate = "DELETE FROM Car WHERE vin = '%s'";
        String cmd = String.format(cmdTemplate, vin);

        return sqlUtil.executeUpdate(cmd);
    }

    /**
     * Update a car by VIN with the given values
     * 
     * @param vin   VIN number of the car to update
     * @param make  Car's make
     * @param model Car's model
     * @param year  Car's year
     * @param msrp  Car's MSRP
     * @return Number of rows affected
     */
    public int updateCar(String vin, String make, String model, int year,
            int msrp, boolean stockStatus, String parkingSpot,
            String parkingLot) {
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
                    "SET Vin=?, StockStatus=?, ParkingSpot=?, ParkingLot=? " +
                    "WHERE Vin=?"
                );
                pst2.setString(1, vin);
                pst2.setBoolean(2, stockStatus);
                pst2.setString(3, parkingSpot);
                pst2.setString(4, parkingLot);
                pst2.setString(5, vin);
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
