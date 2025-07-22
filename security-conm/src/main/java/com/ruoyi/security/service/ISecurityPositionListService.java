package com.ruoyi.security.service;

import java.util.List;
import com.ruoyi.security.domain.SecurityPositionList;

/**
 * 岗位清单Service接口
 * 
 * @author ruoyi
 * @date 2025-07-18
 */
public interface ISecurityPositionListService 
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
     * 批量删除岗位清单
     * 
     * @param ids 需要删除的岗位清单主键集合
     * @return 结果
     */
    public int deleteSecurityPositionListByIds(String[] ids);

    /**
     * 删除岗位清单信息
     * 
     * @param id 岗位清单主键
     * @return 结果
     */
    public int deleteSecurityPositionListById(String id);

    /**
     * 导入岗位清单数据
     * 
     * @param positionList 岗位清单数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importSecurityPositionList(List<SecurityPositionList> positionList, Boolean isUpdateSupport, String operName);

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
}
