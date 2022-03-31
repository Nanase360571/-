<template>
    <div>

      <el-row>
        <el-col :span="8" v-for="(o, index) in 1" :key="o" :offset="index > 0 ? 2 : 0">
          <el-card :body-style="{ padding: '0px' }">
            <div style="padding: 14px;">
              <span>Linux测试</span>
              <div class="bottom clearfix">
                <time class="time">{{ currentDate }}</time>
                <el-button type="text" class="button">开始测试</el-button>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
</template>

<script>
import axios from "axios";

export default {
  name:"MyTest",
  data() {
    return {
      currentDate: new Date(),
      subject:'',
      startData:'',
      endDate:'',

    };
  },
  mounted() {
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
