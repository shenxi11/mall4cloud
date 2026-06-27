package com.mall4j.cloud.multishop.controller.app;

/*
 * 模块名: app_short_video_controller
 * 功能概述: 提供移动端短视频公开列表接口。
 * 对外接口: /ua/short_video
 * 依赖关系: ShortVideoService, ServerResponseEntity
 * 输入输出: 输入店铺 id，输出已上线短视频列表。
 * 异常与错误: 数据访问异常交由全局异常处理。
 * 维护说明: 当前仅返回视频播放地址，后续可追加云点播鉴权播放信息。
 */

import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.multishop.service.ShortVideoService;
import com.mall4j.cloud.multishop.vo.ShortVideoVO;
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
 * 短视频
 */
@RestController("appShortVideoController")
@RequestMapping("/ua/short_video")
@Tag(name = "app-短视频")
public class ShortVideoController {

    @Autowired
    private ShortVideoService shortVideoService;

    @GetMapping("/list")
    @Operation(summary = "获取短视频列表", description = "获取短视频列表")
    @Parameter(name = "shopId", description = "店铺id（平台：0）")
    public ServerResponseEntity<List<ShortVideoVO>> list(@RequestParam(value = "shopId", required = false) Long shopId) {
        return ServerResponseEntity.success(shortVideoService.listEnabled(shopId));
    }
}
