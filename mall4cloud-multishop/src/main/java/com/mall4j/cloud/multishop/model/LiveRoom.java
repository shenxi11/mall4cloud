package com.mall4j.cloud.multishop.model;

/*
 * 模块名: live_room_model
 * 功能概述: 承载直播间持久化字段，边界限定为商家运营内容，不直接绑定第三方直播服务。
 * 对外接口: LiveRoom
 * 依赖关系: BaseModel
 * 输入输出: 输入后台保存的直播间资料，输出 MyBatis 可持久化模型。
 * 异常与错误: 无业务异常，由 Service 和 Controller 层处理。
 * 维护说明: 播放地址先按手工配置保存，后续接入直播云服务时保持字段兼容。
 */

import com.mall4j.cloud.common.model.BaseModel;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 直播间
 */
public class LiveRoom extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long liveRoomId;

    private Long shopId;

    private String title;

    private String coverUrl;

    private String anchorName;

    /**
     * 0预告 1直播中 2回放
     */
    private Integer liveStatus;

    /**
     * 0下线 1上线
     */
    private Integer status;

    private Date startTime;

    private Date endTime;

    private String playUrl;

    private String replayUrl;

    private Long relatedSpuId;

    private Integer seq;

    private String locationName;

    private Double longitude;

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

    @Override
    public String toString() {
        return "LiveRoom{" +
                "liveRoomId=" + liveRoomId +
                ", shopId=" + shopId +
                ", title='" + title + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", anchorName='" + anchorName + '\'' +
                ", liveStatus=" + liveStatus +
                ", status=" + status +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", playUrl='" + playUrl + '\'' +
                ", replayUrl='" + replayUrl + '\'' +
                ", relatedSpuId=" + relatedSpuId +
                ", seq=" + seq +
                ", locationName='" + locationName + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
