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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SignUpController {

    @FXML
    private TextField passWordTxt;

    @FXML
    private TextField confirmPasswordTxt;

    @FXML
    private TextField patientIdTxt;

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField userNametxt;
    
    // Event listener for Save Button
    public void saveButtonListener(ActionEvent e) {
    	// Get user input
    	String patientIDText = patientIdTxt.getText();
    	String username = userNametxt.getText();
    	String password = passWordTxt.getText();
    	String confirmPassword = confirmPasswordTxt.getText();
    	
    	// Check for empty values
    	if (patientIDText.isEmpty() || username.isEmpty()
    			|| password.isEmpty() || confirmPassword.isEmpty()) {
    		
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Invalid Sign Up Attempt");
    		alert.setContentText("All fields are required");

    		alert.showAndWait();
    	}
    	else {
    		// User filled out all fields
    		// Check if patient exists
    		int patientID = Integer.parseInt(patientIDText);
    		
    		// Create a connection to the database.
	        Connection conn =
	               AppointmentDBUtil.getDBConnection();
	        
	        try {
	        	// Create a statement object
				Statement stmt = conn.createStatement();
				
				// Query patient table
		        String sqlSelect = "SELECT Username FROM " + AppointmentDBConstants.PATIENT_TABLE_NAME
		        		+ " WHERE " + AppointmentDBConstants.PATIENT_PK_NAME + " = " + patientID;
		        ResultSet result = stmt.executeQuery(sqlSelect);
		        
		        if (result.last()) {
		        	// Patient exists
		        	String databaseUsername = result.getString("Username");
		        	if (databaseUsername != null && databaseUsername.equalsIgnoreCase(username)) {
		        		// Patient already has an account
		        		Alert alert = new Alert(AlertType.ERROR);
			        	alert.setTitle("Error");
			        	alert.setHeaderText("Account Already Exists");
			        	alert.setContentText("Please log in to your account");

			        	alert.showAndWait();
		        	}
		        	else {
		        		// Patient does not already have an account
		        		// Check if passwords match
		        		if (password.equals(confirmPassword)) {
		        			// Passwords match: add account
		        			String sqlInsert = "INSERT INTO " + AppointmentDBConstants.USER_TABLE_NAME
		        					+ " VALUES('" + username + "','" + password + "','patient')";
		        			stmt.executeUpdate(sqlInsert);
		        			
		        			String sqlUpdate = "UPDATE " + AppointmentDBConstants.PATIENT_TABLE_NAME
		        					+ " SET username = '" + username + "' WHERE PatientID = " 
		        					+ patientID;
		        			stmt.executeUpdate(sqlUpdate);
		        			
		        			Alert alert = new Alert(AlertType.INFORMATION);
				        	alert.setTitle("Success");
				        	alert.setHeaderText("Account Successfully Created");

				        	alert.showAndWait();
		        		}
		        		else {
		        			// Passwords do not match
		        			Alert alert = new Alert(AlertType.ERROR);
				        	alert.setTitle("Error");
				        	alert.setHeaderText("Passwords do not match");
				        	alert.setContentText("Please re-enter passwords");

				        	alert.showAndWait();
		        		}
		        	}
		        }
		        else {
		        	// Patient does not exist
		        	AppointmentDBUtil.closeDBConnection(conn);
		        	// No rows returned: Username does not exist in database
		        	Alert alert = new Alert(AlertType.ERROR);
		        	alert.setTitle("Error");
		        	alert.setHeaderText("Patient ID does not exist");
		        	alert.setContentText("Contact your doctor's office");

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
    
    // Event listener for Cancel Button
    public void cancelButtonListener(ActionEvent e) throws Exception {
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("SignIn.fxml"));
    	
    	// load the UI and call the controller method
    	Parent parent = loader.load();
    	
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