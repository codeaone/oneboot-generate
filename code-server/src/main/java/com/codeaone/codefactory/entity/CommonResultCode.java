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
package com.codeaone.codefactory.entity;

import org.apache.commons.lang3.StringUtils;
import org.oneboot.core.error.ErrorLevel;
import org.oneboot.core.error.ErrorType;
import org.oneboot.core.exception.IErrorCode;

/**
 * 通用结果码<br/>
 */
public enum CommonResultCode implements IErrorCode {

	/** 处理成功 */
	SUCCESS("001", "处理成功", "处理成功", ErrorLevel.INFO, ErrorType.BIZ),

	/** 未知异常 */
	UNKNOWN_ERROR("002", "未知异常", "抱歉，出错了，小二紧急处理中", ErrorLevel.WARN, ErrorType.BIZ),

	/** 非法参数 */
	ILLEGAL_PARAMETERS("003", "非法参数", "请检查下你输入的参数是否正确哦~", ErrorLevel.WARN, ErrorType.BIZ),

	/** 系统错误 */
	SYSTEM_ERROR("004", "系统错误", "抱歉，出错了，小二紧急处理中", ErrorLevel.ERROR, ErrorType.SYSTEM),

	/** 重复提交错误 */
	REPEATED_SUBMIT("005", "重复提交", "请勿重复提交", ErrorLevel.WARN, ErrorType.BIZ),

	/** 服务调用异常 */
	RPC_ERROR("010", "服务调用异常", "服务调用异常", ErrorLevel.ERROR, ErrorType.SYSTEM),

	/** 验证码过期 */
	SMSCODE_EXPIRED("011", "SMSCODE_EXPIRED", "验证码过期 ", ErrorLevel.ERROR, ErrorType.SYSTEM),

	/** 数据查询失败 */
	DATA_SELECT_FAIL("012", "DATA_SELECT_FAIL", "数据查询失败 ", ErrorLevel.ERROR, ErrorType.SYSTEM),

	/** 数据检查失败 */
	DATA_CHCEK_FAIL("013", "DATA_CHCEK_FAIL", "数据检查失败 ", ErrorLevel.ERROR, ErrorType.BIZ),

	/** 数据被占用 */
	DATA_CHCEK_EXIST("01４", "DATA_CHCEK_EXIST", "数据被占用 ", ErrorLevel.ERROR, ErrorType.BIZ),

	SESSION_EXPIRATION("401", "您的会话已过期，需要重新登录！", "您的会话已过期，需要重新登录！", ErrorLevel.WARN, ErrorType.BIZ),

	/** session过期 */
	SESSION_EXPIRED("033", "SESSION_EXPIRED", "会话过期", ErrorLevel.INFO, ErrorType.BIZ),;

	/** 结果码 */
	private String code;

	/** 描述 */
	private String desc;

	/** 显示错误内容 */
	private String view;

	/** errorLevel */
	private String errorLevel;

	/** errorType */
	private String errorType;

	/**
	 * 构造函数
	 * 
	 * @param code
	 *            错误码
	 * @param desc
	 *            描述
	 * @param view
	 *            显示错误内容
	 */
	private CommonResultCode(String code, String desc, String view, String errorLevel, String errorType) {
		this.code = code;
		this.desc = desc;
		this.view = view;
		this.errorLevel = errorLevel;
		this.errorType = errorType;
	}

	/**
	 * 通过name获取结果码
	 * 
	 * @param code
	 *            错误码
	 * @return 返回业务结果码
	 */
	public static CommonResultCode getResultEnum(String code) {
		for (CommonResultCode result : values()) {
			if (StringUtils.equals(result.getCode(), code)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Getter method for property <tt>code</tt>.
	 * 
	 * @return property value of code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * Setter method for property <tt>code</tt>.
	 * 
	 * @param code
	 *            value to be assigned to property code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * Getter method for property <tt>desc</tt>.
	 * 
	 * @return property value of desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * Setter method for property <tt>desc</tt>.
	 * 
	 * @param desc
	 *            value to be assigned to property desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * Getter method for property <tt>view</tt>.
	 * 
	 * @return property value of view
	 */
	public String getView() {
		return view;
	}

	/**
	 * Setter method for property <tt>view</tt>.
	 * 
	 * @param view
	 *            value to be assigned to property view
	 */
	public void setView(String view) {
		this.view = view;
	}

	/**
	 * Getter method for property <tt>errorLevel</tt>.
	 * 
	 * @return property value of errorLevel
	 */
	public String getErrorLevel() {
		return errorLevel;
	}

	/**
	 * Setter method for property <tt>errorLevel</tt>.
	 * 
	 * @param errorLevel
	 *            value to be assigned to property errorLevel
	 */
	public void setErrorLevel(String errorLevel) {
		this.errorLevel = errorLevel;
	}

	/**
	 * Getter method for property <tt>errorType</tt>.
	 * 
	 * @return property value of errorType
	 */
	public String getErrorType() {
		return errorType;
	}

	/**
	 * Setter method for property <tt>errorType</tt>.
	 * 
	 * @param errorType
	 *            value to be assigned to property errorType
	 */
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

}
