/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jesslambertims;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import jesslambertims.Model.InhousePart;
import jesslambertims.Model.Inventory;
import static jesslambertims.Model.Inventory.addPart;
import static jesslambertims.Model.Inventory.addProduct;
import jesslambertims.Model.OutsourcedPart;
import jesslambertims.Model.Product;

/**
 *
 * @author Jessica Lambert
 */

public class JessLambertIMS extends Application {
    
    private int partID;
    
    public JessLambertIMS() {
        
    
    
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception {

        // just load fxml file and display it in the stage:

        FXMLLoader loader = new FXMLLoader(getClass().getResource("View_Controller/MainScreen.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setTitle("Inventory Management System");
        scene.getStylesheets().add("jesslambertims/styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    // main method to support non-JavaFX-aware environments:

    public static void main(String[] args) {
        // starts the FX toolkit, instantiates this class, 
        // and calls start(...) on the FX Application thread:
       
        
        launch(args); 
    }
}