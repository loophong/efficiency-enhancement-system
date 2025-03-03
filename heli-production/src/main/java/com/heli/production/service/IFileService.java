package com.heli.production.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.heli.production.domain.entity.FileEntity;

/**
 * 生产文件Service接口
 *
 * @author hong
 * @date 2025-03-03
 */
public interface IFileService extends IService<FileEntity> {

    /**
     * 查询生产文件列表
     *
     * @param productionFile 生产文件
     * @return 生产文件集合
     */
    public List<FileEntity> selectProductionFileList(FileEntity productionFile);

}
