<!--
模块名: multishop_distribution_agent_page
功能概述: 商家端分销代理管理入口，占位展示代理申请、推广和结算的管理落点。
对外接口: 动态路由 component=multishop/distribution-agent
依赖关系: Element Plus
输入输出: 当前不提交数据，仅展示后续业务接入位置。
异常与错误: 无接口调用错误。
维护说明: 接入分销后应补充代理列表、推广订单、佣金规则和结算接口。
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

    <el-empty description="分销代理接口待接入：后续可扩展代理审核、推广关系、佣金规则和结算记录。" />
  </div>
</template>

<script setup>
import { reactive, toRefs } from 'vue'

const Data = reactive({
  summary: [
    { label: '待审核代理', value: 0 },
    { label: '有效代理', value: 0 },
    { label: '推广订单', value: 0 },
    { label: '待结算佣金', value: '0.00' }
  ]
})
const { summary } = toRefs(Data)
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
