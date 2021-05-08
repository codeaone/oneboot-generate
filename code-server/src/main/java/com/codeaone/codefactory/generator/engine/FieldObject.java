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

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author shiqiao.pro
 * @see https://boot.codeaone.com
 */
public class FieldObject {

	private String name;
	private String dbName;
	private String type;
	private String upName;
	private boolean autoIncrement = false;
	public boolean isKeyId;

	public boolean isAutoIncrement() {
		return autoIncrement;
	}

	public void setAutoIncrement(boolean autoIncrement) {
		this.autoIncrement = autoIncrement;
	}

	/**
	 * Getter method for property <tt>upName</tt>.
	 * 
	 * @return property value of upName
	 */
	public String getUpName() {
		if (StringUtils.isBlank(upName)) {
			return getFirstUpperCase(name);
		}
		return upName;
	}

	/**
	 * 首字母大写
	 * 
	 * @param name
	 * @return
	 */
	public String getFirstUpperCase(String name) {
		String first = name.substring(0, 1).toUpperCase();
		String rest = name.substring(1, name.length());
		String newStr = new StringBuffer(first).append(rest).toString();
		return newStr;
	}

	/**
	 * Setter method for property <tt>upName</tt>.
	 * 
	 * @param upName
	 *            value to be assigned to property upName
	 */
	public void setUpName(String upName) {
		this.upName = upName;
	}

	/**
	 * Getter method for property <tt>name</tt>.
	 * 
	 * @return property value of name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter method for property <tt>name</tt>.
	 * 
	 * @param name
	 *            value to be assigned to property name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for property <tt>dbName</tt>.
	 * 
	 * @return property value of dbName
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * Setter method for property <tt>dbName</tt>.
	 * 
	 * @param dbName
	 *            value to be assigned to property dbName
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * Getter method for property <tt>type</tt>.
	 * 
	 * @return property value of type
	 */
	public String getType() {
		return type;
	}

	/**
	 * Setter method for property <tt>type</tt>.
	 * 
	 * @param type
	 *            value to be assigned to property type
	 */
	public void setType(String type) {
		this.type = type;
	}

	public boolean isKeyId() {
		return this.isKeyId;
	}
}
