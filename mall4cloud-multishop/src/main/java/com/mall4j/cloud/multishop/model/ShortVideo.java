package com.mall4j.cloud.multishop.model;

/*
 * 模块名: short_video_model
 * 功能概述: 承载短视频内容持久化字段，边界限定为商家运营内容。
 * 对外接口: ShortVideo
 * 依赖关系: BaseModel
 * 输入输出: 输入后台保存的短视频资料，输出 MyBatis 可持久化模型。
 * 异常与错误: 无业务异常，由 Service 和 Controller 层处理。
 * 维护说明: 视频地址先按手工配置保存，后续接入云点播时保持字段兼容。
 */

import com.mall4j.cloud.common.model.BaseModel;

import java.io.Serial;
import java.io.Serializable;

/**
 * 短视频
 */
public class ShortVideo extends BaseModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long shortVideoId;

    private Long shopId;

    private String title;

    private String coverUrl;

    private String videoUrl;

    private String description;

    private Long relatedSpuId;

    /**
     * 0下线 1上线
     */
    private Integer status;

    private Long viewCount;

    private Long likeCount;

    private Integer seq;

    private String locationName;

    private Double longitude;

    private Double latitude;

    public Long getShortVideoId() {
        return shortVideoId;
    }

    public void setShortVideoId(Long shortVideoId) {
        this.shortVideoId = shortVideoId;
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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getRelatedSpuId() {
        return relatedSpuId;
    }

    public void setRelatedSpuId(Long relatedSpuId) {
        this.relatedSpuId = relatedSpuId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
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
        return "ShortVideo{" +
                "shortVideoId=" + shortVideoId +
                ", shopId=" + shopId +
                ", title='" + title + '\'' +
                ", coverUrl='" + coverUrl + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", description='" + description + '\'' +
                ", relatedSpuId=" + relatedSpuId +
                ", status=" + status +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                ", seq=" + seq +
                ", locationName='" + locationName + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
