package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.User;
import model.UserList;

/**
 * The Class Controller_UserList.
 *
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
public class Controller_UserList implements Controller_Logout {
	
	/** The table. */
	@FXML
	TableView<User> table;
	
	/** The username. */
	@FXML
	TextField username;
	
	/** The username column. */
	@FXML
	TableColumn<User,String> usernameColumn;
			
	/** The delete column. */
	@FXML
	TableColumn<User,User> deleteColumn;
	
	/** The obs list. */
	private ObservableList<User> obsList;
	
	/** The users. */
	private List<User> users = new ArrayList<User>();
	
	/** The ulist. */
	private UserList ulist;
	
	/**
	 * Start.
	 *
	 * @param mainStage the main stage
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void start(Stage mainStage) throws ClassNotFoundException, IOException {
		ulist = UserList.readUserList();
		users = ulist.getUsers();
		
		obsList = FXCollections.observableArrayList(users);
				
		usernameColumn.setCellValueFactory(new Callback<CellDataFeatures<User, String>, ObservableValue<String>>() {
			public ObservableValue<String> call(CellDataFeatures<User, String> u) {
				return new SimpleStringProperty(u.getValue().getUsername());
			}
		});
		 
		deleteColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		deleteColumn.setCellFactory(param -> new TableCell<User,User>() {
			private final Button deleteButton = new Button("x");

			@Override
			protected void updateItem(User user, boolean empty) {
				super.updateItem(user, empty);
				if (user == null) {
					setGraphic(null);
					return;
				}
				setGraphic(deleteButton);
				deleteButton.setOnAction(event -> {
					Alert alert = new Alert(AlertType.INFORMATION);
                	alert.setTitle("Delete User");
                	alert.setHeaderText("There's no going back!");
                	String content = "Are you sure you want to delete " + user.getUsername() + "?";

                	alert.setContentText(content);

                	Optional<ButtonType> result = alert.showAndWait();
           		   
                	if(!user.getUsername().equals("admin") && result.isPresent()){
                		obsList.remove(user);
                		users.remove(user);
                		try {
                			UserList.writeUserList(ulist);
                		} catch (Exception e) {
                			e.printStackTrace();
                		}
                	}
                	else if(user.getUsername().equals("admin")) {
                		Alert alert1 = new Alert(AlertType.INFORMATION);
	            		alert1.setTitle("Error");
	            		alert1.setHeaderText("Error");
	            		alert1.setContentText("You cannot delete admin");
	            		alert1.showAndWait();
            			}
                	});
            }
	  });

	  table.setItems(obsList);		
	
	  if (!obsList.isEmpty())
		  table.getSelectionModel().select(0);
	}
	
	/**
	 * Handle add button.
	 *
	 * @param event the event
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@FXML
	   private void handleAddButton(ActionEvent event) throws IOException {
		  int index = table.getSelectionModel().getSelectedIndex();
		  
		   Dialog<User> dialog = new Dialog<>();
		   dialog.setTitle("Create a New User");
		   dialog.setHeaderText("Add a new user to Photos!");
		   dialog.setResizable(true);
		   
		   Label usernameLabel = new Label("Username: ");
		   TextField usernameTextField = new TextField();
		   
		   GridPane grid = new GridPane();
		   grid.add(usernameLabel, 1, 1);
		   grid.add(usernameTextField, 2, 1);
		   
		   dialog.getDialogPane().setContent(grid);
		   
		   ButtonType buttonTypeOk = new ButtonType("Add", ButtonData.OK_DONE);
		   dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
		   
		   dialog.setResultConverter(new Callback<ButtonType, User>() {
			   @Override
			   public User call(ButtonType b) {
				   if (b == buttonTypeOk) {
					   
					   String error = checkFields(usernameTextField.getText());
					   
					   if (error != null) {
						   Alert alert = new Alert(AlertType.INFORMATION);
						   alert.setTitle("Error");
						   alert.setHeaderText("Error");
						   String content = error;
						   alert.setContentText(content);
						   alert.showAndWait();
						   return null;
					   }
											   
					   return new User(usernameTextField.getText().trim());
				   }
				   return null;
			   }

			
		   });
		   
		   Optional<User> result = dialog.showAndWait();
		   
		   if (result.isPresent()) {
			   User tempUser = (User) result.get();
			   obsList.add(tempUser);
			   users.add(tempUser);
			   UserList.writeUserList(ulist);
			   if (obsList.size() == 1) {
				   table.getSelectionModel().select(0);
			   }
			   else{
				   index = 0;
				   for(User s: ulist.getUsers()) {
					   
					   if(s == tempUser) {
						  table.getSelectionModel().select(index);
						  break;
					   }
					   
					   index++;
				   }
			   }
				   
		   }
	   }
	   
	   /**
   	 * Check the fields, return null if no errors found.
   	 *
   	 * @param username the username
   	 * @return the error message in string format, null if no errors
   	 */
	   private String checkFields(String username) {
		   if (username.trim().isEmpty())
			   return "Username is a required field.";
		   if (ulist.userExists(username))
			   return "This username is already taken, please try another username.";
		   else	   
			   return null;
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
}
