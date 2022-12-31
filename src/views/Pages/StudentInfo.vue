<template>
  <!--修改弹窗-->
  <div class="modify_student">
    <el-dialog title="修改学生信息" v-model="modifyDialogVisible" width="20%" :append-to-body="true">
      <el-form :model="modifyformData">
        <el-form-item label="学号：" prop="uid">
          <el-input v-model="modifyformData.uid" size="small" style="width: 200px">></el-input>
        </el-form-item>
        <el-form-item label="学生姓名：" prop="uname">
          <el-input v-model="modifyformData.uname" size="small" style="width: 200px">></el-input>
        </el-form-item>
        <el-form-item label="学生年级：" prop="grade">
          <el-select v-model="modifyformData.grade">
            <el-option label="2019级" value="2019级"></el-option>
            <el-option label="2020级" value="2020级"></el-option>
            <el-option label="2021级" value="2021级"></el-option>
            <el-option label="2022级" value="2022级"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="modifyDialogVisible=false">取 消</el-button>
          <!--将信息提交到后台-->
          <el-button type="primary" @click="modifySubmit(modifyformData)">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>

  <div class="total_list">
    <div class="topline">
      <!--搜索框-->
      <div class="search_box"  >
        <el-input type="text" v-model="student_Id" placeholder="请输入查询的学号"></el-input>
        <el-button @click="getStudentById">查询</el-button>
      </div>
      <!--批量操作按钮-->
      <el-button @click="deleteStudent()" style="margin-right: 15px;margin-left: 550px">批量删除</el-button>
      <el-upload
          class="upload-demo"
          ref="upload"
          :action="uploadUrl()"
          name="excelFile"
          :file-list="fileList"
          accept=".xlsx"
          :on-remove="handleRemove"
          :on-change="onUploadChange"
          :auto-upload="false">
          <!--选择文件后立即上传-->
        <el-button type="primary">选取文件</el-button>
        <div slot="tip" class="el-upload__tip">只能上传xlsx文件</div>
      </el-upload>
      <el-button style="margin-left: 10px" type="success" @click="submitUpload">批量导入</el-button>
    </div>

    <!--学生表单-->
    <div class="students">
      <el-table :data="tableData" height="400" border style="width: 100%" @selection-change="handleSelectionChange">>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="uid" label="学号" sortable>
        </el-table-column>
        <el-table-column prop="uname" label="学生姓名">
        </el-table-column>
        <el-table-column prop="grade" label="年级" sortable>
        </el-table-column>
        <el-table-column prop="action" label="操作">
          <template #default="scope">
            <el-button size="small" @click="modifyStudent(scope.row.uid)">修改</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import "async";
import { inquireStudents } from '@/api/students/inquireStudents'
import { inquireStudentById } from '@/api/students/inquireStudentById'
import { submitStudent } from '@/api/students/submitStudent'
import { deleteStudents } from '@/api/students/deleteStudents'
import { addStudents } from '@/api/students/addStudents'
import axios from "axios";
export default {
  data(){
    return{
      name: "StudentInfo",
      tableData: [],
      student_Id:" ",
      fileList: [],
      files:[],
      modifyDialogVisible: false,
      modifyformData: [],
      multipleSelection: []
    }

  },
  created () {
    this.getStudentsList();
  },
  methods:{
    uploadUrl: function() {
      return (
          '/student/AddStudent'
      );
    },
    onUploadChange(file,fileList){
      let reader = new FileReader();
      reader.readAsDataURL(file.raw);
      reader.onload = (e) => {
        this.files.push({ name: file.raw.name, url: e.target.result });
      }
      this.fileList.push(file.raw);
      fileList = this.fileList;

      // this.files=file//这是新添加的file
      // this.fileList = fileList;//这是总体的fileList
      console.log("---files----->"+this.files)
      console.log("---fileList----->"+this.fileList)
    },

    async submitUpload() {
      console.log("submitUpload-------->"+this.fileList)
      let formData = new window.FormData();
      for (let i = 0; i < this.fileList.length; i++) {
        formData.append('file', this.fileList[i]);
      }
      console.log(formData.get("file"))
      const {data} = await addStudents(formData);
      console.log(data)

      if (data.state === 200) {
        this.$message.success("批量添加学生成功")
        await this.getStudentsList();
      } else {
        alert(data.message);
      }
    },
    //移除文件
    handleRemove(file, fileList) {
      this.fileList=fileList
      console.log(file, fileList);
    },
    //查询学生列表
    async getStudentsList() {
      const {data} = await inquireStudents({});
      if (data.state === 200) {
        this.tableData = data.data
      } else {
        alert(data.message);
      }
    },
    // 根据id查询学生信息
    async getStudentById() {
      let uid = this.student_Id;
      const {data} = await inquireStudentById(uid);
      console.log(data)
      if (data.state === 200) {
        let arr = []
        arr.push(data.data)
        this.tableData = arr
      } else {
        alert(data.message);
      }
    },
    //让该行数据回显到编辑弹框里
    async modifyStudent (student_id) {
      this.modifyDialogVisible = true;
      const { data } =await inquireStudentById(student_id);
      if (data.state === 200) {
        //表单中的数据等于后台返回的
        this.modifyformData = data.data
      }
      else {
        //提示错误
        alert(data.message);
      }
    },
    //把修改后的数据提交到后台
    async modifySubmit (modifyformData) {
      console.log(modifyformData)
      const { data } = await submitStudent(modifyformData);
      if (data.state === 200) {
        this.modifyDialogVisible = false;
        this.modifyformData=[];
        alert("修改成功！");
        await this.getStudentsList();
      }
      else {
        this.modifyDialogVisible = false;
        alert(data.message);
      }
    },
    // 用于保存选中的行
    handleSelectionChange(val) {
      let ids =""
      for(let i=0;i<val.length;i++){
        ids +="uids="+val[i].uid + "&"
      }
      ids = ids.substring(0,ids.length-1)
      console.log(ids)
      this.multipleSelection=ids;
    },
    //批量删除学生
    async deleteStudent(){
      console.log(this.multipleSelection)
      if(this.multipleSelection == ""){
        this.$message.warning('请至少选择一条数据进行删除！')
      }else {
        this.$confirm('此操作将删除选中学生的个人信息、预报名信息及成绩信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center:true
        }).then(async () => {
          console.log(this.multipleSelection)
          const {data} = await deleteStudents(this.multipleSelection);
          if (data.state === 200) {
            alert("删除成功！");
            await this.getStudentsList();
          } else {
            alert(data.message);
          }
        })
      }
    }
  }
}
</script>

<style scoped>
.search_box{
  box-sizing: border-box;
  width : 40%;
  display: flex;
}
.topline{
  margin-top : 10px;
  display: flex;
  flex-direction : row;
  align-items: start;
}
.total_list{
  height : 100%;
  width : 100%;
  align-items: center;
  flex-direction : column;
}
.students{
  margin-top : 10px;
  display: flex;
  flex : 1;
}
.modify_student {
  width: 500px;
  height: 400px;
  position: fixed;
  left: 50%;
  top: 50%;
}
</style>