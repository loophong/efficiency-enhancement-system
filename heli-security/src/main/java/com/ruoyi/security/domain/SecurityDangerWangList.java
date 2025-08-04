package com.ruoyi.security.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 风险网格化清单对象 security_danger_wang_list
 * 
 * @author ruoyi
 * @date 2025-07-30
 */
public class SecurityDangerWangList extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 序号 */
    private Integer id;

    /** 所在班组 */
    @Excel(name = "所在班组")
    private String changsuoBanzu;

    /** 岗位/工序/设备 */
    @Excel(name = "岗位/工序/设备")
    private String gongxuShebei;

    /** 危险物质、作业、能量 */
    @Excel(name = "危险物质、作业、能量")
    private String jituBuwei;

    /** 风险因素 */
    @Excel(name = "风险因素")
    private String zhuyaoWeixian;

    /** 可能发生的事故类型 */
    @Excel(name = "可能发生的事故类型")
    private String keNengFaSheng;

    /** L */
    @Excel(name = "L")
    private String scoreL;

    /** S */
    @Excel(name = "S")
    private String scoreS;

    /** R(LS) */
    @Excel(name = "R(LS)")
    private String scoreLs;

    /** 风险等级 */
    @Excel(name = "风险等级")
    private String fenXianDengJi;

    /** 管控/预防措施 */
    @Excel(name = "管控/预防措施")
    private String guankongYufang;

    /** 本岗位责任人 */
    @Excel(name = "本岗位责任人")
    private String fuZeRen;

    /** 网格负责人及联系电话 */
    @Excel(name = "岗位责任人联系方式")
    private String wangLuoFuZeRen;

    /** 备注 */
    @Excel(name = "备注")
    private String zhuyaoGongzuo;

    /** 关联id */
    private String relatedId;

    public void setId(Integer id) 
    {
        this.id = id;
    }

    public Integer getId() 
    {
        return id;
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
    public void setScoreL(String scoreL) 
    {
        this.scoreL = scoreL;
    }

    public String getScoreL() 
    {
        return scoreL;
    }
    public void setScoreS(String scoreS) 
    {
        this.scoreS = scoreS;
    }

    public String getScoreS() 
    {
        return scoreS;
    }
    public void setScoreLs(String scoreLs) 
    {
        this.scoreLs = scoreLs;
    }

    public String getScoreLs() 
    {
        return scoreLs;
    }
    public void setFenXianDengJi(String fenXianDengJi) 
    {
        this.fenXianDengJi = fenXianDengJi;
    }

    public String getFenXianDengJi() 
    {
        return fenXianDengJi;
    }
    public void setGuankongYufang(String guankongYufang) 
    {
        this.guankongYufang = guankongYufang;
    }

    public String getGuankongYufang() 
    {
        return guankongYufang;
    }
    public void setFuZeRen(String fuZeRen) 
    {
        this.fuZeRen = fuZeRen;
    }

    public String getFuZeRen() 
    {
        return fuZeRen;
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
            .append("changsuoBanzu", getChangsuoBanzu())
            .append("gongxuShebei", getGongxuShebei())
            .append("jituBuwei", getJituBuwei())
            .append("zhuyaoWeixian", getZhuyaoWeixian())
            .append("keNengFaSheng", getKeNengFaSheng())
            .append("scoreL", getScoreL())
            .append("scoreS", getScoreS())
            .append("scoreLs", getScoreLs())
            .append("fenXianDengJi", getFenXianDengJi())
            .append("guankongYufang", getGuankongYufang())
            .append("fuZeRen", getFuZeRen())
            .append("wangLuoFuZeRen", getWangLuoFuZeRen())
            .append("zhuyaoGongzuo", getZhuyaoGongzuo())
            .append("relatedId", getRelatedId())
            .toString();
    }
}
