package app;
/**
 * @author Khangnyon Kim
 * @author Whitney Poh
 * 
 * This is the driver class
 */

import java.io.FileNotFoundException;
import java.io.IOException;

import controller.Login;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Admin;
import model.User;
//import controller.Login;
import model.UserList;

public class Photos extends Application {
	private UserList usersObject;
	/**
	 * Launcher
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		//on program execution, try to load serialized Users
		this.usersObject = this.getUsersContainer();

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Login.fxml"));

		AnchorPane root = (AnchorPane)loader.load();

		Login Controller = loader.getController();
		Controller.start(primaryStage, this.usersObject);

		Scene scene = new Scene(root, 360, 280);
		primaryStage.setTitle("Photos63 by Khangynon Kim and Whitney Poh");
		primaryStage.setResizable(false);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * Main driver
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void stop() {
		try {
			UserList.writeUserList(this.usersObject);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private UserList getUsersContainer() {
		UserList usersContainer = null;
		try {
			usersContainer = UserList.readUserList();
		} catch (FileNotFoundException e) {
			usersContainer = new UserList();
			User adminUser = new Admin();
			usersContainer.addUser(adminUser);
			try {
				UserList.writeUserList(usersContainer);
			} catch (IOException f) {
				f.printStackTrace();
			}
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();	
		}
		return usersContainer;
	}

}
