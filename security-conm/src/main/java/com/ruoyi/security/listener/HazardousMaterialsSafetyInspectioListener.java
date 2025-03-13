package com.ruoyi.security.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.ruoyi.security.domain.SecurityHazardousMaterialsSafetyInspection;
import com.ruoyi.security.mapper.SecurityHazardousMaterialsSafetyInspectionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class HazardousMaterialsSafetyInspectioListener implements ReadListener<SecurityHazardousMaterialsSafetyInspection> {

    @Autowired
    private SecurityHazardousMaterialsSafetyInspectionMapper securityHazardousMaterialsSafetyInspectionMapper;

    public HazardousMaterialsSafetyInspectioListener(SecurityHazardousMaterialsSafetyInspectionMapper securityHazardousMaterialsSafetyInspectionMapperr) {
        this.securityHazardousMaterialsSafetyInspectionMapper = securityHazardousMaterialsSafetyInspectionMapperr;
    }


    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(SecurityHazardousMaterialsSafetyInspection registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中

        log.info("当前读取的数据为:" + registerInfoExcel);

        if (registerInfoExcel.getInspectionItem() != null) {
            securityHazardousMaterialsSafetyInspectionMapper.insertSecurityHazardousMaterialsSafetyInspection(registerInfoExcel);
        }

    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("读取完毕");
    }

//    /**
//     * 处理结尾, 不足100条
//     *
//     * @param analysisContext 全局监听内容
//     */
//    @Override
//    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//        saveToDB();
//        log.info("所有数据解析完成");
//    }
//
//    /**
//     * 将读取到的内容写入DB
//     */
//    private void saveToDB() {
//        log.info("开始写入数据库");
////        historyOrderMapper.insert(cacheDataList);
//        historyOrderMapper.batchInsert(cacheDataList);
//
////        historyOrderService.saveBatch(cacheDataList, BATCH_COUNT);
//
//    }
}
