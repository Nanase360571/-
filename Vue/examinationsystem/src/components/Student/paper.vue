<!-- 考试系统 -->
<template>
  <div class="exam">


    <div class="main">
      <div class="header-wrapper">
        <div class="inner">
          <el-row>
            <el-col :span="18">
              <div class="grid-content bg-purple ovf left">
                <div class="logo">
                  <router-link to="/">
                    <h1>to wyu</h1>
                  </router-link>
                </div>
                <div class="index">{{title}}</div>
              </div>
            </el-col>
            <el-col :span="6">
              <div class="grid-content bg-purple right ovf">
                <div class="esc" @click="esc">退出</div>
                <div class="name">{{name}}</div>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>


      <div class="mian-body">
        <div class="main-side">
          <div class="title">
            <div class="title_border"></div>
            <div class="title_content">答题卡</div>
          </div>
          <div class="card-content-list">
            <div class="card-content">
              <div class="card-content-title">单选题(共{{examinationData.length}}题，合计{{full_score}}分)</div>
              <div class="box-list">
                <div
                    class="box normal-box question_cbox"
                    v-for="(item,index) in examinationData"
                    :key="index"
                >
                  <div
                      class="checki"
                      :class="{'checked':radio[index]}"
                      v-show="!checkResult"
                  >{{index+1}}</div>
                  <div
                      class="checki checked"
                      :class="{'check-error':radio[index]}"
                      v-show="checkResult"
                  >{{index+1}}</div>
                </div>
              </div>
            </div>
          </div>
        </div>


        <div class="main-center">
          <div class="body-wrapper">
            <div class="questions">
              <div class="questions-title">单选题(共{{examinationData.length}}题，合计{{full_score}}分)</div>
              <div class="questions-content">
                <div class="question-content" v-for="(item,i) in examinationData" :key="i">

                  <div v-if="item.type=='radiogroup'">
                    <div class="exam-question">
                      <span class="question-index ellipsis">{{i+1}}.</span>
                      {{item.title}}
                    </div>
                    <div v-if="!checking">
                      <div class="answers" v-for="(son,index) in item.choices" :key="index">
                        <el-radio-group v-model="radio[i]">
                          <el-radio
                              v-model="radio[i]"
                              :label="son.value"
                              :name="son.text"
                              @change="getIputValue(i)"
                          >{{son.text}}</el-radio>
                        </el-radio-group>
                      </div>
                    </div>
                    <div v-else>
                      <div class="answers" v-for="(son,index) in item.choices" :key="index">
                        <el-radio-group v-model="checkData[i].value">
                          <el-radio
                              v-model="radio[i]"
                              :label="son.value"
                              :name="son.text"
                              @change="getIputValue(i)"
                          >{{son.text}}</el-radio>
                        </el-radio-group>
                      </div>
                    </div>
                  </div>

                  <div v-if="item.type=='imagepicker'">
                    <div class="exam-question">
                      <span class="question-index ellipsis">{{i+1}}.</span>
                      {{item.title}}
                    </div>
                    <div v-if="!checking">
                      <div class="answers" v-for="(son,index) in item.choices" :key="index">
                        <el-radio-group v-model="radio[i]">
                          <el-radio
                              v-model="radio[i]"
                              :label="son.value"
                              :name="son.value"
                              @change="getIputValue(i)"
                          >
                            <img :src="son.imageLink" alt />
                          </el-radio>
                        </el-radio-group>
                      </div>
                    </div>
                    <div v-else>
                      <div class="answers" v-for="(son,index) in item.choices" :key="index">
                        <el-radio-group v-model="checkData[i].value">
                          <el-radio
                              v-model="radio[i]"
                              :label="son.value"
                              :name="son.value"
                              @change="getIputValue(i)"
                          >
                            <img :src="son.imageLink" alt />
                          </el-radio>
                        </el-radio-group>
                      </div>
                    </div>
                  </div>
                  <!-- <div class="analysis" v-show="checkResult">
                    <div class="question-icon-wrapper">
                      <div>
                        <i class="sign icon-right" v-show="signView">正确</i>
                        <i class="sign icon-error" v-show="!signView">错误</i>
                      </div>
                    </div>
                    <div class="analysis-row">
                      <div class="analysis-title">学员答案：</div>
                      <div class="analysis-content error">A</div>
                    </div>
                    <div class="analysis-row">
                      <div class="analysis-title">正确答案：</div>
                      <div class="analysis-content">B</div>
                    </div>
                    <div class="analysis-row">
                      <div class="analysis-title">解释说明：</div>
                      <div class="analysis-content question-analysis">11111111111111111111111111</div>
                    </div>
                  </div>-->
                </div>
              </div>
            </div>
          </div>
        </div>


        <div class="main-right">
          <div class="nav">
            <ul v-show="!checkResult">
              <li class="menu-item">
                <div class="item-label">剩余时间</div>
                <div class="item-data">
                  <Time
                      @show="handInHand"
                      :status="submitView"
                      :examtime="examtime"
                      v-if="examtime!=''"
                  ></Time>
                </div>
              </li>
              <li class="menu-item">
                <div class="item-label">当前进度</div>
                <div class="item-press">
                  <span>{{radio.filter(v=>v).length}}</span>
                  <span>{{examinationData.length}}</span>
                </div>
                <div class="percentage">
                  <el-progress :percentage="percentage" :color="customColor"></el-progress>
                </div>
              </li>
            </ul>
            <!-- <ul v-show="checkResult">
              <li class="menu-item">
                <div class="item-label">考试成绩</div>
                <div class="item-result">90</div>
              </li>
              <li class="menu-item">
                <div class="item-label">考试状态</div>
                <div class="item-satus pass" v-show="passView">通过</div>
                <div class="item-satus unpass" v-show="!passView">未通过</div>
              </li>
              <li class="menu-item">
                <div class="item-label">筛选试题</div>
                <el-select v-model="checkVal" placeholder="请选择">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  ></el-option>
                </el-select>
              </li>
            </ul>-->
          </div>
          <div class="btn" v-show="!checkResult" @click="submit">提交</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import { saveAnswers } from "../../request/api";
import { mapGetters } from "vuex";
import Time from "../../components/module/Time"; // 倒计时组件
// axios.defaults.baseURL="http://47.101.133.168:10086"

export default {
  name: "Exam",
  data() {
    return {
      checkData: [],
      checking: false,
      name: "",
      examtime: "",
      title: "",
      customColor: "#55b6da",
      percentage: 0,
      submitView: false,
      full_score: "",
      signView: false, // 正确与错误
      passView: false, // 通过或者未通过
      checkResult: false, // 左侧栏、右侧栏、答题结果栏
      options: [
        {
          value: "0",
          label: "全部"
        },
        {
          value: "1",
          label: "答对"
        },
        {
          value: "2",
          label: "答错"
        }
      ],
      checkVal: "0",
      examinationData: "",
      allRadio: [], //提交给后台的真题数据
      radio: [], //单选真题答案,
      checkLen: [],
      obj: { value: "" },
      answersData: []
    };
  },
  computed: {
    ...mapGetters(["getInfo"])
  },
  methods: {
    // 退出考试系统
    esc() {
      this.$router.push({
        path: "/"
      });
    },
    // 主动交卷
    submit() {
      this.$confirm("确定交卷吗?", "提示", {
        distinguishCancelAndClose: true,
        confirmButtonText: "交卷",
        cancelButtonText: "不交卷",
        type: "warning"
      })
          .then(() => {
            if (this.checking === false) {
              this.answersData.value = this.radio || "";
              this.upAnswer(1, JSON.stringify(this.answersData)); //提交答案
            } else {
              this.upAnswer(1, JSON.stringify(this.checkData)); //提交答案
            }
            this.submitView = true; // 提示已提交
          })
          .catch(err => {
            console.log(err);
          });
    },
    // 自动交卷（时间到）
    handInHand() {
      // this.signView = true; // 正确与错误
      // this.passView = true; // 通过或者未通过
      // this.checkResult = true; // 左侧栏、右侧栏、答题结果栏
      if (this.checking === false) {
        this.answersData.value = this.radio || "";
        this.upAnswer(1, JSON.stringify(this.answersData)); //提交答案
      } else {
        this.upAnswer(1, JSON.stringify(this.checkData)); //提交答案
      }
    },
    // 提交答案接口
    upAnswer(finish, answers) {
      let postData = {
        exam_id: Number(this.$route.params.id),
        finish: finish,
        answers: answers
      };
      saveAnswers(postData)
          .then(res => {
            if (res.code == 0) {
              console.log("提交/保存答案成功")
            } else if (res.code == 201) {
              this.$message.success({
                message: res.msg,
                offset: 380,
                duration: 1000
              });
            } else {
              this.$message.warning({
                message: res.msg,
                offset: 380,
                duration: 1000
              });
            }
          })
          .catch(err => {
            console.log(err);
          });
    },
    // 获取考试题
    get() {
      if (this.$route.params.id != "" && this.$route.params.id != undefined) {
        axios
            .get("http://47.101.133.168:10086/api/exams/" + this.$route.params.id)
            .then(res => {
              if (res.data.code == 0) {
                let data = res.data.data.item.exercise;
                this.title = res.data.data.item.name;
                this.examtime = this.toHHmmss(
                    this.madeTime(
                        res.data.serverTime,
                        res.data.data.userExam.begin_at,
                        res.data.data.item.duration
                    )
                );
                this.examinationData = data.pages[0].elements;
                this.examinationData.forEach((item, i) => {
                  this.answersData[i] = { value: "" };
                });
                if (res.data.data.userExam.answer != null) {
                  let fobj = JSON.parse(res.data.data.userExam.answer);
                  this.checkData = JSON.parse(res.data.data.userExam.answer);
                  fobj.forEach((item, i) => {
                    if (item.value != "") {
                      this.radio[i] = item;
                    }
                  });
                  this.checking = true;
                  let len =
                      (this.radio.filter(v => v).length /
                          this.examinationData.length) *
                      100;
                  this.percentage = len;
                }
                this.full_score = res.data.data.item.full_score;
              }
            })
            .catch(err => {
              console.log(err);
            });
      }
    },
    // 倒计时处理
    madeTime(serverTime1, begin1, duration1) {
      var serverTime = new Date(serverTime1); // 系统时间
      var duration = duration1; //考试时间
      if (begin1 != null) {
        var begin = new Date(begin1); //开始时间
        var residue = begin.getTime() + duration * 1000 - serverTime.getTime(); // 倒计时
      } else {
        // eslint-disable-next-line no-redeclare
        var residue = duration * 1000 - serverTime.getTime(); // 倒计时
      }
      return residue;
    },
    // 时间戳时分秒
    toHHmmss(data) {
      let date = {};
      let hours = parseInt((data % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
      let minutes = parseInt((data % (1000 * 60 * 60)) / (1000 * 60));
      let seconds = (data % (1000 * 60)) / 1000;
      date.hours = hours;
      date.minutes = minutes;
      date.seconds = seconds;
      return date;
    },
    // 选择操作
    getIputValue(index) {
      this.allRadio[index] = this.radio[index]; // 将数据存入提交给后台的数据中
      let len =
          (this.radio.filter(v => v).length / this.examinationData.length) * 100;
      this.percentage = len;
      if (this.checking === false) {
        this.answersData[index].value = this.radio[index] || "";
        this.upAnswer(0, JSON.stringify(this.answersData)); //提交答案
      } else {
        this.upAnswer(0, JSON.stringify(this.checkData)); //提交答案
      }
    }
  },
  beforeRouteEnter(to, from, next) {
    next(() => {
      // 改变html背景
      document.querySelector("html").style.cssText = `
        background: #ecf1f6;
      `;
    });
  },
  beforeRouteLeave(to, from, next) {
    // 消除html背景
    document.querySelector("html").style.cssText = `background: #fff;`;
    // 中途退出提示
    this.$confirm("确定中途退出吗, 是否继续?", "提示", {
      confirmButtonText: "确定",
      cancelButtonText: "取消",
      type: "warning"
    })
        .then(() => {
          next();
        })
        .catch();
  },
  components: {
    Time
  },
  created() {
    this.get();
  },
  mounted() {
    this.name = this.getInfo.name;
  }
};
</script>


<style scoped lang="scss">
/deep/ .el-progress__text {
  display: none;
}
/deep/ .el-select .el-input__inner:focus {
  border-color: #55b6da;
}
/deep/ .el-select {
  border: none;
}
/deep/ .el-select-dropdown__item.selected {
  color: #55b6da !important;
  font-weight: bold;
}
/deep/ .el-radio__input.is-checked + .el-radio__label {
  color: #55b6da;
}
/deep/ .el-radio__input.is-checked .el-radio__inner {
  border-color: #55b6da;
  background: #55b6da;
}
/deep/ .el-radio__inner:hover {
  border-color: #55b6da;
}
/deep/ .el-radio {
  display: block;
  margin: 20px 0;
}
.inner {
  padding: 0px 90px;
}
.exam {
  background: #ecf1f6;
  height: 100%;
  min-height: 100%;
}
.main {
  .header-wrapper {
    height: 80px;
    width: 100%;
    line-height: 80px;
    background: #ffffff;
    box-shadow: 0 2px 4px 0 rgba(153, 153, 153, 0.1);
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1040;
    .left {
      display: flex;
      align-items: center;
      font-size: 18px;
      .logo {
        width: 150px;
        margin-right: 120px;
        img {
          width: 100%;
        }
      }
    }
    .right {
      div {
        float: right;
        font-size: 16px;
      }
      .esc {
        width: 120px;
        height: 38px;
        line-height: 38px;
        margin: 21px 0;
        color: #fff;
        background: #55b6da;
        border-radius: 30px;
        text-align: center;
        cursor: pointer;
        user-select: none;
        &:hover {
          filter: brightness(80%);
        }
        &:active {
          filter: brightness(60%);
        }
      }
      .name {
        margin-right: 55px;
        font-weight: bold;
      }
    }
  }
  .mian-body {
    .main-side {
      display: inline-block;
      height: calc(100% - 140px);
      position: fixed;
      top: 120px;
      width: 240px;
      left: 90px;
      background: #fff;
      box-shadow: 0 1px 4px 0 rgba(58, 62, 81, 0.1);
      .title {
        position: relative;
        text-align: left;
        margin: 23px 0px 0px 12px;
        .title_border {
          display: inline-block;
          width: 4px;
          height: 26px;
          background: #33394b;
          border-radius: 15px;
          position: absolute;
          top: 0;
          bottom: 0;
          margin: auto;
        }
        .title_content {
          margin-left: 14px;
          font-size: 18px;
          font-weight: 600;
          color: #27274a;
        }
      }
      .card-content-list {
        height: calc(100% - 38px);
        overflow: auto;
        .card-content {
          padding: 0 12px 0 12px;
          position: relative;
          .card-content-title {
            font-size: 14px;
            color: #27274a;
            font-weight: 600;
            padding-bottom: 12px;
            padding-top: 20px;
          }
          .box-list {
            padding-bottom: 0;
            position: relative;
            left: -5px;
            font-size: 0;
            margin-right: -15px;
            .box {
              height: auto;
              line-height: unset;
              position: relative;
              margin-bottom: 15px;
              font-size: 14px;
              width: 35px;
              margin-top: unset;
              margin-right: unset;
              display: inline-block;
              .checki {
                border: 1px solid #dcdfe6;
                color: #dcdfe6;
                width: 27px;
                height: 27px;
                text-align: center;
                display: inline-block;
                line-height: 27px;
                background: #fff;
                border-radius: 50%;
                cursor: pointer;
              }
              .checked {
                color: #fff;
                background: #55b6da;
              }
              .check-error {
                color: #fff;
                background: #ec6941;
              }
            }
          }
        }
      }
    }
    .main-center {
      margin: 120px 230px 0px 360px;
      height: 100vh;
      .body-wrapper {
        color: #27274a;
        width: 100%;
        background: #ffffff;
        border: 1px solid #dedede;
        border-radius: 4px;
        .questions-title {
          font-size: 18px;
          line-height: 25px;
          padding: 18px 20px;
          background: #fafafa;
          border-bottom: 1px solid #dedede;
        }
        .questions-content {
          padding-left: 30px;
          padding-right: 75px;
        }
        .question-content {
          border-bottom: 1px solid #dedede;
          padding: 30px 0;
          position: relative;
          &:last-child {
            border: none;
          }
          .exam-question {
            font-size: 16px;
            line-height: 22px;
            margin-bottom: 10px;
            padding-left: 20px;
            position: relative;
            .question-index {
              color: #55b6da;
              position: absolute;
              left: -25px;
              top: 0;
              display: inline-block;
              width: 40px;
              line-height: 22px;
              text-align: right;
            }
          }
          .analysis {
            overflow: auto;
            background: rgba(222, 222, 222, 0.2);
            border-radius: 4px;
            padding: 15px 20px;
            line-height: 24px;
            margin-top: 10px;
            position: relative;
            .question-icon-wrapper {
              position: absolute;
              right: 10px;
              top: 14px;
              .sign {
                width: 48px;
                height: 28px;
                position: absolute;
                color: #fff;
                top: 10px;
                right: 0;
                font-size: 14px;
                border-radius: 2px;
                line-height: 28px;
                text-align: center;
                font-style: normal;
              }
              .icon-error {
                background: #ec6941;
              }
              .icon-right {
                background: #55b6da;
              }
            }
            .analysis-row {
              font-size: 14px;
              margin-top: 10px;
              min-height: 24px;
              padding-left: 80px;
              padding-right: 60px;
              position: relative;
              .analysis-title {
                position: absolute;
                width: 80px;
                left: 0;
                top: 0;
              }
              .question-analysis {
                text-align: justify;
              }
              .error {
                color: #f72a3a;
                font-weight: bold;
              }
            }
          }
        }
      }
    }
    .main-right {
      right: 90px;
      bottom: 20px;
      position: fixed;
      top: 120px;
      width: 120px;
      .nav {
        color: #27274a;
        line-height: 20px;
        padding: 0 10px;
        background: #ffffff;
        box-shadow: 0 1px 4px 0 rgba(0, 0, 0, 0.1);
        border-radius: 4px;
        margin-bottom: 10px;
        text-align: center;
        .menu-item {
          padding: 14px 0;
          border-bottom: 1px solid #dedede;
        }
        .menu-item:last-child {
          border: none;
        }
        .pass {
          color: #55b6da;
        }
        .item-result,
        .unpass {
          color: rgb(236, 105, 65);
        }
        .item-label {
          margin-bottom: 6px;
        }
        .item-data {
          font-size: 18px;
          line-height: 22px;
          color: #ff0000;
          font-weight: 400;
        }
        .item-press {
          line-height: 17px;
          margin-bottom: 7px;
          color: #27274a;
          font-size: 14px;
          & span:nth-child(1)::after {
            content: "/";
            margin: 0 5px;
          }
        }
      }
      .btn {
        bottom: -10px;
        position: absolute;
        width: 100%;
        margin-bottom: 10px;
        cursor: pointer;
        text-align: center;
        height: 38px;
        line-height: 38px;
        border-radius: 20px;
        color: #fff;
        background: #33394b;
        user-select: none;
        &:hover {
          filter: brightness(120%);
        }
        &:active {
          filter: brightness(60%);
        }
      }
    }
  }
}
</style>



