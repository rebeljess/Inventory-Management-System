package jesslambertims.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Jessica Lambert
 */

public class Inventory {
    private static ObservableList<Product> productInventory = FXCollections.observableArrayList();
    private static ObservableList<Part> partInventory = FXCollections.observableArrayList();
    private static int partIDCount = 0;
    private static int prodIDCount = 0;
    
    public static ObservableList<Part> getPartInventory() {
        return partInventory;
    }
    
    public static void addPart(Part part) {
        partInventory.add(part);
    }
    
    public static void deletePart(Part part) {
        partInventory.remove(part);
    }
    
    public static void updatePart(int index, Part part) {
        partInventory.set(index, part);
    }
    
    public static int getPartIDCount() {
        partIDCount++;
        return partIDCount;
    }
    
    public static boolean validatePartDelete(Part part) {
        boolean isFound = false;
        for (int i = 0; i < productInventory.size(); i++) {
            if (productInventory.get(i).getProdParts().contains(part)) {
                isFound = true;
            }
        }
        return isFound;
    }
    
    public static boolean validateProductDelete(Product product) {
        boolean isFound = false;
        int prodID = product.getProdID();
        for (int i = 0; i < productInventory.size(); i++) {
            if (productInventory.get(i).getProdID() == prodID) {
                if (!productInventory.get(i).getProdParts().isEmpty()) {
                    isFound = true;
                }
            }
        }
        return isFound;
    }
    
    public static int lookupPart(String searchTerm) {
        boolean isFound = false;
        int index = 0;
        if (isInteger(searchTerm)) {
            for (int i = 0; i < partInventory.size(); i++) {
                if (Integer.parseInt(searchTerm) == partInventory.get(i).getPartID()){
                    index = i;
                    isFound = true;
                }
            }
        }
        else {
            for (int i = 0; i < partInventory.size(); i++) {
                searchTerm = searchTerm.toLowerCase();
                if (searchTerm.equals(partInventory.get(i).getPartName().toLowerCase())){
                    index = i;
                    isFound = true;
                }
            }
        }
    
        if (isFound == true) {
            return index;
        }
        else{
            System.out.println("No parts found.");
            return -1;
        }
    }

    public static ObservableList<Product> getProductInventory() {
        return productInventory;
    }

    public static void addProduct(Product product) {
        productInventory.add(product);
    }

    public static void deleteProduct(Product product) {
        productInventory.remove(product);
    }

    public static int getProdIDCount() {
        prodIDCount++;
        return prodIDCount;
    }

    public static int lookupProduct(String searchTerm) {
        boolean isFound = false;
        int index = 0;
        if (isInteger(searchTerm)) {
            for (int i = 0; i < productInventory.size(); i++) {
                if (Integer.parseInt(searchTerm) == productInventory.get(i).getProdID()) {
                    index = i;
                    isFound = true;
                }
            }
        }
        else {
            for (int i = 0; i < productInventory.size(); i++) {
                if (searchTerm.equals(productInventory.get(i).getProdName())) {
                    index = i;
                    isFound = true;
                }
            }
        }
    
        if (isFound == true) {
            return index;
        }
        else {
            System.out.println("No products found.");
            return -1;
        }
    }

    public static void updateProduct(int index, Product product) {
        productInventory.set(index, product);
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }
}