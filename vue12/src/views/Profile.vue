<template>
   <div>
     <ProfileHeader :is-me="IsMe" :user="user" :isfollow="isfollow"/>
     <el-divider/>
     <profile-content :user="user" :is-me="IsMe"/>
   </div>
</template>

<script>
    import ProfileContent from "./profile/ProfileContent";
    import ProfileHeader from "./profile/ProfileHeader";
    export default {
       name: "Profile",
       components: {ProfileHeader,ProfileContent},
       data () {
           return {
              IsMe:true,
              user:{
                 profile:{}
              },
              isfollow:false,

          }
       },
       methods:{
          getData(){
             const _this=this
             if(!this.IsMe)
             this.$http.get("/profile/findUserProfile/"+this.$route.params.username).then(res=>{
                if(res.data.status===200){
                   _this.user=res.data.data.user
                   _this.isfollow=res.data.data.isfollower
                   _this.sendMsg('updateAside',this.$route.params.username)
                }
                else{
                    this.$message.error("用户不存在!")
                }
             })
             else{
                 this.user=this.$store.getters.getUser

             }

          },
          sendMsg(name,param){
             this.$bus.$emit(name,param)
          },

       },
       beforeRouteEnter(to,from,next){
          next(vm => {
             if(to.params.username===vm.$store.getters.getUser.username){
                vm.IsMe=true
                vm.sendMsg('NoupdateAside',null)
             }
             else{
                vm.IsMe=false
             }

             vm.getData()

          })
       },
       beforeRouteUpdate(to,from,next){
          next()
          if(to.params.username===this.$store.getters.getUser.username){
              this.sendMsg('NoupdateAside',null)
              this.IsMe=true
          }
          else this.IsMe=false
          this.sendMsg("backTop",null)

          this.getData()

       },
       beforeRouteLeave(to,from,next){
           this.sendMsg('NoupdateAside',null)
           next()
       },
      created() {
           this.getData()
      },
      mounted() {

      }

    }
</script>

<style lang="less" scoped>


</style>