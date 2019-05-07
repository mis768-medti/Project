package edu.unlv.mis768.project;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * Controller for the Patient Appointment GUI
 */
public class PatientAppointmentController {

    @FXML
    private TableColumn<Appointment, String> patientNameColumn;

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableView<Appointment> appointmentTableView;

    @FXML
    private TableColumn<Appointment, String> providerColumn;

    @FXML
    private Button returnHomeBtn;

    @FXML
    private TableColumn<Appointment, String> appDateColumn;

    @FXML
    private TableColumn<Appointment, String> visitReasonColumn;

    @FXML
    private TableColumn<Appointment, String> status;

    @FXML
    private Button deleteButton;
    
    private PatientAdult patient;
    
    /**
     * Method to call on initialization setting up columns
     * in the tableview
     */
    public void initialize() {
    	// set up the columns in the table
    	patientNameColumn.setCellValueFactory(new 
    			PropertyValueFactory<Appointment, String>("patient"));
    	appDateColumn.setCellValueFactory(new 
    			PropertyValueFactory<Appointment, String>("slot"));
    	providerColumn.setCellValueFactory(new 
    			PropertyValueFactory<Appointment, String>("doctor"));
    	visitReasonColumn.setCellValueFactory(new 
    			PropertyValueFactory<Appointment, String>("visitType"));
    }
    
    /**
     * Refreshes appointments listed in the TableView
     */
    public void refreshAppointmentTableView() {
    	
    	appointmentTableView.getItems().clear();
    	
    	ArrayList<Appointment> appointmentList = patient.getAppointments();
    	
    	// Loop over ArrayList
    	for (int i = 0; i < appointmentList.size(); i++) {
    		appointmentTableView.getItems().add(appointmentList.get(i));
    	}
    }
    
    /**
     * Method for passing data between calling GUI and this GUI
     * @param patient a PatientAdult object
     */
    public void initData(PatientAdult patient) {
    	this.patient = patient;
    	
    	// Update welcomeLabel with user's first name
    	welcomeLabel.setText("Hello " + this.patient.getFirstName() + "!"); 	
    	
    	// Populate the tableView with patient's appointment information
    	// To Do: Restrict to only upcoming (future) appointments
    	//	Need to make future appointments in the database
    	//  Need to make a getFutureAppointments method in patient
    	refreshAppointmentTableView();
    }
    
    /**
     * Determines which appointment is selected in the tableview
     * 	and deletes it from patient's record 
     */
    public void deleteButtonListener() {
    	// get the appointment selected in the TableView
    	int selectedRow = appointmentTableView.getSelectionModel().getSelectedIndex();
    	
    	if (selectedRow == -1) {
    		// No rows selected
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Invalid Deletion Attempt");
    		alert.setContentText("Please select an appointment to delete");

    		alert.showAndWait();
    	}
    	else {
    		Appointment appointmentToRemove = appointmentTableView.getItems().get(selectedRow);
        	
        	// remove appointment
        	patient.removeAppointment(appointmentToRemove);
        	
        	// refresh tableView
        	refreshAppointmentTableView();	
    	}
    	
    	
    }
    
    /**
     * Redirects user to the Patient Portal GUI
     * @param e ActionEvent
     * @throws Exception when the PatientPortal.fxml file cannot be found
     */
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
