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
                //eslint-disable-next-line no-unused-vars
                const { data } = await CheckLogin({
                    username: form.username,
                    password: form.password,
                });
                jsCookie.set('username',this.username)
                if (data.code == 200) {
                    //存储用户信息
                    window.localStorage.setItem('userToken',JSON.stringify(data.token))
                    window.localStorage.setItem('userdata',JSON.stringify(data.data))
                    this.$router.push('/Main')
                }
                else {
                    alert(data.msg);
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
        background-image: url("../assets/login.jpg");
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
