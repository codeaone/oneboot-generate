package com.codeaone.codefactory.config;

import org.oneboot.core.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义转换器 去掉前后空格
 * 
 * @author shiqiao.pro
 * @see https://boot.codeaone.com
 */
@Slf4j
public class ArrayToStringConverter implements Converter<String[], String> {

	@Override
	public String convert(String[] source) {
		try {
			if (source != null) {
				return StringUtils.join(source, ",");
			}
		} catch (Exception e) {
			log.error("BlankSpaceConverter:", e);
		}
		return null;
	}
}
