/*
 Navicat Premium Data Transfer

 Source Server         : anan
 Source Server Type    : MySQL
 Source Server Version : 50623
 Source Host           : localhost:3306
 Source Schema         : wyu0504

 Target Server Type    : MySQL
 Target Server Version : 50623
 File Encoding         : 65001

 Date: 15/07/2022 22:31:12
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for wyu_admin
-- ----------------------------
DROP TABLE IF EXISTS `wyu_admin`;
CREATE TABLE `wyu_admin`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_classes
-- ----------------------------
DROP TABLE IF EXISTS `wyu_classes`;
CREATE TABLE `wyu_classes`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cla_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `cla_no`(`cla_no`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 457 CHARACTER SET = utf8 COLLATE = utf8_bin COMMENT = '班级表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_classes_course
-- ----------------------------
DROP TABLE IF EXISTS `wyu_classes_course`;
CREATE TABLE `wyu_classes_course`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cc_course` int(255) NULL DEFAULT NULL,
  `cc_class` int(255) NULL DEFAULT NULL,
  `cc_teacher` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `cc_course`(`cc_course`, `cc_class`, `cc_teacher`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 237 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_course
-- ----------------------------
DROP TABLE IF EXISTS `wyu_course`;
CREATE TABLE `wyu_course`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cou_subject` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `cou_number` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `is_test` int(255) NULL DEFAULT 0,
  PRIMARY KEY (`id`, `cou_number`) USING BTREE,
  UNIQUE INDEX `cou_number`(`cou_number`) USING BTREE,
  UNIQUE INDEX `cou_number_2`(`cou_number`) USING BTREE,
  UNIQUE INDEX `cou_number_3`(`cou_number`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_course_knowledge_teacher
-- ----------------------------
DROP TABLE IF EXISTS `wyu_course_knowledge_teacher`;
CREATE TABLE `wyu_course_knowledge_teacher`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ck_course` int(255) NULL DEFAULT NULL,
  `ck_knowledge` int(255) NULL DEFAULT NULL,
  `ck_teacher` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `an`(`ck_course`, `ck_knowledge`, `ck_teacher`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_course_student
-- ----------------------------
DROP TABLE IF EXISTS `wyu_course_student`;
CREATE TABLE `wyu_course_student`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cs_course` int(255) NULL DEFAULT NULL,
  `cs_student` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_course_target_teacher
-- ----------------------------
DROP TABLE IF EXISTS `wyu_course_target_teacher`;
CREATE TABLE `wyu_course_target_teacher`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ctt_course` int(255) NULL DEFAULT NULL,
  `ctt_target` int(255) NULL DEFAULT NULL,
  `ctt_teacher` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_course_teacher_major
-- ----------------------------
DROP TABLE IF EXISTS `wyu_course_teacher_major`;
CREATE TABLE `wyu_course_teacher_major`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ct_course` int(255) NULL DEFAULT NULL,
  `ct_teacher` int(255) NULL DEFAULT NULL,
  `ct_major` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `ctm3`(`ct_course`, `ct_major`, `ct_teacher`) USING BTREE,
  INDEX `ct_course`(`ct_course`, `ct_teacher`, `ct_major`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_course_teacher_student
-- ----------------------------
DROP TABLE IF EXISTS `wyu_course_teacher_student`;
CREATE TABLE `wyu_course_teacher_student`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `cts_course` int(255) NULL DEFAULT NULL,
  `cts_teacher` int(255) NULL DEFAULT NULL,
  `cts_student` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `an`(`cts_course`, `cts_teacher`, `cts_student`) USING BTREE COMMENT '这三个属性不能重复一样值'
) ENGINE = InnoDB AUTO_INCREMENT = 1482 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_db
-- ----------------------------
DROP TABLE IF EXISTS `wyu_db`;
CREATE TABLE `wyu_db`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `db_type` int(255) NULL DEFAULT NULL COMMENT '题目类型 1判断题 2多选题 3判断题',
  `db_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `db_answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `db_answer_text` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `db_course` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 307 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_db_course
-- ----------------------------
DROP TABLE IF EXISTS `wyu_db_course`;
CREATE TABLE `wyu_db_course`  (
  `id` int(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  `dc_db` int(255) NULL DEFAULT NULL,
  `dc_course` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_db_knowledge_teacher
-- ----------------------------
DROP TABLE IF EXISTS `wyu_db_knowledge_teacher`;
CREATE TABLE `wyu_db_knowledge_teacher`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `dkt_db` int(255) NULL DEFAULT NULL,
  `dkt_knowledge` int(255) NULL DEFAULT NULL,
  `dkt_teacher` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_db_target_paper
-- ----------------------------
DROP TABLE IF EXISTS `wyu_db_target_paper`;
CREATE TABLE `wyu_db_target_paper`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `dtp_db` int(255) NULL DEFAULT NULL,
  `dtp_target` int(255) NULL DEFAULT NULL,
  `dtp_paper` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_db_target_teacher
-- ----------------------------
DROP TABLE IF EXISTS `wyu_db_target_teacher`;
CREATE TABLE `wyu_db_target_teacher`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `dt_db` int(255) NULL DEFAULT NULL,
  `dt_target` int(255) NULL DEFAULT NULL,
  `dt_teacher` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `wyu_knowledge`;
CREATE TABLE `wyu_knowledge`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `kno_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `kno_course` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_major
-- ----------------------------
DROP TABLE IF EXISTS `wyu_major`;
CREATE TABLE `wyu_major`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `major` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`, `major`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_major_classes
-- ----------------------------
DROP TABLE IF EXISTS `wyu_major_classes`;
CREATE TABLE `wyu_major_classes`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `mc_major` int(255) NULL DEFAULT NULL,
  `mc_classes` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_major_target_course
-- ----------------------------
DROP TABLE IF EXISTS `wyu_major_target_course`;
CREATE TABLE `wyu_major_target_course`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `mtc_major` int(255) NOT NULL,
  `mtc_target` int(255) NOT NULL,
  `mtc_course` int(255) NOT NULL,
  `proportion` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_paper
-- ----------------------------
DROP TABLE IF EXISTS `wyu_paper`;
CREATE TABLE `wyu_paper`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pap_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `pap_start` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '考试开始时间',
  `pap_end` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '考试结束时间',
  `pap_total` int(255) NULL DEFAULT NULL COMMENT '试卷总分',
  `pap_single` int(255) NULL DEFAULT NULL COMMENT '单选题数量',
  `pap_multi` int(255) NULL DEFAULT NULL COMMENT '多选题数量',
  `pap_judge` int(255) NULL DEFAULT NULL COMMENT '判断题数量',
  `pap_single_sum` int(255) NULL DEFAULT NULL COMMENT '单选题总分',
  `pap_multi_sum` int(255) NULL DEFAULT NULL COMMENT '多选题总分',
  `pap_judge_sum` int(255) NULL DEFAULT NULL COMMENT '判断题总分',
  `pap_found` int(255) NULL DEFAULT NULL COMMENT '组卷方式，1按知识点2按课程目标3随机组卷4自选题目组卷',
  `pap_exam_time` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL COMMENT '考试时长',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_paper_db
-- ----------------------------
DROP TABLE IF EXISTS `wyu_paper_db`;
CREATE TABLE `wyu_paper_db`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pd_paper` int(255) NULL DEFAULT NULL,
  `pd_db` int(255) NULL DEFAULT NULL,
  `pd_score` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 49 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_paper_knowledge
-- ----------------------------
DROP TABLE IF EXISTS `wyu_paper_knowledge`;
CREATE TABLE `wyu_paper_knowledge`  (
  `id` int(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pk_paper` int(255) NULL DEFAULT NULL,
  `pk_knowledge` int(255) NULL DEFAULT NULL,
  `pk_proportion` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_paper_student
-- ----------------------------
DROP TABLE IF EXISTS `wyu_paper_student`;
CREATE TABLE `wyu_paper_student`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ps_student` int(255) NULL DEFAULT NULL,
  `ps_paper` int(255) NULL DEFAULT NULL,
  `paper_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `paper_start` timestamp(6) NULL DEFAULT NULL,
  `paper_end` timestamp(6) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_paper_student_db
-- ----------------------------
DROP TABLE IF EXISTS `wyu_paper_student_db`;
CREATE TABLE `wyu_paper_student_db`  (
  `id` int(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  `psd_student` int(255) NOT NULL,
  `psd_paper` int(255) NOT NULL,
  `psd_db` int(255) NOT NULL,
  `psd_score` double(255, 0) UNSIGNED NULL DEFAULT 0,
  `psd_answer` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `psd_db_type` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2731 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_paper_target
-- ----------------------------
DROP TABLE IF EXISTS `wyu_paper_target`;
CREATE TABLE `wyu_paper_target`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `pt_paper` int(255) NULL DEFAULT NULL,
  `pt_target` int(255) NULL DEFAULT NULL,
  `pt_proportion` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_paper_teacher_course
-- ----------------------------
DROP TABLE IF EXISTS `wyu_paper_teacher_course`;
CREATE TABLE `wyu_paper_teacher_course`  (
  `id` int(255) UNSIGNED NOT NULL AUTO_INCREMENT,
  `ptc_teacher` int(255) NULL DEFAULT NULL,
  `ptc_paper` int(255) NULL DEFAULT NULL,
  `ptc_course` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_result
-- ----------------------------
DROP TABLE IF EXISTS `wyu_result`;
CREATE TABLE `wyu_result`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `res_student` int(255) NULL DEFAULT NULL,
  `res_db` int(255) NULL DEFAULT NULL,
  `res_paper` int(255) NULL DEFAULT NULL,
  `res_correct` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `res_own` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `res_score` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_student
-- ----------------------------
DROP TABLE IF EXISTS `wyu_student`;
CREATE TABLE `wyu_student`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '123456',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `cla_no` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `account`) USING BTREE,
  UNIQUE INDEX `account`(`account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 919 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_student_paper
-- ----------------------------
DROP TABLE IF EXISTS `wyu_student_paper`;
CREATE TABLE `wyu_student_paper`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `sp_student` int(255) NULL DEFAULT NULL,
  `sp_paper` int(255) NULL DEFAULT NULL,
  `status` int(255) NULL DEFAULT 0 COMMENT '0,还没考试 1,已经考试',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 456 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_target
-- ----------------------------
DROP TABLE IF EXISTS `wyu_target`;
CREATE TABLE `wyu_target`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tar_content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_teacher
-- ----------------------------
DROP TABLE IF EXISTS `wyu_teacher`;
CREATE TABLE `wyu_teacher`  (
  `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT,
  `account` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`, `account`) USING BTREE,
  UNIQUE INDEX `account`(`account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for wyu_totle
-- ----------------------------
DROP TABLE IF EXISTS `wyu_totle`;
CREATE TABLE `wyu_totle`  (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `tot_student` int(255) NULL DEFAULT NULL,
  `tot_paper` int(255) NULL DEFAULT NULL,
  `tot_score` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
