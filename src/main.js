/* eslint-disable no-undef */
import { createApp } from 'vue'
import App from './App.vue'
import router from './routers'
import ElementPlus from 'element-plus'
import './assets/css/global.css'
import 'element-plus/dist/index.css'
import 'vue3-layer/dist/s3Layer.css';
import "element-ui/lib/theme-chalk/index.css";
import { read, utils } from "xlsx";
Vue.use(ElementUI);
createApp(App).use(router).use(ElementPlus).mount('#app');

