export interface RequestRegisterModel{
    lgnId: string;
    lgnPw: string;
    name: string;
    gender: string;
    phone: string;
    email: string;
}

export interface RequestLoginModel{
    lgnId: string;
    lgnPw: string;
}

export interface RequestDayoffListModel{
    idx: number;
}

export interface UserListModel{
    idx: number;  		 
    lgnId: string;
    lgnPw: string;		
    name: string;		
    gender: string;		
    position: number;	
    phone: string;		
    email: string;		
    active: number;		
    regDt: string;		
    workInDt: string;	
    udtDt: string;
}

export interface UserWaitModel{
  idx: number;
  active: number;

}

export interface UserPositionModel{
  idx: number;
  position: number;

}
