import request from '../../utils/request'
export function submitSession(formData) {
    console.log(formData)
    return request({
        url: '/prediction/ModifyPrediction',
        method: "post",
        data: {
            formData
        },
    })
}