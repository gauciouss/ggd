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
-- Table structure for table `app_version`
--

DROP TABLE IF EXISTS `app_version`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app_version` (
  `app_id` varchar(200) NOT NULL,
  `version` varchar(50) NOT NULL,
  `publish_time` datetime DEFAULT NULL,
  `link` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`app_id`,`version`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app_version`
--

LOCK TABLES `app_version` WRITE;
/*!40000 ALTER TABLE `app_version` DISABLE KEYS */;
INSERT INTO `app_version` VALUES ('APP0000001','1.0.0','2018-03-09 01:13:09','/app/APP0000001/YouTube_v13.09.57_apkpure.com.apk'),('APP0000001','1.1.0','2018-03-10 01:13:09','/app/APP0000001/YouTube_v13.09.57_apkpure.com.apk'),('APP0000001','13.09.57','2018-03-11 01:13:09','/app/APP0000001/YouTube_v13.09.57_apkpure.com.apk'),('APP0000001','13.10.59','2018-03-26 17:08:06','/app/APP0000001/YouTube.apk'),('APP0000002','1.0.0','2018-03-09 01:13:09','/app/APP0000001/YouTube_v13.09.57_apkpure.com.apk'),('APP0000003','1.0.0','2018-03-09 01:13:09','/app/APP0000001/YouTube_v13.09.57_apkpure.com.apk'),('APP0000004','1.0.0','2018-03-09 01:13:09','/app/APP0000001/YouTube_v13.09.57_apkpure.com.apk'),('APP0000004','1.1.0','2018-03-10 01:13:09','/app/APP0000001/YouTube_v13.09.57_apkpure.com.apk'),('APP0000005','1.0.0','2018-03-09 01:13:09','/app/APP0000001/YouTube_v13.09.57_apkpure.com.apk'),('APP0000006','1.0.0','2018-03-09 01:13:09','/app/APP0000001/YouTube_v13.09.57_apkpure.com.apk'),('APP0000007','1.0.0','2018-03-09 01:13:09','/app/APP0000001/YouTube_v13.09.57_apkpure.com.apk'),('APP0000012','13.10.59','2018-03-26 17:17:57','/app/APP0000012/YouTube.apk');
/*!40000 ALTER TABLE `app_version` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-27  1:45:16
