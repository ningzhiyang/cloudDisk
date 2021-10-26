<template>
	<div class="main">


		<el-card class="box-card" v-loading="loading" element-loading-text="正在登录，请稍后">
			<div slot="header" class="clearfix">
				<span>登录</span>
				<el-button style="float: right; padding: 3px 0" type="text" @click="register">还没有账号？去注册
				</el-button>
			</div>
			<el-row type="flex" justify="center">
				<el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
					<el-form-item prop="name">
						<el-input v-model="ruleForm.name " placeholder="账号"></el-input>
					</el-form-item>
					<el-form-item prop="pass">
						<el-input v-model="ruleForm.pass" show-password placeholder="密码"></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="login('ruleForm')" class="login">登录</el-button>
					</el-form-item>

				</el-form>

			</el-row>
		</el-card>



	</div>
</template>

<script>

	export default {
		name: "Login",

		data() {
			var name = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请输入账号'));
				} else {
					callback();
				}
			};
			var pass = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请输入密码'));
				} else {
					callback();
				}
			};
			return {
				loading: false,
				ruleForm: {
					name: '',
					pass: ''
				},
				rules: {
					name: [{
							validator: name,
							trigger: 'blur'
						},

					],
					pass: [{
						validator: pass,
						trigger: 'blur'
					}, ],

				}

			}
		},
		methods: {
			login(ruleForm) {
				this.$refs[ruleForm].validate((valid) => {
					if (valid) {
						var that = this;
						this.$http.post(this.$host+'to_login', this.$qs.stringify({
								username: this.ruleForm.name,
								password: this.ruleForm.pass,
							}))
							.then(function(response) {
								
								
								if (response.data.code == 1) {
									that.$message({
										message: '登录成功！！！',
										type: 'success'
									});
									//使用location 记录用户信息

									localStorage.setItem('userimg',response.data.userimg)
									localStorage.setItem('uid', that.$md5(that.ruleForm.name))
									localStorage.setItem('name', that.ruleForm.name)
									localStorage.setItem('used',response.data.used)

									that.$router.push("/home")
								} else if(response.data.code == -1){
									that.$message({
										message: '当前用户被封禁！！！',
										type: 'error'
									});
								}else {
									that.$message({
										message: '用户名或密码错误！！！',
										type: 'error'
									});
								}
							})
							.catch(function(error) {
								console.log(error);
							});
					} else {
						console.log('error submit!!');
						return false;
					}
				});


			},
			resetForm(ruleForm) {
				this.$refs[ruleForm].resetFields();
			},
			register() {
				this.$router.push({
					path: 'Register'
				})
			},
			home() {
				this.$router.push({
					path: '/Home'
				})
			}
		}
	}
</script>

<style scoped>
	a {
		color: white;
		text-decoration: none;
	}

	a:hover {
		color: gray;
	}

	.main {
		background: url("../assets/bg.jpg") no-repeat;
		width: 100%;
		height: 100%;
		background-size: 100%100%;
	}

	.box-card {
		max-width: 400px;
		min-width: 320px;
		position: absolute;
		left: 50%;
		top: 50%;
		transform: translate(-50%, -50%);

	}

	.login {
		width: 100%;
	}
</style>
