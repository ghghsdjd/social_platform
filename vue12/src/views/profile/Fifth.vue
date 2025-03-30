<template>
    <div>
        <h3>{{sex+'被提到的'}}</h3>
        <div v-for="item in list" >

            <el-timeline>
                <MentionItem  @delete="del" :item="item" :is-me="IsMe" :sex="sex"/>
            </el-timeline>
        </div>
        <div v-show="list.length===0" style="text-align: center;margin-top: 10%;margin-bottom: 10%">空空如也</div>

    </div>
</template>

<script>
    import MentionItem from "./mentionChil/MentionItem";
    export default {
        name: "Fifth",
        components: {MentionItem},
        props:{
            user:{},
            IsMe:false,
        },
        data(){
            return{
                list:[]
            }
        },
        methods:{
            getData(){
                this.$http.get("/profile/findMentionsByUid/"+this.user.id).then(res=>{
                    console.log(res)
                    if(res.data.status===200){
                        this.list=res.data.data.list
                    }
                })
            },
            del(id){
                console.log(id)
                for(let i=0;i<this.list.length;i++){
                    if(this.list[i].id===id){
                        this.list.splice(i,1)
                        break
                    }
                }
            }
        },
        computed:{
            sex(){
                if(this.IsMe){
                    return '我'
                }
                if(this.user.sex===0)
                    return this.user.username
                else if(this.user.sex===1)
                    return '他'
                return '她'
            },

        },
        mounted() {
           this.getData()
        },
        watch:{
            user:{
                handler(){
                    this.getData()
                },
                deep:true
            }
        },
    }
</script>

<style scoped>

</style>