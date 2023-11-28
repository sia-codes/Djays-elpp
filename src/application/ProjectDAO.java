package application;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDAO {

    // Method to get a project by its ID, including details from related tables
    public Project getProjectById(int projectId) {
        String projectQuery = "SELECT * FROM projects WHERE id = ?";
        Project project = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(projectQuery)) {

            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                project = new Project(rs.getInt("id"), rs.getString("name"));
                project.setLifecycles(getLifecyclesForProject(projectId, conn));
                project.setDeliverables(getDeliverablesForProject(projectId, conn));
                project.setPlans(getPlansForProject(projectId, conn));
                project.setEffortCategories(getEffortCategoriesForProject(projectId, conn));
                project.setInterruptions(getInterruptionsForProject(projectId, conn));
                project.setDefectCategories(getDefectCategoriesForProject(projectId, conn));
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    private List<String> getDeliverablesForProject(int projectId, Connection conn) throws SQLException {
        List<String> deliverables = new ArrayList<>();
        String query = "SELECT name FROM deliverables WHERE project_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                deliverables.add(rs.getString("name"));
            }
        }
        return deliverables;
    }

    private List<String> getLifecyclesForProject(int projectId, Connection conn) throws SQLException {
        List<String> lifecycles = new ArrayList<>();
        String query = "SELECT name FROM lifecycles WHERE project_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                lifecycles.add(rs.getString("name"));
            }
        }
        return lifecycles;
    }
    
    private List<String> getPlansForProject(int projectId, Connection conn) throws SQLException {
        List<String> plans = new ArrayList<>();
        String query = "SELECT name FROM plans WHERE project_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	plans.add(rs.getString("name"));
            }
        }
        return plans;
    }
    
    private List<String> getEffortCategoriesForProject(int projectId, Connection conn) throws SQLException {
        List<String> effortCategories = new ArrayList<>();
        String query = "SELECT name FROM effort_categories WHERE project_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	effortCategories.add(rs.getString("name"));
            }
        }
        return effortCategories;
    }
    
    private List<String> getInterruptionsForProject(int projectId, Connection conn) throws SQLException {
        List<String> interruptions = new ArrayList<>();
        String query = "SELECT name FROM interruptions WHERE project_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	interruptions.add(rs.getString("name"));
            }
        }
        return interruptions;
    }
    
    private List<String> getDefectCategoriesForProject(int projectId, Connection conn) throws SQLException {
        List<String> defectCategories = new ArrayList<>();
        String query = "SELECT name FROM defect_categories WHERE project_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	defectCategories.add(rs.getString("name"));
            }
        }
        return defectCategories;
    }



    // Method to get all projects
    public List<Project> getAllProjects() {
        List<Project> projects = new ArrayList<>();
        String query = "SELECT * FROM projects";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Project project = getProjectById(rs.getInt("id"));
                projects.add(project);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return projects;
    }

    public boolean addProject(Project project) {
        String insertProjectQuery = "INSERT INTO projects (name) VALUES (?) RETURNING id";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Start transaction

            // Insert the project
            try (PreparedStatement projectStmt = conn.prepareStatement(insertProjectQuery)) {
                projectStmt.setString(1, project.getName());
                ResultSet rs = projectStmt.executeQuery();

                if (rs.next()) {
                    int projectId = rs.getInt(1);

                    for (String lifecycle : project.getLifecycles()) {
                        if (!addLifecycleStep(projectId, lifecycle)) {
                            conn.rollback();
                            return false;
                        }
                    }

                    // Add deliverables
                    for (String deliverable : project.getDeliverables()) {
                        if (!addDeliverable(projectId, deliverable)) {
                            conn.rollback();
                            return false;
                        }
                    }


                    conn.commit(); // Commit the transaction
                    return true;
                } else {
                    conn.rollback();
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addLifecycleStep(int projectId, String lifecycle) {
        String insertLifecycleQuery = "INSERT INTO lifecycles (name, project_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertLifecycleQuery)) {

            pstmt.setString(1, lifecycle);
            pstmt.setInt(2, projectId);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean addDeliverable(int projectId, String deliverable) {
        String insertDeliverableQuery = "INSERT INTO deliverables (name, project_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertDeliverableQuery)) {

            pstmt.setString(1, deliverable);
            pstmt.setInt(2, projectId);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean addPlan(int projectId, String plan) {
        String insertDeliverableQuery = "INSERT INTO plans (name, project_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertDeliverableQuery)) {

            pstmt.setString(1, plan);
            pstmt.setInt(2, projectId);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean addEffortCategory(int projectId, String effort_category) {
        String insertDeliverableQuery = "INSERT INTO effort_categories (name, project_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertDeliverableQuery)) {

            pstmt.setString(1, effort_category);
            pstmt.setInt(2, projectId);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean addInterruption(int projectId, String interruption) {
        String insertDeliverableQuery = "INSERT INTO interruptions (name, project_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertDeliverableQuery)) {

            pstmt.setString(1, interruption);
            pstmt.setInt(2, projectId);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean addDefectCategory(int projectId, String defect_category) {
        String insertDeliverableQuery = "INSERT INTO defect_categories (name, project_id) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertDeliverableQuery)) {

            pstmt.setString(1, defect_category);
            pstmt.setInt(2, projectId);
            int affectedRows = pstmt.executeUpdate();

            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    
   
    public List<String> getLifecyclesForProject(int projectId) {
        List<String> lifecycles = new ArrayList<>();
        String query = "SELECT name FROM lifecycles WHERE project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                lifecycles.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        return lifecycles;
    }
    
    public List<String> getDeliverablesForProject( int projectId) {
        List<String> deliverables = new ArrayList<>();
        String query = "SELECT name FROM deliverables WHERE project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                deliverables.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        return deliverables;
    }
    
    public List<String> getPlansForProject( int projectId) {
        List<String> plans = new ArrayList<>();
        String query = "SELECT name FROM plans WHERE project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	plans.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        return plans;
    }
    
    public List<String> getInterruptionsForProject( int projectId) {
        List<String> interruptions = new ArrayList<>();
        String query = "SELECT name FROM interruptions WHERE project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	interruptions.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        return interruptions;
    }
    
    
    public List<String> getEffortCategoriesForProject( int projectId) {
        List<String> effortCategories = new ArrayList<>();
        String query = "SELECT name FROM effort_categories WHERE project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	effortCategories.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        return effortCategories;
    }
    
    public List<String> getDefectCategoriesForProject( int projectId) {
        List<String> defectCategories = new ArrayList<>();
        String query = "SELECT name FROM defect_categories WHERE project_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, projectId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
            	defectCategories.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }

        return defectCategories;
    }

    

}
