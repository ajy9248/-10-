import request from '../../utils/request'
export function deleteTeachers (uids) {
    const Data = JSON.parse(JSON.stringify(uids))[1];
    return request({
        url: '',
        method: "post",
        data: {
            Data
        },
    })
}