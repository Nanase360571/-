<template>
  <div>
    <template>
      <el-select
        v-model="allCourseList.course"
        placeholder="请选择课程"
        @change="selectEvent"
      >
        <el-option
          v-for="item in allCourseList"
          :key="item.couNumber"
          :label="item.couSubject"
          :value="item.couSubject"
        ></el-option>
      </el-select>
      <el-button v-show="selected" @click="clickSelected">添加题目</el-button>
    </template>
    <el-table
      :data="dbList"
      height="1000"
      border
      style="width: 100%"
      :key="itemKey"
    >
      <el-table-column prop="dbId" label="ID" width="50"> </el-table-column>
      <el-table-column
        prop="dbType"
        label="题目类型"
        width="100"
        :filters="[
          { text: '单选题', value: '单选题' },
          { text: '多选题', value: '多选题' },
          { text: '判断题', value: '判断题' },
        ]"
        :filter-method="filterHandler"
        :filter-multiple="false"
      >
      </el-table-column>
      <el-table-column prop="dbContent" label="题目" width="300">
      </el-table-column>
      <el-table-column prop="dbAnswer" label="答案" width="300">
      </el-table-column>

      <el-table-column
        label="课程目标"
        :filters="targetFilterList"
        :filter-method="filterHandler2"
        :filter-multiple="false"
      >
        <template scope="scope">
          <el-select
            v-model="scope.row.tarContent"
            placeholder="请选择"
            @change="(val) => changeTarget(val, scope.row)"
          >
            <el-option
              v-for="item in options"
              :key="item.id"
              :label="item.tarContent"
              :value="item.id"
            ></el-option>
          </el-select>
        </template>
      </el-table-column>

      <el-table-column
        label="知识点"
        :filters="knowledgeFilterList"
        :filter-method="filterHandler3"
        :filter-multiple="false"
      >
        <template scope="scope">
          <el-select
            v-model="scope.row.knoContent"
            placeholder="请选择"
            @change="(val) => changeKnowledge(val, scope.row)"
          >
            <el-option
              v-for="item in knowledgeOptions"
              :key="item.id"
              :label="item.knoContent"
              :value="item.id"
            ></el-option>
          </el-select>
        </template>
      </el-table-column>
      <el-table-column>
        <template scope="scope">
          <el-button
            size="mini"
            @click="updateKnowledgeAndTarget(scope, scope.row)"
            >更新</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <el-dialog
      :visible.sync="addDBVisible"
      width="60%"
      :append-to-body="true"
    >
      <div class="big_box">
        <div class="left_box">
          <div>
            <span>
              <h3>单个添加题目</h3>
            </span>
            <span>
            <span>请选择题目类型: </span><el-select v-model="value" placeholder="请选择" @change="getSelected">
              <el-option
                v-for="item in dbType"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
            </span>
            <br><br><br>
                <div v-show="tag === 1">
                <el-form label-position="left"  label-width="auto">
                <el-form-item label="题目:">
                  <el-input type="text" v-model="newDb.dbTitle"></el-input>
                </el-form-item>
                 <el-form-item label="A:">
                  <el-input type="text" v-model="newDb.dbOption[0]"></el-input>
                </el-form-item>
                 <el-form-item label="B:">
                  <el-input type="text"  v-model="newDb.dbOption[1]"></el-input>
                </el-form-item>
                <el-form-item label="C:" >
                  <el-input type="text" v-model="newDb.dbOption[2]"></el-input>
                </el-form-item>
                <el-form-item label="D:" >
                  <el-input type="text" v-model="newDb.dbOption[3]"></el-input>
                </el-form-item>
                <el-form-item label="答案:">
                    <el-select v-model="newDb.dbAnswer[0]"  placeholder="请选择" @change="changeTF">
                      <el-option
                        v-for="item in SMOption"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                <el-button type="primary" style="margin-left: 80%" @click="addDB">提交</el-button>
                </el-form-item>
              </el-form>
                </div>
                <div v-show="tag === 2">
                <el-form label-position="left"  label-width="auto">
                <el-form-item label="题目:">
                  <el-input type="text" v-model="newDb.dbTitle"></el-input>
                </el-form-item>
                 <el-form-item label="A:">
                  <el-input type="text" v-model="newDb.dbOption[0]"></el-input>
                </el-form-item>
                 <el-form-item label="B:">
                  <el-input type="text"  v-model="newDb.dbOption[1]"></el-input>
                </el-form-item>
                <el-form-item label="C:" >
                  <el-input type="text" v-model="newDb.dbOption[2]"></el-input>
                </el-form-item>
                <el-form-item label="D:" >
                  <el-input type="text" v-model="newDb.dbOption[3]"></el-input>
                </el-form-item>
                  <el-form-item label="答案:">
                    <el-select v-model="newDb.dbAnswer" multiple placeholder="请选择" >
                      <el-option
                        v-for="item in SMOption"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                <el-button type="primary" style="margin-left: 80%" @click="addDB">提交</el-button>
                </el-form-item>
              </el-form>
              </div>
                <div v-show="tag === 3">
                <el-form label-position="left"  label-width="auto">
                <el-form-item label="题目:">
                  <el-input type="text" v-model="newDb.dbTitle"></el-input>
                </el-form-item>
                  <el-form-item label="答案:">
                    <el-select v-model="newDb.dbAnswer[0]"  placeholder="请选择" @change="changeTF">
                      <el-option
                        v-for="item in TFOption"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value">
                      </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                <el-button type="primary" style="margin-left: 80%" @click="addDB">提交</el-button>
                </el-form-item>
              </el-form>
              </div>
          </div>
        
        
        
        </div>
          <div class="right_box">
                  <div style="display: flex">
                        <span>
                        <h3>批量添加题目&nbsp;&nbsp;&nbsp;&nbsp;<a href="/static/ItemFormat.xls" download>下载模板</a></h3>
                        <!-- <h3>批量添加题目<el-button @click="downLoad">x下载模板</el-button></h3> -->
                      </span>
                      <span>
                        
                      </span>
                  </div>

                  <el-form label-position="left"  label-width="auto">
                    <el-form-item>
                      <el-upload
                          class="upload-demo"
                          drag
                          multiple
                          action="addPatchDb"
                          :data="uploadData"
                          :headers="headers"
                          :show-file-list="false"
                          :on-success="handleAvatarSuccess"
                          :before-upload="beforeAvatarUpload">
                        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
                      </el-upload>
                    </el-form-item>
                  </el-form>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import axios from "axios";axios.defaults.baseURL="http://localhost:10087"

export default {
  name: "TeacherCourse",

  data() {
    return {
      headers:{
        Authorization: sessionStorage.getItem("Authorization")
      },
      uploadData:{
        courseId:this.currentCourseId
      },
      newDb:{
        dbType:0,
        dbTitle:'',
        dbOption:[],
        dbAnswer:[],
      },
      value1:"",
      value2:[],
      value3:"",
      dbType:[
        {
          value:'单选题',
          lable:'1'
        },
        {
          value:'多选题',
          lable:'2'
        },
        {
          value:'判断题',
          lable:'3'
        },
      ],
      SMOption:[
        {
          value:'A',
          lable:'1'
        },
        {
          value:'B',
          lable:'2'
        },
        {
          value:'C',
          lable:'3'
        },
        {
          value:'D',
          lable:'3'
        }
      ],
      TFOption:[
        {
          value:'T',
          lable:'1'
        },
        {
          value:'F',
          lable:'2'
        }
      ],
      addDBVisible: false,
      selected: 0,
      targetFilterList: [],
      itemKey: Math.random(),
      allCourseList: [
        {
          id: "",
          couSubject: "",
          couNumber: "",
        },
      ],
      options: [
        {
          id: "",
          tarContent: "",
        },
      ],
      knowledgeOptions: [{}],
      knowledgeFilterList: [{}],

      val: "",
      dbList: [],
      currentCourseId: "",
      tag:0,
      value:"",
    };
  },
  methods: {
    downLoad(){
      window.location.href="/static/Itemformat.xls"
    },
    addDB(){
      axios.post("addDB",{
        db: this.newDb,
        courseId: this.currentCourseId
      },{
        headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            if(response.data.data === 200){
            alert("添加成功")

            }
          }
      )
    },
    clickSelected(){
      this.addDBVisible = true
    },
    changeTF(val){console.log(val)},
    getSelected(){
      this.value1 = ""
      this.value2 = [],
      this.value3 = ""
      this.newDb = {
        dbType:0,
        dbTitle:'',
        dbOption:[],
        dbAnswer:[],
      }
      if(this.value === '单选题'){
        this.tag = 1
        this.newDb.dbType = 1
      }
      if(this.value === '多选题'){
        this.tag = 2
        this.newDb.dbType = 2
      }
      if(this.value === '判断题'){
        this.tag = 3
        this.newDb.dbType = 3

      }
    },
    changeKnowledge(val, row) {
      this.isEdit = true;
      console.log("传入" + val);
      console.log("目前" + row.knoId);
      row.knoId = val;
    },
    changeTarget(val, row) {
      this.isEdit = true;
      console.log("传入" + val);
      console.log("目前" + row.targetId);
      row.targetId = val;
    },
    filterHandler(value, row, column) {
      console.log(row, column);
      return row.dbType === value;
    },
    filterHandler2(value, row, column) {
      console.log(row, column);
      return row.tarContent === value;
    },
    filterHandler3(value, row, column) {
      console.log(row, column);
      return row.knoContent === value;
    },
    selectEvent(val) {
      this.selected = 1;
      let course;
      for (let i in this.allCourseList) {
        if (this.allCourseList[i].couSubject === val) {
          course = this.allCourseList[i];
        }
      }
      this.currentCourseId = course.id;
      this.uploadData.courseId = course.id
      // console.log(course.id)

      this.itemKey = Math.random();
      console.log("this.itemKey" + this.itemKey);
      axios
        .get("getCourseDB", {
          params: {
            courseId: course.id,
            teacherId: this.$store.state.teacher.id,
          },
          headers: {
            Authorization: sessionStorage.getItem("Authorization"),
          },
        })
        .then((response) => {
          if (response.data.code == 200) {
            this.dbList = response.data.data;

            axios
              .get("getKnowledgeList", {
                params: {
                  courseId: course.id,
                  teacherId: this.$store.state.teacher.id,
                },
                headers: {
                  Authorization: sessionStorage.getItem("Authorization"),
                },
              })
              .then((response) => {
                if (response.data.code == 200) {
                  this.knowledgeOptions = response.data.data;
                  this.knowledgeFilterList = [];
                  this.knowledgeFilterList.push({
                    text: "未设置",
                    value: "未设置",
                  });

                  for (let index in this.knowledgeOptions) {
                    const obj = {
                      text: this.knowledgeOptions[index].knoContent,
                      value: this.knowledgeOptions[index].knoContent,
                    };
                    this.knowledgeFilterList.push(obj);
                  }

                  axios
                    .get("getTargetList", {
                      params: {
                        courseId: course.id,
                        teacherId: this.$store.state.teacher.id,
                      },
                      headers: {
                        Authorization: sessionStorage.getItem("Authorization"),
                      },
                    })
                    .then((response) => {
                      if (response.data.code == 200) {
                        this.options = response.data.data;
                        this.targetFilterList = [];
                        for (let index in this.options) {
                          const obj = {
                            text: this.options[index].tarContent,
                            value: this.options[index].tarContent,
                          };
                          this.targetFilterList.unshift(obj);
                        }
                        this.targetFilterList.unshift({
                          text: "未设置",
                          value: "未设置",
                        });

                        console.log(response.data.data);
                        console.log(this.options[0].id + "this.options.id");
                      }
                    });
                }
              });
          } else {
            this.dbList = response.data.data;
          }
        });
      console.log("this.dbList" + this.dbList);
      
    },

    updateKnowledgeAndTarget(scope, row) {
      let targetId = row.targetId
      let knoId = row.knoId
      if(row.targetId === null){
          targetId = -1
      }
      if(row.knoId === null){
          knoId = -1
      }
      console.log(row.targetId);
      axios
        .post("updateKnowledgeAndTarget", {
          dbId: row.dbId,
          knoId: knoId,
          targetId: targetId,
          courseId: this.currentCourseId,
          teacherId: this.$store.state.teacher.id,

        },{
          headers:{
            Authorization: sessionStorage.getItem("Authorization")
          }
        })
        .then((response) => {
          if (response.data.code == 200) {
            this.$message("更新成功");
          }
        });
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    beforeAvatarUpload(file) {
      console.log(file.type)
      const  isXls = file.type
      if(isXls === "application/vnd.ms-excel")
      {
        alert("插入成功")
      }

    },
  },

  mounted() {
    console.log("TeacherCourse");
    axios
      .get("getAllCourse", {
        params: {
          teacherId: this.$store.state.teacher.id,
        },
        headers: {
          Authorization: sessionStorage.getItem("Authorization"),
        },
      })
      .then((response) => {
        if (response.data.code == 200) {
          this.allCourseList = response.data.data;
        }
      });
  },
};
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
  clear: both;
}
.big_box {
  width: 100%;
  height: 100%;
  display: flex;
} /* 设置大盒子的布局为flex布局 */
.left_box {
  width: 50%;
  height: 30%;
}
.right_box {
  width: 50%;
  height: 50%;
  display: table;
  justify-content: center;
  align-items: center;
  padding-left: 20px;
}
</style>
