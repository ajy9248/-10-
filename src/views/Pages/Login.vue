<template>
    <!--有且只有一个根节点-->
    <!-- login-wrap:图片样式-->
    <div class="login-wrap">
        <el-form class="login-container">
            <h1 class="title">CSP考试预报名系统</h1>
            <el-form-item label="">
                <!--placeholder：文本框内提示 -->
                <el-input type="text" placeholder="请输入账号" v-model="username" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="">
                <el-input type="password" placeholder="请输入密码" v-model="password" autocomplete="off"></el-input>
            </el-form-item>
            <el-button type="primary" @click="SubmitForm" style="width: 100%;margin-bottom: 10px">登录</el-button>
        </el-form>
    </div>
</template>

<script>
    import jsCookie from 'js-cookie';

    export default{
        name:'Login',
        data:function(){
            return {
                username:'',
                password:''
            }
        },
        methods:{
            async SubmitForm() {
                const { form } = this;
                //eslint-disable-next-line no-unused-vars
                const { data } = await CheckLogin({
                    UserName: form.UserName,
                    PassWord: form.PassWord,
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
