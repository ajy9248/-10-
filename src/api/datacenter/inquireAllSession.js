import request from '../../utils/request'
//查询所有可选择的考试场次，返回进下拉选择框中
export function inquireAllSession () {
    return request({
        url: '/score/session',
        method: "get",
        data:'',
    })
}