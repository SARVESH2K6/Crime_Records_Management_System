package CRIME;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

class CrimeService extends AbstractService implements Colors {
    
    // Stack to keep track of recent operations for undo functionality
    private Stack<Crime> crimeHistory = new Stack<>();
    public CrimeService() throws SQLException {
        super();
    }

    public void addCrimeRecord(int crimeId, String name, String crimeType, int age, Date arrestDate) throws SQLException {
        String query = "INSERT INTO crime_records (crime_id, name, crime_type, age, arrest_date) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, crimeId);
        stmt.setString(2, name);
        stmt.setString(3, crimeType);
        stmt.setInt(4, age);
        stmt.setDate(5, arrestDate);
        stmt.executeUpdate();
        
        // Push the added crime to the stack
        crimeHistory.push(new Crime(crimeId, name, crimeType, age, arrestDate));
    }

    public void updateCrimeRecord(int crimeId, String name, String crimeType, Integer age, Date arrestDate) throws SQLException {
        // Retrieve the current record before updating for undo purposes
        Crime currentCrime = getCrimeById(crimeId);
        if (currentCrime != null) {
            crimeHistory.push(currentCrime); // Push the current state before updating
        }

        StringBuilder query = new StringBuilder("UPDATE crime_records SET ");
        if (name != null) query.append("name = ?, ");
        if (crimeType != null) query.append("crime_type = ?, ");
        if (age != null) query.append("age = ?, ");
        if (arrestDate != null) query.append("arrest_date = ?, ");
        query.setLength(query.length() - 2); // Remove trailing comma
        query.append(" WHERE crime_id = ?");

        PreparedStatement stmt = conn.prepareStatement(query.toString());
        int index = 1;
        if (name != null) stmt.setString(index++, name);
        if (crimeType != null) stmt.setString(index++, crimeType);
        if (age != null) stmt.setInt(index++, age);
        if (arrestDate != null) stmt.setDate(index++, arrestDate);
        stmt.setInt(index, crimeId);
        stmt.executeUpdate();
    }

    public void deleteCrimeRecord(int crimeId) throws SQLException {
        // Retrieve the current record before deleting for undo purposes
        Crime currentCrime = getCrimeById(crimeId);
        if (currentCrime != null) {
            crimeHistory.push(currentCrime); // Push the current state before deleting
        }

        String query = "DELETE FROM crime_records WHERE crime_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, crimeId);
        stmt.executeUpdate();
    }

    public List<Crime> getAllCriminals(int limit, int offset) throws SQLException {
        List<Crime> crimes = new ArrayList<>();
        String query = "SELECT * FROM crime_records LIMIT ? OFFSET ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, limit);
        stmt.setInt(2, offset);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            crimes.add(new Crime(
                rs.getInt("crime_id"),
                rs.getString("name"),
                rs.getString("crime_type"),
                rs.getInt("age"),
                rs.getDate("arrest_date")
            ));
        }
        return crimes;
    }

    public List<Crime> searchCriminals(int searchChoice, String searchTerm, String sortOption) throws SQLException {
        List<Crime> crimes = new ArrayList<>();
        String query = "SELECT * FROM crime_records WHERE ";
    
        // Adjust query based on search choice
        switch (searchChoice) {
            case 1: query += "name LIKE ?"; break;           // Search by name
            case 2: query += "age = ?"; break;               // Search by age (exact match)
            case 3: query += "crime_type LIKE ?"; break;     // Search by crime type
            case 4: query += "arrest_date = ?"; break;       // Search by arrest date (exact match)
            default: query += "name LIKE ?"; break;
        }
    
        // Add sorting option
        switch (sortOption) {
            case "1": query += " ORDER BY name"; break;
            case "2": query += " ORDER BY age"; break;
            case "3": query += " ORDER BY crime_type"; break;
            case "4": query += " ORDER BY arrest_date"; break;
            default: query += " ORDER BY name"; break;
        }
    
        PreparedStatement stmt = conn.prepareStatement(query);
    
        // Set the parameter based on the search choice
        if (searchChoice == 2 || searchChoice == 4) {  // Age or Arrest Date (exact match)
            stmt.setString(1, searchTerm);
        } else {  // Name or Crime Type (LIKE match)
            String likeSearchTerm = "%" + searchTerm + "%";
            stmt.setString(1, likeSearchTerm);
        }
    
        ResultSet rs = stmt.executeQuery();
    
        while (rs.next()) {
            crimes.add(new Crime(
                rs.getInt("crime_id"),
                rs.getString("name"),
                rs.getString("crime_type"),
                rs.getInt("age"),
                rs.getDate("arrest_date")
            ));
        }
        return crimes;
    }
    
    // Method to get officers associated with a crime
    void getOfficersByCrimeId(int crimeId) throws SQLException {
        List<Officer> officers = new ArrayList<>();
        String query = "SELECT officers.officer_id, officer_name, rank, age, department FROM officers INNER JOIN officer_cases ON officers.officer_id = officer_cases.officer_id WHERE officer_cases.crime_id = ?";
        
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, crimeId);
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
        for(Officer officer: officers) {
            System.out.println(GREEN+"\t\t\tID: " + officer.getOfficerId() + "|| Name: " + officer.getName() + "|| Rank: " + officer.getRank() + "|| Department: " + officer.getDepartment()+RESET);
        }
    }

    // Assign an officer to a crime
    public void assignOfficerToCrime() throws SQLException {
        Queue<int[]> assignments = new LinkedList<>();
        Scanner sc = new Scanner(System.in);
        String moreAssignments;

        do {
            System.out.print(YELLOW+"\n\t\t\tENTER OFFICER ID: "+RESET);
            int officerId = sc.nextInt();
            System.out.print(YELLOW+"\n\t\t\tENTER CRIME ID: "+RESET);
            int crimeId = sc.nextInt();
            assignments.add(new int[]{officerId, crimeId});

            while (true) {
                System.out.print(YELLOW+"\t\t\tDO YOU WANT TO ADD MORE ASSIGNMENTS? (yes/no)"+RESET);
                moreAssignments = sc.next().trim().toLowerCase();
    
                if (moreAssignments.equals("yes") || moreAssignments.equals("no")) {
                    break;
                } else {
                    System.out.println(RED+"INVALID INPUT! PLEASE ENTER 'yes' OR 'no'"+RESET);
                }
            }

        } while (moreAssignments.equalsIgnoreCase("yes"));

        while (!assignments.isEmpty()) {
            int[] assignment = assignments.poll();
            String query = "INSERT INTO officer_cases (officer_id, crime_id) VALUES (?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, assignment[0]);
                stmt.setInt(2, assignment[1]);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println(GREEN+"\t\t\tOFFICER ASSIGNED TO CRIME SUCCESSFULLY."+RESET);
                } else {
                    System.out.println(RED+"\t\t\tFAILED TO ASSIGN OFFICER TO CRIME."+RESET);
                }
            } catch (SQLException e) {
                System.err.println(RED+"\t\t\tERROR: " + e.getMessage()+RESET);
                throw e;
            }
            sc.close();
        }
    }

    // Utility method to retrieve a crime by its ID
    private Crime getCrimeById(int crimeId) throws SQLException {
        String query = "SELECT * FROM crime_records WHERE crime_id = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, crimeId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return new Crime(
                rs.getInt("crime_id"),
                rs.getString("name"),
                rs.getString("crime_type"),
                rs.getInt("age"),
                rs.getDate("arrest_date")
            );
        }
        return null;
    }
}
