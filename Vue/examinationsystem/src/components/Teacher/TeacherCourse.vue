<template>
  <el-table
      :data="tableData.filter(data => !search || data.name.toLowerCase().includes(search.toLowerCase()))"
      style="width: 100%">
    <el-table-column
        label="课程名称"
        prop="course"
        width="200"
        >
    </el-table-column>
    <el-table-column
        label="课程编号"
        prop="couNumber"
        width="100"
    >
    </el-table-column>
    <el-table-column
        label="知识点"
        prop="knowledge">
    </el-table-column>
    <el-table-column
        label="课程目标"
        prop="target"
        >
    </el-table-column>


    <el-table-column
        align="right"

    >
<!--      <template slot="header" slot-scope="scope">-->

<!--      </template>-->
      <template slot-scope="scope">
        <el-button
            size="mini"
            @click="handleEdit(scope.$index, scope.row)">查看学生</el-button>
        <el-button
            size="mini"
            @click="handleAdd(scope.$index, scope.row)">添加学生</el-button>
        <el-button
            size="mini"
            @click="handleKnowledge(scope.$index, scope.row)">知识点</el-button>
      </template>




    </el-table-column>
    <el-dialog
        title="学生列表"
        :visible.sync="dialogVisible"
        width="60%"
        :before-close="handleClose"
        :append-to-body="true"
        :destroy-on-close="true"
    >
      <el-table width="100%" :data="studentList">
        <el-table-column
            label="学生名字"
            prop="name">
        </el-table-column>
        <el-table-column
            label="学号"
            prop="account">
        </el-table-column>
        <el-table-column
            label="班级"
            prop="claNo">
        </el-table-column>

        <el-table-column
            align="right">
          <!--      <template slot="header" slot-scope="scope">-->

          <!--      </template>-->
          <template slot-scope="scope">

            <el-tooltip class="item" effect="dark" content="移除学生" placement="right-start">
              <el-button
                  size="mini"
                  @click.native.prevent="deleteStudent(scope.$index, scope.row)">移除</el-button>
            </el-tooltip>
          </template>

        </el-table-column>


      </el-table>
      <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog
        title=""
        :visible.sync="addDialogVisible"
        width="60%"
        :before-close="handleClose"
        :append-to-body="true"
        :destroy-on-close="true"
    >

<div class="big_box">
  <div class="left_box">
    <div >
      <span>
      <h3>单个添加学生</h3>
    </span>
    </div>
    <el-form label-position="left"  label-width="auto">
      <el-form-item label="姓名:">
        <el-input type="text" v-model="newStudent.name"></el-input>
      </el-form-item>
      <el-form-item label="学号:">
        <el-input type="text" v-model="newStudent.account"></el-input>
      </el-form-item>
      <el-form-item label="班级:">
        <el-input type="text" v-model="newStudent.claNo"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" style="margin-left: 80%" @click="addStudentToCourse">提交</el-button>
      </el-form-item>
    </el-form>
  </div>
  <div class="right_box">
<div style="display: flex">
      <span>
      <h3>批量添加学生</h3>

    </span>
</div>

<el-form label-position="left"  label-width="auto">
  <el-form-item>
    <el-upload
        class="upload-demo"
        drag
        multiple
        action="upload"
        :headers="headers"
        :data="uploadData"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload">
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
    </el-upload>
  </el-form-item>
</el-form>
  </div>
</div>

      <span slot="footer" class="dialog-footer">
    <el-button @click="addDialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="addDialogVisible = false">确 定</el-button>
  </span>
    </el-dialog>
    <el-dialog
        title=""
        :visible.sync="checkKnowledge"
        width="40%"
        :before-close="handleClose"
        :append-to-body="true"
        :destroy-on-close="true"
    >
      <el-table width="60%" :data="showKnowledge">
        <el-table-column
            label="知识点"
            prop="knoContent">
        </el-table-column>


        <el-table-column
            align="right">
          <!--      <template slot="header" slot-scope="scope">-->

          <!--      </template>-->
          <template slot-scope="scope">

            <el-tooltip class="item" effect="dark" content="更改知识点" placement="right-start">
              <el-button
                  size="mini"
                  @click.native.prevent="updateKnowledge(scope.$index, scope.row)">更改</el-button>
            </el-tooltip>
            <el-tooltip class="item" effect="dark" content="移除知识点" placement="right-start">
              <el-button
                  size="mini"
                  @click.native.prevent="removeKnowledge(scope.$index, scope.row)">移除</el-button>
            </el-tooltip>
          </template>

        </el-table-column>


      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button  @click="addKnowledgeToCourse" type="primary" >添加知识点</el-button>
        <el-button @click="checkKnowledge = false">取 消</el-button>
        <el-button type="primary" @click="checkKnowledge = false">确 定</el-button>
  </span>

    </el-dialog>

  </el-table>

</template>

<script>
import axios from "axios";axios.defaults.baseURL="http://localhost:10087"

export default {
  data() {
    return {
      headers:{
        Authorization: sessionStorage.getItem("Authorization")
      },
      imageUrl: '',
      //控制弹窗 显示
      dialogVisible:false,
      addDialogVisible:false,
      tableData: [{

      }],
      studentList:[],
      search: '',
      //点击查看学生时候，将课程的值保存
      tempCourseId: '',
      newStudent:{
        name:"",
        account:'',
        claNo:""

      },
      uploadData:{
        teacherId: this.$store.state.teacher.id,
        courseId:this.tempCourseId,
        Authorization: sessionStorage.getItem("Authorization"),
        headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      },
      checkKnowledge:false,
      KnowledgeList:[],
      showKnowledge:[],
      tagCourseId:"",
      currentIndex:""
    }
  },
  methods: {
    handleEdit(index, row) {
      console.log(row+"row");
      axios.post("getStudentList",{
        teacherId: this.$store.state.teacher.id,
        courseId: this.tableData[index].courseId
      },{
        headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            this.studentList = response.data.data
            console.log(this.studentList)
          }
      )
      console.log(index, row);
      this.tempCourseId = this.tableData[index].courseId
      this.dialogVisible = true;
    },
    handleAdd(index, row) {
      this.addDialogVisible = true
      this.tempCourseId = this.tableData[index].courseId
      this.uploadData.courseId = this.tableData[index].courseId

    },
    handleKnowledge(index, row){
      this.tagCourseId = this.tableData[index].courseId
      this.checkKnowledge = true
      this.currentIndex = index
      this.showKnowledge = this.KnowledgeList[index]
    },
    deleteStudent(index, row) {
      const tag = window.confirm("确定移除该学生吗?");
      if(tag){
        console.log(this.tempCourseId, row.id,this.$store.state.teacher.id);
        axios.post('removeStudent',{
          courseId:this.tempCourseId,
          teacherId: this.$store.state.teacher.id,
          studentId: row.id
        },{
          headers:{
            Authorization: sessionStorage.getItem("Authorization")
          }
        }).then(
            response =>{
              this.$message('移除成功');
              this.studentList.splice(index, 1);
              this.updateDataWhenChange()
            }
        )
      }

    },
    handleClose(done) {
      this.$confirm('确认关闭？')
          .then(_ => {
            done();
          })
          .catch(_ => {});
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      console.log(file.type)
      const  isXls = file.type
      if(isXls === "application/vnd.ms-excel")
      {
        alert("添加成功")
      }

    },
    addStudentToCourse(){
      axios.post('addStudentToCourse',{
          teacherId: this.$store.state.teacher.id,
          name:this.newStudent.name,
          account:this.newStudent.account,
          claNo:this.newStudent.claNo,
          courseId:this.tempCourseId
      },{
        headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            if(response.data.code == 200) {
              alert("添加成功")
              this.updateDataWhenChange()
            }
          }
      )
    },
    updateKnowledge(index, row){
      var knoContent = this.showKnowledge[index].knoContent;
      console.log("this.showKnowledge[index].id"+this.showKnowledge[index].id)
      this.$prompt("你要将知识点{"+knoContent+"}修改为：", '更改知识点', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({ value }) => {
        axios.post('updateKnowledge',{
          "teacherId": this.$store.state.teacher.id,
          "courseId":this.showKnowledge[index].knoCourse,
          "id":this.showKnowledge[index].id,
          "knoContent": value

        },{
          headers:{
            Authorization:sessionStorage.getItem("Authorization")
          }
        }).then(
            async response => {
              console.log(response)
              if (response.data.code === 200) {

                await this.updateDataWhenChange()
                this.showKnowledge = this.KnowledgeList[this.currentIndex]
                this.showKnowledge[index].knoContent = value
                alert("修改成功")
              }
            }
        )

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    },
    removeKnowledge(index, row){
      axios.post('removeKnowledge',{
        "teacherId": this.$store.state.teacher.id,
        "courseId":this.showKnowledge[index].knoCourse,
        "id":this.showKnowledge[index].id,
        "knoContent": this.showKnowledge[index].knoContent

      },{
        headers:{
          Authorization:sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            if(response.data.code == 200)
            {
              alert("删除成功")
              this.showKnowledge.splice(index,1)
              this.updateDataWhenChange()
            }
          }
      )
    },
    addKnowledgeToCourse(){
      this.$prompt("", '添加知识点', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
      }).then(({ value }) => {
        axios.post('addKnowledgeToCourse',{
          "teacherId": this.$store.state.teacher.id,
          "courseId":this.tagCourseId,
          "knoContent": value

        },{
          headers:{
            Authorization:sessionStorage.getItem("Authorization")
          }
        }).then(
            response =>{
              console.log(response)
              if(response.data.code === 200){
                alert("添加成功")
                this.updateDataWhenChange()
                this.showKnowledge.push(response.data.data)
              }
            }
        )

      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消输入'
        });
      });
    },
    async updateDataWhenChange() {
       axios.get('getTeacherCourse?teacherId=' + this.$store.state.teacher.id, {
        // params:{
        //   'teacherId': that.$store.state.teacher.id
        // },
        headers: {
          Authorization: sessionStorage.getItem("Authorization")
        }

      }).then(
          response => {
            console.log(response.config)
            console.log(response.data.data)
            const temp = response.data.data;
            const table = []

            for (let i in temp) {
              const course = temp[i].course.couSubject;
              const couNumber = temp[i].course.couNumber;
              const courseId = temp[i].course.id;

              /*knowledge封装*/
              var knowledge = ''
              for (let j in temp[i].knowledgeList) {

                let s = j
                s++
                let ss = s + "." + temp[i].knowledgeList[j].knoContent + "  "
                knowledge = knowledge + ss
              }
              if (knowledge === '') {
                knowledge = '无'
              }
              /*target封装*/
              var target = ''
              let tempKnowledge2 = []
              for (let k in temp[i].targetList) {
                let s = k
                s++
                let ss = s + "." + temp[i].targetList[k].tarContent + "  "
                target = target + ss
              }
              if (target === '') {
                target = '无'
              }

              table[i] = {course,couNumber, knowledge, target, courseId}
            }

            for (let index in table) {
              console.log(table[index].course)
              console.log(table[index].knowledge)
              console.log(table[index].target)

            }
            this.tableData = table


            let tempKnowledge2 = {
              id: "",
              knoContent: "",
              knoCourse: ""
            }
            let tempKnowledge3 = []
            let tempKnowledge4 = []
            for (let index in temp) {
              for (let index2 in temp[index].knowledgeList) {
                var knoContent = temp[index].knowledgeList[index2].knoContent;
                var knoCourse = temp[index].knowledgeList[index2].knoCourse;
                var id = temp[index].knowledgeList[index2].id;

                tempKnowledge2.id = id
                tempKnowledge2.knoContent = knoContent
                tempKnowledge2.knoCourse = knoCourse

                tempKnowledge3[index2] = tempKnowledge2
                tempKnowledge2 = []
              }
              tempKnowledge4[index] = tempKnowledge3
              tempKnowledge3 = []
            }

            this.KnowledgeList = tempKnowledge4


          }
      )
    }

  },
  mounted() {
    console.log("TeacherCourse...")
    console.log(this.$store.state)
    this.updateDataWhenChange()
  },
  created() {
    // 在页面加载时读取sessionStorage里的状态信息
    if (sessionStorage.getItem('store')) {
      this.$store.replaceState(
          Object.assign(
              {},
              this.$store.state,
              JSON.parse(sessionStorage.getItem('store'))
          )
      )
    }

    // 在页面刷新时将vuex里的信息保存到sessionStorage里
    // beforeunload事件在页面刷新时先触发
    window.addEventListener('beforeunload', () => {
      sessionStorage.setItem('store', JSON.stringify(this.$store.state))
    })
  }

}

</script>
<style>
  .big_box{width: 100%;height: 100%;display: flex;}/* 设置大盒子的布局为flex布局 */
  .left_box{width: 50%;height: 30%;}
  .right_box{width: 50%;height: 50%;
    display: table;
    justify-content: center;
    align-items: center;
    padding-left: 20px;
    }
</style>