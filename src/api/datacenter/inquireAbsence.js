import request from '../../utils/request'
//查询缺考人数
export function inquireAbsence (session) {
    return request({
        url: '/score/Absence',
        method: "get",
        data: {session},
    })
}