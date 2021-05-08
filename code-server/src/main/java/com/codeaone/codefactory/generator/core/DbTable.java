package com.codeaone.codefactory.generator.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据库对应的Table对像
 * 
 * @author shiqiao.pro
 * @see https://boot.codeaone.com
 */
@Setter
@Getter
public class DbTable implements Comparable<DbTable> {
	private String id;

	/** 要生成的 model name */
	private String javaName;

	/** 对应 DB 的表名字 */
	private String tableName;

	/** 数据的备注 */
	private String remark;

	/** 所有的字段 */
	private List<Column> allColumns;

	/** 主键字段 */
	private List<Column> primaryKeyColumns;
	/** 基本字段 */
	private List<Column> baseColumns;
	/** 大字段 */
	private List<Column> blobColumns;

	/** 需要引入的包名 **/
	private final Set<String> importPackages = new HashSet<>();

	public void addBaseColumns(List<Column> list) {
		if (baseColumns == null) {
			baseColumns = new ArrayList<Column>();
		}
		this.baseColumns.addAll(list);
	}

	/**
	 * 在model 增加的字段，需要过滤一些
	 * 
	 * @return
	 */
	public List<Column> getModelColumns() {
		List<Column> list = new ArrayList<>();
		for (Column column : allColumns) {
			if (column.getAddOpt()) {
				list.add(column);
			}
		}
		return list;
	}

	@Override
	public int compareTo(DbTable o) {
		return this.javaName.compareTo(o.javaName);
	}
}
