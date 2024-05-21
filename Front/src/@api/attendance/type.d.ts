export interface RequestAttendanceInModel{
  idx: number;
}

export interface ReqUserCheckModel{
  userIdx: number;
  position: number;
  size: number;
  page: number;
}

export interface ResAttendanceListModel{
  idx: number;
  userIdx: number;
  lgnId: string;
  lgnName: string;
  position: number;
  regDt: string;
  workIn: string;
  workOut: string;
  size: number;
  page: number;
  user: {
    name: string;
    lgnId: string;
  };
}
