<template>
  <div >
    <el-upload
        class="upload-demo"
        ref="upload"
        :action="uploadUrl()"
        name="excelFile"
        accept=".xlsx,.xls"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :on-error="uploadFalse"
        :on-success="uploadSuccess"
        :auto-upload="false">
      <el-button type="primary">选取文件</el-button>
      <el-button style="margin-left: 10px" type="success" @click="submitUpload">批量导入</el-button>
    </el-upload>
  </div>
  <div class="scores">
    <el-table :data="tableData" height="400" border style="width: 100%">>
      <el-table-column prop="uid" label="学号" sortable :formatter="formatter">
      </el-table-column>
      <el-table-column prop="uname" label="姓名" :formatter="formatter">
      </el-table-column>
      <el-table-column prop="grade" label="年级" sortable :formatter="formatter">
      </el-table-column>
      <el-table-column prop="score" label="分数"  sortable :formatter="formatter">
      </el-table-column>
      <el-table-column prop="session" label="考试场次" sortable :formatter="formatter">
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {inquireScores} from "@/api/managescores/inquireScores";
import {scoresImport} from "@/api/managescores/scoresImport";

export default {
  data() {
    return {
      tableData: []
    }
  },
  //渲染查询结果
  created() {
    this.getScoresList();
  },
  methods: {
    uploadUrl: function() {
      return (
          "@/api/managescores/scoresImport"
      );
    },
    // eslint-disable-next-line no-unused-vars
    uploadSuccess(response) {
      alert("文件上传成功！ ");
    },
    uploadFalse() {
      alert("文件上传失败！");
    },
    submitUpload() {
      //this.$refs.upload.submit();
      for (let i = 0; i < this.fileList.length; i++) {
        let fd = new FormData()
        // fd.append('name', '文件名字')
        // fd.append('type', '类型一')
        // fd.append('file', this.fileList[i].raw)
        this.upDataFile(fd);
      }
    },
    async upDataFile(fileData) {
      const {data} = await scoresImport(fileData);
      console.log(data)
      if (data.message) {
        this.$message({
          message: data.message,
          type: 'success'
        })
      }
    },
    //移除文件
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    //预览文件
    handlePreview(file) {
      if (file.response.status) {
        alert("此文件导入成功");
      } else {
        alert("此文件导入失败");
      }
    },
    //查询成绩列表
    async getScoresList() {
      //eslint-disable-next-line no-unused-vars
      const {data} = await inquireScores({});
      if (data.code == 200) {
        //表单中的数据等于后台返回的列表
        this.tableData = data.data
      } else {
        //提示错误
        alert(data.msg);
      }
    }
  }
}
</script>

<style scoped>
.scores {
  margin: 20px;
}
.search_box {
  position: fixed;
  left: 20%;
  top: 11%;
}
.button{
  position: fixed;
  left:80%;
  top: 11%;
}

</style>
