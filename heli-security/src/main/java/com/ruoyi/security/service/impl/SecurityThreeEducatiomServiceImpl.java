package com.ruoyi.security.service.impl;

import java.io.InputStream;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.security.mapper.SecurityThreeEducatiomMapper;
import com.ruoyi.security.domain.SecurityThreeEducatiom;
import com.ruoyi.security.service.ISecurityThreeEducatiomService;

/**
 * 新职工三级安全教育台账Service业务层处理
 *
 * @author wang
 * @date 2025-07-18
 */
@Service
public class SecurityThreeEducatiomServiceImpl implements ISecurityThreeEducatiomService
{
    private static final Logger log = LoggerFactory.getLogger(SecurityThreeEducatiomServiceImpl.class);

    @Autowired
    private SecurityThreeEducatiomMapper securityThreeEducatiomMapper;

    /**
     * 查询新职工三级安全教育台账
     * 
     * @param id 新职工三级安全教育台账主键
     * @return 新职工三级安全教育台账
     */
    @Override
    public SecurityThreeEducatiom selectSecurityThreeEducatiomById(String id)
    {
        return securityThreeEducatiomMapper.selectSecurityThreeEducatiomById(id);
    }

    /**
     * 查询新职工三级安全教育台账列表
     *
     * @param securityThreeEducatiom 新职工三级安全教育台账
     * @return 新职工三级安全教育台账
     */
    @Override
    public List<SecurityThreeEducatiom> selectSecurityThreeEducatiomList(SecurityThreeEducatiom securityThreeEducatiom)
    {
        return securityThreeEducatiomMapper.selectSecurityThreeEducatiomList(securityThreeEducatiom);
    }

    /**
     * 根据关联ID查询新职工三级安全教育台账列表
     *
     * @param relatedId 关联ID
     * @return 新职工三级安全教育台账集合
     */
    @Override
    public List<SecurityThreeEducatiom> selectSecurityThreeEducatiomByRelatedId(String relatedId)
    {
        return securityThreeEducatiomMapper.selectSecurityThreeEducatiomByRelatedId(relatedId);
    }

    /**
     * 新增新职工三级安全教育台账
     * 
     * @param securityThreeEducatiom 新职工三级安全教育台账
     * @return 结果
     */
    @Override
    public int insertSecurityThreeEducatiom(SecurityThreeEducatiom securityThreeEducatiom)
    {
        return securityThreeEducatiomMapper.insertSecurityThreeEducatiom(securityThreeEducatiom);
    }

    /**
     * 修改新职工三级安全教育台账
     * 
     * @param securityThreeEducatiom 新职工三级安全教育台账
     * @return 结果
     */
    @Override
    public int updateSecurityThreeEducatiom(SecurityThreeEducatiom securityThreeEducatiom)
    {
        return securityThreeEducatiomMapper.updateSecurityThreeEducatiom(securityThreeEducatiom);
    }

    /**
     * 批量删除新职工三级安全教育台账
     * 
     * @param ids 需要删除的新职工三级安全教育台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityThreeEducatiomByIds(String[] ids)
    {
        return securityThreeEducatiomMapper.deleteSecurityThreeEducatiomByIds(ids);
    }

    /**
     * 删除新职工三级安全教育台账信息
     *
     * @param id 新职工三级安全教育台账主键
     * @return 结果
     */
    @Override
    public int deleteSecurityThreeEducatiomById(String id)
    {
        return securityThreeEducatiomMapper.deleteSecurityThreeEducatiomById(id);
    }

    /**
     * 导入新职工三级安全教育台账数据
     *
     * @param inputStream 文件输入流
     * @param updateSupport 是否更新已存在数据
     * @return 导入结果信息
     */
    @Override
    public String importData(InputStream inputStream, boolean updateSupport) throws Exception
    {
        ExcelUtil<SecurityThreeEducatiom> util = new ExcelUtil<SecurityThreeEducatiom>(SecurityThreeEducatiom.class);
        List<SecurityThreeEducatiom> dataList = util.importExcel(inputStream);
        String operName = "系统";
        if (StringUtils.isNull(dataList) || dataList.size() == 0)
        {
            throw new Exception("导入数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (SecurityThreeEducatiom data : dataList)
        {
            try
            {
                // 验证是否存在这个数据
                SecurityThreeEducatiom existData = securityThreeEducatiomMapper.selectSecurityThreeEducatiomById(data.getId());
                if (StringUtils.isNull(existData))
                {
                    data.setCreateBy(operName);
                    this.insertSecurityThreeEducatiom(data);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、姓名 " + data.getXingMing() + " 导入成功");
                }
                else if (updateSupport)
                {
                    data.setUpdateBy(operName);
                    this.updateSecurityThreeEducatiom(data);
                    successNum++;
                    successMsg.append("<br/>" + successNum + "、姓名 " + data.getXingMing() + " 更新成功");
                }
                else
                {
                    failureNum++;
                    failureMsg.append("<br/>" + failureNum + "、姓名 " + data.getXingMing() + " 已存在");
                }
            }
            catch (Exception e)
            {
                failureNum++;
                String msg = "<br/>" + failureNum + "、姓名 " + data.getXingMing() + " 导入失败：";
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
            log.warn("更新最近导入新职工三级安全教育台账关联ID失败：relatedId为空");
            return 0;
        }

        log.info("更新最近导入新职工三级安全教育台账关联ID: {}", relatedId);

        try {
            // 直接使用批量更新SQL
            int updatedCount = securityThreeEducatiomMapper.updateLatestImportedRelatedId(relatedId);
            log.info("批量更新新职工三级安全教育台账关联ID成功，更新记录数: {}", updatedCount);
            return updatedCount;
        } catch (Exception e) {
            log.error("更新新职工三级安全教育台账关联ID失败: {}", e.getMessage(), e);
            return 0;
        }
    }
}
