import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
// Element UI
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
// アイコン
import './assets/css/global.css'
import './assets/fonts/iconfont.css'
// リッチテキストエディタ
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import showdown from 'showdown'
// import hljs from 'highlight.js/lib/highlight';
import 'highlight.js/styles/monokai-sublime.css';

import './axious'

Vue.prototype.$http=axios
Vue.prototype.$bus = new Vue()

var showdownHighlight = require("showdown-highlight")
Vue.prototype.md2html = (md)=> {
  let converter = new showdown.Converter({
    extensions:[showdownHighlight]
  })
  let text = md.toString()
  let html = converter.makeHtml(text)
  return html
}
Vue.use(ElementUI)
Vue.use(mavonEditor)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
