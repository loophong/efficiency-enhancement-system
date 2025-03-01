package com.heli.device.fileTable.service.impl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import com.alibaba.excel.EasyExcel;
import com.heli.device.fileTable.listener.DetailsExcelListener;

import com.ruoyi.common.core.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.heli.device.fileTable.mapper.DeviceDetailsMapper;
import com.heli.device.fileTable.domain.DeviceDetails;
import com.heli.device.fileTable.service.IDeviceDetailsService;

/**
 * 设备台账Service业务层处理
 * 
 * @author YYY
 * @date 2025-01-19
 */
@Service
@Slf4j
public class DeviceDetailsServiceImpl implements IDeviceDetailsService 
{
    @Autowired
    private DeviceDetailsMapper deviceDetailsMapper;


    @Override
    public R<String> readDetailsToDB(String fileName, InputStream inputStream) {
        try {
            log.info("开始读取文件: {}", fileName);
            byte[] buffer = inputStream.readAllBytes();
            InputStream stream1 = new ByteArrayInputStream(buffer);
            InputStream stream2 = new ByteArrayInputStream(buffer);

            EasyExcel.read(stream1, DeviceDetails.class, new DetailsExcelListener(deviceDetailsMapper)).sheet().doRead();
            EasyExcel.read(stream2, DeviceDetails.class, new DetailsExcelListener(deviceDetailsMapper)).sheet(2).doRead();

            return R.ok("读取" + fileName + "文件成功");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("读取 " + fileName + " 文件失败, 原因: {}", e.getMessage());
            return R.fail("读取文件失败,当前上传的文件为：" + fileName);
        }
    }

    /**
     * 查询设备台账
     *
     * @param detailsId 设备台账主键
     * @return 设备台账
     */
    @Override
    public DeviceDetails selectDeviceDetailsByDetailsId(String detailsId)
    {
        return deviceDetailsMapper.selectDeviceDetailsByDetailsId(detailsId);
    }

    /**
     * 查询设备台账列表
     * 
     * @param deviceDetails 设备台账
     * @return 设备台账
     */
    @Override
    public List<DeviceDetails> selectDeviceDetailsList(DeviceDetails deviceDetails)
    {
        return deviceDetailsMapper.selectDeviceDetailsList(deviceDetails);
    }

    /**
     * 新增设备台账
     * 
     * @param deviceDetails 设备台账
     * @return 结果
     */
    @Override
    public int insertDeviceDetails(DeviceDetails deviceDetails)
    {
        return deviceDetailsMapper.insertDeviceDetails(deviceDetails);
    }

    /**
     * 修改设备台账
     * 
     * @param deviceDetails 设备台账
     * @return 结果
     */
    @Override
    public int updateDeviceDetails(DeviceDetails deviceDetails)
    {
        return deviceDetailsMapper.updateDeviceDetails(deviceDetails);
    }

    /**
     * 批量删除设备台账
     * 
     * @param detailsIds 需要删除的设备台账主键
     * @return 结果
     */
    @Override
    public int deleteDeviceDetailsByDetailsIds(String[] detailsIds)
    {
        return deviceDetailsMapper.deleteDeviceDetailsByDetailsIds(detailsIds);
    }

    /**
     * 删除设备台账信息
     * 
     * @param detailsId 设备台账主键
     * @return 结果
     */
    @Override
    public int deleteDeviceDetailsByDetailsId(String detailsId)
    {
        return deviceDetailsMapper.deleteDeviceDetailsByDetailsId(detailsId);
    }
}
