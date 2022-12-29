<template>
    <div class="self" style="margin: auto">
        <h3 style="margin-top: 40px">学   号：  {{showUserName}}</h3>
        免费报名次数：  {{showUserFreeChance}}
        <el-form :model="resetPwdForm" ref="pwdForm" :rules="rules" style="margin-top: 20px">
            <el-form-item label="原密码" prop="password">
                <el-input style="margin-left: 15px" v-model="resetPwdForm.password" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="新密码" prop="newPassword">
                <el-input style="margin-left: 15px" v-model="resetPwdForm.newPassword" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="确认密码" prop="checkPassword">
                <el-input v-model="resetPwdForm.checkPassword" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <span class="dialog-footer">
            <el-button type="primary" style="margin-bottom: 20px" @click="submitForm">提 交</el-button>
        </span>
    </div>
</template>

<script>
    import jsCookie from 'js-cookie';
    import { modifyPwd } from '@/api/self/modifyPwd';
    export default {
        name: "self_center",
        data:function(){
            // 检测新密码和确认密码一致性
            let checkpass = (rule, value, callback) => {
                if (value == this.resetPwdForm.newPassword) {
                    callback();
                } else {
                    callback(new Error("密码不一致"));
                }
            };
            return {
                rules: {
                    password: [
                        { required: true, message: "请输入原密码", trigger: "blur" },
                    ],
                    newPassword: [
                        { required: true, message: "请输入新密码", trigger: "blur" },
                    ],
                    checkPassword: [
                        { required: true, message: "不能为空", trigger: "blur" },
                        { validator: checkpass, trigger: "blur" },
                    ],
                },
                freechance: "",
                resetPwdForm: {
                    username: "",
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
            async submitForm() {
                this.resetPwdForm.username = jsCookie.get('username')
                const form = this.resetPwdForm;
                const { data } = await modifyPwd(form);
                console.log(data)
                if (data.state === 200) {
                    alert("修改成功，请重新登录！");
                    this.$router.push(`/login`);
                }
                else {
                    alert(data.msg);
                }
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