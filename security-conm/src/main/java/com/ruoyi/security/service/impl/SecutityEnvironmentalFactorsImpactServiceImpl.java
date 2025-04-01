package com.ruoyi.security.service.impl;

import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.ruoyi.security.listener.SecutityEnvironmentalFactorsImpactListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecutityEnvironmentalFactorsImpactMapper;
import com.ruoyi.security.domain.SecutityEnvironmentalFactorsImpact;
import com.ruoyi.security.service.ISecutityEnvironmentalFactorsImpactService;
import org.springframework.web.multipart.MultipartFile;
@Slf4j
/**
 * 环境因素清单Service业务层处理
 * 
 * @author wang
 * @date 2025-03-26
 */
@Service
public class SecutityEnvironmentalFactorsImpactServiceImpl implements ISecutityEnvironmentalFactorsImpactService 
{
    @Autowired
    private SecutityEnvironmentalFactorsImpactMapper secutityEnvironmentalFactorsImpactMapper;

    /**
     * 查询环境因素清单
     * 
     * @param id 环境因素清单主键
     * @return 环境因素清单
     */
    @Override
    public SecutityEnvironmentalFactorsImpact selectSecutityEnvironmentalFactorsImpactById(Long id)
    {
        return secutityEnvironmentalFactorsImpactMapper.selectSecutityEnvironmentalFactorsImpactById(id);
    }

    /**
     * 查询环境因素清单列表
     * 
     * @param secutityEnvironmentalFactorsImpact 环境因素清单
     * @return 环境因素清单
     */
    @Override
    public List<SecutityEnvironmentalFactorsImpact> selectSecutityEnvironmentalFactorsImpactList(SecutityEnvironmentalFactorsImpact secutityEnvironmentalFactorsImpact)
    {
        return secutityEnvironmentalFactorsImpactMapper.selectSecutityEnvironmentalFactorsImpactList(secutityEnvironmentalFactorsImpact);
    }

    /**
     * 新增环境因素清单
     * 
     * @param secutityEnvironmentalFactorsImpact 环境因素清单
     * @return 结果
     */
    @Override
    public int insertSecutityEnvironmentalFactorsImpact(SecutityEnvironmentalFactorsImpact secutityEnvironmentalFactorsImpact)
    {
        return secutityEnvironmentalFactorsImpactMapper.insertSecutityEnvironmentalFactorsImpact(secutityEnvironmentalFactorsImpact);
    }

    /**
     * 修改环境因素清单
     * 
     * @param secutityEnvironmentalFactorsImpact 环境因素清单
     * @return 结果
     */
    @Override
    public int updateSecutityEnvironmentalFactorsImpact(SecutityEnvironmentalFactorsImpact secutityEnvironmentalFactorsImpact)
    {
        return secutityEnvironmentalFactorsImpactMapper.updateSecutityEnvironmentalFactorsImpact(secutityEnvironmentalFactorsImpact);
    }

    /**
     * 批量删除环境因素清单
     * 
     * @param ids 需要删除的环境因素清单主键
     * @return 结果
     */
    @Override
    public int deleteSecutityEnvironmentalFactorsImpactByIds(Long[] ids)
    {
        return secutityEnvironmentalFactorsImpactMapper.deleteSecutityEnvironmentalFactorsImpactByIds(ids);
    }

    /**
     * 删除环境因素清单信息
     * 
     * @param id 环境因素清单主键
     * @return 结果
     */
    @Override
    public int deleteSecutityEnvironmentalFactorsImpactById(Long id)
    {
        return secutityEnvironmentalFactorsImpactMapper.deleteSecutityEnvironmentalFactorsImpactById(id);
    }

    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile) {
        try {
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);

            try {


                EasyExcel.read(excelFile.getInputStream(), SecutityEnvironmentalFactorsImpact.class,
                        new SecutityEnvironmentalFactorsImpactListener(secutityEnvironmentalFactorsImpactMapper)).headRowNumber(1).sheet().doRead();

                log.info("读取文件成功: {}", fileName);

            } catch (Exception e) {
                log.info("读取文件失败: {}", e.getMessage());
            }

//            return R.ok("读取" + fileName + "文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
//            return R.fail("读取文件失败,当前上传的文件为：" + fileName);
        }
    }
}

