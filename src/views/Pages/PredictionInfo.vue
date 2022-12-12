<template>
  <div class="startPrediction">
    <el-dialog title="开始预报名" v-model="startDialogVisible" width="20%" :append-to-body="true">
      <el-form>
        <el-form-item label="场次：" prop="session">
          <el-input v-model="session" size="small" style="width: 200px">></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="startDialogVisible=false">取 消</el-button>
          <!--将信息提交到后台-->
          <el-button type="primary" @click="startSubmit(session)">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>

  <div class="endPrediction">
    <el-dialog title="你确定要停止本次预报名吗？" v-model="endDialogVisible" width="20%" :append-to-body="true">
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="endDialogVisible=false">取 消</el-button>
          <!--将信息提交到后台-->
          <el-button type="primary" @click="endSubmit(endformData)">确 定</el-button>
        </span>
      </template>
    </el-dialog>
  </div>

  <div class="total_list">
    <div class="topline">
        <div class="search_box">
          <el-input v-model="student_id" placeholder="请输入查询的学号"></el-input>
          <el-button style="margin-top: 5px" @click="getPredictionById">查询</el-button>&nbsp;&nbsp;&nbsp;
        </div>
      <div style="margin-left: 30px">
      <td>
      类型：<el-select name="" id="" v-model="operation">
      <option value="" disabled>--请选择--</option>
      <option>免费团报</option>
      <option>自费团报</option>
      </el-select>
      <el-button style="margin-left: 20px" @click="getPredictionByType">查询</el-button>
      </td>
    </div>
      <td>
        <el-upload
                action="/上传文件的接口"
                :on-change="onChange"
                :auto-upload="false"
                :show-file-list="false"
                accept=".xls, .xlsx"
                ref="upload"
                :multiple="true">
        <el-button style="margin-left: 100px" type="primary">导入信息</el-button>
        </el-upload>
      </td>
      <td>
        <el-button style="margin-left: 20px" @click="exportClick" type="success">导出信息</el-button>
      </td>
      <br>
  </div>
    <div class="excels" style="margin-top: 15px">
      <el-button @click = "startPre()" type="warning">开始预报名</el-button>
      <el-button style="margin-left: 20px" @click = "endPre()" type="danger">停止预报名</el-button>
    </div>
    <div class="predictions">
      <el-table :data="tableData" height="400" border style="width: 100%" id="predictionTable">
        <el-table-column prop="student_id" label="学号" sortable width="200">
        </el-table-column>
        <el-table-column prop="student_name" label="姓名" :formatter="formatter">
        </el-table-column>
        <el-table-column prop="student_grade" label="年级" sortable width="150">
        </el-table-column>
        <el-table-column prop="prediction_type" label="类型" :formatter="formatter">
        </el-table-column>
        <el-table-column prop="prediction_time" label="提交时间" sortable width="300">
        </el-table-column>
        <el-table-column prop="prediction_session" label="场次" sortable width="150">
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>
<script>
  import "async";
  import FileSaver from "file-saver";
  import * as XLSX from "xlsx";
  import { submitSession } from '@/api/prediction/submitSession';
  import { inquirePredictionSession } from '@/api/prediction/inquirePredictionSession';
  import { inquirePredictions } from '@/api/prediction/inquirePredictions'
  import { inquirePredictionById } from '@/api/prediction/inquirePredictionById'
  import { inquirePredictionByType } from '@/api/prediction/inquirePredictionByType'
  export default {
    data () {
      return {
        name: "PredictionInfo",
        tableData: [],
        endformData:[],
        student_id:'',
        session: '',
        startDialogVisible: false,
        endDialogVisible: false,
      };
    },
    created () {
      this.getPredictionsList();
      this.tableData.forEach(item => {
        this.$set(item, 'isChecked', false) // 添加判断的字段
      })
    },
    methods: {
      //导入
      onChange(file, fileList) {
        this.readExcel(file); // 调用读取数据的方法
      },
      // 读取数据
      readExcel(file) {
        let that = this;
        if (!file) {
          //如果没有文件
          return false;
        } else if (!/.(xls|xlsx)$/.test(file.name.toLowerCase())) {
          this.$message.error("上传格式不正确，请上传xls或者xlsx格式");
          return false;
        }
        const fileReader = new FileReader();
        fileReader.onload = (ev) => {
          try {
            const data = ev.target.result;
            const workbook = XLSX.read(data, {
              type: "binary",
            });
            if (workbook.SheetNames.length >= 1) {
              this.$message({
                message: "导入数据表格成功",
                showClose: true,
                type: "success",
              });
            }
            const wsname = workbook.SheetNames[0]; //取第一张表
            const ws = XLSX.utils.sheet_to_json(workbook.Sheets[wsname]); //生成json表格内容
            console.log("生成json：", ws);
            // that.tableData = [];
            for (var i = 0; i < ws.length; i++) {
              let sheetData = {
                // 键名为绑定 el 表格的关键字，值则是 ws[i][对应表头名]
                student_id: ws[i]["学号"],
                student_name: ws[i]["姓名"],
                student_grade: ws[i]["年级"],
                prediction_type: ws[i]["类型"],
                prediction_time: ws[i]["提交时间"],
                prediction_session: ws[i]["场次"],
              };
              console.log("上传的数据:", sheetData);
              //添加到表格中
              that.tableData.push(sheetData);
              //正常导入需要拿到上传的数据就在这从新弄个数组push进去，然后传给后台，后台保存后查询表格返给前端。
            }
            this.$refs.upload.value = "";
          } catch (e) {
            console.log(e);
            return false;
          }
        };
        // 如果为原生 input 则应是 files[0]
        fileReader.readAsBinaryString(file.raw);
      },
      //导出
      exportClick() {
        //第一个参数是到处后文件名，第二个是id绑定表格dom
        this.exportExcel("test", "predictionTable");
      },
      exportExcel(filename, tableId) {
        var xlsxParam = { raw: true }; // 导出的内容只做解析，不进行格式转换
        var table = document.querySelector("#" + tableId).cloneNode(true);
        var wb = XLSX.utils.table_to_book(table, xlsxParam);
        /* 获取二进制字符进行输出 */
        var wbout = XLSX.write(wb, {
          bookType: "xlsx",
          bookSST: true,
          type: "array",
        });
        try {
          FileSaver.saveAs(
                  new Blob([wbout], { type: "application/octet-stream" }),
                  filename + ".xlsx"
          );
        } catch (e) {
          if (typeof console !== "undefined") {
            console.log(e, wbout);
          }
        }
        return wbout;
      },
      async getPredictionsList () {
        const { data } = await inquirePredictions({});
        if (data.code == 200) {
          this.tableData = data.data
        }
        else {
          alert(data.msg);
        }
      },
      // 根据id查询学生信息
      async getPredictionById() {
        const uId = this.student_Id;
        const {data} = await inquirePredictionById(uId);
        if (data.code === 200) {
          let arr = []
          arr.push(data.data)
          console.log(arr)
          this.tableData = arr
        } else {
          alert(data.msg);
        }
      },
      async getPredictionByType() {
        const uId = this.student_Id;
        const {data} = await inquirePredictionByType(type);
        if (data.code === 200) {
          let arr = []
          arr.push(data.data)
          console.log(arr)
          this.tableData = arr
        } else {
          alert(data.msg);
        }
      },
      async startPre () {
        this.startDialogVisible = true;
      },
      async endPre () {
        this.endDialogVisible = true;
      },
      async startSubmit () {
        const session = this.session;
        const { data } = await submitSession(
                session);
        if (data.code ==0 || data.code == 200) {
          that.startDialogVisible = false;
          alert("操作成功！");
        }
        else {
          alert(data.msg);
        }
      },
      async endSubmit () {
        this.session = "暂无场次";
        const session = this.session;
        const { data } = await submitSession(
                session);
        if (data.code ==0 || data.code == 200) {
          that.startDialogVisible = false;
          alert("操作成功！");
        }
        else {
          alert(data.msg);
        }
      }
    }
  }

</script>
<style>
  .total_list{
    height : 100%;
    width : 100%;
    align-items: center;
    flex-direction : column;
  }
  .topline{
    margin-top : 10px;
    display: flex;
    flex-direction : row;
    align-items: start;
  }
  .search_box{
    box-sizing: border-box;
    width : 28%;
    display: flex;
  }
  .excels{
    align-items: center;
  }
  .predictions{
    margin: 20px;
  }
</style>
