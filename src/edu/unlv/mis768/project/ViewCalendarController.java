package edu.unlv.mis768.project;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ViewCalendarController {

    @FXML
    private TableColumn<Appointment, Patient> patientColumn;

    @FXML
    private TextArea aptCmtTxt;

    @FXML
    private DatePicker appDate;

    @FXML
    private Button addBtn;

    @FXML
    private Button addCmtBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private TableColumn<Appointment, Slot> timeColumn;

    @FXML
    private TableView<Appointment> doctorAptTableView;

    @FXML
    private TableColumn<Appointment, String> appointmentTypeColumn;

    @FXML
    private ComboBox<Doctor> providerCmb;

    @FXML
    private ImageView logo;

    @FXML
    private Button rescheduleBtn;
    
    private Staff admin;
    
    private ArrayList<Doctor> doctorList;
    
    public void initialize() {
    	
    	// set up the columns in the table
    	patientColumn.setCellValueFactory(new 
    			PropertyValueFactory<Appointment, Patient>("patient"));
    	timeColumn.setCellValueFactory(new 
    			PropertyValueFactory<Appointment, Slot>("slot"));
    	appointmentTypeColumn.setCellValueFactory(new 
    			PropertyValueFactory<Appointment, String>("visitType"));
    	
    	// populate provider combo box
    	doctorList = new ArrayList<Doctor>();
    	
    	// Create a connection to the database.
        Connection conn =
               AppointmentDBUtil.getDBConnection();
        
        try {
        	// Create a statement object
			Statement stmt = conn.createStatement();
	        
	        // Populate doctorList ArrayList 
	        String sqlSelect = "SELECT FirstName, LastName, ProviderID, Specialty "
	        		+ "FROM " + AppointmentDBConstants.PROVIDER_TABLE_NAME;
	        
	        ResultSet result = stmt.executeQuery(sqlSelect);
	        
	        result.first();
	        do {
	        	doctorList.add(new Doctor(result.getString("FirstName"),
	        						result.getString("LastName"),
	        						result.getInt("ProviderID"),
	        						result.getString("Specialty")));
	        } while(result.next());
	        
	        
        }  catch (SQLException ex) {
				Alert alert = new Alert(AlertType.ERROR);
	        	alert.setTitle("Error");
	        	alert.setHeaderText("Application Error");
	        	alert.setContentText(ex.getMessage());
	        	
	        	alert.showAndWait();
		} finally {
			providerCmb.getItems().clear();
			providerCmb.getItems().addAll(doctorList);
		}
    }
    
    /**
     * Accepts Staff object and updates welcome label
     * Used for navigation through staff portal screens
     * @param patient A staff object
     */
    public void initData(Staff staff) {
    	this.admin = staff; 
    }
    
    public void populateTableView() {
    	
    	// Clear TableView
    	doctorAptTableView.getItems().clear();
    	
    	// Determine selected doctor
    	Doctor doctor = providerCmb.getValue();
    	
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
    
    // Event listener for Date Picker
    public void appDateListener() {
    	
    	// Check if a provider is selected
    	Doctor doctor = providerCmb.getValue();
    	
    	if (doctor != null) {
    		// A provider is selected
    		// Populate table view 
    		populateTableView();
    	}
    	
    }
    
    // Event listener for Provider combo box
    public void providerComboBoxListener() {
    	
    	// Check if a date is selected
    	LocalDate appointmentDate = appDate.getValue();
    	if (appointmentDate != null) {
    		// A date is selected
    		// Populate table view
    		populateTableView();
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
    		populateTableView();
    	}
    	
    	
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
