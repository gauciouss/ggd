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
-- Table structure for table `adm_user`
--

DROP TABLE IF EXISTS `adm_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `adm_user` (
  `account` varchar(20) NOT NULL,
  `pwd` varchar(50) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `tel` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `group_id` varchar(10) DEFAULT NULL,
  `create_date` datetime DEFAULT NULL,
  `update_date` datetime DEFAULT NULL,
  `isEnabled` bit(1) DEFAULT NULL,
  `isApproved` bit(1) DEFAULT NULL,
  PRIMARY KEY (`account`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `adm_user`
--

LOCK TABLES `adm_user` WRITE;
/*!40000 ALTER TABLE `adm_user` DISABLE KEYS */;
INSERT INTO `adm_user` VALUES ('admin','123456','系統管理者','gauciouss@gmail.com','','','','GRP0000001','2017-08-23 23:10:54',NULL,'',''),('test1','123456','測試1','test@gmail.com','測試地址1','321','0985214796','GRP0000003','2017-09-01 00:20:16',NULL,'',''),('test10','123456','測試帳號10','test@gmail.com','test123','0987654321','0987123456','GRP0000003','2017-09-01 00:20:16',NULL,'',''),('test11','123456','測試帳號11','test@gmail.com','','','','GRP0000003','2017-09-01 00:20:16',NULL,'',''),('test12','123456','測試帳號12','test@gmail.com','','','','GRP0000003','2017-09-01 00:20:16',NULL,'',''),('test1234','123456','ttt','test@gmail.com','test','0987456123','0987456123','GRP0000004','2018-02-22 00:09:57',NULL,'',''),('test13','123456','測試帳號13','test@gmail.com','','','','GRP0000003','2017-09-01 00:20:16',NULL,'',''),('test14','123456','測試帳號14','test@gmail.com','','','','GRP0000003','2017-09-01 00:20:16',NULL,'',''),('test2','123456','測試帳號2','test@gmail.com','','','','GRP0000003','2017-09-01 00:20:16',NULL,'',''),('test3','123456','測試帳號3','test@gmail.com','','','','GRP0000003','2017-09-01 00:20:16',NULL,'',''),('test4','123456','測試帳號4','test@gmail.com','','','','GRP0000003','2017-09-01 00:20:16',NULL,'',''),('test5','123456','測試帳號5','test@gmail.com','','','','GRP0000003','2017-09-01 00:20:16',NULL,'',''),('test6','123456','測試帳號6','test@gmail.com','','','','GRP0000003','2017-09-01 00:20:16',NULL,'',''),('test7','123456','測試帳號7','test@gmail.com','','','','GRP0000003','2017-09-01 00:20:16',NULL,'',''),('test8','123456','測試帳號8','test@gmail.com','','','','GRP0000003','2017-09-01 00:20:16',NULL,'',''),('test9','123456','測試帳號9','test@gmail.com','','','','GRP0000003','2017-09-01 00:20:16',NULL,'',''),('testABCD','123456','ABCD','abd@gmail.com','ASSD','0987456321','0987456321','GRP0000004','2018-02-22 00:28:21',NULL,'',''),('TESTQAZ','123456','QAZ','QAZ@gmail.com','QAZ','0987654321','0987654321','GRP0000004','2018-02-22 00:32:06',NULL,'','\0');
/*!40000 ALTER TABLE `adm_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-05  2:53:37
