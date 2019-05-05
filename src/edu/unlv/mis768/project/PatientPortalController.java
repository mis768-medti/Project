package edu.unlv.mis768.project;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
import javafx.scene.control.cell.PropertyValueFactory;

public class PatientPortalController {

    @FXML
    private Button manageDependentBtn;

    @FXML
    private TableColumn<Appointment, String> patientNameColumn;

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableColumn<Appointment, String> appDateColumn;

    @FXML
    private TableColumn<Appointment, String> appProviderColumn;

    @FXML
    private Button modifyAppointmentBtn;

    @FXML
    private Button myProfileBtn;

    @FXML
    private Button newAppointmentBtn;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, String> visitReasonColumn;
    
    private PatientAdult patient;
    
    public void initialize() {
    	// set up the columns in the table
    	patientNameColumn.setCellValueFactory(new 
    			PropertyValueFactory<Appointment, String>("patient"));
    	appDateColumn.setCellValueFactory(new 
    			PropertyValueFactory<Appointment, String>("slot"));
    	appProviderColumn.setCellValueFactory(new 
    			PropertyValueFactory<Appointment, String>("doctor"));
    	visitReasonColumn.setCellValueFactory(new 
    			PropertyValueFactory<Appointment, String>("visitType"));
    }
    
    public void refreshAppointmentTableView() {
    	
    	appointmentTableView.getItems().clear();
    	
    	ArrayList<Appointment> appointmentList = patient.getAppointments();
    	
    	// Loop over ArrayList
    	for (int i = 0; i < appointmentList.size(); i++) {
    		appointmentTableView.getItems().add(appointmentList.get(i));
    	}
    }
    
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
	        String sqlSelect = "SELECT *  FROM " + AppointmentDBConstants.PATIENT_TABLE_NAME
	        		+ " WHERE Username = '" + user + "'";
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        result.last();
	        
	        this.patient = new PatientAdult(result.getInt("PatientID"),
	        				result.getString("FirstName"), 
	        				result.getString("LastName"),
	        				result.getDate("DateOfBirth"));
	        welcomeLabel.setText("Hello " + this.patient.getPatientFirstName() + "!");
	        
	        // Populate the tableView with patient's appointment information
	    	refreshAppointmentTableView();
	        
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
    public void initData(PatientAdult patient) {
    	this.patient = patient;
    	
    	// Update welcomeLabel with user's first name
    	welcomeLabel.setText("Hello " + this.patient.getPatientFirstName() + "!"); 
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
    
    // Event listener for Logout Button
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

