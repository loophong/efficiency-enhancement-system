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
            // 处理单元格合并：如果接收单位为空，使用前一条记录的接收单位
            if(registerInfoExcel.getReceivingUnit() == null && !cacheDataList.isEmpty()){
                registerInfoExcel.setReceivingUnit(cacheDataList.get(cacheDataList.size() - 1).getReceivingUnit());
            }

            // 清除ID，让数据库自动生成
            registerInfoExcel.setId(null);
            // 清除关联ID，稍后通过updateLatestImportedRelatedId方法设置
            registerInfoExcel.setRelatedId(null);

            cacheDataList.add(registerInfoExcel);
        }
    }

    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("所有数据解析完成！");
        securityControlledDocumentDistributionDirectoryMapper.batchInsert(cacheDataList);
    }
}

