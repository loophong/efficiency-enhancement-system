package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 危险职业岗位人员清单及管理台帐对象 security_occupational_hazard_position_personnel_list
 * 
 * @author wang
 * @date 2025-03-05
 */
public class SecurityOccupationalHazardPositionPersonnelList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
//    @Excel(name = "序号", width = 10)
    private Long id;

    /** 班组 */
    @Excel(name = "班组", width = 20, prompt = "请输入班组名称，相同班组的人员可以合并单元格")
    private String teamName;

    /** 姓名 */
    @Excel(name = "姓名", width = 12, prompt = "请输入人员姓名，必填项")
    private String name;

    /** 性别 */
    @Excel(name = "性别", width = 8, prompt = "请输入男或女")
    private String gender;

    /** 工种 */
    @Excel(name = "工种", width = 15, prompt = "请输入具体工种名称")
    private String jobType;

    /** 接害因素（毒害、噪声、高温、粉尘、特殊工种） */
    @Excel(name = "接害因素", width = 35, prompt = "请详细描述接触的有害因素，如：毒害、噪声、高温、粉尘、特殊工种等")
    private String exposureFactors;

    /** 上岗前 */
    @Excel(name = "上岗前", width = 15, prompt = "请输入√表示已完成健康检查，空白表示未完成")
    private String preJobHealthCheckup;

    /** 在岗中 */
    @Excel(name = "在岗中", width = 15, prompt = "请输入√表示已完成健康检查，空白表示未完成")
    private String duringJobHealthCheckup;

    /** 离岗时 */
    @Excel(name = "离岗时", width = 15, prompt = "请输入√表示已完成健康检查，空白表示未完成")
    private String postJobHealthCheckup;

    /** 备注 */
    @Excel(name = "备注", width = 25, prompt = "可选填写补充说明信息")
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
    public void setTeamName(String teamName) 
    {
        this.teamName = teamName;
    }

    public String getTeamName() 
    {
        return teamName;
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
    public void setJobType(String jobType) 
    {
        this.jobType = jobType;
    }

    public String getJobType() 
    {
        return jobType;
    }
    public void setExposureFactors(String exposureFactors) 
    {
        this.exposureFactors = exposureFactors;
    }

    public String getExposureFactors() 
    {
        return exposureFactors;
    }
    public void setPreJobHealthCheckup(String preJobHealthCheckup) 
    {
        this.preJobHealthCheckup = preJobHealthCheckup;
    }

    public String getPreJobHealthCheckup() 
    {
        return preJobHealthCheckup;
    }
    public void setDuringJobHealthCheckup(String duringJobHealthCheckup) 
    {
        this.duringJobHealthCheckup = duringJobHealthCheckup;
    }

    public String getDuringJobHealthCheckup() 
    {
        return duringJobHealthCheckup;
    }
    public void setPostJobHealthCheckup(String postJobHealthCheckup) 
    {
        this.postJobHealthCheckup = postJobHealthCheckup;
    }

    public String getPostJobHealthCheckup() 
    {
        return postJobHealthCheckup;
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
            .append("teamName", getTeamName())
            .append("name", getName())
            .append("gender", getGender())
            .append("jobType", getJobType())
            .append("exposureFactors", getExposureFactors())
            .append("preJobHealthCheckup", getPreJobHealthCheckup())
            .append("duringJobHealthCheckup", getDuringJobHealthCheckup())
            .append("postJobHealthCheckup", getPostJobHealthCheckup())
            .append("remarks", getRemarks())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
