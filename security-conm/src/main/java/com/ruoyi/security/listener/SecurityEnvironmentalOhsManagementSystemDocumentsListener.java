package com.ruoyi.security.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.ruoyi.security.domain.SecurityEnvironmentalOhsManagementSystemDocuments;
import com.ruoyi.security.mapper.SecurityEnvironmentalOhsManagementSystemDocumentsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
public class SecurityEnvironmentalOhsManagementSystemDocumentsListener implements ReadListener<SecurityEnvironmentalOhsManagementSystemDocuments> {
    @Autowired
    private SecurityEnvironmentalOhsManagementSystemDocumentsMapper securityEnvironmentalOhsManagementSystemDocumentsMapper;
    public SecurityEnvironmentalOhsManagementSystemDocumentsListener(SecurityEnvironmentalOhsManagementSystemDocumentsMapper securityEnvironmentalOhsManagementSystemDocumentsMapper)
    {
        this.securityEnvironmentalOhsManagementSystemDocumentsMapper = securityEnvironmentalOhsManagementSystemDocumentsMapper;
    }
    public void invoke(SecurityEnvironmentalOhsManagementSystemDocuments registerInfoExcel, AnalysisContext analysisContext){
        log.info("当前读取的数据为:" + registerInfoExcel);
        if (registerInfoExcel.getSystemName() != null) {
            securityEnvironmentalOhsManagementSystemDocumentsMapper.insertSecurityEnvironmentalOhsManagementSystemDocuments(registerInfoExcel);
        }
    }
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("读取完毕");
    }
}
