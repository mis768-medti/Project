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
    
    private Staff admin;
    
    private ArrayList<Doctor> doctorList;
   
    /**
     * Method to call on initialization setting up columns
     * in the tableview and populate provider combo box
     */
    public void initialize() {
    	addBtn.setVisible(false);
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
     * @param staff A staff object
     */
    public void initData(Staff staff) {
    	this.admin = staff; 
    }
    
    /**
     * Lists out appointment scheduled in the TableView
     * for the selected date and selected provider
     */
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
    
    /**
     * Refreshes tableview if both a provider
     * and date are selected
     */
    public void appDateListener() {
    	
    	// Check if a provider is selected
    	Doctor doctor = providerCmb.getValue();
    	
    	if (doctor != null) {
    		// A provider is selected
    		// Populate table view 
    		populateTableView();
    		addBtn.setVisible(true);
    	}
    	
    }
    
    /**
     * Refreshes tableview if both a provider
     * and date are selected
     */
    public void providerComboBoxListener() {
    	
    	// Check if a date is selected
    	LocalDate appointmentDate = appDate.getValue();
    	if (appointmentDate != null) {
    		// A date is selected
    		// Populate table view
    		populateTableView();
    	}
    	
    }
    
    /**
     * Deletes the appointment selected in the TableView
     */
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
    
    
    /**
     * Redirects user to the Staff Portal GUI
     * @param e ActionEvent
     */
    public void returnHomeButtonListener(ActionEvent e) {
    	try {
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
    	} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Application Error");
        	alert.setContentText(ex.getMessage());
        	
        	alert.showAndWait();
		}
    }
    
    /**
     * Redirects user to the Add Appointment GUI
     * @param e ActionEvent
     */
    public void addAppointmentListener(ActionEvent e) {
    	try {
	    	Doctor doctor = providerCmb.getValue();
	    	FXMLLoader loader = new FXMLLoader();
	    	// specify the file location
	    	loader.setLocation(getClass().getResource("AdminAddAppointment.fxml"));
	    	
	    	// load the UI and call the controller method
	    	Parent parent = loader.load();
	    	AdminAddAppointmentController controller = loader.getController();
	    	controller.initData(doctor, this.admin);
	    	
	    	// set the scene
	    	Scene scene = new Scene(parent);
	    	
	    	// get the current window
	    	Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
	    	// change the title
	    	stage.setTitle("Schedule Appointment");
	    	// set the scene for the stage
	    	stage.setScene(scene);
	    	// show the stage
	    	stage.show();
    	} catch (Exception ex) {
			Alert alert = new Alert(AlertType.ERROR);
        	alert.setTitle("Error");
        	alert.setHeaderText("Application Error");
        	alert.setContentText(ex.getMessage());
        	
        	alert.showAndWait();
		}
    }
    
    /**
     * Lists comments for the selected appointment in the text field
     */
    public void commentTxtListener(){
    	int selectedRow = doctorAptTableView.getSelectionModel().getSelectedIndex();
    	
    	if (selectedRow >= 0) {
    		Appointment commentToDisplay = doctorAptTableView.getItems().get(selectedRow);
        	aptCmtTxt.setText(commentToDisplay.getComments());	
    	}
    	
    }
    
    /**
     * Saves comments entered in the text field to the selected
     * appointment object
     */
    public void addCommentBtnListener() {
    	// get the Appointment selected in the TableView
    	int selectedRow = doctorAptTableView.getSelectionModel().getSelectedIndex();
    	String commentToAdd = aptCmtTxt.getText();
    	if (selectedRow == -1) {
    		// No rows selected
    		Alert alert = new Alert(AlertType.WARNING);
    		alert.setTitle("Warning");
    		alert.setHeaderText("Invalid Addition of Comment Attempt");
    		alert.setContentText("Please select an appointment to add comment to");

    		alert.showAndWait();
    	}
    	else {
    		Appointment appointmentToAddComment = doctorAptTableView.getItems().get(selectedRow);
        	
        	// add comment
    		appointmentToAddComment.setComments(commentToAdd);
        	appointmentToAddComment.updateCommentInDB();
        	//show dialog
        	Alert alert = new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Confirmation");
    		alert.setHeaderText("Comment Added to DB");
    		alert.setContentText("Comment successfully  Added");

    		alert.showAndWait();
        	// refresh tableView
        	appDateListener();
        	
    	}
    }

}
