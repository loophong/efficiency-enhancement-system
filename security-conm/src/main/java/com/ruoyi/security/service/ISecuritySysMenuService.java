package com.ruoyi.security.service;

import com.ruoyi.security.domain.securitysysmenu;
import java.util.List;

public interface ISecuritySysMenuService {
    List<securitysysmenu> selectMenuTree();
    int insertMenu(securitysysmenu menu);
    int updateMenu(securitysysmenu menu);
    int deleteMenuById(Long menuId);
    securitysysmenu selectMenuById(Long menuId);
    boolean hasChildByMenuId(Long menuId);
}