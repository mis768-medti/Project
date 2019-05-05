package edu.unlv.mis768.project;

import java.io.IOException;
import java.util.ArrayList;

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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Stage;

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
    
    public void refreshInsuranceTableView() {
    	
    	insuranceTableView.getItems().clear();
    	
    	ArrayList<PatientInsurance> insuranceList = patient.getInsurance();
    	// Loop over ArrayList
    	for (int i = 0; i < insuranceList.size(); i++) {
    		insuranceTableView.getItems().add(insuranceList.get(i));
    	}
    }
    
    public void initData(PatientAdult patient) {
    	this.patient = patient;
    	
    	// Update welcomeLabel with user's first name
    	welcomeLabel.setText("Hello " + this.patient.getPatientFirstName() + "!"); 	
    	
    	// Update nameLabel with user's full name
    	nameLabel.setText(this.patient.getPatientLastName() + ", " + this.patient.getPatientFirstName());
    	
    	// Update dateOfBirthLabel with user's date of birth
    	dateOfBirthLabel.setText(this.patient.getDateOfBirth().toString());
    	
    	// Populate the tableView with patient's insurance information
    	refreshInsuranceTableView();
    }
    
    public void deleteButtonListener() {
    	// get the insurance selected in the TableView
    	int selectedRow = insuranceTableView.getSelectionModel().getSelectedIndex();
    	PatientInsurance insuranceToRemove = insuranceTableView.getItems().get(selectedRow);
    	
    	// remove dependent
    	patient.removeInsurance(insuranceToRemove);
    	
    	// refresh tableView
    	refreshInsuranceTableView();
    	
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
