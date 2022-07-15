import Vue from 'vue'
import App from './App.vue'
import store from './store'
import router from './router'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import global from "@/js/global";
import Plugin from 'v-fit-columns';


Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.use(Plugin);
Vue.prototype.$global_msg = global;

new Vue({
  store,
  router,
  render: h => h(App)
}).$mount('#app')
