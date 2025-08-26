package com.ruoyi.security.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 作废受控文件收回销毁登记对象 security_obsolete_controlled_document_disposal_register
 *
 * @author wang
 * @date 2025-04-12
 */
public class SecurityObsoleteControlledDocumentDisposalRegister extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 文件名称 */
    @Excel(name = "文件名称")
    @ExcelProperty(index = 0)
    private String fileName;

    /** 编号 */
    @Excel(name = "编号")
    @ExcelProperty(index = 1)
    private String documentNumber;

    /** 回收部门 */
    @Excel(name = "回收部门")
    @ExcelProperty(index = 2)
    private String recyclingDepartment;

    /** 是否销毁 */
    @Excel(name = "是否销毁")
    @ExcelProperty(index = 3)
    private String isDestroyed;

    /** 备注 */
    @Excel(name = "备注")
    @ExcelProperty(index = 4)
    private String remarks;

    /** 关联ID */
    private Long relatedId;

    /** 时间 */
//    @Excel(name = "时间")
    private String time;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getFileName()
    {
        return fileName;
    }
    public void setDocumentNumber(String documentNumber)
    {
        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber()
    {
        return documentNumber;
    }
    public void setRecyclingDepartment(String recyclingDepartment)
    {
        this.recyclingDepartment = recyclingDepartment;
    }

    public String getRecyclingDepartment()
    {
        return recyclingDepartment;
    }
    public void setIsDestroyed(String isDestroyed)
    {
        this.isDestroyed = isDestroyed;
    }

    public String getIsDestroyed()
    {
        return isDestroyed;
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

    public String getTime() 
    {
        return time;
    }

    public void setTime(String time) 
    {
        this.time = time;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("fileName", getFileName())
                .append("documentNumber", getDocumentNumber())
                .append("recyclingDepartment", getRecyclingDepartment())
                .append("isDestroyed", getIsDestroyed())
                .append("remarks", getRemarks())
                .append("relatedId", getRelatedId())
                .append("time", getTime())
                .toString();
    }
}
