-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 60.248.131.18    Database: ggd
-- ------------------------------------------------------
-- Server version	5.7.21

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `comp_app_mapping`
--

DROP TABLE IF EXISTS `comp_app_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `comp_app_mapping` (
  `EIN` varchar(10) DEFAULT NULL,
  `app_id` varchar(200) DEFAULT NULL,
  `comp_app_mappingcol` varchar(45) DEFAULT NULL,
  `seial_no` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`seial_no`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comp_app_mapping`
--

LOCK TABLES `comp_app_mapping` WRITE;
/*!40000 ALTER TABLE `comp_app_mapping` DISABLE KEYS */;
INSERT INTO `comp_app_mapping` VALUES ('89125266','APP0000001',NULL,1),('89125266','APP0000002',NULL,2),('89125266','APP0000003',NULL,3),('89125266','APP0000004',NULL,4),('23255818','APP0000001',NULL,5),('23255818','APP0000002',NULL,6),('23255818','APP0000003',NULL,7),('23255818','APP0000004',NULL,8),('23255818','APP0000005',NULL,9),('23255818','APP0000006',NULL,10),('23255818','APP0000007',NULL,11);
/*!40000 ALTER TABLE `comp_app_mapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-27  1:45:15
