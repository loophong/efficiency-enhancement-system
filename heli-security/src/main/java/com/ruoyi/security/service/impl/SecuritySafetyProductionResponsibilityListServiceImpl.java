package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecuritySafetyProductionResponsibilityListMapper;
import com.ruoyi.security.domain.SecuritySafetyProductionResponsibilityList;
import com.ruoyi.security.service.ISecuritySafetyProductionResponsibilityListService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 安全生产责任制清单Service业务层处理
 * 
 * @author wang
 * @date 2025-07-18
 */
@Service
public class SecuritySafetyProductionResponsibilityListServiceImpl implements ISecuritySafetyProductionResponsibilityListService
{
    private static final Logger log = LoggerFactory.getLogger(SecuritySafetyProductionResponsibilityListServiceImpl.class);

    @Autowired
    private SecuritySafetyProductionResponsibilityListMapper securitySafetyProductionResponsibilityListMapper;

    /**
     * 查询安全生产责任制清单
     * 
     * @param id 安全生产责任制清单主键
     * @return 安全生产责任制清单
     */
    @Override
    public SecuritySafetyProductionResponsibilityList selectSecuritySafetyProductionResponsibilityListById(String id)
    {
        return securitySafetyProductionResponsibilityListMapper.selectSecuritySafetyProductionResponsibilityListById(id);
    }

    /**
     * 查询安全生产责任制清单列表
     * 
     * @param securitySafetyProductionResponsibilityList 安全生产责任制清单
     * @return 安全生产责任制清单
     */
    @Override
    public List<SecuritySafetyProductionResponsibilityList> selectSecuritySafetyProductionResponsibilityListList(SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList)
    {
        return securitySafetyProductionResponsibilityListMapper.selectSecuritySafetyProductionResponsibilityListList(securitySafetyProductionResponsibilityList);
    }

    /**
     * 新增安全生产责任制清单
     * 
     * @param securitySafetyProductionResponsibilityList 安全生产责任制清单
     * @return 结果
     */
    @Override
    public int insertSecuritySafetyProductionResponsibilityList(SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList)
    {
        return securitySafetyProductionResponsibilityListMapper.insertSecuritySafetyProductionResponsibilityList(securitySafetyProductionResponsibilityList);
    }

    /**
     * 修改安全生产责任制清单
     * 
     * @param securitySafetyProductionResponsibilityList 安全生产责任制清单
     * @return 结果
     */
    @Override
    public int updateSecuritySafetyProductionResponsibilityList(SecuritySafetyProductionResponsibilityList securitySafetyProductionResponsibilityList)
    {
        return securitySafetyProductionResponsibilityListMapper.updateSecuritySafetyProductionResponsibilityList(securitySafetyProductionResponsibilityList);
    }

    /**
     * 批量删除安全生产责任制清单
     * 
     * @param ids 需要删除的安全生产责任制清单主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySafetyProductionResponsibilityListByIds(String[] ids)
    {
        return securitySafetyProductionResponsibilityListMapper.deleteSecuritySafetyProductionResponsibilityListByIds(ids);
    }

    /**
     * 删除安全生产责任制清单信息
     * 
     * @param id 安全生产责任制清单主键
     * @return 结果
     */
    @Override
    public int deleteSecuritySafetyProductionResponsibilityListById(String id)
    {
        return securitySafetyProductionResponsibilityListMapper.deleteSecuritySafetyProductionResponsibilityListById(id);
    }

    /**
     * 导入安全生产责任制清单数据
     *
     * @param responsibilityList 安全生产责任制清单数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importSecuritySafetyProductionResponsibilityList(List<SecuritySafetyProductionResponsibilityList> responsibilityList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(responsibilityList) || responsibilityList.size() == 0) {
            throw new ServiceException("导入安全生产责任制清单数据不能为空！");
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        // 处理单元格合并的情况 - 记录上一行的值
        String lastZhiDu = null;
        String lastBanFa = null;

        for (int i = 0; i < responsibilityList.size(); i++) {
            SecuritySafetyProductionResponsibilityList responsibility = responsibilityList.get(i);
            try {
                // 清空id字段，确保使用数据库自动生成的id
                responsibility.setId(null);

                // 处理单元格合并：制度名称没有时从上一行获取
                if (StringUtils.isEmpty(responsibility.getZhiDu()) && StringUtils.isNotEmpty(lastZhiDu)) {
                    responsibility.setZhiDu(lastZhiDu);
                    log.debug("第{}行制度名称为空，使用上一行值：{}", i + 1, lastZhiDu);
                }

                // 处理单元格合并：办法名称没有时从前一列（制度名称）获取
                if (StringUtils.isEmpty(responsibility.getBanFa()) && StringUtils.isNotEmpty(responsibility.getZhiDu())) {
                    responsibility.setBanFa(responsibility.getZhiDu());
                    log.debug("第{}行办法名称为空，使用制度名称值：{}", i + 1, responsibility.getZhiDu());
                }
                // 如果办法名称为空且制度名称也为空，则尝试使用上一行的办法名称
                else if (StringUtils.isEmpty(responsibility.getBanFa()) && StringUtils.isNotEmpty(lastBanFa)) {
                    responsibility.setBanFa(lastBanFa);
                    log.debug("第{}行办法名称为空且制度名称也为空，使用上一行办法名称值：{}", i + 1, lastBanFa);
                }

                // 更新最后的值，用于下一行的合并处理
                // 只有当前行有值时才更新lastZhiDu和lastBanFa
                if (StringUtils.isNotEmpty(responsibility.getZhiDu())) {
                    lastZhiDu = responsibility.getZhiDu().trim();
                }
                if (StringUtils.isNotEmpty(responsibility.getBanFa())) {
                    lastBanFa = responsibility.getBanFa().trim();
                }

                // 跳过完全空白的行
                if (StringUtils.isEmpty(responsibility.getZhiDu()) && StringUtils.isEmpty(responsibility.getBanFa())) {
                    log.debug("跳过第{}行：完全空白", i + 1);
                    continue;
                }

                // 验证必填字段 - 至少要有制度名称或办法名称其中之一
                if (StringUtils.isEmpty(responsibility.getZhiDu()) && StringUtils.isEmpty(responsibility.getBanFa())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、第" + (i + 1) + "行：制度名称和办法名称不能同时为空");
                    continue;
                }

                // 根据isUpdateSupport参数决定是否检查重复
                if (isUpdateSupport) {
                    // 启用更新支持，检查是否存在相同记录
                    SecuritySafetyProductionResponsibilityList existingRecord = findByUniqueFields(responsibility.getZhiDu(), responsibility.getBanFa());
                    if (existingRecord != null) {
                        // 更新现有记录
                        responsibility.setId(existingRecord.getId());
                        this.updateSecuritySafetyProductionResponsibilityList(responsibility);
                        successNum++;
                        String identifier = buildIdentifier(responsibility);
                        successMsg.append("<br/>" + successNum + "、" + identifier + " 更新成功");
                    } else {
                        // 插入新记录
                        this.insertSecuritySafetyProductionResponsibilityList(responsibility);
                        successNum++;
                        String identifier = buildIdentifier(responsibility);
                        successMsg.append("<br/>" + successNum + "、" + identifier + " 导入成功");
                    }
                } else {
                    // 不启用更新支持，直接插入（允许重复数据）
                    this.insertSecuritySafetyProductionResponsibilityList(responsibility);
                    successNum++;
                    String identifier = buildIdentifier(responsibility);
                    successMsg.append("<br/>" + successNum + "、" + identifier + " 导入成功");
                }
            } catch (Exception e) {
                failureNum++;
                String identifier = buildIdentifier(responsibility);
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
    private String buildIdentifier(SecuritySafetyProductionResponsibilityList responsibility) {
        if (StringUtils.isNotEmpty(responsibility.getZhiDu()) && StringUtils.isNotEmpty(responsibility.getBanFa())) {
            return responsibility.getZhiDu() + " - " + responsibility.getBanFa();
        } else if (StringUtils.isNotEmpty(responsibility.getZhiDu())) {
            return responsibility.getZhiDu();
        } else if (StringUtils.isNotEmpty(responsibility.getBanFa())) {
            return responsibility.getBanFa();
        }
        return "";
    }

    /**
     * 根据唯一字段查找记录
     */
    private SecuritySafetyProductionResponsibilityList findByUniqueFields(String zhiDu, String banFa) {
        SecuritySafetyProductionResponsibilityList query = new SecuritySafetyProductionResponsibilityList();
        if (StringUtils.isNotEmpty(zhiDu)) {
            query.setZhiDu(zhiDu);
        }
        if (StringUtils.isNotEmpty(banFa)) {
            query.setBanFa(banFa);
        }
        List<SecuritySafetyProductionResponsibilityList> list = securitySafetyProductionResponsibilityListMapper.selectSecuritySafetyProductionResponsibilityListList(query);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 根据关联ID查询安全生产责任制清单列表
     *
     * @param relatedId 关联ID
     * @return 安全生产责任制清单集合
     */
    @Override
    public List<SecuritySafetyProductionResponsibilityList> selectSecuritySafetyProductionResponsibilityListByRelatedId(Long relatedId) {
        return securitySafetyProductionResponsibilityListMapper.selectSecuritySafetyProductionResponsibilityListByRelatedId(relatedId);
    }

    /**
     * 更新最新导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId) {
        return securitySafetyProductionResponsibilityListMapper.updateLatestImportedRelatedId(relatedId);
    }
}
