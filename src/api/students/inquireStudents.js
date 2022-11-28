import request from '../../utils/request'
//进入学生管理页面时，自动获取全部学生的列表
export function inquireStudents () {
    return request({
        url: '/student/StudentInfo',
        method: "get",
        data: '',
    })
}