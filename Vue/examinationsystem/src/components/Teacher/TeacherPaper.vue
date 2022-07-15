<template>

  <div>
    
    <el-table :data="paperList">
      <el-table-column
          label="试卷名称"
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


        <el-dialog
        title=""
        :visible.sync="dialogVisible"
        width="100%"
        heigh="80%"
        :fullscreen="true"
        :close-on-click-modal="false"
        :close-on-press-escape="false"
        :append-to-body="true"
        :destroy-on-close="true"
        :show-close="false"
        >
        <div>
          <el-select
            v-model="value"
            placeholder="请选择课程"
            @change="selectEvent"
          >
            <el-option
              v-for="item in classesList"
              :key="item.claNo"
              :label="item.claNo"
              :value="item.claNo"
            ></el-option>
          </el-select>
          
    </div>
      <el-table
        :data="finalList"
        border height="550" 
        style="width:100%"
      >
        <el-table-column v-for='(item,i) in numberList' :key="i" :label="item" show-overflow-tooltip class-name="leave-alone">
        <template slot-scope="scope">
          {{scope.row[i]}}
        </template>
      </el-table-column>

      </el-table>
                
          <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = exitAnalyse()">取 消</el-button>
            <el-button type="primary" @click="dialogVisible = exitAnalyse()">确 定</el-button>
          </span>
        </el-dialog>

  </div>
</template>


<script>
import axios from "axios";
export default {
  name:"TeacherCourse",
  data() {
    return {
      currentFound:1,
      finalList:[],
      numberList:[],
      value:"",
      paperList:[],
      dialogVisible:false,
      classesList:[

        ],
      analysisList:[],
      currentPaperId:"",
      paperData:{},

    };
  },
  methods: {
    exitAnalyse(){
      this.dialogVisible = false
      this.value = ''
      this.finalList = []
      this.numberList = []
    },
    selectEvent(val){
        console.log(this.currentPaperId);
        if(this.currentFound === 1){
        axios.post("http://localhost:10087/analyseByKnowledge",{
        classNo: val,
        paperId: this.currentPaperId,
        teacherId:this.$store.state.teacher.id
      },{
          headers:{
            Authorization: sessionStorage.getItem("Authorization")
          }
        }).then(
          response =>{
            if(response.data.code === 200){
              this.analysisList = response.data.data.list
              this.paperData = response.data.data
              this.numberList = response.data.data.number
              this.finalList = response.data.data.final
            }
          }
      )
        }
        if(this.currentFound === 2){
        axios.post("http://localhost:10087/analyseByTarget",{
        classNo: val,
        paperId: this.currentPaperId,
        teacherId:this.$store.state.teacher.id
      },{
          headers:{
            Authorization: sessionStorage.getItem("Authorization")
          }
        }).then(
          response =>{
            if(response.data.code === 200){
              this.analysisList = response.data.data.list
              this.paperData = response.data.data
              this.numberList = response.data.data.number
              this.finalList = response.data.data.final
            }
          }
      )
        }
        if(this.currentFound === 3){
        axios.post("http://localhost:10087/analyseByRandom",{
        classNo: val,
        paperId: this.currentPaperId,
        teacherId:this.$store.state.teacher.id
      },{
          headers:{
            Authorization: sessionStorage.getItem("Authorization")
          }
        }).then(
          response =>{
            if(response.data.code === 200){
              this.numberList = response.data.data.headList
              this.finalList = response.data.data.listResult
            }
          }
      )
        }
        if(this.currentFound === 4){
        axios.post("http://localhost:10087/analyseByRandom",{
        classNo: val,
        paperId: this.currentPaperId,
        teacherId:this.$store.state.teacher.id
      },{
          headers:{
            Authorization: sessionStorage.getItem("Authorization")
          }
        }).then(
          response =>{
            if(response.data.code === 200){
              this.numberList = response.data.data.headList
              this.finalList = response.data.data.listResult
            }
          }
      )
        }
    },
    analyse(row){
      var end = new Date(Date.parse(row.papEnd.replace("-", "/")));
      var start = new Date(Date.parse(row.papStart.replace("-", "/")));
      var now = new Date(); //取今天的日期
      this.currentPaperId = row.id
      this.dialogVisible = true


      // if(now < start)
      // {
      //   alert("考试还未考试")
      // }if(now > start && now <end)
      // {
      //   alert("考试还没结束")
      //   // eslint-disable-next-line no-constant-condition
      // }if(1 === 1 )
      {
        //alert("考试结束,可以查看")
            
            this.currentFound = row.papFound
            axios.get("http://localhost:10087/getAllClasses",{
              params:{
                teacherId:this.$store.state.teacher.id,
                paperId:row.id
              },headers:{
                Authorization: sessionStorage.getItem("Authorization")
              }
            }).then(
                response =>{
                  this.classesList = response.data.data
                  console.log(this.classesList);
                  this.dialogVisible = true
                }
            )
        
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
.el-table .cell {
  white-space: pre-wrap;
}
</style>
