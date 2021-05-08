CREATE TABLE `dev_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '项目ID',
  `name` varchar(32) NOT NULL COMMENT '项目名',
  `owner_id` varchar(32) NOT NULL COMMENT '项目所属人',
  `base_package` varchar(1024) DEFAULT '' COMMENT '项目的包',
  `root_path` varchar(1024) DEFAULT '' COMMENT '生成文件的存放位置',
  `front_path` varchar(1024) DEFAULT '' COMMENT '前端代码生成目录',
  `temp_path` varchar(1024) DEFAULT '' COMMENT '代码模板',
  `overlay_gen` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否覆盖',
  `single` tinyint(1) NOT NULL DEFAULT 0 COMMENT '工程结构',
  `need_fun` varchar(1024) DEFAULT '' COMMENT '需要功能',
  `url` varchar(1024) DEFAULT '' COMMENT '前端代码生成目录',
  `db_type` varchar(128) DEFAULT NULL COMMENT '数据库类型',
  `username` varchar(128) DEFAULT NULL COMMENT '连接用户名',
  `password` varchar(128) DEFAULT NULL COMMENT '连接密码',
  `memo` varchar(128) DEFAULT NULL COMMENT '备注',
  `ext_map` varchar(1024) DEFAULT NULL COMMENT '扩展信息',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='开发项目';

CREATE TABLE `dev_table_mate` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `table_name` varchar(32) NOT NULL COMMENT '表名',
  `project_id` varchar(32) NOT NULL COMMENT '项目ID',
  `ext_map` text COMMENT '扩展信息',
  `gmt_create` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `gmt_modified` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `table_name` (`table_name`,`project_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='表生成代码信息';
