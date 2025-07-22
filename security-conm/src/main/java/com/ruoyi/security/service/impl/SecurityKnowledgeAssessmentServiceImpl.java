package com.ruoyi.security.service.impl;

import java.io.InputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.security.mapper.SecurityKnowledgeAssessmentMapper;
import com.ruoyi.security.domain.SecurityKnowledgeAssessment;
import com.ruoyi.security.service.ISecurityKnowledgeAssessmentService;

/**
 * 安全知识考核Service业务层处理
 *
 * @author wang
 * @date 2025-03-02
 */
@Service
public class SecurityKnowledgeAssessmentServiceImpl implements ISecurityKnowledgeAssessmentService
{
    private static final Logger log = LoggerFactory.getLogger(SecurityKnowledgeAssessmentServiceImpl.class);

    @Autowired
    private SecurityKnowledgeAssessmentMapper securityKnowledgeAssessmentMapper;

    /**
     * 查询安全知识考核
     * 
     * @param id 安全知识考核主键
     * @return 安全知识考核
     */
    @Override
    public SecurityKnowledgeAssessment selectSecurityKnowledgeAssessmentById(Long id)
    {
        return securityKnowledgeAssessmentMapper.selectSecurityKnowledgeAssessmentById(id);
    }

    /**
     * 查询安全知识考核列表
     * 
     * @param securityKnowledgeAssessment 安全知识考核
     * @return 安全知识考核
     */
    @Override
    public List<SecurityKnowledgeAssessment> selectSecurityKnowledgeAssessmentList(SecurityKnowledgeAssessment securityKnowledgeAssessment)
    {
        return securityKnowledgeAssessmentMapper.selectSecurityKnowledgeAssessmentList(securityKnowledgeAssessment);
    }

    /**
     * 新增安全知识考核
     * 
     * @param securityKnowledgeAssessment 安全知识考核
     * @return 结果
     */
    @Override
    public int insertSecurityKnowledgeAssessment(SecurityKnowledgeAssessment securityKnowledgeAssessment)
    {
        return securityKnowledgeAssessmentMapper.insertSecurityKnowledgeAssessment(securityKnowledgeAssessment);
    }

    /**
     * 修改安全知识考核
     * 
     * @param securityKnowledgeAssessment 安全知识考核
     * @return 结果
     */
    @Override
    public int updateSecurityKnowledgeAssessment(SecurityKnowledgeAssessment securityKnowledgeAssessment)
    {
        return securityKnowledgeAssessmentMapper.updateSecurityKnowledgeAssessment(securityKnowledgeAssessment);
    }

    /**
     * 批量删除安全知识考核
     * 
     * @param ids 需要删除的安全知识考核主键
     * @return 结果
     */
    @Override
    public int deleteSecurityKnowledgeAssessmentByIds(Long[] ids)
    {
        return securityKnowledgeAssessmentMapper.deleteSecurityKnowledgeAssessmentByIds(ids);
    }

    /**
     * 删除安全知识考核信息
     *
     * @param id 安全知识考核主键
     * @return 结果
     */
    @Override
    public int deleteSecurityKnowledgeAssessmentById(Long id)
    {
        return securityKnowledgeAssessmentMapper.deleteSecurityKnowledgeAssessmentById(id);
    }

    /**
     * 导入安全知识考核数据
     *
     * @param inputStream 文件输入流
     * @param updateSupport 是否更新已存在数据
     * @return 导入结果信息
     */
    @Override
    public String importData(InputStream inputStream, boolean updateSupport) throws Exception
    {
        ExcelUtil<SecurityKnowledgeAssessment> util = new ExcelUtil<SecurityKnowledgeAssessment>(SecurityKnowledgeAssessment.class);
        List<SecurityKnowledgeAssessment> dataList = util.importExcel(inputStream);
        String operName = "系统";
        if (StringUtils.isNull(dataList) || dataList.size() == 0)
        {
            throw new Exception("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SecurityKnowledgeAssessment data : dataList)
        {
            try
            {
                // 验证是否存在这个数据
                SecurityKnowledgeAssessment existData = securityKnowledgeAssessmentMapper.selectSecurityKnowledgeAssessmentById(data.getId());
                if (StringUtils.isNull(existData))
                {
                    data.setCreateBy(operName);
                    this.insertSecurityKnowledgeAssessment(data);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、考生姓名 " + data.getExamineeName() + " 导入成功");
                }
                else if (updateSupport)
                {
                    data.setUpdateBy(operName);
                    this.updateSecurityKnowledgeAssessment(data);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、考生姓名 " + data.getExamineeName() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、考生姓名 " + data.getExamineeName() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、考生姓名 " + data.getExamineeName() + " 导入失败：";
                failureMsg.append(msg + e.getMessage());
                log.error(msg, e);
            }
        }
        if (failureNum > 0)
        {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new Exception(failureMsg.toString());
        }
        else
        {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        return successMsg.toString();
    }

    /**
     * 更新最近导入数据的关联ID
     *
     * @param relatedId 关联ID
     * @return 更新的记录数
     */
    @Override
    public int updateLatestImportedRelatedId(Long relatedId) {
        if (relatedId == null) {
            log.warn("更新最近导入安全知识考核关联ID失败：relatedId为空");
            return 0;
        }

        log.info("更新最近导入安全知识考核关联ID: {}", relatedId);

        try {
            // 直接使用批量更新SQL
            int updatedCount = securityKnowledgeAssessmentMapper.updateLatestImportedRelatedId(relatedId);
            log.info("批量更新安全知识考核关联ID成功，更新记录数: {}", updatedCount);
            return updatedCount;
        } catch (Exception e) {
            log.error("更新安全知识考核关联ID失败: {}", e.getMessage(), e);
            return 0;
        }
    }

    /**
     * 根据关联ID查询安全知识考核列表
     *
     * @param relatedId 关联ID
     * @return 安全知识考核集合
     */
    @Override
    public List<SecurityKnowledgeAssessment> selectSecurityKnowledgeAssessmentByRelatedId(Long relatedId)
    {
        return securityKnowledgeAssessmentMapper.selectSecurityKnowledgeAssessmentByRelatedId(relatedId);
    }
}
