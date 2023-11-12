package application;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

//This class will handle actions in the effort log console
public class DisplayViewController extends Controller implements Initializable{
	
	private static LocalDateTime start;
	private static LocalDateTime end;
	private static String startTime;
	private static String endTime;
	private UserList userList;
	private boolean isActivityRunning;
	@FXML
	private Label invalidLog;
	@FXML
	private Pane clockPane;
	@FXML 
	private Label clockLabel;
	@FXML
	private TextField projectField;
	@FXML
	private TextField lifeCycleField;
	@FXML
	private TextField categoryField;
	@FXML
	private TextField planField;
	@FXML
	private ComboBox<String> projectDropDown;
	@FXML
	private ComboBox<String> lifeCycleDropDown;
	@FXML
	private ComboBox<String> categoryDropDown;
	@FXML
	private ComboBox<String> planDropDown;
	
	//Our lists that include the items for the dropdowns
	private ObservableList<String> projectList = FXCollections.observableArrayList(Main.currentUser.getProjectNames());
	private ObservableList<String> lifeCycleList = FXCollections.observableArrayList("Planning","Team Gathering","Verifying","Outlining","Drafting","Finalzing");
	private ObservableList<String> categoryList = FXCollections.observableArrayList("Plans","Deliverables","Interruptions","Defects","Others");
	private ObservableList<String> planList = FXCollections.observableArrayList("Project Plan", "Risk Management Plan", "Conceptual Design Plan", "Detailed Design Plan", "Implementation Plan");
	
	//starts the clock when the activity is started
	public void startClock() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
		isActivityRunning = true;
		start = LocalDateTime.now();
		startTime = start.format(formatter);
		clockLabel.setText("Clock is Running");
		clockPane.setStyle("-fx-background-color: green;");
		
	}
	
	//function to initialize the drop downs when this screen is loaded
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		projectDropDown.setItems(projectList);
		lifeCycleDropDown.setItems(lifeCycleList);
		categoryDropDown.setItems(categoryList);
		planDropDown.setItems(planList);
	}
	
	//when we update a user such as adding a log, we will need to update the list which holds all of our users
	//which is kept in a file so it persists so we will read the file, modify the list, the rewrite the file.
	public void updateUserList() {
		userList = null;
		userList = readFromFile();
		userList.updateUser(Main.currentUser);
		byte[] serializedUsers = userList.serialize();
		writeToFile(serializedUsers);
	}
	
	//add a project for an individual user; this gets added to the project dropdown
	public void addProject() {
		String newProject = projectField.getText();
		Main.currentUser.createProject(newProject);
		updateUserList();
		//userList = null;
		//userList = readFromFile();
		//userList.updateUser(Main.currentUser);
		
		//User test = userList.findUser(Main.currentUser.getUserName());
		//test.printProjectNames();
		//byte[] serializedUsers = userList.serialize();
		//writeToFile(serializedUsers);
		//Main.currentUser.printProjectNames();
		projectList.addAll(newProject);
		//projectDropDown.addAll(Main.currentUser.getProjectNames().toArray());
	}
	
	//TODO: implement this -> might just save it globally so all users have access to it
	public void addLifeCycle() {
		String newCycle = lifeCycleField.getText();
		lifeCycleList.addAll(newCycle);
	}
	//TODO: implement this
	public void addCategory() {
		String newCategory = categoryField.getText();
		categoryList.addAll(newCategory);
	}
	//TODO: implement this
	public void addPlan() {
		String newPlan = planField.getText();
		planList.addAll(newPlan);
	}
	
	//This function creates a log and saves it
	public void createLog()
	{
		if(!isActivityRunning) {
			invalidLog.setText("You must start the activity");
			return;
		}
		if(projectDropDown.getValue() == null || lifeCycleDropDown.getValue() == null || categoryDropDown.getValue() == null || planDropDown.getValue() == null) {
		invalidLog.setText("All Fields MUST Be Selected!");
			return;
		}
		clockLabel.setText("Clock is Stopped");
		clockPane.setStyle("-fx-background-color: red;");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss a");
		isActivityRunning = false;
		end = LocalDateTime.now();
		endTime = end.format(formatter);
		Log newLog = new Log(projectDropDown.getValue(),lifeCycleDropDown.getValue(),categoryDropDown.getValue(),planDropDown.getValue(),startTime,endTime);
		Project currentProject = Main.currentUser.getProject(projectDropDown.getValue());
		currentProject.addLog(newLog);
		updateUserList();
	}
	
	//reads from the user file
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

}
