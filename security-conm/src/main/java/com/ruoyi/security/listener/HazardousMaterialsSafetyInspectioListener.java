package com.ruoyi.security.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.ruoyi.security.domain.SecurityHazardousMaterialsSafetyInspection;
import com.ruoyi.security.mapper.SecurityHazardousMaterialsSafetyInspectionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class HazardousMaterialsSafetyInspectioListener implements ReadListener<SecurityHazardousMaterialsSafetyInspection> {

    @Autowired
    private SecurityHazardousMaterialsSafetyInspectionMapper securityHazardousMaterialsSafetyInspectionMapper;

    public HazardousMaterialsSafetyInspectioListener(SecurityHazardousMaterialsSafetyInspectionMapper securityHazardousMaterialsSafetyInspectionMapperr) {
        this.securityHazardousMaterialsSafetyInspectionMapper = securityHazardousMaterialsSafetyInspectionMapperr;
    }
    private static final int BATCH_COUNT = 1000;

    private List<SecurityHazardousMaterialsSafetyInspection> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);


    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(SecurityHazardousMaterialsSafetyInspection registerInfoExcel, AnalysisContext analysisContext) {
        log.info("当前读取的数据为: {}", registerInfoExcel);

        // 检查数据是否有效
        if (isValidData(registerInfoExcel)) {
            cacheDataList.add(registerInfoExcel);
        }

        // 如果 inspectionItem 为空，使用上一个记录的 inspectionItem
        if (registerInfoExcel.getInspectionItem() == null && !cacheDataList.isEmpty()) {
            registerInfoExcel.setInspectionItem(cacheDataList.get(cacheDataList.size() - 1).getInspectionItem());
            cacheDataList.add(registerInfoExcel);
        }
    }

    /**
     * 检查数据是否有效
     *
     * @param registerInfoExcel 当前读取的数据
     * @return 数据是否有效
     */
    private boolean isValidData(SecurityHazardousMaterialsSafetyInspection registerInfoExcel) {
        // 检查 inspectionItem 是否不为空，或者 id 是 Long 类型
        return registerInfoExcel.getInspectionItem() != null || registerInfoExcel.getId() instanceof Long;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("读取完毕");
        securityHazardousMaterialsSafetyInspectionMapper.batchInsert(cacheDataList);
    }

}
