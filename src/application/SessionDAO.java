package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessionDAO {
    private static long currentSessionId;

    public Session findSessionByName(String name) {
        String sql = "SELECT * FROM sessions WHERE name = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Session session = new Session(rs.getString("name"), rs.getString("pin"));
                session.setId(rs.getString("id"));
                session.setStatus(rs.getString("status"));

                // Update currentSessionId
                currentSessionId = Long.parseLong(session.getId());

                return session;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addSession(Session session) {
        String sql = "INSERT INTO sessions (name, pin, status) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, session.getName());
            pstmt.setString(2, session.getPin());
            pstmt.setString(3, "closed");

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                // Update currentSessionId after successful insertion
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        session.setId(generatedKeys.getString(1));
                        //Update currentSessionId
                        currentSessionId = Long.parseLong(session.getId());
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static long getCurrentSessionId() {
        return currentSessionId;
    }
}
