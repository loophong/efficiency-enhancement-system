import useDictStore from '@/store/modules/dict'
import { getDicts } from '@/api/system/dict/data'
import { ref, toRefs } from 'vue'

/**
 * 获取字典数据
 */
export function useDict(...args) {
  const res = ref({});
  return (() => {
    args.forEach((dictType, index) => {
      res.value[dictType] = [];
      const dicts = useDictStore().getDict(dictType);
      if (dicts) {
        res.value[dictType] = dicts;
      } else {
        getDicts(dictType).then(resp => {
          res.value[dictType] = resp.data.map(p => ({ label: p.dictLabel, value: p.dictValue, elTagType: p.listClass, elTagClass: p.cssClass }))
          useDictStore().setDict(dictType, res.value[dictType]);
        })
      }
    })
    return toRefs(res.value);
  })()
}

/**
 * 文件分类字典数据
 */
export const file_category = [
  { label: '上传文件', value: 'UPLOAD', elTagType: 'primary' },
  { label: '导入文件', value: 'IMPORT', elTagType: 'success' }
]