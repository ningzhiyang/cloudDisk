<template>
	<div>
		<div
			style="height: 400px;width: 400px;margin: 30px auto; background-color: #dedede;border-radius: 20px;text-align: center;">
			<img :src="require('../../static/img/'+fileImg)" alt="" slot="reference"
				style="width: 150px;height: 150px;margin: 50px 125px;">
			<div>文件大小：<font style="font-size: 18px;">{{fileCount}}</font>
			</div>
			<br>
			<div>文件名称：<font style="font-size: 18px;">{{fileName}}</font>
			</div>
			<br>
			<el-button type="primary" round @click="to_download()"><i class="el-icon-download"></i>下载此文件</el-button>
		</div>
	</div>

</template>
<script>
	export default {
		name: "ShareFileMain",

		data() {
			return {
				fileImg:'',
				fileCount:'',
				fileName:''
			}
		},
		created() {
			this.init()
		},
		methods: {
			to_download(){
				console.log("download")
				var fid = localStorage.getItem("fid")
				var sid = localStorage.getItem("sid")
				var username = localStorage.getItem("name")
				window.location.href = this.$hostFile + 'downShareFile?username=' +username + '&fid=' +
					fid+ '&sid=' +sid
			},
				init(){
					var fid = localStorage.getItem("fid")
					var sid = localStorage.getItem("sid")
					this.$http.post(this.$hostFile + 'getFile', this.$qs.stringify({
						fid: fid,
					})).then(res => {
						if(res.data.code == 1){
							this.fileName = res.data.vueFile.fname
							this.fileImg = res.data.fimg
							this.fileId = res.data.vueFile.fid
							var sSize = parseInt(res.data.vueFile.fsize.split("KB")[0]);
							// console.log(sSize)
							if (sSize > 1023 && sSize < 1048576) {
								var size = (sSize / 1024).toFixed(2) + 'MB'
							} else if (sSize > 1048577) {
								var size = (sSize / 1024 / 1024).toFixed(2) + 'GB'
							} else {
								var size = res.data.vueFile.fsize
							}
							this.fileCount = size
						}else{
							this.$alert('当前文件已删除', '提示', {
								confirmButtonText: '确定'
							});
						}
					});
					}
					,
		}
	}
</script>
<style scoped>
	.bt {
		max-width: 100%;
		background-color: white;
		height: 40px;
		font: 12px/1.5 "Microsoft YaHei", arial, SimSun, "宋体";
		line-height: 30px;
	}

	.nav {
		max-width: 100%;
		background-color: white;
		height: 20px;
		/*font: 12px/1.5 "Microsoft YaHei", arial, SimSun, "宋体";*/
		font-size: 8px;
		line-height: 20px;
	}

	.el-table-column {
		max-height: 48px;
		line-height: 48px;
	}

	.el-table {
		max-width: 100%;
		font: 12px/1.5 "Microsoft YaHei", arial, SimSun, "宋体";
	}

	#search {
		width: 315px;
		border-radius: 33px;
		background-color: #f7f7f7;
		float: right;
		text-align: center;
		height: 32px;
		line-height: 32px;
		border: none;
	}

	.search {
		border: none;
		background-color: #f7f7f7;
		height: 30px;
		width: 248px;
		outline: none;
	}

	img {
		width: 30px;
		height: 30px;
	}

	a {
		color: #424e67;
		text-decoration: none;
	}

	a:hover {
		color: #09AAFF;
	}

	.el-icon-delete {
		color: #F56C6C;
	}

	.btn {
		background-color: transparent;
		border: 0;
		outline: none;
	}

	/*  .btn:hover{
	  background-color: #23fff1;
  } */
</style>
