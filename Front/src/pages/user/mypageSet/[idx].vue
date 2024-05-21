<!-- eslint-disable no-alert -->
<script setup lang="ts">
import { getMypageUpdate } from '@/@api/user/index'
import type { RequestMypageUpdateModel } from '@/@api/user/type'

const router = useRouter()

let idx = 0
let lgnId = ''
let name = ''
let gender = ''
let phone = ''
let email = ''
const lgnPw_ck = ref<string>('')

// session에서 정보 불러오기
const loginUser = sessionStorage.getItem('loginUser')
if (loginUser) {
  const userInfo = JSON.parse(loginUser)

  idx = userInfo.idx
  lgnId = userInfo.lgnId
  name = userInfo.name
  gender = userInfo.gender
  phone = userInfo.phone
  email = userInfo.email
}
else {
  console.error('loginUser 정보가 없습니다.')
}

const reqModel = reactive<RequestMypageUpdateModel>({
  idx,
  lgnId,
  lgnPw: '',
  name,
  gender,
  phone,
  email,
})

const req: RequestMypageUpdateModel = reqModel

// 정보수정
const mypageUpdate = async () => {
  // 비밀번호 미입력시 return
  if (req.lgnPw.length === 0) {
    alert('비밀번호를 입력해주세요.')

    return
  }

  // 비밀번호 비밀번호확인 체크
  if (req.lgnPw !== lgnPw_ck.value) {
    alert('비밀번호와 비밀번호 확인란이 일치하지 않습니다.')

    return
  }

  // 글자수 제한 (minlength)
  if (req.lgnPw.length < 6) {
    alert('비밀번호는 6자이상 입력해주세요.')

    return
  }

  // 정규표현식
  const regExpIdPw = /^[a-zA-Z0-9]+$/
  if (!regExpIdPw.test(req.lgnPw)) {
    alert('비밀번호는 영어 및 숫자만 입력가능합니다.')

    return
  }

  // 이름
  const regExpName = /^[가-힣]+$/
  if (!regExpName.test(req.name)) {
    alert('이름은 한글만 가능합니다.')

    return
  }

  // 전화번호
  const regExpPhone = /^[0-9-]+$/
  if (!regExpPhone.test(req.phone)) {
    alert('핸드폰 번호형식이 잘못됐습니다. 숫자와 (-)만 입력해주세요')

    return
  }

  // 이메일
  const regExpEmail = /^[a-zA-Z0-9@.]+$/
  if (!regExpEmail.test(req.email)) {
    alert('이메일 형식이 잘못됐습니다. 영어 및 (@.)만 입력해주세요.')

    return
  }

  try {
    const result = await getMypageUpdate(req)
    if (result.data.res !== 'OK') {
      alert(result.data.msg)

      return
    }
    console.log('정보수정 완료, 세션 다시 생성')

    // 세션 생성
    sessionStorage.removeItem('loginUser')
    useSessionStorage('loginUser', result.data.list)
    alert(result.data.msg)
    router.push('/')
  }
  catch (e) {
    console.error(e)
  }
}
</script>

<template>
  <VRow>
    <VCol cols="12">
      <VCard title="마이페이지">
        <VCardText>
          <VForm @submit.prevent="mypageUpdate">
            <VRow>
              <!-- 👉 name -->
              <VCol
                cols="12"
                md="12"
              >
                <VTextField
                  v-model="req.lgnId"
                  label="ID"
                  disabled
                />
              </VCol>
              <!-- 👉 password -->
              <VCol
                cols="12"
                md="12"
              >
                <VTextField
                  v-model="req.lgnPw"
                  label="Password"
                  maxlength="18"
                  autocomplete="off"
                  type="password"
                />
              </VCol>
              <!-- 👉 password check -->
              <VCol
                cols="12"
                md="12"
              >
                <VTextField
                  v-model="lgnPw_ck"
                  label="Passward Check"
                  maxlength="18"
                  autocomplete="off"
                  type="password"
                />
              </VCol>
              <!-- 👉 name -->
              <VCol
                cols="12"
                md="12"
              >
                <VTextField
                  v-model="req.name"
                  label="Name"
                  maxlength="8"
                />
              </VCol>
              <!-- 👉 gender -->
              <VCol
                cols="12"
                md="12"
              >
                <VRadioGroup
                  v-model="req.gender"
                  inline
                >
                  <VRadio
                    style="margin-right: 25%;"
                    label="남"
                    value="남"
                  />
                  <VRadio
                    label="여"
                    value="여"
                  />
                </VRadioGroup>
              </VCol>
              <!-- 👉 phone -->
              <VCol
                cols="12"
                md="12"
              >
                <VTextField
                  v-model="req.phone"
                  label="Phone"
                  maxlength="13"
                />
              </VCol>
              <!-- 👉 email -->
              <VCol
                cols="12"
                md="12"
              >
                <VTextField
                  v-model="req.email"
                  label="Email"
                  maxlength="30"
                />
              </VCol>

              <!-- 👉 Actions Button -->
              <VCol
                cols="12"
                class="d-flex flex-wrap gap-4"
              >
                <VBtn type="submit">
                  정보수정
                </VBtn>
              </VCol>
            </VRow>
          </VForm>
        </VCardText>
      </VCard>
    </VCol>
  </VRow>
</template>

<style lang="scss">
.pricing-dialog {
  .pricing-title {
    font-size: 1.5rem !important;
  }

  .v-card {
    border: 1px solid rgba(var(--v-border-color), var(--v-border-opacity));
    box-shadow: none;
  }
}
</style>
