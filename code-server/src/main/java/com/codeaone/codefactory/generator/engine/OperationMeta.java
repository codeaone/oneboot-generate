/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.codeaone.codefactory.generator.engine;

import com.codeaone.codefactory.enums.FrontWidgetTypeEnum;

public class OperationMeta {

	/** 页面字段显示名称 */
	private String desc;
	/** 控件类型 */
	private String inputType;
	/** 枚举数据 */// List<EnumModel>
	private String enums;
	/** 枚举Java类名 */
	private String enumClassName;
	/** 校验类型 ：required， email， mobile 还要增加长度检查，这个由DB数据返回 */
	// List<String>
	private String ruleType;

	/** 是否查询 */
	private boolean searcher = true;
	/** 是否进行模糊查询 */
	private boolean searcherLike = false;
	/** 是否可增加操作 */
	private boolean add = true;
	/** 是否可修改操作 */
	private boolean update = true;
	/** 在列表页面是否显示该字段 */
	private boolean listView = true;
	/** 是否做重复检查 */
	private boolean exists = false;
	/** 修改是否显示 */
	private boolean modifyDisplay = true;
	/** 修改是否为Text */
	private boolean modifyText = false;
	/** 修改是否不显示 */
	private boolean modifyDisabled = false;

	private boolean autoIncrement = false;

	private String tableAssociateStr;

	public String getTableAssociateStr() {
		return tableAssociateStr;
	}

	public void setTableAssociateStr(String tableAssociateStr) {
		this.tableAssociateStr = tableAssociateStr;
	}

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	public boolean add() {
		return add;
	}

	public boolean update() {
		return update;
	}

	public boolean listView() {
		return listView;
	}

	public boolean searcher() {
		return searcher;
	}

	public boolean searcherLike() {
		return searcherLike;
	}

	public String desc() {
		return desc;
	}

	@SuppressWarnings("rawtypes")
	public Class enumClass() {
		return null;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getInputType() {
		return inputType;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public String getEnums() {
		return enums;
	}

	public void setEnums(String enums) {
		this.enums = enums;
	}

	public String getEnumClassName() {
		return enumClassName;
	}

	public void setEnumClassName(String enumClassName) {
		this.enumClassName = enumClassName;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public boolean isSearcher() {
		return searcher;
	}

	public void setSearcher(boolean searcher) {
		this.searcher = searcher;
	}

	public boolean isSearcherLike() {
		return searcherLike;
	}

	public void setSearcherLike(boolean searcherLike) {
		this.searcherLike = searcherLike;
	}

	public boolean isAdd() {
		return add;
	}

	public void setAdd(boolean add) {
		this.add = add;
	}

	public boolean isUpdate() {
		return update;
	}

	public void setUpdate(boolean update) {
		this.update = update;
	}

	public boolean isListView() {
		return listView;
	}

	public void setListView(boolean listView) {
		this.listView = listView;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

	public boolean isModifyDisplay() {
		return modifyDisplay;
	}

	public void setModifyDisplay(boolean modifyDisplay) {
		this.modifyDisplay = modifyDisplay;
	}

	public boolean isModifyText() {
		return modifyText;
	}

	public void setModifyText(boolean modifyText) {
		this.modifyText = modifyText;
	}

	public boolean isModifyDisabled() {
		return modifyDisabled;
	}

	public void setModifyDisabled(boolean modifyDisabled) {
		this.modifyDisabled = modifyDisabled;
	}

	public FrontWidgetTypeEnum[] type() {
		// TODO Auto-generated method stub
		return null;
	}

}
