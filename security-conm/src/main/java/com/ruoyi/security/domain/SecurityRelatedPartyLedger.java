package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 相关方管理台账对象 security_related_party_ledger
 * 
 * @author wang
 * @date 2025-03-05
 */
public class SecurityRelatedPartyLedger extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 主管科室 */
    @Excel(name = "主管科室")
    private String responsibleDepartment;

    /** 相关方名称 */
    @Excel(name = "相关方名称")
    private String relatedPartyName;

    /** 主要联系人 */
    @Excel(name = "主要联系人")
    private String mainContactPerson;

    /** 我公司对接人员 */
    @Excel(name = "我公司对接人员")
    private String ourCompanyContactPerson;

    /** 是否签订安全管理协议 */
    @Excel(name = "是否签订安全管理协议")
    private Integer isSafetyManagementAgreementSigned;

    /** 签订时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签订时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date agreementSigningDate;

    /** 进厂时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "进厂时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date factoryEntryStartDate;

    /** 外来参观 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "外来参观", width = 30, dateFormat = "yyyy-MM-dd")
    private Date factoryEntryEndDate;

    /** 实习生 */
    @Excel(name = "实习生")
    private String externalVisitors;

    /** 劳务派遣，外包 */
    @Excel(name = "劳务派遣，外包")
    private String interns;

    /** 施工作业类 */
    @Excel(name = "施工作业类")
    private String laborDispatchOutsourcing;

    /** 清运、监测、服务 */
    @Excel(name = "清运、监测、服务")
    private String constructionWork;

    /** 物流配送 */
    @Excel(name = "物流配送")
    private String transportationInspectionServices;

    /** 驻厂相关方 */
    @Excel(name = "驻厂相关方")
    private String logisticsDelivery;

    /** 其他 */
    @Excel(name = "其他")
    private String onSiteParties;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String others;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setResponsibleDepartment(String responsibleDepartment) 
    {
        this.responsibleDepartment = responsibleDepartment;
    }

    public String getResponsibleDepartment() 
    {
        return responsibleDepartment;
    }
    public void setRelatedPartyName(String relatedPartyName) 
    {
        this.relatedPartyName = relatedPartyName;
    }

    public String getRelatedPartyName() 
    {
        return relatedPartyName;
    }
    public void setMainContactPerson(String mainContactPerson) 
    {
        this.mainContactPerson = mainContactPerson;
    }

    public String getMainContactPerson() 
    {
        return mainContactPerson;
    }
    public void setOurCompanyContactPerson(String ourCompanyContactPerson) 
    {
        this.ourCompanyContactPerson = ourCompanyContactPerson;
    }

    public String getOurCompanyContactPerson() 
    {
        return ourCompanyContactPerson;
    }
    public void setIsSafetyManagementAgreementSigned(Integer isSafetyManagementAgreementSigned) 
    {
        this.isSafetyManagementAgreementSigned = isSafetyManagementAgreementSigned;
    }

    public Integer getIsSafetyManagementAgreementSigned() 
    {
        return isSafetyManagementAgreementSigned;
    }
    public void setAgreementSigningDate(Date agreementSigningDate) 
    {
        this.agreementSigningDate = agreementSigningDate;
    }

    public Date getAgreementSigningDate() 
    {
        return agreementSigningDate;
    }
    public void setFactoryEntryStartDate(Date factoryEntryStartDate) 
    {
        this.factoryEntryStartDate = factoryEntryStartDate;
    }

    public Date getFactoryEntryStartDate() 
    {
        return factoryEntryStartDate;
    }
    public void setFactoryEntryEndDate(Date factoryEntryEndDate) 
    {
        this.factoryEntryEndDate = factoryEntryEndDate;
    }

    public Date getFactoryEntryEndDate() 
    {
        return factoryEntryEndDate;
    }
    public void setExternalVisitors(String externalVisitors) 
    {
        this.externalVisitors = externalVisitors;
    }

    public String getExternalVisitors() 
    {
        return externalVisitors;
    }
    public void setInterns(String interns) 
    {
        this.interns = interns;
    }

    public String getInterns() 
    {
        return interns;
    }
    public void setLaborDispatchOutsourcing(String laborDispatchOutsourcing) 
    {
        this.laborDispatchOutsourcing = laborDispatchOutsourcing;
    }

    public String getLaborDispatchOutsourcing() 
    {
        return laborDispatchOutsourcing;
    }
    public void setConstructionWork(String constructionWork) 
    {
        this.constructionWork = constructionWork;
    }

    public String getConstructionWork() 
    {
        return constructionWork;
    }
    public void setTransportationInspectionServices(String transportationInspectionServices) 
    {
        this.transportationInspectionServices = transportationInspectionServices;
    }

    public String getTransportationInspectionServices() 
    {
        return transportationInspectionServices;
    }
    public void setLogisticsDelivery(String logisticsDelivery) 
    {
        this.logisticsDelivery = logisticsDelivery;
    }

    public String getLogisticsDelivery() 
    {
        return logisticsDelivery;
    }
    public void setOnSiteParties(String onSiteParties) 
    {
        this.onSiteParties = onSiteParties;
    }

    public String getOnSiteParties() 
    {
        return onSiteParties;
    }
    public void setOthers(String others) 
    {
        this.others = others;
    }

    public String getOthers() 
    {
        return others;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("responsibleDepartment", getResponsibleDepartment())
            .append("relatedPartyName", getRelatedPartyName())
            .append("mainContactPerson", getMainContactPerson())
            .append("ourCompanyContactPerson", getOurCompanyContactPerson())
            .append("isSafetyManagementAgreementSigned", getIsSafetyManagementAgreementSigned())
            .append("agreementSigningDate", getAgreementSigningDate())
            .append("factoryEntryStartDate", getFactoryEntryStartDate())
            .append("factoryEntryEndDate", getFactoryEntryEndDate())
            .append("externalVisitors", getExternalVisitors())
            .append("interns", getInterns())
            .append("laborDispatchOutsourcing", getLaborDispatchOutsourcing())
            .append("constructionWork", getConstructionWork())
            .append("transportationInspectionServices", getTransportationInspectionServices())
            .append("logisticsDelivery", getLogisticsDelivery())
            .append("onSiteParties", getOnSiteParties())
            .append("others", getOthers())
            .toString();
    }
}
