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
        log.info("当前读取的数据为:" + registerInfoExcel);

        if (registerInfoExcel.getInspectionItem() != null || registerInfoExcel.getId() instanceof Long) {
            cacheDataList.add(registerInfoExcel);
        }
        if(registerInfoExcel.getInspectionItem()==null){
            registerInfoExcel.setInspectionItem(cacheDataList.get(cacheDataList.size() - 1).getInspectionItem());
            cacheDataList.add(registerInfoExcel);
        }

    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("读取完毕");
        securityHazardousMaterialsSafetyInspectionMapper.batchInsert(cacheDataList);
    }

}
