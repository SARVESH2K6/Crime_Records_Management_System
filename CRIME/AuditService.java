package CRIME;

import java.sql.*;
import java.util.*;

class AuditService extends AbstractService implements Colors {
    private final Queue<String[]> auditLogQueue = new LinkedList<>(); // Queue to hold audit log entries
    
    public AuditService() throws SQLException {
        super();
    }

    public void addLogToQueue(int userId, String action, String details) {
        // Adding the log entry to the queue
        auditLogQueue.add(new String[]{String.valueOf(userId), action, details});
    }

    public void processLogQueue() throws SQLException {
        // Process all queued log entries
        while (!auditLogQueue.isEmpty()) {
            String[] logEntry = auditLogQueue.poll(); // Retrieves and removes the head of the queue
            if (logEntry != null) {
                logAction(Integer.parseInt(logEntry[0]), logEntry[1], logEntry[2]);
            }
        }
    }

    void logAction(int userId, String action, String details) throws SQLException {
        String query = "INSERT INTO audit_logs (user_id, action_type, action_description, action_time) VALUES (?, ?, ?, NOW())";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, userId);
        stmt.setString(2, action);
        stmt.setString(3, details);
        stmt.executeUpdate();
    }

    public void viewAuditLogs() throws SQLException {
        String query = "SELECT * FROM audit_logs";
        PreparedStatement stmt = conn.prepareStatement(query);
        ResultSet rs = stmt.executeQuery();
        System.out.println(YELLOW + "\t\t\tAUDIT LOGS:" + RESET);
        while (rs.next()) {
            System.out.println(GREEN+"\t\t\tID: " + rs.getInt("log_id") + "|| User ID: " + rs.getInt("user_id") + "|| Action: " + rs.getString("action_type") + "|| Details: " + rs.getString("action_description") + "|| Timestamp: " + rs.getTimestamp("action_time")+RESET);
        }
    }
}
