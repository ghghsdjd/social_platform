<template>
    <div>
        <el-card class="box-card" >
            <el-link @click="vistUser(item.username)">
                <el-avatar :src="item.avatar" />
                <span>{{item.username}}</span>
            </el-link>
        </el-card>
    </div>
</template>

<script>
    export default {
        name: "UserList",
        props:{
            item:{}
        },
        methods:{
            vistUser(username){
                this.$router.push("/profile/"+username)
            }

        }
    }
</script>

<style scoped>
    .box-card{
        width: 20%;
        display: block;
        margin: auto;
    }
</style>