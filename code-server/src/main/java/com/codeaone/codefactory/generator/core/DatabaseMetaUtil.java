package com.codeaone.codefactory.generator.core;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.JavaTypeResolver;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.Context;
import org.mybatis.generator.config.TableConfiguration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.ObjectFactory;
import org.mybatis.generator.internal.db.DatabaseIntrospector;
import org.oneboot.core.exception.CommonErrorCode;
import org.oneboot.core.exception.ObootException;
import org.oneboot.core.lang.StringUtils;
import org.oneboot.core.logging.LoggerUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 数据库元数据获取工具类
 * 
 * @author shiqiao.pro
 * @see https://boot.codeaone.com
 */
@Slf4j
public class DatabaseMetaUtil {

	public static List<DbTable> getTableNameByCon(Connection connection) {
		// TODO 这里的具体实现需要处理下

		List<DbTable> tables = new ArrayList<>();
		try {
			DatabaseMetaData meta = connection.getMetaData();
			ResultSet rs = meta.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				DbTable info = new DbTable();
				String name = rs.getString(3);
				String r = rs.getString("REMARKS");

				info.setTableName(name);
				info.setId(name);
				info.setRemark(r);

				/*
				 * try { if (StringUtils.equals(dbname, rs.getString(1))) {
				 * tables.add(info); } } catch (Exception e) { }
				 */
				tables.add(info);

			}
		} catch (Exception e) {
			log.error("读取表结构失败：", e);
			throw new ObootException(CommonErrorCode.SYSTEM_ERROR, "读取数据失败", e);
		}

		return tables;
	}

	public static List<DbTable> getTableMeta(List<String> tables, Connection connection) {
		try {
			SAXReader saxReader = new SAXReader();
			;

			// 方法1
			InputStream io = Thread.currentThread().getContextClassLoader().getResourceAsStream("generatorConfig.xml");

			// Document document =
			// saxReader.read(this.getClass().getClassLoader().getResourceAsStream("generatorConfig.xml"));
			// // 读取XML文件,获得document对象
			Document document = saxReader.read(io); // 读取XML文件,获得document对象
			// Document document =
			// saxReader.read(ResourceUtils.getFile("classpath:generatorConfig.xml"));
			// // 读取XML文件,获得document对象
			// System.out.println(document.asXML());
			LoggerUtil.info("init:{0}", document.asXML());
			Element root = document.getRootElement();
			Element context = root.element("context");

			for (String tab : tables) {
				Element bookElement = context.addElement("table");// 添加一个book节点

				bookElement.addAttribute("tableName", tab);// 添加属性内容
				bookElement.addAttribute("domainObjectName", underline2Camel(tab, false));//
				// 添加属性内容
			}

			LoggerUtil.info("new:{0}", document.asXML());

			InputStream is = new ByteArrayInputStream(document.asXML().getBytes("UTF-8"));

			return getTableMeta(is, connection);

		} catch (Exception ex) {
			LoggerUtil.error(ex, "唉呀，出错了{0}", tables);
		}
		return null;
	}

	public static List<DbTable> getTableMeta(Connection connection) {
		return getTableMeta(connection.getClass().getClassLoader().getResourceAsStream("generatorConfig.xml"),
				connection);
	}

	public static List<DbTable> getTableMeta(InputStream configXml, Connection connection) {
		List<DbTable> tbList = new ArrayList<DbTable>();
		try {
			List<String> warnings = new ArrayList<String>();
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(configXml);
			Context context = config.getContext("DB2Tables");

			JavaTypeResolver javaTypeResolver = ObjectFactory.createJavaTypeResolver(context, warnings);
			DatabaseMetaData metaData = connection.getMetaData();
			DatabaseIntrospector databaseIntrospector = new DatabaseIntrospector(context, metaData, javaTypeResolver,
					warnings);

			for (TableConfiguration tc : context.getTableConfigurations()) {
				List<IntrospectedTable> tables = databaseIntrospector.introspectTables(tc);

				for (IntrospectedTable table : tables) {
					DbTable tb = new DbTable();
					tb.setJavaName(tc.getDomainObjectName());
					tb.setTableName(table.getFullyQualifiedTable().getIntrospectedTableName());
					 tb.setBaseColumns(getColumn(table.getBaseColumns()));
					 tb.setPrimaryKeyColumns(getColumn(table.getPrimaryKeyColumns()));
					 tb.addBaseColumns(getColumn(table.getBLOBColumns()));
					tb.setAllColumns(getColumn(table.getAllColumns()));
					tbList.add(tb);
				}
			}

			for (DbTable table : tbList) {
				table.setRemark(getTableRemarks(connection, table.getTableName()));
			}

		} catch (Exception e) {
			LoggerUtil.error(e, "唉呀，出错了{0}", "");
		}
		return tbList;
	}

	private static List<Column> getColumn(List<IntrospectedColumn> list) {
		List<Column> columns = new ArrayList<Column>();
		for (IntrospectedColumn column : list) {
			Column c = new Column();
			c.setJavaType(column.getFullyQualifiedJavaType().getFullyQualifiedName());
			c.setJavaShortType(column.getFullyQualifiedJavaType().getShortName());
			c.setRemarks(column.getRemarks());
			c.setJavaName(column.getJavaProperty());
			c.setJdbcName(column.getActualColumnName());
			if ("BIGINT".equals(column.getJdbcTypeName())) {
				// 默认为自增吧
				c.setAutoIncrement(true);
			}
			c.setJdbcTypeName(column.getJdbcTypeName());
			c.setLength(column.getLength());
			columns.add(c);

		}

		return columns;
	}

	private static String getTableRemarks(Connection connection, String tableName) throws SQLException {
		DatabaseMetaData metaData = connection.getMetaData();
		ResultSet resultSet = metaData.getTables(null, null, tableName, new String[] { "TABLE" });
		if (resultSet.next()) {
			String r = resultSet.getString("REMARKS");
			return r;
		}
		return "";
	}

	public static List<DbTable> mergeTableMeta(Connection connection, List<DbTable> tables) {
		if (tables == null) {
			return null;
		}
		tables.forEach(table -> {
			DbTable dbTable = getTableDBMeta(connection, table.getTableName());
			if (dbTable != null) {
				table.getAllColumns().forEach(col -> {
					dbTable.getAllColumns().forEach(newCol -> {
						if (StringUtils.equals(col.getJdbcName(), newCol.getJdbcName())) {
							col.setDefaultd(newCol.getDefaultd());
							col.setJdbcType(newCol.getJdbcType());
							col.setNulld(newCol.isNulld());

						}
					});
				});
			}
		});
		return tables;
	}

	public static DbTable getTableDBMeta(Connection connection, String table) {
		DbTable dbTable = new DbTable();
		String sql = "show full fields from `%s`";
		String tableSql = String.format(sql, table);
		List<Column> allColumn = new ArrayList<>();
		dbTable.setAllColumns(allColumn);
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(tableSql);
			ResultSet results = preparedStatement.executeQuery();

			while (results.next()) {
				String columnName = results.getString("FIELD");
				String type = results.getString("type");
				String defaultd = results.getString("default");
				String nulld = results.getString("null");
				// String Comment = results.getString("Comment");

				Column col = new Column();
				col.setJdbcName(columnName);
				col.setJdbcType(type);
				col.setDefaultd(defaultd);
				if ("NO".equals(nulld)) {
					col.setNulld(true);
				}

				allColumn.add(col);

			}
		} catch (Exception e) {
		}
		return dbTable;
	}

	/**
	 * 下划线转驼峰法
	 * 
	 * @param line
	 *            源字符串
	 * @param smallCamel
	 *            大小驼峰,是否为小驼峰
	 * @return 转换后的字符串
	 */
	public static String underline2Camel(String line, boolean smallCamel) {
		if (line == null || "".equals(line)) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		Pattern pattern = Pattern.compile("([A-Za-z\\d]+)(_)?");
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			String word = matcher.group();
			sb.append(smallCamel && matcher.start() == 0 ? Character.toLowerCase(word.charAt(0))
					: Character.toUpperCase(word.charAt(0)));
			int index = word.lastIndexOf('_');
			if (index > 0) {
				sb.append(word.substring(1, index).toLowerCase());
			} else {
				sb.append(word.substring(1).toLowerCase());
			}
		}
		return sb.toString();
	}
}
