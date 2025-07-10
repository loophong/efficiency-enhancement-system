package com.heli.device.maintenanceTable.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import com.alibaba.excel.EasyExcel;
import com.heli.device.maintenanceTable.listener.MaintenanceTableListener;
import com.heli.device.maintenanceTable.mapper.DeviceMaintenanceTableMapper;
import com.heli.device.maintenanceTable.service.IDeviceMaintenanceTableService;
import com.ruoyi.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.device.maintenanceTable.domain.DeviceMaintenanceTable;


/**
 * 2.设备故障记录(导入)Service业务层处理
 *
 * @author ruoyi
 * @date 2025-01-09
 */
@Slf4j
@Service
public class DeviceMaintenanceTableServiceImpl implements IDeviceMaintenanceTableService
{
    @Autowired
    private DeviceMaintenanceTableMapper deviceMaintenanceTableMapper;


    /**
     * 读取故障记录到数据库
     *
     * @param fileName    文件名
     * @param inputStream 输入流
     * @return {@link R }<{@link String }>
     */
    @Override
    public R<String> readFaultRecordsToDB(String fileName, InputStream inputStream) {
        try {
            // 读取文件内容
            log.info("开始读取文件: {}", fileName);
            EasyExcel.read(inputStream, DeviceMaintenanceTable.class, new MaintenanceTableListener(deviceMaintenanceTableMapper)).sheet().headRowNumber(1).doRead();
            return R.ok("读取" + fileName + "文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
            return R.fail("读取文件失败,当前上传的文件为：" + fileName);
        }
    }

    /**
     * 构建设备故障树
     *
     * @return {@link List }<{@link Map }<{@link String }, {@link Object }>>
     */
    @Override
    public List<Map<String, Object>> handleTreeList(DeviceMaintenanceTable deviceMaintenanceTable){
        // 转换逻辑
        List<DeviceMaintenanceTable> list = deviceMaintenanceTableMapper.selectDeviceMaintenanceTableList(deviceMaintenanceTable);
        // 过滤掉 faultType 为 null 或空的项
        List<DeviceMaintenanceTable> processedList = list.stream()
                .peek(item -> {
                    if (item.getFaultType() == null || item.getFaultType().trim().isEmpty()) {
                        item.setFaultType("未定义类型");
                    }
                })
                .collect(Collectors.toList());
        List<Map<String, Object>> transformedList = processedList.stream().collect(Collectors.groupingBy(DeviceMaintenanceTable::getDeviceNum))
                .entrySet().stream().map(entry -> {
                    Map<String, Object> deviceMap = new HashMap<>();
                    String deviceNum = entry.getKey();
                    // 为deviceMap生成一个唯一的ID
                    String deviceId = UUID.nameUUIDFromBytes(deviceNum.getBytes()).toString();
                    deviceMap.put("uniqueId", deviceId);
                    deviceMap.put("deviceNum", deviceNum);

                    List<Map<String, Object>> faultTypeChildren = entry.getValue().stream()
                            .collect(Collectors.groupingBy(DeviceMaintenanceTable::getFaultType))
                            .entrySet().stream().map(faultEntry -> {
                                Map<String, Object> faultTypeMap = new HashMap<>();
                                String faultType = faultEntry.getKey();
                                // 为issueTypeMap生成一个唯一的ID，基于设备编号和问题类型组合
                                String issueTypeId = UUID.nameUUIDFromBytes((deviceNum + faultType).getBytes()).toString();
                                faultTypeMap.put("uniqueId", issueTypeId);
                                faultTypeMap.put("faultType", faultType);

                                List<Map<String, Object>> maintenanceItems = faultEntry.getValue().stream().map(maintenanceItem -> {
                                    Map<String, Object> itemMap = new HashMap<>();
                                    itemMap.put("uniqueId", maintenanceItem.getMaintenanceTableId());
                                    itemMap.put("maintenanceTableId", maintenanceItem.getMaintenanceTableId());
                                    itemMap.put("deviceName", maintenanceItem.getDeviceName());
                                    itemMap.put("workStatus", maintenanceItem.getWorkStatus());
                                    itemMap.put("issueType", maintenanceItem.getIssueType());
                                    itemMap.put("faultType", maintenanceItem.getFaultType());
                                    itemMap.put("applyBy", maintenanceItem.getApplyBy());
                                    itemMap.put("applyDepartment", maintenanceItem.getApplyDepartment());
                                    itemMap.put("maintenancePeople", maintenanceItem.getMaintenancePeople());
                                    itemMap.put("faultPhenomenon", maintenanceItem.getFaultPhenomenon());
                                    itemMap.put("maintenanceAnalysis", maintenanceItem.getMaintenanceAnalysis());
                                    itemMap.put("maintenanceContent", maintenanceItem.getMaintenanceContent());
                                    itemMap.put("reportedTime", maintenanceItem.getReportedTime());
                                    itemMap.put("resolutionTime", maintenanceItem.getResolutionTime());
                                    itemMap.put("faultDuration", maintenanceItem.getFaultDuration());
                                    itemMap.put("maintenanceCast", maintenanceItem.getMaintenanceCast());
                                    itemMap.put("ifDown", maintenanceItem.getIfDown());
                                    return itemMap;
                                }).collect(Collectors.toList());


                                BigDecimal timeCount = maintenanceItems.stream()
                                        .map(item -> {
                                            String faultDurationStr = (String) item.get("faultDuration");
                                            if (faultDurationStr == null) {
                                                return BigDecimal.ZERO;
                                            }
                                            try {
                                                // 使用 BigDecimal 精确计算
                                                return new BigDecimal(faultDurationStr.trim());
                                            } catch (NumberFormatException e) {
                                                return BigDecimal.ZERO;
                                            }
                                        })
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                faultTypeMap.put("timeSum", timeCount);

                                int count = maintenanceItems.size();
                                faultTypeMap.put("count", count);
                                faultTypeMap.put("children", maintenanceItems);
                                return faultTypeMap;
                            }).collect(Collectors.toList());

                    deviceMap.put("children", faultTypeChildren);
                    return deviceMap;
                }).collect(Collectors.toList());
        return transformedList;
    }

    /**
     * 查询设备故障记录
     *
     * @param
     * @return 设备故障记录
     */
    @Override
    public DeviceMaintenanceTable selectDeviceMaintenanceTableByMaintenanceTableId(Long maintenanceTableId)
    {
        return deviceMaintenanceTableMapper.selectDeviceMaintenanceTableByMaintenanceTableId(maintenanceTableId);
    }

    /**
     * 查询设备故障记录列表
     *
     * @param deviceMaintenanceTable 2.设备故障记录(导入)
     * @return 2.设备故障记录(导入)
     */
    @Override
    public List<DeviceMaintenanceTable> selectDeviceMaintenanceTableList(DeviceMaintenanceTable deviceMaintenanceTable)
    {
        return deviceMaintenanceTableMapper.selectDeviceMaintenanceTableList(deviceMaintenanceTable);
    }

    /**
     * 新增2.设备故障记录(导入)
     *
     * @param deviceMaintenanceTable 2.设备故障记录(导入)
     * @return 结果
     */
    @Override
    public int insertDeviceMaintenanceTable(DeviceMaintenanceTable deviceMaintenanceTable)
    {
        return deviceMaintenanceTableMapper.insertDeviceMaintenanceTable(deviceMaintenanceTable);
    }

    /**
     * 修改2.设备故障记录(导入)
     *
     * @param deviceMaintenanceTable 2.设备故障记录(导入)
     * @return 结果
     */
    @Override
    public int updateDeviceMaintenanceTable(DeviceMaintenanceTable deviceMaintenanceTable)
    {
        return deviceMaintenanceTableMapper.updateDeviceMaintenanceTable(deviceMaintenanceTable);
    }

    /**
     * 批量删除2.设备故障记录(导入)
     *
     * @param maintenanceTableIds 需要删除的2.设备故障记录(导入)主键
     * @return 结果
     */
    @Override
    public int deleteDeviceMaintenanceTableByMaintenanceTableIds(Long[] maintenanceTableIds)
    {
        return deviceMaintenanceTableMapper.deleteDeviceMaintenanceTableByMaintenanceTableIds(maintenanceTableIds);
    }

    /**
     * 删除2.设备故障记录(导入)信息
     *
     * @param maintenanceTableId 2.设备故障记录(导入)主键
     * @return 结果
     */
    @Override
    public int deleteDeviceMaintenanceTableByMaintenanceTableId(Long maintenanceTableId)
    {
        return deviceMaintenanceTableMapper.deleteDeviceMaintenanceTableByMaintenanceTableId(maintenanceTableId);
    }
}
