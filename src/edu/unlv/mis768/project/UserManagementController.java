package edu.unlv.mis768.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class UserManagementController {

    @FXML
    private Label welcomeLabel;
	
    @FXML
    private Button addPhysicianBtn;

    @FXML
    private Button addStaffbtn;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private TableView<?> manageUserTblData;

    @FXML
    private TableColumn<?, ?> userType;

    @FXML
    private Button addPatientBtn;

    @FXML
    private TableColumn<?, ?> remove;

    @FXML
    private Button saveBtn;

    @FXML
    private Button returnBtn;
    
    private Staff admin;
    
    public void initialize() {}
    
    /**
     * Accepts Staff object and updates welcome label
     * Used for navigation through staff portal screens
     * @param patient A staff object
     */
    public void initData(Staff staff) {
    	this.admin = staff; 
    	
    	// Update welcomeLabel with user's first name
    	welcomeLabel.setText("Hello " + this.admin.getFirstName() + "!");
    }
    
    // Event listener for Return Home Button
    public void returnHomeButtonListener(ActionEvent e) throws Exception {
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("StaffPrtal.fxml"));
    	
    	// load the UI and call the controller method
    	Parent parent = loader.load();
    	AdminPortalController controller = loader.getController();
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
