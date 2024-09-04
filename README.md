# Crime Records Management System

The `Crime Records Management System` is a `console-based` application designed to facilitate the efficient management of Criminal as well as Officer records. The system allows the admin to perform various tasks, including managing officer and criminal records which includes add, update and delete, assigning officer to a crime, efficient searching of records and viewing Audit Logs.

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Usage](#usage)
- [Database Schema](#database-schema)
- [Usage Instructions](#usage-instructions)
- [Conclusion](#conclusion)

## Features

- **Criminal Management**: Add, update, delete, view, and search.
- **Officer Management**: Add, update, delete, view, assign to a crime, view audit logs and search.
- **Action Logging**: User and Admin's log in and logout are stored in a stack as well as in the database(with more details).
- **Search**: Both Admin and User can search for records more efficiently.
- **Error Messages**: Proper Error messsages are displayed for wrong inputs and unavailable data.
  
- **NOTE**: Here Admin is preinstalled in the database. So everytime you register, you register yourself as an User.


## System Requirements

### Software Requirements

- Java Development Kit (JDK) 8 or above
- Database Management System (XAMPP(MySQL))
- Java IDE (Eclipse, IntelliJ IDEA, or NetBeans)

### Hardware Requirements

- Processor: Intel Core i3 or above
- RAM: 4 GB or above
- Storage: 10 GB of free disk space

## Installation

1. **Clone the repository**:
    ```sh
    gh repo clone SARVESH2K6/Crime_Records_Management_System
    ```

2. **Navigate to the project directory**:
    ```sh
    cd src CRIME
    ```

3.**Installation of XAMPP**:
  - Search 'XAMPP download' in your web browser.
  - Install and allow all permissions.
  - Also download the .jar file `mysql-connector-j-9.0.0.jar`.
  - Add the file to the Refrenced libraries.

4. **Set up the database**:
    - Open Xammp control panel.
    - Start Apache and MySQL
    - Once MySQL has started running, click on Admin. It directs you to the localhost in your web browser.
    - Create a MySQL database named `crime_records`.
    - Now import the `crime_records.sql` file in the database.

5. **Compile and run the application**:
    You can directly run the code instead of using command prompt.

## Usage

### Admin Menu

1. **Crime Management**: Manage Criminal records.
2. **Officer Management**: Manage Officer records.
3. **Search Records**: Searching both Officer and Criminal records by name, age, (crime type and arrest date for criminals), (Rank and department for Officers).
4. **Assign Officer**: Assign officer to a crime.
5. **View Login History**: View all User's Login and Logout actions.
6. **View Audit Logs**: View all actions of both Admin and User along with Timestamp.
7. **Exit**: Exit the application.

### User Management

- **Register User**: Register as a User along with password verification.

  
## Database Schema

### Tables

```sql
CREATE TABLE `audit_logs` (
 `log_id` int(11) NOT NULL AUTO_INCREMENT,
 `user_id` int(11) NOT NULL,
 `action_type` varchar(50) NOT NULL,
 `action_description` text DEFAULT NULL,
 `action_time` timestamp NOT NULL DEFAULT current_timestamp(),
 PRIMARY KEY (`log_id`),
 KEY `user_id` (`user_id`),
 CONSTRAINT `audit_logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
);

CREATE TABLE `crime_records` (
 `crime_id` int(11) NOT NULL AUTO_INCREMENT,
 `name` varchar(100) NOT NULL,
 `crime_type` varchar(100) DEFAULT NULL,
 `age` int(11) DEFAULT NULL CHECK (`age` > 0),
 `arrest_date` date DEFAULT NULL,
 PRIMARY KEY (`crime_id`)
);

CREATE TABLE `officers` (
 `officer_id` int(11) NOT NULL AUTO_INCREMENT,
 `officer_name` varchar(100) NOT NULL,
 `rank` varchar(50) NOT NULL,
 `age` int(11) DEFAULT NULL CHECK (`age` > 0),
 `department` varchar(100) DEFAULT NULL,
 PRIMARY KEY (`officer_id`)
);

CREATE TABLE `officer_cases` (
 `officer_case_id` int(11) NOT NULL AUTO_INCREMENT,
 `officer_id` int(11) NOT NULL,
 `crime_id` int(11) NOT NULL,
 PRIMARY KEY (`officer_case_id`),
 KEY `officer_id` (`officer_id`),
 KEY `crime_id` (`crime_id`),
 CONSTRAINT `officer_cases_ibfk_1` FOREIGN KEY (`officer_id`) REFERENCES `officers` (`officer_id`) ON DELETE CASCADE,
 CONSTRAINT `officer_cases_ibfk_2` FOREIGN KEY (`crime_id`) REFERENCES `crime_records` (`crime_id`) ON DELETE CASCADE
);

CREATE TABLE `users` (
 `id` int(11) NOT NULL AUTO_INCREMENT,
 `username` varchar(50) NOT NULL,
 `password` varchar(255) NOT NULL,
 `role` enum('User','Admin') NOT NULL DEFAULT 'User',
 PRIMARY KEY (`id`),
 UNIQUE KEY `username` (`username`)
);
```

### Triggers for inserting logs into audit_logs table after Insert, Update, Delete operations on both Officer and Criminal Records
```sql
  CREATE TRIGGER `after_crime_delete` AFTER DELETE ON `crime_records`
 FOR EACH ROW BEGIN
    INSERT INTO audit_logs (user_id, action_type, action_description, action_time)
    VALUES (IFNULL(@current_user_id, 1), 'DELETE', CONCAT('Crime record deleted: ', OLD.name, ' (ID: ', OLD.crime_id, ')'), NOW());
END

CREATE TRIGGER `after_crime_insert` AFTER INSERT ON `crime_records`
 FOR EACH ROW BEGIN
    INSERT INTO audit_logs (user_id, action_type, action_description, action_time)
    VALUES (IFNULL(@current_user_id, 1), 'INSERT', CONCAT('New crime record added: ', NEW.name, ' (ID: ', NEW.crime_id, ')'), NOW());
END

CREATE TRIGGER `after_crime_update` AFTER UPDATE ON `crime_records`
 FOR EACH ROW BEGIN
    INSERT INTO audit_logs (user_id, action_type, action_description, action_time)
    VALUES (IFNULL(@current_user_id, 1), 'UPDATE', CONCAT('Crime record updated: ', OLD.name, ' (ID: ', OLD.crime_id, ')'), NOW());
END

CREATE TRIGGER `after_officer_delete` AFTER DELETE ON `officers`
 FOR EACH ROW BEGIN
    INSERT INTO audit_logs (user_id, action_type, action_description, action_time)
    VALUES (IFNULL(@current_user_id, 1), 'DELETE', CONCAT('Officer record deleted: ', OLD.officer_name, ' (ID: ', OLD.officer_id, ')'), NOW());
END

CREATE TRIGGER `after_officer_insert` AFTER INSERT ON `officers`
 FOR EACH ROW BEGIN
    INSERT INTO audit_logs (user_id, action_type, action_description, action_time)
    VALUES (IFNULL(@current_user_id, 1), 'INSERT', CONCAT('New officer added: ', NEW.officer_name, ' (ID: ', NEW.officer_id, ')'), NOW());
END

CREATE TRIGGER `after_officer_update` AFTER UPDATE ON `officers`
 FOR EACH ROW BEGIN
    INSERT INTO audit_logs (user_id, action_type, action_description, action_time)
    VALUES (IFNULL(@current_user_id, 1), 'UPDATE', CONCAT('Officer record updated: ', OLD.officer_name, ' (ID: ', OLD.officer_id, ')'), NOW());
END

```

# Usage Instructions
## Prerequisites
1. Ensure you have Java JDK 8 or above installed.
2. Ensure you have Xampp installed and running.
## Setup
1. Clone the repository
2. Create the `MySQL database` named `crime_records`.
3. Import the `crime_records.sql` file into the database.
4. Open the project in your preferred Java IDE.
5. Update the database connection settings in the `AbstractService` class if necessary.

## Running the Application
1. Run the `Main` class.
2. Follow the on-screen instructions to perform the desired actions.
3. **NOTE**: You will be registered as a user by default. To perform Admin actions, username:`Admin`, password:`password`.

# Conclusion
The Crime Records Management System will streamline the management of Officer and Criminal Records. The use of Java for backend development, a robust DBMS for data storage, and efficient data structures such as ArrayList, Stacks, and LinkedList will ensure the system is both reliable and scalable. The final system will be a console-based application with all outputs printed to the terminal, ensuring simplicity and ease of use.
