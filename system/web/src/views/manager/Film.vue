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
        <el-table-column prop="cover" label="封面" width="180" >
          <template #default="scope">
            <el-image :src="scope.row.cover" style="width: 40px; height: 40px; border-radius: 5px;"
            :preview-src-list="[scope.row.cover]" preview-teleported></el-image>
          </template>
        </el-table-column>
        <el-table-column prop="descri" label="描述" width="180" show-overflow-tooltip />
        <el-table-column prop="year" label="上映年份" width="180" />
        <el-table-column prop="director" label="导演" width="180" />
        <el-table-column prop="actors" label="演员" width="180" show-overflow-tooltip />
        <el-table-column prop="categoryName" label="分类" width="180" />
        <el-table-column prop="country" label="国家" width="180" />
        <el-table-column prop="language" label="语言" width="180" />
        <el-table-column prop="date" label="上映日期" width="180" />
        <el-table-column prop="duration" label="片长" width="180" />
        <el-table-column prop="imdb" label="IMDb" width="180" />

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
    <el-dialog v-model="data.formVisible" title="电影信息" width="40%">
      <el-form :model="data.form" label-width="80px" style="padding-right: 30px">

        <el-form-item label="名称">
          <el-input v-model="data.form.name" autocomplete="off" placeholder="请输入名称"/>
        </el-form-item>
        <el-form-item label="封面" prop="cover">
          <el-upload :action="uploadUrl" list-type="picture" :on-success="handleImgSuccess">
            <el-button type="primary">上传图片</el-button>
          </el-upload>
        </el-form-item>
        <el-form-item label="描述">
          <el-input type="textarea" v-model="data.form.descri" autocomplete="off" placeholder="请输入描述"/>
        </el-form-item>
        <el-form-item label="上映年份">
          <el-input v-model="data.form.year" autocomplete="off" placeholder="请输入上映年份"/>
        </el-form-item>
        <el-form-item label="导演">
          <el-input v-model="data.form.director" autocomplete="off" placeholder="请输入导演"/>
        </el-form-item>
        <el-form-item label="演员">
          <el-input :rows="5" type="textarea" v-model="data.form.actors" autocomplete="off" placeholder="请输入演员"/>
        </el-form-item>
        <el-form-item label="分类">
          <el-input v-model="data.form.categoryId" autocomplete="off" placeholder="请输入分类"/>
        </el-form-item>
        <el-form-item label="国家">
          <el-input v-model="data.form.country" autocomplete="off" placeholder="请输入国家"/>
        </el-form-item>
        <el-form-item label="语言">
          <el-input v-model="data.form.language" autocomplete="off" placeholder="请输入语言"/>
        </el-form-item>
        <el-form-item label="上映日期">
          <el-date-picker v-model="data.form.date" type="date" placeholder="请选择上映日期"
          format="YYYY-MM-DD" value-format="YYYY-MM-DD" style="width: 100%"></el-date-picker>
        </el-form-item>
        <el-form-item label="片长">
          <el-input v-model="data.form.duration" autocomplete="off" placeholder="请输入片长"/>
        </el-form-item>
        <el-form-item label="IMDb">
          <el-input v-model="data.form.imdb" autocomplete="off" placeholder="请输入IMDb"/>
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
const uploadUrl = import.meta.env.VITE_BASE_URL + '/files/upload'

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
  request.get('/film/selectPage', {
    params: {
      pageNum: data.pageNum,
      pageSize: data.pageSize,
      name: data.name
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
// 初始化新增的数据
const handleAdd = () => {
  data.form = {}
  data.formVisible = true
}
const add = () => {
  request.post('/film/add', data.form).then(res => {
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
  request.put('/film/update', data.form).then(res => {
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
    request.delete('/film/delete/' + id).then(res => {
      if (res.code === '200') {
        load()
        ElMessage.success('操作成功')
      } else {
        ElMessage.error(res.msg)
      }
    })
  }).catch(err => {})
}

const handleImgSuccess = (res) => {
  data.form.cover = res.data  // res.data就是文件上传返回的文件路径，获取到路径后赋值表单的属性
}
</script>


<!--<style scoped>-->

<!--</style>-->