package com.ruoyi.security.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("security_sys_menu")
public class securitysysmenu extends BaseEntity {
    private static final long serialVersionUID = 1L;

    private Long menuId;
    private String menuName;
    private Long parentId;
    private Integer orderNum;
    private String path;
    private String component;
    private Boolean isFrame;
    private String menuType;
    private Boolean visible;
    private Boolean status;
    private String perms;
    private String icon;
    private String remark;
}