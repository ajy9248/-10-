<template>
  <div class="button">
    <el-button type="primary" @click="prediction">进行预报名</el-button>
  </div>
  <!--个人的预报名信息表单-->
  <div class="MyPrediction">
    <el-table :data="tableData" height="400" border style="width: 100%" >>
      <el-table-column prop="uid" label="学号">
      </el-table-column>
      <el-table-column prop="uname" label="姓名">
      </el-table-column>
      <el-table-column prop="type" label="类型" sortable>
      </el-table-column>
      <el-table-column prop="timeString" label="操作时间" sortable>
      </el-table-column>
      <el-table-column prop="session" label="场次" sortable>
      </el-table-column>
      <el-table-column prop="action" label="操作">
        <template #default="scope">
          <el-button size="small" @click="cancelPrediction(scope.row.uid,scope.row.session)">撤销</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
  <!--预报名弹窗-->
  <div class="Prediction">
    <el-dialog title="进行CSP预报名" v-model="preDialogVisible" width="20%" :append-to-body="true">
      <el-form :model="preformData">
        <el-form-item label="报名类型：" prop="type">
          <el-select v-model="preformData.type">
            <el-option label="自费团报" value="自费团报"></el-option>
            <el-option label="免费团报" value="免费团报"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="preDialogVisible=false">取 消</el-button>
          <!--将信息提交到后台-->
          <el-button type="primary" @click="preSubmit(preformData)">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script>
import jsCookie from 'js-cookie';
import { submitPrediction } from '@/api/prediction_stu/submitPrediction'
import { cancelPrediction } from '@/api/prediction_stu/cancelPrediction'
import { inquireMyPrediction } from "@/api/prediction_stu/inquireMyPrediction";
export default {
  data(){
    return{
      name: "StudentPre",
      student_id:'',
      tableData: [
      //   {
      //     uid: 1,
      //     uname: "张三",
      //     pretype: "2020级",
      //     pretime: "2022-10-01 22:33:44",
      //     session: 21
      //   },{
      //     uid: 1,
      //     uname: "张三",
      //     pretype: "2020级",
      //     pretime: "2022-10-01 22:55:00",
      //     session: 21
      // }
      ],
      preDialogVisible: false,
      preformData: [],
    }

  },
  computed:{
  },
  created () {
    this.student_id=jsCookie.get('username')
    console.log(this.student_id)
    this.getMyPredictionList();
  },
  methods: {
    async cancelPrediction(uid , session) {
      this.$confirm('此操作将撤销预报名，是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(async () => {
        const {data} = await cancelPrediction(uid , session);
        console.log(data)
        if (data.state === 200) {
          alert("删除成功！");
          await this.getStudentsList();
        } else {
          alert(data.msg);
        }
      })
    },
    prediction() {
      this.preDialogVisible = true
    },
    //将修改数据提交到后台
    preSubmit(preformData) {
      const {data} = submitPrediction(this.student_id,preformData);
      if (data.state === 200) {
        this.modifyDialogVisible = false;
        alert("修改成功！");
        this.getMyPredictionList();
      } else {
        alert(data.msg);
      }
    },
    async getMyPredictionList() {
      const {data} = await inquireMyPrediction(this.student_id);
      if (data.state === 200) {
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
.button{
  box-sizing: border-box;
  width : 40%;
  display: flex;
  margin-right : 20px;
}
.MyPrediction{
  margin-top : 10px;
  display: flex;
  flex : 1;
}
.Prediction {
  width: 500px;
  height: 400px;
  position: fixed;
  left: 50%;
  top: 50%;
}
</style>