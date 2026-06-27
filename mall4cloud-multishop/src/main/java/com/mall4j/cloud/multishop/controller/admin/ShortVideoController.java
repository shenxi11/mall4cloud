package com.mall4j.cloud.multishop.controller.admin;

/*
 * 模块名: admin_short_video_controller
 * 功能概述: 提供管理端短视频分页、详情、保存、更新和删除接口。
 * 对外接口: /admin/short_video
 * 依赖关系: ShortVideoService, AuthUserContext, BeanUtil
 * 输入输出: 输入管理端短视频表单，输出统一 ServerResponseEntity 响应。
 * 异常与错误: 参数和业务异常交由全局异常处理。
 * 维护说明: 当前版本使用手工视频地址，后续云点播接入不改变接口路径。
 */

import com.mall4j.cloud.common.constant.StatusEnum;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.common.util.BeanUtil;
import com.mall4j.cloud.multishop.dto.ShortVideoDTO;
import com.mall4j.cloud.multishop.model.ShortVideo;
import com.mall4j.cloud.multishop.service.ShortVideoService;
import com.mall4j.cloud.multishop.vo.ShortVideoVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 短视频
 */
@RestController("adminShortVideoController")
@RequestMapping("/admin/short_video")
@Tag(name = "admin-短视频")
public class ShortVideoController {

    @Autowired
    private ShortVideoService shortVideoService;

    @GetMapping("/page")
    @Operation(summary = "分页获取短视频列表", description = "分页获取短视频列表")
    public ServerResponseEntity<PageVO<ShortVideoVO>> page(@Valid PageDTO pageDTO, ShortVideoDTO shortVideoDTO) {
        shortVideoDTO.setShopId(AuthUserContext.get().getTenantId());
        PageVO<ShortVideoVO> shortVideoPage = shortVideoService.page(pageDTO, shortVideoDTO);
        return ServerResponseEntity.success(shortVideoPage);
    }

    @GetMapping
    @Operation(summary = "获取短视频", description = "根据shortVideoId获取短视频")
    public ServerResponseEntity<ShortVideoVO> getByShortVideoId(@RequestParam Long shortVideoId) {
        return ServerResponseEntity.success(shortVideoService.getByShortVideoId(shortVideoId));
    }

    @PostMapping
    @Operation(summary = "保存短视频", description = "保存短视频")
    public ServerResponseEntity<Void> save(@Valid @RequestBody ShortVideoDTO shortVideoDTO) {
        ShortVideo shortVideo = BeanUtil.map(shortVideoDTO, ShortVideo.class);
        shortVideo.setShortVideoId(null);
        shortVideo.setShopId(AuthUserContext.get().getTenantId());
        if (Objects.isNull(shortVideo.getStatus())) {
            shortVideo.setStatus(StatusEnum.ENABLE.value());
        }
        if (Objects.isNull(shortVideo.getViewCount())) {
            shortVideo.setViewCount(0L);
        }
        if (Objects.isNull(shortVideo.getLikeCount())) {
            shortVideo.setLikeCount(0L);
        }
        shortVideoService.save(shortVideo);
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新短视频", description = "更新短视频")
    public ServerResponseEntity<Void> update(@Valid @RequestBody ShortVideoDTO shortVideoDTO) {
        ShortVideo shortVideo = BeanUtil.map(shortVideoDTO, ShortVideo.class);
        shortVideo.setShopId(AuthUserContext.get().getTenantId());
        shortVideoService.update(shortVideo);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除短视频", description = "根据短视频id删除短视频")
    public ServerResponseEntity<Void> delete(@RequestParam Long shortVideoId) {
        shortVideoService.deleteById(shortVideoId, AuthUserContext.get().getTenantId());
        return ServerResponseEntity.success();
    }
}
