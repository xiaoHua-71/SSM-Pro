<template>
	<el-container>
		<el-aside width="790px">
			<el-image fit="fill" :src="require('@/assets/image/login_left.png')"></el-image>
		</el-aside>
		<el-main>
			<el-card class="box-card login-card">
				<span class="login-title">XH后台管理系统</span>
				<span class="login-tip">welcome 欢迎登陆</span>
				<el-form ref="user" :model="user" label-width="80px" :rules="rules">
					<el-form-item label="用户名" prop="userName">
						<el-input v-model="user.userName" placeholder="请输入用户"></el-input>
					</el-form-item>
					<el-form-item label="密码" prop="password">
						<el-input v-model="user.password" type="password" placeholder="请输入密码" show-password></el-input>
					</el-form-item>
					<el-form-item>
						<el-button type="primary" @click="doLogin">登陆</el-button>
					</el-form-item>
				</el-form>
			</el-card>
		</el-main>
	</el-container>
</template>

<script>
	import { ElMessage } from 'element-plus'
	export default {
		name: "login",
		data() {
			return {
				// 用户信息
				user: {
					userName: "xh_admin",
					password: "xxxxxx",
				},
				rules: {
					userName: [{
						required: true,
						// blur为鼠标离开触发
						trigger: "blur",
						message: "用户名不能为空"
					},{
						min: 3,
						max: 10,
						message:"用户名的长度必须是3-10",
						trigger: "blur"
					}],
					password: [{
						required: true,
						trigger: "blur",
						message: "密码不能为空"
					}],
					code: [{
						required: true,
						trigger: "change",
						message: "验证码不能为空"
					}]
				}
			};
		},
		methods: {
			doLogin() {
				this.$refs.user.validate(valid =>{
					if(valid){
						this.$store.dispatch("LOGIN", this.user).then(res =>{
							if(res.status === 200){
								this.$store.dispatch("GET_INFO").then(() =>{
									this.$router.push({name:'main'})
								})
							

							   this.$router.push({name:"main"});
							   ElMessage({
							     showClose: true,
							     message: '登录成功!',
							     type: 'success',
							   })
							}
						});
						
					}else{
						   ElMessage({
						     showClose: true,
						     message: 'Your Data Is ERROR!',
						     type: 'error',
						   })
					}
				})
				
			}
		}

	};
</script>

<style scope>
	.el-image {
		height: 885px;
		width: 750px;
	}

	.el-main {
		position: relative;
	}

	.login-card {
		position: absolute;
		top: 0;
		left: 0;
		bottom: 0;
		right: 0;
		margin: auto;
		width: 480px;
		height: 400px;
		padding: 50px;
	}

	.login-title {
		width: 459px;
		height: 70px;
		font-size: 40px;
		font-family: AlibabaPuHuiTiB;
		color: #333333;
		line-height: 90px;
		letter-spacing: 1px;
		font-weight: 800;
		display: block;
		text-align: left;
	}

	.login-tip {
		width: 319px;
		height: 70px;
		font-size: 30px;
		font-family: PingFangSC-Regular, PingFang SC;
		font-weight: 400;
		color: #999999;
		line-height: 90px;
		letter-spacing: 1px;
		display: block;
		text-align: left;
		margin-bottom: 30px;
	}
</style>
