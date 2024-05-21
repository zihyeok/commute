/* eslint-disable import/order */
import { getScheduleList, postScheduleDelete, postScheduleUpdate, postScheduleUpload } from '@/@api/schedule'
import axios from '@axios'
import { format } from 'date-fns'

import type { Event, NewEvent } from './types'

export const useCalendarStore = defineStore('calendar', {
  // arrow function recommended for full type inference
  state: () => ({
    availableCalendars: [

      {
        color: 'error',
        label: '연차',
      },
      {
        color: 'warning',
        label: '스케줄',
      },

    ],
    selectedCalendars: ['연차', '스케줄'],
  }),
  actions: {
    async fetchList() {
      // 리스트
      return await getScheduleList()
    },
    async fetchEvents() {
      return axios.get('/apps/calendar/events', { params: { calendars: this.selectedCalendars.join(',') } })
    },
    async addEvent(event: NewEvent) {
      // 등록
      return await postScheduleUpload(event)
    },
    async updateEvent(event: Event) {
      // console.log(event, '===================')
      const updatedEvent = { ...event, udtActive: 2 }
      const formattedStartDate = format(new Date(event.start), 'yyyy-MM-dd')
      const formattedEndDate = format(new Date(event.end), 'yyyy-MM-dd')

      updatedEvent.start = formattedStartDate
      updatedEvent.end = formattedEndDate

      // 수정
      // return axios.post(`/apps/calendar/events/${event.id}`, { event })
      return await postScheduleUpdate(updatedEvent)
    },
    async removeEvent(id: number) {
      // 삭제
      // return axios.delete(`/apps/calendar/events/${eventId}`)

      const data = {
        id,
        udtActive: 3,
      }

      return await postScheduleDelete(data)
    },

  },
})
