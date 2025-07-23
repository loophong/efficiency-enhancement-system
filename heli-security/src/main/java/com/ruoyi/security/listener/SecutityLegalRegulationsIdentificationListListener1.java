package com.ruoyi.security.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.ruoyi.security.domain.SecutityLegalRegulationsIdentificationList1;
import com.ruoyi.security.mapper.SecutityLegalRegulationsIdentificationList1Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class SecutityLegalRegulationsIdentificationListListener1 implements ReadListener<SecutityLegalRegulationsIdentificationList1> {
    @Autowired
    private SecutityLegalRegulationsIdentificationList1Mapper secutityLegalRegulationsIdentificationList1Mapper;

    public SecutityLegalRegulationsIdentificationListListener1(SecutityLegalRegulationsIdentificationList1Mapper secutityLegalRegulationsIdentificationList1Mapper)
    {
        this.secutityLegalRegulationsIdentificationList1Mapper = secutityLegalRegulationsIdentificationList1Mapper;
    }

    public void invoke(SecutityLegalRegulationsIdentificationList1 registerInfoExcel, AnalysisContext analysisContext)
    {
        log.info("解析到一条数据:{}", registerInfoExcel);
        if (registerInfoExcel.getId() != null)
        {
            secutityLegalRegulationsIdentificationList1Mapper.insertSecutityLegalRegulationsIdentificationList1(registerInfoExcel);
        }
    }

    public void doAfterAllAnalysed(AnalysisContext analysisContext)
    {
        log.info("所有数据解析完成");
    }
}
