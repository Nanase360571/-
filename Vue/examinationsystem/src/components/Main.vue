<template>
  <div id="app">
    <el-container style="height: 500px; border: 1px solid #eee">
  <el-aside width="200px" style="background-color: rgb(238, 241, 246)">
    <el-menu :default-openeds="[]">
      <h1>五邑大学在线考试系统</h1>
      <el-menu-item index="1">
        <template slot="title">
          <router-link  active-class="active" to="/MyClass" >我的班级</router-link>
          </template>
        
      </el-menu-item>
      <el-menu-item index="2">
        <template slot="title">
          <router-link  active-class="active" to="/MyTest" >我的考试</router-link>
          </template>
      </el-menu-item>
      <el-menu-item index="3">
        <template slot="title">
          <router-link  active-class="active" to="/MyNotice" >我的通知</router-link>
          </template>
      </el-menu-item>
        <el-menu-item index="4">
        <template slot="title">
          <router-link  active-class="active" to="/MyPersonalCenter" >个人中心</router-link>
          </template>
      </el-menu-item>
    </el-menu>
  </el-aside>
  
  <el-container >
    <el-header style="text-align: right; font-size: 12px ">
      <el-dropdown>
        <i class="el-icon-setting" style="margin-right: 15px"></i>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item>查看</el-dropdown-item>
          <el-dropdown-item>新增</el-dropdown-item>
          <el-dropdown-item>删除</el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      <span>王小虎</span>
    </el-header>
    
    <el-main style="height: 1000px">
      <!-- 指定组件的呈现位置 -->
            <router-view></router-view>
    </el-main>
  </el-container>
</el-container>
  </div>
</template>
<script>
export default {
  name:'Main',
  components:{}
}
</script>
<style>

</style>
