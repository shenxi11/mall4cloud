/*
 * 模块名: platform_short_video_api
 * 功能概述: 平台端短视频管理接口封装。
 * 对外接口: page, get, save, update, deleteById
 * 依赖关系: utils/request
 * 输入输出: 输入页面查询或表单数据，输出后端统一响应数据。
 * 异常与错误: 请求异常由全局 request 拦截器处理。
 * 维护说明: 后端路径保持 /mall4cloud_multishop/admin/short_video。
 */
import request from '@/utils/request'

export function page (pageParam) {
  return request({
    url: '/mall4cloud_multishop/admin/short_video/page',
    method: 'get',
    params: pageParam
  })
}

export function get (shortVideoId) {
  return request({
    url: '/mall4cloud_multishop/admin/short_video',
    method: 'get',
    params: {
      shortVideoId
    }
  })
}

export function save (data) {
  return request({
    url: '/mall4cloud_multishop/admin/short_video',
    method: 'post',
    data
  })
}

export function update (data) {
  return request({
    url: '/mall4cloud_multishop/admin/short_video',
    method: 'put',
    data
  })
}

export function deleteById (shortVideoId) {
  return request({
    url: '/mall4cloud_multishop/admin/short_video',
    method: 'delete',
    params: {
      shortVideoId
    }
  })
}
