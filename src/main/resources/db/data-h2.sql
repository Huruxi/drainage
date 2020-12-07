-- DELETE FROM user;
--
-- INSERT INTO user (id, name, age, email) VALUES
-- (1, 'Jone', 18, 'test1@baomidou.com'),
-- (2, 'Jack', 20, 'test2@baomidou.com'),
-- (3, 'Tom', 28, 'test3@baomidou.com'),
-- (4, 'Sandy', 21, 'test4@baomidou.com'),
-- (5, 'Billie', 24, 'test5@baomidou.com');


DELETE FROM t_admin;

INSERT INTO t_admin (id, name, password, update_time,add_time) VALUES
(1, 'admin', '123456', now(),now()),
(2, 'Jack', '123456', now(),now()),
(3, 'Tom', '123456', now(),now()),
(4, 'Sandy', '123456', now(),now()),
(5, 'Billie', '123456', now(),now());

