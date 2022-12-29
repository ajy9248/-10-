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
        <div class="demo-basic--circle">
          <div class="block">
            <el-avatar :size="40" :src="avatar" :class="['avatar-info']">
            </el-avatar>
          </div>
        </div>
        <el-button type="danger" @click="logout()">退出 </el-button>
      </el-header>
      <el-container class="aside">
        <el-aside class="common-aside" width="200px">
          <el-menu
                  background-color="#000" text-color="#fff" active-text-color="#ffd04b"
                  :default-active="$route.path"
                  @select="menuClick"
                  :router="true"
          >
            <el-menu-item>
              <el-menu-item style="font-size: large" index="/StudentInfo">学生管理</el-menu-item>
            </el-menu-item>
            <el-menu-item>
              <el-menu-item style="font-size: large" index="/TeacherInfo">教师管理</el-menu-item>
            </el-menu-item>
            <el-menu-item>
              <el-menu-item style="font-size: large" index="/PredictionInfo">预报名管理</el-menu-item>
            </el-menu-item>
            <el-menu-item>
              <el-menu-item style="font-size: large" index="/ScoreInfo">成绩管理</el-menu-item>
            </el-menu-item>
            <el-menu-item>
              <el-menu-item style="font-size: large" index="/DataCenter">数据中心</el-menu-item>
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
    data(){
      return{
        activeIndex: '',
        avatar: 'https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png'
      }
    },
    watch: {
      $route () {
        this.menuClick(this.activeIndex)
      }
    },
    methods: {
      menuClick (index) {
        this.activeIndex = index
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
    line-height: 60px;
  }
  .avatar-info {
    margin-top: 25px;
    margin-left: 1100px;
    cursor: pointer;
  }
  .el-aside {
    background: rgb(39, 72, 98);
    text-align: center;
  }
  .el-menu-item {
    font-size: 12px;
    height: 80px;
    line-height: 80px;
    min-height:80px;
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
  .el-main{
    background-image: url("../../assets/login.jpg");
  }
</style>
