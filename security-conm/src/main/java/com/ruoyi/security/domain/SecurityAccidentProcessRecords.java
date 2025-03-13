package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 事故过程记录对象 security_accident_process_records
 * 
 * @author wang
 * @date 2025-03-13
 */
public class SecurityAccidentProcessRecords extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 事故名称 */
    @Excel(name = "事故名称")
    private String accidentName;

    /** 发生地点 */
    @Excel(name = "发生地点")
    private String location;

    /** 事故类别 */
    @Excel(name = "事故类别")
    private String accidentCategory;

    /** 事故伤害人员姓名 */
    @Excel(name = "事故伤害人员姓名")
    private String injuredPersonName;

    /** 性别 */
    @Excel(name = "性别")
    private String injuredPersonGender;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long injuredPersonAge;

    /** 工种 */
    @Excel(name = "工种")
    private String injuredPersonJobTitle;

    /** 工作描述 */
    @Excel(name = "工作描述")
    private String injuredPersonWorkDescription;

    /** 受伤部位 */
    @Excel(name = "受伤部位")
    private String injuredPart;

    /** 受过何种安全教育 */
    @Excel(name = "受过何种安全教育")
    private String safetyEducationReceivedInjured;

    /** 职工编码 */
    @Excel(name = "职工编码")
    private String employeeCode;

    /** 家庭详细地址 */
    @Excel(name = "家庭详细地址")
    private String homeAddress;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String contactNumber;

    /** 身份证号码 */
    @Excel(name = "身份证号码")
    private String idNumber;

    /** 工作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "工作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date workTime;

    /** 事故责任人员姓名 */
    @Excel(name = "事故责任人员姓名")
    private String responsiblePersonName;

    /** 性别 */
    @Excel(name = "性别")
    private String responsiblePersonGender;

    /** 年龄 */
    @Excel(name = "年龄")
    private Long responsiblePersonAge;

    /** 工种 */
    @Excel(name = "工种")
    private String responsiblePersonJobTitle;

    /** 伤害程度 */
    @Excel(name = "伤害程度")
    private String injurySeverity;

    /** 受过何种安全教育 */
    @Excel(name = "受过何种安全教育")
    private String safetyEducationReceivedResponsible;

    /** 参加工作时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "参加工作时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startWorkingDate;

    /** 事故发生过程 */
    @Excel(name = "事故发生过程")
    private String accidentProcess;

    /** 事故发生后抢救措施 */
    @Excel(name = "事故发生后抢救措施")
    private String postAccidentActions;

    /** 事故判定 */
    @Excel(name = "事故判定")
    private String accidentClassification;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date recordDate;

    /** 记录人 */
    @Excel(name = "记录人")
    private String recorder;

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
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }
    public void setAccidentCategory(String accidentCategory) 
    {
        this.accidentCategory = accidentCategory;
    }

    public String getAccidentCategory() 
    {
        return accidentCategory;
    }
    public void setInjuredPersonName(String injuredPersonName) 
    {
        this.injuredPersonName = injuredPersonName;
    }

    public String getInjuredPersonName() 
    {
        return injuredPersonName;
    }
    public void setInjuredPersonGender(String injuredPersonGender) 
    {
        this.injuredPersonGender = injuredPersonGender;
    }

    public String getInjuredPersonGender() 
    {
        return injuredPersonGender;
    }
    public void setInjuredPersonAge(Long injuredPersonAge) 
    {
        this.injuredPersonAge = injuredPersonAge;
    }

    public Long getInjuredPersonAge() 
    {
        return injuredPersonAge;
    }
    public void setInjuredPersonJobTitle(String injuredPersonJobTitle) 
    {
        this.injuredPersonJobTitle = injuredPersonJobTitle;
    }

    public String getInjuredPersonJobTitle() 
    {
        return injuredPersonJobTitle;
    }
    public void setInjuredPersonWorkDescription(String injuredPersonWorkDescription) 
    {
        this.injuredPersonWorkDescription = injuredPersonWorkDescription;
    }

    public String getInjuredPersonWorkDescription() 
    {
        return injuredPersonWorkDescription;
    }
    public void setInjuredPart(String injuredPart) 
    {
        this.injuredPart = injuredPart;
    }

    public String getInjuredPart() 
    {
        return injuredPart;
    }
    public void setSafetyEducationReceivedInjured(String safetyEducationReceivedInjured) 
    {
        this.safetyEducationReceivedInjured = safetyEducationReceivedInjured;
    }

    public String getSafetyEducationReceivedInjured() 
    {
        return safetyEducationReceivedInjured;
    }
    public void setEmployeeCode(String employeeCode) 
    {
        this.employeeCode = employeeCode;
    }

    public String getEmployeeCode() 
    {
        return employeeCode;
    }
    public void setHomeAddress(String homeAddress) 
    {
        this.homeAddress = homeAddress;
    }

    public String getHomeAddress() 
    {
        return homeAddress;
    }
    public void setContactNumber(String contactNumber) 
    {
        this.contactNumber = contactNumber;
    }

    public String getContactNumber() 
    {
        return contactNumber;
    }
    public void setIdNumber(String idNumber) 
    {
        this.idNumber = idNumber;
    }

    public String getIdNumber() 
    {
        return idNumber;
    }
    public void setWorkTime(Date workTime) 
    {
        this.workTime = workTime;
    }

    public Date getWorkTime() 
    {
        return workTime;
    }
    public void setResponsiblePersonName(String responsiblePersonName) 
    {
        this.responsiblePersonName = responsiblePersonName;
    }

    public String getResponsiblePersonName() 
    {
        return responsiblePersonName;
    }
    public void setResponsiblePersonGender(String responsiblePersonGender) 
    {
        this.responsiblePersonGender = responsiblePersonGender;
    }

    public String getResponsiblePersonGender() 
    {
        return responsiblePersonGender;
    }
    public void setResponsiblePersonAge(Long responsiblePersonAge) 
    {
        this.responsiblePersonAge = responsiblePersonAge;
    }

    public Long getResponsiblePersonAge() 
    {
        return responsiblePersonAge;
    }
    public void setResponsiblePersonJobTitle(String responsiblePersonJobTitle) 
    {
        this.responsiblePersonJobTitle = responsiblePersonJobTitle;
    }

    public String getResponsiblePersonJobTitle() 
    {
        return responsiblePersonJobTitle;
    }
    public void setInjurySeverity(String injurySeverity) 
    {
        this.injurySeverity = injurySeverity;
    }

    public String getInjurySeverity() 
    {
        return injurySeverity;
    }
    public void setSafetyEducationReceivedResponsible(String safetyEducationReceivedResponsible) 
    {
        this.safetyEducationReceivedResponsible = safetyEducationReceivedResponsible;
    }

    public String getSafetyEducationReceivedResponsible() 
    {
        return safetyEducationReceivedResponsible;
    }
    public void setStartWorkingDate(Date startWorkingDate) 
    {
        this.startWorkingDate = startWorkingDate;
    }

    public Date getStartWorkingDate() 
    {
        return startWorkingDate;
    }
    public void setAccidentProcess(String accidentProcess) 
    {
        this.accidentProcess = accidentProcess;
    }

    public String getAccidentProcess() 
    {
        return accidentProcess;
    }
    public void setPostAccidentActions(String postAccidentActions) 
    {
        this.postAccidentActions = postAccidentActions;
    }

    public String getPostAccidentActions() 
    {
        return postAccidentActions;
    }
    public void setAccidentClassification(String accidentClassification) 
    {
        this.accidentClassification = accidentClassification;
    }

    public String getAccidentClassification() 
    {
        return accidentClassification;
    }
    public void setRecordDate(Date recordDate) 
    {
        this.recordDate = recordDate;
    }

    public Date getRecordDate() 
    {
        return recordDate;
    }
    public void setRecorder(String recorder) 
    {
        this.recorder = recorder;
    }

    public String getRecorder() 
    {
        return recorder;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("accidentName", getAccidentName())
            .append("location", getLocation())
            .append("accidentCategory", getAccidentCategory())
            .append("injuredPersonName", getInjuredPersonName())
            .append("injuredPersonGender", getInjuredPersonGender())
            .append("injuredPersonAge", getInjuredPersonAge())
            .append("injuredPersonJobTitle", getInjuredPersonJobTitle())
            .append("injuredPersonWorkDescription", getInjuredPersonWorkDescription())
            .append("injuredPart", getInjuredPart())
            .append("safetyEducationReceivedInjured", getSafetyEducationReceivedInjured())
            .append("employeeCode", getEmployeeCode())
            .append("homeAddress", getHomeAddress())
            .append("contactNumber", getContactNumber())
            .append("idNumber", getIdNumber())
            .append("workTime", getWorkTime())
            .append("responsiblePersonName", getResponsiblePersonName())
            .append("responsiblePersonGender", getResponsiblePersonGender())
            .append("responsiblePersonAge", getResponsiblePersonAge())
            .append("responsiblePersonJobTitle", getResponsiblePersonJobTitle())
            .append("injurySeverity", getInjurySeverity())
            .append("safetyEducationReceivedResponsible", getSafetyEducationReceivedResponsible())
            .append("startWorkingDate", getStartWorkingDate())
            .append("accidentProcess", getAccidentProcess())
            .append("postAccidentActions", getPostAccidentActions())
            .append("accidentClassification", getAccidentClassification())
            .append("recordDate", getRecordDate())
            .append("recorder", getRecorder())
            .toString();
    }
}
