<template>
    <div :class="isFixed== true?'isFixed':''" :style="isFixed==true?'margin-top: 50px':''">
        <slot/>
    </div>
</template>

<script>
    export default {
        name: "PageHeader",
        data(){
            return{
                isFixed:false
            }
        },
        methods:{
            handleScroll () {
                this.$nextTick(() => {
                    let scrollTop = document.documentElement.scrollTop || document.body.scrollTop;


                    if (scrollTop > 30) {
                        this.isFixed = true;
                    } else {
                        this.isFixed = false;
                    }
                })
            }
        },
        mounted(){
            window.addEventListener('scroll', this.handleScroll);
        },
        destroyed(){
            window.removeEventListener('scroll', this.handleScroll);
        }
    }
</script>

<style scoped>

</style>