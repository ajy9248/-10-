import request from '../../utils/request'
export function inquirePredictionById (uid) {
    let Data = "uid="+uid;
    Data = Data.replace(/\s*/g,"");//去除所有空格，用来去除uid=和uid间空格
    return request({
        url: '/prediction/PredictionById',
        method: "post",
        data:Data,
    })
}