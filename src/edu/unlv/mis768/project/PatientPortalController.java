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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

public class PatientPortalController {

    @FXML
    private Button manageDependentBtn;

    @FXML
    private TableColumn<?, ?> patientName;

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableColumn<?, ?> appTime;

    @FXML
    private TableColumn<?, ?> appDate;

    @FXML
    private TableColumn<?, ?> appProvider;

    @FXML
    private Button modifyAppointmentBtn;

    @FXML
    private Button myProfileBtn;

    @FXML
    private Button newAppointmentBtn;

    @FXML
    private TableView<?> patientTableView;

    @FXML
    private TableColumn<?, ?> visitReason;
    
    private Patient patient;
    
    public void initialize() {}
    
    /**
     * Determines patient from username and 
     * creates a patient object
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
			
			// Query patient table
	        String sqlSelect = "SELECT PatientID, FirstName, LastName FROM " + AppointmentDBConstants.PATIENT_TABLE_NAME
	        		+ " WHERE Username = '" + user + "'";
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        result.last();
	        
	        this.patient = new Patient(result.getInt("PatientID"),
	        				result.getString("FirstName"), 
	        				result.getString("LastName"));
	        welcomeLabel.setText("Hello " + this.patient.getFirstName() + "!");
	        
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
     * Accepts patient object and updates welcome label
     * Used for navigation through patient portal screens
     * @param patient A patient object
     */
    public void initData(Patient patient) {
    	this.patient = patient;
    	
    	// Update welcomeLabel with user's first name
    	welcomeLabel.setText("Hello " + this.patient.getFirstName() + "!"); 
    }
    
    // Event listener for My Profile Button
    public void myProfileButtonListener(ActionEvent e) throws Exception {
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("PatientProfile.fxml"));
    	
    	// load the UI and call the controller method
    	Parent parent = loader.load();
    	PatientProfileController controller = loader.getController();
    	controller.initData(this.patient);
    	
    	// set the scene
    	Scene scene = new Scene(parent);
    	
    	// get the current window
    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	// change the title
    	stage.setTitle("Patient Profile");
    	// set the scene for the stage
    	stage.setScene(scene);
    	// show the stage
    	stage.show();

    }
    
    // Event listener for Manage Dependent(s) Button
    public void manageDependentButtonListener(ActionEvent e) throws Exception{
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("PatientDependent.fxml"));
    	
    	// load the UI and call the controller method
    	Parent parent = loader.load();
    	PatientDependentController controller = loader.getController();
    	controller.initData(this.patient);
    	
    	// set the scene
    	Scene scene = new Scene(parent);
    	
    	// get the current window
    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	// change the title
    	stage.setTitle("Dependent Management");
    	// set the scene for the stage
    	stage.setScene(scene);
    	// show the stage
    	stage.show();
    }
    
    // Event listener for Modify Appointments Button
    public void modifyAppointmentButtonListener(ActionEvent e) throws Exception{
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("PatientAppointment.fxml"));
    	
    	// load the UI and call the controller method
    	Parent parent = loader.load();
    	PatientAppointmentController controller = loader.getController();
    	controller.initData(this.patient);
    	
    	// set the scene
    	Scene scene = new Scene(parent);
    	
    	// get the current window
    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	// change the title
    	stage.setTitle("Appointment Management");
    	// set the scene for the stage
    	stage.setScene(scene);
    	// show the stage
    	stage.show();
    }
    
    // Event listener for New Appointment Button
    public void newAppointmentButtonListener(ActionEvent e) throws Exception{
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("NewAppointment.fxml"));
    	
    	// load the UI and call the controller method
    	Parent parent = loader.load();
    	NewAppointmentController controller = loader.getController();
    	controller.initData(this.patient);
    	
    	// set the scene
    	Scene scene = new Scene(parent);
    	
    	// get the current window
    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	// change the title
    	stage.setTitle("Schedule Appointment");
    	// set the scene for the stage
    	stage.setScene(scene);
    	// show the stage
    	stage.show();
    }
} 

