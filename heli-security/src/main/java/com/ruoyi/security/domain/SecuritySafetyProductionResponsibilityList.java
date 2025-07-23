package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 安全生产责任制清单对象 security_safety_production_responsibility_list
 * 
 * @author wang
 * @date 2025-07-18
 */
public class SecuritySafetyProductionResponsibilityList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    @Excel(name = "序号")
    private String id;

    /** 制度名称 */
    @Excel(name = "制度名称")
    private String zhiDu;

    /** 办法名称 */
    @Excel(name = "办法名称")
    private String banFa;

    /** 关联ID */
    private Long relatedId;

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setZhiDu(String zhiDu) 
    {
        this.zhiDu = zhiDu;
    }

    public String getZhiDu() 
    {
        return zhiDu;
    }
    public void setBanFa(String banFa) 
    {
        this.banFa = banFa;
    }

    public String getBanFa()
    {
        return banFa;
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
            .append("zhiDu", getZhiDu())
            .append("banFa", getBanFa())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
