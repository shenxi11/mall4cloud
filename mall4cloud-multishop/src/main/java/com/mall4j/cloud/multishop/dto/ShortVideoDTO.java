package com.mall4j.cloud.multishop.dto;

/*
 * 模块名: short_video_dto
 * 功能概述: 定义短视频后台查询和保存入参，不绑定具体云点播服务。
 * 对外接口: ShortVideoDTO
 * 依赖关系: Swagger Schema
 * 输入输出: 输入管理端表单与筛选条件，输出 Controller 到 Service 的传输对象。
 * 异常与错误: 参数校验由 Controller 和全局校验链处理。
 * 维护说明: 视频地址先作为人工配置入口，后续接入云点播时保持字段兼容。
 */

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 短视频DTO
 */
public class ShortVideoDTO {

    @Schema(description = "短视频id")
    private Long shortVideoId;

    @Schema(description = "店铺id")
    private Long shopId;

    @Schema(description = "标题")
    private String title;

    @Schema(description = "封面图")
    private String coverUrl;

    @Schema(description = "视频地址")
    private String videoUrl;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "关联商品id")
    private Long relatedSpuId;

    @Schema(description = "状态 0下线 1上线")
    private Integer status;

    @Schema(description = "播放量")
    private Long viewCount;

    @Schema(description = "点赞数")
    private Long likeCount;

    @Schema(description = "排序")
    private Integer seq;

    @Schema(description = "地理位置名称")
    private String locationName;

    @Schema(description = "经度")
    private Double longitude;

    @Schema(description = "纬度")
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
}
