package com.ruoyi.security.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.ruoyi.security.domain.SecutityLegalRegulationsIdentificationList;
import com.ruoyi.security.mapper.SecutityLegalRegulationsIdentificationListMapper;
import com.alibaba.excel.read.listener.ReadListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class SecutityLegalRegulationsIdentificationListListener implements ReadListener<SecutityLegalRegulationsIdentificationList> {
    @Autowired
    private SecutityLegalRegulationsIdentificationListMapper secutityLegalRegulationsIdentificationListMapper;

    public SecutityLegalRegulationsIdentificationListListener(SecutityLegalRegulationsIdentificationListMapper secutityLegalRegulationsIdentificationListMapper)
    {
        this.secutityLegalRegulationsIdentificationListMapper = secutityLegalRegulationsIdentificationListMapper;
    }

    public void invoke(SecutityLegalRegulationsIdentificationList registerInfoExcel, AnalysisContext analysisContext)
    {
        log.info("解析到一条数据:{}", registerInfoExcel);
        secutityLegalRegulationsIdentificationListMapper.insertSecutityLegalRegulationsIdentificationList(registerInfoExcel);
    }

    public void doAfterAllAnalysed(AnalysisContext analysisContext)
    {
        log.info("所有数据解析完成");
    }
}
