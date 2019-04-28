package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;

public class AdminPortalController {

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button userMgmtBtn;

    @FXML
    private Button viecalBtn;

    @FXML
    private Button LogoutBtn;

    @FXML
    private Button appMgmtBtn;
    
    private String username;
    
    private int adminID;
    
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
	        String sqlSelect = "SELECT AdminID, FirstName FROM " + AppointmentDBConstants.ADMIN_TABLE_NAME
	        		+ " WHERE Username = '" + user + "'";
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        result.last();
	        
	        this.adminID = result.getInt("AdminID");
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
