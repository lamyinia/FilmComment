<template>
  <div>
    <div>
      <el-input v-model="data.name" placeholder="请输入标题查询" style="margin-top: 10px; width: 300px; margin-right: 10px; "></el-input>
      <el-button type="primary" @click="load">查询</el-button>
      <el-button type="info" @click="reset">重置</el-button>
    </div>

    <div class="card" style="margin-bottom: 10px">
      <div>
        <el-button type="primary" style="margin-bottom: 5px" @click="handleAdd">新增</el-button>
      </div>

      <el-table :data="data.tableData" border style="width: 100%">
        <el-table-column prop="name" label="名称" width="180" />

        <el-table-column label="操作" width="160">
          <template #default="scope">
            <el-button type="primary" @click="handleEdit(scope.row)">编辑</el-button>
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

<!--    弹窗部分-->
    <el-dialog v-model="data.formVisible" title="电影分类" width="40%">
      <el-form :model="data.form" label-width="80px" style="padding-right: 30px">

        <el-form-item label="名称">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入名称"/>
        </el-form-item>

      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
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
  tableData: [],
  total: 0,
  pageNum: 1,
  pageSize: 10,
  name: null,
  formVisible: false,
  form: {}
})
const load = () => {
  request.get('/category/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name
    }
  }).then(res => {
    console.log(res)
    data.tableData = res.data.list;
    data.total = res.data.total;
  })
}
load()

const reset = () => {
  data.name = null
  load()
}
// 初始化新增的数据
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}
const add = () => {
  request.post('/category/add', data.form).then(res => {
    console.log(res)
    if (res.code === '200') {
      load()
      data.formVisible = false;
      ElMessage.success('操作成功')
    } else {
      ElMessage.error(res.msg)
    }
  })
}

// 编辑按钮出发
const handleEdit = (row)=> {
  data.form = JSON.parse(JSON.stringify(row))
  data.formVisible = true
}
const update = () => {
  request.put('/category/update', data.form).then(res => {
    console.log(res)
    if (res.code === '200') {
      load()
      data.formVisible = false;
      ElMessage.success('操作成功')
    } else {
      ElMessage.error(res.msg)
    }
  })
}

const save = () => {
  data.form.id ? update() : add()
}
const del = (id) => {
  ElMessageBox.confirm('删除后无法回复，是否确认？', '确认删除',{type: 'warning'}).then(res => {
    request.delete('/category/delete/' + id).then(res => {
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


<!--<style scoped>-->

<!--</style>-->