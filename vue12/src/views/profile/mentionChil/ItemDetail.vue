<template>
    <div >
        <PageHeader>
            <el-page-header @back="goBack" content="详情">
            </el-page-header>
        </PageHeader>
        <h2>提醒内容:</h2>
        <div class="markdown-body" v-html="data.content"></div>
        <el-divider/>
        <p style="text-align: center"><router-link :to="'/detail/'+data.pid"><el-link type="primary">原文详尽信息</el-link></router-link></p>
        <br>
        <el-divider/>
        <div>tip: 原文可能已被修改或删除 此内容为被提醒时的内容</div>
        <br>
    </div>
</template>

<script>
    import PageHeader from "../../../components/PageHeader";
    export default {
        name: "ItemDetail",
        components:{PageHeader},
        data(){
            return{
                data:{}
            }
        },
        methods:{
            goBack(){
                this.$router.back()
            }
        },
        mounted() {
            this.$http.get("/profile/findMentionById/"+this.$route.params.id).then(res=>{
                if(res.data.status===200){
                    this.data=res.data.data.data
                    this.data.content = this.md2html(this.data.content)
                }
                else{
                    _this.$message.error("访问错误")
                    setTimeout(()=>{
                        _this.$router.back()
                    },2000)

                }
            })
        }
    }
</script>

<style scoped>

</style>