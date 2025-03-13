package com.heli.production.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.production.mapper.SysNotificationsMapper;
import com.heli.production.domain.entity.SysNotificationsEntity;
import com.heli.production.service.ISysNotificationsService;

/**
 * 消息通知Service业务层处理
 *
 * @author hong
 * @date 2025-03-13
 */
@Service
public class SysNotificationsServiceImpl extends ServiceImpl<SysNotificationsMapper, SysNotificationsEntity> implements ISysNotificationsService {
    @Autowired
    private SysNotificationsMapper sysNotificationsMapper;

    @Override
    public boolean createNotifications(SysNotificationsEntity sysNotifications) {
        // 设置未读
        sysNotifications.setStatus(0L);
        // 设置创建时间
        sysNotifications.setCreatedAt(DateUtils.getNowDate());
        return this.save(sysNotifications);
    }

    /**
     * 查询消息通知
     *
     * @param id 消息通知主键
     * @return 消息通知
     */
    @Override
    public SysNotificationsEntity selectSysNotificationsById(String id) {
        return sysNotificationsMapper.selectSysNotificationsById(id);
    }

    /**
     * 查询消息通知列表
     *
     * @param sysNotifications 消息通知
     * @return 消息通知
     */
    @Override
    public List<SysNotificationsEntity> selectSysNotificationsList(SysNotificationsEntity sysNotifications) {
        return sysNotificationsMapper.selectSysNotificationsList(sysNotifications);
    }

    /**
     * 新增消息通知
     *
     * @param sysNotifications 消息通知
     * @return 结果
     */
    @Override
    public int insertSysNotifications(SysNotificationsEntity sysNotifications) {
        return sysNotificationsMapper.insertSysNotifications(sysNotifications);
    }

    /**
     * 修改消息通知
     *
     * @param sysNotifications 消息通知
     * @return 结果
     */
    @Override
    public int updateSysNotifications(SysNotificationsEntity sysNotifications) {
        return sysNotificationsMapper.updateSysNotifications(sysNotifications);
    }

    /**
     * 批量删除消息通知
     *
     * @param ids 需要删除的消息通知主键
     * @return 结果
     */
    @Override
    public int deleteSysNotificationsByIds(String[] ids) {
        return sysNotificationsMapper.deleteSysNotificationsByIds(ids);
    }

    /**
     * 删除消息通知信息
     *
     * @param id 消息通知主键
     * @return 结果
     */
    @Override
    public int deleteSysNotificationsById(String id) {
        return sysNotificationsMapper.deleteSysNotificationsById(id);
    }
}
