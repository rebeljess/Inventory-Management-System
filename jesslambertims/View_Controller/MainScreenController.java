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
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jesslambertims.Model.Inventory;
import static jesslambertims.Model.Inventory.deletePart;
import static jesslambertims.Model.Inventory.deleteProduct;
import static jesslambertims.Model.Inventory.getPartInventory;
import static jesslambertims.Model.Inventory.getProductInventory;
import static jesslambertims.Model.Inventory.validatePartDelete;
import static jesslambertims.Model.Inventory.validateProductDelete;
import jesslambertims.Model.Part;
import jesslambertims.Model.Product;

/**
 *
 * @author Jessica Lambert
 */

public class MainScreenController implements Initializable {
    
    @FXML
    private TextField partSearch;
    
    @FXML
    private TableView<Part> partsTable;
    
    @FXML
    private TableColumn<Part, Integer> partsTablePartID;
    
    @FXML
    private TableColumn<Part, String> partsTablePartName;
    
    @FXML
    private TableColumn<Part, Integer> partsTablePartInv;
    
    @FXML
    private TableColumn<Part, Double> partsTablePartPrice;
    
    @FXML
    private TextField prodSearch;
    
    @FXML
    private TableView<Product> prodTable;
    
    @FXML
    private TableColumn<Product, Integer> prodTableProdID;
    
    @FXML
    private TableColumn<Product, String> prodTableProdName;
    
    @FXML
    private TableColumn<Product, Integer> prodTableProdInv;
    
    @FXML
    private TableColumn<Product, Double> prodTableProdPrice;
    
    
    private ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private int prodID;
        
    private static Part modifyPart;
    private static int modifyPartIndex;
    private static Product modifyProduct;
    private static int modifyProductIndex;
    
    public static int partToModifyIndex() {
        return modifyPartIndex;
    }
    
    public static int prodToModifyIndex() {
        return modifyProductIndex;
    }
    
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
            partsTable.setItems(tempPartList);
        }
    }
    
    @FXML
    void clickProdSearch(ActionEvent event) {
        String searchProduct = prodSearch.getText();
        int productIndex = -1;
        if (Inventory.lookupProduct(searchProduct) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Product not found");
            alert.setContentText("The product you searched for does not exist.");
            alert.showAndWait();
        } else {
            productIndex = Inventory.lookupProduct(searchProduct);
            Product tempProd = getProductInventory().get(productIndex);
            ObservableList<Product> tempProdList = FXCollections.observableArrayList();
            tempProdList.add(tempProd);
            prodTable.setItems(tempProdList);
        }
    }
    
    @FXML
    private void openAddPart(ActionEvent event) throws IOException {
        try {
            Parent addPartParent = FXMLLoader.load(getClass().getResource("AddPart.fxml"));
            Scene addPartScene = new Scene(addPartParent);
            Stage addPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            addPartStage.setScene(addPartScene);
            addPartStage.show();
            addPartStage.setTitle("Add Part");
            
            addPartScene.getStylesheets().add(getClass().getResource("sceneStyles.css").toExternalForm());
            
        } catch(IOException e) {
        }
    }
    
    @FXML
    private void openModifyPart(ActionEvent event) {
        try {
            modifyPart = partsTable.getSelectionModel().getSelectedItem();
            modifyPartIndex = getPartInventory().indexOf(modifyPart);
            Parent modPartParent = FXMLLoader.load(getClass().getResource("ModifyPart.fxml"));
            Scene modPartScene = new Scene(modPartParent);
            Stage modPartStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modPartStage.setTitle("Modify Part");
            modPartStage.setScene(modPartScene);
            modPartStage.show();
            modPartScene.getStylesheets().add(getClass().getResource("sceneStyles.css").toExternalForm());
            
        } catch(IOException e) {
        }
    }
    
    @FXML
    void handleDelete(ActionEvent event) {
        Part part = partsTable.getSelectionModel().getSelectedItem();
        if(validatePartDelete(part)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part Deletion Error");
            alert.setHeaderText("Part cannot be deleted.");
            alert.setContentText( part.getPartName() + " is still used by one or more products.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Part Deletion");
            alert.setHeaderText("Please Confirm Delete");
            alert.setContentText("Are you sure you want to delete " + part.getPartName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
        
            if (result.get() == ButtonType.OK) {
                deletePart(part);
                updatePartsTable();
                System.out.println(part.getPartName() + " has been deleted.");
                
        }
        else {
            System.out.println("You clicked cancel, " + part.getPartName() + " was not deleted");
        }
    }    
    }
    
    
    @FXML
    private void openAddProduct(ActionEvent event) {
        try {
            Parent addProdParent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
            Scene addProdScene = new Scene(addProdParent);
            Stage addProdStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            addProdStage.setTitle("Add Product");
            addProdStage.setScene(addProdScene);
            addProdStage.show();
            addProdScene.getStylesheets().add(getClass().getResource("sceneStyles.css").toExternalForm());
            
        } catch(IOException e) {
        }
    }
    
    @FXML
    private void openModifyProduct(ActionEvent event) {
        try {
            
            modifyProduct = prodTable.getSelectionModel().getSelectedItem();
            modifyProductIndex = getProductInventory().indexOf(modifyProduct);
            Parent modProdParent = FXMLLoader.load(getClass().getResource("ModifyProduct.fxml"));
            Scene modProdScene = new Scene(modProdParent);
            Stage modProdStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            modProdStage.setTitle("Modify Product");
            modProdStage.setScene(modProdScene);
            modProdStage.show();
            modProdScene.getStylesheets().add(getClass().getResource("sceneStyles.css").toExternalForm());
            
        } catch(IOException e) {
        }
    }
    
    @FXML
    void handleProdDelete(ActionEvent event) {
        Product prod = prodTable.getSelectionModel().getSelectedItem();
        if(validateProductDelete(prod)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Product Deletion Error");
            alert.setHeaderText("Product cannot be deleted.");
            alert.setContentText( prod.getProdName() + " still contains one or more parts.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Product Deletion");
            alert.setHeaderText("Please Confirm Delete");
            alert.setContentText("Are you sure you want to delete " + prod.getProdName() + "?");
            Optional<ButtonType> result = alert.showAndWait();
        
            if (result.get() == ButtonType.OK) {
                deleteProduct(prod);
                updateProdTable();
                System.out.println(prod.getProdName() + " has been deleted.");
                
        }
        else {
            System.out.println("You clicked cancel, " + prod.getProdName() + " was not deleted");
        }
    }    
    }
    
    public void updatePartsTable() {
        partsTable.setItems(getPartInventory());
    }
    
    public void updateProdTable() {
        prodTable.setItems(getProductInventory());
    }
    
    @FXML
    private void handleExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Please confirm");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            System.out.println("You clicked cancel.");
        }
    }


    // called by the FXML loader after the labels declared above are injected:
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        partsTablePartID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partsTablePartName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        partsTablePartInv.setCellValueFactory(cellData -> cellData.getValue().partInvProperty().asObject());
        partsTablePartPrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject());
        
        prodTableProdID.setCellValueFactory(cellData -> cellData.getValue().prodIDProperty().asObject());
        prodTableProdName.setCellValueFactory(cellData -> cellData.getValue().prodNameProperty());
        prodTableProdInv.setCellValueFactory(cellData -> cellData.getValue().prodInvProperty().asObject());
        prodTableProdPrice.setCellValueFactory(cellData -> cellData.getValue().prodPriceProperty().asObject());
        
        updatePartsTable();
        
        updateProdTable();
        
    }
    
}