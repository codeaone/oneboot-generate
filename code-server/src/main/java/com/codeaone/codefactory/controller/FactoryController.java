package com.codeaone.codefactory.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.velocity.VelocityContext;
import org.oneboot.core.exception.CommonErrorCode;
import org.oneboot.core.exception.ObootException;
import org.oneboot.core.lang.BeanUtils;
import org.oneboot.core.lang.StringUtils;
import org.oneboot.core.result.CommonMvcResult;
import org.oneboot.core.utils.Assert;
import org.oneboot.core.utils.EnumsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.codeaone.codefactory.controller.form.GenToDiskForm;
import com.codeaone.codefactory.entity.DevProject;
import com.codeaone.codefactory.entity.DevTableMate;
import com.codeaone.codefactory.enums.DbTypeEnum;
import com.codeaone.codefactory.enums.FrontWidgetTypeEnum;
import com.codeaone.codefactory.enums.NeedFunEnum;
import com.codeaone.codefactory.enums.OverlayGenEnum;
import com.codeaone.codefactory.enums.ProjectStructureEnum;
import com.codeaone.codefactory.enums.TempPathEnum;
import com.codeaone.codefactory.generator.config.CodeGenConfig;
import com.codeaone.codefactory.generator.core.Column;
import com.codeaone.codefactory.generator.core.ColumnModel;
import com.codeaone.codefactory.generator.core.DatabaseMetaUtil;
import com.codeaone.codefactory.generator.core.DbTable;
import com.codeaone.codefactory.generator.core.TableModel;
import com.codeaone.codefactory.generator.engine.CodeGenerator;
import com.codeaone.codefactory.generator.engine.GenTableMeta;
import com.codeaone.codefactory.generator.engine.OperationMeta;
import com.codeaone.codefactory.service.IDevProjectService;
import com.codeaone.codefactory.service.IDevTableMateService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@ResponseBody
@RequestMapping("/api/unstable/factory")
public class FactoryController extends BaseController {

	/** ?????????????????? **/
	private static Map<String, DbTable> TABLE_MAP = new HashMap<>();

	/** ????????????????????? **/
	private static Map<String, Connection> DEV_CONN = new HashMap<>();

	@Autowired
	private IDevProjectService devProjectService;
	@Autowired
	private IDevTableMateService devTableMateService;

	@GetMapping
	public CommonMvcResult<Object> doIndex(String id) {
		CommonMvcResult<Object> result = getSucceed();
		DevProject p = devProjectService.getById(id);
		if (p == null) {
			throw new ObootException(CommonErrorCode.DATA_SELECT_FAIL);
		}
		Connection connection = getConnectionByMap(p);
		List<DbTable> list = DatabaseMetaUtil.getTableNameByCon(connection);
		result.setData(list);

		return result;
	}

	private Connection getConnectionByMap(DevProject p) {
		Connection conn = DEV_CONN.get(p.getId() + "");
		if (conn == null) {
			conn = getConnection(p);
			DEV_CONN.put(p.getId() + "", conn);
		}
		return conn;
	}

	@GetMapping("/all/dbdoc")
	public CommonMvcResult<Object> doAllDbDoc(String id) {
		CommonMvcResult<Object> result = getSucceed();
		DevProject p = devProjectService.getById(id);
		if (p == null) {
			throw new ObootException(CommonErrorCode.DATA_SELECT_FAIL);
		}

		String temp = "/velocityTemplate/dao/db.vm";
		String path = "D://";
		String fileName = "test.md";

		Connection connection = getConnectionByMap(p);

		VelocityContext ctx = new VelocityContext();

		return result;
	}

	@GetMapping("/all")
	public CommonMvcResult<Object> doAll(String id) {
		CommonMvcResult<Object> result = getSucceed();
		DevProject p = devProjectService.getById(id);
		if (p == null) {
			throw new ObootException(CommonErrorCode.DATA_SELECT_FAIL);
		}

		log.info("???????????????????????????");

		Connection connection = getConnectionByMap(p);
		List<DbTable> list = DatabaseMetaUtil.getTableNameByCon(connection);

		log.info("????????????: {}", list.size());
		List<String> tables = new ArrayList<>();
		for (DbTable summaryInfo : list) {
			tables.add(summaryInfo.getTableName());
		}
		log.info("???????????? table meta");
		List<DbTable> dbTableList = DatabaseMetaUtil.getTableMeta(tables, connection);
		DatabaseMetaUtil.mergeTableMeta(connection, dbTableList);

		TABLE_MAP.clear();
		for (DbTable dbTable : dbTableList) {
			TABLE_MAP.put(dbTable.getTableName(), dbTable);
		}
		log.info("???????????? table meta");

		return result;
	}

	/**
	 * ????????????
	 * 
	 * @param id
	 * @param table
	 * @return
	 */
	@GetMapping("/procode")
	public CommonMvcResult<Object> doProcode(String id, String table) {
		CommonMvcResult<Object> result = getSucceed();
		DevProject p = devProjectService.getById(id);
		if (p == null) {
			throw new ObootException(CommonErrorCode.DATA_SELECT_FAIL);
		}
		Connection connection = getConnection(p);
		return result;
	}

	/**
	 * ???????????????
	 * 
	 * @param id
	 * @param table
	 * @return
	 */
	@GetMapping("/tableinfo")
	public CommonMvcResult<Object> doTableInfo(String id, String table) {
		CommonMvcResult<Object> result = getSucceed();
		DevProject p = devProjectService.getById(id);
		if (p == null) {
			throw new ObootException(CommonErrorCode.DATA_SELECT_FAIL);
		}
		Connection connection = getConnectionByMap(p);

		DbTable dbTable = getDbTableByName(table, connection);
		if (null == dbTable) {
			throw new ObootException(CommonErrorCode.DATA_SELECT_FAIL);
		}

		TableModel tm = getTableModel(table, dbTable, id);

		Map<String, Object> map = new HashMap<>(16);
		// ????????????????????????????????????
		map.put("config", p);
		map.put("mdata", tm);
		result.setData(map);
		return result;
	}

	private TableModel getTableModel(String name, DbTable dbTable, String id) {
		TableModel tm = new TableModel();

		for (Column c : dbTable.getAllColumns()) {
			ColumnModel cm = new ColumnModel();
			BeanUtils.copyProperties(c, cm);
			cm.setDesc(c.getRemarks());
			cm.setInputType(FrontWidgetTypeEnum.Input.getCode());

			tm.addColumn(cm);
		}
		tm.setName(dbTable.getJavaName());
		tm.setTableName(dbTable.getTableName());
		tm.setRemark(dbTable.getRemark());

		DevTableMate tableMate = devTableMateService.findByTableName(name, id);
		if (tableMate != null) {
			// ??????DB???????????????????????????
			TableModel dbTm = JSONObject.parseObject(tableMate.getExtMap(), TableModel.class);
			System.out.println(dbTm);
			tm.setName(dbTm.getName());
			tm.setBizModel(dbTm.getBizModel());
			tm.setAdd(dbTm.isAdd());
			tm.setDelete(dbTm.isDelete());
			tm.setDetail(dbTm.isDetail());
			tm.setUpdate(dbTm.isUpdate());
			for (ColumnModel cm2 : tm.getAllColumns()) {
				for (ColumnModel dbcm : dbTm.getAllColumns()) {
					if (StringUtils.equals(cm2.getJdbcName(), dbcm.getJdbcName())) {
						// TODO ???????????????????????????????????????DB??????????????????
						BeanUtils.copyProperties(dbcm, cm2);
						continue;
					}
				}
			}
		}
		return tm;
	}

	private DbTable getDbTableByName(String name, Connection connection) {
		// ?????????????????????
		DbTable dbTable = TABLE_MAP.get(name);
		if (dbTable == null) {
			// ?????????????????????
			List<String> newTable = new ArrayList<>();
			newTable.add(name);
			List<DbTable> dbTableList = DatabaseMetaUtil.getTableMeta(newTable, connection);
			for (DbTable dbTable2 : dbTableList) {
				TABLE_MAP.put(dbTable2.getTableName(), dbTable2);
			}
			dbTable = TABLE_MAP.get(name);
		}
		return dbTable;
	}

	/**
	 * ?????????????????????
	 * 
	 * @param form
	 * @return
	 */
	@PostMapping("/tableinfo")
	public CommonMvcResult<Object> doSaveTableInfo(GenToDiskForm form) {
		CommonMvcResult<Object> result = getSucceed();
		return result;
	}

	/**
	 * ?????????????????????????????? GET /init
	 * 
	 * @param model
	 */
	@GetMapping("/init")
	public CommonMvcResult<Object> doInit() {
		Map<String, Object> map = new HashMap<>(16);
		map.put("dbType", EnumsUtil.toEnumObject(DbTypeEnum.class));
		map.put("overlayGen", EnumsUtil.toEnumObject(OverlayGenEnum.class));
		map.put("single", EnumsUtil.toEnumObject(ProjectStructureEnum.class));
		map.put("needFun", EnumsUtil.toEnumObject(NeedFunEnum.class));
		map.put("tempPath", EnumsUtil.toEnumObject(TempPathEnum.class));
		// map.put("merchId", warpTheVoService.get("merchant"));
		return getSucceed().setData(map);
	}

	/**
	 * ???????????????????????????
	 * 
	 * @param form
	 * @return
	 */
	@PostMapping("/gentodisk")
	public CommonMvcResult<Object> goGenToDisk(GenToDiskForm form) {
		CommonMvcResult<Object> result = getSucceed();
		// ???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
		log.info("??????????????????????????????{}", form);
		// ???????????????????????????
		List<DbTable> tlist = new ArrayList<>();
		for (String table : form.getTable().split(",")) {

			DbTable dbTable = TABLE_MAP.get(table);
			Assert.notNull(dbTable, table + " dbTable????????????");

			tlist.add(dbTable);
		}

		Assert.notEmpty(tlist, "dbTable????????????");

		// ???????????????????????????????????????
		DevProject p = devProjectService.getById(form.getId());
		Assert.notNull(p, "????????????????????????");
		tlist.forEach(t -> {
			genToDisk(t, p);
		});

		return result;
	}

	/**
	 * ?????????????????????
	 * 
	 * @param dbTable
	 * @param p
	 */
	public void genToDisk(DbTable dbTable, DevProject p) {
		CodeGenConfig config = new CodeGenConfig();
		config.setBasePackage(p.getBasePackage());
		config.setRootPath(p.getRootPath());
		// config.setPojoPackage(genConfigTest.getPojoPackage());
		if (StringUtils.isBlank(p.getTempPath())) {
			p.setTempPath("");
		}
		config.setTempPath(p.getTempPath());
		config.setOverlayGen(p.getOverlayGen());
		config.setFrontPath(p.getFrontPath());
		config.setSingle(p.getSingle());

		CodeGenerator gen = new CodeGenerator(config);

		List<GenTableMeta> models = new ArrayList<GenTableMeta>();
		GenTableMeta meta = new GenTableMeta();
		meta.setModelName(dbTable.getJavaName().toLowerCase());// ????????????
		meta.setPojoName(dbTable.getJavaName());
		meta.setTable(dbTable);

		models.add(meta);
		List<DbTable> tbList = new ArrayList<>();
		tbList.add(dbTable);

		TableModel tm = getTableModel(dbTable.getTableName(), dbTable, p.getId() + "");
		System.out.println(tm);
		meta.setModelName(tm.getName().toLowerCase());// ????????????
		meta.setPojoName(tm.getName());
		meta.setAdd(tm.isAdd());
		meta.setDelete(tm.isDelete());
		meta.setUpdate(tm.isUpdate());
		meta.setDetail(tm.isDetail());
		meta.setRemark(tm.getRemark());
		meta.setBizModel(tm.getBizModel());

		dbTable.setJavaName(tm.getName());

		// ??????????????????ops????????????
		for (Column c : dbTable.getAllColumns()) {
			if (!c.getAddOpt()) {
				continue;
			}
			if (c.getJavaName().equals("id")) {
				continue;
			}
			String dbName = c.getJdbcName();
			OperationMeta ometa = new OperationMeta();
			OperationMeta defMeta = new OperationMeta();
			// defMeta ??????????????????????????????ID????????????????????????
			for (ColumnModel cm : tm.getAllColumns()) {
				if (StringUtils.equals(cm.getJdbcName(), c.getJdbcName())) {
					// TODO ???????????????????????????????????????DB??????????????????
					BeanUtils.copyProperties(cm, ometa);

					defMeta.setDesc(cm.getRemarks());
					defMeta.setSearcherLike(cm.isSearcherLike());
					defMeta.setAutoIncrement(c.isAutoIncrement());
					defMeta.setInputType(cm.getInputType());
					defMeta.setEnumClassName(cm.getEnumClassName());
					defMeta.setExists(cm.isExists());
					defMeta.setTableAssociateStr(cm.getTableAssociateStr());
					// defMeta.setSearcherLike(cm.gets);
					continue;
				}
			}
			meta.addAndops(dbName, ometa);
			meta.addDefops(dbName, defMeta);
		}

		try {
			String _genTypesStr = p.getNeedFun();
			JSONObject _genTypesObj = JSONObject.parseObject(_genTypesStr);

			for (GenTableMeta co : models) {
				// ??????
				boolean subTable = _genTypesObj.getBoolean("subTable");
				co.setSubTable(subTable);
				if (_genTypesObj.getBoolean("model")) {
					gen.generatorModel(tbList, co);
				}
				if (_genTypesObj.getBoolean("tableMapper")) {
					gen.generatorTableMapper(co);
				}
				if (_genTypesObj.getBoolean("dao")) {
					gen.generatorDao(co);
				}
				if (_genTypesObj.getBoolean("service")) {
					gen.generatorService(co);
				}
				if (_genTypesObj.getBoolean("serviceImpl")) {
					gen.generatorServiceImpl(co);
				}
				if (_genTypesObj.getBoolean("searcher")) {
					gen.generatorSearcher(co);
				}
				if (_genTypesObj.getBoolean("controller")) {
					gen.generatorController(co);
				}
				if (_genTypesObj.getBoolean("controllerFacade")) {
					gen.generatorControllerFacade(co);
				}
				if (_genTypesObj.getBoolean("facade")) {
					gen.generatorFacade(co);
				}
				if (_genTypesObj.getBoolean("cache")) {
					gen.generatorCache(co);
				}

				if (_genTypesObj.getBoolean("antdNew")) {
					gen.generatorAntdNew(co);
				}
				if (_genTypesObj.getBoolean("antd")) {
					gen.generatorAntd(co);
				}

				// gen.generatorAntd(co);
				// gm.generatorAntdAndController(co);
				// ?????????????????????????????????????????????????????????????????????VM?????????????????? META-INF/velocityTemplate
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
