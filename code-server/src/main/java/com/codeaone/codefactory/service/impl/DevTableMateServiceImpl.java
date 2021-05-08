package com.codeaone.codefactory.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.oneboot.core.mybatis.wrapper.QueryWrapperUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.codeaone.codefactory.entity.DevTableMate;
import com.codeaone.codefactory.mapper.DevTableMateMapper;
import com.codeaone.codefactory.service.IDevTableMateService;

@Service
public class DevTableMateServiceImpl extends ServiceImpl<DevTableMateMapper, DevTableMate>
		implements IDevTableMateService {

	@Override
	public DevTableMate findByTableName(String name, String projectId) {
		Map<String, Object> map = new HashMap<>(16);
		map.put("table_name", name);
		map.put("project_id", projectId);

		QueryWrapper<DevTableMate> wrapper = new QueryWrapper<>();
		QueryWrapperUtils.setMapWrapper(wrapper, map);

		return getOne(wrapper);
	}

}
