/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50711
Source Host           : localhost:3306
Source Database       : mst

Target Server Type    : MYSQL
Target Server Version : 50711
File Encoding         : 65001

Date: 2019-03-21 10:17:05
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
  `menu_id` int(5) NOT NULL,
  `menu_name` varchar(30) DEFAULT NULL,
  `icon` varchar(20) DEFAULT NULL,
  `url` varchar(30) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL COMMENT '父目录',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of menu
-- ----------------------------
INSERT INTO `menu` VALUES ('0', '系统菜单', 'icon-sys', '', null);
INSERT INTO `menu` VALUES ('100', '测试', 'icon-sys', null, '0');
INSERT INTO `menu` VALUES ('200', '测试2', 'icon-sys', null, '100');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` char(64) DEFAULT NULL COMMENT '用户名',
  `password` char(64) NOT NULL COMMENT '密码',
  `email` char(32) DEFAULT NULL COMMENT '邮箱',
  `phone` char(20) DEFAULT NULL COMMENT '手机号码',
  `department_id` int(11) DEFAULT NULL COMMENT '所属部门的id',
  `department_code` char(32) DEFAULT NULL COMMENT '所属部门的编号',
  `department_name` char(32) DEFAULT NULL COMMENT '所属部门的名字',
  `login_name` char(32) NOT NULL COMMENT '登录所用的用户名',
  `is_available` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否可用（默认为1），1-可用，0-不可用',
  `img_path` char(255) DEFAULT NULL COMMENT '头像图片的地址（前端文件）',
  `last_login_time` char(32) DEFAULT NULL COMMENT '最后一次登录的时间',
  `create_time` char(32) DEFAULT NULL COMMENT '该用户创建的时间',
  `fingerprint_id` int(11) DEFAULT NULL COMMENT '指纹id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `system_user_login_name_uindex` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='系统用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '张三', '3f509f0f8508992b90675d74568d6958', null, null, null, null, null, 'system', '1', null, null, null, null);
