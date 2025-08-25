package com.ruoyi.security.domain;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import com.alibaba.excel.annotation.ExcelIgnore;

/**
 * 安全/环境法律法规识别清单对象 secutity_legal_regulations_identification_list
 * 
 * @author wang
 * @date 2025-03-02
 */
public class SecutityLegalRegulationsIdentificationList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @ExcelIgnore
    private Long id;

    /** 法律法规名称 */
    @Excel(name = "法律法规名称")
    @ExcelProperty(value = "法律法规名称")
    private String regulationName;

    /** 文号 */
    @Excel(name = "文号")
    @ExcelProperty(value = "文号")
    private String documentNumber;

    /** 发布单位 */
    @Excel(name = "发布单位")
    @ExcelProperty(value = "发布单位")
    private String issuingUnit;

    /** 发布/修订日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发布/修订日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "发布/修订日期")
    private Date issuanceRevisionDate;

    /** 实施日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实施日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelProperty(value = "实施日期")
    private Date implementationDate;

    /** 效力 */
    @Excel(name = "效力")
    @ExcelProperty(value = "效力",index = 5)
    private String effectiveness;
    
    /** 关联ID */
    @ExcelIgnore
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
            .append("relatedId", getRelatedId())
            .toString();
    }
}
