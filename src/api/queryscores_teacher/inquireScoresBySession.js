//教师端根据搜索框输入的考试场次查询学生的成绩
import request from '../../utils/request'
export function inquireScoresBySession (Session) {
    let Data = "session="+Session;
    Data = Data.replace(/\s*/g,"");
    return request({
        url: '/score/ScoresBySession',
        method: "post",
        data:Data,
    })
}