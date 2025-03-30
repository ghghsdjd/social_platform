<template>
	<div>
		<el-card>
			<el-table :data="reportList" border stripe>
				<el-table-column type="index" label="#"></el-table-column>
				<el-table-column prop="time" label="时间"></el-table-column>
				<el-table-column prop="username" label="举报人">
					<template slot-scope="scope">
						<div v-html="scope.row.username"></div>
					</template>
				</el-table-column>
				<el-table-column prop="address" label="举报内容">
					<template slot-scope="scope">
						<div v-html="scope.row.address"></div>
					</template>
				</el-table-column>
				<el-table-column prop="content" label="举报理由"></el-table-column>
				<el-table-column prop="type" label="举报类型">
					<template slot-scope="scope">
						<div v-if="scope.row.type==0">用户</div>
						<div v-else>文章</div>
					</template>
				</el-table-column>
				<el-table-column label="操作">
					<template slot-scope="scope">
						<el-tooltip class="item" effect="dark" content="冻结举报内容" placement="right">
						<el-button
								@click="deleteSolve(scope.row.id)"
								type="danger"
								icon="el-icon-delete"
								circle>
						</el-button>
						</el-tooltip>
						<el-tooltip class="item" effect="dark" content="情况不符合" placement="bottom">
						<el-button
								type="success"
								icon="el-icon-check"
								circle
								style="margin-left:50%"
								@click="solve(scope.row.id)"
						>
						</el-button>
						</el-tooltip>
					</template>
				</el-table-column>
			</el-table>
		</el-card>
	</div>
</template>

<script>
    export default {
        name: "Report",
        data() {
            return {
				reportList:[{'time':1}],
            };
        },
        methods: {
            getDataList(){
                this.$http.get("/admin/findAllReport").then(res=>{

					this.reportList=res.data.data.reportList
				})
			},
            deleteSolve(id){
                this.$http.put("/admin/deleteSolve/"+id).then(res=>{
                    if(res.data.status===200){
                        this.getDataList()
						this.$message.success("操作成功")
					}
					else{
                        this.$message.error("error")
					}
				})
			},
			solve(id){
                this.$http.put("/admin/solve/"+id).then(res=>{
                    if(res.data.status===200){
                        this.getDataList()
                        this.$message.success("操作成功")
                    }
                    else{
                        this.$message.error("error")
                    }
                })
			}
        },
		created(){
            this.getDataList()
		}
    }
</script>

<style scoped>

</style>