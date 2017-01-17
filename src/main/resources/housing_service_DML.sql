-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: housing_service
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Dumping data for table `work_type`
--

LOCK TABLES `work_type` WRITE;
/*!40000 ALTER TABLE `work_type` DISABLE KEYS */;
INSERT INTO `work_type` VALUES (1,'електричество'),(5,'покраска'),(2,'сантехника'),(4,'уборка');
/*!40000 ALTER TABLE `work_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `brigade`
--

LOCK TABLES `brigade` WRITE;
/*!40000 ALTER TABLE `brigade` DISABLE KEYS */;
INSERT INTO `brigade` VALUES (1,'№1',1),(2,'№2',2);
/*!40000 ALTER TABLE `brigade` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (4,'ADMIN'),(2,'ADVISOR'),(1,'CREW'),(3,'TENANT');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'crew','crew',1,'Данила','Самойлов','електрик первого разряда',1,'ул. Башиловская','24',2,'Киев','093-722-9393'),(3,'crew1','crew1',1,'Илья','Лобанов','електрик второго разряда',1,'ул. Батайская','23',3,'Киев','093-722-9393'),(4,'crew2','crew2',1,'Артём','Дьячков','електрик второго разряда',1,'ул. Маршала Конева','12',4,'Киев','093-722-9393'),(5,'advisor','advisor',2,'Дмитрий','Морозов',NULL,NULL,'ул. Барминовская','1',5,'Киев','093-722-9393'),(6,'tenant','tenant',3,'Ярослав','Гусев',NULL,NULL,'ул. Дарвина','12',6,'Киев','093-722-9393'),(7,'tenant1','tenant1',3,'Гавриил','Капустин',NULL,NULL,'ул. Дальняя','7',7,'Киев','093-722-9393'),(8,'tenant2','tenant2',3,'Виталий','Рожков',NULL,NULL,'ул. Михаила Агибалова','8',8,'Киев','093-722-9393'),(9,'crew4','crew4',1,'Наумов','Боян','сантехник первого разряда',2,'ул. Сталина','12',54,'Киев','093-722-9393'),(10,'crew5','crew5',1,'Никита','Мамедов','сантехник второго разряда',2,'ул. Беговая','2',65,'Киев','093-722-9393'),(15,'admin','admin',4,'Максим','Белов',NULL,NULL,'ул. Бакунина','24а',1,'Киев','093-722-9393');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `bid`
--

LOCK TABLES `bid` WRITE;
/*!40000 ALTER TABLE `bid` DISABLE KEYS */;
INSERT INTO `bid` VALUES (1,1,5,'2016-12-12 00:00:00',6,'CLOSE','выбивает щиток','2016-12-10 00:00:00'),(2,1,7,'2016-12-14 00:00:00',7,'IN WORK','потек кран','2016-12-10 00:00:00'),(3,1,5,'2016-12-14 00:00:00',6,'NEW','коротит резетка','2016-12-14 00:00:00'),(4,2,7,'2016-12-14 00:00:00',6,'COMPLETE','лопнула труба','2016-12-14 00:00:00'),(8,1,10,'2017-01-09 01:00:00',15,'NEW','sdsdsd','2017-01-05 16:57:30');
/*!40000 ALTER TABLE `bid` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping data for table `work_plane`
--

LOCK TABLES `work_plane` WRITE;
/*!40000 ALTER TABLE `work_plane` DISABLE KEYS */;
INSERT INTO `work_plane` VALUES (1,5,1,2,'NEW','2016-12-14 00:00:00',NULL);
/*!40000 ALTER TABLE `work_plane` ENABLE KEYS */;
UNLOCK TABLES;


/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-01-15 23:28:30
