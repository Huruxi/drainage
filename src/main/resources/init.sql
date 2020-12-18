
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
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '激活码',
  `status` int(4) DEFAULT 0 COMMENT '是否激活, 0 未激活，1 激活',
  `type_id` int(4) default 0 comment '激活码类型ID',
  `login_state` int(4) DEFAULT 0 COMMENT '激活登录状态',
  `online_time_today` bigint(19) DEFAULT 0 COMMENT '今日在线时长秒为单位',
  `online_time_total` bigint(19) DEFAULT 0 COMMENT '总计在线时长秒为单位',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
);


-- 激活码类型表
CREATE TABLE `t_activation_code_type` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '类型名称',
  `money` DECIMAL(9,2) DEFAULT NULL COMMENT '每天返利金额',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
);


-- 激活码登录日志
CREATE TABLE `t_activation_code_login_log` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `code` varchar(255) DEFAULT NULL COMMENT '类型名称',
  `online_time` int(4) DEFAULT 0 COMMENT '在线时间,以秒为单位',
  `login_state` int(4) DEFAULT 0 COMMENT '激活登录状态',
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
  `content` text DEFAULT NULL COMMENT '公告内容',
  `is_release` int default 0 COMMENT '是否发布公告',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
);


-- 商户信息
CREATE TABLE `t_merchant_info` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
);

-- 商家账户
CREATE TABLE `t_merchant_account` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL COMMENT '名称',
  `mobile` varchar(255) DEFAULT NULL COMMENT '号码',
  `account_number` varchar(255) DEFAULT NULL COMMENT '支付账号',
  `pay_type` int(4) DEFAULT 0 COMMENT '支付类型: 1 微信 2 支付宝 3 银行卡',
  `pay_status` int(4) DEFAULT 0 COMMENT '支付状态: 0 未支付 1 已支付',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
);


ALTER TABLE t_activation_code
ADD COLUMN
`online_time_total` bigint(19) DEFAULT 0 COMMENT '总计在线时长秒为单位' AFTER login_state;


create INDEX index_name on t_admin(`name`);