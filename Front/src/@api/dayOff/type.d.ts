export interface ResponseDayOffListModel{
    idx: number;
    userIdx: number;
    category: string;
    content: string;
    regDt: string;
    active: string;
    dayoffCount: number;
    offCheck: number;
    startDt: string;
    udtDt: string;
    lgnId: string;
    lgnName: string;
}

export interface RequestDayOffListModel{
    idx: number;
    active: string;
    size: number;
    page: number;
    position: number;
}

export interface RequestDayoffModel{
  idx: number;
  userIdx: number;
  startDt: string;
  category: number;
  content: string;
  dayoffCount?: number;
}

export interface ResponseDayOffDetailModel{
  idx: number;
  userIdx: number;
  category: string;
  content: string;
  regDt: string;
  startDt: string;
  udtDt: string;
  active: number;
  offCheck: string;
  dayoffCount: number;
}

export interface ReqDayOffAlarmListModel{
  idx: number;
  position: number;
}

export interface ResDayOffAlarmListModel{
  dayOffIdx: number;
  userIdx: number;
  active: string;
  category: string;
  startDt: string;
  lgnName: string;
  lgnId: string;
  position: number;
  dayoffCount: number;
}

export interface Notification {
  subtitle: string;
  dayOffIdx: number;
  position: number;
}

export interface ReqDayOffAlarmCheckModel{
  dayOffIdx: number;
  position: number;
}

export interface RequestCheckDayOffListModel{
  page: number;
  size: number;
}
