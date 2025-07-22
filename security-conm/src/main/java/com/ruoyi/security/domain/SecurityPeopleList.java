package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 班组人员清单对象 security_people_list
 * 
 * @author wang
 * @date 2025-07-21
 */
public class SecurityPeopleList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private String id;

    /** 班组 */
    @Excel(name = "班组")
    private String banZu;

    /** 姓名 */
    @Excel(name = "姓名")
    private String xingMing;

    /** 性别 */
    @Excel(name = "性别")
    private String xingBie;

    /** 出生年月 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生年月", width = 30, dateFormat = "yyyy-MM-dd")
    private Date chuShen;

    /** 何时进厂 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "何时进厂", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jingCTime;

    /** 何时进班组 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "何时进班组", width = 30, dateFormat = "yyyy-MM-dd")
    private Date jingBanTime;

    /** 文化程度 */
    @Excel(name = "文化程度")
    private String wenhuaChengdu;

    /** 原工种 */
    @Excel(name = "原工种")
    private String yuanGongZhong;

    /** 现工种 */
    @Excel(name = "现工种")
    private String xianGongZhong;

    /** 技术等级 */
    @Excel(name = "技术等级")
    private String level;

    /** 家庭住址及联系电话 */
    @Excel(name = "家庭住址及联系电话")
    private String homePhone;

    /** 何时调出本班组 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "何时调出本班组", width = 30, dateFormat = "yyyy-MM-dd")
    private Date diaoChuBanzu;

    /** 关联id */
//    @Excel(name = "关联id")
    private String relatedId;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setBanZu(String banZu) 
    {
        this.banZu = banZu;
    }

    public String getBanZu() 
    {
        return banZu;
    }
    public void setXingMing(String xingMing) 
    {
        this.xingMing = xingMing;
    }

    public String getXingMing() 
    {
        return xingMing;
    }
    public void setXingBie(String xingBie) 
    {
        this.xingBie = xingBie;
    }

    public String getXingBie() 
    {
        return xingBie;
    }
    public void setChuShen(Date chuShen) 
    {
        this.chuShen = chuShen;
    }

    public Date getChuShen() 
    {
        return chuShen;
    }
    public void setJingCTime(Date jingCTime) 
    {
        this.jingCTime = jingCTime;
    }

    public Date getJingCTime() 
    {
        return jingCTime;
    }
    public void setJingBanTime(Date jingBanTime) 
    {
        this.jingBanTime = jingBanTime;
    }

    public Date getJingBanTime() 
    {
        return jingBanTime;
    }
    public void setWenhuaChengdu(String wenhuaChengdu) 
    {
        this.wenhuaChengdu = wenhuaChengdu;
    }

    public String getWenhuaChengdu() 
    {
        return wenhuaChengdu;
    }
    public void setYuanGongZhong(String yuanGongZhong) 
    {
        this.yuanGongZhong = yuanGongZhong;
    }

    public String getYuanGongZhong() 
    {
        return yuanGongZhong;
    }
    public void setXianGongZhong(String xianGongZhong) 
    {
        this.xianGongZhong = xianGongZhong;
    }

    public String getXianGongZhong() 
    {
        return xianGongZhong;
    }
    public void setLevel(String level) 
    {
        this.level = level;
    }

    public String getLevel() 
    {
        return level;
    }
    public void setHomePhone(String homePhone) 
    {
        this.homePhone = homePhone;
    }

    public String getHomePhone() 
    {
        return homePhone;
    }
    public void setDiaoChuBanzu(Date diaoChuBanzu) 
    {
        this.diaoChuBanzu = diaoChuBanzu;
    }

    public Date getDiaoChuBanzu() 
    {
        return diaoChuBanzu;
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
            .append("banZu", getBanZu())
            .append("xingMing", getXingMing())
            .append("xingBie", getXingBie())
            .append("chuShen", getChuShen())
            .append("jingCTime", getJingCTime())
            .append("jingBanTime", getJingBanTime())
            .append("wenhuaChengdu", getWenhuaChengdu())
            .append("yuanGongZhong", getYuanGongZhong())
            .append("xianGongZhong", getXianGongZhong())
            .append("level", getLevel())
            .append("homePhone", getHomePhone())
            .append("diaoChuBanzu", getDiaoChuBanzu())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
