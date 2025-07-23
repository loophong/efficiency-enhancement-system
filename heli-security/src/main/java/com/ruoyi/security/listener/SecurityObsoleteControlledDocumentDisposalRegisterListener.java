package com.ruoyi.security.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.util.ListUtils;
import com.ruoyi.security.domain.SecurityObsoleteControlledDocumentDisposalRegister;
import com.ruoyi.security.mapper.SecurityObsoleteControlledDocumentDisposalRegisterMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.ruoyi.common.utils.StringUtils.isEmpty;

@Slf4j
public class SecurityObsoleteControlledDocumentDisposalRegisterListener extends AnalysisEventListener<SecurityObsoleteControlledDocumentDisposalRegister> {

    private SecurityObsoleteControlledDocumentDisposalRegisterMapper mapper;
    // 缓存数据
    private final List<SecurityObsoleteControlledDocumentDisposalRegister> cacheDataList = new ArrayList<>();
    private static final int BATCH_COUNT = 100;

    public SecurityObsoleteControlledDocumentDisposalRegisterListener(SecurityObsoleteControlledDocumentDisposalRegisterMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void invoke(SecurityObsoleteControlledDocumentDisposalRegister data, AnalysisContext context) {
        // 处理合并单元格：如果字段为空，自动补齐为上一条的值
        if (!cacheDataList.isEmpty()) {
            SecurityObsoleteControlledDocumentDisposalRegister prev = cacheDataList.get(cacheDataList.size() - 1);

            if (isEmpty(data.getFileName())) {
                data.setFileName(prev.getFileName());
            }
            if (isEmpty(data.getDocumentNumber())) {
                data.setDocumentNumber(prev.getDocumentNumber());
            }
            if (isEmpty(data.getRecyclingDepartment())) {
                data.setRecyclingDepartment(prev.getRecyclingDepartment());
            }
            if (isEmpty(data.getIsDestroyed())) {
                data.setIsDestroyed(prev.getIsDestroyed());
            }
            if (isEmpty(data.getRemarks())) {
                data.setRemarks(prev.getRemarks());
            }

        }
        // 清除ID，防止主键冲突
        data.setId(null);

        cacheDataList.add(data);

        // 达到批量处理数量时，保存数据
        if (cacheDataList.size() >= BATCH_COUNT) {
            saveData();
            // 清空缓存，准备下一批数据
            cacheDataList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        if (!cacheDataList.isEmpty()) {
            saveData();
        }
        log.info("所有数据解析完成！");
    }

    private void saveData() {
        if (!cacheDataList.isEmpty()) {
            log.info("{}条数据，开始存储数据库！", cacheDataList.size());
            mapper.batchInsert(cacheDataList);
            log.info("存储数据库成功！");
        }
    }
    
    // 添加获取数据列表的方法
    public List<SecurityObsoleteControlledDocumentDisposalRegister> getDataList() {
        return cacheDataList;
    }
}
