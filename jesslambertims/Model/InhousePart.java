package jesslambertims.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Jessica Lambert
 */

public class InhousePart extends Part {
    
   private final StringProperty machineID;
   
   public InhousePart(int partID, String name, Double price, int inv, int min, int max, String machineID) {
        super(partID, name, price, inv, min, max);
        this.machineID = new SimpleStringProperty(machineID);
    }
    
    public StringProperty machineIDProperty() {
        return machineID;
    }
    
    public String getMachineID() {
        return this.machineID.get();
    }
    
    public void setMachineID(String machineID) {
        this.machineID.set(machineID);
    }
    
}