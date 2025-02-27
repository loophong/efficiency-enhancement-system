package com.heli.device.maintenanceTable.domain;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 设备指标分析对象 device_indicator_count
 * 
 * @author YYY
 * @date 2025-01-18
 */
@TableName(value ="device_indicator_count")
@Data
public class DeviceIndicatorCount implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    @TableId(type = IdType.AUTO)
    @ExcelIgnore
    private Long indicatorId;

    /** 项目名 */
    @ExcelProperty(value = "项目")
    private String indicatorName;

    /** 目标值 */
    @ExcelProperty(value = "目标值")
    private String indicatorTarget;

    /** 上限 */
    @ExcelProperty(value = "上限")
    @ExcelIgnore
    private String indicatorUp;

    /** 下限 */
    @ExcelProperty(value = "下限")
    @ExcelIgnore
    private String indicatorDown;

    /** 日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
//    @ExcelProperty(value = "日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ExcelIgnore
    private Date indicatorTime;

    /** 一月数据 */
    @ExcelProperty(value = "1月")
    private String indicatorJan;

    /** 二月数据 */
    @ExcelProperty(value = "2月")
    private String indicatorFeb;

    /** 三月数据 */
    @ExcelProperty(value = "3月")
    private String indicatorMar;

    /** 四月数据 */
    @ExcelProperty(value = "4月")
    private String indicatorApr;

    /** 五月数据 */
    @ExcelProperty(value = "5月")
    private String indicatorMay;

    /** 六月数据 */
    @ExcelProperty(value = "6月")
    private String indicatorJun;

    /** 七月数据 */
    @ExcelProperty(value = "7月")
    private String indicatorJul;

    /** 八月数据 */
    @ExcelProperty(value = "8月")
    private String indicatorAug;

    /** 九月数据 */
    @ExcelProperty(value = "9月")
    private String indicatorSep;

    /** 十月数据 */
    @ExcelProperty(value = "10月")
    private String indicatorOct;

    /** 十一月数据 */
    @ExcelProperty(value = "11月")
    private String indicatorNov;

    /** 十二月数据 */
    @ExcelProperty(value = "12月")
    private String indicatorDec;

    public void setIndicatorId(Long indicatorId)
    {
        this.indicatorId = indicatorId;
    }

    public Long getIndicatorId()
    {
        return indicatorId;
    }
    public void setIndicatorName(String indicatorName)
    {
        this.indicatorName = indicatorName;
    }

    public String getIndicatorName()
    {
        return indicatorName;
    }
    public void setIndicatorTarget(String indicatorTarget)
    {
        this.indicatorTarget = indicatorTarget;
    }

    public String getIndicatorTarget()
    {
        return indicatorTarget;
    }
    public void setIndicatorUp(String indicatorUp)
    {
        this.indicatorUp = indicatorUp;
    }

    public String getIndicatorUp()
    {
        return indicatorUp;
    }
    public void setIndicatorDown(String indicatorDown)
    {
        this.indicatorDown = indicatorDown;
    }

    public String getIndicatorDown()
    {
        return indicatorDown;
    }
    public void setIndicatorTime(Date indicatorTime)
    {
        this.indicatorTime = indicatorTime;
    }

    public Date getIndicatorTime()
    {
        return indicatorTime;
    }
    public void setIndicatorJan(String indicatorJan)
    {
        this.indicatorJan = indicatorJan;
    }

    public String getIndicatorJan()
    {
        return indicatorJan;
    }
    public void setIndicatorFeb(String indicatorFeb)
    {
        this.indicatorFeb = indicatorFeb;
    }

    public String getIndicatorFeb()
    {
        return indicatorFeb;
    }
    public void setIndicatorMar(String indicatorMar)
    {
        this.indicatorMar = indicatorMar;
    }

    public String getIndicatorMar()
    {
        return indicatorMar;
    }
    public void setIndicatorApr(String indicatorApr)
    {
        this.indicatorApr = indicatorApr;
    }

    public String getIndicatorApr()
    {
        return indicatorApr;
    }
    public void setIndicatorMay(String indicatorMay)
    {
        this.indicatorMay = indicatorMay;
    }

    public String getIndicatorMay()
    {
        return indicatorMay;
    }
    public void setIndicatorJun(String indicatorJun)
    {
        this.indicatorJun = indicatorJun;
    }

    public String getIndicatorJun()
    {
        return indicatorJun;
    }
    public void setIndicatorJul(String indicatorJul)
    {
        this.indicatorJul = indicatorJul;
    }

    public String getIndicatorJul()
    {
        return indicatorJul;
    }
    public void setIndicatorAug(String indicatorAug)
    {
        this.indicatorAug = indicatorAug;
    }

    public String getIndicatorAug()
    {
        return indicatorAug;
    }
    public void setIndicatorSep(String indicatorSep)
    {
        this.indicatorSep = indicatorSep;
    }

    public String getIndicatorSep()
    {
        return indicatorSep;
    }
    public void setIndicatorOct(String indicatorOct)
    {
        this.indicatorOct = indicatorOct;
    }

    public String getIndicatorOct()
    {
        return indicatorOct;
    }
    public void setIndicatorNov(String indicatorNov)
    {
        this.indicatorNov = indicatorNov;
    }

    public String getIndicatorNov()
    {
        return indicatorNov;
    }
    public void setIndicatorDec(String indicatorDec)
    {
        this.indicatorDec = indicatorDec;
    }

    public String getIndicatorDec()
    {
        return indicatorDec;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("indicatorId", getIndicatorId())
                .append("indicatorName", getIndicatorName())
                .append("indicatorTarget", getIndicatorTarget())
                .append("indicatorUp", getIndicatorUp())
                .append("indicatorDown", getIndicatorDown())
                .append("indicatorTime", getIndicatorTime())
                .append("indicatorJan", getIndicatorJan())
                .append("indicatorFeb", getIndicatorFeb())
                .append("indicatorMar", getIndicatorMar())
                .append("indicatorApr", getIndicatorApr())
                .append("indicatorMay", getIndicatorMay())
                .append("indicatorJun", getIndicatorJun())
                .append("indicatorJul", getIndicatorJul())
                .append("indicatorAug", getIndicatorAug())
                .append("indicatorSep", getIndicatorSep())
                .append("indicatorOct", getIndicatorOct())
                .append("indicatorNov", getIndicatorNov())
                .append("indicatorDec", getIndicatorDec())
                .toString();
    }
}
