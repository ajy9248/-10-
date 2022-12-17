<template>
  <div class="stbox">
    <el-row :gutter="23">
      <el-col :span="8">
        <el-form-item label="考试场次">
          <el-select
              placeholder="请选择考试场次"
              v-model="querysession"
              maxlength="255"
              :disabled="false"
              @change="selectChanged($event)"
              clearable>
            <el-option
              v-for="item in options"
              :key="item.label"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="10">
        <div class="highest_average">
          <el-table :data="scoredata" height="75" border style="width: 100%">
            <el-table-column prop="high" label="最高分" :formatter="formatter">
            </el-table-column>
            <el-table-column prop="avg" label="平均分" :formatter="formatter">
            </el-table-column>
          </el-table>
        </div>
      </el-col>
      <el-col :span="6">
        <el-button style="margin-right: 10px" @click="exportClick" type="success">导出表单</el-button>
      </el-col>
    </el-row>
    <!-- 饼状图第一行-->
    <el-row :gutter="23">
      <el-col :span="12" class="text-c">
        <div class="paybox">
          <div class="cavasbox" ref="SumTPchart"></div>
        </div>
      </el-col>
      <el-col :span="12" class="text-c">
        <div class="paybox">
          <div class="cavasbox" ref="SumGDchart"></div>
        </div>
      </el-col>
    </el-row>
  <!--柱状图第二行-->
    <el-row :gutter="23">
      <el-col class="text-c">
        <div class="st-gbox">
          <div class="cavasbox" ref="ScoreRGchart"></div>
        </div>
      </el-col>
    </el-row>
    <t1>缺考学生信息</t1>
    <el-row>
      <!--缺考的学生表单-->
      <div class="Absence">
        <el-table :data="Absencedata" height="400" border style="width: 100%">
          <el-table-column prop="uid" label="学号" sortable :formatter="formatter">
          </el-table-column>
          <el-table-column prop="uname" label="学生姓名" :formatter="formatter">
          </el-table-column>
          <el-table-column prop="grade" label="年级" sortable :formatter="formatter">
          </el-table-column>
        </el-table>
      </div>
    </el-row>
  </div>
</template>
<script type="text/ecmascript-6">
import  Chart  from 'echarts';
import { inquireSumByGrade} from '@/api/datacenter/inquireSumByGrade';
import { inquireSumByType } from '@/api/datacenter/inquireSumByType';
import { inquireScoreByRange} from '@/api/datacenter/inquireScoreByRange';
import { inquireAllSession } from '@/api/datacenter/inquireAllSession'
import { inquireAbsence } from '@/api/datacenter/inquireAbsence'
import { inquireHighest } from '@/api/datacenter/inquireHighest'
import { inquireAverage } from '@/api/datacenter/inquireAverage'
import * as XLSX from "xlsx";
import jsCookie from "js-cookie";
export default {
  name: "data_center",
  data() {
    return {
      machineNo: '',
      TypeSum:[],
      GradeSum:[],
      ScoreRange:[],
      querysession:'',
      scoredata:[
        //   {
        // high: 350,
        // avg:170}
      ],
      Absencedata: [
      //     {
      //   uid: 1,
      //   uname: "张三",
      //   grade: "2020级"
      // },{uid: 25,
      //   uname: "李四",
      //   grade: "2022级"}
      ],
      options:[
        // {
        // label:'第28次', value:'28'
        // },
        // {
        //   label:'第27次', value:'27'
        // },
        // {
        //   label:'第26次', value:'26'
        // },
        // {
        //   label: '第25次', value: '25'
        // }
      ],
      //  成绩分布柱状图
      ScoreRGoption: {
        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b}分 : {c}"
        },
        legend: {
          data: [{
            name: '成绩分布',
            icon: 'rect'
          }],
          top: 1,
          left: 1,
          itemGap: 10,
          itemWidth: 12,
          itemHeight: 12,
          textStyle: {
            fontSize: 14,
            color: "#323232"
          }
        },
        grid: {
          left: 50,
          right: 10,
          top: 30,
          bottom: 30,
          borderWidth: 1
        },
        xAxis: {
          type: 'category',
          data: ['0-100', '100-200', '200-300', '300-400', '400-500'],
          axisLine: {
            lineStyle: {
              color: "#999999",
              width: 1
            }
          },
          axisLabel: {
            margin: 14,
            height: 70,
            interval: 0,
            textStyle: {
              fontSize: 10,
              color: "#999999"
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLine: {
            lineStyle: {
              color: "#999999",
              width: 1
            }
          },
          axisLabel: {
            margin: 14,
            textStyle: {
              fontSize: 10,
              color: "#999999"
            }
          }
        },
        series: [{
          name: '成绩分布',
          type: 'bar',
          barGap: 0,
          barWidth: 10,
          data: this.ScoreRange,
          // data:[60, 22, 2, 1, 0],
          itemStyle: {
            normal: {
              color: new Chart.graphic.LinearGradient(
                  0, 0, 0, 1,
                  [
                    { offset: 0, color: '#83bff6' },
                    { offset: 0.5, color: '#188df0' },
                    { offset: 1, color: '#188df0' }
                  ]
              )
            },
            emphasis: {
              color: new Chart.graphic.LinearGradient(
                  0, 0, 0, 1,
                  [
                    { offset: 0, color: '#2378f7' },
                    { offset: 0.7, color: '#2378f7' },
                    { offset: 1, color: '#83bff6' }
                  ]
              )
            }
          }
        }]
      },
      //  不同类型的报名人数
      SumTPoption: {
        backgroundColor: '#2c343c',
        title: {
          text: '报名类型统计',
          left: 10,
          top: 5,
          textStyle: {
            fontSize: 14,
            fontStyle:"italic",
            color: '#ccc'
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        visualMap: {
          show: false,
          min: 80,
          max: 600,
          inRange: {
            colorLightness: [0, 1]
          }
        },
        series: [{
          name: '报名类型统计',
          type: 'pie',
          radius: '55%',
          center: ['50%', '50%'],

          data: this.TypeSum,
          // data:[{ value: 400, name: '免费报名' },
          //   { value: 200, name: '自费报名' }
          // ].sort(function (a, b) { return a.value - b.value; }),

          roseType: 'radius',
          label: {
            normal: {
              textStyle: {
                color: 'rgba(255, 255, 255, 0.3)'
              }
            }
          },
          labelLine: {
            normal: {
              lineStyle: {
                color: 'rgba(255, 255, 255, 0.3)'
              },
              smooth: 0.2,
              length: 10,
              length2: 20
            }
          },
          itemStyle: {
            normal: {
              color: '#c23531',
              shadowBlur: 200,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          },
          animationType: 'scale',
          animationEasing: 'elasticOut',
          animationDelay: function (idx) {
            return Math.random() * 200;
          }
        }]
      },
      //  不同年级的报名人数
      SumGDoption: {
        backgroundColor: '#2c343c',
        title: {
          text: '报名年级统计',
          left: 10,
          top: 5,
          textStyle: {
            fontSize: 14,
            fontStyle:"italic",
            color: '#ccc'
          }
        },
        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        visualMap: {
          show: false,
          min: 80,
          max: 600,
          inRange: {
            colorLightness: [0, 1]
          }
        },
        series: [{
          name: '报名年级统计',
          type: 'pie',
          radius: '55%',
          center: ['50%', '50%'],

          data: this.GradeSum,
        //   data: [{ value: 300, name: '2020级' },
        //   { value: 310, name: '2021级' },
        //   { value: 220, name: '2022级' },
        //   { value: 110, name: '2023级' }
        // ].sort(function (a, b) { return a.value - b.value; }),

          roseType: 'radius',
          label: {
            normal: {
              textStyle: {
                color: 'rgba(255, 255, 255, 0.3)'
              }
            }
          },
          labelLine: {
            normal: {
              lineStyle: {
                color: 'rgba(255, 255, 255, 0.3)'
              },
              smooth: 0.2,
              length: 10,
              length2: 20
            }
          },
          itemStyle: {
            normal: {
              color: '#c23531',
              shadowBlur: 200,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          },
          animationType: 'scale',
          animationEasing: 'elasticOut',
          animationDelay: function (idx) {
            return Math.random() * 200;
          }
        }]
      }
    }
  },
  // 导入组件
  components: {
    // 点聚合组件
  },
  // 创建完毕状态(里面是操作)
  created() {
    this.getOptions()
  },
  // 挂载结束状态(里面是操作)
  mounted() {
    this.getSumByType()
    this.getSumByGrade()
    this.getScoreByRange()
  },
  methods: {
    //初始时获取所有可选下拉框
    async getOptions() {
      try {
        const selectResult = await inquireAllSession()
        if(selectResult){
          //查出值之后对之前声明好的变量进行赋值
          this.options = selectResult
        }else{
          this.$message.info(
              '没有可选择的下拉框'
          )
        }
      } catch (err) {
        this.$notify({
          title: '初始化下拉框失败',
          message: err.message,
          type: 'warning',
          showClose: false
        })
      } finally {
        this.dialogLoading = false
      }
    },
    //下拉框选项被改变时,重新存储value
    selectChanged(params) {
      this.session=params
      this.getSumByType()
      this.getSumByGrade()
      this.getScoreByRange()
      this.getAbsence()
      this.getScoreInfo()
    },
    //获取最高分和平均分
    async getScoreInfo() {
      const {high} = await inquireHighest(this.session);
      const {avg} = await inquireAverage(this.session);

      if (avg.code === 200 && high.code === 200) {
        this.scoredata.avg = avg.data
        this.scoredata.high = high.data
      } else {
        alert(avg.msg);
        alert(high.msg);
      }
    },
    // 报名类型统计
    async getSumByType() {
      this.chart = Chart.init(this.$refs.SumTPchart)
      // this.chart.setOption(this.SumTPoption)
      const { data } =await inquireSumByType(this.session
      );
      if (data.code == 200) {
        console.log(data.data)
        this.chart.setOption({
          series: [{
            data: data.data
          }]
        })
      }
      else {
        alert(data.msg);
      }
    },
    // 报名年级统计
    async getSumByGrade() {
      this.chart = Chart.init(this.$refs.SumGDchart)
      // this.chart.setOption(this.SumGDoption)
      const { data } =await inquireSumByGrade(this.session
      );
      if (data.code == 200) {
        console.log(data.data)
        this.chart.setOption({
          series: [{
            data: data.data
          }]
        })
      }
      else {
        alert(data.msg);
      }
    },
    //成绩分布
    async getScoreByRange() {
      this.chart = Chart.init(this.$refs.ScoreRGchart)
      // this.chart.setOption(this.ScoreRGoption,true)
      const { data } =await inquireScoreByRange(this.session
      );
      let ScoreRange = []
      for(let i=0; i<data.data.length; i++){
        ScoreRange.push(data.data[i].range);
      }
      if (data.code == 200) {
        console.log(ScoreRange)
      }
      else {
        alert(data.msg);
      }
    },
    //获取缺考的列表
    async getAbsence() {
      const {data} = await inquireAbsence(this.session);
      if (data.code === 200) {
        this.Absencedata = data.data
      } else {
        alert(data.msg);
      }
    },
    //导出
    exportClick() {
      //第一个参数是到处后文件名，第二个是id绑定表格dom
      this.exportExcel("datacenter", "DataCenterTable");
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
  }
};
</script>
<style>
.stbox {
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}

.stbgc {
  background-color: #fff;
  height: 60px;
  line-height: 60px;
  border-radius: 5px;
  padding: 0px 16px;
}

.stsearch {
  text-align: center;
}
.text-c {
  text-align: center;
}

.st-gbox {
  background-color: #fff;
  margin-top: 20px;
  margin-left: 250px;
  border-radius: 5px;
  height: 30vh;
  width:50%;
  box-sizing: border-box;
  padding: 10px
}
.cavasbox {
  box-sizing: border-box;
  width: 100%;
  height: 100%;
}

.paybox {
  width: 100%;
  background-color: #fff;
  box-sizing: border-box;
  border-radius: 5px;
  margin-top: 20px;
  height: 32vh;
}
.Absence{
  margin-top : 10px;
  display: flex;
  flex : 1;
}
</style>
