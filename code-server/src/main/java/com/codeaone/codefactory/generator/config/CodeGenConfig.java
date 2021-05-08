package com.codeaone.codefactory.generator.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 代码生成配置
 * 
 * @author shiqiao.pro
 * @see https://boot.codeaone.com
 */
@Data
@Accessors(chain = true)
public class CodeGenConfig {

	/** 项目的包 */
	private String basePackage;

	/** 生成文件的存放位置 */
	private String rootPath;

	/** front end 前端代码生成目录，如果为空，则使用rootPath */
	private String frontPath;

	/** 根据类来反生成 CRUD 的代码，package 是实体的存放处 */
	private String pojoPackage;

	/** 模板位置 **/
	private String tempPath;

	/** 是否覆盖生成代码 */
	private Boolean overlayGen;

	/** 是否为单工程 */
	private Boolean single = false;

	/** 模块名 */
	private String modelName;

}
