-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 03, 2024 at 06:09 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `crime_records`
--

-- --------------------------------------------------------

--
-- Table structure for table `audit_logs`
--

CREATE TABLE `audit_logs` (
  `log_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `action_type` varchar(50) NOT NULL,
  `action_description` text DEFAULT NULL,
  `action_time` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `audit_logs`
--

INSERT INTO `audit_logs` (`log_id`, `user_id`, `action_type`, `action_description`, `action_time`) VALUES
(1, 1, 'Login', 'User logged in.', '2024-08-26 07:09:50'),
(2, 1, 'INSERT', 'New crime record added: Sanjay Roy (ID: 101)', '2024-08-26 07:10:26'),
(3, 1, 'Add Record', 'Added record with ID 101', '2024-08-26 07:10:26'),
(4, 1, 'INSERT', 'New officer added:  (ID: 101)', '2024-08-26 07:11:06'),
(5, 1, 'DELETE', 'Officer record deleted:  (ID: 101)', '2024-08-26 07:11:19'),
(6, 1, 'INSERT', 'New officer added: Amit Sharma (ID: 201)', '2024-08-26 07:40:26'),
(7, 1, 'INSERT', 'New officer added: Neha Patel (ID: 202)', '2024-08-26 07:40:26'),
(8, 1, 'INSERT', 'New officer added: Rajesh Singh (ID: 203)', '2024-08-26 07:40:26'),
(9, 1, 'INSERT', 'New officer added: Priya Kumari (ID: 204)', '2024-08-26 07:40:26'),
(10, 1, 'INSERT', 'New officer added: Vikram Singh (ID: 205)', '2024-08-26 07:40:26'),
(11, 1, 'INSERT', 'New officer added: Sneha Rao (ID: 206)', '2024-08-26 07:40:26'),
(12, 1, 'INSERT', 'New officer added: Ravi Kumar (ID: 207)', '2024-08-26 07:40:26'),
(13, 1, 'INSERT', 'New officer added: Anjali Mehta (ID: 208)', '2024-08-26 07:40:26'),
(14, 1, 'INSERT', 'New officer added: Karan Verma (ID: 209)', '2024-08-26 07:40:26'),
(15, 1, 'INSERT', 'New officer added: Ritika Sinha (ID: 210)', '2024-08-26 07:40:26'),
(16, 1, 'INSERT', 'New officer added: Manoj Agarwal (ID: 211)', '2024-08-26 07:40:26'),
(17, 1, 'INSERT', 'New officer added: Meera Joshi (ID: 212)', '2024-08-26 07:40:26'),
(18, 1, 'INSERT', 'New officer added: Deepak Rawat (ID: 213)', '2024-08-26 07:40:26'),
(19, 1, 'INSERT', 'New officer added: Kavita Singh (ID: 214)', '2024-08-26 07:40:26'),
(20, 1, 'INSERT', 'New officer added: Gaurav Choudhury (ID: 215)', '2024-08-26 07:40:26'),
(21, 1, 'DELETE', 'Crime record deleted: Sanjay Roy (ID: 101)', '2024-08-26 07:40:37'),
(22, 1, 'INSERT', 'New crime record added: Rajeev Kumar (ID: 3001)', '2024-08-26 07:40:56'),
(23, 1, 'INSERT', 'New crime record added: Sunita Yadav (ID: 3002)', '2024-08-26 07:40:56'),
(24, 1, 'INSERT', 'New crime record added: Arjun Reddy (ID: 3003)', '2024-08-26 07:40:56'),
(25, 1, 'INSERT', 'New crime record added: Maya Singh (ID: 3004)', '2024-08-26 07:40:56'),
(26, 1, 'INSERT', 'New crime record added: Deepak Sharma (ID: 3005)', '2024-08-26 07:40:56'),
(27, 1, 'INSERT', 'New crime record added: Ravi Deshmukh (ID: 3006)', '2024-08-26 07:40:56'),
(28, 1, 'INSERT', 'New crime record added: Aisha Khan (ID: 3007)', '2024-08-26 07:40:56'),
(29, 1, 'INSERT', 'New crime record added: Suresh Patil (ID: 3008)', '2024-08-26 07:40:56'),
(30, 1, 'INSERT', 'New crime record added: Anita Agarwal (ID: 3009)', '2024-08-26 07:40:56'),
(31, 1, 'INSERT', 'New crime record added: Pradeep Soni (ID: 3010)', '2024-08-26 07:40:56'),
(32, 1, 'INSERT', 'New crime record added: Pooja Mehta (ID: 3011)', '2024-08-26 07:40:56'),
(33, 1, 'INSERT', 'New crime record added: Vijay Kumar (ID: 3012)', '2024-08-26 07:40:56'),
(34, 1, 'INSERT', 'New crime record added: Seema Rao (ID: 3013)', '2024-08-26 07:40:56'),
(35, 1, 'INSERT', 'New crime record added: Gaurav Singh (ID: 3014)', '2024-08-26 07:40:56'),
(36, 1, 'INSERT', 'New crime record added: Neha Sharma (ID: 3015)', '2024-08-26 07:40:56'),
(37, 1, 'Login', 'User logged in.', '2024-08-26 07:41:16'),
(38, 1, 'UPDATE', 'Crime record updated: Deepak Sharma (ID: 3005)', '2024-08-26 07:41:38'),
(39, 1, 'Update Record', 'Updated record with ID 3005', '2024-08-26 07:41:38'),
(40, 1, 'Login', 'User logged in.', '2024-08-26 07:44:48'),
(41, 1, 'Login', 'User logged in.', '2024-08-26 07:53:37'),
(42, 1, 'Login', 'User logged in.', '2024-08-26 08:07:04'),
(43, 1, 'Login', 'User logged in.', '2024-08-26 08:08:06'),
(44, 1, 'Login', 'User logged in.', '2024-08-26 08:08:42'),
(45, 1, 'Login', 'User logged in.', '2024-08-26 08:11:25'),
(46, 1, 'Login', 'User logged in.', '2024-08-26 08:11:55'),
(47, 1, 'Login', 'User logged in.', '2024-08-26 08:14:02'),
(48, 1, 'Login', 'User logged in.', '2024-08-26 08:29:09'),
(49, 1, 'Login', 'User logged in.', '2024-08-26 08:36:06'),
(50, 2, 'Login', 'User logged in.', '2024-08-26 11:31:07'),
(51, 2, 'Login', 'User logged in.', '2024-08-26 11:36:26'),
(52, 2, 'Login', 'User logged in.', '2024-08-26 11:37:00'),
(53, 2, 'Login', 'User logged in.', '2024-08-26 11:37:32'),
(54, 2, 'Login', 'User logged in.', '2024-08-26 11:40:44'),
(55, 2, 'Login', 'User logged in.', '2024-08-26 11:43:53'),
(56, 2, 'Login', 'User logged in.', '2024-08-26 11:45:27'),
(57, 2, 'Login', 'User logged in.', '2024-08-26 11:49:57'),
(58, 1, 'Login', 'User logged in.', '2024-08-26 11:54:38'),
(59, 1, 'Login', 'User logged in.', '2024-08-26 11:56:07'),
(60, 1, 'INSERT', 'New officer added: Shravan Kumar (ID: 216)', '2024-08-26 11:56:40'),
(61, 2, 'Login', 'User logged in.', '2024-08-26 12:35:47'),
(62, 1, 'Login', 'User logged in.', '2024-08-26 19:32:50'),
(63, 1, 'Login', 'User logged in.', '2024-08-26 19:34:33'),
(64, 1, 'Login', 'User logged in.', '2024-08-27 06:32:16'),
(65, 1, 'Login', 'User logged in.', '2024-08-27 07:28:51'),
(66, 1, 'Login', 'User logged in.', '2024-08-30 06:27:32'),
(67, 1, 'INSERT', 'New crime record added: ankush (ID: 305)', '2024-08-30 06:29:04'),
(68, 1, 'Add Record', 'Added record with ID 305', '2024-08-30 06:29:04'),
(69, 1, 'UPDATE', 'Crime record updated: ankush (ID: 305)', '2024-08-30 06:29:38'),
(70, 1, 'Update Record', 'Updated record with ID 305', '2024-08-30 06:29:38'),
(71, 1, 'DELETE', 'Crime record deleted: ankush (ID: 305)', '2024-08-30 06:29:45'),
(72, 1, 'Delete Record', 'Deleted record with ID 305', '2024-08-30 06:29:45'),
(73, 1, 'Login', 'User logged in.', '2024-08-30 06:31:53'),
(74, 1, 'Logout', 'Admin logged out.', '2024-08-30 06:33:58'),
(75, 1, 'Login', 'User logged in.', '2024-08-30 07:16:43'),
(76, 1, 'Login', 'User logged in.', '2024-08-30 07:24:31'),
(77, 1, 'DELETE', 'Crime record deleted: Neha Sharma (ID: 3015)', '2024-08-30 07:24:48'),
(78, 1, 'Delete Record', 'Deleted record with ID 3015', '2024-08-30 07:24:48'),
(79, 1, 'DELETE', 'Officer record deleted: Gaurav Choudhury (ID: 215)', '2024-08-30 07:24:59'),
(80, 2, 'Login', 'User logged in.', '2024-08-30 08:38:28'),
(81, 4, 'Login', 'User logged in.', '2024-08-31 06:43:59'),
(82, 4, 'Login', 'User logged in.', '2024-08-31 06:45:58'),
(83, 4, 'Login', 'User logged in.', '2024-08-31 06:49:30'),
(84, 4, 'Logout', 'User logged out.', '2024-08-31 06:51:19'),
(85, 1, 'Login', 'User logged in.', '2024-08-31 06:51:32'),
(86, 1, 'INSERT', 'New officer added: ankush (ID: 217)', '2024-08-31 06:52:29'),
(87, 1, 'DELETE', 'Officer record deleted: ankush (ID: 217)', '2024-08-31 06:52:38'),
(88, 1, 'INSERT', 'New crime record added: ankush (ID: 3017)', '2024-08-31 06:54:27'),
(89, 1, 'Add Record', 'Added record with ID 3017', '2024-08-31 06:54:27'),
(90, 1, 'UPDATE', 'Crime record updated: ankush (ID: 3017)', '2024-08-31 06:55:31'),
(91, 1, 'Update Record', 'Updated record with ID 3017', '2024-08-31 06:55:31'),
(92, 1, 'DELETE', 'Crime record deleted: sarvesh (ID: 3017)', '2024-08-31 06:55:37'),
(93, 1, 'Delete Record', 'Deleted record with ID 3017', '2024-08-31 06:55:37'),
(94, 1, 'Delete Record', 'Deleted record with ID 3018', '2024-08-31 06:55:42'),
(95, 1, 'Login', 'User logged in.', '2024-08-31 07:27:55'),
(96, 1, 'Login', 'User logged in.', '2024-08-31 07:29:21'),
(97, 1, 'DELETE', 'Officer record deleted: Shravan Kumar (ID: 216)', '2024-08-31 07:29:29'),
(98, 1, 'Login', 'User logged in.', '2024-08-31 07:34:50'),
(99, 1, 'Login', 'User logged in.', '2024-08-31 09:28:48'),
(100, 1, 'INSERT', 'New crime record added: Sample (ID: 216)', '2024-08-31 09:29:46'),
(101, 1, 'Add Record', 'Added record with ID 216', '2024-08-31 09:29:46'),
(102, 1, 'Logout', 'Admin logged out.', '2024-08-31 09:35:34'),
(103, 5, 'Login', 'User logged in.', '2024-08-31 09:37:28'),
(104, 1, 'Login', 'User logged in.', '2024-08-31 09:39:05'),
(105, 1, 'Login', 'User logged in.', '2024-08-31 09:39:51');

-- --------------------------------------------------------

--
-- Table structure for table `crime_records`
--

CREATE TABLE `crime_records` (
  `crime_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `crime_type` varchar(100) DEFAULT NULL,
  `age` int(11) DEFAULT NULL CHECK (`age` > 0),
  `arrest_date` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `crime_records`
--

INSERT INTO `crime_records` (`crime_id`, `name`, `crime_type`, `age`, `arrest_date`) VALUES
(216, 'Sample', 'Kidnaping', 29, '2021-01-11'),
(3001, 'Rajeev Kumar', 'Robbery', 29, '2023-12-10'),
(3002, 'Sunita Yadav', 'Fraud', 34, '2023-11-15'),
(3003, 'Arjun Reddy', 'Assault', 40, '2023-10-20'),
(3004, 'Maya Singh', 'Theft', 25, '2023-09-25'),
(3005, 'Deepak Sharma', 'Rape,Murder', 45, '2023-08-30'),
(3006, 'Ravi Deshmukh', 'Drug Possession', 31, '2023-07-01'),
(3007, 'Aisha Khan', 'Kidnapping', 27, '2023-06-05'),
(3008, 'Suresh Patil', 'Robbery', 33, '2023-05-10'),
(3009, 'Anita Agarwal', 'Assault', 39, '2023-04-15'),
(3010, 'Pradeep Soni', 'Fraud', 41, '2023-03-20'),
(3011, 'Pooja Mehta', 'Theft', 26, '2023-02-25'),
(3012, 'Vijay Kumar', 'Murder', 50, '2023-01-30'),
(3013, 'Seema Rao', 'Drug Possession', 29, '2022-12-02'),
(3014, 'Gaurav Singh', 'Kidnapping', 34, '2022-11-05');

--
-- Triggers `crime_records`
--
DELIMITER $$
CREATE TRIGGER `after_crime_delete` AFTER DELETE ON `crime_records` FOR EACH ROW BEGIN
    INSERT INTO audit_logs (user_id, action_type, action_description, action_time)
    VALUES (IFNULL(@current_user_id, 1), 'DELETE', CONCAT('Crime record deleted: ', OLD.name, ' (ID: ', OLD.crime_id, ')'), NOW());
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_crime_insert` AFTER INSERT ON `crime_records` FOR EACH ROW BEGIN
    INSERT INTO audit_logs (user_id, action_type, action_description, action_time)
    VALUES (IFNULL(@current_user_id, 1), 'INSERT', CONCAT('New crime record added: ', NEW.name, ' (ID: ', NEW.crime_id, ')'), NOW());
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_crime_update` AFTER UPDATE ON `crime_records` FOR EACH ROW BEGIN
    INSERT INTO audit_logs (user_id, action_type, action_description, action_time)
    VALUES (IFNULL(@current_user_id, 1), 'UPDATE', CONCAT('Crime record updated: ', OLD.name, ' (ID: ', OLD.crime_id, ')'), NOW());
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `officers`
--

CREATE TABLE `officers` (
  `officer_id` int(11) NOT NULL,
  `officer_name` varchar(100) NOT NULL,
  `rank` varchar(50) NOT NULL,
  `age` int(11) DEFAULT NULL CHECK (`age` > 0),
  `department` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `officers`
--

INSERT INTO `officers` (`officer_id`, `officer_name`, `rank`, `age`, `department`) VALUES
(201, 'Amit Sharma', 'Inspector', 45, 'Delhi Police'),
(202, 'Neha Patel', 'Sub-Inspector', 38, 'Mumbai Police'),
(203, 'Rajesh Singh', 'Constable', 30, 'Kolkata Police'),
(204, 'Priya Kumari', 'Assistant Commissioner', 42, 'Bengaluru Police'),
(205, 'Vikram Singh', 'Deputy Commissioner', 50, 'Chennai Police'),
(206, 'Sneha Rao', 'Inspector', 35, 'Hyderabad Police'),
(207, 'Ravi Kumar', 'Sub-Inspector', 40, 'Jaipur Police'),
(208, 'Anjali Mehta', 'Constable', 28, 'Ahmedabad Police'),
(209, 'Karan Verma', 'Assistant Commissioner', 46, 'Pune Police'),
(210, 'Ritika Sinha', 'Deputy Commissioner', 52, 'Surat Police'),
(211, 'Manoj Agarwal', 'Inspector', 44, 'Lucknow Police'),
(212, 'Meera Joshi', 'Sub-Inspector', 36, 'Indore Police'),
(213, 'Deepak Rawat', 'Constable', 32, 'Kanpur Police'),
(214, 'Kavita Singh', 'Assistant Commissioner', 41, 'Agra Police');

--
-- Triggers `officers`
--
DELIMITER $$
CREATE TRIGGER `after_officer_delete` AFTER DELETE ON `officers` FOR EACH ROW BEGIN
    INSERT INTO audit_logs (user_id, action_type, action_description, action_time)
    VALUES (IFNULL(@current_user_id, 1), 'DELETE', CONCAT('Officer record deleted: ', OLD.officer_name, ' (ID: ', OLD.officer_id, ')'), NOW());
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_officer_insert` AFTER INSERT ON `officers` FOR EACH ROW BEGIN
    INSERT INTO audit_logs (user_id, action_type, action_description, action_time)
    VALUES (IFNULL(@current_user_id, 1), 'INSERT', CONCAT('New officer added: ', NEW.officer_name, ' (ID: ', NEW.officer_id, ')'), NOW());
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `after_officer_update` AFTER UPDATE ON `officers` FOR EACH ROW BEGIN
    INSERT INTO audit_logs (user_id, action_type, action_description, action_time)
    VALUES (IFNULL(@current_user_id, 1), 'UPDATE', CONCAT('Officer record updated: ', OLD.officer_name, ' (ID: ', OLD.officer_id, ')'), NOW());
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `officer_cases`
--

CREATE TABLE `officer_cases` (
  `officer_case_id` int(11) NOT NULL,
  `officer_id` int(11) NOT NULL,
  `crime_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `officer_cases`
--

INSERT INTO `officer_cases` (`officer_case_id`, `officer_id`, `crime_id`) VALUES
(1, 204, 3005),
(2, 201, 3003),
(3, 206, 3003),
(4, 201, 3006),
(6, 207, 3005),
(7, 205, 3006),
(10, 208, 3006),
(11, 205, 216);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` enum('User','Admin') NOT NULL DEFAULT 'User'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `username`, `password`, `role`) VALUES
(1, 'Admin', 'password', 'Admin'),
(2, 'Sarvesh', '203121', 'User'),
(3, 'Aditi', '011005', 'User'),
(4, 'rajvi', '12345', 'User'),
(5, 'officer1', '12345', 'User');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `audit_logs`
--
ALTER TABLE `audit_logs`
  ADD PRIMARY KEY (`log_id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `crime_records`
--
ALTER TABLE `crime_records`
  ADD PRIMARY KEY (`crime_id`);

--
-- Indexes for table `officers`
--
ALTER TABLE `officers`
  ADD PRIMARY KEY (`officer_id`);

--
-- Indexes for table `officer_cases`
--
ALTER TABLE `officer_cases`
  ADD PRIMARY KEY (`officer_case_id`),
  ADD KEY `officer_id` (`officer_id`),
  ADD KEY `crime_id` (`crime_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `username` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `audit_logs`
--
ALTER TABLE `audit_logs`
  MODIFY `log_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=106;

--
-- AUTO_INCREMENT for table `crime_records`
--
ALTER TABLE `crime_records`
  MODIFY `crime_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3018;

--
-- AUTO_INCREMENT for table `officers`
--
ALTER TABLE `officers`
  MODIFY `officer_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=218;

--
-- AUTO_INCREMENT for table `officer_cases`
--
ALTER TABLE `officer_cases`
  MODIFY `officer_case_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `audit_logs`
--
ALTER TABLE `audit_logs`
  ADD CONSTRAINT `audit_logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `officer_cases`
--
ALTER TABLE `officer_cases`
  ADD CONSTRAINT `officer_cases_ibfk_1` FOREIGN KEY (`officer_id`) REFERENCES `officers` (`officer_id`) ON DELETE CASCADE,
  ADD CONSTRAINT `officer_cases_ibfk_2` FOREIGN KEY (`crime_id`) REFERENCES `crime_records` (`crime_id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
