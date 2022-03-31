<template>
  <div>
    <el-container>
    <el-aside width="20%">
    <!-- template中 -->
    <div  class='image'>
      <img alt="logo" :src='imageLocation' />
    </div>
    <div class="button">更改头像</div>

    </el-aside>
    <el-main>


<el-form label-position="left" label-width="80px" >
  <el-form-item label="用户ID">
    <el-input></el-input>
  </el-form-item>
  <el-form-item label="用户名称">
    <el-input ></el-input>
  </el-form-item>
  <el-form-item label="联系方式">
    <el-input></el-input>
  </el-form-item>
   <el-form-item label="用户身份">
    <el-input></el-input>
  </el-form-item>
  <div class="button">保存</div>
</el-form>
    </el-main>
    </el-container>
  </div>
</template>

<script>

export default {
    name:'MyPersonalCenter',
      data() {
      return {
        imageLocation:require('../static/logo.png'),
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        },
         imageUrl: ''
      }
    },
    methods: {
      onSubmit() {
        console.log('submit!');
      },
            handleAvatarSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },


    }
  
}
</script>

<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
  .button{
    text-align: center;
    border: 1px solid #d9d9d9;
  }
  .image{
    display: flex;
    justify-content: center;
    align-items: center;
  }
</style>