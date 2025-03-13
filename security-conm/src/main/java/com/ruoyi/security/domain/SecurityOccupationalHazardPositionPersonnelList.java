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
    private Long id;

    /** 班组 */
    @Excel(name = "班组")
    private String teamName;

    /** 姓名 */
    @Excel(name = "姓名")
    private String name;

    /** 性别 */
    @Excel(name = "性别")
    private String gender;

    /** 工种 */
    @Excel(name = "工种")
    private String jobType;

    /** 接害因素 */
    @Excel(name = "接害因素")
    private String exposureFactors;

    /** 上岗前 */
    @Excel(name = "上岗前")
    private String preJobHealthCheckup;

    /** 在岗中 */
    @Excel(name = "在岗中")
    private String duringJobHealthCheckup;

    /** 离岗时 */
    @Excel(name = "离岗时")
    private String postJobHealthCheckup;

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
            .toString();
    }
}
