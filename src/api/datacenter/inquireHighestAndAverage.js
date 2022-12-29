import request from '../../utils/request'
//查询本场次考试最高分和平均分
export function inquireHighestAndAverage (session) {
    let Data = "session="+session;
    Data = Data.replace(/\s*/g,"");
    return request({
        url: '/score/highestAndaverage',
        method: "post",
        data: Data,
    })
}