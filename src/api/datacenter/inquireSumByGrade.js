import request from '../../utils/request'
//查询不同年级的报名人数，按照从老生到新生的顺序返回（2019、2020、2021、2022）
export function inquireSumByGrade (session) {
    let Data = "session="+session;
    Data = Data.replace(/\s*/g,"");
    return request({
        url: '/prediction/SumByGrade',
        method: "post",
        data: Data,
    })
}