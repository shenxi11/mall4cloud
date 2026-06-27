package com.mall4j.cloud.multishop.service;

/*
 * 模块名: live_room_service
 * 功能概述: 定义直播间业务能力边界，包括后台分页、保存、更新、删除和移动端列表。
 * 对外接口: LiveRoomService
 * 依赖关系: PageDTO, PageVO, LiveRoomDTO, LiveRoom, LiveRoomVO
 * 输入输出: 输入业务查询和写入参数，输出分页或列表数据。
 * 异常与错误: 业务异常由实现层或调用方处理。
 * 维护说明: 第三方直播服务接入时优先在实现层扩展，不改变 Controller 契约。
 */

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.multishop.dto.LiveRoomDTO;
import com.mall4j.cloud.multishop.model.LiveRoom;
import com.mall4j.cloud.multishop.vo.LiveRoomVO;

import java.util.List;

/**
 * 直播间
 */
public interface LiveRoomService {

    /**
     * 分页获取直播间列表。
     *
     * @param pageDTO 分页参数
     * @param liveRoomDTO 查询参数
     * @return 直播间分页数据
     */
    PageVO<LiveRoomVO> page(PageDTO pageDTO, LiveRoomDTO liveRoomDTO);

    /**
     * 获取直播间详情。
     *
     * @param liveRoomId 直播间id
     * @return 直播间
     */
    LiveRoomVO getByLiveRoomId(Long liveRoomId);

    /**
     * 保存直播间。
     *
     * @param liveRoom 直播间
     */
    void save(LiveRoom liveRoom);

    /**
     * 更新直播间。
     *
     * @param liveRoom 直播间
     */
    void update(LiveRoom liveRoom);

    /**
     * 删除直播间。
     *
     * @param liveRoomId 直播间id
     * @param shopId 店铺id
     */
    void deleteById(Long liveRoomId, Long shopId);

    /**
     * 获取移动端可见直播间列表。
     *
     * @param shopId 店铺id
     * @return 直播间列表
     */
    List<LiveRoomVO> listEnabled(Long shopId);
}
