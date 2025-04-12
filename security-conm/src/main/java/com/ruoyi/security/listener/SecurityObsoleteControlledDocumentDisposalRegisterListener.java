package com.ruoyi.security.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.ruoyi.security.domain.SecurityObsoleteControlledDocumentDisposalRegister;
import com.ruoyi.security.mapper.SecurityObsoleteControlledDocumentDisposalRegisterMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
@Slf4j
public class SecurityObsoleteControlledDocumentDisposalRegisterListener implements ReadListener<SecurityObsoleteControlledDocumentDisposalRegister> {
    @Autowired
    private SecurityObsoleteControlledDocumentDisposalRegisterMapper securityObsoleteControlledDocumentDisposalRegisterMapper;
    public SecurityObsoleteControlledDocumentDisposalRegisterListener(SecurityObsoleteControlledDocumentDisposalRegisterMapper securityObsoleteControlledDocumentDisposalRegisterMapper) {
        this.securityObsoleteControlledDocumentDisposalRegisterMapper = securityObsoleteControlledDocumentDisposalRegisterMapper;
    }
    public void invoke(SecurityObsoleteControlledDocumentDisposalRegister registerInfoExcel, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", registerInfoExcel);
        if (registerInfoExcel.getId() != null) {
            securityObsoleteControlledDocumentDisposalRegisterMapper.insertSecurityObsoleteControlledDocumentDisposalRegister(registerInfoExcel);
        }
    }
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
    }
}
