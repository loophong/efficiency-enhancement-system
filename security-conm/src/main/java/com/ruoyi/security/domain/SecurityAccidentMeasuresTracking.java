package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 事故对策及跟踪记录对象 security_accident_measures_tracking
 * 
 * @author wang
 * @date 2025-03-13
 */
public class SecurityAccidentMeasuresTracking extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 事故名称 */
    @Excel(name = "事故名称")
    private String accidentName;

    /** 发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date occurrenceTime;

    /** 整改措施 */
    @Excel(name = "整改措施")
    private String correctiveMeasures;

    /** 整改责任单位/责任人 */
    @Excel(name = "整改责任单位/责任人")
    private String responsibleUnitPerson;

    /** 整改完成情况 */
    @Excel(name = "整改完成情况")
    private String completionStatus;

    /** 整改完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "整改完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date completionTime;

    /** 验收人 */
    @Excel(name = "验收人")
    private String inspector;

    /** 验收时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "验收时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date inspectionTime;

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
    public void setAccidentName(String accidentName) 
    {
        this.accidentName = accidentName;
    }

    public String getAccidentName() 
    {
        return accidentName;
    }
    public void setOccurrenceTime(Date occurrenceTime) 
    {
        this.occurrenceTime = occurrenceTime;
    }

    public Date getOccurrenceTime() 
    {
        return occurrenceTime;
    }
    public void setCorrectiveMeasures(String correctiveMeasures) 
    {
        this.correctiveMeasures = correctiveMeasures;
    }

    public String getCorrectiveMeasures() 
    {
        return correctiveMeasures;
    }
    public void setResponsibleUnitPerson(String responsibleUnitPerson) 
    {
        this.responsibleUnitPerson = responsibleUnitPerson;
    }

    public String getResponsibleUnitPerson() 
    {
        return responsibleUnitPerson;
    }
    public void setCompletionStatus(String completionStatus) 
    {
        this.completionStatus = completionStatus;
    }

    public String getCompletionStatus() 
    {
        return completionStatus;
    }
    public void setCompletionTime(Date completionTime) 
    {
        this.completionTime = completionTime;
    }

    public Date getCompletionTime() 
    {
        return completionTime;
    }
    public void setInspector(String inspector) 
    {
        this.inspector = inspector;
    }

    public String getInspector() 
    {
        return inspector;
    }
    public void setInspectionTime(Date inspectionTime) 
    {
        this.inspectionTime = inspectionTime;
    }

    public Date getInspectionTime() 
    {
        return inspectionTime;
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
            .append("accidentName", getAccidentName())
            .append("occurrenceTime", getOccurrenceTime())
            .append("correctiveMeasures", getCorrectiveMeasures())
            .append("responsibleUnitPerson", getResponsibleUnitPerson())
            .append("completionStatus", getCompletionStatus())
            .append("completionTime", getCompletionTime())
            .append("inspector", getInspector())
            .append("inspectionTime", getInspectionTime())
            .append("remarks", getRemarks())
            .toString();
    }
}
