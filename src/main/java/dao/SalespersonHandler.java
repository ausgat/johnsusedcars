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
     * Take a plain text password and return a hashed (encrypted) password
     * 
     * @param password Plain text password
     * @return A hashed password as a string
     */
    public static String hashPassword(String password) {
        // TODO: This method currently only returns the plain text password
        // without any modifications whatsoever! Add code that hashes the
        // password and returns the password hash.
        return password;
    }
    
    /**
     * Take the given username and password and check to see if it matches any
     * Salesperson relations in the database
     * 
     * @param username  Username of the salesperson (currently uses email)
     * @param password  Plain text password of the salesperson
     * @return Returns a Salesperson object if the username and password match
     *         and null otherwise
     */
    public Salesperson login(String username, String password) {
        Salesperson sp = null;

        // The query string, made using String.format() to insert the given
        // username into the string
        String query = String.format(
            "SELECT * FROM Salesperson WHERE sUsername='%s' and sPassword = '%s' ",
                username, hashPassword(password));

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
     * Add a new Salesperson relation to the database
     * 
     * @param username  Username
     * @param password  Password
     * @param name      Full name
     * @param phone     Phone number
     * @param email     Email
     * @return Number of rows affected
     */
    public int addSalesperson(String username, String password, String name,
            String phone, String email) {
        
        String cmdTemplate = "INSERT INTO Salesperson (sUsername, sPassword, sName, sPhone, sEmail) VALUES('%s', '%s', '%s', '%s', '%s')";
        String cmd = String.format(cmdTemplate, username,
                hashPassword(password), name, phone, email);
        return sqlUtil.executeUpdate(cmd);
    }

    /**
     * Update a Salesperson relation in the database
     * 
     * @param id        SalespersonID of the relation
     * @param username  Username
     * @param password  Password
     * @param name      Full name
     * @param phone     Phone number
     * @param email     Email
     * @return Number of rows affected
     */
    public int updateSalesperson(int id, String username, String password, String name,
            String phone, String email) {
        
        String cmdTemplate = "UPDATE Salesperson SET sUsername='%s', sPassword='%s', sName='%s', sPhone='%s', sEmail='%s' WHERE SalespersonID=%d";
        String cmd = String.format(cmdTemplate, username, password, name, phone, email, id);
        return sqlUtil.executeUpdate(cmd);
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
                String username = rs.getString("sUsername");
                String name =  rs.getString("sName");
                String phone = rs.getString("sPhone");
                String email = rs.getString("sEmail");

                // Create a new Salesperson object from the relation data
                Salesperson sp = new Salesperson(id, username, name, phone, email);

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
     * Find a salesperson by their ID
     * 
     * @param id The ID of the desired salesperson
     * @return A Salesperson object if found, null otherwise
     */
    public Salesperson findSalesperson(int id) {
        Salesperson foundSp = null;
        
        String cmdTemplate =
                "SELECT SalespersonID, sUsername, sName, sPhone, sEmail FROM Salesperson WHERE SalespersonID = %d";
        String cmd = String.format(cmdTemplate, id);
        
        ResultSet rs = sqlUtil.executeQuery(cmd);
        
        try {
            if (rs.next()) {
                // Get each relevant attribute from the relation
                String username =  rs.getString("sUsername");
                String name = rs.getString("sName");
                String phone = rs.getString("sPhone");
                String email = rs.getString("sEmail");
                
                foundSp = new Salesperson(id, username, name, phone, email);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SalespersonHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return foundSp;
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
