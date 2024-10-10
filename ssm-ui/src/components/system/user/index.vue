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


	<el-table ref="tableRef" row-key="userName" :data="tableData"  align="left">
		<el-table-column prop="userName" label="用户名" width="180" align="left" />
		<el-table-column prop="nickName" label="昵称" width="180" align="left" />
		<el-table-column prop="email" label="邮箱" width="180" align="left" />
		<el-table-column prop="" label="操作" width="180" align="left">
			<el-button type="warning" round v-hasRole ="['admin']">修改</el-button>
			<el-button type="danger" round >删除</el-button>
		</el-table-column>
	</el-table>
	<el-pagination background layout="prev, pager, next" @current-change="changePage" :total="total"
		:page-size="queryParam.size" />
</template>

<script setup>
	import {
		ref,
		onMounted
	} from 'vue';
	import {
		listUser
	} from '@/api/user/index.js';
	import {

	} from '@element-plus/icons-vue'
	
	const queryParam = ref({
		page: 0,
		
		size: 2,
		
		userNmae:'',
		
		nickName:''
	})

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
	
	const onSubmit = function(){
		getList();
	}
	
</script>

<style>
</style>
