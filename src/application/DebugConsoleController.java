package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
public class DebugConsoleController extends Controller implements Initializable{

	private UserList userList;
	private Project currentProject;
	private Defect currentDefect;
	
	@FXML
	private ComboBox<String> defectProjectDropDown;
	@FXML
	private ComboBox<String> injectDropDown;
	@FXML
	private ComboBox<String> removeDropDown;
	@FXML
	private ComboBox<String> defectCategoryDropDown;
	@FXML
	private ComboBox<String> defectNameDropDown;
	@FXML
	private Label defectError;
	@FXML
	private Label statusLabel;
	@FXML
	private TextField defectField;
	@FXML
	private TextArea defectSymArea;
	@FXML
	private TextArea defectResArea;
	@FXML
	private Hyperlink clearPageLabel;
	
	//Initializing our drop down lists with the valid information
	private ObservableList<String> projectList = FXCollections.observableArrayList(Main.currentUser.getProjectNames());
	private ObservableList<String> defectList = FXCollections.observableArrayList();
	private ObservableList<String> injectionList = FXCollections.observableArrayList("Planning","Information Gathering","Information Understanding","Verifying","Outlining","Drafting","Finalizing","Team Meeting","Coach Meeting","Stakeholder Meeting");
	private ObservableList<String> removalList = FXCollections.observableArrayList("Planning","Information Gathering","Information Understanding","Verifying","Outlining","Drafting","Finalizing","Team Meeting","Coach Meeting","Stakeholder Meeting");
	private ObservableList<String> categoryList = FXCollections.observableArrayList("Not Specified","Documentation","Syntax","Build","Assignment","Interface","Checking","Data","Function","System","Environment");
	
	
	//When we first go to this screen, the lists will be initialized with these values
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		defectProjectDropDown.setItems(projectList);
		injectDropDown.setItems(injectionList);
		removeDropDown.setItems(removalList);
		defectCategoryDropDown.setItems(categoryList);
	}
	
	//when the project combo box is changed this function is ran
	public void comboAction(ActionEvent event) {
		defectList.clear();
		currentProject = Main.currentUser.getProject(defectProjectDropDown.getValue());
		defectList.addAll(currentProject.getDefectNames());
		defectNameDropDown.setItems(defectList);
	}
	
	//updates the text displaying the status for each project when the button is pressed
	public void updateStatusText() {
		statusLabel.setText(currentDefect.getStatus());
		if(currentDefect.getStatus().equals("Open")) {
			statusLabel.setStyle("-fx-text-fill: green;");
		}
		else {
			statusLabel.setStyle("-fx-text-fill: red;");
		}
	}
	
	//updates the symptoms for a defect
	public void updateSymptoms() {
		currentDefect.setDefectSymptoms(defectSymArea.getText());
		updateUserList();
	}
	
	//updates the resolutions for a defect
	public void updateResolutions() {
		currentDefect.setDefectResolutions(defectResArea.getText());
		updateUserList();
	}
	
	//when a new defect is selected from the drop down, it updates the screen with all the information for that defect
	public void updateLogInfo(ActionEvent event) {
		defectField.setText("");
		//if the defect drop down becomes empty we want to clear the page of all defect information
		if(defectNameDropDown.getValue() == null) {
			defectSymArea.setText("");
			defectResArea.setText("");
			statusLabel.setText("");
			injectDropDown.setValue(null);
			defectCategoryDropDown.setValue(null);
			removeDropDown.setValue(null);
			return;
		}
		//else display the defect information
		currentDefect = currentProject.findDefect(defectNameDropDown.getValue());
		defectSymArea.setText(currentDefect.getDefectSymptoms());
		defectResArea.setText(currentDefect.getDefectResolutions());
		updateStatusText();
		injectDropDown.setValue(currentDefect.getStepWhenInjected());
		defectCategoryDropDown.setValue(currentDefect.getDefectCategory());
		removeDropDown.setValue(currentDefect.getStepWhenRemoved());
		
	}
	
	//deletes the defect the user has selected in the dropdown
	public void deleteDefect() {
		currentProject.removeDefect(currentDefect);
		updateUserList();
		clearPage();
		defectList.clear();
		defectList.addAll(currentProject.getDefectNames());
		defectNameDropDown.setItems(defectList);
	}
	
	//updates the step injected, step removed, and defect category fields if they are changed.
	//also will update the name of the defect if the user adds text to the defect name field
	public void updateDefect() {
		currentDefect.setDefectCategory(defectCategoryDropDown.getValue());
		if(!defectField.getText().equals("")) {
			currentDefect.setDefectName(defectField.getText());
		}
		currentDefect.setStepWhenInjected(injectDropDown.getValue());
		currentDefect.setStepWhenRemoved(removeDropDown.getValue());
		updateUserList();
		
	}
	
	//clears the dropdown and defect field after an item is deleted.
	public void clearPage() {
		defectNameDropDown.setValue(null);
		currentDefect = null;
		defectField.setText("");
	}
	
	//handles changing the status of each defect
	public void changeStatus() {
		currentDefect.setStatus();
		updateStatusText();
		updateUserList();
	}
	
	//reads the "user.ser" file which contains the file holding the users
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
	
	//writes a serialized userList object to the file
	public void writeToFile(byte[] serializedUsers) {
        try (FileOutputStream fos = new FileOutputStream("user.ser")) {
            fos.write(serializedUsers);
        } catch (IOException e) {
            //e.printStackTrace();
        }
	}
	
	//function to combine reading and writing/updating the file to prevent repetition
	public void updateUserList() {
		userList = null;
		userList = readFromFile();
		userList.updateUser(Main.currentUser);
		byte[] serializedUsers = userList.serialize();
		writeToFile(serializedUsers);
	}
	
	//adds a new defect to a specific project. 
	public void addDefect() {
		//get the project which is highlighted in the drop down box
		Project currentProject = Main.currentUser.getProject(defectProjectDropDown.getValue());
		if(currentProject == null) {
			defectError.setText("Select a Project");
			return;
		}
		//if any of the inputs are invalid, displays an error message
		String defectName = defectField.getText();
		if(defectName.equals("") || injectDropDown.getValue() == null || removeDropDown.getValue()  == null || defectCategoryDropDown.getValue() == null) {
			defectError.setText("Invalid Input");
			return;
		}
		//if the defect already exists, do not create
		if(currentProject.isDefect(defectName)) {
			defectError.setText("This defect already exists");
			return;
		}
		//adds the new defect and updates the user
		Defect newDefect = new Defect(defectName,injectDropDown.getValue(),removeDropDown.getValue(),defectCategoryDropDown.getValue());
		currentProject.addDefect(newDefect);
		updateUserList();
		defectList.clear();
		defectList.addAll(currentProject.getDefectNames());
		defectNameDropDown.setItems(defectList);
	}
	
}
