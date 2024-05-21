export interface UserParams {
  q: string,
  role: string,
  plan: string,
  status: string,
  perPage: number,
  currentPage: number,
}

export interface User {
  id: number;
  password: string;
  position: number;
}

export interface Admin {
  id: number;
  password: string;
  position: number;
}
