package com.heli.device.fileTable.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备导入模板文件对象 device_template
 * 
 * @author YYY
 * @date 2025-07-11
 */
public class DeviceTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private Long templateId;

    /** 自主保全计划模板 */
    @Excel(name = "自主保全计划模板")
    private String templateGroup;

    /** 主计划模板 */
    @Excel(name = "主计划模板")
    private String templateMajor;

    /** 维修记录模板 */
    @Excel(name = "维修记录模板")
    private String templateMaintenance;

    /** 指标模板 */
    @Excel(name = "指标模板")
    private String templateIndicator;

    /** 设备台账模板 */
    @Excel(name = "设备台账模板")
    private String templateDetails;

    public void setTemplateId(Long templateId) 
    {
        this.templateId = templateId;
    }

    public Long getTemplateId() 
    {
        return templateId;
    }
    public void setTemplateGroup(String templateGroup) 
    {
        this.templateGroup = templateGroup;
    }

    public String getTemplateGroup() 
    {
        return templateGroup;
    }
    public void setTemplateMajor(String templateMajor) 
    {
        this.templateMajor = templateMajor;
    }

    public String getTemplateMajor() 
    {
        return templateMajor;
    }
    public void setTemplateMaintenance(String templateMaintenance) 
    {
        this.templateMaintenance = templateMaintenance;
    }

    public String getTemplateMaintenance() 
    {
        return templateMaintenance;
    }
    public void setTemplateIndicator(String templateIndicator) 
    {
        this.templateIndicator = templateIndicator;
    }

    public String getTemplateIndicator() 
    {
        return templateIndicator;
    }
    public void setTemplateDetails(String templateDetails) 
    {
        this.templateDetails = templateDetails;
    }

    public String getTemplateDetails() 
    {
        return templateDetails;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("templateId", getTemplateId())
            .append("templateGroup", getTemplateGroup())
            .append("templateMajor", getTemplateMajor())
            .append("templateMaintenance", getTemplateMaintenance())
            .append("templateIndicator", getTemplateIndicator())
            .append("templateDetails", getTemplateDetails())
            .toString();
    }
}
