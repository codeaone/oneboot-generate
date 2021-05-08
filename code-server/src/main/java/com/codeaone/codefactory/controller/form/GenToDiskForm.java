package com.codeaone.codefactory.controller.form;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class GenToDiskForm {

	@NotBlank
	private String id;
	@NotBlank
	private String table;

	/** 是否覆盖生成代码 */
	@NotBlank
	private Boolean overlayGen;

	/**
	 * 需要功能
	 */
	@NotBlank
	private String needFun;

}
