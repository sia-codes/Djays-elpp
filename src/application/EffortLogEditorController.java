package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import static application.Main.projects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;

public class EffortLogEditorController {
	@FXML
	private ComboBox<Log> logEntryComboBox;
	@FXML
	private ComboBox<Project> projectComboBox;
	@FXML
	private TextField dateTextField;
	@FXML
	private TextField startTimeTextField;
	@FXML
	private TextField stopTimeTextField;
	@FXML
	private Button saveButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button splitButton;
	@FXML
	private Button clearButton;
	@FXML
	private Button toEffortConsole;
	
	
	
	@SuppressWarnings("unchecked")
	@FXML
	public void initialize() {
	    // Populate the projectComboBox when the scene is loaded
	    projectComboBox.setItems((ObservableList<Project>) Main.getProjects()); // This should return ObservableList<Project>

	    // Listener for when a project is selected
	    projectComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
	        if (newValue != null) {
	            // Assuming Project class has a method getLogs() returning an ArrayList<Log>
	            logEntryComboBox.setItems(FXCollections.observableArrayList(newValue.getLogs()));
	        } else {
	            // Clear the log entry ComboBox if no project is selected
	            logEntryComboBox.setItems(FXCollections.observableArrayList());
	        }
	    });

	    // Initialize other UI components if necessary
	}
	
	private void updateLogEntryComboBox() {
        Project selectedProject = projectComboBox.getValue();
        if (selectedProject != null) {
            logEntryComboBox.setItems(FXCollections.observableArrayList(selectedProject.getLogs()));
        } else {
            logEntryComboBox.getItems().clear();
        }
    }
	
	 private void updateTextFields() {
	        Log selectedLog = logEntryComboBox.getValue();
	        if (selectedLog != null) {
	            dateTextField.setText(selectedLog.getDate());
	            startTimeTextField.setText(selectedLog.getStartTime());
	            stopTimeTextField.setText(selectedLog.getEndTime());
	            // ... set other fields as needed
	        } else {
	            updateTextFields();
	        }
	    }
	 
	 private void clearTextFields() {
	        dateTextField.clear();
	        startTimeTextField.clear();
	        stopTimeTextField.clear();
	        // ... clear other fields as needed
	    }

	 @FXML
	    private void handleSaveAction(ActionEvent event) {
	        Log selectedLog = logEntryComboBox.getValue();
	        if (selectedLog != null) {
	            selectedLog.setDate(dateTextField.getText());
	            selectedLog.setStartTime(startTimeTextField.getText());
	            selectedLog.setStopTime(stopTimeTextField.getText());
	            // ... update other properties as needed
	        }
	        // ... additional save logic as needed
	    }

	 private void displayError(String title, String content) {
		    Alert alert = new Alert(Alert.AlertType.ERROR);
		    alert.setTitle(title);
		    alert.setHeaderText(null); // No header
		    alert.setContentText(content);
		    alert.showAndWait();
		}

	private void saveProjectsToFile() {
		// TODO Auto-generated method stub
		
	}

	private boolean validateDateTime(String date, String startTime, String endTime) {
	    // Assuming your date and time are in formats like "yyyy-MM-dd" and "HH:mm:ss"
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

	    try {
	        dateFormat.setLenient(false);
	        dateFormat.parse(date); // Will throw ParseException if the date is invalid

	        timeFormat.setLenient(false);
	        timeFormat.parse(startTime); // Will throw ParseException if the start time is invalid
	        timeFormat.parse(endTime); // Will throw ParseException if the end time is invalid
	        
	        // Additional checks can be added here (e.g., start time must be before end time)
	        
	        return true; // The date and time strings are valid
	    } catch (ParseException e) {
	        return false; // The date and time strings are invalid
	    }
	}

	// Action for the 'Delete' button
	@FXML
	private void handleDeleteAction(ActionEvent event) {
		Project selectedProject = (Project) projectComboBox.getValue();
	    Log selectedLog = (Log) logEntryComboBox.getValue();

	    if (showConfirmationDialog("Delete Log", "Are you sure you want to delete this log entry?")) {
	        // Remove log from the project's logs list
	        selectedProject.getLogs().remove(selectedLog);

	        // Save to file
	        saveProjectsToFile();

	        // Refresh log entry ComboBox
	        logEntryComboBox.getItems().remove(selectedLog);
	    }
	}

	private boolean showConfirmationDialog(String title, String content) {
	    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, content, ButtonType.YES, ButtonType.NO);
	    alert.setTitle(title);
	    Optional<ButtonType> result = alert.showAndWait();
	    return result.isPresent() && result.get() == ButtonType.YES;
	}

	// Action for the 'Split' button
	@FXML
	private void handleSplitAction(ActionEvent event) {
		Log selectedLog = logEntryComboBox.getValue();
	    if (selectedLog == null) {
	        // Display an error message if no log is selected
	        showAlert("No Selection", "No log entry selected to split.", Alert.AlertType.ERROR);
	        return;
	    }

	    // Parse the start and stop times of the selected log
	    String startTimeStr = selectedLog.getStartTime();
	    String stopTimeStr = selectedLog.getEndTime();
	    
	    try {
	        // Assuming your Log class stores times as Strings in the format "HH:mm:ss"
	        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	        Date startTime = timeFormat.parse(startTimeStr);
	        Date stopTime = timeFormat.parse(stopTimeStr);

	        // Calculate the midpoint time
	        long midTime = (startTime.getTime() + stopTime.getTime()) / 2;
	        Date splitTime = new Date(midTime);

	        // Create two new log entries
	        Log firstHalf = new Log(selectedLog.getProjectName(),
	                                selectedLog.getLifeCycleStep(),
	                                selectedLog.getEffortCategory(),
	                                selectedLog.getPlanType(),
	                                startTimeStr,
	                                timeFormat.format(splitTime));

	        Log secondHalf = new Log(selectedLog.getProjectName(),
	                                 selectedLog.getLifeCycleStep(),
	                                 selectedLog.getEffortCategory(),
	                                 selectedLog.getPlanType(),
	                                 timeFormat.format(splitTime),
	                                 stopTimeStr);

	        // Assuming you have a method to add logs to the project
	        Project selectedProject = projectComboBox.getValue();
	        selectedProject.addLog(firstHalf);
	        selectedProject.addLog(secondHalf);

	        // Remove the original log entry and update the list
	        selectedProject.getLogs().remove(selectedLog);
	        logEntryComboBox.getItems().remove(selectedLog);
	        logEntryComboBox.getItems().addAll(firstHalf, secondHalf);

	        // Select the first half by default
	        logEntryComboBox.getSelectionModel().select(firstHalf);

	        // Update the text fields to reflect the split
	        updateTextFields();

	    } catch (ParseException e) {
	        // Handle the error case where the start or stop time couldn't be parsed
	        showAlert("Parse Error", "There was an error parsing the log entry times.", Alert.AlertType.ERROR);
	    }
	}
	
	private void showAlert(String title, String content, Alert.AlertType type) {
	    Alert alert = new Alert(type);
	    alert.setTitle(title);
	    alert.setContentText(content);
	    alert.setHeaderText(null);
	    alert.showAndWait();
	}

	// Action for the 'Clear' button
	@FXML
	private void handleClearAction(ActionEvent event) {
		Project selectedProject = projectComboBox.getValue();
	    if (selectedProject != null) {
	        // Confirm with the user
	        if (showConfirmationDialog("Clear Logs", "Are you sure you want to clear all logs for this project?")) {
	            // Clear logs
	            selectedProject.getLogs().clear();
	            logEntryComboBox.getItems().clear();
	        }
	    }
	}

	// Action for the 'toEffortConsole' button
	@FXML
	private void handleToEffortConsoleAction(ActionEvent event) {
	    // Assuming you have a method to switch scenes or load new FXML
	    loadEffortConsoleView();
	}

	private void loadEffortConsoleView() {
	    try {
	        // Load the Effort Console view
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("EffortConsolePage.fxml"));
	        Parent root = loader.load();
	        
	        // Get the current stage from an existing component via the scene
	        Stage stage = (Stage) toEffortConsole.getScene().getWindow();
	        
	        // Set the Effort Console view in the current stage
	        stage.setScene(new Scene(root));
	        stage.setTitle("Effort Console");
	    } catch (IOException e) {
	        e.printStackTrace();
	        // Handle the error, for example by displaying an alert to the user
	    }
	}


	
}
