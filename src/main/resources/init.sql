
-- 管理员表
CREATE TABLE `t_admin` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
);


-- 激活码表
CREATE TABLE `t_activation_code` (
  `id` int(4) NOT NULL AUTO_INCREMENT,-
  `code` varchar(255) DEFAULT NULL COMMENT '激活码',
  `status` int(4) DEFAULT 0 COMMENT '是否激活, 0 未激活，1 激活',
  `type_id` int(4) default 0 comment '激活码类型ID',
  `login_state` int(4) DEFAULT 0 COMMENT '激活登录状态',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
);


-- 激活码类型表
CREATE TABLE `t_activation_code_type` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '类型名称',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
);


-- 激活码登录日志
CREATE TABLE `t_activation_code_login_log` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '类型名称',
  `online_time` int(4) DEFAULT 0 COMMENT '在线时间',
  `ip` varchar(255) DEFAULT NULL COMMENT '登录IP',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
);



-- 返利表 rebateForm
CREATE TABLE `t_rebate_form` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '激活码',
  `money` decimal(11,2) DEFAULT 0 COMMENT '返利金额',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
);


-- 公告表 placard
CREATE TABLE `t_placard` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `content` varchar(1000) DEFAULT NULL COMMENT '公告内容',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
);