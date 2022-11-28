import request from '../../utils/request'
//用户修改学生数据后提交，进行信息更新
export function submitStudent(formData) {
    console.log(formData)
    return request({
        url: '/student/ModifyStudent',
        method: "post",
        data: {
            formData
        },
    })
}