import request from '../../utils/request'
//用户传入excel表格后，批量添加学生
export function addStudents(fileData) {
    console.log(fileData)
    return request({
        url: '/student/AddStudent',
        method: "post",
        data: {
            fileData
        },
    })
}