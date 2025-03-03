package com.heli.production.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serial;
import java.io.Serializable;

/**
 * 生产文件对象 production_file
 *
 * @author hong
 * @date 2025-03-03
 */
@TableName(value = "production_file")
@Data
public class FileEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 归属类型
     */
    @Excel(name = "归属类型")
    private String type;

    /**
     * 关联编号
     */
    @Excel(name = "关联编号")
    private Long connectionId;

    /**
     * 文件名
     */
    @Excel(name = "文件名")
    private String fileName;

    /**
     * 文件路径
     */
    @Excel(name = "文件路径")
    private String filePath;

    /**
     * 文件类型
     */
    @Excel(name = "文件类型")
    private String fileType;

    /**
     * 文件大小
     */
    @Excel(name = "文件大小")
    private Long fileSize;

    /**
     * 描述
     */
    @Excel(name = "描述")
    private String description;
}
