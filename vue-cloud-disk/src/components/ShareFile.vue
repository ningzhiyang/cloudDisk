<template>
	<el-container>
		<el-header>
			<Header></Header>
		</el-header>
		<el-container>
			<el-aside>
				<!-- <Left></Left> -->
			</el-aside>
			<el-main>
				<ShareFileMain></ShareFileMain>
			</el-main>
			<el-aside>
				<!-- <Left></Left> -->
			</el-aside>
		</el-container>
	</el-container>
</template>

<script>
	import Header from '../components/Header'
	import ShareFileMain from '../components/ShareFileMain'
	export default {
		name: "Home",

		data() {
			return {
				use: "50",
				usesize: '100',
				timer: '',
				ykey: 1,
				someShow:''
			}
		},
		components: {
			Header,
			ShareFileMain
		},
		created() {
			if (localStorage.getItem('uid') == '' || localStorage.getItem('uid') == undefined) {
				this.$confirm('您还未登陆/或者登陆已过期', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.$router.push({
						path: '/'
					})
				}).catch(() => {
					this.$router.push({
						path: '/'
					})
				});
			}
		}
	}
</script>

<style scoped>
	.el-header {
		width: 100%;
		box-shadow: 0 2px 6px 0 rgba(0, 0, 0, .05);

	}

	.el-container {
		height: 100%;

	}

	.el-aside {
		background-color: #f7f7f7;
		max-width: 194px;

	}

	.el-main {
		height: calc(100% - 62px);
		max-width: calc(100% - 194px);
	}

	.el-menu {
		background-color: #f7f7f7;
		border: 0;
		/*text-align: center;*/

	}

	.item {
		color: #424e67;
		font-size: 14px;
		padding-left: 38px;
	}

	.el-progress-bar__outer {}
</style>
