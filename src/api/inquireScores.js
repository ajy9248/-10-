//查询所有学生成绩
import request from '../../utils/request'
    export function inquireScores () {
    return request({
        url: '/score/ScoreInfo',
        method: "get",
        data: '',
    })
}