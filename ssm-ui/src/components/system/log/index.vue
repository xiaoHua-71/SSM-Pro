<template>
	<el-form :inline="true" :model="queryParam" class="demo-form-inline">
		<el-form-item label="用户名:">
			<el-input v-model="queryParam.operName" placeholder="用户名" clearable />
		</el-form-item>
		<el-form-item label="操作:">
			<el-input v-model="queryParam.method" placeholder="操作方法" clearable />
		</el-form-item>
		
		<el-form-item>
			<el-button type="primary" @click="onSubmit">查询</el-button>
		</el-form-item>
	</el-form>
	<div class="mb-4">
	    
	  </div>

	<el-table ref="tableRef" row-key="operId" :data="tableData"  align="left">
		<el-table-column prop="operName" label="用户名" width="180" align="left" />
		<el-table-column prop="method" label="操作方法" width="180" align="left" />
		<el-table-column prop="opertime" label="操作时间" width="180" align="left" />
		<el-table-column prop="delete" label="操作" width="180" align="left" >
			<el-button type="danger" round  width="180">删除</el-button>
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
		listLog
	} from '@/api/log/index.js';
	import {
		
	} from '@element-plus/icons-vue'
	
	const queryParam = ref({
		page: 0,
		
		size: 6,
		
		operName:'',
		
		method:''
	})

	const tableData = ref([]);

	const total = ref(0);
	


	const getList = function() {
		listLog(queryParam.value).then(res => {
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
