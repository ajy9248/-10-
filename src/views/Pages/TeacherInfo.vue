
<template>
  <div class="topline">
  <div class="search_box"  >
    <el-input type="text" v-model="teacher_Id" placeholder="请输入查询的工号"></el-input>
    <el-button @click="getTeacherById">查询</el-button>
  </div>
  <div class="button">
    <el-button style="margin-left: 10px" type="primary" @click="deleteTeacher()">批量删除</el-button>
    <el-button style="margin-left: 10px" type="primary" @click="addTeacher" >添加</el-button>
  </div>
  </div>

  <div class="teachers">
    <el-table :data="tableData"  height="400" border  @selection-change="handleSelectionChange" max-height="500p ">>
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="uid" label="工号" sortable>
      </el-table-column>
      <el-table-column prop="uname" label="姓名" >
      </el-table-column>
      <el-table-column prop="identity" label="身份">
      </el-table-column>
      <el-table-column prop="action" label="操作">
        <template #default="scope">
          <el-button size="small" @click="modifyTeacher(scope.row.uid)">修改</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <!--修改弹窗-->
  <div class="modifyteacher">
    <el-dialog title="修改教师信息" v-model="modifyDialogVisible" width="20%" :append-to-body="true">
      <el-form :model="modifyformData">
        <el-form-item label="工号：" prop="uid">
          <el-input v-model="modifyformData.uid" size="small" style="width: 200px">></el-input>
        </el-form-item>
        <el-form-item label="姓名：" prop="uname">
          <el-input v-model="modifyformData.uname" size="small" style="width: 200px">></el-input>
        </el-form-item>
        <el-form-item label="身份：" prop="identity">
            <el-input v-model="modifyformData.identity" size="small" style="width: 200px">></el-input>
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
  <!--添加弹窗-->
  <div class="addteacher">
    <el-dialog title="添加教师信息" v-model="addDialogVisible" width="20%" :append-to-body="true">
      <el-form :model="addformData">
        <el-form-item label="工号：" prop="uid">
          <el-input v-model="addformData.uid" size="small" style="width: 200px">></el-input>
        </el-form-item>
        <el-form-item label="姓名：" prop="uname">
          <el-input v-model="addformData.uname" size="small" style="width: 200px">></el-input>
        </el-form-item>
        <el-form-item label="身份：" prop="identity">
          <el-input v-model="addformData.identity" size="small" style="width: 200px">></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addDialogVisible=false">取 消</el-button>
          <!--将信息提交到后台-->
          <el-button type="primary" @click="addSubmit(addformData)">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script>
import {inquireTeachers} from '@/api/teachers/inquireTeachers'
import { submitTeacher } from '@/api/teachers/submitTeacher'
import {insertTeacher} from "@/api/teachers/insertTeacher";
import { inquireTeacherById } from '@/api/teachers/inquireTeacherById'
import {deleteTeachers} from "@/api/teachers/deleteTeachers";
import {deleteStudents} from "@/api/students/deleteStudents";

export default {
  data() {
    return {
      name: "TeacherInfo",
      uid:"",
      input: "",
      teacher_Id:'',
      tableData: [],
      modifyDialogVisible: false,
      modifyformData: [],
      addDialogVisible: false,
      addformData: [],
      multipleSelection: []

    }
  },
  //渲染查询结果
  created() {
    this.getTeachersList();
  },

  methods: {
    //查询教师列表
   async getTeachersList() {
      const {data} = await inquireTeachers({});
      if (data.state === 200) {
        //表单中的数据等于后台返回的列表
        this.tableData = data.data
      } else {
        //提示错误
        alert(data.message);
      }
    },
    //uid查询教师
    async getTeacherById() {
      const uid = this.teacher_Id;
      const {data} = await inquireTeacherById(uid);
      if (data.state === 200) {
        let arr = []
        arr.push(data.data)
        this.tableData = arr
      } else {
        alert(data.message);
      }
    },
    //让数据回显到编辑框
    async modifyTeacher(uid){
      this.modifyDialogVisible = true;
      const { data } = await inquireTeacherById(uid);
      console.log(data);
      if (data.state === 200) {
        //表单中的数据等于后台返回的列表
        this.modifyformData = data.data
      }
      else {
        //提示错误
        alert(data.message);
      }
    },
    //将修改数据提交到后台
    async modifySubmit (modifyformData) {
      console.log(modifyformData)
      const { data } = await submitTeacher(modifyformData);
      console.log(data)
      if (data.state === 200) {
        this.modifyDialogVisible = false;
        this.modifyformData=[];
        alert("修改成功！");
        await this.getTeachersList();
      }
      else {
        alert(data.message);
      }
    },
    //获取选中的值
    handleSelectionChange(val) {
      let ids = ''
      for(let i=0;i<val.length;i++){
        ids +="uid="+val[i].uid + "&"
      }
      ids = ids.substring(0,ids.length-1)
      console.log(ids)
      this.multipleSelection=ids;
    },
    //批量删除老师
    async deleteTeacher(){
      console.log(this.multipleSelection)
      if(this.multipleSelection == ""){
        this.$message.warning('请至少选择一条数据进行删除！')
      }else {
        this.$confirm('此操作将删除选中教师的个人信息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          center:true
        }).then(async () => {
          console.log(this.multipleSelection)
          const {data} = await deleteTeachers(this.multipleSelection);
          if (data.state === 200) {
            alert("删除成功！");
            await this.getTeachersList();
          } else {
            alert(data.message);
          }
        })
      }
    },
    addTeacher () {
      this.addDialogVisible = true
      console.log(this.addDialogVisible)
    },
    async addSubmit (addformData) {
      let { data } = await insertTeacher(addformData);
      if (data.state == 200) {
        this.addDialogVisible = false;
        this.addformData=[];
        alert("添加成功！");
        await this.getTeachersList();
      }
      else {
        alert(data.message);
      }
    },


  }
}
</script>

<style scoped>
.teachers {
  margin-top: 60px;
}
.topline{
  margin-top : 10px;
  display: flex;
  flex-direction : row;
  align-items: start;
}
.search_box{
  box-sizing: border-box;
  width : 40%;
  display: flex;
}
.button{
  position: fixed;
  left:75%;
  top: 13%;
}
.modifyteacher {
  width: 500px;
  height: 400px;
  position: fixed;
  left: 50%;
  top: 50%;
}
</style>