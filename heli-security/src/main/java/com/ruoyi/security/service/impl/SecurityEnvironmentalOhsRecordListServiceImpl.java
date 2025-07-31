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
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
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

    /**
     * 导入环境职业健康安全记录清单数据
     *
     * @param recordList 环境职业健康安全记录清单数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importSecurityEnvironmentalOhsRecordList(List<SecurityEnvironmentalOhsRecordList> recordList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(recordList) || recordList.size() == 0) {
            throw new ServiceException("导入环境职业健康安全记录清单数据不能为空！");
        }

        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();

        for (SecurityEnvironmentalOhsRecordList record : recordList) {
            try {
                // 清空id字段，确保使用数据库自动生成的id
                record.setId(null);

                // 验证必填字段
                if (StringUtils.isEmpty(record.getRecordName())) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、记录名称不能为空");
                    continue;
                }

                // 检查是否存在相同的记录（基于记录编号、记录名称）
                SecurityEnvironmentalOhsRecordList existingRecord = findByUniqueFields(record.getRecordNumber(), record.getRecordName());

                if (existingRecord != null && isUpdateSupport) {
                    // 更新现有记录
                    record.setId(existingRecord.getId());
                    this.updateSecurityEnvironmentalOhsRecordList(record);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、记录名称 " + record.getRecordName() + " 更新成功");
                } else if (existingRecord == null) {
                    // 插入新记录
                    this.insertSecurityEnvironmentalOhsRecordList(record);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、记录名称 " + record.getRecordName() + " 导入成功");
                } else {
                    // 存在但不更新，跳过
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、记录名称 " + record.getRecordName() + " 已存在，跳过导入");
                }
            } catch (Exception e) {
                failureNum++;
                String recordName = record.getRecordName();
                String identifier = StringUtils.isNotEmpty(recordName) ? recordName : "第" + (successNum + failureNum) + "行";
                String msg = "<br/>" + failureNum + "、" + identifier + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }

        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }

        return successMsg.toString();
    }

    /**
     * 根据唯一字段查找记录
     */
    private SecurityEnvironmentalOhsRecordList findByUniqueFields(String recordNumber, String recordName) {
        SecurityEnvironmentalOhsRecordList query = new SecurityEnvironmentalOhsRecordList();
        if (StringUtils.isNotEmpty(recordNumber)) {
            query.setRecordNumber(recordNumber);
        }
        query.setRecordName(recordName);
        List<SecurityEnvironmentalOhsRecordList> list = securityEnvironmentalOhsRecordListMapper.selectSecurityEnvironmentalOhsRecordListList(query);
        return list.isEmpty() ? null : list.get(0);
    }

    /**
     * 根据关联ID查询环境职业健康安全记录清单列表
     *
     * @param relatedId 关联ID
     * @return 环境职业健康安全记录清单集合
     */
    @Override
    public List<SecurityEnvironmentalOhsRecordList> selectSecurityEnvironmentalOhsRecordListByRelatedId(Long relatedId) {
        return securityEnvironmentalOhsRecordListMapper.selectSecurityEnvironmentalOhsRecordListByRelatedId(relatedId);
    }

    /**
     * 更新最新导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 结果
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId) {
        return securityEnvironmentalOhsRecordListMapper.updateLatestImportedRelatedId(relatedId);
    }
}
