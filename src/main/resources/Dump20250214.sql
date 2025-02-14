-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: franchise_management
-- ------------------------------------------------------
-- Server version	9.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `company_purchases`
--

DROP TABLE IF EXISTS `company_purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company_purchases` (
  `company_purchase_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `date_of_purchase` date DEFAULT NULL,
  `wholesale_price` double DEFAULT NULL,
  `distributor_price` double DEFAULT NULL,
  PRIMARY KEY (`company_purchase_id`),
  UNIQUE KEY `unique_product_date` (`product_id`,`date_of_purchase`),
  CONSTRAINT `company_purchases_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_purchases`
--

LOCK TABLES `company_purchases` WRITE;
/*!40000 ALTER TABLE `company_purchases` DISABLE KEYS */;
INSERT INTO `company_purchases` VALUES (9,1,100,'2025-02-12',NULL,NULL),(10,2,100,'2025-02-12',NULL,NULL),(11,3,100,'2025-02-12',NULL,NULL);
/*!40000 ALTER TABLE `company_purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company_stocks`
--

DROP TABLE IF EXISTS `company_stocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `company_stocks` (
  `product_id` int NOT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  CONSTRAINT `company_stocks_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company_stocks`
--

LOCK TABLES `company_stocks` WRITE;
/*!40000 ALTER TABLE `company_stocks` DISABLE KEYS */;
INSERT INTO `company_stocks` VALUES (1,50),(2,20),(3,0);
/*!40000 ALTER TABLE `company_stocks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `franchises`
--

DROP TABLE IF EXISTS `franchises`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `franchises` (
  `franchise_id` int NOT NULL AUTO_INCREMENT,
  `location` varchar(50) DEFAULT NULL,
  `building_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`franchise_id`),
  UNIQUE KEY `location` (`location`,`building_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `franchises`
--

LOCK TABLES `franchises` WRITE;
/*!40000 ALTER TABLE `franchises` DISABLE KEYS */;
INSERT INTO `franchises` VALUES (1,'Edapally','Lulu Mall'),(2,'Thodupuzha','Cental Mall');
/*!40000 ALTER TABLE `franchises` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `product_name` varchar(50) DEFAULT NULL,
  `product_company` varchar(50) DEFAULT NULL,
  `retail_price` double DEFAULT NULL,
  PRIMARY KEY (`product_id`),
  UNIQUE KEY `product_name` (`product_name`,`product_company`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'S24','Samsung',NULL),(2,'iPhone 16','Apple',NULL),(3,'IdeaPad Slim3','Lenovo',NULL),(4,'Redmi 16','Xiomi',NULL),(16,'Iphone 16 Pro','Apple',NULL);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchases`
--

DROP TABLE IF EXISTS `purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `purchases` (
  `purchase_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `user_id` varchar(20) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `date_of_purchase` date DEFAULT NULL,
  `returned` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`purchase_id`),
  KEY `product_id` (`product_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `purchases_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`),
  CONSTRAINT `purchases_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchases`
--

LOCK TABLES `purchases` WRITE;
/*!40000 ALTER TABLE `purchases` DISABLE KEYS */;
INSERT INTO `purchases` VALUES (2,1,'TAF001',1,'2025-02-12',0),(3,1,'TAF001',1,'2025-02-12',0),(4,1,'TAF001',1,'2025-02-12',0),(5,1,'TAF001',1,'2025-02-12',0),(6,1,'TAF001',4,'2025-02-12',0),(7,1,'TAF001',6,'2025-02-12',0),(8,2,'TAF001',10,'2025-02-12',1),(9,1,'TAF001',10,'2025-02-14',1),(10,2,'TAF001',10,'2025-02-14',1),(11,3,'TAF001',10,'2025-02-14',1),(12,1,'TAF001',10,'2025-02-14',1),(13,2,'TAF001',10,'2025-02-14',1),(14,3,'TAF001',10,'2025-02-14',1),(15,2,'TAF001',10,'2025-02-14',0),(16,3,'TAF001',10,'2025-02-14',0),(19,2,'TAF001',5,'2025-02-14',0),(20,3,'TAF001',5,'2025-02-14',0),(21,1,'TAF001',5,'2025-02-14',0);
/*!40000 ALTER TABLE `purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requests`
--

DROP TABLE IF EXISTS `requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `requests` (
  `request_id` int NOT NULL AUTO_INCREMENT,
  `franchise_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`request_id`),
  KEY `franchise_id` (`franchise_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `requests_ibfk_1` FOREIGN KEY (`franchise_id`) REFERENCES `franchises` (`franchise_id`) ON DELETE CASCADE,
  CONSTRAINT `requests_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requests`
--

LOCK TABLES `requests` WRITE;
/*!40000 ALTER TABLE `requests` DISABLE KEYS */;
INSERT INTO `requests` VALUES (2,1,1,50,'ACCEPTED'),(3,1,2,80,'ACCEPTED'),(4,1,3,130,'REJECTED'),(5,1,3,30,'PARTIALLY_ACCEPTED');
/*!40000 ALTER TABLE `requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sessions`
--

DROP TABLE IF EXISTS `sessions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sessions` (
  `session_id` varchar(60) NOT NULL,
  `user_id` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`session_id`),
  UNIQUE KEY `session_id_UNIQUE` (`session_id`),
  UNIQUE KEY `session_id` (`session_id`,`user_id`),
  UNIQUE KEY `user_id_UNIQUE` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sessions`
--

LOCK TABLES `sessions` WRITE;
/*!40000 ALTER TABLE `sessions` DISABLE KEYS */;
INSERT INTO `sessions` VALUES ('E170BB691C659F0A447168F410F3A578','JSN001'),('5B07C9E09B2C7C8D6AC437016799FFBA','SADMIN001'),('0C9D8E9EC3B67DF516B52F75A63F4272','TAF001');
/*!40000 ALTER TABLE `sessions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stocks`
--

DROP TABLE IF EXISTS `stocks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `stocks` (
  `franchise_id` int NOT NULL,
  `product_id` int NOT NULL,
  `quantity` int DEFAULT NULL,
  PRIMARY KEY (`franchise_id`,`product_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `stocks_ibfk_1` FOREIGN KEY (`franchise_id`) REFERENCES `franchises` (`franchise_id`) ON DELETE CASCADE,
  CONSTRAINT `stocks_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stocks`
--

LOCK TABLES `stocks` WRITE;
/*!40000 ALTER TABLE `stocks` DISABLE KEYS */;
INSERT INTO `stocks` VALUES (1,1,56),(1,2,85),(1,3,95);
/*!40000 ALTER TABLE `stocks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supply`
--

DROP TABLE IF EXISTS `supply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `supply` (
  `supply_id` int NOT NULL AUTO_INCREMENT,
  `franchise_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `date_of_supply` date DEFAULT NULL,
  PRIMARY KEY (`supply_id`),
  KEY `franchise_id` (`franchise_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `supply_ibfk_1` FOREIGN KEY (`franchise_id`) REFERENCES `franchises` (`franchise_id`),
  CONSTRAINT `supply_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supply`
--

LOCK TABLES `supply` WRITE;
/*!40000 ALTER TABLE `supply` DISABLE KEYS */;
INSERT INTO `supply` VALUES (4,1,1,50,'2025-02-12'),(5,1,2,80,'2025-02-12'),(8,1,3,100,'2025-02-13');
/*!40000 ALTER TABLE `supply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` varchar(20) NOT NULL,
  `user_name` varchar(30) DEFAULT NULL,
  `hashed_password` varchar(100) DEFAULT NULL,
  `franchise_id` int DEFAULT NULL,
  `role` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  KEY `franchise_id` (`franchise_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`franchise_id`) REFERENCES `franchises` (`franchise_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('JSN001','Jerry Sebastian','$2a$12$rrQNT4A7KHRJoECZ5L/L1u1cvvoSbAeUu9WcHjTDTVuXyBJUY0sc.',1,'ADMIN'),('SADMIN001','Jerry','$2a$12$iKspsl0a9bBYylq0gw8yQOCh5P/XvksBMAtJEPnvQT7FkOy40b3n.',NULL,'SADMIN'),('TAF001','Thasleena Ashraf','$2a$12$keYNgsayMu3an1aDCIvjVODDHoLuovZwylJMB3Q4oBhSoBnBlkuRe',1,'EMPLOYEE');
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

-- Dump completed on 2025-02-14 18:00:47
