<template>
  <div>
      <h1>TeacherTest</h1>
  </div>
</template>

<script>
import axios from "axios";
export default {
  name:"TeacherCourse",
  data() {
    return {

    };
  },
  mounted() {
    console.log("TeacherCourse")
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
