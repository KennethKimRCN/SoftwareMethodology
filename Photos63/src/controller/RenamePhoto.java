package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.Album;
import model.Photo;

public class RenamePhoto {
	@FXML
	private TextField newName;
	
	@FXML
	private Button cancel, rename;

	private Stage primaryStage;
	
	private Album currentAlbum;
	
	private Photo activePhoto;
	
	public void start(Stage primaryStage, Album currAlbum, Photo activePhoto) throws IOException {
		this.primaryStage = primaryStage;
		this.currentAlbum = currAlbum;
		this.activePhoto = activePhoto;
	}
	
	@FXML
	void cancelAction(ActionEvent event) {
		Stage stage = (Stage) this.primaryStage;
		stage.close();
	}
	
	@FXML
	void rename(ActionEvent e) {
		if(this.newName.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error: Empty name");
			alert.setHeaderText(null);
			alert.setContentText("Must have a name!");
			alert.showAndWait();
			return;
		}
		for(int i = 0; i < this.currentAlbum.getPhotoList().size(); i++) {
			String currName = this.currentAlbum.getPhotoList().get(i).getCaption().trim();
			if(this.newName.getText().trim().equals(currName) ) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error: Name already exists");
				alert.setHeaderText(null);
				alert.setContentText("Name already exists!");
				alert.showAndWait();
				return;
			}
		}
		
		this.activePhoto.setCaption(this.newName.getText().trim());
		
		Stage stage = (Stage) this.primaryStage;
		stage.close();
	}
}
