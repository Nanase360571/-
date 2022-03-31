<template>
  <div id="app">
      <!-- 指定组件的呈现位置 -->
      <router-view name="default"></router-view>
  </div>
</template>
<script>
	import Login from './components/Login'

export default {
  name:'App',
  components:{Login},
  
}
</script>
<style>

</style>
