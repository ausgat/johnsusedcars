package dao;

import bo.Salesperson;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                String spPhone = rs.getString("sPhone");
                String spEmail = rs.getString("sEmail");

                // Create a new Salesperson object from the relation attributes
                sp = new Salesperson(spId, spName, spPhone, spEmail);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalespersonHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Return the relation, if found
        return sp;
    }
}
