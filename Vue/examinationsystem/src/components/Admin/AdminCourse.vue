<template>
    <el-form :inline="true" :model="formData" class="demo-form-inline">
  <el-form-item label="课程">
    <el-input v-model="formData.subject" ></el-input>
  </el-form-item>
  <el-form-item label="课程编号">
    <el-input v-model="formData.number" ></el-input>
  </el-form-item>
  <el-form-item>
    <el-button type="primary" @click="onSubmitCourse">
      添加
    </el-button>
  </el-form-item>
    
</el-form>
</template>

<script>
import axios from "axios";

export default {
  name:"MyTest",
  data() {
    return {
      formData:{
        subject:"",
        number:""
      }

    };
  },
  methods:{
    onSubmitCourse(){
      if(this.formData.subject === "" || this.formData.number === ""){
        alert("课程编号和课程不能为空")
      }else{
        axios.post('http://localhost:10088/addAdminCourse',{
                "couSubject": this.formData.subject,
                "couNumber": this.formData.number

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
