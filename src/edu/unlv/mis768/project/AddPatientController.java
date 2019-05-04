package edu.unlv.mis768.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;

public class AddPatientController {

    @FXML
    private TextField dateOfBirthTxt;

    @FXML
    private TextField primaryPhysicianTxt;

    @FXML
    private TextField usernameTxt;

    @FXML
    private TextField firstnameTxt;

    @FXML
    private Font x1;

    @FXML
    private Label dateOfBirth;

    @FXML
    private TextField lastNameTxt;

    @FXML
    private Button saveBtn;
    
    public void initialize() {}
    
    // Save Button Listener
    public void saveButtonListener() {
    	
    }

}
