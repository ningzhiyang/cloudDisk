<template>
	<div class="main">

		<!-- <el-tag style="border: none;margin: 100px 45%;font-size: 30px;">阿洋网盘</el-tag> -->
		<el-card class="box-cardx" v-loading="loading" element-loading-text="正在验证，请稍后">
			<div slot="header" class="clearfix">
				<span>文件提取</span>
			</div>
			<el-row type="flex" justify="center">
				<el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="demo-ruleForm">
					<el-form-item prop="name">
						<el-input v-model="ruleForm.name " placeholder="输入提取码"></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="checkUc('ruleForm')" class="login">提取文件</el-button>
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
					callback(new Error('请输入提取码'));
				} else {
					callback();
				}
			};
			return {
				loading: false,
				ruleForm: {
					name: ''
				},
				rules: {
					name: [{
							validator: name,
							trigger: 'blur'
						},

					]

				},
				href: this.$route.params.checkcode

			}
		},
		created() {
			this.init()
		},
		methods: {
			init() {
				console.log(this.href)
				var that = this;
				this.$http.post(this.$hostFile + 'checkUrl', this.$qs.stringify({
					sign: this.$share + this.href,
					code: this.ruleForm.name
				})).then(res => {
					if (res.data.code == 0) {
						this.$alert('当前文件已取消分享', '提示', {
							confirmButtonText: '确定',
							callback: action => {
								this.$router.push({
									path: '/'
								})
							}
						});

					}
				});
			},
			checkUc() {
				this.loading=true
				this.$refs.ruleForm.validate((valid) => {
					
					if (valid) {
						this.$http.post(this.$hostFile + 'checkCode', this.$qs.stringify({
							sign: this.$share + this.href,
							code: this.ruleForm.name
						})).then(res => {
							this.loading=false
							if (res.data.code == 1) {
								localStorage.setItem("fid",res.data.fid)
								localStorage.setItem("sid",res.data.sid)
								this.$router.push({
									path: '/shareFile'
								})
							} else {
								this.$alert('提取码错误', '提示', {
									confirmButtonText: '确定'
								});
							}
						});
					}
				});
			}
		}
	}
</script>

<style>
	a {
		color: white;
		text-decoration: none;
	}

	a:hover {
		color: gray;
	}

	.main {
		background: url("../assets/checkcode.jpg") no-repeat;
		width: 100%;
		height: 100%;
		background-size: 100%100%;
	}

	.box-cardx {
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
