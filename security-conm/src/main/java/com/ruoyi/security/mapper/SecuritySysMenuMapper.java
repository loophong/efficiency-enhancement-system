package com.ruoyi.security.mapper;

import com.ruoyi.security.domain.securitysysmenu;
import java.util.List;

public interface SecuritySysMenuMapper {
    List<securitysysmenu> selectMenuTree();
    int insertMenu(securitysysmenu menu);
    int updateMenu(securitysysmenu menu);
    int deleteMenuById(Long menuId);
    securitysysmenu selectMenuById(Long menuId);
    int hasChildByMenuId(Long menuId);
}