package com.heli.supplier.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 二方审核得分对象 supplier_two_review_score
 *
 * @author wll
 * @date 2025-03-06
 */
@Data
@TableName(value = "supplier_two_review_score")
public class SupplierTwoReviewScore {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;

    /**
     * 供应商代码
     */
    @Excel(name = "供应商编码")
    private String supplierCode;

    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称")
    private String supplierName;

    /**
     * 不符合项1
     */
    @Excel(name = "不符合项1")
    private String notTrue1;

    /**
     * 不符合项2
     */
    @Excel(name = "不符合项2")
    private String notTrue2;

    /**
     * 不符合项3
     */
    @Excel(name = "不符合项3")
    private String notTrue3;

    /**
     * 不符合项4
     */
    @Excel(name = "不符合项4")
    private String notTrue4;

    /**
     * 不符合项5
     */
    @Excel(name = "不符合项5")
    private String notTrue5;

    /**
     * 得分
     */
    @Excel(name = "得分")
    private Long score;

    /**
     * 上传时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "上传时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /**
     * 填报人
     */
    @Excel(name = "填报人")
    private String uploadName;

}
