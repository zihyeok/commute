import type { ReqUserCheckModel, RequestAttendanceInModel } from '@/@api/attendance/type'
import axios from '@/plugins/axios'

const basePath = '/attendance'

// 출근확인
export const getAttendanceInCheck = () => {
  return axios.get(`${basePath}/in/check`).then(response => {
    return response
  })
}

// 출근 및 퇴근
export const getAttendanceIn = (data: RequestAttendanceInModel) => {
  return axios.post(`${basePath}/in`, data).then(response => {
    return response
  })
}

// 출퇴근 리스트
export const getAttendanceList = (data: ReqUserCheckModel) => {
  return axios.get(`${basePath}/list`, { params: data }).then(response => {
    return response
  })
}

// 출퇴근 리스트 엑셀출력
export const getAttendanceListExcel = () => {
  return axios.get(`${basePath}/list/excel`).then(response => {
    return response
  })
}
