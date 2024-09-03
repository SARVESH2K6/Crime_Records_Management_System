package CRIME;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

class Crimes implements Colors {
    private Scanner sc = new Scanner(System.in);
    UserService userService;
    AuditService auditService;
    CrimeService crimeService;
    OfficerService officerService;
    User currentUser;
    public Crimes() {
        
        try {
            userService = new UserService();
            auditService = new AuditService();
            crimeService = new CrimeService();
            officerService = new OfficerService();
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, you can add more error handling logic here
        }
    }

    public static void main(String[] args) {
        new Crimes().start();
    }

    public void start() {
        while (true) {
            System.out.println(YELLOW + "CHOOSE AN OPTION:\t1. REGISTER \t2. LOGIN \t3. EXIT" + RESET);
            int choice = getIntegerInput();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    chooseRoleAndLogin();
                    break;
                case 3:
                    System.out.println(GREEN + "\t\t\tEXITING PORTAL..." + RESET);
                    return;
                default:
                    System.out.println(RED + "\t\t\tINVALID CHOICE! PLEASE TRY AGAIN" + RESET);
            }
        }
    }

    private void register() {
        try {
            System.out.print(YELLOW + "\t\t\tENTER USERNAME: " + RESET);
            String username = sc.nextLine();

            System.out.print(YELLOW + "\t\t\tENTER PASSWORD: " + RESET);
            String password = sc.nextLine();

            System.out.print(YELLOW + "\t\t\tCONFIRM PASSWORD: " + RESET);
            String confirmPassword = sc.nextLine();

            if (password.length() <= 4) {
                System.out.println(RED + "\t\t  PASSWORD MUST BE LONGER THAN 4 CHARACTERS." + RESET);
                return;
            }

            if (!password.equals(confirmPassword)) {
                System.out.println(RED + "\t\t  PASSWORD DOES NOT MATCH. TRY AGAIN." + RESET);
                return;
            }

            // Check if the username already exists
            if (userService.isUsernameTaken(username)) {
                System.out.println(RED + "\t\t  USERNAME ALREADY EXISTS. PLEASE CHOOSE A DIFFERENT NAME." + RESET);
                return;
            }

            if (userService.register(username, password)) {
                System.out.println(GREEN + "\t\t\tREGISTRATION SUCCESSFUL. WELCOME TO CRIME PORTAL" + RESET);
            } else {
                System.out.println(RED + "\t\t  REGISTRATION FAILED. PLEASE TRY AGAIN." + RESET);
            }
        } catch (Exception ex) {
            System.out.println(RED + "\t\t\tERROR: " + ex.getMessage() + RESET);
        }
    }

    private void chooseRoleAndLogin() {
        System.out.println(YELLOW + "SELECT ROLE:\t1. USER \t2. ADMIN" + RESET);
        int roleChoice = getIntegerInput();
    
        switch (roleChoice) {
            case 1:
                login("USER");  // Call with "USER" as argument
                break;
            case 2:
                login("ADMIN");  // Call with "ADMIN" as argument
                break;
            default:
                System.out.println(RED + "\t\t\tINVALID ROLE! PLEASE TRY AGAIN" + RESET);
        }
    }

    private void login(String role) {
        try {
            while (true) {
                System.out.print(YELLOW + "\t\t\tENTER USERNAME: " + RESET);
                String username = sc.nextLine();
                if(role=="USER"&&username.equalsIgnoreCase("Admin"))
                {
                    System.out.println(RED+"\t\t\tERROR. ENTER VALID CREDENTIALS"+RESET);
                    break;
                }
                else if(role=="ADMIN"&&!username.equalsIgnoreCase("Admin"))
                {
                    System.out.println(RED+"\t\t\tERROR. ENTER VALID CREDENTIALS"+RESET);
                    break;
                }
                System.out.print(YELLOW + "\t\t\tENTER PASSWORD: " + RESET);
                String password = sc.nextLine();

                User user = userService.login(username, password, role);
                if (user != null) {
                    currentUser = user;
                    System.out.println(GREEN + "\t\t\tLOGIN SUCCESSFUL. WELCOME TO CRIME PORTAL, " + username + RESET);
                    auditService.logAction(user.getId(), "Login", "User logged in.");
                    if (user.getRole().equals("Admin")) {
                        showAdminMenu(user);
                    } else {
                        showUserMenu(user);
                    }
                    return;
                } else {
                    System.out.println(RED + "\t\t INVALID CREDENTIALS. PLEASE TRY AGAIN" + RESET);
                }
            }
        } catch (Exception ex) {
            System.out.println(RED + "\t\t\tERROR: " + ex.getMessage() + RESET);
        }
    }
    private void logout(String role) {
        if (currentUser != null) {
            userService.logout(currentUser.getUsername(), role);  // Push logout action to stack with role
            System.out.println(GREEN + "\t\t\tLOGOUT SUCCESSFUL! GOODBYE " + currentUser.getUsername() + RESET);
            currentUser = null;  // Clear the current user after logout
        } else {
            System.out.println(RED + "\t\t\tNO USER IS CURRENTLY LOGGED IN!" + RESET);
        }
    }
    
    private void showUserMenu(User user) throws Exception {
        while (true) {
            System.out.println(YELLOW + "\n\t\t\t\t\tUSER MENU" + RESET);
            System.out.println(YELLOW + "CHOOSE AN OPTION: \n\t\t\t1. VIEW CRIMINALS \t\t2. VIEW OFFICERS \n\t\t\t3. SEARCH RECORDS \t\t4. LOGOUT" + RESET);
            int choice = getIntegerInput();

            switch (choice) {
                case 1:
                    viewCriminals();
                    break;
                case 2:
                    viewOfficers();
                    break;
                case 3:
                    searchRecords();
                    break;
                case 4:
                    logout(user.getRole());
                    System.out.println(GREEN + "LOGGING OUT..." + RESET);
                    auditService.logAction(user.getId(), "Logout", "User logged out.");
                    return;
                default:
                    System.out.println(RED + "\t\t\tINVALID CHOICE! PLEASE TRY AGAIN." + RESET);
            }
        }
    }

    private void showAdminMenu(User user) throws Exception {
        while (true) {
            System.out.println(YELLOW + "\n\t\t\t\t\tADMIN MENU" + RESET);
            System.out.println(YELLOW + "CHOOSE AN OPTION: \n\t\t\t1. ADD CRIMINAL RECORD \t\t\t8. ASSIGN OFFICERS TO CASE \n\t\t\t2. UPDATE CRIMINAL RECORD \t\t9. VIEW RECORDS \n\t\t\t3. DELETE CRIMINAL RECORD \t\t10. SEARCH RECORDS \n\t\t\t4. ADD OFFICER RECORD \t\t\t11. VIEW AUDIT LOGS \n\t\t\t5. UPDATE OFFICER RECORD \t\t12. VIEW LOGIN HISTORY \n\t\t\t6. DELETE OFFICER RECORD \t\t13. LOGOUT \n\t\t\t7. SEARCH OFFICERS BY CRIME ID" + RESET);
            int choice = getIntegerInput();
            
            switch (choice) {
                case 1:
                    addRecord(user);
                    break;
                case 2:
                    updateRecord(user);
                    break;
                case 3:
                    deleteRecord(user);
                    break;
                case 4:
                    addOfficer(user);
                    break;
                case 5:
                    updateOfficer(user);
                    break;
                case 6:
                    deleteOfficer(user);
                    break;
                case 7:
                    System.out.print(YELLOW+"\n\t\t\tENTER THE CRIME ID: "+RESET);
                    int CID=sc.nextInt();
                    crimeService.getOfficersByCrimeId(CID);
                    break;
                case 8:
                    crimeService.assignOfficerToCrime();
                    break;
                case 9:
                    viewRecords();
                    break;
                case 10:
                    searchRecords();
                    break;
                case 11:
                    auditService.viewAuditLogs();
                    break;
                case 12:
                    userService.viewLoginHistory();
                    break;
                case 13:
                    logout(user.getRole());
                    System.out.println(GREEN + "LOGGING OUT..." + RESET);
                    auditService.logAction(user.getId(), "Logout", "Admin logged out.");
                    return;  
                default:
                    System.out.println(RED + "\t\t\tINVALID CHOICE! PLEASE TRY AGAIN." + RESET);
            }
        }
    }

    private void addRecord(User user) {
        if (!user.getRole().equals("Admin")) {
            System.out.println(RED + "\t\t\tPERMISSION DENIED. ONLY ADMIN CAN ADD RECORDS." + RESET);
            return;
        }

        try {
            System.out.print(YELLOW + "\t\t\tENTER CRIME ID: " + RESET);
            int crimeId = getIntegerInput();
            if(crimeId<0)
            {
                System.out.println(RED+"\t\t\tCRIME ID MUST BE GREATER THAN 0."+RESET);
                return;
            }

            System.out.print(YELLOW + "\t\t\tENTER CRIMINAL NAME: " + RESET);
            String name = sc.nextLine();

            System.out.print(YELLOW + "\t\t\tENTER CRIME TYPE: " + RESET);
            String crimeType = sc.nextLine();

            System.out.print(YELLOW + "\t\t\tENTER AGE: " + RESET);
            int age = getIntegerInput();
            if(age<0)
            {
                System.out.println(RED+"\t\t\tAGE MUST BE GREATER THAN 0."+RESET);
                return;
            }

            System.out.print(YELLOW + "\t\t\tENTER DATE OF ARREST (YYYY-MM-DD): " + RESET);
            String arrestDate = sc.nextLine();
            Date date = Date.valueOf(arrestDate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate givenDate = LocalDate.parse(arrestDate, formatter);
            LocalDate currentDate = LocalDate.now();
            if(givenDate.isAfter(currentDate)) {
                System.out.println(RED+"\t\t\tDATE OF ARREST MUST BE BEFORE CURRENT DATE."+RESET);
                return;
            }

            crimeService.addCrimeRecord(crimeId, name, crimeType, age, date);
            System.out.println(GREEN + "\t\t\tRECORD ADDED SUCCESSFULLY." + RESET);
            auditService.logAction(user.getId(), "Add Record", "Added record with ID " + crimeId);
        } catch (Exception ex) {
            System.out.println(RED + "\t\t\tERROR: " + ex.getMessage() + RESET);
        }
    }
    private void addOfficer(User user) throws Exception {
        if (!user.getRole().equals("Admin")) {
            System.out.println(RED + "\t\t\tPERMISSION DENIED. ONLY ADMIN CAN ADD RECORDS." + RESET);
            return;
        }
        System.out.print("Enter Officer ID: ");
        int officerId = getIntegerInput();
        if(officerId<0)
            {
                System.out.println(RED+"\t\t\tCRIME ID MUST BE GREATER THAN 0."+RESET);
                return;
            }
        System.out.print("Enter Officer Name: ");
        String officerName = sc.nextLine();
        System.out.print("Enter Officer Rank: ");
        String rank = sc.nextLine();
        System.out.print("Enter Officer Age: ");
        int age = getIntegerInput();
        if(age<0)
            {
                System.out.println(RED+"\t\t\tAGE MUST BE GREATER THAN 0."+RESET);
                return;
            }
        System.out.print("Enter Department: ");
        String department = sc.nextLine();
        officerService.addOfficer(officerId, officerName, rank, age, department);
    }

    private void deleteOfficer(User user) throws Exception {
        if (!user.getRole().equals("Admin")) {
            System.out.println(RED + "\t\t\tPERMISSION DENIED. ONLY ADMIN CAN DELETE RECORDS." + RESET);
            return;
        }
        System.out.print("Enter Officer ID to delete: ");
        int officerId = sc.nextInt();
        officerService.deleteOfficer(officerId);
    }

    private void updateOfficer(User user) {
        if (!user.getRole().equals("Admin")) {
            System.out.println(RED + "\t\t\tPERMISSION DENIED. ONLY ADMIN CAN UPDATE RECORDS." + RESET);
            return;
        }

        try {
            System.out.print(YELLOW + "\t\t\tENTER OFFICER ID TO UPDATE: " + RESET);
            int OffId = getIntegerInput();

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crime_records", "root", "");
            String checkOffIdQuery = "SELECT COUNT(*) FROM officers WHERE officer_id = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkOffIdQuery);
            checkStatement.setInt(1, OffId);
            ResultSet rs = checkStatement.executeQuery();
            rs.next();

            if (rs.getInt(1) == 0) { // If OfficerId is not found
                System.out.println(RED + "\t\t\tOFFICER ID NOT FOUND. PLEASE ENTER A VALID OFFICER ID." + RESET);
                return;
            }

            System.out.println(YELLOW + "CHOOSE AN OPTION: \n1. UPDATE NAME \n2. UPDATE RANK \n3. UPDATE AGE \n4. UPDATE DEPARTMENT" + RESET);
        int updateChoice = getIntegerInput();

        String name = null, rank = null, department = null;
        Integer age = null;

        switch (updateChoice) {
            case 1:
                System.out.print(YELLOW + "ENTER NEW OFFICER NAME: " + RESET);
                name = sc.nextLine();
                break;
            case 2:
                System.out.print(YELLOW + "ENTER NEW RANK: " + RESET);
                rank = sc.nextLine();
                break;
            case 3:
                System.out.print(YELLOW + "ENTER NEW AGE: " + RESET);
                age = getIntegerInput();
                break;
            case 4:
                System.out.print(YELLOW + "ENTER NEW DEPARTMENT: " + RESET);
                department = sc.nextLine();
                break;
            default:
                System.out.println(RED + "INVALID CHOICE!" + RESET);
                return;
        }

        officerService.updateOfficer(OffId, name, rank, age, department);
        }
        catch(Exception ex) {
            System.out.println(RED + "\t\t\tERROR: " + ex.getMessage() + RESET);
        }
    }

    private void updateRecord(User user) {
        if (!user.getRole().equals("Admin")) {
            System.out.println(RED + "\t\t\tPERMISSION DENIED. ONLY ADMIN CAN UPDATE RECORDS." + RESET);
            return;
        }

        try {
            System.out.print(YELLOW + "\t\t\tENTER CRIME ID TO UPDATE: " + RESET);
            int crimeId = getIntegerInput();

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/crime_records", "root", "");
            String checkCrimeIdQuery = "SELECT COUNT(*) FROM crime_records WHERE crime_id = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkCrimeIdQuery);
            checkStatement.setInt(1, crimeId);
            ResultSet rs = checkStatement.executeQuery();
            rs.next();

            if (rs.getInt(1) == 0) { // If crimeId is not found
                System.out.println(RED + "\t\t\tCRIME ID NOT FOUND. PLEASE ENTER A VALID CRIME ID." + RESET);
                return;
            }

            System.out.println(YELLOW + "CHOOSE AN OPTION: \n\t\t\t1. UPDATE NAME \t\t\t2. UPDATE CRIME TYPE \n\t\t\t3. UPDATE AGE OF CRIMINAL \t\t4. UPDATE DATE OF ARREST " + RESET);
            int updateChoice = getIntegerInput();

            String name = null, crimeType = null;
            Integer age = null;
            Date date = null;

            switch (updateChoice) {
                case 1:
                    System.out.print(YELLOW + "\t\t\tENTER NEW CRIMINAL NAME: " + RESET);
                    name = sc.nextLine();
                    break;
                case 2:
                    System.out.print(YELLOW + "\t\t\tENTER NEW CRIME TYPE: " + RESET);
                    crimeType = sc.nextLine();
                    break;
                case 3:
                    System.out.print(YELLOW + "\t\t\tENTER NEW AGE: " + RESET);
                    age = getIntegerInput();
                    break;
                case 4:
                    System.out.print(YELLOW + "\t\t\tENTER NEW DATE OF ARREST (YYYY-MM-DD): " + RESET);
                    String arrestDate = sc.nextLine();
                    date = Date.valueOf(arrestDate);
                    break;
                default:
                    System.out.println(RED + "\t\t\tINVALID CHOICE!" + RESET);
                    return;
            }

            crimeService.updateCrimeRecord(crimeId, name, crimeType, age, date);
            System.out.println(GREEN + "\t\t\tRECORD UPDATED SUCCESSFULLY." + RESET);
            auditService.logAction(user.getId(), "Update Record", "Updated record with ID " + crimeId);
        } catch (Exception ex) {
            System.out.println(RED + "\t\t\tERROR: " + ex.getMessage() + RESET);
        }
    }

    private void deleteRecord(User user) {
        if (!user.getRole().equals("Admin")) {
            System.out.println(RED + "\t\t\tPERMISSION DENIED. ONLY ADMIN CAN DELETE RECORDS." + RESET);
            return;
        }

        try {
            System.out.print(YELLOW + "\t\t\tENTER CRIME ID TO DELETE: " + RESET);
            int crimeId = getIntegerInput();

            crimeService.deleteCrimeRecord(crimeId);
            System.out.println(GREEN + "\t\t\tRECORD DELETED SUCCESSFULLY." + RESET);
            auditService.logAction(user.getId(), "Delete Record", "Deleted record with ID " + crimeId);
        } catch (Exception ex) {
            System.out.println(RED + "\t\t\tERROR: " + ex.getMessage() + RESET);
        }
    }

    private void viewRecords() {
        try {
            System.out.println(YELLOW + "CHOOSE AN OPTION: \n\t\t\t1. VIEW CRIMINALS \t\t2. VIEW OFFICERS" + RESET);
            int choice = getIntegerInput();

            switch (choice) {
                case 1:
                    viewCriminals();
                    break;
                case 2:
                    viewOfficers();
                    break;
                default:
                    System.out.println(RED + "\t\t\tINVALID CHOICE!" + RESET);
            }
        } catch (Exception ex) {
            System.out.println(RED + "\t\t\tERROR: " + ex.getMessage() + RESET);
        }
    }

    private void viewCriminals() {
        final int LIMIT = 5; // Number of records to display per page
        int offset = 0;
        while (true) {
            try {
                List<Crime> crimes = crimeService.getAllCriminals(LIMIT, offset);
                if (crimes.isEmpty()) {
                    if (offset == 0) {
                        System.out.println(RED + "\t\t\tNO CRIMINAL RECORDS FOUND." + RESET);
                    } else {
                        System.out.println(RED + "\t\t\tNO MORE RECORDS TO DISPLAY." + RESET);
                        System.out.println("\nEnter 'n' for next page, 'p' for previous page, or 'q' to quit:");
                    }
                    offset -= LIMIT; // Step back to the previous valid page
                } else {
                    System.out.println(GREEN + "CRIMINAL RECORDS:" + RESET);
                    for (Crime crime : crimes) {
                        System.out.println("ID: " + crime.getCrimeId() + ", Name: " + crime.getName() + ", Crime Type: " + crime.getCrimeType() + ", Age: " + crime.getAge() + ", Arrest Date: " + crime.getArrestDate());
                    }
    
                    System.out.println("\nEnter 'n' for next page, 'p' for previous page, or 'q' to quit:");
                }
    
                String input = sc.nextLine().trim().toLowerCase();
    
                if (input.equals("n")) {
                    offset += LIMIT;
                } else if (input.equals("p")) {
                    if (offset > 0) offset -= LIMIT;
                } else if (input.equals("q")) {
                    break;
                } else {
                    System.out.println(RED + "\t\t\tINVALID INPUT. Please try again." + RESET);
                }
    
            } catch (Exception ex) {
                System.out.println(RED + "\t\t\tERROR: " + ex.getMessage() + RESET);
                return;
            }
        }
    }
    private void viewOfficers() {
        final int LIMIT = 5; // Number of records to display per page
        int offset = 0;
    
        while (true) {
            try {
                List<Officer> officers = officerService.getAllOfficers(LIMIT, offset);
                if (officers.isEmpty()) {
                    if (offset == 0) {
                        System.out.println(RED + "\t\t\tNO OFFICER RECORDS FOUND." + RESET);
                    } else {
                        System.out.println(RED + "\t\t\tNO MORE RECORDS TO DISPLAY." + RESET);
                    }
                    offset -= LIMIT; // Step back to the previous valid page
                    if (offset < 0) offset = 0; // Ensure offset doesn't go negative
                } else {
                    System.out.println(GREEN + "OFFICER RECORDS:" + RESET);
                    for (Officer officer : officers) {
                        System.out.println("ID: " + officer.getOfficerId() + ", Name: " + officer.getName() + ", Rank: " + officer.getRank() + ", Age: " + officer.getAge() + ", Department: " + officer.getDepartment());
                    }
    
                    System.out.println("\nEnter 'n' for next page, 'p' for previous page, or 'q' to quit:");
                }
    
                String input = sc.nextLine().trim().toLowerCase();
    
                if (input.equals("n")) {
                    offset += LIMIT;
                } else if (input.equals("p")) {
                    if (offset > 0) offset -= LIMIT;
                } else if (input.equals("q")) {
                    break;
                } else {
                    System.out.println(RED + "\t\t\tINVALID INPUT. Please try again." + RESET);
                }
    
            } catch (Exception ex) {
                System.out.println(RED + "\t\t\tERROR: " + ex.getMessage() + RESET);
                return;
            }
        }
    }    

    private void searchRecords() {
        try {
            System.out.println(YELLOW + "CHOOSE AN OPTION: \n\t\t\t1. SEARCH CRIMINALS \t\t2. SEARCH OFFICERS" + RESET);
            int choice = getIntegerInput();
    
            switch (choice) {
                case 1:
                    System.out.println(YELLOW + "SEARCH CRIMINALS BY: \n\t\t\t1. NAME \t\t2. AGE \n\t\t\t3. CRIME \t\t4. ARREST DATE" + RESET);
                    int criminalSearchChoice = getIntegerInput();
                    System.out.print(YELLOW + "\t\t\tENTER SEARCH TERM: " + RESET);
                    String criminalSearchTerm = sc.nextLine();
                    searchCriminals(criminalSearchChoice, criminalSearchTerm);
                    break;
                case 2:
                    System.out.println(YELLOW + "SEARCH OFFICERS BY: \n\t\t\t1. NAME \t\t2. AGE \n\t\t\t3. RANK \t\t4. DEPARTMENT" + RESET);
                    int officerSearchChoice = getIntegerInput();
                    System.out.print(YELLOW + "\t\t\tENTER SEARCH TERM: " + RESET);
                    String officerSearchTerm = sc.nextLine();
                    searchOfficers(officerSearchChoice, officerSearchTerm);
                    break;
                default:
                    System.out.println(RED + "\t\t\tINVALID CHOICE!" + RESET);
            }
        } catch (Exception ex) {
            System.out.println(RED + "\t\t\tERROR: " + ex.getMessage() + RESET);
        }
    }
    
    private void searchCriminals(int searchChoice, String searchTerm) {
        System.out.println(YELLOW + "CHOOSE SORTING OPTION: \n\t\t\t1. NAME \t\t2. AGE \n\t\t\t3. CRIME \t\t4. ARREST DATE" + RESET);
        String sortOption = sc.nextLine();  // Get sorting choice from user
    
        try {
            List<Crime> crimes = crimeService.searchCriminals(searchChoice, searchTerm, sortOption);
            if (crimes.isEmpty()) {
                System.out.println(RED + "\t\t\tNO CRIMINAL RECORDS FOUND FOR TERM: " + searchTerm + RESET);
                return;
            }
    
            System.out.println(GREEN + "\t\t\tSEARCH RESULTS:" + RESET);
            for (Crime crime : crimes) {
                System.out.println(GREEN+"\t\t\tID: " + crime.getCrimeId() + "|| NAME: " + crime.getName() + "|| CRIME TYPE: " + crime.getCrimeType() + "|| AGE: " + crime.getAge() + "|| ARREST DATE: " + crime.getArrestDate()+RESET);
            }
        } catch (Exception ex) {
            System.out.println(RED + "\t\t\tERROR: " + ex.getMessage() + RESET);
        }
    }
    
    private void searchOfficers(int searchChoice, String searchTerm) {
        System.out.println(YELLOW + "CHOOSE SORTING OPTION: \n\t\t\t1. NAME \t\t2. AGE \n\t\t\t3. RANK \t\t4. DEPARTMENT" + RESET);
        String sortOption = sc.nextLine();  // Get sorting choice from user
    
        try {
            List<Officer> officers = officerService.searchOfficers(searchChoice, searchTerm, sortOption);
            if (officers.isEmpty()) {
                System.out.println(RED + "\t\t\tNO OFFICER RECORDS FOUND FOR TERM: " + searchTerm + RESET);
                return;
            }
    
            System.out.println(YELLOW + "\t\t\tSEARCH RESULTS:" + RESET);
            for (Officer officer : officers) {
                System.out.println(GREEN+"\t\t\tID: " + officer.getOfficerId() + "|| NAME: " + officer.getName() + "|| RANK: " + officer.getRank() + "|| DEPARTMENT: " + officer.getDepartment()+RESET);
            }
        } catch (Exception ex) {
            System.out.println(RED + "\t\t\tERROR: " + ex.getMessage() + RESET);
        }
    }
    
    private int getIntegerInput() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println(RED + "\t\t\tINVALID INPUT. PLEASE ENTER A NUMBER." + RESET);
            }
        }
    }
}