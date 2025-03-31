package com.heli.supplier.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;

import java.io.Serial;
import java.util.Date;

/**
 * ClassName: ProductionErrorTable
 * Package: com.heli.supplier.domain
 * Description:
 *
 * @Author
 * @Create 2025/3/28 15:53
 * @Version 1.0
 */
@Data
public class ProductionErrorTable {

    @Serial
    @ExcelIgnore
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelIgnore
    @TableId(type = IdType.AUTO)
    private String supplierId;
    /**
     * 供应商名称
     */
    @Excel(name = "供应商名称")
    @ExcelProperty(index= 1)
    private String supplierName;


    @ExcelProperty(index = 11)
    private String one;

    @ExcelProperty(index = 21)
    private String two;

    @ExcelProperty(index = 31)
    private String three;

    @ExcelProperty(index = 41)
    private String four;

    @ExcelProperty(index = 51)
    private String five;

    @ExcelProperty(index = 61)
    private String six;

    @ExcelProperty(index = 71)
    private String seven;

    @ExcelProperty(index = 81)
    private String eight;

    @ExcelProperty(index = 91)
    private String nine;

    @ExcelProperty(index = 101)
    private String ten;

    @ExcelProperty(index = 111)
    private String eleven;

    @ExcelProperty(index = 121)
    private String twelve;


}
