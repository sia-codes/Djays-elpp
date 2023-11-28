package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;

public class LoginViewController {
    private Scene scene;
    private Stage stage;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML 
    private Label errorLabel;

    @FXML
    protected void onCreateUserPressed() {
        String userName = usernameField.getText();
        String password = passwordField.getText();

        if (!isValidPassword(password)) {
            errorLabel.setText("Password does not meet the criteria.");
            return;
        }

        if (Login.registerUser(userName, password)) {
            errorLabel.setText("User successfully added!");
        } else {
            errorLabel.setText("Failed to add user or user already exists.");
        }
    }

    public void submitLogin(ActionEvent event) throws IOException {
        String userName = usernameField.getText();
        String password = passwordField.getText();

        if (Login.authenticate(userName, password)) {
            Main.currentUser = new User(userName, password); // Assuming currentUser is a User object
            loadNextScene(event, "DefinitionsPage.fxml", "Definitions Page"); // Replace with your next scene's FXML and title
        } else {
            errorLabel.setText("Incorrect username or password.");
        }
    }

    public void toForgotPassword(ActionEvent event) throws IOException {
        loadNextScene(event, "ForgotPasswordPage.fxml", "Forgot Password"); // Replace with your forgot password scene's FXML and title
    }

    private void loadNextScene(ActionEvent event, String fxmlFile, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(fxmlLoader.load());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
    private boolean isValidPassword(String password) {
        if (password.length() < 8) {
            return false;
        }
        boolean hasUpper = false, hasDigit = false, hasSpecial = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isDigit(c)) hasDigit = true;
            else if (!Character.isLetterOrDigit(c)) hasSpecial = true;
            if (hasUpper && hasDigit && hasSpecial) return true;
        }
        return false;
    }
}
