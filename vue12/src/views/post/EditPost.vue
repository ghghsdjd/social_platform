<template>
    <div class="content">

        <el-page-header @back="goBack" content="发布文章"/>
        <el-tooltip class="item" effect="dark" content="@用户名 可以提醒谁看" placement="top">
        <p style="text-align: center">创作指南<i class="el-icon-question"/></p>
        </el-tooltip>
        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"  class="demo-ruleForm">
            <el-form-item label="标题" prop="title">
                <el-input v-model="ruleForm.title"></el-input>
            </el-form-item>
            <el-form-item label="摘要" prop="descr">
                <el-input type="textarea" v-model="ruleForm.descr"></el-input>
            </el-form-item>
            <el-form-item label="分类" prop="type_id">
                <el-select v-model="ruleForm.type_id" default-first-option placeholder="文章类型">
                    <el-option v-for="item in typeList" :label="item.name" :value="item.id"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="内容"  prop="content">
                <mavon-editor  @imgAdd="ImgAdd"
                               @imgDel="ImgDel"
                               ref="md"
                               type="textarea"  v-model="ruleForm.content">
                </mavon-editor>
            </el-form-item>

            <el-form-item>
                <el-button type="primary"  @click="submitForm('ruleForm')">发布</el-button>
            </el-form-item>
        </el-form>
    </div>
</template>

<script>

    export default {
        name: "EditPost",

        data() {
            return {
                typeList:[],
                img_file:[],
                ruleForm: {
                    id:'',
                    title: '',
                    descr:'',
                    content:'',
                    type_id:'',
                },
                rules: {
                    title: [
                        { required: true, message: '请输入标题', trigger: 'blur' },
                        { min: 2, max: 25, message: '长度在 2 到 25 个字符', trigger: 'blur' }
                    ],
                    descr: [
                        { required: true, message: '请输入摘要', trigger: 'blur' }
                    ],
                    content: [
                        { required: true, message: '请输入内容', trigger: 'blur' },

                    ],
                    type_id:[
                        { required: true, message: '请选择类型', trigger: 'change' }
                    ]
                }
            };
        },
        methods: {
            goBack() {
                this.$router.back()
            },
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const _this = this

                        _this.$http.post("/post/add", this.ruleForm, {}).then(res => {
                            if(res.data.status==200){
                                _this.$bus.$emit('updateList')
                                _this.$message.success(res.data.message)
                                _this.$router.push("/detail/"+res.data.data.id)
                            }
                            else {
                                _this.$message.error(res.data.message)
                            }
                        })
                    } else {
                        return false;
                    }
                })
            },
            ImgAdd(pos ,$file){
                let formdata = new FormData();
                formdata.append('file', $file);
                this.img_file[pos] = $file;
                this.$http({
                    url: '/qiniu/upload',
                    method: 'post',
                    data: formdata,
                    headers: { 'Content-Type': 'multipart/form-data' },
                }).then(res => {

                    if(res.data.status===200)
                     this.$refs.md.$img2Url(pos,res.data.data.key)
                })
            },
            ImgDel(pos){
                let key=pos[0]
                delete this.img_file[pos];
                key=key.substring(36)
                this.$http.get("/qiniu/delete/"+key)
            }
        },
        created() {
            this.$http.get("/type/postTypeList").then(res=>{
                this.typeList=res.data.data.typeList
            })
        },
        beforeDestroy() {
            // for(let i=0;i<this.img_file.length;i++){
            //     console.log(this.img_file[i])
            //     let key=i[0]
            //     console.log(key)
            // }
        }
    }
</script>

<style scoped>

    .el-form{
        margin-top: 20px;
        margin-right: 50px;
    }


</style>