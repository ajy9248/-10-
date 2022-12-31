<template>
  <div class="total_list">
    <div class="topline">
    <el-upload
        class="upload-demo"
        ref="upload"
        :action="uploadUrl()"
        name="excelFile"
        :file-list="file"
        accept=".xlsx"
        :on-remove="handleRemove"
        :on-change="onUploadChange"
        :auto-upload="false">
      <!--选择文件后立即上传，后端通过文件名获取导入考试的场次-->
      <el-button type="primary">选取文件</el-button>
      <div slot="tip" class="el-upload__tip">只能上传xlsx文件,文件名为场次,如“21.xlsx"</div>
    </el-upload>
    <el-button style="margin-left: 10px" type="success" @click="submitUpload">批量导入</el-button>
    </div>
  <div class="scores">
    <el-table :data="tableData" height="400" border style="width: 100%">>
      <el-table-column prop="uid" label="学号" sortable >
      </el-table-column>
      <el-table-column prop="uname" label="姓名" >
      </el-table-column>
      <el-table-column prop="grade" label="年级" sortable >
      </el-table-column>
      <el-table-column prop="score" label="分数"  sortable >
      </el-table-column>
      <el-table-column prop="session" label="考试场次" sortable >
      </el-table-column>
    </el-table>
  </div>
  </div>
</template>

<script>
import {inquireScores} from "@/api/score/inquireScores";
import {scoresImport} from "@/api/score/scoresImport";
import {addStudents} from "@/api/students/addStudents";
export default {
  data() {
    return {
      session:'',
      tableData: [],
      fileList: [],
      files:[]
    }
  },
  //渲染查询结果
  created() {
    this.getScoresList();
  },
  methods: {
    uploadUrl: function() {
      return (
          '/score/InsertScore'
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

      console.log("---fileList----->"+this.fileList)
    },
    async submitUpload() {
      console.log("submitUpload-------->"+this.fileList)
      let formData = new window.FormData();
      for (let i = 0; i < this.fileList.length; i++) {
        formData.append('file', this.fileList[i]);
      }
      console.log(formData.get("file"))
      const {data} = await scoresImport(formData);
      console.log(data)

      if (data.state === 200) {
        this.$message.success("导入成绩成功")
        this.fileList=[],
        await this.getScoresList();
      } else {
        alert(data.message);
      }
    },
    //移除文件
    handleRemove(file, fileList) {
      this.fileList=fileList
      console.log(file, fileList);
    },
    //查询成绩列表,默认最新的session
    async getScoresList() {
      //eslint-disable-next-line no-unused-vars
      const {data} = await inquireScores({});
      if (data.state === 200) {
        //表单中的数据等于后台返回的列表
        this.tableData = data.data
      } else {
        //提示错误
        alert(data.message);
      }
    },
  }
}
</script>

<style scoped>
.scores{
  margin-top : 10px;
  display: flex;
  flex : 1;
}

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

</style>