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
              @click="foundByKnowledge(scope.row)">按知识点组卷</el-button><el-button
              size="mini"
              @click="foundByTarget(scope.row)">按课程目标组卷</el-button><el-button
              size="mini"
              @click="foundByRandom(scope.row)">随机组卷</el-button><el-button
              size="mini"
              @click="foundBySelf(scope.row)">自选题目组卷</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
        title="按照知识点组卷"
        :visible.sync="knowledgeVisible"
        width="70%"
        :append-to-body="true"
        :destroy-on-close="true"
    >
      <div>
        <el-form :inline="true"  class="demo-form-inline">
          <el-form-item label="考试名称">
            <el-col >
              <el-input v-model="paperName" placeholder=""></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="总分" label-width="40px" size="50px">
              <el-input v-model="paperSum" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <br>
          <el-form-item label="单选题题量" >
              <el-input v-model="singleNumber" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="多选题题量" >
              <el-input v-model="multiNumber" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="判断题题量" >
              <el-input v-model="judgeNumber" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item><el-form-item label="单选题总分值" >
              <el-input v-model="singleSum" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="多选题总分值" >
              <el-input v-model="multiSum" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="判断题总分值" >
              <el-input v-model="judgeSum" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="考试时间" >
            <el-date-picker
                v-model="value1"
                type="datetimerange"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :default-time="['12:00:00']"
                value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="考试时长(小时)">
                 <el-input-number v-model="examTime" :precision="2" :step="0.1" :max="10" ></el-input-number>
          </el-form-item>
        </el-form>
      </div>

      <div>
        <el-table :data="reallyShowKnowledgeList" >
          <el-table-column
              label="知识点"
              prop="knoContent">
          </el-table-column>

          <el-table-column label="占比(%)">
            <template slot-scope="scope">
              <el-input placeholder="请输入占比"  v-model="scope.row.proportion" ></el-input>
            </template>
          </el-table-column>


        </el-table>
      </div>

      <span slot="footer" class="dialog-footer">
    <el-button @click="knowledgeVisible = false">取 消</el-button>
    <el-button type="primary" @click="confirmFoundByKnowledge()">确 定</el-button>
  </span>
    </el-dialog>

    <el-dialog
        title="按照课程目标组卷"
        :visible.sync="targetVisible"
        width="70%"
        :append-to-body="true"
        :destroy-on-close="true"
    >
      <div>
        <el-form :inline="true"  class="demo-form-inline">
          <el-form-item label="考试名称">
            <el-col >
              <el-input v-model="paperName" placeholder=""></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="总分" label-width="40px" size="50px">
            <el-input v-model="paperSum" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <br>
          <el-form-item label="单选题题量" >
            <el-input v-model="singleNumber" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="多选题题量" >
            <el-input v-model="multiNumber" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="判断题题量" >
            <el-input v-model="judgeNumber" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item><el-form-item label="单选题总分值" >
          <el-input v-model="singleSum" placeholder="" style="size:20px" type="number"></el-input>
        </el-form-item>
          <el-form-item label="多选题总分值" >
            <el-input v-model="multiSum" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="判断题总分值" >
            <el-input v-model="judgeSum" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="考试时间" >
            <el-date-picker
                v-model="value1"
                type="datetimerange"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :default-time="['12:00:00']"
                value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="考试时长(小时)">
                 <el-input-number v-model="examTime" :precision="2" :step="0.1" :max="10" ></el-input-number>
          </el-form-item>
        </el-form>
      </div>

      <div>
        <el-table :data="reallyShowTargetList" >
          <el-table-column
              label="课程目标"
              prop="tarContent">
          </el-table-column>

          <el-table-column label="占比(%)">
            <template slot-scope="scope">
              <el-input placeholder="请输入占比"  v-model="scope.row.proportion" ></el-input>
            </template>
          </el-table-column>


        </el-table>
      </div>

      <span slot="footer" class="dialog-footer">
    <el-button @click="targetVisible = false">取 消</el-button>
    <el-button type="primary" @click="confirmFoundByTarget()">确 定</el-button>
  </span>
    </el-dialog>

    <el-dialog
        title="随机组卷"
        :visible.sync="randomVisible"
        width="70%"
        :append-to-body="true"
        :destroy-on-close="true"
    >
      <div>
        <el-form :inline="true"  class="demo-form-inline">
          <el-form-item label="考试名称">
            <el-col >
              <el-input v-model="paperName" placeholder=""></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="总分" label-width="40px" size="50px">
            <el-input v-model="paperSum" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <br>
          <el-form-item label="单选题题量" >
            <el-input v-model="singleNumber" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="多选题题量" >
            <el-input v-model="multiNumber" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="判断题题量" >
            <el-input v-model="judgeNumber" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item><el-form-item label="单选题总分值" >
          <el-input v-model="singleSum" placeholder="" style="size:20px" type="number"></el-input>
        </el-form-item>
          <el-form-item label="多选题总分值" >
            <el-input v-model="multiSum" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="判断题总分值" >
            <el-input v-model="judgeSum" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="考试时间" >
            <el-date-picker
                v-model="value1"
                type="datetimerange"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :default-time="['12:00:00']"
                value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="考试时长(小时)">
                 <el-input-number v-model="examTime" :precision="2" :step="0.1" :max="10" ></el-input-number>
          </el-form-item>
        </el-form>
      </div>

      <span slot="footer" class="dialog-footer">
    <el-button @click="randomVisible = false">取 消</el-button>
    <el-button type="primary" @click="confirmFoundByRandom()">确 定</el-button>
  </span>
    </el-dialog>

    <el-dialog
        title="自行组卷"
        :visible.sync="selfVisible"
        width="70%"
        :append-to-body="true"
        :destroy-on-close="true"
    >
      <div>
        <el-form :inline="true"  class="demo-form-inline">
          <el-form-item label="考试名称">
            <el-col >
              <el-input v-model="paperName" placeholder=""></el-input>
            </el-col>
          </el-form-item>
          <el-form-item label="总分" label-width="40px" size="50px">
            <el-input v-model="paperSum" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <br>
        <el-form-item label="单选题总分值" >
          <el-input v-model="singleSum" placeholder="" style="size:20px" type="number"></el-input>
        </el-form-item>
          <el-form-item label="多选题总分值" >
            <el-input v-model="multiSum" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="判断题总分值" >
            <el-input v-model="judgeSum" placeholder="" style="size:20px" type="number"></el-input>
          </el-form-item>
          <el-form-item label="考试时间" >
            <el-date-picker
                v-model="value1"
                type="datetimerange"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :default-time="['12:00:00']"
                value-format="yyyy-MM-dd HH:mm:ss">
            </el-date-picker>
          </el-form-item>
          <br>
          <el-form-item label="单选题题量" >
            <el-input v-model="selfSingle" placeholder="" style="size:20px" type="number" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="多选题题量" >
            <el-input v-model="selfMulti" placeholder="" style="size:20px" type="number" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="判断题题量" >
            <el-input v-model="selfJudge" placeholder="" style="size:20px" type="number" :disabled="true"></el-input>
          </el-form-item>
          <el-form-item label="考试时长(小时)">
                 <el-input-number v-model="examTime" :precision="2" :step="0.1" :max="10" ></el-input-number>
          </el-form-item>
        </el-form>
      </div>

      <div>
        <el-table :data="reallyShowDbList"
                  ref="multipleTable"
                  tooltip-effect="dark"
                  style="width: 100%"
                  @selection-change="handleSelectionChange">
          <el-table-column
              type="selection"
              width="55">
          </el-table-column>
          <el-table-column
              label="id"
              prop="id"
              width="55">
          </el-table-column>
          <el-table-column
              label="题目"
              prop="dbContent"
              width="700">
          </el-table-column>
          <el-table-column
              label="答案"
              prop="dbAnswer">
          </el-table-column>
          <el-table-column
              label="题目类型"
              prop="dbReallyType"
              :filters="[{text:'单选题',value:'单选题'},{text:'多选题',value:'多选题'},{text:'判断题',value:'判断题'}]"
              :filter-method="filterHandler"
              :filter-multiple="false">
          </el-table-column>



        </el-table>
      </div>

      <span slot="footer" class="dialog-footer">
    <el-button @click="selfVisible = false">取 消</el-button>
    <el-button type="primary" @click="confirmFoundBySelf()">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      examTime:1,
      selfSingle:0,
      selfMulti:0,
      selfJudge:0,
      multipleSelection: [],
      knowledgeList:[
      ],
      targetList:[
      ],
      kno:
          {
            id:"",
            knoContent:"",
            knoCourse:"",
            proportion:""
          },
      reallyShowKnowledgeList:[

      ],
      reallyShowTargetList:[

      ],
      reallyShowDbList:[

      ],
      startTime:"",
      endTime:"",
      paperName:"",
      multiNumber:"",
      singleNumber:"",
      judgeNumber:"",
      multiSum:"",
      singleSum:"",
      judgeSum:"",
      value1:"",
      paperSum:"",
      courseList:[],
      dbList:[],
      currentCourseId:"",
      knowledgeVisible:false,
      targetVisible:false,
      randomVisible:false,
      selfVisible:false,
    };
  },
  methods: {
    filterHandler(value, row, column){
      console.log(row)
      return row.dbReallyType === value
    },
    handleSelectionChange(val){
      let single = 0
      let multi = 0
      let judge = 0

      this.multipleSelection = val
      for(let i in this.multipleSelection)
      {
        if(this.multipleSelection[i].dbType === 1)
        {
          single++
        }if(this.multipleSelection[i].dbType === 2)
        {
          multi++
        }if(this.multipleSelection[i].dbType === 3)
        {
          judge++
        }
      }
      this.selfSingle = single
      this.selfMulti = multi
      this.selfJudge = judge
      console.log(this.multipleSelection)

    },
    confirmFoundByKnowledge(){
      let tag = 0
      let sum = 0
      for(let index in this.reallyShowKnowledgeList)
      {
        if(this.reallyShowKnowledgeList[index].proportion === "")
        {continue}
        sum = sum + parseInt(this.reallyShowKnowledgeList[index].proportion)
      }
      if(sum!=100)
      {
        tag = 1
        alert("知识点总占比需要达到100%")
      }
      if(this.paperName === "" ||this.paperSum === "" ||this.singleNumber === "" ||this.multiNumber === "" ||this.judgeNumber === "" ||
          this.value1 === ""
      )
      {
        tag = 1
        alert("请将信息补充完整")
      }
      if((parseInt(this.singleSum)+parseInt(this.multiSum)+parseInt(this.judgeSum)) != parseInt(this.paperSum))
      {
        tag = 1
        alert("每种题型总分之和与设定试卷卷面分不等")

      }

      if(tag === 0){

        let params = this.reallyShowKnowledgeList
        for(let  index in params){
          if(params[index].proportion === null || params[index].proportion === ""){
              params[index].proportion = 0
          }
        }

        axios.post("http://localhost:10087/FoundByKnowledge",{
          teacherId:this.$store.state.teacher.id,
          courseId:this.currentCourseId,
          paperName:this.paperName,
          paperSum:this.paperSum,
          startTime:this.value1[0],
          endTime:this.value1[1],
          multiNumber:this.multiNumber,
          singleNumber:this.singleNumber,
          judgeNumber:this.judgeNumber,
          multiSum:this.multiSum,
          singleSum:this.singleSum,
          judgeSum:this.judgeSum,
          knowledgeList:params,
          papFound:1,
          examTime:this.examTime,

        },{
          headers:{
            Authorization: sessionStorage.getItem("Authorization")
          }
        }).then(
            response =>{
              if(response.data.code != 200){
                alert(response.data.msg)
              }else {
                this.$message("组卷成功")
                this.knowledgeVisible = false
              }

            }
        )
      }

    },
    confirmFoundByTarget(){
      console.log("confirmFoundByTarget")
      let tag = 0
      let sum = 0
      for(let index in this.reallyShowTargetList)
      {
        if(this.reallyShowTargetList[index].proportion === "")
        {continue}
        sum = sum + parseInt(this.reallyShowTargetList[index].proportion)
      }
      if(sum!=100)
      {
        tag = 1
        alert("知识点总占比需要达到100%")
      }
      if(this.paperName === "" ||this.paperSum === "" ||this.singleNumber === "" ||this.multiNumber === "" ||this.judgeNumber === "" ||
          this.value1 === ""
      )
      {
        tag = 1
        alert("请将信息补充完整")
      }
      if((parseInt(this.singleSum)+parseInt(this.multiSum)+parseInt(this.judgeSum)) != parseInt(this.paperSum))
      {
        tag = 1
        alert("每种题型总分之和与设定试卷卷面分不等")

      }
      if(tag === 0)
      {
        axios.post("http://localhost:10087/FoundByTarget",{
          teacherId:this.$store.state.teacher.id,
          courseId:this.currentCourseId,
          paperName:this.paperName,
          paperSum:this.paperSum,
          startTime:this.value1[0],
          endTime:this.value1[1],
          multiNumber:this.multiNumber,
          singleNumber:this.singleNumber,
          judgeNumber:this.judgeNumber,
          multiSum:this.multiSum,
          singleSum:this.singleSum,
          judgeSum:this.judgeSum,
          targetList:this.reallyShowTargetList,
          papFound:2,
          examTime:this.examTime,

        },{
          headers:{
            Authorization: sessionStorage.getItem("Authorization")
          }
        }).then(
            response =>{
              if(response.data.code === 200){
                this.$message("组卷成功")
                this.targetVisible = false

              }else {
                alert(response.data.data)
              }

            }
        )
      }

    },
    confirmFoundByRandom(){
      let tag = 0
      let sum = 0
      console.log("confirmFoundByRandom")
      if(this.paperName === "" ||this.paperSum === "" ||this.singleNumber === "" ||this.multiNumber === "" ||this.judgeNumber === "" ||
          this.value1 === ""
      )
      {
        tag = 1
        alert("请将信息补充完整")
      }
      if((parseInt(this.singleSum)+parseInt(this.multiSum)+parseInt(this.judgeSum)) != parseInt(this.paperSum))
      {
        tag = 1
        alert("每种题型总分之和与设定试卷卷面分不等")

      }
      if(tag  === 0)
      {
        axios.post("http://localhost:10087/FoundByRandom",{
          teacherId:this.$store.state.teacher.id,
          courseId:this.currentCourseId,
          paperName:this.paperName,
          paperSum:this.paperSum,
          startTime:this.value1[0],
          endTime:this.value1[1],
          multiNumber:this.multiNumber,
          singleNumber:this.singleNumber,
          judgeNumber:this.judgeNumber,
          multiSum:this.multiSum,
          singleSum:this.singleSum,
          judgeSum:this.judgeSum,
          papFound:3,
          examTime:this.examTime,

        },{
          headers:{
            Authorization: sessionStorage.getItem("Authorization")
          }
        }).then(
            response =>{
              if(response.data.code === 200){
                this.$message("组卷成功")
                this.randomVisible = false

              }else {
                alert(response.data.data)
              }

            }
        )
      }
    },
    confirmFoundBySelf(){
      let tag = 0
      let sum = 0
      console.log("confirmFoundByRandom")
      if(this.paperName === "" ||this.paperSum === "" ||this.selfJudge === "" ||this.selfMulti === "" ||this.selfSingle === "" ||
          this.value1 === "")

      {
        console.log(this.paperName+"this.paperName")
        console.log(this.paperSum)
        console.log(this.selfSingle)
        console.log(this.selfMulti)
        console.log(this.selfJudge)
        console.log(this.value1)
        tag = 1
        alert("请将信息补充完整")
      }
      if((parseInt(this.singleSum)+parseInt(this.multiSum)+parseInt(this.judgeSum)) != parseInt(this.paperSum))
      {
        tag = 1
        alert("每种题型总分之和与设定试卷卷面分不等")

      }
      if(tag  === 0)
      {
        axios.post("http://localhost:10087/FoundBySelf",{
          teacherId:this.$store.state.teacher.id,
          courseId:this.currentCourseId,
          paperName:this.paperName,
          paperSum:this.paperSum,
          startTime:this.value1[0],
          endTime:this.value1[1],
          multiNumber:this.selfMulti,
          singleNumber:this.selfSingle,
          judgeNumber:this.selfJudge,
          multiSum:this.multiSum,
          singleSum:this.singleSum,
          judgeSum:this.judgeSum,
          dbList:this.multipleSelection,
          papFound:4,
          examTime:this.examTime,

        },{
          headers:{
            Authorization: sessionStorage.getItem("Authorization")
          }
        }).then(
            response =>{
              if(response.data.code === 200){
                this.$message("组卷成功")
                this.selfVisible = false

              }else {
                alert(response.data.data)
              }

            }
        )
      }
    },
    foundByKnowledge(row){
      console.log("foundByKnowledge"+this.currentCourseId)
      this.currentCourseId = row.id
      axios.get("http://localhost:10087/getKnowledgeList",{
        params:{
          teacherId:this.$store.state.teacher.id,
          courseId:this.currentCourseId,

        },headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            this.knowledgeList = response.data.data
            this.reallyShowKnowledgeList = []
            for(let index in this.knowledgeList)
            {
              this.kno =
              {
                id:"",
                knoContent:"",
                knoCourse:"",
                proportion:""
              }
              this.kno.knoContent = this.knowledgeList[index].knoContent
              this.kno.knoCourse = this.knowledgeList[index].knoCourse
              this.kno.id = this.knowledgeList[index].id
              this.kno.proportion = 0
              this.reallyShowKnowledgeList[index] = this.kno

            }
            console.log(this.reallyShowKnowledgeList+"this.reallyShowKnowledgeList")
            this.knowledgeVisible = true
          }

      )

    },
    foundByTarget(row){
      this.currentCourseId = row.id
      console.log("foundByTarget"+this.currentCourseId)

      axios.get("http://localhost:10087/getAllTargetList",{
        params:{
          teacherId:this.$store.state.teacher.id,
          courseId:this.currentCourseId,

        },headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            this.targetList = response.data.data
            this.reallyShowTargetList = []
            for(let index in this.targetList)
            {
              this.kno =
                  {
                    id:"",
                    tarContent:"",
                    proportion:""
                  }
              this.kno.tarContent = this.targetList[index].tarContent
              this.kno.id = this.targetList[index].id
              this.kno.proportion = 0
              this.reallyShowTargetList[index] = this.kno

            }
            console.log(this.reallyShowTargetList+"this.reallyShowKnowledgeList")
            this.targetVisible = true
          }

      )
    },
    foundByRandom(row){
      this.currentCourseId = row.id
      console.log("foundByRandom"+this.currentCourseId)
      this.randomVisible = true
    },
    foundBySelf(row){
      this.currentCourseId = row.id
      console.log("foundBySelf"+this.currentCourseId)

      axios.get("http://localhost:10087/getAllDb",{
        params:{
          teacherId:this.$store.state.teacher.id,
          courseId:this.currentCourseId,

        },headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            this.dbList = response.data.data
            let temp = {
              // "id":1,"dbType":1,"dbContent":"下列哪个不是Java的基本类型?|int|float|long|String","dbAnswer":"D"
                "id":"",
                "dbType":"",
                "dbReallyType":"",
                "dbContent":"",
                "dbAnswer":"",

            }
            for(let i in this.dbList)
            {
              let temp = {
                // "id":1,"dbType":1,"dbContent":"下列哪个不是Java的基本类型?|int|float|long|String","dbAnswer":"D"
                "id":"",
                "dbType":"",
                "dbReallyType":"",
                "dbContent":"",
                "dbAnswer":"",

              }
              temp.id = this.dbList[i].id
              temp.dbType = this.dbList[i].dbType
              temp.dbContent = this.dbList[i].dbContent
              temp.dbAnswer = this.dbList[i].dbAnswer
              if(this.dbList[i].dbType === 1)
              {
                temp.dbReallyType = "单选题"
              }if(this.dbList[i].dbType === 2)
              {
                temp.dbReallyType = "多选题"
              }if(this.dbList[i].dbType === 3)
              {
                temp.dbReallyType = "判断题"
              }
              this.reallyShowDbList[i] = temp

            }
            console.log(this.reallyShowDbList)


            this.selfVisible = true

          }

      )

    },
  },
  mounted() {
    axios.get("http://localhost:10087/getAllCourse",{
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
</style>
