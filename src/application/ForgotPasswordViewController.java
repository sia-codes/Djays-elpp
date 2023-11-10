package application;

import javafx.scene.Scene;
//import java.net.URL;
//import java.util.ResourceBundle;
//import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Stage;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.*; 

public class ForgotPasswordViewController {
	private Scene scene;
	private Stage stage;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label wrongForgotUser;
	
	@SuppressWarnings("resource")
	public UserList readFromFile() {
		try{
			FileInputStream streamIn = new FileInputStream("user.ser");
			ObjectInputStream ois = new ObjectInputStream(streamIn);
			UserList newList = (UserList) ois.readObject();
			return newList;
		} catch (IOException | ClassNotFoundException e) {
            return null;
        }
	}
	
	public void writeToFile(byte[] serializedUsers) {
        try (FileOutputStream fos = new FileOutputStream("user.ser")) {
            fos.write(serializedUsers);
        } catch (IOException e) {
            //e.printStackTrace();
        }
	}
	
	
	public void backToLogin(ActionEvent event)throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(fxmlLoader.load(), 600, 400);
		stage.setTitle("Login");
		
		stage.setScene(scene);
		stage.show();
	}
	
	public void onSubmit(ActionEvent event) throws IOException{
		String userName = usernameField.getText();
		String password = passwordField.getText();
		UserList userList = readFromFile();
		if(userList == null) {
			wrongForgotUser.setText("This user does not exist");
		}
		User user = userList.findUser(userName);
		if(user != null) {
			user.setPassword(password);
			userList.updateUser(user);
			byte[] serializedUsers = userList.serialize();
			writeToFile(serializedUsers);
		}
		else {
			wrongForgotUser.setText("This user does not exist");
			return;
		}
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(fxmlLoader.load(), 600, 400);
		stage.setTitle("Login");
		
		stage.setScene(scene);
		stage.show();
	}
	
	
	
	
}
