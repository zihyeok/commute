export interface ResponseboardlistModel {
  idx: number
  category: number
  title: string
  content: string
  boardPw: string
  regDt: string
  udtDt: string
  view: number
  state: number
}

export interface pageModel {

  size: number
  page: number

}

export interface BoardDelete {

  idx: number
  boardPw: string
  state: number

}

export interface BoardUpdate {

  idx: number
  boardPw: string
  title: string
  content: string

}

