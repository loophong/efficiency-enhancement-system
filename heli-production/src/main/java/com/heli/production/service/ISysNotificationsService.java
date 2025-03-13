package com.heli.production.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.production.domain.entity.SysNotificationsEntity;

/**
 * 消息通知Service接口
 *
 * @author hong
 * @date 2025-03-13
 */
public interface ISysNotificationsService extends IService<SysNotificationsEntity> {

    /**
     * @description: 新建消息接口
     * @author: hong
     * @date: 2025/3/13 17:00
     * @version: 1.0
     */
    boolean createNotifications(SysNotificationsEntity sysNotifications);

    /**
     * 查询消息通知
     *
     * @param id 消息通知主键
     * @return 消息通知
     */
    public SysNotificationsEntity selectSysNotificationsById(String id);

    /**
     * 查询消息通知列表
     *
     * @param sysNotifications 消息通知
     * @return 消息通知集合
     */
    public List<SysNotificationsEntity> selectSysNotificationsList(SysNotificationsEntity sysNotifications);

    /**
     * 新增消息通知
     *
     * @param sysNotifications 消息通知
     * @return 结果
     */
    public int insertSysNotifications(SysNotificationsEntity sysNotifications);

    /**
     * 修改消息通知
     *
     * @param sysNotifications 消息通知
     * @return 结果
     */
    public int updateSysNotifications(SysNotificationsEntity sysNotifications);

    /**
     * 批量删除消息通知
     *
     * @param ids 需要删除的消息通知主键集合
     * @return 结果
     */
    public int deleteSysNotificationsByIds(String[] ids);

    /**
     * 删除消息通知信息
     *
     * @param id 消息通知主键
     * @return 结果
     */
    public int deleteSysNotificationsById(String id);
}
