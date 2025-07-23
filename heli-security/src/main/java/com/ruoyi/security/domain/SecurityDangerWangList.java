package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 风险网格化清单对象 security_danger_wang_list
 * 
 * @author ruoyi
 * @date 2025-07-21
 */
public class SecurityDangerWangList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** 责任科室 */
    @Excel(name = "责任科室")
    private String zerenKeshi;

    /** 场所/班组 */
    @Excel(name = "场所/班组")
    private String changsuoBanzu;

    /** 工序/设备/区域 */
    @Excel(name = "工序/设备/区域")
    private String gongxuShebei;

    /** 具体部位 */
    @Excel(name = "具体部位")
    private String jituBuwei;

    /** 作业工种 */
    @Excel(name = "作业工种")
    private String zuoyeGongzong;

    /** 主要危险源/危险物质 */
    @Excel(name = "主要危险源/危险物质")
    private String zhuyaoWeixian;

    /** 可能发生的主要事故类型（安全风险类型） */
    @Excel(name = "可能发生的主要事故类型（安全风险类型）")
    private String keNengFaSheng;

    /** 网格负责人及联系电话 */
    @Excel(name = "网格负责人及联系电话")
    private String wangLuoFuZeRen;

    /** 主要工作内容 */
    @Excel(name = "主要工作内容")
    private String zhuyaoGongzuo;

    /** 关联id */
    @Excel(name = "关联id", type = Excel.Type.EXPORT)
    private String relatedId;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setZerenKeshi(String zerenKeshi) 
    {
        this.zerenKeshi = zerenKeshi;
    }

    public String getZerenKeshi() 
    {
        return zerenKeshi;
    }
    public void setChangsuoBanzu(String changsuoBanzu) 
    {
        this.changsuoBanzu = changsuoBanzu;
    }

    public String getChangsuoBanzu() 
    {
        return changsuoBanzu;
    }
    public void setGongxuShebei(String gongxuShebei) 
    {
        this.gongxuShebei = gongxuShebei;
    }

    public String getGongxuShebei() 
    {
        return gongxuShebei;
    }
    public void setJituBuwei(String jituBuwei) 
    {
        this.jituBuwei = jituBuwei;
    }

    public String getJituBuwei() 
    {
        return jituBuwei;
    }
    public void setZuoyeGongzong(String zuoyeGongzong) 
    {
        this.zuoyeGongzong = zuoyeGongzong;
    }

    public String getZuoyeGongzong() 
    {
        return zuoyeGongzong;
    }
    public void setZhuyaoWeixian(String zhuyaoWeixian) 
    {
        this.zhuyaoWeixian = zhuyaoWeixian;
    }

    public String getZhuyaoWeixian() 
    {
        return zhuyaoWeixian;
    }
    public void setKeNengFaSheng(String keNengFaSheng) 
    {
        this.keNengFaSheng = keNengFaSheng;
    }

    public String getKeNengFaSheng() 
    {
        return keNengFaSheng;
    }
    public void setWangLuoFuZeRen(String wangLuoFuZeRen) 
    {
        this.wangLuoFuZeRen = wangLuoFuZeRen;
    }

    public String getWangLuoFuZeRen() 
    {
        return wangLuoFuZeRen;
    }
    public void setZhuyaoGongzuo(String zhuyaoGongzuo) 
    {
        this.zhuyaoGongzuo = zhuyaoGongzuo;
    }

    public String getZhuyaoGongzuo() 
    {
        return zhuyaoGongzuo;
    }
    public void setRelatedId(String relatedId) 
    {
        this.relatedId = relatedId;
    }

    public String getRelatedId() 
    {
        return relatedId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("zerenKeshi", getZerenKeshi())
            .append("changsuoBanzu", getChangsuoBanzu())
            .append("gongxuShebei", getGongxuShebei())
            .append("jituBuwei", getJituBuwei())
            .append("zuoyeGongzong", getZuoyeGongzong())
            .append("zhuyaoWeixian", getZhuyaoWeixian())
            .append("keNengFaSheng", getKeNengFaSheng())
            .append("wangLuoFuZeRen", getWangLuoFuZeRen())
            .append("zhuyaoGongzuo", getZhuyaoGongzuo())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
