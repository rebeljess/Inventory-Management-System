package jesslambertims.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jesslambertims.Model.InhousePart;
import jesslambertims.Model.Inventory;
import static jesslambertims.Model.Inventory.getPartInventory;
import jesslambertims.Model.OutsourcedPart;
import jesslambertims.Model.Part;
import static jesslambertims.View_Controller.MainScreenController.partToModifyIndex;

/**
 * FXML Controller class
 *
 * @author Jessica Lambert
 */

public class ModifyPartController implements Initializable {
    
    @FXML
    private TextField partIDField;
    
    @FXML
    private TextField partNameField;
    
    @FXML
    private TextField partInvField;
    
    @FXML
    private TextField partPriceField;
    
    @FXML
    private TextField partMaxField;
    
    @FXML
    private TextField partMinField;
    
    @FXML
    private Label macOrCompLabel;
    
    @FXML
    private TextField macOrCompField;
    
    @FXML 
    private RadioButton inHouse;
    
    @FXML
    private RadioButton outSource;
    
    private boolean isOutsourced;
    int partIndex = partToModifyIndex();
    private int partID;
    
    @FXML 
    void modPartInhouse(ActionEvent event) {
        isOutsourced = false;
        macOrCompLabel.setText("Machine ID");
        inHouse.setSelected(true);
    }
    
    @FXML
    void modPartOutsourced(ActionEvent event) {
        isOutsourced = true;
        macOrCompLabel.setText("Company Name");
        outSource.setSelected(false);
    }
    
    @FXML
    void modPartClickSave(ActionEvent event) throws IOException {
         try {
            
        //int partID = Integer.parseInt(partIDField.getText());
        String partName = partNameField.getText();
        int partInv = Integer.parseInt(partInvField.getText());
        double partPrice = Double.parseDouble(partPriceField.getText());
        int partMax = Integer.parseInt(partMaxField.getText());
        int partMin = Integer.parseInt(partMinField.getText());
        String machineID = macOrCompField.getText();
        String companyName = macOrCompField.getText();
            
            if (partMin > partMax) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Part");
                alert.setHeaderText("Error");
                alert.setContentText("Min must be less than max.");
                alert.showAndWait();
            } else if(partInv > partMax || partInv < partMin) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Part");
                alert.setHeaderText("Error");
                alert.setContentText("Inventory level must be between min and max.");
                alert.showAndWait();
                
            } else {
                if (isOutsourced == false) {
                    InhousePart in = new InhousePart(partID, partName, partPrice, partInv, partMin, partMax, machineID);
                    
                    in.setPartID(partID);
                    in.setPartName(partName);
                    in.setPartPrice(partPrice);
                    in.setPartInv(partInv);
                    in.setPartMin(partMin);
                    in.setPartMax(partMax);
                    in.setMachineID(machineID);
                    Inventory.updatePart(partIndex, in);
                } else {
                    OutsourcedPart out = new OutsourcedPart(partID, partName, partPrice, partInv, partMin, partMax, companyName);
                    
                    out.setPartID(partID);
                    out.setPartName(partName);
                    out.setPartPrice(partPrice);
                    out.setPartInv(partInv);
                    out.setPartMin(partMin);
                    out.setPartMax(partMax);
                    out.setCompanyName(companyName);
                    Inventory.updatePart(partIndex, out);
                }
                
                Parent partSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(partSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                partSave.getStylesheets().add(getClass().getResource("/jesslambertims/styles.css").toExternalForm());
                window.setScene(scene);
                window.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Error Adding Part");
            alert.setHeaderText("Error");
            alert.setContentText("Please complete all fields.");
            alert.showAndWait();
        }
}
    
    @FXML
    private void modPartCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            Parent cancelModPart = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(cancelModPart);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            cancelModPart.getStylesheets().add(getClass().getResource("/jesslambertims/styles.css").toExternalForm());
            window.setScene(scene);
            window.show();
        } else {
            System.out.println("Cancel confirmed.");
        }
    }
    
     /**
     * Initialize controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Part part = getPartInventory().get(partIndex);
        partID = getPartInventory().get(partIndex).getPartID();
        partIDField.setText(" " + partID + " ");
        partNameField.setText(part.getPartName());
        partInvField.setText(Integer.toString(part.getPartInv()));
        partPriceField.setText(Double.toString(part.getPartPrice()));
        partMaxField.setText(Integer.toString(part.getPartMax()));
        partMinField.setText(Integer.toString(part.getPartMin()));
        if (part instanceof InhousePart) {
            macOrCompField.setText(((InhousePart) getPartInventory().get(partIndex)).getMachineID());
            macOrCompLabel.setText("Machine ID");
            inHouse.setSelected(true);
        } else {
            macOrCompField.setText(((OutsourcedPart) getPartInventory().get(partIndex)).getCompanyName());
            macOrCompLabel.setText("Company Name");
            outSource.setSelected(true);
        }
         
    }    
    
}