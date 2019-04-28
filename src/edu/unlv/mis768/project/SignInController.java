package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    public void logInButtonListener() {
    	
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
		        String sqlSelect = "SELECT Password FROM " + AppointmentDBConstants.USER_TABLE_NAME
		        		+ " WHERE " + AppointmentDBConstants.USER_PK_NAME + " = '" + username + "'";
		        ResultSet result = stmt.executeQuery(sqlSelect);
		        
		        // Determine how many records returned
		        if (result.last()) {
		        	// Rows returned: Username exists in database
		        	String correctPassword = result.getString("password");
		        	
		        	// Check if user supplied password matches password in database
		        	if (password.equals(correctPassword))
		        		System.out.println("Login in successful!");
		        	else {
		        		Alert alert = new Alert(AlertType.ERROR);
			        	alert.setTitle("Error");
			        	alert.setHeaderText("Invalid password");
			        	alert.setContentText("Please retype or reset password");
			        	
			        	alert.showAndWait();
		        	}
		        	
		        }
		        	
		        else {
		        	// No rows returned: Username does not exist in database
		        	Alert alert = new Alert(AlertType.ERROR);
		        	alert.setTitle("Error");
		        	alert.setHeaderText("Username does not exist");
		        	alert.setContentText("Please retype username or create an account");

		        	alert.showAndWait();
		        }
				
				
			} catch (SQLException e) {
				Alert alert = new Alert(AlertType.ERROR);
	        	alert.setTitle("Error");
	        	alert.setHeaderText("Application Error");
	        	alert.setContentText(e.getMessage());
	        	
	        	alert.showAndWait();
			}
	        
	        
    	}
    }

}
