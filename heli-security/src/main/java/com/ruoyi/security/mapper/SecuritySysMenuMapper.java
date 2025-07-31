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
    
    /**
     * 根据路径查询菜单
     * 
     * @param path 路径
     * @return 菜单对象
     */
    securitysysmenu selectMenuByPath(String path);
}