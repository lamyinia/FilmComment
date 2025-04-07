<template>
  <div>
    <div>
      <el-input v-model="data.filmName" placeholder="请输入电影名称" style="margin-top: 10px; width: 300px; margin-right: 10px; "></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">

      <el-table :data="data.tableData" border style="width: 100%">
        <el-table-column prop="filmName" label="电影名称" width="180" />
        <el-table-column prop="score" label="评分" width="180">
          <template #default="scope">
            <el-rate disabled v-model="scope.row.score" allow-half />
          </template>
        </el-table-column>
        <el-table-column prop="content" label="评论" width="180">
          <template #default="scope">
            <el-button @click="preview(scope.row.content)">查看内容</el-button>
          </template>
        </el-table-column>
        <el-table-column prop="userName" label="用户名称" width="180" />
        <el-table-column prop="time" label="评论时间" width="180" />
        <el-table-column prop="type" label="类型" width="180">
          <template #default="scope">
            <el-tag type="primary" v-if="scope.row.type === '短评' ">短评</el-tag>
            <el-tag type="success" v-if="scope.row.type === '长评' ">长评</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="100">
          <template #default="scope">
            <el-button type="danger" @click="del(scope.row.id)">删除</el-button>
          </template>
        </el-table-column>

      </el-table>
    </div>

<!--    分页部分-->
    <div class="card">
      <el-pagination background layout="total, prev, pager, next"
                     v-model:current-page="data.pageNum"
                     v-model:page-size="data.pageSize"
                     :total="data.total"
                     @current-change="load"/>
    </div>

    <el-dialog v-model="data.formVisibleComment" title="电影信息" width="40%">
      <div class="longComment" v-html="data.formContent"></div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisibleComment = false">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>

</template>

<script setup>
import {reactive} from "vue";
import request from "@/utils/request";
import {ElMessage, ElMessageBox} from "element-plus";

const data = reactive({
  user: JSON.parse(localStorage.getItem('system-user') || '{}'),
  tableData: [],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  filmName: null,
  formVisibleComment: false,
  form: {},
  formContent: null
})

const preview = (content)=> {
  data.formContent = content
  data.formVisibleComment = true
}

const load = () => {
  request.get('/comment/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      filmName: data.filmName,
      userId: data.user.role === 'ADMIN' ? null : data.user.id,
    }
  }).then(res => {
    data.tableData = res.data.list;
    data.total = res.data.total;
  })
}
load()

const reset = () => {
  data.filmName = null
  load()
}

const del = (id) => {
  ElMessageBox.confirm('删除后无法回复，是否确认？', '确认删除',{type: 'warning'}).then(res => {
    request.delete('/comment/delete/' + id).then(res => {
      if (res.code === '200') {
        load()
        ElMessage.success('操作成功')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {})
}
</script>


<style>
.longComment img{
  width: 100% !important;
}
</style>