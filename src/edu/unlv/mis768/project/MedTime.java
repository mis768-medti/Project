package edu.unlv.mis768.project;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MedTime extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		// Load the fxml file
		Parent parent = FXMLLoader.load(
				getClass().getResource("SignIn.fxml"));
		
		// Build the scene graph.
		Scene scene = new Scene(parent);
		
		// Display window using the scene graph
		primaryStage.setTitle("MedTime");
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}
