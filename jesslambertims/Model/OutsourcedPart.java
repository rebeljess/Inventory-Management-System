package jesslambertims.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jessica Lambert
 */
public class OutsourcedPart extends Part {
    
    private final StringProperty companyName;
    
    public OutsourcedPart(int partID, String name, Double price, int inv, int min, int max, String companyName) {
        super(partID, name, price, inv, min, max);
        this.companyName = new SimpleStringProperty(companyName);
    }
    
    public StringProperty companyNameProperty() {
        return companyName;
    }

    public String getCompanyName() {
        return this.companyName.get();
    }
    
    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }
    
}