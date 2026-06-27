/*
  模块名: live_short_video_ai_upgrade_sql
  功能概述: 新增直播、短视频内容表和对应管理端菜单资源。
  对外接口: mall4cloud_multishop.live_room, mall4cloud_multishop.short_video, mall4cloud_rbac.menu
  依赖关系: mall4cloud_multishop, mall4cloud_rbac 数据库
  输入输出: 导入后提供直播/短视频数据落表和平台端、商家端菜单入口。
  异常与错误: 如菜单 id 或权限编码已存在，INSERT IGNORE 会跳过冲突行，避免覆盖旧数据。
  维护说明: 仅提供迁移脚本，不在开发环境自动执行；正式环境导入前请先备份数据库。
*/

USE `mall4cloud_multishop`;

CREATE TABLE IF NOT EXISTS `live_room` (
  `live_room_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '直播间id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint NOT NULL COMMENT '店铺ID，0为平台',
  `title` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '直播标题',
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '封面图',
  `anchor_name` varchar(60) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '主播名称',
  `live_status` tinyint NOT NULL DEFAULT '0' COMMENT '直播状态 0预告 1直播中 2回放',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 0下线 1上线',
  `start_time` datetime DEFAULT NULL COMMENT '开播时间',
  `end_time` datetime DEFAULT NULL COMMENT '结束时间',
  `play_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '直播播放地址',
  `replay_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '回放地址',
  `related_spu_id` bigint DEFAULT NULL COMMENT '关联商品id',
  `seq` int NOT NULL DEFAULT '0' COMMENT '排序',
  `location_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地理位置名称',
  `longitude` decimal(10,6) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(10,6) DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`live_room_id`) USING BTREE,
  KEY `idx_shop_status` (`shop_id`,`status`) USING BTREE,
  KEY `idx_related_spu_id` (`related_spu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='直播间';

CREATE TABLE IF NOT EXISTS `short_video` (
  `short_video_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '短视频id',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `shop_id` bigint NOT NULL COMMENT '店铺ID，0为平台',
  `title` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `cover_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '封面图',
  `video_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '视频地址',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '描述',
  `related_spu_id` bigint DEFAULT NULL COMMENT '关联商品id',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态 0下线 1上线',
  `view_count` bigint NOT NULL DEFAULT '0' COMMENT '播放量',
  `like_count` bigint NOT NULL DEFAULT '0' COMMENT '点赞数',
  `seq` int NOT NULL DEFAULT '0' COMMENT '排序',
  `location_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地理位置名称',
  `longitude` decimal(10,6) DEFAULT NULL COMMENT '经度',
  `latitude` decimal(10,6) DEFAULT NULL COMMENT '纬度',
  PRIMARY KEY (`short_video_id`) USING BTREE,
  KEY `idx_shop_status` (`shop_id`,`status`) USING BTREE,
  KEY `idx_related_spu_id` (`related_spu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='短视频';

USE `mall4cloud_rbac`;

INSERT IGNORE INTO `menu` (`menu_id`, `parent_id`, `biz_type`, `permission`, `path`, `component`, `redirect`, `always_show`, `hidden`, `name`, `title`, `icon`, `no_cache`, `breadcrumb`, `affix`, `active_menu`, `seq`) VALUES
(9200, 255, 2, NULL, 'live-room', 'platform/live-room', NULL, NULL, 0, 'platformLiveRoom', '直播管理', '', NULL, NULL, NULL, NULL, 4),
(9201, 255, 2, NULL, 'short-video', 'platform/short-video', NULL, NULL, 0, 'platformShortVideo', '短视频管理', '', NULL, NULL, NULL, NULL, 5),
(9202, 255, 2, NULL, 'ai-operation', 'platform/ai-operation', NULL, NULL, 0, 'platformAiOperation', 'AI运营助手', '', NULL, NULL, NULL, NULL, 6),
(9203, 255, 2, NULL, 'distribution-agent', 'platform/distribution-agent', NULL, NULL, 0, 'platformDistributionAgent', '分销代理', '', NULL, NULL, NULL, NULL, 7),
(9204, 145, 1, NULL, 'live-room', 'multishop/live-room', NULL, NULL, 0, 'multishopLiveRoom', '直播管理', '', NULL, NULL, NULL, NULL, 2),
(9205, 145, 1, NULL, 'short-video', 'multishop/short-video', NULL, NULL, 0, 'multishopShortVideo', '短视频管理', '', NULL, NULL, NULL, NULL, 3),
(9206, 145, 1, NULL, 'ai-operation', 'multishop/ai-operation', NULL, NULL, 0, 'multishopAiOperation', 'AI运营助手', '', NULL, NULL, NULL, NULL, 4),
(9207, 145, 1, NULL, 'distribution-agent', 'multishop/distribution-agent', NULL, NULL, 0, 'multishopDistributionAgent', '分销代理', '', NULL, NULL, NULL, NULL, 5);

INSERT IGNORE INTO `menu_permission` (`menu_permission_id`, `menu_id`, `biz_type`, `permission`, `name`, `uri`, `method`) VALUES
(9300, 9200, 2, 'platform:liveRoom:save', '新增', '/admin/live_room', 2),
(9301, 9200, 2, 'platform:liveRoom:update', '更新', '/admin/live_room', 3),
(9302, 9200, 2, 'platform:liveRoom:delete', '删除', '/admin/live_room', 4),
(9303, 9201, 2, 'platform:shortVideo:save', '新增', '/admin/short_video', 2),
(9304, 9201, 2, 'platform:shortVideo:update', '更新', '/admin/short_video', 3),
(9305, 9201, 2, 'platform:shortVideo:delete', '删除', '/admin/short_video', 4),
(9306, 9202, 2, 'platform:aiOperation:generate', '生成', '/admin/ai_operation/generate', 2),
(9307, 9204, 1, 'admin:liveRoom:save', '新增', '/admin/live_room', 2),
(9308, 9204, 1, 'admin:liveRoom:update', '更新', '/admin/live_room', 3),
(9309, 9204, 1, 'admin:liveRoom:delete', '删除', '/admin/live_room', 4),
(9310, 9205, 1, 'admin:shortVideo:save', '新增', '/admin/short_video', 2),
(9311, 9205, 1, 'admin:shortVideo:update', '更新', '/admin/short_video', 3),
(9312, 9205, 1, 'admin:shortVideo:delete', '删除', '/admin/short_video', 4),
(9313, 9206, 1, 'admin:aiOperation:generate', '生成', '/admin/ai_operation/generate', 2);
