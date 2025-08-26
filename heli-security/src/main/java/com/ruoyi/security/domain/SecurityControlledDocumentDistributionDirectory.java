package com.ruoyi.security.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 受控文件发放目录对象 security_controlled_document_distribution_directory
 * 
 * @author wang
 * @date 2025-03-02
 */
public class SecurityControlledDocumentDistributionDirectory extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 文件名称 */
    @Excel(name = "文件名称")
    @ExcelProperty(value = "文件名称")
    private String fileName;

    /** 编号 */
    @Excel(name = "编号")
    @ExcelProperty(value = "编号")
    private String documentNumber;

    /** 接收单位 */
    @Excel(name = "接收单位")
    @ExcelProperty(value = "接收单位")
    private String receivingUnit;

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
    public void setReceivingUnit(String receivingUnit) 
    {
        this.receivingUnit = receivingUnit;
    }

    public String getReceivingUnit()
    {
        return receivingUnit;
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
            .append("receivingUnit", getReceivingUnit())
            .append("relatedId", getRelatedId())
            .append("time", getTime())
            .toString();
    }
}
