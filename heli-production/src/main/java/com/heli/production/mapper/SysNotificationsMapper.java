package com.heli.production.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.production.domain.entity.SysNotificationsEntity;

/**
 * 消息通知Mapper接口
 *
 * @author hong
 * @date 2025-03-13
 */
public interface SysNotificationsMapper  extends BaseMapper<SysNotificationsEntity> {
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
     * 删除消息通知
     *
     * @param id 消息通知主键
     * @return 结果
     */
    public int deleteSysNotificationsById(String id);

    /**
     * 批量删除消息通知
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysNotificationsByIds(String[] ids);
}
