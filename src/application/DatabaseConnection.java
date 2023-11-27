package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://db.mopnkemwewvyxjzjewna.supabase.co:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASSWORD = "WoYcRuyQBlLODnMq";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
