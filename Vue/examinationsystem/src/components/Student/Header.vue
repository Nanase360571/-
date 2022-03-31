<template>
  <div class="outer">
      <div class="headerDiv">
        <header>
        <h1>五邑大学考试系统</h1>
        <div class="paperName">{{paperName}}</div>
        <div class="stuName">考生姓名：{{stuName}}</div>
        <span>考试剩余时间 00:12:03</span>
        <el-button>退出</el-button>
      </header>
      </div>
            <div style="clear:both"></div>

      <div class="answerPage">
        <div class="leftPage">
          <h4>答题卡</h4>
          <div class="tip">单选题(共2题,合计100分)</div>
          <div class="num">
            <ul v-show="text.length">
                <li v-for="(item,index) in text" :key="index" :class="{'checkLi': item.isChecked==='1', 'unCheckLi': item.isChecked!=='1'}">
                  {{index+1}}
                </li>
            </ul>
          </div>
        </div>
        <div class="centerPage">
          <h4 style="text-align:left; margin-left:10px; font-weight:bold|bolder "><b>单选题(共2题,合计100分)</b></h4>
            <ul v-show="text.length">
              <li v-for="(item,index) in text" :key="index" style="text-align:left; margin-left:10px">
                <span>{{index+1}}.{{item.title}}</span><br>
                <el-radio v-model="item.Achecked" @click.native="checkOption(item.Achecked,item)"></el-radio><span>(A){{item.A}}</span><br>
                <el-radio v-model="item.Bchecked" @click.native="checkOption(item.Bchecked,item)"></el-radio><span>(B){{item.B}}</span><br>
                <el-radio v-model="item.Cchecked" @click.native="checkOption(item.Cchecked,item)"></el-radio><span>(C){{item.C}}</span><br>
                <el-radio v-model="item.Dchecked" @click.native="checkOption(item.Dchecked,item)"></el-radio><span>(D){{item.D}}</span><br>
              </li>
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
const cityOptions = ['(A)上海', '(B)北京', '(C)广州', '(D)深圳']
export default {
  name: 'Header',
  data () {
    return {
      checkedCities: ['上海', '北京'],
      paperName: '数学考试',
      stuName: '张三',
      radio: 3,
      cities: cityOptions,
      text: [{
        'title': '家在哪里?',
        'A': '上海',
        'B': '北京',
        'C': '广州',
        'D': '深圳',
        'Achecked': '1',
        'Bchecked': '1',
        'Cchecked': '1',
        'Dchecked': '1',
        'isChecked': '0'
      }, {
        'title': '家在哪里2?',
        'A': '上海2',
        'B': '北京2',
        'C': '广州2',
        'D': '深圳2',
        'Achecked': '1',
        'Bchecked': '1',
        'Cchecked': '1',
        'Dchecked': '1',
        'isChecked': '0'

      }, {
        'title': '家在哪里3?',
        'A': '上海3',
        'B': '北京3',
        'C': '广州3',
        'D': '深圳3',
        'Achecked': '1',
        'Bchecked': '1',
        'Cchecked': '1',
        'Dchecked': '1',
        'isChecked': '0'

      }, {
        'title': '家在哪里3?',
        'A': '上海3',
        'B': '北京3',
        'C': '广州3',
        'D': '深圳3',
        'Achecked': '1',
        'Bchecked': '1',
        'Cchecked': '1',
        'Dchecked': '1',
        'isChecked': '0'

      }, {
        'title': '家在哪里3?',
        'A': '上海3',
        'B': '北京3',
        'C': '广州3',
        'D': '深圳3',
        'Achecked': '1',
        'Bchecked': '1',
        'Cchecked': '1',
        'Dchecked': '1',
        'isChecked': '0'

      }, {
        'title': '家在哪里3?',
        'A': '上海3',
        'B': '北京3',
        'C': '广州3',
        'D': '深圳3',
        'Achecked': '1',
        'Bchecked': '1',
        'Cchecked': '1',
        'Dchecked': '1',
        'isChecked': '0'

      }, {
        'title': '家在哪里3?',
        'A': '上海3',
        'B': '北京3',
        'C': '广州3',
        'D': '深圳3',
        'Achecked': '1',
        'Bchecked': '1',
        'Cchecked': '1',
        'Dchecked': '1',
        'isChecked': '0'

      }, {
        'title': '家在哪里3?',
        'A': '上海3',
        'B': '北京3',
        'C': '广州3',
        'D': '深圳3',
        'Achecked': '1',
        'Bchecked': '1',
        'Cchecked': '1',
        'Dchecked': '1',
        'isChecked': '0'

      }, {
        'title': '家在哪里3?',
        'A': '上海3',
        'B': '北京3',
        'C': '广州3',
        'D': '深圳3',
        'Achecked': '1',
        'Bchecked': '1',
        'Cchecked': '1',
        'Dchecked': '1',
        'isChecked': '0'

      }, {
        'title': '家在哪里3?',
        'A': '上海3',
        'B': '北京3',
        'C': '广州3',
        'D': '深圳3',
        'Achecked': '1',
        'Bchecked': '1',
        'Cchecked': '1',
        'Dchecked': '1',
        'isChecked': '0'

      }, {
        'title': '家在哪里3?',
        'A': '上海3',
        'B': '北京3',
        'C': '广州3',
        'D': '深圳3',
        'Achecked': '1',
        'Bchecked': '1',
        'Cchecked': '1',
        'Dchecked': '1',
        'isChecked': '0'

      }, {
        'title': '家在哪里3?',
        'A': '上海3',
        'B': '北京3',
        'C': '广州3',
        'D': '深圳3',
        'Achecked': '1',
        'Bchecked': '1',
        'Cchecked': '1',
        'Dchecked': '1',
        'isChecked': '0'

      }]
    }
  },
  methods: {
    checkOption (checked, item) {
      if (item.Achecked !== checked) {
        item.Achecked = '0'
      }
      if (item.Bchecked !== checked) {
        item.Bchecked = '0'
      }
      if (item.Cchecked !== checked) {
        item.Cchecked = '0'
      }
      if (item.Dchecked !== checked) {
        item.Dchecked = '0'
      }
      item.isChecked = '1'
    }
  }
}
</script>

<style lang="scss" scoped>
.outer{
  overflow:hidden;
  width: 100%;
  box-sizing: border-box;
  margin-top: -60px;
  .headerDiv{
    position:fixed;
    top:0;
    z-index:999;
    width: 100%;
    height: 13%;
    background-color: azure;
    border-bottom: 1px solid #ccc;
  }
  header{
    width: 1180px;
    height: 80px;
    line-height: 80px;
    margin: 0 auto;
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
    width: 1180px;
    margin: 0 auto;
    background-color: #e5f2ff;
    display: flex;
    justify-content: space-around;
    .leftPage{
      width: 200px;
      height: 380px;
      background-color: #fff;
      margin-top: 8%;
      h4{
        width: 64px;
        text-align: left;
        border-left: 3px solid #000;
        margin-left: 10px;
        margin-top: 20px;
      }
      .tip{
        font-size: 12px;
        font-weight: bold;
        margin-top: 15px;
        margin-left: -48px;
      }
      .num{
        ul{
          display: flex;
          flex-wrap:wrap;
        }
      }
    }
    .centerPage{
      width: 600px;
      background-color: #fff;
      margin-top: 8%;
      border: 1px solid transparent;
      h4{
        text-align: left;
        width: 90%;
        height: 50px;
        background-color: #fff;
        font-weight: 400;
        border: 1px solid transparent;
      }
    }
    .rightPage{
      width: 100px;
      height: 140px;
      background-color: #fff;
      margin-top: 8%;
    }
  }
}
ul{list-style: none;}
.checkLi{
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
.unCheckLi{
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
</style>
