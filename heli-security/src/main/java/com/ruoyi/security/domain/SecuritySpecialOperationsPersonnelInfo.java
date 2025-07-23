package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 特种作业人员信息对象 security_special_operations_personnel_info
 * 
 * @author wang
 * @date 2025-03-05
 */
public class SecuritySpecialOperationsPersonnelInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号", type = Excel.Type.EXPORT)
    private Long id;

    /** 部门 */
    @Excel(name = "部门")
    private String department;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 发证机关 */
    @Excel(name = "发证机关")
    private String issuingAuthority;

    /** 证件类型 */
    @Excel(name = "证件类型")
    private String certificateType;

    /** 作业类别 */
    @Excel(name = "作业类别")
    private String operationCategory;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idNumber;

    /** 证件编号 */
    @Excel(name = "证件编号")
    private String certificateNumber;

    /** 取证时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "取证时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date issuanceDate;

    /** 第一次到期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "第一次到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date firstExpirationDate;

    /** 第一次复审时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "第一次复审时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date firstRenewalDate;

    /** 第二次到期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "第二次到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date secondExpirationDate;

    /** 第二次复审时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "第二次复审时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date secondRenewalDate;

    /** 第三次到期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "第三次到期时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date thirdExpirationDate;

    /** 第三次复审时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "第三次复审时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date thirdRenewalDate;

    /** 备注 */
    @Excel(name = "备注")
    private String remarks;

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
    public void setDepartment(String department) 
    {
        this.department = department;
    }

    public String getDepartment() 
    {
        return department;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setGender(String gender) 
    {
        this.gender = gender;
    }

    public String getGender() 
    {
        return gender;
    }
    public void setIssuingAuthority(String issuingAuthority) 
    {
        this.issuingAuthority = issuingAuthority;
    }

    public String getIssuingAuthority() 
    {
        return issuingAuthority;
    }
    public void setCertificateType(String certificateType) 
    {
        this.certificateType = certificateType;
    }

    public String getCertificateType() 
    {
        return certificateType;
    }
    public void setOperationCategory(String operationCategory) 
    {
        this.operationCategory = operationCategory;
    }

    public String getOperationCategory() 
    {
        return operationCategory;
    }
    public void setIdNumber(String idNumber) 
    {
        this.idNumber = idNumber;
    }

    public String getIdNumber() 
    {
        return idNumber;
    }
    public void setCertificateNumber(String certificateNumber) 
    {
        this.certificateNumber = certificateNumber;
    }

    public String getCertificateNumber() 
    {
        return certificateNumber;
    }
    public void setIssuanceDate(Date issuanceDate) 
    {
        this.issuanceDate = issuanceDate;
    }

    public Date getIssuanceDate() 
    {
        return issuanceDate;
    }
    public void setFirstExpirationDate(Date firstExpirationDate) 
    {
        this.firstExpirationDate = firstExpirationDate;
    }

    public Date getFirstExpirationDate() 
    {
        return firstExpirationDate;
    }
    public void setFirstRenewalDate(Date firstRenewalDate) 
    {
        this.firstRenewalDate = firstRenewalDate;
    }

    public Date getFirstRenewalDate() 
    {
        return firstRenewalDate;
    }
    public void setSecondExpirationDate(Date secondExpirationDate) 
    {
        this.secondExpirationDate = secondExpirationDate;
    }

    public Date getSecondExpirationDate() 
    {
        return secondExpirationDate;
    }
    public void setSecondRenewalDate(Date secondRenewalDate) 
    {
        this.secondRenewalDate = secondRenewalDate;
    }

    public Date getSecondRenewalDate() 
    {
        return secondRenewalDate;
    }
    public void setThirdExpirationDate(Date thirdExpirationDate) 
    {
        this.thirdExpirationDate = thirdExpirationDate;
    }

    public Date getThirdExpirationDate() 
    {
        return thirdExpirationDate;
    }
    public void setThirdRenewalDate(Date thirdRenewalDate) 
    {
        this.thirdRenewalDate = thirdRenewalDate;
    }

    public Date getThirdRenewalDate() 
    {
        return thirdRenewalDate;
    }
    public void setRemarks(String remarks) 
    {
        this.remarks = remarks;
    }

    public String getRemarks()
    {
        return remarks;
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
            .append("department", getDepartment())
            .append("name", getName())
            .append("gender", getGender())
            .append("issuingAuthority", getIssuingAuthority())
            .append("certificateType", getCertificateType())
            .append("operationCategory", getOperationCategory())
            .append("idNumber", getIdNumber())
            .append("certificateNumber", getCertificateNumber())
            .append("issuanceDate", getIssuanceDate())
            .append("firstExpirationDate", getFirstExpirationDate())
            .append("firstRenewalDate", getFirstRenewalDate())
            .append("secondExpirationDate", getSecondExpirationDate())
            .append("secondRenewalDate", getSecondRenewalDate())
            .append("thirdExpirationDate", getThirdExpirationDate())
            .append("thirdRenewalDate", getThirdRenewalDate())
            .append("remarks", getRemarks())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
