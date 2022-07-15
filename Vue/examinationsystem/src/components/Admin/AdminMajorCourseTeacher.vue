<template>
  <div>
    专业:&nbsp;&nbsp;&nbsp;<el-select v-model="majorValue"   filterable placeholder="请选择" @change="selectMajor" >
    <el-option
      v-for="item in majorOptions"
      :key="item.id"
      :label="item.major"
      :value="item.id">
    </el-option>
    </el-select>

    课程:&nbsp;&nbsp;&nbsp;<el-select v-model="courseValues"   filterable placeholder="请选择" @change="selectMajor" >
    <el-option
      v-for="item in CourseOptions"
      :key="item.id"
      :label="item.couSubject"
      :value="item.id">
    </el-option>
    </el-select>

    教师:&nbsp;&nbsp;&nbsp;<el-select v-model="teacherValue" multiple    filterable placeholder="请选择" @change="selectMajor" >
    <el-option
      v-for="item in teacherOptions"
      :key="item.id"
      :label="item.name"
      :value="item.id">
    </el-option>
    </el-select>
    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span><el-button @click="submit">提交</el-button></span>
  </div>
</template>

<script>
import axios from "axios";
axios.defaults.baseURL="http://localhost:10088"

export default {
  name:"MyTest",
  data() {
    return {
      currentMajorId:"",
      teacherOptions:[],
       majorOptions: [],
       classesOptions:[],
       CourseOptions:[],
        majorValue: '',
        classesValue:'',
        teacherValue:'',
        courseValues:''

    };
  },
  methods:{
    submit(){
      if(this.majorValue === '' || this.courseValues === '' || this.teacherValue === '' || this.teacherValue.length === 0){
        alert("专业,教师,课程不能为空")
      }
      else{
        axios.post("submitRelation",{
        majorValue:this.majorValue,
        courseValues:this.courseValues,
        teacherValue:this.teacherValue
      }).then(
          response =>{
            if(response.data.code === 200){
              alert("添加成功")
            }
            if(response.data.code === 10001){
              alert(response.data.msg)
            }
          }
      )
      }
      
    },
    selectMajor(val){
      console.log(val);
    }

  },
  mounted() {
    axios.post("getAllMajor",{
      }).then(
          response =>{
            if(response.data.code === 200){
              this.majorOptions = response.data.data
            }
          }
      )

      axios.post("getAllClasses",{
      }).then(
          response =>{
            if(response.data.code === 200){
              this.classesOptions = response.data.data
            }
          }
      )

      axios.post("getAllCourse",{
      }).then(
          response =>{
            if(response.data.code === 200){
              this.CourseOptions = response.data.data
            }
          }
      )

      axios.post("getAllTeacher",{
      }).then(
          response =>{
            if(response.data.code === 200){
              this.teacherOptions = response.data.data
            }
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
