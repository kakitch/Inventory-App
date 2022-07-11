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
 * @author Kyle Kitchens for C482
 */
public class ModifyPartController implements Initializable {
    
    private static Part selectedPart;
    private static int selectedIndex;
    
    Stage stage;
    Parent scene;
    
    /**
     * InHouse radio button
     */
    @FXML
    private RadioButton inhouseRB;
    /**
     * Outsourced radio button
     */
    @FXML
    private RadioButton outsourcedRB;
    
    /**
     * Modify Part menu Max text field
     */
    @FXML
    private TextField ModifyPartMaxTxt;
    /**
     * Modify Part menu MachineId / company name test field (switches according to radio button)
     */
    @FXML
    private TextField ModifyPartMidcnTxt;
    /**
     * modify Part menu Min text field
     */
    @FXML
    private TextField ModifyPartMinTxt;
    /**
     * Modify Part menu price text field
     */
    @FXML
    private TextField ModifyPartPriceTxt;
    /**
     * Modify Part menu ID text field
     */
     @FXML
    private TextField ModifyPartIDtxt;
     /**
      * Modify Part menu Inventory text field
      */
    @FXML
    private TextField ModifyPartInventoryTxt;
    /**
     * Modify part menu name text field
     */
    @FXML
    private TextField ModifyPartNametxt;
    
    /**
     * modify part menu Machine ID / company name Label. according to radio button selected.
     */
    @FXML
    private Label MIDCN;
    /**
     * exception/ error text field. left blank unless it populates with errors. used to output error messages. 
     */
    @FXML
    private Label exceptionTxt;
    
    
    
    /** 
     * Takes user back to the main menu when cancel button is used
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
    /**
     * sets MIDCN text field to "Machine ID" when the InHouse radio button is selected. 
     * @param event 
     */
    @FXML 
    void OnInHouse(ActionEvent event) {
        MIDCN.setText("Machine ID");
    }
    /**
     * sets the MIDCN text field to "Company Name" when Outsourced radio button is selected. 
     * @param event 
     */
    @FXML
    void OnOutsourced(ActionEvent event) {
        MIDCN.setText("Company Name");
    }
    /**
     * called when the "Save" button is selected. user input goes through validation and logic checks and produces error if input is 
     * unacceptable or creates a new instance of InHouse. 
     * @param event
     * @throws IOException 
     */
    @FXML
    void OnModifySavedPart(ActionEvent event) throws IOException {
        Part updatedPart;    
        updatedPart = selectedPart;
        
        int id = updatedPart.getId();
        int index = selectedIndex;
        
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
    
    
        
    //convert text fields to useable data for part objects
    
        Name = ModifyPartNametxt.getText();
        companyName = ModifyPartMidcnTxt.getText();
        
    
    
        if(Name.isEmpty()){
            error = true;
            ex1 =  "No data in name field\n";
        }
        try{Stock = Integer.parseInt(ModifyPartInventoryTxt.getText());}
        catch(NumberFormatException NFE){
            error = true;
            ex2 = "Inventory is not an integer\n";
        }
        try{price = Double.parseDouble(ModifyPartPriceTxt.getText());}
        catch(NumberFormatException NFE1){
            error = true;
            ex3 = "Price is not a double\n";
        }
        try{min = Integer.parseInt(ModifyPartMinTxt.getText());}
        catch(NumberFormatException NFE2){
            error = true;
            ex4 = "Min is not an integer\n";
        }
        try{max = Integer.parseInt(ModifyPartMaxTxt.getText());}
        catch(NumberFormatException NFE3){
            error = true;
            ex5 = "Max is not an integer\n";
        }
        if(inhouseRB.isSelected()){
            try{machineid = Integer.parseInt(ModifyPartMidcnTxt.getText());}
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
    
        
        
    
        
        
        System.out.println("it init and assigned machineid and company name");
        
        if(inhouseRB.isSelected()){
            Part partIn = new InHouse( id, Name, price, Stock, min, max, machineid);
            Inventory.updatePart(index,partIn);
        }
        else{
            Part partOut = new Outsourced( id, Name, price, Stock, min, max, companyName);
            Inventory.updatePart(index,partOut);
            
        }
        
        
      
       
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.setTitle("Main Menu");
        stage.show();

    }
    
    /**
     * sets instance of part to be loaded into modify part menu, called in main menu. 
     * @param part
     * @param index 
     */
    public static void SetSelectedPart(Part part,int index){
        selectedPart = part;
        selectedIndex = index;
        
    }
    
    
    

    /**
     * Initializes the controller class.
     * loads the selected part fields onto the Modify Part menu  and sets the radio buttons accordingly
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
            
        
        if(selectedPart.getClass() == InHouse.class){
            inhouseRB.setSelected(true);
            //System.out.println("if statement working");
            ModifyPartMidcnTxt.setText(String.valueOf(((InHouse)selectedPart).getMachineid()));
            MIDCN.setText("Machine ID");
        }
        else{
            outsourcedRB.setSelected(true);
            //System.out.println("else statement working");
            ModifyPartMidcnTxt.setText(String.valueOf(((Outsourced)selectedPart).getCompanyName()));
            MIDCN.setText("Company Name");
        }       
        
                
        String id = String.valueOf(selectedPart.getId());
        String name = selectedPart.getName();
        String stock = String.valueOf(selectedPart.getStock());
        String price = String.valueOf(selectedPart.getPrice());
        String max = String.valueOf(selectedPart.getMax());
        String min = String.valueOf(selectedPart.getMin());
        
        ModifyPartIDtxt.setText(id);
        ModifyPartNametxt.setText(name);
        ModifyPartInventoryTxt.setText(stock);
        ModifyPartPriceTxt.setText(price);
        ModifyPartMinTxt.setText(min);
        ModifyPartMaxTxt.setText(max);
      
        
    }    

    
    
}
