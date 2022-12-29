<template>
  <div style="display: flex">
  <div  >
    <el-input type="text" v-model="Student_id" placeholder="请输入查询的学号" style="width: 180px" size="mini"></el-input>
    <el-button @click="getScoresById">查询</el-button>
  </div>
  <div  >
    <el-input type="text" v-model="Session" placeholder="请输入查询的考试场次" style="width: 180px" size="mini"></el-input>
    <el-button @click="getScoresBySession">查询</el-button>
  </div>
  </div>
  <div class="scores">
    <el-table :data="tableData" height="400" border style="width: 100%">>
      <el-table-column prop="uid" label="学号" sortable>
      </el-table-column>
      <el-table-column prop="uname" label="姓名">
      </el-table-column>
      <el-table-column prop="grade" label="年级" sortable>
      </el-table-column>
      <el-table-column prop="score" label="分数"  sortable>
      </el-table-column>
      <el-table-column prop="session" label="考试场次"  sortable>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import {inquireScores} from "@/api/score/inquireScores";
import {inquireScoresById} from "@/api/queryscores_teacher/inquireScoresById";
import {inquireScoresBySession} from "@/api/queryscores_teacher/inquireScoresBySession";


export default {
  data() {
    return {
      Session:"",
      Student_id: "",
      tableData: []
    }
  },
  //渲染查询结果
  created() {
    this.getScores();
  },
  methods: {
    //查询成绩列表，默认是最新的session
    async getScores() {
      const {data} = await inquireScores({});
      if (data.state === 200) {
        //表单中的数据等于后台返回的列表
        this.tableData = data.data
      } else {
        //提示错误
        alert(data.msg);
      }
    },
    //通过学生学号查询成绩
    async getScoresById() {
      const uid = this.Student_id;
      const {data} = await inquireScoresById(uid);
      if (data.state === 200) {
        let arr = []
        for(let i=0; i<data.data.length; i++){
          arr.push(data.data[i]);
        }
        this.tableData = arr
      } else {
        alert(data.msg);
      }
    },
    //通过输入场次查成绩
    async getScoresBySession() {
      const Session = this.Session;
      const {data} = await inquireScoresBySession(Session);
      if (data.state === 200) {
        let arr = []
        for(let i=0; i<data.data.length; i++){
          arr.push(data.data[i]);
        }
        this.tableData = arr
      } else {
        alert(data.msg);
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
</style>