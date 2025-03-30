package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityThreeEducatiom;

/**
 * 新职工三级安全教育台账Mapper接口
 * 
 * @author wang
 * @date 2025-03-28
 */
public interface SecurityThreeEducatiomMapper 
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
     * 删除新职工三级安全教育台账
     * 
     * @param id 新职工三级安全教育台账主键
     * @return 结果
     */
    public int deleteSecurityThreeEducatiomById(String id);

    /**
     * 批量删除新职工三级安全教育台账
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityThreeEducatiomByIds(String[] ids);
}
