#!/usr/bin/env bash
# 模块名: remote_deploy_script
# 功能概述: 在远程服务器拉取代码后，准备数据目录、构建后端和前端，并启动 Docker Compose。
# 对外接口: bash deploy/remote/deploy.sh
# 依赖关系: git, docker, docker compose
# 输入输出: 输入仓库代码和环境变量，输出运行中的 Mall4cloud 服务。
# 异常与错误: 任一步骤失败会立即退出，并输出失败命令。
# 维护说明: 不保存服务器登录密码；Maven、pnpm、npm 缓存统一放在 MALL4CLOUD_DATA_DIR/cache。

set -Eeuo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
APP_DIR="$(cd "${SCRIPT_DIR}/../.." && pwd)"
ENV_FILE="${SCRIPT_DIR}/.env"

if [ -f "${ENV_FILE}" ]; then
  set -a
  # shellcheck disable=SC1090
  source "${ENV_FILE}"
  set +a
fi

SERVER_IP="${SERVER_IP:-64.90.3.109}"
PUBLIC_PORT="${PUBLIC_PORT:-6688}"
PUBLIC_ORIGIN="${PUBLIC_ORIGIN:-http://${SERVER_IP}:${PUBLIC_PORT}}"
MALL4CLOUD_APP_DIR="${MALL4CLOUD_APP_DIR:-${APP_DIR}}"
MALL4CLOUD_DATA_DIR="${MALL4CLOUD_DATA_DIR:-/data/mall4cloud}"
MYSQL_ROOT_PASSWORD="${MYSQL_ROOT_PASSWORD:-80jpnH4.r5g}"
REDIS_PASSWORD="${REDIS_PASSWORD:-80jpnH4.r5g}"
MINIO_ROOT_USER="${MINIO_ROOT_USER:-admin}"
MINIO_ROOT_PASSWORD="${MINIO_ROOT_PASSWORD:-80jpnH4.r5g}"
ELASTIC_PASSWORD="${ELASTIC_PASSWORD:-80jpnH4.r5g}"

export SERVER_IP PUBLIC_PORT PUBLIC_ORIGIN MALL4CLOUD_APP_DIR MALL4CLOUD_DATA_DIR
export MYSQL_ROOT_PASSWORD REDIS_PASSWORD MINIO_ROOT_USER MINIO_ROOT_PASSWORD ELASTIC_PASSWORD

if ! command -v docker >/dev/null 2>&1; then
  echo "docker is required" >&2
  exit 1
fi

if ! docker compose version >/dev/null 2>&1; then
  echo "docker compose is required" >&2
  exit 1
fi

echo "Deploying Mall4cloud from ${APP_DIR}"
echo "Public origin: ${PUBLIC_ORIGIN}"

mkdir -p \
  "${MALL4CLOUD_DATA_DIR}/cache/m2" \
  "${MALL4CLOUD_DATA_DIR}/cache/pnpm" \
  "${MALL4CLOUD_DATA_DIR}/cache/npm" \
  "${MALL4CLOUD_DATA_DIR}/mysql/conf.d" \
  "${MALL4CLOUD_DATA_DIR}/mysql/initdb" \
  "${MALL4CLOUD_DATA_DIR}/minio/data" \
  "${MALL4CLOUD_DATA_DIR}/nacos/logs" \
  "${MALL4CLOUD_DATA_DIR}/seata" \
  "${MALL4CLOUD_DATA_DIR}/canal/conf" \
  "${MALL4CLOUD_DATA_DIR}/elasticsearch/data" \
  "${MALL4CLOUD_DATA_DIR}/canal/logs" \
  "${MALL4CLOUD_DATA_DIR}/rocketmq/namesrv/logs" \
  "${MALL4CLOUD_DATA_DIR}/rocketmq/namesrv/store" \
  "${MALL4CLOUD_DATA_DIR}/rocketmq/broker/logs" \
  "${MALL4CLOUD_DATA_DIR}/rocketmq/broker/store" \
  "${MALL4CLOUD_DATA_DIR}/rocketmq/broker/conf"

chmod -R 777 \
  "${MALL4CLOUD_DATA_DIR}/elasticsearch/data" \
  "${MALL4CLOUD_DATA_DIR}/rocketmq" \
  "${MALL4CLOUD_DATA_DIR}/canal" || true

replace_runtime_config_hosts() {
  sed -i \
    -e "s/192\\.168\\.1\\.46:3306/mall4cloud-mysql:3306/g" \
    -e "s/192\\.168\\.1\\.46:9876/mall4cloud-rocketmq-namesrv:9876/g" \
    -e "s/password: root/password: ${MYSQL_ROOT_PASSWORD}/g" \
    "${MALL4CLOUD_DATA_DIR}/seata/application.yml"

  sed -i \
    -e "s/192\\.168\\.1\\.46:3306/mall4cloud-mysql:3306/g" \
    "${MALL4CLOUD_DATA_DIR}/canal/conf/example/instance.properties"

  sed -i \
    -e "s/192\\.168\\.1\\.46:9876/mall4cloud-rocketmq-namesrv:9876/g" \
    "${MALL4CLOUD_DATA_DIR}/canal/conf/canal.properties"

  sed -i \
    -e "s/namesrvAddr=192\\.168\\.1\\.46:9876/namesrvAddr=mall4cloud-rocketmq-namesrv:9876/g" \
    -e "s/brokerIP1=192\\.168\\.1\\.46/brokerIP1=mall4cloud-rocketmq-broker/g" \
    "${MALL4CLOUD_DATA_DIR}/rocketmq/broker/conf/broker.conf"
}

replace_initdb_hosts() {
  local initdb="$1"

  find "${initdb}" -type f -name '*.sql' -print0 | xargs -0 sed -i \
    -e "s/preferred-networks: 192\\.168\\.1/preferred-networks: 172\\./g" \
    -e "s/host: 192\\.168\\.1\\.46/host: mall4cloud-redis/g" \
    -e "s/default: 192\\.168\\.1\\.46:8091/default: mall4cloud-seata:8091/g" \
    -e "s|http://192\\.168\\.1\\.46:9000/mall4cloud|${PUBLIC_ORIGIN}/minio/mall4cloud|g" \
    -e "s|endpoint: http://192\\.168\\.1\\.46:9000|endpoint: http://mall4cloud-minio:9000|g" \
    -e "s/name-server: 192\\.168\\.1\\.46:9876/name-server: mall4cloud-rocketmq-namesrv:9876/g" \
    -e "s/address: 192\\.168\\.1\\.46/address: mall4cloud-elasticsearch/g" \
    -e "s/access-key-secret: admin123456/access-key-secret: ${MINIO_ROOT_PASSWORD}/g"
}

prepare_runtime_config() {
  cp "${APP_DIR}/doc/中间件docker-compse一键安装/mysql/conf.d/docker.cnf" \
    "${MALL4CLOUD_DATA_DIR}/mysql/conf.d/docker.cnf"

  cp "${APP_DIR}/doc/中间件docker-compse一键安装/seata/application.yml" \
    "${MALL4CLOUD_DATA_DIR}/seata/application.yml"

  rm -rf "${MALL4CLOUD_DATA_DIR}/canal/conf/example"
  cp -a "${APP_DIR}/doc/中间件docker-compse一键安装/canal/conf/example" \
    "${MALL4CLOUD_DATA_DIR}/canal/conf/example"
  cp "${APP_DIR}/doc/中间件docker-compse一键安装/canal/conf/canal.properties" \
    "${MALL4CLOUD_DATA_DIR}/canal/conf/canal.properties"

  cp "${APP_DIR}/doc/中间件docker-compse一键安装/rocketmq/broker/conf/broker.conf" \
    "${MALL4CLOUD_DATA_DIR}/rocketmq/broker/conf/broker.conf"

  replace_runtime_config_hosts
}

prepare_initdb() {
  local initdb="${MALL4CLOUD_DATA_DIR}/mysql/initdb"
  rm -f "${initdb}"/*.sql

  local idx=10
  for sql in "${APP_DIR}"/db/mall4cloud_*.sql; do
    [ "$(basename "${sql}")" = "mall4cloud-all.sql" ] && continue
    cp "${sql}" "${initdb}/$(printf '%03d' "${idx}")_$(basename "${sql}")"
    idx=$((idx + 10))
  done

  cp "${APP_DIR}/db/20260627_live_short_video_ai_upgrade.sql" "${initdb}/850_live_short_video_ai_upgrade.sql"
  cp "${APP_DIR}/doc/中间件docker-compse一键安装/mysql/initdb/mall4cloud_canal.sql" "${initdb}/900_mall4cloud_canal.sql"
  cp "${APP_DIR}/doc/中间件docker-compse一键安装/mysql/initdb/mall4cloud_seata.sql" "${initdb}/910_mall4cloud_seata.sql"
  cp "${APP_DIR}/doc/中间件docker-compse一键安装/mysql/initdb/mall4cloud_nacos.sql" "${initdb}/920_mall4cloud_nacos.sql"

  replace_initdb_hosts "${initdb}"
}

prepare_runtime_config

if [ ! -d "${MALL4CLOUD_DATA_DIR}/mysql/data/mysql" ]; then
  echo "Preparing first-run MySQL init scripts"
  prepare_initdb
fi

COMPOSE_FILE="${SCRIPT_DIR}/docker-compose.prod.yml"
docker compose -f "${COMPOSE_FILE}" up -d \
  mall4cloud-mysql \
  mall4cloud-redis \
  mall4cloud-minio \
  mall4cloud-elasticsearch \
  mall4cloud-rocketmq-namesrv \
  mall4cloud-rocketmq-broker

echo "Waiting for MySQL"
for i in {1..90}; do
  if docker exec mall4cloud-mysql mysqladmin ping -uroot -p"${MYSQL_ROOT_PASSWORD}" --silent >/dev/null 2>&1; then
    break
  fi
  sleep 2
done
docker exec mall4cloud-mysql mysqladmin ping -uroot -p"${MYSQL_ROOT_PASSWORD}" --silent >/dev/null

echo "Applying upgrade SQL"
docker exec -i mall4cloud-mysql mysql -uroot -p"${MYSQL_ROOT_PASSWORD}" < "${APP_DIR}/db/20260627_live_short_video_ai_upgrade.sql"

docker compose -f "${COMPOSE_FILE}" up -d mall4cloud-nacos mall4cloud-seata mall4cloud-canal

echo "Building backend jars"
docker run --rm --network host \
  -v "${APP_DIR}:/workspace" \
  -v "${MALL4CLOUD_DATA_DIR}/cache/m2:/root/.m2" \
  -w /workspace \
  maven:3.9.9-eclipse-temurin-21 \
  mvn -DskipTests package

build_frontend() {
  local dir="$1"
  local base_path="$2"
  local command="$3"
  echo "Building ${dir}"
  docker run --rm \
    -v "${APP_DIR}:/workspace" \
    -v "${MALL4CLOUD_DATA_DIR}/cache/pnpm:/cache/pnpm" \
    -v "${MALL4CLOUD_DATA_DIR}/cache/npm:/cache/npm" \
    -w "/workspace/${dir}" \
    -e npm_config_cache=/cache/npm \
    -e VITE_APP_BASE_PATH="${base_path}" \
    -e VITE_APP_BASE_API="${PUBLIC_ORIGIN}/api" \
    -e VITE_APP_RESOURCES_URL="${PUBLIC_ORIGIN}/minio/mall4cloud" \
    -e VITE_APP_RESOURCES_TYPE=1 \
    -e VITE_APP_RESOURCES_ACTION_TYPE=1 \
    node:20-bookworm \
    bash -lc "corepack enable && corepack prepare pnpm@10.33.0 --activate && pnpm config set store-dir /cache/pnpm && pnpm install --no-frozen-lockfile --no-lockfile && ${command}"
}

build_frontend "front-end/mall4cloud-platform" "/platform/" "pnpm build"
build_frontend "front-end/mall4cloud-multishop" "/multishop/" "pnpm build"
build_frontend "front-end/mall4cloud-uniapp" "./" "pnpm build:h5"

docker compose -f "${COMPOSE_FILE}" up -d \
  mall4cloud-gateway \
  mall4cloud-auth \
  mall4cloud-rbac \
  mall4cloud-multishop \
  mall4cloud-product \
  mall4cloud-user \
  mall4cloud-order \
  mall4cloud-biz \
  mall4cloud-search \
  mall4cloud-leaf \
  mall4cloud-platform \
  mall4cloud-payment \
  mall4cloud-nginx

echo "Deployment started"
docker compose -f "${COMPOSE_FILE}" ps
echo "Platform: ${PUBLIC_ORIGIN}/platform/"
echo "Multishop: ${PUBLIC_ORIGIN}/multishop/"
echo "Mobile H5: ${PUBLIC_ORIGIN}/cloud/"
