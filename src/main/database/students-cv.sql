CREATE DATABASE  IF NOT EXISTS `students_cv` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `students_cv`;
-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: students_cv
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authorities`
--

DROP TABLE IF EXISTS `authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `authorities` (
  `id` int(10) unsigned NOT NULL,
  `desc` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authorities`
--

LOCK TABLES `authorities` WRITE;
/*!40000 ALTER TABLE `authorities` DISABLE KEYS */;
INSERT INTO `authorities` VALUES (1,'ROLE_STUDENT'),(2,'ROLE_COMPANY'),(3,'ROLE_ADMIN');
/*!40000 ALTER TABLE `authorities` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `companies` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `company_name` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `units` bigint(20) DEFAULT NULL,
  `logo_path` varchar(250) DEFAULT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `usid_idx` (`user_id`),
  CONSTRAINT `usid` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companies`
--

LOCK TABLES `companies` WRITE;
/*!40000 ALTER TABLE `companies` DISABLE KEYS */;
INSERT INTO `companies` VALUES (7,'tasos@icap.gr','ICAP AE',90,'icap.jfif',47),(8,'tasos@agile.gr','Agile AE',NULL,NULL,49),(9,'tasos@cognity.gr','Cognity AE',NULL,NULL,52),(10,'tasos@google.gr','GOOGLE',NULL,NULL,55);
/*!40000 ALTER TABLE `companies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cvs`
--

DROP TABLE IF EXISTS `cvs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cvs` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `file_name` varchar(1500) NOT NULL,
  `file_download_uri` varchar(10000) NOT NULL,
  `file_type` varchar(1500) NOT NULL,
  `size` bigint(20) NOT NULL,
  `time_insert` datetime NOT NULL,
  `student_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `stid_idx` (`student_id`),
  CONSTRAINT `stid` FOREIGN KEY (`student_id`) REFERENCES `students` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cvs`
--

LOCK TABLES `cvs` WRITE;
/*!40000 ALTER TABLE `cvs` DISABLE KEYS */;
INSERT INTO `cvs` VALUES (42,'CV_Andrea_Comellini_light_version_v2 (1) (1).pdf','http://localhost:8080/pada/downloadFile/CV_Andrea_Comellini_light_version_v2%20(1)%20(1).pdf','application/pdf',298441,'2020-01-03 20:46:14',24),(43,'erg.txt','http://localhost:8080/pada/downloadFile/erg.txt','text/plain',1932,'2020-01-06 16:40:19',28),(44,'CV_Andrea_Comellini_light_version_v2 (1) (1) (1).pdf','http://localhost:8080/pada/downloadFile/CV_Andrea_Comellini_light_version_v2%20(1)%20(1)%20(1).pdf','application/pdf',298441,'2020-01-07 17:03:54',32);
/*!40000 ALTER TABLE `cvs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `students` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `firstname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `lastname` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `university_department` int(11) NOT NULL,
  `work_experience` tinyint(1) NOT NULL,
  `dob` date NOT NULL,
  `description` varchar(550) DEFAULT NULL,
  `mobile_phone` bigint(20) DEFAULT NULL,
  `image_path` varchar(250) DEFAULT NULL,
  `user_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `userId_idx` (`user_id`),
  CONSTRAINT `userId` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (24,'sotirinio@hotmail.com','Sotiris','Patsimas',1,0,'1997-04-04','I am very good at football !!! I love gym!',6936803783,'sotiris.jpg',48),(28,'billysdnlew@hotmail.com','Vasilis','Kamarados',1,0,'1993-01-13',NULL,6988762732,NULL,56),(29,'bolositsious@hotmail.com','Chris','Bolosis',1,0,'1991-01-08',NULL,6976974356,NULL,57),(30,'tasosbolosis@gmail.com','Tasos','Bolosis',1,0,'1997-01-13',NULL,6935677888,NULL,58),(31,'andri@hotmail.com','Andriana','Bolosi',1,1,'2007-01-21',NULL,6988114459,NULL,59),(32,'andreas-patsim@hotmail.com','Andreas','Patsimas',1,1,'1993-02-13','I love programming and analytical thinking!!!',6988116719,'andreas.jpg',60);
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `active` tinyint(1) NOT NULL,
  `time_insert` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (47,'icap','$2a$10$/6V78pmH6ZOrLujE4GZ0VOt5suYqE2dhPErxwl.d8dj3NBEz8IgOm',1,'2019-12-30 14:33:30'),(48,'sotiris','$2a$10$AsXrEddk44Tf5QQeI0f7mu.zoYyevSlrJr59UYugPGstp2EXsQeZ6',1,'2019-12-30 14:33:31'),(49,'agile','$2a$10$f5oNaITmSuzYLokyFGiwPOaLEqtkFd0.imvk4H6kfR8Gro/dtPYN6',1,'2019-12-30 15:44:45'),(52,'cognity','$2a$10$RDnxQikVpkUHAgVfypgkx.ySnAhrdQP/D09nbMJc0qquh/4qldfgq',1,'2019-12-31 15:46:22'),(55,'google','$2a$10$iBcjmsiNVOxrVthQ20oN8ujxTk.CnRyuJOYGEUfObqY5Iw0ZD8z7C',1,'2020-01-04 05:43:27'),(56,'billy','$2a$10$HM5GK228ZRh68wYWdXiioul09SYTEuNgmMlCxpu2eB3qz.F3aE5Bi',1,'2020-01-04 05:54:47'),(57,'chris','$2a$10$IOQF3bGpIwtZdbqMzgk88eFZjidPRpfLRXGEwur6.mjuy9JSf5Uti',1,'2020-01-04 05:55:47'),(58,'tasos','$2a$10$Hapj3d1VU6w686n.xlhEi.8ECvTy0xSCHsuUkUPSZHYlu.sKNYZ5G',1,'2020-01-04 05:56:40'),(59,'andriana','$2a$10$ZSi4e1HaRjWxG4k3uCD5IO/TXGHMK45BsEd7Sh7g7DOvYZpCa8kH.',1,'2020-01-04 05:57:55'),(60,'andreas3','$2a$10$la0GP.DldyomThwvXOw8nuHEt0TOnVThwRO71M7Cn5P4WS/7ixGTK',1,'2020-01-04 05:59:41');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_authorities`
--

DROP TABLE IF EXISTS `users_authorities`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users_authorities` (
  `user_id` int(11) unsigned NOT NULL,
  `authority_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`authority_id`),
  KEY `fkauthorityid_idx` (`authority_id`),
  CONSTRAINT `fkaid` FOREIGN KEY (`authority_id`) REFERENCES `authorities` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fkuid` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_authorities`
--

LOCK TABLES `users_authorities` WRITE;
/*!40000 ALTER TABLE `users_authorities` DISABLE KEYS */;
INSERT INTO `users_authorities` VALUES (48,1),(56,1),(57,1),(58,1),(59,1),(60,1),(47,2),(49,2),(52,2),(55,2);
/*!40000 ALTER TABLE `users_authorities` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-01-14 22:08:44
