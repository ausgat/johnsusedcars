package utils;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Utility methods for interacting with the SQL database
 * 
 */
public class SQLUtil {
    
    private Connection con;
    private Statement stm;
    
    public SQLUtil() {
        String url = "jdbc:mysql://localhost:3306/JohnsUsedCars";
        String username = "root";
        String password = "root";

        // Try to connect to the database with the information given above
        try {
            con = DriverManager.getConnection(url, username, password);
            stm = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(SQLUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public Connection getConnection() {
        return con;
    }
    
    public Statement getStatement() {
        return stm;
    }
    
    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(SQLUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Executes a SQL command given as a string (modifies the database).
     * 
     * @param cmd The SQL query string
     * @return An integer of how many rows were affected
     */
    public int executeUpdate(String cmd) {
        try {
            return this.stm.executeUpdate(cmd);
        } catch (SQLException ex) {
            Logger.getLogger(SQLUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }

    /**
     * Executes a SQL command given as a string (modifies the database).
     * 
     * @param cmd The SQL query string
     * @return The key generated
     */
    public int executeUpdateWithGenKey(String cmd) {
        try {
            this.stm.execute(cmd, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs = this.stm.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
        } catch (SQLException ex) {
            Logger.getLogger(SQLUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    /**
     * Executes a SQL query given as a string (gets rows from the database).
     * 
     * @param cmd The SQL query string
     * @return A ResultSet containing the relevant rows
     */
    public ResultSet executeQuery(String cmd) {
        try {
            return this.stm.executeQuery(cmd);
        } catch (SQLException ex) {
            Logger.getLogger(SQLUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
