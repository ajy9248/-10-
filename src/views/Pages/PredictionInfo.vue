<template>
  <div class="startPrediction">
    <el-dialog title="开始预报名" v-model="startDialogVisible" width="20%" :append-to-body="true">
      <el-form :model="startformData">
        <el-form-item label="场次：" prop="session">
          <el-input v-model="startformData.session" size="small" style="width: 200px">></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="startDialogVisible=false">取 消</el-button>
          <!--将信息提交到后台-->
          <el-button type="primary" @click="startSubmit(startformData)">确 定</el-button>
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
      <td>
        &nbsp;&nbsp;&nbsp;&nbsp;
      <input type="text" v-model = "student_Id" placeholder="请输入查询的学号">
      <el-button @click="getPredictionById">查询</el-button>&nbsp;&nbsp;&nbsp;&nbsp;
      </td>
      <td>
      类型：<select name="" id="" v-model="operation">
      <option value="" disabled>--请选择--</option>
      <option>免费团报</option>
      <option>自费团报</option>
      </select>
      <el-button @click="getPredictionByType">查询</el-button>
      </td>
      <td>
        <el-upload
                action="/上传文件的接口"
                :on-change="onChange"
                :auto-upload="false"
                :show-file-list="false"
                accept=".xls, .xlsx"
                ref="upload"
                :multiple="true">
        <el-button>导入报名信息</el-button>
        </el-upload>
      </td>
      <td>
        <el-button @click="exportClick">导出报名信息</el-button>

      </td>
      <td>
        <div style="text-align: right; margin: 0">
      <el-button @click = "startPre()" type="primary">开始预报名</el-button>
      <el-button @click = "endPre()" type="danger">停止预报名</el-button>
        </div>
      </td>
      <br>
  </div>
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
</template>
<script>
  import "async";
  import FileSaver from "file-saver";
  import * as XLSX from "xlsx";
  import { at } from "lodash";
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
        startformData: [],
        endformData:[],
        startDialogVisible: false,
        endDialogVisible: false,
        at
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
        //eslint-disable-next-line no-unused-vars
        const { data } =await inquirePredictionSession({});
        console.log(data)
        if (data.code ==0 || data.code == 200) {
          //表单中的数据等于后台返回的
          console.log(data.data)
          this.endformData = data.data
        }
        else {
          //提示错误
          alert(data.msg);
        }
      },
      async endPre () {
        this.endDialogVisible = true;
      },
      async startSubmit (startformData) {
        const that = this;
        const { data } = await submitSession(
                startformData);
        if (data.code ==0 || data.code == 200) {
          that.startDialogVisible = false;
          alert("操作成功！");
        }
        else {
          alert(data.msg);
        }
      },
      async endSubmit (endformData) {
        endformData.session="暂无场次";
        const that = this;
        const { data } = await submitSession(
                endformData);
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
  .orders {
    margin: 20px;
  }
  .el-button {
    margin-left: 20px;
  }

  .addorders {
    width: 500px;
    height: 400px;
    position: fixed;
    left: 50%;
    top: 50%;
  }
  .modifyorders {
    width: 500px;
    height: 400px;
    position: fixed;
    left: 50%;
    top: 50%;
  }
  .goods {
    width: 500px;
    height: 400px;
    position: fixed;
    left: 50%;
    top: 50%;
  }
</style>
