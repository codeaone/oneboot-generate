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
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author wb-tushiqiao
 * @version $Id: GeneratorModel.java, v 0.1 2015年6月18日 下午5:22:08 wb-tushiqiao Exp $
 */

public class GeneratorModel {

    /** 模板name */
    private String templateName;
    /** 生成文件名 */
    private String fileName;
    /** 生成包名 */
    private String packageName;
    /** 模块名 */
    private String modelName;
    /**  */
    private String biz;
    private String modelNameImpl;
    private String pojoName;
    private boolean isResources = false;
    private String resourcesPath;
    private String rootPath;
    /** 是否在当前项目目录 */

    private boolean isCourseFilePath = false;
    private String packageStr;

    private String antdUpName;
    private String antdName;
    /** 代码生成的目录 */
    private String codeGenPath = "";

    private List<String> importPackages = new ArrayList<>();

    public void addImportPackage(String name) {
        this.importPackages.add(name);
    }

    public List<String> getImportPackages() {
        return importPackages;
    }

    public void setImportPackages(List<String> importPackages) {
        this.importPackages = importPackages;
    }

    public String getCodeGenPath() {
        return codeGenPath;
    }

    public void setCodeGenPath(String codeGenPath) {
        this.codeGenPath = codeGenPath;
    }

    public boolean isCourseFilePath() {
        return isCourseFilePath;
    }

    public void setCourseFilePath(boolean isCourseFilePath) {
        this.isCourseFilePath = isCourseFilePath;
    }

    /**
     * Getter method for property <tt>antdUpName</tt>.
     * 
     * @return property value of antdUpName
     */
    public String getAntdUpName() {
        return antdUpName;
    }

    /**
     * Setter method for property <tt>antdUpName</tt>.
     * 
     * @param antdUpName
     *            value to be assigned to property antdUpName
     */
    public void setAntdUpName(String antdUpName) {
        this.antdUpName = antdUpName;
    }

    /**
     * Getter method for property <tt>antdName</tt>.
     * 
     * @return property value of antdName
     */
    public String getAntdName() {
        return antdName;
    }

    /**
     * Setter method for property <tt>antdName</tt>.
     * 
     * @param antdName
     *            value to be assigned to property antdName
     */
    public void setAntdName(String antdName) {
        this.antdName = antdName;
    }

    /**
     * Setter method for property <tt>packageStr</tt>.
     * 
     * @param packageStr
     *            value to be assigned to property packageStr
     */
    public void setPackageStr(String packageStr) {
        this.packageStr = packageStr;
    }

    /**
     * Getter method for property <tt>rootPath</tt>.
     * 
     * @return property value of rootPath
     */
    public String getRootPath() {
        return rootPath;
    }

    /**
     * Setter method for property <tt>rootPath</tt>.
     * 
     * @param rootPath
     *            value to be assigned to property rootPath
     */
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    /**
     * Getter method for property <tt>resourcesPath</tt>.
     * 
     * @return property value of resourcesPath
     */
    public String getResourcesPath() {
        return resourcesPath;
    }

    /**
     * Setter method for property <tt>resourcesPath</tt>.
     * 
     * @param resourcesPath
     *            value to be assigned to property resourcesPath
     */
    public void setResourcesPath(String resourcesPath) {
        this.resourcesPath = resourcesPath;
    }

    /**
     * Getter method for property <tt>isResources</tt>.
     * 
     * @return property value of isResources
     */
    public boolean isResources() {
        return isResources;
    }

    /**
     * Setter method for property <tt>isResources</tt>.
     * 
     * @param isResources
     *            value to be assigned to property isResources
     */
    public void setResources(boolean isResources) {
        this.isResources = isResources;
    }

    /**
     * Getter method for property <tt>pojoName</tt>.
     * 
     * @return property value of pojoName
     */
    public String getPojoName() {
        return pojoName;
    }

    /**
     * Setter method for property <tt>pojoName</tt>.
     * 
     * @param pojoName
     *            value to be assigned to property pojoName
     */
    public void setPojoName(String pojoName) {
        this.pojoName = pojoName;
    }

    public String getPackageStr() {
        if (StringUtils.isBlank(packageStr)) {
            String packageStr = packageName + "." + biz + "." + modelName;
            if (!StringUtils.isBlank(modelNameImpl)) {
                packageStr = packageStr + "." + modelNameImpl;
            }
            return packageStr;
        }

        return packageStr;
    }

    /**
     * Getter method for property <tt>modelNameImpl</tt>.
     * 
     * @return property value of modelNameImpl
     */
    public String getModelNameImpl() {
        return modelNameImpl;
    }

    /**
     * Setter method for property <tt>modelNameImpl</tt>.
     * 
     * @param modelNameImpl
     *            value to be assigned to property modelNameImpl
     */
    public void setModelNameImpl(String modelNameImpl) {
        this.modelNameImpl = modelNameImpl;
    }

    /**
     * Getter method for property <tt>biz</tt>.
     * 
     * @return property value of biz
     */
    public String getBiz() {
        return biz;
    }

    /**
     * Setter method for property <tt>biz</tt>.
     * 
     * @param biz
     *            value to be assigned to property biz
     */
    public void setBiz(String biz) {
        this.biz = biz;
    }

    /**
     * Getter method for property <tt>modelName</tt>.
     * 
     * @return property value of modelName
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Setter method for property <tt>modelName</tt>.
     * 
     * @param modelName
     *            value to be assigned to property modelName
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
     * Getter method for property <tt>templateName</tt>.
     * 
     * @return property value of templateName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * Setter method for property <tt>templateName</tt>.
     * 
     * @param templateName
     *            value to be assigned to property templateName
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * Getter method for property <tt>fileName</tt>.
     * 
     * @return property value of fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Setter method for property <tt>fileName</tt>.
     * 
     * @param fileName
     *            value to be assigned to property fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Getter method for property <tt>packageName</tt>.
     * 
     * @return property value of packageName
     */
    public String getPackageName() {
        return packageName;
    }

    /**
     * Setter method for property <tt>packageName</tt>.
     * 
     * @param packageName
     *            value to be assigned to property packageName
     */
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

}
