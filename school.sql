-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: lanye_db1
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_course`
--

DROP TABLE IF EXISTS `tb_course`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_course` (
  `Course_id` varchar(4) NOT NULL COMMENT '课程号',
  `Course_name` varchar(20) DEFAULT NULL COMMENT '课程名',
  `Course_hour` int DEFAULT '60' COMMENT '学时',
  `Introduce` varchar(200) DEFAULT NULL COMMENT '课程介绍',
  PRIMARY KEY (`Course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_course`
--

LOCK TABLES `tb_course` WRITE;
/*!40000 ALTER TABLE `tb_course` DISABLE KEYS */;
INSERT INTO `tb_course` VALUES ('0005','计算机科学与技术',60,'学习计算机的底层原理，上层运用。');
/*!40000 ALTER TABLE `tb_course` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_student`
--

DROP TABLE IF EXISTS `tb_student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_student` (
  `Stu_id` varchar(10) NOT NULL COMMENT '学号',
  `Stu_name` varchar(10) NOT NULL COMMENT '姓名',
  `Stu_sex` varchar(2) DEFAULT '男' COMMENT '性别',
  `Birthday` datetime DEFAULT NULL COMMENT '出生年月',
  `Phone` varchar(8) DEFAULT NULL COMMENT '电话，共8位，以‘3935’开头',
  `Address` varchar(100) DEFAULT NULL COMMENT '地址',
  `Class_id` varchar(4) NOT NULL COMMENT '班级编号',
  PRIMARY KEY (`Stu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_student`
--

LOCK TABLES `tb_student` WRITE;
/*!40000 ALTER TABLE `tb_student` DISABLE KEYS */;
INSERT INTO `tb_student` VALUES ('11','哥给','男',NULL,NULL,'广工2','22'),('1107505001','刘晨','男','1990-01-16 00:00:00','39352201','广药宿舍10栋203','0101'),('1107505002','王丽','女','1989-03-20 00:00:00','39351023','广药宿舍8栋607','0101'),('1107505003','冯峰','男','1991-01-09 00:00:00',NULL,'广药宿舍10栋203','0102'),('1107505004','张晓红','女','1990-08-21 00:00:00',NULL,'广药宿舍8栋607','0204'),('1107505005','张力','男',NULL,NULL,'广药宿舍10栋203','0103'),('1107505006','马凤勋','女','1989-04-24 00:00:00',NULL,'广药宿舍8栋607','0102');
/*!40000 ALTER TABLE `tb_student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_studentgrade`
--

DROP TABLE IF EXISTS `tb_studentgrade`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_studentgrade` (
  `Stu_id` varchar(10) NOT NULL COMMENT '学号',
  `Course_id` varchar(4) NOT NULL COMMENT '课程号',
  `Grade` decimal(9,2) DEFAULT NULL COMMENT '成绩',
  UNIQUE KEY `Stu_id` (`Stu_id`),
  UNIQUE KEY `Course_id` (`Course_id`),
  CONSTRAINT `tb_studentgrade_ibfk_1` FOREIGN KEY (`Stu_id`) REFERENCES `tb_student` (`Stu_id`),
  CONSTRAINT `tb_studentgrade_ibfk_2` FOREIGN KEY (`Course_id`) REFERENCES `tb_course` (`Course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_studentgrade`
--

LOCK TABLES `tb_studentgrade` WRITE;
/*!40000 ALTER TABLE `tb_studentgrade` DISABLE KEYS */;
INSERT INTO `tb_studentgrade` VALUES ('1107505001','0005',4.00);
/*!40000 ALTER TABLE `tb_studentgrade` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-03-20  2:15:54
