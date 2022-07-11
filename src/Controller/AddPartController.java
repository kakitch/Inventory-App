/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controller;

import Model.InHouse;
import Model.Inventory;
import Model.Outsourced;
import Model.Part;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Kyle Kitchens.
 * 
 * AddPartController Class controls user input on the add Part menu. 
 */
public class AddPartController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    
    /** radio input used to determine if new part is InHouse or Outsourced  */
    @FXML
    private RadioButton inhouseRB;
    
    /**
     * text input fields
     */
    @FXML
    private TextField AddPartMaxTxt;
     @FXML
    private TextField AddPartMidcnTxt;
     @FXML
    private TextField AddPartMinTxt;
     @FXML
    private TextField AddPartPriceTxt;
     @FXML
    private TextField addPartInventoryTxt;
     @FXML
    private TextField addPartNametxt;
    
    /**Machine ID / Company Name switching label*/
    @FXML
    private Label MIDCN;
    /** output for error messages   */
      @FXML
    private Label exceptionTxt;
    
    
    /**
     *  When Cancel button is pressed on add part menu this loads the main menu.
     * @param event
     * @throws IOException 
     */   
    @FXML
    void BackToMainMenu(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main Menu");
        stage.show();

    }
    /** switches text field label to machine ID when InHouse radio button is selected */
    @FXML 
    void OnInHouse(ActionEvent event) {
        MIDCN.setText("Machine ID");
    }
    /** switches text field label to machine ID when InHouse radio button is selected */
    @FXML
    void OnOutsourced(ActionEvent event) {
        MIDCN.setText("Company Name");
    }
    
    /**
     * Main function of Add Part menu. this takes input from text fields, and runs validation checks on user input.
     * If input is valid data type it will proceed and call InHouse or Outsourced constructor respectively. 
     * If input is Invalid data type or if a field is left empty this will output error messages.
     * if a part is successfully added, this will also bring back up the Main Menu. 
     * @param event
     * @throws IOException 
     */
     @FXML
    void OnAddPartSavePart(ActionEvent event) throws IOException {
    
    int id;    
    String Name = null;    
    int Stock = 0;
    double price = 0.0;
    int min = 0;
    int max = 0;
    int machineid = 0;
    String companyName = null;
    
    String ex1 = "";
    String ex2 = "";
    String ex3 = "";
    String ex4 = "";
    String ex5 = "";
    String ex6 = "";
    String ex7 = "";
    String ex8 = "";
    
    boolean error = false;
    
    
        
    //convert text fields to useable data for part instances. try/catch statements with if statements used for exception handling. 
    
        Name = addPartNametxt.getText();
        companyName = AddPartMidcnTxt.getText();
        
    
    
        if(Name.isEmpty()){
            error = true;
            ex1 =  "No data in name field\n";
        }
        try{Stock = Integer.parseInt(addPartInventoryTxt.getText());}
        catch(NumberFormatException NFE){
            error = true;
            ex2 = "Inventory is not an integer\n";
        }
        try{price = Double.parseDouble(AddPartPriceTxt.getText());}
        catch(NumberFormatException NFE1){
            error = true;
            ex3 = "Price is not a double\n";
        }
        try{min = Integer.parseInt(AddPartMinTxt.getText());}
        catch(NumberFormatException NFE2){
            error = true;
            ex4 = "Min is not an integer\n";
        }
        try{max = Integer.parseInt(AddPartMaxTxt.getText());}
        catch(NumberFormatException NFE3){
            error = true;
            ex5 = "Max is not an integer\n";
        }
        if(inhouseRB.isSelected()){
            try{machineid = Integer.parseInt(AddPartMidcnTxt.getText());}
            catch(NumberFormatException NFE3){
                error = true;
                ex6 = "Machine Id is not an integer\n";
            }
        }    
        else{
            if(companyName.isEmpty()){
                error = true;
                ex6 =  "No data in company name field\n";
            }    
        }
        if(min > max){
            error = true;
            ex7 = "Min is more than Max\n";
        }
        if(Stock < min | Stock > max){
            error = true;
            ex8 = "Stock is out of range\n";
        }
   if(error == true){
       exceptionTxt.setText("error: " + ex1 + ex2 + ex3 + ex4 + ex5 + ex6 + ex7 + ex8);
       return; 
   }    
    
    /*  this pulls the ID list from the part class and determines an unused part number that is available for use. 
   this is designed to never repeat part IDs, deleted parts IDs will not be available for use
   */
    int testId = 1;
    
    while(Part.getIdList().contains(testId) == true){
        testId++;
    }
    
    id = testId;
        
    
    // calls the Inhouse or Outsourced constructor depedent on which radio button is selected. 
    if(inhouseRB.isSelected()){
        Inventory.addPart(new InHouse( id, Name, price, Stock, min, max, machineid));
    }
    else{
        Inventory.addPart(new Outsourced( id, Name, price, Stock, min, max, companyName));
    } 
    // this pulls the main menu
    stage = (Stage)((Button)event.getSource()).getScene().getWindow();
    scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
    stage.setScene(new Scene(scene));
    stage.setTitle("Main Menu");
    stage.show();
    }
    
        
    
    
    
    
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    
    
}
