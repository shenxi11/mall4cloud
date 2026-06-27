package com.mall4j.cloud.multishop.vo;

/*
 * 模块名: short_video_vo
 * 功能概述: 定义短视频对管理端和移动端的返回结构。
 * 对外接口: ShortVideoVO
 * 依赖关系: BaseVO, Swagger Schema
 * 输入输出: 输入 MyBatis 查询结果，输出前端可渲染的短视频数据。
 * 异常与错误: 无业务异常。
 * 维护说明: 字段需与 short_video 表和移动端展示保持兼容。
 */

import com.mall4j.cloud.common.vo.BaseVO;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * 短视频VO
 */
public class ShortVideoVO extends BaseVO {

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
