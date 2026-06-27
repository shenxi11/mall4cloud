<!--
模块名: multishop_ai_operation_page
功能概述: 商家端运营 AI 占位页面，提供商品标题、短视频脚本和直播话术生成入口。
对外接口: 动态路由 component=multishop/ai-operation
依赖关系: api/multishop/ai-operation, Element Plus
输入输出: 输入运营提示词，输出 AI 配置状态和占位结果。
异常与错误: 接口异常由全局 request 拦截器提示，空提示词由本页提示。
维护说明: 接入真实 AI 服务时保持页面数据结构和 API 调用名称不变。
-->
<template>
  <div class="page-ai-operation">
    <el-alert
      title="AI服务未配置"
      type="warning"
      description="当前页面返回占位结果，可先用于梳理商品标题、短视频脚本和直播话术的运营流程。"
      show-icon
      :closable="false"
    />

    <div class="workspace">
      <div class="panel">
        <div class="panel-title">
          运营场景
        </div>
        <el-radio-group
          v-model="form.scene"
          class="scene-list"
        >
          <el-radio
            v-for="item in scenes"
            :key="item.scene"
            :label="item.scene"
            border
          >
            {{ item.result }}
          </el-radio>
        </el-radio-group>
        <el-input
          v-model="form.prompt"
          type="textarea"
          :rows="8"
          maxlength="1000"
          show-word-limit
          placeholder="输入商品资料、活动卖点或直播主题"
        />
        <el-button
          type="primary"
          :loading="loading"
          class="generate-btn"
          @click="generate"
        >
          生成内容
        </el-button>
      </div>

      <div class="panel">
        <div class="panel-title">
          生成结果
        </div>
        <el-descriptions
          :column="1"
          border
        >
          <el-descriptions-item label="服务商">
            {{ result.provider || '-' }}
          </el-descriptions-item>
          <el-descriptions-item label="配置状态">
            <el-tag :type="result.configured ? 'success' : 'warning'">
              {{ result.configured ? '已配置' : '未配置' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="建议提示词">
            {{ result.suggestedPrompt || '-' }}
          </el-descriptions-item>
        </el-descriptions>
        <div class="result-box">
          {{ result.result || '等待生成' }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import * as api from '@/api/multishop/ai-operation'
import { ElMessage } from 'element-plus'
import { onMounted, reactive, toRefs } from 'vue'

const Data = reactive({
  scenes: [],
  form: {
    scene: '',
    prompt: ''
  },
  result: {},
  loading: false
})
const { scenes, form, result, loading } = toRefs(Data)

onMounted(() => {
  api.scenes().then(res => {
    Data.scenes = res
    if (res.length) {
      Data.form.scene = res[0].scene
      Data.result = res[0]
    }
  })
})

const generate = () => {
  if (!Data.form.prompt) {
    ElMessage({
      message: '请输入运营提示词',
      type: 'warning',
      duration: 1500
    })
    return
  }
  Data.loading = true
  api.generate(Data.form).then(res => {
    Data.result = res
    Data.loading = false
  }).catch(() => {
    Data.loading = false
  })
}
</script>

<style lang="scss" scoped>
.page-ai-operation {
  .workspace {
    display: grid;
    grid-template-columns: minmax(360px, 1fr) minmax(360px, 1fr);
    gap: 16px;
    margin-top: 16px;
  }

  .panel {
    border: 1px solid #ebeef5;
    border-radius: 6px;
    padding: 16px;
    background: #fff;
  }

  .panel-title {
    font-size: 16px;
    font-weight: 600;
    margin-bottom: 12px;
  }

  .scene-list {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
    margin-bottom: 14px;
  }

  .generate-btn {
    margin-top: 14px;
  }

  .result-box {
    min-height: 160px;
    margin-top: 14px;
    padding: 14px;
    line-height: 1.8;
    white-space: pre-wrap;
    background: #f7f8fa;
    border-radius: 6px;
  }
}
</style>
