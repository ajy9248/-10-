//教师端根据搜索框输入的学号查询学生的成绩
import request from '../../utils/request'
export function inquireScoresById (uid) {
    let Data = "uid="+uid;
    Data = Data.replace(/\s*/g,"");
    console.log(Data)
    return request({
        url: '/score/ScoresById',
        method: "post",
        data:Data,
    })
}