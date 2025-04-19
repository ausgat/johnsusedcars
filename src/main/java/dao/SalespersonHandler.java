package dao;

import bo.Salesperson;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.SQLUtil;

/**
 * Handles interactions between the Java code and the database relations for
 * Salesperson
 *
 */
public class SalespersonHandler {
    
    private SQLUtil sqlUtil;
    
    public SalespersonHandler() {
        // Create a new SQLUtil to maintain a connection to the database
        sqlUtil = new SQLUtil();
    }
    
    /**
     * Take the given username and password and check to see if it matches any
     * Salesperson relations in the database
     * 
     * @param username  Username of the salesperson (currently uses email)
     * @param password  Password of the salesperson
     * @return Returns a Salesperson object if the username and password match
     *         and null otherwise
     */
    public Salesperson login(String username, String password) {
        Salesperson sp = null;

        // The query string, made using String.format() to insert the given
        // username into the string
        String query = String.format(
            "SELECT * FROM Salesperson WHERE sUsername='%s' and sPassword = '%s' ",username, password);

        // The results from the database
        ResultSet rs = sqlUtil.executeQuery(query);

        try {
            // Get the next row while there are rows (rs.next() returns true)
            while (rs.next()) {
                // Put the relevant data from the Salesperson relation into
                // variables
                int spId = rs.getInt("SalespersonID");
                String spName = rs.getString("sName");
                String spUsername = rs.getString("sUsername");
                String spPhone = rs.getString("sPhone");
                String spEmail = rs.getString("sEmail");

                // Create a new Salesperson object from the relation attributes
                sp = new Salesperson(spId, spName, spUsername, spPhone, spEmail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalespersonHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Return the relation, if found
        return sp;
    }

    /**
     * Get a list of all the Salesperson relations in the database
     * 
     * @return List of all the Salesperson relations
     */
    public List<Salesperson> getSalespeople() {
        List<Salesperson> results = new ArrayList<Salesperson>();

        // Put the query in a string
        String cmd = "SELECT * FROM Salesperson;";

        // Run the query (you could also just put the string directly in the
        // query, but putting it in a string might make things easier to expand
        // on later)
        ResultSet rs = sqlUtil.executeQuery(cmd);
            
        try {
            // Go to the next row (will continue until there are no more rows
            // and rs.next() returns false)
            while (rs.next()) {
                // Get each relevant attribute from the relation
                int id = rs.getInt("SalespersonID");
                String name =  rs.getString("sName");
                String username = rs.getString("sUsername");
                String phone = rs.getString("sPhone");
                String email = rs.getString("sEmail");

                // Create a new Salesperson object from the relation data
                Salesperson sp = new Salesperson(id, name, username, phone, email);

                // Add the newly-created Salesperson object to the list
                results.add(sp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalespersonHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Return the list of Salesperson objects
        return results;
    }

    /**
     * Delete a salesperson from the database using the given ID
     * 
     * @param id   The ID number of the salesperson
     * @return Number of rows affected
     */
    public int deleteSalesperson(int id) {
        String cmdTemplate = "DELETE FROM Salesperson WHERE SalespersonID = %d";
        String cmd = String.format(cmdTemplate, id);

        return sqlUtil.executeUpdate(cmd);
    }
}
