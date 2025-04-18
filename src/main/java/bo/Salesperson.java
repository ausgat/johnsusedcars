package bo;

import java.util.Vector;

/**
 * Class representing the Salesperson relation in the database
 *
 */
public class Salesperson {
    
    private int id;
    private String name;
    private String phone;
    private String email;

    public Salesperson(int id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * Get all the attributes of the relation (mainly for use with
     * DefaultTableModel)
     * 
     * @return A vector list containing the rows, in order
     * @see javax.swing.table.DefaultTableModel
     */
    public Vector getRow() {
        Vector vec = new Vector();
        vec.add(this.id);
        vec.add(this.name);
        vec.add(this.phone);
        vec.add(this.email);
        return vec;
    }
    
    @Override
    public String toString() {
        return String.format("%s <%s>", name, email);
    }
}
