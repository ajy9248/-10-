import axios from 'axios'
const instance = axios.create({
    baseURL: 'http://localhost:3000',
});
//添加一个请求拦截器
config => {
    return config;
},
error => {
    console.log(error);
    return Promise.reject();
};

instance.interceptors.request.use((config) => {
        let temp = '';
        temp = localStorage.getItem('userToken');
        if(temp == null)return config;
        const token = temp
        if (token) {
            // 请求头携带token
            config.headers['Authorization'] = JSON.parse(token);
        }
        console.log('正在加载....')
        return config
    }, (error) => {
        return Promise.reject(error)
    }
)

export default instance;