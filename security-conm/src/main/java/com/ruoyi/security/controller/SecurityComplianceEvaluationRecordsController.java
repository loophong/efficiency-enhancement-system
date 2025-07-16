package com.ruoyi.security.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.lang3.StringUtils;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.DateStringConverter;
import com.ruoyi.security.domain.SecurityComplianceEvaluationRecords;
import com.ruoyi.security.service.ISecurityComplianceEvaluationRecordsService;
import com.ruoyi.security.mapper.SecurityComplianceEvaluationRecordsMapper;
import com.ruoyi.security.listener.SecurityComplianceEvaluationRecordsListener;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

/**
 * 合规性评价记录Controller
 * 
 * @author wang
 * @date 2025-03-02
 */
@Slf4j
@RestController
@RequestMapping("/security/compliance")
public class SecurityComplianceEvaluationRecordsController extends BaseController
{
    @Autowired
    private ISecurityComplianceEvaluationRecordsService securityComplianceEvaluationRecordsService;
    
    @Autowired
    private SecurityComplianceEvaluationRecordsMapper securityComplianceEvaluationRecordsMapper;

    /**
     * 查询合规性评价记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:compliance:list')")
    @GetMapping("/list")
    public TableDataInfo list(SecurityComplianceEvaluationRecords securityComplianceEvaluationRecords)
    {
        startPage();
        List<SecurityComplianceEvaluationRecords> list = securityComplianceEvaluationRecordsService.selectSecurityComplianceEvaluationRecordsList(securityComplianceEvaluationRecords);
        return getDataTable(list);
    }

    /**
     * 导出合规性评价记录列表
     */
    @PreAuthorize("@ss.hasPermi('security:compliance:export')")
    @Log(title = "合规性评价记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SecurityComplianceEvaluationRecords securityComplianceEvaluationRecords)
    {
        List<SecurityComplianceEvaluationRecords> list = securityComplianceEvaluationRecordsService.selectSecurityComplianceEvaluationRecordsList(securityComplianceEvaluationRecords);
        ExcelUtil<SecurityComplianceEvaluationRecords> util = new ExcelUtil<SecurityComplianceEvaluationRecords>(SecurityComplianceEvaluationRecords.class);
        util.exportExcel(response, list, "合规性评价记录数据");
    }

    /**
     * 获取合规性评价记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('security:compliance:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(securityComplianceEvaluationRecordsService.selectSecurityComplianceEvaluationRecordsById(id));
    }

    /**
     * 新增合规性评价记录
     */
    @PreAuthorize("@ss.hasPermi('security:compliance:add')")
    @Log(title = "合规性评价记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SecurityComplianceEvaluationRecords securityComplianceEvaluationRecords)
    {
        return toAjax(securityComplianceEvaluationRecordsService.insertSecurityComplianceEvaluationRecords(securityComplianceEvaluationRecords));
    }

    /**
     * 修改合规性评价记录
     */
    @PreAuthorize("@ss.hasPermi('security:compliance:edit')")
    @Log(title = "合规性评价记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SecurityComplianceEvaluationRecords securityComplianceEvaluationRecords)
    {
        return toAjax(securityComplianceEvaluationRecordsService.updateSecurityComplianceEvaluationRecords(securityComplianceEvaluationRecords));
    }

    /**
     * 删除合规性评价记录
     */
    @PreAuthorize("@ss.hasPermi('security:compliance:remove')")
    @Log(title = "合规性评价记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(securityComplianceEvaluationRecordsService.deleteSecurityComplianceEvaluationRecordsByIds(ids));
    }
    
    /**
     * 导入合规性评价记录
     */
    @PreAuthorize("@ss.hasPermi('security:compliance:import')")
    @Log(title = "合规性评价记录", businessType = BusinessType.IMPORT)
    @PostMapping("/import")
    public AjaxResult importData(MultipartFile file) throws Exception
    {
        log.info("接收到导入请求，文件名：{}", file.getOriginalFilename());
        try {
            // 检查文件是否为空
            if (file == null || file.isEmpty()) {
                log.error("上传的文件为空");
                return error("上传的文件为空");
            }
            
            // 检查文件类型
            String filename = file.getOriginalFilename();
            if (!filename.toLowerCase().endsWith(".xlsx") && !filename.toLowerCase().endsWith(".xls")) {
                log.error("文件格式不正确，仅支持.xlsx和.xls格式");
                return error("文件格式不正确，仅支持.xlsx和.xls格式");
            }
            
            log.info("开始导入文件: {}, 大小: {} 字节", filename, file.getSize());
            
            // 使用辅助类导入Excel
            List<SecurityComplianceEvaluationRecords> dataList = com.ruoyi.security.utils.ExcelImportHelper.importExcel(file.getInputStream());
            
            // 保存所有记录到数据库
            int successCount = 0;
            for (SecurityComplianceEvaluationRecords record : dataList) {
                try {
                    securityComplianceEvaluationRecordsMapper.insertSecurityComplianceEvaluationRecords(record);
                    successCount++;
                } catch (Exception e) {
                    log.error("保存记录失败: {}, 记录: {}", e.getMessage(), record);
                }
            }
            
            log.info("文件导入完成，成功导入 {} 条记录", successCount);
            return success("导入成功，共导入 " + successCount + " 条记录");
        } catch (Exception e) {
            log.error("导入失败: {}", e.getMessage(), e);
            return error("导入失败: " + e.getMessage());
        }
    }
    
    /**
     * 导入合规性评价记录
     *
     * @param recordsList 合规性评价记录列表
     * @param operName 操作人员
     * @return 结果
     */
    public String importRecords(List<SecurityComplianceEvaluationRecords> recordsList, String operName)
    {
        if (recordsList == null || recordsList.isEmpty())
        {
            return "导入数据为空！";
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        
        for (SecurityComplianceEvaluationRecords record : recordsList)
        {
            try
            {
                // 设置创建者和时间
                record.setCreateBy(operName);
                securityComplianceEvaluationRecordsService.insertSecurityComplianceEvaluationRecords(record);
                successNum++;
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、法律法规 " + record.getRegulationName() + " 导入失败：";
                failureMsg.append(msg).append(e.getMessage());
                log.error(msg, e);
            }
        }
        
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            return failureMsg.toString();
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条");
            return successMsg.toString();
        }
    }

    /**
     * 下载合规性评价记录导入模板
     */
    @PreAuthorize("@ss.hasPermi('security:compliance:import')")
    @GetMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws Exception
    {
        log.info("下载合规性评价记录导入模板");
        try {
            // 设置响应头
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            String fileName = "合规性评价记录数据模板";
            response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1") + ".xlsx");
            
            // 创建示例数据，确保模板包含正确的「是否合规」字段值
            List<SecurityComplianceEvaluationRecords> list = new ArrayList<>();
            
            // 示例1：合规示例
            SecurityComplianceEvaluationRecords example1 = new SecurityComplianceEvaluationRecords();
            example1.setRegulationName("《中华人民共和国安全生产法》");
            example1.setDocumentNumber("主席令第13号");
            example1.setIssuingUnit("全国人大常委会");
            example1.setIssuanceRevisionDate(new Date());
            example1.setImplementationDate(new Date());
            example1.setEffectiveness("法律");
            example1.setApplicableClauses("是"); // 设置为「是」
            example1.setComplianceStatus("已评审");
            list.add(example1);
            
            // 示例2：不合规示例
            SecurityComplianceEvaluationRecords example2 = new SecurityComplianceEvaluationRecords();
            example2.setRegulationName("《工伤保险条例》");
            example2.setDocumentNumber("国务院令第586号");
            example2.setIssuingUnit("国务院");
            example2.setIssuanceRevisionDate(new Date());
            example2.setImplementationDate(new Date());
            example2.setEffectiveness("行政法规");
            example2.setApplicableClauses("否"); // 设置为「否」
            example2.setComplianceStatus("需整改");
            list.add(example2);
            
            // 使用带有示例数据的模板导出
            ExcelUtil<SecurityComplianceEvaluationRecords> util = new ExcelUtil<>(SecurityComplianceEvaluationRecords.class);
            util.exportExcel(response, list, "合规性评价记录数据");
            
            log.info("成功生成合规性评价记录导入模板");
        } catch (Exception e) {
            log.error("下载模板失败: {}", e.getMessage(), e);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().println("{\"code\":500,\"msg\":\"下载模板失败: " + e.getMessage() + "\"}");
        }
    }
}
