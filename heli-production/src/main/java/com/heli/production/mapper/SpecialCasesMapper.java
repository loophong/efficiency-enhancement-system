package com.heli.production.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.production.domain.entity.SpecialCasesEntity;

import java.util.List;

/**
 * 特殊情况Mapper接口
 *
 * @author hong
 * @date 2025-02-25
 */
public interface SpecialCasesMapper extends BaseMapper<SpecialCasesEntity> {
    /**
     * 查询特殊情况
     *
     * @param id 特殊情况主键
     * @return 特殊情况
     */
    public SpecialCasesEntity selectSpecialCasesById(String id);

    /**
     * 查询特殊情况列表
     *
     * @param specialCases 特殊情况
     * @return 特殊情况集合
     */
    public List<SpecialCasesEntity> selectSpecialCasesList(SpecialCasesEntity specialCases);

    /**
     * 新增特殊情况
     *
     * @param specialCases 特殊情况
     * @return 结果
     */
    public int insertSpecialCases(SpecialCasesEntity specialCases);

    /**
     * 修改特殊情况
     *
     * @param specialCases 特殊情况
     * @return 结果
     */
    public int updateSpecialCases(SpecialCasesEntity specialCases);

    /**
     * 删除特殊情况
     *
     * @param id 特殊情况主键
     * @return 结果
     */
    public int deleteSpecialCasesById(String id);

    /**
     * 批量删除特殊情况
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSpecialCasesByIds(String[] ids);
}
