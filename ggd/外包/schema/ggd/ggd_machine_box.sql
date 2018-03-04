-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: 192.168.191.128    Database: ggd
-- ------------------------------------------------------
-- Server version	5.7.19

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
-- Table structure for table `machine_box`
--

DROP TABLE IF EXISTS `machine_box`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `machine_box` (
  `serial_no` int(11) NOT NULL AUTO_INCREMENT,
  `machine_sn` varchar(50) DEFAULT NULL,
  `wifi_mac` varchar(50) DEFAULT NULL,
  `ethernet_mac` varchar(50) DEFAULT NULL,
  `area_id` int(11) DEFAULT NULL,
  `company_id` varchar(10) DEFAULT NULL,
  `last_login_time` datetime DEFAULT NULL,
  `login_ip` varchar(200) DEFAULT NULL,
  `customer_name` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `start_date` datetime DEFAULT NULL,
  `isEnabled` bit(1) DEFAULT NULL,
  `authorized_start_date` datetime DEFAULT NULL,
  `authorized_end_date` datetime DEFAULT NULL,
  PRIMARY KEY (`serial_no`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `machine_box`
--

LOCK TABLES `machine_box` WRITE;
/*!40000 ALTER TABLE `machine_box` DISABLE KEYS */;
INSERT INTO `machine_box` VALUES (1,'123456789','','F4-6D-04-64-40-D9',235,'89125266',NULL,NULL,NULL,NULL,NULL,'2018-03-04 00:00:00','','2018-03-04 00:00:00',NULL),(2,'54789963','','1A-2V-3S-FV-AS-FX',235,'89125266','2018-03-05 02:37:41','127.0.0.1',NULL,NULL,NULL,'2018-03-05 02:37:07','','2018-03-04 00:00:00','2019-03-05 02:37:07');
/*!40000 ALTER TABLE `machine_box` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-05  2:53:36
