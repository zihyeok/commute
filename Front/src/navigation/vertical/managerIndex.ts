import type { ManagerVerticalNavItems } from '@/@layouts/types'

export default [
  {
    title: '관리자Home',
    to: { name: 'index' },
    icon: { icon: 'tabler-smart-home' },
  },
  {
    title: '관리자연차',
    to: { name: 'dayOff' },
    icon: { icon: 'tabler-checkbox' },
    children: [
      { title: '연차 내역', to: 'dayOff-dayOffList' },
      { title: '연차 신청', to: 'dayOff-subscription' },
      { title: '연차 수정', to: 'dayOff-update' },
    ],
  },
  {
    title: '관리자직원',
    to: { name: 'user' },
    icon: { icon: 'tabler-user' },
    children: [
      { title: '직원 목록', to: 'user-List' },
      { title: '출퇴근 리스트', to: 'user-workInOutList' },
    ],
  },
  {
    title: '게시판',
    to: { name: 'board' },
    icon: { icon: 'tabler-square-letter-t' },
  },
  {
    title: '스케줄',
    to: { name: 'schedule' },
    icon: { icon: 'tabler-calendar' },
  },

] as ManagerVerticalNavItems
