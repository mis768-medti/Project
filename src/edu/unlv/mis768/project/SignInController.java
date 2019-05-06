package edu.unlv.mis768.project;

import java.io.IOException;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignInController {

    @FXML
    private TextField passWord;

    @FXML
    private Label forgotUserNamePassWord;

    @FXML
    private TextField userName;

    @FXML
    private Label signUpLbl;

    @FXML
    private Button logInBtn;
    
    public void initialize() {}
    
    // Event listener for Log In Button
    public void logInButtonListener(ActionEvent e) throws Exception {
    	
    	// Get login credentials from text fields
    	String username = userName.getText();
    	String password = passWord.getText();
    	
    	// Check if text fields are populated
    	if (username.isEmpty()) {
    		
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Invalid Log In Attempt");
    		alert.setContentText("Please provide your username");

    		alert.showAndWait();
    		
    	}
    	
    	else if (password.isEmpty()) {
    		
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Invalid Log In Attempt");
    		alert.setContentText("Please provide your password");
    		
    		alert.showAndWait();
    	}
    	
    	else {
	    	// Check if login credentials are valid
	    	// Create a connection to the database.
	        Connection conn =
	               AppointmentDBUtil.getDBConnection();
	        
	        try {
	        	// Create a statement object
				Statement stmt = conn.createStatement();
				
				// Query user table
		        String sqlSelect = "SELECT Password, AccountType FROM " + AppointmentDBConstants.USER_TABLE_NAME
		        		+ " WHERE " + AppointmentDBConstants.USER_PK_NAME + " = '" + username + "'";
		        ResultSet result = stmt.executeQuery(sqlSelect);
		        
		        // Determine how many records returned
		        if (result.last()) {
		        	// Rows returned: Username exists in database
		        	String correctPassword = result.getString("password");
		        	
		        	// Check if user supplied password matches password in database
		        	if (password.equals(correctPassword)) {
		        		// Successful log in: Determine user type and send to the appropriate next scene
		        		String userType = result.getString("AccountType");
		        		AppointmentDBUtil.closeDBConnection(conn);
		        		FXMLLoader loader = new FXMLLoader();
		        		
		        		// Specify the file location
		        		// Determine window title
		        		// Access the controller class via the loader
		        		// Call the method in the controller class
		        		String fileLocation = "";
		        		String title = "";
		        		if (userType.equals("admin")) {
		        			loader.setLocation(getClass().getResource("StaffPrtal.fxml"));
		        			
		        			// load the UI and call the controller method
		        			Parent parent = loader.load();
		        			AdminPortalController controller = loader.getController();
		        			controller.initData(username);
		        			
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
		        		
		        		else if (userType.equals("provider")) {
		        			loader.setLocation(getClass().getResource("DoctorInterface.fxml"));
		        			
		        			// load the UI
		        			Parent parent = loader.load();
		        			ProviderPortalController controller = loader.getController();
		        			controller.initData(username);
		        			
		        			// set the scene
		        			Scene scene = new Scene(parent);
		        			
		        			// get the current window
		        			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		        			// change the title
		        			stage.setTitle("Doctor Portal");
		        			// set the scene for the stage
		        			stage.setScene(scene);
		        			// show the stage
		        			stage.show();
		        		}
		        		
		        		else if (userType.equals("patient")) {
		        			loader.setLocation(getClass().getResource("PatientPortal.fxml"));

		        			// load the UI and call the controller method
		        			Parent parent = loader.load();
		        			PatientPortalController controller = loader.getController();
		        			controller.initData(username);
		        			
		        			// set the scene
		        			Scene scene = new Scene(parent);
		        			
		        			// get the current window
			        		Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			        		// change the title
			        		stage.setTitle("Patient Portal");
			        		// set the scene for the stage
			        		stage.setScene(scene);
			        		// show the stage
			        		stage.show();
		        		}
		        	}
		        	
		        	else {
		        		conn.close();
		        		Alert alert = new Alert(AlertType.ERROR);
			        	alert.setTitle("Error");
			        	alert.setHeaderText("Invalid password");
			        	alert.setContentText("Please retype or reset password");
			        	
			        	alert.showAndWait();
		        	}
		        	
		        }
		        	
		        else {
		        	conn.close();
		        	// No rows returned: Username does not exist in database
		        	Alert alert = new Alert(AlertType.ERROR);
		        	alert.setTitle("Error");
		        	alert.setHeaderText("Username does not exist");
		        	alert.setContentText("Please retype username or create an account");

		        	alert.showAndWait();
		        }
				
				
			} catch (SQLException ex) {
				AppointmentDBUtil.closeDBConnection(conn);
				Alert alert = new Alert(AlertType.ERROR);
	        	alert.setTitle("Error");
	        	alert.setHeaderText("Application Error");
	        	alert.setContentText(ex.getMessage());
	        	
	        	alert.showAndWait();
			}
    	}
    }
}
