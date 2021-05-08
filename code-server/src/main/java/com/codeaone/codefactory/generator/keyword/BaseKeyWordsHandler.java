package com.codeaone.codefactory.generator.keyword;

import java.util.List;
import java.util.Locale;

import com.codeaone.codefactory.generator.core.IKeyWordsHandler;

/**
 * 基类关键字处理
 * 
 * @author shiqiao.pro
 * @see https://boot.codeaone.com
 */
public abstract class BaseKeyWordsHandler implements IKeyWordsHandler {

	public List<String> keyWords;

	public BaseKeyWordsHandler(List<String> keyWords) {
		this.keyWords = keyWords;
	}

	@Override
	public List<String> getKeyWords() {
		return keyWords;
	}

	public boolean isKeyWords(String columnName) {
		return getKeyWords().contains(columnName.toUpperCase(Locale.ENGLISH));
	}
}
