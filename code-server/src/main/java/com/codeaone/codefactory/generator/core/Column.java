package com.codeaone.codefactory.generator.core;

import org.oneboot.core.lang.StringUtils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Column implements Comparable<Column> {

	/** 对应的 java 类型 */
	private String javaType;

	/** 对应的 java 类型: String */
	private String javaShortType;

	/** 备注信息 */
	private String remarks;

	/** 对应 java name */
	private String javaName;

	/** 对应表中的列名 */
	private String jdbcName;

	/** DB的字段类型：varchar(32) **/
	private String jdbcType;

	/** DB的字段类型：VARCHAR 没有长度 **/
	private String jdbcTypeName;

	/** 默认值 **/
	private String defaultd;

	/** 是否可以为空 **/
	private boolean nulld = false;

	/** 是否为关键字 **/
	private boolean keyword;

	/** 是否为主键 **/
	private boolean keyFlag;

	/** 长度 **/
	private int length;

	/** 是否为自增字段 **/
	private boolean autoIncrement = false;

	@Override
	public int compareTo(Column o) {
		return this.javaName.compareTo(o.javaName);
	}

	public String getNullName() {
		return nulld ? "是" : "否";
	}
	
	/** 页面操作需要过滤掉的字段 */// "creator"
    private static String[] noAddOptKey = new String[] { "gmtModified", /* "gmtCreate", */ "extMap", "updater",
            "creator", "tenantId", "tntInstId", "instId", "createTime", "updateTime", "createId", "createName", "modifyId",
            "modifyName" };

	/**
     * 是否需要增加Opt注解信息
     * 
     * @return
     */
    public boolean getAddOpt() {
        for (String string : noAddOptKey) {
            if (StringUtils.equals(string, this.javaName)) {
                return false;
            }
        }
        return true;
    }
}
