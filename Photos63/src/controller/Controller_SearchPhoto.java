package controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;
import model.UserList;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
public class Controller_SearchPhoto implements Controller_Logout{

	@FXML
	Button createAlbumBtn;
	
	@FXML
	TextField tagValueTextField, tagTypeTextField;
	
	@FXML
	DatePicker fromDate, toDate;
	
	@FXML
	TableView<Tag> tagTable;
	
	@FXML
	TableColumn<Tag,String> tagTypeColumn, tagValueColumn;
	
	@FXML
	TableColumn<Tag,Tag> tagDeleteColumn;
	
	@FXML
	ListView<Photo> photoListView;
	
	private ObservableList<Photo> photoObsList;
	private ObservableList<Tag> tagObsList;
	private User user;
	private List<Photo> photos;
	private List<Tag> tags;
	private UserList ulist;
	
	public void start(Stage mainStage) {
		tags = new ArrayList<Tag>();
		tagObsList = FXCollections.observableArrayList(tags);
		
		photos = getAllPhotos();
		
		createAlbumBtn.setDisable(photos.isEmpty());
		
		photoObsList = FXCollections.observableArrayList(photos);
		
		photoListView.setCellFactory(new Callback<ListView<Photo>, ListCell<Photo>>(){
			
			@Override
			public ListCell<Photo> call(ListView<Photo> p) {
				
				return new SearchPhotoCell();
			}
		});	
		
		photoListView.setItems(photoObsList);
		
		tagTypeColumn.setCellValueFactory(new Callback<CellDataFeatures<Tag, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Tag, String> t) {
		    	 return new SimpleStringProperty(t.getValue().getType());
		    }
		});
		
		tagValueColumn.setCellValueFactory(new Callback<CellDataFeatures<Tag, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<Tag, String> t) {
		    	 return new SimpleStringProperty(t.getValue().getValue());
		    }
		});
		
		tagDeleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		  tagDeleteColumn.setCellFactory(param -> new TableCell<Tag,Tag>() {
	            private final Button deleteButton = new Button("x");

	            @Override
	            protected void updateItem(Tag tag, boolean empty) {
	                super.updateItem(tag, empty);
	                if (tag == null) {
	                    setGraphic(null);
	                    return;
	                }
	                setGraphic(deleteButton);
	                deleteButton.setOnAction(event -> {
	                									tagObsList.remove(tag);
	                									tags.remove(tag);});
	            }
		  });
		  
		  tagTable.setItems(tagObsList);
		
		
	}
	
	@FXML
	protected void addTag() {
		String tagType = tagTypeTextField.getText().trim();
		String tagValue = tagValueTextField.getText().trim();
		
		if (tagType.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("You need to include a tag type!");
			alert.showAndWait();
			return;
		}
		if (tagValue.isEmpty()) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("You need to include a tag value!");
			alert.showAndWait();
			return;
		}
		
		tagTypeTextField.setText("");
		tagValueTextField.setText("");
		
		boolean alreadyContained = false;
		
		for (Tag t: tags) {
			if (t.getType().equals(tagType) && t.getValue().equals(tagValue)) {
				alreadyContained = true;
				break;
			}
		}
		
		Tag tempTag = new Tag(tagType, tagValue);
		
		if (alreadyContained)  {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("You can't search for duplicate tags!");
			alert.showAndWait();
			return;
		}
		
		tagObsList.add(tempTag);
		tags.add(tempTag);
	}
	
	public List<Photo> getAllPhotos() {
		List<Photo> photos = new ArrayList<Photo>();
		
		List<Album> albums = user.getAlbums();
		
		for(int a = 0; a < albums.size(); a++)
			for (Photo p: albums.get(a).getPhotos())
				if (!photos.contains(p))
					photos.add(p);
		
		return photos;
	}
	
	@FXML
	protected void createAlbumFromResults(ActionEvent e) throws IOException {
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
		   
		Album tempAlbum = null;
		
		//wait for response from add button
		Optional<Album> result = dialog.showAndWait();
		   
		// if user presses Add, add the user to the overall list
		if (result.isPresent()) {
			tempAlbum = (Album) result.get(); //store result
			user.getAlbums().add(tempAlbum);
			
			for (Photo p: photos) {
				tempAlbum.addPhoto(p);
			}
			
			UserList.writeUserList(ulist);
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
	
	@FXML
	protected void searchPhotos(ActionEvent e) {
		List<Photo> allPhotos = getAllPhotos();
		LocalDate from, to;
		
		if (tags.isEmpty() && fromDate.getValue() == null && toDate.getValue() == null) {
			return;
		}
		if (fromDate.getValue() != null && toDate.getValue() != null && fromDate.getValue().isAfter(toDate.getValue())) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Error");
			alert.setHeaderText("Error");
			alert.setContentText("Pick the dates in chronological order!");
			alert.showAndWait();
		}
			
		if (fromDate.getValue() == null)
			from = LocalDate.MIN;
		else
			from = fromDate.getValue();
		if (toDate.getValue() == null)
			to = LocalDate.MAX;
		else
			to = toDate.getValue();
		
		photos.clear();
		photoObsList.clear();
		
		for (Photo p: allPhotos) {
			if (tags.isEmpty()) {
				if (p.isWithinDateRange(from, to)) {
					photos.add(p);
					photoObsList.add(p);
				}
			}
			else {
				if (p.hasSubset(tags) && p.isWithinDateRange(from, to)) {
					photos.add(p);
					photoObsList.add(p);
				}
			}
		}
		
		createAlbumBtn.setDisable(photos.isEmpty());
		
	}
	
	@FXML 
	protected void handleLogoutButton(ActionEvent event) throws ClassNotFoundException {
    	logout(event);        
	}
	
	@FXML
	protected void backToAlbums(ActionEvent event) throws ClassNotFoundException {
		Parent parent;
   	 
		try {
				
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/NonAdminHome.fxml"));
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
	
	public void setUser(User u) {
		user = u;
	}
	
	public void setUlist(UserList ulist) {
		this.ulist = ulist;
	}
	
	private class SearchPhotoCell extends ListCell<Photo> {
			
		AnchorPane apane = new AnchorPane();
		StackPane spane = new StackPane();
		
		ImageView imageView = new ImageView();
		Label captionLabel = new Label();
		Button deletePhotoBtn = new Button("Delete");
		Button editPhotoBtn = new Button("Edit");
		Button viewPhotoBtn = new Button("View");
			
		public SearchPhotoCell() {
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
			
			AnchorPane.setLeftAnchor(viewPhotoBtn, 115.0);
			AnchorPane.setBottomAnchor(viewPhotoBtn, 0.0);
			
			deletePhotoBtn.setVisible(false);
			editPhotoBtn.setVisible(false);
			viewPhotoBtn.setVisible(false);
			apane.getChildren().addAll(spane, captionLabel, deletePhotoBtn,
					editPhotoBtn, viewPhotoBtn);
			
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
				viewPhotoBtn.setVisible(false);
			}
			if (photo != null) {
				imageView.setImage(photo.getImage());
				captionLabel.setText("Caption: " + photo.getCaption());
				deletePhotoBtn.setVisible(true);
				editPhotoBtn.setVisible(true);
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
	 		alert.setHeaderText("There's no going back!");
	 		alert.setContentText("Are you sure you want to delete this photo?");

	 		Optional<ButtonType> result = alert.showAndWait();
	 		   
	 		if(result.isPresent()) {
	 			for (Album a: user.getAlbums()) {
		 			int i = a.findIndexByPhoto(photo);
		 			a.removePhoto(i);
		 			photoObsList.remove(photo);
		 			try {
						UserList.writeUserList(ulist);
					} catch (IOException e) {
						e.printStackTrace();
					}
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
			captionTextArea.setPromptText(photo.getCaption());
			   
			GridPane grid = new GridPane();
			grid.add(captionLabel, 1, 1);
			grid.add(captionTextArea, 2, 1);
			   
			dialog.getDialogPane().setContent(grid);
			   
			ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
			dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
			   
			   Optional<Album> result = dialog.showAndWait();
			   
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

		public void viewPhoto(ActionEvent e, Photo photo) throws IOException, ClassNotFoundException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/PhotoDisplay.fxml"));
	        Parent parent = (Parent) loader.load();
	        Controller_DisplayPhoto ctrl = loader.<Controller_DisplayPhoto>getController();
	        //send user index to album list controller
	        ctrl.setPhotoIndex(photos.indexOf(photo));
	        ctrl.setPhotos(photos);
	        ctrl.setUser(user);
	        ctrl.setUlist(ulist);
	        ctrl.setKey(Controller_DisplayPhoto.CAME_FROM_PHOTO_SEARCH);
	        Scene scene = new Scene(parent);
	        
	        Stage app_stage = (Stage) ((Node) e.getSource()).getScene().getWindow();	
	        
			ctrl.start(app_stage);
	         
			app_stage.setScene(scene);
			app_stage.show();
		}
	}
}
