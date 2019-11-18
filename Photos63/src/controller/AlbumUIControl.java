package controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.Album;
import model.AlbumUI;
import model.Photo;

public class AlbumUIControl {
	@FXML
	private Text name;
	
	@FXML
	private Button logout, createAlbum;
	
	@FXML
	private ScrollPane albumsWrapper;
	@FXML
	private FlowPane albumsContainer;
	
	@FXML
	private Pane albumInfoPane;
	
	@FXML
	private StackPane albumPaneThumbnail;
	
	@FXML
	private Text albumPaneName, albumPaneYear, albumPanePhotoCount;
	
	@FXML
	private Button albumPaneRename, albumPaneDelete, albumPaneOpen;
	
	@FXML
	private Stage primaryStage, previousStage;
	
	private AlbumUI currUI;
	private String username;
	private Album activeAlbum;
	
	private ArrayList<Album> userAlbumList;
	private ArrayList<StackPane> albumFormattedContent;
	
	public void start(Stage primaryStage, Stage previousStage, AlbumUI currentUI) throws IOException{
		this.primaryStage = primaryStage;
		this.previousStage = previousStage;
		
		this.currUI = currentUI;
		this.username = currentUI.getUsername();
		name.setText("Username: " + username);
		
		this.userAlbumList = this.currUI.getAlbumList();
		this.albumFormattedContent = new ArrayList<StackPane>();
		
		initAlbumContainer(this.userAlbumList, this.albumFormattedContent);
	}
	
	private void initAlbumContainer(ArrayList<Album> albumList, ArrayList<StackPane> albumFormattedContent) {
		if(albumList.size() == 0) {
			this.activeAlbum = null;
			this.albumsWrapper.setVisible(false);
			this.albumInfoPane.setVisible(false);
		}
		else {
			this.activeAlbum = albumList.get(0);
			this.albumsWrapper.setVisible(true);
			this.albumInfoPane.setVisible(true);
			
			updateAlbumPane(this.activeAlbum);
			
			for(int i = 0; i< albumList.size(); i++) {
				StackPane pane = getFormattedAlbum(albumList.get(i));
				albumFormattedContent.add(pane);
			}
			this.albumsContainer.getChildren().setAll(albumFormattedContent);
		}
		return;
	}
	
	private void updateAlbumPane(Album album) {
		Image img = new Image("File:img/album-icon.png", 60, 60, false, false);
		ImageView imgView = new ImageView(img);
		
		this.albumPaneThumbnail.getChildren().clear();
		this.albumPaneThumbnail.getChildren().add(imgView);
		
		this.albumPaneName.setText(album.getName());
		this.albumPanePhotoCount.setText(Integer.toString(album.getPhotoCount()) + " Photos");
		if(album.getPhotoCount() == 0) {
			this.albumPaneYear.setText("");
		}
		else {
			long min = -1;
			long max = -1;
			for(int i = 0; i < album.getPhotoList().size(); i++) {
				Photo temp = album.getPhotoList().get(i);
				if(i == 0) {
					min = temp.getMod();
					max = temp.getMod();
				}
				else {
					if(temp.getMod() > max) {
						max = temp.getMod();
					}
					if(temp.getMod() < min) {
						min = temp.getMod();
					}
				}
			}
			Date beginning = new Date(min);
			Date end = new Date(max);
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yy");
			
			this.albumPaneYear.setText(df.format(beginning) + " to " + df.format(end));
		}
	}
	
	private StackPane getFormattedAlbum(Album album) {
		Image img = new Image("File:img/album-icon.png", 60, 60, false, false);
		ImageView imgView = new ImageView(img);
		
		Text text = new Text(album.getName());
		text.setWrappingWidth(80);
		text.setTextAlignment(TextAlignment.CENTER);
		
		StackPane pane = new StackPane();
		pane.getChildren().add(imgView);
		pane.getChildren().add(text);
		
		imgView.setTranslateY(imgView.getTranslateY() - 10);
		text.setTranslateY(text.getTranslateY() + 30);
		
		pane.setMaxSize(90, 90);
		pane.setMinSize(90, 90);
		pane.setStyle("-fx-background-color: #efefef; -fx-border-color: #e0e0e0;");
		
		pane.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent e) {
				e.consume();
				activeAlbum = album;
				albumInfoPane.setVisible(true);
				
				updateAlbumPane(album);
			}
		});
		return pane;
	}
	
	@FXML
	void logout(ActionEvent e) throws IOException{
		Stage stage = (Stage) this.primaryStage;
		stage.close();
		this.previousStage.show();
	}
	
	
	@FXML
	void createAlbumAction(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AlbumCreate.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		Stage newStage = new Stage();
		newStage.initModality(Modality.WINDOW_MODAL);
		newStage.initOwner(this.primaryStage);

		Album newAlbum = new Album();

		
		//CreateAlbumController Controller = loader.getController();
		//Controller.start(newStage, this.currUI, newAlbum);

		Scene scene = new Scene(root, 322, 191);
		newStage.setResizable(false);
		newStage.setScene(scene);
		newStage.showAndWait();

		//if controller did assign name to our album object
		if(!(newAlbum.getName() == null)) {
			//add to arrayList in currPlayer
			this.currUI.addAlbum(newAlbum);
			//set as the active album
			this.activeAlbum = newAlbum;
			//set album display to true and also album pane(Have at least one album now)
			this.albumsWrapper.setVisible(true);
			this.albumInfoPane.setVisible(true);
			//and update Pane to display the information of it
			updateAlbumPane(newAlbum);

			//also need to add to our display content
			StackPane pane = getFormattedAlbum(newAlbum);
			this.albumFormattedContent.add(pane);

			//update view
			this.albumsContainer.getChildren().clear();
			this.albumsContainer.getChildren().setAll(this.albumFormattedContent);
		}

	}

	@FXML
	void deleteAlbumAction(ActionEvent event) throws IOException {
		//trigger warning before continuing, asking for user confirmation
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete Album");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure you want to delete this album?");
		Optional<ButtonType> result = alert.showAndWait();

		if(result.get() == ButtonType.OK) {
			//Delete from albumFormattedContent.
			this.albumFormattedContent.remove(getAlbumIndex(this.albumFormattedContent, this.activeAlbum.getName()));

			//Delete from Player object
			this.currUI.removeAlbum(this.activeAlbum);

			//Refresh Flow Pane
			this.albumsContainer.getChildren().clear();
			this.albumsContainer.getChildren().setAll(this.albumFormattedContent);

			//updates current active album and update Album Pane
			if(this.userAlbumList.size() == 0) {
				this.activeAlbum = null;
				this.albumInfoPane.setVisible(false);
				this.albumsWrapper.setVisible(false);
			}
			else {
				this.activeAlbum = this.userAlbumList.get(0);
				this.albumsWrapper.setVisible(true);
				this.albumInfoPane.setVisible(true);
				updateAlbumPane(this.activeAlbum);
			}
			return;
		}
	}

	@FXML
	void renameAlbumAction(ActionEvent event) throws IOException {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/AlbumRename.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		Stage newStage = new Stage();
		newStage.initModality(Modality.WINDOW_MODAL);
		newStage.initOwner(this.primaryStage);

		String oldName = this.activeAlbum.getName();
		//RenameAlbumController Controller = loader.getController();
		//Controller.start(newStage, this.currUI, this.activeAlbum);

		Scene scene = new Scene(root, 322, 191);
		newStage.setResizable(false);
		newStage.setScene(scene);
		newStage.showAndWait();

		int albumIndex = getAlbumIndex(this.albumFormattedContent, oldName);
		//parse for its text field, and update it
		for(Node node: this.albumFormattedContent.get(albumIndex).getChildren()) {
			if(node instanceof Text && ((Text)node).getText().equals(oldName)) {
				((Text)node).setText(this.activeAlbum.getName());
			}
		}
		//update flow pane
		this.albumsContainer.getChildren().clear();
		this.albumsContainer.getChildren().setAll(this.albumFormattedContent);

		updateAlbumPane(this.activeAlbum);
	}

	@FXML
	void openAlbumAction(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Photos.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		//PhotosController Controller = loader.getController();
		//Controller.start(this.primaryStage, this.previousStage, this.currUI, this.activeAlbum);

		Scene scene = new Scene(root);
		this.primaryStage.setResizable(false);
		this.primaryStage.setScene(scene);
		this.primaryStage.show();

	}

	private int getAlbumIndex(ArrayList<StackPane> albumsFlowList, String albumName) {
		for(int i = 0; i < albumsFlowList.size(); i++) {
			for(Node node: albumsFlowList.get(i).getChildren()) {
				if(node instanceof Text && ((Text)node).getText().equals(albumName.trim())) {
					return i;
				}
			}
		}
		return -1;
	}
	
}
