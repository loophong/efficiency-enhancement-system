package com.ruoyi.security.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 环境职业健康安全管理体系文件清单对象 security_environmental_ohs_management_system_documents
 * 
 * @author wang
 * @date 2025-03-02
 */
public class SecurityEnvironmentalOhsManagementSystemDocuments extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 制度名称 */
    @Excel(name = "制度名称")
    @ExcelProperty(index = 0)
    private String systemName;

    /** 编号 */
    @Excel(name = "编号")
    @ExcelProperty(index = 1)
    private String documentNumber;

    /** 备注 */
    @Excel(name = "备注")
    @ExcelProperty(index = 2)
    private String remarks;

    /** 关联ID */
    private Long relatedId;

    /** 文件列表 */
    private String fileList;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setSystemName(String systemName) 
    {
        this.systemName = systemName;
    }

    public String getSystemName() 
    {
        return systemName;
    }
    public void setDocumentNumber(String documentNumber) 
    {
        this.documentNumber = documentNumber;
    }

    public String getDocumentNumber() 
    {
        return documentNumber;
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

    public String getFileList() 
    {
        return fileList;
    }

    public void setFileList(String fileList) 
    {
        this.fileList = fileList;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("systemName", getSystemName())
            .append("documentNumber", getDocumentNumber())
            .append("remarks", getRemarks())
            .append("relatedId", getRelatedId())
            .append("fileList", getFileList())
            .toString();
    }
}
