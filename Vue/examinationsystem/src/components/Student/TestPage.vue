<template>
  <div class="outer">
    <div class="headerDiv">
      <header>
        <h1>五邑大学练习系统</h1>
        <div ></div>
        <div class="stuName">考生姓名：{{ studentName }}</div>
        <el-button id="submitbtn" @click.once="getDb()">获取题目</el-button>
        <el-button id="submitbtn" @click="submit()">提交</el-button>
        <el-button @click="exit()">退出</el-button>
      </header>
    </div>
    <div style="clear: both"></div>

    <div class="answerPage">
      <div class="leftPage">
        <h4>答题卡</h4>
        <div class="tip">单选题(共10题,合计30分)</div>
        <div class="num">
          <ul v-show="reallySingleChoiceArrayList.length">
            <li
              v-for="(item, index) in reallySingleChoiceArrayList"
              :key="index"
              :class="{
                checkLi: item.isChecked === '1',
                unCheckLi: item.isChecked === '0',
                correctCheckLi: item.isChecked === '2',
                incorrectCheckLi: item.isChecked === '3'

              }"
            >
              {{ index + 1 }}
            </li>
          </ul>
        </div>
        <div class="tip">多选题(共10题,合计40分)</div>
        <div class="num">
          <ul v-show="reallyMultipleChoiceArrayList.length">
            <li
              v-for="(item, index) in reallyMultipleChoiceArrayList"
              :key="index"
              :class="{
                checkLi: item.isChecked === '1',
                unCheckLi: item.isChecked === '0',
                correctCheckLi: item.isChecked === '2',
                incorrectCheckLi: item.isChecked === '3'
              }"
            >
              {{ index + 1 }}
            </li>
          </ul>
        </div>
        <div class="tip">判断题(共10题,合计30分)</div>
        <div class="num">
          <ul v-show="realyTfArrayList.length">
            <li
              v-for="(item, index) in realyTfArrayList"
              :key="index"
              :class="{
                checkLi: item.isChecked === '1',
                unCheckLi: item.isChecked === '0',
                correctCheckLi: item.isChecked === '2',
                incorrectCheckLi: item.isChecked === '3'

              }"
            >
              {{ index + 1 }}
            </li>
          </ul>
        </div>
      </div>
      <div class="centerPage">
        <h4
          style="text-align: left; margin-left: 5px; font-weight: bold|bolder"
        >
          <b>单选题(共2题,合计100分)</b>
        </h4>
        <ul v-show="reallySingleChoiceArrayList.length" class="arraylist">
          <li
            v-for="(item, index) in reallySingleChoiceArrayList"
            :key="index"
            style="text-align: left; margin-left: 0px"
          >
            <span>{{ index + 1 }}.{{ item.title }}</span
            >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="margin-let:10px color:red font-size:10px" v-show="showAnswer">答案:{{ item.answer[0] }}
              </span><br />
            <el-radio
              v-model="item.Achecked"
              @click.native="checkSingleOption(item.option[0], item,index)"
            ></el-radio
            ><span>{{ item.option[0] }}</span
            ><br />
            <el-radio
              v-model="item.Bchecked"
              @click.native="checkSingleOption(item.option[1], item,index)"
            ></el-radio
            ><span>{{ item.option[1] }}</span
            ><br />
            <el-radio
              v-model="item.Cchecked"
              @click.native="checkSingleOption(item.option[2], item,index)"
            ></el-radio
            ><span>{{ item.option[2] }}</span
            ><br />
            <el-radio
              v-model="item.Dchecked"
              @click.native="checkSingleOption(item.option[3], item,index)"
            ></el-radio
            ><span>{{ item.option[3] }}</span
            ><br />
          </li>

        </ul>
        <h4
          style="text-align: left; margin-left: 5px; font-weight: bold|bolder"
        >
          <b>多选题(共2题,合计100分)</b>
        </h4>
        <ul v-show="reallyMultipleChoiceArrayList.length" class="arraylist">
          <li
            v-for="(item, index) in reallyMultipleChoiceArrayList"
            :key="index"
            style="text-align: left; margin-left: 0px"
          >
            <span>{{ index + 1 }}.{{ item.title }}</span
            >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="margin-let:10px color:red font-size:10px" v-show="showAnswer">答案:{{ item.answer[0] }}
              {{ item.answer[1] }}{{ item.answer[2] }}{{ item.answer[3] }}</span>
            <br />
            <el-checkbox-group v-model="reallyMultipleChoiceArrayList[index].checkList"  @change="handleCheckAllChange($event,index)">
              <el-checkbox  :label="reallyMultipleChoiceArrayList[index].option[0]" ></el-checkbox><br>
              <el-checkbox  :label="reallyMultipleChoiceArrayList[index].option[1]" ></el-checkbox><br>
              <el-checkbox :label="reallyMultipleChoiceArrayList[index].option[2]" ></el-checkbox><br>
              <el-checkbox :label="reallyMultipleChoiceArrayList[index].option[3]" ></el-checkbox><br>
            </el-checkbox-group>
            <br />
          </li>


          
        </ul>
        <h4
          style="text-align: left; margin-left: 5px; font-weight: bold|bolder"
        >
          <b>判断题(共2题,合计100分)</b>
        </h4>
        <ul v-show="realyTfArrayList.length" class="arraylist">
          <li
            v-for="(item, index) in realyTfArrayList"
            :key="index"
            style="text-align: left; margin-left: 0px"
          >
              <span>{{ index + 1 }}.{{ item.title }}</span
            >&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <span style="margin-let:10px color:red font-size:10px" v-show="showAnswer">答案:{{ item.answer[0] }}
              </span><br />
            <el-radio
              v-model="item.Achecked"
              @click.native="checkTOption(item,index)"
              
            >对</el-radio
            ><br>
            <el-radio
              v-model="item.Bchecked"
              @click.native="checkFOption(item,index)"
            >错</el-radio
            >
          </li>


          
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name: "Header2",
  props: [
    "examDialogVisible",

    "studentName",
    "singlePoint",
    "multiPoint",
    "judgePoint",
    "papName",
    "paperId",
    "courseId",
    "singleChoiceArrayList",
    "multipleChoiceArrayList",
    "tfArrayList"
  ],
  data() {
    return {
      radio: 3,
      showAnswer:false,
      reallySingleChoiceArrayList: [],
      reallyMultipleChoiceArrayList: [],
      realyTfArrayList: [],
      // singleChoiceArrayList:[],
      // multipleChoiceArrayList:[],
      // tfArrayList:[],
    };
  },
  beforeUpdate(){  
      console.log("beforeUpdate");
      
},
  mounted() {
    this.getDb()
            },
  methods: {
    getDb(){


    //   console.log("mounted")
    //   axios.get('http://localhost:10086/randomTest',{
    //   params:{
    //     'id':this.courseId
    //   }
    // }).then(
    //      response =>{
    //         this.singleChoiceArrayList = response.data.data.singleChoiceArrayList
    //         this.multipleChoiceArrayList = response.data.data.multipleChoiceArrayList
    //         this.tfArrayList = response.data.data.tfArrayList
    //      }
    // )
    console.log(this.singleChoiceArrayList[0]+"singleChoiceArrayList");

      for(let index in this.singleChoiceArrayList){
                let singleTemp = {
                'id': '',
                'dbType':'',
                'title': '',
                'option': [],
                'answer': '',
                'stuAnswer': [],
                'Achecked': '1',
                'Bchecked': '2',
                'Cchecked': '3',
                'Dchecked': '4',
                'isChecked': '0'

              }
                singleTemp.id = this.singleChoiceArrayList[index].id
                singleTemp.dbType = this.singleChoiceArrayList[index].dbType
                singleTemp.title = this.singleChoiceArrayList[index].title
                singleTemp.option = this.singleChoiceArrayList[index].option
                singleTemp.answer = this.singleChoiceArrayList[index].answer
                singleTemp.stuAnswer = this.singleChoiceArrayList[index].stuAnswer
                this.reallySingleChoiceArrayList.push(singleTemp)
              }


              for(let index in this.multipleChoiceArrayList){
                let multipleTemp = {
                'id': '',
                'dbType':'',
                'title': '',
                'option': [],
                'answer': '',
                'stuAnswer': [],
                'Achecked': '1',
                'Bchecked': '1',
                'Cchecked': '1',
                'Dchecked': '1',
                'isChecked': '0',
                'checkList':[]

              }
                multipleTemp.id = this.multipleChoiceArrayList[index].id
                multipleTemp.dbType = this.multipleChoiceArrayList[index].dbType
                multipleTemp.title = this.multipleChoiceArrayList[index].title
                multipleTemp.option = this.multipleChoiceArrayList[index].option
                multipleTemp.answer = this.multipleChoiceArrayList[index].answer
                multipleTemp.stuAnswer = this.multipleChoiceArrayList[index].stuAnswer
                this.reallyMultipleChoiceArrayList.push(multipleTemp)
              }

              for(let index in this.tfArrayList){
                let tfTemp = {
                'id': '',
                'dbType':'',
                'title': '',
                'option': [],
                'answer': '',
                'stuAnswer': [],
                'Achecked': '1',
                'Bchecked': '2',
                'Cchecked': '3',
                'Dchecked': '4',
                'isChecked': '0'

              }
                tfTemp.id = this.tfArrayList[index].id
                tfTemp.dbType = this.tfArrayList[index].dbType
                tfTemp.title = this.tfArrayList[index].title
                tfTemp.option = this.tfArrayList[index].option
                tfTemp.answer = this.tfArrayList[index].answer
                tfTemp.stuAnswer = this.tfArrayList[index].stuAnswer
                this.realyTfArrayList.push(tfTemp)
              } 
    },
    handleCheckAllChange(val, index){
      this.reallyMultipleChoiceArrayList[index].isChecked = "1";      
      this.reallyMultipleChoiceArrayList[index].checkList=(val)
      this.multipleChoiceArrayList[index].stuAnswer = val
      console.log(this.reallyMultipleChoiceArrayList[index].checkList)
    },
    checkSingleOption(checked, item,index) {
      if(checked === 1 ){
        item.Bchecked = 0
        item.Cchecked = 0
        item.Dchecked = 0

      }
      if (item.Achecked !== 2) {
        item.Achecked = "0";
      }
      if (item.Bchecked !== 3) {
        item.Bchecked = "0";
      }
      if (item.Cchecked !== 4) {
        item.Cchecked = "0";
      }
      item.Dchecked = "1";

      this.singleChoiceArrayList[index].stuAnswer = []
      this.singleChoiceArrayList[index].stuAnswer.push(checked)
      console.log(this.singleChoiceArrayList[index].stuAnswer+"this.singleChoiceArrayList[index].stuAnswer");
      item.isChecked = "1";
    },
    checkTOption( item,index) {
          item.Achecked = 1
          item.Bchecked = 0
          this.tfArrayList[index].stuAnswer = []
          this.tfArrayList[index].stuAnswer.push("T")
          item.isChecked = '1'
          this.realyTfArrayList[index].isChecked = '1'
          console.log(this.realyTfArrayList[index])
    },
    checkFOption( item,index){
          item.Achecked = 0
          item.Bchecked = 1
          this.tfArrayList[index].stuAnswer = []
          this.tfArrayList[index].stuAnswer.push("F")
          item.isChecked = '1'
          this.realyTfArrayList[index].isChecked = '1'
          console.log(this.realyTfArrayList[index])
                    console.log("this.tfArrayList"+this.tfArrayList)


    },
    exit() {
              this.$confirm('退出将丢失所有答案,确认退出?')
          .then(_ => {
                console.log(_);
                console.log(this.tfArrayList);
                this.tfArrayList = []
                this.singleChoiceArrayList = []
                this.multipleChoiceArrayList = [],
                this.reallySingleChoiceArrayList = []
                this.reallyMultipleChoiceArrayList =[]
                this.realyTfArrayList =[]
                this.$emit("CDis", false);
          })
          .catch(_ => {
            console.log(_)
          });
    },
    num(n) {
      if(n<10){
        n = '0'+n;   
        return n
      }
      return n
    },

    submit(){

      var tag = 1;
      for(let index in this.singleChoiceArrayList){
        if(this.singleChoiceArrayList[index].stuAnswer === null ||this.singleChoiceArrayList[index].stuAnswer.length ===0 ){
          tag = 0 
          break;
        }
      }
      for(let index in this.multipleChoiceArrayList){
        if(this.multipleChoiceArrayList[index].stuAnswer === null ||this.multipleChoiceArrayList[index].stuAnswer.length ===0 ){
          tag = 0 
          break;
        }
      }
      for(let index in this.tfArrayList){
        if(this.tfArrayList[index].stuAnswer === null ||this.tfArrayList[index].stuAnswer.length ===0 ){
          tag = 0 
          break;
        }
      }
      if(tag === 0){          
        alert("还有未完成题目")
      }
      if(tag === 1){
            this.showAnswer = true

      for(let i in this.singleChoiceArrayList){
        let stuOption = this.singleChoiceArrayList[i].answer[0]
        let stuAnswer = ''
        if(stuOption === 'A'){
            stuAnswer = this.singleChoiceArrayList[i].option[0]
        } if(stuOption === 'B'){
            stuAnswer = this.singleChoiceArrayList[i].option[1]
        }if(stuOption === 'C'){
            stuAnswer = this.singleChoiceArrayList[i].option[2]
        }if(stuOption === 'D'){
            stuAnswer = this.singleChoiceArrayList[i].option[3]
        }
        if(stuAnswer === this.singleChoiceArrayList[i].stuAnswer[0])
        {
          this.reallySingleChoiceArrayList[i].isChecked = '2'
        }else{                    
          this.reallySingleChoiceArrayList[i].isChecked = '3'
        }
        console.log("this.singleChoiceArrayList[i].stuAnswer[0]"+this.singleChoiceArrayList[i].stuAnswer[0]);
        console.log("stuAnswer"+stuAnswer);
        console.log("this.reallySingleChoiceArrayList[i].isChecked"+this.reallySingleChoiceArrayList[i].isChecked);
        
      }

      for(let i in this.tfArrayList){
          if(this.tfArrayList[i].answer[0] === this.tfArrayList[i].stuAnswer[0]){
            this.realyTfArrayList[i].isChecked = '2'
          }else{
            this.realyTfArrayList[i].isChecked = '3'
          }
          console.log("this.tfArrayList[i].answer[0]"+this.tfArrayList[i].answer[0]);
          console.log("this.tfArrayList[i].stuAnswer[0]"+this.tfArrayList[i].stuAnswer[0]);
          console.log("///");
      }

        for(let i in this.multipleChoiceArrayList){
          //option存储标准答案的选项字母
          let option = this.multipleChoiceArrayList[i].answer
          //answer存储标准答案的选项内容
          let answer = []
          // for(let si in this.multipleChoiceArrayList[i].option){
          //   console.log("this.multipleChoiceArrayList[i].option"+this.multipleChoiceArrayList[i].option[si]);
          // }
          for(let j in option){
            if(option[j] === 'A'){
              answer.push(this.multipleChoiceArrayList[i].option[0])
            }
            if(option[j] === 'B'){
              answer.push(this.multipleChoiceArrayList[i].option[1])
            }
            if(option[j] === 'C'){
              answer.push(this.multipleChoiceArrayList[i].option[2])
            }
            if(option[j] === 'D'){
              answer.push(this.multipleChoiceArrayList[i].option[3])
            }
          }
          //存储学生做的答案选项内容
          let stuAnswer = this.multipleChoiceArrayList[i].stuAnswer
          let reaStuAnswer = []
          let reaAnswer =[]
          for(let ss in stuAnswer){
            reaStuAnswer.push(stuAnswer[ss])
            console.log("stuAnswer[ss]"+stuAnswer[ss]);
          }
          for(let ss in answer){
            reaAnswer.push(answer[ss])
            console.log("answer[ss]"+answer[ss]);
          }
          // console.log("reaStuAnswer.length"+reaStuAnswer.length);
          // console.log("reaAnswer.length"+reaAnswer.length);
          if(reaStuAnswer.length !== reaAnswer.length){
              console.log("reaStuAnswer.length != reaAnswer.length");
              this.reallyMultipleChoiceArrayList[i].isChecked = '3'
          }else{
            for(let kk in reaStuAnswer){
            if(reaStuAnswer.indexOf(answer[kk]) === -1){
              this.reallyMultipleChoiceArrayList[i].isChecked = '3'
              console.log("this.multipleChoiceArrayList[kk].isChecked = '3'");
              break
            }
            console.log(reaStuAnswer.indexOf(answer[kk]));
          }
          }

        }
        this.$message('提交成功');

      }
      // axios.post('http://localhost:10086/paper/submitPaper',{
      //     'account':this.$store.state.student.account,
      //     'studentId':this.$store.state.student.id,
      //     "paperId":this.paperId,
      //     singleChoiceArrayList:this.singleChoiceArrayList,
      //     multipleChoiceArrayList:this.multipleChoiceArrayList,
      //     tfArrayList:this.tfArrayList
      //   }).then(
      //     response =>{
      //       if(response.data.code === 200){
      //         alert("提交成功")
      //         // this.$emit("CDis", false);
      //       }
      //     }
      //   )
      }
    
  },
};
</script>

<style lang="scss" scoped>
.outer {
  overflow: hidden;
  width: 100vw;
  box-sizing: border-box;
  margin-top: -60px;
  .headerDiv {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 999;
    width: 100%;
    height: 80px;
    background-color: azure;
    border-bottom: 1px solid #ccc;
  }
  header {
    width: 1180px;
    height: 80px;
    line-height: 80px;
    margin: 0 auto;
    display: flex;
    justify-content: space-around;
    h1 {
      font-size: 20px;
      margin-top: 0px;
    }
    .el-button {
      height: 30px;
      line-height: 5px;
      width: 90px;
      border-radius: 50px;
      font-size: 12px;
      color: #fff;
      background-color: #00bfff;
      margin-top: 26px;
    }
  }
  .answerPage {
    width: 1180px;
    margin: 0 auto;
    background-color: #e5f2ff;
    display: flex;
    justify-content: space-around;
    .leftPage {
      width: 240px;
      height: 20%;
      background-color: #fff;
      margin-top: 8%;
      // position: fixed;
      // top: -8px;
      // left: 112px;
      h4 {
        width: 64px;
        text-align: left;
        border-left: 3px solid #000;
        margin-left: 10px;
        margin-top: 20px;
      }
      .tip {
        font-size: 12px;
        font-weight: bold;
        margin-top: 15px;
        margin-left: 5px;
      }
      .num {
        ul {
          display: flex;
          justify-content: flex-start;;
          text-align: center;
          flex-wrap: wrap;
          margin-left: -40px;
        }
      }
    }
    .centerPage {
      width: 800px;
      background-color: #fff;
      margin-top: 8%;
      border: 1px solid transparent;
      h4 {
        text-align: left;
        width: 90%;
        height: 50px;
        background-color: #fff;
        font-weight: 400;
        border: 1px solid transparent;
      }
    }
    .rightPage {
      width: 100px;
      height: 140px;
      background-color: #fff;
      margin-top: 8%;
    }
  }
}
ul.arraylist {
  list-style: none;
  margin-top: -32px;
  li {
    margin-bottom: 15px;
  }
}
.checkLi {
  list-style: none;
  width: 20px;
  height: 20px;
  line-height: 20px;
  font-size: 12px;
  color: #fff;
  border-radius: 50%;
  background-color: #00bfff;
  margin-left: 5px;
  margin-top: 10px;
}
.unCheckLi {
  list-style: none;
  width: 20px;
  height: 20px;
  line-height: 20px;
  font-size: 12px;
  color: rgb(22, 19, 19);
  border-radius: 50%;
  background-color: #faf7f6;
  margin-left: 5px;
  margin-top: 10px;
}
.correctCheckLi {
  list-style: none;
  width: 20px;
  height: 20px;
  line-height: 20px;
  font-size: 12px;
  color: rgb(22, 19, 19);
  border-radius: 50%;
  background-color: #00bfff;
  margin-left: 5px;
  margin-top: 10px;
}
.incorrectCheckLi {
  list-style: none;
  width: 20px;
  height: 20px;
  line-height: 20px;
  font-size: 12px;
  color: rgb(22, 19, 19);
  border-radius: 50%;
  background-color: #f50303;
  margin-left: 5px;
  margin-top: 10px;
}
/deep/.el-radio {
  margin-right: 0 !important;
}
#submitbtn{
  position: relative;
  left: 80px;
}
</style>
