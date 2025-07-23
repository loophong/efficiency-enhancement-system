package com.ruoyi.security.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.security.mapper.SecurityDangerWangListMapper;
import com.ruoyi.security.domain.SecurityDangerWangList;
import com.ruoyi.security.service.ISecurityDangerWangListService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 风险网格化清单Service业务层处理
 * 
 * @author ruoyi
 * @date 2025-07-21
 */
@Service
public class SecurityDangerWangListServiceImpl implements ISecurityDangerWangListService
{
    private static final Logger log = LoggerFactory.getLogger(SecurityDangerWangListServiceImpl.class);

    @Autowired
    private SecurityDangerWangListMapper securityDangerWangListMapper;

    /**
     * 查询风险网格化清单
     * 
     * @param id 风险网格化清单主键
     * @return 风险网格化清单
     */
    @Override
    public SecurityDangerWangList selectSecurityDangerWangListById(String id)
    {
        return securityDangerWangListMapper.selectSecurityDangerWangListById(id);
    }

    /**
     * 查询风险网格化清单列表
     * 
     * @param securityDangerWangList 风险网格化清单
     * @return 风险网格化清单
     */
    @Override
    public List<SecurityDangerWangList> selectSecurityDangerWangListList(SecurityDangerWangList securityDangerWangList)
    {
        return securityDangerWangListMapper.selectSecurityDangerWangListList(securityDangerWangList);
    }

    /**
     * 新增风险网格化清单
     * 
     * @param securityDangerWangList 风险网格化清单
     * @return 结果
     */
    @Override
    public int insertSecurityDangerWangList(SecurityDangerWangList securityDangerWangList)
    {
        return securityDangerWangListMapper.insertSecurityDangerWangList(securityDangerWangList);
    }

    /**
     * 修改风险网格化清单
     * 
     * @param securityDangerWangList 风险网格化清单
     * @return 结果
     */
    @Override
    public int updateSecurityDangerWangList(SecurityDangerWangList securityDangerWangList)
    {
        return securityDangerWangListMapper.updateSecurityDangerWangList(securityDangerWangList);
    }

    /**
     * 批量删除风险网格化清单
     * 
     * @param ids 需要删除的风险网格化清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityDangerWangListByIds(String[] ids)
    {
        return securityDangerWangListMapper.deleteSecurityDangerWangListByIds(ids);
    }

    /**
     * 删除风险网格化清单信息
     *
     * @param id 风险网格化清单主键
     * @return 结果
     */
    @Override
    public int deleteSecurityDangerWangListById(String id)
    {
        return securityDangerWangListMapper.deleteSecurityDangerWangListById(id);
    }

    /**
     * 导入风险网格化清单数据
     *
     * @param dangerWangListList 风险网格化清单数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    @Override
    public String importDangerWangList(List<SecurityDangerWangList> dangerWangListList, Boolean isUpdateSupport, String operName)
    {
        if (StringUtils.isNull(dangerWangListList) || dangerWangListList.size() == 0)
        {
            throw new ServiceException("导入风险网格化清单数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SecurityDangerWangList dangerWangList : dangerWangListList)
        {
            try
            {
                // 检查对象是否为null
                if (dangerWangList == null)
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、数据行为空，跳过处理");
                    continue;
                }

                // 验证必填字段
                if (StringUtils.isEmpty(dangerWangList.getZerenKeshi()) && StringUtils.isEmpty(dangerWangList.getChangsuoBanzu()))
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、责任科室和场所/班组不能同时为空");
                    continue;
                }

                // 验证是否存在这个风险点
                SecurityDangerWangList u = securityDangerWangListMapper.selectSecurityDangerWangListByName(dangerWangList.getZerenKeshi(), dangerWangList.getChangsuoBanzu());
                if (StringUtils.isNull(u))
                {
                    dangerWangList.setCreateBy(operName);
                    this.insertSecurityDangerWangList(dangerWangList);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、风险点 " + dangerWangList.getZerenKeshi() + "-" + dangerWangList.getChangsuoBanzu() + " 导入成功");
                }
                else if (isUpdateSupport)
                {
                    dangerWangList.setId(u.getId());
                    dangerWangList.setUpdateBy(operName);
                    this.updateSecurityDangerWangList(dangerWangList);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、风险点 " + dangerWangList.getZerenKeshi() + "-" + dangerWangList.getChangsuoBanzu() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、风险点 " + dangerWangList.getZerenKeshi() + "-" + dangerWangList.getChangsuoBanzu() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String identifier = "未知数据";
                if (dangerWangList != null) {
                    try {
                        String zerenKeshi = dangerWangList.getZerenKeshi();
                        String changsuoBanzu = dangerWangList.getChangsuoBanzu();
                        identifier = (zerenKeshi != null ? zerenKeshi : "空") + "-" + (changsuoBanzu != null ? changsuoBanzu : "空");
                    } catch (Exception ex) {
                        identifier = "数据获取异常";
                    }
                }
                String msg = "<br/>" + failureNum + "、风险点 " + identifier + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new ServiceException(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 更新最近导入的数据关联ID
     *
     * @param relatedId 关联ID
     * @return 更新的行数
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId)
    {
        if (relatedId == null) {
            log.warn("更新最近导入风险网格化清单关联ID失败：relatedId为空");
            return 0;
        }

        log.info("更新最近导入风险网格化清单关联ID: {}", relatedId);

        try {
            // 直接使用批量更新SQL
            int updatedCount = securityDangerWangListMapper.updateLatestImportedRelatedId(relatedId);
            log.info("批量更新风险网格化清单关联ID成功，更新记录数: {}", updatedCount);
            return updatedCount;
        } catch (Exception e) {
            log.error("更新风险网格化清单关联ID失败: {}", e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 从Excel文件导入风险网格化清单数据
     */
    @Override
    public String importDangerWangListFromExcel(org.springframework.web.multipart.MultipartFile file, Boolean isUpdateSupport, String operName)
    {
        if (file == null || file.isEmpty()) {
            throw new ServiceException("上传文件不能为空");
        }

        try {
            // 使用Apache POI直接读取Excel文件
            org.apache.poi.ss.usermodel.Workbook workbook = org.apache.poi.ss.usermodel.WorkbookFactory.create(file.getInputStream());
            org.apache.poi.ss.usermodel.Sheet sheet = workbook.getSheetAt(0);

            int successNum = 0;
            int failureNum = 0;
            StringBuilder successMsg = new StringBuilder();
            StringBuilder failureMsg = new StringBuilder();

            // 用于存储上一个非空的责任科室值（处理合并单元格）
            String lastZerenKeshi = "";

            // 从第二行开始读取数据（第一行是标题）
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                org.apache.poi.ss.usermodel.Row row = sheet.getRow(i);
                if (row == null) {
                    continue;
                }

                try {
                    SecurityDangerWangList dangerWangList = new SecurityDangerWangList();

                    // 读取责任科室，如果为空则使用上一行的值（处理合并单元格）
                    String zerenKeshi = getCellValue(row, 1);
                    if (StringUtils.isEmpty(zerenKeshi)) {
                        zerenKeshi = lastZerenKeshi;
                    } else {
                        lastZerenKeshi = zerenKeshi;
                    }

                    // 读取各列数据
                    dangerWangList.setZerenKeshi(zerenKeshi);                     // 责任科室
                    dangerWangList.setChangsuoBanzu(getCellValue(row, 2));        // 场所/班组
                    dangerWangList.setGongxuShebei(getCellValue(row, 3));         // 工序/设备/区域
                    dangerWangList.setJituBuwei(getCellValue(row, 4));            // 具体部位
                    dangerWangList.setZuoyeGongzong(getCellValue(row, 5));        // 作业工种
                    dangerWangList.setZhuyaoWeixian(getCellValue(row, 6));        // 主要危险源/危险物质
                    dangerWangList.setKeNengFaSheng(getCellValue(row, 7));        // 可能发生的主要事故类型
                    dangerWangList.setWangLuoFuZeRen(getCellValue(row, 8));       // 网格负责人及联系电话
                    dangerWangList.setZhuyaoGongzuo(getCellValue(row, 9));        // 主要工作内容

                    // 检查必填字段
                    if (StringUtils.isEmpty(dangerWangList.getZerenKeshi()) && StringUtils.isEmpty(dangerWangList.getChangsuoBanzu())) {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、第" + (i+1) + "行：责任科室和场所/班组不能同时为空");
                        continue;
                    }

                    // 验证是否存在这个风险点
                    SecurityDangerWangList existingRecord = securityDangerWangListMapper.selectSecurityDangerWangListByName(
                        dangerWangList.getZerenKeshi(), dangerWangList.getChangsuoBanzu());

                    if (StringUtils.isNull(existingRecord)) {
                        // 新增
                        dangerWangList.setCreateBy(operName);
                        this.insertSecurityDangerWangList(dangerWangList);
                        successNum++;
                        String identifier = getRecordIdentifier(dangerWangList);
                        successMsg.append("<br/>" + successNum + "、风险点 " + identifier + " 导入成功");
                    } else if (isUpdateSupport) {
                        // 更新
                        dangerWangList.setId(existingRecord.getId());
                        dangerWangList.setUpdateBy(operName);
                        this.updateSecurityDangerWangList(dangerWangList);
                        successNum++;
                        String identifier = getRecordIdentifier(dangerWangList);
                        successMsg.append("<br/>" + successNum + "、风险点 " + identifier + " 更新成功");
                    } else {
                        // 已存在且不允许更新
                        failureNum++;
                        String identifier = getRecordIdentifier(dangerWangList);
                        failureMsg.append("<br/>" + failureNum + "、风险点 " + identifier + " 已存在");
                    }

                } catch (Exception e) {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、第" + (i+1) + "行导入失败：" + e.getMessage());
                    log.error("第" + (i+1) + "行导入失败", e);
                }
            }

            workbook.close();

            if (failureNum > 0) {
                failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
                throw new ServiceException(failureMsg.toString());
            } else {
                successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
            }
            return successMsg.toString();

        } catch (Exception e) {
            log.error("导入Excel文件失败", e);
            throw new ServiceException("导入Excel文件失败：" + e.getMessage());
        }
    }

    /**
     * 获取单元格值
     */
    private String getCellValue(org.apache.poi.ss.usermodel.Row row, int cellIndex) {
        org.apache.poi.ss.usermodel.Cell cell = row.getCell(cellIndex);
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf((long) cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    /**
     * 获取记录标识符
     */
    private String getRecordIdentifier(SecurityDangerWangList dangerWangList) {
        String zerenKeshi = dangerWangList.getZerenKeshi();
        String changsuoBanzu = dangerWangList.getChangsuoBanzu();
        return (zerenKeshi != null ? zerenKeshi : "空") + "-" + (changsuoBanzu != null ? changsuoBanzu : "空");
    }
}
