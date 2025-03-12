package com.heli.supplier.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 服务与协作对象 supplier_performance_services_collaboration
 * 
 * @author wll
 * @date 2025-03-05
 */
public class SupplierPerformanceServicesCollaboration extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private String id;

    /** 供应商代码 */
    @Excel(name = "供应商代码")
    private String supplierCode;

    /** 供应商名称 */
    @Excel(name = "供应商名称")
    private String supplierName;

    /** 记录时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "记录时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date time;

    /** 供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作 */
    @Excel(name = "供应商如对收到的函告、购销协议，未在要求时效节点内采取反馈动作")
    private Long letter;

    /** 供应商如受到相关方处罚 */
    @Excel(name = "供应商如受到相关方处罚")
    private Long punish;

    /** 供应商如对采购员需求反馈不及时 */
    @Excel(name = "供应商如对采购员需求反馈不及时")
    private Long feedbackNotTimely;

    /** 得分 */
    @Excel(name = "得分")
    private Long score;

    /** 具体内容 */
    @Excel(name = "具体内容")
    private String specificContent;

    /** 填报人 */
    @Excel(name = "填报人")
    private String uploadName;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setSupplierCode(String supplierCode) 
    {
        this.supplierCode = supplierCode;
    }

    public String getSupplierCode() 
    {
        return supplierCode;
    }
    public void setSupplierName(String supplierName) 
    {
        this.supplierName = supplierName;
    }

    public String getSupplierName() 
    {
        return supplierName;
    }
    public void setTime(Date time) 
    {
        this.time = time;
    }

    public Date getTime() 
    {
        return time;
    }
    public void setLetter(Long letter) 
    {
        this.letter = letter;
    }

    public Long getLetter() 
    {
        return letter;
    }
    public void setPunish(Long punish) 
    {
        this.punish = punish;
    }

    public Long getPunish() 
    {
        return punish;
    }
    public void setFeedbackNotTimely(Long feedbackNotTimely) 
    {
        this.feedbackNotTimely = feedbackNotTimely;
    }

    public Long getFeedbackNotTimely() 
    {
        return feedbackNotTimely;
    }
    public void setScore(Long score) 
    {
        this.score = score;
    }

    public Long getScore() 
    {
        return score;
    }
    public void setSpecificContent(String specificContent) 
    {
        this.specificContent = specificContent;
    }

    public String getSpecificContent() 
    {
        return specificContent;
    }
    public void setUploadName(String uploadName) 
    {
        this.uploadName = uploadName;
    }

    public String getUploadName() 
    {
        return uploadName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("supplierCode", getSupplierCode())
            .append("supplierName", getSupplierName())
            .append("time", getTime())
            .append("letter", getLetter())
            .append("punish", getPunish())
            .append("feedbackNotTimely", getFeedbackNotTimely())
            .append("score", getScore())
            .append("specificContent", getSpecificContent())
            .append("uploadName", getUploadName())
            .toString();
    }
}
