package controller;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Album;
import model.Photo;
import model.Tag;
import model.User;
import model.UserList;

/**
 * The Class Controller_DisplayPhoto.
 *
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
public class Controller_DisplayPhoto implements Controller_Logout{
	
	/** The Constant CAME_FROM_PHOTO_SEARCH. */
	public final static int CAME_FROM_ALBUM_CONTENT = 0,
			CAME_FROM_PHOTO_SEARCH = 1;
	
	/** The tag list view. */
	@FXML
	ListView<Tag> tagListView;
	
	/** The photo image view. */
	@FXML
	ImageView photoImageView;
	
	/** The photo date text. */
	@FXML
	Text captionText, photoDateText;
	
	/** The next photo btn. */
	@FXML
	Button previousPhotoBtn, nextPhotoBtn;
	
	/** The obs list. */
	private ObservableList<Tag> obsList;
	
	/** The photo index. */
	private int photoIndex;
	
	/** The album. */
	private Album album;
	
	/** The user. */
	private User user;
	
	/** The photos. */
	private List<Photo> photos;
	
	/** The user list. */
	private UserList ulist;
	
	/** The key. */
	private int key;
	
	/**
	 * Start.
	 *
	 * @param mainStage the main stage
	 */
	public void start(Stage mainStage) {
		if (key == CAME_FROM_ALBUM_CONTENT)
			photos = album.getPhotos();
		updatePhotoDetails();
	}
	
	/**
	 * Handle logout button.
	 *
	 * @param event the event
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML 
	protected void handleLogoutButton(ActionEvent event) throws ClassNotFoundException {
    	logout(event);          
	}
	
	/**
	 * Back.
	 *
	 * @param event the event
	 * @throws ClassNotFoundException the class not found exception
	 */
	@FXML
	protected void back(ActionEvent event) throws ClassNotFoundException {
		Parent parent;
	   	 
		try {
			if (key == CAME_FROM_ALBUM_CONTENT) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Photos.fxml"));
		        parent = (Parent) loader.load();
		        Controller_PhotoList ctrl = loader.<Controller_PhotoList>getController();
		        //send user index to album list controller
		        ctrl.setUser(user);
		        ctrl.setAlbum(album);
		        ctrl.setUlist(ulist);
		        Scene scene = new Scene(parent);
		        
		        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
		        
				ctrl.start(app_stage);
		         
				app_stage.setScene(scene);
				app_stage.show();
			}
			if (key == CAME_FROM_PHOTO_SEARCH) {
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Search.fxml"));
		        parent = (Parent) loader.load();
		        Controller_SearchPhoto ctrl = loader.<Controller_SearchPhoto>getController();
		        //send user index to album list controller
		        ctrl.setUser(user);
		        ctrl.setUlist(ulist);
		        Scene scene = new Scene(parent);
		        
		        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
		        
				ctrl.start(app_stage);
		         
				app_stage.setScene(scene);
				app_stage.show();
			}
		
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * Adds the tag.
	 *
	 * @param event the event
	 */
	@FXML
	protected void addTag(ActionEvent event) {
		
		Dialog<Album> dialog = new Dialog<>();
		dialog.setTitle("Add Tag");
		dialog.setHeaderText("Add a new Tag to this photo");
		dialog.setResizable(true);
		   
		Label keyLabel = new Label("Tag Key: ");
		TextField keyTextField = new TextField();
		Label valueLabel = new Label("Tag Value: ");
		TextField valueTextField = new TextField();
		
		GridPane grid = new GridPane();
		grid.add(keyLabel, 1, 1);
		grid.add(keyTextField, 2, 1);
		grid.add(valueLabel, 1, 2);
		grid.add(valueTextField, 2, 2);
		
		dialog.getDialogPane().setContent(grid);
		   
		ButtonType buttonTypeOk = new ButtonType("Add", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		
		Optional<Album> result = dialog.showAndWait();
		
		String error = checkFields(keyTextField.getText(), valueTextField.getText());
			if (result.isPresent()) {
				if (error != null) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Error");
					alert.setHeaderText("Error");
					String content = error;
					alert.setContentText(content);
					alert.showAndWait();
				}
				else
				{	
				Photo p = photos.get(photoIndex);
				p.addTag(keyTextField.getText(),valueTextField.getText());			
				updatePhotoDetails();  
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

	/**
	 * Check fields.
	 *
	 * @param type the type
	 * @param value the value
	 * @return the string
	 */
	private String checkFields(String type, String value) {
		for(Tag t : photos.get(photoIndex).getTags())
		{
			if(t.getType().equals(type) && t.getValue().equals(value))
			return "This tag already exists for this photo";
		}
		if (type.equals("")|| value.equals(""))
			return "You must enter both a type and value";
		
		return null;
	}
	
	/**
	 * Previous photo.
	 *
	 * @param event the event
	 */
	@FXML
	protected void previousPhoto(ActionEvent event) {
		photoIndex--;
		updatePhotoDetails();
	}
	
	/**
	 * Next photo.
	 *
	 * @param event the event
	 */
	@FXML
	protected void nextPhoto(ActionEvent event) {
		photoIndex++;
		updatePhotoDetails();
	}
	
	/**
	 * Update photo details.
	 */
	public void updatePhotoDetails() {
		photoImageView.setImage(photos.get(photoIndex).getImage());
		captionText.setText("Caption: " + photos.get(photoIndex).getCaption());
		photoDateText.setText("Photo Date: " + photos.get(photoIndex).getDate());
		obsList = FXCollections.observableArrayList(photos.get(photoIndex).getTags());
		
		tagListView.setCellFactory(new Callback<ListView<Tag>, ListCell<Tag>>(){
			
			@Override
			public ListCell<Tag> call(ListView<Tag> t) {
				
				return new TagCell();
			}
		});	
		
		tagListView.setItems(obsList);
		
		previousPhotoBtn.setDisable(photoIndex == 0);
		nextPhotoBtn.setDisable(photoIndex == photos.size()-1);
	}
	
	/**
	 * Sets the album.
	 *
	 * @param a the new album
	 */
	public void setAlbum(Album a) {
		album = a;
	}
	
	/**
	 * Sets the user.
	 *
	 * @param u the new user
	 */
	public void setUser(User u) {
		user = u;
	}
	
	/**
	 * Sets the photo index.
	 *
	 * @param i the new photo index
	 */
	public void setPhotoIndex(int i) {
		photoIndex = i;
	}
	
	/**
	 * Sets the ulist.
	 *
	 * @param ulist the new ulist
	 */
	public void setUlist(UserList ulist) {
		this.ulist = ulist;
	}

	/**
	 * Sets the photos.
	 *
	 * @param photos the new photos
	 */
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	
	/**
	 * Sets the key.
	 *
	 * @param key the new key
	 */
	public void setKey(int key) {
		this.key = key;
	}
	
	/**
	 * The Class TagCell.
	 */
	private class TagCell extends ListCell<Tag> {
			
		/** The apane. */
		AnchorPane apane = new AnchorPane();
		
		/** The tag label. */
		Label tagLabel = new Label();
		
		/** The delete tag btn. */
		Button deleteTagBtn = new Button("Delete");
		
		/** The edit tag btn. */
		Button editTagBtn = new Button("Edit");
			
		/**
		 * Instantiates a new tag cell.
		 */
		public TagCell() {
			super();
			
			AnchorPane.setLeftAnchor(tagLabel, 0.0);
			AnchorPane.setTopAnchor(tagLabel, 0.0);
			
			AnchorPane.setRightAnchor(deleteTagBtn, 0.0);
			AnchorPane.setTopAnchor(deleteTagBtn, 0.0);
			
			AnchorPane.setRightAnchor(editTagBtn, 50.0);
			AnchorPane.setTopAnchor(editTagBtn, 0.0);
			
			tagLabel.setMaxWidth(200.0);
			
			deleteTagBtn.setVisible(false);
			editTagBtn.setVisible(false);
			
			apane.getChildren().addAll(tagLabel, deleteTagBtn, editTagBtn);
			
			setGraphic(apane);
		}
			
		/**
		 * Update item.
		 *
		 * @param tag the tag
		 * @param empty the empty
		 */
		@Override
		public void updateItem(Tag tag, boolean empty) {
			super.updateItem(tag, empty);
			setText(null);
			
			if (tag != null) {
				tagLabel.setText(tag.toString());
				deleteTagBtn.setVisible(true);
				editTagBtn.setVisible(true);
				
				deleteTagBtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						deleteTag(e, tag);
					}
				});
				
				editTagBtn.setOnAction(new EventHandler<ActionEvent>() {
					@Override public void handle(ActionEvent e) {
						editTag(e, tag);
					}
				});
				
			}	
		}
	}
		
		/**
		 * Delete tag.
		 *
		 * @param e the e
		 * @param tag the tag
		 */
		public void deleteTag(ActionEvent e, Tag tag) {
			Alert alert = 
	 				   new Alert(AlertType.INFORMATION);
	 		   alert.setTitle("Delete Tag");
	 		   alert.setHeaderText(
	 				   "There's no going back!");

	 		   String content = "Are you sure you want to delete the tag " + tag.getType() + " : " + tag.getValue()+"?";

	 		   alert.setContentText(content);

	 		   Optional<ButtonType> result = alert.showAndWait();
	 		   
	 		  if(result.isPresent())
	 		  {
	 			 Photo p = photos.get(photoIndex);
	 			 for(int t = 0; t< p.getTags().size(); t++)
	 			 {
	 				 if(p.getTag(t).equals(tag))
	 				 {
	 					 p.removeTag(t);
	 					 updatePhotoDetails();
	 					try {
	 						UserList.writeUserList(ulist);
	 					} catch (Exception i) {
	 						i.printStackTrace();
	 					}
	 				 }
	 			 }	 		 
	 		  }
		}
		
		/**
		 * Edits the tag.
		 *
		 * @param e the e
		 * @param tag the tag
		 */
		public void editTag(ActionEvent e, Tag tag) {
			 Dialog<User> dialog = new Dialog<>();
			   dialog.setTitle("Edit Tag");
			   dialog.setHeaderText("Change the key or value of the tag");
			   dialog.setResizable(true);
			   
			   Label keyLabel = new Label("Tag Key: ");
				TextField keyTextField = new TextField();
				keyTextField.setText(tag.getType());
				Label valueLabel = new Label("Tag Value: ");
				TextField valueTextField = new TextField();
				valueTextField.setText(tag.getValue());
				
				GridPane grid = new GridPane();
				grid.add(keyLabel, 1, 1);
				grid.add(keyTextField, 2, 1);
				grid.add(valueLabel, 1, 2);
				grid.add(valueTextField, 2, 2);
				
				dialog.getDialogPane().setContent(grid);
				   
				ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
				
				Optional<User> result = dialog.showAndWait();
				
				String error = checkFields(keyTextField.getText(), valueTextField.getText());
					if (result.isPresent()) {
						if (error != null) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Error");
							alert.setHeaderText("Error");
							String content = error;
							alert.setContentText(content);
							alert.showAndWait();
						}
						else
						{
							Photo p = photos.get(photoIndex);
							for(int t = 0; t< p.getTags().size(); t++)
				 			 {
				 				 if(p.getTag(t).equals(tag))
				 				 {
				 					 p.editTag(t, keyTextField.getText(), valueTextField.getText());
				 					 updatePhotoDetails();
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
					}
		}
}
