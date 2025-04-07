<template>
  <div style="display: flex; grid-gap: 10px">
    <div class="card" style="width: 50%; padding: 20px;">
      <div style="margin-bottom: 30px; font-size: 20px; font-weight: bold">电影评分榜</div>
      <div v-for="(item, index) in data.filmList" :key="item.id">
        <div style="display: flex; grid-gap: 10px; margin-bottom: 10px;">
          <div style="font-weight: bold; font-size: 18px; color: gold" v-if="index===0">1</div>
          <div style="font-weight: bold; font-size: 18px; color: silver" v-if="index===1">1</div>
          <div style="font-weight: bold; font-size: 18px; color: coral" v-if="index===2">1</div>
          <div style="font-weight: bold; font-size: 18px; color: brown" v-if="index===3">1</div>
          <div style="font-weight: bold; font-size: 18px; color: blanchedalmond" v-if="index===4">1</div>

          <img :src="item.cover" alt="" style="width: 150px;">
          <div style="flex: 1">
            <div style="font-size: 20px; margin-bottom: 10px"> {{ item.name }} </div>
            <div style="color: #666666" class="line3"> {{item.descri}} </div>
          </div>
        </div>

      </div>
    </div>


    <div style="width: 50%; padding: 20px;">
      <div style="margin-bottom: 30px; padding-left: 50px; font-size: 20px; font-weight: bold;">系统公告</div>

      <el-timeline>

        <el-timeline-item v-for="item in data.noticeList" :key="item.id" :timestamp="item.time" placement="top">
          <div class="card">
            <div style="font-weight: bold; margin-bottom: 20px; font-size: 16px">{{ item.title }}</div>
            <div style="color: #666">{{ item.content }}</div>
          </div>
        </el-timeline-item>

      </el-timeline>
    </div>

  </div>
</template>

<script setup>
import { reactive } from "vue";
import request from "@/utils/request";

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  noticeList: [],
  filmList: []
})

request.get('/notice/selectAll').then(res => {
  data.noticeList = res.data
})
request.get('/film/selectRaking').then(res => {
  data.filmList = res.data
})
</script>