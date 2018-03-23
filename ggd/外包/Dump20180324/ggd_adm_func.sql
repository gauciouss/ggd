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
-- Table structure for table `adm_func`
--

DROP TABLE IF EXISTS `adm_func`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adm_func` (
  `func_id` varchar(10) NOT NULL,
  `func_name` varchar(20) DEFAULT NULL,
  `parent_id` varchar(10) DEFAULT NULL,
  `is_root` bit(1) DEFAULT NULL,
  `url` varchar(1000) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `isEnabled` bit(1) DEFAULT NULL,
  `isApproved` bit(1) DEFAULT NULL,
  PRIMARY KEY (`func_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_func`
--

LOCK TABLES `adm_func` WRITE;
/*!40000 ALTER TABLE `adm_func` DISABLE KEYS */;
INSERT INTO `adm_func` VALUES ('FUN0000001','權限管理','FUN0000001','','',1,'2017-08-23 23:12:15',NULL,'',''),('FUN0000002','使用者管理','FUN0000001','\0','auth/user',1,'2017-08-23 23:12:15',NULL,'',''),('FUN0000003','群組管理','FUN0000001','\0','auth/grp',2,'2017-08-23 23:12:15',NULL,'',''),('FUN0000004','功能管理','FUN0000001','\0','auth/func',3,'2017-08-23 23:12:15',NULL,'',''),('FUN0000005','廠商管理','FUN0000005','',NULL,0,'2018-02-26 01:50:24',NULL,'',''),('FUN0000006','廠商管理','FUN0000005','\0','main/comp',0,'2018-02-26 02:41:56',NULL,'',''),('FUN0000007','訊息管理','FUN0000007','',NULL,3,'2018-02-28 17:43:27',NULL,'',''),('FUN0000008','廣告管理','FUN0000007','\0','main/kv',1,'2018-02-28 17:43:59',NULL,'',''),('FUN0000009','APP管理','FUN0000009','',NULL,3,'2018-03-13 23:02:01',NULL,'',''),('FUN0000010','APP管理','FUN0000009','\0','main/app',1,'2018-03-13 23:02:43','2018-03-14 00:23:36','',''),('FUN0000011','APP類別管理','FUN0000009','\0','main/appclz',2,'2018-03-22 00:43:26',NULL,'',''),('FUN0000012','遙控器快速鍵設定','FUN0000009','\0','main/quickPanel',3,'2018-03-22 22:49:05',NULL,'',''),('FUN0000013','機台管理','FUN0000013','',NULL,5,'2018-03-22 22:58:13',NULL,'',''),('FUN0000014','機台管理','FUN0000013','\0','main/machine',1,'2018-03-22 22:58:40',NULL,'','');
/*!40000 ALTER TABLE `adm_func` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-24  0:15:27
