package edu.unlv.mis768.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class PatientAppointmentController {

    @FXML
    private TableColumn<?, ?> cancel;

    @FXML
    private TableColumn<?, ?> reschedule;

    @FXML
    private TableColumn<?, ?> patientName;

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableColumn<?, ?> appTime;

    @FXML
    private TableView<?> patientAppointmentsTableView;

    @FXML
    private TableColumn<?, ?> provider;

    @FXML
    private Button returnHomeBtn;

    @FXML
    private TableColumn<?, ?> appDate;

    @FXML
    private TableColumn<?, ?> visitReason;

    @FXML
    private TableColumn<?, ?> status;

    @FXML
    private Button saveBtn;
    
    private PatientAdult patient;
    
    public void initialize() {}
    
    public void initData(PatientAdult patient) {
    	this.patient = patient;
    	
    	// Update welcomeLabel with user's first name
    	welcomeLabel.setText("Hello " + this.patient.getPatientFirstName() + "!"); 	
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
