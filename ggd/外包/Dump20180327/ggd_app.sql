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
-- Table structure for table `app`
--

DROP TABLE IF EXISTS `app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `app` (
  `app_id` varchar(10) NOT NULL,
  `app_name` varchar(50) DEFAULT NULL,
  `app_eng_name` varchar(50) DEFAULT NULL,
  `clz_id` int(11) DEFAULT NULL,
  `icon_path` varchar(50) DEFAULT NULL,
  `app_desc` varchar(200) DEFAULT NULL,
  `pkg_name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `app`
--

LOCK TABLES `app` WRITE;
/*!40000 ALTER TABLE `app` DISABLE KEYS */;
INSERT INTO `app` VALUES ('APP0000002','gamer','gamer',1,'','test2','com.gamer'),('APP0000003','遊戲王','YuGiWong',1,'','test3','com.ygw'),('APP0000004','七騎士','774',1,'','test4','com.ggs'),('APP0000005','七龍珠','DBZ',1,'','test5','com.dbz'),('APP0000006','霹靂','PILI',2,'','test6','com.pili'),('APP0000007','iPhoto','iPhoto',2,'','test7','com.iphoto'),('APP0000008',NULL,NULL,NULL,NULL,NULL,NULL),('APP0000009',NULL,NULL,NULL,NULL,NULL,NULL),('APP0000010',NULL,NULL,NULL,NULL,NULL,NULL),('APP0000011',NULL,NULL,NULL,NULL,NULL,NULL),('APP0000012','YouTube','YouTube',2,'/app/APP0000012/res/mipmap-hdpi-v4/ic_launcher.png','								\r\n							','com.google.android.youtube');
/*!40000 ALTER TABLE `app` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-27  1:45:12
