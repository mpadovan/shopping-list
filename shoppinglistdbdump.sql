-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--
-- Host: localhost    Database: shoppinglistdb
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8mb4 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `anonlists`
--

DROP TABLE IF EXISTS `anonlists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `anonlists` (
  `tokenid` int(10) unsigned NOT NULL,
  `publicproductid` int(11) NOT NULL,
  `quantity` mediumint(9) NOT NULL DEFAULT '1',
  UNIQUE KEY `tokenid` (`tokenid`,`publicproductid`),
  KEY `publicproductid` (`publicproductid`),
  CONSTRAINT `anonlists_ibfk_1` FOREIGN KEY (`tokenid`) REFERENCES `anonusers` (`token`) ON DELETE CASCADE,
  CONSTRAINT `anonlists_ibfk_2` FOREIGN KEY (`publicproductid`) REFERENCES `publicproducts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anonlists`
--

LOCK TABLES `anonlists` WRITE;
/*!40000 ALTER TABLE `anonlists` DISABLE KEYS */;
INSERT INTO `anonlists` VALUES (68,8,1),(68,10,1),(68,11,2),(71,99,1),(81,53,1),(81,122,1),(92,11,1);
/*!40000 ALTER TABLE `anonlists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `anonusers`
--

DROP TABLE IF EXISTS `anonusers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `anonusers` (
  `token` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `idlistcategory` int(11) NOT NULL DEFAULT '1',
  `lastaccess` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`token`),
  KEY `idlistcategory` (`idlistcategory`),
  CONSTRAINT `anonusers_ibfk_1` FOREIGN KEY (`idlistcategory`) REFERENCES `listscategories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=94 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `anonusers`
--

LOCK TABLES `anonusers` WRITE;
/*!40000 ALTER TABLE `anonusers` DISABLE KEYS */;
INSERT INTO `anonusers` VALUES (9,1,'2019-01-13 11:07:46'),(10,1,'2019-01-13 11:53:29'),(11,1,'2019-01-13 14:09:44'),(12,1,'2019-01-13 14:10:17'),(13,1,'2019-01-13 14:10:24'),(14,1,'2019-01-13 14:11:52'),(15,1,'2019-01-13 14:23:01'),(16,1,'2019-01-13 14:25:13'),(17,1,'2019-01-13 14:32:55'),(18,1,'2019-01-13 14:57:59'),(19,1,'2019-01-13 14:58:10'),(20,1,'2019-01-13 15:00:47'),(21,1,'2019-01-13 15:03:19'),(22,1,'2019-01-13 15:17:46'),(23,1,'2019-01-14 09:09:45'),(24,1,'2019-01-14 12:18:06'),(25,1,'2019-01-14 12:20:25'),(26,1,'2019-01-14 12:21:18'),(27,1,'2019-01-14 13:23:24'),(28,1,'2019-01-14 13:23:27'),(29,1,'2019-01-14 14:12:38'),(30,1,'2019-01-14 16:27:50'),(31,1,'2019-01-15 20:53:44'),(32,1,'2019-01-15 20:58:54'),(33,1,'2019-01-17 21:23:08'),(34,1,'2019-01-19 20:01:32'),(35,1,'2019-01-19 20:13:13'),(36,1,'2019-01-19 20:13:14'),(37,1,'2019-01-21 08:19:29'),(38,1,'2019-01-21 08:39:41'),(39,1,'2019-01-21 08:44:32'),(40,1,'2019-01-21 09:01:28'),(41,1,'2019-01-21 09:02:34'),(42,1,'2019-01-21 09:09:26'),(43,1,'2019-01-21 09:10:33'),(44,1,'2019-01-21 09:12:40'),(45,1,'2019-01-21 09:13:40'),(46,1,'2019-01-21 09:15:28'),(47,1,'2019-01-21 09:16:58'),(48,1,'2019-01-21 09:17:51'),(49,1,'2019-01-21 09:19:11'),(50,1,'2019-01-21 09:20:50'),(51,1,'2019-01-21 09:21:18'),(52,1,'2019-01-21 09:28:58'),(53,1,'2019-01-21 09:33:44'),(54,1,'2019-01-21 09:42:56'),(55,1,'2019-01-21 09:45:11'),(56,1,'2019-01-21 10:40:28'),(57,1,'2019-01-21 10:41:10'),(58,1,'2019-01-21 10:46:05'),(59,1,'2019-01-21 10:47:45'),(60,1,'2019-01-21 10:48:39'),(61,1,'2019-01-21 10:51:20'),(62,1,'2019-01-21 11:44:26'),(63,1,'2019-01-21 20:14:35'),(64,1,'2019-01-22 07:58:46'),(65,1,'2019-01-22 13:12:56'),(66,1,'2019-01-22 14:43:32'),(67,1,'2019-01-22 14:44:42'),(68,1,'2019-01-22 14:45:06'),(69,1,'2019-01-22 14:54:19'),(70,1,'2019-01-22 17:08:52'),(71,1,'2019-01-22 20:02:16'),(72,1,'2019-01-22 20:02:50'),(73,1,'2019-01-22 20:30:13'),(74,1,'2019-01-23 07:32:53'),(75,1,'2019-01-23 12:03:57'),(76,1,'2019-01-23 12:04:32'),(77,1,'2019-01-23 12:14:16'),(78,1,'2019-01-23 14:07:50'),(79,1,'2019-01-23 14:46:42'),(80,1,'2019-01-23 14:57:37'),(81,1,'2019-01-23 15:59:17'),(82,1,'2019-01-23 16:10:25'),(83,1,'2019-01-23 20:42:13'),(84,1,'2019-01-23 21:13:46'),(85,1,'2019-01-24 02:48:40'),(86,1,'2019-01-24 11:11:45'),(87,1,'2019-01-24 11:26:58'),(88,1,'2019-01-24 11:34:12'),(89,1,'2019-01-24 14:00:53'),(90,1,'2019-01-25 08:24:14'),(91,1,'2019-01-25 08:51:33'),(92,1,'2019-01-25 09:06:53'),(93,1,'2019-01-25 10:37:05');
/*!40000 ALTER TABLE `anonusers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `flyway_schema_history`
--

DROP TABLE IF EXISTS `flyway_schema_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `flyway_schema_history` (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) DEFAULT NULL,
  `description` varchar(200) NOT NULL,
  `type` varchar(20) NOT NULL,
  `script` varchar(1000) NOT NULL,
  `checksum` int(11) DEFAULT NULL,
  `installed_by` varchar(100) NOT NULL,
  `installed_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`),
  KEY `flyway_schema_history_s_idx` (`success`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `flyway_schema_history`
--

LOCK TABLES `flyway_schema_history` WRITE;
/*!40000 ALTER TABLE `flyway_schema_history` DISABLE KEYS */;
INSERT INTO `flyway_schema_history` VALUES (1,'1','Create users','SQL','V1__Create_users.sql',-1456136790,'root','2019-01-12 21:19:49',1516,1),(2,'2','Add permission','SQL','V2__Add_permission.sql',1248311358,'root','2019-01-12 21:19:50',107,1),(3,'3','Change password length for sha256','SQL','V3__Change_password_length_for_sha256.sql',-1220329050,'root','2019-01-12 21:19:50',279,1),(4,'4','Drop table','SQL','V4__Drop_table.sql',286323066,'root','2019-01-12 21:19:51',1037,1),(5,'5','Create table','SQL','V5__Create_table.sql',-398679319,'root','2019-01-12 21:19:53',1554,1),(6,'6','Modify constraint list','SQL','V6__Modify_constraint_list.sql',-1839755277,'root','2019-01-12 21:19:53',149,1),(7,'7','Add chat','SQL','V7__Add_chat.sql',1336760191,'root','2019-01-12 21:19:53',317,1),(8,'8','Add notification','SQL','V8__Add_notification.sql',935314788,'root','2019-01-12 21:19:54',632,1),(9,'9','Delete cascade e cose','SQL','V9__Delete_cascade_e_cose.sql',-518938651,'root','2019-01-12 21:19:57',3361,1),(10,'10','fix password','SQL','V10__fix_password.sql',-540599902,'root','2019-01-12 21:19:58',399,1),(11,'11','fix sharedlists','SQL','V11__fix_sharedlists.sql',-1507319297,'root','2019-01-12 21:19:58',340,1),(12,'12','Delete constraint list','SQL','V12__Delete_constraint_list.sql',-1506490120,'root','2019-01-12 21:19:58',68,1),(13,'13','Add tokenpassword','SQL','V13__Add_tokenpassword.sql',-336483997,'root','2019-01-12 21:19:58',91,1),(14,'14','Add anon users','SQL','V14__Add_anon_users.sql',1399428686,'root','2019-01-12 21:19:59',428,1),(15,'15','fix notifications','SQL','V15__fix_notifications.sql',2126291361,'root','2019-01-12 21:19:59',87,1);
/*!40000 ALTER TABLE `flyway_schema_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `lists`
--

DROP TABLE IF EXISTS `lists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `lists` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `iduser` int(11) NOT NULL,
  `idcategory` int(11) NOT NULL DEFAULT '1',
  `description` varchar(256) DEFAULT NULL,
  `image` varchar(2083) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idcategory` (`idcategory`),
  KEY `lists_ibfk_1` (`iduser`),
  CONSTRAINT `lists_ibfk_1` FOREIGN KEY (`iduser`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `lists_ibfk_2` FOREIGN KEY (`idcategory`) REFERENCES `listscategories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `lists`
--

LOCK TABLES `lists` WRITE;
/*!40000 ALTER TABLE `lists` DISABLE KEYS */;
INSERT INTO `lists` VALUES (3,'Farmacia',2,3,'Faccio scorte di medicinali senza prescrizione per l\'inverno',''),(5,'MD Spesa',1,1,'',''),(6,'Farmaci da comprare',1,3,'',''),(7,'Sola visualizzazione con Giulia',1,7,'',''),(8,'CasaChiusa',16,1,'',''),(9,'fsdds',3,1,'',''),(10,'Spesa con Giulia ',18,2,'Lista della spesa per questa sera',''),(11,'Cose Da Prendere Domani',17,1,'Cibo per la settimana prossima','/Uploads/restricted/shared/listImage/list-163.jpg'),(12,'La mia lista',18,3,'','');
/*!40000 ALTER TABLE `lists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listscategories`
--

DROP TABLE IF EXISTS `listscategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `listscategories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `description` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listscategories`
--

LOCK TABLES `listscategories` WRITE;
/*!40000 ALTER TABLE `listscategories` DISABLE KEYS */;
INSERT INTO `listscategories` VALUES (1,'Supermercato','Luogo in cui comprare prodotti di vario genere'),(2,'Ferramenta','Negozio di prodotti per il fai-da-te e motoseghe'),(3,'Farmacia','Negozio per medicinali e cura della persona'),(7,'Edicola','Negozio in cui comprare riviste, giornali e prodotti di cartoleria'),(8,'Discount','Supermercato low-cost');
/*!40000 ALTER TABLE `listscategories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `listscategoriesimages`
--

DROP TABLE IF EXISTS `listscategoriesimages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `listscategoriesimages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image` varchar(2083) NOT NULL,
  `idcategory` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `listscategoriesimages_ibfk_1` (`idcategory`),
  CONSTRAINT `listscategoriesimages_ibfk_1` FOREIGN KEY (`idcategory`) REFERENCES `listscategories` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `listscategoriesimages`
--

LOCK TABLES `listscategoriesimages` WRITE;
/*!40000 ALTER TABLE `listscategoriesimages` DISABLE KEYS */;
INSERT INTO `listscategoriesimages` VALUES (2,'/Uploads/public/listCategoryImage/NlJXHd-gPEU_tjUy9oCvMQ==.jpg',2),(3,'/Uploads/public/listCategoryImage/kj_yiFvPe83n_smXxwi1Ig==.jpeg',3),(8,'/Uploads/public/listCategoryImage/wjIe_73Mbybw2PC-J68b-Q==.jpg',7),(9,'/Uploads/public/listCategoryImage/s_noNmRQDnazzlYu56VgFw==.jpg',7),(10,'/Uploads/public/listCategoryImage/T3jXJIIUrmf-ZXzxOy5FIw==.jpg',8),(11,'/Uploads/public/listCategoryImage/o36St844DZhv6HNcYLidDw==.jpg',8),(12,'/Uploads/public/listCategoryImage/hYFmg3AIh6Y6UinYt3LTUg==.jpg',1),(13,'/Uploads/public/listCategoryImage/xlpuX6AnN0fGS2qNZKDk3Q==.jpg',1),(14,'/Uploads/public/listCategoryImage/FH-uiD3z0UcCpzE8XMqMpQ==.jpg',1),(15,'/Uploads/public/listCategoryImage/LDejhMsr8EL982MYlMTlsw==.jpg',1),(16,'/Uploads/public/listCategoryImage/fV7Ei17k9y8QooXDNVIxig==.jpg',1),(17,'/Uploads/public/listCategoryImage/w9WjwBch7MbhQPHvL9nrbA==.jpg',2),(18,'/Uploads/public/listCategoryImage/EJcz8BWs1bvN5c_aHGRU9Q==.jpg',2),(19,'/Uploads/public/listCategoryImage/PwtI9ToNPHCBktdvQkMGcw==.jpg',2),(20,'/Uploads/public/listCategoryImage/BBJXJmLfvZxTXEsqmVNexQ==.jpg',3),(21,'/Uploads/public/listCategoryImage/Ges9bAkiMSD6us54Gw6PHg==.jpg',3),(22,'/Uploads/public/listCategoryImage/RT1cPnR4jSrz0adkB0-D4g==.jpg',3),(23,'/Uploads/public/listCategoryImage/7fyUAKSZYttA0THOU3hQtQ==.jpg',7),(24,'/Uploads/public/listCategoryImage/tfGAn9xAzUQHF4YA74m5Fg==.jpg',7),(25,'/Uploads/public/listCategoryImage/69fL-1tFKk0c7O9I-3Pdiw==.jpg',8),(26,'/Uploads/public/listCategoryImage/0A8-BJa_UkVJiMwQbkyokA==.jpg',8);
/*!40000 ALTER TABLE `listscategoriesimages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `messages`
--

DROP TABLE IF EXISTS `messages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `iduser` int(11) NOT NULL,
  `idlist` int(11) NOT NULL,
  `sendtime` datetime(1) NOT NULL,
  `text` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `iduser` (`iduser`,`idlist`,`sendtime`),
  KEY `sendtime` (`sendtime`),
  KEY `messages_ibfk_2` (`idlist`),
  CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`iduser`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`idlist`) REFERENCES `lists` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `messages`
--

LOCK TABLES `messages` WRITE;
/*!40000 ALTER TABLE `messages` DISABLE KEYS */;
INSERT INTO `messages` VALUES (8,1,5,'2019-01-21 08:43:48.0','ciao'),(22,1,5,'2019-01-21 08:43:49.0','ciao'),(25,1,7,'2019-01-21 09:43:52.0','ciaone'),(26,2,7,'2019-01-21 09:44:11.0','Ciao anche a te'),(27,2,7,'2019-01-21 09:44:14.0','ki 6?'),(34,1,7,'2019-01-21 10:48:40.0','asdas'),(35,1,7,'2019-01-21 10:49:57.0','ehi giulia cosa devo comprare oggi?'),(36,2,7,'2019-01-21 10:50:08.0','Le banane'),(37,2,7,'2019-01-21 10:50:12.0','Per favore'),(38,2,7,'2019-01-21 10:50:21.0','E delle motoseghe se le trovi'),(39,1,7,'2019-01-21 10:50:41.0','Oh certo! Le motoseghe!'),(40,2,7,'2019-01-21 10:51:11.0','Grazie mille primo'),(41,2,7,'2019-01-21 10:51:17.0','Primo*'),(42,18,10,'2019-01-23 12:07:41.0','Ehi Giulia cosa compriamo oltre al riso quindi? '),(43,2,10,'2019-01-23 12:08:38.0','Delle verdure per favore'),(44,18,10,'2019-01-23 12:09:03.0','Quali? '),(45,2,10,'2019-01-23 12:09:26.0','Per esempio delle zucchine '),(46,18,10,'2019-01-23 12:09:29.0','! '),(47,18,10,'2019-01-23 12:09:53.0','Aggiungi qualcosa per mostrarmi qualche esempio! '),(48,18,10,'2019-01-23 21:01:32.0','Che pacco la vita'),(49,18,10,'2019-01-23 21:01:35.0','Che pacco la vita'),(50,18,10,'2019-01-24 11:27:24.0','non credi?'),(51,2,10,'2019-01-24 11:27:43.0','Lo credo'),(52,18,10,'2019-01-24 11:27:51.0','lol'),(53,18,10,'2019-01-25 08:22:45.0','test chat');
/*!40000 ALTER TABLE `messages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `note` varchar(256) DEFAULT NULL,
  `logo` varchar(2083) DEFAULT NULL,
  `photography` varchar(2083) DEFAULT NULL,
  `iduser` int(11) NOT NULL,
  `idproductscategory` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `idproductscategory` (`idproductscategory`),
  KEY `products_ibfk_1` (`iduser`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`iduser`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`idproductscategory`) REFERENCES `productscategories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Sierra Tequila','Alcolico a base di agave blu','\\\\Uploads\\\\restricted\\\\shared\\\\productLogo\\\\yNTJc_FtD_QDEwqRQpdVew==.jpg','\\\\Uploads\\\\restricted\\\\shared\\\\productImage\\\\yNTJc_FtD_QDEwqRQpdVew==.jpg',2,1),(2,'Arachidi','',NULL,NULL,1,1),(3,'Polenta','Polenta gialla di mais',NULL,NULL,2,6),(7,'Aspirina','',NULL,NULL,2,1),(8,'Brillantante','',NULL,NULL,16,1),(9,'Riso basmati','',NULL,NULL,18,6),(10,'Ghiaccioli ','Belli freddi e alla frutta',NULL,NULL,2,13),(11,'Uova','',NULL,NULL,2,1),(12,'Farina','',NULL,NULL,2,1),(14,'Panettone','Quello al Cioccolato',NULL,NULL,17,6),(15,'Ciao','',NULL,NULL,2,1),(16,'aaa','',NULL,NULL,18,1);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productscategories`
--

DROP TABLE IF EXISTS `productscategories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `productscategories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `category` int(11) DEFAULT NULL,
  `description` varchar(256) DEFAULT NULL,
  `logo` varchar(2083) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `category` (`category`),
  CONSTRAINT `productscategories_ibfk_1` FOREIGN KEY (`category`) REFERENCES `productscategories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productscategories`
--

LOCK TABLES `productscategories` WRITE;
/*!40000 ALTER TABLE `productscategories` DISABLE KEYS */;
INSERT INTO `productscategories` VALUES (1,'Default',NULL,'Categoria che comprende i prodotti che al momento non hanno una categoria','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\tLx6+hCdCg5LTiY3xtMCEw==.png'),(4,'Alcolici',NULL,'Bevande sotto il monopolio statale che contengono alcol','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productCategoryLogo\\\\\\\\\\\\\\\\OBiQN_jkuN9b0yBw-DWz1Q==.jpg'),(6,'Pane & Pasta',NULL,'Prodotti a base di acqua e farina','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\N8eCMdeNyqnwfzzSWeOCeA==.jpg'),(7,'Carne & Pesce',NULL,'Prodotti animali di bovino, suino, carne bianca e prodotti ittici','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\M9b4jJItbxsI2qCYF2MuYw==.png'),(9,'Latte & Formaggi',NULL,'Prodotti che contengono latte, possono essere stagionati','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\mbxHPggqec5xEChJIkbD-Vl6E__EUXSsJktzrQTiimc=.jpg'),(10,'Elementi di fissaggio',NULL,'Prodotti per fissare oggetti e superfici','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productCategoryLogo\\\\\\\\-CxvUkxPXpmBkEbHMBkZQQ==.jpg'),(11,'Corde & Nastri',NULL,'Prodotti per legare insieme oggetti e superfici','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\EYzQBU3JKrl2uDCcVRPmBXUDX0hAEr-dLoirWIrqiLk=.jpg'),(12,'Pale, Badili & co.',NULL,'Strumenti per smuovere la terra e spalare la neve','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\wNYmepm5mvAruE-b_TImcw==.png'),(13,'Verdura',NULL,'Prodotti freschi e genuini','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\Vk13faHTwGDkgXIMVnw_Uw==.jpg'),(14,'Utensili',NULL,'Utensili per il fai da te','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productCategoryLogo\\\\\\\\\\\\\\\\AutxD1Y8wRkYNVJhiWg4Gw==.jpg'),(15,'Utensili elettrici',NULL,'Utensili elettrici per il fai da te','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\HtFPNvkKFwPS0zzLMBq2bLu4mLjJ_56YAqu_zEUmZaI=.jpg'),(16,'Pastiglie',NULL,'Medicinali a presa orale','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\B_h-pbPaG11DEz0EImZCow==.jpg'),(17,'Antibiotici',NULL,'Per fermare la proliferazione dei batteri','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\YCmjzJP2qTFJG0sXIupyew==.jpg'),(18,'Medicazioni',NULL,'Per le tue ferite','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productCategoryLogo\\\\\\\\42g3Rn8iHgO6yHXPgo4jQQ==.jpg'),(19,'Integratori alimentari',NULL,'Prodotti per integrare sostanze carenti','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\5LvpngcxPhNhXApDvK59xLmmUTqllgKUCN3uQrbciSA=.jpg'),(20,'Detergenti per il corpo',NULL,'Prodotti per l\'igiene personale','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\LM4oKZbtj0LWlV6SBNZV7DKK33-Q-q3nDOhOtPkE-LU=.JPG'),(21,'Giornali quotidiani',NULL,'Per tenersi sempre informati sul mondo','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\s_gU4Lr3K5xHS07qokGPi5znvqSI-wPpBs0TsqjPV2w=.jpg'),(22,'Riviste di gossip',NULL,'Per chi ha interesse delle vite degli altri','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\F8DEpDlqbjFtE7hEHSddlkf5YOhj6MKj2PTQr0oq3zg=.jpg'),(23,'Riviste di cucina',NULL,'Imparare a cucinare','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\JlRf6LLbVih2B20LWQBK56CVIzmxoikHyW6JPA95D2I=.jpg'),(24,'Materiale scolastico',NULL,'Per scrivere e disegnare','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\Tlz4_TnSLLB7eUTuSU6weJnlCCR7UazRFEBlb3fBbgk=.jpg'),(25,'Gadget per bambini',NULL,'Giochini per i piccoli','\\\\Uploads\\\\public\\\\productCategoryLogo\\\\bw0lcA-gHtUu23LjA9DKIB_KxsrDae8P7n3i5pz8lA0=.jpg');
/*!40000 ALTER TABLE `productscategories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productsnotification`
--

DROP TABLE IF EXISTS `productsnotification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `productsnotification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `time` datetime(1) NOT NULL,
  `idlist` int(11) NOT NULL,
  `idproduct` int(11) DEFAULT NULL,
  `idpublicproduct` int(11) DEFAULT NULL,
  `isread` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `idproduct` (`idproduct`),
  KEY `idpublicproduct` (`idpublicproduct`),
  KEY `productsnotification_ibfk_3` (`idlist`),
  CONSTRAINT `productsnotification_ibfk_1` FOREIGN KEY (`idproduct`) REFERENCES `products` (`id`),
  CONSTRAINT `productsnotification_ibfk_2` FOREIGN KEY (`idpublicproduct`) REFERENCES `publicproducts` (`id`),
  CONSTRAINT `productsnotification_ibfk_3` FOREIGN KEY (`idlist`) REFERENCES `lists` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productsnotification`
--

LOCK TABLES `productsnotification` WRITE;
/*!40000 ALTER TABLE `productsnotification` DISABLE KEYS */;
/*!40000 ALTER TABLE `productsnotification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `productsonlists`
--

DROP TABLE IF EXISTS `productsonlists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `productsonlists` (
  `idlist` int(11) NOT NULL,
  `idproduct` int(11) NOT NULL,
  `quantity` mediumint(9) NOT NULL DEFAULT '1',
  `lastinsert` datetime(1) DEFAULT CURRENT_TIMESTAMP(1),
  `exp_average` int(11) DEFAULT '-1',
  `add_count` mediumint(8) unsigned DEFAULT '0',
  PRIMARY KEY (`idlist`,`idproduct`),
  KEY `productsonlists_ibfk_2` (`idproduct`),
  CONSTRAINT `productsonlists_ibfk_1` FOREIGN KEY (`idlist`) REFERENCES `lists` (`id`) ON DELETE CASCADE,
  CONSTRAINT `productsonlists_ibfk_2` FOREIGN KEY (`idproduct`) REFERENCES `products` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `productsonlists`
--

LOCK TABLES `productsonlists` WRITE;
/*!40000 ALTER TABLE `productsonlists` DISABLE KEYS */;
INSERT INTO `productsonlists` VALUES (3,7,1,'2019-01-19 10:58:27.4',-1,1),(5,2,3,'2019-01-21 09:28:30.9',-1,1),(7,2,-1,'2019-01-21 10:44:11.5',60,2),(8,8,1,'2019-01-21 10:53:32.6',-1,1),(10,7,-1,'2019-01-24 11:30:58.4',-1,1),(10,9,-1,'2019-01-24 11:27:43.2',1399,2),(10,10,-1,'2019-01-23 12:10:45.1',-1,1),(10,11,-1,'2019-01-23 12:38:21.4',-1,1),(10,12,-1,'2019-01-23 12:38:41.4',-1,1),(10,15,-1,'2019-01-24 11:30:29.9',-1,1),(10,16,-1,'2019-01-25 08:22:59.6',-1,1),(11,14,1,'2019-01-23 20:29:16.6',-1,1),(12,16,-1,'2019-01-25 08:27:29.4',-1,1);
/*!40000 ALTER TABLE `productsonlists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publicproducts`
--

DROP TABLE IF EXISTS `publicproducts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `publicproducts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL,
  `note` varchar(256) DEFAULT NULL,
  `logo` varchar(2083) DEFAULT NULL,
  `photography` varchar(2083) DEFAULT NULL,
  `idproductscategory` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `idproductscategory` (`idproductscategory`),
  CONSTRAINT `publicproducts_ibfk_1` FOREIGN KEY (`idproductscategory`) REFERENCES `productscategories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=238 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publicproducts`
--

LOCK TABLES `publicproducts` WRITE;
/*!40000 ALTER TABLE `publicproducts` DISABLE KEYS */;
INSERT INTO `publicproducts` VALUES (2,'Birra','Bevi responsabilmente','/Uploads/public/productLogo/nR2xbQiBLY6TMKR1B4-s-g==.jpg','/Uploads/public/productImage/nR2xbQiBLY6TMKR1B4-s-g==.jpg',4),(3,'Vino rosso','Bevi responsabilmente','/Uploads/public/productLogo/kri0s_eVYKtkVtrJnxkTZg==.jpg','/Uploads/public/productImage/kri0s_eVYKtkVtrJnxkTZg==.jpg',4),(4,'Vino bianco','Bevi responsabilmente','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\4zs2soUrLOSlqJQBqDPIpQ==.png','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productImage\\\\\\\\4zs2soUrLOSlqJQBqDPIpQ==.jpg',4),(5,'Prosecco','Bevi responsabilmente','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\CMGmC9xKhwPWrR-mGmNINA==.png','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productImage\\\\\\\\CMGmC9xKhwPWrR-mGmNINA==.jpg',4),(6,'Vodka','Bevi responsabilmente','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productLogo\\\\\\\\\\\\\\\\jR4LbK7v5J4ISJ31AQdgpQ==.jpg','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productImage\\\\\\\\\\\\\\\\jR4LbK7v5J4ISJ31AQdgpQ==.jpg',4),(7,'Amaro digestivo','Bevi responsabilmente','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\1cZBYPW4Sq8t8tafexV3f3UDX0hAEr-dLoirWIrqiLk=.jpg','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productImage\\\\\\\\1cZBYPW4Sq8t8tafexV3f3UDX0hAEr-dLoirWIrqiLk=.jpeg',4),(8,'Birra - Heineken','Bevi responsabilmente','/Uploads/public/productLogo/_ax-427iRfkje5_Ia1Okds3YvYNtM1inge9edCFX1tQ=.jpg','/Uploads/public/productImage/_ax-427iRfkje5_Ia1Okds3YvYNtM1inge9edCFX1tQ=.jpg',4),(9,'Prosecco - Martini','Bevi responsabilmente','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productLogo\\\\\\\\\\\\\\\\2jpMgicwU9DfkVOmfkkxoll6E__EUXSsJktzrQTiimc=.png','\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\productImage\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\2jpMgicwU9DfkVOmfkkxoll6E__EUXSsJktzrQTiimc=.jpg',4),(10,'Vino rosso - Tavernello','Bevi responsabilmente','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productLogo\\\\\\\\\\\\\\\\ctY2ODAxztL9umO0_rM0xRVWsmkrYWgVhxoaTpUYtTE=.png','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productImage\\\\\\\\\\\\\\\\ctY2ODAxztL9umO0_rM0xRVWsmkrYWgVhxoaTpUYtTE=.jpg',4),(11,'Vodka - Absolut','Bevi responsabilmente','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productLogo\\\\\\\\\\\\\\\\_QghBGhDYu9g1IPJMO9ptw==.png','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productImage\\\\\\\\\\\\\\\\_QghBGhDYu9g1IPJMO9ptw==.jpg',4),(12,'Pane','Carboidrati per un po\' di energia','\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\productLogo\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\R7AT1kDB0UZkbAp6Kaaz7Q==.png','\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\productImage\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\R7AT1kDB0UZkbAp6Kaaz7Q==.jpg',6),(13,'Pane sesamo','Carboidrati per un po\' di energia','\\\\Uploads\\\\public\\\\productLogo\\\\iSN9bEOL4WbeKGanHHrGBA==.png','\\\\Uploads\\\\public\\\\productImage\\\\iSN9bEOL4WbeKGanHHrGBA==.jpg',6),(14,'Pane ai cereali','Carboidrati per un po\' di energia','\\\\Uploads\\\\public\\\\productLogo\\\\iA-SX5RnlveVprLHzii-KVl6E__EUXSsJktzrQTiimc=.png','\\\\Uploads\\\\public\\\\productImage\\\\iA-SX5RnlveVprLHzii-KVl6E__EUXSsJktzrQTiimc=.jpg',6),(15,'Pan Bauletto Bianco - Mulino Bianco','Carboidrati per un po\' di energia','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\983oAjNZigFt8cqRbHx5w4pSZNmA4gc1ufe6bhBhfFrdnu046bOIqqazvNufXuCk.png','\\\\Uploads\\\\public\\\\productImage\\\\q149Mt4UzZ9X7z-_UTMByN7IBQrk9W4lf1x3zini2AylLhPYHk2OT0SoBZTYkWJs.jpg',6),(16,'Pan Brioscé - Mulino Bianco','Carboidrati per un po\' di energia','\\\\Uploads\\\\public\\\\productLogo\\\\IpVsEFhP92qh3uLfXn6wxNdvXZ-72pY3oZff-11o7yg=.png','\\\\Uploads\\\\public\\\\productImage\\\\IpVsEFhP92qh3uLfXn6wxNdvXZ-72pY3oZff-11o7yg=.jpg',6),(17,'Pasta','Carboidrati per un po\' di energia','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\jDbVMrAZVjtSbY-T6wrnbQ==.png','\\\\Uploads\\\\public\\\\productImage\\\\jDbVMrAZVjtSbY-T6wrnbQ==.jpg',6),(18,'Penne - Barilla','Carboidrati per un po\' di energia','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\sojCxROQkgy0ZMmdgZ5IrMoJY8TkpF8ZGguzpcb2NK0=.png','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productImage\\\\\\\\\\\\\\\\sojCxROQkgy0ZMmdgZ5IrMoJY8TkpF8ZGguzpcb2NK0=.jpg',6),(19,'Maccheroni','Carboidrati per un po\' di energia','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productLogo\\\\\\\\\\\\\\\\zaC9AYaYAaHHv8STJP5dDipXlcSMEPR24cPTgCsX494=.png','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productImage\\\\\\\\\\\\\\\\zaC9AYaYAaHHv8STJP5dDipXlcSMEPR24cPTgCsX494=.jpg',6),(20,'Spaghetti','Carboidrati per un po\' di energia','\\\\Uploads\\\\public\\\\productLogo\\\\yo8Tdx6heNWmYZe8aQWGxg==.png','\\\\Uploads\\\\public\\\\productImage\\\\yo8Tdx6heNWmYZe8aQWGxg==.jpg',6),(21,'Spaghetti - Barilla','Carboidrati per un po\' di energia','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\q9OH3eZAcEaNJZz6SX4D1PZqO2E6CnQN4re5mbjmP-g=.png','\\\\Uploads\\\\public\\\\productImage\\\\q9OH3eZAcEaNJZz6SX4D1PZqO2E6CnQN4re5mbjmP-g=.jpeg',6),(22,'Carne','Proteine per crescere','\\\\Uploads\\\\public\\\\productLogo\\\\oAxy3qN1KOdcpSL9Uy8m0A==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\oAxy3qN1KOdcpSL9Uy8m0A==.jpg',7),(23,'Carne rossa','Proteine per crescere','\\\\Uploads\\\\public\\\\productLogo\\\\_aduhC7kMV8NP_0RygR3YQ==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\_aduhC7kMV8NP_0RygR3YQ==.jpg',7),(24,'Carne bianca','Proteine per crescere','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\Q0tB0pWTDqW6oO84JcEzhQ==.jpg','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productImage\\\\\\\\Q0tB0pWTDqW6oO84JcEzhQ==.jpeg',7),(25,'Hamburger','Proteine per crescere','\\\\Uploads\\\\public\\\\productLogo\\\\mQTvpYlRV-8CpE20cfd52g==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\mQTvpYlRV-8CpE20cfd52g==.jpg',7),(26,'Pollo','Proteine per crescere','\\\\Uploads\\\\public\\\\productLogo\\\\0mwlFqd5Su9GVU9oNiUV5g==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\0mwlFqd5Su9GVU9oNiUV5g==.jpg',7),(27,'Pesce','Fosforo per la tua memoria','\\\\Uploads\\\\public\\\\productLogo\\\\J8WV3ZDlIcxKcMKc5ui3fA==.png','\\\\Uploads\\\\public\\\\productImage\\\\J8WV3ZDlIcxKcMKc5ui3fA==.jpg',7),(28,'Pesce spada','Fosforo per la tua memoria','\\\\Uploads\\\\public\\\\productLogo\\\\ZOx-G48YR7ddZx07k0waNg==.png','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productImage\\\\\\\\ZOx-G48YR7ddZx07k0waNg==.jpg',7),(29,'Tonno','Fosforo per la tua memoria','\\\\Uploads\\\\public\\\\productLogo\\\\1ZlSFegMAHyvw2uq9hRebw==.png','\\\\Uploads\\\\public\\\\productImage\\\\1ZlSFegMAHyvw2uq9hRebw==.jpg',7),(30,'Gamberi','Fosforo per la tua memoria','\\\\Uploads\\\\public\\\\productLogo\\\\jGpkHJDBv2KaWbgfDB21uQ==.png','\\\\Uploads\\\\public\\\\productImage\\\\jGpkHJDBv2KaWbgfDB21uQ==.jpg',7),(31,'Gamberetti','Fosforo per la tua memoria','\\\\Uploads\\\\public\\\\productLogo\\\\WKR16eJCIqO75HPwt3mUNg==.png','\\\\Uploads\\\\public\\\\productImage\\\\WKR16eJCIqO75HPwt3mUNg==.jpg',7),(32,'Latte','Un po\' di calcio per le tue ossa','\\\\Uploads\\\\public\\\\productLogo\\\\MuJHxbAJ_cX2YQexNTYIRQ==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\MuJHxbAJ_cX2YQexNTYIRQ==.jpg',9),(33,'Latte di soia ','Un po\' di calcio per le tue ossa','\\\\Uploads\\\\public\\\\productLogo\\\\ZeBINIZHsUOI1TggzG5KlnUDX0hAEr-dLoirWIrqiLk=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\ZeBINIZHsUOI1TggzG5KlnUDX0hAEr-dLoirWIrqiLk=.jpg',9),(34,'Formaggio','Un po\' di calcio per le tue ossa','\\\\Uploads\\\\public\\\\productLogo\\\\Vtr9huI3BiAFSTX0juNgdQ==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\Vtr9huI3BiAFSTX0juNgdQ==.jpg',9),(35,'Brie','Un po\' di calcio per le tue ossa','\\\\Uploads\\\\public\\\\productLogo\\\\58wJkcYbUMwSTjXYEXgL8g==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\58wJkcYbUMwSTjXYEXgL8g==.jpeg',9),(36,'Tosella','Un po\' di calcio per le tue ossa','\\\\Uploads\\\\public\\\\productLogo\\\\4VlDid2ZFN5LLZCzhXWn0Q==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\4VlDid2ZFN5LLZCzhXWn0Q==.jpg',9),(37,'Mascarpone','Un po\' di calcio per le tue ossa','\\\\Uploads\\\\public\\\\productLogo\\\\O5_1yg0ZFhJiFIBcE4waPg==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\O5_1yg0ZFhJiFIBcE4waPg==.jpg',9),(38,'Emmental','Un po\' di calcio per le tue ossa','\\\\Uploads\\\\public\\\\productLogo\\\\CRn7yz_6XCOHuty-gaf5tw==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\CRn7yz_6XCOHuty-gaf5tw==.jpg',9),(39,'Grana Padano','Un po\' di calcio per le tue ossa','\\\\Uploads\\\\public\\\\productLogo\\\\OsdjwYO2pzytZUeMQw8OUA==.png','\\\\Uploads\\\\public\\\\productImage\\\\OsdjwYO2pzytZUeMQw8OUA==.jpg',9),(40,'Gorgonzola','Un po\' di calcio per le tue ossa','\\\\Uploads\\\\public\\\\productLogo\\\\EhtB9FcUS66f9TGQZT-7Mw==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\EhtB9FcUS66f9TGQZT-7Mw==.jpg',9),(41,'Mozzarella','Un po\' di calcio per le tue ossa','\\\\Uploads\\\\public\\\\productLogo\\\\iUZVa8M7JP7gRsZzZxyp6w==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\iUZVa8M7JP7gRsZzZxyp6w==.jpg',9),(42,'Chiodi','Per fissare oggetti e superfici','\\\\Uploads\\\\public\\\\productLogo\\\\upWcrobZ4ckqJZlYRnzshA==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\upWcrobZ4ckqJZlYRnzshA==.jpg',10),(43,'Viti','Per fissare oggetti e superfici','\\\\Uploads\\\\public\\\\productLogo\\\\nmbQEniGXAXnzVUPiZXn5w==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\nmbQEniGXAXnzVUPiZXn5w==.jpg',10),(44,'Dadi','Per fissare oggetti e superfici','\\\\Uploads\\\\public\\\\productLogo\\\\anng7iIxuBYvQp4vb5yECw==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\anng7iIxuBYvQp4vb5yECw==.jpg',10),(45,'Viti torx','Per fissare oggetti e superfici','\\\\Uploads\\\\public\\\\productLogo\\\\hooV5ypKdS6NqmOBMVUtug==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\hooV5ypKdS6NqmOBMVUtug==.jpg',10),(46,'Rondella','Per fissare oggetti e superfici','\\\\Uploads\\\\public\\\\productLogo\\\\zjnucQaxC9aCHNyZHfO7aw==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\zjnucQaxC9aCHNyZHfO7aw==.jpg',10),(47,'Rivetti','Per fissare oggetti e superfici','\\\\Uploads\\\\public\\\\productLogo\\\\z4xb-WIidUyEVYreLpshhA==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\z4xb-WIidUyEVYreLpshhA==.jpg',10),(48,'Elementi di giunzione','Per fissare oggetti e superfici','\\\\Uploads\\\\public\\\\productLogo\\\\eVrZYxke6wlrNSpUjlk332R2yuWiAJ_Z7OBWWNWRS3s=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\eVrZYxke6wlrNSpUjlk332R2yuWiAJ_Z7OBWWNWRS3s=.jpg',10),(49,'Catene','Per fissare oggetti e superfici','\\\\Uploads\\\\public\\\\productLogo\\\\tkCpHsPygCyyX-Jjsmhayw==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\tkCpHsPygCyyX-Jjsmhayw==.jpg',10),(50,'Rinforzo filetti','Per fissare oggetti e superfici','\\\\Uploads\\\\public\\\\productLogo\\\\LdARyq4KcQX52FwRLDzpOSBef9ZgCN5HUj6qpA-eeww=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\LdARyq4KcQX52FwRLDzpOSBef9ZgCN5HUj6qpA-eeww=.jpg',10),(51,'Elementi di tenuta','Per fissare oggetti e superfici','\\\\Uploads\\\\public\\\\productLogo\\\\UUhqkr9a0TIudeP0Lhq1pbGJcKc6JCikL5vxkxt84nk=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\UUhqkr9a0TIudeP0Lhq1pbGJcKc6JCikL5vxkxt84nk=.jpg',10),(52,'Carote','Adatte per molti cibi','\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\productLogo\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\Zy54GZvxOFKLPRqn2xZ_NQ==.jpg','\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\productImage\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\Zy54GZvxOFKLPRqn2xZ_NQ==.jpg',13),(53,'Zucchine','Buone e genuine','\\\\Uploads\\\\public\\\\productLogo\\\\u-O4iHgUi_a_Lfcr05cB5w==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\u-O4iHgUi_a_Lfcr05cB5w==.jpg',13),(54,'Peperoni','Verdi, Gialli, Rossi','\\\\Uploads\\\\public\\\\productLogo\\\\U6JdRIpwyTfOwnCFpupu1g==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\U6JdRIpwyTfOwnCFpupu1g==.jpg',13),(55,'Pomodoro','Pomodoro fresco e ottimo per fare passata','\\\\Uploads\\\\public\\\\productLogo\\\\qpDjrv28jvyUZxTSHcSL2w==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\qpDjrv28jvyUZxTSHcSL2w==.jpg',13),(56,'Broccolo','Sano e nutriente','\\\\Uploads\\\\public\\\\productLogo\\\\QTZQRP-A6-6urBIIkFZT9g==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\QTZQRP-A6-6urBIIkFZT9g==.jpg',13),(57,'Cipolla','Ottima per insaporire i cibi','\\\\Uploads\\\\public\\\\productLogo\\\\mF88bfM_fuxokVtaNfZFDQ==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\mF88bfM_fuxokVtaNfZFDQ==.png',13),(58,'Melanzana','Fresca e genuina','\\\\Uploads\\\\public\\\\productLogo\\\\E0sahJ_LsKeHsFKPzjIqzg==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\E0sahJ_LsKeHsFKPzjIqzg==.jpg',13),(59,'Fagiolini','Freschi e genuini','\\\\Uploads\\\\public\\\\productLogo\\\\8w-eKzacnFkaBH-CULh5Ww==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\8w-eKzacnFkaBH-CULh5Ww==.jpg',13),(60,'Piselli','Freschi e genuini','\\\\Uploads\\\\public\\\\productLogo\\\\0EZlQigD9rfNysYPEF-kWA==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\0EZlQigD9rfNysYPEF-kWA==.jpg',13),(61,'Patata','è un tubero','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productLogo\\\\\\\\\\\\\\\\94y3ABmV8Ndo03AuMlF8YA==.jpg','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productImage\\\\\\\\\\\\\\\\94y3ABmV8Ndo03AuMlF8YA==.jpg',13),(62,'Pastiglie mal di gola - neoBorocillina','Rimedio contro ogni tipo di mal di gola','\\\\Uploads\\\\public\\\\productLogo\\\\2BxHoE5kgOydvx-GtK23EiA9u8GfYdqcV0GvyiZHImHP0aFTAwnBbLs88fKTUUud.jpg','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productImage\\\\\\\\2BxHoE5kgOydvx-GtK23EiA9u8GfYdqcV0GvyiZHImHP0aFTAwnBbLs88fKTUUud.jpg',16),(63,'Tachipirina - Angelini','Paracetamolo dell\'Angelini','\\\\Uploads\\\\public\\\\productLogo\\\\da1WjfC9ef_bnXHW0gnW0qBXmMWv-oCorq59VS_KNgE=.jpg','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productImage\\\\\\\\\\\\\\\\da1WjfC9ef_bnXHW0gnW0qBXmMWv-oCorq59VS_KNgE=.jpeg',16),(64,'Moment - Angelini','Ibuprofene dell\'Angelini','\\\\Uploads\\\\public\\\\productLogo\\\\UmEFnZBMXJNuXDEqGqWEcHWUgFLog9h-_llL51MNp7I=.jpg','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productImage\\\\\\\\UmEFnZBMXJNuXDEqGqWEcHWUgFLog9h-_llL51MNp7I=.jpg',16),(65,'Moment Act - Angelini','Per dolori ancora più forti','\\\\Uploads\\\\public\\\\productLogo\\\\8CpBOXljntXqn0kdK5rpRuCvWGTWzZL09H_B_drLeDg=.jpg','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productImage\\\\\\\\8CpBOXljntXqn0kdK5rpRuCvWGTWzZL09H_B_drLeDg=.png',16),(66,'Valeriana dispert - Vemedia','Per rilassare la mente','\\\\Uploads\\\\public\\\\productLogo\\\\_mHpjbVnwaU7TANNW9ME0HIX97piVBZP6qieYY_5Qbg=.jpg','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productImage\\\\\\\\_mHpjbVnwaU7TANNW9ME0HIX97piVBZP6qieYY_5Qbg=.jpg',16),(67,'Pastiglie mal di gola','Generiche','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\r12rXtl98KbRaOTH_A29WHwX8b32h8vBRhV9dfbh09E=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\r12rXtl98KbRaOTH_A29WHwX8b32h8vBRhV9dfbh09E=.jpg',16),(68,'Antinfiammatorio - Voltadvance','Contro ogni dolore','\\\\Uploads\\\\public\\\\productLogo\\\\_nI2gusMQOyaDFC0OplWrh36z8IudEPo6Yga3nc9ktF1A19IQBK_nS6Iq1iK6oi5.jpg','\\\\Uploads\\\\public\\\\productImage\\\\_nI2gusMQOyaDFC0OplWrh36z8IudEPo6Yga3nc9ktF1A19IQBK_nS6Iq1iK6oi5.jpg',16),(69,'Antinfiammatorio - Alfawassermann','Pastiglie contro il dolore','\\\\Uploads\\\\public\\\\productLogo\\\\Sy_dJhGoj7K_lwXXxi27Dk2rnhUWi8KfFYjYauCOTZ0m0ef9SlKbdxsI_p0ujJL4.jpg','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productImage\\\\\\\\Sy_dJhGoj7K_lwXXxi27Dk2rnhUWi8KfFYjYauCOTZ0m0ef9SlKbdxsI_p0ujJL4.jpg',16),(71,'Multivitaminico - Supradyn','Pastiglie vitaminiche','\\\\Uploads\\\\public\\\\productLogo\\\\i61mfM1r4nJzaMji1Nq3wK8WjmDXjhA2ekqVsZHvy0Q=.gif','\\\\Uploads\\\\public\\\\productImage\\\\i61mfM1r4nJzaMji1Nq3wK8WjmDXjhA2ekqVsZHvy0Q=.png',16),(72,'Vitamine','Generiche','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productLogo\\\\\\\\\\\\\\\\TxxBBu50VT_zx1_duGLWaQ==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\TxxBBu50VT_zx1_duGLWaQ==.jpg',16),(73,'Antibiotico - Augmentin','Antibiotico adatto a tutti','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\YtWNQXxnif1tUOAlTddcZNJoqKmYokYYnt863TlMhd8=.png','\\\\Uploads\\\\public\\\\productImage\\\\YtWNQXxnif1tUOAlTddcZNJoqKmYokYYnt863TlMhd8=.jpg',17),(74,'Amoxicillina - Rathiofarm','Antibiotico contro la bronchite','\\\\Uploads\\\\public\\\\productLogo\\\\9N1IPg-8FdDdx89uk7Rzqdn9d6whU_2iR00_pCah6CI=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\9N1IPg-8FdDdx89uk7Rzqdn9d6whU_2iR00_pCah6CI=.jpg',17),(75,'Monuril - Zambon','Antibiotico per cistite','\\\\Uploads\\\\public\\\\productLogo\\\\8z9gZOsp-LCM-XWitjZ7nh6YWBwdJniFlpG3a82Y8KY=.png','\\\\Uploads\\\\public\\\\productImage\\\\8z9gZOsp-LCM-XWitjZ7nh6YWBwdJniFlpG3a82Y8KY=.jpg',17),(76,'Anauran - Zambon','Antibiotico per otite','\\\\Uploads\\\\public\\\\productLogo\\\\QhcZJCCbgGkoDyVYEH_RTh6YWBwdJniFlpG3a82Y8KY=.png','\\\\Uploads\\\\public\\\\productImage\\\\QhcZJCCbgGkoDyVYEH_RTh6YWBwdJniFlpG3a82Y8KY=.jpg',17),(77,'Macladin','Antibiotico per bambini con tonsillite e bronchite','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\zGhpYFcqciRkb3YSy4FhBQ==.svg','\\\\Uploads\\\\public\\\\productImage\\\\zGhpYFcqciRkb3YSy4FhBQ==.jpg',17),(78,'Antibiotico Tosse','Generico','\\\\Uploads\\\\public\\\\productLogo\\\\0cjFcWYsP2hzYZRzSGWKvSNSFV81efcRuNagbM37CiE=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\0cjFcWYsP2hzYZRzSGWKvSNSFV81efcRuNagbM37CiE=.jpg',17),(79,'Antibiotico per bambini','Generico','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\SyJxx1AuU67o7YwXhFM7PraCS8khAbt8w0zP6wRASdE=.jpg','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productImage\\\\\\\\SyJxx1AuU67o7YwXhFM7PraCS8khAbt8w0zP6wRASdE=.jpg',17),(80,'Antibiotico per denti','Generico','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\q2Gd5mjnAMbzv0KUwsCoManc_5Pe8jm3Z07TJ6lo2zk=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\q2Gd5mjnAMbzv0KUwsCoManc_5Pe8jm3Z07TJ6lo2zk=.jpg',17),(81,'Antibiotici per infezioni intestinali','Generico','\\\\Uploads\\\\public\\\\productLogo\\\\lATJDmfmoT1eeUAp-v7iLXeFi3xhfUVqViH2FJ6iS0toC6fTr2Fm37kCcIJILSi9.jpg','\\\\Uploads\\\\public\\\\productImage\\\\lATJDmfmoT1eeUAp-v7iLXeFi3xhfUVqViH2FJ6iS0toC6fTr2Fm37kCcIJILSi9.jpg',17),(82,'Antibiotico per infezioni cutanee','Generico','\\\\Uploads\\\\public\\\\productLogo\\\\S7RtzMZaa1sMNNR8SyxI8bCOUShb0P2HZ4uOr-9FUOGinaxlsQRT41RWUoXCHPLF.jpg','\\\\Uploads\\\\public\\\\productImage\\\\S7RtzMZaa1sMNNR8SyxI8bCOUShb0P2HZ4uOr-9FUOGinaxlsQRT41RWUoXCHPLF.jpg',17),(83,'Cerotti','Generico','\\\\Uploads\\\\public\\\\productLogo\\\\QuzieUlb0WotXu4AKR6s8w==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\QuzieUlb0WotXu4AKR6s8w==.jpg',18),(84,'Garze','Generico','\\\\Uploads\\\\public\\\\productLogo\\\\9pVIyJYavWl8SYfL2MtPzQ==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\9pVIyJYavWl8SYfL2MtPzQ==.jpg',18),(85,'Cerotti grandi','Generico','\\\\Uploads\\\\public\\\\productLogo\\\\CwKuB8Gaq7RqhuBxy1j4xnUDX0hAEr-dLoirWIrqiLk=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\CwKuB8Gaq7RqhuBxy1j4xnUDX0hAEr-dLoirWIrqiLk=.jpg',18),(86,'Nastri adesivi','Generico','\\\\Uploads\\\\public\\\\productLogo\\\\-nKeDobzRxJ0_ZFWE9a7lXUDX0hAEr-dLoirWIrqiLk=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\-nKeDobzRxJ0_ZFWE9a7lXUDX0hAEr-dLoirWIrqiLk=.jpg',18),(87,'Bendaggio','Generico','\\\\Uploads\\\\public\\\\productLogo\\\\cDmBzFn8m8jC0Jn6U6_9eQ==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\cDmBzFn8m8jC0Jn6U6_9eQ==.jpg',18),(88,'Cerotti - Hansaplast','Cerotti flessibili','\\\\Uploads\\\\public\\\\productLogo\\\\tDjztJGUo2sdx6l7P0VVvBcs_-h7H9Y8-WuDc8qT6gU=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\tDjztJGUo2sdx6l7P0VVvBcs_-h7H9Y8-WuDc8qT6gU=.jpg',18),(89,'Cerotti bambini - Hansaplast','Cerotti con disegni','\\\\Uploads\\\\public\\\\productLogo\\\\75RIzymXV_lOLSC4ZyTlZOBuVGzIfcfhDk8v896HI0E=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\75RIzymXV_lOLSC4ZyTlZOBuVGzIfcfhDk8v896HI0E=.jpg',18),(90,'Garze - Pic Solution','Garze bianche','\\\\Uploads\\\\public\\\\productLogo\\\\8kPuqr4MIujpC07PEc_CrnciJUi47md0x9d74Wkdb9c=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\8kPuqr4MIujpC07PEc_CrnciJUi47md0x9d74Wkdb9c=.jpg',18),(91,'Bendaggio - Pic Solution','Benda','\\\\Uploads\\\\public\\\\productLogo\\\\MgmiX_tvHi7sFDgNyNzY3R5DHEPLTUhtXwox6DqRK20=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\MgmiX_tvHi7sFDgNyNzY3R5DHEPLTUhtXwox6DqRK20=.jpg',18),(92,'Nastro adesivio - Hansaplast ','Nastro per aiutare a medicare','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\UIp6S6attAxSnTnVYnYfyacdqF1Lj6Af29_cfCss66M=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\UIp6S6attAxSnTnVYnYfyacdqF1Lj6Af29_cfCss66M=.jpg',18),(93,'Matita','Materiale scolastico per tutti i giorni','\\\\Uploads\\\\public\\\\productLogo\\\\_YISCTUDDpgjaKvjSLgAtw==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\_YISCTUDDpgjaKvjSLgAtw==.jpeg',24),(94,'Penna','Materiale scolastico per tutti i giorni','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\7L3gYbsta_DypLD6P3Z1IQ==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\7L3gYbsta_DypLD6P3Z1IQ==.jpg',24),(95,'Righello','Materiale scolastico per tutti i giorni','\\\\Uploads\\\\public\\\\productLogo\\\\9ukZavQX9pZIoJlvoikFLA==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\9ukZavQX9pZIoJlvoikFLA==.jpg',24),(96,'Temperino','Materiale scolastico per tutti i giorni','\\\\Uploads\\\\public\\\\productLogo\\\\-BEIHIJqnBmCrdAxw8Sr4g==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\-BEIHIJqnBmCrdAxw8Sr4g==.jpg',24),(97,'Astuccio','Materiale scolastico per tutti i giorni','\\\\Uploads\\\\public\\\\productLogo\\\\7Ye-R89DbbxU6L4XeXNheA==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\7Ye-R89DbbxU6L4XeXNheA==.jpg',24),(98,'Compasso','Materiale scolastico per tutti i giorni','\\\\Uploads\\\\public\\\\productLogo\\\\TAvJUvcxnMnDLbvHd285_Q==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\TAvJUvcxnMnDLbvHd285_Q==.jpg',24),(99,'Quaderno','Materiale scolastico per tutti i giorni','\\\\Uploads\\\\public\\\\productLogo\\\\FExeYbPCoaTj1LUSb65Qfg==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\FExeYbPCoaTj1LUSb65Qfg==.jpg',24),(100,'Quaderno ad anelli','Materiale scolastico per tutti i giorni','\\\\Uploads\\\\public\\\\productLogo\\\\QUZckKd7FRrlXL-Urz7e0mjze5DzTMMuIDLZ1nnN62s=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\QUZckKd7FRrlXL-Urz7e0mjze5DzTMMuIDLZ1nnN62s=.jpg',24),(101,'Diario','Materiale scolastico per tutti i giorni','\\\\Uploads\\\\public\\\\productLogo\\\\RojSDLqwAJy0RZFO7ktsmg==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\RojSDLqwAJy0RZFO7ktsmg==.jpg',24),(102,'Colla - Pritt','Colla stick - non commestibile','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\DRZjtOFZBtALu4t509DX_HUDX0hAEr-dLoirWIrqiLk=.png','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productImage\\\\\\\\DRZjtOFZBtALu4t509DX_HUDX0hAEr-dLoirWIrqiLk=.jpg',24),(103,'Bagnodoccia - Borotalco','Prodotti per l\'igiene personale','\\\\Uploads\\\\public\\\\productLogo\\\\4uPHfPk_BZX2Vlem0SYpWHLZydUwYcuSLBf5sosPtkY=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\4uPHfPk_BZX2Vlem0SYpWHLZydUwYcuSLBf5sosPtkY=.jpg',20),(104,'Sapone','Prodotti per l\'igiene personale','\\\\Uploads\\\\public\\\\productLogo\\\\fk-XIFgqqlleTOZrw5nTIA==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\fk-XIFgqqlleTOZrw5nTIA==.jpg',20),(105,'Shampoo - Nivea','Prodotti per l\'igiene personale','\\\\Uploads\\\\public\\\\productLogo\\\\wr6N3lvDAKPj14i81rrh0UCYaCgiTXwls0v46WX8nUo=.png','\\\\Uploads\\\\public\\\\productImage\\\\wr6N3lvDAKPj14i81rrh0UCYaCgiTXwls0v46WX8nUo=.jpg',20),(106,'Bagnodoccia - Nivea','Prodotti per l\'igiene personale','\\\\Uploads\\\\public\\\\productLogo\\\\CygyxoYwyorOtT1HEr-_ho6klraXVEV5qX72LkjHb14=.png','\\\\Uploads\\\\public\\\\productImage\\\\CygyxoYwyorOtT1HEr-_ho6klraXVEV5qX72LkjHb14=.jpg',20),(107,'Chilly idratante','Prodotti per l\'igiene personale','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productLogo\\\\\\\\\\\\\\\\dBCh0P4FoRutAvnUobAH5RC2HhLvez6G8iJsLRRF3oU=.jpg','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productImage\\\\\\\\\\\\\\\\dBCh0P4FoRutAvnUobAH5RC2HhLvez6G8iJsLRRF3oU=.jpg',20),(108,'Chilly gel','Prodotti per l\'igiene personale','\\\\Uploads\\\\public\\\\productLogo\\\\TANwfN3QZxDOBwdPJxahfw==.jpg','\\\\Uploads\\\\public\\\\productImage\\\\TANwfN3QZxDOBwdPJxahfw==.jpg',20),(109,'Chilly delicato','Prodotti per l\'igiene personale','\\\\Uploads\\\\public\\\\productLogo\\\\NZFP3pnmnU8q12xiH_dPPlz8kBhOJlFk0FEyd8om8SA=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\NZFP3pnmnU8q12xiH_dPPlz8kBhOJlFk0FEyd8om8SA=.jpg',20),(110,'Shampoo antiforfora - Vidal','Prodotti per l\'igiene personale','\\\\Uploads\\\\public\\\\productLogo\\\\0Vcgm7feMOzEfbpIeAa18j4uGnIOO6nlGyCjqACZdKY=.jpg','\\\\Uploads\\\\public\\\\productImage\\\\0Vcgm7feMOzEfbpIeAa18j4uGnIOO6nlGyCjqACZdKY=.jpg',20),(111,'Shampoo liscio & seta - Vidal','Prodotti per l\'igiene personale','\\\\Uploads\\\\public\\\\productLogo\\\\czHyJBc8unuVrLxk8j0FA9RLisRrjFzelwHmZprBnI11A19IQBK_nS6Iq1iK6oi5.jpg','\\\\Uploads\\\\public\\\\productImage\\\\czHyJBc8unuVrLxk8j0FA9RLisRrjFzelwHmZprBnI11A19IQBK_nS6Iq1iK6oi5.jpg',20),(112,'Shamooo colore e luce - Vidal','Prodotti per l\'igiene personale','\\\\Uploads\\\\public\\\\productLogo\\\\6VUagjIcTtQEpG8-_ZK76KNPH_C6o1SPjMkdDZTHGmV1A19IQBK_nS6Iq1iK6oi5.jpg','\\\\Uploads\\\\public\\\\productImage\\\\6VUagjIcTtQEpG8-_ZK76KNPH_C6o1SPjMkdDZTHGmV1A19IQBK_nS6Iq1iK6oi5.jpg',20),(113,'Martello','Utensili per il fai da te','\\\\Uploads\\\\public\\\\productLogo\\\\blhlVE4r4rKHVjSZ-qG2cA==.png','\\\\Uploads\\\\public\\\\productImage\\\\blhlVE4r4rKHVjSZ-qG2cA==.jpg',14),(114,'Cacciavite taglio','Utensili per il fai da te','\\\\Uploads\\\\public\\\\productLogo\\\\m9_EPcIb65_Ka1GXztnkSzH5sL9A9TNkwGPwEGJaRSs=.png','\\\\Uploads\\\\public\\\\productImage\\\\m9_EPcIb65_Ka1GXztnkSzH5sL9A9TNkwGPwEGJaRSs=.jpg',14),(115,'Cacciavite a croce','Utensili per il fai da te','\\\\Uploads\\\\public\\\\productLogo\\\\9k3k6i1Dktcco9FdbiW8pWDeWGwYU67Jy9QROAvtPmA=.png','\\\\Uploads\\\\public\\\\productImage\\\\9k3k6i1Dktcco9FdbiW8pWDeWGwYU67Jy9QROAvtPmA=.jpg',14),(116,'Cacciavite Torx','Utensili per il fai da te','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productLogo\\\\\\\\O48ddwg1g8HpZ7-0iMdJWtA28BPE9dYH9AkSc6gufCY=.png','\\\\\\\\Uploads\\\\\\\\public\\\\\\\\productImage\\\\\\\\O48ddwg1g8HpZ7-0iMdJWtA28BPE9dYH9AkSc6gufCY=.jpg',14),(117,'Pinza','Utensili per il fai da te','\\\\Uploads\\\\public\\\\productLogo\\\\oJg3rIGNZ5lfKrrdvds9UA==.png','\\\\Uploads\\\\public\\\\productImage\\\\oJg3rIGNZ5lfKrrdvds9UA==.jpg',14),(118,'Chiavi a brugola','Utensili per il fai da te','\\\\Uploads\\\\public\\\\productLogo\\\\P76FMpAZHG5ausYf6VffHjjjCzccGEEvimry4t3qiqs=.png','\\\\Uploads\\\\public\\\\productImage\\\\P76FMpAZHG5ausYf6VffHjjjCzccGEEvimry4t3qiqs=.jpg',14),(119,'Cutter','Utensili per il fai da te','\\\\Uploads\\\\public\\\\productLogo\\\\vNwOBHE5O0L4lRGkFn5Jfw==.png','\\\\Uploads\\\\public\\\\productImage\\\\vNwOBHE5O0L4lRGkFn5Jfw==.jpg',14),(120,'Cacciaviti di precisione','Utensili per il fai da te','\\\\Uploads\\\\public\\\\productLogo\\\\mRtOwrpM6CQiCY3DcRXzhVWutQrbj_hnqNpuWX6pCPc=.png','\\\\Uploads\\\\public\\\\productImage\\\\mRtOwrpM6CQiCY3DcRXzhVWutQrbj_hnqNpuWX6pCPc=.jpg',14),(121,'Forbice universale','Utensili per il fai da te','\\\\Uploads\\\\public\\\\productLogo\\\\wYQA8tsDlc9zocFXYpn_Vgozo0utyB1pUnRlespDHH4=.png','\\\\Uploads\\\\public\\\\productImage\\\\wYQA8tsDlc9zocFXYpn_Vgozo0utyB1pUnRlespDHH4=.jpg',14),(122,'Flessometro','Utensili per il fai da te','\\\\Uploads\\\\public\\\\productLogo\\\\V3qO-qfTl99bqYvwjDtTTQ==.png','\\\\Uploads\\\\public\\\\productImage\\\\V3qO-qfTl99bqYvwjDtTTQ==.jpg',14),(123,'Levigatrice','Utensili elettrici per il fai da te','\\\\Uploads\\\\public\\\\productLogo\\\\52THuEak3RbCcxp3_3YLuw==.png','\\\\Uploads\\\\public\\\\productImage\\\\52THuEak3RbCcxp3_3YLuw==.JPG',15),(124,'Sega circolare - Bosch','Utensili elettrici per il fai da te','\\\\Uploads\\\\public\\\\productLogo\\\\_ZAUjo9BC1JA0ME_3tPKM0CqAY_wWGNRK8HFvC2RSO0=.png','\\\\Uploads\\\\public\\\\productImage\\\\_ZAUjo9BC1JA0ME_3tPKM0CqAY_wWGNRK8HFvC2RSO0=.jpg',15),(125,'Seghetto - Bosch','Utensili elettrici per il fai da te','\\\\Uploads\\\\public\\\\productLogo\\\\ymGRP_hw7ZVHYEKFCfmFOhhtsi7ou8OOiV4yoKKACRk=.png','\\\\Uploads\\\\public\\\\productImage\\\\ymGRP_hw7ZVHYEKFCfmFOhhtsi7ou8OOiV4yoKKACRk=.jpg',15),(126,'Smerigliatrice','Utensili elettrici per il fai da te','\\\\Uploads\\\\public\\\\productLogo\\\\lzuYaDqnTiQseuvURPXjGA5kwF3IXALm5PpLdSenBR0=.png','\\\\Uploads\\\\public\\\\productImage\\\\lzuYaDqnTiQseuvURPXjGA5kwF3IXALm5PpLdSenBR0=.jpg',15),(127,'Trapano avvitatore - Bosch','Utensili elettrici per il fai da te','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productLogo\\\\\\\\\\\\\\\\vsvJtQUyAQK9LDHvOKMM0L8XMircTVJxoY_H77BmXjI=.png','\\\\\\\\\\\\\\\\Uploads\\\\\\\\\\\\\\\\public\\\\\\\\\\\\\\\\productImage\\\\\\\\\\\\\\\\vsvJtQUyAQK9LDHvOKMM0L8XMircTVJxoY_H77BmXjI=.jpg',15),(137,'Rivista - Chi','Tutto di tutti','\\\\Uploads\\\\public\\\\productLogo\\\\e-SiI-ynqFsBoO7eQRkPhQ==.png','\\\\Uploads\\\\public\\\\productImage\\\\e-SiI-ynqFsBoO7eQRkPhQ==.jpg',22),(162,'Trapano - Bosch','Utensili elettrici per il fai da te','/Uploads/public/productLogo/Ag4biI8w68gnMWXr_ZYFr3WUro_3wcJM2F-RWaxlWw0=.png','/Uploads/public/productImage/Ag4biI8w68gnMWXr_ZYFr3WUro_3wcJM2F-RWaxlWw0=.jpg',15),(163,'Vanga','Da usare per smuovere la terra','/Uploads/public/productLogo/yXdRYb82U6n8xRjfJ4aDSQ==.jpg','/Uploads/public/productImage/yXdRYb82U6n8xRjfJ4aDSQ==.jpg',12),(164,'Martello demolitore - Bosch','Utensili elettrici per il fai da te','/Uploads/public/productLogo/1hMzEabFfxtccjDzcU6bPpjZMu-iVXH5QL7cRLTFCtk=.png','/Uploads/public/productImage/1hMzEabFfxtccjDzcU6bPpjZMu-iVXH5QL7cRLTFCtk=.jpg',15),(165,'Tasselatore - Bosch','Utensili elettrici per il fai da te','/Uploads/public/productLogo/jsrvlxRFQ9pje8UyqwVGVn7EQpD0-xBo0sMzDBHqI30=.png','/Uploads/public/productImage/jsrvlxRFQ9pje8UyqwVGVn7EQpD0-xBo0sMzDBHqI30=.jpg',15),(166,'Saldatore elettrico - Proxxon','Utensili elettrici per il fai da te','/Uploads/public/productLogo/DdW_q5r5YaAdzdRINDPAP3_mgjSP597eR8_tM7PR6i91A19IQBK_nS6Iq1iK6oi5.gif','/Uploads/public/productImage/DdW_q5r5YaAdzdRINDPAP3_mgjSP597eR8_tM7PR6i91A19IQBK_nS6Iq1iK6oi5.jpg',15),(167,'Sega traforo - Proxxon','Utensili elettrici per il fai da te','/Uploads/public/productLogo/v6IByYgzYilzvNl8qGQAEya1nYymROTMWiz7SQXvw3w=.gif','/Uploads/public/productImage/v6IByYgzYilzvNl8qGQAEya1nYymROTMWiz7SQXvw3w=.jpg',15),(168,'Badile a pala larga','Utile per spalare grandi quantità di neve','/Uploads/public/productLogo/UTB8hfRKocXM0jCNLGyNu9JAgqSSmNsACgs6T2UbPTE=.jpg','/Uploads/public/productImage/UTB8hfRKocXM0jCNLGyNu9JAgqSSmNsACgs6T2UbPTE=.jpg',12),(169,'Badile a manico corto','Utile per spalare piccole quantità di neve','/Uploads/public/productLogo/kwJoPAVZmwfRIokoo6uCbL_meqZ1YnWLFnbwJfekS4c=.jpg','/Uploads/public/productImage/kwJoPAVZmwfRIokoo6uCbL_meqZ1YnWLFnbwJfekS4c=.jpg',12),(170,'Piccone','Usalo per smuovere la terra più dura','/Uploads/public/productLogo/q7r7854EZwDuQkzdGL2uog==.jpg','/Uploads/public/productImage/q7r7854EZwDuQkzdGL2uog==.jpg',12),(171,'Rastrello-scopa','Utile per rastrellare le foglie','/Uploads/public/productLogo/W1bf4VyYDKYzba-KaXsBMX-ykYg00Dwj6-LCNEoUVLA=.jpg','/Uploads/public/productImage/W1bf4VyYDKYzba-KaXsBMX-ykYg00Dwj6-LCNEoUVLA=.jpeg',12),(172,'Rastrello a 14 denti','Utile per rastrellare erba e fieno','/Uploads/public/productLogo/iEg80sg0BDkyZXm_m1K9flQjOzKuxQF7UEZ371MrOX8=.jpg','/Uploads/public/productImage/iEg80sg0BDkyZXm_m1K9flQjOzKuxQF7UEZ371MrOX8=.jpg',12),(173,'Rompighiaccio','Usalo per rompere anche il ghiaccio più spesso','/Uploads/public/productLogo/d3AZJzbC-t9YtLT-eoXg_3UDX0hAEr-dLoirWIrqiLk=.jpg','/Uploads/public/productImage/d3AZJzbC-t9YtLT-eoXg_3UDX0hAEr-dLoirWIrqiLk=.jpeg',12),(174,'Quotidiano - LaVerità','Un po\' di Informazione','/Uploads/public/productLogo/BPUP-ujPn2sCvyNlarOeJQ==.png','/Uploads/public/productImage/BPUP-ujPn2sCvyNlarOeJQ==.jpg',21),(175,'Vanga - Fiskars','Vanga multiuso di buona fattura','/Uploads/public/productLogo/dh8WPJrkJnbHEi2KXUtSWPh1BJxG6l11CqrWa-5uAJE=.jpeg','/Uploads/public/productImage/dh8WPJrkJnbHEi2KXUtSWPh1BJxG6l11CqrWa-5uAJE=.jpg',12),(176,'Accetta - Fiskars','Attenzione! La lama è molto tagliente.','/Uploads/public/productLogo/FXxrQY4tNPMZXCSup9sfNTHm_UQZ6h64oZVKBvvn6ms=.jpeg','/Uploads/public/productImage/FXxrQY4tNPMZXCSup9sfNTHm_UQZ6h64oZVKBvvn6ms=.jpg',12),(177,'Ascia bipenne','Attenzione! La lama è molto tagliente.','/Uploads/public/productLogo/1NwATqrFvpNI4DzUxhta3nUDX0hAEr-dLoirWIrqiLk=.jpg','/Uploads/public/productImage/1NwATqrFvpNI4DzUxhta3nUDX0hAEr-dLoirWIrqiLk=.jpg',12),(178,'Quotidiano - Il fatto Quotidiano','Un po\' di Informazione','/Uploads/public/productLogo/vfxyTLsMUupBG1SHlJx1vEPiaidiMK3Gztnse_lsMFMzp47bymXy_G2mnPY5nlqa.png','/Uploads/public/productImage/vfxyTLsMUupBG1SHlJx1vEPiaidiMK3Gztnse_lsMFMzp47bymXy_G2mnPY5nlqa.jpg',21),(179,'Quotidiano - Libero','Un po\' di Informazione','/Uploads/public/productLogo/d79IIrZ60cdCOOt-zpnWaTJZFLUafZ5Xl5p9yG_6dEY=.png','/Uploads/public/productImage/d79IIrZ60cdCOOt-zpnWaTJZFLUafZ5Xl5p9yG_6dEY=.jpg',21),(180,'Quotidiano - Il tempo','Un po\' di Informazione','/Uploads/public/productLogo/3cbem2-8B1R6Tw0V9DiiqxrJ7QW3zw05Oy6lOq5SRrE=.png','/Uploads/public/productImage/3cbem2-8B1R6Tw0V9DiiqxrJ7QW3zw05Oy6lOq5SRrE=.jpg',21),(181,'Quotidiano - La Repubblica','Un po\' di Informazione','/Uploads/public/productLogo/0O0SNw131JJEgyZcI2rk0YS0H8u7eO2nqc3wKiGTjoE=.png','/Uploads/public/productImage/0O0SNw131JJEgyZcI2rk0YS0H8u7eO2nqc3wKiGTjoE=.jpg',21),(182,'Corda 20 mm','Corda in fibra naturale','/Uploads/public/productLogo/txTq3axQ2GYFQox3Ru-gYg==.jpg','/Uploads/public/productImage/txTq3axQ2GYFQox3Ru-gYg==.jpg',11),(183,'Quotidiano - Il Messaggero','Un po\' di Informazione','/Uploads/public/productLogo/slAEfB0YhMW4e9aW0_qb1sJZBc2f9NiCQNJca7Uxa_s=.png','/Uploads/public/productImage/slAEfB0YhMW4e9aW0_qb1sJZBc2f9NiCQNJca7Uxa_s=.jpg',21),(184,'Quotidiano - Il Gazzettino','Un po\' di Informazione','/Uploads/public/productLogo/oThHWpplMfRNyCEOGkQ_u898ISn8GxG5zBcF5cEGJEI=.png','/Uploads/public/productImage/oThHWpplMfRNyCEOGkQ_u898ISn8GxG5zBcF5cEGJEI=.jpg',21),(185,'Corda in nylon','Corda sintetica','/Uploads/public/productLogo/mZPLgQyLmK-i8Gdc8SeN083YvYNtM1inge9edCFX1tQ=.jpg','/Uploads/public/productImage/mZPLgQyLmK-i8Gdc8SeN083YvYNtM1inge9edCFX1tQ=.jpg',11),(186,'Quotidiano - Il manifesto','Un po\' di Informazione','/Uploads/public/productLogo/gkN7ggRLMHS1LxDND9WwbJ9cw-4elOVHtVrZoK7DFWc=.jpg','/Uploads/public/productImage/gkN7ggRLMHS1LxDND9WwbJ9cw-4elOVHtVrZoK7DFWc=.jpg',21),(187,'Corda per saltare in nylon - Adidas','Perfetta per i principianti','/Uploads/public/productLogo/PZE5K2TWVqsOSpZIsPn3d_vnpYjN1XWjsn1Z9DOv0i9omyNfAmYMU9kEn3mwkJZf.jpg','/Uploads/public/productImage/PZE5K2TWVqsOSpZIsPn3d_vnpYjN1XWjsn1Z9DOv0i9omyNfAmYMU9kEn3mwkJZf.jpg',11),(188,'Quotidiano - Il dubbio','Un po\' di Informazione','/Uploads/public/productLogo/w8VrMkDS_n4EzXZ-oQ8e0JNthYDx10jmlFxb0mxRGgo=.png','/Uploads/public/productImage/w8VrMkDS_n4EzXZ-oQ8e0JNthYDx10jmlFxb0mxRGgo=.jpg',21),(189,'Corda per saltare in cuoio - Domyos','Perfetta per salti veloci. I principianti rischiano di farsi male!','/Uploads/public/productLogo/l2pNCOm9TRLC3FV1-p1bYFqECG4e-HCEpUJQWk_WQ-zcnGLagOH36jijLUv9vCSy.jpg','/Uploads/public/productImage/l2pNCOm9TRLC3FV1-p1bYFqECG4e-HCEpUJQWk_WQ-zcnGLagOH36jijLUv9vCSy.jpg',11),(190,'Quotidiano - La stampa','Un po\' di Informazione','/Uploads/public/productLogo/H4GsIBXyA-Tw4QWTlAsmZ0kkLhT4PPzfFRHiHJ4V_AU=.png','/Uploads/public/productImage/H4GsIBXyA-Tw4QWTlAsmZ0kkLhT4PPzfFRHiHJ4V_AU=.jpg',21),(191,'Nastro rosso 20 mm','Usalo per i tuoi regali di Natale e San Valentino','/Uploads/public/productLogo/Z9gKGbqYUKooELUvW0uwdPa7ch5yIM_KCx4m7MxYP3M=.jpg','/Uploads/public/productImage/Z9gKGbqYUKooELUvW0uwdPa7ch5yIM_KCx4m7MxYP3M=.jpg',11),(192,'Corda da arrampicata','La sicurezza prima di tutto','/Uploads/public/productLogo/waRCktN7jDd-oeWjirXtalftqIjtpSEQjyxyuwce-yU=.jpg','/Uploads/public/productImage/waRCktN7jDd-oeWjirXtalftqIjtpSEQjyxyuwce-yU=.jpg',11),(193,'Nastro da cerimonia','Nastri per le grandi occasioni','/Uploads/public/productLogo/Lr5PjQ9fKqIfZSiTpOyKBG16p6_9igNhduqg3BvdOR4=.jpg','/Uploads/public/productImage/Lr5PjQ9fKqIfZSiTpOyKBG16p6_9igNhduqg3BvdOR4=.jpg',11),(194,'Corda nautica','Ideale per la barca a vela','/Uploads/public/productLogo/63rhJYiAMChPkI1TBL-YnnUDX0hAEr-dLoirWIrqiLk=.jpg','/Uploads/public/productImage/63rhJYiAMChPkI1TBL-YnnUDX0hAEr-dLoirWIrqiLk=.jpg',11),(195,'Nastro adesivo largo - Staples','Attacca tutto','/Uploads/public/productLogo/A0dCnvtueaiIKBtHWbhkmiGxbXy5_B_Nmv9XbBsv2EEicu2z8uA1_V2bGoNud-rD.gif','/Uploads/public/productImage/A0dCnvtueaiIKBtHWbhkmiGxbXy5_B_Nmv9XbBsv2EEicu2z8uA1_V2bGoNud-rD.jpg',11),(196,'Nastro di Moebius','Ideale per rimanere sempre dalla stessa parte!','/Uploads/public/productLogo/ooEnjwbpN_sBQbT6AEx7wZaqSRcSYKbwCrXcrpu9lEs=.jpg','/Uploads/public/productImage/ooEnjwbpN_sBQbT6AEx7wZaqSRcSYKbwCrXcrpu9lEs=.jpg',11),(197,'Multicentrum','Multivitaminico','/Uploads/public/productLogo/bPJGr2-ObGavujD0vsupuA==.png','/Uploads/public/productImage/bPJGr2-ObGavujD0vsupuA==.jpg',19),(198,'Creatina Vector','Multivitaminico per sportivi','/Uploads/public/productLogo/WRXf5SjJDzpKA36zfWy5yAA3Y12fqmK6aiCM8dkfAZg=.png','/Uploads/public/productImage/WRXf5SjJDzpKA36zfWy5yAA3Y12fqmK6aiCM8dkfAZg=.jpg',19),(199,'Sustenium Plus','Multivitaminico Energizzante','/Uploads/public/productLogo/bT6Rqy9rZK8O3h05EkFnECJy7bPy4DX9XZsag2536sM=.png','/Uploads/public/productImage/bT6Rqy9rZK8O3h05EkFnECJy7bPy4DX9XZsag2536sM=.jpg',19),(200,'Relax System','Integratore per rilassarsi','/Uploads/public/productLogo/r0Ojs_GofBFZ0JPF07_IsA==.png','/Uploads/public/productImage/r0Ojs_GofBFZ0JPF07_IsA==.jpg',19),(201,'Protezione Retina','Integratore per l\'invecchiamento cellulare','/Uploads/public/productLogo/ENSN6NVnH7Hbka1NDPPwC-hObLlr4oTvktcXLzT8pJg=.jpg','/Uploads/public/productImage/ENSN6NVnH7Hbka1NDPPwC-hObLlr4oTvktcXLzT8pJg=.jpg',19),(202,'PesoMeno Blocker','Integratore per dieta','/Uploads/public/productLogo/pyh7IJeTHb60Xm8xoN8tnlaspkOkX75XRLgTe5tJ3Yo=.png','/Uploads/public/productImage/pyh7IJeTHb60Xm8xoN8tnlaspkOkX75XRLgTe5tJ3Yo=.jpg',19),(203,'Respiro Influ','Integratore per aiutare la respirazione','/Uploads/public/productLogo/TuUrM7mwKHuGxyvDw_KyQ3UDX0hAEr-dLoirWIrqiLk=.png','/Uploads/public/productImage/TuUrM7mwKHuGxyvDw_KyQ3UDX0hAEr-dLoirWIrqiLk=.jpg',19),(204,'Vitadyn','Integratore di Magnesio e Potassio','/Uploads/public/productLogo/i0NfjRyjtd2v0nWxA9apLw==.jpg','/Uploads/public/productImage/i0NfjRyjtd2v0nWxA9apLw==.jpg',19),(205,'Monster Gainer','Integratore di Proteine','/Uploads/public/productLogo/3sdZeS3ZLJC453Bu27yvZaUVERkckw7iKvdciLwGSMI=.jpg','/Uploads/public/productImage/3sdZeS3ZLJC453Bu27yvZaUVERkckw7iKvdciLwGSMI=.jpg',19),(206,'Carbo Mix XXL','Integratore di Carboidrati','/Uploads/public/productLogo/CdV0AQ5eMwuLkoE_TmDgwXUDX0hAEr-dLoirWIrqiLk=.jpg','/Uploads/public/productImage/CdV0AQ5eMwuLkoE_TmDgwXUDX0hAEr-dLoirWIrqiLk=.jpg',19),(207,'Rivista - Vero','Tutto di tutti','/Uploads/public/productLogo/0bM6bUkWwmDkGZvKCHMPomgBHj8kZhDk8KutpNGmb78=.jpg','/Uploads/public/productImage/0bM6bUkWwmDkGZvKCHMPomgBHj8kZhDk8KutpNGmb78=.jpg',22),(208,'Rivista - Tutto','Tutto di tutti','/Uploads/public/productLogo/BE75YjnmfXOSPLK8vDFYrFz8kBhOJlFk0FEyd8om8SA=.png','/Uploads/public/productImage/BE75YjnmfXOSPLK8vDFYrFz8kBhOJlFk0FEyd8om8SA=.jpg',22),(209,'Rivista - dipiù','Tutto di tutti','/Uploads/public/productLogo/TTHXPBygZ8LIT14_ozsS6MxD-IQOetA6-1p1j0EzGN4=.jpg','/Uploads/public/productImage/TTHXPBygZ8LIT14_ozsS6MxD-IQOetA6-1p1j0EzGN4=.jpg',22),(210,'Rivista - Dive e donna','Tutto di tutti','/Uploads/public/productLogo/kR7hRmexzaCtvgNF5SOmQHyMTi_jzvhy83JeBBHUq1Y=.jpg','/Uploads/public/productImage/kR7hRmexzaCtvgNF5SOmQHyMTi_jzvhy83JeBBHUq1Y=.jpg',22),(211,'Rivista - Donna Moderna','Tutto di tutti','/Uploads/public/productLogo/ZXPGyITrDEpAfIJ631GjlYbmkY7Us7lPIcVCmBM4O44=.jpg','/Uploads/public/productImage/ZXPGyITrDEpAfIJ631GjlYbmkY7Us7lPIcVCmBM4O44=.jpg',22),(212,'Rivista - Vip','Tutto di tutti','/Uploads/public/productLogo/3ldKI-UBextbF1AXRVsL_HUDX0hAEr-dLoirWIrqiLk=.jpg','/Uploads/public/productImage/3ldKI-UBextbF1AXRVsL_HUDX0hAEr-dLoirWIrqiLk=.jpg',22),(213,'Italia Squisita','Cucina Italiana','/Uploads/public/productLogo/tkS3OBzsOJr5xvDFbQbJPDHO1rGkHGFTlInXxjWyugg=.jpg','/Uploads/public/productImage/tkS3OBzsOJr5xvDFbQbJPDHO1rGkHGFTlInXxjWyugg=.jpg',23),(214,'Rivista - Stop','Tutto di tutti','/Uploads/public/productLogo/J55H_3Uzoe_Gg7-f8xeHFbUvgEOaD-acWfpMrB_Afss=.jpg','/Uploads/public/productImage/J55H_3Uzoe_Gg7-f8xeHFbUvgEOaD-acWfpMrB_Afss=.jpg',22),(215,'Sale & Pepe','Cucina Generale','/Uploads/public/productLogo/9IfqjK6mAItfTE7xzq1e-Q==.jpg','/Uploads/public/productImage/9IfqjK6mAItfTE7xzq1e-Q==.jpg',23),(216,'Rivista - Novella','Tutto di tutti','/Uploads/public/productLogo/i7jvFph4PNxlbf8DShuzmsef6FSsclF5-YpmfnIdrfY=.png','/Uploads/public/productImage/i7jvFph4PNxlbf8DShuzmsef6FSsclF5-YpmfnIdrfY=.jpg',22),(217,'Cucina Moderna','Cucina Moderna','/Uploads/public/productLogo/jzYLDOhCv6jZWSInHbU9JaMReIIwEalnu5gsIGsCpac=.jpg','/Uploads/public/productImage/jzYLDOhCv6jZWSInHbU9JaMReIIwEalnu5gsIGsCpac=.jpg',23),(218,'Cucina di Campagna','Cucina Tradizionale','/Uploads/public/productLogo/rM_6dNHUklPmsQ2XI07ShtwfkvXB_qSTCySqDtVcqBE=.jpg','/Uploads/public/productImage/rM_6dNHUklPmsQ2XI07ShtwfkvXB_qSTCySqDtVcqBE=.jpg',23),(219,'Oggi Cucino','Cucina Generale','/Uploads/public/productLogo/dhCcDMeOqa1jrqyd8dBSAA==.jpg','/Uploads/public/productImage/dhCcDMeOqa1jrqyd8dBSAA==.jpg',23),(220,'Jamie','Cucina Francese','/Uploads/public/productLogo/Lwi_knOw5S3BDxRfg4rs3w==.jpg','/Uploads/public/productImage/Lwi_knOw5S3BDxRfg4rs3w==.jpg',23),(221,'Cucina Vegetariana','Cucina Vegetariana','/Uploads/public/productLogo/gb808LMWl2b8nnvEdVw3pOPiSZpA3ZFg7u0IWH4Zpy4=.jpg','/Uploads/public/productImage/gb808LMWl2b8nnvEdVw3pOPiSZpA3ZFg7u0IWH4Zpy4=.jpg',23),(222,'Giallo Zafferano','Cucina Generale','/Uploads/public/productLogo/uf9-7tmu29PMu5rhBb4s9TOnjtvKZfL8baac9jmeWpo=.jpg','/Uploads/public/productImage/uf9-7tmu29PMu5rhBb4s9TOnjtvKZfL8baac9jmeWpo=.jpg',23),(223,'Rivista - Visto','Tutto di tutti','/Uploads/public/productLogo/opkmPskVouGsP3TTqbqicVz8kBhOJlFk0FEyd8om8SA=.jpg','/Uploads/public/productImage/opkmPskVouGsP3TTqbqicVz8kBhOJlFk0FEyd8om8SA=.jpg',22),(224,'Rivista - Eva ','Tutto di tutti','/Uploads/public/productLogo/oomB_gwFPUXpL13P2K0nD0NnlCNEr2O-YRtJzGOByL4=.png','/Uploads/public/productImage/oomB_gwFPUXpL13P2K0nD0NnlCNEr2O-YRtJzGOByL4=.jpg',22),(225,'Ricette Per Il Mio Bimby','Cucina per Bambini','/Uploads/public/productLogo/7N00p5GkyXuvqs8r6WiVaA2Wp-ohAVDAE0Hs08YT2T8=.jpg','/Uploads/public/productImage/7N00p5GkyXuvqs8r6WiVaA2Wp-ohAVDAE0Hs08YT2T8=.jpg',23),(226,'Bustina - Gormiti ','Giochi per i più piccolini','/Uploads/public/productLogo/KtvTyWXvnzEdT5lMOi7JYQ==.jpg','/Uploads/public/productImage/KtvTyWXvnzEdT5lMOi7JYQ==.jpg',25),(227,'La Cucina Italiana','Cucina Italiana','/Uploads/public/productLogo/8x04qxyx0Ly6GjMwFr2UHrxr4x--sPFaRCLf5192dk0=.jpg','/Uploads/public/productImage/8x04qxyx0Ly6GjMwFr2UHrxr4x--sPFaRCLf5192dk0=.jpg',23),(228,'Bustina di carte - Dragonball','Giochi per i più piccolini','/Uploads/public/productLogo/O55i1FkJOZQI9ExoVpVMFzTBMRgoZ40tzS-78yFAUO0=.jpg','/Uploads/public/productImage/O55i1FkJOZQI9ExoVpVMFzTBMRgoZ40tzS-78yFAUO0=.jpg',25),(229,'Bustina di carte - Yu-Gi-Oh! ','Giochi per i più piccolini','/Uploads/public/productLogo/kLMLIGwdjnyliPTDhiU7DL7_Sfg0c6gBqutLWLVc0At1A19IQBK_nS6Iq1iK6oi5.jpg','/Uploads/public/productImage/kLMLIGwdjnyliPTDhiU7DL7_Sfg0c6gBqutLWLVc0At1A19IQBK_nS6Iq1iK6oi5.jpg',25),(230,'Deck Yu-Gi-Oh!','Giochi per i più piccolini','/Uploads/public/productLogo/QBX3oMu_UW8dhcqYi_M2gc85AtA00yAODNvAHjg22_U=.jpg','/Uploads/public/productImage/QBX3oMu_UW8dhcqYi_M2gc85AtA00yAODNvAHjg22_U=.jpg',25),(231,'Bustina di carte - Pokémon','Giochi per i più piccolini','/Uploads/public/productLogo/3mTKYB_xAj9jIVCahCeLQOAXYhBO79dr6CW1Ic6tdXc=.jpg','/Uploads/public/productImage/3mTKYB_xAj9jIVCahCeLQOAXYhBO79dr6CW1Ic6tdXc=.jpg',25),(232,'Mazzo di carte - Pokémon','Giochi per i più piccolini','/Uploads/public/productLogo/i996KWHkSSgRQhjj7H7PR-VomA0xDXzyt1XAOt5Ax_c=.jpg','/Uploads/public/productImage/i996KWHkSSgRQhjj7H7PR-VomA0xDXzyt1XAOt5Ax_c=.jpg',25),(233,'Slime - Skifidol','Giochi per i più piccolini','/Uploads/public/productLogo/CJXEb436WIzSjZZO44XTszwcJo1F39KEMBHkpeK14Hg=.jpg','/Uploads/public/productImage/CJXEb436WIzSjZZO44XTszwcJo1F39KEMBHkpeK14Hg=.jpg',25),(234,'Bustina - Calciatori panini','Giochi per i più piccolini','/Uploads/public/productLogo/CGKdTS1EQGLt9N1Xc4TRVlIy8ewAlXZ-vsyz3KscWJA=.jpg','/Uploads/public/productImage/CGKdTS1EQGLt9N1Xc4TRVlIy8ewAlXZ-vsyz3KscWJA=.jpg',25),(235,'Album - Calciatori panini','Giochi per i più piccolini','/Uploads/public/productLogo/y33aoMUNKKpV-H-1z7kRwr40gGPmhU04cNgN6I3Sm-E=.jpg','/Uploads/public/productImage/y33aoMUNKKpV-H-1z7kRwr40gGPmhU04cNgN6I3Sm-E=.jpg',25),(236,'Bustina di carte - Gormiti','Giochi per i più piccolini','/Uploads/public/productLogo/1IeOtnmVLxX-L7sdy3BtmMM2FYaG7X3MEMCLLiuU2cs=.jpg','/Uploads/public/productImage/1IeOtnmVLxX-L7sdy3BtmMM2FYaG7X3MEMCLLiuU2cs=.jpg',25);
/*!40000 ALTER TABLE `publicproducts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `publicproductsonlists`
--

DROP TABLE IF EXISTS `publicproductsonlists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `publicproductsonlists` (
  `idlist` int(11) NOT NULL,
  `idpublicproduct` int(11) NOT NULL,
  `quantity` mediumint(9) NOT NULL DEFAULT '1',
  `lastinsert` datetime(1) DEFAULT CURRENT_TIMESTAMP(1),
  `exp_average` int(11) DEFAULT '-1',
  `add_count` mediumint(8) unsigned DEFAULT '0',
  PRIMARY KEY (`idlist`,`idpublicproduct`),
  KEY `publicproductsonlists_ibfk_2` (`idpublicproduct`),
  CONSTRAINT `publicproductsonlists_ibfk_1` FOREIGN KEY (`idlist`) REFERENCES `lists` (`id`) ON DELETE CASCADE,
  CONSTRAINT `publicproductsonlists_ibfk_2` FOREIGN KEY (`idpublicproduct`) REFERENCES `publicproducts` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `publicproductsonlists`
--

LOCK TABLES `publicproductsonlists` WRITE;
/*!40000 ALTER TABLE `publicproductsonlists` DISABLE KEYS */;
INSERT INTO `publicproductsonlists` VALUES (9,2,-1,'2019-01-23 20:50:57.7',-1,1),(9,5,-1,'2019-01-23 20:57:03.8',-1,1),(9,9,-1,'2019-01-23 20:57:13.1',-1,1),(10,3,-1,'2019-01-24 11:31:23.7',-1,1),(10,4,1,'2019-01-25 09:48:39.4',-1,1),(10,12,-1,'2019-01-25 08:22:21.2',-1,1),(10,14,-1,'2019-01-25 08:22:32.2',-1,1),(10,15,-1,'2019-01-23 12:14:51.4',-1,1),(10,34,-1,'2019-01-23 18:47:24.3',-1,1),(10,53,-1,'2019-01-23 14:47:41.6',157,2),(10,61,-1,'2019-01-23 12:39:17.0',-1,1),(10,102,-1,'2019-01-23 12:21:17.7',-1,1),(12,74,-1,'2019-01-25 08:26:41.8',-1,1);
/*!40000 ALTER TABLE `publicproductsonlists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sharedlists`
--

DROP TABLE IF EXISTS `sharedlists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `sharedlists` (
  `iduser` int(11) NOT NULL,
  `idlist` int(11) NOT NULL,
  `modifylist` tinyint(1) NOT NULL DEFAULT '0',
  `deletelist` tinyint(1) NOT NULL DEFAULT '0',
  `adddelete` tinyint(1) NOT NULL DEFAULT '1',
  `lastchataccess` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`iduser`,`idlist`),
  KEY `sharedlists_ibfk_2` (`idlist`),
  CONSTRAINT `sharedlists_ibfk_1` FOREIGN KEY (`iduser`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `sharedlists_ibfk_2` FOREIGN KEY (`idlist`) REFERENCES `lists` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sharedlists`
--

LOCK TABLES `sharedlists` WRITE;
/*!40000 ALTER TABLE `sharedlists` DISABLE KEYS */;
INSERT INTO `sharedlists` VALUES (2,7,0,0,0,'2019-01-25 09:48:27'),(2,10,1,1,1,'2019-01-25 09:48:31'),(18,10,0,0,0,'2019-01-25 08:26:10');
/*!40000 ALTER TABLE `sharedlists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(64) NOT NULL,
  `name` varchar(40) NOT NULL,
  `lastname` varchar(40) NOT NULL,
  `image` varchar(2083) DEFAULT NULL,
  `administrator` tinyint(1) NOT NULL DEFAULT '0',
  `tokenpassword` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'padovanmatteo96@gmail.com','i4oZ89A6KtcpuyZ0O7yGy+EA+EaAiwFQpYAOCHE5jQo=','Primo','Brown','\\\\\\\\Uploads\\\\\\\\restricted\\\\\\\\avatar\\\\\\\\MVjjb1xWaK5IO4Kb_FiE4tRIHeOZU5L8gGMgiKENYzQ=.jpg',1,NULL),(2,'giuliacarocari@gmail.com','5KH6Jrt7y6kEMdVhYa75Of/7DJaxWVcQFlSEP3jMWfk=','Giulia','Carocari','\\\\Uploads\\\\restricted\\\\avatar\\\\user-34182.jpg',1,NULL),(3,'simonelever@gmail.com','DmDFBEgMt8OuBWPXGHdHl8illC/0aZxZqAGw3y5wyeE=','Simone','Lever','',1,NULL),(5,'mariorossi@shoppinglist.com','sTOgwOm+474gFj0q0x1iSNspKqbcse4IeiqlDg/HWuI=','Mario','Rossi','\\\\Uploads\\\\restricted\\\\avatar\\\\user-34203',0,NULL),(6,'mariaverdi@shoppinglist.com','sTOgwOm+474gFj0q0x1iSNspKqbcse4IeiqlDg/HWuI=','Maria','Verdi','\\\\Uploads\\\\restricted\\\\avatar\\\\user-34214.jpeg',0,NULL),(7,'annamarchi@shoppinglist.com','sTOgwOm+474gFj0q0x1iSNspKqbcse4IeiqlDg/HWuI=','Anna','Marchi','\\\\Uploads\\\\restricted\\\\avatar\\\\user-34227.jpg',0,NULL),(8,'luciadalla@shoppinglist.com','sTOgwOm+474gFj0q0x1iSNspKqbcse4IeiqlDg/HWuI=','Lucia','D\'Alla','\\\\Uploads\\\\restricted\\\\avatar\\\\user-34242.jpg',0,NULL),(9,'marcomentana@shoppinglist.com','sTOgwOm+474gFj0q0x1iSNspKqbcse4IeiqlDg/HWuI=','Marco','Mentana','\\\\Uploads\\\\restricted\\\\avatar\\\\user-34259.png',0,NULL),(10,'francescarim@shoppinglist.com','sTOgwOm+474gFj0q0x1iSNspKqbcse4IeiqlDg/HWuI=','Francesca','Da Rimini','\\\\Uploads\\\\restricted\\\\avatar\\\\user-34278.jpg',0,NULL),(11,'hoelderlin@shoppinglist.com','sTOgwOm+474gFj0q0x1iSNspKqbcse4IeiqlDg/HWuI=','Friederich','Hoelderlin','\\\\Uploads\\\\restricted\\\\avatar\\\\user-34299.jpg',0,NULL),(12,'jjrousseau@shoppinglist.com','sTOgwOm+474gFj0q0x1iSNspKqbcse4IeiqlDg/HWuI=','Jean Jacques','Rousseau','\\\\Uploads\\\\restricted\\\\avatar\\\\user-34322.png',0,NULL),(13,'fabsut@shoppinglist.com','sTOgwOm+474gFj0q0x1iSNspKqbcse4IeiqlDg/HWuI=','Fabien','Suter','\\\\Uploads\\\\restricted\\\\avatar\\\\user-34347.jpg',0,NULL),(14,'l.vonn@shoppinglist.com','sTOgwOm+474gFj0q0x1iSNspKqbcse4IeiqlDg/HWuI=','Lindsey','Vonn','\\\\Uploads\\\\restricted\\\\avatar\\\\user-34374.jpg',0,NULL),(15,'pippo.verga@shoppinglist.com','sTOgwOm+474gFj0q0x1iSNspKqbcse4IeiqlDg/HWuI=','Filippo','Verga','\\\\Uploads\\\\restricted\\\\avatar\\\\user-34403.jpg',0,NULL),(16,'linda.vannini@libero.it','yyPC37RPurtLZwoJkw93KbghDqUDJyA6Ahpc46t64Vg=','Linda','Vannini','',0,NULL),(17,'michele.tessari@studenti.unitn.it','TToBf7k49U1GCaORa1Ml0NR4LVz2yg1CYU2HublSxwg=','Michele','Tessari','/Uploads/restricted/avatar/user-34467.jpg',1,NULL),(18,'mpadovan@live.it','i4oZ89A6KtcpuyZ0O7yGy+EA+EaAiwFQpYAOCHE5jQo=','Matteo','Padovan','\\\\Uploads\\\\restricted\\\\avatar\\\\user-34502.jpg',0,NULL),(19,'giu.peserico@gmail.com','QYzs4OySJM2CWF8lJCoRsuUrkqjCJ9KxjMj166Sb2gU=','Giulia','Peserico','',1,NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-01-25 10:44:38
