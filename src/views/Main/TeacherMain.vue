<!-- eslint-disable vue/no-deprecated-slot-attribute -->
<!-- eslint-disable vue/no-parsing-error -->
<template>
  <div class="common-main">
    <el-container>
      <el-header class="common-header flex-float">
        <div class="flex">
          <img class="logo" src="../../assets/csp.png" alt="">
          <h1 style="color: #eaeaea;margin-top: 35px;margin-left: 10px;font-size: x-large" class="title">CSP预报名系统</h1>
        </div>
        <el-button type="danger" @click="logout()">退出 </el-button>
      </el-header>
      <el-container class="aside">
        <el-aside class="common-aside" width="200px">
          <el-menu @select="menuClick" background-color="#000" text-color="#fff" :router="true" text-align: center>
            <el-menu-item>
              <el-menu-item index="/InquireScores">成绩查询</el-menu-item>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>
<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names, no-undef
  name: "main",
  methods: {
    menuClick (index) {
      this.$router.push(index);
    },
    logout(){
      localStorage.clear();
      this.$router.push({ name: 'login' })
    }
  }
}
</script>
<style>
.el-container {
  height: 100vh;
  overflow: hidden;
}
.aside scope{
  height:700px;
}
.el-header {
  background: rgb(23, 44,60);
}
.el-aside {
  background: rgb(240, 255, 255);
  height:700px;
}
.el-menu-item {
  font-size: 12px;
  height: 35px;
  line-height: 35px;
  min-height:35px;
  background-color: rgb(39, 72, 98);
}

.logo {
  width: 60px;
  height: 60px;
}
.title {
  color:rgb(255, 255, 255);
  font-size: larger;
}
</style>
