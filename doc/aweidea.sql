/*
Navicat MySQL Data Transfer

Source Server         : 本机Master
Source Server Version : 50622
Source Host           : 127.0.0.1:3306
Source Database       : aweidea

Target Server Type    : MYSQL
Target Server Version : 50622
File Encoding         : 65001

Date: 2020-10-25 21:22:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of qrtz_blob_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(190) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of qrtz_calendars
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of qrtz_cron_triggers
-- ----------------------------
INSERT INTO `qrtz_cron_triggers` VALUES ('AweBatch_Scheduler', 'TASK_1', 'DEFAULT', '0 * * * * ? *', 'Asia/Shanghai');
INSERT INTO `qrtz_cron_triggers` VALUES ('AweBatch_Scheduler', 'TASK_2', 'DEFAULT', '30 * * * * ? *', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(190) DEFAULT NULL,
  `JOB_GROUP` varchar(190) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of qrtz_fired_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of qrtz_job_details
-- ----------------------------
INSERT INTO `qrtz_job_details` VALUES ('AweBatch_Scheduler', 'TASK_1', 'DEFAULT', null, 'awe.idea.com.batch.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002D6177652E696465612E636F6D2E736572766963652E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C000A6D6574686F644E616D6571007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000158BAF593307874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000017400047465737474000672656E72656E74000FE69C89E58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);
INSERT INTO `qrtz_job_details` VALUES ('AweBatch_Scheduler', 'TASK_2', 'DEFAULT', null, 'awe.idea.com.batch.utils.ScheduleJob', '0', '0', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002D6177652E696465612E636F6D2E736572766963652E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C000A6D6574686F644E616D6571007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000158C377C4607874000E3020302F3330202A202A202A203F7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000274000574657374327074000FE697A0E58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000017800);

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of qrtz_locks
-- ----------------------------
INSERT INTO `qrtz_locks` VALUES ('AweBatch_Scheduler', 'STATE_ACCESS');
INSERT INTO `qrtz_locks` VALUES ('AweBatch_Scheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of qrtz_paused_trigger_grps
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(190) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of qrtz_scheduler_state
-- ----------------------------
INSERT INTO `qrtz_scheduler_state` VALUES ('AweBatch_Scheduler', 'CHINA00011603631617451', '1603631798649', '15000');

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of qrtz_simple_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of qrtz_simprop_triggers
-- ----------------------------

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(190) NOT NULL,
  `TRIGGER_GROUP` varchar(190) NOT NULL,
  `JOB_NAME` varchar(190) NOT NULL,
  `JOB_GROUP` varchar(190) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(190) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of qrtz_triggers
-- ----------------------------
INSERT INTO `qrtz_triggers` VALUES ('AweBatch_Scheduler', 'TASK_1', 'DEFAULT', 'TASK_1', 'DEFAULT', null, '1603631820000', '1603631760000', '5', 'WAITING', 'CRON', '1602326916000', '0', null, '2', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002D6177652E696465612E636F6D2E736572766963652E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C000A6D6574686F644E616D6571007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000158BAF593307874000D30202A202A202A202A203F202A7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B020000787000000000000000017400047465737474000672656E72656E74000FE69C89E58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);
INSERT INTO `qrtz_triggers` VALUES ('AweBatch_Scheduler', 'TASK_2', 'DEFAULT', 'TASK_2', 'DEFAULT', null, '1603631850000', '1603631790000', '5', 'WAITING', 'CRON', '1602326914000', '0', null, '2', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C7708000000100000000174000D4A4F425F504152414D5F4B45597372002D6177652E696465612E636F6D2E736572766963652E656E746974792E5363686564756C654A6F62456E7469747900000000000000010200084C00086265616E4E616D657400124C6A6176612F6C616E672F537472696E673B4C000A63726561746554696D657400104C6A6176612F7574696C2F446174653B4C000E63726F6E45787072657373696F6E71007E00094C00056A6F6249647400104C6A6176612F6C616E672F4C6F6E673B4C000A6D6574686F644E616D6571007E00094C0006706172616D7371007E00094C000672656D61726B71007E00094C00067374617475737400134C6A6176612F6C616E672F496E74656765723B7870740008746573745461736B7372000E6A6176612E7574696C2E44617465686A81014B5974190300007870770800000158C377C4607874000E3330202A202A202A202A203F202A7372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000000274000574657374327074000FE697A0E58F82E695B0E6B58BE8AF95737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C75657871007E0013000000007800);

-- ----------------------------
-- Table structure for schedule_job
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job`;
CREATE TABLE `schedule_job` (
  `job_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `status` tinyint(4) DEFAULT NULL COMMENT '任务状态',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='定时任务';

-- ----------------------------
-- Records of schedule_job
-- ----------------------------
INSERT INTO `schedule_job` VALUES ('1', 'testTask', 'test', 'renren', '0 * * * * ? *', '0', '有参数测试', '2016-12-01 23:16:46');
INSERT INTO `schedule_job` VALUES ('2', 'testTask', 'test2', null, '30 * * * * ? *', '0', '无参数测试', '2016-12-03 14:55:56');

-- ----------------------------
-- Table structure for schedule_job_log
-- ----------------------------
DROP TABLE IF EXISTS `schedule_job_log`;
CREATE TABLE `schedule_job_log` (
  `log_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` bigint(20) NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `method_name` varchar(100) DEFAULT NULL COMMENT '方法名',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`log_id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

-- ----------------------------
-- Records of schedule_job_log
-- ----------------------------
INSERT INTO `schedule_job_log` VALUES ('1', '1', 'testTask', 'test', 'renren', '0', null, '1007', '2020-10-10 19:00:00');
INSERT INTO `schedule_job_log` VALUES ('2', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-10 19:15:11');
INSERT INTO `schedule_job_log` VALUES ('3', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-10 19:16:05');
INSERT INTO `schedule_job_log` VALUES ('4', '1', 'testTask', 'test', 'renren', '0', null, '14554', '2020-10-10 19:17:56');
INSERT INTO `schedule_job_log` VALUES ('5', '2', 'testTask', 'test2', null, '0', null, '2799', '2020-10-10 19:19:40');
INSERT INTO `schedule_job_log` VALUES ('6', '2', 'testTask', 'test2', null, '0', null, '6', '2020-10-10 19:19:51');
INSERT INTO `schedule_job_log` VALUES ('7', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-10 19:20:00');
INSERT INTO `schedule_job_log` VALUES ('8', '1', 'testTask', 'test', 'renren', '0', null, '1007', '2020-10-10 19:20:00');
INSERT INTO `schedule_job_log` VALUES ('9', '2', 'testTask', 'test2', null, '0', null, '1', '2020-10-10 19:20:10');
INSERT INTO `schedule_job_log` VALUES ('10', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2020-10-10 19:20:10');
INSERT INTO `schedule_job_log` VALUES ('11', '2', 'testTask', 'test2', null, '0', null, '22', '2020-10-10 19:20:21');
INSERT INTO `schedule_job_log` VALUES ('12', '1', 'testTask', 'test', 'renren', '0', null, '1008', '2020-10-10 19:20:20');
INSERT INTO `schedule_job_log` VALUES ('13', '2', 'testTask', 'test2', null, '0', null, '2', '2020-10-10 19:20:30');
INSERT INTO `schedule_job_log` VALUES ('14', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2020-10-10 19:20:30');
INSERT INTO `schedule_job_log` VALUES ('15', '2', 'testTask', 'test2', null, '0', null, '2', '2020-10-10 19:20:40');
INSERT INTO `schedule_job_log` VALUES ('16', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-10 19:20:40');
INSERT INTO `schedule_job_log` VALUES ('17', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-10 19:20:50');
INSERT INTO `schedule_job_log` VALUES ('18', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2020-10-10 19:20:50');
INSERT INTO `schedule_job_log` VALUES ('19', '2', 'testTask', 'test2', null, '0', null, '53', '2020-10-10 19:21:00');
INSERT INTO `schedule_job_log` VALUES ('20', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2020-10-10 19:21:00');
INSERT INTO `schedule_job_log` VALUES ('21', '2', 'testTask', 'test2', null, '0', null, '2', '2020-10-10 19:21:10');
INSERT INTO `schedule_job_log` VALUES ('22', '2', 'testTask', 'test2', null, '0', null, '20', '2020-10-10 19:21:20');
INSERT INTO `schedule_job_log` VALUES ('23', '2', 'testTask', 'test2', null, '0', null, '2', '2020-10-10 19:21:30');
INSERT INTO `schedule_job_log` VALUES ('24', '2', 'testTask', 'test2', null, '0', null, '2', '2020-10-10 19:21:40');
INSERT INTO `schedule_job_log` VALUES ('25', '2', 'testTask', 'test2', null, '0', null, '2', '2020-10-10 19:21:50');
INSERT INTO `schedule_job_log` VALUES ('26', '1', 'testTask', 'test', 'renren', '0', null, '1001', '2020-10-10 19:22:00');
INSERT INTO `schedule_job_log` VALUES ('27', '2', 'testTask', 'test2', null, '0', null, '2', '2020-10-10 19:22:30');
INSERT INTO `schedule_job_log` VALUES ('28', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-10 19:23:00');
INSERT INTO `schedule_job_log` VALUES ('29', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2020-10-10 19:26:00');
INSERT INTO `schedule_job_log` VALUES ('30', '2', 'testTask', 'test2', null, '0', null, '4', '2020-10-10 19:26:30');
INSERT INTO `schedule_job_log` VALUES ('31', '1', 'testTask', 'test', 'renren', '0', null, '1000', '2020-10-10 19:27:00');
INSERT INTO `schedule_job_log` VALUES ('32', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2020-10-10 19:34:00');
INSERT INTO `schedule_job_log` VALUES ('33', '2', 'testTask', 'test2', null, '0', null, '1', '2020-10-10 19:34:30');
INSERT INTO `schedule_job_log` VALUES ('34', '1', 'testTask', 'test', 'renren', '0', null, '1005', '2020-10-12 12:07:00');
INSERT INTO `schedule_job_log` VALUES ('35', '2', 'testTask', 'test2', null, '0', null, '6', '2020-10-12 12:07:30');
INSERT INTO `schedule_job_log` VALUES ('36', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2020-10-12 12:08:00');
INSERT INTO `schedule_job_log` VALUES ('37', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2020-10-12 13:00:00');
INSERT INTO `schedule_job_log` VALUES ('38', '2', 'testTask', 'test2', null, '0', null, '13', '2020-10-12 13:00:30');
INSERT INTO `schedule_job_log` VALUES ('39', '1', 'testTask', 'test', 'renren', '0', null, '1001', '2020-10-12 13:01:00');
INSERT INTO `schedule_job_log` VALUES ('40', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-12 13:02:00');
INSERT INTO `schedule_job_log` VALUES ('41', '2', 'testTask', 'test2', null, '0', null, '4', '2020-10-12 13:02:30');
INSERT INTO `schedule_job_log` VALUES ('42', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-12 13:03:00');
INSERT INTO `schedule_job_log` VALUES ('43', '2', 'testTask', 'test2', null, '0', null, '20', '2020-10-12 13:06:30');
INSERT INTO `schedule_job_log` VALUES ('44', '1', 'testTask', 'test', 'renren', '0', null, '1006', '2020-10-12 13:07:00');
INSERT INTO `schedule_job_log` VALUES ('45', '2', 'testTask', 'test2', null, '0', null, '8', '2020-10-12 13:07:30');
INSERT INTO `schedule_job_log` VALUES ('46', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-12 13:08:00');
INSERT INTO `schedule_job_log` VALUES ('47', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-12 13:08:30');
INSERT INTO `schedule_job_log` VALUES ('48', '1', 'testTask', 'test', 'renren', '0', null, '1005', '2020-10-12 13:09:00');
INSERT INTO `schedule_job_log` VALUES ('49', '2', 'testTask', 'test2', null, '0', null, '21', '2020-10-12 13:09:30');
INSERT INTO `schedule_job_log` VALUES ('50', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-12 13:10:00');
INSERT INTO `schedule_job_log` VALUES ('51', '2', 'testTask', 'test2', null, '0', null, '9', '2020-10-12 13:15:30');
INSERT INTO `schedule_job_log` VALUES ('52', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2020-10-12 13:16:00');
INSERT INTO `schedule_job_log` VALUES ('53', '2', 'testTask', 'test2', null, '0', null, '1', '2020-10-12 13:16:30');
INSERT INTO `schedule_job_log` VALUES ('54', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2020-10-12 13:18:00');
INSERT INTO `schedule_job_log` VALUES ('55', '2', 'testTask', 'test2', null, '0', null, '4', '2020-10-12 15:13:30');
INSERT INTO `schedule_job_log` VALUES ('56', '1', 'testTask', 'test', 'renren', '0', null, '1015', '2020-10-12 15:14:00');
INSERT INTO `schedule_job_log` VALUES ('57', '2', 'testTask', 'test2', null, '0', null, '9', '2020-10-12 15:14:31');
INSERT INTO `schedule_job_log` VALUES ('58', '1', 'testTask', 'test', 'renren', '0', null, '1012', '2020-10-12 15:15:00');
INSERT INTO `schedule_job_log` VALUES ('59', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-12 15:15:30');
INSERT INTO `schedule_job_log` VALUES ('60', '1', 'testTask', 'test', 'renren', '0', null, '1038', '2020-10-12 15:16:00');
INSERT INTO `schedule_job_log` VALUES ('61', '2', 'testTask', 'test2', null, '0', null, '4', '2020-10-12 15:16:30');
INSERT INTO `schedule_job_log` VALUES ('62', '1', 'testTask', 'test', 'renren', '0', null, '1011', '2020-10-12 15:17:00');
INSERT INTO `schedule_job_log` VALUES ('63', '2', 'testTask', 'test2', null, '0', null, '10', '2020-10-12 15:17:30');
INSERT INTO `schedule_job_log` VALUES ('64', '1', 'testTask', 'test', 'renren', '0', null, '1001', '2020-10-12 15:18:00');
INSERT INTO `schedule_job_log` VALUES ('65', '2', 'testTask', 'test2', null, '0', null, '42', '2020-10-12 15:18:30');
INSERT INTO `schedule_job_log` VALUES ('66', '1', 'testTask', 'test', 'renren', '0', null, '1016', '2020-10-12 15:19:00');
INSERT INTO `schedule_job_log` VALUES ('67', '2', 'testTask', 'test2', null, '0', null, '8', '2020-10-12 15:19:30');
INSERT INTO `schedule_job_log` VALUES ('68', '1', 'testTask', 'test', 'renren', '0', null, '1008', '2020-10-12 15:20:00');
INSERT INTO `schedule_job_log` VALUES ('69', '2', 'testTask', 'test2', null, '0', null, '2', '2020-10-12 15:20:30');
INSERT INTO `schedule_job_log` VALUES ('70', '1', 'testTask', 'test', 'renren', '0', null, '1019', '2020-10-12 15:21:00');
INSERT INTO `schedule_job_log` VALUES ('71', '2', 'testTask', 'test2', null, '0', null, '5', '2020-10-12 15:21:30');
INSERT INTO `schedule_job_log` VALUES ('72', '1', 'testTask', 'test', 'renren', '0', null, '1007', '2020-10-12 15:22:00');
INSERT INTO `schedule_job_log` VALUES ('73', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-12 15:22:30');
INSERT INTO `schedule_job_log` VALUES ('74', '1', 'testTask', 'test', 'renren', '0', null, '1012', '2020-10-12 15:23:00');
INSERT INTO `schedule_job_log` VALUES ('75', '2', 'testTask', 'test2', null, '0', null, '11', '2020-10-12 15:23:30');
INSERT INTO `schedule_job_log` VALUES ('76', '1', 'testTask', 'test', 'renren', '0', null, '1001', '2020-10-12 15:24:00');
INSERT INTO `schedule_job_log` VALUES ('77', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-12 15:24:30');
INSERT INTO `schedule_job_log` VALUES ('78', '1', 'testTask', 'test', 'renren', '0', null, '1019', '2020-10-12 15:25:00');
INSERT INTO `schedule_job_log` VALUES ('79', '2', 'testTask', 'test2', null, '0', null, '8', '2020-10-12 15:25:30');
INSERT INTO `schedule_job_log` VALUES ('80', '1', 'testTask', 'test', 'renren', '0', null, '1001', '2020-10-12 15:26:00');
INSERT INTO `schedule_job_log` VALUES ('81', '2', 'testTask', 'test2', null, '0', null, '13', '2020-10-12 15:26:30');
INSERT INTO `schedule_job_log` VALUES ('82', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-12 15:27:00');
INSERT INTO `schedule_job_log` VALUES ('83', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-12 15:27:30');
INSERT INTO `schedule_job_log` VALUES ('84', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-12 15:28:00');
INSERT INTO `schedule_job_log` VALUES ('85', '2', 'testTask', 'test2', null, '0', null, '6', '2020-10-12 15:28:30');
INSERT INTO `schedule_job_log` VALUES ('86', '1', 'testTask', 'test', 'renren', '0', null, '1006', '2020-10-12 15:29:00');
INSERT INTO `schedule_job_log` VALUES ('87', '2', 'testTask', 'test2', null, '0', null, '2', '2020-10-12 15:29:30');
INSERT INTO `schedule_job_log` VALUES ('88', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2020-10-12 15:30:00');
INSERT INTO `schedule_job_log` VALUES ('89', '2', 'testTask', 'test2', null, '0', null, '1', '2020-10-12 15:30:30');
INSERT INTO `schedule_job_log` VALUES ('90', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-12 15:31:00');
INSERT INTO `schedule_job_log` VALUES ('91', '2', 'testTask', 'test2', null, '0', null, '5', '2020-10-12 15:31:30');
INSERT INTO `schedule_job_log` VALUES ('92', '1', 'testTask', 'test', 'renren', '0', null, '1005', '2020-10-12 15:32:00');
INSERT INTO `schedule_job_log` VALUES ('93', '2', 'testTask', 'test2', null, '0', null, '1', '2020-10-12 15:32:30');
INSERT INTO `schedule_job_log` VALUES ('94', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2020-10-12 15:33:00');
INSERT INTO `schedule_job_log` VALUES ('95', '2', 'testTask', 'test2', null, '0', null, '4', '2020-10-12 15:33:30');
INSERT INTO `schedule_job_log` VALUES ('96', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-12 15:34:00');
INSERT INTO `schedule_job_log` VALUES ('97', '2', 'testTask', 'test2', null, '0', null, '1', '2020-10-12 15:34:30');
INSERT INTO `schedule_job_log` VALUES ('98', '1', 'testTask', 'test', 'renren', '0', null, '1001', '2020-10-12 15:35:00');
INSERT INTO `schedule_job_log` VALUES ('99', '2', 'testTask', 'test2', null, '0', null, '1', '2020-10-12 15:35:30');
INSERT INTO `schedule_job_log` VALUES ('100', '1', 'testTask', 'test', 'renren', '0', null, '1006', '2020-10-13 18:36:02');
INSERT INTO `schedule_job_log` VALUES ('101', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-13 18:36:30');
INSERT INTO `schedule_job_log` VALUES ('102', '1', 'testTask', 'test', 'renren', '0', null, '1001', '2020-10-13 18:37:00');
INSERT INTO `schedule_job_log` VALUES ('103', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-13 18:37:30');
INSERT INTO `schedule_job_log` VALUES ('104', '1', 'testTask', 'test', 'renren', '0', null, '1001', '2020-10-13 18:38:00');
INSERT INTO `schedule_job_log` VALUES ('105', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-13 18:38:30');
INSERT INTO `schedule_job_log` VALUES ('106', '1', 'testTask', 'test', 'renren', '0', null, '1001', '2020-10-13 18:39:00');
INSERT INTO `schedule_job_log` VALUES ('107', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2020-10-13 18:45:01');
INSERT INTO `schedule_job_log` VALUES ('108', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-13 18:45:30');
INSERT INTO `schedule_job_log` VALUES ('109', '1', 'testTask', 'test', 'renren', '0', null, '1007', '2020-10-13 18:46:00');
INSERT INTO `schedule_job_log` VALUES ('110', '2', 'testTask', 'test2', null, '0', null, '2', '2020-10-13 18:46:30');
INSERT INTO `schedule_job_log` VALUES ('111', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-13 18:47:00');
INSERT INTO `schedule_job_log` VALUES ('112', '2', 'testTask', 'test2', null, '0', null, '5', '2020-10-13 18:47:30');
INSERT INTO `schedule_job_log` VALUES ('113', '1', 'testTask', 'test', 'renren', '0', null, '1026', '2020-10-13 18:48:00');
INSERT INTO `schedule_job_log` VALUES ('114', '2', 'testTask', 'test2', null, '0', null, '2', '2020-10-13 18:48:30');
INSERT INTO `schedule_job_log` VALUES ('115', '1', 'testTask', 'test', 'renren', '0', null, '1000', '2020-10-13 18:49:00');
INSERT INTO `schedule_job_log` VALUES ('116', '2', 'testTask', 'test2', null, '0', null, '1', '2020-10-13 18:49:30');
INSERT INTO `schedule_job_log` VALUES ('117', '1', 'testTask', 'test', 'renren', '0', null, '1001', '2020-10-13 18:50:00');
INSERT INTO `schedule_job_log` VALUES ('118', '2', 'testTask', 'test2', null, '0', null, '6', '2020-10-17 16:12:30');
INSERT INTO `schedule_job_log` VALUES ('119', '2', 'testTask', 'test2', null, '0', null, '1', '2020-10-17 16:12:50');
INSERT INTO `schedule_job_log` VALUES ('120', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2020-10-17 16:13:00');
INSERT INTO `schedule_job_log` VALUES ('121', '2', 'testTask', 'test2', null, '0', null, '37', '2020-10-17 16:13:30');
INSERT INTO `schedule_job_log` VALUES ('122', '1', 'testTask', 'test', 'renren', '0', null, '1114', '2020-10-17 16:14:00');
INSERT INTO `schedule_job_log` VALUES ('123', '2', 'testTask', 'test2', null, '0', null, '4', '2020-10-17 16:14:30');
INSERT INTO `schedule_job_log` VALUES ('124', '1', 'testTask', 'test', 'renren', '0', null, '1046', '2020-10-17 16:15:00');
INSERT INTO `schedule_job_log` VALUES ('125', '2', 'testTask', 'test2', null, '0', null, '1', '2020-10-17 16:15:30');
INSERT INTO `schedule_job_log` VALUES ('126', '1', 'testTask', 'test', 'renren', '0', null, '1000', '2020-10-17 16:16:00');
INSERT INTO `schedule_job_log` VALUES ('127', '2', 'testTask', 'test2', null, '0', null, '32', '2020-10-17 16:16:30');
INSERT INTO `schedule_job_log` VALUES ('128', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-17 16:17:00');
INSERT INTO `schedule_job_log` VALUES ('129', '2', 'testTask', 'test2', null, '0', null, '9', '2020-10-17 16:17:30');
INSERT INTO `schedule_job_log` VALUES ('130', '1', 'testTask', 'test', 'renren', '0', null, '1005', '2020-10-17 16:18:00');
INSERT INTO `schedule_job_log` VALUES ('131', '2', 'testTask', 'test2', null, '0', null, '11', '2020-10-17 16:18:30');
INSERT INTO `schedule_job_log` VALUES ('132', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-22 12:14:30');
INSERT INTO `schedule_job_log` VALUES ('133', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2020-10-22 12:15:00');
INSERT INTO `schedule_job_log` VALUES ('134', '2', 'testTask', 'test2', null, '0', null, '46', '2020-10-22 12:15:30');
INSERT INTO `schedule_job_log` VALUES ('135', '1', 'testTask', 'test', 'renren', '0', null, '1013', '2020-10-22 12:16:00');
INSERT INTO `schedule_job_log` VALUES ('136', '2', 'testTask', 'test2', null, '0', null, '7', '2020-10-25 17:09:30');
INSERT INTO `schedule_job_log` VALUES ('137', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-25 17:10:00');
INSERT INTO `schedule_job_log` VALUES ('138', '2', 'testTask', 'test2', null, '0', null, '6', '2020-10-25 17:10:30');
INSERT INTO `schedule_job_log` VALUES ('139', '1', 'testTask', 'test', 'renren', '0', null, '1000', '2020-10-25 17:11:00');
INSERT INTO `schedule_job_log` VALUES ('140', '2', 'testTask', 'test2', null, '0', null, '2', '2020-10-25 17:11:30');
INSERT INTO `schedule_job_log` VALUES ('141', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2020-10-25 17:12:00');
INSERT INTO `schedule_job_log` VALUES ('142', '2', 'testTask', 'test2', null, '0', null, '6', '2020-10-25 17:12:30');
INSERT INTO `schedule_job_log` VALUES ('143', '1', 'testTask', 'test', 'renren', '0', null, '1001', '2020-10-25 17:13:00');
INSERT INTO `schedule_job_log` VALUES ('144', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-25 17:13:30');
INSERT INTO `schedule_job_log` VALUES ('145', '1', 'testTask', 'test', 'renren', '0', null, '1001', '2020-10-25 17:14:00');
INSERT INTO `schedule_job_log` VALUES ('146', '2', 'testTask', 'test2', null, '0', null, '2', '2020-10-25 17:14:30');
INSERT INTO `schedule_job_log` VALUES ('147', '1', 'testTask', 'test', 'renren', '0', null, '1002', '2020-10-25 17:15:00');
INSERT INTO `schedule_job_log` VALUES ('148', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-25 17:15:30');
INSERT INTO `schedule_job_log` VALUES ('149', '1', 'testTask', 'test', 'renren', '0', null, '1004', '2020-10-25 17:16:00');
INSERT INTO `schedule_job_log` VALUES ('150', '2', 'testTask', 'test2', null, '0', null, '1', '2020-10-25 17:16:30');
INSERT INTO `schedule_job_log` VALUES ('151', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2020-10-25 17:17:00');
INSERT INTO `schedule_job_log` VALUES ('152', '2', 'testTask', 'test2', null, '0', null, '2', '2020-10-25 17:17:30');
INSERT INTO `schedule_job_log` VALUES ('153', '1', 'testTask', 'test', 'renren', '0', null, '1001', '2020-10-25 17:18:00');
INSERT INTO `schedule_job_log` VALUES ('154', '2', 'testTask', 'test2', null, '0', null, '3', '2020-10-25 17:18:30');
INSERT INTO `schedule_job_log` VALUES ('155', '1', 'testTask', 'test', 'renren', '0', null, '1035', '2020-10-25 17:19:00');
INSERT INTO `schedule_job_log` VALUES ('156', '2', 'testTask', 'test2', null, '0', null, '4', '2020-10-25 21:14:30');
INSERT INTO `schedule_job_log` VALUES ('157', '1', 'testTask', 'test', 'renren', '0', null, '1013', '2020-10-25 21:15:00');
INSERT INTO `schedule_job_log` VALUES ('158', '2', 'testTask', 'test2', null, '0', null, '1', '2020-10-25 21:15:30');
INSERT INTO `schedule_job_log` VALUES ('159', '1', 'testTask', 'test', 'renren', '0', null, '1003', '2020-10-25 21:16:00');
INSERT INTO `schedule_job_log` VALUES ('160', '2', 'testTask', 'test2', null, '0', null, '1', '2020-10-25 21:16:30');

-- ----------------------------
-- Table structure for sys_config
-- ----------------------------
DROP TABLE IF EXISTS `sys_config`;
CREATE TABLE `sys_config` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `key` varchar(50) DEFAULT NULL COMMENT 'key',
  `value` varchar(2000) DEFAULT NULL COMMENT 'value',
  `status` tinyint(4) DEFAULT '1' COMMENT '状态   0：隐藏   1：显示',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `key` (`key`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统配置信息表';

-- ----------------------------
-- Records of sys_config
-- ----------------------------
INSERT INTO `sys_config` VALUES ('1', 'CLOUD_STORAGE_CONFIG_KEY', '{\"aliyunAccessKeyId\":\"\",\"aliyunAccessKeySecret\":\"\",\"aliyunBucketName\":\"\",\"aliyunDomain\":\"\",\"aliyunEndPoint\":\"\",\"aliyunPrefix\":\"\",\"qcloudBucketName\":\"\",\"qcloudDomain\":\"\",\"qcloudPrefix\":\"\",\"qcloudSecretId\":\"\",\"qcloudSecretKey\":\"\",\"qiniuAccessKey\":\"NrgMfABZxWLo5B-YYSjoE8-AZ1EISdi1Z3ubLOeZ\",\"qiniuBucketName\":\"ios-app\",\"qiniuDomain\":\"http://7xqbwh.dl1.z0.glb.clouddn.com\",\"qiniuPrefix\":\"upload\",\"qiniuSecretKey\":\"uIwJHevMRWU0VLxFvgy0tAcOdGqasdtVlJkdy6vV\",\"type\":1}', '0', '云存储配置信息1');

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL COMMENT '用户名',
  `operation` varchar(50) DEFAULT NULL COMMENT '用户操作',
  `method` varchar(200) DEFAULT NULL COMMENT '请求方法',
  `params` varchar(5000) DEFAULT NULL COMMENT '请求参数',
  `ip` varchar(64) DEFAULT NULL COMMENT 'IP地址',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1', 'admin', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-11 16:30:14');
INSERT INTO `sys_log` VALUES ('2', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-11 16:31:20');
INSERT INTO `sys_log` VALUES ('3', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-11 16:34:10');
INSERT INTO `sys_log` VALUES ('4', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-11 16:34:21');
INSERT INTO `sys_log` VALUES ('5', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 11:44:00');
INSERT INTO `sys_log` VALUES ('6', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-12 11:44:36');
INSERT INTO `sys_log` VALUES ('7', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 11:51:10');
INSERT INTO `sys_log` VALUES ('8', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-12 11:51:22');
INSERT INTO `sys_log` VALUES ('9', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 11:51:48');
INSERT INTO `sys_log` VALUES ('10', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-12 11:52:14');
INSERT INTO `sys_log` VALUES ('11', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 11:57:11');
INSERT INTO `sys_log` VALUES ('12', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 11:57:24');
INSERT INTO `sys_log` VALUES ('13', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-12 11:57:52');
INSERT INTO `sys_log` VALUES ('14', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 11:58:07');
INSERT INTO `sys_log` VALUES ('15', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-12 11:58:25');
INSERT INTO `sys_log` VALUES ('16', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 11:58:38');
INSERT INTO `sys_log` VALUES ('17', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-12 12:00:24');
INSERT INTO `sys_log` VALUES ('18', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 12:00:50');
INSERT INTO `sys_log` VALUES ('19', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-12 12:01:09');
INSERT INTO `sys_log` VALUES ('20', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 12:01:21');
INSERT INTO `sys_log` VALUES ('21', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 12:01:32');
INSERT INTO `sys_log` VALUES ('22', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-12 12:07:29');
INSERT INTO `sys_log` VALUES ('23', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 12:38:14');
INSERT INTO `sys_log` VALUES ('24', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 12:38:50');
INSERT INTO `sys_log` VALUES ('25', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 12:43:04');
INSERT INTO `sys_log` VALUES ('26', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-12 12:49:01');
INSERT INTO `sys_log` VALUES ('27', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 12:49:08');
INSERT INTO `sys_log` VALUES ('28', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-12 12:55:43');
INSERT INTO `sys_log` VALUES ('29', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 12:55:50');
INSERT INTO `sys_log` VALUES ('30', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-12 12:59:26');
INSERT INTO `sys_log` VALUES ('31', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 13:04:15');
INSERT INTO `sys_log` VALUES ('32', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-12 13:06:26');
INSERT INTO `sys_log` VALUES ('33', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 14:50:47');
INSERT INTO `sys_log` VALUES ('34', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 14:52:37');
INSERT INTO `sys_log` VALUES ('35', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 15:10:55');
INSERT INTO `sys_log` VALUES ('36', 'admin', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 15:17:55');
INSERT INTO `sys_log` VALUES ('37', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-12 15:18:04');
INSERT INTO `sys_log` VALUES ('38', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 15:18:25');
INSERT INTO `sys_log` VALUES ('39', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-12 15:23:46');
INSERT INTO `sys_log` VALUES ('40', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-13 18:43:00');
INSERT INTO `sys_log` VALUES ('41', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-13 18:44:17');
INSERT INTO `sys_log` VALUES ('42', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-13 18:51:40');
INSERT INTO `sys_log` VALUES ('43', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-13 18:53:45');
INSERT INTO `sys_log` VALUES ('44', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-13 18:54:44');
INSERT INTO `sys_log` VALUES ('45', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-14 12:04:39');
INSERT INTO `sys_log` VALUES ('46', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-14 12:04:54');
INSERT INTO `sys_log` VALUES ('47', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-14 12:06:04');
INSERT INTO `sys_log` VALUES ('48', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-14 12:17:34');
INSERT INTO `sys_log` VALUES ('49', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-14 12:24:34');
INSERT INTO `sys_log` VALUES ('50', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-14 12:26:47');
INSERT INTO `sys_log` VALUES ('51', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"root\"', '0:0:0:0:0:0:0:1', '2020-10-14 12:26:57');
INSERT INTO `sys_log` VALUES ('52', 'root', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-14 12:27:03');
INSERT INTO `sys_log` VALUES ('53', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-14 12:27:10');
INSERT INTO `sys_log` VALUES ('54', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-14 12:35:43');
INSERT INTO `sys_log` VALUES ('55', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-14 12:40:01');
INSERT INTO `sys_log` VALUES ('56', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-14 12:47:53');
INSERT INTO `sys_log` VALUES ('57', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"root\"', '0:0:0:0:0:0:0:1', '2020-10-14 12:48:02');
INSERT INTO `sys_log` VALUES ('58', 'root', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-14 12:49:15');
INSERT INTO `sys_log` VALUES ('59', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-14 12:49:20');
INSERT INTO `sys_log` VALUES ('60', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-14 12:50:22');
INSERT INTO `sys_log` VALUES ('61', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"root\"', '0:0:0:0:0:0:0:1', '2020-10-14 12:50:30');
INSERT INTO `sys_log` VALUES ('62', 'root', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-14 12:51:10');
INSERT INTO `sys_log` VALUES ('63', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-14 12:51:15');
INSERT INTO `sys_log` VALUES ('64', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-14 19:40:08');
INSERT INTO `sys_log` VALUES ('65', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 11:36:41');
INSERT INTO `sys_log` VALUES ('66', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 12:06:26');
INSERT INTO `sys_log` VALUES ('67', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '127.0.0.1', '2020-10-16 12:17:56');
INSERT INTO `sys_log` VALUES ('68', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 12:23:30');
INSERT INTO `sys_log` VALUES ('69', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 13:05:05');
INSERT INTO `sys_log` VALUES ('70', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 13:06:41');
INSERT INTO `sys_log` VALUES ('71', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 13:06:48');
INSERT INTO `sys_log` VALUES ('72', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 13:10:20');
INSERT INTO `sys_log` VALUES ('73', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 13:14:11');
INSERT INTO `sys_log` VALUES ('74', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 13:17:07');
INSERT INTO `sys_log` VALUES ('75', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 13:19:03');
INSERT INTO `sys_log` VALUES ('76', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 13:24:49');
INSERT INTO `sys_log` VALUES ('77', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 13:33:29');
INSERT INTO `sys_log` VALUES ('78', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 13:37:21');
INSERT INTO `sys_log` VALUES ('79', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 13:48:24');
INSERT INTO `sys_log` VALUES ('80', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 17:29:14');
INSERT INTO `sys_log` VALUES ('81', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 17:31:03');
INSERT INTO `sys_log` VALUES ('82', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 17:41:25');
INSERT INTO `sys_log` VALUES ('83', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 17:46:08');
INSERT INTO `sys_log` VALUES ('84', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 17:48:43');
INSERT INTO `sys_log` VALUES ('85', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 18:13:47');
INSERT INTO `sys_log` VALUES ('86', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 18:21:55');
INSERT INTO `sys_log` VALUES ('87', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 18:29:04');
INSERT INTO `sys_log` VALUES ('88', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 18:33:15');
INSERT INTO `sys_log` VALUES ('89', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 18:57:11');
INSERT INTO `sys_log` VALUES ('90', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 19:29:48');
INSERT INTO `sys_log` VALUES ('91', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 21:40:57');
INSERT INTO `sys_log` VALUES ('92', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 21:41:06');
INSERT INTO `sys_log` VALUES ('93', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 21:44:29');
INSERT INTO `sys_log` VALUES ('94', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 21:50:06');
INSERT INTO `sys_log` VALUES ('95', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 22:35:06');
INSERT INTO `sys_log` VALUES ('96', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 22:37:01');
INSERT INTO `sys_log` VALUES ('97', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 22:39:17');
INSERT INTO `sys_log` VALUES ('98', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 22:39:40');
INSERT INTO `sys_log` VALUES ('99', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 22:40:06');
INSERT INTO `sys_log` VALUES ('100', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 22:41:35');
INSERT INTO `sys_log` VALUES ('101', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 22:41:39');
INSERT INTO `sys_log` VALUES ('102', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 22:41:52');
INSERT INTO `sys_log` VALUES ('103', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 22:42:26');
INSERT INTO `sys_log` VALUES ('104', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 22:43:14');
INSERT INTO `sys_log` VALUES ('105', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 22:44:46');
INSERT INTO `sys_log` VALUES ('106', 'system', '登录', 'awe.idea.com.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 23:12:46');
INSERT INTO `sys_log` VALUES ('107', 'system', '登录', 'awe.idea.com.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 23:20:16');
INSERT INTO `sys_log` VALUES ('108', 'system', '登录', 'awe.idea.com.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 23:20:23');
INSERT INTO `sys_log` VALUES ('109', 'system', '登录', 'awe.idea.com.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 23:20:46');
INSERT INTO `sys_log` VALUES ('110', 'system', '登录', 'awe.idea.com.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 23:22:17');
INSERT INTO `sys_log` VALUES ('111', 'system', '登录', 'awe.idea.com.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 23:28:39');
INSERT INTO `sys_log` VALUES ('112', 'system', '登录', 'awe.idea.com.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 23:30:33');
INSERT INTO `sys_log` VALUES ('113', 'system', '登录', 'awe.idea.com.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 23:34:48');
INSERT INTO `sys_log` VALUES ('114', 'system', '登录', 'awe.idea.com.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 23:36:52');
INSERT INTO `sys_log` VALUES ('115', 'system', '登录', 'awe.idea.com.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 23:46:59');
INSERT INTO `sys_log` VALUES ('116', 'system', '登录', 'awe.idea.com.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-16 23:55:06');
INSERT INTO `sys_log` VALUES ('117', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-17 00:02:02');
INSERT INTO `sys_log` VALUES ('118', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-17 00:05:58');
INSERT INTO `sys_log` VALUES ('119', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-17 00:10:31');
INSERT INTO `sys_log` VALUES ('120', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-17 15:48:56');
INSERT INTO `sys_log` VALUES ('121', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-17 15:54:30');
INSERT INTO `sys_log` VALUES ('122', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-17 15:58:04');
INSERT INTO `sys_log` VALUES ('123', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-17 16:00:42');
INSERT INTO `sys_log` VALUES ('124', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-22 12:17:52');
INSERT INTO `sys_log` VALUES ('125', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-25 16:52:48');
INSERT INTO `sys_log` VALUES ('126', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-25 16:58:00');
INSERT INTO `sys_log` VALUES ('127', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-25 17:04:20');
INSERT INTO `sys_log` VALUES ('128', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-25 17:11:10');
INSERT INTO `sys_log` VALUES ('129', 'admin', '修改密码', 'awe.idea.com.admin.controller.SysUserController.password()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-25 17:21:46');
INSERT INTO `sys_log` VALUES ('130', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-25 17:21:56');
INSERT INTO `sys_log` VALUES ('131', 'admin', '退出登录', 'awe.idea.com.admin.controller.SysLoginController.logout()', null, '0:0:0:0:0:0:0:1', '2020-10-25 17:22:02');
INSERT INTO `sys_log` VALUES ('132', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"root\"', '0:0:0:0:0:0:0:1', '2020-10-25 17:22:09');
INSERT INTO `sys_log` VALUES ('133', 'root', '修改密码', 'awe.idea.com.admin.controller.SysUserController.password()', '\"root\"', '0:0:0:0:0:0:0:1', '2020-10-25 17:22:17');
INSERT INTO `sys_log` VALUES ('134', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"root\"', '0:0:0:0:0:0:0:1', '2020-10-25 17:22:29');
INSERT INTO `sys_log` VALUES ('135', 'system', '登录', 'awe.idea.com.admin.controller.SysLoginController.login()', '\"admin\"', '0:0:0:0:0:0:0:1', '2020-10-25 21:14:27');

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(50) DEFAULT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `perms` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) DEFAULT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=82 DEFAULT CHARSET=utf8 COMMENT='菜单管理';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('1', '0', '系统管理', null, null, '0', 'fa fa-cog', '0');
INSERT INTO `sys_menu` VALUES ('36', '1', '定时任务', 'sys/schedule.html', null, '1', 'fa fa-tasks', '6');
INSERT INTO `sys_menu` VALUES ('37', '36', '查看', null, 'schedulejob:list,schedulejob:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('38', '36', '新增', null, 'schedulejob:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('39', '36', '修改', null, 'schedulejob:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('40', '36', '删除', null, 'schedulejob:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('41', '1', '定时任务日志', 'sys/schedule_log.html', null, '1', 'fa fa-tasks', '6');
INSERT INTO `sys_menu` VALUES ('42', '41', '查看', null, 'schedulejoblog:list,schedulejoblog:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('43', '41', '新增', null, 'schedulejoblog:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('44', '41', '修改', null, 'schedulejoblog:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('45', '41', '删除', null, 'schedulejoblog:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('46', '1', '系统日志', 'sys/log.html', null, '1', 'fa fa-file-text-o', '6');
INSERT INTO `sys_menu` VALUES ('47', '46', '查看', null, 'syslog:list,syslog:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('48', '46', '新增', null, 'syslog:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('49', '46', '修改', null, 'syslog:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('50', '46', '删除', null, 'syslog:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('51', '1', '菜单管理', 'sys/menu.html', null, '1', 'fa fa-th-list', '6');
INSERT INTO `sys_menu` VALUES ('52', '51', '查看', null, 'sysmenu:list,sysmenu:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('53', '51', '新增', null, 'sysmenu:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('54', '51', '修改', null, 'sysmenu:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('55', '51', '删除', null, 'sysmenu:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('56', '1', '角色', 'sys/role.html', null, '1', 'fa fa-user-secret', '6');
INSERT INTO `sys_menu` VALUES ('57', '56', '查看', null, 'sysrole:list,sysrole:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('58', '56', '新增', null, 'sysrole:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('59', '56', '修改', null, 'sysrole:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('60', '56', '删除', null, 'sysrole:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('66', '1', '系统用户', 'sys/user.html', null, '1', 'fa fa-user', '6');
INSERT INTO `sys_menu` VALUES ('67', '66', '查看', null, 'sysuser:list,sysuser:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('68', '66', '新增', null, 'sysuser:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('69', '66', '修改', null, 'sysuser:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('70', '66', '删除', null, 'sysuser:delete', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('71', '36', '执行', null, 'schedulejob:run', '2', null, '7');
INSERT INTO `sys_menu` VALUES ('72', '36', '删除', null, 'schedulejob:delete', '2', null, '8');
INSERT INTO `sys_menu` VALUES ('73', '36', '暂停', null, 'schedulejob:pause', '2', null, '9');
INSERT INTO `sys_menu` VALUES ('74', '36', '恢复', null, 'schedulejob:resume', '2', null, '10');
INSERT INTO `sys_menu` VALUES ('75', '51', '授权', null, 'sysmenu:select,sysmenu:perms', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('76', '1', 'SQL监控', 'druid/sql.html', 'sql:list', '1', 'fa fa-bug', '4');
INSERT INTO `sys_menu` VALUES ('77', '1', '系统参数配置', 'sys/config.html', null, '1', 'fa fa-bookmark-o', '6');
INSERT INTO `sys_menu` VALUES ('78', '77', '查看', null, 'sysconfig:list,sysconfig:info', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('79', '77', '新增', null, 'sysconfig:save', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('80', '77', '修改', null, 'sysconfig:update', '2', null, '6');
INSERT INTO `sys_menu` VALUES ('81', '77', '删除', null, 'sysconfig:delete', '2', null, '6');

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(100) DEFAULT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8 COMMENT='角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', 'manger', '', '1', null);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `menu_id` bigint(20) DEFAULT NULL COMMENT '菜单ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=526 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES ('504', '1', '1');
INSERT INTO `sys_role_menu` VALUES ('505', '1', '76');
INSERT INTO `sys_role_menu` VALUES ('506', '1', '41');
INSERT INTO `sys_role_menu` VALUES ('507', '1', '45');
INSERT INTO `sys_role_menu` VALUES ('508', '1', '44');
INSERT INTO `sys_role_menu` VALUES ('509', '1', '43');
INSERT INTO `sys_role_menu` VALUES ('510', '1', '42');
INSERT INTO `sys_role_menu` VALUES ('511', '1', '36');
INSERT INTO `sys_role_menu` VALUES ('512', '1', '74');
INSERT INTO `sys_role_menu` VALUES ('513', '1', '73');
INSERT INTO `sys_role_menu` VALUES ('514', '1', '72');
INSERT INTO `sys_role_menu` VALUES ('515', '1', '71');
INSERT INTO `sys_role_menu` VALUES ('516', '1', '40');
INSERT INTO `sys_role_menu` VALUES ('517', '1', '39');
INSERT INTO `sys_role_menu` VALUES ('518', '1', '38');
INSERT INTO `sys_role_menu` VALUES ('519', '1', '37');

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `password` varchar(100) DEFAULT NULL COMMENT '密码',
  `email` varchar(100) DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态  0：禁用   1：正常',
  `create_user_id` bigint(20) DEFAULT NULL COMMENT '创建者ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'root@renren.io', '13612345678', '1', null, '2016-11-11 11:11:11');
INSERT INTO `sys_user` VALUES ('2', 'root', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'root@renren.io', '13612345678', '1', null, '2016-11-11 11:11:11');

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户与角色对应关系';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('8', '2', '1');

-- ----------------------------
-- Table structure for tb_account
-- ----------------------------
DROP TABLE IF EXISTS `tb_account`;
CREATE TABLE `tb_account` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `user_id` bigint(20) NOT NULL COMMENT '系统对应的用户id',
  `account_name` varchar(50) COLLATE utf8_bin NOT NULL COMMENT '账户名称',
  `account_type` int(11) NOT NULL COMMENT '账户类型（1.管理员 2.商务 3站长 4客户）',
  `parent_account_id` bigint(20) DEFAULT NULL COMMENT '所属上级账户id',
  `account_amount` double(18,4) NOT NULL COMMENT '账户当前所有的金额',
  `account_status` int(11) NOT NULL COMMENT '账户状态',
  `address` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '用户的公司地址或者家庭地址',
  `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- ----------------------------
-- Records of tb_account
-- ----------------------------
INSERT INTO `tb_account` VALUES ('1', '1', 'ivan', '1', null, '99.2100', '1', null, '2020-10-07 15:32:55', '2020-10-07 15:32:55');

-- ----------------------------
-- Table structure for tb_token
-- ----------------------------
DROP TABLE IF EXISTS `tb_token`;
CREATE TABLE `tb_token` (
  `user_id` bigint(20) NOT NULL,
  `token` varchar(100) NOT NULL COMMENT 'token',
  `expire_time` datetime DEFAULT NULL COMMENT '过期时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `token` (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户Token';

-- ----------------------------
-- Records of tb_token
-- ----------------------------

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL COMMENT '用户名',
  `mobile` varchar(20) NOT NULL COMMENT '手机号',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户';

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('1', 'mark', '13612345678', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', '2017-03-23 22:37:41');
