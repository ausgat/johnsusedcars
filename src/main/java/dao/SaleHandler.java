package dao;

import bo.Sale;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.SQLUtil;

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
    public int addSale(LocalDate date, int price, int salespersonId, int vin) {
        String cmdTemplate = "INSERT INTO Sale (sDate, sPrice, SalespersonID, Vin) VALUES('%s', %d, %d, %d);";
        
        // Add the values to the string template with String.format (this will
        // fill the template with the given data for each of the %d's and %s's,
        // in order)
        String cmd = String.format(cmdTemplate, date.toString(), price,
                salespersonId, vin);

        // Run the SQL command and return its value
        return sqlUtil.executeUpdate(cmd);
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
            int vin) {
        String cmdTemplate = "INSERT INTO Sale (sDate, sPrice, SalespersonID, Vin) VALUES('%s', %d, %d, %d);";
        String cmd = String.format(cmdTemplate, date.toString(), price,
                salespersonId, vin);

        // Run the SQL command and return its generated key
        return sqlUtil.executeUpdateWithGenKey(cmd);
    }

    /**
     * Delete a sale from the database using the given sale ID
     * 
     * @param id The sID of the sale
     * @return Number of rows affected
     */
    public int deleteSale(int id) {
        String cmdTemplate = "DELETE FROM Sale WHERE sID = %d";
        String cmd = String.format(cmdTemplate, id);

        return sqlUtil.executeUpdate(cmd);
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
    public int updateSale(int id, LocalDate date, int price, int salespersonId, int vin) {
        String cmdTemplate = "UPDATE Sale SET sDate='%s', sPrice=%d, SalespersonID=%d, VIN=%d WHERE sID=%d;";
        String cmd = String.format(cmdTemplate, date, price, salespersonId, vin, id);
        return sqlUtil.executeUpdate(cmd);
    }
    
    /**
     * Find a sale by its sID
     * 
     * @param id The sID of the desired sale
     * @return A Sale object if found, null otherwise
     */
    public Sale findSale(int id) {
        Sale foundSale = null;
        
        String cmdTemplate =
                "SELECT sDate, sPrice, SalespersonID, VIN FROM Sale WHERE sID = %d";
        String cmd = String.format(cmdTemplate, id);
        
        ResultSet rs = sqlUtil.executeQuery(cmd);
        
        try {
            if (rs.next()) {
                // Get each relevant attribute from the relation
                LocalDate date = rs.getDate("sDate").toLocalDate();
                int price = rs.getInt("sPrice");
                int spid = rs.getInt("SalespersonID");
                int vin = rs.getInt("VIN");
                
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
        String cmd = "SELECT * FROM Sale;";
        ResultSet rs = sqlUtil.executeQuery(cmd);
            
        try {
            while (rs.next()) {
                int id = rs.getInt("sID");
                LocalDate date = rs.getDate("sDate").toLocalDate();
                int price = rs.getInt("sPrice");
                int spid = rs.getInt("SalespersonID");
                int vin = rs.getInt("VIN");

                results.add(new Sale(id, date, price, spid, vin));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SaleHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return results;
    }
}
