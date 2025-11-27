import type { RouteRecordRaw } from 'vue-router';

const routes: RouteRecordRaw[] = [
  {
    name: 'Bots',
    path: '/bots',
    component: () => import('#/views/bots/index.vue'),
    meta: {
      icon: 'mdi:robot-outline',
      order: 1,
      title: '智能体',
    },
  },
  {
    name: 'Bot',
    path: '/bots/bot/:id',
    component: () => import('#/views/bots/bot/index.vue'),
    meta: {
      title: '智能体',
      hideInMenu: true,
      hideInTab: true,
      hideInBreadcrumb: true,
      activePath: '/bots',
    },
  },
];

export default routes;
