package com.mall4j.cloud.multishop.controller.app;

/*
 * 模块名: app_live_room_controller
 * 功能概述: 提供移动端直播间公开列表接口。
 * 对外接口: /ua/live_room
 * 依赖关系: LiveRoomService, ServerResponseEntity
 * 输入输出: 输入店铺 id，输出已上线直播间列表。
 * 异常与错误: 数据访问异常交由全局异常处理。
 * 维护说明: 当前仅返回播放地址，后续可追加云直播鉴权播放信息。
 */

import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.multishop.service.LiveRoomService;
import com.mall4j.cloud.multishop.vo.LiveRoomVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 直播间
 */
@RestController("appLiveRoomController")
@RequestMapping("/ua/live_room")
@Tag(name = "app-直播间")
public class LiveRoomController {

    @Autowired
    private LiveRoomService liveRoomService;

    @GetMapping("/list")
    @Operation(summary = "获取直播间列表", description = "获取直播间列表")
    @Parameter(name = "shopId", description = "店铺id（平台：0）")
    public ServerResponseEntity<List<LiveRoomVO>> list(@RequestParam(value = "shopId", required = false) Long shopId) {
        return ServerResponseEntity.success(liveRoomService.listEnabled(shopId));
    }
}
