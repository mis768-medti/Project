package edu.unlv.mis768.project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;


public class ProviderPortalController {

    @FXML
    private TextArea aptCmtTxt;


    @FXML
    private DatePicker appDate;

    @FXML
    private Button logoutButton;


    @FXML
    private Button addBtn;

    @FXML
    private Button addCmtBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private TableColumn<Appointment, Slot> timeColumn;

    @FXML
    private TableView<Appointment> doctorAptTableView;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumn;

    @FXML
    private ImageView logo;

    @FXML
    private TableColumn<Appointment, Patient> patientColumn;

    @FXML
    private Button rescheduleBtn;
    
    @FXML
    private Button exportButton;
    
    private Doctor doctor;
    
    public void initialize() {
    	// set up the columns in the table
    	patientColumn.setCellValueFactory(new 
    			PropertyValueFactory<Appointment, Patient>("patient"));
    	timeColumn.setCellValueFactory(new 
    			PropertyValueFactory<Appointment, Slot>("slot"));
    	appointmentTypeColumn.setCellValueFactory(new 
    			PropertyValueFactory<Appointment, String>("visitType"));
    }
    
    public void initData(String user) {

    	// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
			// Create a statement object
			stmt = conn.createStatement();
			
			// Query provider table
	        String sqlSelect = "SELECT * FROM " + AppointmentDBConstants.PROVIDER_TABLE_NAME
	        		+ " WHERE Username = '" + user + "'";
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        result.last();
	        this.doctor = new Doctor(result.getString("FirstName"), 
    				result.getString("LastName"), result.getInt("ProviderID"),
    				result.getString("Specialty"));
	        
		} 
		catch (SQLException ex) {
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Application Error");
        	alert.setContentText(ex.getMessage());
        	
        	alert.showAndWait();
		}
    	
    }
    public void initData(Doctor doctor) {
    	this.doctor = doctor;
    	// Populate the tableView with Doctors's appointment information
    	appDateListener();
    }
    
    // Event listener for Date Picker
    public void appDateListener() {
    	// Clear TableView
    	doctorAptTableView.getItems().clear();
    	
    	// Determine selected date
    	LocalDate appointmentDate = appDate.getValue();
    	if (appointmentDate != null) {
    		
	    	Date date = Date.from(appointmentDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	    	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	    	String dateString = formatter.format(date);
	    	
	    	try {
	    		// Get booked times
	    		ArrayList<Slot> slotList = doctor.getSlots(dateString);
	    		
	    		// Loop over slots
	    		for (int i = 0; i < slotList.size(); i++) {
	    			
	    			if (slotList.get(i).getBookedIndicator()) {
	    				// Get appointment and add to TableView
	    				doctorAptTableView.getItems().add(new Appointment(doctor, 
	    															slotList.get(i)));
	    			}
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
    
    // Event listener for Delete button
    public void deleteButtonListener() {
    	// get the Appointment selected in the TableView
    	int selectedRow = doctorAptTableView.getSelectionModel().getSelectedIndex();
    	
    	if (selectedRow == -1) {
    		// No rows selected
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Invalid Deletion Attempt");
    		alert.setContentText("Please select an appointment to delete");

    		alert.showAndWait();
    	}
    	else {
    		Appointment appointmentToRemove = doctorAptTableView.getItems().get(selectedRow);
        	
        	// remove appointment
    		appointmentToRemove.remove();
        	
        	// refresh tableView
        	appDateListener();
    	}
    	
    	
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
    
    // Event listener for Export Button
    public void exportButtonListener() {
    	PrintWriter outputFile;
		try {
			outputFile = new PrintWriter("Schedule.csv");
		
	    	for(Appointment appointment: doctorAptTableView.getItems()) {
    		    String outputString ="";
    			outputString += appointment.getPatient().getFirstName() + " " + appointment.getPatient().getLastName() + ",";
    			outputString += appointment.getSlot() +",";
    			outputString += appointment.getVisitType()+",";
    			outputString += appointment.getComments();
    			outputFile.println(outputString);
    		}
	    	outputFile.close();
	    	
	    	Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Information");
			alert.setHeaderText("Save Successful");

			alert.showAndWait();
	    	
	    	

		} catch (FileNotFoundException e) {
	
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Application Error");
        	alert.setContentText(e.getMessage());
        	
        	alert.showAndWait();
		}

    }
    
    // Event Listener for Add Button
    public void addBtnListener(ActionEvent e) {
    	try {
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("DoctorAddAppointment.fxml"));
    	
    	// load the UI and call the controller method
		Parent parent = loader.load();
		
    	DoctorAddAppointmentController controller = loader.getController();
    	controller.initData(this.doctor);
    	
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
    	
		} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Application Error");
        	alert.setContentText(ex.getMessage());
        	
        	alert.showAndWait();
		}

    }
    
    public void commentTxtListener(){
    	int selectedRow = doctorAptTableView.getSelectionModel().getSelectedIndex();
    	Appointment commentToDisplay = doctorAptTableView.getItems().get(selectedRow);
    	aptCmtTxt.setText(commentToDisplay.getComments());
    }
    
    public void addCommentBtnListener() {
    	// get the Appointment selected in the TableView
    	int selectedRow = doctorAptTableView.getSelectionModel().getSelectedIndex();
    	String commentToAdd = aptCmtTxt.getText();
    	if (selectedRow == -1) {
    		// No rows selected
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Invalid Addition of Comment Attempt");
    		alert.setContentText("Please select an appointment to add comment to");

    		alert.showAndWait();
    	}
    	else {
    		Appointment appointmentToAddComment = doctorAptTableView.getItems().get(selectedRow);
        	
        	// add comment
    		appointmentToAddComment.setComments(commentToAdd);
        	appointmentToAddComment.updateCommentInDB();
        	//show dialog
        	Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Confirmation");
    		alert.setHeaderText("Comment Added to DB");
    		alert.setContentText("Comment successfully  Added");

    		alert.showAndWait();
        	// refresh tableView
        	appDateListener();
        	
    	}
    }

}
