package com.codeaone.codefactory.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.codeaone.codefactory.entity.DevTableMate;

public interface IDevTableMateService extends IService<DevTableMate> {

	DevTableMate findByTableName(String name, String string);

}
