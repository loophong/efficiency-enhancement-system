import fileMonitor from '@/utils/fileMonitor'

export default {
  install(app) {
    // 全局注册文件监控服务
    app.config.globalProperties.$fileMonitor = fileMonitor

    // 拦截全局文件上传事件
    const originalFetch = window.fetch
    window.fetch = async function(...args) {
      try {
        const response = await originalFetch.apply(this, args)
        
        // 检查是否是文件上传相关请求
        const url = args[0]
        if (typeof url === 'string' && 
            (url.includes('/upload') || url.includes('/import') || url.includes('/file'))) {
          
          // 克隆响应以便多次读取
          const clone = response.clone()
          
          try {
            // 尝试解析响应
            const contentType = response.headers.get('content-type') || ''
            
            if (contentType.includes('application/json')) {
              const data = await clone.json()
              
              // 如果响应包含文件信息，记录到文件监控系统
              if (data && data.code === 200 && data.data) {
                const fileData = data.data
                if (fileData.fileName || fileData.url || fileData.name) {
                  const isImport = url.includes('/import')
                  fileMonitor.recordFileUpload({
                    fileName: fileData.fileName || fileData.name,
                    filePath: fileData.url || fileData.filePath,
                    size: fileData.size || 0,
                    type: fileData.type || '',
                    isImport
                  }).then(() => {
                    console.log('文件记录已保存')
                  }).catch(err => {
                    console.error('文件记录保存失败:', err)
                  })
                }
              }
            }
          } catch (err) {
            console.error('拦截文件上传监控出错:', err)
          }
        }
        
        return response
      } catch (error) {
        return Promise.reject(error)
      }
    }

    // 拦截XMLHttpRequest
    const originalXhrOpen = XMLHttpRequest.prototype.open
    const originalXhrSend = XMLHttpRequest.prototype.send
    
    XMLHttpRequest.prototype.open = function(method, url, ...args) {
      this._url = url
      return originalXhrOpen.apply(this, [method, url, ...args])
    }
    
    XMLHttpRequest.prototype.send = function(body) {
      const xhr = this
      const url = xhr._url || ''
      
      if (typeof url === 'string' && 
          (url.includes('/upload') || url.includes('/import') || url.includes('/file'))) {
        
        const originalOnLoad = xhr.onload
        xhr.onload = function(e) {
          if (xhr.status >= 200 && xhr.status < 300) {
            try {
              const response = JSON.parse(xhr.responseText)
              if (response && response.code === 200 && response.data) {
                const fileData = response.data
                if (fileData.fileName || fileData.url || fileData.name) {
                  const isImport = url.includes('/import')
                  fileMonitor.recordFileUpload({
                    fileName: fileData.fileName || fileData.name,
                    filePath: fileData.url || fileData.filePath,
                    size: fileData.size || 0,
                    type: fileData.type || '',
                    isImport
                  }).then(() => {
                    console.log('文件记录已保存')
                  }).catch(err => {
                    console.error('文件记录保存失败:', err)
                  })
                }
              }
            } catch (err) {
              console.error('XHR拦截文件上传监控出错:', err)
            }
          }
          
          if (originalOnLoad) {
            originalOnLoad.apply(xhr, [e])
          }
        }
      }
      
      return originalXhrSend.apply(this, [body])
    }
  }
} 