package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserSessionDAO {
    
    public void initializeUserSessionForLoggedInUser() {
        long currentUserId = UserDAO.getCurrentUserId();
        long currentSessionId = SessionDAO.getCurrentSessionId();

        if (currentUserId != 0 && currentSessionId != 0) { // Check for default long value
            UserSession userSession = new UserSession(currentUserId, currentSessionId, null);
            addUserSession(userSession);
        }
    }

    public UserSession findUserSessionByUserIdAndSessionId(long userId, String sessionId) { // Changed userId to long
        String sql = "SELECT * FROM user_sessions WHERE user_id = ? AND session_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userId); // Set userId as long
            pstmt.setString(2, sessionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                UserSession userSession = new UserSession(rs.getLong("user_id"), rs.getLong("session_id"), rs.getString("selected_card"));
                userSession.setId(rs.getString("id"));
                return userSession;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addUserSession(UserSession userSession) {
        String sql = "INSERT INTO user_sessions (user_id, session_id, selected_card) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, userSession.getUserId()); // Set userId as long
            pstmt.setLong(2, userSession.getSessionId());
            pstmt.setString(3, userSession.getSelectedCard());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    
    public boolean updateUserSession(UserSession userSession) {
        String sql = "UPDATE user_sessions SET selected_card = ? WHERE user_id = ? AND session_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, userSession.getSelectedCard());
            pstmt.setLong(2, userSession.getUserId()); // Set userId as long
            pstmt.setLong(3, userSession.getSessionId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
