package edu.unlv.mis768.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AddPhysicianController {

    @FXML
    private TextField usernameTxt;

    @FXML
    private TextField firstnameTxt;

    @FXML
    private TextField specialtyTxt;

    @FXML
    private Font x1;

    @FXML
    private TextField lastNameTxt;

    @FXML
    private Button saveBtn;
    
    @FXML
    private Button cancelButton;
    
    private Staff admin;
    
    public void initialize() {}
    
    public void initData(Staff staff) {
    	this.admin = staff; 
    }

    // Save Button Listener
    public void saveButtonListener() {
    	
    }
    
    // Cancel Button Listener
    public void cancelButtonListener(ActionEvent e) throws Exception {
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("UserMgmt.fxml"));
    	
    	// load the UI and call the controller method
    	Parent parent = loader.load();
    	UserManagementController controller = loader.getController();
    	controller.initData(this.admin);
    	
    	// set the scene
    	Scene scene = new Scene(parent);
    	
    	// get the current window
    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	// change the title
    	stage.setTitle("Staff Portal");
    	// set the scene for the stage
    	stage.setScene(scene);
    	// show the stage
    	stage.show();
    }
}
