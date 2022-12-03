import request from '../../utils/request'
export function inquirePredictionById (uid) {
    return request({
        url: '/prediction/PredictionById',
        method: "post",
        data:{uid},
    })
}