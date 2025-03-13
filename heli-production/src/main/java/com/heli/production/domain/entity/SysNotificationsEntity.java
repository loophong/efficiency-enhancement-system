package com.heli.production.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 消息通知对象 sys_notifications
 *
 * @author hong
 * @date 2025-03-13
 */
@Data
@TableName("sys_notifications")
public class SysNotificationsEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 消息类型
     */
    @Excel(name = "消息类型")
    private String type;

    /**
     * 接收消息的用户 ID
     */
    @Excel(name = "接收消息的用户 ID")
    private Long receiverId;

    /**
     * 发送消息的用户 ID
     */
    @Excel(name = "发送消息的用户 ID")
    private Long senderId;

    /**
     * 标题
     */
    @Excel(name = "标题")
    private String title;

    /**
     * 内容
     */
    @Excel(name = "内容")
    private String content;

    /**
     * 状态(0未读，1已读)
     */
    @Excel(name = "状态(0未读，1已读)")
    private Long status;

    /**
     * 消息创建的时间
     */
    @Excel(name = "消息创建的时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdAt;
}
