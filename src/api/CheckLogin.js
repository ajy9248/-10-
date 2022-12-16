//检查登录用户是否存在于数据库中
import request from '../utils/request'
export function CheckLogin (user) {
    if(user.username == ''){
        alert('请填写用户名');
        return;
      }
      if(user.password == ''){
        alert('请填写密码');
        return;
    }
    return request({
        url: '/login/check',
        method: "post",
        data: {
            user
        }
    })
}
