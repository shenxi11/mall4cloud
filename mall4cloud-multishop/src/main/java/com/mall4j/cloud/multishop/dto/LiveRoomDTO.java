package com.mall4j.cloud.multishop.dto;

/*
 * 模块名: live_room_dto
 * 功能概述: 定义直播间后台查询和保存入参，不暴露第三方直播实现细节。
 * 对外接口: LiveRoomDTO
 * 依赖关系: Swagger Schema
 * 输入输出: 输入管理端表单与筛选条件，输出 Controller 到 Service 的传输对象。
 * 异常与错误: 参数校验由 Controller 和全局校验链处理。
 * 维护说明: 第三方直播配置接入前，播放地址字段仍作为人工配置入口保留。
 */

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Date;

/**
 * 直播间DTO
 */
public class LiveRoomDTO {

    @Schema(description = "直播间id")
    private Long liveRoomId;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "直播标题")
    private String title;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "主播名称")
    private String anchorName;

    @Schema(description = "直播状态 0预告 1直播中 2回放")
    private Integer liveStatus;

    @Schema(description = "状态 0下线 1上线")
    private Integer status;

    @Schema(description = "开播时间")
    private Date startTime;

    @Schema(description = "结束时间")
    private Date endTime;

    @Schema(description = "直播播放地址")
    private String playUrl;

    @Schema(description = "回放地址")
    private String replayUrl;

    @Schema(description = "关联商品id")
    private Long relatedSpuId;

    @Schema(description = "排序")
    private Integer seq;

    @Schema(description = "地理位置名称")
    private String locationName;

    @Schema(description = "经度")
    private Double longitude;

    @Schema(description = "纬度")
    private Double latitude;

    public Long getLiveRoomId() {
        return liveRoomId;
    }

    public void setLiveRoomId(Long liveRoomId) {
        this.liveRoomId = liveRoomId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getAnchorName() {
        return anchorName;
    }

    public void setAnchorName(String anchorName) {
        this.anchorName = anchorName;
    }

    public Integer getLiveStatus() {
        return liveStatus;
    }

    public void setLiveStatus(Integer liveStatus) {
        this.liveStatus = liveStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

    public String getReplayUrl() {
        return replayUrl;
    }

    public void setReplayUrl(String replayUrl) {
        this.replayUrl = replayUrl;
    }

    public Long getRelatedSpuId() {
        return relatedSpuId;
    }

    public void setRelatedSpuId(Long relatedSpuId) {
        this.relatedSpuId = relatedSpuId;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
