import type { RequestLoginModel, RequestMypageUpdateModel, RequestRegisterModel, UserListModel, UserPositionModel, UserWaitModel } from '@/@api/user/type'
import axios from '@/plugins/axios'

const basePath = '/user'

// 회원가입
export const getRegister = (data: RequestRegisterModel) => {
  return axios.post(`${basePath}/register`, data).then(response => {
    return response
  })
}

// 로그인
export const getLogin = (data: RequestLoginModel) => {
  return axios.post(`${basePath}/login`, data).then(response => {
    return response
  })
}

// 전체리스트
export const getlist = (data: UserListModel) => {
  return axios.get(`${basePath}/list`, { params: data }).then(response => {
    return response
  })
}

// 전체리스트
export const getActivelist = (data: UserListModel) => {
  return axios.get(`${basePath}/activelist`, { params: data }).then(response => {
    return response
  })
}

// 승인대기, 삭제
export const postUserwait = (data: UserWaitModel) => {
  return axios.post(`${basePath}/wait`, data).then(response => {
    return response
  })
}

// 정보수정
export const getMypageUpdate = (data: RequestMypageUpdateModel) => {
  return axios.post(`${basePath}/update`, data).then(response => {
    return response
  })
}

// 직급수정
export const PostPosisionUpdate = (data: UserPositionModel) => {
  return axios.post(`${basePath}/position`, data).then(response => {
    return response
  })
}

// 유저정보
export const getUserInfo = () => {
  return axios.post(`${basePath}/info`).then(response => {
    return response
  })
}
