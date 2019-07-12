/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50718
Source Host           : localhost:3306
Source Database       : ds0

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2019-07-12 18:12:42
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for t_config
-- ----------------------------
DROP TABLE IF EXISTS `t_config`;
CREATE TABLE `t_config` (
  `id` bigint(20) NOT NULL,
  `keyword` varchar(255) NOT NULL,
  `value` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_order0
-- ----------------------------
DROP TABLE IF EXISTS `t_order0`;
CREATE TABLE `t_order0` (
  `order_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  KEY `create_time_idx` (`create_time`),
  KEY `user_id_idx` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_order1
-- ----------------------------
DROP TABLE IF EXISTS `t_order1`;
CREATE TABLE `t_order1` (
  `order_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`order_id`),
  KEY `create_time_idx` (`create_time`) USING BTREE,
  KEY `user_id_idx` (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_order_item0
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item0`;
CREATE TABLE `t_order_item0` (
  `order_item_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `product_id` int(10) NOT NULL,
  `product_count` int(10) NOT NULL,
  PRIMARY KEY (`order_item_id`),
  KEY `order_id_idx` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for t_order_item1
-- ----------------------------
DROP TABLE IF EXISTS `t_order_item1`;
CREATE TABLE `t_order_item1` (
  `order_item_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `product_id` int(10) NOT NULL,
  `product_count` int(10) NOT NULL,
  PRIMARY KEY (`order_item_id`),
  KEY `order_id_idx` (`order_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
SET FOREIGN_KEY_CHECKS=1;
