//检查登录用户是否存在于数据库中
import request from '../utils/request'
export function CheckLogin (username,password) {
    let Data = "uid="+username+"&pwd="+password;
    Data = Data.replace(/\s*/g,"");
    return request({
        url: '/user/login',
        method: "post",
        data: Data
    })
}
