package application;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ViewLogsController  extends Controller{
    // ... Other code ...

    // Method to retrieve defect logs for a project by project ID
    private List<String> getDefectLogsForProject(int projectId, Connection conn) throws SQLException {
        List<String> defectLogs = new ArrayList<>();
        String query = "SELECT description FROM defect_logs WHERE project_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                defectLogs.add(rs.getString("description"));
            }
        }
        return defectLogs;
    }

    // Method to retrieve effort logs for a project by project ID
    private List<String> getEffortLogsForProject(int projectId, Connection conn) throws SQLException {
        List<String> effortLogs = new ArrayList<>();
        String query = "SELECT description FROM effort_logs WHERE project_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                effortLogs.add(rs.getString("description"));
            }
        }
        return effortLogs;
    }

}
