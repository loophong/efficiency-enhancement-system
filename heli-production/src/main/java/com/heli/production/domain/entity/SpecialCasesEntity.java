package com.heli.production.domain.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 特殊情况对象 production_special_cases
 *
 * @author hong
 * @date 2025-02-25
 */

@TableName("production_special_cases")
@Data
public class SpecialCasesEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 订单号
     */
    @Excel(name = "订单号")
    private String orderNumber;

    /**
     * 上线日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上线日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date onlineDate;

    /**
     * 故障原因
     */
    @Excel(name = "故障原因")
    private Long faultReason;

    /**
     * 故障说明
     */
    @Excel(name = "故障说明")
    private String faultDescription;

    /**
     * 责任科室
     */
    @Excel(name = "责任科室")
    private Long responsibleDepartment;

    /**
     * 解决方案
     */
    @Excel(name = "解决方案")
    private String solution;

    /**
     * 是否为重大故障
     */
    @Excel(name = "是否为重大故障")
    private Integer isMajorFault;

    /**
     * 处理状态
     */
    @Excel(name = "处理状态")
    private Integer approvalStatus;

    /**
     * 填报时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "填报时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date uploadDate;

    /**
     * 处理时间
     */
    private String files;

}
