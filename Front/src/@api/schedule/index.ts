import type { scheduleDelete, scheduleList, scheduleUpdate, scheduleUpload } from '@/@api/schedule/type'
import axios from '@/plugins/axios'

const basePath = '/schedule'

// 스케줄리스트
export const getScheduleList = (data: scheduleList) => {
  return axios.get(`${basePath}/list`, { params: data }).then(response => {
    return response
  })
}

// 스케줄업로드
export const postScheduleUpload = (data: scheduleUpload) => {
  return axios.post(`${basePath}/upload`, data).then(response => {
    return response
  })
}

// // 스케줄업로드
// export const postScheduleUpload = (data: scheduleUpload) => {
//   return axios.post(`${basePath}/upload`, data, {
//     headers: {
//       'Content-Type': 'application/json',
//     },
//   }).then(response => {
//     return response
//   })
// }

// 스케줄삭제
export const postScheduleDelete = (data: scheduleDelete) => {
  return axios.post(`${basePath}/delete`, data).then(response => {
    return response
  })
}

// 스케줄수정
export const postScheduleUpdate = (data: scheduleUpdate) => {
  return axios.post(`${basePath}/update`, data).then(response => {
    return response
  })
}
