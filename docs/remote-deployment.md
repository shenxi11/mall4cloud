# 远程部署说明

## 业务结论

本项目按远程服务器部署，不在本机安装项目依赖。代码提交到 GitHub 后，服务器拉取仓库，通过 Docker 完成中间件、后端微服务和三个前端的构建与运行。

远程 Compose 使用 Docker bridge 网络隔离 MySQL、Redis、MinIO、Nacos、RocketMQ、ES 等内部端口，避免占用服务器已有的 `3306`、`6379`、`9001` 等端口；公网只发布 `6688`。

## 部署入口

- 服务器：`64.90.3.109`
- 公网统一入口：`http://64.90.3.109:6688`
- 平台端：`http://64.90.3.109:6688/platform/`
- 商家端：`http://64.90.3.109:6688/multishop/`
- 移动端 H5：`http://64.90.3.109:6688/cloud/`
- API 代理：`http://64.90.3.109:6688/api/`
- MinIO 控制台代理：`http://64.90.3.109:6688/minio-console/`

## 首次部署步骤

在服务器执行：

```bash
mkdir -p /opt
cd /opt
git clone https://github.com/shenxi11/mall4cloud.git
cd /opt/mall4cloud
cp deploy/remote/.env.example deploy/remote/.env
bash deploy/remote/deploy.sh
```

`deploy/remote/.env` 已加入忽略列表，只保留本机/服务器真实配置，不提交到仓库。如仓库恢复私有，需要先配置 GitHub Deploy Key 或访问令牌。

## 数据和缓存位置

- 应用代码：`/opt/mall4cloud`
- 数据目录：`/data/mall4cloud`
- Maven 缓存：`/data/mall4cloud/cache/m2`
- pnpm 缓存：`/data/mall4cloud/cache/pnpm`
- npm 缓存：`/data/mall4cloud/cache/npm`
- MySQL 数据：`/data/mall4cloud/mysql/data`
- 中间件运行配置副本：`/data/mall4cloud/seata`、`/data/mall4cloud/canal/conf`、`/data/mall4cloud/rocketmq/broker/conf`
- MinIO 数据：`/data/mall4cloud/minio/data`

## 运维命令

```bash
cd /opt/mall4cloud
docker compose -f deploy/remote/docker-compose.prod.yml ps
docker compose -f deploy/remote/docker-compose.prod.yml logs -f mall4cloud-gateway
docker compose -f deploy/remote/docker-compose.prod.yml restart mall4cloud-nginx
```

重新部署：

```bash
cd /opt/mall4cloud
git pull
bash deploy/remote/deploy.sh
```

## 注意事项

- 远程部署脚本会在首次初始化 MySQL 时导入基础库、Nacos 配置和直播短视频升级 SQL。
- 首次初始化会先导入 `mall4cloud_user.sql`，再导入地区数据 `mall4cloud_user_area_data.sql`；脚本会等待 MySQL 最终服务端口和全部基础 schema 就绪后再执行后续升级 SQL。
- 后续重复执行部署脚本只会再次执行升级 SQL；该 SQL 使用 `IF NOT EXISTS` 和 `INSERT IGNORE`，可重复执行。
- 移动端 H5 使用相对 base 构建并由 Nginx 挂载到 `/cloud/`，平台端和商家端分别使用 `/platform/`、`/multishop/` 子路径构建。
- 业务公网只需要开放 `6688`。MySQL、Redis、Nacos、MinIO、RocketMQ、ES 等服务默认只在 Docker 内部网络访问。
