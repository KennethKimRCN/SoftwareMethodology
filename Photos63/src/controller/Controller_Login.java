package controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.UserList;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
public class Controller_Login {
	
	@FXML TextField usernameField;
	@FXML Button login;
	@FXML Button exit;
	@FXML Text errorDisplay;
		
	@FXML
	public void login(ActionEvent event) throws ClassNotFoundException{
		String username = usernameField.getText().trim();
		Parent parent;
		if(usernameField.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("ERROR: MUST ENTER USERNAME");
			alert.setHeaderText(null);
			alert.setContentText("ERROR: MUST ENTER USERNAME");
			alert.showAndWait();
			return;
		}
		UserList ulist = null;
		try {
			ulist = UserList.readUserList();
			
		}catch (IOException e) {
			e.printStackTrace();
		}
		try {
			
			if(ulist.userExists(username)) {
				if(username.equals("admin")){	 
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Admin.fxml"));
					parent = (Parent) loader.load();
					        
					Controller_UserList ctrl = loader.getController();
					Scene scene = new Scene(parent);
								
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
				                
					ctrl.start(app_stage);
				             
				    app_stage.setScene(scene);
				    app_stage.show();  
							
				}
				else{
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Home.fxml"));
					parent = (Parent) loader.load();
					Controller_AlbumList ctrl = loader.<Controller_AlbumList>getController();
					ctrl.setUlist(ulist);
					ctrl.setUser(ulist.getUsername(username));
					Scene scene = new Scene(parent);
					        
					Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();	
				                
					ctrl.start(app_stage);
				             
				    app_stage.setScene(scene);
				    app_stage.show();  
				}
					
			}
			else
				errorDisplay.setText("User does not exist");
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	@FXML
	public void exit(ActionEvent e) {
		exit.setOnAction(i -> Platform.exit());
	}
}


