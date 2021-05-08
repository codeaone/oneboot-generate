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

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.oneboot.core.logging.LoggerUtil;

import com.codeaone.codefactory.generator.config.CodeGenConfig;

/**
 * 
 * 
 * @author shiqiao.pro
 * @see https://boot.codeaone.com
 */
public class VelocityGen {

	public static void generator(VelocityContext ctx, String temp, String path, String fileName) {
		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		Template template = null;
		try {
			template = ve.getTemplate(temp, "UTF-8");
		} catch (Exception e) {
			LoggerUtil.error("error {0}", e.getMessage());
			return;
		}

		merge(template, ctx, path, fileName, false);
	}

	public static void generator(VelocityContext ctx, GeneratorModel gm, CodeGenConfig config) throws Exception {

		VelocityEngine ve = new VelocityEngine();
		ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		ve.init();
		Template template = null;
		try {
			template = ve.getTemplate(gm.getTemplateName(), "UTF-8");
		} catch (Exception e) {
			LoggerUtil.error("error {0}", e.getMessage());
			return;
		}

		ctx.put("pojoNameLower", getFirstLowCase(gm.getPojoName()));
		ctx.put("pojoName", gm.getPojoName());
		ctx.put("date", new Date());
		ctx.put("basepackage", gm.getPackageName());
		ctx.put("modelName", gm.getModelName());
		ctx.put("biz", gm.getBiz());
		ctx.put("impl", gm.getModelNameImpl());
		ctx.put("antdName", gm.getAntdUpName());
		ctx.put("antdUpName", gm.getAntdName());

		String packagePath = getPackageStr(gm.getPackageStr(), gm);

		String rootPath = config.getRootPath();

		if (gm.isCourseFilePath()) {
			rootPath = getCourseFilePath() + "\\";
		}

		if (StringUtils.isNoneBlank(gm.getCodeGenPath())) {
			//
			rootPath = gm.getCodeGenPath();
		}

		/*
		 * merge(template, ctx, rootPath + gm.getRootPath() + packagePath,
		 * gm.getFileName());
		 */

		merge(template, ctx, rootPath + gm.getRootPath() + packagePath, gm.getFileName(), config.getOverlayGen());
	}

	public static String getCourseFilePath() {
		File directory = new File("");// 参数为空
		String courseFile = "";
		try {
			courseFile = directory.getCanonicalPath();
		} catch (IOException e) {
		}
		return courseFile;
	}

	public static String getPackageStr(String packageName, GeneratorModel gm) {
		if (gm.isResources()) {
			if (gm.getResourcesPath().contains("webapp")) {
				return gm.getResourcesPath() + gm.getModelName() + "/" + getFirstLowCase(gm.getPojoName()) + "/";
			} else {

				return gm.getResourcesPath();
			}
		} else {
			return "/java/" + packageName.replaceAll("\\.", "\\\\") + "/";
		}
	}

	public static void merge(Template template, VelocityContext ctx, String path, String fileName,
			boolean isOverlayGen) {
		path = path.replace("\\", "/");

		File filePath = new File(path);
		if (!filePath.exists()) {
			filePath.mkdirs();
		}
		String filePathstr = path + fileName;
		// 在这里，需要把 \ 转成 / 方便在不同系统中使用
		filePathstr = filePathstr.replace("\\", "/");
		File file = new File(filePathstr);
		if (file.exists()) {
			if (!isOverlayGen) {
				System.out.println("存在文件，不生成：" + filePathstr);
				return;
			}

		}
		System.out.println("生成文件：" + filePathstr);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(filePathstr, "UTF-8");
			template.merge(ctx, writer);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			writer.close();
		}
	}

	/**
	 * 首字母小定
	 * 
	 * @param name
	 * @return
	 */
	public static String getFirstLowCase(String name) {
		String first = name.substring(0, 1).toLowerCase();
		String rest = name.substring(1, name.length());
		String newStr = new StringBuffer(first).append(rest).toString();
		return newStr;
	}

	/**
	 * 首字母大写
	 * 
	 * @param name
	 * @return
	 */
	public static String getFirstUpperCase(String name) {
		String first = name.substring(0, 1).toUpperCase();
		String rest = name.substring(1, name.length());
		String newStr = new StringBuffer(first).append(rest).toString();
		return newStr;
	}

}
