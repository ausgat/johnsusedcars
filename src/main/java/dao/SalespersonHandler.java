package dao;

import bo.Salesperson;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.PasswordEncryptor;
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
        password= PasswordEncryptor.encryptPassword(password);
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

        try {
            // The query string, made using SQLUtil.prepareStatement to safely
            // insert the given username and password into the statement
            PreparedStatement pst = sqlUtil.prepareStatement(
                "SELECT * FROM Salesperson WHERE sUsername=?"
            );

            // Replace the ?s in the statement with the values given
            pst.setString(1, username);

            // The results from the database
            ResultSet rs = pst.executeQuery();

            // Get the next row while there are rows (rs.next() returns true)
            while (rs.next()) {
                // Put the relevant data from the Salesperson relation into
                // variables
                int spId = rs.getInt("SalespersonID");
                String spName = rs.getString("sName");
                String spUsername = rs.getString("sUsername");
                String spPhone = rs.getString("sPhone");
                String spEmail = rs.getString("sEmail");
                String passwordHash = rs.getString("sPassword");

                // Make sure the hashed password matches the one stored
                if (passwordHash.equals(hashPassword(password))) {
                    // Create a new Salesperson object from the relation attributes
                    sp = new Salesperson(spId, spName, spUsername, spPhone, spEmail);
                }
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
    try {
        PreparedStatement pst = sqlUtil.prepareStatement(
            "INSERT INTO Salesperson (sUsername, sPassword, sName, sPhone, sEmail) VALUES (?, ?, ?, ?, ?)"
        );

        pst.setString(1, username);
        pst.setString(2, hashPassword(password));
        pst.setString(3, name);
        pst.setString(4, phone);
        pst.setString(5, email);

        return pst.executeUpdate();
    } catch (SQLException ex) {
        Logger.getLogger(SalespersonHandler.class.getName()).log(Level.SEVERE, null, ex);
        return -1;
    }
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
    try {
        if (password.isEmpty()) {
            PreparedStatement pst = sqlUtil.prepareStatement(
                "UPDATE Salesperson SET sUsername=?, sName=?, sPhone=?, sEmail=? WHERE SalespersonID=?"
            );
            pst.setString(1, username);
            pst.setString(2, name);
            pst.setString(3, phone);
            pst.setString(4, email);
            pst.setInt(5, id);

            return pst.executeUpdate();
        } else {
            PreparedStatement pst = sqlUtil.prepareStatement(
                "UPDATE Salesperson SET sUsername=?, sPassword=?, sName=?, sPhone=?, sEmail=? WHERE SalespersonID=?"
            );
            pst.setString(1, username);
            pst.setString(2, hashPassword(password));
            pst.setString(3, name);
            pst.setString(4, phone);
            pst.setString(5, email);
            pst.setInt(6, id);

            return pst.executeUpdate();
        }
    } catch (SQLException ex) {
        Logger.getLogger(SalespersonHandler.class.getName()).log(Level.SEVERE, null, ex);
    }

    return -1;
}
    /**
     * Get a list of all the Salesperson relations in the database
     * 
     * @return List of all the Salesperson relations
     */
    public List<Salesperson> getSalespeople() {
    List<Salesperson> results = new ArrayList<>();

    try {
        PreparedStatement pst = sqlUtil.prepareStatement("SELECT * FROM Salesperson");
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("SalespersonID");
            String username = rs.getString("sUsername");
            String name = rs.getString("sName");
            String phone = rs.getString("sPhone");
            String email = rs.getString("sEmail");

            Salesperson sp = new Salesperson(id, username, name, phone, email);
            results.add(sp);
        }
    } catch (SQLException ex) {
        Logger.getLogger(SalespersonHandler.class.getName()).log(Level.SEVERE, null, ex);
    }

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
        try {
        PreparedStatement pst =
                sqlUtil.prepareStatement("SELECT SalespersonID, sUsername, sName, sPhone, sEmail FROM Salesperson WHERE SalespersonID = ?");
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        
        
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
        try{
        PreparedStatement pst = sqlUtil.prepareStatement("DELETE FROM Salesperson WHERE SalespersonID = ?");
        pst.setInt(1, id);

            return pst.executeUpdate();
        
    } catch (SQLException ex) {
        Logger.getLogger(SalespersonHandler.class.getName()).log(Level.SEVERE, null, ex);
    }
    return -1;
    }
}
