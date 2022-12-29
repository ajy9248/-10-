import request from '../../utils/request'
//进入个人信息页面提交修改好的密码
export function modifyPwd(form) {
    let Data = "uid="+form.username+"&old_pwd="+form.password+"&new_pwd="+form.newPassword+"&check_pwd="+form.checkPassword;
    Data = Data.replace(/\s*/g,"");
    console.log(Data);
    return request({
        url: '/user/ModifyPwd',
        method: "post",
        data: Data,
    })
}
