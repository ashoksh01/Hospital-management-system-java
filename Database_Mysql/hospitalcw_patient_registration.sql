-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hospitalcw
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `patient_registration`
--

DROP TABLE IF EXISTS `patient_registration`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient_registration` (
  `id` int NOT NULL AUTO_INCREMENT,
  `firstname` varchar(45) DEFAULT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `dos` varchar(45) DEFAULT NULL,
  `admitted_date` varchar(45) DEFAULT NULL,
  `medical_history` varchar(45) DEFAULT 'not assigned yet',
  `description` varchar(45) DEFAULT NULL,
  `doctor_id` int NOT NULL,
  `nurse_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_patient_registration_user_registration_idx` (`doctor_id`),
  KEY `fk_patient_registration_user_registration1_idx` (`nurse_id`),
  CONSTRAINT `fk_patient_registration_user_registration` FOREIGN KEY (`doctor_id`) REFERENCES `user_registration` (`id`),
  CONSTRAINT `fk_patient_registration_user_registration1` FOREIGN KEY (`nurse_id`) REFERENCES `user_registration` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient_registration`
--

LOCK TABLES `patient_registration` WRITE;
/*!40000 ALTER TABLE `patient_registration` DISABLE KEYS */;
INSERT INTO `patient_registration` VALUES (1,'ashok','shrestha','a','aaa','non','asa',1,3),(2,'d','fdd','fdf','ff','non','tht55rt',4,3),(3,'ram','asaa','aa','aaa','non','aaa',2,3),(4,'manish','silwal','12-12-1999','112-8-2020','not assigned yet','heart patient',4,3),(5,'werw','erwrw','werwe','aq','not assigned yet','werwrewr',4,3),(6,'bipas','stha','12211','XASDA','not assigned yet','',4,3),(7,'asa','sdad','asdsa','adsa','not assigned yet','gdfgdf',2,3),(8,'qwe','we','wqe','wqe','not assigned yet','we',5,3),(9,'chirag','thapa','12-12-1999','12-12-2021','not assigned yet','',8,6),(10,'chirag','thapa','12-12-1999','12-12-2021','not assigned yet','',4,6),(11,'1','1','1','1','not assigned yet','sxsxs',1,7),(12,'a','a','a','a','not assigned yet','aaa',1,7),(13,'sarina','thapa','12-12-1999','12-12-2020','not assigned yet','heart patient',10,7);
/*!40000 ALTER TABLE `patient_registration` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-10-06 23:04:17
