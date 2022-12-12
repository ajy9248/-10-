import request from '../../utils/request'
//根据学生学号查询对应的预报名信息
export function inquirePredictionById (uid) {
    return request({
        url: '/prediction/PredictionById',
        method: "post",
        data:{uid},
    })
}
