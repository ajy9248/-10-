<template>
    <div class="self" style="margin: auto">
        <h3 style="margin-top: 40px">学   号：  {{showUserName}}</h3>
        免费报名次数：  {{showUserFreeChance}}
        <el-form :model="form" ref="pwdForm" :rules="rules" style="margin-top: 20px">
            <el-form-item label="原密码" prop="password">
                <el-input style="margin-left: 15px" v-model="form.password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
                <el-input style="margin-left: 15px" v-model="form.newPassword" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPassword">
                <el-input v-model="form.checkPassword" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <span class="dialog-footer">
            <el-button type="primary" style="margin-bottom: 20px" @click="onSubmit('pwdForm')">提 交</el-button>
        </span>
    </div>
</template>

<script>
    import jsCookie from 'js-cookie';
    //import { modifyPwd } from '@/api/self/modifyPwd';
    //import pwd from "@/api/pwd.js";
    export default {
        name: "selfcenter",
        data:function(){
            // 校验原密码
            let validatePass = (rule, value, callback) => {
                let userdata = JSON.parse(localStorage.getItem("sms-user"));
                userdata.pwd = this.form.password;
                pwd.getPass(userdata).then((res) => {
                    if (res.data.flag) {
                        callback();
                    } else {
                        callback(new Error(res.data.message));
                    }
                });
            };
            // 检测新密码和确认密码一致性
            let checkpass = (rule, value, callback) => {
                if (value == this.form.newPassword) {
                    callback();
                } else {
                    callback(new Error("密码不一致"));
                }
            };
            return {
                rules: {
                    password: [
                        { required: true, message: "请输入原密码", trigger: "blur" },
                        { validator: validatePass, message: "原密码不正确", trigger: "blur" },
                    ],
                    newPassword: [
                        { required: true, message: "请输入新密码", trigger: "blur" },
                    ],
                    checkPassword: [
                        { required: true, message: "不能为空", trigger: "blur" },
                        { validator: checkpass, trigger: "blur" },
                    ],
                },
                form: {
                    username: "",
                    freechance: "",
                    password: "",
                    newPassword: "",
                    checkPassword: "",
                },
            }
        },
        computed:{
            showUserName(){
                return jsCookie.get('username')
            },
            showUserFreeChance(){
                return jsCookie.get('freechance')
            }
        },
        methods:{
            toAmend() {
                this.$refs.resetForm.validate(valid => {
                    if (valid) {
                        api.self.modifyPwd(this.pwdForm)
                            .then(res => {
                                if(res.code === 2){
                                    this.$message({
                                        message: res.msg,
                                        type: "error",
                                        duration: "2000"
                                    });
                                    return false;
                                }
                                if (res.code === 0) {
                                    this.$message.success("修改成功,3秒后跳转到登录页！");
                                    setTimeout(() => {
                                        this.logout();//调用跳转到登陆页的方法
                                    }, 3000);
                                }
                                ic
                            })
                            .catch(() => {});
                    }
                });
            },
            //这是修改成功后重新返回登陆页的方法，看个人需要自行调整
            async logout() {
                await this.$store.dispatch("user/logout");
                this.$router.push(`/login`);
            }
        }
    }
</script>

<style scoped>
    .self-title{
        text-align: center;
        margin: 0px 0px 0px 0px;
        color: #303133;
    }
.self{
    width: 350px;
    padding: 0px 35px 15px 35px;
}

</style>