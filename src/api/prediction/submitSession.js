import request from '../../utils/request'
//提交开启预报名的场次
export function submitSession(session) {
    console.log(session)
    return request({
        url: '/prediction/ModifyPrediction',
        method: "post",
        data: {
            session
        },
    })
}
