<template>
  <div>
    <el-table :data="paperList">
      <el-table-column
          label="课程名称"
          prop="papName">
      </el-table-column>
      <el-table-column
          label="开考时间"
          prop="papStart">
      </el-table-column>
      <el-table-column
          label="结束时间"
          prop="papEnd">
      </el-table-column>
      <el-table-column>
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="analyse(scope.row)">成绩分析</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>


<script>
import axios from "axios";
export default {
  name:"TeacherCourse",
  data() {
    return {
      paperList:[],
    };
  },
  methods: {
    analyse(row){

      var end = new Date(Date.parse(row.papEnd.replace("-", "/")));
      var start = new Date(Date.parse(row.papStart.replace("-", "/")));
      var now = new Date(); //取今天的日期

      if(now < start)
      {
        alert("考试还未考试")
      }if(now > start && now <end)
      {
        alert("考试还没结束")
      }if(now > end )
      {
        alert("考试结束,可以查看")
      }

    }
  },
  mounted() {
    axios.get("http://localhost:10087/getAllPaperList",{
      params:{
        teacherId:this.$store.state.teacher.id
      },headers:{
        Authorization: sessionStorage.getItem("Authorization")
      }
    }).then(
        response =>{
          this.paperList = response.data.data
        }
    )
  },

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
