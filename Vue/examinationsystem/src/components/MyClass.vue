<template>
<div>
      <el-table
    :data="claData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
    style="width: 100%">
    <el-table-column
      label="创建时间"
      prop="claStartTime">
    </el-table-column>
    <el-table-column
      label="创建人"
      prop="claFounder">
    </el-table-column>
    <el-table-column
      label="科目"
      prop="claSubject">
    </el-table-column>
    <el-table-column
      label="人数"
      prop="claNumber">
    </el-table-column>
     <el-table-column
      label="简介"
      prop="claBrief">
    </el-table-column>
    <el-table-column
      align="right">
      <template slot="header" >
       <el-button class="el-icon-plus" @click="openClass">添加班级</el-button>
      </template>
      <template slot-scope="scope">
        
        <el-button
          size="mini"
          type="danger"
          @click="handleDelete(scope.$index, scope.row)">退出班级</el-button>
      </template>
    </el-table-column>
    
  </el-table>
  <el-pagination
  small
  layout="prev, pager, next"
  :total="50">
</el-pagination>
</div>

</template>

<script>
import axios from 'axios'

  export default {
      name:'MyClass',
    data() {
      return {
        claData:[],
        tableData: [{
          date: '2016-05-02',
          name: '王小虎',
          subject:'语文',
          people:56,
          summary:'我是测试用的简介，我是测试用的简介，我是测试用的简介'
        },{
          date: '2016-05-02',
          name: '王小虎',
          subject:'语文',
          people:56,
          summary:'我是测试用的简介，我是测试用的简介，我是测试用的简介'
        },{
          date: '2016-05-02',
          name: '王小虎',
          subject:'语文',
          people:56,
          summary:'我是测试用的简介，我是测试用的简介，我是测试用的简介'
        },{
          date: '2016-05-02',
          name: '王小虎',
          subject:'语文',
          people:56,
          summary:'我是测试用的简介，我是测试用的简介，我是测试用的简介'
        },{
          date: '2016-05-02',
          name: '王小虎',
          subject:'语文',
          people:56,
          summary:'我是测试用的简介，我是测试用的简介，我是测试用的简介'
        },
        ],
        search: ''
      }
    },
    methods: {
      handleEdit(index, row) {
        console.log(index, row);
      },
      handleDelete(index, row) {
        console.log(index, row);
      },
        openClass() {
        this.$prompt('请输入班级ID', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
          inputErrorMessage: '邮箱格式不正确'
        }).then(({ value }) => {
          this.$message({
            type: 'success',
            message: '你的邮箱是: ' + value
          });
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消输入'
          });       
        });
      }
    },
  mounted(){
    console.log("Myclass")
      axios.get('http://localhost:8848/classList').then(
          response =>{
            console.log('请求成功',response.data)
            console.log(this)
            this.claData=response.data
            console.log(this.claData[0].id)
            },
          error =>{console.log('请求失败',error.message)}
        )
  }
  }
</script>
<style>

</style>