package com.mall4j.cloud.multishop.service.impl;

/*
 * 模块名: live_room_service_impl
 * 功能概述: 实现直播间分页、详情、写入和移动端列表查询。
 * 对外接口: LiveRoomServiceImpl
 * 依赖关系: LiveRoomMapper, PageUtil
 * 输入输出: 输入业务查询和写入参数，输出分页或列表数据。
 * 异常与错误: 数据访问异常向上抛出，由全局异常处理统一响应。
 * 维护说明: 第三方直播状态同步可在本类增加适配调用，保持 Mapper 字段稳定。
 */

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.util.PageUtil;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.multishop.dto.LiveRoomDTO;
import com.mall4j.cloud.multishop.mapper.LiveRoomMapper;
import com.mall4j.cloud.multishop.model.LiveRoom;
import com.mall4j.cloud.multishop.service.LiveRoomService;
import com.mall4j.cloud.multishop.vo.LiveRoomVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 直播间
 */
@Service
public class LiveRoomServiceImpl implements LiveRoomService {

    @Autowired
    private LiveRoomMapper liveRoomMapper;

    @Override
    public PageVO<LiveRoomVO> page(PageDTO pageDTO, LiveRoomDTO liveRoomDTO) {
        return PageUtil.doPage(pageDTO, () -> liveRoomMapper.list(liveRoomDTO));
    }

    @Override
    public LiveRoomVO getByLiveRoomId(Long liveRoomId) {
        return liveRoomMapper.getByLiveRoomId(liveRoomId);
    }

    @Override
    public void save(LiveRoom liveRoom) {
        liveRoomMapper.save(liveRoom);
    }

    @Override
    public void update(LiveRoom liveRoom) {
        liveRoomMapper.update(liveRoom);
    }

    @Override
    public void deleteById(Long liveRoomId, Long shopId) {
        liveRoomMapper.deleteById(liveRoomId, shopId);
    }

    @Override
    public List<LiveRoomVO> listEnabled(Long shopId) {
        return liveRoomMapper.listEnabled(shopId);
    }
}
