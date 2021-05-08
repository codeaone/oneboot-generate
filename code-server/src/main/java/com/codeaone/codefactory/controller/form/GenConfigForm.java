package com.codeaone.codefactory.controller.form;

import lombok.Data;

@Data
public class GenConfigForm {

	/** 需要生成的表名 */
    private String tableName;

    /**
	 * 是否覆盖
	 */
	private Boolean overlayGen;
	
	/**
	 * 需要功能
	 */
	private String needFun;

	/**
	 * 前端代码生成目录
	 */
	private String url;

	/**
	 * 数据库类型
	 */
	private String dbType;

	/**
	 * 连接用户名
	 */
	private String username;

	/**
	 * 连接密码
	 */
	private String password;
   
    
}
