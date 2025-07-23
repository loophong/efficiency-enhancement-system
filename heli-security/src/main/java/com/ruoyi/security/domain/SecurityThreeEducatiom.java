package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 新职工三级安全教育台账对象 security_three_educatiom
 * 
 * @author wang
 * @date 2025-07-18
 */
public class SecurityThreeEducatiom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private String id;

    /** 姓名 */
    @Excel(name = "姓名")
    private String xingMing;

    /** 班组 */
    @Excel(name = "所在班组")
    private String banZu;

    /** 工作 */
    @Excel(name = "工种")
    private String gongZhong;

    /** 进厂时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "进厂时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jingCTime;

    /** 一级教育时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "一级教育时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date educationOne;

    /** 二级教育时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "二级教育时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date educationTwo;

    /** 三期教育时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "三级教育时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date educationThree;

    /** 是否合规 */
    @Excel(name = "是否合格")
    private String heGui;

    /** 来源 */
    @Excel(name = "来源")
    private String source;

    /** 关联id */
    private String relatedId;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setXingMing(String xingMing) 
    {
        this.xingMing = xingMing;
    }

    public String getXingMing() 
    {
        return xingMing;
    }
    public void setBanZu(String banZu) 
    {
        this.banZu = banZu;
    }

    public String getBanZu() 
    {
        return banZu;
    }
    public void setGongZhong(String gongZhong) 
    {
        this.gongZhong = gongZhong;
    }

    public String getGongZhong() 
    {
        return gongZhong;
    }
    public void setJingCTime(Date jingCTime) 
    {
        this.jingCTime = jingCTime;
    }

    public Date getJingCTime() 
    {
        return jingCTime;
    }
    public void setEducationOne(Date educationOne)
    {
        this.educationOne = educationOne;
    }

    public Date getEducationOne()
    {
        return educationOne;
    }
    public void setEducationTwo(Date educationTwo)
    {
        this.educationTwo = educationTwo;
    }

    public Date getEducationTwo()
    {
        return educationTwo;
    }
    public void setEducationThree(Date educationThree)
    {
        this.educationThree = educationThree;
    }

    public Date getEducationThree()
    {
        return educationThree;
    }
    public void setHeGui(String heGui) 
    {
        this.heGui = heGui;
    }

    public String getHeGui() 
    {
        return heGui;
    }
    public void setSource(String source) 
    {
        this.source = source;
    }

    public String getSource() 
    {
        return source;
    }
    public void setRelatedId(String relatedId) 
    {
        this.relatedId = relatedId;
    }

    public String getRelatedId() 
    {
        return relatedId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("xingMing", getXingMing())
            .append("banZu", getBanZu())
            .append("gongZhong", getGongZhong())
            .append("jingCTime", getJingCTime())
            .append("educationOne", getEducationOne())
            .append("educationTwo", getEducationTwo())
            .append("educationThree", getEducationThree())
            .append("heGui", getHeGui())
            .append("source", getSource())
            .append("remark", getRemark())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
