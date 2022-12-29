import request from '../../utils/request'
//开始预报名，提交一个session,作为后端全局变量
export function submitSession(session) {
    let Data = "session="+session;
    Data = Data.replace(/\s*/g,"");
    console.log(Data)
    return request({
        url: '/prediction/ModifyPrediction',
        method: "post",
        data: Data,
    })
}