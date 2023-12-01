package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.util.List;

public class DefinitionsViewController extends Controller{

    @FXML private TextField projectField, lifecycleStepField, effortCategoryField, planField, deliverableField, interruptionField, defectCategoryField;
    @FXML private ComboBox<Project> projectsDropdown; // Changed to ComboBox<Project>
    @FXML private ComboBox<String> lifecycleStepsDropdown, deliverablesDropdown, plansDropdown, interruptionsDropdown, effortCategoriesDropdown, defectCategoriesDropdown;
    // Update other ComboBoxes if they also hold Project objects or other custom types
    @FXML private Button addProjectButton, addLifecycleStepButton, addEffortCategoryButton, addPlanButton, addDeliverableButton, addInterruptionButton, addDefectCategoryButton;

    private ProjectDAO projectDAO = new ProjectDAO();

    @FXML
    public void initialize() {
        loadAllProjects();
        configureProjectDropdown();
        setupProjectSelectionListener();
    }
    
    private void setupProjectSelectionListener() {
        projectsDropdown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateLifecycleStepsDropdown(newValue.getId());
                updateDeliverablesDropdown(newValue.getId());
                updatePlansDropdown(newValue.getId());
                updateInterruptionsDropdown(newValue.getId());
                updateEffortCategoriesDropdown(newValue.getId());
                updateDefectCategoriesDropdown(newValue.getId());
            }
        });
    }

    private void updateLifecycleStepsDropdown(int projectId) {
        List<String> lifecycles = projectDAO.getLifecyclesForProject(projectId);
        ObservableList<String> lifecycleItems = FXCollections.observableArrayList(lifecycles);
        lifecycleStepsDropdown.setItems(lifecycleItems);
    }
    
    
    private void updateDeliverablesDropdown(int projectId) {
        List<String> deliverables = projectDAO.getDeliverablesForProject(projectId);
        ObservableList<String> deliverableItems = FXCollections.observableArrayList(deliverables);
        deliverablesDropdown.setItems(deliverableItems);
    }
    
    private void updatePlansDropdown(int projectId) {
        List<String> plans = projectDAO.getPlansForProject(projectId);
        ObservableList<String> planItems = FXCollections.observableArrayList(plans);
        plansDropdown.setItems(planItems);
    }
    
    private void updateInterruptionsDropdown(int projectId) {
        List<String> interruptions = projectDAO.getInterruptionsForProject(projectId);
        ObservableList<String> interruptionItems = FXCollections.observableArrayList(interruptions);
        interruptionsDropdown.setItems(interruptionItems);  // Corrected the dropdown name
    }

    private void updateEffortCategoriesDropdown(int projectId) {
        List<String> effortCategories = projectDAO.getEffortCategoriesForProject(projectId);
        ObservableList<String> effortCategoryItems = FXCollections.observableArrayList(effortCategories);
        effortCategoriesDropdown.setItems(effortCategoryItems);  // Corrected the dropdown name
    }

    private void updateDefectCategoriesDropdown(int projectId) {
        List<String> defectCategories = projectDAO.getDefectCategoriesForProject(projectId);
        ObservableList<String> defectCategoryItems = FXCollections.observableArrayList(defectCategories);
        defectCategoriesDropdown.setItems(defectCategoryItems);  // Corrected the dropdown name
    }

    private void loadAllProjects() {
        List<Project> projects = projectDAO.getAllProjects();
        ObservableList<Project> projectItems = FXCollections.observableArrayList(projects);
        projectsDropdown.setItems(projectItems);
    }

    private void configureProjectDropdown() {
        projectsDropdown.setCellFactory(lv -> new ListCell<Project>() {
            @Override
            protected void updateItem(Project item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        });

        projectsDropdown.setConverter(new StringConverter<Project>() {
            @Override
            public String toString(Project project) {
                return project == null ? "" : project.getName();
            }

            @Override
            public Project fromString(String string) {
                return null; // No conversion from string to Project
            }
        });
    }

    @FXML
    protected void addProject(ActionEvent event) {
        String projectName = projectField.getText();
        Project newProject = new Project(0, projectName); // ID is auto-generated

        try {
            if (projectDAO.addProject(newProject)) {
                // Refresh projects list to include the new project with its ID
                loadAllProjects();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add the project.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error occurred while accessing the database.");
            e.printStackTrace(); // Log the exception for debugging purposes
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    protected void addLifecycleStep(ActionEvent event) {
        Project selectedProject = projectsDropdown.getValue();
        String lifecycleStep = lifecycleStepField.getText();

        if (selectedProject != null && !lifecycleStep.isEmpty()) {
            boolean isSuccess = projectDAO.addLifecycleStep(selectedProject.getId(), lifecycleStep);
            if (isSuccess) {
                updateLifecycleStepsDropdown(selectedProject.getId()); // Update the dropdown
                lifecycleStepField.clear();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Lifecycle step added successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add lifecycle step.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a project and enter a lifecycle step.");
        }
    }
    
    @FXML
    protected void addDeliverable(ActionEvent event) {
        Project selectedProject = projectsDropdown.getValue();
        String deliverable = deliverableField.getText();

        if (selectedProject != null && !deliverable.isEmpty()) {
            boolean isSuccess = projectDAO.addDeliverable(selectedProject.getId(), deliverable);
            if (isSuccess) {
            	updateDeliverablesDropdown(selectedProject.getId()); // Update the dropdown
                deliverableField.clear();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Deliverable added successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add deliverable.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a project and enter a deliverable.");
        }
    }
    
    @FXML
    protected void addPlan(ActionEvent event) {
        Project selectedProject = projectsDropdown.getValue();
        String plan = planField.getText();

        if (selectedProject != null && !plan.isEmpty()) {
            boolean isSuccess = projectDAO.addPlan(selectedProject.getId(), plan);
            if (isSuccess) {
            	updatePlansDropdown(selectedProject.getId()); // Update the dropdown
                planField.clear();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Plan added successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add plan.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a project and enter a plan.");
        }
    }
    
    @FXML
    protected void addInterruption(ActionEvent event) {
        Project selectedProject = projectsDropdown.getValue();
        String interruption = interruptionField.getText();

        if (selectedProject != null && !interruption.isEmpty()) {
            boolean isSuccess = projectDAO.addInterruption(selectedProject.getId(), interruption);
            if (isSuccess) {
            	updateInterruptionsDropdown(selectedProject.getId()); // Update the dropdown
                planField.clear();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Interruption added successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add interruption.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a project and enter a interruption.");
        }
    }
    
    @FXML
    protected void addEffortCategory(ActionEvent event) {
        Project selectedProject = projectsDropdown.getValue();
        String effortCategory = effortCategoryField.getText();

        if (selectedProject != null && !effortCategory.isEmpty()) {
            boolean isSuccess = projectDAO.addEffortCategory(selectedProject.getId(), effortCategory);
            if (isSuccess) {
            	updateEffortCategoriesDropdown(selectedProject.getId()); // Update the dropdown
                planField.clear();
                showAlert(Alert.AlertType.INFORMATION, "Success", "EffortCategory added successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add effortCategory.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a project and enter a effortCategory.");
        }
    }
    
    @FXML
    protected void addDefectCategory(ActionEvent event) {
        Project selectedProject = projectsDropdown.getValue();
        String defectCategory = defectCategoryField.getText();

        if (selectedProject != null && !defectCategory.isEmpty()) {
            boolean isSuccess = projectDAO.addDefectCategory(selectedProject.getId(), defectCategory);
            if (isSuccess) {
            	updateDefectCategoriesDropdown(selectedProject.getId()); // Update the dropdown
                planField.clear();
                showAlert(Alert.AlertType.INFORMATION, "Success", "DefectCategory added successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add Defect Category.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please select a project and enter a Defect Category.");
        }
    }
    
    
    
    
   
}
