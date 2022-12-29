import request from '../../utils/request'
//告知后端需将所有全局变量session的预报名学生的免费次数减一，将session置为-1
export function endSession() {
    return request({
        url: '/prediction/SubFreeChance',
        method: "get",
        data:'',
    })
}