package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全责任清单对象 security_zeren_list
 * 
 * @author wang
 * @date 2025-07-18
 */
public class SecurityZerenList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 企业名称 */
    @Excel(name = "企业名称")
    private String qiYe;

    /** 科室 */
    @Excel(name = "科室")
    private String keShi;

    /** 岗位 */
    @Excel(name = "岗位")
    private String gangWei;

    /** 岗位负责人 */
    @Excel(name = "岗位负责人")
    private String gangweiFuze;

    /** 岗位责任人安全生产职责 */
    @Excel(name = "岗位责任人 安全生产职责")
    private String gangweiFuzeZizhe;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setQiYe(String qiYe) 
    {
        this.qiYe = qiYe;
    }

    public String getQiYe() 
    {
        return qiYe;
    }
    public void setKeShi(String keShi) 
    {
        this.keShi = keShi;
    }

    public String getKeShi() 
    {
        return keShi;
    }
    public void setGangWei(String gangWei) 
    {
        this.gangWei = gangWei;
    }

    public String getGangWei() 
    {
        return gangWei;
    }
    public void setGangweiFuze(String gangweiFuze) 
    {
        this.gangweiFuze = gangweiFuze;
    }

    public String getGangweiFuze() 
    {
        return gangweiFuze;
    }
    public void setGangweiFuzeZizhe(String gangweiFuzeZizhe) 
    {
        this.gangweiFuzeZizhe = gangweiFuzeZizhe;
    }

    public String getGangweiFuzeZizhe() 
    {
        return gangweiFuzeZizhe;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("qiYe", getQiYe())
            .append("keShi", getKeShi())
            .append("gangWei", getGangWei())
            .append("gangweiFuze", getGangweiFuze())
            .append("gangweiFuzeZizhe", getGangweiFuzeZizhe())
            .append("remark", getRemark())
            .toString();
    }
}
