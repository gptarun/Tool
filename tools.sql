-- MySQL dump 10.13  Distrib 8.0.17, for Win64 (x86_64)
--
-- Host: localhost    Database: one_assessment
-- ------------------------------------------------------
-- Server version	8.0.17

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
-- Table structure for table `answer`
--

DROP TABLE IF EXISTS `answer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answer` (
  `session_id` int(11) NOT NULL,
  `question_id` int(11) NOT NULL,
  `ans_val` int(11) DEFAULT NULL,
  `area` int(11) DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  `competency` int(11) DEFAULT NULL,
  PRIMARY KEY (`session_id`,`question_id`),
  KEY `ans-to-question_idx` (`question_id`),
  KEY `ans-to-area_idx` (`area`),
  KEY `ans-to-cat_idx` (`category`),
  KEY `ans-to-comp_idx` (`competency`),
  CONSTRAINT `ans-to-area` FOREIGN KEY (`area`) REFERENCES `area` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ans-to-cat` FOREIGN KEY (`category`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ans-to-comp` FOREIGN KEY (`competency`) REFERENCES `competency` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ans-to-question` FOREIGN KEY (`question_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ans-to-session` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answer`
--

LOCK TABLES `answer` WRITE;
/*!40000 ALTER TABLE `answer` DISABLE KEYS */;
INSERT INTO `answer` VALUES (414,1,1,0,1,0),(414,2,3,0,1,0),(414,3,2,0,1,0),(414,4,5,0,1,1),(414,5,1,0,1,1),(414,6,3,0,1,1),(414,7,2,0,1,2),(414,8,3,0,1,2),(414,9,3,0,1,2),(414,10,3,0,1,3),(414,11,2,0,1,3),(414,12,2,0,1,4),(414,13,3,0,1,4),(415,1,2,0,1,0);
/*!40000 ALTER TABLE `answer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `area`
--

DROP TABLE IF EXISTS `area`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `area` (
  `id` int(11) NOT NULL,
  `area_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `area`
--

LOCK TABLES `area` WRITE;
/*!40000 ALTER TABLE `area` DISABLE KEYS */;
INSERT INTO `area` VALUES (0,'test');
/*!40000 ALTER TABLE `area` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `category_name` varchar(45) DEFAULT NULL,
  `area_id` int(11) NOT NULL,
  PRIMARY KEY (`id`,`area_id`),
  KEY `cat_to_area_idx` (`area_id`),
  CONSTRAINT `cat_to_area` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (0,'Organization',0),(1,'Talent',0),(2,'Flow',0),(3,'Technical',0),(4,'Governance',0);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `choice`
--

DROP TABLE IF EXISTS `choice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `choice` (
  `choice_id` int(11) NOT NULL,
  `choice_val` int(11) NOT NULL,
  `choice_text` varchar(256) DEFAULT NULL,
  `weight` decimal(3,2) DEFAULT NULL,
  PRIMARY KEY (`choice_id`,`choice_val`),
  KEY `choice_val_idx` (`choice_val`),
  CONSTRAINT `choice-to-q` FOREIGN KEY (`choice_id`) REFERENCES `question` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `choice`
--

LOCK TABLES `choice` WRITE;
/*!40000 ALTER TABLE `choice` DISABLE KEYS */;
INSERT INTO `choice` VALUES (1,1,'1',1.00),(1,2,'2',1.00),(1,3,'3',1.00),(1,4,'4',1.00),(1,5,'5',1.00),(2,1,'1',1.00),(2,3,'3',1.00),(2,4,'4',1.00),(2,5,'5',1.00),(3,1,'1',1.00),(3,2,'2',1.00),(3,3,'3',1.00),(3,4,'4',1.00),(3,5,'5',1.00),(4,1,'1',1.00),(4,2,'2',1.00),(4,3,'3',1.00),(4,4,'4',1.00),(4,5,'5',1.00),(5,1,'1',1.00),(5,2,'2',1.00),(5,4,'4',1.00),(5,5,'5',1.00),(6,1,'1',1.00),(6,2,'2',1.00),(6,3,'3',1.00),(6,4,'4',1.00),(6,5,'5',1.00),(7,1,'1',1.00),(7,2,'2',1.00),(7,3,'3',1.00),(7,4,'4',1.00),(7,5,'5',1.00),(8,1,'1',1.00),(8,2,'2',1.00),(8,3,'3',1.00),(8,4,'4',1.00),(8,5,'5',1.00),(9,1,'1',1.00),(9,2,'2',1.00),(9,3,'3',1.00),(9,4,'4',1.00),(9,5,'5',1.00),(10,1,'1',1.00),(10,2,'2',1.00),(10,3,'3',1.00),(10,4,'4',1.00),(10,5,'5',1.00),(11,1,'1',1.00),(11,2,'2',1.00),(11,3,'3',1.00),(11,5,'5',1.00),(12,1,'1',1.00),(12,2,'2',1.00),(12,4,'4',1.00),(12,5,'5',1.00),(13,1,'1',1.00),(13,3,'3',1.00),(13,5,'5',1.00),(14,1,'1',1.00),(14,2,'2',1.00),(14,3,'3',1.00),(14,4,'4',1.00),(14,5,'5',1.00),(15,1,'1',1.00),(15,2,'2',1.00),(15,3,'3',1.00),(15,4,'4',1.00),(15,5,'5',1.00),(16,1,'1',1.00),(16,2,'2',1.00),(16,3,'3',1.00),(16,4,'4',1.00),(16,5,'5',1.00),(17,1,'1',1.00),(17,2,'2',1.00),(17,3,'3',1.00),(17,4,'4',1.00),(17,5,'5',1.00),(18,1,'1',1.00),(18,3,'3',1.00),(18,5,'5',1.00),(19,1,'1',1.00),(19,2,'2',1.00),(19,3,'3',1.00),(19,4,'4',1.00),(19,5,'5',1.00),(20,1,'1',1.00),(20,2,'2',1.00),(20,3,'3',1.00),(20,4,'4',1.00),(20,5,'5',1.00),(21,1,'1',1.00),(21,2,'2',1.00),(21,3,'3',1.00),(21,4,'4',1.00),(21,5,'5',1.00),(22,1,'1',1.00),(22,2,'2',1.00),(22,3,'3',1.00),(22,4,'4',1.00),(22,5,'5',1.00),(23,1,'1',1.00),(23,2,'2',1.00),(23,3,'3',1.00),(23,4,'4',1.00),(23,5,'5',1.00),(24,1,'1',1.00),(24,2,'2',1.00),(24,3,'3',1.00),(24,4,'4',1.00),(24,5,'5',1.00),(25,1,'1',1.00),(25,3,'3',1.00),(25,5,'4',1.00),(26,1,'1',1.00),(26,2,'2',1.00),(26,3,'3',1.00),(26,4,'4',1.00),(26,5,'5',1.00),(27,1,'1',1.00),(27,2,'2',1.00),(27,3,'3',1.00),(27,4,'4',1.00),(27,5,'5',1.00),(28,1,'1',1.00),(28,2,'2',1.00),(28,4,'4',1.00),(28,5,'5',1.00),(29,1,'1',1.00),(29,2,'2',1.00),(29,3,'3',1.00),(29,4,'4',1.00),(29,5,'5',1.00),(30,1,'1',1.00),(30,2,'2',1.00),(30,3,'3',1.00),(30,4,'4',1.00),(30,5,'5',1.00),(31,1,'1',1.00),(31,2,'2',1.00),(31,4,'4',1.00),(31,5,'5',1.00),(32,1,'1',1.00),(32,2,'2',1.00),(32,3,'3',1.00),(32,4,'4',1.00),(32,5,'5',1.00),(33,1,'1',1.00),(33,2,'2',1.00),(33,3,'3',1.00),(33,4,'4',1.00),(33,5,'5',1.00),(34,1,'1',1.00),(34,2,'2',1.00),(34,3,'3',1.00),(34,4,'4',1.00),(34,5,'5',1.00),(35,2,'2',1.00),(35,5,'5',1.00),(36,1,'1',1.00),(36,2,'2',1.00),(36,5,'5',1.00),(37,3,'3',1.00),(37,4,'4',1.00),(37,5,'5',1.00),(38,1,'1',1.00),(38,2,'2',1.00),(38,3,'3',1.00),(38,4,'4',1.00),(38,5,'5',1.00),(39,1,'1',1.00),(39,5,'5',1.00),(40,1,'1',1.00),(40,3,'3',1.00),(40,4,'4',1.00),(40,5,'5',1.00),(41,1,'1',1.00),(41,2,'2',1.00),(41,3,'3',1.00),(41,4,'4',1.00),(41,5,'5',1.00),(42,1,'1',1.00),(42,2,'2',1.00),(42,3,'3',1.00),(42,5,'5',1.00),(43,3,'3',1.00),(43,4,'4',1.00),(43,5,'5',1.00),(44,1,'1',1.00),(44,2,'2',1.00),(44,3,'3',1.00),(44,4,'4',1.00),(44,5,'5',1.00),(45,2,'2',1.00),(45,5,'5',1.00),(46,3,'3',1.00),(46,4,'4',1.00),(46,5,'5',1.00),(47,1,'1',1.00),(47,5,'5',1.00),(48,1,'1',1.00),(48,2,'2',1.00),(48,3,'3',1.00),(48,4,'4',1.00),(48,5,'5',1.00),(49,1,'1',1.00),(49,2,'2',1.00),(49,3,'3',1.00),(49,4,'4',1.00),(49,5,'5',1.00),(50,1,'1',1.00),(50,2,'2',1.00),(50,4,'4',1.00),(50,5,'5',1.00),(51,1,'1',1.00),(51,2,'2',1.00),(51,3,'3',1.00),(51,5,'5',1.00),(52,1,'1',1.00),(52,2,'2',1.00),(52,3,'3',1.00),(52,5,'5',1.00),(53,1,'1',1.00),(53,2,'2',1.00),(53,3,'3',1.00),(53,4,'4',1.00),(53,5,'5',1.00),(54,1,'1',1.00),(54,2,'2',1.00),(54,3,'3',1.00),(54,4,'4',1.00),(54,5,'5',1.00),(55,1,'1',1.00),(55,2,'2',1.00),(55,3,'3',1.00),(55,4,'4',1.00),(55,5,'5',1.00),(56,1,'1',1.00),(56,2,'2',1.00),(56,3,'3',1.00),(56,4,'4',1.00),(56,5,'5',1.00),(57,1,'1',1.00),(57,2,'2',1.00),(57,3,'3',1.00),(57,4,'4',1.00),(57,5,'5',1.00);
/*!40000 ALTER TABLE `choice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `competency`
--

DROP TABLE IF EXISTS `competency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `competency` (
  `id` int(11) NOT NULL,
  `competency_name` varchar(45) DEFAULT NULL,
  `cat_id` int(11) NOT NULL,
  `minScore` decimal(5,2) DEFAULT '0.00',
  `maxScore` decimal(5,2) DEFAULT '0.00',
  PRIMARY KEY (`id`,`cat_id`),
  KEY `comp_to_cat_idx` (`cat_id`),
  CONSTRAINT `comp_to_cat` FOREIGN KEY (`cat_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `competency`
--

LOCK TABLES `competency` WRITE;
/*!40000 ALTER TABLE `competency` DISABLE KEYS */;
INSERT INTO `competency` VALUES (0,'Role of Managers',0,1.00,5.00),(0,'Skills & Competency',1,3.00,15.00),(0,'Process',2,3.00,15.00),(0,'Automation & Tools',3,22.00,70.00),(0,'Dependency Management',4,1.00,5.00),(1,'Decision Making',0,2.00,10.00),(1,'Performance Management',1,3.00,15.00),(1,'Behaviors',2,2.00,10.00),(1,'Life Cycle',3,3.00,15.00),(1,'Progress Status',4,1.00,5.00),(2,'Business/IT Relationship',0,3.00,15.00),(2,'Roles & Responsibilities',1,3.00,15.00),(2,'Planning',2,3.00,15.00),(2,'Architecture',3,2.00,10.00),(2,'Measurement',4,1.00,5.00),(3,'Knowledge Sharing',0,1.00,5.00),(3,'Onboarding Maturity',1,2.00,10.00),(3,'Delivery Execution',2,3.00,15.00),(3,'Quality',3,0.00,0.00),(3,'Capacity Planning and Road Mapping',4,2.00,10.00),(4,'Structure & Process',0,2.00,10.00),(4,'Incentives & Rewards',1,2.00,10.00);
/*!40000 ALTER TABLE `competency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `model`
--

DROP TABLE IF EXISTS `model`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `model` (
  `id` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `model_val` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`,`level`),
  CONSTRAINT `model-to-map` FOREIGN KEY (`id`) REFERENCES `outcome_map` (`outcome_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `model`
--

LOCK TABLES `model` WRITE;
/*!40000 ALTER TABLE `model` DISABLE KEYS */;
INSERT INTO `model` VALUES (1,1,'1'),(1,2,'2'),(1,3,'3'),(1,4,'4'),(1,5,'5'),(2,1,'1'),(2,2,'2'),(2,3,'3'),(2,4,'4'),(2,5,'5'),(3,1,'1'),(3,2,'2'),(3,3,'3'),(3,4,'4'),(3,5,'5'),(4,1,'1'),(4,2,'2'),(4,3,'3'),(4,4,'4'),(4,5,'5'),(5,1,'1'),(5,2,'2'),(5,3,'3'),(5,4,'4'),(5,5,'5'),(6,1,'1'),(6,2,'2'),(6,3,'3'),(6,4,'4'),(6,5,'5'),(7,1,'1'),(7,2,'2'),(7,3,'3'),(7,4,'4'),(7,5,'5'),(8,1,'1'),(8,2,'2'),(8,3,'3'),(8,4,'4'),(8,5,'5'),(9,1,'1'),(9,2,'2'),(9,3,'3'),(9,4,'4'),(9,5,'5'),(10,1,'1'),(10,2,'2'),(10,3,'3'),(10,4,'4'),(10,5,'5'),(11,1,'1'),(11,2,'2'),(11,3,'3'),(11,4,'4'),(11,5,'5'),(12,1,'1'),(12,2,'2'),(12,3,'3'),(12,4,'4'),(12,5,'5'),(13,1,'1'),(13,2,'2'),(13,3,'3'),(13,4,'4'),(13,5,'5'),(14,1,'1'),(14,2,'2'),(14,3,'3'),(14,4,'4'),(14,5,'5'),(15,1,'1'),(15,2,'2'),(15,3,'3'),(15,4,'4'),(15,5,'5'),(16,1,'1'),(16,2,'2'),(16,3,'3'),(16,4,'4'),(16,5,'5'),(17,1,'1'),(17,2,'2'),(17,3,'3'),(17,4,'4'),(17,5,'5'),(19,1,'1'),(19,2,'2'),(19,3,'3'),(19,4,'4'),(19,5,'5'),(20,1,'1'),(20,2,'2'),(20,3,'3'),(20,4,'4'),(20,5,'5'),(21,1,'1'),(21,2,'2'),(21,3,'3'),(21,4,'4'),(21,5,'5'),(22,1,'1'),(22,2,'2'),(22,3,'3'),(22,4,'4'),(22,5,'5');
/*!40000 ALTER TABLE `model` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outcome`
--

DROP TABLE IF EXISTS `outcome`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `outcome` (
  `id` int(11) NOT NULL,
  `level` int(11) NOT NULL,
  `outcome_val` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`,`level`),
  CONSTRAINT `outcome-to-map` FOREIGN KEY (`id`) REFERENCES `outcome_map` (`outcome_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outcome`
--

LOCK TABLES `outcome` WRITE;
/*!40000 ALTER TABLE `outcome` DISABLE KEYS */;
INSERT INTO `outcome` VALUES (1,1,'<li>Outcome Values.</li>'),(1,2,'<li>Outcome Values.</li>'),(1,3,'<li>Outcome Values.</li>'),(1,4,'<li>Outcome Values.</li>'),(1,5,'<li>Outcome Values.</li>'),(2,1,'<li>Outcome Values.</li>'),(2,2,'<li>Outcome Values.</li>'),(2,3,'<li>Outcome Values.</li>'),(2,4,'<li>Outcome Values.</li>'),(2,5,'<li>Outcome Values.</li>'),(3,1,'<li>Outcome Values.</li>'),(3,2,'<li>Outcome Values.</li>'),(3,3,'<li>Outcome Values.</li>'),(3,4,'<li>Outcome Values.</li>'),(3,5,'<li>Outcome Values.</li>'),(4,1,'<li>Outcome Values.</li>'),(4,2,'<li>Outcome Values.</li>'),(4,3,'<li>Outcome Values.</li>'),(4,4,'<li>Outcome Values.</li>'),(4,5,'<li>Outcome Values.</li>'),(5,1,'<li>Outcome Values.</li>'),(5,2,'<li>Outcome Values.</li>'),(5,3,'<li>Outcome Values.</li>'),(5,4,'<li>Outcome Values.</li>'),(5,5,'<li>Outcome Values.</li>'),(6,1,'<li>Outcome Values.</li>'),(6,2,'<li>Outcome Values.</li>'),(6,3,'<li>Outcome Values.</li>'),(6,4,'<li>Outcome Values.</li>'),(6,5,'<li>Outcome Values.</li>'),(7,1,'<li>Outcome Values.</li>'),(7,2,'<li>Outcome Values.</li>'),(7,3,'<li>Outcome Values.</li>'),(7,4,'<li>Outcome Values.</li>'),(7,5,'<li>Outcome Values.</li>'),(8,1,'<li>Outcome Values.</li>'),(8,2,'<li>Outcome Values.</li>'),(8,3,'<li>Outcome Values.</li>'),(8,4,'<li>Outcome Values.</li>'),(8,5,'<li>Outcome Values.</li>'),(9,1,'<li>Outcome Values.</li>'),(9,2,'<li>Outcome Values.</li>'),(9,3,'<li>Outcome Values.</li>'),(9,4,'<li>Outcome Values.</li>'),(9,5,'<li>Outcome Values.</li>'),(10,1,'<li>Outcome Values.</li>'),(10,2,'<li>Outcome Values.</li>'),(10,3,'<li>Outcome Values.</li>'),(10,4,'<li>Outcome Values.</li>'),(10,5,'<li>Outcome Values.</li>'),(11,1,'<li>Outcome Values.</li>'),(11,2,'<li>Outcome Values.</li>'),(11,3,'<li>Outcome Values.</li>'),(11,4,'<li>Outcome Values.</li>'),(11,5,'<li>Outcome Values.</li>'),(12,1,'<li>Outcome Values.</li>'),(12,2,'<li>Outcome Values.</li>'),(12,3,'<li>Outcome Values.</li>'),(12,4,'<li>Outcome Values.</li>'),(12,5,'<li>Outcome Values.</li>'),(13,1,'<li>Outcome Values.</li>'),(13,2,'<li>Outcome Values.</li>'),(13,3,'<li>Outcome Values.</li>'),(13,4,'<li>Outcome Values.</li>'),(13,5,'<li>Outcome Values.</li>'),(14,1,'<li>Outcome Values.</li>'),(14,2,'<li>Outcome Values.</li>'),(14,3,'<li>Outcome Values.</li>'),(14,4,'<li>Outcome Values.</li>'),(14,5,'<li>Outcome Values.</li>'),(15,1,'<li>Outcome Values.</li>'),(15,2,'<li>Outcome Values.</li>'),(15,3,'<li>Outcome Values.</li>'),(15,4,'<li>Outcome Values.</li>'),(15,5,'<li>Outcome Values.</li>'),(16,1,'<li>Outcome Values.</li>'),(16,2,'<li>Outcome Values.</li>'),(16,3,'<li>Outcome Values.</li>'),(16,4,'<li>Outcome Values.</li>'),(16,5,'<li>Outcome Values.</li>'),(17,1,'<li>Outcome Values.</li>'),(17,2,'<li>Outcome Values.</li>'),(17,3,'<li>Outcome Values.</li>'),(17,4,'<li>Outcome Values.</li>'),(17,5,'<li>Outcome Values.</li>'),(19,1,'<li>Outcome Values.</li>'),(19,2,'<li>Outcome Values.</li>'),(19,3,'<li>Outcome Values.</li>'),(19,4,'<li>Outcome Values.</li>'),(19,5,'<li>Outcome Values.</li>'),(20,1,'<li>Outcome Values.</li>'),(20,2,'<li>Outcome Values.</li>'),(20,3,'<li>Outcome Values.</li>'),(20,4,'<li>Outcome Values.</li>'),(20,5,'<li>Outcome Values.</li>'),(21,1,'<li>Outcome Values.</li>'),(21,2,'<li>Outcome Values.</li>'),(21,3,'<li>Outcome Values.</li>'),(21,4,'<li>Outcome Values.</li>'),(21,5,'<li>Outcome Values.</li>'),(22,1,'<li>Outcome Values.</li>'),(22,2,'<li>Outcome Values.</li>'),(22,3,'<li>Outcome Values.</li>'),(22,4,'<li>Outcome Values.</li>'),(22,5,'<li>Outcome Values.</li>');
/*!40000 ALTER TABLE `outcome` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `outcome_map`
--

DROP TABLE IF EXISTS `outcome_map`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `outcome_map` (
  `area_id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `competency_id` int(11) NOT NULL,
  `outcome_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`area_id`,`category_id`,`competency_id`),
  KEY `outcome-to-cat_idx` (`category_id`),
  KEY `outcome-id-idx` (`outcome_id`),
  CONSTRAINT `outcome-to-area` FOREIGN KEY (`area_id`) REFERENCES `area` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `outcome-to-cat` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `outcome_map`
--

LOCK TABLES `outcome_map` WRITE;
/*!40000 ALTER TABLE `outcome_map` DISABLE KEYS */;
INSERT INTO `outcome_map` VALUES (0,0,0,1),(0,0,1,2),(0,0,2,3),(0,0,3,4),(0,0,4,5),(0,1,0,6),(0,1,1,7),(0,1,2,8),(0,1,3,9),(0,1,4,10),(0,2,0,11),(0,2,1,12),(0,2,2,13),(0,2,3,14),(0,3,0,15),(0,3,1,16),(0,3,2,17),(0,3,3,18),(0,4,0,19),(0,4,1,20),(0,4,2,21),(0,4,3,22);
/*!40000 ALTER TABLE `outcome_map` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `portfolio`
--

DROP TABLE IF EXISTS `portfolio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `portfolio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `portfolio` varchar(45) DEFAULT NULL,
  `vertical` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `portfolio`
--

LOCK TABLES `portfolio` WRITE;
/*!40000 ALTER TABLE `portfolio` DISABLE KEYS */;
INSERT INTO `portfolio` VALUES (1,'Car','Ground'),(2,'Bike','Ground'),(3,'Van','Ground'),(4,'Care','Ground'),(5,'floor1','Corporate'),(6,'floor2','Corporate'),(7,'floor3','Corporate'),(8,'floor4','Corporate'),(9,'floor4','Corporate'),(10,'floor5','Corporate'),(11,'floor6','Corporate'),(12,'port1','Care'),(13,'port2','Care'),(14,'port3','Care'),(15,'port4','Care'),(16,'port5','Care'),(17,'port6','Care'),(18,'port7','Care'),(19,'Risk','Risk'),(20,'Great','Deliver'),(21,'Application','Tech'),(22,'User','Tech'),(23,'Support','Tech');
/*!40000 ALTER TABLE `portfolio` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_functions`
--

DROP TABLE IF EXISTS `product_functions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_functions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `function` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_functions`
--

LOCK TABLES `product_functions` WRITE;
/*!40000 ALTER TABLE `product_functions` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_functions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `question`
--

DROP TABLE IF EXISTS `question`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `question` (
  `id` int(11) NOT NULL,
  `desc` varchar(256) DEFAULT NULL,
  `area` int(11) DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  `competency` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `question`
--

LOCK TABLES `question` WRITE;
/*!40000 ALTER TABLE `question` DISABLE KEYS */;
INSERT INTO `question` VALUES (1,'0',0,1,0),(2,'0',0,1,0),(3,'0',0,1,0),(4,'1',0,1,1),(5,'1',0,1,1),(6,'1',0,1,1),(7,'2',0,1,2),(8,'2',0,1,2),(9,'2',0,1,2),(10,'3',0,1,3),(11,'3',0,1,3),(12,'4',0,1,4),(13,'4',0,1,4),(14,'0',0,0,0),(15,'1',0,0,1),(16,'1',0,0,1),(17,'2',0,0,2),(18,'2',0,0,2),(19,'2',0,0,2),(20,'3',0,0,3),(21,'4',0,0,4),(22,'4',0,0,4),(23,'0',0,2,0),(24,'0',0,2,0),(25,'0',0,2,0),(26,'1',0,2,1),(27,'1',0,2,1),(28,'2',0,2,2),(29,'2',0,2,2),(30,'2',0,2,2),(31,'3',0,2,3),(32,'3',0,2,3),(33,'3',0,2,3),(34,'0',0,3,0),(35,'0',0,3,0),(36,'0',0,3,0),(37,'0',0,3,0),(38,'0',0,3,0),(39,'0',0,3,0),(40,'0',0,3,0),(41,'0',0,3,0),(42,'0',0,3,0),(43,'0',0,3,0),(44,'0',0,3,0),(45,'0',0,3,0),(46,'0',0,3,0),(47,'0',0,3,0),(48,'1',0,3,1),(49,'1',0,3,1),(50,'1',0,3,1),(51,'2',0,3,2),(52,'2',0,3,2),(53,'3',0,4,3),(54,'3',0,4,3),(55,'0',0,4,0),(56,'1',0,4,1),(57,'2',0,4,2);
/*!40000 ALTER TABLE `question` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `team_id` int(11) DEFAULT NULL,
  `email` varchar(54) DEFAULT NULL,
  `createdOn` datetime DEFAULT NULL,
  `completedOn` datetime DEFAULT NULL,
  `urlKey` varchar(45) DEFAULT NULL,
  `location` varchar(45) DEFAULT NULL,
  `product_group` varchar(45) DEFAULT NULL,
  `IT_portfolio` int(11) DEFAULT NULL,
  `size` int(11) DEFAULT NULL,
  `team_name` varchar(72) DEFAULT NULL,
  `session_cat` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sess-to-team_idx` (`team_id`),
  KEY `key_idx` (`urlKey`),
  KEY `sess-to-port_idx` (`IT_portfolio`),
  CONSTRAINT `sess-to-port` FOREIGN KEY (`IT_portfolio`) REFERENCES `portfolio` (`id`) ON DELETE SET NULL ON UPDATE SET NULL,
  CONSTRAINT `sess-to-team` FOREIGN KEY (`team_id`) REFERENCES `team` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=417 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
INSERT INTO `session` VALUES (414,1,'tarung1201@gmail.com','2019-08-17 15:37:03','2019-08-17 15:37:51','MTU2NjAzNjQyMTU1NzA',NULL,NULL,1,NULL,'TarunTest',1),(415,1,'tarung1201@gmail.com','2019-09-07 12:54:25',NULL,'MTU2Nzg0MTA2NDc3MTA',NULL,NULL,5,NULL,'TarunTest',1),(416,1,'tarung1201@gmail.com','2019-09-07 12:54:55',NULL,'MTU2Nzg0MTA5NTE3MDE',NULL,NULL,5,NULL,'TarunTest',NULL);
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session_works`
--

DROP TABLE IF EXISTS `session_works`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session_works` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `work` varchar(72) DEFAULT NULL,
  `session_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `works-to-sess_idx` (`session_id`),
  CONSTRAINT `works-to-sess` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session_works`
--

LOCK TABLES `session_works` WRITE;
/*!40000 ALTER TABLE `session_works` DISABLE KEYS */;
/*!40000 ALTER TABLE `session_works` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team`
--

DROP TABLE IF EXISTS `team`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `last_assessment` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team`
--

LOCK TABLES `team` WRITE;
/*!40000 ALTER TABLE `team` DISABLE KEYS */;
INSERT INTO `team` VALUES (1,'TarunTest','2019-08-17 00:00:00');
/*!40000 ALTER TABLE `team` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `team_prod_func`
--

DROP TABLE IF EXISTS `team_prod_func`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `team_prod_func` (
  `sess_id` int(11) NOT NULL,
  `prod_func_id` int(11) NOT NULL,
  PRIMARY KEY (`sess_id`,`prod_func_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `team_prod_func`
--

LOCK TABLES `team_prod_func` WRITE;
/*!40000 ALTER TABLE `team_prod_func` DISABLE KEYS */;
/*!40000 ALTER TABLE `team_prod_func` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-09-09 17:55:02
