<template>
  <div id="app">
    <el-container style="height: 1000px; border: 1px solid #eee">
      <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
        <el-menu :default-openeds="[]">
          <h1>五邑大学在线考试系统</h1>
          <el-menu-item index="1">
            <template slot="title">
              <router-link  active-class="active" style="text-decoration-line: none" to="/AdminMain/AdminCourse" >课程</router-link>
            </template>

          </el-menu-item>
          <el-menu-item index="2">
            <template slot="title">
              <router-link  active-class="active" style="text-decoration-line: none" to="/AdminMain/AdminTeacher" >教师</router-link>
            </template>
          </el-menu-item>
          <el-menu-item index="3">
            <template slot="title">
              <router-link  active-class="active" style="text-decoration-line: none" to="/AdminMain/AdminMajor" >专业</router-link>
            </template>
          </el-menu-item>
          <el-menu-item index="4">
            <template slot="title">
              <router-link  active-class="active" style="text-decoration-line: none" to="/AdminMain/AdminClasses" >班级</router-link>
            </template>
          </el-menu-item>
          <el-menu-item index="5">
            <template slot="title">
              <router-link  active-class="active" style="text-decoration-line: none" to="/AdminMain/AdminMajorCourseTeacher" >专业-课程-教师</router-link>
            </template>
          </el-menu-item>

        </el-menu>
      </el-aside>

      <el-container>
        <el-header style="text-align: right; font-size: 5px " >

          <el-button style="float: right; padding: 3px 0 ; font-size: 12px; color: black" type="text" @click="open" >更改密码</el-button>

          <el-descriptions title="">
            <el-descriptions-item label="用户名" >{{ name }}</el-descriptions-item>
            <el-descriptions-item label="学校">
              <el-tag size="small">五邑大学</el-tag>
            </el-descriptions-item>
          </el-descriptions>        </el-header>
        <HR/>
        <el-main>
          <!-- 指定组件的呈现位置 -->
          <router-view name="AdminCourse"></router-view>
          <router-view name="AdminTeacher"></router-view>
          <router-view name="AdminMajor"></router-view>
          <router-view name="AdminClasses"></router-view>
          <router-view name="FoundPaper"></router-view>
          <router-view name="AdminMajorCourseTeacher"></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
import axios from "axios";
// axios.defaults.baseURL="http://47.101.133.168:10087"

export default {
  name:'TeacherMain',
  components:{},
  data(){
    return{
      name: this.$store.state.teacher.name
    }
  },
  methods: {
    open() {
      this.$prompt('请输入新的密码', '修改密码', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({ value }) => {
        axios.post('http://47.101.133.168:10088/updatePassword',{
          "account": this.$store.state.teacher.account,
          "password": value

        },{
          headers:{
            Authorization:sessionStorage.getItem("Authorization")
          }
        }).then(
            response =>{
              console.log(response)
              if(response.data.code === 200){
                sessionStorage.setItem("Authorization",response.data.data)
              }if(response.data.code === 10004){
                alert(response.data.msg)
              }if(response.data.code === 10003){
                alert(response.data.msg)
              }
            }
        )

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    },
    created() {
      // 在页面加载时读取sessionStorage里的状态信息
      if (sessionStorage.getItem('store')) {
        this.$store.replaceState(
            Object.assign(
                {},
                this.$store.state,
                JSON.parse(sessionStorage.getItem('store'))
            )
        )
      }

      // 在页面刷新时将vuex里的信息保存到sessionStorage里
      // beforeunload事件在页面刷新时先触发
      window.addEventListener('beforeunload', () => {
        sessionStorage.setItem('store', JSON.stringify(this.$store.state))
      })
    }

  }
}
</script>
<style>

</style>
