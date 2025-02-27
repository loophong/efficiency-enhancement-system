package com.heli.device.maintenanceTable.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.alibaba.excel.annotation.ExcelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serializable;

/**
 * 专业计划保养对象 device_major_plan
 *
 * @author YYY
 * @date 2025-02-26
 */
public class DeviceMajorPlan implements Serializable
{
    private static final long serialVersionUID = 1L;

    /** 主键id */
    private String majorId;

    /** 序号 */
    @ExcelProperty(index = 0)
    private String majorOrder;

    /** 设备名称 */
    @ExcelProperty(index = 1)
    private String majorName;

    /** 部位 */
    @ExcelProperty(index = 2)
    private String majorPosition;

    /** 保养维修项目 */
    @ExcelProperty(index = 3)
    private String majorProject;

    /** 周期 */
    @ExcelProperty(index = 4)
    private String majorCycleNum;

    /** 人员 */
    @ExcelProperty(index = 5)
    private String majorPeople;

    /** 月份 */
//    @ExcelProperty(index = 6)
    @ExcelIgnore
    private String majorMonth;

    /** 一月1W */
    @ExcelProperty(index = 6)
    private String weekJanOne;

    /** 一月2W */
    @ExcelProperty(index = 7)
    private String weekJanTwo;

    /** 一月3W */
    @ExcelProperty(index = 8)
    private String weekJanThree;

    /** 一月4W */
    @ExcelProperty(index = 9)
    private String weekJanFour;

    /** 二月1W */
    @ExcelProperty(index = 10)
    private String weekFebOne;

    /** 二月2W */
    @ExcelProperty(index = 11)
    private String weekFebTwo;

    /** 二月3W */
    @ExcelProperty(index = 12)
    private String weekFebThree;

    /** 二月4W */
    @ExcelProperty(index = 13)
    private String weekFebFour;

    /** 三月1W */
    @ExcelProperty(index = 14)
    private String weekMarOne;

    /** 三月2W */
    @ExcelProperty(index = 15)
    private String weekMarTwo;

    /** 三月3W */
    @ExcelProperty(index = 16)
    private String weekMarThree;

    /** 三月4W */
    @ExcelProperty(index = 17)
    private String weekMarFour;

    /** 四月1W */
    @ExcelProperty(index = 18)
    private String weekAprOne;

    /** 四月2W */
    @ExcelProperty(index = 19)
    private String weekAprTwo;

    /** 四月3W */
    @ExcelProperty(index = 20)
    private String weekAprThree;

    /** 四月4W */
    @ExcelProperty(index = 21)
    private String weekAprFour;

    /** 五月1W */
    @ExcelProperty(index = 22)
    private String weekMayOne;

    /** 五月2W */
    @ExcelProperty(index = 23)
    private String weekMayTwo;

    /** 五月3W */
    @ExcelProperty(index = 24)
    private String weekMayThree;

    /** 五月4W */
    @ExcelProperty(index = 25)
    private String weekMayFour;

    /** 六月1W */
    @ExcelProperty(index = 26)
    private String weekJunOne;

    /** 六月2W */
    @ExcelProperty(index = 27)
    private String weekJunTwo;

    /** 六月3W */
    @ExcelProperty(index = 28)
    private String weekJunThree;

    /** 六月4W */
    @ExcelProperty(index = 29)
    private String weekJunFour;

    /** 七月1W */
    @ExcelProperty(index = 30)
    private String weekJulOne;

    /** 七月2W */
    @ExcelProperty(index = 31)
    private String weekJulTwo;

    /** 七月3W */
    @ExcelProperty(index = 32)
    private String weekJulThree;

    /** 七月4W */
    @ExcelProperty(index = 33)
    private String weekJulFour;

    /** 八月1W */
    @ExcelProperty(index = 34)
    private String weekAugOne;

    /** 八月2W */
    @ExcelProperty(index = 35)
    private String weekAugTwo;

    /** 八月3W */
    @ExcelProperty(index = 36)
    private String weekAugThree;

    /** 八月4W */
    @ExcelProperty(index = 37)
    private String weekAugFour;

    /** 九月1W */
    @ExcelProperty(index = 38)
    private String weekSepOne;

    /** 九月2W */
    @ExcelProperty(index = 39)
    private String weekSepTwo;

    /** 九月3W */
    @ExcelProperty(index = 40)
    private String weekSepThree;

    /** 九月4W */
    @ExcelProperty(index = 41)
    private String weekSepFour;

    /** 十月1W */
    @ExcelProperty(index = 42)
    private String weekOctOne;

    /** 十月2W */
    @ExcelProperty(index = 43)
    private String weekOctTwo;

    /** 十月3W */
    @ExcelProperty(index = 44)
    private String weekOctThree;

    /** 十月4W */
    @ExcelProperty(index = 45)
    private String weekOctFour;

    /** 十一月1W */
    @ExcelProperty(index = 46)
    private String weekNovOne;

    /** 十一月2W */
    @ExcelProperty(index = 47)
    private String weekNovTwo;

    /** 十一月3W */
    @ExcelProperty(index = 48)
    private String weekNovThree;

    /** 十一月4W */
    @ExcelProperty(index = 49)
    private String weekNovFour;

    /** 十二月1W */
    @ExcelProperty(index = 50)
    private String weekDecOne;

    /** 十二月2W */
    @ExcelProperty(index = 51)
    private String weekDecTwo;

    /** 十二月3W */
    @ExcelProperty(index = 52)
    private String weekDecThree;

    /** 十二月4W */
    @ExcelProperty(index = 53)
    private String weekDecFour;

    public void setMajorId(String majorId)
    {
        this.majorId = majorId;
    }

    public String getMajorId()
    {
        return majorId;
    }
    public void setMajorOrder(String majorOrder)
    {
        this.majorOrder = majorOrder;
    }

    public String getMajorOrder()
    {
        return majorOrder;
    }
    public void setMajorName(String majorName)
    {
        this.majorName = majorName;
    }

    public String getMajorName()
    {
        return majorName;
    }
    public void setMajorPosition(String majorPosition)
    {
        this.majorPosition = majorPosition;
    }

    public String getMajorPosition()
    {
        return majorPosition;
    }
    public void setMajorProject(String majorProject)
    {
        this.majorProject = majorProject;
    }

    public String getMajorProject()
    {
        return majorProject;
    }
    public void setMajorCycleNum(String majorCycleNum)
    {
        this.majorCycleNum = majorCycleNum;
    }

    public String getMajorCycleNum()
    {
        return majorCycleNum;
    }
    public void setMajorPeople(String majorPeople)
    {
        this.majorPeople = majorPeople;
    }

    public String getMajorPeople()
    {
        return majorPeople;
    }
    public void setMajorMonth(String majorMonth)
    {
        this.majorMonth = majorMonth;
    }

    public String getMajorMonth()
    {
        return majorMonth;
    }
    public void setWeekJanOne(String weekJanOne)
    {
        this.weekJanOne = weekJanOne;
    }

    public String getWeekJanOne()
    {
        return weekJanOne;
    }
    public void setWeekJanTwo(String weekJanTwo)
    {
        this.weekJanTwo = weekJanTwo;
    }

    public String getWeekJanTwo()
    {
        return weekJanTwo;
    }
    public void setWeekJanThree(String weekJanThree)
    {
        this.weekJanThree = weekJanThree;
    }

    public String getWeekJanThree()
    {
        return weekJanThree;
    }
    public void setWeekJanFour(String weekJanFour)
    {
        this.weekJanFour = weekJanFour;
    }

    public String getWeekJanFour()
    {
        return weekJanFour;
    }
    public void setWeekFebOne(String weekFebOne)
    {
        this.weekFebOne = weekFebOne;
    }

    public String getWeekFebOne()
    {
        return weekFebOne;
    }
    public void setWeekFebTwo(String weekFebTwo)
    {
        this.weekFebTwo = weekFebTwo;
    }

    public String getWeekFebTwo()
    {
        return weekFebTwo;
    }
    public void setWeekFebThree(String weekFebThree)
    {
        this.weekFebThree = weekFebThree;
    }

    public String getWeekFebThree()
    {
        return weekFebThree;
    }
    public void setWeekFebFour(String weekFebFour)
    {
        this.weekFebFour = weekFebFour;
    }

    public String getWeekFebFour()
    {
        return weekFebFour;
    }
    public void setWeekMarOne(String weekMarOne)
    {
        this.weekMarOne = weekMarOne;
    }

    public String getWeekMarOne()
    {
        return weekMarOne;
    }
    public void setWeekMarTwo(String weekMarTwo)
    {
        this.weekMarTwo = weekMarTwo;
    }

    public String getWeekMarTwo()
    {
        return weekMarTwo;
    }
    public void setWeekMarThree(String weekMarThree)
    {
        this.weekMarThree = weekMarThree;
    }

    public String getWeekMarThree()
    {
        return weekMarThree;
    }
    public void setWeekMarFour(String weekMarFour)
    {
        this.weekMarFour = weekMarFour;
    }

    public String getWeekMarFour()
    {
        return weekMarFour;
    }
    public void setWeekAprOne(String weekAprOne)
    {
        this.weekAprOne = weekAprOne;
    }

    public String getWeekAprOne()
    {
        return weekAprOne;
    }
    public void setWeekAprTwo(String weekAprTwo)
    {
        this.weekAprTwo = weekAprTwo;
    }

    public String getWeekAprTwo()
    {
        return weekAprTwo;
    }
    public void setWeekAprThree(String weekAprThree)
    {
        this.weekAprThree = weekAprThree;
    }

    public String getWeekAprThree()
    {
        return weekAprThree;
    }
    public void setWeekAprFour(String weekAprFour)
    {
        this.weekAprFour = weekAprFour;
    }

    public String getWeekAprFour()
    {
        return weekAprFour;
    }
    public void setWeekMayOne(String weekMayOne)
    {
        this.weekMayOne = weekMayOne;
    }

    public String getWeekMayOne()
    {
        return weekMayOne;
    }
    public void setWeekMayTwo(String weekMayTwo)
    {
        this.weekMayTwo = weekMayTwo;
    }

    public String getWeekMayTwo()
    {
        return weekMayTwo;
    }
    public void setWeekMayThree(String weekMayThree)
    {
        this.weekMayThree = weekMayThree;
    }

    public String getWeekMayThree()
    {
        return weekMayThree;
    }
    public void setWeekMayFour(String weekMayFour)
    {
        this.weekMayFour = weekMayFour;
    }

    public String getWeekMayFour()
    {
        return weekMayFour;
    }
    public void setWeekJunOne(String weekJunOne)
    {
        this.weekJunOne = weekJunOne;
    }

    public String getWeekJunOne()
    {
        return weekJunOne;
    }
    public void setWeekJunTwo(String weekJunTwo)
    {
        this.weekJunTwo = weekJunTwo;
    }

    public String getWeekJunTwo()
    {
        return weekJunTwo;
    }
    public void setWeekJunThree(String weekJunThree)
    {
        this.weekJunThree = weekJunThree;
    }

    public String getWeekJunThree()
    {
        return weekJunThree;
    }
    public void setWeekJunFour(String weekJunFour)
    {
        this.weekJunFour = weekJunFour;
    }

    public String getWeekJunFour()
    {
        return weekJunFour;
    }
    public void setWeekJulOne(String weekJulOne)
    {
        this.weekJulOne = weekJulOne;
    }

    public String getWeekJulOne()
    {
        return weekJulOne;
    }
    public void setWeekJulTwo(String weekJulTwo)
    {
        this.weekJulTwo = weekJulTwo;
    }

    public String getWeekJulTwo()
    {
        return weekJulTwo;
    }
    public void setWeekJulThree(String weekJulThree)
    {
        this.weekJulThree = weekJulThree;
    }

    public String getWeekJulThree()
    {
        return weekJulThree;
    }
    public void setWeekJulFour(String weekJulFour)
    {
        this.weekJulFour = weekJulFour;
    }

    public String getWeekJulFour()
    {
        return weekJulFour;
    }
    public void setWeekAugOne(String weekAugOne)
    {
        this.weekAugOne = weekAugOne;
    }

    public String getWeekAugOne()
    {
        return weekAugOne;
    }
    public void setWeekAugTwo(String weekAugTwo)
    {
        this.weekAugTwo = weekAugTwo;
    }

    public String getWeekAugTwo()
    {
        return weekAugTwo;
    }
    public void setWeekAugThree(String weekAugThree)
    {
        this.weekAugThree = weekAugThree;
    }

    public String getWeekAugThree()
    {
        return weekAugThree;
    }
    public void setWeekAugFour(String weekAugFour)
    {
        this.weekAugFour = weekAugFour;
    }

    public String getWeekAugFour()
    {
        return weekAugFour;
    }
    public void setWeekSepOne(String weekSepOne)
    {
        this.weekSepOne = weekSepOne;
    }

    public String getWeekSepOne()
    {
        return weekSepOne;
    }
    public void setWeekSepTwo(String weekSepTwo)
    {
        this.weekSepTwo = weekSepTwo;
    }

    public String getWeekSepTwo()
    {
        return weekSepTwo;
    }
    public void setWeekSepThree(String weekSepThree)
    {
        this.weekSepThree = weekSepThree;
    }

    public String getWeekSepThree()
    {
        return weekSepThree;
    }
    public void setWeekSepFour(String weekSepFour)
    {
        this.weekSepFour = weekSepFour;
    }

    public String getWeekSepFour()
    {
        return weekSepFour;
    }
    public void setWeekOctOne(String weekOctOne)
    {
        this.weekOctOne = weekOctOne;
    }

    public String getWeekOctOne()
    {
        return weekOctOne;
    }
    public void setWeekOctTwo(String weekOctTwo)
    {
        this.weekOctTwo = weekOctTwo;
    }

    public String getWeekOctTwo()
    {
        return weekOctTwo;
    }
    public void setWeekOctThree(String weekOctThree)
    {
        this.weekOctThree = weekOctThree;
    }

    public String getWeekOctThree()
    {
        return weekOctThree;
    }
    public void setWeekOctFour(String weekOctFour)
    {
        this.weekOctFour = weekOctFour;
    }

    public String getWeekOctFour()
    {
        return weekOctFour;
    }
    public void setWeekNovOne(String weekNovOne)
    {
        this.weekNovOne = weekNovOne;
    }

    public String getWeekNovOne()
    {
        return weekNovOne;
    }
    public void setWeekNovTwo(String weekNovTwo)
    {
        this.weekNovTwo = weekNovTwo;
    }

    public String getWeekNovTwo()
    {
        return weekNovTwo;
    }
    public void setWeekNovThree(String weekNovThree)
    {
        this.weekNovThree = weekNovThree;
    }

    public String getWeekNovThree()
    {
        return weekNovThree;
    }
    public void setWeekNovFour(String weekNovFour)
    {
        this.weekNovFour = weekNovFour;
    }

    public String getWeekNovFour()
    {
        return weekNovFour;
    }
    public void setWeekDecOne(String weekDecOne)
    {
        this.weekDecOne = weekDecOne;
    }

    public String getWeekDecOne()
    {
        return weekDecOne;
    }
    public void setWeekDecTwo(String weekDecTwo)
    {
        this.weekDecTwo = weekDecTwo;
    }

    public String getWeekDecTwo()
    {
        return weekDecTwo;
    }
    public void setWeekDecThree(String weekDecThree)
    {
        this.weekDecThree = weekDecThree;
    }

    public String getWeekDecThree()
    {
        return weekDecThree;
    }
    public void setWeekDecFour(String weekDecFour)
    {
        this.weekDecFour = weekDecFour;
    }

    public String getWeekDecFour()
    {
        return weekDecFour;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
                .append("majorId", getMajorId())
                .append("majorOrder", getMajorOrder())
                .append("majorName", getMajorName())
                .append("majorPosition", getMajorPosition())
                .append("majorProject", getMajorProject())
                .append("majorCycleNum", getMajorCycleNum())
                .append("majorPeople", getMajorPeople())
                .append("majorMonth", getMajorMonth())
                .append("weekJanOne", getWeekJanOne())
                .append("weekJanTwo", getWeekJanTwo())
                .append("weekJanThree", getWeekJanThree())
                .append("weekJanFour", getWeekJanFour())
                .append("weekFebOne", getWeekFebOne())
                .append("weekFebTwo", getWeekFebTwo())
                .append("weekFebThree", getWeekFebThree())
                .append("weekFebFour", getWeekFebFour())
                .append("weekMarOne", getWeekMarOne())
                .append("weekMarTwo", getWeekMarTwo())
                .append("weekMarThree", getWeekMarThree())
                .append("weekMarFour", getWeekMarFour())
                .append("weekAprOne", getWeekAprOne())
                .append("weekAprTwo", getWeekAprTwo())
                .append("weekAprThree", getWeekAprThree())
                .append("weekAprFour", getWeekAprFour())
                .append("weekMayOne", getWeekMayOne())
                .append("weekMayTwo", getWeekMayTwo())
                .append("weekMayThree", getWeekMayThree())
                .append("weekMayFour", getWeekMayFour())
                .append("weekJunOne", getWeekJunOne())
                .append("weekJunTwo", getWeekJunTwo())
                .append("weekJunThree", getWeekJunThree())
                .append("weekJunFour", getWeekJunFour())
                .append("weekJulOne", getWeekJulOne())
                .append("weekJulTwo", getWeekJulTwo())
                .append("weekJulThree", getWeekJulThree())
                .append("weekJulFour", getWeekJulFour())
                .append("weekAugOne", getWeekAugOne())
                .append("weekAugTwo", getWeekAugTwo())
                .append("weekAugThree", getWeekAugThree())
                .append("weekAugFour", getWeekAugFour())
                .append("weekSepOne", getWeekSepOne())
                .append("weekSepTwo", getWeekSepTwo())
                .append("weekSepThree", getWeekSepThree())
                .append("weekSepFour", getWeekSepFour())
                .append("weekOctOne", getWeekOctOne())
                .append("weekOctTwo", getWeekOctTwo())
                .append("weekOctThree", getWeekOctThree())
                .append("weekOctFour", getWeekOctFour())
                .append("weekNovOne", getWeekNovOne())
                .append("weekNovTwo", getWeekNovTwo())
                .append("weekNovThree", getWeekNovThree())
                .append("weekNovFour", getWeekNovFour())
                .append("weekDecOne", getWeekDecOne())
                .append("weekDecTwo", getWeekDecTwo())
                .append("weekDecThree", getWeekDecThree())
                .append("weekDecFour", getWeekDecFour())
                .toString();
    }
}
