package com.ruoyi.security.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.ruoyi.security.domain.SecutityEnvironmentalFactorsImpact;
import com.ruoyi.security.mapper.SecutityEnvironmentalFactorsImpactMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class SecutityEnvironmentalFactorsImpactListener implements ReadListener<SecutityEnvironmentalFactorsImpact> {
    @Autowired
    private SecutityEnvironmentalFactorsImpactMapper secutityEnvironmentalFactorsImpactMapper;

    public SecutityEnvironmentalFactorsImpactListener(SecutityEnvironmentalFactorsImpactMapper secutityEnvironmentalFactorsImpactMapper) {
        this.secutityEnvironmentalFactorsImpactMapper = secutityEnvironmentalFactorsImpactMapper;
    }
    public void invoke(SecutityEnvironmentalFactorsImpact registerInfoExcel, AnalysisContext analysisContext) {
        log.info("当前读取的数据为:" + registerInfoExcel);
        if (registerInfoExcel.getId() != null) {
            secutityEnvironmentalFactorsImpactMapper.insertSecutityEnvironmentalFactorsImpact(registerInfoExcel);
        }


    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("读取完成");
}
}
