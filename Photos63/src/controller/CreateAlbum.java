package controller;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
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

public class CreateAlbum {
	@FXML
	private TextField albumname;
	
	@FXML
	private Button cancel, create;
	
	private Album newAlbum;
	private Stage primaryStage;
	private AlbumUI currentUI;
	
	public void start(Stage primaryStage, AlbumUI currentUI, Album newAlbum) throws IOException{
		this.primaryStage = primaryStage;
		this.currentUI = currentUI;
		this.newAlbum = newAlbum;
	}
	
	@FXML
	void cancel(ActionEvent e) {
		Stage stage = (Stage) this.primaryStage;
		stage.close();
	}
	
	void create(ActionEvent e) {
		//If input is empty
		if(this.albumname.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error: No Title");
			alert.setHeaderText(null);
			alert.setContentText("Must include name");
			alert.showAndWait();
			return;
		}
		
		for(int i = 0; i <this.currentUI.getAlbumList().size(); i++) {
			String currentAlbum = this.currentUI.getAlbumList().get(i).getName().trim();
			String newName = this.albumname.getText().trim();
			if(newName.equals(currentAlbum)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error: Name already exists");
				alert.setHeaderText(null);
				alert.setContentText("Name already exists!");
				alert.showAndWait();
				return;
			}
		}
		this.newAlbum.setName(this.albumname.getText().trim());
		Stage stage = (Stage) this.primaryStage;
		stage.close();
	}
}
