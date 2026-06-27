/*
 * 模块名: platform_ai_operation_api
 * 功能概述: 平台端运营 AI 占位接口封装。
 * 对外接口: scenes, generate
 * 依赖关系: utils/request
 * 输入输出: 输入运营场景和提示词，输出 AI 配置状态与占位结果。
 * 异常与错误: 请求异常由全局 request 拦截器处理。
 * 维护说明: 真实 AI 服务接入后保持当前前端 API 名称不变。
 */
import request from '@/utils/request'

export function scenes () {
  return request({
    url: '/mall4cloud_multishop/admin/ai_operation/scenes',
    method: 'get'
  })
}

export function generate (data) {
  return request({
    url: '/mall4cloud_multishop/admin/ai_operation/generate',
    method: 'post',
    data
  })
}
