package controller;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Photo;

public class DisplayPhoto {
	@FXML
	private StackPane photoWrapper;
	public void start(Stage primaryStage, Photo activePhoto) {
		Image imgFile = new Image(activePhoto.getPath(), 500, 300, true, true);
		ImageView imgView = new ImageView(imgFile);
		
		photoWrapper.getChildren().clear();
		photoWrapper.getChildren().add(imgView);
	}
}
