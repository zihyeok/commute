<!-- eslint-disable no-alert -->
<script lang="ts" setup>
import { getDayOffAlarmList } from '@/@api/dayOff/index';
import type { Notification, ReqDayOffAlarmListModel, ResDayOffAlarmListModel } from '@/@api/dayOff/type';
import { alarmListStore } from '@/store/alarmListStore';

import Notifications from '@core/components/Notifications.vue';

const store = alarmListStore()
const router = useRouter()
const resDayOffAlarmList = ref<ResDayOffAlarmListModel[]>([])

const reqModel = ref<ReqDayOffAlarmListModel>({
  idx: 0,
  position: 0,
})

const notifications = ref<Notification[]>([])

const req: ReqDayOffAlarmListModel = { ...reqModel.value }

// session에서 userIdx, porition 추출
// const loginUser = sessionStorage.getItem('loginUser')
// if (loginUser) {
//   const userInfo = JSON.parse(loginUser)

//   req.idx = userInfo.idx
//   req.position = userInfo.position
// }
// else {
//   alert('다시 시도해주세요')
//   console.error('loginUser 정보가 없습니다.')
//   router.push('/login')
// }

const DayOffAlarmList = async () => {
  console.log('알람 호출됨')

  // await store.alarmList(req)

  // console.log('여기', store.ResDayOffAlarmListModel)

  try {
    const result = await getDayOffAlarmList(req)

    resDayOffAlarmList.value = result.data.list

    // 받은 데이터 가공 category, active
    for (const item of resDayOffAlarmList.value) {
      if (item.category === '1')
        item.category = '연차'
      else if (item.category === '2')
        item.category = '반차'
      else if (item.category === '3')
        item.category = '기타연차'

      if (item.active === '0')
        item.active = '취소'
      else if (item.active === '1')
        item.active = '신청'
      else if (item.active === '2')
        item.active = '승인'
      else if (item.active === '3')
        item.active = '거절'
    }
    if (req.position === 4) {
      notifications.value = resDayOffAlarmList.value.map(item => ({
        subtitle: `${item.lgnName}(${item.lgnId})님이 ${item.category}를 신청하셨습니다.`,
        dayOffIdx: item.dayOffIdx,
        position: req.position,
      }))
    }
    else {
      notifications.value = resDayOffAlarmList.value.map(item => ({
        subtitle: `${item.startDt} ${item.category}가 ${item.active}되었습니다.`,
        dayOffIdx: item.dayOffIdx,
        position: req.position,
      }))
    }
  }
  catch (e) {
    console.error(e)
  }
}

const notificationsTop5 = computed(() => notifications.value.slice(0, 5))

onMounted(() => {
  DayOffAlarmList()

  // 3분에 한번씩 알람 호출
  setInterval(DayOffAlarmList, 3 * 60 * 1000)
})
</script>

<template>
  <Notifications
    :notifications="notificationsTop5"
    :notifications-original="notifications"
  />
</template>
