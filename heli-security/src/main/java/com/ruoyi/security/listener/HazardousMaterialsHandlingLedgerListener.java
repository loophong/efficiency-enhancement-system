package com.ruoyi.security.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.security.domain.SecurityHazardousMaterialsHandlingLedger;
import com.ruoyi.security.domain.dto.SecurityHazardousMaterialsHandlingLedgerImportDto;
import com.ruoyi.security.mapper.SecurityHazardousMaterialsHandlingLedgerMapper;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 危化品处理台账Excel导入监听器
 *
 * @author wang
 * @date 2025-07-16
 */
@Slf4j
public class HazardousMaterialsHandlingLedgerListener implements ReadListener<SecurityHazardousMaterialsHandlingLedgerImportDto> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;

    /**
     * 缓存的数据
     */
    private List<SecurityHazardousMaterialsHandlingLedger> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 日期格式化器
     */
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 假设这个是一个DAO，当然有业务逻辑这个也可以是一个service。当然如果不用存储这个对象没用。
     */
    private SecurityHazardousMaterialsHandlingLedgerMapper securityHazardousMaterialsHandlingLedgerMapper;

    public HazardousMaterialsHandlingLedgerListener() {
        // 这里是demo，所以随便new一个。实际使用如果到了spring,请使用下面的有参构造函数
    }

    /**
     * 如果使用了spring,请使用这个构造函数。每次创建Listener的时候需要把spring管理的类传进来
     *
     * @param securityHazardousMaterialsHandlingLedgerMapper
     */
    public HazardousMaterialsHandlingLedgerListener(SecurityHazardousMaterialsHandlingLedgerMapper securityHazardousMaterialsHandlingLedgerMapper) {
        this.securityHazardousMaterialsHandlingLedgerMapper = securityHazardousMaterialsHandlingLedgerMapper;
    }

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(SecurityHazardousMaterialsHandlingLedgerImportDto data, AnalysisContext context) {
        log.info("解析到一条数据:{}", data);

        // 数据转换和验证
        try {
            SecurityHazardousMaterialsHandlingLedger entity = convertToEntity(data);
            if (isValidData(entity)) {
                cachedDataList.add(entity);
                // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
                if (cachedDataList.size() >= BATCH_COUNT) {
                    saveData();
                    // 存储完成清理 list
                    cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
                }
            } else {
                log.warn("跳过无效数据，行号: {}, 数据: {}", context.readRowHolder().getRowIndex(), data);
            }
        } catch (Exception e) {
            log.error("数据处理失败，行号: {}, 数据: {}, 错误: {}", context.readRowHolder().getRowIndex(), data, e.getMessage());
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    /**
     * 将DTO转换为实体类
     */
    private SecurityHazardousMaterialsHandlingLedger convertToEntity(SecurityHazardousMaterialsHandlingLedgerImportDto dto) {
        SecurityHazardousMaterialsHandlingLedger entity = new SecurityHazardousMaterialsHandlingLedger();

        // 转换处理时间
        if (StringUtils.isNotEmpty(dto.getHandlingTime())) {
            try {
                Date date = dateFormat.parse(dto.getHandlingTime().trim());
                entity.setHandlingTime(date);
            } catch (ParseException e) {
                log.warn("处理时间格式错误: {}, 跳过该字段", dto.getHandlingTime());
                // 处理时间格式错误时，不设置该字段，让其为null
            }
        }
        // 如果没有提供处理时间，保持为null，由用户决定是否填写

        // 转换字符串字段
        entity.setChemicalName(StringUtils.isNotEmpty(dto.getChemicalName()) ? dto.getChemicalName().trim() : null);
        entity.setUserInCharge(StringUtils.isNotEmpty(dto.getUserInCharge()) ? dto.getUserInCharge().trim() : null);
        entity.setHandlingMethod(StringUtils.isNotEmpty(dto.getHandlingMethod()) ? dto.getHandlingMethod().trim() : null);
        entity.setHandler(StringUtils.isNotEmpty(dto.getHandler()) ? dto.getHandler().trim() : null);

        // 转换处理量
        if (StringUtils.isNotEmpty(dto.getHandlingQuantity())) {
            try {
                BigDecimal quantity = new BigDecimal(dto.getHandlingQuantity().trim());
                entity.setHandlingQuantity(quantity);
            } catch (NumberFormatException e) {
                log.warn("处理量格式错误: {}", dto.getHandlingQuantity());
            }
        }

        // 转换是否合规 - 直接使用原始值
        if (StringUtils.isNotEmpty(dto.getIsCompliant())) {
            entity.setIsCompliant(dto.getIsCompliant().trim());
        } else {
            entity.setIsCompliant("否"); // 默认为不合规
        }

        return entity;
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        for (SecurityHazardousMaterialsHandlingLedger data : cachedDataList) {
            try {
                securityHazardousMaterialsHandlingLedgerMapper.insertSecurityHazardousMaterialsHandlingLedger(data);
            } catch (Exception e) {
                log.error("插入数据失败: {}, 错误: {}", data, e.getMessage());
            }
        }
        log.info("存储数据库成功！");
    }

    /**
     * 验证数据是否有效
     */
    private boolean isValidData(SecurityHazardousMaterialsHandlingLedger data) {
        // 至少需要有化学品名称
        return data.getChemicalName() != null && !data.getChemicalName().trim().isEmpty();
    }
}
