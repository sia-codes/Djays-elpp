package application;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DebugConsoleController extends Controller implements Initializable {
    @FXML private TableView<DefectLog> defectLogTable;
    // Define TableColumns assuming DefectLog has these properties
    @FXML private TableColumn<DefectLog, String> nameColumn;
    @FXML private TableColumn<DefectLog, String> symptomColumn;
    @FXML private TableColumn<DefectLog, String> resolutionColumn;
    // Add more TableColumns as needed

    private DefectLogDAO defectLogDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defectLogDAO = new DefectLogDAO();

        // Initialize TableView columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        symptomColumn.setCellValueFactory(new PropertyValueFactory<>("defectSymptom"));
        resolutionColumn.setCellValueFactory(new PropertyValueFactory<>("defectResolution"));
        // Initialize other columns similarly

        loadDefectLogs();
    }

    private void loadDefectLogs() {
        // Load defect logs from the database and set them in the TableView
        defectLogTable.setItems(FXCollections.observableArrayList(defectLogDAO.fetchDefectLogs()));
    }

    // Event handler for adding a defect log
    @FXML
    private void handleAddDefectLog() {
        // Code to handle adding a new defect log
        // This might involve opening a dialog to input new defect log details
    }

    // Event handler for updating a defect log
    @FXML
    private void handleUpdateDefectLog() {
        DefectLog selectedDefectLog = defectLogTable.getSelectionModel().getSelectedItem();
        if (selectedDefectLog != null) {
            DefectLog updatedDefectLog = showUpdateDefectLogDialog(selectedDefectLog);
            if (updatedDefectLog != null) {
                defectLogDAO.updateDefectLog(updatedDefectLog);
                loadDefectLogs(); // Refresh table
            }
        }
    }

    private DefectLog showUpdateDefectLogDialog(DefectLog selectedDefectLog) {
		// TODO Auto-generated method stub
		return null;
	}

	// Event handler for deleting a defect log
    @FXML
    private void handleDeleteDefectLog() {
        DefectLog selectedDefectLog = defectLogTable.getSelectionModel().getSelectedItem();
        if (selectedDefectLog != null && confirmDeletion()) {
            int defectLogId = defectLogDAO.getDefectLogId(selectedDefectLog);
            if (defectLogId != -1) {
                defectLogDAO.deleteDefectLog(defectLogId);
                loadDefectLogs(); // Refresh table
            } else {
                // Handle the case where the defect log ID was not found
               
            }
        }
    }
    
    private boolean confirmDeletion() {
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Deletion");
        confirmationAlert.setHeaderText("Delete Defect Log");
        confirmationAlert.setContentText("Are you sure you want to delete this defect log?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
    

    // Add other event handler methods as needed for your UI components
}
