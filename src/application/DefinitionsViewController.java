package application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class DefinitionsViewController extends Controller{

    @FXML
    private TextField projectField, lifeCycleField, effortCategoryField, planField, deliverableField, interruptionField, defectCategoryField;
    @FXML
    private ComboBox<String> projectDropDown, lifeCycleDropDown, effortCategoryDropDown, planDropDown, deliverablesDropDown, interruptionsDropDown, defectCategoryDropDown;
    @FXML
    private Button addProjectButton, addLifecycleButton, addEffortCategoryButton, addPlanButton, addDeliverableButton, addInterruptionButton, addDefectCategoryButton;

    private ProjList projList;

    public DefinitionsViewController() {
        projList = new ProjList(); // Initialize with existing or new ProjList
        // Load existing ProjList data from file or database if necessary
    }

    // Called after the FXML fields are injected
    @FXML
    private void initialize() {
        // Initialize UI components with data
        updateProjectDropDown();
        // Similar initializations for other drop-downs
    }

    // Event handler for adding a new project
    @FXML
    private void addProject(ActionEvent event) {
        String projectName = projectField.getText();
        Proj newProject = new Proj(projectName);
        projList.addProj(newProject);
        updateProjectDropDown();
        projectField.clear(); // Clear the input field
    }

    // Add life cycle step to a selected project
    @FXML
    private void addLifeCycleStep(ActionEvent event) {
        String step = lifeCycleField.getText();
        Proj selectedProject = getSelectedProject();
        if (selectedProject != null) {
            selectedProject.addLifeCycleStep(step);
            updateLifeCycleDropDown();
        }
        lifeCycleField.clear();
    }

    // Similar methods for adding effort categories, plans, etc.

    // Update method for project drop-down
    private void updateProjectDropDown() {
        projectDropDown.getItems().clear();
        for (Proj proj : projList.getProjs()) {
            projectDropDown.getItems().add(proj.getProjName());
        }
    }

    // Update method for life cycle steps drop-down
    private void updateLifeCycleDropDown() {
        lifeCycleDropDown.getItems().clear();
        Proj selectedProject = getSelectedProject();
        if (selectedProject != null) {
            lifeCycleDropDown.getItems().addAll(selectedProject.getLifeCycleSteps());
        }
    }

    // Similar update methods for other drop-downs

    // Helper method to get the selected project
    private Proj getSelectedProject() {
        String selectedProjectName = projectDropDown.getSelectionModel().getSelectedItem();
        return projList.findProj(selectedProjectName);
    }

    // Additional methods as required for functionality...
}
