package com.ruoyi.security.domain;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 危化品检查记录对象 security_hazardous_materials_safety_inspection
 * 
 * @author wang
 * @date 2025-03-03
 */
public class SecurityHazardousMaterialsSafetyInspection extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 检查项目 */
    @Excel(name = "检查项目")
    @ExcelProperty(index = 0)
    private String inspectionItem;

    /** 检查标准 */
    @Excel(name = "检查标准")
    @ExcelProperty(index = 1)
    private String inspectionStandard;

    /** 检查挤入 */
    @Excel(name = "检查记录")
    @ExcelProperty(index = 2)
    private String inspectionRecord;

    /** 检查人 */
//    @Excel(name = "检查人")
    private String inspecter;

    /** 检查时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "检查时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectionTime;

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
    public void setInspectionItem(String inspectionItem) 
    {
        this.inspectionItem = inspectionItem;
    }

    public String getInspectionItem() 
    {
        return inspectionItem;
    }
    public void setInspectionStandard(String inspectionStandard) 
    {
        this.inspectionStandard = inspectionStandard;
    }

    public String getInspectionStandard() 
    {
        return inspectionStandard;
    }
    public void setInspectionRecord(String inspectionRecord) 
    {
        this.inspectionRecord = inspectionRecord;
    }

    public String getInspectionRecord() 
    {
        return inspectionRecord;
    }
    public void setInspecter(String inspecter) 
    {
        this.inspecter = inspecter;
    }

    public String getInspecter() 
    {
        return inspecter;
    }
    public void setInspectionTime(Date inspectionTime) 
    {
        this.inspectionTime = inspectionTime;
    }

    public Date getInspectionTime()
    {
        return inspectionTime;
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
            .append("inspectionItem", getInspectionItem())
            .append("inspectionStandard", getInspectionStandard())
            .append("inspectionRecord", getInspectionRecord())
            .append("inspecter", getInspecter())
            .append("inspectionTime", getInspectionTime())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
