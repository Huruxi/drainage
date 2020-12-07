
-- 管理员表
CREATE TABLE `t_admin` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
)CHARSET=utf8mb4 COMMENT='管理员表';



-- 激活码表
CREATE TABLE `t_activation_code` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '激活码',
  `status` int DEFAULT 0 COMMENT '是否激活, 0 未激活，1 激活',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
)CHARSET=utf8mb4 COMMENT='激活码表';



-- 返利表 rebateForm
CREATE TABLE `t_rebate_form` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '激活码',
  `money` decimal(11,2) DEFAULT 0 COMMENT '返利金额',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
)CHARSET=utf8mb4 COMMENT='返利表';

