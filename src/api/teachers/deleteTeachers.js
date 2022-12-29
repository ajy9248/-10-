import request from '../../utils/request'
export function deleteTeachers (uids) {
    let Data = uids
    return request({
        url: '/teacher/DeleteTeachers',
        method: "post",
        data: Data,
    })
}