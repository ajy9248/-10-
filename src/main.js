/* eslint-disable no-undef */
import { createApp } from 'vue'
import App from './App.vue'
import axios from "axios"
import router from './routers'
import ElementPlus from 'element-plus'
import './assets/css/global.css'
import 'element-plus/dist/index.css'
import 'vue3-layer/dist/s3Layer.css';
import  Chart  from 'echarts';
createApp(App).use(router).use(ElementPlus).mount('#app');

