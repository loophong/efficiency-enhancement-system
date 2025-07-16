package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 合规性评价记录对象 security_compliance_evaluation_records
 * 
 * @author wang
 * @date 2025-03-02
 */
public class SecurityComplianceEvaluationRecords extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 法律法规名称 */
    @Excel(name = "法律法规名称")
    @ExcelProperty(value = "法律法规名称", index = 0)
    private String regulationName;

    /** 文号 */
    @Excel(name = "文号")
    @ExcelProperty(value = "文号", index = 1)
    private String documentNumber;

    /** 发布单位 */
    @Excel(name = "发布单位")
    @ExcelProperty(value = "发布单位", index = 2)
    private String issuingUnit;

    /** 发布/修订日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布/修订日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "发布/修订日期", index = 4)
    @DateTimeFormat("yyyy-MM-dd")
    private Date issuanceRevisionDate;

    /** 实施日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实施日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "实施日期", index = 5)
    @DateTimeFormat("yyyy-MM-dd")
    private Date implementationDate;

    /** 效力 */
    @Excel(name = "效力")
    @ExcelProperty(value = "效力", index = 5)
    private String effectiveness;

    /** 是否合规（值应为「是」或「否」） */
    @Excel(name = "是否合规", readConverterExp = "是=是,否=否,y=是,n=否,yes=是,no=否,true=是,false=否,1=是,0=否")
    @ExcelProperty(value = "是否合规", index = 6)
    private String applicableClauses;

    /** 备注 */
    @Excel(name = "备注")
    @ExcelProperty(value = "备注", index = 7)
    private String complianceStatus;
    
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
    public void setRegulationName(String regulationName) 
    {
        this.regulationName = regulationName;
    }

    public String getRegulationName() 
    {
        return regulationName;
    }
    public void setDocumentNumber(String documentNumber) 
    {
        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber() 
    {
        return documentNumber;
    }
    public void setIssuingUnit(String issuingUnit) 
    {
        this.issuingUnit = issuingUnit;
    }

    public String getIssuingUnit() 
    {
        return issuingUnit;
    }
    public void setIssuanceRevisionDate(Date issuanceRevisionDate) 
    {
        this.issuanceRevisionDate = issuanceRevisionDate;
    }

    public Date getIssuanceRevisionDate() 
    {
        return issuanceRevisionDate;
    }
    public void setImplementationDate(Date implementationDate) 
    {
        this.implementationDate = implementationDate;
    }

    public Date getImplementationDate() 
    {
        return implementationDate;
    }
    public void setEffectiveness(String effectiveness) 
    {
        this.effectiveness = effectiveness;
    }

    public String getEffectiveness() 
    {
        return effectiveness;
    }
    public void setApplicableClauses(String applicableClauses) 
    {
        this.applicableClauses = applicableClauses;
    }

    public String getApplicableClauses() 
    {
        return applicableClauses;
    }
    public void setComplianceStatus(String complianceStatus) 
    {
        this.complianceStatus = complianceStatus;
    }

    public String getComplianceStatus() 
    {
        return complianceStatus;
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
            .append("regulationName", getRegulationName())
            .append("documentNumber", getDocumentNumber())
            .append("issuingUnit", getIssuingUnit())
            .append("issuanceRevisionDate", getIssuanceRevisionDate())
            .append("implementationDate", getImplementationDate())
            .append("effectiveness", getEffectiveness())
            .append("applicableClauses", getApplicableClauses())
            .append("complianceStatus", getComplianceStatus())
            .toString();
    }
}
