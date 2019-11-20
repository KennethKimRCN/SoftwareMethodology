package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.User;
import model.UserList;

/**
 * @author Khangnyon Kim
 * @author Whitney Poh
 */
public class Controller_AlbumList implements Controller_Logout {
	
	@FXML
	ListView<Album> albumListView;
	
	@FXML
	Text nameText;
	
	private ObservableList<Album> obsList;
	private List<Album> albums = new ArrayList<Album>();
	private User user;
	private UserList ulist;
	
	
	public void start(Stage mainStage) {
		
		nameText.setText("Welcome, " + user.getUsername());
		
		albums = user.getAlbums();
		
		obsList = FXCollections.observableArrayList(albums);
		
		albumListView.setCellFactory(new Callback<ListView<Album>, ListCell<Album>>(){
			
			@Override
			public ListCell<Album> call(ListView<Album> p) {
				
				return new AlbumCell();
			}
		});	
		
		albumListView.setItems(obsList);;

	}
	
	public void goToAlbum(Stage mainStage) throws ClassNotFoundException, IOException {
		int index = albumListView.getSelectionModel().getSelectedIndex();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumContent.fxml"));
        Parent parent = (Parent) loader.load();
        Controller_PhotoList ctrl = loader.<Controller_PhotoList>getController();
        ctrl.setAlbum(albums.get(index));
        Scene scene = new Scene(parent);
        
		ctrl.start(mainStage);
         
		mainStage.setScene(scene);
		mainStage.show();  
		
	}
	
	@FXML 
	protected void handleLogoutButton(ActionEvent event) throws ClassNotFoundException {
    	logout(event);       
	}
	
	@FXML
	private void searchPhotos(ActionEvent event) throws IOException, ClassNotFoundException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoSearch.fxml"));
        Parent parent = (Parent) loader.load();
        Controller_SearchPhoto ctrl = loader.<Controller_SearchPhoto>getController();
        ctrl.setUser(user);
        ctrl.setUlist(ulist);
        Scene scene = new Scene(parent);
        
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
        
		ctrl.start(app_stage);
         
		app_stage.setScene(scene);
		app_stage.show();
	}
	
	@FXML
	private void handleAddButton(ActionEvent event) throws IOException {
		int index = albumListView.getSelectionModel().getSelectedIndex();
		Dialog<Album> dialog = new Dialog<>();
		dialog.setTitle("Create a New Album");
		dialog.setHeaderText("Add a new album to PhotoBox!");
		dialog.setResizable(true);
		   
		Label albumLabel = new Label("Album Name: ");
		TextField albumTextField = new TextField();
		albumTextField.setPromptText("Album Name");
		   
		GridPane grid = new GridPane();
		grid.add(albumLabel, 1, 1);
		grid.add(albumTextField, 2, 1);
		   
		dialog.getDialogPane().setContent(grid);
		   
		ButtonType buttonTypeOk = new ButtonType("Add", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		   
		dialog.setResultConverter(new Callback<ButtonType, Album>() {
			@Override
			public Album call(ButtonType b) {
				if (b == buttonTypeOk) {
					   
					String error = checkFields(albumTextField.getText());
					   
					if (error != null) {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Error");
						alert.setHeaderText("Error");
						String content = error;
						alert.setContentText(content);
						alert.showAndWait();
						return null;
					}
											   
					return new Album(albumTextField.getText().trim());
				}
				return null;
			}

			
		});
		   
		Optional<Album> result = dialog.showAndWait();
		   
		if (result.isPresent()) {
			Album tempAlbum = (Album) result.get();
			obsList.add(tempAlbum);
			albums.add(tempAlbum);
			UserList.writeUserList(ulist);
			if (obsList.size() == 1) {
				albumListView.getSelectionModel().select(0);
			}
			else
			{
				index = 0;
				for(Album a: albums)
				{
					   
					if(a == tempAlbum)
					{
						albumListView.getSelectionModel().select(index);
						break;
					}
					   
					index++;
				}
			}
				   
		}
	}
	
	private String checkFields(String albumName) {
		if (albumName.trim().isEmpty())
			return "Album Name is a required field.";
		if (user.albumNameExists(albumName))
			return albumName + " already exists.";
		else
			return null;
	}
	
	public void setUser(User u) {
		this.user = u;
	}
	
	public void setUlist(UserList ulist) {
		this.ulist = ulist;
	}
	
	private class AlbumCell extends ListCell<Album> {
			
		AnchorPane apane = new AnchorPane();
		StackPane spane = new StackPane();
		
		ImageView imageView = new ImageView();
		Label albumNameLabel = new Label();
		Label dateRangeLabel = new Label();
		Label oldestPhotoLabel = new Label();
		Label numPhotosLabel = new Label();
		Button deleteAlbumBtn = new Button("Delete");
		Button renameAlbumBtn = new Button("Rename");
		Button viewAlbumBtn = new Button("View");
			
		public AlbumCell() {
			super();
		
			
			
			imageView.setFitWidth(90.0);
			imageView.setFitHeight(90.0);
			imageView.setPreserveRatio(true);
			
			StackPane.setAlignment(imageView, Pos.CENTER);
			
			spane.getChildren().add(imageView);
			
			spane.setPrefHeight(110.0);
			spane.setPrefWidth(90.0);
			
			
			AnchorPane.setLeftAnchor(spane, 0.0);
			
			AnchorPane.setLeftAnchor(albumNameLabel, 100.0);
			AnchorPane.setTopAnchor(albumNameLabel, 0.0);
			
			AnchorPane.setLeftAnchor(dateRangeLabel, 100.0);
			AnchorPane.setTopAnchor(dateRangeLabel, 15.0);
			
			AnchorPane.setLeftAnchor(oldestPhotoLabel, 100.0);
			AnchorPane.setTopAnchor(oldestPhotoLabel, 30.0);
			
			AnchorPane.setLeftAnchor(numPhotosLabel, 100.0);
			AnchorPane.setTopAnchor(numPhotosLabel, 45.0);
			
			deleteAlbumBtn.setVisible(false);
			
			AnchorPane.setLeftAnchor(deleteAlbumBtn, 100.0);
			AnchorPane.setBottomAnchor(deleteAlbumBtn, 0.0);
			
			renameAlbumBtn.setVisible(false);
			
			AnchorPane.setRightAnchor(renameAlbumBtn, 0.0);
			AnchorPane.setBottomAnchor(renameAlbumBtn, 0.0);
			
			viewAlbumBtn.setVisible(false);
			
			AnchorPane.setRightAnchor(viewAlbumBtn, 0.0);
			AnchorPane.setTopAnchor(viewAlbumBtn, 0.0);
			
			apane.getChildren().addAll(spane, albumNameLabel, dateRangeLabel,
					oldestPhotoLabel, numPhotosLabel, deleteAlbumBtn, renameAlbumBtn, viewAlbumBtn);
			
			apane.setPrefHeight(110.0);
			
			albumNameLabel.setMaxWidth(250.0);
			
			setGraphic(apane);
		}
			
		@Override
		public void updateItem(Album album, boolean empty) {
			super.updateItem(album, empty);
			setText(null);
			if(album == null)
			{
				imageView.setImage(null);
				albumNameLabel.setText("");
				dateRangeLabel.setText("");
				oldestPhotoLabel.setText("");
				numPhotosLabel.setText("");
				deleteAlbumBtn.setVisible(false);
				renameAlbumBtn.setVisible(false);
				viewAlbumBtn.setVisible(false);
			}
			else{
				imageView.setImage(album.getAlbumPhoto());
				albumNameLabel.setText("Album name: " + album.getName());
				dateRangeLabel.setText(album.getDateRange());
				oldestPhotoLabel.setText("Oldest Photo: " + album.getOldestPhotoDate());
				numPhotosLabel.setText("Number of Photos: " + album.getCount());
				deleteAlbumBtn.setVisible(true);
				renameAlbumBtn.setVisible(true);
				viewAlbumBtn.setVisible(true);
				
				deleteAlbumBtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						deleteAlbum(e, album);
					}
				});
				
				renameAlbumBtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						renameAlbum(e, album);
					}
				});
				
				viewAlbumBtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						try {
							goToAlbumContent(e, album);
						} catch (ClassNotFoundException | IOException e1) {
							e1.printStackTrace();
						} 
					}
				});
			}
			
		}
		
		public void deleteAlbum(ActionEvent event, Album album) {
			Alert alert = 
	 				   new Alert(AlertType.INFORMATION);
	 		   alert.setTitle("Delete Album");
	 		   alert.setHeaderText(
	 				   "There's no going back!");

	 		   String content = "Are you sure you want to delete the album " + album.getName() + "?";

	 		   alert.setContentText(content);

	 		   Optional<ButtonType> result = alert.showAndWait();
	 		   
	 		  if(result.isPresent())
	 		  {
	 			  obsList.remove(album);
	 			
	 			  user.removeAlbum(album);
	 		  
				try {
					
					UserList.writeUserList(ulist);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
	 		  }
		}
		
		public void renameAlbum(ActionEvent e, Album album) {
			   Dialog<User> dialog = new Dialog<>();
			   dialog.setTitle("Rename Album");
			   dialog.setHeaderText("Rename " + album.getName() + " ?");
			   dialog.setResizable(true);
			   
			   Label nameLabel = new Label("New Name: ");
			   TextField nameTextField = new TextField();
			   
			   GridPane grid = new GridPane();
			   grid.add(nameLabel, 1, 1);
			   grid.add(nameTextField, 2, 1);
			   			   
			   dialog.getDialogPane().setContent(grid);
			   
			   ButtonType buttonTypeOk = new ButtonType("Rename", ButtonData.OK_DONE);
			   dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
			   
			   Optional<User> result = dialog.showAndWait();
			   
			   String error = checkFields(nameTextField.getText());
			   
				
				 // if user presses rename, set the new name
					if (result.isPresent()) {
						if (error != null) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Error");
							alert.setHeaderText("Error");
							String content = error;
							alert.setContentText(content);
							alert.showAndWait();
							return;
						}
						else{
						   album.setName(nameTextField.getText().trim());
						  updateItem(album, true);
						  try{
							  UserList.writeUserList(ulist);
						  }
						  catch(Exception i)
						  {
							  i.printStackTrace();
						  }
						}
				   }
					   
		
		}
		
		public void goToAlbumContent(ActionEvent e, Album album) throws IOException, ClassNotFoundException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AlbumContent.fxml"));
	        Parent parent = (Parent) loader.load();
	        Controller_PhotoList ctrl = loader.<Controller_PhotoList>getController();
	        ctrl.setAlbum(album);
	        ctrl.setUser(user);
	        ctrl.setUlist(ulist);
	        Scene scene = new Scene(parent);
	        
	        Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();	
	        
			ctrl.start(app_stage);
	         
			app_stage.setScene(scene);
			app_stage.show();
		}
	}
	
}
