import request from '../../utils/request'
//进入个人信息界面，显示个人信息
export function selfInfo() {
    return request({
        url: '/self/SelfInfo',
        method: "get",
        data: '',
    })
}