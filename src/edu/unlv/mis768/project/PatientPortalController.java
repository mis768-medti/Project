package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;

public class PatientPortalController {

    @FXML
    private Button manageDependentBtn;

    @FXML
    private TableColumn<?, ?> patientName;

    @FXML
    private Label welcomeLabel;

    @FXML
    private TableColumn<?, ?> appTime;

    @FXML
    private TableColumn<?, ?> appDate;

    @FXML
    private TableColumn<?, ?> appProvider;

    @FXML
    private Button modifyAppointmentBtn;

    @FXML
    private Button myProfileBtn;

    @FXML
    private Button newAppointmentBtn;

    @FXML
    private TableView<?> patientTableView;

    @FXML
    private TableColumn<?, ?> visitReason;
    
    private String username;
    
    private int patientID;
    
    public void initialize() {}
    
    public void initData(String user) {
    	this.username = user;
    	
    	// Update welcomeLabel with user's first name
    	// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
		Statement stmt;
		try {
			// Create a statement object
			stmt = conn.createStatement();
			
			// Query patient table
	        String sqlSelect = "SELECT PatientID, FirstName FROM " + AppointmentDBConstants.PATIENT_TABLE_NAME
	        		+ " WHERE Username = '" + user + "'";
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        result.last();
	        
	        this.patientID = result.getInt("PatientID");
	        welcomeLabel.setText("Hello " + result.getString("FirstName") + "!");
	        
		} 
		catch (SQLException ex) {
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Application Error");
        	alert.setContentText(ex.getMessage());
        	
        	alert.showAndWait();
		}
		
		
    	
    }

} 

