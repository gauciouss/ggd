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
-- Table structure for table `kv_comp_mapping`
--

DROP TABLE IF EXISTS `kv_comp_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kv_comp_mapping` (
  `EIN` varchar(10) NOT NULL,
  `kv_serial_no` int(11) NOT NULL,
  `start_date` datetime DEFAULT NULL,
  `end_date` datetime DEFAULT NULL,
  `isEnabled` bit(1) DEFAULT NULL,
  `isApproved` bit(1) DEFAULT NULL,
  PRIMARY KEY (`EIN`,`kv_serial_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kv_comp_mapping`
--

LOCK TABLES `kv_comp_mapping` WRITE;
/*!40000 ALTER TABLE `kv_comp_mapping` DISABLE KEYS */;
INSERT INTO `kv_comp_mapping` VALUES ('23258518',2,'2018-03-06 00:00:00','2018-03-30 00:00:00','',''),('23258518',3,'2018-03-08 00:00:00','2018-03-21 00:00:00','',''),('23258518',4,'2018-03-08 00:00:00','2018-03-29 00:00:00','',''),('23258518',5,'2018-03-01 00:00:00','2018-03-31 00:00:00','',''),('23258518',7,'2018-03-01 00:00:00','2018-03-31 00:00:00','',''),('89125266',1,'2018-02-28 16:54:15','2018-03-31 16:54:15','',''),('89125266',2,'2018-03-06 00:00:00','2018-03-30 00:00:00','',''),('89125266',3,'2018-03-08 00:00:00','2018-03-21 00:00:00','',''),('89125266',4,'2018-03-08 00:00:00','2018-03-29 00:00:00','',''),('89125266',5,'2018-03-01 00:00:00','2018-03-31 00:00:00','',''),('89125266',6,'2018-03-15 00:00:00','2018-03-31 00:00:00','',''),('89125266',7,'2018-03-01 00:00:00','2018-03-31 00:00:00','','');
/*!40000 ALTER TABLE `kv_comp_mapping` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-27  1:45:13
