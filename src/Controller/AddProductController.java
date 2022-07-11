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
 *
 * @author Kyle Kitchens for C482
 * 
 * AddProduct controller class directs user input for interaction with the Product class from the Add Product Menu.
 */
public class AddProductController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    private static final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    
    
    /** Id column of the all parts table view on the add Product menu*/
    @FXML
    private TableColumn<Part, Integer> APallPartsPartIdClm;
    /** Inventory column of the all parts table view on the add Product menu*/
    @FXML
    private TableColumn<Part, Integer> APallPartsPartInvClm;
    /** Name column of the all parts table view on the add Product menu*/
    @FXML
    private TableColumn<Part, String> APallPartsPartNameClm;
    /** Price column of the all parts table view on the add Product menu*/
    @FXML
    private TableColumn<Part, Double> APallPartsPartPriceClm;
    /** All parts table view on the add Product menu*/
    @FXML
    private TableView<Part> APallPartsTV;

    
    //associated Parts table view and collumns (this is the bottom)
    @FXML
    private TableColumn<Part, Integer> APassociatedPartsPartIdClm;
    @FXML
    private TableColumn<Part, Integer> APassociatedPartsPartInvClm;
    @FXML
    private TableColumn<Part, String> APassociatedPartsPartNameClm;
    @FXML
    private TableColumn<Part, Double> APassociatedPartsPartPriceClm;
    @FXML
    private TableView<Part> APassociatedPartsTV;

    
    
    
    //Add Product form Text Fields FXID
    @FXML
    private TextField addProductInvTxt;
    @FXML
    private TextField addProductMaxTxt;
    @FXML
    private TextField addProductMinTxt;
    @FXML
    private TextField addProductNameTxt;
    @FXML
    private TextField addProductPriceTxt;
    
    //search field on add Product form. 
    @FXML
    private TextField APpartSearch;
    
    @FXML
    private Label exceptionTxt;
    
    
    /** adds a product from the all parts table view to the 
     associated parts observable List
     */
    @FXML
    void onAddAssociatedPart(ActionEvent event) {
        //table view selector.
        Part selectedItem = APallPartsTV.getSelectionModel().getSelectedItem();
        //adds selected part to the observable list
        
        associatedParts.add(selectedItem);
    }
    /** 
     clears all data from the associated parts observable list and takes user back to the main menu. 
     */
    @FXML
    void onCancel(ActionEvent event) throws IOException {
        
        associatedParts.clear();
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main Menu");
        stage.show();
    }
    /**
     * searches part either by part name or part id on the all parts table view in the add product menu. returns a filtered table view. 
     * @param event 
     */
    @FXML
    void onPartSearch(ActionEvent event) {
        Part tp;
        String searchInput = APpartSearch.getText();
        //System.out.println("you clicked a button");
        if(!"".equals(searchInput)){
                
            ObservableList<Part> selectedParts = Inventory.LookupPart(searchInput);
            //System.out.println("it did a name search");
        
            if(selectedParts.isEmpty() == true){
                int searchInputInt = Integer.parseInt(APpartSearch.getText());
                tp = Inventory.LookupPart(searchInputInt);
                selectedParts.add(tp);
                }

        
            APallPartsTV.setItems(selectedParts);
            
        }
        else if("".equals(searchInput)){
        APallPartsTV.setItems(Inventory.getAllParts());
        //System.out.println("there was nothing in the text field");
        }

    }
    /**
     * onSaveProduct collects user in put an performs input validation and logical error checks and calls Product constructor.
     * if part is successfully created without error it opens the main menu. 
     * @throws IOException 
     */
    @FXML
    void onSaveProduct(ActionEvent event) throws IOException {
    int id;
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
    
    
        
    //convert text fields to useable data for part objects
    
        Name = addProductNameTxt.getText();
         
    
        if(Name.isEmpty()){
            error = true;
            ex1 =  "No data in name field\n";
        }
        try{Stock = Integer.parseInt(addProductInvTxt.getText());}
        catch(NumberFormatException NFE){
            error = true;
            ex2 = "Inventory is not an integer\n";
        }
        try{price = Double.parseDouble(addProductPriceTxt.getText());}
        catch(NumberFormatException NFE1){
            error = true;
            ex3 = "Price is not a double\n";
        }
        try{min = Integer.parseInt(addProductMinTxt.getText());}
        catch(NumberFormatException NFE2){
            error = true;
            ex4 = "Min is not an integer\n";
        }
        try{max = Integer.parseInt(addProductMaxTxt.getText());}
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
            exceptionTxt.setText("error: " + ex1 + ex2 + ex3 + ex4 + ex5 + ex6 + ex7);
            return; 
       }    
    
        
    int testId = 1;
    
    while(Product.getIdList().contains(testId) == true){
        testId++;
    }
    
    id = testId;
    
    Product product = new Product( id, Name, price, Stock, min, max);
    Inventory.addProduct(product);
    
    
    int i = 0;
    for(Part part: associatedParts){
        product.addAssociatedPart(associatedParts.get(i));
        i++;
        }
     
    
    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
    stage.setScene(new Scene(scene));
    stage.setTitle("Main Menu");
    stage.show();
    }

    @FXML
    void removeAssociatedPart(ActionEvent event) {
        int selectedItem = APassociatedPartsTV.getSelectionModel().getSelectedIndex();
        
                
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you Sure you want to remove this part");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            
            associatedParts.remove(selectedItem);   
        }             
        
    }
    /**
     * Initializes the controller class.
     * loads the allParts observable list from the inventory class and the associatedParts Observable list from the product class
     * upon opening of the add Product menu.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        APallPartsTV.setItems(Inventory.getAllParts());
        
        APallPartsPartIdClm.setCellValueFactory(new PropertyValueFactory<>("id"));
        APallPartsPartNameClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        APallPartsPartInvClm.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        APallPartsPartPriceClm.setCellValueFactory(new PropertyValueFactory<>("price"));
        
                
        APassociatedPartsTV.setItems(associatedParts);
        
        APassociatedPartsPartIdClm.setCellValueFactory(new PropertyValueFactory<>("id"));
        APassociatedPartsPartNameClm.setCellValueFactory(new PropertyValueFactory<>("name"));
        APassociatedPartsPartInvClm.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        APassociatedPartsPartPriceClm.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    
    
}
