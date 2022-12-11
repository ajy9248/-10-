import request from '../../utils/request'
export function inquireTeacherById (uid) {
    const Data = JSON.parse(JSON.stringify(uid))[1];
    return request({
        url: '',
        method: "post",
        data:{Data},
    })
}