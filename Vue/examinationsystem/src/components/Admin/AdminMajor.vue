<template>
    <div>
      <el-form :inline="true" :model="formData" class="demo-form-inline">
      <el-form-item label="专业">
        <el-input v-model="formData.major" ></el-input>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" @click="onSubmitMajor">
          添加
        </el-button>
      </el-form-item>
        
    </el-form>
    </div>
</template>

<script>
import axios from "axios";
axios.defaults.baseURL="http://localhost:10088"

export default {
  name:"MyTest",
  data() {
    return {
        formData:{
          major:""
        }

    };
  },
  methods:{
    onSubmitMajor(){
       if(this.formData.major === "" ){
        alert("专业名称不能为空")
      }else{
        axios.post('addAdminMajor',{
                "major":this.formData.major

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
