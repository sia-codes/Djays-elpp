package application;
	
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;


public class Main extends Application {
	
	public static User currentUser;
	public static ObservableList<Project> projects = FXCollections.observableArrayList();

	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Login");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
    public static void loadProjectsFromFile() {
       
    }
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static ObservableList<?> getProjects() {
		// TODO Auto-generated method stub
		return null;
	}

}
