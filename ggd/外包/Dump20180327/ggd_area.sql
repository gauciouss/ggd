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
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `area` (
  `area_id` int(11) NOT NULL,
  `area_name` varchar(50) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `cwb_code` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (1,'臺北市',1,'F-C0032-009'),(2,'基隆市',1,'F-C0032-011'),(3,'新北市',1,'F-C0032-010'),(4,'南海島',1,NULL),(5,'宜蘭縣',1,'F-C0032-013'),(6,'釣魚臺',1,NULL),(7,'連江縣',1,'F-C0032-030'),(8,'新竹市',1,'F-C0032-024'),(9,'新竹縣',1,'F-C0032-023'),(10,'桃園市',1,'F-C0032-022'),(12,'苗栗縣',1,'F-C0032-020'),(13,'臺中市',1,'F-C0032-021'),(14,'彰化縣',1,'F-C0032-028'),(15,'南投縣',1,'F-C0032-026'),(16,'嘉義市',1,'F-C0032-019'),(17,'嘉義縣',1,'F-C0032-018'),(18,'雲林縣',1,'F-C0032-029'),(19,'臺南市',1,'F-C0032-016'),(20,'高雄市',1,'F-C0032-017'),(22,'屏東縣',1,'F-C0032-025'),(23,'澎湖縣',1,'F-C0032-015'),(24,'臺東縣',1,'F-C0032-027'),(26,'金門縣',1,'F-C0032-014'),(27,'花蓮縣',1,'F-C0032-012');
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
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
