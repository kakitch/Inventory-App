/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *modify product menu controller class
 * @author Kyle Kitchens
 */
public class modifyProductController implements Initializable {
    /**
     * selected instance of product to be passed into modify product controller
     */
    private static Product selectedProduct;
    /**
     * index of allProducts that selected product is pulled from. 
     */
    private static int selectedIndex;
    
    Stage stage;
    Parent scene;
    
    /**
     * observable array list to be loaded into modify product menu of associated parts of selected product.modified on save product as well. 
     */
    private static ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    
    /**
     * Part Id column of all Parts table view in modify product menu
     */
    @FXML
    private TableColumn<Part, Integer> allPartsPartIdClm;
    /**
     * Part inventory column of all Parts table view
     */    
    @FXML
    private TableColumn<Part, Integer> allPartsPartInvClm;
    /**
     * Part Name column of allParts table view
     */
    @FXML
    private TableColumn<Part, String> allPartsPartNameClm;
    /**
     * Part Price column of all Parts table view
     */
    @FXML
    private TableColumn<Part, Double> allPartsPartPriceClm;
    /**
     * allParts Table view
     */
    @FXML
    private TableView<Part> allPartsTV;

    
    /**
     * Part Id column of associatedParts Table view
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartsPartIdClm;
    /**
     * Part inventory column of associatedParts table view
     */
    @FXML
    private TableColumn<Part, Integer> associatedPartsPartInvClm;
    /**
     * Part name column of associatedParts Table view
     */
    @FXML
    private TableColumn<Part, String> associatedPartsPartNameClm;
    /**
     * Parts price column of associated Parts table view
     */
    @FXML
    private TableColumn<Part, Double> associatedPartsPartPriceClm;
    /**
     * associatedParts Table view. 
     */
    @FXML
    private TableView<Part> associatedPartsTV;

    
    //Add Product Form Buttons
    

    
    /**
     * product inventory text field FXID
     */
    @FXML
    private TextField productInvTxt;
    /**
     * product Max text field FXID
     */
    @FXML
    private TextField productMaxTxt;
    /**
     * product min text field FXID
     */
    @FXML
    private TextField productMinTxt;
    /**
     * product Name text field FXID
     */
    @FXML
    private TextField productNameTxt;
    /**
     * product price text field FXID
     */
    @FXML
    private TextField productPriceTxt;
    /**
     * product Id text field FXID
     */
    @FXML
    private TextField productIdTxt;
    /**
     * allParts table view part search text field
     */
    @FXML
    private TextField partSearch;
    /**
     * exception error output, left blank except when logical errors or exceptions are present. 
     */
    @FXML
    private Label exceptionTxt;
    
    
    /**
     * add associated Part to associatedParts observable list
     * @param event 
     */
    @FXML
    void onAddAssociatedPart(ActionEvent event) {
        //table view selector.
        Part selectedItem = allPartsTV.getSelectionModel().getSelectedItem();
        //adds selected part to the observable list
        
        
        associatedParts.add(selectedItem);
    }
    /**
     * returns to the main menu. 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main Menu");
        stage.show();
    }
    /**
     * searches the allParts table view on the modify product menu. 
     * @param event 
     */
    @FXML
    void onPartSearch(ActionEvent event) {
        Part tp;
        String searchInput = partSearch.getText();
        //System.out.println("you clicked a button");
        if(!"".equals(searchInput)){
                
            ObservableList<Part> selectedParts = Inventory.LookupPart(searchInput);
            //System.out.println("it did a name search");
        
            if(selectedParts.isEmpty() == true){
                int searchInputInt = Integer.parseInt(partSearch.getText());
                tp = Inventory.LookupPart(searchInputInt);
                selectedParts.add(tp);
                }

        
            allPartsTV.setItems(selectedParts);
            //System.out.println("it repopulated the table with names");
        }
        else if("".equals(searchInput)){
        allPartsTV.setItems(Inventory.getAllParts());
        //System.out.println("there was nothing in the text field");
        }

    }
    /**
     * Performs validation checks and logical error checks and changes the  Product attributes. returns to main menu. 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onSaveProduct(ActionEvent event) throws IOException {
        Product updatedProduct;    
        updatedProduct = selectedProduct;
        int index = selectedIndex;
        
               
        String Name;    
        int Stock = 0;
        double price = 0.0;
        int min = 0;
        int max = 0;
        
        String ex1 = "";
        String ex2 = "";
        String ex3 = "";
        String ex4 = "";
        String ex5 = "";
        String ex6 = "";
        String ex7 = "";
    
    
    boolean error = false;
    
    
        
    
    
        Name = productNameTxt.getText();
         
    
        if(Name.isEmpty()){
            error = true;
            ex1 =  "No data in name field\n";
        }
        try{Stock = Integer.parseInt(productInvTxt.getText());}
        catch(NumberFormatException NFE){
            error = true;
            ex2 = "Inventory is not an integer\n";
        }
        try{price = Double.parseDouble(productPriceTxt.getText());}
        catch(NumberFormatException NFE1){
            error = true;
            ex3 = "Price is not a double\n";
        }
        try{min = Integer.parseInt(productMinTxt.getText());}
        catch(NumberFormatException NFE2){
            error = true;
            ex4 = "Min is not an integer\n";
        }
        try{max = Integer.parseInt(productMaxTxt.getText());}
        catch(NumberFormatException NFE3){
            error = true;
            ex5 = "Max is not an integer\n";
        }
        if(min > max){
            error = true;
            ex6 = "Min is more than Max\n";
        }
        if(Stock < min | Stock > max){
            error = true;
            ex7 = "Stock is out of range\n";
        }
        if(error == true){
            System.out.println("this is saying error is true");
            exceptionTxt.setText("error: " + ex1 + ex2 + ex3 + ex4 + ex5 + ex6 + ex7);
            return; 
       }    
    
    
        
        updatedProduct.setName(Name);
        updatedProduct.setStock(Stock);
        updatedProduct.setPrice(price);
        updatedProduct.setMax(max);
        updatedProduct.setMin(min);
        
    
        Inventory.updateProduct(index, updatedProduct);
     
        
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main Menu");
        stage.show();
    }
    /**
     * removes an associated part from the associated parts table view.
     * @param event 
     */
    @FXML
    void removeAssociatedPart(ActionEvent event) {
        int selectedItem = associatedPartsTV.getSelectionModel().getSelectedIndex();
        
                
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you Sure you want to remove this part");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            
            associatedParts.remove(selectedItem);   
        }             
        
        
    }
    /**
     * method to be used in main menu controller in order to pass Product attributes to modify product form. 
     * @param product
     * @param index 
     */
    public static void SetSelectedProduct(Product product,int index){
        selectedProduct = product;
        selectedIndex = index;
        associatedParts = selectedProduct.getAllAssociatedParts();
        
    }
    
    
    /**
     * Initializes the controller class.
     * Loads the allParts table view, associatedParts table view and the corresponding text fields. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        allPartsTV.setItems(Inventory.getAllParts());
        
        allPartsPartIdClm.setCellValueFactory(new PropertyValueFactory<>("id"));
        allPartsPartNameClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        allPartsPartInvClm.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        allPartsPartPriceClm.setCellValueFactory(new PropertyValueFactory<>("price"));
        
                
        associatedPartsTV.setItems(associatedParts);
        
        associatedPartsPartIdClm.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartsPartNameClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartsPartInvClm.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        associatedPartsPartPriceClm.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        String id = String.valueOf(selectedProduct.getId());
        String name = selectedProduct.getName();
        String stock = String.valueOf(selectedProduct.getStock());
        String price = String.valueOf(selectedProduct.getPrice());
        String max = String.valueOf(selectedProduct.getMax());
        String min = String.valueOf(selectedProduct.getMin());
        
        productIdTxt.setText(id);
        productNameTxt.setText(name);
        productInvTxt.setText(stock);
        productPriceTxt.setText(price);
        productMinTxt.setText(min);
        productMaxTxt.setText(max);
    }    
    
}
