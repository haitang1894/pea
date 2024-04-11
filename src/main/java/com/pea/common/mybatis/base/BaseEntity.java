package com.pea.common.mybatis.base;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础实体
 */
@Getter
@Setter
public class BaseEntity implements Serializable {

	@Schema(description = "创建者ID")
	private Long createId;

	@Schema(description = "创建者名称")
	private String createBy;

	@Schema(description = "创建时间")
	@JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;

	@Schema(description = "修改者ID")
	private Long updateId;

	@Schema(description = "修改者名称")
	private String updateBy;

	@Schema(description = "更新时间")
	@JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;

	@TableLogic(value = "0")
	@Schema(description = "是否已删除：0->未删除；1->已删除")
	private Integer isDeleted;

	@Schema(description = "删除时间")
	@JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN, timezone = "GMT+8")
	private LocalDateTime deleteTime;

}