package dao;

import bo.Inventory;
import bo.InventoryStock;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.SQLUtil;

/**
 * Handler for the Inventory relation
 */
public class InventoryHandler {
    
    private SQLUtil sqlUtil = null;
    
    public InventoryHandler() {
        sqlUtil = new SQLUtil();
    }

    /**
     * Add an Inventory relation to the database
     * @param vin           Car's VIN
     * @param stockStatus   Stock status of the car
     * @param parkingSpot   Car's parking spot
     * @param parkingLot    Car's parking lot
     * @return -1 on failure or number of rows affected on success
     */
    public int addInventory(String vin, boolean stockStatus, String parkingSpot,
            String parkingLot) {
        try {
            // Create a SQL injection-safe statement
            PreparedStatement pst = sqlUtil.prepareStatement(
                "INSERT INTO Inventory " +
                "(StockStatus, ParkingSpot, ParkingLot) " +
                "VALUES (?, ?, ?)"
            );

            // Set the values where the ?s appear in the SQL statement
         
            pst.setBoolean(1, stockStatus);
            pst.setString(2, parkingSpot);
            pst.setString(3, parkingLot);

            // Execute the statement and return the result
            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InventoryHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }

        // Return value when there was an error
        return -1;
    }

    /**
     * Update an Inventory relation with the given VIN in the database
     * @param vin           Car's VIN
     * @param stockStatus   Stock status of the car
     * @param parkingSpot   Car's parking spot
     * @param parkingLot    Car's parking lot
     * @return -1 on failure or number of rows affected on success
     */
    public int updateInventory(boolean stockStatus,
            String parkingSpot, String parkingLot) {
        try {
            // Create a SQL injection-safe statement
            PreparedStatement pst = sqlUtil.prepareStatement(
                "UPDATE Inventory " +
                "SET StockStatus=?, ParkingSpot=?, ParkingLot=? " +
                "WHERE Vin=?"
            );

            // Set the values where the ?s appear in the SQL statement
            pst.setBoolean(1, stockStatus);
            pst.setString(2, parkingSpot);
            pst.setString(3, parkingLot);

            // Vin is fourth as it has to appear in the WHERE Vin=? clause
          
            // Execute the statement and return the result
            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InventoryHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }

        // Return value when there was an error
        return -1;
    }

    /**
     * Delete an Inventory relation with the given VIN from the database
     * @param vin Car's VIN
     * @return -1 on failure or number of rows affected on success
     */
    public int deleteInventory(String vin) {
        try {
            // Create a SQL injection-safe statement
            PreparedStatement pst = sqlUtil.prepareStatement(
                "DELETE FROM Inventory WHERE Vin=?"
            );
            
            // Set the VIN by replacing ? in the statement with vin
            pst.setString(1, vin);

            // Execute the statement and return the result
            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InventoryHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
        
        // Return value when there was an error
        return -1;
    }

    public ArrayList<Inventory> findInventories(String vin) {
        ArrayList<Inventory> inventories = new ArrayList<>();

        try {
            // Create a SQL injection-safe statement
           PreparedStatement pst = sqlUtil.prepareStatement(
    "SELECT i.StockStatus, i.ParkingSpot, i.ParkingLot " +
    "FROM Inventory i " +
    "JOIN Car c ON c.Vin = ? " +
    "WHERE c.ParkingSpot = i.ParkingSpot AND c.ParkingLot = i.ParkingLot"
);
            
            // Set the VIN by replacing ? in the statement with vin
            pst.setString(1, vin);

            // Execute the statement and get the result set
            ResultSet rs = pst.executeQuery();

            // Iterate through the result set, and add each row as an Inventory
            // to the array
            while (rs.next()) {
                // Get the relevant attributes from the relation
                boolean stockStatus = rs.getBoolean("StockStatus");
                String parkingSpot = rs.getString("ParkingSpot");
                String parkingLot = rs.getString("ParkingLot");
                
                // Create an Inventory object from the attributes and add it to
                // the array
                inventories.add(new Inventory(vin, stockStatus, parkingSpot,
                        parkingLot));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InventoryHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
        
        // Return value when there was an error
        return inventories;
    }

    public ArrayList<InventoryStock> getInventoryStock() {
        ArrayList<InventoryStock> inventories = new ArrayList<>();

        try {
            // Create a SQL statement
            String cmd = 
    "SELECT COUNT(c.Vin) AS CarCount, i.ParkingLot " +
    "FROM Car c " +
    "JOIN Inventory i ON c.ParkingSpot = i.ParkingSpot AND c.ParkingLot = i.ParkingLot " +
    "GROUP BY i.ParkingLot";
            // Execute the statement and get the result set
            ResultSet rs = sqlUtil.executeQuery(cmd);

            // Iterate through the result set, and add each row as an Inventory
            // to the array
            while (rs.next()) {
                // Get the relevant attributes from the relation
                int carCount = rs.getInt("CarCount");
                String parkingLot = rs.getString("ParkingLot");
                
                // Create an InventoryStock object from the attributes and add
                // it to the array
                inventories.add(new InventoryStock(carCount, parkingLot));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InventoryHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
        
        // Return value when there was an error
        return inventories;
    }
}
