<template>
    <div>
  <el-form :inline="true" :model="formData" class="demo-form-inline">
  <el-form-item label="编号">
    <el-input v-model="formData.account" ></el-input>
  </el-form-item>
  <el-form-item label="密码">
    <el-input v-model="formData.password" ></el-input>
  </el-form-item>
  <el-form-item label="姓名">
    <el-input v-model="formData.name" ></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onSubmitTeacher">
      添加
    </el-button>
  </el-form-item>
    
</el-form>
    </div>
</template>

<script>
import axios from "axios";

export default {
  name:"MyTest",
  data() {
    return {
        formData:{
        account:"",
        password:"",
        name:""
      }

    };
  },
  methods:{
    onSubmitTeacher(){
      if(this.formData.account === "" || this.formData.number === "" || this.formData.name === ""){
        alert("编号和密码和姓名不能为空")
      }else{
        axios.post('http://localhost:10088/addAdminTeacher',{
                "account": this.formData.account,
                "password": this.formData.password,
                "name":this.formData.name

              },{
                headers:{
                  Authorization:sessionStorage.getItem("Authorization")
                }
              }).then(
                  response => {
                    if(response.data.code === 200){
                      alert("添加成功")
                    }else{
                      alert("添加失败")
                    }
                  }
              )
      }
    }
  },
  mounted() {
}
}
</script>
<style>
.time {
  font-size: 13px;
  color: #999;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.button {
  padding: 0;
  float: right;
}

.image {
  width: 100%;
  display: block;
}

.clearfix:before,
.clearfix:after {
  display: table;
  content: "";
}

.clearfix:after {
  clear: both
}
</style>
