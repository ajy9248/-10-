import request from '../../utils/request'
//用户传入excel表格后，批量添加学生
export function addStudent(formData) {
    console.log(formData)
    return request({
        url: '/student/AddStudent',
        method: "post",
        data: {
            formData
        },
    })
}