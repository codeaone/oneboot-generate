package com.codeaone.codefactory.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class DevProject implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 项目名
	 */
	private String name;

	/**
	 * 项目所属人
	 */
	private String ownerId;

	/**
	 * 项目的包
	 */
	private String basePackage;

	/**
	 * 生成文件的存放位置
	 */
	private String rootPath;

	/**
	 * 前端代码生成目录
	 */
	private String frontPath;

	/**
	 * 代码模板
	 */
	private String tempPath;

	/**
	 * 是否覆盖
	 */
	private Boolean overlayGen;

	/**
	 * 工程结构
	 */
	private Boolean single;

	/**
	 * 需要功能
	 */
	private String needFun;

	/**
	 * 前端代码生成目录
	 */
	private String url;

	/**
	 * 数据库类型
	 */
	private String dbType;

	/**
	 * 连接用户名
	 */
	private String username;

	/**
	 * 连接密码
	 */
	private String password;

	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 扩展信息
	 */
	private String extMap;

	/**
	 * 创建时间
	 */
	private LocalDateTime gmtCreate;

	/**
	 * 最后修改时间
	 */
	private LocalDateTime gmtModified;

}
