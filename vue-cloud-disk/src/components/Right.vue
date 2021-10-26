<template>
	<div>
		<div class="bt">

			<el-button type="text" size="medium" icon="el-icon-arrow-left" @click="back" v-if="path=='/'?false:true">
				返回上一级</el-button>
			<el-button type="primary" size="medium" icon="el-icon-upload2" @click="dialogVisible = true" v-if="fshow">
				上传文件
			</el-button>
			<el-button size="medium" @click="addfolder = true" v-if="fshow">新建文件夹</el-button>
			<el-button size="medium" @click="addtxt = true" v-if="fshow">新建文件</el-button>

			<el-dialog title="输入文件夹名称" :visible.sync="addfolder" width="20%">
				<el-input v-model="input" placeholder="请输入内容"></el-input>
				<span slot="footer" class="dialog-footer">
					<el-button @click="addfolder = false">取 消</el-button>
					<el-button type="primary" @click="newfolder">确 定</el-button>
				</span>
			</el-dialog>
			
			<el-dialog title="输入文件名称" :visible.sync="addtxt" width="20%">
				<el-input v-model="input" placeholder="请输入内容"></el-input>
				<span slot="footer" class="dialog-footer">
					<el-button @click="addtxt = false">取 消</el-button>
					<el-button type="primary" @click="newtxt">确 定</el-button>
				</span>
			</el-dialog>

			<el-dialog title="查看分享" :visible.sync="seeDetail1" width="20%">
				分享链接：<span><font style="font-size: 18px;">{{seehref}}</font></span>
				<br /><br />
				提取码：<span><font style="font-size: 18px;">{{seecode}}</font></span>
				<span slot="footer" class="dialog-footer">
					<!-- <el-button @click="seeDetail = false">取 消</el-button> -->
					<el-button type="primary" @click="seeDetail1 = false">确 定</el-button>
				</span>
			</el-dialog>

			<el-dialog title="提取码" :visible.sync="addshare" width="20%" :model="tableData">
				<el-radio v-model="radio" label="1">7天</el-radio>
				<el-radio v-model="radio" label="2">30天</el-radio>
				<el-radio v-model="radio" label="3">永久</el-radio>
				<br /><br />
				<el-input v-model="input" placeholder="请输入提取码"></el-input>
				<span slot="footer" class="dialog-footer">
					<el-button @click="addshare = false">取 消</el-button>
					<el-button type="primary" @click="shareFilea()">确 定</el-button>
				</span>
			</el-dialog>

			<el-dialog title="上传" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
				<el-upload class="upload-demo" drag action="upload" :before-upload="beforeUpload" multiple
					v-loading="loading" element-loading-text="正在上传">
					<i class="el-icon-upload"></i>
					<div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
					<div class="el-upload__tip" slot="tip"></div>
				</el-upload>
				<span slot="footer" class="dialog-footer">
				</span>
			</el-dialog>
			<div id="search">
				<input placeholder="请输入内容" class="search" v-model="keywords" />
				<span class="btn"><i class="el-icon-search"></i></span>
			</div>
		</div>
		<el-table :data="search(keywords)" :height="height">
			<el-table-column prop="name" label="文件名" width="600">
				<template slot-scope="{row,$index}">
					<img :src="require('../../static/img/'+row.img)" alt="" style="cursor: default;display: block;
					height: 26px;
					width: 26px;
					position: absolute;
					left:0px;
					top: 10px;" slot="reference">
					<a href="javascript:void(0)" style="position: absolute;left:40px;top: 12px;"
						@click="next(row.name,row.img)">{{row.name}}</a>
				</template>
			</el-table-column>
			<el-table-column prop="size" label="大小" width="270">
			</el-table-column>
			<el-table-column prop="time" label="修改日期" width="220">
			</el-table-column>
			<el-table-column>
				<template slot-scope="{row,$index}">
					<el-tooltip class="item" effect="dark" content="分享" placement="bottom-start" v-if="fshow">
						<el-button type="text"><i class="el-icon-share" @click="addsharemid(row.name)"></i></el-button>
					</el-tooltip>
					<el-tooltip class="item" effect="dark" content="下载" placement="bottom-start" v-if="fshow">
						<el-button type="text"><i class="el-icon-download" @click="download(row.name)"></i></el-button>
					</el-tooltip>
					<el-tooltip class="item" effect="dark" content="还原" placement="bottom-start" v-if="freshShow">
						<el-button type="text"><i class="el-icon-refresh-left" @click="huanyuan(row.id,row.href,row.name)"></i>
						</el-button>
					</el-tooltip>
					<el-tooltip class="item" effect="dark" content="查看分享" placement="bottom-start" v-if="quitShow">
						<el-button type="text"><i class="el-icon-view" @click="seeDetail(row.href,row.code)"></i></el-button>
					</el-tooltip>
					<el-tooltip class="item" effect="dark" content="取消分享" placement="bottom-start" v-if="quitShow">
						<el-button type="text"><i class="el-icon-circle-close" @click="quitShare(row.id)"></i>
						</el-button>
					</el-tooltip>
					<el-tooltip class="item" effect="dark" content="删除" placement="bottom-start" v-if="delShow">
						<el-button type="text"><i class="el-icon-delete" @click="del(row.id,row.name,$index)"></i></el-button>
					</el-tooltip>
					<!-- <el-button @click="onPreview">预览</el-button> -->
					<el-image-viewer v-if="showViewer" :on-close="closeViewer" :url-list="[url]" />
				</template>
			</el-table-column>
		</el-table>

	</div>

</template>

<script>
	import ElImageViewer from 'element-ui/packages/image/src/image-viewer'
	export default {
		name: 'Right',
		props: {
			ykey: Number
		},
		components: {
			ElImageViewer
		},
		data() {
			return {
				input: '',
				height: window.innerHeight - 62 - 80 - 40,
				tableData: [],
				keywords: '',
				dialogVisible: false,
				loading: false,
				path: '/',
				username: localStorage.getItem('name'),
				addfolder: false,
				addtxt: false,
				addshare: false,
				showViewer: false, // 显示查看器
				url: '',
				shareFileName: '',
				fshow: true,
				freshShow: false,
				quitShow: false,
				delShow:true,
				radio: '1',
				seehref:'',
				seecode:'',
				seeDetail1:false,
			}
		},
		created() {
			this.init();
		},
		methods: {
			//当前用户全部
			init() {
				localStorage.setItem('path', '/')
				var ykey = this.ykey;
			
				if (ykey == 2) {
					this.quitShow = true
					this.fshow = false
					this.delShow = false
				}
				if (ykey == 3) {
					this.freshShow = true
					this.fshow = false
				}
				this.$http.post(this.$hostFile + 'listAll', this.$qs.stringify({
					sign: this.ykey,
					username: localStorage.getItem('name')
				})).then(res => {
					console.log("全部页面时候：" + res.data)
					console.log(res.data)
					if (res.data.dir != null) {
						res.data.dir.forEach(item => {
							if (item.size == '') {
								var size = '-'
							} else {

								if (item.size < 1048576) {
									var size = (item.size / 1024).toFixed(2) + 'KB'
								} else if (item.size > 1048576 && item.size < 1073741824) {
									var size = (item.size / 1024 / 1024).toFixed(2) + 'MB'
								} else if (item.size > 1073741824) {
									var size = (item.size / 1024 / 1024 / 1024).toFixed(2) + 'GB'
								}

							}

							this.tableData.push({
								name: item.name,
								time: item.mtime,
								img: item.img,
								size: size
							})
						})
					}
					if (res.data.file != null) {
						res.data.file.forEach(item => {
							if (item.size == '') {
								var size = '-'
							} else {
								if (ykey == 1) {
									if (item.size < 1048576) {
										var size = (item.size / 1024).toFixed(2) + 'KB'
									} else if (item.size > 1048576 && item.size < 1073741824) {
										var size = (item.size / 1024 / 1024).toFixed(2) + 'MB'
									} else if (item.size > 1073741824) {
										var size = (item.size / 1024 / 1024 / 1024).toFixed(2) + 'GB'
									}
								} else {
									var sSize = parseInt(item.size.split("KB")[0]);
									console.log(typeof sSize)
									if (sSize > 1023 && sSize < 1048576) {
										var size = (sSize / 1024).toFixed(2) + 'MB'
									} else if (sSize > 1048577) {
										var size = (sSize / 1024 / 1024).toFixed(2) + 'GB'
									} else {
										var size = item.size
									}

								}
							}
							if (ykey == 2) {
								this.tableData.push({
									name: item.name,
									time: item.mtime,
									img: item.img,
									size: size,
									id:item.id,
									href:item.href,
									code:item.code,
								})
							}else if(ykey == 3){
								this.tableData.push({
									name: item.name,
									time: item.mtime,
									img: item.img,
									size: size,
									id:item.id,
									href:item.href,
								})
							}else if(ykey == 1){
								this.tableData.push({
									name: item.name,
									time: item.mtime,
									img: item.img,
									size: size,
								})
							}else{
								this.tableData.push({
									name: item.name,
									time: item.mtime,
									img: item.img,
									size: size,
									id:item.id,
								})
							}
						})
					}
				})
			},
			//当前用户进入二级目录
			next(name, img) {
				console.log(img.split(".svg")[0])
				if (img.split(".svg")[0] === 'folder') {
					var newpath = localStorage.getItem('path') + name + '/'
					this.path = newpath
					this.$http.post(this.$hostFile + 'nextLevel', this.$qs.stringify({
						sign: this.$sign,
						username: localStorage.getItem('name'),
						path: newpath

					})).then(res => {
						localStorage.setItem('path', newpath)
						this.tableData = []
						console.log("下一页时候：" + res.data)
						console.log(res.data)
						if (res.data.dir != null) {
							res.data.dir.forEach(item => {
								if (item.size == '') {
									var size = '-'
								} else {
									if (item.size < 1048576) {
										var size = (item.size / 1024).toFixed(2) + 'KB'
									} else if (item.size > 1048576 && item.size < 1073741824) {
										var size = (item.size / 1024 / 1024).toFixed(2) + 'MB'
									} else if (item.size > 1073741824) {
										var size = (item.size / 1024 / 1024 / 1024).toFixed(2) + 'GB'
									}
								}
								this.tableData.push({
									name: item.name,
									time: item.mtime,
									img: item.img,
									size: size
								})
							})
						}

						if (res.data.file != null) {
							res.data.file.forEach(item => {
								if (item.size == '') {
									var size = '-'
								} else {
									if (item.size < 1048576) {
										var size = (item.size / 1024).toFixed(2) + 'KB'
									} else if (item.size > 1048576 && item.size < 1073741824) {
										var size = (item.size / 1024 / 1024).toFixed(2) + 'MB'
									} else if (item.size > 1073741824) {
										var size = (item.size / 1024 / 1024 / 1024).toFixed(2) + 'GB'
									}
								}
								this.tableData.push({
									name: item.name,
									time: item.mtime,
									img: item.img,
									size: size
								})
							})
						}


					})
				} else {
					console.log(name)

					this.$http.post(this.$hostFile + 'seeFile', this.$qs.stringify({
						username: localStorage.getItem('name'),
						filename: name
					})).then(res => {
						if (res.data.sort === 'picture') {
							this.url = res.data.uri;
							this.showViewer = true
						} else if (res.data.sort === 'video') {
							this.$router.push({
								path: "/MP4",
								query: {
									uri: res.data.uri
								}
							})
						} else if (res.data.sort === 'pdf') {
							window.open(
								res.data.uri,
								"_blank"
							);
						} else if (res.data.sort === 'world' || res.data.sort === 'excel') {
							window.open(
								"https://view.officeapps.live.com/op/view.aspx?src=" + res.data.uri,
								"_blank"
							);
						} else {
							alert("当前文件暂不支持打开！！！")
						}
					});
				}
			},
			// onPreview() {
			// 	this.showViewer = true
			// },
			// 关闭查看器
			closeViewer() {
				this.showViewer = false
			},
			//当前用户返回上一层目录
			back() {
				// console.log( localStorage.getItem('path').split('/'))
				var str = localStorage.getItem('path').split('/')
				str.splice(0, 1)
				str.splice(str.length - 1, 1)
				str.splice(str.length - 1, 1)
				var backpath = '/'
				str.forEach(item => {
					backpath += item + '/'
				})
				this.path = backpath
				this.$http.post(this.$hostFile + 'lastLevel', this.$qs.stringify({
					sign: this.$sign,
					username: localStorage.getItem('name'),
					path: backpath

				})).then(res => {
					localStorage.setItem('path', backpath)
					this.tableData = []
					console.log("上一页时候：" + res.data)
					console.log(res.data)
					res.data.dir.forEach(item => {
						if (item.size == '') {
							var size = '-'
						} else {
							if (item.size < 1048576) {
								var size = (item.size / 1024).toFixed(2) + 'KB'
							} else if (item.size > 1048576 && item.size < 1073741824) {
								var size = (item.size / 1024 / 1024).toFixed(2) + 'MB'
							} else if (item.size > 1073741824) {
								var size = (item.size / 1024 / 1024 / 1024).toFixed(2) + 'GB'
							}
						}
						this.tableData.push({
							name: item.name,
							time: item.mtime,
							img: item.img,
							size: size
						})
					})
					if (res.data.file != null) {
						res.data.file.forEach(item => {
							if (item.size == '') {
								var size = '-'
							} else {
								if (item.size < 1048576) {
									var size = (item.size / 1024).toFixed(2) + 'KB'
								} else if (item.size > 1048576 && item.size < 1073741824) {
									var size = (item.size / 1024 / 1024).toFixed(2) + 'MB'
								} else if (item.size > 1073741824) {
									var size = (item.size / 1024 / 1024 / 1024).toFixed(2) +
										'GB'
								}
							}
							this.tableData.push({
								name: item.name,
								time: item.mtime,
								img: item.img,
								size: size
							})
						})
					}
				})

			},
			//当前页面搜索
			search(key) {
				var newlist = []
				this.tableData.forEach(item => {
					if (item.name.indexOf(key) != -1) {
						newlist.push(item)
					}
				})
				return newlist
			},
			handleClose(done) {
				done();
				// this.$confirm('确认关闭？')
				//   .then(_ => {
				//     done();
				//   })
				//   .catch(_ => {
				//   });
			},
			//当前文件上传
			beforeUpload(file) {
				this.loading = true
				let fd = new FormData();
				fd.append('file', file); //传文件
				fd.append('sign', this.$sign); //传其他参数
				fd.append('username', localStorage.getItem('name')); //传其他参数
				fd.append('path', localStorage.getItem('path')); //传其他参数
				var that = this
				console.log(file)
				// const fr = new FileReader();
				// fr.readAsDataURL("File");
				// fr.onload = function(){
				// console.log(fr.result);
				// }

				this.$http.post(this.$hostFile + 'uploadFileToHDFS', fd).then(res => {

					if (res.data.code == 1) {
						this.dialogVisible = false
						that.tableData = []
						that.$message({
							showClose: true,
							message: '上传成功',
							type: 'success'
						});
						that.loading = false
						window.location.reload();
					} else if (res.data.code == 0) {
						that.$message({
							showClose: true,
							message: '文件已存在',
							type: 'warning'
						});
					}
					that.loading = false
				})
			},
			//当前文件或文件夹删除
			del(id,filename, index) {
				this.$confirm('确定删除所选的文件吗？\n删除的文件可通过回收站还原?', '提示', {
					confirmButtonText: '确定',
					cancelButtonText: '取消',
					type: 'warning'
				}).then(() => {
					this.tableData.splice(index, 1)
					if(this.ykey == 1){
						this.$http.post(this.$hostFile + 'delfile', this.$qs.stringify({
							filename:filename,
							name: localStorage.getItem('path') + name,
							username: this.username
						})).then(res => {
							if (res.data.code == 1) {
								this.$message({
									type: 'success',
									message: '删除成功!'
								});
							} else {
								this.$message({
									type: 'warning',
									message: '删除失败!'
								});
							}
						
						})
					}else{
						this.$http.post(this.$hostFile + 'delSortfile', this.$qs.stringify({
							id: id,
							// sign:this.ykey,
							name: localStorage.getItem('path') + name,
							username: this.username
						})).then(res => {
							if (res.data.code == 1) {
								this.$message({
									type: 'success',
									message: '删除成功!'
								});
							} else {
								this.$message({
									type: 'warning',
									message: '删除失败!'
								});
							}
						
						})
					}
				

				}).catch(() => {
					this.$message({
						type: 'info',
						message: '已取消删除'
					});
				});

			},
			//下载
			download(name) {
				window.location.href = this.$hostFile + 'download?username=' + this.username + '&fileName=' +
					name
			},
			//创建新目录（文件夹）
			newfolder() {
				this.$http.post(this.$hostFile + 'newfolder', this.$qs.stringify({
					sign: this.$sign,
					username: this.username,
					path: localStorage.getItem('path'),
					fname: this.input
				})).then(res => {
					if (res.data.code == 0) {
						this.addfolder = false
						this.input = ''

						// this.next('')
						this.$alert('创建文件夹成功', '提示', {
							confirmButtonText: '确定',
							callback: action => {
								this.$message({
									type: 'info',
									message: "完成创建"
								});
								window.location.reload();
							}
						});
					} else {
						this.$alert('创建文件夹失败，文件夹已存在', '提示', {
							confirmButtonText: '确定',
							callback: action => {
								this.$message({
									type: 'info',
									message: "创建失败"
								});
							}
						});
					}
				})
			},
			//创建新文件
			newtxt() {
				this.$http.post(this.$hostFile + 'newtxt', this.$qs.stringify({
					sign: this.$sign,
					username: this.username,
					path: localStorage.getItem('path'),
					txtname: this.input
				})).then(res => {
					if (res.data.code == 0) {
						this.addtxt = false
						this.input = ''
						// this.next('')
						this.$alert('创建文件成功', '提示', {
							confirmButtonText: '确定',
							callback: action => {
								this.$message({
									type: 'info',
									message: "完成创建"
								});
								window.location.reload();
							}
						});
					} else {
						this.$alert('创建文件失败，文件已存在', '提示', {
							confirmButtonText: '确定',
							callback: action => {
								this.$message({
									type: 'info',
									message: "创建失败"
								});
							}
						});
					}
				})
			},
			addsharemid(name) {
				this.shareFileName = name
				this.addshare = true
			},
			shareFilea() {
				this.addshare = false
				this.$http.post(this.$hostFile + 'to_shareFile', this.$qs.stringify({
					username: this.username,
					filename: this.shareFileName,
					code: this.input,
					overdue: this.radio,
				})).then(res => {
					this.input = '';
					this.shareFileName = '';
					if (res.data.code == 1) {
						this.$message({
							type: 'info',
							message: "分享成功！！！"
						});
					}else{
						this.$message({
							type: 'info',
							message: "分享失败！！！"
						});
					}
				})
			},quitShare(id){
				this.$http.post(this.$hostFile + 'quitShare', this.$qs.stringify({
					id: id,
				})).then(res => {
					if (res.data.code == 1) {
						this.$message({
							type: 'info',
							message: "取消成功！！！"
						});
						window.location.reload();
					}else{
						this.$message({
							type: 'info',
							message: "取消失败！！！"
						});
					}
				})
			},huanyuan(id,href,fname){
				this.$http.post(this.$hostFile + 'huanYuan', this.$qs.stringify({
					id: id,
					href:href,
					username:this.username,
					fname:fname,
				})).then(res => {
					if (res.data.code == 1) {
						this.$message({
							type: 'info',
							message: "还原成功！！！"
						});
						window.location.reload();
					}else{
						this.$message({
							type: 'info',
							message: "还原失败！！！"
						});
					}
				})
			},seeDetail(href,code){
				this.seehref = href
				this.seecode = code
				this.seeDetail1 = true
			}
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
