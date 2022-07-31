<template>
  <div>
    <el-table :data="courseList">
      <el-table-column
          label="课程名称"
          prop="couSubject">
      </el-table-column>
      <el-table-column>
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="startTest(scope.row)">开启练习</el-button>
          <el-button
              size="mini"
              @click="endTest(scope.row)">关闭练习</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from "axios";
// axios.defaults.baseURL="http://47.101.133.168:10087"

export default {
  name:"TeacherCourse",
  data() {
    return {
      courseList:[]
    };
  },
  methods:{
    startTest(row){
    axios.get("http://47.101.133.168:10087/startTest",{
      params:{
        id:row.id,
        couSubject:row.couSubject,
        couNumber:row.couNumber,
      },headers:{
        Authorization: sessionStorage.getItem("Authorization")
      }
    }).then(
        response =>{
          if(response.data.code === 200){
            alert("开启成功")
          }
        }
    )
    },
    endTest(row){
    axios.get("http://47.101.133.168:10087/endTest",{
      params:{
        id:row.id,
        couSubject:row.couSubject,
        couNumber:row.couNumber,
      },headers:{
        Authorization: sessionStorage.getItem("Authorization")
      }
    }).then(
        response =>{
          if(response.data.code === 200){
            alert("关闭成功")
          }
        }
    )
    },
  },
  mounted() {
    axios.get("http://47.101.133.168:10087/getAllCourse",{
      params:{
        teacherId:this.$store.state.teacher.id
      },headers:{
        Authorization: sessionStorage.getItem("Authorization")
      }
    }).then(
        response =>{
          this.courseList = response.data.data
        }
    )
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
