package com.mall4j.cloud.multishop.service;

/*
 * 模块名: short_video_service
 * 功能概述: 定义短视频业务能力边界，包括后台分页、保存、更新、删除和移动端列表。
 * 对外接口: ShortVideoService
 * 依赖关系: PageDTO, PageVO, ShortVideoDTO, ShortVideo, ShortVideoVO
 * 输入输出: 输入业务查询和写入参数，输出分页或列表数据。
 * 异常与错误: 业务异常由实现层或调用方处理。
 * 维护说明: 云点播服务接入时优先在实现层扩展，不改变 Controller 契约。
 */

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.multishop.dto.ShortVideoDTO;
import com.mall4j.cloud.multishop.model.ShortVideo;
import com.mall4j.cloud.multishop.vo.ShortVideoVO;

import java.util.List;

/**
 * 短视频
 */
public interface ShortVideoService {

    /**
     * 分页获取短视频列表。
     *
     * @param pageDTO 分页参数
     * @param shortVideoDTO 查询参数
     * @return 短视频分页数据
     */
    PageVO<ShortVideoVO> page(PageDTO pageDTO, ShortVideoDTO shortVideoDTO);

    /**
     * 获取短视频详情。
     *
     * @param shortVideoId 短视频id
     * @return 短视频
     */
    ShortVideoVO getByShortVideoId(Long shortVideoId);

    /**
     * 保存短视频。
     *
     * @param shortVideo 短视频
     */
    void save(ShortVideo shortVideo);

    /**
     * 更新短视频。
     *
     * @param shortVideo 短视频
     */
    void update(ShortVideo shortVideo);

    /**
     * 删除短视频。
     *
     * @param shortVideoId 短视频id
     * @param shopId 店铺id
     */
    void deleteById(Long shortVideoId, Long shopId);

    /**
     * 获取移动端可见短视频列表。
     *
     * @param shopId 店铺id
     * @return 短视频列表
     */
    List<ShortVideoVO> listEnabled(Long shopId);
}
