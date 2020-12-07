DROP TABLE IF EXISTS t_admin;

-- CREATE TABLE user
-- (
-- 	id BIGINT(20) NOT NULL COMMENT '主键ID',
-- 	name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
-- 	age INT(11) NULL DEFAULT NULL COMMENT '年龄',
-- 	email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
-- 	PRIMARY KEY (id)
-- );


-- 管理员表
CREATE TABLE `t_admin`
(
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL COMMENT '用户名',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `add_time` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`)
);