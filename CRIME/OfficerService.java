package CRIME;

import java.sql.*;
import java.util.*;

class OfficerService extends AbstractService implements Colors {

    // Queue to manage recently added officers
    private Queue<Officer> officerQueue = new LinkedList<>();

    public OfficerService() throws SQLException {
        super();
    }
    public void addOfficer(int officerId, String officerName, String rank, int age, String department) throws SQLException {
        String query = "INSERT INTO officers (officer_id, officer_name, rank, age, department) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, officerId);
            pstmt.setString(2, officerName);
            pstmt.setString(3, rank);
            pstmt.setInt(4, age);
            pstmt.setString(5, department);
            pstmt.executeUpdate();

            // Add the new officer to the queue
            officerQueue.add(new Officer(officerId, officerName, rank, age, department));
            System.out.println(GREEN+"\t\t\tOFFICER ADDED SUCCESSFULLY."+RESET);
        } catch (SQLException e) {
            System.out.print(RED+"\t\tERROR ADDING OFFICER: " + e.getMessage()+RESET);
            throw e;
        }
    }

    // Method to update an officer
    public void updateOfficer(int officerId, String name, String rank, Integer age, String department) throws SQLException {
        StringBuilder query = new StringBuilder("UPDATE officers SET ");
        if (name != null) query.append("officer_name = ?, ");
        if (rank != null) query.append("rank = ?, ");
        if (age != null) query.append("age = ?, ");
        if (department != null) query.append("department = ?, ");
        query.setLength(query.length() - 2); // Remove trailing comma
        query.append(" WHERE officer_id = ?");

        PreparedStatement stmt = conn.prepareStatement(query.toString());
        int index = 1;
        if (name != null) stmt.setString(index++, name);
        if (rank != null) stmt.setString(index++, rank);
        if (age != null) stmt.setInt(index++, age);
        if (department != null) stmt.setString(index++, department);
        stmt.setInt(index, officerId);
        stmt.executeUpdate();
    }

    // Method to delete an officer
    public void deleteOfficer(int officerId) throws SQLException {
        String query = "DELETE FROM officers WHERE officer_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, officerId);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println(GREEN+"\t\t\tOFFICER DELETED SUCCESSFULLY."+RESET);
            } else {
                System.out.println(RED+"\t\t\tOFFICER RECORD NOT FOUND.."+GREEN);
            }
        } catch (SQLException e) {
            System.out.print(RED+"\t\tERROR DELETING OFFICER: " + e.getMessage()+RESET);
            throw e;
        }
    }

    public List<Officer> getAllOfficers(int limit, int offset) throws SQLException {
        List<Officer> officers = new ArrayList<>();
        String query = "SELECT * FROM officers LIMIT ? OFFSET ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, limit);  // Set the limit for the number of records per page
        stmt.setInt(2, offset); // Set the offset to start fetching records
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            officers.add(new Officer(
                rs.getInt("officer_id"),
                rs.getString("officer_name"),
                rs.getString("rank"),
                rs.getInt("age"),
                rs.getString("department")
            ));
        }
        return officers;
    }

    public List<Officer> searchOfficers(int searchChoice, String searchTerm, String sortOption) throws SQLException {
        List<Officer> officers = new ArrayList<>();
        String query = "SELECT * FROM officers WHERE ";
    
        // Adjust query based on search choice
        switch (searchChoice) {
            case 1: query += "officer_name LIKE ?"; break;   // Search by name
            case 2: query += "age = ?"; break;               // Search by age (exact match)
            case 3: query += "rank LIKE ?"; break;           // Search by rank
            case 4: query += "department LIKE ?"; break;     // Search by department
            default: query += "officer_name LIKE ?"; break;
        }
    
        // Add sorting option
        switch (sortOption) {
            case "1": query += " ORDER BY officer_name"; break;
            case "2": query += " ORDER BY age"; break;
            case "3": query += " ORDER BY rank"; break;
            case "4": query += " ORDER BY department"; break;
            default: query += " ORDER BY officer_name"; break;
        }
    
        PreparedStatement stmt = conn.prepareStatement(query);
    
        // Set the parameter based on the search choice
        if (searchChoice == 2) {  // Age (exact match)
            stmt.setString(1, searchTerm);
        } else {  // Name, Rank, or Department (LIKE match)
            String likeSearchTerm = "%" + searchTerm + "%";
            stmt.setString(1, likeSearchTerm);
        }
    
        ResultSet rs = stmt.executeQuery();
    
        while (rs.next()) {
            officers.add(new Officer(
                rs.getInt("officer_id"),
                rs.getString("officer_name"),
                rs.getString("rank"),
                rs.getInt("age"),
                rs.getString("department")
            ));
        }
        return officers;
    }  
}
