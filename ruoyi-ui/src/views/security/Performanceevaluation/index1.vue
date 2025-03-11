<template>
    <div class="app-container">
      <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      </el-form>
  
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="info"
            plain
            icon="Upload"
            @click="handleImport"
          >导入</el-button>
          <el-button 
          type="danger" plain
           icon="Delete" 
           @click="handleRemoveFile"
           >删除文件</el-button>
        </el-col>
        <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
  
  
      <!-- 文件预览区域 -->
      <div v-if="filePreview" class="file-preview">
        <div v-if="fileType === 'pdf'">
          <canvas ref="pdfCanvas"></canvas>
        </div>
        <div v-else-if="fileType === 'word'">
          <div v-html="wordContent"></div>
        </div>
        <div v-else-if="fileType === 'excel'">
          <div v-html="excelContent"></div>
        </div>
        <div v-else>
          <p>不支持的文件类型</p>
        </div>
      </div>
    </div>
  </template>
  
  <script setup name="Management">
  import { listManagement, getManagement, delManagement, addManagement, updateManagement } from "@/api/security/management";
  import { ref, reactive, toRefs, getCurrentInstance } from 'vue';
  import * as pdfjsLib from 'pdfjs-dist';
  import mammoth from 'mammoth';
  import * as XLSX from 'xlsx';
  import { ElMessage } from 'element-plus';
  const { proxy } = getCurrentInstance();
  import { onMounted } from 'vue';
  
  
  const managementList = ref([]);
  const open = ref(false);
  const loading = ref(true);
  const showSearch = ref(true);
  const ids = ref([]);
  const single = ref(true);
  const multiple = ref(true);
  const total = ref(0);
  const title = ref("");
  const filePreview = ref(false);
  const fileType = ref('');
  const wordContent = ref('');
  const excelContent = ref('');
  
  const data = reactive({
    form: {},
    queryParams: {
      pageNum: 1,
      pageSize: 10,
      address: null,
      beiyong1: null,
      beiyong2: null,
      beiyong3: null,
      beiyong4: null
    },
    rules: {
      beiyong2: [
        { required: true, message: "2备用字段不能为空", trigger: "blur" }
      ],
    }
  });
  
  onMounted(() => {
    const storedFileType = localStorage.getItem('fileType');
    const storedFilePreview = localStorage.getItem('filePreview');
  
    if (storedFilePreview === 'true') {
      fileType.value = storedFileType;
      filePreview.value = true;
  
      if (storedFileType === 'pdf') {
        const pdfCanvasData = localStorage.getItem('pdfCanvasData');
        const canvas = document.querySelector('canvas');
        const context = canvas.getContext('2d');
        const img = new Image();
        img.onload = () => {
          canvas.width = img.width;
          canvas.height = img.height;
          context.drawImage(img, 0, 0);
        };
        img.src = pdfCanvasData;
      } else if (storedFileType === 'word') {
        wordContent.value = localStorage.getItem('wordContent');
      } else if (storedFileType === 'excel') {
        excelContent.value = localStorage.getItem('excelContent');
      }
    }
  });
  
  
  const { queryParams, form, rules } = toRefs(data);
  
  /** 查询管理手册列表 */
  function getList() {
    loading.value = true;
    listManagement(queryParams.value).then(response => {
      managementList.value = response.rows;
      total.value = response.total;
      loading.value = false;
    });
  }
  
  // 取消按钮
  function cancel() {
    open.value = false;
    reset();
  }
  
  // 表单重置
  function reset() {
    form.value = {
      id: null,
      address: null,
      beiyong1: null,
      beiyong2: null,
      beiyong3: null,
      beiyong4: null
    };
    proxy.resetForm("managementRef");
  }
  
  /** 搜索按钮操作 */
  function handleQuery() {
    queryParams.value.pageNum = 1;
    getList();
  }
  
  /** 重置按钮操作 */
  function resetQuery() {
    proxy.resetForm("queryRef");
    handleQuery();
  }
  
  // 多选框选中数据
  function handleSelectionChange(selection) {
    ids.value = selection.map(item => item.id);
    single.value = selection.length != 1;
    multiple.value = !selection.length;
  }
  
  /** 新增按钮操作 */
  function handleAdd() {
    reset();
    open.value = true;
    title.value = "添加管理手册";
  }
  
  /** 修改按钮操作 */
  function handleUpdate(row) {
    reset();
    const _id = row.id || ids.value;
    getManagement(_id).then(response => {
      form.value = response.data;
      open.value = true;
      title.value = "修改管理手册";
    });
  }
  
  /** 提交按钮 */
  function submitForm() {
    proxy.$refs["managementRef"].validate(valid => {
      if (valid) {
        if (form.value.id != null) {
          updateManagement(form.value).then(response => {
            proxy.$modal.msgSuccess("修改成功");
            open.value = false;
            getList();
          });
        } else {
          addManagement(form.value).then(response => {
            proxy.$modal.msgSuccess("新增成功");
            open.value = false;
            getList();
          });
        }
      }
    });
  }
  
  /** 删除按钮操作 */
  function handleDelete(row) {
    const _ids = row.id || ids.value;
    proxy.$modal.confirm('是否确认删除管理手册编号为"' + _ids + '"的数据项？').then(function() {
      return delManagement(_ids);
    }).then(() => {
      getList();
      proxy.$modal.msgSuccess("删除成功");
    }).catch(() => {});
  }
  
  /** 导出按钮操作 */
  function handleExport() {
    proxy.download('security/management/export', {
      ...queryParams.value
    }, `management_${new Date().getTime()}.xlsx`);
  }
  
  /** 导入按钮操作 */
  function handleImport() {
    const input = document.createElement('input');
    input.type = 'file';
    input.accept = '.xls, .xlsx, .pdf, .doc, .docx';
    input.onchange = (event) => {
      const file = event.target.files[0];
      if (file) {
        handleFilePreview(file);
      }
    };
    input.click();
  }
  
  /** 文件预览处理 */
  function handleFilePreview(file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      const content = e.target.result;
      if (file.type === 'application/pdf') {
        fileType.value = 'pdf';
        pdfjsLib.getDocument(content).promise.then(pdfDoc => {
          pdfDoc.getPage(1).then(page => {
            const scale = 1.5;
            const viewport = page.getViewport({ scale });
            const canvas = document.querySelector('canvas');
            const context = canvas.getContext('2d');
            canvas.height = viewport.height;
            canvas.width = viewport.width;
  
            const renderContext = {
              canvasContext: context,
              viewport: viewport
            };
            page.render(renderContext);
            filePreview.value = true;
  
            // 存储文件预览内容到 localStorage
            localStorage.setItem('fileType', fileType.value);
            localStorage.setItem('filePreview', true);
            localStorage.setItem('pdfCanvasData', canvas.toDataURL());
          });
        });
      } else if (file.type === 'application/vnd.openxmlformats-officedocument.wordprocessingml.document' ||
                 file.type === 'application/msword') {
        fileType.value = 'word';
        mammoth.convertToHtml({ arrayBuffer: content })
          .then(result => {
            wordContent.value = result.value;
            filePreview.value = true;
  
            // 存储文件预览内容到 localStorage
            localStorage.setItem('fileType', fileType.value);
            localStorage.setItem('filePreview', true);
            localStorage.setItem('wordContent', wordContent.value);
          })
          .catch(err => {
            console.error(err);
            ElMessage.error('文件预览失败');
          });
      } else if (file.type === 'application/vnd.ms-excel' ||
                 file.type === 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet') {
        fileType.value = 'excel';
        const workbook = XLSX.read(content, { type: 'binary' });
        const firstSheetName = workbook.SheetNames[0];
        const worksheet = workbook.Sheets[firstSheetName];
        excelContent.value = XLSX.utils.sheet_to_html(worksheet);
        filePreview.value = true;
  
        // 存储文件预览内容到 localStorage
        localStorage.setItem('fileType', fileType.value);
        localStorage.setItem('filePreview', true);
        localStorage.setItem('excelContent', excelContent.value);
      } else {
        ElMessage.error('不支持的文件类型');
      }
    };
    reader.readAsArrayBuffer(file);
  }
  
  function handleRemoveFile() {
    filePreview.value = false;
    fileType.value = '';
    wordContent.value = '';
    excelContent.value = '';
  
    // 清除 localStorage 中的文件预览内容
    localStorage.removeItem('fileType');
    localStorage.removeItem('filePreview');
    localStorage.removeItem('pdfCanvasData');
    localStorage.removeItem('wordContent');
    localStorage.removeItem('excelContent');
  }
  
  getList();
  </script>
  
  <style scoped>
  .file-preview {
    margin-top: 20px;
    border: 1px solid #ccc;
    padding: 10px;
    max-height: 500px;
    overflow-y: auto;
  }
  </style>