package controller;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import model.Album;
import model.AlbumUI;

public class OpenAlbum {
	@FXML
	private ListView<Album> albumListView;
	private ObservableList<Album> obsList;
	
	private Stage primaryStage;
	private AlbumUI currentUI;
	private Album currentAlbum;
	
	private ArrayList<Album> albumList;
	
	public void start(Stage primaryStage, AlbumUI currentUI, Album currentAlbum) {
		this.primaryStage = primaryStage;
		this.currentUI = currentUI;
		this.currentAlbum = currentAlbum;
		
		this.albumList = this.currentUI.getAlbumList();
		this.obsList = FXCollections.observableArrayList();
		for(int i = 0; i < this.albumList.size(); i++){
			if(!this.albumList.get(i).equals(this.currentAlbum)) {
				this.obsList.add(this.albumList.get(i));
			}
		}
		this.albumListView.setItems(this.obsList.sorted());
		
		this.albumListView.getSelectionModel().selectFirst();
	}
	
	@FXML
	void cancelAction(ActionEvent e) {
		Stage stage = (Stage) this.primaryStage;
		stage.close();
	}
	
	@FXML
	void openAlbum(ActionEvent e){
		Stage stage = (Stage) this.primaryStage;
		stage.close();
	}
}
