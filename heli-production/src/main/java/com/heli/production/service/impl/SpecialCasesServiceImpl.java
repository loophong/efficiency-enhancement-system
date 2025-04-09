package com.heli.production.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heli.production.domain.entity.SpecialCasesEntity;
import com.heli.production.mapper.SpecialCasesMapper;
import com.heli.production.service.ISpecialCasesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 特殊情况Service业务层处理
 *
 * @author hong
 * @date 2025-02-25
 */
@Slf4j
@Service
public class SpecialCasesServiceImpl extends ServiceImpl<SpecialCasesMapper,SpecialCasesEntity> implements ISpecialCasesService {
    @Autowired
    private SpecialCasesMapper specialCasesMapper;

    /**
     * 查询特殊情况
     *
     * @param id 特殊情况主键
     * @return 特殊情况
     */
    @Override
    public SpecialCasesEntity selectSpecialCasesById(String id) {
        return specialCasesMapper.selectSpecialCasesById(id);
    }

    /**
     * 查询特殊情况列表
     *
     * @param specialCases 特殊情况
     * @return 特殊情况
     */
    @Override
    public List<SpecialCasesEntity> selectSpecialCasesList(SpecialCasesEntity specialCases) {
        log.info("查询特殊情况service:{}", specialCases);
        return specialCasesMapper.selectSpecialCasesList(specialCases);
    }

    /**
     * 新增特殊情况
     *
     * @param specialCases 特殊情况
     * @return 结果
     */
    @Override
    public int insertSpecialCases(SpecialCasesEntity specialCases) {
        return specialCasesMapper.insertSpecialCases(specialCases);
    }

    /**
     * 修改特殊情况
     *
     * @param specialCases 特殊情况
     * @return 结果
     */
    @Override
    public int updateSpecialCases(SpecialCasesEntity specialCases) {
        return specialCasesMapper.updateSpecialCases(specialCases);
    }

    /**
     * 批量删除特殊情况
     *
     * @param ids 需要删除的特殊情况主键
     * @return 结果
     */
    @Override
    public int deleteSpecialCasesByIds(String[] ids) {
        return specialCasesMapper.deleteSpecialCasesByIds(ids);
    }

    /**
     * 删除特殊情况信息
     *
     * @param id 特殊情况主键
     * @return 结果
     */
    @Override
    public int deleteSpecialCasesById(String id) {
        return specialCasesMapper.deleteSpecialCasesById(id);
    }
}
