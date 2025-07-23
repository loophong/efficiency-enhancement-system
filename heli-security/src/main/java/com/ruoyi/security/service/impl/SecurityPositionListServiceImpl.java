package com.ruoyi.security.service.impl;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityPositionListMapper;
import com.ruoyi.security.domain.SecurityPositionList;
import com.ruoyi.security.service.ISecurityPositionListService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 岗位清单Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-18
 */
@Service
public class SecurityPositionListServiceImpl implements ISecurityPositionListService 
{
    private static final Logger log = LoggerFactory.getLogger(SecurityPositionListServiceImpl.class);

    @Autowired
    private SecurityPositionListMapper securityPositionListMapper;

    /**
     * 查询岗位清单
     * 
     * @param id 岗位清单主键
     * @return 岗位清单
     */
    @Override
    public SecurityPositionList selectSecurityPositionListById(String id)
    {
        return securityPositionListMapper.selectSecurityPositionListById(id);
    }

    /**
     * 查询岗位清单列表
     * 
     * @param securityPositionList 岗位清单
     * @return 岗位清单
     */
    @Override
    public List<SecurityPositionList> selectSecurityPositionListList(SecurityPositionList securityPositionList)
    {
        return securityPositionListMapper.selectSecurityPositionListList(securityPositionList);
    }

    /**
     * 新增岗位清单
     * 
     * @param securityPositionList 岗位清单
     * @return 结果
     */
    @Override
    public int insertSecurityPositionList(SecurityPositionList securityPositionList)
    {
        // 生成UUID作为主键
        if (StringUtils.isEmpty(securityPositionList.getId())) {
            securityPositionList.setId(UUID.randomUUID().toString());
        }
        securityPositionList.setCreateTime(DateUtils.getNowDate());
        return securityPositionListMapper.insertSecurityPositionList(securityPositionList);
    }

    /**
     * 修改岗位清单
     * 
     * @param securityPositionList 岗位清单
     * @return 结果
     */
    @Override
    public int updateSecurityPositionList(SecurityPositionList securityPositionList)
    {
        securityPositionList.setUpdateTime(DateUtils.getNowDate());
        return securityPositionListMapper.updateSecurityPositionList(securityPositionList);
    }

    /**
     * 批量删除岗位清单
     * 
     * @param ids 需要删除的岗位清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityPositionListByIds(String[] ids)
    {
        return securityPositionListMapper.deleteSecurityPositionListByIds(ids);
    }

    /**
     * 删除岗位清单信息
     * 
     * @param id 岗位清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityPositionListById(String id)
    {
        return securityPositionListMapper.deleteSecurityPositionListById(id);
    }

    /**
     * 导入岗位清单数据
     * 
     * @param positionList 岗位清单数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importSecurityPositionList(List<SecurityPositionList> positionList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(positionList) || positionList.size() == 0) {
            throw new ServiceException("导入岗位清单数据不能为空！");
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        // 处理单元格合并的情况 - 记录上一行的值
        String lastPositionName = null;
        String lastDepartment = null;

        for (int i = 0; i < positionList.size(); i++) {
            SecurityPositionList position = positionList.get(i);
            try {
                // 清空id字段，确保使用数据库自动生成的id
                position.setId(null);

                // 处理单元格合并：岗位名称没有时从上一行获取
                if (StringUtils.isEmpty(position.getPositionName()) && StringUtils.isNotEmpty(lastPositionName)) {
                    position.setPositionName(lastPositionName);
                    log.debug("第{}行岗位名称为空，使用上一行值：{}", i + 1, lastPositionName);
                }
                
                // 处理单元格合并：部门没有时从上一行获取
                if (StringUtils.isEmpty(position.getDepartment()) && StringUtils.isNotEmpty(lastDepartment)) {
                    position.setDepartment(lastDepartment);
                    log.debug("第{}行部门为空，使用上一行值：{}", i + 1, lastDepartment);
                }

                // 更新最后的值，用于下一行的合并处理
                if (StringUtils.isNotEmpty(position.getPositionName())) {
                    lastPositionName = position.getPositionName().trim();
                }
                if (StringUtils.isNotEmpty(position.getDepartment())) {
                    lastDepartment = position.getDepartment().trim();
                }

                // 跳过完全空白的行
                if (StringUtils.isEmpty(position.getPositionName()) && StringUtils.isEmpty(position.getDepartment())) {
                    log.debug("跳过第{}行：完全空白", i + 1);
                    continue;
                }

                // 验证必填字段
                if (StringUtils.isEmpty(position.getPositionName())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、第" + (i + 1) + "行：岗位名称不能为空");
                    continue;
                }
                if (StringUtils.isEmpty(position.getDepartment())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、第" + (i + 1) + "行：部门不能为空");
                    continue;
                }

                // 设置操作用户
                position.setCreateBy(operName);
                position.setUpdateBy(operName);

                // 根据isUpdateSupport参数决定是否检查重复
                if (isUpdateSupport) {
                    // 启用更新支持，检查是否存在相同记录
                    SecurityPositionList existingRecord = securityPositionListMapper.selectByPositionNameAndDepartment(
                        position.getPositionName(), position.getDepartment());
                    if (existingRecord != null) {
                        // 更新现有记录
                        position.setId(existingRecord.getId());
                        this.updateSecurityPositionList(position);
                        successNum++;
                        String identifier = buildIdentifier(position);
                        successMsg.append("<br/>" + successNum + "、" + identifier + " 更新成功");
                    } else {
                        // 插入新记录
                        this.insertSecurityPositionList(position);
                        successNum++;
                        String identifier = buildIdentifier(position);
                        successMsg.append("<br/>" + successNum + "、" + identifier + " 导入成功");
                    }
                } else {
                    // 不启用更新支持，直接插入（允许重复数据）
                    this.insertSecurityPositionList(position);
                    successNum++;
                    String identifier = buildIdentifier(position);
                    successMsg.append("<br/>" + successNum + "、" + identifier + " 导入成功");
                }
            } catch (Exception e) {
                failureNum++;
                String identifier = buildIdentifier(position);
                if (StringUtils.isEmpty(identifier)) {
                    identifier = "第" + (i + 1) + "行";
                }
                String msg = "<br/>" + failureNum + "、" + identifier + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error("导入第{}行数据失败：{}", i + 1, e.getMessage(), e);
            }
        }

        // 构建返回消息
        if (failureNum > 0) {
            failureMsg.insert(0, "导入完成！成功 " + successNum + " 条，失败 " + failureNum + " 条，错误如下：");
            if (successNum > 0) {
                successMsg.insert(0, "成功数据：");
                return failureMsg.toString() + successMsg.toString();
            } else {
                return failureMsg.toString();
            }
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }

        return successMsg.toString();
    }

    /**
     * 构建记录标识符，用于显示导入结果
     */
    private String buildIdentifier(SecurityPositionList position) {
        if (StringUtils.isNotEmpty(position.getPositionName()) && StringUtils.isNotEmpty(position.getDepartment())) {
            return position.getPositionName() + " - " + position.getDepartment();
        } else if (StringUtils.isNotEmpty(position.getPositionName())) {
            return position.getPositionName();
        } else if (StringUtils.isNotEmpty(position.getDepartment())) {
            return position.getDepartment();
        }
        return "";
    }

    /**
     * 根据关联ID查询岗位清单列表
     * 
     * @param relatedId 关联ID
     * @return 岗位清单集合
     */
    @Override
    public List<SecurityPositionList> selectSecurityPositionListByRelatedId(Long relatedId) {
        return securityPositionListMapper.selectSecurityPositionListByRelatedId(relatedId);
    }

    /**
     * 更新最新导入数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId) {
        return securityPositionListMapper.updateLatestImportedRelatedId(relatedId);
    }
}
