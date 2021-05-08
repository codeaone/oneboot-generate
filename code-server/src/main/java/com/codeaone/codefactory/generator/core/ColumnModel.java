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
package com.codeaone.codefactory.generator.core;

import org.oneboot.core.logging.ToStringUtil;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ColumnModel extends Column {
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
	private boolean searcher = false;
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

	/** 表关联字段 */
	private String tableAssociateStr;

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ToStringUtil.reflectionToLogStringByFields(this);
	}

}
