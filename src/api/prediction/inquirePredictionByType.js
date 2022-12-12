import request from '../../utils/request'
//根据类型（自费、免费）查询预报名信息
export function inquirePredictionByType (type) {
    return request({
        url: '/prediction/PredictionByType',
        method: "post",
        data:{type},
    })
}
