/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80033
 Source Host           : localhost:3306
 Source Schema         : test

 Target Server Type    : MySQL
 Target Server Version : 80033
 File Encoding         : 65001

 Date: 01/07/2023 14:00:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint(0) NULL DEFAULT NULL
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);
INSERT INTO `hibernate_sequence` VALUES (1);

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `available` bit(1) NULL DEFAULT b'1',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `parent_id` bigint(0) NULL DEFAULT NULL,
  `parent_ids` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `permission` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `resource_type` enum('menu','button') CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 48 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------
INSERT INTO `permission` VALUES (1, b'1', '员工管理', 12, '0/', 'user:view', 'menu', 'user');
INSERT INTO `permission` VALUES (2, b'1', '员工添加', 1, '0/1', 'user:add', 'button', 'user/add');
INSERT INTO `permission` VALUES (3, b'1', '员工离职', 1, '0/1', 'user:del', 'button', 'user/del');
INSERT INTO `permission` VALUES (4, b'1', '权限管理', 12, '0/', 'role:view', 'menu', 'role');
INSERT INTO `permission` VALUES (5, b'1', '新增角色', 4, '0/4', 'role:add', 'button', 'role/add');
INSERT INTO `permission` VALUES (8, b'1', '员工编辑', 1, '0/1', 'user:update', 'button', 'user/update');
INSERT INTO `permission` VALUES (11, b'1', '设置用户角色', 1, '0/1', 'user:role', 'button', 'user/role');
INSERT INTO `permission` VALUES (10, b'1', '编辑角色', 4, '0/4', 'role:perm', 'button', 'role/perm');
INSERT INTO `permission` VALUES (12, b'1', '学校管理', 0, NULL, 'manage:school', NULL, NULL);
INSERT INTO `permission` VALUES (13, b'1', '查询角色', 4, NULL, 'role:view', NULL, NULL);
INSERT INTO `permission` VALUES (14, b'1', '删除角色', 4, NULL, 'role:del', NULL, NULL);
INSERT INTO `permission` VALUES (15, b'1', '查看在职员工', 1, NULL, 'user:on', NULL, NULL);
INSERT INTO `permission` VALUES (16, b'1', '查看离职员工', 1, NULL, 'user:off', NULL, NULL);
INSERT INTO `permission` VALUES (17, b'1', '导入管理', 12, NULL, 'exp:user', NULL, NULL);
INSERT INTO `permission` VALUES (18, b'1', '导入学员信息', 17, NULL, 'exp:student', NULL, NULL);
INSERT INTO `permission` VALUES (19, b'1', '导入员工信息', 17, NULL, 'exp:user', NULL, NULL);
INSERT INTO `permission` VALUES (20, b'1', '导入班级信息', 17, NULL, 'exp:class', NULL, NULL);
INSERT INTO `permission` VALUES (21, b'1', '导入学员购课信息', 17, NULL, 'exp:info', NULL, NULL);
INSERT INTO `permission` VALUES (22, b'1', '教务管理', 0, NULL, 'manage:class', NULL, NULL);
INSERT INTO `permission` VALUES (23, b'1', '课表管理', 22, NULL, 'class:view', NULL, NULL);
INSERT INTO `permission` VALUES (24, b'1', '全部课表', 23, NULL, 'class:my', NULL, NULL);
INSERT INTO `permission` VALUES (25, b'1', '新增排课', 23, NULL, 'class:add', NULL, NULL);
INSERT INTO `permission` VALUES (26, b'1', '删除排课', 23, NULL, 'class:del', NULL, NULL);
INSERT INTO `permission` VALUES (27, b'1', '编辑排课', 23, NULL, 'class:update', NULL, NULL);
INSERT INTO `permission` VALUES (28, b'1', '导出课表', 23, NULL, 'class:exp', NULL, NULL);
INSERT INTO `permission` VALUES (29, b'1', '添加学员', 23, NULL, 'class:addstudent', NULL, NULL);
INSERT INTO `permission` VALUES (30, b'1', '移除学员', 23, NULL, 'class:delstudent', NULL, NULL);
INSERT INTO `permission` VALUES (31, b'1', '点名记录', 22, NULL, 'sign', NULL, NULL);
INSERT INTO `permission` VALUES (32, b'1', '查看记录', 31, NULL, 'sign:view', NULL, NULL);
INSERT INTO `permission` VALUES (33, b'1', '点名消课', 31, NULL, 'sign:add', NULL, NULL);
INSERT INTO `permission` VALUES (34, b'1', '导出记录', 31, NULL, 'sign:exp', NULL, NULL);
INSERT INTO `permission` VALUES (35, b'1', '数据中心', 0, NULL, 'manage:data', NULL, NULL);
INSERT INTO `permission` VALUES (36, b'1', '数据概览', 35, NULL, 'data', NULL, NULL);
INSERT INTO `permission` VALUES (37, b'1', '查看数据', 36, NULL, 'data:view', NULL, NULL);
INSERT INTO `permission` VALUES (38, b'1', '学员数据', 35, NULL, 'data:student', NULL, NULL);
INSERT INTO `permission` VALUES (39, b'1', '费用统计', 38, NULL, 'data:fee', NULL, NULL);
INSERT INTO `permission` VALUES (40, b'1', '导出费用数据', 38, NULL, 'data:fee:exp', NULL, NULL);
INSERT INTO `permission` VALUES (41, b'1', '课时统计', 38, NULL, 'data:class', NULL, NULL);
INSERT INTO `permission` VALUES (42, b'1', '导出课时数据', 38, NULL, 'data:class:exp', NULL, NULL);
INSERT INTO `permission` VALUES (43, b'1', '老师数据', 35, NULL, 'data:techer', NULL, NULL);
INSERT INTO `permission` VALUES (44, b'1', '上课统计', 43, NULL, 'data:cou', NULL, NULL);
INSERT INTO `permission` VALUES (45, b'1', '导出上课统计', 43, NULL, 'data:cou:exp', NULL, NULL);
INSERT INTO `permission` VALUES (46, b'1', '签单业绩统计', 43, NULL, 'data:sales', NULL, NULL);
INSERT INTO `permission` VALUES (47, b'1', '导出业绩统计', 43, NULL, 'data:sales:exp', NULL, NULL);

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(0) NOT NULL AUTO_INCREMENT,
  `available` bit(1) NULL DEFAULT NULL,
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, b'1', '管理员', 'admin');
INSERT INTO `role` VALUES (2, b'1', 'VIP会员', 'vip');
INSERT INTO `role` VALUES (3, b'1', 'test', 'test');
INSERT INTO `role` VALUES (8, b'1', '111', '111');
INSERT INTO `role` VALUES (7, b'1', '1234', '1234');
INSERT INTO `role` VALUES (6, b'1', '123', '123');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `permission_id` int(0) NOT NULL,
  `role_id` int(0) NOT NULL,
  INDEX `FK9q28ewrhntqeipl1t04kh1be7`(`role_id`) USING BTREE,
  INDEX `FKomxrs8a388bknvhjokh440waq`(`permission_id`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of role_permission
-- ----------------------------
INSERT INTO `role_permission` VALUES (31, 1);
INSERT INTO `role_permission` VALUES (30, 1);
INSERT INTO `role_permission` VALUES (29, 1);
INSERT INTO `role_permission` VALUES (28, 1);
INSERT INTO `role_permission` VALUES (27, 1);
INSERT INTO `role_permission` VALUES (26, 1);
INSERT INTO `role_permission` VALUES (25, 1);
INSERT INTO `role_permission` VALUES (24, 1);
INSERT INTO `role_permission` VALUES (23, 1);
INSERT INTO `role_permission` VALUES (22, 1);
INSERT INTO `role_permission` VALUES (21, 1);
INSERT INTO `role_permission` VALUES (20, 1);
INSERT INTO `role_permission` VALUES (19, 1);
INSERT INTO `role_permission` VALUES (18, 1);
INSERT INTO `role_permission` VALUES (17, 1);
INSERT INTO `role_permission` VALUES (16, 1);
INSERT INTO `role_permission` VALUES (15, 1);
INSERT INTO `role_permission` VALUES (14, 1);
INSERT INTO `role_permission` VALUES (13, 1);
INSERT INTO `role_permission` VALUES (12, 1);
INSERT INTO `role_permission` VALUES (11, 1);
INSERT INTO `role_permission` VALUES (10, 1);
INSERT INTO `role_permission` VALUES (8, 1);
INSERT INTO `role_permission` VALUES (5, 1);
INSERT INTO `role_permission` VALUES (4, 1);
INSERT INTO `role_permission` VALUES (3, 1);
INSERT INTO `role_permission` VALUES (2, 1);
INSERT INTO `role_permission` VALUES (1, 1);
INSERT INTO `role_permission` VALUES (32, 1);
INSERT INTO `role_permission` VALUES (33, 1);
INSERT INTO `role_permission` VALUES (34, 1);
INSERT INTO `role_permission` VALUES (35, 1);
INSERT INTO `role_permission` VALUES (36, 1);
INSERT INTO `role_permission` VALUES (37, 1);
INSERT INTO `role_permission` VALUES (38, 1);
INSERT INTO `role_permission` VALUES (39, 1);
INSERT INTO `role_permission` VALUES (40, 1);
INSERT INTO `role_permission` VALUES (41, 1);
INSERT INTO `role_permission` VALUES (42, 1);
INSERT INTO `role_permission` VALUES (43, 1);
INSERT INTO `role_permission` VALUES (44, 1);
INSERT INTO `role_permission` VALUES (45, 1);
INSERT INTO `role_permission` VALUES (46, 1);
INSERT INTO `role_permission` VALUES (47, 1);
INSERT INTO `role_permission` VALUES (1, 3);
INSERT INTO `role_permission` VALUES (2, 3);
INSERT INTO `role_permission` VALUES (3, 3);
INSERT INTO `role_permission` VALUES (8, 3);
INSERT INTO `role_permission` VALUES (11, 3);
INSERT INTO `role_permission` VALUES (15, 3);
INSERT INTO `role_permission` VALUES (16, 3);
INSERT INTO `role_permission` VALUES (12, 3);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `uid` int(0) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `state` tinyint(0) NOT NULL DEFAULT 1,
  `account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, '管理员', 'QUJBNUYyM0M3OTNEN0I4MUFBOTZBOTkwOEI1NDI0MUE=', 1, 'admin');
INSERT INTO `user_info` VALUES (4, '', 'NTExNkMyNDMyQzk3MTdFQUVGRUVEQzk1Q0Y5OUJCQTc=', 0, '123');
INSERT INTO `user_info` VALUES (3, 'nihao', 'NTY1N0NGN0QwNEEwOEY2QkFCNjVBNTA0QTVDREI3QUY=', 0, 'ffff');
INSERT INTO `user_info` VALUES (5, '456', 'RUNFMDMxQ0FGNEFDRTkzMTkyQ0Q2NzQ0ODhENzhDNUY=', 0, '456');
INSERT INTO `user_info` VALUES (6, '135', 'MTAyODkxQUYzREQxNTE3M0JGM0NGN0I4MTc4N0FDNDk=', 1, '135');
INSERT INTO `user_info` VALUES (7, '789', 'M0JGNUMyNkVDRURFNUFDM0U2NEZBQ0U0NjkwMTUyNEU=', 1, '789');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `uid` int(0) NOT NULL,
  `role_id` int(0) NOT NULL,
  INDEX `FKhh52n8vd4ny9ff4x9fb8v65qx`(`role_id`) USING BTREE,
  INDEX `FKgkmyslkrfeyn9ukmolvek8b8f`(`uid`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Fixed;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (4, 2);
INSERT INTO `user_role` VALUES (1, 1);
INSERT INTO `user_role` VALUES (1, 7);
INSERT INTO `user_role` VALUES (1, 2);
INSERT INTO `user_role` VALUES (6, 6);
INSERT INTO `user_role` VALUES (5, 2);
INSERT INTO `user_role` VALUES (4, 3);
INSERT INTO `user_role` VALUES (1, 6);

SET FOREIGN_KEY_CHECKS = 1;
