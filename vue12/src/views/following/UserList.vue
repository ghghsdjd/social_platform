<template>
    <div>
        <div v-for="item in list">
            <UserItem :item="item" />
        </div>
        <div v-show="list.length===0" style="text-align: center;margin-top: 10%;margin-bottom: 10%;">空空如也</div>
    </div>
</template>

<script>
    import UserItem from "../profile/UserItem";
    export default {
        name: "UserList",
        components:{UserItem},
        data(){
          return{
              list:[]
          }
        },
        methods:{
            getData(){
                this.$http.get("/following/findFowings").then(res=>{
                    if(res.data.status===200) {
                        this.list = res.data.data.followers
                    }
                })
            }
        },
        mounted() {
            this.getData()
        }
    }
</script>

<style scoped>

</style>