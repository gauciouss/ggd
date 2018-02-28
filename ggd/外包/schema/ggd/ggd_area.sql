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
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `area` (
  `area_id` int(11) NOT NULL,
  `area_name` varchar(50) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  PRIMARY KEY (`area_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (1,'臺北市',0,1),(2,'基隆市',0,1),(3,'新北市',0,1),(4,'南海島',0,1),(5,'宜蘭縣',0,1),(6,'釣魚臺',0,1),(7,'連江縣',0,1),(8,'新竹市',0,1),(9,'新竹縣',0,1),(10,'桃園市',0,1),(11,'桃園市',0,1),(12,'苗栗縣',0,1),(13,'臺中市',0,1),(14,'彰化縣',0,1),(15,'南投縣',0,1),(16,'嘉義市',0,1),(17,'嘉義縣',0,1),(18,'雲林縣',0,1),(19,'臺南市',0,1),(20,'高雄市',0,1),(21,'高雄市',0,1),(22,'屏東縣',0,1),(23,'澎湖縣',0,1),(24,'臺東縣',0,1),(25,'高雄市',0,1),(26,'金門縣',0,1),(27,'花蓮縣',0,1),(100,'中正區',1,1),(103,'大同區',1,1),(104,'中正區',1,1),(105,'松山區',1,1),(106,'大安區',1,1),(108,'萬華區',1,1),(110,'信義區',1,1),(111,'士林區',1,1),(112,'北投區',1,1),(114,'內湖區',1,1),(115,'南港區',1,1),(116,'文山區',1,1),(200,'仁愛區',2,1),(201,'信義區',2,1),(202,'中正區',2,1),(203,'中山區',2,1),(204,'安樂區',2,1),(205,'暖暖區',2,1),(206,'七堵區',2,1),(207,'萬里區',3,1),(208,'金山區',3,1),(209,'南竿鄉',7,1),(210,'北竿鄉',7,1),(211,'莒光鄉',7,1),(212,'東引鄉',7,1),(220,'板橋區',3,1),(221,'汐止區',3,1),(222,'深坑區',3,1),(223,'石碇區',3,1),(224,'瑞芳區',3,1),(226,'平溪區',3,1),(227,'雙溪區',3,1),(228,'貢寮區',3,1),(231,'新店區',3,1),(232,'坪林區 ',3,1),(233,'烏來區',3,1),(234,'永和區',3,1),(235,'中和區',3,1),(236,'土城區',3,1),(237,'三峽區',3,1),(238,'樹林區',3,1),(239,'鶯歌區',3,1),(241,'三重區',3,1),(242,'新莊區',3,1),(243,'泰山區',3,1),(244,'林口區',3,1),(247,'蘆洲區',3,1),(248,'五股區',3,1),(249,'八里區',3,1),(251,'淡水區',3,1),(252,'三芝區',3,1),(253,'石門區',3,1),(260,'宜蘭市',5,1),(261,'頭城鎮',5,1),(262,'礁溪鄉',5,1),(263,'壯圍鄉',5,1),(264,'員山鄉',5,1),(265,'羅東鎮',5,1),(266,'三星鄉',5,1),(267,'大同鄉',5,1),(268,'五結鄉',5,1),(269,'冬山鄉',5,1),(270,'蘇澳鎮',5,1),(272,'南澳鄉',5,1),(290,'釣魚臺',6,1),(817,'東沙群島',4,1),(819,'南沙群島',4,1);
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

-- Dump completed on 2018-03-01  1:51:45
