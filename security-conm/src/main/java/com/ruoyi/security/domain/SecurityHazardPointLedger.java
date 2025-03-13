package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 有点害台账对象 security_hazard_point_ledger
 * 
 * @author wang
 * @date 2025-03-05
 */
public class SecurityHazardPointLedger extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 评价单元 */
    @Excel(name = "评价单元")
    private String evaluationUnit;

    /** 岗位 */
    @Excel(name = "岗位")
    private String position;

    /** 检测地点 */
    @Excel(name = "检测地点")
    private String inspectionLocation;

    /** 化学因素名称 */
    @Excel(name = "化学因素名称")
    private String chemicalFactorName;

    /** 物理因素名称 */
    @Excel(name = "物理因素名称")
    private String physicalFactorName;

    /** 生产性粉尘名称 */
    @Excel(name = "生产性粉尘名称")
    private String productionDustName;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setEvaluationUnit(String evaluationUnit) 
    {
        this.evaluationUnit = evaluationUnit;
    }

    public String getEvaluationUnit() 
    {
        return evaluationUnit;
    }
    public void setPosition(String position) 
    {
        this.position = position;
    }

    public String getPosition() 
    {
        return position;
    }
    public void setInspectionLocation(String inspectionLocation) 
    {
        this.inspectionLocation = inspectionLocation;
    }

    public String getInspectionLocation() 
    {
        return inspectionLocation;
    }
    public void setChemicalFactorName(String chemicalFactorName) 
    {
        this.chemicalFactorName = chemicalFactorName;
    }

    public String getChemicalFactorName() 
    {
        return chemicalFactorName;
    }
    public void setPhysicalFactorName(String physicalFactorName) 
    {
        this.physicalFactorName = physicalFactorName;
    }

    public String getPhysicalFactorName() 
    {
        return physicalFactorName;
    }
    public void setProductionDustName(String productionDustName) 
    {
        this.productionDustName = productionDustName;
    }

    public String getProductionDustName() 
    {
        return productionDustName;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks() 
    {
        return remarks;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("evaluationUnit", getEvaluationUnit())
            .append("position", getPosition())
            .append("inspectionLocation", getInspectionLocation())
            .append("chemicalFactorName", getChemicalFactorName())
            .append("physicalFactorName", getPhysicalFactorName())
            .append("productionDustName", getProductionDustName())
            .append("remarks", getRemarks())
            .toString();
    }
}
