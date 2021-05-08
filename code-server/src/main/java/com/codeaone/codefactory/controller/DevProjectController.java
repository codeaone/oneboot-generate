package com.codeaone.codefactory.controller;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.oneboot.core.exception.CommonErrorCode;
import org.oneboot.core.exception.ObootException;
import org.oneboot.core.lang.BeanUtils;
import org.oneboot.core.result.CommonMvcResult;
import org.oneboot.core.utils.EnumsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.codeaone.codefactory.controller.form.DevProjectForm;
import com.codeaone.codefactory.controller.form.GenConfigForm;
import com.codeaone.codefactory.entity.DevProject;
import com.codeaone.codefactory.enums.DbTypeEnum;
import com.codeaone.codefactory.enums.NeedFunEnum;
import com.codeaone.codefactory.enums.OverlayGenEnum;
import com.codeaone.codefactory.enums.ProjectStructureEnum;
import com.codeaone.codefactory.enums.TempPathEnum;
import com.codeaone.codefactory.service.IDevProjectService;

@Controller
@ResponseBody
@RequestMapping("/api/unstable/projects")
public class DevProjectController extends BaseController {

	@Autowired
	private IDevProjectService devProjectService;

	/**
	 * 获取列表信息列表 GET /DevProjects
	 * 
	 * @param form
	 * @return
	 */
	@GetMapping
	public CommonMvcResult<Object> doIndex(DevProjectForm form) {
		CommonMvcResult<Object> result = getSucceed();
		result.setData(devProjectService.list());
		return result;
	}

	/**
	 * 获取新建信息初始数据 GET /new
	 * 
	 * @param model
	 */
	@GetMapping("/init")
	public CommonMvcResult<Object> doNew() {
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
	 * 新建信息 POST /DevProjects
	 * 
	 * @param model
	 * @param DevProject
	 *            信息
	 */
	@PostMapping
	@Transactional(rollbackFor = Exception.class)
	public CommonMvcResult<Object> doCreate(@Valid DevProjectForm form) {

		DevProject devProject = new DevProject();
		BeanUtils.copyProperties(form, devProject);
		devProject.setOwnerId("admin");
		devProjectService.save(devProject);
		CommonMvcResult<Object> result = getSucceed();
		return result;
	}

	/**
	 * 修改信息，增量修改信息，如果有全量修改的需求，请使用PUT请求 PATCH /:id
	 * 
	 * @param model
	 * @param id
	 *            用户ID
	 * @param DevProject
	 *            修改后的信息
	 */
	@PatchMapping("/{id}")
	@Transactional(rollbackFor = Exception.class)
	public CommonMvcResult<Object> doUpdate(@PathVariable String id, @Valid DevProjectForm form) {
		DevProject devProject = devProjectService.getById(id);
		if (null == devProject) {
			throw new ObootException(CommonErrorCode.DATA_SELECT_FAIL);
		}
		// 增量修改信息
		BeanUtils.copyPropertiesNull(form, devProject);
		devProjectService.updateById(devProject);
		CommonMvcResult<Object> result = getSucceed();
		return result;
	}

	@PatchMapping("/config/{id}")
	@Transactional(rollbackFor = Exception.class)
	public CommonMvcResult<Object> doUpdateConfig(@PathVariable String id, @Valid GenConfigForm form) {
		DevProject devProject = devProjectService.getById(id);
		if (null == devProject) {
			throw new ObootException(CommonErrorCode.DATA_SELECT_FAIL);
		}
		// 增量修改信息
		BeanUtils.copyPropertiesNull(form, devProject);
		Connection connection = getConnection(devProject);
		System.out.println(connection);
		
		devProjectService.updateById(devProject);
		CommonMvcResult<Object> result = getSucceed();
		return result;
	}

	@GetMapping("/{id}")
	public CommonMvcResult<Object> doShow(@PathVariable String id) {
		CommonMvcResult<Object> map = getSucceed();
		DevProject devProject = devProjectService.getById(id);
		if (null == devProject) {
			throw new ObootException(CommonErrorCode.DATA_SELECT_FAIL);
		}
		map.setData(devProject);
		return map;
	}
}
