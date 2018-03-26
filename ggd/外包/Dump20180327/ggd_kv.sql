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
-- Table structure for table `kv`
--

DROP TABLE IF EXISTS `kv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kv` (
  `kv_serial_no` int(11) NOT NULL AUTO_INCREMENT,
  `kind` int(11) DEFAULT NULL,
  `img_path` varchar(255) DEFAULT NULL,
  `click_link` varchar(255) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `create_user` varchar(50) DEFAULT NULL,
  `update_user` varchar(50) DEFAULT NULL,
  `msg` varchar(255) DEFAULT NULL,
  `click_count` int(11) DEFAULT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`kv_serial_no`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kv`
--

LOCK TABLES `kv` WRITE;
/*!40000 ALTER TABLE `kv` DISABLE KEYS */;
INSERT INTO `kv` VALUES (1,1,'kv/1521787747011.jpg','https://tw.yahoo.com',NULL,'2018-03-23 14:49:07',NULL,'admin','測試1',NULL,'測試1'),(2,2,'kv/1521787765377.jpg','https://www.pchome.com','2018-03-21 17:14:37','2018-03-23 14:49:25','admin','admin','this is a test',NULL,'測試2'),(3,2,'kv/1521787778095.jpg','https://www.pchome.com','2018-03-21 17:19:56','2018-03-23 14:49:38','admin','admin','ewrtqwertq',NULL,'G.D'),(4,2,'kv/1521787786950.jpg','https://www.pchome.com','2018-03-21 21:47:49','2018-03-23 14:49:47','admin','admin','zxcv',NULL,'測試3'),(5,1,'kv/1521640149472.jpg','https://www.pchome.com','2018-03-21 21:49:09',NULL,'admin',NULL,'qwer',NULL,'測試4'),(6,3,'kv/1522050871452.jpg','https://www.msn.com','2018-03-21 22:06:07','2018-03-26 15:54:31','admin','admin','12345',NULL,'訊息測試A'),(7,4,'','','2018-03-22 03:35:54',NULL,'admin',NULL,'跑馬燈訊息跑馬燈訊息跑馬燈訊息',NULL,'跑馬燈訊息');
/*!40000 ALTER TABLE `kv` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-27  1:45:17
