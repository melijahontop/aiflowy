<script setup>
import { onMounted, reactive, ref, watch } from 'vue';

import { ElEmpty, ElPagination, ElSkeleton } from 'element-plus';

import { api } from '#/api/request';
// Props
const props = defineProps({
  queryUrl: {
    type: String,
    required: true,
  },
  pageSize: {
    type: Number,
    default: 10,
  },
  initQueryParams: {
    type: Object,
    default: () => ({}),
  },
});

// Emits (用于替代 useImperativeHandle)
const emit = defineEmits(['query-change']);

// 响应式数据
const pageList = ref([]);
const loading = ref(false);
const queryParams = ref({});

const pageInfo = reactive({
  pageNumber: 1,
  pageSize: props.pageSize,
  total: 0,
});

// 模拟 API 调用 - 这里需要根据你的实际 API 调用方式调整
const doGet = async (params) => {
  loading.value = true;
  try {
    // 这里替换为你的实际 API 调用
    // 例如：return await api.get(props.queryUrl, { params })
    const response = await api(`${props.queryUrl}`, {
      params,
    });
    const data = await response.data;
    return { data };
  } finally {
    loading.value = false;
  }
};

// 获取页面数据
const getPageList = async () => {
  try {
    const res = await doGet({
      pageNumber: pageInfo.pageNumber,
      pageSize: pageInfo.pageSize,
      ...queryParams.value,
      ...props.initQueryParams,
    });

    // 根据你的 API 响应结构调整下面的数据访问
    pageList.value = res.data.records || res.data.data || [];
    pageInfo.total = res.data.totalRow || res.data.total || 0;
  } catch (error) {
    console.error('获取数据失败:', error);
    pageList.value = [];
    pageInfo.total = 0;
  }
};

// 分页事件处理
const handleSizeChange = (newSize) => {
  pageInfo.pageSize = newSize;
  pageInfo.pageNumber = 1; // 重置到第一页
};

const handleCurrentChange = (newPage) => {
  pageInfo.pageNumber = newPage;
};

// 暴露给父组件的方法 (替代 useImperativeHandle)
const setQuery = (newQueryParams) => {
  pageInfo.pageNumber = 1;
  pageInfo.pageSize = props.pageSize;
  if (newQueryParams) {
    queryParams.value = newQueryParams;
  } else {
    getPageList();
  }
};

// 暴露方法给父组件
defineExpose({
  setQuery,
  refresh: getPageList,
});

// 监听器
watch(
  [() => pageInfo.pageNumber, () => pageInfo.pageSize, queryParams],
  () => {
    getPageList();
  },
  { deep: true },
);

// 监听 initQueryParams 变化
watch(
  () => props.initQueryParams,
  () => {
    pageInfo.pageNumber = 1;
    getPageList();
  },
  { deep: true },
);

// 生命周期
onMounted(() => {
  getPageList();
});
</script>

<template>
  <div class="page-data-container">
    <ElSkeleton v-if="loading" :rows="6" animated />
    <div v-else>
      <div v-if="pageList.length > 0">
        <slot :page-list="pageList" :refresh="getPageList"></slot>
      </div>
      <ElEmpty v-else :image="Empty.PRESENTED_IMAGE_SIMPLE" />

      <div class="pagination-container">
        <ElPagination
          v-model:current-page="pageInfo.pageNumber"
          v-model:page-size="pageInfo.pageSize"
          :total="pageInfo.total"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>
  </div>
</template>

<style scoped></style>
