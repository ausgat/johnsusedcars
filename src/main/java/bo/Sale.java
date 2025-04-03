package bo;

import java.time.LocalDateTime;

/**
 * Class representing the Sale relation in the database
 *
 */
public class Sale {
    
    private int id;
    private double price;
    private LocalDateTime date;

    public Sale(int id, double price, LocalDateTime date) {
        this.id = id;
        this.price = price;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
