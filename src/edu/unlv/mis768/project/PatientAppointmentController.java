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

public class PatientAppointmentController {

    @FXML
    private TableColumn<?, ?> cancel;

    @FXML
    private TableColumn<?, ?> reschedule;

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
    
    public void refreshAppointmentTableView() {
    	
    	appointmentTableView.getItems().clear();
    	
    	ArrayList<Appointment> appointmentList = patient.getAppointments();
    	
    	// Loop over ArrayList
    	for (int i = 0; i < appointmentList.size(); i++) {
    		appointmentTableView.getItems().add(appointmentList.get(i));
    	}
    }
    
    public void initData(PatientAdult patient) {
    	this.patient = patient;
    	
    	// Update welcomeLabel with user's first name
    	welcomeLabel.setText("Hello " + this.patient.getPatientFirstName() + "!"); 	
    	
    	// Populate the tableView with patient's appointment information
    	refreshAppointmentTableView();
    }
    
    public void deleteButtonListener() {
    	// get the insurance selected in the TableView
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
