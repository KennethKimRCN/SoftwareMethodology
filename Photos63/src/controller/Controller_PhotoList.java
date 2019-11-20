package controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.SerializableImage;
import model.User;
import model.UserList;

/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
public class Controller_PhotoList implements Controller_Logout{
	@FXML
	ListView<Photo> photoListView;
	
	@FXML
	ImageView albumImageView;
	
	@FXML
	Text albumDateRangeText, oldestPhotoText, numPhotosText;
	
	@FXML
	Label albumNameText;
	
	private ObservableList<Photo> obsList;
	private List<Photo> photos;
	private Album album;
	private User user;
	private UserList ulist;
	
	public void start(Stage mainStage) {
		albumNameText.setText(album.getName());
		updateAlbumDetails();
		photos = album.getPhotos();
		obsList = FXCollections.observableArrayList(photos);
		photoListView.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>(){
			@Override
			public ListCell<Photo> call(ListView<Photo> p) {
				return new PhotoCell();
			}
		});	
		photoListView.setItems(obsList);
	}
	
	@FXML
	protected void deleteAlbum(ActionEvent event) {
      	  Alert alert = 
 				   new Alert(AlertType.INFORMATION);
 		   alert.setTitle("Delete Album");
 		   alert.setHeaderText(
 				   "There's no going back!");

 		   String content = "Are you sure you want to delete " + album.getName() + "?";

 		   alert.setContentText(content);

 		   Optional<ButtonType> result = alert.showAndWait();
 		   
 		  if(result.isPresent()){
 			  user.removeAlbum(album);
 		  
			try {
				UserList.writeUserList(ulist);
				backToAlbums(event);
			} catch (Exception e) {
				e.printStackTrace();
			}
 		  }
	}
	
	@FXML
	protected void backToAlbums(ActionEvent event) throws ClassNotFoundException {
		Parent parent;
   	 
		try {
				
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
	        parent = (Parent) loader.load();
	        Controller_AlbumList ctrl = loader.<Controller_AlbumList>getController();
	        //send user index to album list controller
	        ctrl.setUser(user);
	        ctrl.setUlist(ulist);
	        Scene scene = new Scene(parent);
	        
	        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
	        
			ctrl.start(app_stage);
	         
			app_stage.setScene(scene);
			app_stage.show();
		
		} catch (IOException e) {
			e.printStackTrace();
		}   
	}
	
	@FXML
	protected void choosePhoto(ActionEvent event) throws IOException {
		FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);
		
		fileChooser.setTitle("Upload Photo");
		Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		File file = fileChooser.showOpenDialog(app_stage);
		
		if (file == null)
			return;
		
        BufferedImage bufferedImage = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
        
        //check to see if this photo exists in the album
        SerializableImage tempImage = new SerializableImage();
        tempImage.setImage(image);
        for (Photo p: album.getPhotos()) {
        	if (tempImage.equals(p.getSerializableImage())) {
        		Alert alert = new Alert(AlertType.INFORMATION);
        		alert.setTitle("Error");
        		alert.setHeaderText("Error");
        		alert.setContentText("You cannot add the same photo to the same album!");
        		alert.showAndWait();
        		return;
        	}
        }
        
        Photo tempPhoto = null;
        boolean photoFound = false;
        
        //check to see if this photo exists in other albums
        //then tempPhoto = the photo object that it equals to
        for (Album a: user.getAlbums()) {
        	for (Photo p: a.getPhotos()) {
        		if (tempImage.equals(p.getSerializableImage())) {
        			tempPhoto = p;
        			photoFound = true;
        			System.out.println("Found the photo!");
        			break;
        		}
        		if (photoFound)
        			break;
        	}
        }
        
        //else, create a new photo object
        if (!photoFound)
        	tempPhoto = new Photo(image);
        
        
        
        album.addPhoto(tempPhoto);
        obsList.add(tempPhoto);
		UserList.writeUserList(ulist);
		
		updateAlbumDetails();
	}
	
	public void updateAlbumDetails() {
		albumImageView.setImage(album.getAlbumPhoto());
		albumDateRangeText.setText(album.getDateRange());
		oldestPhotoText.setText("Oldest Photo: " + album.getOldestPhotoDate());
		numPhotosText.setText("Number of Photos: " + album.getCount());
	}
	
	@FXML 
	protected void handleLogoutButton(ActionEvent event) throws ClassNotFoundException {
    	logout(event);      
	}
	
	private class PhotoCell extends ListCell<Photo> {
			
		AnchorPane apane = new AnchorPane();
		StackPane spane = new StackPane();
		
		ImageView imageView = new ImageView();
		Label captionLabel = new Label();
		Button deletePhotoBtn = new Button("Delete");
		Button editPhotoBtn = new Button("Edit");
		Button movePhotoBtn = new Button("Move");
		Button viewPhotoBtn = new Button("View");
			
		public PhotoCell() {
			super();
		
			imageView.setFitWidth(45.0);
			imageView.setFitHeight(45.0);
			imageView.setPreserveRatio(true);
			
			StackPane.setAlignment(imageView, Pos.CENTER);
			
			spane.getChildren().add(imageView);
			
			spane.setPrefHeight(55.0);
			spane.setPrefWidth(45.0);
			
			AnchorPane.setLeftAnchor(spane, 0.0);
			
			AnchorPane.setLeftAnchor(captionLabel, 55.0);
			AnchorPane.setTopAnchor(captionLabel, 0.0);
			
			AnchorPane.setRightAnchor(deletePhotoBtn, 0.0);
			AnchorPane.setBottomAnchor(deletePhotoBtn, 0.0);
			
			AnchorPane.setLeftAnchor(editPhotoBtn, 55.0);
			AnchorPane.setBottomAnchor(editPhotoBtn, 0.0);
			
			AnchorPane.setRightAnchor(movePhotoBtn, 70.0);
			AnchorPane.setBottomAnchor(movePhotoBtn, 0.0);
			
			AnchorPane.setLeftAnchor(viewPhotoBtn, 115.0);
			AnchorPane.setBottomAnchor(viewPhotoBtn, 0.0);
			
			deletePhotoBtn.setVisible(false);
			editPhotoBtn.setVisible(false);
			movePhotoBtn.setVisible(false);
			viewPhotoBtn.setVisible(false);
			
			apane.getChildren().addAll(spane, captionLabel,
					deletePhotoBtn, editPhotoBtn, movePhotoBtn, viewPhotoBtn);
			
			apane.setPrefHeight(55.0);
			
			captionLabel.setMaxWidth(300.0);
			
			setGraphic(apane);
		}
			
		@Override
		public void updateItem(Photo photo, boolean empty) {
			super.updateItem(photo, empty);
			setText(null);
			if(photo == null)
			{
				imageView.setImage(null);
				captionLabel.setText("");
				deletePhotoBtn.setVisible(false);
				editPhotoBtn.setVisible(false);
				movePhotoBtn.setVisible(false);
				viewPhotoBtn.setVisible(false);
			}
			if (photo != null) {
				imageView.setImage(photo.getImage());
				captionLabel.setText("Caption: " + photo.getCaption());
				deletePhotoBtn.setVisible(true);
				editPhotoBtn.setVisible(true);
				movePhotoBtn.setVisible(true);
				viewPhotoBtn.setVisible(true);
				
				deletePhotoBtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						deletePhoto(e, photo);
					}
				});
				
				editPhotoBtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						editPhoto(e, photo);
					}
				});
				
				movePhotoBtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						movePhoto(e, photo);
					}
				});
				
				viewPhotoBtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						try {
							viewPhoto(e, photo);
						} catch (ClassNotFoundException | IOException e1) {
							e1.printStackTrace();
						} 
					}
				});
			}	
		}
		
		public void deletePhoto(ActionEvent event, Photo photo) {
			Alert alert = 
	 				   new Alert(AlertType.INFORMATION);
	 		   alert.setTitle("Delete photo");
	 		   alert.setHeaderText(
	 				   "There's no going back!");

	 		   String content = "Are you sure you want to delete this photo?";

	 		   alert.setContentText(content);

	 		   Optional<ButtonType> result = alert.showAndWait();
	 		   
	 		  if(result.isPresent())
	 		  {
	 			 int i = album.findIndexByPhoto(photo);
	 			 album.removePhoto(i);
	 			 obsList.remove(photo);
	 			 updateAlbumDetails();
	 		  
				try {
					UserList.writeUserList(ulist);
				
				} catch (Exception e) {
					e.printStackTrace();
				}
				
	 		  }
		}
		
		public void editPhoto(ActionEvent e, Photo photo) {
			
			Dialog<Album> dialog = new Dialog<>();
			dialog.setTitle("Edit Caption");
			dialog.setHeaderText("Edit the caption for this photo.");
			dialog.setResizable(true);
			   
			Label captionLabel = new Label("Caption: ");
			TextArea captionTextArea = new TextArea();
			captionTextArea.setText(photo.getCaption());
			   
			GridPane grid = new GridPane();
			grid.add(captionLabel, 1, 1);
			grid.add(captionTextArea, 2, 1);
			   
			dialog.getDialogPane().setContent(grid);
			captionTextArea.setWrapText(true);
			ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
			   
			//wait for response from ok button
			   Optional<Album> result = dialog.showAndWait();
			   
			   //If user presses ok
					if (result.isPresent()) {
					   photo.setCaption(captionTextArea.getText());
					  updateItem(photo, true);
					  try{
						  UserList.writeUserList(ulist);
					  }
					  catch(Exception i)
					  {
						  i.printStackTrace();
					  }
				   
				   }
					   

		}
		
		public void movePhoto(ActionEvent e, Photo photo) {
			
			Dialog<Album> dialog = new Dialog<>();
			dialog.setTitle("Move Photo");
			dialog.setHeaderText("Move this photo to another album");
			dialog.setResizable(true);
			   
			Label moveLabel = new Label("Album to move this photo to: ");
			
			List<String> albumNames = new ArrayList<String>();
			for(Album a : user.getAlbums())
			{
				String temp = a.getName();
				if(a.getName()!= album.getName())
					albumNames.add(temp);
			}
			
			ObservableList<String> options = 
			FXCollections.observableArrayList(albumNames);
			
			
			ComboBox<String> moveComboBox = new ComboBox<String>(options);
	    
			   
			GridPane grid = new GridPane();
			grid.add(moveLabel, 1, 1);
			grid.add(moveComboBox, 1, 2);
			   
			dialog.getDialogPane().setContent(grid);
			   
			ButtonType buttonTypeOk = new ButtonType("Move", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
			   
			//wait for response from move button
			Optional<Album> result = dialog.showAndWait();
			 
			//If user presses move
			if (result.isPresent()) {
				String newAlbumName = moveComboBox.getSelectionModel().getSelectedItem();
				Album newAlbum = user.getAlbumByName(newAlbumName);
				newAlbum.addPhoto(photo);
				obsList.remove(photo);
				int index = album.findIndexByPhoto(photo);
				album.removePhoto(index);
				
				updateAlbumDetails();
			  try{
				  UserList.writeUserList(ulist);
			  }
			  catch(Exception i)
			  {
				  i.printStackTrace();
			  }
			}
		}
		
		public void viewPhoto(ActionEvent e, Photo photo) throws IOException, ClassNotFoundException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoDisplay.fxml"));
	        Parent parent = (Parent) loader.load();
	        Controller_DisplayPhoto ctrl = loader.<Controller_DisplayPhoto>getController();
	        //send user index to album list controller
	        ctrl.setPhotoIndex(album.findIndexByPhoto(photo));
	        ctrl.setAlbum(album);
	        ctrl.setUser(user);
	        ctrl.setUlist(ulist);
	        ctrl.setKey(Controller_DisplayPhoto.CAME_FROM_ALBUM_CONTENT);
	        Scene scene = new Scene(parent);
	        
	        Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();	
	        
			ctrl.start(app_stage);
	         
			app_stage.setScene(scene);
			app_stage.show();
		}
	}
	
	public void setAlbum(Album a) {
		album = a;
	}
	
	public void setUser(User u) {
		user = u;
	}
	
	public void setUlist(UserList ulist) {
		this.ulist = ulist;
	}
}
