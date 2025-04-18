package bo;

/**
 * Class representing the Customer relation in the database
 *
 */
public class Customer {
    
    private int id;
    private String name;
    private String phone;
    private String email;

    public Customer(int id, String name, String phone, String email) {
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
     * Useful function when you need to represent a Customer as a human-readable
     * string (e.g. in a list or combo box)
     * @return A human-readable string representation of a Customer
     */
    @Override
    public String toString() {
        return String.format("%s, %s, %s", name, phone, email);
    }
}
