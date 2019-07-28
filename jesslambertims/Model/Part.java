package jesslambertims.Model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jessica Lambert
 */

public abstract class Part {
    
    //Instance variables
    private final IntegerProperty partID;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty inv;
    private final IntegerProperty min;
    private final IntegerProperty max;
    
    
    //Constructor
    public Part(int partID, String name, Double price, int inv, int min, int max) {
        this.partID = new SimpleIntegerProperty(partID);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.inv = new SimpleIntegerProperty(inv);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
        
    }
    
    public IntegerProperty partIDProperty() {
        return partID;
    }
    
    public StringProperty partNameProperty() {
        return name;
    }
    
    public DoubleProperty partPriceProperty() {
        return price;
    }
    
    public IntegerProperty partInvProperty() {
        return inv;
    }
    
    public IntegerProperty partMinProperty() {
        return min;
    }
    
    public IntegerProperty partMaxProperty() {
        return max;
    }
    
    
    //Getters
    public int getPartID() {
        return this.partID.get();
    }
    
    public String getPartName() {
        return this.name.get();
    }
    
    public double getPartPrice() {
        return this.price.get();
    }
    
    public int getPartInv() {
        return this.inv.get();
    }
    
    public int getPartMin() {
        return this.min.get();
    }
    
    public int getPartMax() {
        return this.max.get();
    }
    
    
    //Setters
    
    public void setPartID(int partID) {
        this.partID.set(partID);
    }
    
    public void setPartName(String name) {
        this.name.set(name);
    }
    
    public void setPartPrice(double price) {
        this.price.set(price);
    }
    
    public void setPartInv(int inStock) {
        this.inv.set(inStock);
    }
    
    public void setPartMin(int min) {
        this.min.set(min);
    }
    
    public void setPartMax(int max) {
        this.max.set(max);
    }

}