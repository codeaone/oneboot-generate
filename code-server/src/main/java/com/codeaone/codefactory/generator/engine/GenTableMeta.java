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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.oneboot.core.logging.LoggerUtil;
import org.oneboot.core.logging.ToString;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.codeaone.codefactory.enums.FrontWidgetTypeEnum;
import com.codeaone.codefactory.generator.core.Column;
import com.codeaone.codefactory.generator.core.DbTable;

/**
 * 
 * @author tushiqiao
 * @version $Id: GenTableMeta.java, v 0.1 2018年4月5日 下午9:31:23 tushiqiao Exp $
 */
public class GenTableMeta extends ToString {
    private static final long serialVersionUID = -8339197176483604664L;
    /** 需要修改模块名 */
    private String modelName;
    /** 需要生成的实体bean */
    private String pojoName;

    /** 业务模块名 */
    private String bizModel;
    /** remark */
    private String remark;

    /** 项目的包 */
    private String basepackage;

    private boolean subTable = false;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**  */
    private DbTable table;

    private Map<String, OperationMeta> defops = new HashMap<String, OperationMeta>();
    private Map<String, OperationMeta> andops = new HashMap<String, OperationMeta>();

    /** 是否做增加操作 */
    private boolean add = true;
    /** 是否做修改操作 */
    private boolean update = true;
    /** 是否做删除操作 */
    private boolean delete = true;
    /** 是否显示详情 */
    private boolean detail = false;

    public boolean isSubTable() {
        return subTable;
    }

    public void setSubTable(boolean subTable) {
        this.subTable = subTable;
    }

    public void addDefops(String key, OperationMeta om) {
        this.defops.put(key, om);
    }

    public void addAndops(String key, OperationMeta om) {
        this.andops.put(key, om);
    }

    public String getBasepackage() {
        return basepackage;
    }

    public void setBasepackage(String basepackage) {
        this.basepackage = basepackage;
    }

    public boolean isAdd() {
        return add;
    }

    public void setAdd(boolean add) {
        this.add = add;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isDetail() {
        return detail;
    }

    public void setDetail(boolean detail) {
        this.detail = detail;
    }

    public GenTableMeta(String modelName, String pojoName) {
        super();
        this.modelName = modelName;
        this.pojoName = pojoName;
    }

    public GenTableMeta() {
    }

    public boolean getAddOrUpdateOrDetailOrDelete() {

        LoggerUtil.info("add={0} update={1} detail={2} delete={3}", add, update, detail, delete);
        if (add || update || detail || delete) {
            return true;
        }
        return false;
    }

    public void setTable(DbTable table) {
        // 增加默认配置信息
        this.table = table;
        for (Column column : this.table.getBaseColumns()) {
            if (column.getAddOpt()) {
                OperationMeta defop = new OperationMeta();
                // defop.
                defops.put(column.getJdbcName(), defop);
                andops.put(column.getJdbcName(), defop);
            }
        }
        /**
         * @Column(name = "${op.jdbcName}") #if (${op.addOpt}) @Operation(desc="${op.remarks}", searcher=true,
         * update=true, add=true, listView=true) @AntdColumn(desc="${op.remarks}", searcher=true, update=true, add=true,
         * listView=true)
         */
    }

    /**
     * 主键信息，目前只支持单字体
     * 
     * @return
     */
    public FieldObject getKey() {
        for (Column column : this.table.getPrimaryKeyColumns()) {
            if ("tenant_id".equals(column.getJdbcName()) || "tnt_inst_id".equals(column.getJdbcName())) {
                continue;
            }
            FieldObject fo = new FieldObject();
            fo.setName(column.getJavaName());
            fo.setDbName(column.getJdbcName());
            fo.setUpName(getFirstUpperCase(column.getJavaName()));
            fo.setType((column.getJavaShortType()));
            fo.setAutoIncrement(column.isAutoIncrement());
            /*
             * GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class); if (generatedValue != null) {
             * fo.setAutoIncrement(column.get); }
             */
            if ("id".equals(column.getJdbcName())) {
                fo.setAutoIncrement(true);
            }

            return fo;
        }
        return null;
    }

    /**
     * 所有的操作信息，增册改查，一条件等
     * 
     * @return
     */
    public List<ExtendsOperationObject> getOps(GenTableMeta co) {
        List<ExtendsOperationObject> operatList = new ArrayList<ExtendsOperationObject>();
        List<FieldObject> fieldList = getFields();
        for (FieldObject f : fieldList) {
            OperationMeta meta = defops.get(f.getDbName());
            if (meta == null) {
                LoggerUtil.info("{0}字段没有meta信息", f.getDbName());
                continue;
            }
            ExtendsOperationObject fo = setOperationObject(f, meta, co);
            if (fo != null) {
                operatList.add(fo);
            }
        }

        return operatList;
    }

    public List<ExtendsOperationObject> getOps() {
        return getOps(null);
    }

    /**
     * Antd前端页面上的操作信息，此信息是最重要的
     * 
     * @return
     */
    public List<ExtendsOperationObject> getAntdops(GenTableMeta co) {
        List<ExtendsOperationObject> operatList = new ArrayList<ExtendsOperationObject>();
        List<FieldObject> fieldList = getFields();
        for (FieldObject f : fieldList) {
            // TODO 此处应该是antd的配置信息
            OperationMeta meta = andops.get(f.getDbName());
            if (meta == null) {
                LoggerUtil.info("{0}字段没有meta信息", f.getDbName());
                continue;
            }

            ExtendsOperationObject fo = setOperationObject(f, meta, co);
            if (fo != null) {
                if ("gmt_create".equals(f.getDbName())) {
                    if (!"date_start_end".equals(fo.getOtype())) {
                        LoggerUtil.info("gmt_create字段 otype !=date_start_end {0}", fo.getOtype());
                        continue;
                    }
                    fo.add = false;
                    fo.update = false;
                }
                operatList.add(fo);
            }
        }

        return operatList;
    }

    /**
     * 所有的字段信息, 就是dbTable里的信息吧，有一些字段是需要过滤掉就行了
     * 
     * @return
     */
    public List<FieldObject> getFields() {
        List<FieldObject> fieldList = new ArrayList<FieldObject>();
        for (Column column : table.getAllColumns()) {
            FieldObject fo = new FieldObject();
            fo.setName(column.getJavaName());
            fo.setDbName(column.getJdbcName());
            fo.setType(column.getJavaShortType());
            fo.setAutoIncrement(column.isAutoIncrement());
            // fo.isKeyId = column.k

            fieldList.add(fo);
        }
        return fieldList;
    }

    /**
     * 首字母小定
     * 
     * @param name
     * @return
     */
    public String getFirstLowCase(String name) {
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
    public String getFirstUpperCase(String name) {
        String first = name.substring(0, 1).toUpperCase();
        String rest = name.substring(1, name.length());
        String newStr = new StringBuffer(first).append(rest).toString();
        return newStr;
    }

    private ExtendsOperationObject setOperationObject(FieldObject field, OperationMeta opertation, GenTableMeta co) {
        ExtendsOperationObject fo = new ExtendsOperationObject();
        fo.setName(field.getName());
        fo.setUpName(getFirstUpperCase(field.getName()));
        // fo.setType((field.getType().getSimpleName()));

        fo.setType((field.getType()));
        fo.setAdd(opertation.add());
        fo.setUpdate(opertation.update());
        fo.setListView(opertation.listView());
        fo.setSearcher(opertation.searcher());
        if (opertation.searcherLike()) {
            fo.setSearcherLike(true);
        }

        fo.setInputType(opertation.getInputType());
        fo.setAutoIncrement(opertation.isAutoIncrement());

        if (StringUtils.isNoneBlank(opertation.getRuleType())) {
            String[] RuleTypeData = opertation.getRuleType().split(",");
            List<String> mlist = new ArrayList<String>();
            boolean isrequire = false;
            for (int i = 0; i < RuleTypeData.length; i++) {
                String string = RuleTypeData[i];
                if (StringUtils.equals(string, "required")) {
                    isrequire = true;
                } else {
                    mlist.add(string);
                }
            }
            if (!CollectionUtils.isEmpty(mlist)) {
                fo.setRuleTypeFormat("{" + JSONObject.toJSONString(mlist) + "}");
                fo.setRuleType(true);
            }
            fo.setRequired(isrequire);
        } else {
            fo.setRequired(false);
        }

        fo.setExists(opertation.isExists());
        fo.setModifyDisplay(opertation.isModifyDisplay());
        fo.setModifyText(opertation.isModifyText());
        fo.setModifyDisabled(opertation.isModifyDisabled());

        fo.setSearcherLike(opertation.searcherLike());
        fo.setDesc(opertation.desc());
        FrontWidgetTypeEnum[] ots = opertation.type();
        if (ots != null && ots.length > 0) {
            fo.setOtype(ots[0].getCode());
        } else {
            fo.setOtype("");
        }

        if ("gmt_create".equals(fo.getDbName())) {
            System.out.println();
        }

        // rangepicker
        if ("rangepicker".equals(fo.getInputType())) {
            fo.setOtype("date_start_end");
            fo.addImportPackage("org.apache.commons.lang3.time.DateUtils");
        }
        if ("datepicker".equals(fo.getInputType())) {
            fo.setOtype("date");
        }

        /*
         * if (opertation.type()!=null && opertation.type().length > 0) { fo.setOtype(opertation.type()[0].getCode()); }
         */

        Object ienum = opertation.enumClass();

        if (ienum != null && StringUtils.endsWith(ienum.toString(), "Enum")) {
            fo.setOtype(FrontWidgetTypeEnum.Select.getCode());
            fo.setInputType("select");
            fo.setEnums(true);
            fo.setEnumName(opertation.enumClass().getName());
        }

        if (StringUtils.isNotBlank(opertation.getEnumClassName()) && co != null) {
            fo.setOtype(FrontWidgetTypeEnum.Select.getCode());
            fo.setEnums(true);
            fo.setInputType("select");
            String name = opertation.getEnumClassName();
            name = getFirstUpperCase(name);
            if (!StringUtils.endsWith(name, "Enum")) {
                name = name + "Enum";
            }
            String pname = co.getBasepackage() + ".model.enums." + name;
            fo.setEnumName(pname);
            fo.addImportPackage(pname);
            fo.addImportPackage("org.oneboot.core.utils.EnumsUtil");
        }

        if (StringUtils.isNotBlank(opertation.getTableAssociateStr()) && co != null) {
            fo.setOtype(FrontWidgetTypeEnum.Select.getCode());
            fo.setInputType("select");
            fo.setMappingData(true);
            fo.setMappingDataName(opertation.getTableAssociateStr());
            fo.setMappingDataNameField(fo.getName() + "Name");
            fo.setMappingDataNameField(fo.getMappingDataNameField().replace("Id", ""));
            fo.setMappingDataNameFieldUp(getFirstUpperCase(fo.getMappingDataNameField()));
        }

        fo.setDbName(field.getDbName());

        if (field.isKeyId()) {
            fo.setKeyid(true);
        }
        return fo;

    }

    public static void main(String[] args) {
        System.out.println("ddddd");

        String[] RuleTypeData = "required,moblie,hard".split(",");
        List<String> mlist = new ArrayList<String>();
        ;
        for (int i = 0; i < RuleTypeData.length; i++) {
            String string = RuleTypeData[i];
            if (StringUtils.equals(string, "required")) {

            } else {
                mlist.add(string);
            }
        }

        System.out.println(mlist);
        String[] array = new String[mlist.size()];
        String[] s = mlist.toArray(array);
        System.out.println(Arrays.toString(s));

    }

    /**
     * 是否存在Date类型
     * 
     * @param opList
     */
    public boolean isExistDateType() {
        for (ExtendsOperationObject ExtendsOperationObject : getOps()) {
            if ("Date".equals(ExtendsOperationObject.getType())) {
                return true;
            }
        }
        return false;
    }

    public Map<String, OperationMeta> getDefops() {
        return defops;
    }

    public void setDefops(Map<String, OperationMeta> defops) {
        this.defops = defops;
    }

    public Map<String, OperationMeta> getAndops() {
        return andops;
    }

    public void setAndops(Map<String, OperationMeta> andops) {
        this.andops = andops;
    }

    public String getTableName() {
        return this.table.getTableName();
    }

    public DbTable getTable() {
        return table;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPojoName() {
        return pojoName;
    }

    public void setPojoName(String pojoName) {
        this.pojoName = pojoName;
    }

    public String getBizModel() {
        return bizModel;
    }

    public void setBizModel(String bizModel) {
        this.bizModel = bizModel;
    }

}
