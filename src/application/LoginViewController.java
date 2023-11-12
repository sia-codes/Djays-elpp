package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Stage;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import java.io.FileOutputStream;
//import java.net.URL;
//import java.util.ResourceBundle;
//import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.io.*; 
//import java.util.*; 
public class LoginViewController {
	private Scene scene;
	private Stage stage;
	//private Login login = new Login();
	private UserList userList;
	
	
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML 
	private Label errorLabel;

	
	

	public void writeToFile(byte[] serializedUsers) {
        try (FileOutputStream fos = new FileOutputStream("user" + ".ser")) {
            fos.write(serializedUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("resource")
	public UserList readFromFile() {
		try{
			FileInputStream streamIn = new FileInputStream("user.ser");
			ObjectInputStream ois = new ObjectInputStream(streamIn);
			UserList newList = (UserList) ois.readObject();
			return newList;
		} catch (IOException | ClassNotFoundException e) {
			return null;
			//e.printStackTrace();
            //return null;
        }
	}
	
	@FXML
	protected void onCreateUserPressed() {
		String userName = usernameField.getText();
        String password = passwordField.getText();
        userList = null;
        userList = readFromFile();
        if(userList == null) {
        	userList = new UserList();
        }
        User newUser = new User(userName, password);
        if(userList.findUser(userName) == null) {
        	userList.addUser(newUser);
        	errorLabel.setText("User successfully added!");
        }
        else {
        	errorLabel.setText("This user already exists!");
        	return;
        }
        byte[] serializedUsers = userList.serialize();
        writeToFile(serializedUsers);
	}
        //try {
        	
            //App.user = newUser;
            // Load the main layout
            //App.loadMainLayout((Stage) FullNameField.getScene().getWindow());
        //} catch (IOException e) {
            //e.printStackTrace();
        //}
	//}
	public void submitLogin(ActionEvent event) throws IOException
	{
		String userName = usernameField.getText();
		String password = passwordField.getText();
		userList = readFromFile();
		if(userList == null) {
			errorLabel.setText("This user does not exist");
			return;
		}
		User currentUser = userList.findUser(userName);
		if(currentUser == null) {
			errorLabel.setText("This user does not exist");
			return;
		}
		//If the password matches user's password proceed with login
		if(currentUser.getPassword().equals(password)) {
			Main.currentUser = currentUser;
			//initializeFields();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EffortConsolePage.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(fxmlLoader.load(), 600, 400);
			stage.setTitle("Effort Console");
			stage.setScene(scene);
			stage.show();
		}
		else {
			errorLabel.setText("Incorrect password");
		}
		
	}
	
	public void toForgotPassword(ActionEvent event)throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ForgotPasswordPage.fxml"));
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(fxmlLoader.load(), 600, 400);
		stage.setTitle("Forgot Password");
		
		stage.setScene(scene);
		stage.show();
	}
	
	

}
