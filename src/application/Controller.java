package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

//This Controller class will hold the functions for the navbar and each Controller class will inherit these methods
//Since they will all contain the navbar
public class Controller {
	private Scene scene;
	private Stage stage;
	//private Login login = new Login();
	
	@FXML
	private Label userLabel;
	//@FXML
	//private PasswordField passwordField;
	

	//function to switch to effort console screen
	public void toEffortConsole(ActionEvent event) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EffortConsolePage.fxml"));
		//login.setName(usernameField.getText());
		//login.setPassword(passwordField.getText());
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(fxmlLoader.load(), 600, 400);
		stage.setTitle("Effort Console");
		
		//DisplayViewController control = fxmlLoader.getController();
		//control.setLogin(login);
		stage.setScene(scene);
		stage.show();
		
		
	}
	
	//function to switch to defect console screen
	public void toDefectConsole(ActionEvent event) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("DefectConsolePage.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(fxmlLoader.load(), 600, 400);
		stage.setTitle("Defect Console");
		
		stage.setScene(scene);
		stage.show();
		
		
	}
	
	//function to switch to view logs screen
	public void toViewLogs(ActionEvent event) throws IOException
	{
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewLogsPage.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(fxmlLoader.load(), 600, 400);
		stage.setTitle("View Logs");
		
		stage.setScene(scene);
		stage.show();
		
		
	}
	
	//function to log out as the current user and return to the log in screen
	public void logOut(ActionEvent event)throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(fxmlLoader.load(), 600, 400);
		stage.setTitle("Login");
		
		stage.setScene(scene);
		stage.show();
	}

}