import request from '../../utils/request'
//用户输入学生学号，进行信息查询
export function inquireStudentById (uid) {
    let Data = "uid="+uid;
    Data = Data.replace(/\s*/g,"");//去除所有空格，用来去除uid=和uid间空格
    return request({
        url: '/student/StudentById',
        method: "post",
        data:Data,
    })
}