<template>
    <el-timeline-item :timestamp="item.time" placement="top">
        <el-card class="box-card">
                    <el-link @click="vistUser(item.from_name)">
                        <el-avatar :src="item.from_avatar" />
                        <span>{{item.from_name}}</span>
                    </el-link>
            在<el-link type="primary" @click="detail(item.id)">{{item.title}}</el-link>提到了{{sex}}

            <el-tooltip  class="item" effect="dark" content="删除" placement="top">
                <el-button  style="margin-left: 20px" type="danger"  v-show="IsMe" @click="deleteMention(item.id)" size="mini" icon="el-icon-delete" circle></el-button>
            </el-tooltip>
        </el-card>
    </el-timeline-item>
</template>

<script>
    export default {
        name: "MentionItem",
        props:{
            item:{},
            sex:'',
            IsMe:false
        }
        ,
        methods:{
            vistUser(username){

                this.$router.push("/profile/"+username)
            },
            detail(id){
                this.$router.push("/mention/"+id)
            },
            deleteMention(id){
                this.$http.put("/profile/deleteMention",this.item).then(res=>{
                    if(res.data.status==200){
                        this.$message.success("删除成功")
                        this.$emit('delete',id)
                    }
                    else{
                        this.$message.error("删除失败")
                    }

                })
            }
        }

    }
</script>

<style scoped>

</style>