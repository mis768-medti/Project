package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class NewAppointmentController {

    @FXML
    private Button requestButton;

    @FXML
    private TextArea cmtTxt;

    @FXML
    private ComboBox<Slot> timeComboBox;

    @FXML
    private ComboBox<Patient> patientComboBox;

    @FXML
    private ComboBox<Doctor> providerComboBox;

    @FXML
    private Button returnHomeButton;

    @FXML
    private DatePicker appDate;

    @FXML
    private ComboBox<String> reasonComboBox;
    
    private PatientAdult patient;
    
    private ArrayList<Doctor> doctorList;
    
    public void initialize() {
    	
    	doctorList = new ArrayList<Doctor>();
    	
    	// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
        try {
        	// Create a statement object
			Statement stmt = conn.createStatement();
			
			// Populate reason for visit combo box
			// Query specialty table
	        String sqlSelect = "SELECT DISTINCT VisitReason FROM " 
	        		+ AppointmentDBConstants.SPECIALTY_TABLE_NAME;
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        result.first();
	        do {
	        	reasonComboBox.getItems().add(result.getString("VisitReason"));
	        } while(result.next());
	        
	        // Populate doctorList ArrayList 
	        sqlSelect = "SELECT FirstName, LastName, ProviderID, Specialty "
	        		+ "FROM " + AppointmentDBConstants.PROVIDER_TABLE_NAME;
	        
	        result = stmt.executeQuery(sqlSelect);
	        
	        result.first();
	        do {
	        	doctorList.add(new Doctor(result.getString("FirstName"),
	        						result.getString("LastName"),
	        						result.getInt("ProviderID"),
	        						result.getString("Specialty")));
	        } while(result.next());
	        
	        
        }  catch (SQLException ex) {
				Alert alert = new Alert(AlertType.ERROR);
	        	alert.setTitle("Error");
	        	alert.setHeaderText("Application Error");
	        	alert.setContentText(ex.getMessage());
	        	
	        	alert.showAndWait();
		}
    }
    
    public void initData(PatientAdult patient) {
    	this.patient = patient;
    	
    	// Populate patient combo box
    	// Add patient
    	patientComboBox.getItems().add(this.patient);
    	
    	// Add dependents (if necessary)
    	if (this.patient.hasDependents()) {
    		// Get dependent list
    		ArrayList<PatientDependent> dependents = this.patient.getDependents();
    		// Loop over dependents
    		for (int i = 0; i < dependents.size(); i++) {
    			// Add dependent to patient combo box
    			patientComboBox.getItems().add(dependents.get(i));
    		}
    	}
    	
    }
    
    // Event listener for Visit Reason Combobox
    public void visitReasonListener() {
    	// Determine visit reason
    	String visitReason = reasonComboBox.getValue();
    	
    	// Populate provider combobox with provider's that 
    	// can treat selected reason
    	providerComboBox.getItems().clear();
    	
    	for (int i = 0; i < doctorList.size(); i++) {
    		Doctor doctor = doctorList.get(i);
    		
    		if (doctor.canTreat(visitReason))
    			providerComboBox.getItems().add(doctor);
    	}
    }
    
    // Event listener for Date Picker
    public void appDateListener() {
    	// Determine selected provider
    	Doctor doctor = providerComboBox.getValue();
    	
    	// Determine selected date
    	String appointmentDate = appDate.getValue().toString();
    	
    	try {
    		// Get available times and add to time combo box
    		ArrayList<Slot> availableSlots = doctor.getAvailableSlots(appointmentDate);
    		timeComboBox.getItems().addAll(availableSlots);
    		
    	} catch (Exception e) {
    		
    		Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Application Error");
        	alert.setContentText(e.getMessage());
        	
        	alert.showAndWait();
    	}
    	
    }
    
    // Event listener for Return Home Button
    public void returnHomeButtonListener(ActionEvent e) throws Exception {
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("PatientPortal.fxml"));
    	
    	// load the UI and call the controller method
    	Parent parent = loader.load();
    	PatientPortalController controller = loader.getController();
    	controller.initData(this.patient);
    	
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
