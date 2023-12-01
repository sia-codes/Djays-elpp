package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DefectLogDAO {
	
	// Method to get the ID for a defect log based on criteria
	public int getDefectLogId(DefectLog defectLog) {
	    String sql = "SELECT id FROM defect_logs WHERE " +
	                 "name = ? AND " +
	                 "defect_symptom = ? AND " +
	                 "defect_resolution = ? AND " +
	                 "lifecycle_step_injected_id = ? AND " +
	                 "lifecycle_step_removed_id = ? AND " +
	                 "defect_category_id = ?";

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {

	        pstmt.setString(1, defectLog.getName());
	        pstmt.setString(2, defectLog.getDefectSymptom());
	        pstmt.setString(3, defectLog.getDefectResolution());
	        pstmt.setInt(4, defectLog.getLifecycleStepInjectedId());
	        pstmt.setInt(5, defectLog.getLifecycleStepRemovedId());
	        pstmt.setInt(6, defectLog.getDefectCategoryId());

	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                return rs.getInt("id");
	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return -1; // Return -1 if no matching defect log is found
	}

    // Method to add a new defect log
    public void addDefectLog(DefectLog defectLog) {
        String sql = "INSERT INTO defect_logs (name, defect_symptom, defect_resolution, lifecycle_step_injected_id, lifecycle_step_removed_id, defect_category_id) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, defectLog.getName());
            pstmt.setString(2, defectLog.getDefectSymptom());
            pstmt.setString(3, defectLog.getDefectResolution());
            pstmt.setInt(4, defectLog.getLifecycleStepInjectedId());
            pstmt.setInt(5, defectLog.getLifecycleStepRemovedId());
            pstmt.setInt(6, defectLog.getDefectCategoryId());
            
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to fetch defect logs
    public List<DefectLog> fetchDefectLogs() {
        List<DefectLog> defectLogs = new ArrayList<>();
        String sql = "SELECT * FROM defect_logs";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                DefectLog defectLog = new DefectLog(
                    rs.getString("name"),
                    rs.getString("defect_symptom"),
                    rs.getString("defect_resolution"),
                    rs.getInt("lifecycle_step_injected_id"),
                    rs.getInt("lifecycle_step_removed_id"),
                    rs.getInt("defect_category_id")
                );
                defectLogs.add(defectLog);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return defectLogs;
    }

    // Method to update a defect log
    public void updateDefectLog(DefectLog defectLog) {
        String sql = "UPDATE defect_logs SET name = ?, defect_symptom = ?, defect_resolution = ?, lifecycle_step_injected_id = ?, lifecycle_step_removed_id = ?, defect_category_id = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, defectLog.getName());
            pstmt.setString(2, defectLog.getDefectSymptom());
            pstmt.setString(3, defectLog.getDefectResolution());
            pstmt.setInt(4, defectLog.getLifecycleStepInjectedId());
            pstmt.setInt(5, defectLog.getLifecycleStepRemovedId());
            pstmt.setInt(6, defectLog.getDefectCategoryId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a defect log
    public void deleteDefectLog(int id) {
        String sql = "DELETE FROM defect_logs WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
