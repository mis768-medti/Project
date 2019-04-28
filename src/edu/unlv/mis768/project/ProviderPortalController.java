package edu.unlv.mis768.project;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

public class ProviderPortalController {

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
    private TableColumn<?, ?> name;

    @FXML
    private ImageView logo;

    @FXML
    private TableColumn<?, ?> time;

    @FXML
    private TableColumn<?, ?> age;

    @FXML
    private Button rescheduleBtn;

}
