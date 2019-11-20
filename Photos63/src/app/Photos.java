package app;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import model.Photo;
import model.User;
import model.UserList;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

/**
 * The Class Photos.
 *
 * @author Khangnyon Kim
 * @author Whitney Poh
 */
public class Photos extends Application {
	
	/**
	 * This is the main driver class for the software
	 * Troubleshooting: If the software does not start up, you must initialize the admin account first.
	 *
	 * @param primaryStage the primary stage
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	@Override
	public void start(Stage primaryStage) throws IOException, ClassNotFoundException {
		/**
		 * Warning:
		 * Un-commenting the code block below will hard reset the user list.
		 */
		/*
		UserList ulist = new UserList();
		ulist.addUser(new User("admin"));
		ulist.addUser(new User("stock"));
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
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
}
