-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.26 - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  9.5.0.5196
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 qfnews 的数据库结构
DROP DATABASE IF EXISTS `qfnews`;
CREATE DATABASE IF NOT EXISTS `qfnews` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE `qfnews`;

-- 导出  表 qfnews.tbl_nav 结构
DROP TABLE IF EXISTS `tbl_nav`;
CREATE TABLE IF NOT EXISTS `tbl_nav` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nav` varchar(50) NOT NULL,
  `type` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `nav` (`nav`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  qfnews.tbl_nav 的数据：~7 rows (大约)
/*!40000 ALTER TABLE `tbl_nav` DISABLE KEYS */;
REPLACE INTO `tbl_nav` (`id`, `nav`, `type`) VALUES
	(1, '国内', 0),
	(2, '国际', 0),
	(3, '购物', 1),
	(4, '体育', 0),
	(5, '财经', 0),
	(6, '娱乐', 0),
	(7, '游戏', 0);
/*!40000 ALTER TABLE `tbl_nav` ENABLE KEYS */;

-- 导出  表 qfnews.tbl_news 结构
DROP TABLE IF EXISTS `tbl_news`;
CREATE TABLE IF NOT EXISTS `tbl_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) NOT NULL,
  `content` text NOT NULL,
  `ctime` datetime NOT NULL,
  `nid` int(11) NOT NULL,
  `image` varchar(500) DEFAULT NULL,
  `photo` varchar(500) DEFAULT NULL,
  `price` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_tbl_news_tbl_nav` (`nid`),
  CONSTRAINT `FK_tbl_news_tbl_nav` FOREIGN KEY (`nid`) REFERENCES `tbl_nav` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  qfnews.tbl_news 的数据：~7 rows (大约)
/*!40000 ALTER TABLE `tbl_news` DISABLE KEYS */;
REPLACE INTO `tbl_news` (`id`, `title`, `content`, `ctime`, `nid`, `image`, `photo`, `price`) VALUES
	(1, '田野', '是我同学的名字', '2023-12-20 16:57:54', 1, '/qfnews/upload/ic_student.png', '', 0),
	(2, '夏天', '青岛的夏天不那么热', '2023-12-20 16:58:03', 1, '/qfnews/upload/xuemaojiao.jpg', '/qfnews/upload/ic_yunnan.png', 0),
	(3, '牛仔裤', '颜色是蓝色的', '2023-12-20 16:58:11', 3, '/qfnews/upload/jeans.webp', '', 100),
	(4, '春天', '是个粗去浪的好季节', '2023-12-20 16:58:19', 2, '/qfnews/upload/apple.jpg', '', 0),
	(5, '秋天', '秋天的第一杯奶茶', '2023-12-21 12:10:23', 1, '/qfnews/upload/ic_yiqing.png', '/qfnews/upload/tangyuan.jpg', 0),
	(19, '卫衣', '卫衣穿着很舒服', '2023-12-21 18:40:02', 3, '/qfnews/upload/clothing.webp', '', 200),
	(20, '冬天', '东北的烤冰溜子真的能吃吗', '2023-12-21 18:41:11', 4, '/qfnews/upload/kiwifruit.jpg', '', 0);
/*!40000 ALTER TABLE `tbl_news` ENABLE KEYS */;

-- 导出  表 qfnews.tbl_order 结构
DROP TABLE IF EXISTS `tbl_order`;
CREATE TABLE IF NOT EXISTS `tbl_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `mobile` varchar(50) NOT NULL,
  `count` int(11) NOT NULL,
  `ctime` datetime NOT NULL,
  `pid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_tbl_order_tbl_news` (`pid`),
  CONSTRAINT `FK_tbl_order_tbl_news` FOREIGN KEY (`pid`) REFERENCES `tbl_news` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 正在导出表  qfnews.tbl_order 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `tbl_order` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_order` ENABLE KEYS */;

-- 导出  表 qfnews.tbl_user 结构
DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE IF NOT EXISTS `tbl_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- 正在导出表  qfnews.tbl_user 的数据：~2 rows (大约)
/*!40000 ALTER TABLE `tbl_user` DISABLE KEYS */;
REPLACE INTO `tbl_user` (`id`, `username`, `password`) VALUES
	(1, '张三', '123456'),
	(2, '李四', '123456');
/*!40000 ALTER TABLE `tbl_user` ENABLE KEYS */;

-- 导出  视图 qfnews.v_news 结构
DROP VIEW IF EXISTS `v_news`;
-- 创建临时表以解决视图依赖性错误
CREATE TABLE `v_news` (
	`id` INT(11) NOT NULL,
	`title` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`content` TEXT NOT NULL COLLATE 'utf8mb4_general_ci',
	`nid` INT(11) NOT NULL,
	`ctime` DATETIME NOT NULL,
	`price` INT(11) NOT NULL,
	`image` VARCHAR(500) NULL COLLATE 'utf8mb4_general_ci',
	`photo` VARCHAR(500) NULL COLLATE 'utf8mb4_general_ci',
	`nav` VARCHAR(50) NULL COLLATE 'utf8mb4_general_ci',
	`type` INT(11) NULL
) ENGINE=MyISAM;

-- 导出  视图 qfnews.v_order 结构
DROP VIEW IF EXISTS `v_order`;
-- 创建临时表以解决视图依赖性错误
CREATE TABLE `v_order` (
	`id` INT(11) NOT NULL,
	`name` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`address` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`mobile` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`count` INT(11) NOT NULL,
	`ctime` DATETIME NOT NULL,
	`pid` INT(11) NOT NULL,
	`title` VARCHAR(50) NULL COLLATE 'utf8mb4_general_ci'
) ENGINE=MyISAM;

-- 导出  视图 qfnews.v_news 结构
DROP VIEW IF EXISTS `v_news`;
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `v_news`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_news` AS select `tbl_news`.`id` AS `id`,`tbl_news`.`title` AS `title`,`tbl_news`.`content` AS `content`,`tbl_news`.`nid` AS `nid`,`tbl_news`.`ctime` AS `ctime`,`tbl_news`.`price` AS `price`,`tbl_news`.`image` AS `image`,`tbl_news`.`photo` AS `photo`,`tbl_nav`.`nav` AS `nav`,`tbl_nav`.`type` AS `type` from (`tbl_news` left join `tbl_nav` on((`tbl_news`.`nid` = `tbl_nav`.`id`)));

-- 导出  视图 qfnews.v_order 结构
DROP VIEW IF EXISTS `v_order`;
-- 移除临时表并创建最终视图结构
DROP TABLE IF EXISTS `v_order`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `v_order` AS select `tbl_order`.`id` AS `id`,`tbl_order`.`name` AS `name`,`tbl_order`.`address` AS `address`,`tbl_order`.`mobile` AS `mobile`,`tbl_order`.`count` AS `count`,`tbl_order`.`ctime` AS `ctime`,`tbl_order`.`pid` AS `pid`,`tbl_news`.`title` AS `title` from (`tbl_order` left join `tbl_news` on((`tbl_order`.`pid` = `tbl_news`.`id`)));

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
