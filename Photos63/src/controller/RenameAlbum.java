package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Album;
import model.AlbumUI;

public class RenameAlbum {
	@FXML
	private TextField newName;
	
	@FXML
	private Button cancel, rename;
	
	private Stage primaryStage;
	private AlbumUI currentUI;
	private Album activeAlbum;
	
	public void start(Stage primaryStage, AlbumUI currentUI, Album activeAlbum) throws IOException{
		this.primaryStage = primaryStage;
		this.currentUI = currentUI;
		this.activeAlbum = activeAlbum;
	}
	
	@FXML
	void cancel(ActionEvent e) {
		Stage stage = (Stage) this.primaryStage;
		stage.close();
	}
	
	@FXML
	void rename(ActionEvent e) {
		if(this.newName.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error: No input");
			alert.setHeaderText(null);
			alert.setContentText("Must include a name!");
			alert.showAndWait();
			return;
		}
		
		/**
		 * Check for any duplicates
		 */
		for(int i = 0; i < this.currentUI.getAlbumList().size(); i++) {
			String currName = this.currentUI.getAlbumList().get(i).getName().trim();
			if(this.newName.getText().trim().equals(currName) ) {
				Alert alert2 = new Alert(AlertType.WARNING);
				alert2.setTitle("Warning: Name in use");
				alert2.setHeaderText(null);
				alert2.setContentText("An album with that name already exists.");
				alert2.showAndWait();
				return;
			}
		}
		
		this.activeAlbum.setName(newName.getText().trim());
		
		Stage stage = (Stage) this.primaryStage;
		stage.close();
	}
}
