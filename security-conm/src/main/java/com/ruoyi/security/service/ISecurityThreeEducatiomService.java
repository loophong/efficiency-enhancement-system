package com.ruoyi.security.service;

import java.io.InputStream;
import java.util.List;
import com.ruoyi.security.domain.SecurityThreeEducatiom;

/**
 * 新职工三级安全教育台账Service接口
 * 
 * @author wang
 * @date 2025-07-18
 */
public interface ISecurityThreeEducatiomService 
{
    /**
     * 查询新职工三级安全教育台账
     * 
     * @param id 新职工三级安全教育台账主键
     * @return 新职工三级安全教育台账
     */
    public SecurityThreeEducatiom selectSecurityThreeEducatiomById(String id);

    /**
     * 查询新职工三级安全教育台账列表
     *
     * @param securityThreeEducatiom 新职工三级安全教育台账
     * @return 新职工三级安全教育台账集合
     */
    public List<SecurityThreeEducatiom> selectSecurityThreeEducatiomList(SecurityThreeEducatiom securityThreeEducatiom);

    /**
     * 根据关联ID查询新职工三级安全教育台账列表
     *
     * @param relatedId 关联ID
     * @return 新职工三级安全教育台账集合
     */
    public List<SecurityThreeEducatiom> selectSecurityThreeEducatiomByRelatedId(String relatedId);

    /**
     * 新增新职工三级安全教育台账
     * 
     * @param securityThreeEducatiom 新职工三级安全教育台账
     * @return 结果
     */
    public int insertSecurityThreeEducatiom(SecurityThreeEducatiom securityThreeEducatiom);

    /**
     * 修改新职工三级安全教育台账
     * 
     * @param securityThreeEducatiom 新职工三级安全教育台账
     * @return 结果
     */
    public int updateSecurityThreeEducatiom(SecurityThreeEducatiom securityThreeEducatiom);

    /**
     * 批量删除新职工三级安全教育台账
     * 
     * @param ids 需要删除的新职工三级安全教育台账主键集合
     * @return 结果
     */
    public int deleteSecurityThreeEducatiomByIds(String[] ids);

    /**
     * 删除新职工三级安全教育台账信息
     *
     * @param id 新职工三级安全教育台账主键
     * @return 结果
     */
    public int deleteSecurityThreeEducatiomById(String id);

    /**
     * 导入新职工三级安全教育台账数据
     *
     * @param inputStream 文件输入流
     * @param updateSupport 是否更新已存在数据
     * @return 导入结果信息
     */
    public String importData(InputStream inputStream, boolean updateSupport) throws Exception;

    /**
     * 更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    public int updateLatestImportedRelatedId(Long relatedId);
}
