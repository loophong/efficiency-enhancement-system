package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 环境职业健康安全记录清单对象 security_environmental_ohs_record_list
 * 
 * @author wang
 * @date 2025-03-07
 */
public class SecurityEnvironmentalOhsRecordList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 记录编号 */
    @Excel(name = "记录编号")
    private String recordNumber;

    /** 记录名称 */
    @Excel(name = "记录名称")
    private String recordName;

    /** 归口部门 */
    @Excel(name = "归口部门")
    private String responsibleDepartment;

    /** 保存期限 */
    @Excel(name = "保存期限")
    private String retentionPeriod;

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
    public void setRecordNumber(String recordNumber) 
    {
        this.recordNumber = recordNumber;
    }

    public String getRecordNumber() 
    {
        return recordNumber;
    }
    public void setRecordName(String recordName) 
    {
        this.recordName = recordName;
    }

    public String getRecordName() 
    {
        return recordName;
    }
    public void setResponsibleDepartment(String responsibleDepartment) 
    {
        this.responsibleDepartment = responsibleDepartment;
    }

    public String getResponsibleDepartment() 
    {
        return responsibleDepartment;
    }
    public void setRetentionPeriod(String retentionPeriod) 
    {
        this.retentionPeriod = retentionPeriod;
    }

    public String getRetentionPeriod()
    {
        return retentionPeriod;
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
            .append("recordNumber", getRecordNumber())
            .append("recordName", getRecordName())
            .append("responsibleDepartment", getResponsibleDepartment())
            .append("retentionPeriod", getRetentionPeriod())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
