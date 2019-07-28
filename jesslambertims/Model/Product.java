package jesslambertims.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jessica Lambert
 */

public class Product {
    
    private static ObservableList<Part> parts = FXCollections.observableArrayList();
    private final IntegerProperty prodID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inv;
    private final IntegerProperty min;
    private final IntegerProperty max;
    
    //Constructor
    
    public Product(int prodID, String name, Double price, int inv, int min, int max) {
        this.prodID = new SimpleIntegerProperty(prodID);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.inv = new SimpleIntegerProperty(inv);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
    }
    
    //Getters
    
    public IntegerProperty prodIDProperty() {
        return prodID;
    }
    
    public StringProperty prodNameProperty() {
        return name;
    }
    
    public DoubleProperty prodPriceProperty() {
        return price;
    }
    
    public IntegerProperty prodInvProperty() {
        return inv;
    }
    
    public IntegerProperty prodMinProperty() {
        return min;
    }
    
    public IntegerProperty prodMaxProperty() {
        return max;
    }
    
    public int getProdID() {
        return this.prodID.get();
    }
    
    public String getProdName() {
        return this.name.get();
    }
    
    public double getProdPrice() {
        return this.price.get();
    }
    
    public int getProdInv() {
        return this.inv.get();
    }
    
    public int getProdMin() {
        return this.min.get();
    }
    
    public int getProdMax() {
        return this.max.get();
    }
    
    public ObservableList getProdParts() {
        return parts;
    }
    
    //Setters
    public void setProdID(int prodID) {
        this.prodID.set(prodID);
    }
    
    public void setProdName(String name) {
        this.name.set(name);
    }
    
    public void setProdPrice(double price) {
        this.price.set(price);
    }
    
    public void setProdInv(int inv) {
        this.inv.set(inv);
    }
    
    public void setProdMin(int min) {
        this.min.set(min);
    }
    
    public void setProdMax(int max) {
        this.max.set(max);
    }
    
    public void setProdParts(ObservableList<Part> parts) {
        this.parts = parts;
    }
}