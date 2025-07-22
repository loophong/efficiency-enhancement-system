package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityPeopleListMapper;
import com.ruoyi.security.domain.SecurityPeopleList;
import com.ruoyi.security.service.ISecurityPeopleListService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 班组人员清单Service业务层处理
 *
 * @author wang
 * @date 2025-07-21
 */
@Service
public class SecurityPeopleListServiceImpl implements ISecurityPeopleListService
{
    private static final Logger log = LoggerFactory.getLogger(SecurityPeopleListServiceImpl.class);

    @Autowired
    private SecurityPeopleListMapper securityPeopleListMapper;

    /**
     * 查询班组人员清单
     * 
     * @param id 班组人员清单主键
     * @return 班组人员清单
     */
    @Override
    public SecurityPeopleList selectSecurityPeopleListById(String id)
    {
        return securityPeopleListMapper.selectSecurityPeopleListById(id);
    }

    /**
     * 查询班组人员清单列表
     * 
     * @param securityPeopleList 班组人员清单
     * @return 班组人员清单
     */
    @Override
    public List<SecurityPeopleList> selectSecurityPeopleListList(SecurityPeopleList securityPeopleList)
    {
        return securityPeopleListMapper.selectSecurityPeopleListList(securityPeopleList);
    }

    /**
     * 新增班组人员清单
     * 
     * @param securityPeopleList 班组人员清单
     * @return 结果
     */
    @Override
    public int insertSecurityPeopleList(SecurityPeopleList securityPeopleList)
    {
        return securityPeopleListMapper.insertSecurityPeopleList(securityPeopleList);
    }

    /**
     * 修改班组人员清单
     * 
     * @param securityPeopleList 班组人员清单
     * @return 结果
     */
    @Override
    public int updateSecurityPeopleList(SecurityPeopleList securityPeopleList)
    {
        return securityPeopleListMapper.updateSecurityPeopleList(securityPeopleList);
    }

    /**
     * 批量删除班组人员清单
     * 
     * @param ids 需要删除的班组人员清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityPeopleListByIds(String[] ids)
    {
        return securityPeopleListMapper.deleteSecurityPeopleListByIds(ids);
    }

    /**
     * 删除班组人员清单信息
     *
     * @param id 班组人员清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityPeopleListById(String id)
    {
        return securityPeopleListMapper.deleteSecurityPeopleListById(id);
    }

    /**
     * 导入班组人员清单数据
     *
     * @param peopleListList 班组人员清单数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importPeopleList(List<SecurityPeopleList> peopleListList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(peopleListList) || peopleListList.size() == 0)
        {
            throw new ServiceException("导入班组人员清单数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SecurityPeopleList peopleList : peopleListList)
        {
            try
            {
                // 验证是否存在这个班组人员
                SecurityPeopleList u = securityPeopleListMapper.selectSecurityPeopleListByName(peopleList.getXingMing(), peopleList.getBanZu());
                if (StringUtils.isNull(u))
                {
                    peopleList.setCreateBy(operName);
                    this.insertSecurityPeopleList(peopleList);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、班组人员 " + peopleList.getXingMing() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    peopleList.setId(u.getId());
                    peopleList.setUpdateBy(operName);
                    this.updateSecurityPeopleList(peopleList);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、班组人员 " + peopleList.getXingMing() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、班组人员 " + peopleList.getXingMing() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、班组人员 " + peopleList.getXingMing() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
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
     * 更新最近导入的数据关联ID
     *
     * @param relatedId 关联ID
     * @return 更新的行数
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId)
    {
        if (relatedId == null) {
            log.warn("更新最近导入班组人员清单关联ID失败：relatedId为空");
            return 0;
        }

        log.info("更新最近导入班组人员清单关联ID: {}", relatedId);

        try {
            // 直接使用批量更新SQL
            int updatedCount = securityPeopleListMapper.updateLatestImportedRelatedId(relatedId);
            log.info("批量更新班组人员清单关联ID成功，更新记录数: {}", updatedCount);
            return updatedCount;
        } catch (Exception e) {
            log.error("更新班组人员清单关联ID失败: {}", e.getMessage(), e);
            return 0;
        }
    }
}
