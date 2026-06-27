<!--
模块名: uniapp_short_video_page
功能概述: 移动端短视频 tab 页面，展示已上线短视频内容流。
对外接口: pages/short-video/short-video
依赖关系: http 请求封装、uni video 组件
输入输出: 输入公开短视频接口数据，输出视频流卡片。
异常与错误: 请求失败由 http 封装提示，无视频地址时本页跳过播放。
维护说明: 当前使用手工视频地址，后续云点播接入后保持字段兼容。
-->
<template>
  <view class="page-short-video">
    <view class="topbar">
      <view>
        <view class="title">
          短视频
        </view>
        <view class="sub">
          AI选品内容流，边看边买
        </view>
      </view>
      <view class="tag">
        智能推荐
      </view>
    </view>

    <view class="video-list">
      <view
        v-for="item in shortVideos"
        :key="item.shortVideoId"
        class="video-card"
      >
        <video
          v-if="item.videoUrl"
          class="video"
          :src="addDomain(item.videoUrl)"
          :poster="addDomain(item.coverUrl)"
          controls
          object-fit="cover"
        />
        <image
          v-else
          class="video"
          :src="addDomain(item.coverUrl) || '/static/images/bannerBg.png'"
          mode="aspectFill"
        />
        <view class="info">
          <view class="title-row">
            <view class="name">
              {{ item.title }}
            </view>
            <view class="count">
              {{ item.likeCount || 0 }}赞
            </view>
          </view>
          <view
            v-if="item.description"
            class="desc"
          >
            {{ item.description }}
          </view>
          <view class="meta-row">
            <view class="meta">
              {{ item.viewCount || 0 }}次播放
            </view>
            <view
              v-if="item.locationName"
              class="meta"
            >
              {{ item.locationName }}
            </view>
          </view>
          <view
            v-if="item.relatedSpuId"
            class="prod-btn"
            @tap="toProdDetail(item.relatedSpuId)"
          >
            查看关联商品
          </view>
        </view>
      </view>
    </view>

    <view
      v-if="!shortVideos.length"
      class="empty"
    >
      <image src="/static/empty-img/not-found.png" />
      <view class="text">
        暂无短视频内容
      </view>
    </view>
  </view>
</template>

<script setup>
import { reactive, toRefs } from 'vue'

const Data = reactive({
  shortVideos: []
})
const { shortVideos } = toRefs(Data)

onShow(() => {
  getShortVideos()
})

const getShortVideos = () => {
  http.request({
    url: '/mall4cloud_multishop/ua/short_video/list',
    method: 'GET',
    data: {
      shopId: 0
    }
  }).then(res => {
    Data.shortVideos = res || []
  })
}

const addDomain = (path) => {
  const resourcesUrl = import.meta.env.VITE_APP_RESOURCES_URL
  if (!path || /^https?:\/\//.test(path)) {
    return path
  }
  return resourcesUrl + path
}

const toProdDetail = (spuId) => {
  uni.navigateTo({
    url: '/pages/detail/detail?spuId=' + spuId
  })
}
</script>

<style lang="scss" scoped>
page {
  background: #101114;
}

.page-short-video {
  min-height: 100vh;
  padding-bottom: 28rpx;
  color: #fff;

  .topbar {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 34rpx 30rpx 24rpx;
  }

  .title {
    font-size: 40rpx;
    font-weight: 700;
  }

  .sub {
    margin-top: 8rpx;
    color: rgba(255, 255, 255, .68);
  }

  .tag {
    padding: 10rpx 16rpx;
    border-radius: 999rpx;
    color: #111;
    background: #f6d365;
    font-size: 24rpx;
  }

  .video-card {
    margin: 0 24rpx 28rpx;
    background: #1b1d22;
    border-radius: 14rpx;
    overflow: hidden;
  }

  .video {
    width: 100%;
    height: 760rpx;
    background: #000;
  }

  .info {
    padding: 20rpx 22rpx 24rpx;
  }

  .title-row,
  .meta-row {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  .name {
    flex: 1;
    min-width: 0;
    font-size: 32rpx;
    font-weight: 600;
    line-height: 1.35;
  }

  .count {
    margin-left: 18rpx;
    color: #f6d365;
    font-size: 24rpx;
  }

  .desc {
    margin-top: 12rpx;
    color: rgba(255, 255, 255, .78);
    line-height: 1.6;
  }

  .meta-row {
    justify-content: flex-start;
    gap: 18rpx;
    margin-top: 14rpx;
  }

  .meta {
    color: rgba(255, 255, 255, .56);
    font-size: 24rpx;
  }

  .prod-btn {
    display: inline-block;
    margin-top: 18rpx;
    height: 56rpx;
    line-height: 56rpx;
    padding: 0 20rpx;
    border-radius: 8rpx;
    color: #fff;
    background: #fc1b35;
    font-size: 24rpx;
  }

  .empty {
    padding-top: 180rpx;
    text-align: center;
    color: rgba(255, 255, 255, .62);
  }

  .empty image {
    width: 220rpx;
    height: 220rpx;
  }
}
</style>
