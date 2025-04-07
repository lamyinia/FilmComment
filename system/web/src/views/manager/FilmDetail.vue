<template>
  <div>
    <div style="display: flex; align-items: flex-start; grip-gap: 20px">

      <div style="flex: 1; width: 0">

        <div class="card" style="padding: 20px; margin-bottom: 10px;">
          <div style="display: flex; align-items: center;">
            <div style="font-weight: bold; font-size: 18px; flex: 1"> {{ data.film.name }} </div>
            <div>
              <el-button type="primary" plain @click="addComment('短评')">写短评</el-button>
              <el-button type="success" plain @click="addComment('长评')">写长评</el-button>
            </div>
          </div>
          <div style="margin-bottom: 20px">
            <el-rate v-model="data.film.score" disabled allow-half show-score></el-rate>
          </div>

          <div style="display: flex; grid-gap: 20px">
            <img :src="data.film.cover" alt="" style="width: 200px; height: 300px; border-radius: 5px">
            <div style="flex: 1; color: #666">
              <div style="margin-bottom: 10px"><strong>导演：</strong> {{data.film.director}} </div>
              <div style="margin-bottom: 10px"><strong>主演：</strong> {{data.film.actors}} </div>
              <div style="margin-bottom: 10px"><strong>类型：</strong> {{data.film.categoryName}} </div>
              <div style="margin-bottom: 10px"><strong>制片国家/地区：</strong> {{data.film.country}} </div>
              <div style="margin-bottom: 10px"><strong>语言：</strong> {{data.film.language}} </div>
              <div style="margin-bottom: 10px"><strong>上映日期：</strong> {{data.film.date}} </div>
              <div style="margin-bottom: 10px"><strong>片长：</strong> {{data.film.duration}} </div>
              <div style="margin-bottom: 10px"><strong>IMDb：</strong> {{data.film.imdb}} </div>
            </div>
          </div>

          <div style="font-size: 20px; color: #239113; margin-bottom: 10px;"> {{data.film.name}} 的剧情简介</div>
          <div style="color: #666; text-indent: 2rem; line-height: 24px"> {{data.film.descri}} </div>
        </div>

        <div class="card" style="padding: 20px;">
          <div style="font-size: 20px; color: #1967e3; margin-bottom: 10px;">
            {{ data.film.name }} 的短评 <span style="font-size: 14px; color: #333333">...(全部{{data.totalShort}}条)</span>
          </div>
          <div style="border-bottom: 1px solid #eee; padding: 20px 0" v-for="item in data.commentShortList" :key="item.id">
            <div style="display: flex; align-items: center; margin-bottom: 5px">
              <span style="margin-right: 10px"> {{item.userName==null ? "匿名" : item.userName}} 的 评分 </span>
              <el-rate style="margin: 0 10px" v-model="item.score" disabled allow-half></el-rate>
              <span style="color: #666666"> {{item.time}} </span>
            </div>
            <div style="line-height: 24px; color: #666666;"> {{item.content}} </div>
          </div>
          <el-pagination layout="total, prev, pager, next"
                         v-model:current-page="data.pageNumShort"
                         v-model:page-size="data.pageSizeShort"
                         :total="data.totalShort"
                         @current-change="loadShortComment"/>
        </div>

        <div class="card" style="padding: 20px; margin-bottom: 10px;">
          <div style="font-size: 20px; color: orange; margin-bottom: 10px;">
            {{ data.film.name }} 的长评 <span style="font-size: 14px; color: #333333">...(全部{{data.totalLong}}条)</span>
          </div>
          <div style="border-bottom: 1px solid #eee; padding: 20px 0" v-for="item in data.commentLongList" :key="item.id">
            <div style="display: flex; align-items: center; margin-bottom: 5px">
              <span style="margin-right: 10px"> {{item.userName==null ? "匿名" : item.userName}} 的 评分 </span>
              <el-rate style="margin: 0 10px" v-model="item.score" disabled allow-half></el-rate>
              <span style="color: #666666"> {{ item.time }} </span>
            </div>
            <div style="line-height: 24px; color: #666666; margin-bottom: 5px; height: 300px; overflow: auto" class="line3, longComment" >
              <view v-html="item.content"></view>
            </div>
            <div><span style="color:#1967e3; cursor: pointer" @click="viewLongComment(item.content)">查看更多...</span></div>
          </div>
          <el-pagination layout="total, prev, pager, next"
                         v-model:current-page="data.pageNumLong"
                         v-model:page-size="data.pageSizeLong"
                         :total="data.totalLong"
                         @current-change="loadLongComment"/>
        </div>
      </div>


      <div style="width: 300px; padding: 20px" class="card">
        <div style="font-size: 20px; margin-bottom: 20px">推荐电影</div>
        <div style="cursor: pointer; margin-bottom: 20px" v-for="item in data.RecommendList" :key="item.filmId" @click="goDetail(item.filmId)">
          <img :src="item.cover" alt="" style="width: 80%; border-radius: 5px; margin-bottom: 5px">
          <div style="margin: 5px 0; font-size: 16px;"> {{item.name}} </div>
          <div>
            <el-rate v-model="item.score" disabled allow-half show-score></el-rate>
          </div>
        </div>
      </div>
    </div>

    <el-dialog v-model="data.formVisible" title="评论信息" width="50%">
      <el-form :model="data.form" label-width="80px" style="padding-right: 30px">

        <el-form-item label="打分">
          <el-rate v-model="data.form.score" allow-half show-score></el-rate>
        </el-form-item>
        <el-form-item label="内容" v-if="data.form.type === '短评'">
          <el-input :rows="5" type="textarea" v-model="data.form.content" autocomplete="off" placeholder="请输入内容"/>
        </el-form-item>
        <el-form-item label="长评介绍" prop="content"  v-if="data.form.type === '长评'">
          <div style="border: 1px solid #ccc; width: 100%">
            <Toolbar
                style="border-bottom: 1px solid #ccc"
                :editor="editorRef"
                :mode="mode"
            />
            <Editor
                style="height: 500px; overflow-y: hidden;"
                v-model="data.form.content"
                :mode="mode"
                :defaultConfig="editorConfig"
                @onCreated="handleCreated"
            />
          </div>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisible = false">取消</el-button>
          <el-button type="primary" @click="save">保存</el-button>
        </div>
      </template>
    </el-dialog>

    <el-dialog v-model="data.formVisibleLongMore" title="长评" width="50%">
      <div style="line-height: 24px">
        <div class="longComment" v-html="data.dialogLongComment"></div>
      </div>
      <template #footer>
        <div class="dialog-footer">
          <el-button @click="data.formVisibleLongMore = false; data.dialogLongComment = null">关闭</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
  import { reactive} from "vue";
  import request from "@/utils/request";
  import router from "@/router";
  import {ElMessage} from "element-plus";
  import '@wangeditor/editor/dist/css/style.css' // 引入 css
  import {onBeforeUnmount, shallowRef} from "vue";
  import { Editor, Toolbar } from '@wangeditor/editor-for-vue'

  /* wangEditor5 初始化开始 */
  const baseUrl = import.meta.env.VITE_BASE_URL
  const editorRef = shallowRef()  // 编辑器实例，必须用 shallowRef
  const mode = 'default'
  const editorConfig = {
    MENU_CONF: {},
    placeholder: "请输入内容",
  }
  // 图片上传配置
  editorConfig.MENU_CONF['uploadImage'] = {
    server: baseUrl + '/files/wang/upload',
    fieldName: 'file',
    maxFileSize: 2*1024*1024,
    maxNumberOfFiles: 10,

    onError(file, err) {
      const MAX_MB = 2
      if (err.message?.includes('exceeds maximum')) {
        const fileSizeMB = (file.size / 1024 / 1024).toFixed(1)
        ElMessage.error(`文件大小（${fileSizeMB}MB）超过${MAX_MB}MB限制`)
      } else {
        ElMessage.error(`上传失败: ${err.message || '未知错误'}`)
      }
    },

    customInsert(res, insertFn){
      if (res.code === '200'){
        ElMessage.success('上传成功')
        insertFn(res.data)
      } else {
        ElMessage.error('文本上传失败')
      }
    }
  }
  // 组件销毁时，也及时销毁编辑器，否则可能会造成内存泄漏
  onBeforeUnmount(() => {
    const editor = editorRef.value
    if (editor == null) return
    editor.destroy()
  })
  // 记录 editor 实例，重要！
  const handleCreated = (editor) => {
    editorRef.value = editor
  }
  /* wangEditor5 初始化结束 */

  const data = reactive({
    user: JSON.parse(localStorage.getItem('system-user') || '{}'),
    filmId: router.currentRoute.value.query.id,

    film: {},
    form: {},
    RecommendList: [],

    formVisible: false,
    pageNumShort: 1,
    pageSizeShort: 3,
    totalShort: 0,
    commentShortList: [],

    formVisibleLong: false,
    pageNumLong: 1,
    pageSizeLong: 3,
    totalLong: 0,
    commentLongList: [],

    formVisibleLongMore: false,
    dialogLongComment: null,
  })
  const goDetail = (filmId) => {
      location.href = 'filmDetail?id=' + filmId;
  }
  const viewLongComment = (content)=> {
    data.dialogLongComment = content
    data.formVisibleLongMore = true
  }
  const save = ()=> {
    data.form.filmId = data.filmId
    data.form.userId = data.user.id
    request.post('/comment/add', data.form).then(res => {
      console.log(res)
      if (res.code === '200'){
        data.formVisible = false
        ElMessage.success('评论成功')
        loadShortComment()
        loadLongComment()
      } else {
        ElMessage(res.msg)
      }
    })
  }

  const loadShortComment = () => {
    request.get('comment/selectPage', {
      params: {
        pageNum: data.pageNumShort,
        pageSize: data.pageSizeShort,
        filmId: data.filmId,
        type: '短评',
      }
    }).then(res => {
      data.commentShortList = res.data.list
      data.totalShort = res.data.total
    })
  }
  const addComment = (CommentType)=> {
    data.form = { type: CommentType }
    data.formVisible = true
  }

  const loadLongComment = () => {
    request.get('comment/selectPage', {
      params: {
        pageNum: data.pageNumLong,
        pageSize: data.pageSizeLong,
        filmId: data.filmId,
        type: '长评',
      }
    }).then(res => {
      data.commentLongList = res.data.list
      data.totalLong = res.data.total
    })
  }
  loadShortComment()
  loadLongComment()


  request.get('film/selectById/' + data.filmId).then(res => {
    data.film = res.data
  })
  request.get('film/selectRecommend/' + data.filmId).then(res => {
    data.RecommendList = res.data
  })
</script>

<style>
.longComment img{
  width: 100% !important;
}
</style>