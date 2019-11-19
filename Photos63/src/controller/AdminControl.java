package controller;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import model.AlbumUI;
import model.User;
import model.UserList;

public class AdminControl {
	private Stage primaryStage, previousStage;
	
	@FXML
	private Button logout, create;
	
	@FXML
	private TextField username;
	
	@FXML
	private ListView<User> listView;
	private ObservableList<User> obsList;
	
	private UserList usersObject;
	private ArrayList<User> userList;
	
	public void start(Stage primaryStage, Stage previousStage, UserList usersObject) throws IOException { 
		this.primaryStage = primaryStage;
		this.previousStage = previousStage;

		this.usersObject = usersObject;
		this.userList = this.usersObject.getUsers();

		this.obsList = FXCollections.observableArrayList();
		for(int i = 0; i < this.userList.size(); i++){
			this.obsList.add(this.userList.get(i));
		}
		this.listView.setItems(this.obsList.sorted());
		this.listView.getSelectionModel().selectFirst();
	}
	
	/**
	 * Logout function
	 * @param event
	 * @throws IOException
	 */
	@FXML
	void logout(ActionEvent event) throws IOException{
		Stage stage = (Stage) this.primaryStage;
		stage.close();
		
		/**
		 * Go back to log-in page;
		 */
		this.previousStage.show();
	}
	
	@FXML
	void create(ActionEvent event) {
		String newUsername = username.getText();
		//warn user if they want to create new account with empty name
		if(username.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning: Creating empty username");
			alert.setHeaderText(null);
			alert.setContentText("Creating a new user requires a name!");
			alert.showAndWait();
		}
		//check if new user is already exist
		else if(isDuplicate(newUsername)) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Warning: Existing user");
			alert.setHeaderText(null);
			alert.setContentText("The username you are trying to create already exists.");
			alert.showAndWait();
		}
		
		else {
			User newPlayer = new AlbumUI(newUsername.trim());
			//add user to our object being serialized on close.
			this.usersObject.addUser(newPlayer);

			this.obsList.add(newPlayer);
			//need to resort the list after adding a user
			this.listView.setItems(this.obsList.sorted());
			//select new user who was added to the listview
			this.listView.getSelectionModel().select(newPlayer);


			//clear the textbox after successfully adding new user to the list
			username.clear();
		}
	}
	
	/**
	 * This function checks to see if username already exists
	 * @param username
	 * @return true if username is not in the list
	 */
	private boolean isDuplicate(String username) {
		for(int i = 0; i < this.userList.size(); i++) {
			User temp = this.userList.get(i);
			if(username.trim().equalsIgnoreCase(temp.getUsername().trim())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This function can delete users in the list (deleting an admin is prohibited)
	 * 
	 */
	@FXML
	public void delete(ActionEvent e) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Delete User");
		alert.setHeaderText(null);
		alert.setContentText("Are you sure?");
		Optional<ButtonType> result = alert.showAndWait();
		
		if(result.get() == ButtonType.OK) {
			User currUser = this.listView.getSelectionModel().getSelectedItem();
			/*
			 * You cannot delete an admin
			 */
			if(currUser.isAdmin == true) {
				Alert adminalert = new Alert(AlertType.WARNING);
				adminalert.setTitle("ERROR");
				adminalert.setHeaderText(null);
				adminalert.setContentText("You cannot delete yourself");
				adminalert.showAndWait();
			}
			else {
				this.userList.remove(this.listView.getSelectionModel().getSelectedItem());
				this.obsList.setAll(this.userList);
				this.listView.setItems(this.obsList.sorted());
				this.listView.getSelectionModel().select(0);
			}
		}
	}
}
