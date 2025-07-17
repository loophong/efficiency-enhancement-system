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
          <input type="file" ref="inputFile" @change="checkFile"/>
          <br>
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
      inputFile: null,
      isLoading: false,
      form: {}
    };
  },
  mounted() {
    this.inputFile = this.$refs.inputFile;
  },
  methods: {
    /** 导入按钮操作 */
    handleImport() {
      this.resetUpload();
      this.uploadDialogVisible = true;
    },
    
    /** 表单重置 */
    resetUpload() {
      if (this.inputFile) {
        this.inputFile.value = "";
      }
      this.form = {};
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
      if (this.inputFile && this.inputFile.files.length > 0) {
        this.isLoading = true;
        const file = this.inputFile.files[0];
        
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
          
          // 添加文件信息到响应对象
          response.file = file;
          
          // 触发成功事件
          this.$emit("success", response);
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
    checkFile() {
      if (!this.inputFile || !this.inputFile.files || this.inputFile.files.length === 0) {
        return;
      }
      
      const file = this.inputFile.files[0];
      const fileName = file.name;
      const fileExt = fileName.split(".").pop().toLowerCase(); // 获取文件的扩展名

      if (fileExt !== "xlsx" && fileExt !== "xls") {
        this.$modal.msgError("只能上传 Excel 文件！");
        this.resetUpload();
        return false;
      }
      
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