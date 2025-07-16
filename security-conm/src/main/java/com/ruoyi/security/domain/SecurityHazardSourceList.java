package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 危险源清单对象 security_hazard_source_list
 * 
 * @author wang
 * @date 2025-03-01
 */
public class SecurityHazardSourceList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号")
    private Long id;

    /** 作业活动 */
    @Excel(name = "作业活动", needMerge = true)
    private String activity;

    /** 危险源 */
    @Excel(name = "危险源")
    private String hazardSource;

    /** 危害方式 */
    @Excel(name = "危害方式")
    private String hazardMode;

    /** 可能导致的事故 */
    @Excel(name = "可能导致的事故")
    private String possibleConsequences;

    /** 评价方式(LEC) */
//    @Excel(name = "评价方式(LEC)")
    private String evaluationMethod;

    /** L */
    @Excel(name = "L")
    private String evaluationA;

    /** E */
    @Excel(name = "E")
    private String evaluationB;

    /** C */
    @Excel(name = "C")
    private String evaluationC;

    /** 三角形 */
    @Excel(name = "三角形")
    private String evaluationSanjiaoxing;

    /** 风险级别 */
    @Excel(name = "风险级别")
    private String riskLevel;

    /** 控制措施 */
    @Excel(name = "控制措施")
    private String controlMeasures;

    /** 审核人 */
//    @Excel(name = "审核人")
    private String auditor;

    /** 批准人 */
//    @Excel(name = "批准人")
    private String approver;

    /** 时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @Excel(name = "时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;
    
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
    public void setActivity(String activity) 
    {
        this.activity = activity;
    }

    public String getActivity() 
    {
        return activity;
    }
    public void setHazardSource(String hazardSource) 
    {
        this.hazardSource = hazardSource;
    }

    public String getHazardSource() 
    {
        return hazardSource;
    }
    public void setHazardMode(String hazardMode) 
    {
        this.hazardMode = hazardMode;
    }

    public String getHazardMode() 
    {
        return hazardMode;
    }
    public void setPossibleConsequences(String possibleConsequences) 
    {
        this.possibleConsequences = possibleConsequences;
    }

    public String getPossibleConsequences() 
    {
        return possibleConsequences;
    }
    public void setEvaluationMethod(String evaluationMethod) 
    {
        this.evaluationMethod = evaluationMethod;
    }

    public String getEvaluationMethod() 
    {
        return evaluationMethod;
    }
    public void setEvaluationA(String evaluationA) 
    {
        this.evaluationA = evaluationA;
    }

    public String getEvaluationA() 
    {
        return evaluationA;
    }
    public void setEvaluationB(String evaluationB) 
    {
        this.evaluationB = evaluationB;
    }

    public String getEvaluationB() 
    {
        return evaluationB;
    }
    public void setEvaluationC(String evaluationC) 
    {
        this.evaluationC = evaluationC;
    }

    public String getEvaluationC() 
    {
        return evaluationC;
    }
    public void setEvaluationSanjiaoxing(String evaluationSanjiaoxing) 
    {
        this.evaluationSanjiaoxing = evaluationSanjiaoxing;
    }

    public String getEvaluationSanjiaoxing() 
    {
        return evaluationSanjiaoxing;
    }
    public void setRiskLevel(String riskLevel) 
    {
        this.riskLevel = riskLevel;
    }

    public String getRiskLevel() 
    {
        return riskLevel;
    }
    public void setControlMeasures(String controlMeasures) 
    {
        this.controlMeasures = controlMeasures;
    }

    public String getControlMeasures() 
    {
        return controlMeasures;
    }
    public void setAuditor(String auditor) 
    {
        this.auditor = auditor;
    }

    public String getAuditor() 
    {
        return auditor;
    }
    public void setApprover(String approver) 
    {
        this.approver = approver;
    }

    public String getApprover() 
    {
        return approver;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
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
            .append("activity", getActivity())
            .append("hazardSource", getHazardSource())
            .append("hazardMode", getHazardMode())
            .append("possibleConsequences", getPossibleConsequences())
            .append("evaluationMethod", getEvaluationMethod())
            .append("evaluationA", getEvaluationA())
            .append("evaluationB", getEvaluationB())
            .append("evaluationC", getEvaluationC())
            .append("evaluationSanjiaoxing", getEvaluationSanjiaoxing())
            .append("riskLevel", getRiskLevel())
            .append("controlMeasures", getControlMeasures())
            .append("auditor", getAuditor())
            .append("approver", getApprover())
            .append("time", getTime())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
