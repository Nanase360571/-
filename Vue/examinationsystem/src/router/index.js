import Vue from 'vue'
import VueRouter from 'vue-router'

import MyTest from '../components/MyTest' 
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
