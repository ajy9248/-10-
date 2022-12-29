import request from '../../utils/request'
//用户修改学生数据后提交，进行信息更新
export function submitStudent(modifyformData) {
    let Data = "uid="+modifyformData.uid+"&uname="+modifyformData.uname+"&grade="+modifyformData.grade;
    Data = Data.replace(/\s*/g,"");
    return request({
        url: '/student/ModifyStudent',
        method: "post",
        data: Data,
    })
}