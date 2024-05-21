import type { BoardDelete, BoardUpdate, ResponseboardlistModel } from '@/@api/board/type'

import axios from '@/plugins/axios'

const basePath = '/board'

// 게시판등록
export const PostBoardCreate = (data: ResponseboardlistModel) => {
  return axios.post(`${basePath}/create`, data).then(response => {
    return response
  })
}

// 게시판리스트
export const getBoardList = (data: ResponseboardlistModel) => {
  return axios.get(`${basePath}/list`, { params: data }).then(response => {
    return response
  })
}

// 게시판삭제
export const boardDelete = (data: BoardDelete) => {
  return axios.post(`${basePath}/delete`, data).then(response => {
    return response
  })
}

// 게시판수정
export const boardUpdate = (data: BoardUpdate) => {
  return axios.post(`${basePath}/update`, data).then(response => {
    return response
  })
}

// 게시판상세
export const boardArticle = (data: ResponseboardlistModel) => {
  return axios.get(`${basePath}/article`, { params: data }).then(response => {
    return response
  })
}
