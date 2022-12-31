<template>
    <div class="login-wrap">
        <h1 style="font-size: 40px" class="title">CSP考试预报名系统</h1>
        <el-form class="login-container" id="form" ref="loginForm" :model="form" :rules="rules">
            <el-form-item prop="username">
                <!--placeholder：文本框内提示 -->
                <el-input style="margin-top: 20px" placeholder="请输入账号" v-model="form.username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item prop="password">
                <el-input placeholder="请输入密码" v-model="form.password" autocomplete="off"></el-input>
            </el-form-item>
            <el-button type="primary" @click="SubmitForm" style="width: 50%;margin-bottom: 20px;margin-top: 20px">登录</el-button>
        </el-form>
    </div>
</template>

<script>
    import jsCookie from 'js-cookie';
    import {CheckLogin} from "@/api/CheckLogin";

    export default{
        data(){
            return {
                form:{
                    username: "",
                    password: "",
                },
                rules: {
                    username: [{required: true, message: "请输入账号", trigger: "blur"}],
                    password: [{required: true, message: "请输入密码", trigger: "blur"}],
                },
            }
        },
        methods:{
            async SubmitForm() {
                const { form } = this;
                if(form.username === ''){
                    alert('请填写用户名');
                    return;
                }else if(form.password === ''){
                    alert('请填写密码');
                    return;
                }
                const { data } = await CheckLogin(form.username,form.password);

                if (data.state === 200) {
                    //存储用户信息
                  // window.sessionStorage.setItem('userToken',JSON.stringify(data.token))
                  // window.sessionStorage.setItem('userdata',JSON.stringify(data.data))
                  if(data.data.type === "管理员") {
                    console.log("1")
                    this.$router.push('/main')
                  }else if(data.data.type === "学生"){
                    console.log("2")
                    // console.log(form.username)
                    // console.log(data.data.freeChance)
                    jsCookie.set('username', form.username)
                    jsCookie.set('freechance',data.data.freeChance)
                    this.$router.push('/StudentMain')
                  }else {
                    console.log("3")
                    this.$router.push('/TeacherMain')
                  }
                }
                else {
                    alert(data.message);
                }
            }
        }
    }

</script>

<style>
    .login-wrap {
        box-sizing: border-box;
        width: 100%;
        height: 100%;
        padding-top: 10%;
        background-image: url("../../assets/login.jpg");
        background-repeat: no-repeat;
        background-position: center right;
        background-size: 100%;
    }

    .login-container {
        border-radius: 10px;
        margin: 0px auto;
        width: 350px;
        padding: 30px 35px 15px 35px;
        background: #fff;
        border: 1px solid #eaeaea;
        text-align: center;
        box-shadow: 0 0 20px 2px rgba(0, 0, 0, 0.1);
    }

    .title {
        margin: 0px auto 40px auto;
        text-align: center;
        color: #505458;
    }
</style>