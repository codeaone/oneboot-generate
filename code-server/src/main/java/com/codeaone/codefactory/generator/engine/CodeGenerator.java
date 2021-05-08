package com.codeaone.codefactory.generator.engine;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.velocity.VelocityContext;
import org.oneboot.core.lang.StringUtils;
import org.oneboot.core.logging.LoggerUtil;

import com.codeaone.codefactory.generator.config.CodeGenConfig;
import com.codeaone.codefactory.generator.core.Column;
import com.codeaone.codefactory.generator.core.DbTable;

public class CodeGenerator {
	/** 配置信息 */
	private final CodeGenConfig config;

	/**
	 * @param conifg
	 */
	public CodeGenerator(CodeGenConfig conifg) {
		super();
		this.config = conifg;
	}

	/**
	 * 
	 * @param models
	 */
	public void printAntd(List<GenTableMeta> models) {
		System.out.println("在 src/index.js \n 3. Model 文件中增加");
		for (GenTableMeta co : models) {
			String antdName = co.getModelName();
			System.out.println("app.model(require('./models/" + antdName + "s'));");

			/*
			 * System.out.println(gm.getAntdName());
			 * System.out.println(gm.getAntdUpName());
			 */
		}
		System.out.println();
		System.out.println("在 src/router.js 文件中增加");
		for (GenTableMeta co : models) {
			String antdName = co.getModelName();
			String antModelName = getFirstUpperCase(antdName);
			// modelName antdName
			System.out.println("import " + antModelName + "s from './routes/" + antdName + "s';");
		}
		System.out.println();
		for (GenTableMeta co : models) {
			String antdName = co.getModelName();
			String antModelName = getFirstUpperCase(antdName);
			// modelName antdName
			System.out.println(
					"<Route path=\"" + antdName + "s\" component={" + antModelName + "s} onLeave={nprogress}/>");
		}
	}

	public String getCourseFilePath() {
		File directory = new File("");// 参数为空
		String courseFile = "";
		try {
			courseFile = directory.getCanonicalPath();
		} catch (IOException e) {
		}
		return courseFile;
	}

	/** 项目的包 */
	// protected String basepackage = "com.huanwu.sp.switchmng";

	// private String vmPath = "META-INF/";
	// protected String vmPath = "";

	/** 是否覆盖生成代码 */
	// protected Boolean isOverlayGen = true;

	/** 生成文件的存放位置 */
	// protected String rootPath =
	// "D:/codes/work/huanwu/smart_platform/switchmng/";

	/** 根据类来反生成CRUD的代码，package是实体的存放处 */
	// protected String pojoPackage = "com.huanwu.sp.switchmng.test.genmodel";

	/**
	 * 会生成三个model类，如果有需求可以单个生成
	 * 
	 * @param tbList
	 * @throws Exception
	 */
	public void generatorModel(List<DbTable> tbList, GenTableMeta co) throws Exception {
		LoggerUtil.info("开始生成model {0}", tbList);
		for (DbTable table : tbList) {
			String pojoName = table.getJavaName();
			// String modelName = co.getModelName();
			String modelName = "";
			VelocityContext ctx = new VelocityContext();
			ctx.put("table", table);
			ctx.put("subTable", co.isSubTable());
			ctx.put("key", co.getKey());
			GeneratorModel gm = new GeneratorModel();

			for (Column column : table.getModelColumns()) {
				if ("Date".equals(column.getJavaShortType())) {
					String p = "java.util.Date";
					if (!gm.getImportPackages().contains(p)) {
						gm.addImportPackage(p);
					}
				}
			}

			ctx.put("tableName", co.getTableName());
			ctx.put("fields", co.getFields());
			List<ExtendsOperationObject> ops = co.getOps();
			FieldObject key = co.getKey();
			for (ExtendsOperationObject op : ops) {
				if (key != null) {
					if (key.getDbName().equals(op.getDbName())) {
						op.add = false;
						continue;
					}
				}
				if ("gmt_create".equals(op.getDbName())) {
					op.add = false;
					op.update = false;
					LoggerUtil.info("gmt_create dal {0}", op);
				}
			}

			ctx.put("ops", ops);

			ctx.put("importPackages", gm.getImportPackages());

			gm.setPojoName(pojoName);
			gm.setTemplateName(config.getTempPath() + "velocityTemplate/model/gen-model.vm");
			gm.setFileName(pojoName + ".java");
			gm.setModelName(modelName);
			gm.setPackageName(config.getPojoPackage());
			gm.setCourseFilePath(true);

			if (gm.isCourseFilePath()) {
				gm.setRootPath("src/test");
			} else {
				gm.setRootPath("test/src/test");
			}
			gm.setPackageStr(config.getPojoPackage());

			// test model
			// VelocityGen.generator(ctx, gm, config);

			gm.setCourseFilePath(false);
			gm.setTemplateName(config.getTempPath() + "velocityTemplate/model/model.vm");
			gm.setFileName(pojoName + ".java");
			gm.setModelName(modelName);
			gm.setPackageName(config.getBasePackage());
			if (config.getSingle()) {
				gm.setRootPath("src/main");
			} else {
				gm.setRootPath("core-model/src/main");
			}
			gm.setPackageStr(config.getBasePackage() + ".model.models");
			// model
			VelocityGen.generator(ctx, gm, config);

			gm.setTemplateName(config.getTempPath() + "velocityTemplate/model/model-do.vm");
			gm.setFileName(pojoName + "DO.java");
			gm.setModelName(modelName);
			gm.setPackageName(config.getBasePackage());
			if (config.getSingle()) {
				gm.setRootPath("src/main");
			} else {
				gm.setRootPath("common-dal/src/main");
			}
			gm.setPackageStr(config.getBasePackage() + ".repository.domain");
			// model DO
			VelocityGen.generator(ctx, gm, config);
		}
	}

	public void generatorTestModel(List<DbTable> tbList) throws Exception {
		for (DbTable table : tbList) {
			String pojoName = table.getJavaName();
			// String modelName = co.getModelName();
			String modelName = "";
			VelocityContext ctx = new VelocityContext();
			ctx.put("table", table);

			GeneratorModel gm = new GeneratorModel();
			gm.setPojoName(pojoName);
			gm.setTemplateName(config.getTempPath() + "velocityTemplate/model/gen-model.vm");
			gm.setFileName(pojoName + ".java");
			gm.setModelName(modelName);
			gm.setPackageName(config.getPojoPackage());
			gm.setCourseFilePath(true);

			if (gm.isCourseFilePath()) {
				gm.setRootPath("src/test");
			} else {
				gm.setRootPath("test/src/test");
			}
			gm.setPackageStr(config.getPojoPackage());

			// test model
			VelocityGen.generator(ctx, gm, config);

		}
	}

	public void generatorAntdNew(GenTableMeta co) throws Exception {
		String pojoName = co.getPojoName();
		String modelName = co.getModelName();
		VelocityContext ctx = new VelocityContext();
		String antModelName = getFirstUpperCase(modelName);

		ExtendGeneratorModel gm = new ExtendGeneratorModel();
		gm.setPojoName(pojoName);
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/models.vm");

		gm.setModelName(modelName);
		gm.setAntdUpName(antModelName);
		gm.setAntdName(modelName);
		gm.setModelNameImpl("impl");
		gm.setPackageName(config.getBasePackage());
		gm.setResources(true);

		gm.setBizModel(co.getBizModel());

		String resourcesPath = "/resources/antd";
		if (StringUtils.isNoneBlank(config.getFrontPath())) {
			gm.setCodeGenPath(config.getFrontPath());
			resourcesPath = "";
			gm.setRootPath("src");
		} else {
			gm.setRootPath("test/src/test");
		}
		resourcesPath = resourcesPath + "/pages";
		if (StringUtils.isNotBlank(gm.getBizModel())) {
			resourcesPath = resourcesPath + "/" + gm.getBizModel().toLowerCase();
		}
		resourcesPath = resourcesPath + "/" + gm.getAntdName() + "/";

		gm.setResourcesPath(resourcesPath);
		gm.setFileName("model.js");

		gm.setAntdName(modelName);
		gm.setAntdUpName(antModelName);
		ctx.put("tableName", co.getTableName());
		ctx.put("fields", co.getFields());

		ctx.put("add", co.isAdd());
		ctx.put("update", co.isUpdate());
		ctx.put("delete", co.isDelete());
		ctx.put("detail", co.isDetail());
		if (StringUtils.isNotBlank(gm.getBizModel())) {
			ctx.put("bizModel", gm.getBizModel().toLowerCase());
		}

		ctx.put("addOrUpdateOrDetailOrDelete", co.getAddOrUpdateOrDetailOrDelete());

		ctx.put("remark", co.getRemark());
		List<ExtendsOperationObject> ops = co.getAntdops(co);
		for (ExtendsOperationObject op : ops) {
			if ("creator".equals(op.getDbName()) || "updater".equals(op.getDbName())) {
				op.add = false;
				op.update = false;
			}
		}

		ctx.put("ops", ops);
		// ctx.put("ops", co.getOps(co));

		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/services.vm");
		gm.setFileName("service.js");
		gm.setResourcesPath(resourcesPath);
		gm.setResources(true);
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/view-list.vm");
		// gm.setFileName(antModelName + "List.jsx");
		gm.setFileName(antModelName + "List.vue");
		// gm.setResourcesPath(resourcesPath + "components/");
		gm.setResourcesPath(resourcesPath);
		gm.setResources(true);
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/view-modal.vm");
		gm.setFileName(antModelName + "Modal.vue");
		// gm.setResourcesPath(resourcesPath + "components/");
		gm.setResourcesPath(resourcesPath);
		gm.setResources(true);
		generator(ctx, gm);

		/*
		 * gm.setTemplateName(config.getTempPath() +
		 * "velocityTemplate/antd/view-search-css.vm");
		 * gm.setFileName(antModelName + "Search.less");
		 * gm.setResourcesPath(resourcesPath + "/routes/" + gm.getAntdName() +
		 * "s/"); gm.setResources(true);
		 */
		// generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/view-search.vm");
		gm.setFileName(antModelName + "Search.jsx");
		gm.setResourcesPath(resourcesPath + "components/");
		gm.setResources(true);
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/view-detail.vm");
		gm.setFileName(antModelName + "Detail.vue");
		// gm.setResourcesPath(resourcesPath + "components/");
		gm.setResourcesPath(resourcesPath);
		gm.setResources(true);
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/view-modal-import-check.vm");
		gm.setFileName(antModelName + "ImportCheck.jsx");
		gm.setResourcesPath(resourcesPath + "components/");
		gm.setResources(true);
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/view-modal-import.vm");
		gm.setFileName(antModelName + "Import.jsx");
		gm.setResourcesPath(resourcesPath + "components/");
		gm.setResources(true);
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/index-page.vm");
		gm.setFileName("index.jsx");
		// gm.setFileName("index.jsx");
		gm.setResourcesPath(resourcesPath);
		gm.setResources(true);
		generator(ctx, gm);
		/*
		 * gm.setTemplateName(config.getTempPath() +
		 * "velocityTemplate/antd/index-page-css.vm");
		 * gm.setFileName(antModelName + "sPage.less");
		 * gm.setResourcesPath(resourcesPath + "/routes/");
		 * gm.setResources(true);
		 */
		// generator(ctx, gm);

	}

	public void generatorAntd(GenTableMeta co) throws Exception {
		String pojoName = co.getPojoName();
		String modelName = co.getModelName();
		VelocityContext ctx = new VelocityContext();
		String antModelName = getFirstUpperCase(modelName);

		GeneratorModel gm = new GeneratorModel();
		gm.setPojoName(pojoName);
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/models.vm");
		gm.setFileName(modelName + "s.js");
		gm.setModelName(modelName);
		gm.setModelNameImpl("impl");
		gm.setPackageName(config.getBasePackage());
		gm.setResources(true);
		String resourcesPath = "/resources/antd";
		if (StringUtils.isNoneBlank(config.getFrontPath())) {
			gm.setCodeGenPath(config.getFrontPath());
			resourcesPath = "";
			gm.setRootPath("src");
		} else {
			gm.setRootPath("test/src/test");
		}

		gm.setResourcesPath(resourcesPath + "/models/");
		gm.setAntdName(modelName);
		gm.setAntdUpName(antModelName);
		ctx.put("tableName", co.getTableName());
		ctx.put("fields", co.getFields());
		ctx.put("ops", co.getAntdops(co));

		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/services.vm");
		gm.setFileName(modelName + "s.js");
		gm.setResourcesPath(resourcesPath + "/services/");
		gm.setResources(true);
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/view-list.vm");
		gm.setFileName(antModelName + "List.jsx");
		gm.setResourcesPath(resourcesPath + "/routes/" + gm.getAntdName() + "s/");
		gm.setResources(true);
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/view-modal.vm");
		gm.setFileName(antModelName + "Modal.jsx");
		gm.setResourcesPath(resourcesPath + "/routes/" + gm.getAntdName() + "s/");
		gm.setResources(true);
		generator(ctx, gm);

		/*
		 * gm.setTemplateName(config.getTempPath() +
		 * "velocityTemplate/antd/view-search-css.vm");
		 * gm.setFileName(antModelName + "Search.less");
		 * gm.setResourcesPath(resourcesPath + "/routes/" + gm.getAntdName() +
		 * "s/"); gm.setResources(true);
		 */
		// generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/view-search.vm");
		gm.setFileName(antModelName + "Search.jsx");
		gm.setResourcesPath(resourcesPath + "/routes/" + gm.getAntdName() + "s/");
		gm.setResources(true);
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/antd/index-page.vm");
		gm.setFileName(antModelName + "Index.jsx");
		// gm.setFileName("index.jsx");
		gm.setResourcesPath(resourcesPath + "/routes/" + gm.getAntdName() + "s/");
		gm.setResources(true);
		generator(ctx, gm);
		/*
		 * gm.setTemplateName(config.getTempPath() +
		 * "velocityTemplate/antd/index-page-css.vm");
		 * gm.setFileName(antModelName + "sPage.less");
		 * gm.setResourcesPath(resourcesPath + "/routes/");
		 * gm.setResources(true);
		 */
		// generator(ctx, gm);

	}

	public void generatorTableMapper(GenTableMeta co) throws Exception {
		String pojoName = co.getPojoName();
		String modelName = co.getModelName();
		VelocityContext ctx = new VelocityContext();

		GeneratorModel gm = new GeneratorModel();
		gm.setPojoName(pojoName);
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/dao/table-mapper.vm");
		// gm.setFileName(getFirstLowCase(pojoName) + "-mapper.xml");
		gm.setFileName(pojoName + "Mapper.xml");
		gm.setModelName(modelName);
		gm.setModelNameImpl("impl");
		gm.setPackageName(config.getBasePackage());
		gm.setResourcesPath("/resources/mybatis/");
		gm.setResources(true);
		gm.setBiz("");
		if (config.getSingle()) {
			gm.setRootPath("src/main");
		} else {
			gm.setRootPath("common-dal/src/main");
		}
		ctx.put("tableName", co.getTableName());
		ctx.put("fields", co.getFields());
		List<ExtendsOperationObject> ops = co.getOps();
		FieldObject key = co.getKey();
		for (ExtendsOperationObject op : ops) {
			if (key != null) {
				if (key.getDbName().equals(op.getDbName())) {
					op.add = false;
					continue;
				}
			}
			if ("gmt_create".equals(op.getDbName())) {
				op.add = false;
				op.update = false;
				LoggerUtil.info("gmt_create dal {0}", op);
			}
		}

		ctx.put("ops", ops);
		ctx.put("key", key);
		ctx.put("subTable", co.isSubTable());

		generator(ctx, gm);
	}

	public void generatorJspList(GenTableMeta co) throws Exception {
		String pojoName = co.getPojoName();
		String modelName = co.getModelName();
		VelocityContext ctx = new VelocityContext();

		GeneratorModel gm = new GeneratorModel();
		gm.setPojoName(pojoName);
		gm.setModelName(modelName);
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/jsp/list.vm");
		gm.setFileName(getFirstLowCase(pojoName) + "_list.jsp");
		gm.setPackageName(config.getBasePackage());
		gm.setResourcesPath("/webapp/WEB-INF/pages/");
		gm.setResources(true);
		gm.setBiz("");
		ctx.put("ops", co.getOps());
		generator(ctx, gm);

		// 生成manage.jsp
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/jsp/manage.vm");
		gm.setFileName(getFirstLowCase(pojoName) + "_manage.jsp");
		generator(ctx, gm);
	}

	public void generatorControllerFacade(GenTableMeta co) throws Exception {
		String templateName = "controllerFacade.vm";
		generatorControllerTemp(co, templateName);
	}

	public void generatorController(GenTableMeta co) throws Exception {

		String templateName = "controller.vm";
		generatorControllerTemp(co, templateName);
	}

	/**
	 * 只生成controller、from、vo
	 * 
	 * @param co
	 * @throws Exception
	 */
	public void generatorControllerTemp(GenTableMeta co, String templateName) throws Exception {
		String pojoName = co.getPojoName();
		String modelName = co.getModelName();
		VelocityContext ctx = new VelocityContext();

		GeneratorModel gm = new GeneratorModel();

		for (ExtendsOperationObject column : co.getOps(co)) {
			if ("Date".equals(column.getType())) {
				String p = "java.util.Date";
				if (!gm.getImportPackages().contains(p)) {
					gm.addImportPackage(p);
				}
			}
		}

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/web/" + templateName);
		gm.setFileName(pojoName + "Controller.java");
		gm.setPackageName(config.getBasePackage());
		co.setBasepackage(config.getBasePackage());
		gm.setModelName(modelName);
		gm.setBiz("webapp.controller");

		if (config.getSingle()) {
			gm.setRootPath("src/main");
		} else {
			gm.setRootPath("web/src/main");
		}

		gm.setPojoName(pojoName);
		gm.setPackageStr(config.getBasePackage() + ".controller" + "." + modelName);

		ctx.put("tableName", co.getTableName());
		ctx.put("fields", co.getFields());
		ctx.put("ops", co.getOps(co));
		ctx.put("key", co.getKey());
		ctx.put("isExistDateType", co.isExistDateType());

		for (ExtendsOperationObject oo : co.getOps(co)) {
			for (String s : oo.getImportPackages()) {
				if (!gm.getImportPackages().contains(s)) {
					gm.addImportPackage(s);
				}
			}
		}
		ctx.put("importPackages", gm.getImportPackages());
		//

		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/web/form.vm");
		gm.setFileName(pojoName + "Form.java");
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/web/vo.vm");
		gm.setFileName(pojoName + "VO.java");
		generator(ctx, gm);

		for (ExtendsOperationObject oo : co.getOps(co)) {
			if (oo.enums) {
				if (config.getSingle()) {
					gm.setRootPath("src/main");
				} else {
					gm.setRootPath("core-model/src/main");
				}
				String pName = oo.enumName.substring(oo.enumName.lastIndexOf(".") + 1);
				pName = getFirstUpperCase(pName);
				gm.setPojoName(pName);
				gm.setPackageStr(config.getBasePackage() + ".model.enums");
				gm.setTemplateName(config.getTempPath() + "velocityTemplate/model/enum.vm");
				gm.setFileName(pName + ".java");
				generator(ctx, gm);
			}
		}

		/*
		 * gm.setRootPath("web/src/test"); gm.setPojoName(pojoName);
		 * gm.setPackageStr(config.getBasePackage() + ".controller");
		 * gm.setTemplateName(config.getTempPath() +
		 * "velocityTemplate/web/test-controll.vm"); gm.setFileName(pojoName +
		 * "ControllerTest.java"); generator(ctx, gm);
		 */

		/*
		 * gm.setPackageStr(config.getBasePackage() + ".web");
		 * gm.setTemplateName(config.getTempPath() +
		 * "velocityTemplate/web/search-form.vm"); gm.setFileName(pojoName +
		 * "SearchForm.java"); gm.setModelName(modelName);
		 * gm.setPackageStr(config.getBasePackage() + ".controller" + "." +
		 * modelName); generator(ctx, gm);
		 * 
		 * gm.setTemplateName(config.getTempPath() +
		 * "velocityTemplate/web/show-vo.vm"); gm.setFileName(pojoName +
		 * "ShowVO.java"); generator(ctx, gm);
		 * 
		 * gm.setTemplateName(config.getTempPath() +
		 * "velocityTemplate/web/add-form.vm"); gm.setFileName(pojoName +
		 * "AddForm.java"); generator(ctx, gm);
		 * 
		 * gm.setTemplateName(config.getTempPath() +
		 * "velocityTemplate/web/edit-form.vm"); gm.setFileName(pojoName +
		 * "EditForm.java"); generator(ctx, gm);
		 * 
		 * gm.setTemplateName(config.getTempPath() +
		 * "velocityTemplate/web/edit-vo.vm"); gm.setFileName(pojoName +
		 * "EditVO.java"); generator(ctx, gm);
		 * 
		 * gm.setTemplateName(config.getTempPath() +
		 * "velocityTemplate/web/list-vo.vm"); gm.setFileName(pojoName +
		 * "ListVO.java"); generator(ctx, gm);
		 */
	}

	public void generatorControllerNoCom(GenTableMeta co) throws Exception {
		String pojoName = co.getPojoName();
		String modelName = co.getModelName();
		VelocityContext ctx = new VelocityContext();

		GeneratorModel gm = new GeneratorModel();
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/web2/controller.vm");
		gm.setFileName(pojoName + "Controller.java");
		gm.setPackageName(config.getBasePackage());
		gm.setModelName(modelName);
		gm.setBiz("webapp.controller");
		gm.setRootPath("web/src/main");
		gm.setPojoName(pojoName);
		gm.setPackageStr(config.getBasePackage() + ".controller" + "." + modelName);

		ctx.put("tableName", co.getTableName());
		ctx.put("fields", co.getFields());
		ctx.put("ops", co.getOps());

		generator(ctx, gm);

		gm.setPackageStr(config.getBasePackage());
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/web/search-form.vm");
		gm.setFileName(pojoName + "SearchForm.java");
		gm.setModelName(modelName);
		gm.setPackageStr(config.getBasePackage() + ".controller" + "." + modelName);
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/web/show-vo.vm");
		gm.setFileName(pojoName + "ShowVO.java");
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/web/add-form.vm");
		gm.setFileName(pojoName + "AddForm.java");
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/web/edit-form.vm");
		gm.setFileName(pojoName + "EditForm.java");
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/web/edit-vo.vm");
		gm.setFileName(pojoName + "EditVO.java");
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/web2/list-vo.vm");
		gm.setFileName(pojoName + "ListVO.java");
		generator(ctx, gm);

		gm.setRootPath("web/src/test");
		gm.setPojoName(pojoName);
		gm.setPackageStr(config.getBasePackage() + ".controller" + "." + modelName);
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/web/test-controll.vm");
		gm.setFileName(pojoName + "ControllerTest.java");
		// generator(ctx, gm);

	}

	/**
	 * 生成antd 与 contorller 层的代码
	 * 
	 * @param co
	 * @throws Exception
	 */
	public void generatorAntdAndController(GenTableMeta co) throws Exception {
		generatorAntd(co);
		generatorControllerNoCom(co);

	}

	public void generatorSearcher(GenTableMeta co) throws Exception {
		String pojoName = co.getPojoName();
		String modelName = co.getModelName();
		VelocityContext ctx = new VelocityContext();

		GeneratorModel gm = new GeneratorModel();

		for (ExtendsOperationObject column : co.getOps()) {
			if ("Date".equals(column.getType())) {
				String p = "java.util.Date";
				if (!gm.getImportPackages().contains(p)) {
					gm.addImportPackage(p);
				}
			}
		}

		gm.setPojoName(pojoName);
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/web/searcher.vm");
		gm.setFileName(pojoName + "ManagePageSearcher.java");
		gm.setPackageName(config.getBasePackage());
		gm.setModelName(modelName + ".searcher");
		gm.setBiz("webapp.controller");
		if (config.getSingle()) {
			gm.setRootPath("src/main");
		} else {
			gm.setRootPath("web/src/main");
		}
		gm.setPackageStr(config.getBasePackage() + ".controller" + "." + modelName);
		ctx.put("importPackages", gm.getImportPackages());
		ctx.put("ops", co.getOps());
		ctx.put("subTable", co.isSubTable());
		// 不生成searcher.java
		// generator(ctx, gm);

		gm.setPackageStr(config.getBasePackage() + ".repository.searcher");
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/dao/search-context.vm");
		gm.setFileName(pojoName + "SearchContext.java");
		if (config.getSingle()) {
			gm.setRootPath("src/main");
		} else {
			gm.setRootPath("common-dal/src/main");
		}
		ctx.put("key", co.getKey());
		generator(ctx, gm);

	}

	public void generatorDao(GenTableMeta co) throws Exception {
		String pojoName = co.getPojoName();
		String modelName = co.getModelName();
		VelocityContext ctx = new VelocityContext();

		GeneratorModel gm = new GeneratorModel();
		gm.setPojoName(pojoName);
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/dao/dao.vm");
		gm.setFileName(pojoName + "DAO.java");
		gm.setModelName(modelName);
		gm.setPackageName(config.getBasePackage());
		// gm.setBiz("dao");
		if (config.getSingle()) {
			gm.setRootPath("src/main");
		} else {
			gm.setRootPath("common-dal/src/main");
		}
		gm.setPackageStr(config.getBasePackage() + ".repository.mapper");
		ctx.put("key", co.getKey());
		ctx.put("subTable", co.isSubTable());

		generator(ctx, gm);
	}

	public void generatorService(GenTableMeta co) throws Exception {
		String pojoName = co.getPojoName();
		String modelName = co.getModelName();
		VelocityContext ctx = new VelocityContext();

		GeneratorModel gm = new GeneratorModel();
		gm.setPojoName(pojoName);
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/service/service.vm");
		gm.setFileName(pojoName + "Repository.java");
		gm.setModelName(modelName);
		gm.setPackageName(config.getBasePackage());
		gm.setBiz("manager");
		if (config.getSingle()) {
			gm.setRootPath("src/main");
		} else {
			gm.setRootPath("core-service/src/main");
		}
		gm.setPackageStr(config.getBasePackage() + ".repository");
		ctx.put("key", co.getKey());
		ctx.put("subTable", co.isSubTable());
		generator(ctx, gm);
	}

	public void generatorServiceImpl(GenTableMeta co) throws Exception {
		String pojoName = co.getPojoName();
		String modelName = co.getModelName();
		VelocityContext ctx = new VelocityContext();

		GeneratorModel gm = new GeneratorModel();
		gm.setPojoName(pojoName);
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/service/service-impl.vm");
		gm.setFileName(pojoName + "RepositoryImpl.java");
		gm.setModelName(modelName);
		gm.setModelNameImpl("impl");
		gm.setPackageName(config.getBasePackage());
		gm.setBiz("manager");
		if (config.getSingle()) {
			gm.setRootPath("src/main");
		} else {
			gm.setRootPath("core-service/src/main");
		}
		gm.setPackageStr(config.getBasePackage() + ".repository.impl");
		ctx.put("key", co.getKey());
		ctx.put("subTable", co.isSubTable());
		generator(ctx, gm);
	}

	public void generatorFacade(GenTableMeta co) throws Exception {
		String pojoName = co.getPojoName();
		String modelName = co.getModelName();
		VelocityContext ctx = new VelocityContext();

		GeneratorModel gm = new GeneratorModel();
		gm.setBiz("manager");
		if (config.getSingle()) {
			gm.setRootPath("src/main");
		} else {
			gm.setRootPath("common-facade/src/main");
		}
		ctx.put("key", co.getKey());

		for (ExtendsOperationObject column : co.getOps(co)) {
			if ("Date".equals(column.getType())) {
				String p = "java.util.Date";
				if (!gm.getImportPackages().contains(p)) {
					gm.addImportPackage(p);
				}
			}
		}

		ctx.put("tableName", co.getTableName());
		ctx.put("fields", co.getFields());
		ctx.put("ops", co.getOps(co));
		ctx.put("key", co.getKey());
		ctx.put("isExistDateType", co.isExistDateType());

		for (ExtendsOperationObject oo : co.getOps(co)) {
			for (String s : oo.getImportPackages()) {
				if (!gm.getImportPackages().contains(s)) {
					gm.addImportPackage(s);
				}
			}
		}
		ctx.put("importPackages", gm.getImportPackages());

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/facade/facade.vm");
		// gm.setFileName(pojoName + "Facade.java");
		gm.setFileName(pojoName + "Provider.java");
		gm.setPojoName(pojoName);
		gm.setModelName(modelName);
		gm.setPackageName(config.getBasePackage());
		gm.setPackageStr(config.getBasePackage() + ".common.facade");
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/facade/request.vm");
		gm.setFileName(pojoName + "Request.java");
		gm.setModelName(modelName);
		gm.setPackageName(config.getBasePackage());
		gm.setPackageStr(config.getBasePackage() + ".common.facade.request");

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/facade/request-search.vm");
		gm.setFileName(pojoName + "SearchRequest.java");
		generator(ctx, gm);
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/facade/request-insert.vm");
		gm.setFileName(pojoName + "InsertRequest.java");
		generator(ctx, gm);
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/facade/request-update.vm");
		gm.setFileName(pojoName + "UpdateRequest.java");
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/facade/result-dto.vm");
		gm.setFileName(pojoName + "DTO.java");
		gm.setModelName(modelName);
		gm.setPackageName(config.getBasePackage());
		gm.setPackageStr(config.getBasePackage() + ".common.facade.model");
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/facade/result.vm");
		gm.setFileName(pojoName + "Result.java");
		gm.setModelName(modelName);
		gm.setPackageName(config.getBasePackage());
		gm.setPackageStr(config.getBasePackage() + ".common.facade.result");
		// generator(ctx, gm);

		if (config.getSingle()) {
			gm.setRootPath("src/main");
		} else {
			gm.setRootPath("service-impl/src/main");
		}
		gm.setPojoName(pojoName);
		gm.setTemplateName(config.getTempPath() + "velocityTemplate/facade/facade-impl.vm");
		gm.setFileName(pojoName + "ProviderMockTest.java");
		gm.setModelName(modelName);
		gm.setModelNameImpl("impl");
		gm.setPackageName(config.getBasePackage());
		gm.setPackageStr(config.getBasePackage() + ".service.impl");

		generator(ctx, gm);
	}

	public void generatorCache(GenTableMeta co) throws Exception {
		String pojoName = co.getPojoName();
		String modelName = co.getModelName();
		VelocityContext ctx = new VelocityContext();

		GeneratorModel gm = new GeneratorModel();
		gm.setBiz("manager");
		if (config.getSingle()) {
			gm.setRootPath("src/main");
		} else {
			gm.setRootPath("core-service/src/main");
		}
		ctx.put("key", co.getKey());

		for (ExtendsOperationObject column : co.getOps(co)) {
			if ("Date".equals(column.getType())) {
				String p = "java.util.Date";
				if (!gm.getImportPackages().contains(p)) {
					gm.addImportPackage(p);
				}
			}
		}

		ctx.put("tableName", co.getTableName());
		ctx.put("fields", co.getFields());
		ctx.put("ops", co.getOps(co));
		ctx.put("key", co.getKey());
		ctx.put("isExistDateType", co.isExistDateType());

		for (ExtendsOperationObject oo : co.getOps(co)) {
			for (String s : oo.getImportPackages()) {
				if (!gm.getImportPackages().contains(s)) {
					gm.addImportPackage(s);
				}
			}
		}
		ctx.put("importPackages", gm.getImportPackages());

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/service/cache.vm");
		gm.setFileName(pojoName + "Cache.java");
		gm.setPojoName(pojoName);
		gm.setModelName(modelName);
		gm.setPackageName(config.getBasePackage());
		gm.setPackageStr(config.getBasePackage() + ".localcache");
		generator(ctx, gm);

		gm.setTemplateName(config.getTempPath() + "velocityTemplate/service/cacheManager.vm");
		gm.setFileName(pojoName + "CacheManager.java");
		gm.setModelName(modelName);
		gm.setPackageName(config.getBasePackage());
		gm.setPackageStr(config.getBasePackage() + ".localcache");
		generator(ctx, gm);

	}

	public void generator(VelocityContext ctx, GeneratorModel gm) throws Exception {

		VelocityGen.generator(ctx, gm, config);
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
}
