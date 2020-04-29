CREATE TABLE `t_user` (
  `id` varchar(40) NOT NULL,
  `username` varchar(255) DEFAULT NULL COMMENT '用户名（登录名）',
  `displayName` varchar(255) DEFAULT NULL COMMENT '昵称（界面显示名称）',
  `password` varchar(40) NOT NULL,
  `phoneNumber` varchar(40) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT 1 COMMENT '预留',
  `sysRole` int(11) DEFAULT 2 COMMENT '在系统中的角色（非业务角色，类似于管理员之类的，业务角色其他表多对多关联）1-管理员 2-用户',
  `lastLoginTime` datetime DEFAULT NULL,
  `loginFailedTimes` int(11) DEFAULT NULL COMMENT '登录失败次数（两次登录间隔小于某个值，且失败次数大于0就需要验证码）',
  `createTime` datetime DEFAULT NULL,
  `updateTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`userName`) USING HASH
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';


CREATE TABLE `t_verification_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` varchar(40) DEFAULT NULL COMMENT '所属用户',
  `value` varchar(40) NOT NULL COMMENT '验证码值',
  `type` tinyint(4) NOT NULL COMMENT '验证码类型（用于不同功能分类）',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  `expireTime` datetime NOT NULL COMMENT '验证码失效时间',
  PRIMARY KEY (`id`),
  KEY `idx_user_type` (`userId`,`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='验证码表';

-- 其他表略