import request from '../../utils/request'
//进入个人信息页面提交修改好的密码
export function modifyPwd(form) {
    return request({
        url: '/self/ModifyPwd',
        method: "post",
        data: {form},
    })
}
