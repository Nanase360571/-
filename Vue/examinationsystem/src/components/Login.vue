<template>
  <div class="login">
    <el-form class="form" :rules="rules" :model="form" ref="form">
      <h3>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;wyu在线考试登录界面</h3>
      <el-form-item label="账号" label-width="80px" prop="account">
        <el-input class="item" v-model="form.account" placeholder="请输入账号"></el-input>
      </el-form-item>
      <el-form-item label="密码" label-width="80px" prop="password">
        <el-input
          class="item"
          type="password"
          v-model="form.password"
          placeholder="请输入密码"
        ></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmitStudent" size="medium" class="myButton">学生登录</el-button>
        <el-button type="primary" @click="onSubmitTeacher" size="medium" class="myButton">教师登录</el-button>
        <el-button type="primary" @click="onSubmitAdmin" size="medium" class="myButton">管理员登录</el-button>

      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import axios from 'axios';
export default {

name:'Login',
data() {
    return {
      form: {
        account: '3118002201',
        password: '123456'
      },
      rules: {
        account: [
          { required: true, message: "请输入账号", trigger: "blur" },
          { min: 3, max: 10, message: "长度在 3 到 5 个字符", trigger: "blur" }
        ],
        password: [
          { required: true, message: "请输入密码", trigger: "blur" },
          { min: 6, max: 12, message: "长度在 6 到 12 个字符", trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    onSubmitStudent(){
      axios.post('http://localhost:10086/loginStudent',{
        "account": this.form.account,
        "password": this.form.password

      },{
        headers:{
          Authorization:sessionStorage.getItem("Authorization")
        }
      }).then(
        response =>{
          console.log(response)
          if(response.data.code === 200){
            sessionStorage.setItem("Authorization",response.data.data)
            /*
            * 请求学生信息，并存储到store中
            * */
            axios.post('http://localhost:10086/getStudentMsg',{
              "account": this.form.account,
              "password": this.form.password

            },{
              headers:{
                Authorization:sessionStorage.getItem("Authorization")
              }
            }).then(
                response => {
                  this.$store.state.student = response.data.data
                  console.log("this.$store.state.student"+this.$store.state.student.name)
                  this.$router.push("/StudentMain");
                }
            )
          }if(response.data.code === 10000){
            alert(response.data.msg)
          }if(response.data.code === 10001){
            alert(response.data.msg)
          }
          if(response.data.code === 10003){
            alert(response.data.msg)
          }

        },
        error => {
          alert("500,登录失败")
        }
      )
    },
    onSubmitTeacher(){
      axios.post('http://localhost:10087/loginTeacher',{
        "account": this.form.account,
        "password": this.form.password

      },{
        headers:{
          Authorization:sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            console.log(response)
            if(response.data.code === 200){
              sessionStorage.setItem("Authorization",response.data.data)
              /*
              * 请求学生信息，并存储到store中
              * */
              axios.post('http://localhost:10087/getTeacherMsg',{
                "account": this.form.account,
                "password": this.form.password

              },{
                headers:{
                  Authorization:sessionStorage.getItem("Authorization")
                }
              }).then(
                  response => {
                    this.$store.state.teacher = response.data.data
                    console.log("this.$store.state.teacher"+this.$store.state.teacher.name)
                    this.$router.push("/TeacherMain");
                  }
              )

            }if(response.data.code === 10000){
              alert(response.data.msg)
            }if(response.data.code === 10001){
              alert(response.data.msg)
            }
            if(response.data.code === 10003){
              alert(response.data.msg)
            }

          },
          error => {
            alert("500,登录失败")
          }
      )
    },
    onSubmitAdmin(){
            axios.post('http://localhost:10088/loginAdmin',{
        "account": this.form.account,
        "password": this.form.password

      },{
        headers:{
          Authorization:sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            console.log(response)
            if(response.data.code === 200){
              sessionStorage.setItem("Authorization",response.data.data)
              /*
              * 请求管理员信息，并存储到store中
              * */
              axios.post('http://localhost:10088/getAdminMsg',{
                "account": this.form.account,
                "password": this.form.password

              },{
                headers:{
                  Authorization:sessionStorage.getItem("Authorization")
                }
              }).then(
                  response => {
                    this.$store.state.admin = response.data.data
                    console.log("this.$store.state.teacher"+this.$store.state.admin.account)
                    this.$router.push("/AdminMain");
                  }
              )

            }if(response.data.code === 10000){
              alert(response.data.msg)
            }if(response.data.code === 10001){
              alert(response.data.msg)
            }
            if(response.data.code === 10003){
              alert(response.data.msg)
            }

          },
          error => {
            alert("500,登录失败")
          }
      )

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
    },
    beforeDestroy(){
      console.log("login is destoryed")
    }

  }
};
</script>

<style scoped>
.login {
  background-color: #bcdef3;
  background-image: url("./back.jpg");
  background-size: 1000px;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
}
.form {
  width: 40%;
  margin-bottom: 20vh;
  background-color: #eff6f5;
  border-radius: 2px;
  padding: 5% 3%;
}
.item {
  width: 75%;
}
.h3{
  margin-left: 30%;
  background-color: aquamarine;
  text-align: center;
}
.myButton{
    margin-left: 18%;

}
</style>