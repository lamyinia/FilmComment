<template>
  <div>
    <div class="card" style="margin-bottom: 5px">
      <el-input v-model="data.name" placeholder="请输入电影名称" style="margin-top: 10px; width: 300px; margin-right: 10px; "></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <el-button :class="{ 'film-active': data.categoryId === null}" @click="loadFilmByCategory(null)">全部</el-button>
      <el-button v-for="item in data.categoryList" :key="item.id" @click="loadFilmByCategory(item.id)"
                 :class="{ 'film-active': data.categoryId === item.id}"> {{ item.name }} </el-button>
    </div>

    <div class="card" style="margin-bottom: 5px">
      <div>
        <el-row :gutter="20">
          <el-col :span="6" v-for="item in data.tableData" :key="item.id" style="cursor: pointer; margin-bottom: 30px;" @click="goDetail(item.id)" >
            <img :src="item.cover" alt="" style="width: 90%; height: 350px; border-radius: 5px">
            <div style="margin: 5px 0; font-size: 16px;" class="line1"> {{item.name}} </div>
            <div style="margin: 5px 0; display: flex; align-items: center;">
              <el-rate v-model="item.score" disabled allow-half show-score></el-rate>
              <div style="flex: 1;text-align: right; color: #1450aa"> {{ item.commentCount }}人评论</div>
            </div>
            <div style="font-size: 13px; color: #666" class="line3"> {{ item.descri }} </div>
          </el-col>
        </el-row>
      </div>
    </div>

    <div class="card" >
      <el-pagination background layout="total, prev, pager, next"
                     v-model:current-page="data.pageNum"
                     v-model:page-size="data.pageSize"
                     :total="data.total"
                     @current-change="load"/>
    </div>
  </div>
</template>

<script setup>
  import { reactive} from "vue";
  import request from "@/utils/request";

  const data = reactive({
    name: null,
    pageNum: 1,
    pageSize: 8,
    total: 0,
    categoryId: null,
    tableData: [],
    categoryList: [],
  })

  const goDetail = (id) => {
    location.href = '/filmDetail?id=' + id
  }

  const loadCategory = () => {
    request.get('/category/selectAll').then(res => {
      data.categoryList = res.data;
    })
  }
  loadCategory()

  const loadFilmByCategory = (categoryId) => {
    data.categoryId = categoryId;
    load();
  }

  const load = () => {
    request.get('/film/selectPage', {
      params: {
        pageNum: data.pageNum,
        pageSize: data.pageSize,
        name: data.name,
        CategoryId: data.categoryId
      }
    }).then(res => {
      data.tableData = res.data.list;
      data.total = res.data.total;
    })
  }
  load()

  const reset = () => {
    data.name = null
    load()
  }
</script>

<style scoped>
.film-active {
  background-color: #1967e3;
  color: white;
}
</style>