package com.mall4j.cloud.multishop.mapper;

/*
 * 模块名: short_video_mapper
 * 功能概述: 定义短视频 MyBatis 数据访问接口。
 * 对外接口: ShortVideoMapper
 * 依赖关系: ShortVideoDTO, ShortVideo, ShortVideoVO, MyBatis Param
 * 输入输出: 输入查询条件或短视频模型，输出短视频列表、详情或写入结果。
 * 异常与错误: 数据访问异常由 MyBatis 和事务框架向上抛出。
 * 维护说明: SQL 统一维护在 ShortVideoMapper.xml，避免在业务层拼接 SQL。
 */

import com.mall4j.cloud.multishop.dto.ShortVideoDTO;
import com.mall4j.cloud.multishop.model.ShortVideo;
import com.mall4j.cloud.multishop.vo.ShortVideoVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 短视频
 */
public interface ShortVideoMapper {

    /**
     * 获取短视频列表。
     *
     * @param shortVideoDTO 查询参数
     * @return 短视频列表
     */
    List<ShortVideoVO> list(@Param("shortVideoDTO") ShortVideoDTO shortVideoDTO);

    /**
     * 获取短视频详情。
     *
     * @param shortVideoId 短视频id
     * @return 短视频
     */
    ShortVideoVO getByShortVideoId(@Param("shortVideoId") Long shortVideoId);

    /**
     * 保存短视频。
     *
     * @param shortVideo 短视频
     */
    void save(@Param("shortVideo") ShortVideo shortVideo);

    /**
     * 更新短视频。
     *
     * @param shortVideo 短视频
     */
    void update(@Param("shortVideo") ShortVideo shortVideo);

    /**
     * 删除短视频。
     *
     * @param shortVideoId 短视频id
     * @param shopId 店铺id
     */
    void deleteById(@Param("shortVideoId") Long shortVideoId, @Param("shopId") Long shopId);

    /**
     * 获取移动端可见短视频列表。
     *
     * @param shopId 店铺id
     * @return 短视频列表
     */
    List<ShortVideoVO> listEnabled(@Param("shopId") Long shopId);
}
