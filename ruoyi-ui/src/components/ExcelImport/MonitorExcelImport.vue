<template>
  <div class="import-excel">
    <excel-import ref="excelImportRef" v-bind="$attrs" @success="handleSuccess" />
  </div>
</template>

<script setup>
import ExcelImport from '@/components/ExcelImport/index'
import fileMonitor from '@/utils/fileMonitor'

const props = defineProps({
  // 自定义属性
})

const emit = defineEmits(['success'])

const excelImportRef = ref(null)

// 处理导入成功事件
const handleSuccess = (response) => {
  if (response && response.file) {
    // 记录文件导入信息
    const fileInfo = {
      fileName: response.file.name,
      filePath: response.file.name, // 导入文件通常不保存路径
      fileSize: response.file.size,
      fileType: response.file.type,
      isImport: true, // 标记为导入类型
      remark: '导入文件，数据已处理'
    }
    
    // 调用文件监控服务记录文件导入
    fileMonitor.recordFileUpload(fileInfo).then(res => {
      console.log('文件导入记录已保存:', res)
    }).catch(err => {
      console.error('文件导入记录保存失败:', err)
    })
  }
  
  // 向上传递success事件
  emit('success', response)
}

// 对外暴露导入组件的方法
defineExpose({
  // 导出原组件的方法
  click: () => excelImportRef.value?.click()
})
</script> 