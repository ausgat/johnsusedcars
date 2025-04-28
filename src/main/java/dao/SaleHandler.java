package dao;

import bo.Sale;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.SQLUtil;
import java.sql.PreparedStatement;

/**
 * Handles interactions between the Java code and the database relations for
 * Sale
 *
 */
public class SaleHandler {
    private SQLUtil sqlUtil;
    
    public SaleHandler() {
        // Create a new SQLUtil to maintain a connection to the database
        sqlUtil = new SQLUtil();
    }
    
    /**
     * Create a new Sale relation in the database
     * 
     * @param date The date of the sale
     * @param price The agreed-upon price
     * @param salespersonId The ID of the salesperson who made the sale
     * @param vin   The car's VIN number
     * @return Number of rows affected
     */
    public boolean isCarAvailable(String vin) {
    try (PreparedStatement pst = sqlUtil.prepareStatement(
            "SELECT COUNT(*) AS sale_count FROM Sale WHERE Vin = ?")) {
        pst.setString(1, vin);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) {
            int saleCount = rs.getInt("sale_count");
            return saleCount == 0; // Available if no sale yet
        }
    } catch (SQLException ex) {
        Logger.getLogger(SaleHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false; // If error, assume not available
}
    public int addSale(LocalDate date, int price, int salespersonId, String vin) {
        try{
        PreparedStatement pst = sqlUtil.prepareStatement("INSERT INTO Sale" + "(sDate, sPrice, SalespersonID, Vin)" +"VALUES(?, ?, ?, ?);");
        
        // Add the values to the string template with String.format (this will
        // fill the template with the given data for each of the %d's and %s's,
        // in order)
            pst.setDate(1, java.sql.Date.valueOf(date));
            pst.setInt(2, price);
            pst.setInt(3, salespersonId);
            pst.setString(4, vin);

            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SaleHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
        return -1;
    }

    /**
     * Create a new Sale relation in the database, returning the sale ID (auto-
     * incremented in SQL)
     * 
     * @param date The date of the sale
     * @param price The agreed-upon price
     * @param salespersonId The ID of the salesperson who made the sale
     * @param vin   The car's VIN number
     * @return The ID of the new sale
     */
    public int addSaleAndGetKey(LocalDate date, int price, int salespersonId,
            String vin) {
        try{     
        PreparedStatement pst = sqlUtil.prepareStatement("INSERT INTO Sale" + "(sDate, sPrice, SalespersonID, Vin)" + "VALUES(?, ?, ?, ?)");
       
        pst.setDate(1, java.sql.Date.valueOf(date));
            pst.setInt(2, price);
            pst.setInt(3, salespersonId);
            pst.setString(4, vin);
        
         return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SaleHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
        return -1;
    }

    /**
     * Delete a sale from the database using the given sale ID
     * 
     * @param id The sID of the sale
     * @return Number of rows affected
     */
    public int deleteSale(int id) {
    try{
        PreparedStatement pst = sqlUtil.prepareStatement("DELETE FROM Sale" + " WHERE sID = ?");
           pst.setInt(1, id);

            return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SaleHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
        return -1;
    }

    /**
     * Update a sale by sID with the given values
     * 
     * @param id sID to search by
     * @param date Date of sale
     * @param salespersonId Salesperson who made sale
     * @param price Price of sale
     * @param vin VIN of car
     * @return Number of rows affected
     */
    public int updateSale(int id, LocalDate date, int price, int salespersonId, String vin) {
try{
        PreparedStatement pst = sqlUtil.prepareStatement("UPDATE Sale" + "SET sDate= ?, sPrice= ?, SalespersonID= ?, VIN= ?" +"WHERE sID= ?");
            pst.setInt(1, id);
            pst.setDate(1, java.sql.Date.valueOf(date));
            pst.setInt(2, price);
            pst.setInt(3, salespersonId);
            pst.setString(4, vin);
        
         return pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(SaleHandler.class.getName()).log(Level.SEVERE,
                null, ex);
        }
        return -1;
    }
    
    /**
     * Find a sale by its sID
     * 
     * @param id The sID of the desired sale
     * @return A Sale object if found, null otherwise
     */
    public Sale findSale(int id) {
        Sale foundSale = null;
        try{
          PreparedStatement pst = sqlUtil.prepareStatement(
                "SELECT sDate, sPrice, SalespersonID, VIN" + "FROM Sale" + "WHERE sID = ?");
       
        ResultSet rs = pst.executeQuery();
        
    
            if (rs.next()) {
                // Get each relevant attribute from the relation
                LocalDate date = rs.getDate("sDate").toLocalDate();
                int price = rs.getInt("sPrice");
                int spid = rs.getInt("SalespersonID");
                String vin = rs.getString("VIN");
                
                foundSale = new Sale(id, date, price, spid, vin);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SaleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return foundSale;
    }
    
    /**
     * Get a list of all the Sale relations in the database
     * 
     * @return List of all the Sale relations
     */
    public List<Sale> getSales() {
        List<Sale> results = new ArrayList<Sale>();
        try {
            PreparedStatement pst = sqlUtil.prepareStatement(
                "SELECT sID, sDate, sPrice, SalespersonID, Sale.Vin, Car.cID " +
                "FROM Sale " +
                "JOIN Car ON Sale.Vin = Car.Vin " +
                "JOIN Customer ON Car.cID = Customer.cID ");
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt("sID");
                LocalDate date = rs.getDate("sDate").toLocalDate();
                int price = rs.getInt("sPrice");
                int spid = rs.getInt("SalespersonID");
                String vin = rs.getString("Sale.Vin");
                int cid = rs.getInt("Car.cID");

                Sale sale = new Sale(id, date, price, spid, vin);

                // If the car has been sold, include the customer sold to
                if (cid > 0)
                    sale.setCid(cid);
                
                results.add(sale);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SaleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return results;
    }
}
