package jesslambertims.View_Controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jesslambertims.Model.Inventory;
import static jesslambertims.Model.Inventory.getPartInventory;
import static jesslambertims.Model.Inventory.getProductInventory;
import jesslambertims.Model.Part;
import jesslambertims.Model.Product;
import static jesslambertims.View_Controller.MainScreenController.prodToModifyIndex;

/**
 * FXML Controller class
 *
 * @author Jessica Lambert
 */

public class ModifyProductController implements Initializable{
        
    @FXML
    private TextField prodIDField;
    
    @FXML
    private TextField prodNameField;
    
    @FXML
    private TextField prodInvField;
    
    @FXML
    private TextField prodPriceField;
    
    @FXML
    private TextField prodMaxField;
    
    @FXML
    private TextField prodMinField;
    
    @FXML
    private TextField partSearch;
    
    @FXML
    private TableView<Part> addPartTable;
    
    @FXML
    private TableColumn<Part, Integer> addPartTablePartID;
    
    @FXML
    private TableColumn<Part, String> addPartTablePartName;
    
    @FXML
    private TableColumn<Part, Integer> addPartTablePartInv;
    
    @FXML
    private TableColumn<Part, Double> addPartTablePartPrice;
    
    @FXML
    private TableView<Part> delPartTable;
    
    @FXML
    private TableColumn<Part, Integer> delPartTablePartID;
    
    @FXML
    private TableColumn<Part, String> delPartTablePartName;
    
    @FXML
    private TableColumn<Part, Integer> delPartTablePartInv;
    
    @FXML
    private TableColumn<Part, Double> delPartTablePartPrice;
    
    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    
    int productIndex = prodToModifyIndex();
    private int prodID;
    
    @FXML
    void clickSearch(ActionEvent event) {
        String searchPart = partSearch.getText();
        int partIndex = -1;
        if (Inventory.lookupPart(searchPart) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Part not found");
            alert.setContentText("The part you searched for does not exist.");
            alert.showAndWait();
        } else {
            partIndex = Inventory.lookupPart(searchPart);
            Part tempPart = getPartInventory().get(partIndex);
            ObservableList<Part> tempPartList = FXCollections.observableArrayList();
            tempPartList.add(tempPart);
            addPartTable.setItems(tempPartList);
        }
    }
    
    @FXML
    void handleAdd(ActionEvent event) {
        Part part = addPartTable.getSelectionModel().getSelectedItem();
        currentParts.add(part);
        updateDelPartTable();
    }
    
    @FXML
    void handleDelete(ActionEvent event) {
        Part part = delPartTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Part Deletion");
        alert.setHeaderText("Confirm");
        alert.setContentText("Are you sure you want to delete " + part.getPartName() + " from parts?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.out.println("Part deleted.");
            currentParts.remove(part);
        }
        else {
            System.out.println("You clicked cancel.");
        }
    }    
    
       @FXML 
    void modProductClickSave(ActionEvent event) throws IOException {
     
        try {
            
        //int partID = Integer.parseInt(partIDField.getText());
        String prodName = prodNameField.getText();
        int prodInv = Integer.parseInt(prodInvField.getText());
        double prodPrice = Double.parseDouble(prodPriceField.getText());
        int prodMax = Integer.parseInt(prodMaxField.getText());
        int prodMin = Integer.parseInt(prodMinField.getText());
        
        Double sumOfParts = 0.00;
        
        for (int i = 0; i < currentParts.size(); i++) {
            sumOfParts += currentParts.get(i).getPartPrice();
        }
        
            if (prodMin > prodMax) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Part");
                alert.setHeaderText("Error");
                alert.setContentText("Min must be less than max.");
                alert.showAndWait();
            }else if (prodInv > prodMax || prodInv < prodMin) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Part");
                alert.setHeaderText("Error");
                alert.setContentText("Inventory level must be between min and max.");
                alert.showAndWait();
                
            } else if (prodPrice < sumOfParts) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Part");
                alert.setHeaderText("Error");
                alert.setContentText("Product Price cannot be less than the sum of the price of the parts.");
                alert.showAndWait();
                
            } else {
                System.out.println("Product name: " + prodName);
            
                Product modProd = new Product(prodID, prodName, prodPrice, prodInv, prodMax, prodMin);
            
                modProd.setProdID(prodID);
                modProd.setProdName(prodName);
                modProd.setProdPrice(prodPrice);
                modProd.setProdInv(prodInv);
                modProd.setProdMax(prodMax);
                modProd.setProdMin(prodMin);
                modProd.setProdParts(currentParts);
            
            Inventory.updateProduct(productIndex, modProd);
                    
                
                Parent partSave = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
                Scene scene = new Scene(partSave);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                partSave.getStylesheets().add(getClass().getResource("/jesslambertims/styles.css").toExternalForm());
                window.setScene(scene);
                window.show();
            }
                    
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error Adding Product");
            alert.setHeaderText("Error");
            alert.setContentText("Please complete all fields.");
            alert.showAndWait();
        }
        
    }
    
    @FXML
    private void modProdCancel(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Cancel");
        alert.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert.showAndWait();
        
        if (result.get() == ButtonType.OK) {
            Parent cancelAddPart = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
            Scene scene = new Scene(cancelAddPart);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            cancelAddPart.getStylesheets().add(getClass().getResource("/jesslambertims/styles.css").toExternalForm());
            window.setScene(scene);
            window.show();
        } else {
            System.out.println("Cancel confirmed.");
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Product product = getProductInventory().get(productIndex);
        prodID = getProductInventory().get(productIndex).getProdID();
        
        prodIDField.setText("" + prodID + "");
        prodNameField.setText(product.getProdName());
        prodInvField.setText(Integer.toString(product.getProdInv()));
        prodPriceField.setText(Double.toString(product.getProdPrice()));
        prodMaxField.setText(Integer.toString(product.getProdMax()));
        prodMinField.setText(Integer.toString(product.getProdMin()));
        
        currentParts = product.getProdParts();
        
        addPartTablePartID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        addPartTablePartName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        addPartTablePartInv.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        addPartTablePartPrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        
        delPartTablePartID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        delPartTablePartName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        delPartTablePartInv.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        delPartTablePartPrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        
        updateAddPartTable();
        
        updateDelPartTable();
        
        //prodID = Inventory.getProdIDCount();
        
    }  
     
    public void updateAddPartTable() {
        addPartTable.setItems(getPartInventory());
    }
    
    public void updateDelPartTable() {
        delPartTable.setItems(currentParts);
    }
    
}
