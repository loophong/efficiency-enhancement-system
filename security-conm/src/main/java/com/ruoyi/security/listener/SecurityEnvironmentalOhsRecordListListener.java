package com.ruoyi.security.listener;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.ruoyi.security.domain.SecurityEnvironmentalOhsRecordList;
import com.ruoyi.security.mapper.SecurityEnvironmentalOhsRecordListMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;

@Slf4j
public class SecurityEnvironmentalOhsRecordListListener implements ReadListener<SecurityEnvironmentalOhsRecordList> {
    @Autowired
    private SecurityEnvironmentalOhsRecordListMapper securityEnvironmentalOhsRecordListMapper;

    public SecurityEnvironmentalOhsRecordListListener(SecurityEnvironmentalOhsRecordListMapper securityEnvironmentalOhsRecordListMapper) {
        this.securityEnvironmentalOhsRecordListMapper = securityEnvironmentalOhsRecordListMapper;
    }
    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    public void invoke(SecurityEnvironmentalOhsRecordList registerInfoExcel, AnalysisContext analysisContext ) {
        log.info("当前读取的数据为:" + registerInfoExcel);

        if (registerInfoExcel.getRecordName() != null) {
            securityEnvironmentalOhsRecordListMapper.insertSecurityEnvironmentalOhsRecordList(registerInfoExcel);
        }
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        log.info("读取完毕");
    }
}


