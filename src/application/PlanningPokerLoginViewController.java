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
public class PlanningPokerLoginViewController extends Controller {
	private Scene scene;
	private Stage stage;
	//private Login login = new Login();
	private SessionList sessionList;
	
	
	@FXML
	private TextField sessionNameField;
	@FXML
	private PasswordField passwordField;
	@FXML 
	private Label errorLabel;

	
	

	public void writeToFile(byte[] serializedSessions) {
        try (FileOutputStream fos = new FileOutputStream("session" + ".ser")) {
            fos.write(serializedSessions);
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	@SuppressWarnings("resource")
	public SessionList readFromFile() {
		try{
			FileInputStream streamIn = new FileInputStream("session.ser");
			ObjectInputStream ois = new ObjectInputStream(streamIn);
			SessionList newList = (SessionList) ois.readObject();
			return newList;
		} catch (IOException | ClassNotFoundException e) {
			return null;
			//e.printStackTrace();
            //return null;
        }
	}
	
	@FXML
	protected void onCreateSessionPressed() {
	    String sessionName = sessionNameField.getText();
	    String password = passwordField.getText();
	    sessionList = null;
	    sessionList = readFromFile();
	    if(sessionList == null) {
	        sessionList = new SessionList();
	    }

	    if (!password.matches("\\d{5}")) {
	        errorLabel.setText("PIN must be exactly 5 digits and contain no special characters or letters!");
	        return;
	    }

	    Session newSession = new Session(sessionName, password);
	    if(sessionList.findSession(sessionName) == null) {
	        sessionList.addSession(newSession);
	        errorLabel.setText("Session successfully added!");
	    }
	    else {
	        errorLabel.setText("This session already exists!");
	        return;
	    }
	    byte[] serializedSessions = sessionList.serialize();
	    writeToFile(serializedSessions);
	}

	public void submitLogin(ActionEvent event) throws IOException
	{
		String sessionName = sessionNameField.getText();
		String password = passwordField.getText();
		sessionList = readFromFile();
		if(sessionList == null) {
			errorLabel.setText("This session does not exist");
			return;
		}
		Session currentSession = sessionList.findSession(sessionName);
		if(currentSession == null) {
			errorLabel.setText("This session does not exist");
			return;
		}
		
		if(currentSession.getPassword().equals(password)) {
//			Main.currentSession = currentSession;
			//initializeFields();
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PlanningPokerPage.fxml"));
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(fxmlLoader.load(), 600, 400);
			stage.setTitle("Login Info");
			stage.setScene(scene);
			stage.show();
		}
		else {
			errorLabel.setText("Incorrect PIN");
		}
		
	}
	

}