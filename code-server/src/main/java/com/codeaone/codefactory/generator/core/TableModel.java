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

import java.util.ArrayList;
import java.util.List;

import org.oneboot.core.logging.ToString;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TableModel extends ToString {

	/** serialVersionUID */
	private static final long serialVersionUID = 9030161133750893893L;
	/** 要生成的model name */
	private String name;
	/** 对应DB的表名字 */
	private String tableName;

	/** 数据的备注 */
	private String remark;

	/** 业务模块 */
	private String bizModel;

	/** 所有的字段 */
	private List<ColumnModel> allColumns = new ArrayList<ColumnModel>();

	/** 是否做增加操作 */
	private boolean add = true;
	/** 是否做修改操作 */
	private boolean update = true;
	/** 是否做删除操作 */
	private boolean delete = true;
	/** 是否显示详情 */
	private boolean detail = false;

	public void addColumn(ColumnModel cm) {
		this.allColumns.add(cm);
	}

}
