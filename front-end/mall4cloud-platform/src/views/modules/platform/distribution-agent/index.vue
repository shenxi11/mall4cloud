<!--
模块名: platform_distribution_agent_page
功能概述: 平台端分销代理管理入口，占位展示代理审核、规则和结算的管理落点。
对外接口: 动态路由 component=platform/distribution-agent
依赖关系: Element Plus
输入输出: 当前不提交数据，仅展示后续业务接入位置。
异常与错误: 无接口调用错误。
维护说明: 接入分销后应补充代理申请、关系链、佣金规则和结算接口。
-->
<template>
  <div class="page-distribution-agent">
    <div class="summary">
      <div
        v-for="item in summary"
        :key="item.label"
        class="summary-item"
      >
        <div class="value">
          {{ item.value }}
        </div>
        <div class="label">
          {{ item.label }}
        </div>
      </div>
    </div>

    <el-table
      :data="agentList"
      border
      stripe
      fit
      style="width: 100%;"
    >
      <template #empty>
        <el-empty description="待接入分销代理接口" />
      </template>
      <el-table-column
        label="代理名称"
        prop="name"
        min-width="160"
      />
      <el-table-column
        label="等级"
        prop="level"
        width="120"
        align="center"
      />
      <el-table-column
        label="状态"
        prop="status"
        width="120"
        align="center"
      />
      <el-table-column
        label="累计佣金"
        prop="commission"
        width="140"
        align="center"
      />
      <el-table-column
        label="操作"
        width="160"
        align="center"
      />
    </el-table>
  </div>
</template>

<script setup>
import { reactive, toRefs } from 'vue'

const Data = reactive({
  summary: [
    { label: '待审核代理', value: 0 },
    { label: '有效代理', value: 0 },
    { label: '本月佣金', value: '0.00' },
    { label: '待结算', value: '0.00' }
  ],
  agentList: []
})
const { summary, agentList } = toRefs(Data)
</script>

<style lang="scss" scoped>
.page-distribution-agent {
  .summary {
    display: grid;
    grid-template-columns: repeat(4, minmax(120px, 1fr));
    gap: 12px;
    margin-bottom: 16px;
  }

  .summary-item {
    border: 1px solid #ebeef5;
    border-radius: 6px;
    padding: 16px;
    background: #fff;
  }

  .value {
    font-size: 22px;
    font-weight: 600;
    color: #303133;
  }

  .label {
    margin-top: 8px;
    color: #606266;
  }
}
</style>
