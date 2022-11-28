<template>
  <div>
    <!-- 新增信息弹窗 -->
    <el-dialog
        :title="新增老师"
        v-model:visible="dialogFormVisible"
        width="500px"
    >
      <el-form :model="addteacherData">
        <el-form-item label="姓名" style="width: 200px" prop="name">
          <el-input v-model="addteacherData.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="工号" style="width: 200px" prop="id">
          <el-input v-model.number="addteacherData.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="身份" style="width: 200px" prop="identity">
          <el-input v-model="addteacherData.identity" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="modifyDialogVisible=false">取 消</el-button>
          <el-button type="primary" @click="modifySubmit(modifyformData)">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
export default {
  data(){
    return{
      name: "TeacherInfo",
      addteacherData: []
    }
  },
  methods:{
    //获取表格信息
    getData(){
      this.servers.get('/msg',this.form).then((res) => {
        // console.log(res);
        if(res.status==200){

          this.tableData=[res.data.data]
        }
      }).catch((err) => {
        console.log(res);
      })
    },
    //修改数据的方法
    updataInfo(row) {
      console.log(row),
          //显示弹窗状态
          this.state=false
      this.modifyDialogVisible = true
      this.form=row
    },
    sure(form) {
      // console.log(this.form);
      this.$refs[form].validate((result) => {
        if (result) {
          //通过调用新增信息接口
          this.tableData.push(this.form)
          this.dialogFormVisible = false
          this.form={}
          if(this.state){
            //如果是ture调用添加接口

          }else{
            //调用修改的接口
          }
        }else{
          console.error(this.form);
        }
      })
  }

}
</script>

<style scoped>

</style>