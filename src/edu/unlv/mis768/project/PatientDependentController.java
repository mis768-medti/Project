package edu.unlv.mis768.project;

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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PatientDependentController {

    @FXML
    private TextField dependentLastNameTxt;

    @FXML
    private TextField dependentPrimaryPhysicianTxt;

    @FXML
    private TextField dependentFirstNametxt;

    @FXML
    private AnchorPane addButton;

    @FXML
    private TableColumn<PatientDependent, Date> dependentDateOfBirthColumn;

    @FXML
    private Button addBtn;

    @FXML
    private TableColumn<PatientDependent, String> dependentFirstNameColumn;

    @FXML
    private TableColumn<PatientDependent, String> dependentLastNameColumn;
    
    @FXML
    private Label welcomeLabel;

    @FXML
    private TextField dependentDateOfBirthTxt;

    @FXML
    private Button returnHomeBtn;

    @FXML
    private TableColumn<PatientDependent, Doctor> dependentPrimaryPhysicianColumn;

    @FXML
    private TableView<PatientDependent> dependentTableView;

    @FXML
    private Button deleteButton;
    
    private PatientAdult patient;
    
    public void initialize() {
    	// set up the columns in the table
    	dependentFirstNameColumn.setCellValueFactory(new 
    			PropertyValueFactory<PatientDependent, String>("patientFirstName"));
    	dependentLastNameColumn.setCellValueFactory(new 
    			PropertyValueFactory<PatientDependent, String>("patientLastName"));
    	dependentDateOfBirthColumn.setCellValueFactory(new 
    			PropertyValueFactory<PatientDependent, Date>("dateOfBirth"));
//    	dependentPrimaryPhysicianColumn.setCellValueFactory(new 
//    			PropertyValueFactory<Patient, Doctor>("doctor"));
    }
    
    public void initData(PatientAdult patient) {
    	this.patient = patient;
    	
    	// Update welcomeLabel with user's first name
    	welcomeLabel.setText("Hello " + this.patient.getPatientFirstName() + "!"); 	
    	
    	// Populate the tableView with patient's dependent information
    	this.refreshDependentTableView();
    }
    
    /**
     * Populates TableView with dependent information
     * for the user
     */
    private void refreshDependentTableView() {
    	// Get dependent ArrayList for user
    	ArrayList<PatientDependent> dependentList = patient.getDependents();
    	
    	// Clear any items already in TableView
    	dependentTableView.getItems().clear();
    	
    	// Loop over ArrayList and add dependent to tableview
    	for (int i = 0; i < dependentList.size(); i++) {
    		dependentTableView.getItems().add(dependentList.get(i));
    	}
    }
    
    /**
     * Reads text fields and creates a new dependent
     */
    public void addButtonListener() {
    	String firstName = dependentFirstNametxt.getText();
    	String lastName = dependentLastNameTxt.getText();
    	String dateOfBirth = dependentDateOfBirthTxt.getText();
    	//String primaryPhysician = dependentPrimaryPhysicianTxt.getText();
    	
    	// Check if required fields are filled out
    	if (firstName.isEmpty()) {
    		
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Invalid Dependent");
    		alert.setContentText("Please provide dependent's first name");

    		alert.showAndWait();
    		
    	}

    	else if (lastName.isEmpty()) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Invalid Dependent");
    		alert.setContentText("Please provide dependent's last name");

    		alert.showAndWait();
    	}
    	
    	else if (dateOfBirth.isEmpty() || dateOfBirth.length() < 10) {
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Invalid Dependent");
    		alert.setContentText("Please provide dependent's date of birth");

    		alert.showAndWait();
    	}
    	
    	else {

    		try {
    			// Instantiate a dependent object based on the specified information
        		PatientDependent newDependent = new PatientDependent();
        		newDependent.setPatientFirstName(firstName);
        		newDependent.setPatientLastName(lastName);
				newDependent.setDateOfBirth(dateOfBirth);
				
				// Check if user already has specified dependent
	    		if (patient.hasDependent(newDependent)) {
	    			Alert alert = new Alert(AlertType.WARNING);
	        		alert.setTitle("Warning");
	        		alert.setHeaderText("Invalid Dependent");
	        		alert.setContentText("Dependent already exists");	
	        		
	        		alert.showAndWait();
	    		}
	    		
	    		// Dependent does not already exist
	    		else {
	    			// Add dependent
	    			patient.addDependent(newDependent);
	    			// Refresh tableView so new dependent is displayed
	    			refreshDependentTableView();
	    		}
			
    		
    		} 
    		catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
        		alert.setTitle("Error");
        		alert.setHeaderText("Application Error");
        		alert.setContentText(e.getMessage());
        		
        		alert.showAndWait();
			}

    	}
    
    }
    
    public void deleteButtonListener() {
    	// get the dependent selected in the TableView
    	int selectedRow = dependentTableView.getSelectionModel().getSelectedIndex();
    	PatientDependent dependentToRemove = dependentTableView.getItems().get(selectedRow);
    	
    	// remove dependent
    	patient.removeDependent(dependentToRemove);
    	
    	// refresh tableView
    	refreshDependentTableView();
    	
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
