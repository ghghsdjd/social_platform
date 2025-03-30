<template>
    <div>
        <div class="serach">
            <el-input clearable v-model="serach" maxlength="15"
                      show-word-limit style="width: 40%"  placeholder="请输入搜索内容"></el-input>
            <el-button type="primary" @click="getData" style="height: 40px;margin-left: 5px" size="mini" icon="el-icon-search"></el-button>
        </div>
        <div class="content">
            <el-tabs v-model="activeName" type="card"  @tab-click="handleClick">
                <el-tab-pane label="文章" name="1">
                    <div v-for="item in postList">
                        <el-timeline>
                        <ListItem :item="item"></ListItem>
                        </el-timeline>
                    </div>
                    <div v-show="postList.length===0" style="text-align: center;margin-top: 10%;margin-bottom: 10%;">无搜索结果</div>
                </el-tab-pane>
                <el-tab-pane label="用户" name="2">
                    <div v-for="item in userList">
                        <UserItem :item="item"></UserItem>
                    </div>
                    <div v-show="userList.length===0" style="text-align: center;margin-top: 10%;margin-bottom: 10%;">无搜索结果</div>
                </el-tab-pane>
            </el-tabs>
        </div>
    </div>
</template>

<script>
    import ListItem from "./post/ListItem";
    import UserItem from "./profile/UserItem";
    export default {
        name: "serach",
        components:{ListItem,UserItem},
        data() {
            return {
                serach:'',
                userList:[],
                postList:[],
                activeName: '1',
            }
        },
        methods: {
            handleClick(tab, event) {

                this.activeName=tab.name
            },
            getData(){
                if(this.serach.length===0){
                    this.$message.error("输入内容不能为空")
                    return false
                }
                this.$http.get("/serach?content="+this.serach).then(res=>{
                    if(res.data.status===200){
                        this.postList=res.data.data.data.post
                        this.userList=res.data.data.data.users
                    }
                })

            }
        }

    }
</script>

<style scoped>
    .serach{
        display: flex;
        margin-top: 5px;
        margin-left: 5px;
    }
    .content{
        box-shadow: 0 2px 4px rgba(0, 0, 0, .12), 0 0 6px rgba(0, 0, 0, .04);

        margin-top: 10px;
    }
</style>