<!--
模块名: multishop_short_video_page
功能概述: 商家端短视频管理页面，提供短视频创建、编辑、删除和筛选。
对外接口: 动态路由 component=multishop/short-video
依赖关系: api/multishop/short-video, Element Plus, img-upload
输入输出: 输入商家短视频资料，输出短视频管理请求。
异常与错误: 接口异常由全局 request 拦截器提示，表单缺失由本页校验。
维护说明: 当前版本使用手工视频地址，后续点播服务接入时保留页面契约。
-->
<template>
  <div class="page-short-video">
    <div class="filter-container">
      <el-form
        :inline="true"
        :model="pageQuery"
      >
        <el-form-item label="视频标题">
          <el-input
            v-model="pageQuery.title"
            clearable
            placeholder="视频标题"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            @click="getSearchList"
          >
            查询
          </el-button>
          <el-button @click="clearSearchInfo">
            清空
          </el-button>
        </el-form-item>
      </el-form>
      <el-button
        type="primary"
        class="filter-item"
        @click="openForm()"
      >
        新增短视频
      </el-button>
    </div>

    <el-table
      v-loading="pageLoading"
      :data="pageVO.list"
      border
      stripe
      fit
      style="width: 100%;"
    >
      <el-table-column
        label="封面"
        width="110"
        align="center"
      >
        <template #default="{row}">
          <img
            v-if="row.coverUrl"
            :src="addDomain(row.coverUrl)"
            class="cover"
          >
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column
        label="标题"
        prop="title"
        min-width="180"
      />
      <el-table-column
        label="上线"
        width="90"
        align="center"
      >
        <template #default="{row}">
          <el-tag :type="row.status === 1 ? 'success' : 'danger'">
            {{ row.status === 1 ? '上线' : '下线' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="播放"
        prop="viewCount"
        width="90"
        align="center"
      />
      <el-table-column
        label="点赞"
        prop="likeCount"
        width="90"
        align="center"
      />
      <el-table-column
        label="位置"
        prop="locationName"
        min-width="140"
      />
      <el-table-column
        label="操作"
        width="150"
        align="center"
      >
        <template #default="{row}">
          <el-button
            link
            type="primary"
            @click="openForm(row.shortVideoId)"
          >
            编辑
          </el-button>
          <el-button
            link
            type="primary"
            @click="deleteHandle(row.shortVideoId)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="pageVO.total>0"
      v-model:page="pageQuery.pageNum"
      v-model:limit="pageQuery.pageSize"
      :total="pageVO.total"
      @pagination="getPage()"
    />

    <el-dialog
      v-model="formVisible"
      :title="dataForm.shortVideoId ? '编辑短视频' : '新增短视频'"
      :close-on-click-modal="false"
      width="760px"
    >
      <el-form
        ref="dataFormRef"
        :model="dataForm"
        :rules="rules"
        label-width="100px"
      >
        <el-form-item
          label="视频标题"
          prop="title"
        >
          <el-input v-model="dataForm.title" />
        </el-form-item>
        <el-form-item label="封面图">
          <img-upload v-model="dataForm.coverUrl" />
        </el-form-item>
        <el-form-item
          label="视频地址"
          prop="videoUrl"
        >
          <el-input v-model="dataForm.videoUrl" />
        </el-form-item>
        <el-form-item label="视频文案">
          <el-input
            v-model="dataForm.description"
            type="textarea"
            :rows="4"
            maxlength="500"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="上线状态">
          <el-radio-group v-model="dataForm.status">
            <el-radio :label="1">
              上线
            </el-radio>
            <el-radio :label="0">
              下线
            </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="关联商品ID">
          <el-input-number
            v-model="dataForm.relatedSpuId"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
        <el-form-item label="地理位置">
          <el-input v-model="dataForm.locationName" />
        </el-form-item>
        <el-form-item label="排序">
          <el-input-number
            v-model="dataForm.seq"
            :min="0"
            :precision="0"
            controls-position="right"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="formVisible = false">
          取消
        </el-button>
        <el-button
          type="primary"
          :disabled="!canSubmit"
          @click="submitForm"
        >
          确定
        </el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import * as api from '@/api/multishop/short-video'
import { ElMessage, ElMessageBox } from 'element-plus'
import { onMounted, reactive, ref, toRefs } from 'vue'

const defaultForm = () => ({
  shortVideoId: 0,
  title: '',
  coverUrl: '',
  videoUrl: '',
  description: '',
  relatedSpuId: null,
  status: 1,
  viewCount: 0,
  likeCount: 0,
  seq: 0,
  locationName: ''
})

const Data = reactive({
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
  pageQuery: {
    pageSize: 10,
    pageNum: 1,
    title: null
  },
  pageVO: {
    list: [],
    total: 0,
    pages: 0
  },
  pageLoading: true,
  formVisible: false,
  canSubmit: true,
  dataForm: defaultForm(),
  rules: {
    title: [
      { required: true, message: '请输入视频标题', trigger: 'blur' }
    ],
    videoUrl: [
      { required: true, message: '请输入视频地址', trigger: 'blur' }
    ]
  }
})
const { pageQuery, pageVO, pageLoading, formVisible, canSubmit, dataForm, rules } = toRefs(Data)
const dataFormRef = ref()

onMounted(() => {
  getPage()
})

const getPage = () => {
  Data.pageLoading = true
  api.page(Data.pageQuery).then(pageVO => {
    Data.pageVO = pageVO
    Data.pageLoading = false
  }).catch(() => {
    Data.pageLoading = false
  })
}

const getSearchList = () => {
  Data.pageQuery.pageNum = 1
  getPage()
}

const clearSearchInfo = () => {
  Data.pageQuery.title = null
  getSearchList()
}

const openForm = (shortVideoId) => {
  Data.canSubmit = true
  Data.dataForm = defaultForm()
  Data.formVisible = true
  if (!shortVideoId) {
    return
  }
  api.get(shortVideoId).then(data => {
    Data.dataForm = { ...defaultForm(), ...data }
  })
}

const submitForm = () => {
  Data.canSubmit = false
  dataFormRef.value.validate(valid => {
    if (!valid) {
      Data.canSubmit = true
      return
    }
    const request = Data.dataForm.shortVideoId ? api.update(Data.dataForm) : api.save(Data.dataForm)
    request.then(() => {
      ElMessage({
        message: '操作成功',
        type: 'success',
        duration: 1500,
        onClose: () => {
          Data.formVisible = false
          Data.canSubmit = true
          getPage()
        }
      })
    }).catch(() => {
      Data.canSubmit = true
    })
  })
}

const deleteHandle = (shortVideoId) => {
  ElMessageBox.confirm('确定删除该短视频?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    api.deleteById(shortVideoId).then(() => {
      ElMessage({
        message: '删除成功',
        type: 'success',
        duration: 1500,
        onClose: () => getPage()
      })
    })
  })
}

const addDomain = (path) => {
  if (!path || /^https?:\/\//.test(path)) {
    return path
  }
  return Data.resourcesUrl + path
}
</script>

<style lang="scss" scoped>
.page-short-video {
  .cover {
    width: 72px;
    height: 48px;
    object-fit: cover;
    border-radius: 4px;
  }
}
</style>
