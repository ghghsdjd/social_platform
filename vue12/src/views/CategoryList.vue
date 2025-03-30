<template>
    <div>
        <List :url="'post/findType/'+$route.params.name" :header1="'首页'" :header2="'分类:'+$route.params.name"></List>
    </div>
</template>

<script>
    import List from "./post/List";
    export default {
        name: "CategoryList",
        components:{List},
        beforeRouteUpdate (to, from, next) {
            next()
            this.$children[0].asycUrl='post/findType/'+this.$route.params.name
            this.$children[0].getList()
        }
    }
</script>

<style scoped>

</style>