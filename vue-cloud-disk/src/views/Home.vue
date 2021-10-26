<template>
	<el-container>
		<el-header>
			<Header></Header>
		</el-header>
		<el-container>
			<el-aside>
				<!-- <Left></Left> -->
				<el-container>
					<el-header>
						<el-menu default-active="1" class="el-menu-vertical-demo">
							<el-menu-item index="1" @click="handleSelect(1)">
								<i class="el-icon-tickets"></i>
								<span slot="title">全部文件</span>
							</el-menu-item>
							<el-menu-item index="1-2" @click="handleSelect(12)">
								<span slot="title" class="item">图片</span>
							</el-menu-item>
							<el-menu-item index="1-3" @click="handleSelect(13)">
								<!-- <i class="el-icon-minus"></i> -->
								<span slot="title" class="item">文档</span>
							</el-menu-item>
							<el-menu-item index="1-4" @click="handleSelect(14)">
								<!-- <i class="el-icon-minus"></i> -->
								<span slot="title" class="item">视频</span>
							</el-menu-item>
							<el-menu-item index="1-5" @click="handleSelect(15)">
								<!-- <i class="el-icon-minus"></i> -->
								<span slot="title" class="item">音乐</span>
							</el-menu-item>
							<el-menu-item index="2" @click="handleSelect(2)">
								<i class="el-icon-share"></i>
								<span slot="title">我的分享</span>
							</el-menu-item>
							<el-menu-item index="3" @click="handleSelect(3) ">
								<i class="el-icon-delete"></i>
								<span slot="title">回收站</span>
							</el-menu-item>
						</el-menu>
					</el-header>
					<el-main></el-main>
					<el-footer>
						<el-progress :text-inside="false" :stroke-width="10" :percentage="use" :show-text="false">
						</el-progress>
						<el-breadcrumb separator="/" style="margin-top: 5px;font-size: 12px;">
							<el-breadcrumb-item>{{usesize}}</el-breadcrumb-item>
							<el-breadcrumb-item>500M</el-breadcrumb-item>
						</el-breadcrumb>
					</el-footer>
				</el-container>
			</el-aside>
			<el-main>
				<Right v-bind:ykey="ykey" v-if="someShow"></Right>
			</el-main>
		</el-container>
	</el-container>
</template>

<script>
	import Header from '../components/Header'
	import Right from '../components/Right'
	
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
			Right
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
			this.handleSelect(1);
			var used = localStorage.getItem("used")
			var MB=1024*1024*1024;
			var persent=parseInt(used/MB)
			  if(persent==0){
			    this.use=1
			  }else{
			    this.use=persent
			  }
				this.usesize= Math.ceil(used/1024/1024)+"M"
			
		},
		methods: {
			handleSelect(key) {
				console.log("key------->"+key)
				this.ykey=key
				this.refesh()
			},
			refesh:function(){
				this.someShow=false;
				var _this=this;
				this.$nextTick(function(){
					_this.someShow = true;
			})}
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
