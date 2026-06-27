<!--
模块名: platform_live_room_page
功能概述: 平台端直播间管理页面，提供列表、筛选、编辑和删除能力。
对外接口: 动态路由 component=platform/live-room
依赖关系: api/platform/live-room, Element Plus, img-upload
输入输出: 输入运营人员表单数据，输出直播间管理请求。
异常与错误: 接口异常由全局 request 拦截器提示，表单缺失由本页校验。
维护说明: 直播服务商未绑定前，播放地址按人工录入处理。
-->
<template>
  <div class="page-live-room">
    <div class="filter-container">
      <el-form
        :inline="true"
        :model="pageQuery"
      >
        <el-form-item label="直播标题">
          <el-input
            v-model="pageQuery.title"
            clearable
            placeholder="直播标题"
          />
        </el-form-item>
        <el-form-item label="直播状态">
          <el-select
            v-model="pageQuery.liveStatus"
            clearable
            placeholder="直播状态"
          >
            <el-option
              label="预告"
              :value="0"
            />
            <el-option
              label="直播中"
              :value="1"
            />
            <el-option
              label="回放"
              :value="2"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="上线状态">
          <el-select
            v-model="pageQuery.status"
            clearable
            placeholder="上线状态"
          >
            <el-option
              label="下线"
              :value="0"
            />
            <el-option
              label="上线"
              :value="1"
            />
          </el-select>
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
        新增直播
      </el-button>
    </div>

    <el-table
      v-loading="pageLoading"
      :data="pageVO.list"
      border
      stripe
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column
        label="封面"
        prop="coverUrl"
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
        label="主播"
        prop="anchorName"
        width="120"
        align="center"
      />
      <el-table-column
        label="直播状态"
        prop="liveStatus"
        width="110"
        align="center"
      >
        <template #default="{row}">
          <el-tag :type="liveStatusTag(row.liveStatus)">
            {{ liveStatusText(row.liveStatus) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="上线状态"
        prop="status"
        width="110"
        align="center"
      >
        <template #default="{row}">
          <el-tag
            v-if="row.status === 1"
            type="success"
          >
            上线
          </el-tag>
          <el-tag
            v-else
            type="danger"
          >
            下线
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="开播时间"
        prop="startTime"
        width="180"
        align="center"
      />
      <el-table-column
        label="位置"
        prop="locationName"
        min-width="140"
      />
      <el-table-column
        label="排序"
        prop="seq"
        width="80"
        align="center"
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
            @click="openForm(row.liveRoomId)"
          >
            编辑
          </el-button>
          <el-button
            link
            type="primary"
            @click="deleteHandle(row.liveRoomId)"
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
      :title="dataForm.liveRoomId ? '编辑直播' : '新增直播'"
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
          label="直播标题"
          prop="title"
        >
          <el-input
            v-model="dataForm.title"
            maxlength="80"
            show-word-limit
          />
        </el-form-item>
        <el-form-item label="封面图">
          <img-upload v-model="dataForm.coverUrl" />
        </el-form-item>
        <el-form-item label="主播名称">
          <el-input v-model="dataForm.anchorName" />
        </el-form-item>
        <el-form-item label="直播状态">
          <el-radio-group v-model="dataForm.liveStatus">
            <el-radio :label="0">
              预告
            </el-radio>
            <el-radio :label="1">
              直播中
            </el-radio>
            <el-radio :label="2">
              回放
            </el-radio>
          </el-radio-group>
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
        <el-form-item label="直播时间">
          <el-date-picker
            v-model="dataForm.startTime"
            type="datetime"
            placeholder="开播时间"
          />
          <el-date-picker
            v-model="dataForm.endTime"
            type="datetime"
            placeholder="结束时间"
            class="date-end"
          />
        </el-form-item>
        <el-form-item label="播放地址">
          <el-input
            v-model="dataForm.playUrl"
            placeholder="直播流或播放页地址"
          />
        </el-form-item>
        <el-form-item label="回放地址">
          <el-input
            v-model="dataForm.replayUrl"
            placeholder="回放视频地址"
          />
        </el-form-item>
        <el-form-item label="关联商品ID">
          <el-input-number
            v-model="dataForm.relatedSpuId"
            :min="0"
            controls-position="right"
          />
        </el-form-item>
        <el-form-item label="地理位置">
          <el-input
            v-model="dataForm.locationName"
            placeholder="城市、门店或直播地点"
          />
        </el-form-item>
        <el-form-item label="经纬度">
          <el-input-number
            v-model="dataForm.longitude"
            :precision="6"
            placeholder="经度"
            controls-position="right"
          />
          <el-input-number
            v-model="dataForm.latitude"
            :precision="6"
            placeholder="纬度"
            controls-position="right"
            class="geo-input"
          />
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
import * as api from '@/api/platform/live-room'
import { ElMessage, ElMessageBox } from 'element-plus'
import { onMounted, reactive, ref, toRefs } from 'vue'

const defaultForm = () => ({
  liveRoomId: 0,
  title: '',
  coverUrl: '',
  anchorName: '',
  liveStatus: 0,
  status: 1,
  startTime: null,
  endTime: null,
  playUrl: '',
  replayUrl: '',
  relatedSpuId: null,
  seq: 0,
  locationName: '',
  longitude: null,
  latitude: null
})

const Data = reactive({
  resourcesUrl: import.meta.env.VITE_APP_RESOURCES_URL,
  pageQuery: {
    pageSize: 10,
    pageNum: 1,
    title: null,
    liveStatus: null,
    status: null
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
      { required: true, message: '请输入直播标题', trigger: 'blur' }
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
  Data.pageQuery.liveStatus = null
  Data.pageQuery.status = null
  getSearchList()
}

const openForm = (liveRoomId) => {
  Data.canSubmit = true
  Data.dataForm = defaultForm()
  Data.formVisible = true
  if (!liveRoomId) {
    return
  }
  api.get(liveRoomId).then(data => {
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
    const request = Data.dataForm.liveRoomId ? api.update(Data.dataForm) : api.save(Data.dataForm)
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

const deleteHandle = (liveRoomId) => {
  ElMessageBox.confirm('确定删除该直播间?', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  }).then(() => {
    api.deleteById(liveRoomId).then(() => {
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

const liveStatusText = (status) => {
  return ['预告', '直播中', '回放'][status] || '未知'
}

const liveStatusTag = (status) => {
  return ['warning', 'success', 'info'][status] || 'info'
}
</script>

<style lang="scss" scoped>
.page-live-room {
  .cover {
    width: 72px;
    height: 48px;
    object-fit: cover;
    border-radius: 4px;
  }

  .date-end,
  .geo-input {
    margin-left: 12px;
  }
}
</style>
