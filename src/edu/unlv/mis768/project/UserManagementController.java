package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UserManagementController {

    @FXML
    private Label welcomeLabel;
	
    @FXML
    private Button addPhysicianBtn;

    @FXML
    private Button addStaffbtn;

    @FXML
    private TableColumn<User, String> firstNameColumn;
    
    @FXML
    private TableColumn<User, String> lastNameColumn;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private TableColumn<User, String> userTypeColumn;

    @FXML
    private Button addPatientBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private Button returnBtn;
    
    private Staff admin;
    
    public void initialize() {
    	// set up the columns in the table
    	firstNameColumn.setCellValueFactory(new 
    			PropertyValueFactory<User, String>("firstName"));
    	lastNameColumn.setCellValueFactory(new 
    			PropertyValueFactory<User, String>("lastName"));
    	userTypeColumn.setCellValueFactory(new 
    			PropertyValueFactory<User, String>("userType"));
    }
    
    /**
     * Accepts Staff object and updates welcome label
     * Used for navigation through staff portal screens
     * @param patient A staff object
     */
    public void initData(Staff staff) {
    	this.admin = staff; 
    	
    	// Update welcomeLabel with user's first name
    	welcomeLabel.setText("Hello " + this.admin.getFirstName() + "!");
    	
    	// Populate the tableView with user information
    	refreshUserTableView();
    }
    
    public void refreshUserTableView() {
    	
    	userTableView.getItems().clear();
    	
    	// Pull users and populate in TableView
    	// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
        try {
        	// Create a statement object
			Statement stmt = conn.createStatement();
			
			// Query user table
	        String sqlSelect = "SELECT Username, AccountType FROM " 
	        		+ AppointmentDBConstants.USER_TABLE_NAME;
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        if (result.next()) {
		        result.first();
		        do {
		        	String userName = result.getString("Username");
		        	String userType = result.getString("AccountType");
		        	
		        	if (userType.equals("patient")) {
		        		Statement patientStmt = conn.createStatement();
		        		sqlSelect = "SELECT *  FROM " + AppointmentDBConstants.PATIENT_TABLE_NAME
		    	        		+ " WHERE Username = '" + userName + "'";
		    	        ResultSet patientResult = patientStmt.executeQuery(sqlSelect);
		    	        
		    	        patientResult.last();
		    	        
		    	        Patient patient = new PatientAdult(patientResult.getInt("PatientID"),
		    	        		patientResult.getString("FirstName"), 
		    	        		patientResult.getString("LastName"),
		    	        		patientResult.getDate("DateOfBirth"));
		    	        
		    	        userTableView.getItems().add(patient);
		        		
		        	}
		        	else if (userType.equals("admin")) {
		        		Statement adminStmt = conn.createStatement();
		    	        sqlSelect = "SELECT * FROM " + AppointmentDBConstants.ADMIN_TABLE_NAME
		    	        		+ " WHERE Username = '" + userName + "'";
		    	        ResultSet adminResult = adminStmt.executeQuery(sqlSelect);
		    	        
		    	        adminResult.last();
		    	        Staff admin = new Staff(adminResult.getString("FirstName"), 
		    	        		adminResult.getString("LastName"), adminResult.getInt("AdminID"), 
		    	        		adminResult.getInt("AccessLevel"), adminResult.getString("Role"));
		    	        
		    	        userTableView.getItems().add(admin);
		        		
		        	}
		        	else if (userType.equals("provider")) {
		        		Statement providerStmt = conn.createStatement();
		        		sqlSelect = "SELECT * FROM " + AppointmentDBConstants.PROVIDER_TABLE_NAME
		    	        		+ " WHERE Username = '" + userName + "'";
		    	        ResultSet providerResult = providerStmt.executeQuery(sqlSelect);
		    	        
		    	        providerResult.last();
		    	        Doctor doctor = new Doctor(providerResult.getString("FirstName"), 
		    	        		providerResult.getString("LastName"), 
		    	        		providerResult.getInt("ProviderID"), 
		    	        		providerResult.getString("Specialty"));
		    	        
		    	        userTableView.getItems().add(doctor);
		        	}

		        } while(result.next());
	        }
	        
	        AppointmentDBUtil.closeDBConnection(conn);
        } catch (SQLException ex) {
        	ex.printStackTrace();
			AppointmentDBUtil.closeDBConnection(conn);
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Application Error");
        	alert.setContentText(ex.getMessage());
        	
        	alert.showAndWait();
		}
        
    }
    
    // Event listener for Add Physician Button
    public void addPhysicianButtonListener(ActionEvent e) throws Exception {
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("AddPhysician.fxml"));
    	
    	// load the UI and call the controller method
    	Parent parent = loader.load();
    	//SignInController controller = loader.getController();
    	
    	// set the scene
    	Scene scene = new Scene(parent);
    	
    	// get the current window
    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	// change the title
    	stage.setTitle("Add Physician");
    	// set the scene for the stage
    	stage.setScene(scene);
    	// show the stage
    	stage.show();
    }
    
    // Event listener for Add Patient Button
    public void addPatientButtonListener(ActionEvent e) throws Exception {
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("AddPatient.fxml"));
    	
    	// load the UI and call the controller method
    	Parent parent = loader.load();
    	//SignInController controller = loader.getController();
    	
    	// set the scene
    	Scene scene = new Scene(parent);
    	
    	// get the current window
    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	// change the title
    	stage.setTitle("Add Patient");
    	// set the scene for the stage
    	stage.setScene(scene);
    	// show the stage
    	stage.show();
    }
    
    // Event listener for Add Patient Button
    public void addStaffButtonListener(ActionEvent e) throws Exception {
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("AddStaff.fxml"));
    	
    	// load the UI and call the controller method
    	Parent parent = loader.load();
    	//SignInController controller = loader.getController();
    	
    	// set the scene
    	Scene scene = new Scene(parent);
    	
    	// get the current window
    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	// change the title
    	stage.setTitle("Add Staff");
    	// set the scene for the stage
    	stage.setScene(scene);
    	// show the stage
    	stage.show();
    }
    
    // Event listener for Return Home Button
    public void returnHomeButtonListener(ActionEvent e) throws Exception {
    	// FXML loader object to load the UI design
    	FXMLLoader loader = new FXMLLoader();
    	// specify the file location
    	loader.setLocation(getClass().getResource("StaffPrtal.fxml"));
    	
    	// load the UI and call the controller method
    	Parent parent = loader.load();
    	AdminPortalController controller = loader.getController();
    	controller.initData(this.admin);
    	
    	// set the scene
    	Scene scene = new Scene(parent);
    	
    	// get the current window
    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
    	// change the title
    	stage.setTitle("Staff Portal");
    	// set the scene for the stage
    	stage.setScene(scene);
    	// show the stage
    	stage.show();
    }

}
