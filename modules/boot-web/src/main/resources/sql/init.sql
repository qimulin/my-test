-- mybatis测的表
CREATE TABLE `user_info` (
`userid` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
`userno` varchar(50) NOT NULL COMMENT '用户名',
`username` varchar(64) NOT NULL COMMENT '显示名称',
`userage` int(11) NOT NULL COMMENT '部门id',
PRIMARY KEY (`userid`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `user_info`(`userid`, `userno`, `username`, `userage`) VALUES (1, 'zhangsan', '张三', 1);
INSERT INTO `user_info`(`userid`, `userno`, `username`, `userage`) VALUES (2, 'sunquan', '孙权', 1);

-- mybatis-plus测的表
-- 创建用户表
DROP TABLE IF EXISTS `user`;

CREATE TABLE `user`
(
  id BIGINT NOT NULL COMMENT '主键ID',
  name VARCHAR(30) NULL DEFAULT NULL COMMENT '姓名',
  age INT NULL DEFAULT NULL COMMENT '年龄',
  email VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
PRIMARY KEY (id)
);

-- 用户表添加测试数据
TRUNCATE TABLE `user`;

INSERT INTO `user` (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@baomidou.com'),
(2, 'Jack', 20, 'test2@baomidou.com'),
(3, 'Tom', 28, 'test3@baomidou.com'),
(4, 'Sandy', 21, 'test4@baomidou.com'),
(5, 'Billie', 24, 'test5@baomidou.com');