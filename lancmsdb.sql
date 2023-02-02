-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 12, 2018 at 02:37 PM
-- Server version: 10.1.29-MariaDB
-- PHP Version: 7.1.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `lancmsdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `departement`
--

CREATE TABLE `departement` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `description` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `departement`
--

INSERT INTO `departement` (`id`, `nom`, `description`) VALUES
(1, 'IFA', 'Informatique Fondamental et Ses Application');

-- --------------------------------------------------------

--
-- Table structure for table `enseignant`
--

CREATE TABLE `enseignant` (
  `id_utilisateur` int(11) NOT NULL,
  `id_enseignant` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `enseignant`
--

INSERT INTO `enseignant` (`id_utilisateur`, `id_enseignant`) VALUES
(26, 0);

-- --------------------------------------------------------

--
-- Table structure for table `enseigner`
--

CREATE TABLE `enseigner` (
  `id` int(11) NOT NULL,
  `id_groupe` int(11) NOT NULL,
  `id_module` int(11) NOT NULL,
  `id_enseignant` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `enseigner`
--

INSERT INTO `enseigner` (`id`, `id_groupe`, `id_module`, `id_enseignant`) VALUES
(10, 55, 2, 26),
(13, 55, 1, 26);

-- --------------------------------------------------------

--
-- Table structure for table `etudiant`
--

CREATE TABLE `etudiant` (
  `num_carte` int(11) NOT NULL,
  `id_utilisateur` int(11) NOT NULL,
  `id_groupe` int(11) NOT NULL,
  `espace_de_travail` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `etudiant`
--

INSERT INTO `etudiant` (`num_carte`, `id_utilisateur`, `id_groupe`, `espace_de_travail`) VALUES
(1414, 56, 55, 'C:\\Users\\MedTaki\\workspace JavaFX');

-- --------------------------------------------------------

--
-- Table structure for table `fichier`
--

CREATE TABLE `fichier` (
  `id` int(11) NOT NULL,
  `id_tp` int(11) DEFAULT NULL,
  `id_type_fichier` int(11) DEFAULT NULL,
  `id_enseigner` int(11) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `chemin` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `fichier`
--

INSERT INTO `fichier` (`id`, `id_tp`, `id_type_fichier`, `id_enseigner`, `nom`, `chemin`) VALUES
(60, NULL, 0, 10, 'TP_RIP_Partie_III.pdf', 'C:\\Users\\MedTaki\\Desktop\\3eme SCI\\S1\\RIP\\TPs\\enonce\\TP_RIP_Partie_III.pdf'),
(63, NULL, 0, 10, '34334503_2022096938043616_6421525962118135808_n.jpg', 'C:\\Users\\MedTaki\\Pictures\\34334503_2022096938043616_6421525962118135808_n.jpg'),
(64, NULL, 2, 10, 'music1.zpl', 'C:\\Users\\MedTaki\\Music\\Playlists\\music1.zpl'),
(65, NULL, 2, 10, '6704090c-7dc9-4741-9e05-d7b85feb08f6.psd', 'C:\\Users\\MedTaki\\Pictures\\6704090c-7dc9-4741-9e05-d7b85feb08f6.psd'),
(66, NULL, 0, 13, '29019560_616998678637565_162195821_n.png', 'C:\\Users\\MedTaki\\Documents\\29019560_616998678637565_162195821_n.png'),
(67, 60, 1, 10, 'lastVersion.sql', 'C:\\Users\\MedTaki\\Documents\\lastVersion.sql');

-- --------------------------------------------------------

--
-- Table structure for table `groupe`
--

CREATE TABLE `groupe` (
  `id` int(11) NOT NULL,
  `num_groupe` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `groupe`
--

INSERT INTO `groupe` (`id`, `num_groupe`) VALUES
(55, 1);

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

CREATE TABLE `message` (
  `id` int(11) NOT NULL,
  `destination` int(11) DEFAULT NULL,
  `source` int(11) DEFAULT NULL,
  `id_fichier` int(11) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `contenue` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id`, `destination`, `source`, `id_fichier`, `date_time`, `contenue`) VALUES
(134, NULL, 26, 60, '2018-06-09 17:29:57', NULL),
(139, NULL, 26, 63, '2018-06-10 04:31:53', NULL),
(140, NULL, 26, 64, '2018-06-10 15:17:54', NULL),
(141, NULL, 26, 65, '2018-06-10 15:18:07', NULL),
(143, NULL, 26, 66, '2018-06-11 05:26:58', NULL),
(144, NULL, 56, 67, '2018-06-12 12:33:02', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `module`
--

CREATE TABLE `module` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `description` text,
  `id_spécialité` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `module`
--

INSERT INTO `module` (`id`, `nom`, `description`, `id_spécialité`) VALUES
(1, 'SE', 'Systéme d\' Exploitation', 1),
(2, 'RIP', 'RéseauIP', 1),
(3, 'ACS', 'ASC', 2);

-- --------------------------------------------------------

--
-- Table structure for table `spécialité`
--

CREATE TABLE `spécialité` (
  `id` int(11) NOT NULL,
  `abv` varchar(255) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `description` text,
  `id_departement` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `spécialité`
--

INSERT INTO `spécialité` (`id`, `abv`, `nom`, `description`, `id_departement`) VALUES
(1, 'SCI', 'Science Informatique', 'Science Informatique', 1),
(2, 'TI', 'Technologie Informatique', 'Technologie Informatique', 1);

-- --------------------------------------------------------

--
-- Table structure for table `type_fichier`
--

CREATE TABLE `type_fichier` (
  `id` int(11) NOT NULL,
  `type` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `type_fichier`
--

INSERT INTO `type_fichier` (`id`, `type`) VALUES
(0, 'TP'),
(1, 'Projet'),
(2, 'Others');

-- --------------------------------------------------------

--
-- Table structure for table `utilisateurs`
--

CREATE TABLE `utilisateurs` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `prenom` varchar(255) NOT NULL,
  `nom_utilisateur` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `utilisateurs`
--

INSERT INTO `utilisateurs` (`id`, `nom`, `prenom`, `nom_utilisateur`, `email`, `password`) VALUES
(26, 'Mohammed Takieddine', 'MOULATI', 'MEDTAki', 'medmoulati@gmai.com', 'med'),
(56, 'Hamza', 'Hamza', 'za', 'za', 'za');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `departement`
--
ALTER TABLE `departement`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `enseignant`
--
ALTER TABLE `enseignant`
  ADD PRIMARY KEY (`id_utilisateur`),
  ADD UNIQUE KEY `id_utilisateur` (`id_utilisateur`),
  ADD UNIQUE KEY `id_enseignant` (`id_enseignant`);

--
-- Indexes for table `enseigner`
--
ALTER TABLE `enseigner`
  ADD PRIMARY KEY (`id`),
  ADD KEY `enseignant_enseigner` (`id_enseignant`),
  ADD KEY `groupe_enseigner` (`id_groupe`),
  ADD KEY `module_enseigner` (`id_module`);

--
-- Indexes for table `etudiant`
--
ALTER TABLE `etudiant`
  ADD PRIMARY KEY (`num_carte`),
  ADD UNIQUE KEY `id_utilisateur` (`id_utilisateur`),
  ADD KEY `groupe_etudiant` (`id_groupe`);

--
-- Indexes for table `fichier`
--
ALTER TABLE `fichier`
  ADD PRIMARY KEY (`id`),
  ADD KEY `tp_fichier` (`id_tp`),
  ADD KEY `type_fichier_fichier` (`id_type_fichier`),
  ADD KEY `enseigner_fichier` (`id_enseigner`);

--
-- Indexes for table `groupe`
--
ALTER TABLE `groupe`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `message`
--
ALTER TABLE `message`
  ADD PRIMARY KEY (`id`),
  ADD KEY `utilisateur_message_2` (`destination`),
  ADD KEY `utilisateur2_message` (`source`),
  ADD KEY `fichier_message` (`id_fichier`);

--
-- Indexes for table `module`
--
ALTER TABLE `module`
  ADD PRIMARY KEY (`id`),
  ADD KEY `spécialité_module` (`id_spécialité`);

--
-- Indexes for table `spécialité`
--
ALTER TABLE `spécialité`
  ADD PRIMARY KEY (`id`),
  ADD KEY `deepartement_spécialité` (`id_departement`);

--
-- Indexes for table `type_fichier`
--
ALTER TABLE `type_fichier`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `departement`
--
ALTER TABLE `departement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `enseigner`
--
ALTER TABLE `enseigner`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `fichier`
--
ALTER TABLE `fichier`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=68;

--
-- AUTO_INCREMENT for table `groupe`
--
ALTER TABLE `groupe`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- AUTO_INCREMENT for table `message`
--
ALTER TABLE `message`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=145;

--
-- AUTO_INCREMENT for table `module`
--
ALTER TABLE `module`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `spécialité`
--
ALTER TABLE `spécialité`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `utilisateurs`
--
ALTER TABLE `utilisateurs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `enseignant`
--
ALTER TABLE `enseignant`
  ADD CONSTRAINT `utilisateur_enseignant` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateurs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `enseigner`
--
ALTER TABLE `enseigner`
  ADD CONSTRAINT `enseignant_enseigner` FOREIGN KEY (`id_enseignant`) REFERENCES `enseignant` (`id_utilisateur`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `groupe_enseigner` FOREIGN KEY (`id_groupe`) REFERENCES `groupe` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `module_enseigner` FOREIGN KEY (`id_module`) REFERENCES `module` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `etudiant`
--
ALTER TABLE `etudiant`
  ADD CONSTRAINT `groupe_etudiant` FOREIGN KEY (`id_groupe`) REFERENCES `groupe` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `utilisateur_etudiant` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateurs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `fichier`
--
ALTER TABLE `fichier`
  ADD CONSTRAINT `enseigner_fichier` FOREIGN KEY (`id_enseigner`) REFERENCES `enseigner` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tp_fichier` FOREIGN KEY (`id_tp`) REFERENCES `fichier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `type_fichier_fichier` FOREIGN KEY (`id_type_fichier`) REFERENCES `type_fichier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `fichier_message` FOREIGN KEY (`id_fichier`) REFERENCES `fichier` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `utilisateur2_message` FOREIGN KEY (`source`) REFERENCES `utilisateurs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `utilisateur_message` FOREIGN KEY (`destination`) REFERENCES `utilisateurs` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `module`
--
ALTER TABLE `module`
  ADD CONSTRAINT `spécialité_module` FOREIGN KEY (`id_spécialité`) REFERENCES `spécialité` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `spécialité`
--
ALTER TABLE `spécialité`
  ADD CONSTRAINT `deepartement_spécialité` FOREIGN KEY (`id_departement`) REFERENCES `departement` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
