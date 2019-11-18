package controller;

import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Login {
	
	@FXML
	TextField username;
	
	@FXML
	Button login;
	
	@FXML
	Button delete;

	private Stage primaryStage;
	
	public void login(ActionEvent e) {
		if(username.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR: MUST ENTER USERNAME");
			alert.setHeaderText(null);
			alert.setContentText("ERROR: MUST ENTER USERNAME");
			alert.showAndWait();
			return;
		}
		
		String name = username.getText().trim();
		
		if(name.equalsIgnoreCase("admin")) {
			loadAdmin();
			return;
		}
		
		
	}
	
	private void loadAdmin() {
		this.primaryStage.hide();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/admin.fxml"));
	}
}


