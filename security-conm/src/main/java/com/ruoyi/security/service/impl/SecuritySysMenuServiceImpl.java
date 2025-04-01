package com.ruoyi.security.service.impl;

import com.ruoyi.security.domain.securitysysmenu;
import com.ruoyi.security.mapper.SecuritySysMenuMapper;
import com.ruoyi.security.service.ISecuritySysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SecuritySysMenuServiceImpl implements ISecuritySysMenuService {

    @Autowired
    private SecuritySysMenuMapper securitySysMenuMapper;

    @Override
    public List<securitysysmenu> selectMenuTree() {
        return securitySysMenuMapper.selectMenuTree();
    }

    @Override
    public int insertMenu(securitysysmenu menu) {
        return securitySysMenuMapper.insertMenu(menu);
    }

    @Override
    public int updateMenu(securitysysmenu menu) {
        return securitySysMenuMapper.updateMenu(menu);
    }

    @Override
    public int deleteMenuById(Long menuId) {
        return securitySysMenuMapper.deleteMenuById(menuId);
    }

    @Override
    public securitysysmenu selectMenuById(Long menuId) {
        return securitySysMenuMapper.selectMenuById(menuId);
    }

    @Override
    public boolean hasChildByMenuId(Long menuId) {
        int result = securitySysMenuMapper.hasChildByMenuId(menuId);
        return result > 0;
    }
}