<template>
  <div class="upload-file">
    <file-upload ref="fileUploadRef" v-model="fileUrl" v-bind="$attrs" @change="handleChange" />
  </div>
</template>

<script setup>
import FileUpload from '@/components/FileUpload/index'
import fileMonitor from '@/utils/fileMonitor'

const props = defineProps({
  modelValue: {
    type: [String, Object, Array],
    default: ''
  },
  isImport: {
    type: Boolean,
    default: false
  }
})

const emit = defineEmits(['update:modelValue', 'change'])
const fileUploadRef = ref(null)
const fileUrl = ref(props.modelValue)

watch(() => props.modelValue, val => {
  fileUrl.value = val
})

watch(() => fileUrl.value, val => {
  emit('update:modelValue', val)
})

// 处理文件上传变更
const handleChange = (file) => {
  if (file) {
    // 记录文件上传信息
    const fileInfo = {
      ...file,
      isImport: props.isImport
    }
    
    // 调用文件监控服务记录文件上传
    fileMonitor.recordFileUpload(fileInfo).then(res => {
      console.log('文件上传记录已保存:', res)
    }).catch(err => {
      console.error('文件上传记录保存失败:', err)
    })
  }
  
  // 向上传递change事件
  emit('change', file)
}

// 对外暴露上传组件的方法
defineExpose({
  upload: () => {
    return fileUploadRef.value?.upload()
  },
  clearFiles: () => fileUploadRef.value?.clearFiles(),
  handleRemove: (file) => fileUploadRef.value?.handleRemove(file)
})
</script> 