import request from '../../utils/request'
export function inquireFreeChance (user) {
    return request({
        url: '/login/inquireFreeChance',
        method: "get",
        data: {user},
    })
}