import Vue from 'vue'
import VueRouter from 'vue-router'

import MyTest from '../components/Student/MyTest' 
import Main from '../components/Main'
import Login from '../components/Login'
import StudentMain from '../components/Student/StudentMain'
import MyExam from "@/components/Student/MyExam";
import TeacherMain from "../components/Teacher/TeacherMain"
import TeacherCourse from "@/components/Teacher/TeacherCourse";
import FoundPaper from "@/components/Teacher/FoundPaper";
import TeacherDB from "@/components/Teacher/TeacherDB";
import TeacherTest from "@/components/Teacher/TeacherTest";
import TeacherPaper from "@/components/Teacher/TeacherPaper";
import ClassesMajorTarget from "@/components/Teacher/ClassesMajorTarget";
import AdminMain from "../components/Admin/AdminMain"
import AdminCourse from "../components/Admin/AdminCourse"
import AdminTeacher from "../components/Admin/AdminTeacher"
import AdminMajor from "../components/Admin/AdminMajor"
import AdminClasses from "../components/Admin/AdminClasses"
import AdminMajorCourseTeacher from "../components/Admin/AdminMajorCourseTeacher"
Vue.use(VueRouter)

const routes = [
    //默认页面，路由到登录页面
  {
    path: '/',
    name: 'default',
    redirect: '/Login',
  },
//container，内容页面
  {
    path: "/Main",
    name: 'Main',
    component: Main,
    beforeEnter: (to, from, next) => {
      console.log('前置路由守卫', to, from)
      if (1===1) { //判断是否需要鉴权
        if (localStorage.getItem('Authorization') !== 'atguigu') {
          next()
        } else {
          alert('学校名不对，无权限查看！')
        }
      } else {
        next()
      }
    }
  },
    //登录页面
    {
    path: "/Login",
    name: 'Login',
    component: Login
  },
    //学生的Container页面，即考试页面和测试页面
  {
    path: "/StudentMain",
    name: "StudentMain",
    component: StudentMain,
    redirect: "/StudentMain/MyExam",
    children:[
      {
        name:"test",
        path:"/StudentMain/MyExam",
        components: {exam:MyExam},
        beforeEnter: (to, from, next) =>{
          console.log("enter")
          next();
        }
      },
      {
        path:"/StudentMain/MyTest",
        components: {test:MyTest},
        beforeEnter: (to, from, next) =>{
          console.log("enter")
          next();
        }
      }
    ]
  },
  //管理员
  {
    path:"/AdminMain",
    name:"AdminMain",
    component: AdminMain,
    redirect: "/AdminMain/AdminCourse",
    children: [
      {
        name:'AdminCourse',
        path: '/AdminMain/AdminCourse',
        components: {AdminCourse:AdminCourse},
        beforeRouteEnter(to,from,next){
          console.log("enter AdminCourse")
          next();
        }
      },
      {
        name:'AdminTeacher',
        path: '/AdminMain/AdminTeacher',
        components: {AdminTeacher:AdminTeacher},
        beforeRouteEnter(to,from,next){
          console.log("enter AdminTeacher")
          next();
        }
      },
      {
        name:'AdminMajor',
        path: '/AdminMain/AdminMajor',
        components: {AdminMajor:AdminMajor},
        beforeRouteEnter(to,from,next){
          console.log("enter AdminMajor")
          next();
        }
      },
      {
        name:'AdminClasses',
        path: '/AdminMain/AdminClasses',
        components: {AdminClasses:AdminClasses},
        beforeRouteEnter(to,from,next){
          console.log("enter AdminClasses")
          next();
        }
      },
      {
        name:'AdminMajorCourseTeacher',
        path: '/AdminMain/AdminMajorCourseTeacher',
        components: {AdminMajorCourseTeacher:AdminMajorCourseTeacher},
        beforeRouteEnter(to,from,next){
          console.log("enter AdminMajorCourseTeacher")
          next();
        }
      },
    ]
  },
  //教师
  {
    path:"/TeacherMain",
    name:"TeacherMain",
    component: TeacherMain,
    redirect: "/TeacherMain/TeacherCourse",
    children: [
      {
        name:'TeacherCourse',
        path: '/TeacherMain/TeacherCourse',
        components: {TeacherCourse:TeacherCourse},
        beforeRouteEnter(to,from,next){
          console.log("enter TeacherCourse")
          next();
        }
      },
      {
        name:'TeacherTest',
        path: '/TeacherMain/TeacherTest',
        components: {TeacherTest:TeacherTest},
        beforeRouteEnter(to,from,next){
          console.log("enter TeacherTest")
          next();
        }

      },
      {
        name:'TeacherPaper',
        path: '/TeacherMain/TeacherPaper',
        components: {TeacherPaper:TeacherPaper},
        beforeRouteEnter(to,from,next){
          console.log("enter TeacherPaper")
          next();
        }

      },
      {
        name:'TeacherDB',
        path: '/TeacherMain/TeacherDB',
        components: {TeacherDB:TeacherDB},
        beforeRouteEnter(to,from,next){
          console.log("enter TeacherDB")
          next();
        }

      }
      ,
      {
        name:'FoundPaper',
        path: '/TeacherMain/FoundPaper',
        components: {FoundPaper:FoundPaper},
        beforeRouteEnter(to,from,next){
          console.log("enter FoundPaper")
          next();
        }

      },{
        name:'ClassesMajorTarget',
        path: '/TeacherMain/ClassesMajorTarget',
        components: {ClassesMajorTarget:ClassesMajorTarget},
        beforeRouteEnter(to,from,next){
          console.log("enter FoundPaper")
          next();
        }

      },
    ]
  }
]

const router = new VueRouter({
  routes
})

export default router
