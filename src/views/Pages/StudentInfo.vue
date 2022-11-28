<template>
  <!--修改弹窗-->
  <div class="modify_student">
    <el-dialog title="修改学生信息" v-model="modifyDialogVisible" width="20%" :append-to-body="true">
      <el-form :model="modifyformData">
        <el-form-item label="学号：" prop="student_id">
          <el-input v-model="modifyformData.student_id" size="small" style="width: 200px">></el-input>
        </el-form-item>
        <el-form-item label="学生姓名：" prop="car_status">
          <el-input v-model="modifyformData.student_name" size="small" style="width: 200px">></el-input>
        </el-form-item>
        <el-form-item label="学生年级：" prop="origin_place">
          <el-select v-model="modifyformData.student_grade">
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
        <el-input v-model="student_Id" placeholder="请输入查询的学号"></el-input>
        <el-button @click="getStudentById">查询</el-button>
      </div>
      <!--批量操作按钮-->
      <el-button @click="deleteStudent()" style="margin-right: 15px;margin-left: 550px">批量删除</el-button>
      <el-upload
          class="upload-demo"
          ref="upload"
          :action="uploadUrl()"
          name="excelFile"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          :on-error="uploadFalse"
          :on-success="uploadSuccess"
          :auto-upload="false"
          :before-upload="beforeAvatarUpload">
        <el-button v-slot="trigger" type="primary">选取文件</el-button>
        <el-button style="margin-left: 10px" type="success" @click="submitUpload">批量导入</el-button>
      </el-upload>
    </div>

    <!--学生表单-->
    <div class="students">
      <el-table :data="tableData" height="400" border style="width: 100%" @selection-change="handleSelectionChange">>
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="student_id" label="学号" sortable :formatter="formatter">
        </el-table-column>
        <el-table-column prop="student_name" label="学生姓名" :formatter="formatter">
        </el-table-column>
        <el-table-column prop="student_grade" label="年级" sortable :formatter="formatter">
        </el-table-column>
        <el-table-column prop="action" label="操作" :formatter="formatter">
          <template #default="scope">
            <el-button size="small" @click="modifyStudent(scope.row.student_id)">编辑</el-button>
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
import {read, utils} from "xlsx";
export default {
  data(){
    return{
      name: "StudentInfo",
      tableData: [],
      student_Id:'',
      allChecked:false,
      modifyDialogVisible: false,
      modifyformData: [],
      multipleSelection: []
    }

  },
  created () {
    this.getStudentsList();
    this.tableData.forEach(item => {
      this.$set(item, 'isChecked', false) // 添加判断的字段
    })
  },
  methods:{
    uploadUrl: function() {
      return (
          "/student/import"
      );
    },
    uploadSuccess(response, file, fileList) {
      if (response.status) {
        alert("文件导入成功");
      } else {
        alert("文件导入失败");
      }
    },
    uploadFalse() {
      alert("文件上传失败！");
    },
    // 上传前对文件的类型的判断
    beforeAvatarUpload(file) {
      const extension = file.name.split(".")[1] === "xls";
      const extension2 = file.name.split(".")[1] === "xlsx";
      if (!extension && !extension2) {
        alert("上传文件只能是 xls、xlsx 格式!");
      }
      return extension || extension2;
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      if (file.response.status) {
        alert("此文件导入成功");
      } else {
        alert("此文件导入失败");
      }
    },

    // 用于保存选中的行
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //查询学生列表
    async getStudentsList() {
      const {data} = await inquireStudents({});
      if (data.code === 200) {
        this.tableData = data.data
      } else {
        alert(data.msg);
      }
    },
    // 根据id查询学生信息
    async getStudentById() {
      const uId = this.student_Id;
      const {data} = await inquireStudentById(uId);
      if (data.code === 200) {
        let arr = []
        arr.push(data.data)
        console.log(arr)
        this.tableData = arr
      } else {
        alert(data.msg);
      }
    },
    //让该行数据回显到编辑弹框里
    async modifyStudent (student_id) {
      this.modifyDialogVisible = true;
      //eslint-disable-next-line no-unused-vars
      const { data } =await inquireStudentById(student_id);
      console.log(data)
      if (data.code === 200) {
        //表单中的数据等于后台返回的
        console.log(data.data)
        this.modifyformData = data.data
      }
      else {
        //提示错误
        alert(data.msg);
      }
    },
    //把修改后的数据提交到后台
    async modifySubmit (modifyformData) {
      const that = this;
      const { data } = await submitStudent(
          modifyformData);
      if (data.code === 200) {
        that.modifyDialogVisible = false;
        alert("修改成功！");
        await this.getStudentsList();
      }
      else {
        alert(data.msg);
      }
    },
    //批量删除学生
    async deleteStudent(){
      if(this.multipleSelection==""){
        this.$message.warning('请至少选择一条数据进行删除！')
      }else {
        this.$confirm('此操作将批量删除学生, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center:true
        }).then(async () => {
          const {data} = await deleteStudents(this.multipleSelection);
          if (data.code == 200) {
            alert("删除成功！");
            await this.getStudentsList();
          } else {
            alert(data.msg);
          }
        })
      }
    },
    async addStudent(){

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