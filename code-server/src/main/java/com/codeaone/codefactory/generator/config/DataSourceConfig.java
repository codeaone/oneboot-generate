package com.codeaone.codefactory.generator.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.oneboot.core.exception.CommonErrorCode;
import org.oneboot.core.exception.ObootException;
import org.oneboot.core.lang.StringUtils;

import com.codeaone.codefactory.enums.DbTypeEnum;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

/**
 * 数据库配置
 * 
 * @author shiqiao.pro
 * @see https://boot.codeaone.com
 */
@Slf4j
@Data
@Accessors(chain = true)
public class DataSourceConfig {

	/** 数据库类型 **/
	private DbTypeEnum dbType;

	/** 驱动连接的URL **/
	private String url;

	/** 驱动名称 **/
	private String driverName;

	/** 数据库连接用户名 **/
	private String username;

	/** 数据库连接密码 **/
	private String password;

	/**
	 * 创建数据库连接对象
	 *
	 * @return Connection
	 */
	public Connection getConn() {
		Connection conn;
		Properties props = new Properties();
		try {
			if (StringUtils.isBlank(driverName) && dbType != null) {
				driverName = dbType.getDescription();
			}
			Class.forName(driverName);
			props.setProperty("user", username);
			props.setProperty("password", password);
			props.setProperty("remarks", "true"); // 设置可以获取remarks信息
			props.setProperty("useInformationSchema", "true");// 设置可以获取tables
			
			conn = DriverManager.getConnection(url, props);
		} catch (ClassNotFoundException | SQLException e) {
			log.error("DB 连接失败", e);
			throw new ObootException(CommonErrorCode.SYSTEM_ERROR, "数据库连接失败，请检查配置！", e);
		}
		return conn;
	}
}
