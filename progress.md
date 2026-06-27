## 2026-06-27 - Task: 直播短视频与AI运营升级 v1
### What was done
- 在 Mall4cloud 当前架构内新增直播、短视频后端模型、管理接口、移动端公开列表接口和 MyBatis 映射。
- 平台端与商家端新增直播管理、短视频管理、AI运营助手、分销代理入口页面和 API 封装。
- 移动端底部导航调整为首页、直播、短视频、购物车、我的，并新增直播与短视频页面；分类入口迁移到首页功能区。
- 新增数据库迁移脚本，创建直播/短视频表并写入平台端、商家端菜单与资源权限。
- 新增升级说明文档，明确旧 CRMEB 后台不能直接搬进当前 Mall4cloud 后端，只能按页面体验复刻并做协议适配。

### Testing
- 已执行 UTF-8 解码检查：新增/修改的 41 个文本文件均可按 UTF-8 读取，未发现替换字符。
- 已执行 `front-end/mall4cloud-uniapp/src/pages.json` JSON 解析检查，通过。
- 已执行 `LiveRoomMapper.xml`、`ShortVideoMapper.xml` XML 解析检查，通过。
- 已执行移动端 tabbar 数量检查，结果为 5 项，通过。
- 已执行新增管理端动态路由目标文件存在性检查，通过。
- 未执行后端 Maven 编译：当前环境 `java -version` 为 11.0.2，项目需要 Java 17，且 `mvn` 不在 PATH。
- 未执行前端 lint/build：三个前端项目当前均无 `node_modules`，本轮未安装依赖以避免引入大量本地依赖变更。

### Notes
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/model/LiveRoom.java`：新增直播间持久化模型。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/model/ShortVideo.java`：新增短视频持久化模型。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/dto/LiveRoomDTO.java`：新增直播间后台入参对象。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/dto/ShortVideoDTO.java`：新增短视频后台入参对象。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/dto/AiOperationDTO.java`：新增 AI 运营占位入参对象。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/vo/LiveRoomVO.java`：新增直播间返回对象。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/vo/ShortVideoVO.java`：新增短视频返回对象。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/vo/AiOperationVO.java`：新增 AI 运营占位返回对象。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/mapper/LiveRoomMapper.java`：新增直播间 Mapper 接口。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/mapper/ShortVideoMapper.java`：新增短视频 Mapper 接口。
- `mall4cloud-multishop/src/main/resources/mapper/LiveRoomMapper.xml`：新增直播间 MyBatis SQL 映射。
- `mall4cloud-multishop/src/main/resources/mapper/ShortVideoMapper.xml`：新增短视频 MyBatis SQL 映射。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/service/LiveRoomService.java`：新增直播间 Service 接口。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/service/ShortVideoService.java`：新增短视频 Service 接口。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/service/impl/LiveRoomServiceImpl.java`：新增直播间 Service 实现。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/service/impl/ShortVideoServiceImpl.java`：新增短视频 Service 实现。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/controller/admin/LiveRoomController.java`：新增后台直播间管理接口。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/controller/admin/ShortVideoController.java`：新增后台短视频管理接口。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/controller/admin/AiOperationController.java`：新增后台 AI 运营占位接口。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/controller/app/LiveRoomController.java`：新增移动端直播公开列表接口。
- `mall4cloud-multishop/src/main/java/com/mall4j/cloud/multishop/controller/app/ShortVideoController.java`：新增移动端短视频公开列表接口。
- `front-end/mall4cloud-platform/src/api/platform/live-room.js`：新增平台端直播管理 API 封装。
- `front-end/mall4cloud-platform/src/api/platform/short-video.js`：新增平台端短视频管理 API 封装。
- `front-end/mall4cloud-platform/src/api/platform/ai-operation.js`：新增平台端 AI 运营 API 封装。
- `front-end/mall4cloud-platform/src/views/modules/platform/live-room/index.vue`：新增平台端直播管理页面。
- `front-end/mall4cloud-platform/src/views/modules/platform/short-video/index.vue`：新增平台端短视频管理页面。
- `front-end/mall4cloud-platform/src/views/modules/platform/ai-operation/index.vue`：新增平台端 AI 运营助手页面。
- `front-end/mall4cloud-platform/src/views/modules/platform/distribution-agent/index.vue`：新增平台端分销代理占位页面。
- `front-end/mall4cloud-multishop/src/api/multishop/live-room.js`：新增商家端直播管理 API 封装。
- `front-end/mall4cloud-multishop/src/api/multishop/short-video.js`：新增商家端短视频管理 API 封装。
- `front-end/mall4cloud-multishop/src/api/multishop/ai-operation.js`：新增商家端 AI 运营 API 封装。
- `front-end/mall4cloud-multishop/src/views/modules/multishop/live-room/index.vue`：新增商家端直播管理页面。
- `front-end/mall4cloud-multishop/src/views/modules/multishop/short-video/index.vue`：新增商家端短视频管理页面。
- `front-end/mall4cloud-multishop/src/views/modules/multishop/ai-operation/index.vue`：新增商家端 AI 运营助手页面。
- `front-end/mall4cloud-multishop/src/views/modules/multishop/distribution-agent/index.vue`：新增商家端分销代理占位页面。
- `front-end/mall4cloud-uniapp/src/pages.json`：新增直播/短视频页面并调整底部导航。
- `front-end/mall4cloud-uniapp/src/pages/live/live.vue`：新增移动端直播 tab 页面。
- `front-end/mall4cloud-uniapp/src/pages/short-video/short-video.vue`：新增移动端短视频 tab 页面。
- `front-end/mall4cloud-uniapp/src/pages/index/index.vue`：将首页功能区第二项改为商品分类入口。
- `db/20260627_live_short_video_ai_upgrade.sql`：新增直播、短视频表和菜单资源迁移脚本。
- `docs/live-short-video-ai-upgrade.md`：新增功能升级说明和导入联调说明。
- `progress.md`：新增本轮施工日志。
- 回滚方式：在未导入数据库脚本前，可通过 `git restore <上述已跟踪文件>` 并删除新增文件回滚代码；若已导入 SQL，需备份恢复或手动删除 `live_room`、`short_video` 表及菜单 ID 9200-9207、资源 ID 9300-9313。

## 2026-06-27 - Task: 远程 Docker 部署配置与构建验证
### What was done
- 新增远程 Docker Compose、Nginx、部署脚本和环境变量示例，统一通过 `6688` 暴露平台端、商家端、移动端 H5、API 与 MinIO 控制台代理。
- 部署脚本改为在 `/data/mall4cloud` 下维护 Maven、pnpm、npm 缓存和中间件运行配置副本，避免服务器 Git 工作区被运行时配置替换污染。
- 平台端、商家端改为按 `/platform/`、`/multishop/` 子路径构建，移动端 H5 改为相对 base 并挂载到 `/cloud/`。
- 远程部署真实 `.env` 加入 Git 忽略，部署构建使用 `pnpm install --no-lockfile`，避免服务器生成未提交 lockfile。

### Testing
- 已执行 `git diff --check`，通过；仅有 Windows 换行提示，无空白错误。
- 已执行 `front-end/mall4cloud-uniapp/src/pages.json` 与 `src/manifest.json` 解析检查，通过；H5 router base 为 `./`。
- 已执行部署文件与 H5 base 相关文件 UTF-8 严格解码检查，通过。
- 已使用 `JAVA_HOME=E:\jdk21`、Maven `E:\apache-maven-3.9.9`、本地仓库 `E:\mall4cloud-cache\m2` 执行 `mvn -Dmaven.repo.local=E:/mall4cloud-cache/m2 -pl mall4cloud-multishop -am -DskipTests compile`，通过。
- 已使用 `E:\mall4cloud-cache\npm` 和 `E:\mall4cloud-cache\pnpm` 作为前端缓存，分别执行平台端 `pnpm build`、商家端 `pnpm build`、移动端 `pnpm build:h5`，均通过；仅保留既有 ESLint/CSS/大包体积警告。
- 已确认三端本地构建没有生成 `pnpm-lock.yaml`；生成的 `node_modules`、`dist`、`target` 均为 Git 忽略项。
- 未在本机执行 `bash -n deploy/remote/deploy.sh`：当前 Windows `bash.exe` 指向 WSL，但 WSL 服务不可用并返回 `HCS_E_SERVICE_NOT_AVAILABLE`。
- 未在本机执行 `docker compose config`：本机未检测到 Docker CLI；Compose 语法留到远端服务器验证。

### Notes
- `.gitignore`：忽略远程部署真实配置 `deploy/remote/.env`。
- `deploy/remote/.env.example`：新增远程部署环境变量示例，补充 Redis 密码变量。
- `deploy/remote/docker-compose.prod.yml`：新增生产 Compose，使用 host 网络运行中间件、微服务和统一 Nginx 入口。
- `deploy/remote/deploy.sh`：新增远程部署脚本，负责准备数据目录、复制运行时配置、导入升级 SQL、构建后端和三端前端。
- `deploy/remote/nginx.conf`：新增统一入口配置，代理 `/api/`、`/minio/`、`/minio-console/` 并承载三个前端子路径。
- `docs/remote-deployment.md`：新增远程部署、缓存目录、访问入口和运维命令说明。
- `front-end/mall4cloud-platform/vite.config.js`：支持部署时通过 `VITE_APP_BASE_PATH` 设置构建 base。
- `front-end/mall4cloud-platform/src/router/index.js`：路由 history 使用 Vite base，适配 `/platform/` 子路径。
- `front-end/mall4cloud-multishop/vite.config.js`：支持部署时通过 `VITE_APP_BASE_PATH` 设置构建 base。
- `front-end/mall4cloud-multishop/src/router/index.js`：路由 history 使用 Vite base，适配 `/multishop/` 子路径。
- `front-end/mall4cloud-uniapp/vite.config.js`：支持移动端 H5 相对 base 构建。
- `front-end/mall4cloud-uniapp/src/manifest.json`：H5 history router 增加相对 base，适配 `/cloud/` 子路径挂载。
- `front-end/mall4cloud-uniapp/.eslintrc-auto-import.json`：移动端 H5 构建刷新自动导入 ESLint globals。
- `progress.md`：追加本轮施工和验证日志。
- 回滚方式：远程未部署前可通过 `git revert <本次提交>` 回滚；若已部署，可先在服务器执行 `docker compose -f deploy/remote/docker-compose.prod.yml down` 停止容器，再回退 Git 提交并重新执行部署脚本。数据库若已初始化，需按上一条日志中的表和菜单资源回滚说明处理业务升级数据。

## 2026-06-27 - Task: 远程服务器端口冲突隔离
### What was done
- 远程预检发现服务器已有 MariaDB 占用 `3306`，已有进程占用 `6379` 和 `9001`，原 host 网络部署会与现有服务冲突。
- 将远程 Compose 调整为 Docker bridge 网络，MySQL、Redis、MinIO、Nacos、RocketMQ、ES、Seata、Canal 和后端微服务通过容器名互联。
- 统一 Nginx 入口仍只发布公网 `6688`，内部中间件端口不再暴露到宿主机。
- 部署脚本同步改写运行时配置和 Nacos 初始化 SQL，将固定内网 IP 替换为容器名，确保首次初始化后服务注册、OSS、RocketMQ、ES、Seata 等配置能在 Docker 网络内解析。

### Testing
- 已执行远程预检，确认服务器 Ubuntu、Docker、Compose、Git 可用，并确认 `/opt/mall4cloud` 尚未存在。
- 已执行 `git diff --check`，通过；仅有 Windows 换行提示，无空白错误。
- 已执行 `deploy/remote/docker-compose.prod.yml` YAML 解析检查，通过。
- 尚未在远程执行完整部署；本轮提交后会由服务器拉取最新代码并执行 `deploy/remote/deploy.sh`。

### Notes
- `deploy/remote/docker-compose.prod.yml`：改为 bridge 网络和容器名互联，仅 Nginx 发布 `${PUBLIC_PORT:-6688}`。
- `deploy/remote/deploy.sh`：改写运行时配置和初始化 SQL 的服务地址，适配 Docker 内部网络。
- `deploy/remote/nginx.conf`：代理 upstream 从 `127.0.0.1` 改为对应容器服务名。
- `docs/remote-deployment.md`：补充 bridge 网络隔离和只开放 `6688` 的部署说明。
- `progress.md`：追加本轮端口冲突处理和验证记录。
- 回滚方式：远程未部署前可通过 `git revert <本次提交>` 回滚；若已部署，可先执行 `docker compose -f deploy/remote/docker-compose.prod.yml down` 停止 Mall4cloud 容器，再回退本次提交并重新部署。该操作不会处理服务器上原有 MariaDB、Redis 或其他既有服务。
