import request from '../../utils/request'
//用户输入学生学号，进行信息查询
export function inquireStudentById (uid) {
    return request({
        url: '/student/StudentById',
        method: "post",
        data:{uid},
    })
}