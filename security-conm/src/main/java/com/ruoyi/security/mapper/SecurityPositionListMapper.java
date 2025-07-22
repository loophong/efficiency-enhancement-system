package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityPositionList;

/**
 * 岗位清单Mapper接口
 * 
 * @author ruoyi
 * @date 2025-07-18
 */
public interface SecurityPositionListMapper 
{
    /**
     * 查询岗位清单
     * 
     * @param id 岗位清单主键
     * @return 岗位清单
     */
    public SecurityPositionList selectSecurityPositionListById(String id);

    /**
     * 查询岗位清单列表
     * 
     * @param securityPositionList 岗位清单
     * @return 岗位清单集合
     */
    public List<SecurityPositionList> selectSecurityPositionListList(SecurityPositionList securityPositionList);

    /**
     * 新增岗位清单
     * 
     * @param securityPositionList 岗位清单
     * @return 结果
     */
    public int insertSecurityPositionList(SecurityPositionList securityPositionList);

    /**
     * 修改岗位清单
     * 
     * @param securityPositionList 岗位清单
     * @return 结果
     */
    public int updateSecurityPositionList(SecurityPositionList securityPositionList);

    /**
     * 删除岗位清单
     * 
     * @param id 岗位清单主键
     * @return 结果
     */
    public int deleteSecurityPositionListById(String id);

    /**
     * 批量删除岗位清单
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityPositionListByIds(String[] ids);

    /**
     * 根据关联ID查询岗位清单列表
     * 
     * @param relatedId 关联ID
     * @return 岗位清单集合
     */
    public List<SecurityPositionList> selectSecurityPositionListByRelatedId(Long relatedId);

    /**
     * 更新最新导入数据的关联ID
     * 
     * @param relatedId 关联ID
     * @return 结果
     */
    public int updateLatestImportedRelatedId(Long relatedId);

    /**
     * 根据岗位名称和部门查询岗位清单
     * 
     * @param positionName 岗位名称
     * @param department 部门
     * @return 岗位清单
     */
    public SecurityPositionList selectByPositionNameAndDepartment(String positionName, String department);
}
