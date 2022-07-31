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
              @click="randomTest(scope.row)">开始练习</el-button>
          
        </template>
      </el-table-column>
    </el-table>

          <el-dialog
        title=""
        :visible.sync="testDialogVisible"
        width="100%"
        heigh="100%"
        :fullscreen="true"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        :append-to-body="true"
        :destroy-on-close="true"
        :show-close="false"
        
    >
    <div v-if="testDialogVisible">
<TestPage :examDialogVisible="testDialogVisible" @CDis="changTestDialogVisible" 
       :singleChoiceArrayList="singleChoiceArrayList"
       :multipleChoiceArrayList="multipleChoiceArrayList"
       :tfArrayList="tfArrayList"
        :studentName="$store.state.student.name"
        :singlePoint="2"
        :multiPoint="2"
        :judgePoint="2"
        :papName="2"
        :paperId="1"
        :courseId="courseId"
      ></TestPage>
    </div>
      

    </el-dialog>


    </div>
</template>

<script>
import axios from "axios";
import TestPage from "@/components/Student/TestPage";
// axios.defaults.baseURL="http://47.101.133.168:10086"

export default {
  name:"MyTest",
  components:{TestPage},
  data() {
    return {
      courseList:[],
      testDialogVisible:false,
      singleChoiceArrayList:[],
      multipleChoiceArrayList:[],
      tfArrayList:[],
      courseId:0

    };
  },
  methods:{
    changTestDialogVisible(value){
      console.log(value)
      this.testDialogVisible = value
      this.singleChoiceArrayList = []
      this.multipleChoiceArrayList =[]
      this.tfArrayList = []
    },
    randomTest(row){
      this.courseId=row.id
        this.getData(row)
    },
    getData(row){
      alert("可以进入考试")
      this.testDialogVisible = true

      axios.get('http://47.101.133.168:10086/randomTest',{
      params:{
        'id':row.id
      }
    }).then(
         response =>{
            this.courseList = response.data.data
            this.singleChoiceArrayList = response.data.data.singleChoiceArrayList
            this.multipleChoiceArrayList = response.data.data.multipleChoiceArrayList
            this.tfArrayList = response.data.data.tfArrayList
            this.testDialogVisible = true
         }
    )
    },
  },
  mounted() {
    
    axios.get('http://47.101.133.168:10086/getCourseTest',{
      params:{
        'studentId':this.$store.state.student.id
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
