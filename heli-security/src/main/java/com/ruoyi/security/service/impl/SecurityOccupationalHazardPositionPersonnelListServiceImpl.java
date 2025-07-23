package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityOccupationalHazardPositionPersonnelListMapper;
import com.ruoyi.security.domain.SecurityOccupationalHazardPositionPersonnelList;
import com.ruoyi.security.service.ISecurityOccupationalHazardPositionPersonnelListService;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.exception.ServiceException;

/**
 * 危险职业岗位人员清单及管理台帐Service业务层处理
 * 
 * @author wang
 * @date 2025-03-05
 */
@Service
public class SecurityOccupationalHazardPositionPersonnelListServiceImpl implements ISecurityOccupationalHazardPositionPersonnelListService 
{
    @Autowired
    private SecurityOccupationalHazardPositionPersonnelListMapper securityOccupationalHazardPositionPersonnelListMapper;

    /**
     * 查询危险职业岗位人员清单及管理台帐
     * 
     * @param id 危险职业岗位人员清单及管理台帐主键
     * @return 危险职业岗位人员清单及管理台帐
     */
    @Override
    public SecurityOccupationalHazardPositionPersonnelList selectSecurityOccupationalHazardPositionPersonnelListById(Long id)
    {
        return securityOccupationalHazardPositionPersonnelListMapper.selectSecurityOccupationalHazardPositionPersonnelListById(id);
    }

    /**
     * 查询危险职业岗位人员清单及管理台帐列表
     * 
     * @param securityOccupationalHazardPositionPersonnelList 危险职业岗位人员清单及管理台帐
     * @return 危险职业岗位人员清单及管理台帐
     */
    @Override
    public List<SecurityOccupationalHazardPositionPersonnelList> selectSecurityOccupationalHazardPositionPersonnelListList(SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList)
    {
        return securityOccupationalHazardPositionPersonnelListMapper.selectSecurityOccupationalHazardPositionPersonnelListList(securityOccupationalHazardPositionPersonnelList);
    }

    /**
     * 新增危险职业岗位人员清单及管理台帐
     * 
     * @param securityOccupationalHazardPositionPersonnelList 危险职业岗位人员清单及管理台帐
     * @return 结果
     */
    @Override
    public int insertSecurityOccupationalHazardPositionPersonnelList(SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList)
    {
        return securityOccupationalHazardPositionPersonnelListMapper.insertSecurityOccupationalHazardPositionPersonnelList(securityOccupationalHazardPositionPersonnelList);
    }

    /**
     * 修改危险职业岗位人员清单及管理台帐
     * 
     * @param securityOccupationalHazardPositionPersonnelList 危险职业岗位人员清单及管理台帐
     * @return 结果
     */
    @Override
    public int updateSecurityOccupationalHazardPositionPersonnelList(SecurityOccupationalHazardPositionPersonnelList securityOccupationalHazardPositionPersonnelList)
    {
        return securityOccupationalHazardPositionPersonnelListMapper.updateSecurityOccupationalHazardPositionPersonnelList(securityOccupationalHazardPositionPersonnelList);
    }

    /**
     * 批量删除危险职业岗位人员清单及管理台帐
     * 
     * @param ids 需要删除的危险职业岗位人员清单及管理台帐主键
     * @return 结果
     */
    @Override
    public int deleteSecurityOccupationalHazardPositionPersonnelListByIds(Long[] ids)
    {
        return securityOccupationalHazardPositionPersonnelListMapper.deleteSecurityOccupationalHazardPositionPersonnelListByIds(ids);
    }

    /**
     * 删除危险职业岗位人员清单及管理台帐信息
     *
     * @param id 危险职业岗位人员清单及管理台帐主键
     * @return 结果
     */
    @Override
    public int deleteSecurityOccupationalHazardPositionPersonnelListById(Long id)
    {
        return securityOccupationalHazardPositionPersonnelListMapper.deleteSecurityOccupationalHazardPositionPersonnelListById(id);
    }

    /**
     * 更新最新导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId)
    {
        return securityOccupationalHazardPositionPersonnelListMapper.updateLatestImportedRelatedId(relatedId);
    }

    /**
     * 导入危险职业岗位人员清单及管理台帐数据
     *
     * @param personnelList 人员清单数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importPersonnelList(List<SecurityOccupationalHazardPositionPersonnelList> personnelList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(personnelList) || personnelList.size() == 0)
        {
            throw new ServiceException("导入人员数据不能为空！请检查Excel文件格式是否正确，确保表头与模板一致，并且至少包含一行数据。");
        }

        // 处理合并单元格数据
        processeMergedCellData(personnelList);

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (SecurityOccupationalHazardPositionPersonnelList personnel : personnelList)
        {
            try
            {
                // 跳过姓名为空的行（可能是合并单元格的空行）
                if (StringUtils.isEmpty(personnel.getName()))
                {
                    continue;
                }

                // 处理健康检查数据：将"√"转换为"是"，空值转换为"否"
                personnel.setPreJobHealthCheckup(convertHealthCheckData(personnel.getPreJobHealthCheckup()));
                personnel.setDuringJobHealthCheckup(convertHealthCheckData(personnel.getDuringJobHealthCheckup()));
                personnel.setPostJobHealthCheckup(convertHealthCheckData(personnel.getPostJobHealthCheckup()));

                // 直接插入数据，不检查重复
                this.insertSecurityOccupationalHazardPositionPersonnelList(personnel);
                successNum++;
                successMsg.append("<br/>" + successNum + "、人员 " + personnel.getName() + "（" + personnel.getTeamName() + "）导入成功");
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、人员 " + (personnel.getName() != null ? personnel.getName() : "未知") + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
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
     * 处理合并单元格数据
     * 将空的班组字段填充为上一行的班组值
     */
    private void processeMergedCellData(List<SecurityOccupationalHazardPositionPersonnelList> personnelList) {
        String currentTeamName = "";

        for (SecurityOccupationalHazardPositionPersonnelList personnel : personnelList) {
            // 如果班组字段不为空，更新当前班组
            if (StringUtils.isNotEmpty(personnel.getTeamName())) {
                currentTeamName = personnel.getTeamName().trim();
            } else {
                // 如果班组字段为空，使用当前班组值
                personnel.setTeamName(currentTeamName);
            }
        }
    }

    /**
     * 转换健康检查数据
     * 将"√"转换为"是"，空值或其他值转换为"否"
     */
    private String convertHealthCheckData(String value) {
        if (StringUtils.isNotEmpty(value) && "√".equals(value.trim())) {
            return "是";
        }
        return "否";
    }

    /**
     * 根据关联ID查询危险职业岗位人员清单及管理台帐列表
     *
     * @param relatedId 关联ID
     * @return 危险职业岗位人员清单及管理台帐集合
     */
    @Override
    public List<SecurityOccupationalHazardPositionPersonnelList> selectByRelatedId(Long relatedId)
    {
        return securityOccupationalHazardPositionPersonnelListMapper.selectByRelatedId(relatedId);
    }
}
