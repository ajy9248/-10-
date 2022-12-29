import request from '../../utils/request'
//用户选中多个学生，进行批量删除
export function deleteStudents(selection) {
    let Data = selection;
    return request({
        url: '/student/DeleteStudents',
        method: "post",
        data:Data,
    })
}