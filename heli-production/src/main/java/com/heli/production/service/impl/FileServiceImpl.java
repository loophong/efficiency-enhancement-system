package com.heli.production.service.impl;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.production.mapper.ProductionFileMapper;
import com.heli.production.domain.entity.FileEntity;
import com.heli.production.service.IFileService;

/**
 * 生产文件Service业务层处理
 *
 * @author hong
 * @date 2025-03-03
 */
@Service
public class FileServiceImpl extends ServiceImpl<ProductionFileMapper, FileEntity> implements IFileService {
    @Autowired
    private ProductionFileMapper productionFileMapper;

    /**
     * 查询生产文件列表
     *
     * @param productionFile 生产文件
     * @return 生产文件
     */
    @Override
    public List<FileEntity> selectProductionFileList(FileEntity productionFile) {
        return productionFileMapper.selectProductionFileList(productionFile);
    }
}
