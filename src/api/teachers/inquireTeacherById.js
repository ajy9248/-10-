import request from '../../utils/request'
export function inquireTeacherById (uid) {
    let Data = "uid="+uid;
    Data = Data.replace(/\s*/g,"");//去除所有空格，用来去除uid=和uid间空格
    console.log(Data)
    return request({
        url: '/teacher/TeacherById',
        method: "post",
        data:Data,
    })
}