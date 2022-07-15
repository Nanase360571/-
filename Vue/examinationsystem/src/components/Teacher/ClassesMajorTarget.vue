<template>
  <div>
    <el-table :data="majorList">
      <el-table-column
          label="专业"
          prop="major">
      </el-table-column>
      <!--      </template>-->
      <el-table-column>
        <template slot-scope="scope">
          <el-button
              size="mini"
              @click="showClasses(scope.$index, scope.row)">查看班级</el-button>
          <el-button
              size="mini"
              @click="distributeClasses(scope.$index, scope.row)">分配班级</el-button>
          <el-button
              size="mini"
              @click="handleKnowledge(scope.$index, scope.row)">课程目标和知识点</el-button>
        </template>

      </el-table-column>
    </el-table>
<!--    查看班级dialog-->
    <el-dialog
        title="班级列表"
        :visible.sync="dialogVisible"
        width="60%"
        :append-to-body="true"
        :destroy-on-close="true"
    >

      <el-table width="100%" :data="majorClassesList">
        <el-table-column
          label="班级"
          prop="claNo"
        >

        </el-table-column>
        <el-table-column
            align="right">

          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" content="移除班级" placement="right-start">
              <el-button
                  size="mini"
                  @click.native.prevent="removeClasses(scope.$index, scope.row)">移除</el-button>
            </el-tooltip>
          </template>

        </el-table-column>
      </el-table>


      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
    </el-dialog>
<!--    分配班级dialog-->
    <el-dialog
        title="班级列表"
        :visible.sync="distributeDialogVisible"
        width="60%"
        :append-to-body="true"
        :destroy-on-close="true"
    >

      <el-table
          :data="reallyShowMajorClassesList"
          style="width: 100%">
        <el-table-column
            label="班级"
            prop="claNo">
        </el-table-column>
        <el-table-column
            align="right">
          <template slot="header" slot-scope="scope">
            <el-input
                v-model="search"
                size="mini"
                placeholder="输入关键字搜索"/>
          </template>
          <template slot-scope="scope">
            <el-button
                size="mini"
                @click="addClassesToMajor(scope.$index, scope.row)">添加到该专业</el-button>

          </template>
        </el-table-column>
      </el-table>

      <span slot="footer" class="dialog-footer">
    <el-button @click="distributeDialogVisible = false ;search = null">取 消</el-button>
    <el-button type="primary" @click="distributeDialogVisible = false ;search = null">确 定</el-button>
  </span>
    </el-dialog>
<!--    查看课程目标dialog-->
    <el-dialog
        title="课程"
        :visible.sync="targetDialogVisible"
        width="60%"
        :append-to-body="true"
        :destroy-on-close="true"
    >

      <el-table
          :data="reallyShowCourseList"
          style="width: 100%">
        <el-table-column
            label="课程"
            prop="couSubject">
        </el-table-column>
        <el-table-column
            label="课程编号"
            prop="couNumber">
        </el-table-column>
        <el-table-column
            align="right">
          <template slot="header" slot-scope="scope">
            <el-input
                v-model="searchCourse"
                size="mini"
                placeholder="输入关键字搜索"/>
          </template>
          <template slot-scope="scope">
            <el-button
                size="mini"
                @click="handleTarget(scope.$index, scope.row)">课程目标</el-button>
            <el-button
                size="mini"
                @click="disKnowledge(scope.$index, scope.row)">知识点</el-button>

          </template>
        </el-table-column>
      </el-table>

      <span slot="footer" class="dialog-footer">
    <el-button @click="targetDialogVisible = false ;searchCourse = null">取 消</el-button>
    <el-button type="primary" @click="targetDialogVisible = false ;searchCourse = null">确 定</el-button>
  </span>
    </el-dialog>
<!--    管理课程目标dialog-->
    <el-dialog
        title="管理课程目标"
        :visible.sync="handleTargetDialogVisible"
        width="60%"
        :append-to-body="true"
        :destroy-on-close="true"
    >
<!--    <el-table-->
<!--        :data="targetList"-->
<!--        style="width: 100%">-->
<!--      <el-table-column-->
<!--          label="课程目标"-->
<!--          prop="tarContent">-->
<!--      </el-table-column>-->
<!--      <el-table-column-->
<!--          label="占比(%)"-->
<!--          prop="proportion">-->
<!--      </el-table-column>-->

<!--      <el-table-column-->
<!--          align="right">-->
<!--        <template slot="header" slot-scope="scope">-->
<!--          <el-button size="mini">添加一条</el-button>-->
<!--        </template>-->
<!--        <template slot-scope="scope">-->
<!--          <el-button-->
<!--              size="mini"-->
<!--              @click="handleTarget(scope.$index, scope.row)">编辑</el-button>-->
<!--          <el-button-->
<!--              size="mini"-->
<!--              @click="handleTarget(scope.$index, scope.row)">移除</el-button>-->

<!--        </template>-->
<!--      </el-table-column>-->


<!--    </el-table>-->
      <div>
        <el-table :data="targetList" >
          <el-table-column label="课程目标">
            <template slot-scope="scope">
              <el-input placeholder="请输入内容" v-show="isEdit" v-model="scope.row.tarContent"></el-input>

            </template>
          </el-table-column>
          <!-- <el-table-column label="占比(%)">
            <template slot-scope="scope">
              <el-input placeholder="请输入占比" v-show="isEdit" v-model="scope.row.proportion" oninput="value=value.replace(/[^0-9.]/g,'')"></el-input>
            </template>
          </el-table-column> -->
          <el-table-column label="">
            <template slot-scope="scope">
              <el-button @click="removeTarget(scope.$index,scope.row)">移除</el-button>
            </template>
          </el-table-column>
          <el-table-column align="right">
            <template slot="header" slot-scope="scope">
              <el-button size="mini" @click="addTarget(scope.$index,scope.row)">添加一行</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <span slot="footer" class="dialog-footer">
    <el-button @click="handleTargetDialogVisible = false ">取 消</el-button>
    <el-button type="primary" @click="handleTargetDialogVisible = false">确定</el-button>
    <el-button type="primary" @click="handleModify">提交修改</el-button>
  </span>
    </el-dialog>
    <!--    管理知识点dialog-->
    <el-dialog
        title="管理课程目标"
        :visible.sync="handleKnowledgeDialogVisible"
        width="60%"
        :append-to-body="true"
        :destroy-on-close="true"
    >
      <div>
        <el-table :data="knowledgeList" >
          <el-table-column label="知识点">
            <template slot-scope="scope">
              <el-input placeholder="请输入内容" v-show="isEdit" v-model="scope.row.knoContent"></el-input>

            </template>
          </el-table-column>
          <el-table-column label="">
            <template slot-scope="scope">
              <el-button @click="removeKnowledge(scope.$index,scope.row)">移除</el-button>
            </template>
          </el-table-column>
          <el-table-column align="right">
            <template slot="header" slot-scope="scope">
              <el-button size="mini" @click="addKnowledge(scope.$index,scope.row)">添加一行</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <span slot="footer" class="dialog-footer">
    <el-button @click="handleKnowledgeDialogVisible = false ">取 消</el-button>
    <el-button type="primary" @click="handleKnowledgeDialogVisible = false">确定</el-button>
    <el-button type="primary" @click="handleModifyKnowledge">提交修改</el-button>
  </span>
    </el-dialog>

  </div>
</template>

<script>
import axios from "axios";
export default {
  name:"ClassesMajorTarget",
  data() {
    return {
      majorList:[
      ],
      majorClassesList:[],
      currentMajorId:"",
      dialogVisible:false,
      distributeDialogVisible:false,
      targetDialogVisible:false,
      handleTargetDialogVisible:false,
      handleKnowledgeDialogVisible:false,
      search:"",
      reallyShowMajorClassesList:[],
      courseList:[],
      reallyShowCourseList:[],
      searchCourse:"",
      targetList:[],
      knowledgeList:[],
      isEdit:true,
      currentCourseId:"",

    };
  },

  watch:{
    search(newVal,oldVal){
      console.log(newVal+"newVal")
      if(this.majorClassesList != null){
        this.reallyShowMajorClassesList = this.majorClassesList.filter(data => !this.search || data.claNo.toLowerCase().includes(this.search.toLowerCase()));
      }
    },
    majorClassesList(newVal,oldVal){
      this.reallyShowMajorClassesList = newVal
    },
    searchCourse(newVal,oldVal){
      console.log(newVal)
      this.reallyShowCourseList = this.courseList.filter(data => !this.searchCourse || data.couSubject.toLowerCase().includes(this.searchCourse.toLowerCase()));
      console.log(this.reallyShowCourseList)
    },
    courseList(newVal,oldVal){
      console.log(newVal)
      this.reallyShowCourseList = this.courseList
      console.log(this.reallyShowCourseList+"this.reallyShowCourseList")
    }
  },

  methods:{

    showClasses(index, row) {
      console.log(this.majorList[index].id)
      this.currentMajorId = this.majorList[index].id
      axios.get("http://localhost:10087/getMajorClasses",{
        params:{
          majorId: this.majorList[index].id,
        },
        headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{

            console.log(response.data.data)
            if(response.data.data === null){
              this.majorClassesList = {}
              console.log(this.majorClassesList+"000")

            }
            else {
              this.majorClassesList = response.data.data
            }
          }
      )
      this.dialogVisible = true
    },
    removeClasses(index, row){
      console.log(index)
      axios.post("http://localhost:10087/removeMajorClasses",{
          majorId: this.currentMajorId,
          classesId: this.majorClassesList[index].id

      },{
        headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            if(response.data.data = 200)
            {
              this.$message({
                message: '移除成功',
                type: 'success'
              });

              this.majorClassesList.splice(index,1)
            }else {this.$message.error("移除失败")}


          }
      )
    },
    distributeClasses(index, row){

      console.log(this.majorList[index].id)
      this.currentMajorId = this.majorList[index].id
      axios.get("http://localhost:10087/getLeftClasses",{
        params:{
          majorId: this.majorList[index].id,
        },
        headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            this.currentMajorId = this.majorList[index].id
            this.distributeDialogVisible = true
            console.log(response.data.data)
            this.majorClassesList = response.data.data

          }
      )

    },
    addClassesToMajor(index,row){
      console.log(index)
      axios.post("http://localhost:10087/addClassesToMajor",{
        majorId: this.currentMajorId,
        classesId: this.majorClassesList[index].id

      },{
        headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            if(response.data.data = 200)
            {
              this.$message({
                message: '添加成功',
                type: 'success'
              });
              this.majorClassesList.splice(index,1)
            }else {
              this.$message.error('添加失败');
            }


          }
      )
    },
    handleKnowledge(index,row){
      console.log("this.currentMajorId"+this.currentMajorId)
      this.currentMajorId = this.majorList[index].id
      axios.get("http://localhost:10087/getCourseList",{
        params:{
          teacherId:this.$store.state.teacher.id,
          majorId:this.currentMajorId
        },
        headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            if(response.data.code = 200)
            {
              this.courseList = response.data.data
              this.targetDialogVisible = true

            }


          }
      )
    },
    handleTarget(index,row){
      this.handleTargetDialogVisible = true
      this.currentCourseId = this.reallyShowCourseList[index].id
      axios.post("http://localhost:10087/getTargetList",{
        majorId: this.currentMajorId,
        courseId: this.courseList[index].id

      },{
        headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            if(response.data.code = 200)
            {
              this.targetList = response.data.data
              if(this.targetList === null){
                let obj = {
                  proportion: 0,
                  tarContent: "",
                  tarId: ""
                }
                console.log("this.targetList = obj")
                this.targetList = []
                this.targetList.push(obj)
              }
              this.targetDialogVisible = true

            }


          }
      )
    },
    disKnowledge(index,row){
      this.handleKnowledgeDialogVisible = true
      this.currentCourseId = this.reallyShowCourseList[index].id
      axios.get("http://localhost:10087/getKnowledgeList",{
        params:{
          courseId:this.courseList[index].id,
          teacherId:this.$store.state.teacher.id,
        },
        headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
          response=>{
            if(response.data.code == 200){
              this.knowledgeList = response.data.data
              this.knowledgeList = response.data.data
              console.log(this.knowledgeList)
              if(this.knowledgeList === null){
                let obj = {
                  knoCourse: 0,
                  knoContent: "",
                  id: 0
                }
                console.log("this.targetList = obj")
                this.knowledgeList = []
                this.knowledgeList.push(obj)
              }
              this.handleKnowledgeDialogVisible = true

            }
          }
      )
    },
    removeTarget(index,row){
      console.log(index)
      this.targetList.splice(index,1)
    },removeKnowledge(index,row){
      console.log(index)
      this.knowledgeList.splice(index,1)
    },
    addTarget(index,row){
      console.log(this.targetList)
      if(this.targetList === null){
        console.log("null")
        let obj = {
          proportion: 0,
          tarContent: "",
          tarId: ""
        }
        this.targetList = obj
      }else{
              this.targetList.push({       
      })
      }

    },addKnowledge(index,row){
      console.log(index)
      if(this.knowledgeList === null){
        console.log("null")
        let obj = {
          knoCourse: 0,
          knoContent: "",
          id: 0
        }
        this.knowledgeList = obj
      }else{
        this.knowledgeList.push({
        })
      }
    },
    handleModify(){
      
        let sum = Number(0)
        for(let index in this.targetList){
          sum = sum +Number(this.targetList[index].proportion)
          if(this.targetList[index].tarContent === undefined){
            alert("课程目标不能为空")
          }
        }
      if(sum == -1 ){
        alert("课程总占比需要为100")
      }
      else {
        axios.post("http://localhost:10087/setTargetForMajor",{
          majorId: this.currentMajorId,
          courseId:this.currentCourseId,
          targetList:this.targetList,
          teacherId:this.$store.state.teacher.id

        },{
          headers:{
            Authorization: sessionStorage.getItem("Authorization")
          }
        }).then(
            response =>{



            }
        )
        this.handleTargetDialogVisible = false

      }
      

    },
    handleModifyKnowledge(){

        let tag = 0
        for(let  index in this.knowledgeList)
        {
          if(this.knowledgeList[index].knoContent == null)
          {
            alert("知识点不能为空")
           tag = 1
          }
        }
        if(tag ===0 )
        {
          axios.post("http://localhost:10087/setKnowledgeForCourse",{
            courseId:this.currentCourseId,
            teacherId:this.$store.state.teacher.id,
            knowledgeList:this.knowledgeList

          },{
            headers:{
              Authorization: sessionStorage.getItem("Authorization")
            }
          }).then(
              response=>{
                  if(response.data.code === 200)
                  {
                    this.$message('修改成功');
                  }else {
                    this.$message('修改失败');
                  }
              }
          )
        }

      this.handleKnowledgeDialogVisible = false
    }

  },

  mounted() {
    axios.get("http://localhost:10087/getMajorList",{
      headers:{
        Authorization: sessionStorage.getItem("Authorization")
      }
    }).then(
        response =>{

          console.log(response.data.data)
          this.majorList = response.data.data
        }
    )
  },

}
</script >
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
