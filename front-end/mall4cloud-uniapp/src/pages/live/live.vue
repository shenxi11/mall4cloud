<!--
模块名: uniapp_live_page
功能概述: 移动端直播 tab 页面，展示已上线直播间和播放入口。
对外接口: pages/live/live
依赖关系: http 请求封装、uni video 组件
输入输出: 输入公开直播间接口数据，输出直播卡片和播放器。
异常与错误: 请求失败由 http 封装提示，无播放地址时本页提示。
维护说明: 当前使用手工播放地址，后续云直播接入后保持字段兼容。
-->
<template>
  <view class="page-live">
    <view class="hero">
      <view class="hero-main">
        <view class="title">
          智能直播
        </view>
        <view class="sub">
          精选商家直播、回放和附近活动
        </view>
      </view>
      <view class="ai-badge">
        AI运营
      </view>
    </view>

    <view
      v-if="currentRoom"
      class="player-card"
    >
      <video
        class="player"
        :src="currentPlayUrl"
        :poster="addDomain(currentRoom.coverUrl)"
        controls
        autoplay
      />
      <view class="player-title">
        {{ currentRoom.title }}
      </view>
    </view>

    <view class="room-list">
      <view
        v-for="item in liveRooms"
        :key="item.liveRoomId"
        class="room-card"
      >
        <image
          class="cover"
          :src="addDomain(item.coverUrl) || '/static/images/bannerBg.png'"
          mode="aspectFill"
        />
        <view class="content">
          <view class="row">
            <view class="name">
              {{ item.title }}
            </view>
            <view
              class="status"
              :class="'s' + item.liveStatus"
            >
              {{ liveStatusText(item.liveStatus) }}
            </view>
          </view>
          <view class="meta">
            主播：{{ item.anchorName || '官方直播间' }}
          </view>
          <view
            v-if="item.locationName"
            class="meta"
          >
            位置：{{ item.locationName }}
          </view>
          <view class="actions">
            <view
              class="btn primary"
              @tap="playRoom(item)"
            >
              {{ item.liveStatus === 2 ? '看回放' : '进入直播' }}
            </view>
            <view
              v-if="item.relatedSpuId"
              class="btn ghost"
              @tap="toProdDetail(item.relatedSpuId)"
            >
              关联商品
            </view>
          </view>
        </view>
      </view>
    </view>

    <view
      v-if="!liveRooms.length"
      class="empty"
    >
      <image src="/static/empty-img/not-found.png" />
      <view class="text">
        暂无直播内容
      </view>
    </view>
  </view>
</template>

<script setup>
import { computed, reactive, toRefs } from 'vue'

const Data = reactive({
  liveRooms: [],
  currentRoom: null
})
const { liveRooms, currentRoom } = toRefs(Data)

const currentPlayUrl = computed(() => {
  if (!Data.currentRoom) {
    return ''
  }
  return Data.currentRoom.liveStatus === 2 ? Data.currentRoom.replayUrl : Data.currentRoom.playUrl
})

onShow(() => {
  getLiveRooms()
})

const getLiveRooms = () => {
  http.request({
    url: '/mall4cloud_multishop/ua/live_room/list',
    method: 'GET',
    data: {
      shopId: 0
    }
  }).then(res => {
    Data.liveRooms = res || []
    if (!Data.currentRoom && Data.liveRooms.length) {
      Data.currentRoom = Data.liveRooms[0]
    }
  })
}

const addDomain = (path) => {
  const resourcesUrl = import.meta.env.VITE_APP_RESOURCES_URL
  if (!path || /^https?:\/\//.test(path)) {
    return path
  }
  return resourcesUrl + path
}

const liveStatusText = (status) => {
  return ['预告', '直播中', '回放'][status] || '直播'
}

const playRoom = (item) => {
  const url = item.liveStatus === 2 ? item.replayUrl : item.playUrl
  if (!url) {
    uni.showToast({
      title: '暂无播放地址',
      icon: 'none'
    })
    return
  }
  Data.currentRoom = item
}

const toProdDetail = (spuId) => {
  uni.navigateTo({
    url: '/pages/detail/detail?spuId=' + spuId
  })
}
</script>

<style lang="scss" scoped>
page {
  background: #f4f5f8;
}

.page-live {
  min-height: 100vh;
  padding-bottom: 28rpx;

  .hero {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 36rpx 30rpx 44rpx;
    color: #fff;
    background: linear-gradient(135deg, #fc1b35 0%, #232946 100%);
  }

  .title {
    font-size: 40rpx;
    font-weight: 700;
  }

  .sub {
    margin-top: 10rpx;
    color: rgba(255, 255, 255, .82);
  }

  .ai-badge {
    padding: 10rpx 18rpx;
    border: 1rpx solid rgba(255, 255, 255, .55);
    border-radius: 999rpx;
    font-size: 24rpx;
  }

  .player-card,
  .room-card {
    margin: 24rpx 24rpx 0;
    background: #fff;
    border-radius: 12rpx;
    overflow: hidden;
  }

  .player {
    width: 100%;
    height: 420rpx;
    background: #111;
  }

  .player-title {
    padding: 18rpx 22rpx;
    font-size: 30rpx;
    font-weight: 600;
  }

  .room-card {
    display: flex;
    padding: 18rpx;
  }

  .cover {
    width: 190rpx;
    height: 150rpx;
    border-radius: 10rpx;
    background: #eee;
  }

  .content {
    flex: 1;
    min-width: 0;
    margin-left: 18rpx;
  }

  .row {
    display: flex;
    align-items: flex-start;
  }

  .name {
    flex: 1;
    font-size: 30rpx;
    font-weight: 600;
    color: #222;
    line-height: 1.35;
  }

  .status {
    margin-left: 12rpx;
    padding: 4rpx 10rpx;
    border-radius: 6rpx;
    font-size: 22rpx;
    color: #fff;
    background: #8a94a6;
  }

  .status.s1 {
    background: #fc1b35;
  }

  .status.s2 {
    background: #2f80ed;
  }

  .meta {
    margin-top: 10rpx;
    color: #777;
    font-size: 24rpx;
  }

  .actions {
    display: flex;
    margin-top: 16rpx;
  }

  .btn {
    height: 52rpx;
    line-height: 52rpx;
    padding: 0 18rpx;
    border-radius: 8rpx;
    font-size: 24rpx;
  }

  .btn.primary {
    color: #fff;
    background: #fc1b35;
  }

  .btn.ghost {
    margin-left: 12rpx;
    color: #333;
    background: #f0f1f4;
  }

  .empty {
    padding-top: 160rpx;
    text-align: center;
    color: #999;
  }

  .empty image {
    width: 220rpx;
    height: 220rpx;
  }
}
</style>
