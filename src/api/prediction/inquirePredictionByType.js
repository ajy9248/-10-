import request from '../../utils/request'
export function inquirePredictionByType (type) {
    return request({
        url: '/prediction/PredictionByType',
        method: "post",
        data:{type},
    })
}