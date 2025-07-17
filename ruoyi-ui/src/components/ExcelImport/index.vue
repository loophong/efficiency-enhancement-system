<template>
  <div>
    <el-button
      v-if="showButton"
      @click="handleImport"
      :type="buttonType"
      :plain="buttonPlain"
      :size="buttonSize"
      :icon="buttonIcon">
      {{ buttonText }}
    </el-button>

    <!-- 文件上传弹窗 -->
    <el-dialog :title="dialogTitle" v-model="uploadDialogVisible" width="35%" @close="resetUpload">
      <el-form :model="form" ref="form" label-width="90px">
        <el-form-item :label="moduleLabelText">
          <span :style="{ color: moduleLabelColor }">{{ moduleName }}</span>
          <br>
        </el-form-item>
        <el-form-item label="上传文件：">
          <div class="file-upload-container">
            <input
              type="file"
              ref="inputFile"
              @change="checkFile"
              accept=".xlsx,.xls"
              style="display: none;"
            />
            <el-button
              type="primary"
              plain
              @click="triggerFileSelect"
              icon="el-icon-upload"
            >
              选择文件
            </el-button>
            <span v-if="selectedFileName" class="selected-file-name">
              {{ selectedFileName }}
            </span>
            <span v-else class="file-tip">
              请选择 Excel 文件（.xlsx, .xls）
            </span>
          </div>
        </el-form-item>
        <slot name="extra-form-items"></slot>
      </el-form>
      <div slot="footer" class="dialog-footer" style="display: flex; justify-content: center;">
        <el-button @click="cancelUpload">取 消</el-button>
        <el-button type="primary" @click="uploadFile" v-if="!isLoading">确 定</el-button>
        <el-button type="primary" v-if="isLoading" :loading="true">上传中</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import request from '@/utils/request'
import { getToken } from '@/utils/auth'

export default {
  name: "ExcelImport",
  props: {
    // 导入API路径，必须
    importUrl: {
      type: String,
      required: true
    },
    // 模块名称，必须
    moduleName: {
      type: String,
      required: true
    },
    // 模块代码，可选，默认使用importUrl
    moduleCode: {
      type: String,
      default: null
    },
    // 对话框标题
    dialogTitle: {
      type: String,
      default: "Excel导入"
    },
    // 模块标签文本
    moduleLabelText: {
      type: String,
      default: "导入模块："
    },
    // 模块标签颜色
    moduleLabelColor: {
      type: String,
      default: "rgb(68, 140, 39)"
    },
    // 是否显示导入按钮
    showButton: {
      type: Boolean,
      default: true
    },
    // 按钮文字
    buttonText: {
      type: String,
      default: "导入"
    },
    // 按钮类型
    buttonType: {
      type: String,
      default: "success"
    },
    // 按钮是否为朴素
    buttonPlain: {
      type: Boolean,
      default: true
    },
    // 按钮大小
    buttonSize: {
      type: String,
      default: "default"
    },
    // 按钮图标
    buttonIcon: {
      type: String,
      default: "el-icon-upload"
    },
    // 额外请求参数
    extraParams: {
      type: Object,
      default: () => ({})
    }
  },
  data() {
    return {
      uploadDialogVisible: false,
      isLoading: false,
      form: {},
      selectedFileName: ''
    };
  },
  methods: {
    /** 导入按钮操作 */
    handleImport() {
      this.resetUpload();
      this.uploadDialogVisible = true;
    },

    /** 触发文件选择 */
    triggerFileSelect() {
      this.$nextTick(() => {
        const inputElement = this.$refs.inputFile;
        if (inputElement) {
          inputElement.click();
        }
      });
    },
    
    /** 表单重置 */
    resetUpload() {
      const inputElement = this.$refs.inputFile;
      if (inputElement) {
        inputElement.value = "";
      }
      this.form = {};
      this.selectedFileName = '';
      this.$emit("reset");
    },

    /** 取消上传 */
    cancelUpload() {
      this.uploadDialogVisible = false;
      this.resetUpload();
      this.$emit("cancel");
    },

    /** excel文件上传 */
    uploadFile() {
      const inputElement = this.$refs.inputFile;
      if (inputElement && inputElement.files.length > 0) {
        this.isLoading = true;
        const file = inputElement.files[0];
        
        // 构建参数
        const params = { ...this.extraParams };
        // 允许组件通过getExtraParams方法提供额外参数
        if (typeof this.$parent.getExtraParams === 'function') {
          Object.assign(params, this.$parent.getExtraParams());
        }
        
        // 创建表单数据
        const formData = new FormData();
        formData.append('file', file);
        
        // 添加额外参数
        if (params) {
          Object.keys(params).forEach(key => {
            formData.append(key, params[key]);
          });
        }
        
        // 发送请求
        request({
          url: this.importUrl,
          method: 'post',
          data: formData,
          headers: {
            'Content-Type': 'multipart/form-data',
            'Authorization': 'Bearer ' + getToken()
          }
        }).then(response => {
          this.$modal.msgSuccess(response.msg || "导入成功");
          this.uploadDialogVisible = false;
          this.isLoading = false;
          this.resetUpload();

          // 安全地添加文件信息到响应对象
          const responseData = response || {};
          if (typeof responseData === 'object' && responseData !== null) {
            responseData.file = file;
          }

          // 触发成功事件
          this.$emit("success", responseData);
        }).catch(error => {
          this.$modal.msgError(error.message || "导入失败");
          this.isLoading = false;
          this.$emit("error", error);
        });
      } else {
        this.$modal.msgError("请选择文件");
      }
    },

    /** 检查文件是否为excel */
    checkFile(event) {
      const inputElement = this.$refs.inputFile;

      if (!inputElement || !inputElement.files || inputElement.files.length === 0) {
        this.selectedFileName = '';
        return;
      }

      const file = inputElement.files[0];
      const fileName = file.name;
      const fileExt = fileName.split(".").pop().toLowerCase(); // 获取文件的扩展名

      if (fileExt !== "xlsx" && fileExt !== "xls") {
        this.$modal.msgError("只能上传 Excel 文件！");
        this.resetUpload();
        return false;
      }

      // 显示选中的文件名
      this.selectedFileName = fileName;
      this.$emit("file-selected", file);
      return true;
    },
    
    // 外部调用方法 - 手动触发导入
    triggerImport() {
      this.handleImport();
    }
  }
};
</script>

<style scoped>
.file-upload-container {
  display: flex;
  align-items: center;
  gap: 12px;
}

.selected-file-name {
  color: #409eff;
  font-size: 14px;
  font-weight: 500;
  padding: 4px 8px;
  background-color: #f0f9ff;
  border: 1px solid #b3d8ff;
  border-radius: 4px;
  max-width: 300px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.file-tip {
  color: #909399;
  font-size: 12px;
  font-style: italic;
}

.file-upload-container .el-button {
  margin-right: 0;
}
</style>