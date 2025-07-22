package com.heli.supplier.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.heli.supplier.domain.SupplierImportance;
import com.heli.supplier.domain.SupplierOnetimeSimple;
import com.heli.supplier.mapper.SupplierImportanceMapper;
import com.heli.supplier.mapper.SupplierOnetimeSimpleMapper;
import com.heli.supplier.service.impl.SupplierImportanceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.Date;
import java.util.List;

/**
 * ClassName: OnetimeSimpleListener
 * Package: com.heli.supplier.listener
 * Description:
 *
 * @Author
 * @Create 2025/2/21 15:29
 * @Version 1.0
 */
@Slf4j
public class ImportanceListener implements ReadListener<SupplierImportance> {

    private static final int BATCH_COUNT = 200;

    private int currentRow = 0;

    @Autowired
    private SupplierImportanceMapper supplierImportanceMapper;
    private SupplierImportanceServiceImpl importanceService;//++
//    private Date date;
    private Date uploadMonth;

    private List<SupplierImportance> cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    public ImportanceListener(SupplierImportanceMapper supplierImportanceMapper, Date uploadMonth) {
        this.supplierImportanceMapper = supplierImportanceMapper;
        this.uploadMonth = uploadMonth;
    }

    // 新增构造函数，接收service引用
    public ImportanceListener(SupplierImportanceMapper supplierImportanceMapper, Date uploadMonth,
                              SupplierImportanceServiceImpl importanceService) {
        this.supplierImportanceMapper = supplierImportanceMapper;
        this.uploadMonth = uploadMonth;
        this.importanceService = importanceService;
    }



    /**
     * 批量读取Excel写入DB
     *
     * @param registerInfoExcel 全局监听内容
     * @param analysisContext   读取到的Excel内容
     */
    @Override
    public void invoke(SupplierImportance registerInfoExcel, AnalysisContext analysisContext) {
        // 将监听到的数据存入缓存集合中
        log.info("当前读取的数据为:" + registerInfoExcel);

        // 数据处理
        if(registerInfoExcel.getSupplierCode() != null){
            registerInfoExcel.setUploadTime(uploadMonth);
            currentRow++;
            // 加入缓存
            cacheDataList.add(registerInfoExcel);
        }
        // 批量处理缓存的数据
        if (cacheDataList.size() >= BATCH_COUNT) {
            saveToDB();
            cacheDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 处理结尾, 不足100条
     *
     * @param analysisContext 全局监听内容
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveToDB();
        log.info("所有数据解析完成");
    }

    /**
     * 将读取到的内容写入DB
     */
//    private void saveToDB() {
//        log.info("开始写入数据库");
//        supplierImportanceMapper.insert(cacheDataList);
//    }
    private void saveToDB() {
        log.info("开始写入数据库，共 {} 条数据", cacheDataList.size());
        // 逐条插入数据，而不是批量插入，避免错误
        for (SupplierImportance item : cacheDataList) {
            try {
                supplierImportanceMapper.insert(item);
            } catch (Exception e) {
                log.error("插入数据失败: {}, 错误: {}", item.getSupplierName(), e.getMessage());
            }
        }
    }
}
