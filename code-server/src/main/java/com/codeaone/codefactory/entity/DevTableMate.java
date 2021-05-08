package com.codeaone.codefactory.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class DevTableMate implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Long id;

	/**
	 * 表名
	 */
	private String tableName;

	/**
	 * 项目ID
	 */
	private String projectId;

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
