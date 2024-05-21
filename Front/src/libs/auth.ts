import { useCookies } from 'vue3-cookies'

const { cookies } = useCookies()

// 토큰 쿠키에 저장
export function setAuthToken(data: string) {
  return cookies.set('authorization', data)
}

// 토큰 쿠키에서 불러오기
export function getAuthToken() {
  return cookies.get('authorization')
}

// 쿠키에서 토큰 지우기
export function removeAuthToken() {
  return cookies.remove('authorization')
}

// 포지션 쿠키에 저장
export function setAuthPosition(data: string) {
  return cookies.set('setAuthPosition', data)
}

// 포지션 쿠키 지우기
export function removeAuthPosition() {
  return cookies.remove('setAuthPosition')
}

// 토큰 쿠키에서 postion 불러오기
export function getAuthPosition() {
  return cookies.get('setAuthPosition')
}
