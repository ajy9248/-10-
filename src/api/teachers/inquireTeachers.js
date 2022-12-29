//查询所有教师
import request from '../../utils/request'
export function inquireTeachers () {
    return request({
        url: '/teacher/TeacherInfo',
        method: "get",
        data: '',
    })
}