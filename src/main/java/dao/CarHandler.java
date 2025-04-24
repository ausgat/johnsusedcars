package dao;

import bo.Car;
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
     * Create new Car relation in the database
     * 
     * @param vin           The car's VIN number (primary key)
     * @param make          The car's make
     * @param model         The car's model
     * @param year          The car's year
     * @param msrp          The car's MSRP
     * @param stockStatus   The car's availability
     * @param parkingSpot   The car's parking spot
     * @param iid           Inventory ID number
     * @return Number of rows affected
     */
    public int addCar(String vin, String make, String model, int year, int msrp,
            boolean stockStatus, String parkingSpot, int iid) {
        try {
            PreparedStatement pst;

            pst = sqlUtil.prepareStatement(
                "INSERT INTO Car VALUES(?, ?, ?, ?, ?, ?, ?, ?)"
            );
            pst.setString(1, vin);
            pst.setString(2, make);
            pst.setString(3, model);
            pst.setInt(4, year);
            pst.setInt(5, msrp);
            pst.setBoolean(6, stockStatus);
            pst.setString(7, parkingSpot);
            pst.setInt(8, iid);

            return pst.executeUpdate();
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
            int msrp, boolean stockStatus, String parkingSpot, int iid) {
        try {
            // Add the car
            PreparedStatement pst = sqlUtil.prepareStatement(
                "UPDATE Car SET Vin=?, Make=?, Model=?, Year=?, Msrp=?, " +
                "StockStatus=?, ParkingSpot=?, iID=? " +
                "WHERE Vin=?"
            );
            pst.setString(1, vin);
            pst.setString(2, make);
            pst.setString(3, model);
            pst.setInt(4, year);
            pst.setInt(5, msrp);
            pst.setBoolean(6, stockStatus);
            pst.setString(7, parkingSpot);
            pst.setInt(8, iid);
            pst.setString(9, vin);
            return pst.executeUpdate();
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
        try {
            PreparedStatement pst = sqlUtil.prepareStatement(
                "UPDATE Car SET cID=?, StockStatus=? WHERE Vin=?"
            );
            pst.setString(3, vin);
            pst.setInt(1, cid);
            pst.setBoolean(2, stockStatus);

            return pst.executeUpdate();
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
                "SELECT * FROM Car JOIN Inventory WHERE Vin = '%s'";
        String cmd = String.format(cmdTemplate, vin);
        ResultSet rs = sqlUtil.executeQuery(cmd);
        
        try {
            if (rs.next()) {
                // Get each relevant attribute from the relation
                String make =  rs.getString("Make");
                String model = rs.getString("Model");
                int year = rs.getInt("Year");
                int msrp = rs.getInt("MSRP");
                boolean stockStatus = rs.getBoolean("StockStatus");
                String parkingSpot = rs.getString("ParkingSpot");
                int iid = rs.getInt("iID");
                int cid = rs.getInt("cID");
                
                foundCar = new Car(vin, make, model, year, msrp, stockStatus,
                        parkingSpot, iid, cid);
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
                boolean stockStatus = rs.getBoolean("StockStatus");
                String parkingSpot = rs.getString("ParkingSpot");
                int iid = rs.getInt("iID");
                int cid = rs.getInt("cID");

                // Create a new Car object from the relation data
                Car c = new Car(vin, make, model, year, msrp, stockStatus,
                        parkingSpot, iid, cid);

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
