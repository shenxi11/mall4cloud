package com.mall4j.cloud.multishop.mapper;

/*
 * 模块名: live_room_mapper
 * 功能概述: 定义直播间 MyBatis 数据访问接口。
 * 对外接口: LiveRoomMapper
 * 依赖关系: LiveRoomDTO, LiveRoom, LiveRoomVO, MyBatis Param
 * 输入输出: 输入查询条件或直播间模型，输出直播间列表、详情或写入结果。
 * 异常与错误: 数据访问异常由 MyBatis 和事务框架向上抛出。
 * 维护说明: SQL 统一维护在 LiveRoomMapper.xml，避免在业务层拼接 SQL。
 */

import com.mall4j.cloud.multishop.dto.LiveRoomDTO;
import com.mall4j.cloud.multishop.model.LiveRoom;
import com.mall4j.cloud.multishop.vo.LiveRoomVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 直播间
 */
public interface LiveRoomMapper {

    /**
     * 获取直播间列表。
     *
     * @param liveRoomDTO 查询参数
     * @return 直播间列表
     */
    List<LiveRoomVO> list(@Param("liveRoomDTO") LiveRoomDTO liveRoomDTO);

    /**
     * 获取直播间详情。
     *
     * @param liveRoomId 直播间id
     * @return 直播间
     */
    LiveRoomVO getByLiveRoomId(@Param("liveRoomId") Long liveRoomId);

    /**
     * 保存直播间。
     *
     * @param liveRoom 直播间
     */
    void save(@Param("liveRoom") LiveRoom liveRoom);

    /**
     * 更新直播间。
     *
     * @param liveRoom 直播间
     */
    void update(@Param("liveRoom") LiveRoom liveRoom);

    /**
     * 删除直播间。
     *
     * @param liveRoomId 直播间id
     * @param shopId 店铺id
     */
    void deleteById(@Param("liveRoomId") Long liveRoomId, @Param("shopId") Long shopId);

    /**
     * 获取移动端可见直播间列表。
     *
     * @param shopId 店铺id
     * @return 直播间列表
     */
    List<LiveRoomVO> listEnabled(@Param("shopId") Long shopId);
}
