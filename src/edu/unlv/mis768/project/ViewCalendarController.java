package edu.unlv.mis768.project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class ViewCalendarController {

    @FXML
    private TableColumn<?, ?> minor;

    @FXML
    private TextArea aptCmtTxt;

    @FXML
    private DatePicker appDate;

    @FXML
    private TableColumn<?, ?> dependentInd;

    @FXML
    private Button addBtn;

    @FXML
    private Button addCmtBtn;

    @FXML
    private Button deleteBtn;

    @FXML
    private Button returnBtn;

    @FXML
    private TableView<?> doctorAptTableView;

    @FXML
    private TableColumn<?, ?> appType;

    @FXML
    private ComboBox<?> providerCmb;

    @FXML
    private TableColumn<?, ?> name;

    @FXML
    private ImageView logo;

    @FXML
    private TableColumn<?, ?> time;

    @FXML
    private TableColumn<?, ?> age;

    @FXML
    private Button rescheduleBtn;
    
    private Staff admin;
    
    public void initialize() {}
    
    /**
     * Accepts Staff object and updates welcome label
     * Used for navigation through staff portal screens
     * @param patient A staff object
     */
    public void initData(Staff staff) {
    	this.admin = staff; 
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
