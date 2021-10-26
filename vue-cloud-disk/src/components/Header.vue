<template>
	<el-card class="box-card">
		<a href="javascript:void(0)" class="nav first">阿洋网盘</a>
		<a href="javascript:void(0)" class="nav"></a>
		<a href="javascript:void(0)" class="nav"></a>
		<span style="float: right;margin-top: 5px;" class="name">{{name}}</span>
		<el-dropdown class="avatar">
			<span class="el-dropdown-link">
				<!-- <img :src="userImg" alt="" slot="reference"> -->
				<div class="block" style="float: right;margin-bottom: 10px;">
					<el-avatar :size="35" :src="userImg"></el-avatar>
				</div>
			</span>

			<el-dropdown-menu slot="dropdown">
				<el-dropdown-item @click.native="cpshow = true">修改密码</el-dropdown-item>
				<el-dropdown-item divided><a @click="loginout">
						<font style="color: #000000;">注销</font>
					</a></el-dropdown-item>
			</el-dropdown-menu>
		</el-dropdown>

		<el-dialog title="修改密码" :visible.sync="cpshow" width="20%">
			<el-input v-model="input" placeholder="请输入修改的密码"></el-input>
			<span slot="footer" class="dialog-footer">
				<!-- <el-button @click="seeDetail = false">取 消</el-button> -->
				<el-button type="primary" @click.native="cp()">确 定</el-button>
			</span>
		</el-dialog>
		
	</el-card>
</template>

<script>
	export default {
		name: "Header",
		data() {
			return {
				name: '未登录',
				userImg: require('../assets/avatar.svg'),
				cpshow: false,
				input:''
			}
		},
		components: {

		},
		created() {
			if (localStorage.getItem('userimg') != null) {
				this.userImg = 'http://47.99.39.91:8080/' + localStorage.getItem('userimg')
			}
			this.name = localStorage.getItem('name')
		},
		methods: {
			loginout() {
				localStorage.setItem('uid', '')
				this.$router.push({
					path: '/'
				})
			},cp(){
				var that = this
				this.$http.post(this.$host + 'correctPwd', this.$qs.stringify({
					pwd: this.input,
					username: localStorage.getItem('name')
				})).then(res => {
					this.cpshow = false
					if(res.data.code == 1){
						that.$message({
							message: '修改成功！！！',
							type: 'success'
						});
					}else{
						that.$message({
							message: '修改失败！！！',
							type: 'error'
						});
					}
				});
			}
		}
	}
</script>

<style scoped>
	.box-card {
		height: 60px;
		width: 100%;
		box-shadow: 0 0 !important;
		border: 0 !important;

	}

	.nav {
		margin-top: -20px;
		margin-left: 40px;
		font-family: PingFangSC-Semibold;
		font-size: 16px;
		color: black;
		text-decoration: none;

	}

	a:hover {
		color: #0098ea;
	}

	.first {
		color: #0098ea;
	}

	.avatar {
		float: right;
		width: 30px;
		height: 30px;
	}

	img {
		width: 30px;
		margin-top: -5px;
	}

	.name {
		margin-left: 10px;
		font-size: 14px;
		font-weight: 500;
		color: #424e67;
	}
</style>
