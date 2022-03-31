import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    student : {
      id: '',
      account: '',
      password:'',
      name:''
    },
    teacher : {
      id: '',
      account: '',
      password:'',
      name:''
    }
  },
  mutations: {
  },
  actions: {
  },
  modules: {
  }
})
