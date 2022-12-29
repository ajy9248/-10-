import request from '../../utils/request'
//学生取消自己的预报名(如果场次不是最新的，则不能撤销)
export function cancelPrediction(uid,session) {
    let Data = "uid="+uid+"&session="+session;
    Data = Data.replace(/\s*/g,"");
    return request({
        url: '/prediction/CancelPrediction',
        method: "post",
        data: Data,
    })
}