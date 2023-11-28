package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class PlanningPokerLoginViewController extends Controller {
    private Scene scene;
    private Stage stage;
    private SessionDAO sessionDAO = new SessionDAO();

    @FXML
    private TextField sessionNameField;
    @FXML
    private PasswordField passwordField;
    @FXML 
    private Label errorLabel;

    @FXML
    protected void onCreateSessionPressed() {
        String sessionName = sessionNameField.getText();
        String pin = passwordField.getText();

        if (!pin.matches("\\d{5}")) {
            errorLabel.setText("PIN must be exactly 5 digits and contain no special characters or letters!");
            return;
        }

        Session newSession = new Session(sessionName, pin);
        if(sessionDAO.findSessionByName(sessionName) == null) {
            sessionDAO.addSession(newSession);
            errorLabel.setText("Session successfully added!");
        }
        else {
            errorLabel.setText("This session already exists!");
            return;
        }
    }

    public void submitLogin(ActionEvent event) throws IOException {
        String sessionName = sessionNameField.getText();
        String pin = passwordField.getText();
        Session currentSession = sessionDAO.findSessionByName(sessionName);

        if(currentSession == null) {
            errorLabel.setText("This session does not exist");
            return;
        }

        if(currentSession.getPin().equals(pin)) {
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