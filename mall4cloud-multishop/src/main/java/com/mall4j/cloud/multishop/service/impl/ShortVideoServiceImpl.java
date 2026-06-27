package com.mall4j.cloud.multishop.service.impl;

/*
 * 模块名: short_video_service_impl
 * 功能概述: 实现短视频分页、详情、写入和移动端列表查询。
 * 对外接口: ShortVideoServiceImpl
 * 依赖关系: ShortVideoMapper, PageUtil
 * 输入输出: 输入业务查询和写入参数，输出分页或列表数据。
 * 异常与错误: 数据访问异常向上抛出，由全局异常处理统一响应。
 * 维护说明: 云点播状态同步可在本类增加适配调用，保持 Mapper 字段稳定。
 */

import com.mall4j.cloud.common.database.dto.PageDTO;
import com.mall4j.cloud.common.database.util.PageUtil;
import com.mall4j.cloud.common.database.vo.PageVO;
import com.mall4j.cloud.multishop.dto.ShortVideoDTO;
import com.mall4j.cloud.multishop.mapper.ShortVideoMapper;
import com.mall4j.cloud.multishop.model.ShortVideo;
import com.mall4j.cloud.multishop.service.ShortVideoService;
import com.mall4j.cloud.multishop.vo.ShortVideoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 短视频
 */
@Service
public class ShortVideoServiceImpl implements ShortVideoService {

    @Autowired
    private ShortVideoMapper shortVideoMapper;

    @Override
    public PageVO<ShortVideoVO> page(PageDTO pageDTO, ShortVideoDTO shortVideoDTO) {
        return PageUtil.doPage(pageDTO, () -> shortVideoMapper.list(shortVideoDTO));
    }

    @Override
    public ShortVideoVO getByShortVideoId(Long shortVideoId) {
        return shortVideoMapper.getByShortVideoId(shortVideoId);
    }

    @Override
    public void save(ShortVideo shortVideo) {
        shortVideoMapper.save(shortVideo);
    }

    @Override
    public void update(ShortVideo shortVideo) {
        shortVideoMapper.update(shortVideo);
    }

    @Override
    public void deleteById(Long shortVideoId, Long shopId) {
        shortVideoMapper.deleteById(shortVideoId, shopId);
    }

    @Override
    public List<ShortVideoVO> listEnabled(Long shopId) {
        return shortVideoMapper.listEnabled(shopId);
    }
}
