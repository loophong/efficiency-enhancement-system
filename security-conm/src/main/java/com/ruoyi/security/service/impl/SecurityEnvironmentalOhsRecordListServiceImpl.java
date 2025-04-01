package com.ruoyi.security.service.impl;

import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.ReadListener;
import com.ruoyi.security.listener.SecurityEnvironmentalOhsRecordListListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityEnvironmentalOhsRecordListMapper;
import com.ruoyi.security.domain.SecurityEnvironmentalOhsRecordList;
import com.ruoyi.security.service.ISecurityEnvironmentalOhsRecordListService;
import org.springframework.web.multipart.MultipartFile;
@Slf4j
/**
 * 环境职业健康安全记录清单Service业务层处理
 * 
 * @author wang
 * @date 2025-03-07
 */
@Service
public class SecurityEnvironmentalOhsRecordListServiceImpl implements ISecurityEnvironmentalOhsRecordListService 
{
    @Autowired
    private SecurityEnvironmentalOhsRecordListMapper securityEnvironmentalOhsRecordListMapper;

    /**
     * 查询环境职业健康安全记录清单
     * 
     * @param id 环境职业健康安全记录清单主键
     * @return 环境职业健康安全记录清单
     */
    @Override
    public SecurityEnvironmentalOhsRecordList selectSecurityEnvironmentalOhsRecordListById(Long id)
    {
        return securityEnvironmentalOhsRecordListMapper.selectSecurityEnvironmentalOhsRecordListById(id);
    }

    /**
     * 查询环境职业健康安全记录清单列表
     * 
     * @param securityEnvironmentalOhsRecordList 环境职业健康安全记录清单
     * @return 环境职业健康安全记录清单
     */
    @Override
    public List<SecurityEnvironmentalOhsRecordList> selectSecurityEnvironmentalOhsRecordListList(SecurityEnvironmentalOhsRecordList securityEnvironmentalOhsRecordList)
    {
        return securityEnvironmentalOhsRecordListMapper.selectSecurityEnvironmentalOhsRecordListList(securityEnvironmentalOhsRecordList);
    }

    /**
     * 新增环境职业健康安全记录清单
     * 
     * @param securityEnvironmentalOhsRecordList 环境职业健康安全记录清单
     * @return 结果
     */
    @Override
    public int insertSecurityEnvironmentalOhsRecordList(SecurityEnvironmentalOhsRecordList securityEnvironmentalOhsRecordList)
    {
        return securityEnvironmentalOhsRecordListMapper.insertSecurityEnvironmentalOhsRecordList(securityEnvironmentalOhsRecordList);
    }

    /**
     * 修改环境职业健康安全记录清单
     * 
     * @param securityEnvironmentalOhsRecordList 环境职业健康安全记录清单
     * @return 结果
     */
    @Override
    public int updateSecurityEnvironmentalOhsRecordList(SecurityEnvironmentalOhsRecordList securityEnvironmentalOhsRecordList)
    {
        return securityEnvironmentalOhsRecordListMapper.updateSecurityEnvironmentalOhsRecordList(securityEnvironmentalOhsRecordList);
    }

    /**
     * 批量删除环境职业健康安全记录清单
     * 
     * @param ids 需要删除的环境职业健康安全记录清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOhsRecordListByIds(Long[] ids)
    {
        return securityEnvironmentalOhsRecordListMapper.deleteSecurityEnvironmentalOhsRecordListByIds(ids);
    }

    /**
     * 删除环境职业健康安全记录清单信息
     * 
     * @param id 环境职业健康安全记录清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityEnvironmentalOhsRecordListById(Long id)
    {
        return securityEnvironmentalOhsRecordListMapper.deleteSecurityEnvironmentalOhsRecordListById(id);
    }

    public void readSalaryExcelToDB(String fileName, MultipartFile excelFile) {
        try {
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);
            try {
                EasyExcel.read(excelFile.getInputStream(), (Class) SecurityEnvironmentalOhsRecordList.class,
                        (ReadListener) new SecurityEnvironmentalOhsRecordListListener(securityEnvironmentalOhsRecordListMapper)).headRowNumber(6).sheet().doRead();
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
