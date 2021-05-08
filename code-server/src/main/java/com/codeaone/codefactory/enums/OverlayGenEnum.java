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
package com.codeaone.codefactory.enums;

import org.oneboot.core.enums.IEnum;
import org.oneboot.core.lang.StringUtils;

/**
 * 枚举类
 * 
 * @author shiqiao.pro
 * @see https://boot.codeaone.com
 */
public enum OverlayGenEnum implements IEnum {

	/** 覆盖代码 **/
	TRUE("true", "覆盖代码"),

	/** 跳过生成 **/
	FALSE("false", "跳过生成"),

	;

	/** 代码 */
	private final String code;

	/** 名称 */
	private final String name;

	/** 描述 */
	private final String description;

	private OverlayGenEnum(String code, String name) {
		this.code = code;
		this.name = name;
		this.description = name;
	}

	private OverlayGenEnum(String code, String name, String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}

	/**
	 * 根据编码查询枚举。
	 * 
	 * @param code
	 *            编码。
	 * @return 枚举。
	 */
	public static OverlayGenEnum getByCode(String code) {
		for (OverlayGenEnum value : OverlayGenEnum.values()) {
			if (StringUtils.equals(code, value.getCode())) {
				return value;
			}
		}
		return null;
	}

	/**
	 * 根据中文名称查询枚举。
	 * 
	 * @param name
	 *            中文名称。
	 * @return 枚举。
	 */
	public static OverlayGenEnum getByName(String name) {
		for (OverlayGenEnum value : OverlayGenEnum.values()) {
			if (StringUtils.equals(name, value.getName())) {
				return value;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param code
	 * @return
	 */
	public static String getNameByCode(String code) {
		OverlayGenEnum ee = getByCode(code);
		if (ee != null) {
			return ee.getName();
		}
		return code;
	}

	@Override
	public String getCode() {
		return this.code;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

}
