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
public class OperationObject extends FieldObject {

    public String desc;

    public boolean searcher;

    public boolean searcherLike = false;

    public boolean add;

    public boolean update;

    public boolean listView;

    public boolean keyid = false;

    public String otype;

    public boolean enums = false;

    public String enumName;

    public String enumSimpleName;

    /** 页面操作需要过滤掉的字段 */
    private static String[] noAddOptKey = new String[] { "gmtCreate", "gmtModified", "creator", "updater", "tenantId",
            "tntInstId", "createTime", "updateTime" };

    public boolean getAddOrUpdate() {
        if (add || update) {
            if (getAddOpt() && !keyid) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否需要增加Opt注解信息
     * 
     * @return
     */
    public boolean getAddOpt() {
        for (String string : noAddOptKey) {
            if (StringUtils.equals(string, this.getName())) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        OperationObject oo = new OperationObject();
        oo.setEnums(true);
        // oo.setEnumName(TestTypeEnum.class.getName());
        System.out.println(oo.getEnumLink());
        System.out.println(oo.getEnumSimpleName());
    }

    public String getEnumLink() {
        if (enums) {
            return "{ @link " + getEnumSimpleName() + " }";
        }
        return "";
    }

    public String getEnumSimpleName() {
        if (enums) {
            return StringUtils.substringAfterLast(enumName, ".");
        }
        return "";
    }

    /**
     * Setter method for property <tt>enumSimpleName</tt>.
     * 
     * @param enumSimpleName
     *            value to be assigned to property enumSimpleName
     */
    public void setEnumSimpleName(String enumSimpleName) {
        this.enumSimpleName = enumSimpleName;
    }

    /**
     * Getter method for property <tt>enums</tt>.
     * 
     * @return property value of enums
     */
    public boolean isEnums() {
        return enums;
    }

    /**
     * Setter method for property <tt>enums</tt>.
     * 
     * @param enums
     *            value to be assigned to property enums
     */
    public void setEnums(boolean enums) {
        this.enums = enums;
    }

    /**
     * Getter method for property <tt>enumName</tt>.
     * 
     * @return property value of enumName
     */
    public String getEnumName() {
        return enumName;
    }

    /**
     * Getter method for property <tt>keyid</tt>.
     * 
     * @return property value of keyid
     */
    public boolean isKeyid() {
        return keyid;
    }

    /**
     * Setter method for property <tt>keyid</tt>.
     * 
     * @param keyid
     *            value to be assigned to property keyid
     */
    public void setKeyid(boolean keyid) {
        this.keyid = keyid;
    }

    /**
     * Setter method for property <tt>enumName</tt>.
     * 
     * @param enumName
     *            value to be assigned to property enumName
     */
    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    /**
     * Getter method for property <tt>otype</tt>.
     * 
     * @return property value of otype
     */
    public String getOtype() {
        if (StringUtils.isBlank(this.otype)) {
            if (enums) {
                return "select";
            }
        }
        return otype;
    }

    /**
     * Setter method for property <tt>otype</tt>.
     * 
     * @param otype
     *            value to be assigned to property otype
     */
    public void setOtype(String otype) {
        this.otype = otype;
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
     * Getter method for property <tt>searcher</tt>.
     * 
     * @return property value of searcher
     */
    public boolean isSearcher() {
        return searcher;
    }

    /**
     * Setter method for property <tt>searcher</tt>.
     * 
     * @param searcher
     *            value to be assigned to property searcher
     */
    public void setSearcher(boolean searcher) {
        this.searcher = searcher;
    }

    /**
     * Getter method for property <tt>searcherLike</tt>.
     * 
     * @return property value of searcherLike
     */
    public boolean isSearcherLike() {
        return searcherLike;
    }

    /**
     * Setter method for property <tt>searcherLike</tt>.
     * 
     * @param searcherLike
     *            value to be assigned to property searcherLike
     */
    public void setSearcherLike(boolean searcherLike) {
        this.searcherLike = searcherLike;
    }

    /**
     * Getter method for property <tt>add</tt>.
     * 
     * @return property value of add
     */
    public boolean isAdd() {
        return add;
    }

    /**
     * Setter method for property <tt>add</tt>.
     * 
     * @param add
     *            value to be assigned to property add
     */
    public void setAdd(boolean add) {
        this.add = add;
    }

    /**
     * Getter method for property <tt>update</tt>.
     * 
     * @return property value of update
     */
    public boolean isUpdate() {
        return update;
    }

    /**
     * Setter method for property <tt>update</tt>.
     * 
     * @param update
     *            value to be assigned to property update
     */
    public void setUpdate(boolean update) {
        this.update = update;
    }

    /**
     * Getter method for property <tt>listView</tt>.
     * 
     * @return property value of listView
     */
    public boolean isListView() {
        return listView;
    }

    /**
     * Setter method for property <tt>listView</tt>.
     * 
     * @param listView
     *            value to be assigned to property listView
     */
    public void setListView(boolean listView) {
        this.listView = listView;
    }

}
