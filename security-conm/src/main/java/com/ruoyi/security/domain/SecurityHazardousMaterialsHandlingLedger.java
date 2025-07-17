package com.ruoyi.security.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 危化品处理台账对象 security_hazardous_materials_handling_ledger
 * 
 * @author wang
 * @date 2025-03-03
 */
public class SecurityHazardousMaterialsHandlingLedger extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 处理时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "处理时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date handlingTime;

    /** 化学品名称 */
    @Excel(name = "化学品名称")
    private String chemicalName;

    /** 使用负责人 */
    @Excel(name = "使用负责人")
    private String userInCharge;

    /** 处理量 */
    @Excel(name = "处理量")
    private BigDecimal handlingQuantity;

    /** 处理方法 */
    @Excel(name = "处理方法")
    private String handlingMethod;

    /** 是否合规 */
    @Excel(name = "是否合规")
    private String isCompliant;

    /** 处理人 */
    @Excel(name = "处理人")
    private String handler;

    /** 关联ID */
    private Long relatedId;

    public SecurityHazardousMaterialsHandlingLedger() {
        // 默认构造函数，不设置默认值，避免影响查询条件
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setHandlingTime(Date handlingTime) 
    {
        this.handlingTime = handlingTime;
    }

    public Date getHandlingTime() 
    {
        return handlingTime;
    }
    public void setChemicalName(String chemicalName) 
    {
        this.chemicalName = chemicalName;
    }

    public String getChemicalName() 
    {
        return chemicalName;
    }
    public void setUserInCharge(String userInCharge) 
    {
        this.userInCharge = userInCharge;
    }

    public String getUserInCharge() 
    {
        return userInCharge;
    }
    public void setHandlingQuantity(BigDecimal handlingQuantity) 
    {
        this.handlingQuantity = handlingQuantity;
    }

    public BigDecimal getHandlingQuantity() 
    {
        return handlingQuantity;
    }
    public void setHandlingMethod(String handlingMethod) 
    {
        this.handlingMethod = handlingMethod;
    }

    public String getHandlingMethod() 
    {
        return handlingMethod;
    }
    public void setIsCompliant(String isCompliant)
    {
        this.isCompliant = isCompliant;
    }

    public String getIsCompliant()
    {
        return isCompliant;
    }
    public void setHandler(String handler) 
    {
        this.handler = handler;
    }

    public String getHandler()
    {
        return handler;
    }

    public void setRelatedId(Long relatedId)
    {
        this.relatedId = relatedId;
    }

    public Long getRelatedId()
    {
        return relatedId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("handlingTime", getHandlingTime())
            .append("chemicalName", getChemicalName())
            .append("userInCharge", getUserInCharge())
            .append("handlingQuantity", getHandlingQuantity())
            .append("handlingMethod", getHandlingMethod())
            .append("isCompliant", getIsCompliant())
            .append("handler", getHandler())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
