import request from '../../utils/request'
//获取所有预报名信息
export function inquirePredictions () {
    return request({
        url: '/prediction/PredictionInfo',
        method: "get",
        data: '',
    })
}
