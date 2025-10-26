import axios from "axios";
import Element from 'element-ui';
import router from "./router";
import store from "./store";


// ベースURLを設定
axios.defaults.baseURL="http://localhost:8888/retwisApi/"

// サーバーに到達する前に、use 内のこのコールバックが呼ばれ、リクエストヘッダを追加します
axios.interceptors.request.use(config=>{
    config.headers.token=window.sessionStorage.getItem('token')
    return config
})

axios.interceptors.response.use(response=>{
    let res=response.data.status
    if(response.config.url==="/message/exists"){
        return response
    }
    if(response.data.code==="401"||response.data.code===401)
    {
        router.push("/login")
        store.commit("REMOVE_INFO")
        Element.Message.error('登录失效', {duration: 3 * 1000})
        return false
    }
    if(res===401||res==="401"){
        router.push("/login")
        store.commit("REMOVE_INFO")
        Element.Message.error('登录失效', {duration: 3 * 1000})
        return false
    }
    if(res)
    if(res==200||res===400)
        return response
    else
        Element.Message.error(response.data.message,{duration: 3 * 1000})
},error => {
    return err.response;
})
