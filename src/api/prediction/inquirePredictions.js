import request from '../../utils/request'
export function inquirePredictions () {
    return request({
        url: '/prediction/PredictionInfo',
        method: "get",
        data: '',
    })
}