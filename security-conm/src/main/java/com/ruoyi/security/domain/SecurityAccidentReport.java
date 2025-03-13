package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 事故处理报告对象 security_accident_report
 * 
 * @author wang
 * @date 2025-03-13
 */
public class SecurityAccidentReport extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 事故名称 */
    @Excel(name = "事故名称")
    private String accidentName;

    /** 事故时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "事故时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date accidentTime;

    /** 伤亡情况 */
    @Excel(name = "伤亡情况")
    private String casualties;

    /** 事故损失 */
    @Excel(name = "事故损失")
    private String accidentLoss;

    /** 事故经过 */
    @Excel(name = "事故经过")
    private String accidentDescription;

    /** 事故现场环境 */
    @Excel(name = "事故现场环境")
    private String accidentSceneEnvironment;

    /** 事故原因 */
    @Excel(name = "事故原因")
    private String accidentCause;

    /** 事故责任 */
    @Excel(name = "事故责任")
    private String accidentResponsibility;

    /** 事故处理 */
    @Excel(name = "事故处理")
    private String accidentHandling;

    /** 事故防范措施 */
    @Excel(name = "事故防范措施")
    private String accidentPreventiveMeasures;

    /** 事故调查人（签字） */
    @Excel(name = "事故调查人", readConverterExp = "签=字")
    private String investigatorSignature;

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
    public void setAccidentTime(Date accidentTime) 
    {
        this.accidentTime = accidentTime;
    }

    public Date getAccidentTime() 
    {
        return accidentTime;
    }
    public void setCasualties(String casualties) 
    {
        this.casualties = casualties;
    }

    public String getCasualties() 
    {
        return casualties;
    }
    public void setAccidentLoss(String accidentLoss) 
    {
        this.accidentLoss = accidentLoss;
    }

    public String getAccidentLoss() 
    {
        return accidentLoss;
    }
    public void setAccidentDescription(String accidentDescription) 
    {
        this.accidentDescription = accidentDescription;
    }

    public String getAccidentDescription() 
    {
        return accidentDescription;
    }
    public void setAccidentSceneEnvironment(String accidentSceneEnvironment) 
    {
        this.accidentSceneEnvironment = accidentSceneEnvironment;
    }

    public String getAccidentSceneEnvironment() 
    {
        return accidentSceneEnvironment;
    }
    public void setAccidentCause(String accidentCause) 
    {
        this.accidentCause = accidentCause;
    }

    public String getAccidentCause() 
    {
        return accidentCause;
    }
    public void setAccidentResponsibility(String accidentResponsibility) 
    {
        this.accidentResponsibility = accidentResponsibility;
    }

    public String getAccidentResponsibility() 
    {
        return accidentResponsibility;
    }
    public void setAccidentHandling(String accidentHandling) 
    {
        this.accidentHandling = accidentHandling;
    }

    public String getAccidentHandling() 
    {
        return accidentHandling;
    }
    public void setAccidentPreventiveMeasures(String accidentPreventiveMeasures) 
    {
        this.accidentPreventiveMeasures = accidentPreventiveMeasures;
    }

    public String getAccidentPreventiveMeasures() 
    {
        return accidentPreventiveMeasures;
    }
    public void setInvestigatorSignature(String investigatorSignature) 
    {
        this.investigatorSignature = investigatorSignature;
    }

    public String getInvestigatorSignature() 
    {
        return investigatorSignature;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("accidentName", getAccidentName())
            .append("accidentTime", getAccidentTime())
            .append("casualties", getCasualties())
            .append("accidentLoss", getAccidentLoss())
            .append("accidentDescription", getAccidentDescription())
            .append("accidentSceneEnvironment", getAccidentSceneEnvironment())
            .append("accidentCause", getAccidentCause())
            .append("accidentResponsibility", getAccidentResponsibility())
            .append("accidentHandling", getAccidentHandling())
            .append("accidentPreventiveMeasures", getAccidentPreventiveMeasures())
            .append("investigatorSignature", getInvestigatorSignature())
            .toString();
    }
}
