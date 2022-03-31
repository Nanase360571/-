<template>
  <div>
    <template>
      <el-select v-model="allCourseList.course" placeholder="请选择课程" @change="selectEvent">
        <el-option
            v-for="item in allCourseList"
            :key = "item.couNumber"
            :label="item.couSubject"
            :value="item.couSubject"
        ></el-option>
      </el-select>
    </template>
    <el-table
        :data="dbList"
        height="1000"
        border
        style="width: 100% "
        :key = itemKey
    >
      <el-table-column
          prop="dbId"
          label="ID"
          width="50">
      </el-table-column>
      <el-table-column
          prop="dbType"
          label="题目类型"
          width="100"
          :filters="[{text:'单选题',value:'单选题'},{text:'多选题',value:'多选题'},{text:'判断题',value:'判断题'}]"
          :filter-method="filterHandler"
          :filter-multiple="false"
      >
      </el-table-column>
      <el-table-column
          prop="dbContent"
          label="题目"
          width="300">
      </el-table-column>
      <el-table-column
          prop="dbAnswer"
          label="答案"
          width="300">
      </el-table-column>

      <el-table-column
          label="课程目标"
          :filters="targetFilterList"
          :filter-method="filterHandler2"
          :filter-multiple="false"
      >
          <template scope="scope">
            <el-select v-model="scope.row.tarContent" placeholder="请选择"  @change="(val)=>changeTarget(val,scope.row)">
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
          :filter-multiple="false">
        <template scope="scope">
          <el-select v-model="scope.row.knoContent" placeholder="请选择" @change="(val)=>changeKnowledge(val,scope.row)">
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
        <el-button size="mini" @click="updateKnowledgeAndTarget(scope,scope.row)" >更新</el-button>
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
      targetFilterList:[],
      itemKey:Math.random(),
      allCourseList:[{
        id:"",
        couSubject:"",
        couNumber:""
      }],
      options:[
        {
          id:"",
          tarContent:""
        }
      ],
      knowledgeOptions:[{}],
      knowledgeFilterList:[{}],

      val:'',
      dbList:[],
      currentCourseId:"",
    };
  },
  methods:{
    changeKnowledge(val,row){
      this.isEdit = true
      console.log("传入"+val)
      console.log("目前"+row.knoId)
      row.knoId = val

    },
    changeTarget(val,row){
      this.isEdit = true
      console.log("传入"+val)
      console.log("目前"+row.targetId)
      row.targetId = val
    },
    filterHandler(value, row, column){
      console.log(row)
      return row.dbType === value
    },
    filterHandler2(value, row, column){
      console.log(row)
      return row.tarContent === value
    },
    filterHandler3(value, row, column){
      console.log(row)
      return row.knoContent === value
    },
    selectEvent(val){
      let course;
      for(let i in this.allCourseList){
        if(this.allCourseList[i].couSubject === val){
          course = this.allCourseList[i]
        }
      }
      this.currentCourseId = course.id
      // console.log(course.id)





      this.itemKey = Math.random()
      console.log("this.itemKey"+this.itemKey)
      axios.get("http://localhost:10087/getCourseDB",{
        params:{
          courseId: course.id,
          teacherId:this.$store.state.teacher.id
        },
        headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
          response =>{
            if(response.data.code == 200){
              this.dbList = response.data.data

              axios.get("http://localhost:10087/getKnowledgeList",{
                params:{
                  courseId: course.id,
                  teacherId:this.$store.state.teacher.id
                },
                headers:{
                  Authorization: sessionStorage.getItem("Authorization")
                }
              }).then(
                  response =>{
                    if(response.data.code == 200){
                      this.knowledgeOptions = response.data.data
                      this.knowledgeFilterList = []
                      this.knowledgeFilterList.push({text:"未设置",value:"未设置"})

                      for(let index in this.knowledgeOptions){
                        const obj = {
                          text: this.knowledgeOptions[index].knoContent,
                          value: this.knowledgeOptions[index].knoContent
                        };
                        this.knowledgeFilterList.push(obj)
                      }

                      axios.get("http://localhost:10087/getTargetList",{
                        params:{
                          courseId: course.id,
                          teacherId:this.$store.state.teacher.id
                        },
                        headers:{
                          Authorization: sessionStorage.getItem("Authorization")
                        }
                      }).then(
                          response =>{
                            if(response.data.code == 200){
                              this.options = response.data.data
                              this.targetFilterList = []
                              for(let index in this.options){
                                const obj = {
                                  text: this.options[index].tarContent,
                                  value: this.options[index].tarContent
                                };
                                this.targetFilterList.unshift(obj)
                              }
                              this.targetFilterList.unshift({text:"未设置",value:"未设置"})

                              console.log(response.data.data)
                              console.log(this.options[0].id+"this.options.id")
                            }
                          }
                      )

                    }
                  }
              )


            }
            else {this.dbList = response.data.data}

          }
      )
      console.log("this.dbList"+this.dbList)
    },

    updateKnowledgeAndTarget(scope,row){

      console.log(row.targetId)
      axios.post("http://localhost:10087/updateKnowledgeAndTarget",{
        dbId:row.dbId,
        knoId:row.knoId,
        targetId:row.targetId,
        courseId:this.currentCourseId,
        teacherId:this.$store.state.teacher.id,
        headers:{
          Authorization: sessionStorage.getItem("Authorization")
        }
      }).then(
        response =>{
          if(response.data.code == 200)
          {
            this.$message('更新成功');
          }
        })
    }
  },

  mounted() {
    console.log("TeacherCourse")
    axios.get("http://localhost:10087/getCourseList",{
      params:{
        teacherId: this.$store.state.teacher.id,
      },
      headers:{
        Authorization: sessionStorage.getItem("Authorization")
      }
    }).then(
        response =>{
            if(response.data.code == 200){
              this.allCourseList = response.data.data
            }
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
