package com.ruoyi.security.mapper;

import java.util.List;
import com.ruoyi.security.domain.SecurityShenpiBiao;

/**
 * 不符合、纠正措施和预防措施程序Mapper接口
 * 
 * @author wang
 * @date 2025-07-29
 */
public interface SecurityShenpiBiaoMapper 
{
    /**
     * 查询不符合、纠正措施和预防措施程序
     * 
     * @param id 不符合、纠正措施和预防措施程序主键
     * @return 不符合、纠正措施和预防措施程序
     */
    public SecurityShenpiBiao selectSecurityShenpiBiaoById(String id);

    /**
     * 查询不符合、纠正措施和预防措施程序列表
     * 
     * @param securityShenpiBiao 不符合、纠正措施和预防措施程序
     * @return 不符合、纠正措施和预防措施程序集合
     */
    public List<SecurityShenpiBiao> selectSecurityShenpiBiaoList(SecurityShenpiBiao securityShenpiBiao);

    /**
     * 新增不符合、纠正措施和预防措施程序
     * 
     * @param securityShenpiBiao 不符合、纠正措施和预防措施程序
     * @return 结果
     */
    public int insertSecurityShenpiBiao(SecurityShenpiBiao securityShenpiBiao);

    /**
     * 修改不符合、纠正措施和预防措施程序
     * 
     * @param securityShenpiBiao 不符合、纠正措施和预防措施程序
     * @return 结果
     */
    public int updateSecurityShenpiBiao(SecurityShenpiBiao securityShenpiBiao);

    /**
     * 删除不符合、纠正措施和预防措施程序
     * 
     * @param id 不符合、纠正措施和预防措施程序主键
     * @return 结果
     */
    public int deleteSecurityShenpiBiaoById(String id);

    /**
     * 批量删除不符合、纠正措施和预防措施程序
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSecurityShenpiBiaoByIds(String[] ids);
}
