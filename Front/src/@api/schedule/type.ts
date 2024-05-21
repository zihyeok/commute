export interface scheduleList {

  id: number
  start: string
  end: string
  title: string
  regDt: string
  udtDt: string
  udtActive: number

}
export interface scheduleUpload {

  title: string
  start: string
  end: string

}
export interface scheduleUpdate {

  id: number
  title: string
  start: string
  end: string
  udtActive: number

}
export interface scheduleDelete {

  id: number
  udtActive: number

}
