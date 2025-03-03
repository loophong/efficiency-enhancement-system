package com.heli.production.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heli.production.domain.entity.FileEntity;

/**
 * 生产文件Mapper接口
 *
 * @author hong
 * @date 2025-03-03
 */
public interface ProductionFileMapper extends BaseMapper<FileEntity> {

    /**
     * 查询生产文件列表
     *
     * @param productionFile 生产文件
     * @return 生产文件集合
     */
    public List<FileEntity> selectProductionFileList(FileEntity productionFile);
}
