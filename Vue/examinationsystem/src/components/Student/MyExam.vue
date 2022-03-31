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
              @click="goTest(scope.row)">开始考试</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
        title=""
        :visible.sync="examDialogVisible"
        width="100%"
        heigh="100%"
        :fullscreen="true"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        :append-to-body="true"
        :destroy-on-close="true"
        :show-close="false"
        
    >
      <Header2 :examDialogVisible="examDialogVisible" @CDis="changExamDialogVisible" 
        :singleChoiceArrayList="singleChoiceArrayList"
        :multipleChoiceArrayList="multipleChoiceArrayList"
        :tfArrayList="tfArrayList"
        :studentName="$store.state.student.name"
        :singlePoint="singlePoint"
        :multiPoint="multiPoint"
        :judgePoint="judgePoint"
        :fillTime="fillTime"
        :papName="papName"
        :paperId="paperId"
      ></Header2>

    </el-dialog>
  </div>

</template>

<script>
import axios from "axios";
import Header2 from "@/components/Student/Header2";

export default {
  name:"MyTest",
  components: {Header2},
  data() {
    return {
      paperList:[],
      examDialogVisible:false,
      singleChoiceArrayList: [],
      multipleChoiceArrayList: [],
      tfArrayList: [],
      dbVoList:{},
      papName:"",
      fillTime:"",
      judgePoint:"",
      multiPoint:"",
      singlePoint:"",
      paperId:""

    };
  },
  methods:{
    changExamDialogVisible(value){
      console.log(value)
      this.examDialogVisible = value
    },
    goTest(row){
      console.log(row.id)
      var end = new Date(Date.parse(row.papEnd.replace("-", "/")));
      var start = new Date(Date.parse(row.papStart.replace("-", "/")));
      var now = new Date(); //取今天的日期

      if(now < start)
      {
        alert("考试还未考试")
      }if(now > start && now <end)
      {
        alert("可以开始考试")
        this.papName = row.papName
        this.paperId = row.id
        axios.get('http://localhost:10086/paper/getPaperDb',{
          params:{
            'studentId':this.$store.state.student.id,
            'paperId':row.id,
          }
        }).then(

            response =>{
              this.examDialogVisible = true
              console.log(response.data)
              console.log(response.data.code)

              if(response.data.code === 200){
                console.log(response.data.data.singleChoiceArrayList)
                this.dbVoList = response.data.data.dbVoList
                this.singleChoiceArrayList = response.data.data.dbVoList.singleChoiceArrayList
                this.multipleChoiceArrayList = response.data.data.dbVoList.multipleChoiceArrayList
                this.tfArrayList = response.data.data.dbVoList.tfArrayList
                this.singlePoint=response.data.data.singlePoint
                this.multiPoint=response.data.data.multiPoint
                this.judgePoint=response.data.data.judgePoint
                this.fillTime=response.data.data.fillTime
              }
            }
        )
      }if(now > end )
      {
        alert("考试已经结束")
      }

    }
  },
  mounted() {
    console.log("MyExam")
    axios.get('http://localhost:10086/paper/getPaperSummary',{
      params:{
        'studentId':this.$store.state.student.id
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
