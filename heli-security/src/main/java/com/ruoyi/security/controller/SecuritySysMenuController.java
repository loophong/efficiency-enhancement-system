package com.ruoyi.security.controller;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.security.domain.securitysysmenu;
import com.ruoyi.security.service.ISecuritySysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security/sysmenu")
public class SecuritySysMenuController extends BaseController {

    @Autowired
    private ISecuritySysMenuService securitySysMenuService;

    // 获取菜单树
    @GetMapping("/tree")
    public AjaxResult tree() {
        List<securitysysmenu> menus = securitySysMenuService.selectMenuTree();
        return success(menus);
    }

    // 新增菜单
    @PostMapping
    public AjaxResult add(@RequestBody securitysysmenu menu) {
        return toAjax(securitySysMenuService.insertMenu(menu));
    }

    // 修改菜单
    @PutMapping
    public AjaxResult edit(@RequestBody securitysysmenu menu) {
        return toAjax(securitySysMenuService.updateMenu(menu));
    }

    // 删除菜单
    @DeleteMapping("/{menuId}")
    public AjaxResult remove(@PathVariable Long menuId) {
        if (securitySysMenuService.hasChildByMenuId(menuId)) {
            return warn("存在子菜单,不允许删除");
        }
        return toAjax(securitySysMenuService.deleteMenuById(menuId));
    }

    // 获取菜单详情
    @GetMapping("/{menuId}")
    public AjaxResult getInfo(@PathVariable Long menuId) {
        return success(securitySysMenuService.selectMenuById(menuId));
    }
}