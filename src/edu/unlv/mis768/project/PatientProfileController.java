package edu.unlv.mis768.project;

import java.io.IOException;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Controller for the Patient Profile GUI
 */
public class PatientProfileController {


    @FXML
    private Label welcomeLabel;

    @FXML
    private TableColumn<PatientInsurance, String> memberNumberColumn;

    @FXML
    private Label dateOfBirthLabel;

    @FXML
    private TableColumn<PatientInsurance, String> insuranceNameColumn;

    @FXML
    private TableColumn<PatientInsurance, String> groupNumberColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private TableColumn<PatientInsurance, String> insuranceTypeColumn;

    @FXML
    private Button returnHomeBtn;

    @FXML
    private Font x1;

    @FXML
    private TableView<PatientInsurance> insuranceTableView;

    @FXML
    private Label nameLabel;
    
    private PatientAdult patient;
    
    /**
     * Method to call on initialization setting up columns
     * in the tableview
     */
    public void initialize() {
    	// set up the columns in the table
    	insuranceNameColumn.setCellValueFactory(new 
    			PropertyValueFactory<PatientInsurance, String>("insuranceName"));
    	groupNumberColumn.setCellValueFactory(new 
    			PropertyValueFactory<PatientInsurance, String>("groupNumber"));
    	memberNumberColumn.setCellValueFactory(new 
    			PropertyValueFactory<PatientInsurance, String>("memberNumber"));
    	insuranceTypeColumn.setCellValueFactory(new 
    			PropertyValueFactory<PatientInsurance, String>("type"));
    }
    
    /**
     * Refreshes appointments listed in the TableView
     */
    public void refreshInsuranceTableView() {
    	
    	insuranceTableView.getItems().clear();
    	
    	ArrayList<PatientInsurance> insuranceList = patient.getInsurance();
    	// Loop over ArrayList
    	for (int i = 0; i < insuranceList.size(); i++) {
    		insuranceTableView.getItems().add(insuranceList.get(i));
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
    	
    	// Update nameLabel with user's full name
    	nameLabel.setText(this.patient.getLastName() + ", " + this.patient.getFirstName());
    	
    	// Update dateOfBirthLabel with user's date of birth
    	dateOfBirthLabel.setText(this.patient.getDateOfBirth().toString());
    	
    	// Populate the tableView with patient's insurance information
    	refreshInsuranceTableView();
    }
    
    /**
     * Determines which insurance is selected in the tableview
     * 	and deletes it from patient's record 
     */
    public void deleteButtonListener() {
    	// get the insurance selected in the TableView
    	int selectedRow = insuranceTableView.getSelectionModel().getSelectedIndex();
    	
    	if (selectedRow == -1) {
    		// No rows selected
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Invalid Deletion Attempt");
    		alert.setContentText("Please select an insurance to delete");

    		alert.showAndWait();
    	}
    	else {
    		PatientInsurance insuranceToRemove = insuranceTableView.getItems().get(selectedRow);
        	
        	// remove insurance
        	patient.removeInsurance(insuranceToRemove);
        	
        	// refresh tableView
        	refreshInsuranceTableView();	
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
