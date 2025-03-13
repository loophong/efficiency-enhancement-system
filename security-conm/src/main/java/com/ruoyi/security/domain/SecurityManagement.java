package com.ruoyi.security.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 管理手册对象 security_management
 * r
 * @author 王
 * @date 2025-01-13
 */
public class SecurityManagement extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String address;

    /** 1备用字段 */
    @Excel(name = "1备用字段")
    private String beiyong1;

    /** 2备用字段 */
    @Excel(name = "2备用字段")
    private Long beiyong2;

    /** 3备用字段 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "3备用字段", width = 30, dateFormat = "yyyy-MM-dd")
    private Date beiyong3;

    /** 4备用字段 */
    @Excel(name = "4备用字段")
    private String beiyong4;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setAddress(String address) 
    {
        this.address = address;
    }

    public String getAddress() 
    {
        return address;
    }
    public void setBeiyong1(String beiyong1) 
    {
        this.beiyong1 = beiyong1;
    }

    public String getBeiyong1() 
    {
        return beiyong1;
    }
    public void setBeiyong2(Long beiyong2) 
    {
        this.beiyong2 = beiyong2;
    }

    public Long getBeiyong2() 
    {
        return beiyong2;
    }
    public void setBeiyong3(Date beiyong3) 
    {
        this.beiyong3 = beiyong3;
    }

    public Date getBeiyong3() 
    {
        return beiyong3;
    }
    public void setBeiyong4(String beiyong4) 
    {
        this.beiyong4 = beiyong4;
    }

    public String getBeiyong4() 
    {
        return beiyong4;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("address", getAddress())
            .append("beiyong1", getBeiyong1())
            .append("beiyong2", getBeiyong2())
            .append("beiyong3", getBeiyong3())
            .append("beiyong4", getBeiyong4())
            .toString();
    }
}
