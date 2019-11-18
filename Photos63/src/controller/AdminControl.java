package controller;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
}
