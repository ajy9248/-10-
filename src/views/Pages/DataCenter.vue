<template>
    <div class="total_list">
        <div class="topline">
            <td>
                &nbsp;&nbsp;&nbsp;&nbsp;
                <input type="text" v-model = "session" placeholder="请输入查询的场次">&nbsp;&nbsp;
                <el-button @click="getTotalData">查询</el-button>&nbsp;&nbsp;&nbsp;&nbsp;
                <el-button type="primary" @click="getAbsence">查询缺考人数</el-button>&nbsp;&nbsp;&nbsp;&nbsp;
                <el-button @click="exportClick">导出表单</el-button>

                <el-dialog title="缺考查询" v-model="DialogVisible" width="20%" :append-to-body="true">
                    <el-form-item label="场次：" prop="session" property="session"></el-form-item>
                    <el-table :data="absenceData">
                        <el-table-column prop="student_id" label="学号" sortable width="200"></el-table-column>
                    </el-table>
                    <template #footer>
                        <span class="dialog-footer">
                            <el-button @click="DialogVisible=false">确定</el-button>
                        </span>
                    </template>
                </el-dialog>
            </td>
        </div>
        <br>
        <el-table height="400" border style="width: 100%" id="datacenterTable">
            <!--饼图-->
            <div id="myChart1" style="width: 50%;height: 200px;"></div>
            <div id="myChart2" style="width: 50%;height: 200px;"></div>
            <!--柱状图-->
            <div id="myChart3" style="width: 100%;height: 200px;"></div>
        </el-table>
    </div>
</template>

<script>
    import echarts from 'echarts';
    import {inquireAbsence} from '@/api/datacenter/inquireAbsence';
    import {inquireScoreByRange} from "@/api/datacenter/inquireScoreByRange";
    import {inquireSumByGrade} from "@/api/datacenter/inquireSumByGrade";
    import {inquireSumByType} from "@/api/datacenter/inquireSumByType";
    import * as XLSX from "xlsx";
    import html2canvas from "html2canvas";

    export default {
        data() {
            return {
                name: "DataCenter",
                session: '',
                formData: [],
                absenceData: [],
                DialogVisible: false,
                chartData1: [],
                chartData2: [],
                chartData3: [],
            }
        },
        mounted(){
            // 界面数据从后台获取
            this.initChart1();
            this.initChart2();
            this.initChart3();
        },
        beforeUnmount() {
            if (!this.chart) {
                return
            }
            this.chart.dispose()
            this.chart = null
        },
        methods: {
            async getTotalData() {
                const session = this.session;
                this.getData1();
                this.getData2();
                this.getData3();
            },
            async getData1() {
                const session = this.session;
                const {data1} = await inquireSumByType(session);
                if (data1.code === 200) {
                    let arr1 = []
                    arr1.push(data1.data)
                    console.log(arr1)
                    this.chartData1 = arr1
                } else {
                    alert(data.msg);
                }
            },
            async getData2() {
                const session = this.session;
                const {data2} = await inquireSumByGrade(session);
                if (data2.code === 200) {
                    let arr2 = []
                    arr2.push(data2.data)
                    console.log(arr2)
                    this.chartData2 = arr2
                } else {
                    alert(data.msg);
                }
            },
            async getData3() {
                const session = this.session;
                const {data3} = await inquireScoreByRange(session);
                if (data3.code === 200) {
                    let arr3 = []
                    arr3.push(data3.data)
                    console.log(arr3)
                    this.chartData3 = arr3
                } else {
                    alert(data.msg);
                }
            },
            async getAbsence() {
                this.DialogVisible = true;
                const session = this.session;
                const {data} = await inquireAbsence(session);
                if (data.code === 200) {
                    let arr = []
                    arr.push(data.data)
                    console.log(arr)
                    this.absenceData = arr
                } else {
                    alert(data.msg);
                }
            },
            initChart1() {
                console.log("加载");
                var myChart1 = echarts.init(document.getElementById("myChart1"));
                // 绘制图表
                myChart1.setOption({
                    title: {
                        text: '不同报名类型人数',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: "item",
                        formatter: "{a} <br/>{b} : {c} ({d}%)",
                    },
                    legend: {
                        orient: 'vertical',
                        bottom: 'bottom',
                        data: this.chartData1
                    },
                    color: ['#32dadd', '#b6a2de'],
                    series: [
                        {
                            name: "类型",
                            type: "pie",
                            radius: "55%",
                            center: ["50%", "50%"],//位置
                            data: this.chartData1,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: "rgba(0, 0, 0, 0.5)",
                                },
                            },
                        },
                    ],
                });
            },
            initChart2() {
                console.log("加载");
                var myChart2 = echarts.init(document.getElementById("myChart2"));
                // 绘制图表
                myChart2.setOption({
                    title: {
                        text: '各年级报名人数',
                        x: 'center'
                    },
                    tooltip: {
                        trigger: "item",
                        formatter: "{a} <br/>{b} : {c} ({d}%)",
                    },
                    legend: {
                        orient: 'vertical',
                        bottom: 'bottom',
                        data: this.chartData2
                    },
                    color: ['#32dadd', '#b6a2de', '#5ab1ef', '#005EEA'],
                    series: [
                        {
                            name: "年级",
                            type: "pie",
                            radius: "55%",
                            center: ["50%", "50%"],//位置
                            data: this.chartData2,
                            itemStyle: {
                                emphasis: {
                                    shadowBlur: 10,
                                    shadowOffsetX: 0,
                                    shadowColor: "rgba(0, 0, 0, 0.5)",
                                },
                            },
                        },
                    ],
                });
            },
            initChart3(){
                console.log("加载");
                var myChart3 = echarts.init(document.getElementById("myChart3"));
                myChart3.setOption({
                    title: { text: '成绩分布' },  //图表标题
                    tooltip: {},
                    xAxis: {
                        name: '',
                        data: ["0-100分","100-200分","200-300分","300-400分","400-500"]  //x轴数据
                    },
                    yAxis: {
                        name: '人数',     //y轴标题
                        type: 'value',
                    },
                    series: [{
                        type: 'bar',      //图表形状
                        //柱状图的颜色
                        itemStyle:{
                            normal:{
                                color:'#4ad2ff'
                            }
                        },
                        barWidth : 10,    //柱图宽度
                        data: this.chartData3,  //图表数据
                    }]
                })
            },
            exportClick(){
                const chart1 = document.getElementById("myChart1").getElementsByTagName('canvas')
                const chart2 = document.getElementById("myChart2").getElementsByTagName('canvas')
                const chart3 = document.getElementById("myChart1").getElementsByTagName('canvas')
                // 创建标签
                const element = document.createElement("a")
                // 设置下载名称
                element.download = "v-charts图表下载" +".png"
                // 设置地址以及文件类型
                element.href = chart1[0].toDataURL("image1/png")
                document.body.appendChild(element)
                // 触发下载事件
                element.click()
                // 移除标签
                element.remove()
            }

        }

    }
</script>

<style scoped>

</style>
