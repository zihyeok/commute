import type { ReqDayOffAlarmCheckModel, ReqDayOffAlarmListModel, RequestCheckDayOffListModel, RequestDayOffListModel, RequestDayoffModel } from '@/@api/dayOff/type'

import axios from '@/plugins/axios'

const basePath = '/dayOff'

// 연차신청
export const getDayoffCreate = (data: RequestDayoffModel) => {
  return axios.post(`${basePath}/create`, data).then(response => {
    return response
  })
}

// 연차리스트
export const getDayOffList = (data: RequestDayOffListModel) => {
  return axios.get(`${basePath}/list`, { params: data }).then(response => {
    return response
  })
}

// 연차전체리스트
export const getDayOffListAll = (data: RequestDayOffListModel) => {
  return axios.get(`${basePath}/listall`, { params: data }).then(response => {
    return response
  })
}

// 연차상세조회
export const getDayOffDetail = (idx: number) => {
  return axios.get(`${basePath}/detail`, { params: { idx } }).then(response => {
    return response
  })
}

// 연차알람리스트조회
export const getDayOffAlarmList = (data: ReqDayOffAlarmListModel) => {
  return axios.get(`${basePath}/alarm/list`, { params: data }).then(response => {
    return response
  })
}

// 연차알람확인
export const getDayOffAlarmCheckUp = (data: ReqDayOffAlarmCheckModel) => {
  return axios.get(`${basePath}/alarm/check`, { params: data }).then(response => {
    return response
  })
}

// 연차승인거절리스트
export const getDayOffCheckList = (data: RequestCheckDayOffListModel) => {
  return axios.get(`${basePath}/list/check`, { params: data }).then(response => {
    return response
  })
}

// 연차 승인
export const getDayOffApproval = (idx: number) => {
  return axios.get(`${basePath}/approval`, { params: { idx } }).then(response => {
    return response
  })
}

// 연차 거절
export const getDayOffRefuse = (idx: number) => {
  return axios.get(`${basePath}/refuse`, { params: { idx } }).then(response => {
    return response
  })
}

// 연차 취소
export const getDayoffCancel = (idx: number) => {
  return axios.get(`${basePath}/cancel`, { params: { idx } }).then(response => {
    return response
  })
}
