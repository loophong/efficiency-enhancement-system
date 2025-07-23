package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全制度清单对象 security_regulation_list
 * 
 * @author wang
 * @date 2025-03-12
 */
public class SecurityRegulationList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Long id;

    /** 制度名称 */
    @Excel(name = "制度名称")
    private String regulationName;

    /** 办法名称 */
    @Excel(name = "办法名称")
    private String methodName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setRegulationName(String regulationName) 
    {
        this.regulationName = regulationName;
    }

    public String getRegulationName() 
    {
        return regulationName;
    }
    public void setMethodName(String methodName) 
    {
        this.methodName = methodName;
    }

    public String getMethodName() 
    {
        return methodName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("regulationName", getRegulationName())
            .append("methodName", getMethodName())
            .toString();
    }
}
