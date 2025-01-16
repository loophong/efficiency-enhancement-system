package com.heli.production.domain.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @TableName production_workday
 */
@TableName(value = "production_workday")
@Data
public class WorkdayEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 日期
     */
    @TableId
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    /**
     * 状态：1、工作日  0、休息日
     */
    private Integer status;
}


