package app;

/**
 * This is the driver class
 */

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import controller.Login;

public class Photos extends Application {
	@Override
	public void start(Stage primaryStage) 
	throws IOException {
		FXMLLoader loader = new FXMLLoader();   
		loader.setLocation(getClass().getResource("/view/login.fxml"));
		AnchorPane root = (AnchorPane)loader.load();

		Scene scene = new Scene(root, 360, 280);
		primaryStage.setTitle("Photos63 by Khangnyon and Whitney");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show(); 
	}

	public static void main(String[] args) {
		launch(args);
	}

}
