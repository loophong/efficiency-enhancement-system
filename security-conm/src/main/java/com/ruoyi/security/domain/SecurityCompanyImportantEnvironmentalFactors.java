package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 公司级重要环境因素清单对象 security_company_important_environmental_factors
 * 
 * @author wang
 * @date 2025-03-26
 */
public class SecurityCompanyImportantEnvironmentalFactors extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 环境因素 */
    @Excel(name = "环境因素", needMerge = true)
    private String environmentalFactor;

    /** 环境影响 */
    @Excel(name = "环境影响", needMerge = true)
    private String environmentalImpact;

    /** 活动/生产/服务 */
    @Excel(name = "活动/产品/服务")
    private String activityProductService;

    /** 部门/工序/岗位 */
    @Excel(name = "部门/工序/岗位")
    private String departmentProcessPosition;

    /** 时态/状态 */
    @Excel(name = "时态/状态")
    private String temporalState;

    /** 管控措施 */
    @Excel(name = "管控措施", needMerge = true)
    private String controlMeasures;

    /** 审批状态 */
    private String statu;
    
    /** 关联ID */
    private Long relatedId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    
    public void setRelatedId(Long relatedId) 
    {
        this.relatedId = relatedId;
    }

    public Long getRelatedId() 
    {
        return relatedId;
    }
    public void setEnvironmentalFactor(String environmentalFactor) 
    {
        this.environmentalFactor = environmentalFactor;
    }

    public String getEnvironmentalFactor() 
    {
        return environmentalFactor;
    }
    public void setEnvironmentalImpact(String environmentalImpact) 
    {
        this.environmentalImpact = environmentalImpact;
    }

    public String getEnvironmentalImpact() 
    {
        return environmentalImpact;
    }
    public void setActivityProductService(String activityProductService) 
    {
        this.activityProductService = activityProductService;
    }

    public String getActivityProductService() 
    {
        return activityProductService;
    }
    public void setDepartmentProcessPosition(String departmentProcessPosition) 
    {
        this.departmentProcessPosition = departmentProcessPosition;
    }

    public String getDepartmentProcessPosition() 
    {
        return departmentProcessPosition;
    }
    public void setTemporalState(String temporalState) 
    {
        this.temporalState = temporalState;
    }

    public String getTemporalState() 
    {
        return temporalState;
    }
    public void setControlMeasures(String controlMeasures) 
    {
        this.controlMeasures = controlMeasures;
    }

    public String getControlMeasures() 
    {
        return controlMeasures;
    }
    public void setStatu(String statu) 
    {
        this.statu = statu;
    }

    public String getStatu() 
    {
        return statu;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("environmentalFactor", getEnvironmentalFactor())
            .append("environmentalImpact", getEnvironmentalImpact())
            .append("activityProductService", getActivityProductService())
            .append("departmentProcessPosition", getDepartmentProcessPosition())
            .append("temporalState", getTemporalState())
            .append("controlMeasures", getControlMeasures())
            .append("statu", getStatu())
            .toString();
    }
}
