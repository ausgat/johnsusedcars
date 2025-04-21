/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import bo.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.SQLUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author jsmit
 */
public class CustomerHandler {
    
    private SQLUtil sqlUtil;
    
    public CustomerHandler() {
        // Create a new SQLUtil to maintain a connection to the database
        sqlUtil = new SQLUtil();
    }
    public int addCustomer(String name, String phone, String email){
        String cmdTemplate = "INSERT INTO Customer (cName, cPhone, cEmail) VALUES ('%s', '%s', '%s')";
        String cmd = String.format(cmdTemplate, name, phone, email);
        return sqlUtil.executeUpdateWithGenKey(cmd);
    }
    public int deleteCustomer(int id){
        String cmd = String.format("DELETE FROM Customer where cID = %d", id);
        return sqlUtil.executeUpdate(cmd);
    }
    public int updateStudent(int id, String name, String phone, String email){
        String cmdTemplate = "UPDATE Customer set cName = '%s', cPhone = '%s', cEmail = '%s' WHERE cID = %d";
        String cmd = String.format(cmdTemplate, id, name, phone, email);
        return sqlUtil.executeUpdate(cmd);
    }
    public List<Customer>getCustomers(){
        List<Customer> results = new ArrayList<>();
        String cmd = "SELECT * FROM Customer";
        ResultSet rsCustomer = sqlUtil.executeQuery(cmd);
        try {
            while(rsCustomer.next()){
                int id = rsCustomer.getInt("cID");
                String name = rsCustomer.getString("cName");
                String phone = rsCustomer.getString("cPhone");
                String email = rsCustomer.getString("cEmail");
                
                Customer sc = new Customer(id, name, phone, email);
                results.add(sc);
            }
                
        } catch (SQLException ex) {
            Logger.getLogger(CustomerHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return results;
    }
    }
    

