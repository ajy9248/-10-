import request from '../../utils/request'
export function submitTeacher (formData) {
    console.log(formData);
    const Data = JSON.parse(JSON.stringify(formData))[1];
    console.log(Data);
    return request({
        url: '',
        method: "post",
        data: {
            Data
        },
    })
}