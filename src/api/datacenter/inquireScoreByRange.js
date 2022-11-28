import request from '../utils/request'
//查询不同范围的考试分布，返回值由低分人数到高分人数
export function inquireScoreByRange () {
    return request({
        url: '/score/ScoreByRange',
        method: "get",
        data: '',
    })
}