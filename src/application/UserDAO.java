package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static long currentUserId; // Changed to long

    public User authenticate(String username, String password) {
        // Database logic to authenticate user
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getString("username"), rs.getString("password"));
                user.setId(rs.getString("id")); // Retrieve and store the auto-generated ID as long
                currentUserId = Long.parseLong(user.getId()); // Update the currentUserId
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static long getCurrentUserId() { // Return type changed to long
        return currentUserId;
    }
}
