<template>
    <div >
        <el-tooltip class="item" effect="dark" content="点击回复" placement="top">
        <div @click="open()">
        <el-link @click="vistUser(item.uname)">
        <el-avatar :src="item.avatar" />
            <span>{{item.uname}}</span>
        </el-link>
        <span style="margin-left: 3%">{{item.content}}</span>
        </div>
        </el-tooltip>
        <el-divider></el-divider>
        <Answer style="margin-left: 20%" :list="item.answerList"></Answer>
    </div>
</template>

<script>
    import Answer from "./Answer";
    export default {
        name: "CommentItem",
        components: {Answer},
        props: {
            item: {
                answerList:[]
            }
        },
        methods: {
            vistUser(username) {
                this.$router.push("/profile/" + username)
            },
            open() {
                this.$prompt('回复' + this.item.uname, '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    inputValidator: value => {
                        return value.length >= 3 && value.length <= 50
                    },
                    inputErrorMessage: '长度为3-50'
                }).then(({value}) => {
                    this.$http.post("/answer/"+this.item.id,{'content':value}).then(res=>{
                        if(res.data.status===200){
                            this.item.answerList.splice(0,0,res.data.data.answer)

                            this.$message({
                                type: 'success',
                                message: '回复成功'
                            });
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '取消回复'
                    });
                });

            }
        }
    }
</script>

<style scoped>

</style>