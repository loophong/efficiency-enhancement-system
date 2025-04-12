package com.ruoyi.security.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.ruoyi.security.domain.SecurityControlledDocumentDistributionDirectory;
import com.ruoyi.security.mapper.SecurityControlledDocumentDistributionDirectoryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Slf4j
public class SecurityControlledDocumentDistributionDirectoryListener implements ReadListener<SecurityControlledDocumentDistributionDirectory> {
    @Autowired
    private SecurityControlledDocumentDistributionDirectoryMapper securityControlledDocumentDistributionDirectoryMapper;

    private static final int BATCH_COUNT = 1000;


    private List<SecurityControlledDocumentDistributionDirectory> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);


    public SecurityControlledDocumentDistributionDirectoryListener(SecurityControlledDocumentDistributionDirectoryMapper securityControlledDocumentDistributionDirectoryMapper) {
        this.securityControlledDocumentDistributionDirectoryMapper = securityControlledDocumentDistributionDirectoryMapper;
    }

    public  void invoke(SecurityControlledDocumentDistributionDirectory registerInfoExcel, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", registerInfoExcel);
//        securityControlledDocumentDistributionDirectoryMapper.insertSecurityControlledDocumentDistributionDirectory(registerInfoExcel);
        if(registerInfoExcel.getFileName() != null && !registerInfoExcel.getFileName().startsWith("接收人")){
            if(registerInfoExcel.getReceivingUnit() == null){
                registerInfoExcel.setReceivingUnit(cacheDataList.get(cacheDataList.size() - 1).getReceivingUnit());
            }
            cacheDataList.add(registerInfoExcel);
        }



    }

    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
        securityControlledDocumentDistributionDirectoryMapper.batchInsert(cacheDataList);
    }
}

