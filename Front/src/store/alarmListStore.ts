import type { ReqDayOffAlarmListModel } from '@/@api/dayOff/type'
import axios from '@/plugins/axios'
import { defineStore } from 'pinia'

interface State {
  ResDayOffAlarmListModel: {
    dayOffIdx: number
    userIdx: number
    active: string
    category: string
    startDt: string
    lgnName: string
    lgnId: string
    position: number
    dayoffCount: number
  }
}

export const alarmListStore = defineStore('alarmList', {
  state: (): State => ({
    ResDayOffAlarmListModel: {
      dayOffIdx: 0,
      userIdx: 0,
      active: '',
      category: '',
      startDt: '',
      lgnName: '',
      lgnId: '',
      position: 0,
      dayoffCount: 0,
    },
  }),
  actions: {
    async alarmList(payload: ReqDayOffAlarmListModel) {
      const result = await axios.get('dayOff/alarm/list', { params: payload })
      const ResDayOffAlarmListModel = result.data

      this.ResDayOffAlarmListModel = ResDayOffAlarmListModel
    },
  },
})
