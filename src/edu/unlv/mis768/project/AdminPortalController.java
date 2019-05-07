package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

/**
 * Controller for the Admin Portal GUI
 */
public class AdminPortalController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button userMgmtBtn;

    @FXML
    private Button viecalBtn;

    @FXML
    private Button LogoutBtn;


    private Staff admin;
    
    public void initialize() {}
    
    /**
     * Determines user passed on provided username
     * @param user String username
     */
    public void initData(String user) {

    	// Update welcomeLabel with user's first name
    	// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
			// Create a statement object
			stmt = conn.createStatement();
			
			// Query admin table
	        String sqlSelect = "SELECT * FROM " + AppointmentDBConstants.ADMIN_TABLE_NAME
	        		+ " WHERE Username = '" + user + "'";
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        result.last();
	        this.admin = new Staff(result.getString("FirstName"), 
    				result.getString("LastName"), result.getInt("AdminID"));
	        welcomeLabel.setText("Hello " + this.admin.getFirstName() + "!");
	        
		} 
		catch (SQLException ex) {
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Application Error");
        	alert.setContentText(ex.getMessage());
        	
        	alert.showAndWait();
		}
    	
    }
    
    /**
     * Accepts Staff object and updates welcome label
     * Used for navigation through staff portal screens
     * @param staff A staff object
     */
    public void initData(Staff staff) {
    	this.admin = staff;
    	
    	// Update welcomeLabel with user's first name
    	welcomeLabel.setText("Hello " + this.admin.getFirstName() + "!"); 
    }
    
   /**
    * Redirects the user to the View Calendar GUI
    * @param e ActionEvent
    * @throws Exception when the ViewCalendar.fxml file cannot be found
    */
    public void viewCalendarButtonListener(ActionEvent e) throws Exception {
    	
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("ViewCalendar.fxml"));
    	
    	// load the UI and call the controller method
    	Parent parent = loader.load();
    	ViewCalendarController controller = loader.getController();
    	controller.initData(this.admin);
    	
    	// set the scene
    	Scene scene = new Scene(parent);
    	
    	// get the current window
    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	// change the title
    	stage.setTitle("View Calendar");
    	// set the scene for the stage
    	stage.setScene(scene);
    	// show the stage
    	stage.show();
    }
    
    /**
     * Redirects user to the User Management screen
     * @param e ActionEvent
     * @throws Exception when the UserMgmt.fxml file cannot be found
     */
    public void userManagementButtonListener(ActionEvent e) throws Exception {
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
    	stage.setTitle("User Management");
    	// set the scene for the stage
    	stage.setScene(scene);
    	// show the stage
    	stage.show();
    }
    
    /**
     * Redirects user to the sign in page
     * @param e ActionEvent
     * @throws Exception when SignIn.fxml file cannot be found
     */
    public void logoutButtonListener(ActionEvent e) throws Exception {
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("SignIn.fxml"));
    	
    	// load the UI and call the controller method
    	Parent parent = loader.load();
    	SignInController controller = loader.getController();
    	
    	// set the scene
    	Scene scene = new Scene(parent);
    	
    	// get the current window
    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	// change the title
    	stage.setTitle("MedTime");
    	// set the scene for the stage
    	stage.setScene(scene);
    	// show the stage
    	stage.show();	
    }

}
