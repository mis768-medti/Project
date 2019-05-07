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

public class AdminAddAppointmentController {

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
    
    private Doctor doctor;
    
    private Staff admin;
    
    private ArrayList<Patient> patientList;
    
    public void initialize() {
    	
    	patientList = new ArrayList<Patient>();
    	
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
	        sqlSelect = "SELECT patientID, FirstName, LastName, DateOfBirth "
	        		+ "FROM " + AppointmentDBConstants.PATIENT_TABLE_NAME;
	        
	        result = stmt.executeQuery(sqlSelect);
	        
	        result.first();
	        do {
	        	patientList.add(new Patient(result.getInt("patientID"),
	        			result.getString("FirstName"),
	        						result.getString("LastName"),
	        						result.getDate("DateOfBirth")
	        						));
	        } while(result.next());
	        
	    	for (int i = 0; i < patientList.size(); i++) {
	    		Patient patient = patientList.get(i);
	    			patientComboBox.getItems().add(patient);
	    	}
	        
	        
        }  catch (SQLException ex) {
				Alert alert = new Alert(AlertType.ERROR);
	        	alert.setTitle("Error");
	        	alert.setHeaderText("Application Error");
	        	alert.setContentText(ex.getMessage());
	        	
	        	alert.showAndWait();
		}
    }
    
    public void initData(Doctor doctor, Staff admin) {
    	this.doctor = doctor;
    	this.admin = admin;
    	
    	// Populate patient combo box
    	// Add patient
    	providerComboBox.getItems().add(this.doctor);    	
    }
    
    
    // Event listener for Date Picker
    public void appDateListener() {
    	// Clear timeComboBox
    	timeComboBox.getItems().clear();
    	
    	// Determine selected provider
    	Doctor doctor = providerComboBox.getValue();
    	
    	// Determine selected date
    	LocalDate appointmentDate = appDate.getValue();
    	if (appointmentDate != null) {
    		
	    	Date date = Date.from(appointmentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    	String dateString = formatter.format(date);
	    	
	    	try {
	    		// Get available times and add to time combo box
	    		ArrayList<Slot> slotList = doctor.getSlots(dateString);
	    		
	    		// Loop over slots
	    		for (int i = 0; i < slotList.size(); i++) {
	    			// Add unbooked times to combo box
	    			if (!slotList.get(i).getBookedIndicator())
	    				timeComboBox.getItems().add(slotList.get(i));
	    		}
	    		
	    	} catch (Exception e) {
	    		
	    		Alert alert = new Alert(AlertType.ERROR);
	        	alert.setTitle("Error");
	        	alert.setHeaderText("Application Error");
	        	alert.setContentText(e.getMessage());
	        	
	        	alert.showAndWait();
	    	}
    	}
    	
    }
    
    // Event listener for Request Button
    public void requestButtonListener() {
    	// Get values from input controls
    	Patient patient = patientComboBox.getValue();
    	Doctor provider = providerComboBox.getValue();
    	Slot slot = timeComboBox.getValue();
    	String visitReason = reasonComboBox.getValue();
    	String comments = cmtTxt.getText();
    	
    	// Create an appointment object
    	Appointment appointment = new Appointment(patient, provider, slot, visitReason);
    	
    	// Schedule appointment
    	appointment.scheduleAppointment();
    	
    	// Notify user
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Information");
    	alert.setHeaderText("Appointment Scheduled");
    	alert.showAndWait();
    	
    	// Clear selections
    	timeComboBox.setValue(null);
    	appDate.setValue(null);
    	providerComboBox.setValue(null);
    	reasonComboBox.setValue(null);
    	patientComboBox.setValue(null);
    	
    }
    
    // Event listener for Return Home Button
    public void returnHomeButtonListener(ActionEvent e) throws Exception {
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

}
