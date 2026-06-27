# 直播、短视频与AI运营升级说明

## 业务结论

本次升级按 Mall4cloud 当前架构实现，不直接搬运 `http://121.196.144.27/admin` 的 CRMEB 页面代码。

原因是旧站后台是 Vue2 + Element UI + CRMEB `/adminapi` 协议，登录、菜单、权限和数据结构都与 Mall4cloud 的 Vue3 + Element Plus + Spring Cloud 接口不一致。直接嵌入或复制打包产物只能访问旧系统接口，无法作为当前项目的可维护功能升级。

## 本次交付范围

- 移动端底部导航新增 `直播`、`短视频`。
- 移动端新增直播列表、直播播放、短视频内容流、关联商品入口。
- 平台端新增 `直播管理`、`短视频管理`、`AI运营助手`、`分销代理` 页面。
- 商家端新增 `直播管理`、`短视频管理`、`AI运营助手`、`分销代理` 页面。
- 后端新增直播、短视频管理接口和移动端公开列表接口。
- AI 先做配置占位，不调用真实模型服务。
- 分销代理先做管理入口占位，后续再接代理关系、佣金规则、结算记录。

## 数据库迁移

迁移脚本：

```text
db/20260627_live_short_video_ai_upgrade.sql
```

脚本内容：

- `mall4cloud_multishop.live_room`
- `mall4cloud_multishop.short_video`
- 平台端与商家端菜单记录
- 直播、短视频、AI生成接口的菜单资源记录

导入前请先备份数据库。脚本使用 `CREATE TABLE IF NOT EXISTS` 和 `INSERT IGNORE`，避免重复导入时报错或覆盖已有菜单。

## 接口路径

后台管理：

- `GET /mall4cloud_multishop/admin/live_room/page`
- `GET /mall4cloud_multishop/admin/live_room`
- `POST /mall4cloud_multishop/admin/live_room`
- `PUT /mall4cloud_multishop/admin/live_room`
- `DELETE /mall4cloud_multishop/admin/live_room`
- `GET /mall4cloud_multishop/admin/short_video/page`
- `GET /mall4cloud_multishop/admin/short_video`
- `POST /mall4cloud_multishop/admin/short_video`
- `PUT /mall4cloud_multishop/admin/short_video`
- `DELETE /mall4cloud_multishop/admin/short_video`
- `GET /mall4cloud_multishop/admin/ai_operation/scenes`
- `POST /mall4cloud_multishop/admin/ai_operation/generate`

移动端公开接口：

- `GET /mall4cloud_multishop/ua/live_room/list`
- `GET /mall4cloud_multishop/ua/short_video/list`

## 后续接入点

- 直播云服务：保留 `playUrl`、`replayUrl`，后续可增加推流地址、第三方房间 ID、鉴权播放地址。
- 云点播服务：保留 `videoUrl`，后续可增加媒资 ID、转码状态、审核状态。
- AI 服务：保留 `scene`、`prompt`、`configured`、`provider`、`result` 返回结构，后续在服务层替换真实模型调用。
- 地理位置：当前保留 `locationName`、`longitude`、`latitude`，后续可接用户定位、附近直播和附近短视频排序。
