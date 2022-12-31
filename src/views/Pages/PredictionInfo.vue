<template>
<div class="startPrediction">
    <el-dialog title="开始预报名" v-model="startDialogVisible" width="20%" :append-to-body="true">
        <el-form>
            <el-form-item label="场次：" prop="session">
                <el-input v-model="querysession" size="small" style="width: 200px">></el-input>
            </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
          <el-button type="info" @click="startDialogVisible=false">取 消</el-button>
            <!--将信息提交到后台-->
          <el-button type="primary" @click="startSubmit()">确 定</el-button>
        </span>
        </template>
    </el-dialog>
</div>

<div class="endPrediction">
    <el-dialog title="你确定要停止本次预报名吗？" v-model="endDialogVisible" width="20%" :append-to-body="true">
        <template #footer>
        <span class="dialog-footer">
          <el-button type="info" @click="endDialogVisible=false">取 消</el-button>
            <!--将信息提交到后台-->
          <el-button type="primary" @click="endSubmit()">确 定</el-button>
        </span>
        </template>
    </el-dialog>
</div>

<div class="total_list">
    <div class="topline" style="margin-left: 100px">
        <div class="search_box">
            <el-input v-model="student_id" placeholder="请输入查询的学号"></el-input>
            <el-button style="margin-left: 20px;" type="info" @click="getPredictionById">查询</el-button>&nbsp;&nbsp;&nbsp;
        </div>
        <div class="excels" style="margin-left: 50px">
            <el-button @click = "startPre()" type="warning">开始预报名</el-button>
            <el-button style="margin-left: 20px" @click = "endPre()" type="danger">停止预报名</el-button>
        </div>
            <el-button style="margin-left: 20px" @click="exportClick" type="primary">导出信息</el-button>
    </div>
    <div class="predictions">
        <el-table :data="tableData" height="400" border style="width: 100%" id="predictionTable">
            <el-table-column prop="uid" label="学号" sortable width="250">
            </el-table-column>
            <el-table-column prop="uname" label="姓名" :formatter="formatter">
            </el-table-column>
            <el-table-column prop="grade" label="年级" sortable width="150">
            </el-table-column>
            <el-table-column prop="type" label="类型" sortable width="200">
            </el-table-column>
            <el-table-column prop="timeString" label="提交时间" sortable width="300">
            </el-table-column>
            <el-table-column prop="session" label="场次" sortable width="150">
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
    import { endSession } from '@/api/prediction/endSession';
    import { inquirePredictions } from '@/api/prediction/inquirePredictions'
    import { inquirePredictionById } from '@/api/prediction/inquirePredictionById'

    export default {
        data () {
            return {
                name: "PredictionInfo",
                tableData: [],
                endformData:[],
                subFreeForm:[],
                student_id:'',
                inquireType:'',
                querysession: '',
                startDialogVisible: false,
                endDialogVisible: false,
            };
        },
        created () {
            this.getPredictionsList();
        },
        methods: {
            //导出
            exportClick() {
                //第一个参数是到处后文件名，第二个是id绑定表格dom
                this.exportExcel("PredictionInfo", "predictionTable");
            },
            exportExcel(filename, tableId) {
                const xlsxParam = {raw: true}; // 导出的内容只做解析，不进行格式转换
                const table = document.querySelector("#" + tableId).cloneNode(true);
                const wb = XLSX.utils.table_to_book(table, xlsxParam);
                /* 获取二进制字符进行输出 */
                const wbout = XLSX.write(wb, {
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
                if (data.state == 200) {
                    this.tableData = data.data
                }
                else {
                    alert(data.message);
                }
            },
            // 根据id查询学生信息
            async getPredictionById() {
                const uId = this.student_id;
                const {data} = await inquirePredictionById(uId);
                if (data.state === 200) {
                    let arr = []
                    for(let i=0; i<data.data.length; i++){
                      arr.push(data.data[i]);
                    }
                    this.tableData = arr
                } else {
                    alert(data.message);
                }
            },
            async startPre () {
                this.startDialogVisible = true;
            },
            async endPre () {
                this.endDialogVisible = true;
            },
          //开始预报名点击确定后
            async startSubmit () {
                const session = this.querysession;
                const { data } = await submitSession(session);
                if (data.state === 200) {
                    this.startDialogVisible = false;
                    alert("操作成功！");
                }
                else {
                    this.startDialogVisible = false;
                    alert(data.message);
                }
            },
          //停止预报名后
            async endSubmit () {
                const { data } = await endSession();
                this.endDialogVisible = false;
                if (data.state === 200) {
                    this.endDialogVisible = false;
                    alert("操作成功！预报名已结束");
                }
                else {
                    this.endDialogVisible = false;
                    alert(data.message);
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