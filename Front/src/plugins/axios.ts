import { getAuthToken, setAuthPosition, setAuthToken } from '@/libs/auth'
// eslint-disable-next-line import/order
import axios from 'axios'

const axiosIns = axios.create({
  // You can add your headers here
  // ================================
  // baseURL: 'https://some-domain.com/api/',
  // timeout: 1000,
  // headers: {'X-Custom-Header': 'foobar'}

  // baseURL: 'http://3.34.179.41:8080',

  baseURL: 'http://localhost:9944',

  headers: {
    'Content-Type': 'application/json',
  },

})

// 요청 인터셉터
axiosIns.interceptors.request.use(
  config => {
    // 요청 보내기 전 수행
    // 헤더가 없는 경우 헤더 객체를 생성
    config.headers = config.headers || {}

    const token = getAuthToken()

    // 헤더에 토큰 추가
    if (token)

      config.headers.Authorization = token

    return config
  },
  error => {
    // 요청 에러시 실행
    return Promise.reject(error)
  },
)

// 응답 인터셉터
axiosIns.interceptors.response.use(
  response => {
    if (response.status === 200) {
      // 쿠키에 토큰 넣기

      const token = response.headers.authorization
      const position = response.headers.position

      if (token)
        setAuthToken(token)

      if (position)
        setAuthPosition(position)

      return response
    }
    else {
      return Promise.reject(response)
    }
  },
  error => {
    // // 오류 응답 처리
    // back에서 security 사용 보류중...!!!
    // const status = error.response.request.status
    // if (status === 401)
    //   alert('로그인 정보가 없습니다.')
    // console.error(error.response.data)
    // // 푸쉬 안되는듯?
    // router.push('/login')
    return Promise.reject(error)
  },
)
export default axiosIns
