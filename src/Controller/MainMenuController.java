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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kyle Kitchens
 * Main menu controller. allows end user to access part and product add and modify menus. the add and modify methods open alternative windows 
 * to perform those tasks. delete methods will remove items from the table views. 
 */
public class MainMenuController implements Initializable {
    
    Stage stage;
    Parent scene;

       
    
    // Product table views.
    /**
     * product table view Id column. 
     */
    @FXML
    private TableColumn<Product, Integer> productIdcol;
    /**
     * product table view Inventory column
     */
    @FXML
    private TableColumn<Product, Integer> productInvCol;
    /**
     * product table view name column
     */
    @FXML
    private TableColumn<Product, String> productNameCol;
    /**
     * product table view price column
     */
    @FXML
    private TableColumn<Product, Double> productPriceCol;
    /**
     * product table view
     */
    @FXML
    private TableView<Product> productTableView;
   
    /**
     * parts table view Id column
     */
    @FXML
    private TableColumn<Part, Integer> PartIdCol;
    /**
     * parts table view Inventory column
     */
    @FXML
    private TableColumn<Part, Integer> PartInventoryCol;
    /**
     * parts table view Name column
     */
    @FXML
    private TableColumn<Part, String> PartNameCol;
    /**
     * parts table view price column
     */
    @FXML
    private TableColumn<Part, Double>  PartPriceCol;
    /**
     * parts table view
     */
    @FXML
    private TableView<Part> PartsTableView;
    
    /**
     * parts search text field
     */
    @FXML
    private TextField partSearchTXT;
    /**
     * Product search text field
     */
    @FXML
    private TextField productSearchTXT;
    
    
    @FXML
    void ExitApp(ActionEvent event) {
        System.exit(0);
    }
    /**
     * Opens the add part menu. 
     * @param event
     * @throws IOException 
     */
    @FXML
    void OpenAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Part");
        stage.show();

    }
    /**
     * Opens the modify part menu and passes selected part information into the modify part menu controller.
     * @param event
     * @throws IOException 
     */
    @FXML
    void OpenModifyPart(ActionEvent event) throws IOException {
        // gets Table view selection and declares variable SelectionIndex
        Part SelectedItem = PartsTableView.getSelectionModel().getSelectedItem();
        int selectedIndex = PartsTableView.getSelectionModel().getSelectedIndex();
        // if statement used to make sure there is a valid selection
        if(SelectedItem != null){
        // statement is used to pass selection information from main menu to modify part menu.     
        ModifyPartController.SetSelectedPart(SelectedItem,selectedIndex);
        
        //opens modify part 
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/ModifyPart.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Modify Part");
        stage.show();
        }
        // else statement used for if there is not a valid input from table view selection. 
        else{}
    }
    /**
     * Deletes a part  from the parts table view with a confirmation dialog box to confirm. 
     * @param event 
     */
    @FXML
    void OnDeletePart(ActionEvent event) {
        
        Part selectedItem;
        selectedItem = PartsTableView.getSelectionModel().getSelectedItem();
        
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you Sure you want to delete this part");
        
        Optional<ButtonType> result = alert.showAndWait();
        
        if(result.isPresent() && result.get() == ButtonType.OK){
            
            Inventory.DeletePart(selectedItem);   
        }             
    }        
    /**
     * executes a part search for the Parts table view. 
     * @param event 
     */
     @FXML
    void onPartSearch(ActionEvent event) {
        Part tp;
        String searchInput = partSearchTXT.getText();
        //System.out.println("you clicked a button");
        if(!"".equals(searchInput)){
                
            ObservableList<Part> selectedParts = Inventory.LookupPart(searchInput);
            //System.out.println("it did a name search");
        
            if(selectedParts.isEmpty() == true){
                int searchInputInt = Integer.parseInt(partSearchTXT.getText());
                tp = Inventory.LookupPart(searchInputInt);
                selectedParts.add(tp);
                }

        
            PartsTableView.setItems(selectedParts);
            //System.out.println("it repopulated the table with names");
        }
        else if("".equals(searchInput)){
        PartsTableView.setItems(Inventory.getAllParts());
        //System.out.println("there was nothing in the text field");
        }
    }
    /**
     * executes a product search by name or ID and returns a filtered table view. 
     * @param event 
     */
     @FXML
    void onProductSearch(ActionEvent event) {
        Product tp;
        String searchInput = productSearchTXT.getText();
        //System.out.println("you clicked a button");
        if(!"".equals(searchInput)){
                
            ObservableList<Product> selectedProduct = Inventory.LookupProduct(searchInput);
            //System.out.println("it did a name search");
        
            if(selectedProduct.isEmpty() == true){
                int searchInputInt = Integer.parseInt(productSearchTXT.getText());
                tp = Inventory.LookupProduct(searchInputInt);
                selectedProduct.add(tp);
                }

        
            productTableView.setItems(selectedProduct);
            //System.out.println("it repopulated the table with names");
        }
        else if("".equals(searchInput)){
        productTableView.setItems(Inventory.getAllProducts());
        //System.out.println("there was nothing in the text field");
        }

    }
    /**
     * opens the add product menu. 
     * @param event
     * @throws IOException 
     */
     @FXML
    void onAddProduct(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/addProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Add Part");
        stage.show();

    }
    /**
     * performs a delete from the table view if the user confirms in the dialog box and has no associated parts. 
     * @param event 
     */
    @FXML
    void onDeleteProduct(ActionEvent event) {
        Product selectedItem;
        selectedItem = productTableView.getSelectionModel().getSelectedItem();
        
        if(selectedItem.getAllAssociatedParts().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you Sure you want to delete this product");
        
            Optional<ButtonType> result = alert.showAndWait();
        
            if(result.isPresent() && result.get() == ButtonType.OK){
            
                Inventory.DeleteProduct(selectedItem);
            }
        }
        else{Alert error = new Alert(Alert.AlertType.ERROR,"This product has associated Parts\n Remove them to Proceed");
        
        Optional<ButtonType> result2 = error.showAndWait();
        
//        if(result.isPresent() && result.get() == ButtonType.OK){}
            
             
        }             
    }
    /**
     * opens the Modify Product form and passes information into the modify product control about the selected product. 
     * @param event
     * @throws IOException 
     */
    @FXML
    void onModifyProduct(ActionEvent event) throws IOException {
        // gets Table view selection and declares variable SelectionIndex
        Product SelectedItem = productTableView.getSelectionModel().getSelectedItem();
        int selectedIndex = productTableView.getSelectionModel().getSelectedIndex();
        // if statement used to make sure there is a valid selection
        if(SelectedItem != null){
        // statement is used to pass selection information from main menu to modify part menu.     
        modifyProductController.SetSelectedProduct(SelectedItem,selectedIndex);
        
        //opens modify part 
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/modifyProduct.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Modify Product");
        stage.show();
        }
        // else statement used for if there is not a valid input from table view selection. 
        else{}
    

    }

    



    /**
     * Initializes the controller class.
     * loads part and product information for both table views. 
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        PartsTableView.setItems(Inventory.getAllParts());
        
        PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartInventoryCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        productTableView.setItems(Inventory.getAllProducts());
        
        productIdcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("Stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    

    
    
}
