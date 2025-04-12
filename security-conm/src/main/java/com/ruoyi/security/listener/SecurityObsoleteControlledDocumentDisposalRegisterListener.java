package com.ruoyi.security.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.ruoyi.security.domain.SecurityObsoleteControlledDocumentDisposalRegister;
import com.ruoyi.security.mapper.SecurityObsoleteControlledDocumentDisposalRegisterMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
public class SecurityObsoleteControlledDocumentDisposalRegisterListener implements ReadListener<SecurityObsoleteControlledDocumentDisposalRegister> {
    @Autowired
    private SecurityObsoleteControlledDocumentDisposalRegisterMapper securityObsoleteControlledDocumentDisposalRegisterMapper;

    private static final int BATCH_COUNT = 1000;

    private List<SecurityObsoleteControlledDocumentDisposalRegister> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public SecurityObsoleteControlledDocumentDisposalRegisterListener(SecurityObsoleteControlledDocumentDisposalRegisterMapper securityObsoleteControlledDocumentDisposalRegisterMapper) {
        this.securityObsoleteControlledDocumentDisposalRegisterMapper = securityObsoleteControlledDocumentDisposalRegisterMapper;
    }
    public void invoke(SecurityObsoleteControlledDocumentDisposalRegister registerInfoExcel, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", registerInfoExcel);
        if(registerInfoExcel.getFileName() != null && !registerInfoExcel.getFileName().startsWith("确认人")){
            if(registerInfoExcel.getRecyclingDepartment() == null){
                registerInfoExcel.setRecyclingDepartment(cacheDataList.get(cacheDataList.size() - 1).getRecyclingDepartment());
            }
            cacheDataList.add(registerInfoExcel);

    }
    }
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
        securityObsoleteControlledDocumentDisposalRegisterMapper.batchInsert(cacheDataList);
    }
}
