/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bo;

import java.util.Vector;

/**
 *
 * @author ausgat
 */
public class Car {
    
    private int vin;
    private String make;
    private String model;
    private int year;
    private int msrp;

    public Car(int vin, String make, String model, int year, int msrp) {
        this.vin = vin;
        this.make = make;
        this.model = model;
        this.year = year;
        this.msrp = msrp;
    }

public Vector getRow() {
        Vector vec = new Vector();
        vec.add(this.vin);
        vec.add(this.make);
        vec.add(this.model);
        vec.add(this.year);
        vec.add(this.msrp);
        return vec;
    }

    public int getVin() {
        return vin;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getMsrp() {
        return msrp;
    }

    public void setMsrp(int msrp) {
        this.msrp = msrp;
    }
    
    
}
