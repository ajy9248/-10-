import request from '../../utils/request'
export function submitTeacher (formData) {
    let Data = "uid="+formData.uid+"&uname="+formData.uname+"&identity="+formData.identity;
    Data = Data.replace(/\s*/g,"");
    console.log(Data)
    return request({
        url: '/teacher/ModifyTeacher',
        method: "post",
        data:Data,
    })
}