import request from '../../utils/request'
//查询缺考列表
export function inquireAbsence (session) {
    let Data = "session="+session;
    Data = Data.replace(/\s*/g,"");
    return request({
        url: '/score/Absence',
        method: "post",
        data: Data,
    })
}