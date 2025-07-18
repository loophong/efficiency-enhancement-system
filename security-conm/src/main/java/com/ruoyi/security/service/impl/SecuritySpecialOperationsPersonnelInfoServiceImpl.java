package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecuritySpecialOperationsPersonnelInfoMapper;
import com.ruoyi.security.domain.SecuritySpecialOperationsPersonnelInfo;
import com.ruoyi.security.service.ISecuritySpecialOperationsPersonnelInfoService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 特种作业人员信息Service业务层处理
 * 
 * @author wang
 * @date 2025-03-05
 */
@Service
public class SecuritySpecialOperationsPersonnelInfoServiceImpl implements ISecuritySpecialOperationsPersonnelInfoService
{
    private static final Logger log = LoggerFactory.getLogger(SecuritySpecialOperationsPersonnelInfoServiceImpl.class);

    @Autowired
    private SecuritySpecialOperationsPersonnelInfoMapper securitySpecialOperationsPersonnelInfoMapper;

    /**
     * 查询特种作业人员信息
     * 
     * @param id 特种作业人员信息主键
     * @return 特种作业人员信息
     */
    @Override
    public SecuritySpecialOperationsPersonnelInfo selectSecuritySpecialOperationsPersonnelInfoById(Long id)
    {
        return securitySpecialOperationsPersonnelInfoMapper.selectSecuritySpecialOperationsPersonnelInfoById(id);
    }

    /**
     * 查询特种作业人员信息列表
     * 
     * @param securitySpecialOperationsPersonnelInfo 特种作业人员信息
     * @return 特种作业人员信息
     */
    @Override
    public List<SecuritySpecialOperationsPersonnelInfo> selectSecuritySpecialOperationsPersonnelInfoList(SecuritySpecialOperationsPersonnelInfo securitySpecialOperationsPersonnelInfo)
    {
        return securitySpecialOperationsPersonnelInfoMapper.selectSecuritySpecialOperationsPersonnelInfoList(securitySpecialOperationsPersonnelInfo);
    }

    /**
     * 新增特种作业人员信息
     * 
     * @param securitySpecialOperationsPersonnelInfo 特种作业人员信息
     * @return 结果
     */
    @Override
    public int insertSecuritySpecialOperationsPersonnelInfo(SecuritySpecialOperationsPersonnelInfo securitySpecialOperationsPersonnelInfo)
    {
        return securitySpecialOperationsPersonnelInfoMapper.insertSecuritySpecialOperationsPersonnelInfo(securitySpecialOperationsPersonnelInfo);
    }

    /**
     * 修改特种作业人员信息
     * 
     * @param securitySpecialOperationsPersonnelInfo 特种作业人员信息
     * @return 结果
     */
    @Override
    public int updateSecuritySpecialOperationsPersonnelInfo(SecuritySpecialOperationsPersonnelInfo securitySpecialOperationsPersonnelInfo)
    {
        return securitySpecialOperationsPersonnelInfoMapper.updateSecuritySpecialOperationsPersonnelInfo(securitySpecialOperationsPersonnelInfo);
    }

    /**
     * 批量删除特种作业人员信息
     * 
     * @param ids 需要删除的特种作业人员信息主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySpecialOperationsPersonnelInfoByIds(Long[] ids)
    {
        return securitySpecialOperationsPersonnelInfoMapper.deleteSecuritySpecialOperationsPersonnelInfoByIds(ids);
    }

    /**
     * 删除特种作业人员信息信息
     *
     * @param id 特种作业人员信息主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySpecialOperationsPersonnelInfoById(Long id)
    {
        return securitySpecialOperationsPersonnelInfoMapper.deleteSecuritySpecialOperationsPersonnelInfoById(id);
    }

    /**
     * 导入特种作业人员信息数据
     *
     * @param personnelInfoList 特种作业人员信息数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importSpecialOperationsPersonnelInfo(List<SecuritySpecialOperationsPersonnelInfo> personnelInfoList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(personnelInfoList) || personnelInfoList.size() == 0)
        {
            throw new ServiceException("导入特种作业人员信息数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SecuritySpecialOperationsPersonnelInfo personnelInfo : personnelInfoList)
        {
            try
            {
                // 跳过空记录
                if (personnelInfo == null) {
                    continue;
                }

                // 获取姓名，如果为空则使用默认值
                String name = StringUtils.isNotEmpty(personnelInfo.getName()) ? personnelInfo.getName() : "未知姓名_" + System.currentTimeMillis();

                // 如果姓名为空，设置默认值
                if (StringUtils.isEmpty(personnelInfo.getName())) {
                    personnelInfo.setName(name);
                }

                // 清空id字段，确保使用数据库自动生成的id
                personnelInfo.setId(null);

                // 直接导入，不进行重复检查
                // 如果启用更新支持，则尝试根据姓名查找并更新；否则直接插入新记录
                if (isUpdateSupport && StringUtils.isNotEmpty(name) && !name.startsWith("未知姓名_")) {
                    // 只有在启用更新支持且姓名有效时才检查重复
                    SecuritySpecialOperationsPersonnelInfo u = securitySpecialOperationsPersonnelInfoMapper.selectSecuritySpecialOperationsPersonnelInfoByName(name);
                    if (StringUtils.isNotNull(u)) {
                        // 找到重复记录，执行更新
                        personnelInfo.setUpdateBy(operName);
                        personnelInfo.setId(u.getId());
                        this.updateSecuritySpecialOperationsPersonnelInfo(personnelInfo);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、人员 " + name + " 更新成功");
                    } else {
                        // 没有找到重复记录，插入新记录
                        try {
                            personnelInfo.setCreateBy(operName);
                            this.insertSecuritySpecialOperationsPersonnelInfo(personnelInfo);
                            successNum++;
                            successMsg.append("<br/>" + successNum + "、人员 " + name + " 导入成功");
                        } catch (Exception insertEx) {
                            // 如果仍然有唯一性约束冲突，记录错误但不中断导入
                            if (insertEx.getMessage().contains("Duplicate entry")) {
                                failureNum++;
                                failureMsg.append("<br/>" + failureNum + "、人员 " + name + " 导入失败：数据重复（请检查数据库唯一性约束配置）");
                                log.error("插入人员信息失败，可能存在未移除的唯一性约束: {}", insertEx.getMessage());
                            } else {
                                failureNum++;
                                failureMsg.append("<br/>" + failureNum + "、人员 " + name + " 导入失败：" + insertEx.getMessage());
                                log.error("插入人员信息失败: {}", insertEx.getMessage(), insertEx);
                            }
                        }
                    }
                } else {
                    // 不启用更新支持或姓名无效，直接插入新记录（允许重复）
                    try {
                        personnelInfo.setCreateBy(operName);
                        this.insertSecuritySpecialOperationsPersonnelInfo(personnelInfo);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、人员 " + name + " 导入成功");
                    } catch (Exception insertEx) {
                        // 如果仍然有唯一性约束冲突，记录错误但不中断导入
                        if (insertEx.getMessage().contains("Duplicate entry")) {
                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + "、人员 " + name + " 导入失败：数据重复（请检查数据库唯一性约束配置）");
                            log.error("插入人员信息失败，可能存在未移除的唯一性约束: {}", insertEx.getMessage());
                        } else {
                            failureNum++;
                            failureMsg.append("<br/>" + failureNum + "、人员 " + name + " 导入失败：" + insertEx.getMessage());
                            log.error("插入人员信息失败: {}", insertEx.getMessage(), insertEx);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String name = (personnelInfo != null && StringUtils.isNotEmpty(personnelInfo.getName()))
                    ? personnelInfo.getName() : "未知记录";
                String msg = "<br/>" + failureNum + "、人员 " + name + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error("导入人员信息失败: {}", e.getMessage(), e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId) {
        if (relatedId == null) {
            log.warn("更新最近导入特种作业人员信息关联ID失败：relatedId为空");
            return 0;
        }

        log.info("更新最近导入特种作业人员信息关联ID: {}", relatedId);

        try {
            // 直接使用批量更新SQL
            int updatedCount = securitySpecialOperationsPersonnelInfoMapper.updateLatestImportedRelatedId(relatedId);
            log.info("批量更新特种作业人员信息关联ID成功，更新记录数: {}", updatedCount);
            return updatedCount;
        } catch (Exception e) {
            log.error("更新特种作业人员信息关联ID失败: {}", e.getMessage(), e);
            return 0;
        }
    }
}
