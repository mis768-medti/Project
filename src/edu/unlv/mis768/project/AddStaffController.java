package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Controller for the Add Staff GUI
 */
public class AddStaffController {

    @FXML
    private TextField usernameTxt;

    @FXML
    private TextField firstnameTxt;

    @FXML
    private Font x1;

    @FXML
    private TextField lastNameTxt;

    @FXML
    private Button saveBtn;
    
    private Staff admin;
    
    public void initialize() {}
    
    public void initData(Staff staff) {
    	this.admin = staff; 
    }
    
    /**
     * Save Button Listener executes when save button 
     * is selected and attempts to create a new staff 
     * record based on the information provided in text
     * fields. To create a new staff, the user provide
     * the staff's first name, last name, and username. 
     */
    public void saveButtonListener() {
    	String username = usernameTxt.getText();
    	String firstName = firstnameTxt.getText();
    	String lastName = lastNameTxt.getText();
    	
    	// Check if required fields are filled out
    	if (firstName.isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Invalid Staff");
    		alert.setContentText("Please provide staff's first name");

    		alert.showAndWait();
    	}
    	else if (lastName.isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Invalid Staff");
    		alert.setContentText("Please provide staff's last name");

    		alert.showAndWait();
    	}
    	
    	else if (username.isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Invalid Staff");
    		alert.setContentText("Please provide staff's username");

    		alert.showAndWait();
    	}
    	
    	else {
    		
    		// Create a connection to the database.
            Connection conn =
                   AppointmentDBUtil.getDBConnection();
            

    		try {
    			// Create a statement object
				Statement stmt = conn.createStatement();
	    			// Determine if username already exists
			        String sqlSelect = "SELECT * FROM " + AppointmentDBConstants.USER_TABLE_NAME
			        		+ " WHERE " + AppointmentDBConstants.USER_PK_NAME + " = '" + username + "'";
			        ResultSet result = stmt.executeQuery(sqlSelect);
			        
			        // Determine how many records returned
			        if (result.last()) {
			        	// Username already exists
			        	Alert alert = new Alert(AlertType.WARNING);
			    		alert.setTitle("Warning");
			    		alert.setHeaderText("Invalid Staff");
			    		alert.setContentText("Username already exists");
			    		
			    		alert.showAndWait();
			        }
			        else {
			        	// Username does not already exist
			        	// Insert record into user table
			        	String sqlInsert = "INSERT INTO " + AppointmentDBConstants.USER_TABLE_NAME
			        			+ " VALUES('" + username + "','password','admin')";
			        	stmt.executeUpdate(sqlInsert);
			        	
			        	// Insert record into provider table
			        	sqlInsert = "INSERT INTO " + AppointmentDBConstants.ADMIN_TABLE_NAME
			        			+ "(Username, FirstName, LastName) VALUES ('"
			        			+ username + "','"
			        			+ firstName + "','" 
			        			+ lastName + "')";
			        	stmt.executeUpdate(sqlInsert);
			        	
			        	Alert alert = new Alert(AlertType.INFORMATION);
			    		alert.setTitle("Information");
			    		alert.setHeaderText("Staff Added");
			    		
			    		alert.showAndWait();
			        }
			
    		
    		} 
    		catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error");
        		alert.setHeaderText("Application Error");
        		alert.setContentText(e.getMessage());
        		
        		alert.showAndWait();
			} finally {
				// clear out text fields
				usernameTxt.clear();
				firstnameTxt.clear();
				lastNameTxt.clear();
			}

    	}
    	
    }

    
    /**
     * Cancel Button Listener executes when cancel button 
     * is selected and redirects user to the 
	 * user management GUI
     */
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
