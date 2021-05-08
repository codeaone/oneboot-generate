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
package com.codeaone.codefactory.controller.form;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DevProjectForm {

	/** 项目名 */
	private String name;

	/** 项目所属人 */
	private String ownerId;

	/** 备注 */
	private String memo;

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

}
