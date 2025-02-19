-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ecommerce-db
-- ------------------------------------------------------
-- Server version	8.0.40

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
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `active` bit(1) DEFAULT NULL,
  `price` decimal(38,2) NOT NULL,
  `quantity` int NOT NULL,
  `category_id` bigint NOT NULL,
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `imageurl` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (_binary '',25.00,5,2,4,'Chanel Mascara','Noir, volume et courbes. Réf. 194210','','https://www.chanel.com/images//t_one//w_0.43,h_0.43,c_crop/q_auto:good,f_autoplus,fl_lossy,dpr_1.1/w_1240/le-volume-de-chanel-waterproof-mascara-10-noir-0-21oz--packshot-default-194210-9561249513502.jpg'),(_binary '',150.00,5,1,5,'Valentino parfum','Rome HDP 100 ml','','https://kapao.fr/37691-thickbox_default/bir-donna-edp-int-.jpg'),(_binary '',60.00,5,3,6,'Bottines Louis Vuitton','Femme Louis Vuitton Bottine Star Trail','','https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcSVnwzoFm9fqKKZnOHtuN3GZndqdXONVifVA_x3ctI4jmnqxYjEJb-QlQOufVMhFzftJM8CKd4qqYZC3HetZ1hz4-Z5VCW90T7VGeJuANuD54918ly-sb8Ks7lm'),(_binary '',120.00,8,1,8,'Si Armani','Armani Eau de Parfum Sì',NULL,'https://encrypted-tbn3.gstatic.com/shopping?q=tbn:ANd9GcScczZWQ6tMTag9mrCk1p7ow__lDoZLOD3QF24Vl4A5GyA6TMFZ0wqWrPImzvM-8Z-9W563NktYcrnsJmdyDJfaJgy5t1NJ9tssRqOQmgQ'),(_binary '',500.00,5,3,9,'Chaussure Louboutin','Christian Louboutin 42',NULL,'https://eu.christianlouboutin.com/media/catalog/product/3/1/3130694bk01-3130694bk01-main_image-ecommerce-christianlouboutin-sokate-3130694_bk01_1_1200x1200.jpg'),(_binary '',20.00,3,2,10,'NYX Gloss','NYX Professional Makeup Fat Oil Lip Drip',NULL,'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcR5KL2wtkg54YRzO-xpjaz9jFNVvgIrlj7ZIi1P64CMWc3IgcyKaKw4pqz9ylqOXaBlOiHdkXeoSoDqCVih9xK5pFERLDjmZfAQDOH_1FQWm-ElHNeDbDhl'),(_binary '',115.00,5,1,11,'Guerlain La Petite Robe Noire','Eau de Parfum',NULL,'https://www.avenue-des-parfums.fr/83030-product_image/la-petite-robe-noire-eau-de-parfum.jpg'),(_binary '',47.00,0,2,12,'DIOR BACKSTAGE Font de teint','Dior Backstage Face & Body Foundation - Fond de teint visage et corps',NULL,'https://www.sephora.fr/on/demandware.static/-/Sites-masterCatalog_Sephora/default/dw91f665c9/images/hi-res/SKU/SKU_4190/658837_swatch.jpg'),(_binary '',36.00,2,3,13,'Stradivarius Baskets basses - dark red','Couleur: dark red',NULL,'https://img01.ztat.net/article/spp-media-p1/12b54fafd44445b984ac2602cb538819/ef421867284f4b6f969f667f782523b8.jpg?imwidth=1800');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-02-19 22:44:50
