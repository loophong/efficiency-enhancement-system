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
 * @date 2025-04-09
 */
public class SecurityRelatedPartyLedger extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    @Excel(name = "序号", type = Excel.Type.EXPORT)
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
    private String isSafetyManagementAgreementSigned;

    /** 签订时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "签订时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date agreementSigningDate;

    /** 进厂时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "进厂时间（起止时间）", width = 30, dateFormat = "yyyy-MM-dd")
    private Date factoryEntryStartDate;

    /** 外来参观 */
    @Excel(name = "外来参观")
    private String waiLai;

    /** 实习生 */
    @Excel(name = "实习生")
    private String shiXi;

    /** 劳务派遣、外包等 */
    @Excel(name = "劳务派遣、外包等")
    private String laoWu;

    /** 施工作业类 */
    @Excel(name = "施工作业类")
    private String shiGong;

    /** 清洁检测服务 */
    @Excel(name = "清运、检测、服务等")
    private String qinjieJianceFuwu;

    /** 物流配送 */
    @Excel(name = "物流配送")
    private String wuLiu;

    /** 驻厂相关方 */
    @Excel(name = "驻厂相关方")
    private String zhuCangXiangguanfang;

    /** 其他 */
    @Excel(name = "其他")
    private String other;

    /** 相关方活动区域 */
    @Excel(name = "相关方活动区域")
    private String otherActivity;

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
    public void setIsSafetyManagementAgreementSigned(String isSafetyManagementAgreementSigned)
    {
        this.isSafetyManagementAgreementSigned = isSafetyManagementAgreementSigned;
    }

    public String getIsSafetyManagementAgreementSigned()
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
    public void setWaiLai(String waiLai)
    {
        this.waiLai = waiLai;
    }

    public String getWaiLai()
    {
        return waiLai;
    }

    public void setShiXi(String shiXi)
    {
        this.shiXi = shiXi;
    }

    public String getShiXi()
    {
        return shiXi;
    }

    public void setLaoWu(String laoWu)
    {
        this.laoWu = laoWu;
    }

    public String getLaoWu()
    {
        return laoWu;
    }

    public void setShiGong(String shiGong)
    {
        this.shiGong = shiGong;
    }

    public String getShiGong()
    {
        return shiGong;
    }

    public void setQinjieJianceFuwu(String qinjieJianceFuwu)
    {
        this.qinjieJianceFuwu = qinjieJianceFuwu;
    }

    public String getQinjieJianceFuwu()
    {
        return qinjieJianceFuwu;
    }

    public void setWuLiu(String wuLiu)
    {
        this.wuLiu = wuLiu;
    }

    public String getWuLiu()
    {
        return wuLiu;
    }

    public void setZhuCangXiangguanfang(String zhuCangXiangguanfang)
    {
        this.zhuCangXiangguanfang = zhuCangXiangguanfang;
    }

    public String getZhuCangXiangguanfang()
    {
        return zhuCangXiangguanfang;
    }

    public void setOther(String other)
    {
        this.other = other;
    }

    public String getOther()
    {
        return other;
    }
    public void setOtherActivity(String otherActivity) 
    {
        this.otherActivity = otherActivity;
    }

    public String getOtherActivity()
    {
        return otherActivity;
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
            .append("responsibleDepartment", getResponsibleDepartment())
            .append("relatedPartyName", getRelatedPartyName())
            .append("mainContactPerson", getMainContactPerson())
            .append("ourCompanyContactPerson", getOurCompanyContactPerson())
            .append("isSafetyManagementAgreementSigned", getIsSafetyManagementAgreementSigned())
            .append("agreementSigningDate", getAgreementSigningDate())
            .append("factoryEntryStartDate", getFactoryEntryStartDate())
            .append("waiLai", getWaiLai())
            .append("shiXi", getShiXi())
            .append("laoWu", getLaoWu())
            .append("shiGong", getShiGong())
            .append("qinjieJianceFuwu", getQinjieJianceFuwu())
            .append("wuLiu", getWuLiu())
            .append("zhuCangXiangguanfang", getZhuCangXiangguanfang())
            .append("other", getOther())
            .append("otherActivity", getOtherActivity())
            .append("relatedId", getRelatedId())
            .append("remark", getRemark())
            .toString();
    }
}
