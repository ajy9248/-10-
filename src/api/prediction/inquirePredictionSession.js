import request from '../../utils/request'
export function inquirePredictionSession () {
    return request({
        url: '/prediction/PredictionSession',
        method: "get",
        data:'',
    })
}