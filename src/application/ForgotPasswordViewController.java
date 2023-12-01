package application;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ForgotPasswordViewController {
    private Scene scene;
    private Stage stage;

    @FXML
    private TextField usernameField;
    @FXML
    private TextField resetKeyField; // Added TextField for reset_key
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private Label errorLabel; // Renamed for clarity

    public void backToLogin(ActionEvent event) throws IOException {
        changeScene(event, "LoginPage.fxml", "Login");
    }

    public void onSubmit(ActionEvent event) {
        String userName = usernameField.getText();
        String resetKey = resetKeyField.getText(); // Get reset_key from TextField
        String newPassword = newPasswordField.getText();

        if (validateResetKey(userName, resetKey)) {
            if (updatePassword(userName, newPassword)) {
            	errorLabel.setText("Password successfully updated.");
                changeScene(event, "LoginPage.fxml", "Login"); // Navigate back to login
            } else {
            	errorLabel.setText("Failed to update password.");
            }
        } else {
        	errorLabel.setText("Invalid username or reset key.");
        }
    }
    private boolean validateResetKey(String userName, String resetKey) {
        String query = "SELECT * FROM users WHERE username = ? AND reset_key = CAST(? AS uuid)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, userName);
            pstmt.setString(2, resetKey);
            ResultSet rs = pstmt.executeQuery();

            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean updatePassword(String userName, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, newPassword); // Assuming you'll use hashed passwords
            pstmt.setString(2, userName);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void changeScene(ActionEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(fxmlLoader.load());
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
