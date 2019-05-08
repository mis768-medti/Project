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
import javafx.scene.control.RadioButton;
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
    private RadioButton patientRadioButton;

    @FXML
    private RadioButton staffRadioButton;

    @FXML
    private RadioButton doctorRadioButton;

    @FXML
    private Button saveButton;

    @FXML
    private TextField userNametxt;
    
    /**
     * Attempts to make a a new account based on the 
     * user provided information 
     * @param e ActionEvent
     */
    public void saveButtonListener(ActionEvent e) {
    	// Get user input
    	String patientIDText = patientIdTxt.getText();
    	String username = userNametxt.getText();
    	String password = passWordTxt.getText();
    	String confirmPassword = confirmPasswordTxt.getText();
    	
    	boolean isPatient = patientRadioButton.isSelected();
    	boolean isDoctor = doctorRadioButton.isSelected();
    	boolean isStaff = staffRadioButton.isSelected();
    	boolean selectedUserType = (isPatient || isDoctor || isStaff);
    	
    	// Check for empty values
    	if (patientIDText.isEmpty() || username.isEmpty()
    			|| password.isEmpty() || confirmPassword.isEmpty()
    			|| !selectedUserType) {
    		
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
    		
    		// Determine user type
    		if (isPatient)
    		{
    			// User is a patient
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
			        		// Update password
			        		if (password.equals(confirmPassword)) {
			        			
			        			String sqlUpdate = "UPDATE " + AppointmentDBConstants.USER_TABLE_NAME
			        					+ " SET Password = '" + password + "'"
			        					+ " WHERE Username = '" + username + "'";
			        			stmt.executeUpdate(sqlUpdate);
			        			
			        			Alert alert = new Alert(AlertType.INFORMATION);
					        	alert.setTitle("Success");
					        	alert.setHeaderText("Password changed!");
	
					        	alert.showAndWait();
			        		}
			        		else {
			        			Alert alert = new Alert(AlertType.ERROR);
					        	alert.setTitle("Error");
					        	alert.setHeaderText("Passwords do not match");
					        	alert.setContentText("Please re-enter passwords");
		
					        	alert.showAndWait();
	
			        		}
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
    		
    		else if (isDoctor)
    		{
    			// User is a doctor
	    		// Create a connection to the database.
		        Connection conn =
		               AppointmentDBUtil.getDBConnection();
		        
		        try {
		        	// Create a statement object
					Statement stmt = conn.createStatement();
					
					// Query patient table
			        String sqlSelect = "SELECT Username FROM " + AppointmentDBConstants.PROVIDER_TABLE_NAME
			        		+ " WHERE " + AppointmentDBConstants.PROVIDER_PK_NAME + " = " + patientID;
			        ResultSet result = stmt.executeQuery(sqlSelect);
			        
			        if (result.last()) {
			        	// Doctor exists
			        	String databaseUsername = result.getString("Username");
			        	
			        	if (databaseUsername != null && databaseUsername.equalsIgnoreCase(username)) {
			        		// Doctor already has an account
			        		// Update password
			        		if (password.equals(confirmPassword)) {
			        			
			        			String sqlUpdate = "UPDATE " + AppointmentDBConstants.USER_TABLE_NAME
			        					+ " SET Password = '" + password + "'"
			        					+ " WHERE Username = '" + username + "'";
			        			stmt.executeUpdate(sqlUpdate);
			        			
			        			Alert alert = new Alert(AlertType.INFORMATION);
					        	alert.setTitle("Success");
					        	alert.setHeaderText("Password changed!");
	
					        	alert.showAndWait();
			        		}
			        		else {
			        			Alert alert = new Alert(AlertType.ERROR);
					        	alert.setTitle("Error");
					        	alert.setHeaderText("Passwords do not match");
					        	alert.setContentText("Please re-enter passwords");
		
					        	alert.showAndWait();
	
			        		}
			        	}
			        	
			        	
			        	else {
			        		// Doctor does not already have an account
			        		// Check if passwords match
			        		if (password.equals(confirmPassword)) {
			        			// Passwords match: add account
			        			String sqlInsert = "INSERT INTO " + AppointmentDBConstants.USER_TABLE_NAME
			        					+ " VALUES('" + username + "','" + password + "','provider')";
			        			stmt.executeUpdate(sqlInsert);
			        			
			        			String sqlUpdate = "UPDATE " + AppointmentDBConstants.PROVIDER_TABLE_NAME
			        					+ " SET username = '" + username + "' WHERE ProviderID = " 
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
			        	// Provider does not exist
			        	AppointmentDBUtil.closeDBConnection(conn);
			        	// No rows returned: Username does not exist in database
			        	Alert alert = new Alert(AlertType.ERROR);
			        	alert.setTitle("Error");
			        	alert.setHeaderText("Provider ID does not exist");
			        	alert.setContentText("Contact system administrator");
	
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
    		
    		else if (isStaff)
    		{
    			// User is a staff
	    		// Create a connection to the database.
		        Connection conn =
		               AppointmentDBUtil.getDBConnection();
		        
		        try {
		        	// Create a statement object
					Statement stmt = conn.createStatement();
					
					// Query patient table
			        String sqlSelect = "SELECT Username FROM " + AppointmentDBConstants.ADMIN_TABLE_NAME
			        		+ " WHERE " + AppointmentDBConstants.ADMIN_PK_NAME + " = " + patientID;
			        ResultSet result = stmt.executeQuery(sqlSelect);
			        
			        if (result.last()) {
			        	// Staff exists
			        	String databaseUsername = result.getString("Username");
			        	if (databaseUsername != null && databaseUsername.equalsIgnoreCase(username)) {
			        		// Staff already has an account
			        		// Update password
			        		if (password.equals(confirmPassword)) {
			        			
			        			String sqlUpdate = "UPDATE " + AppointmentDBConstants.USER_TABLE_NAME
			        					+ " SET Password = '" + password + "'"
			        					+ " WHERE Username = '" + username + "'";
			        			stmt.executeUpdate(sqlUpdate);
			        			
			        			Alert alert = new Alert(AlertType.INFORMATION);
					        	alert.setTitle("Success");
					        	alert.setHeaderText("Password changed!");
	
					        	alert.showAndWait();
			        		}
			        		else {
			        			Alert alert = new Alert(AlertType.ERROR);
					        	alert.setTitle("Error");
					        	alert.setHeaderText("Passwords do not match");
					        	alert.setContentText("Please re-enter passwords");
		
					        	alert.showAndWait();
	
			        		}
			        	}
			        	else {
			        		// Doctor does not already have an account
			        		// Check if passwords match
			        		if (password.equals(confirmPassword)) {
			        			// Passwords match: add account
			        			String sqlInsert = "INSERT INTO " + AppointmentDBConstants.USER_TABLE_NAME
			        					+ " VALUES('" + username + "','" + password + "','admin')";
			        			stmt.executeUpdate(sqlInsert);
			        			
			        			String sqlUpdate = "UPDATE " + AppointmentDBConstants.ADMIN_TABLE_NAME
			        					+ " SET username = '" + username + "' WHERE AdminID = " 
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
			        	// Staff does not exist
			        	AppointmentDBUtil.closeDBConnection(conn);
			        	// No rows returned: Username does not exist in database
			        	Alert alert = new Alert(AlertType.ERROR);
			        	alert.setTitle("Error");
			        	alert.setHeaderText("Admin ID does not exist");
			        	alert.setContentText("Contact a system administrator");
	
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
    
    /**
     * Redirects to the log in GUI
     * @param e ActionEvent
     * @throws Exception when SignIn.fxml file is not found
     */
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