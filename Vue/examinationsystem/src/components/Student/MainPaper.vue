<template>
  <div class="outer">
      <div class="headerDiv">
        <header>
        <h1>五邑大学考试系统</h1>
        <div class="paperName">{{paperName}}</div>
        <div class="stuName">考生姓名：{{stuName}}</div>
        <el-button @click="exit">退出</el-button>
      </header>
      </div>
      <div class="answerPage">
        <div class="leftPage">
          <h4>答题卡</h4>
          <div class="tip">单选题(共2题,合计100分)</div>
          <div class="num">
            <ul>
              <li>1</li>
              <li>2</li>
            </ul>
          </div>
        </div>
        <div class="centerPage">
          <h4>单选题(共2题,合计100分)</h4>
          <ul>
            <li>
              <div>1.问题1</div>
              <el-radio-group v-model="radio">
                <el-radio :label="3">备选项1</el-radio>
                <el-radio :label="6">备选项2</el-radio>
                <el-radio :label="9">备选项3</el-radio>
              </el-radio-group>
            </li>
            <!-- <li>1.问题1</li>
            <li>1.问题1</li>
            <li>1.问题1</li> -->
          </ul>
        </div>
        <div class="rightPage">
            <div>剩余时间</div>
            <div>09:10:09</div>
        </div>
      </div>
  </div>
</template>

<script>
export default {
  name: 'MainPaper',
  props:['examDialogVisible'],
  data () {
    return {
      paperName: '数学考试',
      stuName: '张三',
      radio: 3
    }
  },
  methods:{
    exit(){
      console.log("changExamDialogVisible")
      this.$emit('CDis', false)
    }
  }
}
</script>

<style lang="scss" scoped>
.outer{
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  .headerDiv{
    width: 100%;
    height: 80px;
    background-color: azure;
    border-bottom: 1px solid #ccc;
  }
  header{

    width: 1180px;
    height: 80px;
    line-height: 80px;
    margin: -60px auto;
    display: flex;
    justify-content: space-around;
    h1{
      font-size: 20px;
    }
    .el-button{
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
  .answerPage{
    width: 100%;
    height: 100%;
    margin: 0 auto;
    background-color: #e5f2ff;
    display: flex;
    justify-content: space-around;
    .leftPage{
      width: 200px;
      height: 380px;
      background-color: #fff;
      margin-top: 40px;
      h4{
        width: 64px;
        text-align: center;
        border-left: 3px solid #000;
        margin-left: 10px;
        margin-top: 20px;
      }
      .tip{
        font-size: 12px;
        font-weight: bold;
        margin-top: 15px;
        margin-left: 0px;
      }
      .num{
        ul{
          display: flex;
          li{
            list-style: none;
            width: 20px;
            height: 20px;
            line-height: 20px;
            font-size: 12px;
            color: #fff;
            border-radius: 50%;
            background-color: #00bfff;
            margin-left: 3px;
            margin-top: 10px;
          }
        }
      }
    }
    .centerPage{
      width: 60%;
      height: 800px;
      background-color: #fff;
      margin-top: 40px;
      border: 1px solid transparent;
      h4{
        width: 100%;
        height: 20px;
        background-color: #fff;
        font-weight: 400;
        border: 1px solid transparent;
      }
    }
    .rightPage{
      width: 120px;
      height: 140px;
      background-color: #fff;
      margin-top: 40px;

    }
  }
}
</style>
