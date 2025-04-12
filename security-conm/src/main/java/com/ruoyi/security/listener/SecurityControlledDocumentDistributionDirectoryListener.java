package com.ruoyi.security.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.ruoyi.security.domain.SecurityControlledDocumentDistributionDirectory;
import com.ruoyi.security.mapper.SecurityControlledDocumentDistributionDirectoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;


@Slf4j
public class SecurityControlledDocumentDistributionDirectoryListener implements ReadListener<SecurityControlledDocumentDistributionDirectory> {
    @Autowired
    private SecurityControlledDocumentDistributionDirectoryMapper securityControlledDocumentDistributionDirectoryMapper;
    public SecurityControlledDocumentDistributionDirectoryListener(SecurityControlledDocumentDistributionDirectoryMapper securityControlledDocumentDistributionDirectoryMapper) {
        this.securityControlledDocumentDistributionDirectoryMapper = securityControlledDocumentDistributionDirectoryMapper;
    }
    public  void invoke(SecurityControlledDocumentDistributionDirectory registerInfoExcel, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", registerInfoExcel);
        securityControlledDocumentDistributionDirectoryMapper.insertSecurityControlledDocumentDistributionDirectory(registerInfoExcel);
    }
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
    }
}

