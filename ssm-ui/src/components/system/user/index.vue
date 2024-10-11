<template>
	<el-form :inline="true" :model="queryParam" class="demo-form-inline">
		<el-form-item label="用户名:">
			<el-input v-model="queryParam.userName" placeholder="用户名" clearable />
		</el-form-item>
		<el-form-item label="昵称:">
			<el-input v-model="queryParam.nickName" placeholder="昵称" clearable />
		</el-form-item>

		<el-form-item>
			<el-button type="primary" @click="onSubmit">查询</el-button>

		</el-form-item>

	</el-form>
	<el-button type="success" round v-hasRole="['admin']" @click="create" align="left">新增</el-button>


	<el-table ref="tableRef" row-key="userName" :data="tableData" align="left">
		<el-table-column prop="userName" label="用户名" width="180" align="left" />
		<el-table-column prop="nickName" label="昵称" width="180" align="left" />
		<el-table-column prop="email" label="邮箱" width="200" align="left" />
		<el-table-column prop="" label="操作" width="330" align="left">
			<template #default="scope">
				<el-button type="warning" round v-hasRole="['admin']" @click="handleEdit(scope.$index,scope.row)">修改
				</el-button>
				<el-button type="danger" round @click="handleDelete(scope.$index,scope.row)">删除</el-button>
			</template>

		</el-table-column>
	</el-table>
	<!-- 新增弹窗按钮 -->
	<el-dialog v-model="centerDialogVisible" :title="title" width="600" center>
		<!-- 新增用户表单 -->
		<el-form :model="userFormRef" label-width="auto" style="max-width: 500">
			<el-form-item label="用户名">
				<el-input v-model="userForm.userName" />
			</el-form-item>
			<el-form-item label="昵称">
				<el-input v-model="userForm.nickName" />
			</el-form-item>
			<el-form-item label="邮箱">
				<el-input v-model="userForm.email" />
			</el-form-item>
			<el-form-item label="密码" v-if="userForm.userId === null">
				<el-input v-model="userForm.password" />
			</el-form-item>
			<el-form-item label="确认密码" v-if="userForm.userId === null">
				<el-input v-model="userForm.confirmPasswrod" />
			</el-form-item>
		</el-form>


		<template #footer>
			<div class="dialog-footer">
				<el-button @click="centerDialogVisible = false">取消</el-button>
				<el-button type="primary" @click="submitFrom">
					提交
				</el-button>
			</div>
		</template>
	</el-dialog>

	<el-pagination background layout="prev, pager, next" @current-change="changePage" :total="total"
		:page-size="queryParam.size" />
</template>

<script setup>
	import {
		ref,
		onMounted
	} from 'vue';
	import {
		listUser,
		add,
		getById,
		update,
		deleteUser
	} from '@/api/user/index.js';
	import {

	} from '@element-plus/icons-vue'

	import {
		ElMessage,
		ElMessageBox
	} from 'element-plus'

	const queryParam = ref({
		page: 0,

		size: 3,

		userNmae: '',

		nickName: ''
	})

	let userForm = ref({
		userId: null,
		userName: '',
		nickName: '',
		email: '',
		password: '',
		confirmPasswrod: ''


	})
	let centerDialogVisible = ref(false);

	let title = ref("新增用户")
	const tableData = ref([]);

	const total = ref(0);



	const getList = function() {
		listUser(queryParam.value).then(res => {
			tableData.value = res.data.content;
			total.value = res.data.totalElements;
			queryParam.value.size = res.data.size;
		});
	}

	onMounted(() => {
		getList();
	});

	const changePage = async function(current) {
		queryParam.value.page = current - 1;
		getList();
	}

	const onSubmit = function() {
		getList();
	}



	const submitFrom = function() {

		//1.对比确认密码和密码

		//2.校验表单

		//3.提交ajax请求

		delete userForm.value.confirmPasswrod;
		//提交ajax请求

		//判断当前的userid是否为null，如果是null就是新增

		if (userForm.value.userId === null) {
			add(userForm.value).then(() => {
				centerDialogVisible.value = false;
			})
		} else {
			update(userForm.value).then(res => {
				if (res.status === 200) {
					centerDialogVisible.value = false;
				}
				//更新完之后更新列表



			})
		}
		getList();

	}

	const handleEdit = function(index, row) {
		// 查询到相应的信息，并赋值到表单上去
		getById(row.userId).then(res => {
			userForm.value = res.data;
			//打开修改的窗口
			title.value = "修改用户"
			centerDialogVisible.value = true;
		})
	}

	const handleDelete = function(index, row) {
	    ElMessageBox.confirm(
	        '您确定要删除吗?',
	        '提示消息',
	        {
	            confirmButtonText: '确定',
	            cancelButtonText: '取消',
	            type: 'warning'
	        }
	    ).then(async () => {
	        try {
	            const res = await deleteUser(row.userId);
	            if (res.status === 200) {
	                getList();
	                ElMessage({
	                    type: 'success',
	                    message: '删除成功!'
	                });
	            } else {
	                ElMessage.error('删除失败！');
	            }
	        } catch (error) {
	            ElMessage.error('删除失败：' + error.message);
	        }
	    }).catch(() => {
	        ElMessage({
	            type: 'warning',
	            message: '删除已取消'
	        });
	    });
	};

	let create = function() {
		//清理数据
		userForm.value = {
			userId: null,
			userName: '',
			nickName: '',
			email: '',
			password: '',
			confirmPasswrod: ''
		};
		title.value = "新增用户";
		centerDialogVisible.value = true;

	}
</script>

<style>
</style>
