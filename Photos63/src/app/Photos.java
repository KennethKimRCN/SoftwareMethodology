package app;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * @author Khangnyon Kim
 * @author Whitney Poh
 */
public class Photos extends Application {
	
	/**
	 * This is the main driver class for the software
	 * Troubleshooting: If the software does not start up, you must initialize the admin account first. 
	 */
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
		//Initialize user file so it won't crash when executed
		/*
		UserList ulist = new UserList();
		ulist.addUser(new User("admin"));
		UserList.writeUserList(ulist);
		System.out.println("Initialization successful!");
		*/
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/Login.fxml"));
			AnchorPane root = (AnchorPane)loader.load();
			Scene scene = new Scene(root, 360, 280);
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Photo63 by Khangnyon Kim and Whitney Poh");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
