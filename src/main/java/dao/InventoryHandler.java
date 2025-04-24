package dao;

import bo.Inventory;
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
     * @param id            Inventory ID
     * @param parkingLot    Parking lot
     * @return -1 on failure or number of rows affected on success
     */
    public int addInventory(int id, String parkingLot) {
        try {
            // Create a SQL injection-safe statement
            PreparedStatement pst = sqlUtil.prepareStatement(
                "INSERT INTO Inventory (iID, ParkingLot) VALUES (?, ?)"
            );

            // Set the values where the ?s appear in the SQL statement
            pst.setInt(1, id);
            pst.setString(2, parkingLot);

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
     * Update an Inventory relation with the given ID in the database
     * @param id            Inventory ID
     * @param parkingLot    Parking lot
     * @return -1 on failure or number of rows affected on success
     */
    public int updateInventory(int id, String parkingLot) {
        try {
            // Create a SQL injection-safe statement
            PreparedStatement pst = sqlUtil.prepareStatement(
                "UPDATE Inventory ParkingLot=? WHERE iID=?"
            );

            // Set the values where the ?s appear in the SQL statement
            pst.setString(1, parkingLot);
            pst.setInt(2, id);

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
     * Delete an Inventory relation with the given ID from the database
     * @param id Inventory ID
     * @return -1 on failure or number of rows affected on success
     */
    public int deleteInventory(int id) {
        try {
            // Create a SQL injection-safe statement
            PreparedStatement pst = sqlUtil.prepareStatement(
                "DELETE FROM Inventory WHERE iID=?"
            );
            
            // Set the ID by replacing ? in the statement with vin
            pst.setInt(1, id);

            // Execute the statement and return the result
            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InventoryHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
        
        // Return value when there was an error
        return -1;
    }

    public Inventory findInventory(int iid) {
        Inventory inv = null;
        try {
            PreparedStatement pst = sqlUtil.prepareStatement(
                    "SELECT * FROM Inventory WHERE iID=?"
            );
            pst.setInt(1, iid);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String parkingLot = rs.getString("ParkingLot");
                inv = new Inventory(iid, parkingLot);
            }
        } catch (SQLException ex) {
            Logger.getLogger(InventoryHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return inv;
    }
    
    public ArrayList<Inventory> getInventories() {
        ArrayList<Inventory> inventories = new ArrayList<>();

        try {
            // Create a SQL injection-safe statement
            PreparedStatement pst = sqlUtil.prepareStatement(
                "SELECT iID, ParkingLot FROM Inventory"
            );

            // Execute the statement and get the result set
            ResultSet rs = pst.executeQuery();

            // Iterate through the result set, and add each row as an Inventory
            // to the array
            while (rs.next()) {
                // Get the relevant attributes from the relation
                int iid = rs.getInt("iID");
                String parkingLot = rs.getString("ParkingLot");
                
                // Create an Inventory object from the attributes and add it to
                // the array
                inventories.add(new Inventory(iid, parkingLot));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(InventoryHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
        
        // Return value when there was an error
        return inventories;
    }
}
