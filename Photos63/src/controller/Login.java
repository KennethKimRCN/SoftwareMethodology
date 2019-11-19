package controller;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.AlbumUI;
import model.User;
import model.UserList;

public class Login {
	
	@FXML
	TextField username;
	
	@FXML
	Button login;
	
	@FXML
	Button delete;

	private Stage primaryStage;
	private UserList usersObject;
	private ArrayList<User> usersList;
	
	public void start(Stage primaryStage, UserList usersObject) throws IOException{
		this.primaryStage = primaryStage;
		this.usersObject = usersObject;
		this.usersList = this.usersObject.getUsers();
	}
	
	public void login(ActionEvent e) {
		if(username.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR: MUST ENTER USERNAME");
			alert.setHeaderText(null);
			alert.setContentText("ERROR: MUST ENTER USERNAME");
			alert.showAndWait();
			return;
		}
		
		String name = username.getText().trim();
		
		for(int i = 0; i < this.usersList.size(); i++) {
			User currUser = this.usersList.get(i);
			if(name.equalsIgnoreCase(currUser.getUsername())) {
				if(currUser.isAdmin()) {
					try {
						loadAdmin(this.usersObject);
						return;
					} catch (IOException f) {
						f.printStackTrace();
					}
				}
				else{
					//non-admin user
					try {
						loadUser(currUser);
						return;
					} catch (IOException f) {
						f.printStackTrace();
					}
				}
			}
		}
		
		
	}
	
	private void loadAdmin(UserList usersObject) throws IOException {
		this.primaryStage.hide();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/admin.fxml"));
		AnchorPane root = (AnchorPane)loader.load();
		
		Stage newStage = new Stage();
		
		AdminControl Controller = loader.getController();
		Controller.start(newStage, this.primaryStage, this.usersObject);
		
		Scene scene = new Scene(root);
		newStage.setScene(scene);
		newStage.showAndWait();
	}
	
	private void loadUser(User currUser) throws IOException{
		this.username.clear();
		this.primaryStage.hide();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Player.fxml"));	
		AnchorPane root = (AnchorPane)loader.load();

		Stage newStage = new Stage();

		AlbumUIControl Controller = loader.getController();
		AlbumUI currPlayer = (AlbumUI) currUser;
		Controller.start(newStage, this.primaryStage, currPlayer);

		Scene scene = new Scene(root);
		newStage.setScene(scene);
		newStage.showAndWait();
	}
}


