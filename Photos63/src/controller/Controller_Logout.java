package controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Interface Controller_Logout.
 *
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
public interface Controller_Logout {

	/**
	 * handleLogoutButton
	 * Logs out of the user on logout button click.
	 *
	 * @param event 	logout button click
	 * @throws ClassNotFoundException Exception for switching scenes
	 */
	default void logout(ActionEvent event) throws ClassNotFoundException {
    	Parent parent;
    	 
		try {
						
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
			parent = (Parent) loader.load();
		
			Scene scene = new Scene(parent);
							
			Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();			             
			app_stage.setScene(scene);
			app_stage.show();  
						
			 
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}         
         
	}
}
