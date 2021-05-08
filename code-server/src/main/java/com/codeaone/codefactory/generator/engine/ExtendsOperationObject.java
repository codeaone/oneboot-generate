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

public class ExtendsOperationObject extends OperationObject {

    public String enumClassName;

    public boolean exists = false;

    public String inputType;

    public boolean modifyDisabled = false;

    public boolean modifyDisplay = false;

    public boolean modifyText = false;

    public String remarks;

    public boolean ruleType = false;
    public String ruleTypeFormat;
    public boolean required = false;
    private List<String> importPackages = new ArrayList<>();

    public boolean mappingData = false;
    public String mappingDataName;
    public String mappingDataNameField;
    public String mappingDataNameFieldUp;

    public String getMappingDataNameFieldUp() {
        return mappingDataNameFieldUp;
    }

    public void setMappingDataNameFieldUp(String mappingDataNameFieldUp) {
        this.mappingDataNameFieldUp = mappingDataNameFieldUp;
    }

    public String getMappingDataNameField() {
        return mappingDataNameField;
    }

    public void setMappingDataNameField(String mappingDataNameField) {
        this.mappingDataNameField = mappingDataNameField;
    }

    public boolean isMappingData() {
        return mappingData;
    }

    public void setMappingData(boolean mappingData) {
        this.mappingData = mappingData;
    }

    public String getMappingDataName() {
        return mappingDataName;
    }

    public void setMappingDataName(String mappingDataName) {
        this.mappingDataName = mappingDataName;
    }

    public String getRuleTypeFormat() {
        return ruleTypeFormat;
    }

    public void setRuleTypeFormat(String ruleTypeFormat) {
        this.ruleTypeFormat = ruleTypeFormat;
    }

    public void addImportPackage(String name) {
        this.importPackages.add(name);
    }

    public List<String> getImportPackages() {
        return importPackages;
    }

    public void setImportPackages(List<String> importPackages) {
        this.importPackages = importPackages;
    }

    public String getEnumClassName() {
        return enumClassName;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public void setEnumClassName(String enumClassName) {
        this.enumClassName = enumClassName;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }

    public boolean isModifyDisabled() {
        return modifyDisabled;
    }

    public void setModifyDisabled(boolean modifyDisabled) {
        this.modifyDisabled = modifyDisabled;
    }

    public boolean isModifyDisplay() {
        return modifyDisplay;
    }

    public void setModifyDisplay(boolean modifyDisplay) {
        this.modifyDisplay = modifyDisplay;
    }

    public boolean isModifyText() {
        return modifyText;
    }

    public void setModifyText(boolean modifyText) {
        this.modifyText = modifyText;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public boolean getRuleType() {
        return ruleType;
    }

    public void setRuleType(boolean ruleType) {
        this.ruleType = ruleType;
    }

}
