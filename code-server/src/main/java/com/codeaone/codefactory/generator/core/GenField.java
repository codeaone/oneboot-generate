package com.codeaone.codefactory.generator.core;

/**
 * 需要生成字段？？
 * 
 * @author shiqiao.pro
 * @see https://boot.codeaone.com
 */
public class GenField extends Column {
	/** 备注说明 **/
	public String desc;

	/** 是否显示查询字段 **/
	public boolean searcher;

	/** 是否模糊查询 **/
	public boolean searcherLike = false;

	/** 新增 **/
	public boolean add;

	/** 修改 **/
	public boolean update;

	/** 列表显示 **/
	public boolean listView;

	public boolean keyid = false;

	/** 前端操作控制类型 { @link FrontWidgetType} **/
	public String otype;

	public boolean enums = false;

	public String enumName;

	public String enumSimpleName;
}
