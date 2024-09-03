package CRIME;

import java.sql.*;
import java.util.*;

class UserService extends AbstractService implements Colors{
    private Stack<String> loginHistory;  // Stack to track login history
    public UserService() throws SQLException {
        super();
        this.loginHistory = new Stack<>();  // Initialize the stack
    }

    public boolean register(String username, String password) throws SQLException {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, 'User')";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0;
    }

    public boolean isUsernameTaken(String username) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        return rs.getInt(1) > 0;
    }

    public User login(String username, String password, String role) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        stmt.setString(2, password);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            loginHistory.push(GREEN+role.toUpperCase() + ": " + username + " LOGGED IN SUCCESSFULLY."+RESET);
            return new User(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("role")
            );
        } else {
            loginHistory.push(RED+role.toUpperCase() + ": FAILED LOGIN ATTEMPT FOR USER: " + username+RESET);
            return null;
        }
    }

    // Method to view login history
    public void viewLoginHistory() {
        System.out.println(YELLOW+"\t\t\tLOGIN HISTORY:"+RESET);
        for (String entry : loginHistory) {
            System.out.println("\t\t\t\t"+entry);
        }
    }

    public void logout(String username, String role) {
        if (username != null && !username.isEmpty()) {
            loginHistory.push(GREEN+role.toUpperCase() + ": " + username + " LOGGED OUT SUCCESSFULLY."+RESET);
        }
    }
    
}
