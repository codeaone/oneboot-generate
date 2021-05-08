package com.codeaone.codefactory.controller;

import java.sql.Connection;

import org.oneboot.core.mvc.ObootBaseController;

import com.codeaone.codefactory.entity.DevProject;
import com.codeaone.codefactory.enums.DbTypeEnum;
import com.codeaone.codefactory.generator.config.DataSourceConfig;

public class BaseController extends ObootBaseController {

	protected Connection getConnection(DevProject p) {
		DataSourceConfig config = new DataSourceConfig();
		config.setDbType(DbTypeEnum.getByCode(p.getDbType()));
		config.setUrl(p.getUrl()).setUsername(p.getUsername()).setPassword(p.getPassword());
		return config.getConn();
	}
}
