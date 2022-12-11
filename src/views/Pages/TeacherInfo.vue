<!-- eslint-disable vue/valid-template-root -->
<template>
  <div class="search_box"  >
    <el-input v-model="uid" placeholder="请输入查询的工号" style="width: 180px" size="mini"></el-input>
    <el-button @click="getTeacherById">查询</el-button>
  </div>
  <div class="button">
    <!--// :disabled="this.sels.length === 0" 如果没有数据让删除按钮失效 -->
    <el-button style="margin-left: 10px" type="primary" @click="batchDelect" :disabled="this.sels.length === 0">批量删除</el-button>
    <el-button style="margin-left: 10px" type="primary" @click="addTeacher" >添加</el-button>
  </div>

  <div class="teachers">
    <el-table :data="tableData"  height="400" border  @selection-change="handleSelectionChange" max-height="500p ">>
      <el-table-column type="selection" width="40"></el-table-column>
      <el-table-column prop="uid" label="工号" sortable :formatter="formatter">
      </el-table-column>
      <el-table-column prop="uname" label="姓名" :formatter="formatter">
      </el-table-column>
      <el-table-column prop="identity" label="身份" :formatter="formatter">
      </el-table-column>
      <el-table-column prop="action" label="操作" :formatter="formatter">
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
import { inquireTeacherById } from '@/api/teachers/inquireTeacherById'
import {deleteTeachers} from "@/api/teachers/deleteTeachers";

export default {
  data() {
    return {
      input: "",
      tableData: [{
        uid: 1,
        uname: "张三",
        identity: "2020级主任"
      },{uid: 25,
        uname: "张四",
        identity: "2021级主任"}
      ],
      modifyDialogVisible: false,
      modifyformData: [],
      addDialogVisible: false,
      addformData: [],
      sels: [], //当前选框选中的值

    }
  },
  //渲染查询结果
  created() {
    this.getTeachersList();
  },

  methods: {

    //查询教师列表
   async getTeachersList() {
      //eslint-disable-next-line no-unused-vars
      const {data} = await inquireTeachers({});
      if (data.code == 200) {
        //表单中的数据等于后台返回的列表
        this.tableData = data.data
      } else {
        //提示错误
        alert(data.msg);
      }
    },
    //uid查询教师
    async getTeacherById() {
      const uid = this.uid;
      const {data} = await inquireTeacherById(uid);
      if (data.code === 200) {
        let arr = []
        arr.push(data.data)
        console.log(arr)
        this.tableData = arr
      } else {
        alert(data.msg);
      }
    },
    //让数据回显到编辑框
    async modifyTeacher(uid){
      this.modifyDialogVisible = true;
      const { data } = await inquireTeacherById(uid);
      console.log(data);
      if (data.code == 200) {
        //表单中的数据等于后台返回的列表
        this.modifyformData = data.data
      }
      else {
        //提示错误
        alert(data.msg);
      }
    },
    //将修改数据提交到后台
    modifySubmit (modifyformData) {
      var that = this;
      const { data } = submitTeacher(
          modifyformData);
      if (data.code == 200) {
        that.modifyDialogVisible = false;
        alert("修改成功！");
        this.getTeachersList();
      }
      else {
        alert(data.msg);
      }
    },
    //获取选中的值
    handleSelectionChange(sels) {
      this.sels = sels;
      console.log("选中的值",sels.map((item) => item.id));
    },
    //批量删除执行操作
    batchDelect() {
      // 删除前的提示
      this.$confirm("确认删除记录吗?", "提示", {
        type: "warning",
      }).then(() => {
        let ids = this.sels.map((item) => item.id);
        /* 根据后台想要的参数格式选择
          console.log(ids.join(",")); //1,2,3,4
           console.log(ids); //[1,2,3,4]
        */
        // 请求接口
        deleteTeachers({ ids: ids }).then((res) => {
          if (res.code == "200") {
            this.$message({
              message: "删除成功",
              type: "success",
            });
          }
        });
      });
    },
    addTeacher () {
      this.addDialogVisible = true
      console.log(this.addDialogVisible)
    },
    addSubmit (addformData) {
      var that = this;
      let { data } = submitTeacher(addformData);
      if (data.code == 200) {
        that.addDialogVisible = false;
        alert("添加成功！");
        this.getTeachersList();
      }
      else {
        alert(data.msg);
      }
    },


  }
}
</script>

<style scoped>
.teachers {
  margin: 20px;
}
.search_box {
  position: fixed;
  left: 25%;
  top: 13%;
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
