<template>
    <div class="block">
        <PageHeader v-show="header1">
            <el-page-header @back="goBack" :content="header2">
            </el-page-header>
        </PageHeader>

        <el-timeline>
            <ListItem v-for="item in list" :item="item"></ListItem>
        </el-timeline>
        <div v-show="total===0" style="text-align: center">
            暂无内容
        </div>

            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="queryInfo.pagenum"
                    :page-sizes="[1,3, 5]"
                    :page-size="queryInfo.pagesize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total"
                    class="el-pagination"
            >
            </el-pagination>

    </div>
</template>

<script>
    import ListItem from "./ListItem";
    import PageHeader from "../../components/PageHeader";
    export default {
        name: "List",
        components:{ListItem,PageHeader},
        data(){
            return{
                list:[],
                queryInfo:{
                    query:'',
                    pagenum:1,
                    pagesize:5
                },
                total:0,
                asycUrl:''
            }
        },
        props:{url:'',header1:'',header2:''},
        methods:{
            goBack(){
                this.$router.back()
            },
            getList(){
                const _this=this
                this.$http.get(_this.asycUrl===''?_this.url:_this.asycUrl,{params:_this.queryInfo}).then(res=>{
                    if(res.data.status==200){
                        _this.list=res.data.data.postList.list
                        _this.total=res.data.data.postList.total
                    }
                    else {
                        _this.$message.error(res.data.message)
                        setTimeout(()=>{
                            _this.goBack()
                        },3000)

                    }
                })
            },
            handleSizeChange(newSize){
                this.queryInfo.pagesize=newSize
                this.getList()
            },
            handleCurrentChange(newPage){
                this.queryInfo.pagenum=newPage
                this.getList()
            },

        },

        mounted() {
            this.getList()
        },

    }
</script>

<style scoped>
   .el-pagination{
       margin: auto;
       text-align: center;
   }

</style>