package com.mall4j.cloud.multishop.controller.admin;

/*
 * 模块名: admin_live_room_controller
 * 功能概述: 提供管理端直播间分页、详情、保存、更新和删除接口。
 * 对外接口: /admin/live_room
 * 依赖关系: LiveRoomService, AuthUserContext, BeanUtil
 * 输入输出: 输入管理端直播间表单，输出统一 ServerResponseEntity 响应。
 * 异常与错误: 参数和业务异常交由全局异常处理。
 * 维护说明: 当前版本使用手工播放地址，后续云直播接入不改变接口路径。
 */

import com.mall4j.cloud.common.constant.StatusEnum;
import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.common.response.ServerResponseEntity;
import com.mall4j.cloud.common.security.AuthUserContext;
import com.mall4j.cloud.common.util.BeanUtil;
import com.mall4j.cloud.multishop.dto.LiveRoomDTO;
import com.mall4j.cloud.multishop.model.LiveRoom;
import com.mall4j.cloud.multishop.service.LiveRoomService;
import com.mall4j.cloud.multishop.vo.LiveRoomVO;
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
 * 直播间
 */
@RestController("adminLiveRoomController")
@RequestMapping("/admin/live_room")
@Tag(name = "admin-直播间")
public class LiveRoomController {

    @Autowired
    private LiveRoomService liveRoomService;

    @GetMapping("/page")
    @Operation(summary = "分页获取直播间列表", description = "分页获取直播间列表")
    public ServerResponseEntity<PageVO<LiveRoomVO>> page(@Valid PageDTO pageDTO, LiveRoomDTO liveRoomDTO) {
        liveRoomDTO.setShopId(AuthUserContext.get().getTenantId());
        PageVO<LiveRoomVO> liveRoomPage = liveRoomService.page(pageDTO, liveRoomDTO);
        return ServerResponseEntity.success(liveRoomPage);
    }

    @GetMapping
    @Operation(summary = "获取直播间", description = "根据liveRoomId获取直播间")
    public ServerResponseEntity<LiveRoomVO> getByLiveRoomId(@RequestParam Long liveRoomId) {
        return ServerResponseEntity.success(liveRoomService.getByLiveRoomId(liveRoomId));
    }

    @PostMapping
    @Operation(summary = "保存直播间", description = "保存直播间")
    public ServerResponseEntity<Void> save(@Valid @RequestBody LiveRoomDTO liveRoomDTO) {
        LiveRoom liveRoom = BeanUtil.map(liveRoomDTO, LiveRoom.class);
        liveRoom.setLiveRoomId(null);
        liveRoom.setShopId(AuthUserContext.get().getTenantId());
        if (Objects.isNull(liveRoom.getStatus())) {
            liveRoom.setStatus(StatusEnum.ENABLE.value());
        }
        if (Objects.isNull(liveRoom.getLiveStatus())) {
            liveRoom.setLiveStatus(StatusEnum.DISABLE.value());
        }
        liveRoomService.save(liveRoom);
        return ServerResponseEntity.success();
    }

    @PutMapping
    @Operation(summary = "更新直播间", description = "更新直播间")
    public ServerResponseEntity<Void> update(@Valid @RequestBody LiveRoomDTO liveRoomDTO) {
        LiveRoom liveRoom = BeanUtil.map(liveRoomDTO, LiveRoom.class);
        liveRoom.setShopId(AuthUserContext.get().getTenantId());
        liveRoomService.update(liveRoom);
        return ServerResponseEntity.success();
    }

    @DeleteMapping
    @Operation(summary = "删除直播间", description = "根据直播间id删除直播间")
    public ServerResponseEntity<Void> delete(@RequestParam Long liveRoomId) {
        liveRoomService.deleteById(liveRoomId, AuthUserContext.get().getTenantId());
        return ServerResponseEntity.success();
    }
}
