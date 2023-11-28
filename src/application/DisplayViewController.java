package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;	
import java.util.List;
import java.util.ResourceBundle;
import java.sql.Date;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Controller for displaying views in the application.
 */
public class DisplayViewController extends Controller implements Initializable {
    private LocalDateTime start, end;
    private String startTime, endTime;
    private boolean isActivityRunning;

    @FXML private Label invalidLog;
    @FXML private Pane clockPane;
    @FXML private Label clockLabel;
    @FXML private ComboBox<Project> projectDropDown;
    @FXML private ComboBox<String> lifeCycleDropDown, categoryDropDown, planDropDown;

    private final EffortLogDAO effortLogDAO = new EffortLogDAO();   
    private final ProjectDAO projectDAO = new ProjectDAO();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeDropdowns();
        //testAddEffortLogToDatabase();
    }
    


//    private void testAddEffortLogToDatabase() {
//        try {
//            // Hardcoded test data
//            LocalDate date = LocalDate.now();
//            Time startTimeSql = Time.valueOf("08:00:00");
//            Time stopTimeSql = Time.valueOf("10:00:00");
//            Time deltaTimeSql = new Time(stopTimeSql.getTime() - startTimeSql.getTime());
//            int projectId = 1; // Assume project with ID 1 exists
//            int lifecycleStepId = 1; // Assume lifecycle step with ID 1 exists
//            int effortCategoryId = 1; // Assume effort category with ID 1 exists
//            int planId = 1; // Assume plan with ID 1 exists
//
//            // Create EffortLog object
//            EffortLog testLog = new EffortLog(0, Date.valueOf(date), startTimeSql, stopTimeSql, 
//                                              projectId, lifecycleStepId, effortCategoryId, planId);
//
//            // Add the log to the database using EffortLogDAO
//            if (effortLogDAO.addEffortLog(testLog)) {
//                System.out.println("Test log added successfully.");
//            } else {
//                System.out.println("Failed to add test log.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Unexpected error: " + e.getMessage());
//        }
//    }
    
    
    
    
    

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
                    updateDropdownsForSelectedProject(newValue.getId());
                } catch (Exception e) {
                    showAlert("Error updating dropdowns: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            }
        });
    }

    private void updateDropdownsForSelectedProject(int projectId) throws SQLException {
        updateLifecycleStepsDropdown(projectId);
        updateEffortCategoriesDropdown(projectId);
        updatePlansDropdown(projectId);
    }

    private void updateLifecycleStepsDropdown(int projectId) throws SQLException {
        List<String> lifecycles = projectDAO.getLifecyclesForProject(projectId);
        ObservableList<String> lifecycleItems = FXCollections.observableArrayList(lifecycles);
        lifeCycleDropDown.setItems(lifecycleItems);
    }

    private void updateEffortCategoriesDropdown(int projectId) throws SQLException {
        List<String> effortCategories = projectDAO.getEffortCategoriesForProject(projectId);
        ObservableList<String> effortCategoryItems = FXCollections.observableArrayList(effortCategories);
        categoryDropDown.setItems(effortCategoryItems);
    }

    private void updatePlansDropdown(int projectId) throws SQLException {
        List<String> plans = projectDAO.getPlansForProject(projectId);
        ObservableList<String> planItems = FXCollections.observableArrayList(plans);
        planDropDown.setItems(planItems);
    }
    private void updateClockUI(boolean isRunning) {
        clockLabel.setText(isRunning ? "Clock is Running" : "Clock is Stopped");
        clockPane.setStyle(isRunning ? "-fx-background-color: green;" : "-fx-background-color: red;");
    }
    
    public void startClock() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        isActivityRunning = true;
        start = LocalDateTime.now();
        startTime = start.format(formatter);
        updateClockUI(true);
        System.out.println("Start Time: " + startTime); // For debugging

        // Add this line to see if the method is called
        System.out.println("startClock() method called");
    }
    
    public void stopClock() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        end = LocalDateTime.now();
        endTime = end.format(formatter); // Ensure endTime is set here
        updateClockUI(false);
        System.out.println("End Time: " + endTime); // For debugging

        // Add this line to see if the method is called
        System.out.println("stopClock() method called");

        createAndAddLog(); // Call createAndAddLog() here to ensure endTime is captured before log creation

        isActivityRunning = false; // Move this line to after createAndAddLog
    }

    private void createAndAddLog() {
        if (!validateLogCreation()) {
            return;
        }

        try {
            LocalDate date = LocalDate.now();
            LocalTime startTimeLocal = start.toLocalTime();
            LocalTime stopTimeLocal = end.toLocalTime();
            double deltaTimeInSeconds = calculateDeltaTimeInSeconds(startTimeLocal, stopTimeLocal);

            System.out.println("Start Local Time: " + startTimeLocal);
            System.out.println("Stop Local Time: " + stopTimeLocal);
            System.out.println("Delta Time (Seconds): " + deltaTimeInSeconds);

            Project selectedProject = projectDropDown.getValue();
            int projectId = selectedProject.getId();
            int lifecycleStepId = projectDAO.findLifecycleStepIdByName(lifeCycleDropDown.getValue(), projectId);
            int effortCategoryId = projectDAO.findEffortCategoryIdByName(categoryDropDown.getValue(), projectId);
            int planId = projectDAO.findPlanIdByName(planDropDown.getValue(), projectId);

            System.out.println("projectId: " + projectId);
            System.out.println("lifecycleStepId: " + lifecycleStepId);
            System.out.println("effortCategoryId: " + effortCategoryId);
            System.out.println("planId: " + planId);
            
            EffortLog log = new EffortLog(0, Date.valueOf(date), Time.valueOf(startTimeLocal), Time.valueOf(stopTimeLocal),
                                          projectId, lifecycleStepId, effortCategoryId, planId);

            if (effortLogDAO.addEffortLog(log)) {
                System.out.println("Log added successfully.");
                showAlert("Log added successfully.", Alert.AlertType.INFORMATION);
            } else {
                System.out.println("Failed to add log.");
                showAlert("Failed to add log.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for detailed error information
            showAlert("Database error: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for detailed error information
            showAlert("Unexpected error: " + e.getMessage(), Alert.AlertType.ERROR);
        } finally {
            resetLogFields();
        }
    }


 // Updated validateLogCreation method
    private boolean validateLogCreation() {
        if (!isActivityRunning || start == null || end == null) {
            showAlert("The activity has not started or ended properly.", Alert.AlertType.WARNING);
            return false;
        }
        if (projectDropDown.getValue() == null || lifeCycleDropDown.getValue() == null ||
            categoryDropDown.getValue() == null || planDropDown.getValue() == null) {
            showAlert("All Fields MUST Be Selected!", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    // Calculate deltaTimeInSeconds
    private double calculateDeltaTimeInSeconds(LocalTime startTime, LocalTime stopTime) {
        return Duration.between(startTime, stopTime).toSeconds();
    }

    private void resetLogFields() {
        // Resetting all the dropdown fields
        projectDropDown.setValue(null);
        lifeCycleDropDown.setValue(null);
        categoryDropDown.setValue(null);
        planDropDown.setValue(null);
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
    
    

    // Implement other methods and functionalities as required
}
