import request from '../../utils/request'
//学生提交预报名信息
export function submitPrediction(uid,formData) {
    let Data = "uid="+uid+"&type="+formData.type;
    Data = Data.replace(/\s*/g,"");
    console.log(Data)
    return request({
        url: '/prediction/AddPrediction',
        method: "post",
        data: Data,
    })
}