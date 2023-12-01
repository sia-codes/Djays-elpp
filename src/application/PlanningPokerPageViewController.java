package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.scene.control.Label;


public class PlanningPokerPageViewController extends Controller implements Initializable, RealTimeUpdateListener{
	
	@FXML private ComboBox<String> userStoriesDropdown;
	@FXML private TextField addUserStoryField;
	@FXML private Label userStoryNameSelectedLabel; // Label to display the selected user story

	
	
	private RealtimeClient realtimeClient;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	    populateUserStories();
	  realtimeClient = new RealtimeClient(this); 
	  //testAddEffortLogToDatabase();
	    // other initialization code...
	}
	

    @Override
    public void onRealTimeUpdate(RealTimeUpdateType updateType) {
        switch (updateType) {
            case USER_STORY:
                populateUserStories();
                break;
            case CARD:
                // handle card updates
                break;
            case OTHER:
                // handle other updates
                break;
        }
    }
    

	
	public void populateUserStories() {
		
        long sessionId = SessionDAO.getCurrentSessionId();

        // SQL query to select user stories based on session ID
        String query = "SELECT name FROM user_stories WHERE session_id = ?";

        ObservableList<String> stories = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setLong(1, sessionId);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String storyName = rs.getString("name");
                    stories.add(storyName);
                }
            }

            // Update the dropdown on the JavaFX application thread
            userStoriesDropdown.setItems(stories);

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }
    }
	
	  private void handleVote(double voteValue) {
	        String selectedStory = userStoriesDropdown.getSelectionModel().getSelectedItem();
	        realtimeClient.sendVote(selectedStory, voteValue);
	    }

    public void onAddUserStoryButtonClicked() {
        String storyName = addUserStoryField.getText();
        long sessionId = SessionDAO.getCurrentSessionId();

        // SQL query to insert a new story
        String query = "INSERT INTO user_stories (name, session_id, status) VALUES (?, ?, 'not_started')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, storyName);
            pstmt.setLong(2, sessionId);

            pstmt.executeUpdate();
            // Optionally, after successful insertion, you can clear the input field or update the UI
            addUserStoryField.clear();
            
            
            // Here to send to node server for realtime update.
            // Call method to update the UI with new stories
            populateUserStories(); // Assuming you have a method to update the UI

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors
        }
    }
	
}