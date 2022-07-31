<template>
  <div id="app">
    <el-container style="height: 500px; border: 1px solid #eee">
  <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
    <el-menu :default-openeds="[]">
      <h1>五邑大学在线考试系统</h1>
      <el-menu-item index="1">
        <template slot="title">
          <router-link  active-class="active" style="text-decoration-line: none" to="/StudentMain/MyExam" >我的考试</router-link>
          </template>
        
      </el-menu-item>
      <el-menu-item index="2">
        <template slot="title">
          <router-link  active-class="active" style="text-decoration-line: none" to="/StudentMain/MyTest" >我的练习</router-link>
          </template>
      </el-menu-item>

    </el-menu>
  </el-aside>
  
  <el-container>
    <el-header style="text-align: right; font-size: 15px " >

      <el-button style="float: right; padding: 3px 0 ; font-size: 18px; color: black" type="text" @click="open" >{{ $store.state.student.name }}</el-button>
    </el-header>
<HR/>
    <el-main>
      <!-- 指定组件的呈现位置 -->
            <router-view name="exam"></router-view>
            <router-view name="test"></router-view>
    </el-main>
  </el-container>
</el-container>
  </div>
</template>
<script>
import axios from "axios";
// axios.defaults.baseURL="http://47.101.133.168:10086"

export default {
  name: 'StudentMain',
  components: {},
  data() {
    return {
      name: this.$store.state.student.name
    }
  },
  methods: {
    open() {
      this.$prompt('请输入新的密码', '修改密码', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({value}) => {
        axios.post('http://47.101.133.168:10086/updatePassword', {
          "account": this.$store.state.student.account,
          "password": value

        }, {
          headers: {
            Authorization: sessionStorage.getItem("Authorization")
          }
        }).then(
            response => {
              console.log(response)
              if (response.data.code === 200) {
                sessionStorage.setItem("Authorization", response.data.data)
              }
              if (response.data.code === 10004) {
                alert(response.data.msg)
              }
            },
            error => {
              alert("500,登录失败")
            }
        )
        // this.$message({
        //   type: 'success',
        //   message: '你的邮箱是: ' + value
        // });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    }
  },
  mounted() {
    console.log(this.$store.state.student.name)
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

</script>
<style>

</style>
