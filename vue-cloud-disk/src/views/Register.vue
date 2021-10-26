<template>
	<div class="main">

		<el-card class="box-card" v-loading="loading" :element-loading-text="loading_text">
			<div slot="header" class="clearfix">
				<span>注册</span>
				<el-button style="float: right; padding: 3px 0" type="text" @click="login">已经有账号？去登陆
				</el-button>
			</div>
			<el-row type="flex" justify="center">
				<el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">

					<el-form-item prop="name">
						<el-input v-model="ruleForm.name" placeholder="账号"></el-input>
					</el-form-item>
					<el-form-item prop="pass">
						<el-input v-model="ruleForm.pass" show-password placeholder="密码"></el-input>
					</el-form-item>
					<el-form-item prop="repass">
						<el-input v-model="ruleForm.repass" show-password placeholder="确认密码"></el-input>
					</el-form-item>
					<el-form-item prop="phone">
						<el-col :span="16">
							<el-input v-model="ruleForm.phone" style="width: 90%;" placeholder="手机号码"></el-input>
						</el-col>
						<el-col :span="8">
							<el-button type="primary" @click="getcode('ruleForm')" id="yzm" style="width: 100%;">获取验证码
							</el-button>
						</el-col>
					</el-form-item>
					<el-form-item prop="yzm" id="yzm">
						<el-input v-model="ruleForm.yzm" placeholder="手机验证码"></el-input>
					</el-form-item>
					<el-form-item>
						<el-col :span="8">
							<el-upload class="upload-demo" :before-upload="beforeUpload" multiple>
								<!-- <el-button slot="trigger" size="small" type="primary" @click="delFile">选取文件</el-button> -->

								<el-button size="small" type="primary" @click="uploadImg">上传头像</el-button>
							</el-upload>
						</el-col>
						<el-col :span="16">
							<!-- <el-button
							  style="margin-left: 10px;"
							  size="small"
							  type="success"
							  @click="submitUpload"
							>上传到服务器</el-button> -->
							<div class="el-upload__tip">
								<font style="size: 8px;">上传的文件限制大小为500k</font>
							</div>
						</el-col>

					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="register('ruleForm')" class="register">注册</el-button>
					</el-form-item>
				</el-form>


			</el-row>
		</el-card>



	</div>
</template>

<script>
	export default {
		name: "Register",
		data() {

			var name = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请输入账号'));
				} else if (value.length < 3) {
					callback(new Error('至少位三个字符'));
				} else {
					callback();
				}
			};
			var pass = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请输入密码'));
				} else if (value.length < 6) {
					callback(new Error('至少位六个字符'));
				} else {
					callback();
				}
			};
			var repass = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请输入密码'));
				} else if (value !== this.ruleForm.pass) {
					callback(new Error('两次输入密码不一致!'));
				} else {
					callback();
				}
			};
			var phone = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请输入手机号'));
				} else if (value.length !== 11) {
					callback(new Error('请输入正确的手机号'));
				} else {
					callback();
				}
			};

			var yzm = (rule, value, callback) => {
				if (value === '') {
					callback(new Error('请输入验证码'));
				} else if (value.length !== 6) {
					callback(new Error('请输入6位数字验证码'));
				} else {
					callback();
				}
			};
			return {
				checkcode: '',
				imgPath: '',
				loading: false,
				ruleForm: {
					name: '',
					pass: '',
					repass: '',
					yzm: '',
					phone: '',
					loading_text: ''

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
					repass: [{
						validator: repass,
						trigger: 'blur'
					}, ],
					phone: [{
						validator: phone,
						trigger: 'blur'
					}],
					// yzm:[ { validator: yzm, trigger: 'blur' }],
				}
			}
		},
		methods: {
			login() {
				this.$router.push({
					path: '/'
				})
			},
			beforeUpload(file) {
				let fd = new FormData();
				fd.append('image', file); //传文件
				fd.append('sign', this.$sign); //传其他参数
				fd.append('username', localStorage.getItem('name')); //传其他参数
				fd.append('path', localStorage.getItem('path')); //传其他参数
				var that = this
				console.log(file)

				this.$http.post(this.$host + 'upload', fd).then(res => {
					if (res.data.code == 1) {
						that.imgPath = res.data.name
						// console.log("img-------->"+that.imgPath)
						that.$message({
							showClose: true,
							message: '上传成功',
							type: 'success'
						});
					}
				})
			},
			register(ruleForm) {

				this.$refs[ruleForm].validate((valid) => {
					if (valid) {
						if(this.ruleForm.yzm != this.checkcode){
							this.$alert('验证码错误', '提示', {
								confirmButtonText: '确定',
							})
							return false;
						}
						this.loading = true
						this.loading_text = '正在注册，请稍后'
						this.$http.post(this.$host + "register", this.$qs.stringify({
							"username": this.ruleForm.name,
							"password": this.ruleForm.pass,
							"phone": this.ruleForm.phone,
							// "code": this.ruleForm.yzm,
							// "truecode": this.checkcode,
							"imgpath": this.imgPath
						})).then(res => {
							if (res.data.code == 0) {
								this.$alert('注册成功', '提示', {
									confirmButtonText: '确定',
									callback: action => {
										this.$router.push({
											path: '/'
										})
									}

								})

							}  else if (res.data.code == 1) {
								this.loading = false
								this.$refs[ruleForm].resetFields();
								this.$alert('用户名已存在', '提示', {
									confirmButtonText: '确定',
								})
							}
						})
					}
				})
			},
			home() {
				this.$router.push({
					path: '/'
				})
			},
			resetForm(ruleForm) {
				this.$refs[ruleForm].resetFields();
			},
			getcode(ruleForm) {
				this.$refs[ruleForm].validate((valid) => {
					if (valid) {
						this.loading = true
						this.loading_text = '正在发送验证码'
						if (this.ruleForm.phone.length !== 11) {
							this.$alert('请输入正确的手机格式', '提示', {
								confirmButtonText: '确定',
							})
						} else {
							var that = this;
							this.$http.post(this.$host + 'yzm', this.$qs.stringify({
								"phone": this.ruleForm.phone
							})).then(res => {
								if (res.data.code == 0) {
									that.checkcode = res.data.checkcode
									// console.log("yzm ------------->"+that.checkcode)
									this.loading = false
									this.$alert('发送成功', '提示', {
										confirmButtonText: '确定',
									})
								}
							})
						}
					} else {
						return
					}
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

	.register {
		width: 100%;
	}
</style>
