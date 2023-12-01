// Yuvraj Bhatia
package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class EffortLogEditorController extends Controller {

    @FXML private ComboBox<EffortLog> effortLogDropDown;
    @FXML private ComboBox<Project> projectDropDown;
    @FXML private TextField startTimeTextField,stopTimeTextField;
    @FXML private Button saveButton, deleteButton, clearButton;
    @FXML private DatePicker datePicker;

    
    private final EffortLogDAO effortLogDAO = new EffortLogDAO();
    private final ProjectDAO projectDAO = new ProjectDAO();
    
    public void initialize() {
    	initializeDropdowns();
    	setupEffortLogSelectionListener();
    	saveButton.setOnAction(event -> saveEffortLog());
        deleteButton.setOnAction(event -> deleteEffortLog());
        clearButton.setOnAction(event -> clearEffortLogs());
        }
    
    private void initializeDropdowns() {
        try {
            loadAllProjects();
            configureProjectDropdown();
           setupProjectSelectionListener();
        } catch (SQLException e) {
            showAlert("Database Error: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            showAlert("Unexpected error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    private void loadAllProjects() throws SQLException {
        List<Project> projects = projectDAO.getAllProjects();
        ObservableList<Project> projectItems = FXCollections.observableArrayList(projects);
        projectDropDown.setItems(projectItems);
    }

    private void configureProjectDropdown() {
        projectDropDown.setCellFactory(lv -> new ListCell<Project>() {
            @Override
            protected void updateItem(Project item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        });

        projectDropDown.setConverter(new StringConverter<Project>() {
            @Override
            public String toString(Project project) {
                return project == null ? "" : project.getName();
            }

            @Override
            public Project fromString(String string) {
                // No conversion from string to Project
                return null;
            }
        });
    }
    
    private void setupProjectSelectionListener() {
        projectDropDown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    updateDropdownsForSelectedProject(newValue.getId(), newValue);
                } catch (Exception e) {
                    showAlert("Error updating dropdowns: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }
    
    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    
    private void updateDropdownsForSelectedProject(int projectId, Project project) throws SQLException {
        updateEffortLogsDropdown(projectId, project);
    }

    private void updateEffortLogsDropdown(int projectId, Project project) throws SQLException {
        List<EffortLog> effortLogs = effortLogDAO.getEffortLogsForProject(projectId);
        ObservableList<EffortLog> effortLogItems = FXCollections.observableArrayList(effortLogs);
        effortLogDropDown.setItems(effortLogItems);

        // Use a cell factory to display the details of each EffortLog in the dropdown
        effortLogDropDown.setCellFactory(lv -> new ListCell<EffortLog>() {
            @Override
            protected void updateItem(EffortLog effortLog, boolean empty) {
                super.updateItem(effortLog, empty);
                if (effortLog == null || empty) {
                    setText("");
                } else {
                    try {
                        String lifeCycleStepName = projectDAO.getLifeCycleStepName(effortLog.getLifecycleStepId());
                        String effortCategoryName = projectDAO.getEffortCategoryName(effortLog.getEffortCategoryId());
                        String planName = projectDAO.getPlanName(effortLog.getPlanId());
                        setText(String.format("%d. %s (%s-%s) %s; %s; %s",
                                effortLog.getId(),
                                effortLog.getDate(),
                                effortLog.getStartTime(),
                                effortLog.getStopTime(),
                                lifeCycleStepName,
                                effortCategoryName,
                                planName));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Use a button cell to display the details of the selected EffortLog
        effortLogDropDown.setButtonCell(new ListCell<EffortLog>() {
            @Override
            protected void updateItem(EffortLog effortLog, boolean empty) {
                super.updateItem(effortLog, empty);
                if (effortLog == null || empty) {
                    setText("");
                } else {
                    try {
                        String lifeCycleStepName = projectDAO.getLifeCycleStepName(effortLog.getLifecycleStepId());
                        String effortCategoryName = projectDAO.getEffortCategoryName(effortLog.getEffortCategoryId());
                        String planName = projectDAO.getPlanName(effortLog.getPlanId());
                        setText(String.format("%d. %s (%s-%s) %s; %s; %s",
                                effortLog.getId(),
                                effortLog.getDate(),
                                effortLog.getStartTime(),
                                effortLog.getStopTime(),
                                lifeCycleStepName,
                                effortCategoryName,
                                planName));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
    
    private void setupEffortLogSelectionListener() {
        effortLogDropDown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    updateFieldsForSelectedEffortLog(newValue);
                } catch (Exception e) {
                    showAlert("Error updating fields: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }

    private void updateFieldsForSelectedEffortLog(EffortLog effortLog) {
        // Convert sql.Date to LocalDate for DatePicker
        datePicker.setValue(effortLog.getDate().toLocalDate());

        // Convert sql.Time to String for TextFields
        startTimeTextField.setText(effortLog.getStartTime().toString());
        stopTimeTextField.setText(effortLog.getStopTime().toString());
    }
    
    private void saveEffortLog() {
        EffortLog effortLog = effortLogDropDown.getSelectionModel().getSelectedItem();
        if (effortLog != null) {
            effortLog.setDate(Date.valueOf(datePicker.getValue()));
			effortLog.setStartTime(Time.valueOf(startTimeTextField.getText()));
			effortLog.setStopTime(Time.valueOf(stopTimeTextField.getText()));
			effortLogDAO.updateEffortLog(effortLog);
			showAlert("Effort log updated successfully", Alert.AlertType.INFORMATION);
        } else {
            showAlert("No effort log selected", Alert.AlertType.WARNING);
        }
        clearFields();
    }

    private void deleteEffortLog() {
        EffortLog effortLog = effortLogDropDown.getSelectionModel().getSelectedItem();
        if (effortLog != null) {
            effortLogDAO.deleteEffortLog(effortLog.getId());
			showAlert("Effort log deleted successfully", Alert.AlertType.INFORMATION);
        } else {
            showAlert("No effort log selected", Alert.AlertType.WARNING);
        }
        clearFields();
    }

    private void clearEffortLogs() {
        Project project = projectDropDown.getSelectionModel().getSelectedItem();
        if (project != null) {
            effortLogDAO.deleteEffortLogsForProject(project.getId());
			showAlert("Effort logs cleared successfully", Alert.AlertType.INFORMATION);
        } else {
            showAlert("No project selected", Alert.AlertType.WARNING);
        }
        clearFields();
    }
    
    private void clearFields() {
        datePicker.setValue(null);
        startTimeTextField.clear();
        stopTimeTextField.clear();
        effortLogDropDown.getSelectionModel().clearSelection();
        projectDropDown.getSelectionModel().clearSelection();
    }


}