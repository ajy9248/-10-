//把导入的成绩excel表传到后端
import request from '../../utils/request'
export function scoresImport(scoresdata) {
    return request({
        url: '/score/InsertScore',
        method: "post",
        data: { scoresdata},
    })
}