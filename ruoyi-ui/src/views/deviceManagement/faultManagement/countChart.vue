<template>
    <div ref="chart" style="width: 100%; height: 600px;"></div>
</template>

<script setup name="Plan">
import * as echarts from 'echarts';
import { onMounted, watch, ref, nextTick } from 'vue';


const props = defineProps({
    chartData: {
        type: Object,
        required: true,
    },
});


// 英文月份到中文的映射
const monthMapping = {
    Jan: '一\n月',
    Feb: '二\n月',
    Mar: '三\n月',
    Apr: '四\n月',
    May: '五\n月',
    Jun: '六\n月',
    Jul: '七\n月',
    Aug: '八\n月',
    Sep: '九\n月',
    Oct: '十\n月',
    Nov: '十\n一\n月',
    Dec: '十\n二\n月'
};

// 解析传入的chartData并提取所需数据
const parseChartData = (chartData) => {
    const data = chartData.data || {};
    console.log('chartData', chartData);
    // 提取月份和对应的数据，并过滤掉值为空的月份
    const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
    const validEntries = months.map(month => ({
        month: monthMapping[month], // 使用中文月份
        value: parseFloat(data[`indicator${month}`]),
        target: parseFloat(data.indicatorTarget),
        targetUp: parseFloat(data.indicatorUp),
        targetDown: parseFloat(data.indicatorDown)
    })).filter(entry => !isNaN(entry.value)); // 过滤掉无效值
    console.log('validEntries--->', validEntries)
    const categories = validEntries.map(entry => entry.month);
    const barData = validEntries.map(entry => entry.value);
    // 使用indicatorTarget作为lineData的值
    const lineData = validEntries.map(() => validEntries[0]?.target || 0);

    return {
        categories,
        barData,
        lineData,
        targetValue: validEntries[0]?.target || 0,
        targetUp: validEntries[0]?.targetUp || 0,
        targetDown: validEntries[0]?.targetDown || 0,
    };
};

let parsedData = parseChartData(props.chartData);

console.log('解析后的数据:', parsedData);

const chart = ref(null);
let myChart = null;

const initChart = () => {
    if (chart.value) {
        myChart = echarts.init(chart.value);
        updateChart();
    }
};

const updateChart = () => {
    if (!myChart) return;
    myChart = echarts.init(chart.value);

    const option = {
        animation: false,
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'cross',
                crossStyle: {
                    color: '#999'
                }
            }
        },
        // toolbox: {
        //     feature: {
        //         saveAsImage: {}
        //     }
        // },
        legend: {
            data: ['指标值', '目标值']
        },
        xAxis: [
            {
                type: 'category',
                data: parsedData.categories,
                axisPointer: {
                    type: 'shadow'
                }
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: '指标值',
            },
            {
                type: 'value',
                name: '目标值',
            }
        ],
        series: [
            {
                name: '指标值',
                type: 'bar',
                data: parsedData.barData,
                itemStyle: {
                    color: function (params) {
                        const value = params.value;
                        const targetDown = parsedData.targetDown;
                        const targetUp = parsedData.targetUp;
                        let color = '#5470c6';
                        // 如果 targetDown 存在且值小于 targetDown，则设置为红色
                        if (targetDown !== undefined && targetDown != '' && value < targetDown) {
                            color = '#de6673';
                        }
                        // 如果 targetUp 存在且值大于 targetUp，则设置为红色
                        else if (targetUp !== undefined && targetUp != '' && value > targetUp) {
                            color = '#de6673';
                        }

                        return color;
                    }
                },
                label: {
                    show: true,
                    position: 'top',
                    valueAnimation: true,
                    formatter: '{c}'
                }
            },
            {
                name: '目标值',
                type: 'line',
                data: parsedData.lineData
            }
        ],

    };
    myChart.setOption(option);
    myChart.on('finished', () => {
        const base64 = myChart.getDataURL({
            type: 'png',
            pixelRatio: 2,
            backgroundColor: '#fff'
        });
    });

};

onMounted(() => {
    nextTick(() => {
        initChart();
    });
});

watch(() => props.chartData, (newVal) => {
    parsedData = parseChartData(newVal);
    updateChart();
}, { deep: true });

const getChartBase64 = () => {
    return myChart?.getDataURL({
        type: 'png',
        pixelRatio: 2,
        backgroundColor: '#fff'
    });
};

defineExpose({
    getChartBase64
});

</script>

<style scoped>
/* 可根据需要添加样式 */
</style>