import request from '../utils/request'
//查询不同类型的报名人数，按照从免费到付费的顺序返回
export function inquireSumByType () {
    return request({
        url: '/prediction/SumByType',
        method: "get",
        data: '',
    })
}