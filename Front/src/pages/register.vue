<!-- eslint-disable no-alert -->
<script setup lang="ts">
import { getRegister } from '@/@api/user/index';
import type { RequestRegisterModel } from '@/@api/user/type';
import authV1BottomShape from '@images/svg/auth-v1-bottom-shape.svg';
import authV1TopShape from '@images/svg/auth-v1-top-shape.svg';

const router = useRouter()
const lgnPw_ck = ref<string>('')

const reqModel = ref<RequestRegisterModel>({
  lgnId: '',
  lgnPw: '',
  name: '',
  gender: '',
  phone: '',
  email: '',
})

const register = async () => {
  const req: RequestRegisterModel = { ...reqModel.value }

  // 회원가입 미입력시 return
  if (req.lgnId.trim().length === 0) {
    alert('아이디를 입력해주세요.')

    return
  }
  else if (req.lgnPw.length === 0) {
    alert('비밀번호를 입력해주세요.')

    return
  }
  else if (req.name.trim().length === 0) {
    alert('이름을 입력해주세요.')

    return
  }

  else if (req.phone.trim().length === 0) {
    alert('핸드폰 번호를 입력해주세요.')

    return
  }
  else if (req.email.trim().length === 0) {
    alert('이메일을 입력해주세요.')

    return
  }

  // 비밀번호 비밀번호확인 체크
  if (req.lgnPw !== lgnPw_ck.value) {
    alert('비밀번호와 비밀번호 확인란이 일치하지 않습니다.')

    return
  }

  // 글자수 제한 (minlength)
  if (req.lgnId.length < 3) {
    alert('아이디는 3자이상 입력해주세요.')

    return
  }
  else if (req.lgnPw.length < 6) {
    alert('비밀번호는 6자이상 입력해주세요.')

    return
  }

  // 정규표현식
  // 아이디
  const regExpIdPw = /^[a-zA-Z0-9]+$/
  if (!regExpIdPw.test(req.lgnId)) {
    alert('아이디는 영어 및 숫자만 입력가능합니다.')

    return
  }
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
    const result = await getRegister(req)
    if (result.data.res !== 'OK') {
      alert(result.data.msg)

      return
    }
    alert(result.data.msg)
    router.push('/login')
  }
  catch (e) {
    console.log(e, '회원가입 에러...')
  }
}
</script>

<template>
  <div class="auth-wrapper d-flex align-center justify-center pa-4">
    <div class="position-relative my-sm-16">
      <!-- 👉 Top shape -->
      <VImg
        :src="authV1TopShape"
        class="auth-v1-top-shape d-none d-sm-block"
      />

      <!-- 👉 Bottom shape -->
      <VImg
        :src="authV1BottomShape"
        class="auth-v1-bottom-shape d-none d-sm-block"
      />

      <!-- 👉 Auth card -->
      <VCard
        class="auth-card pa-4"
        max-width="448"
      >
        <VCardText class="pt-2 mt-6">
          <h5 class="text-h5 font-weight-semibold mb-1">
            회원가입
          </h5>
        </VCardText>

        <VCardText>
          <VForm @submit.prevent="register">
            <VRow>
              <!-- ID -->
              <VCol cols="12">
                <VTextField
                  ref="lgnIdInput"
                  v-model="reqModel.lgnId"
                  label="ID"
                  maxlength="12"
                />
              </VCol>
              <!-- Passward -->
              <VCol cols="12">
                <VTextField
                  v-model="reqModel.lgnPw"
                  label="Passward"
                  maxlength="18"
                  autocomplete="off"
                  type="password"
                />
              </VCol>
              <!-- Passward Check -->
              <VCol cols="12">
                <VTextField
                  v-model="lgnPw_ck"
                  label="Passward Check"
                  maxlength="18"
                  autocomplete="off"
                  type="password"
                />
              </VCol>
              <!-- Username -->
              <VCol cols="12">
                <VTextField
                  v-model="reqModel.name"
                  maxlength="8"
                  label="Username"
                />
              </VCol>
              <!-- gender -->
              <VCol
                cols="12"
                style="margin-left: 23%;"
              >
                <!--
                  <VRadioGroup
                  v-model="reqModel.gender"
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
                -->
              </VCol>
              <!-- Phone -->
              <VCol cols="12">
                <VTextField
                  v-model="reqModel.phone"
                  maxlength="13"
                  label="Phone"
                />
              </VCol>
              <!-- email -->
              <VCol cols="12">
                <VTextField
                  v-model="reqModel.email"
                  label="Email"
                  maxlength="30"
                  type="email"
                />
              </VCol>

              <!-- password -->
              <VCol cols="12">
                <VBtn
                  block
                  type="submit"
                >
                  회원가입
                </VBtn>
              </VCol>

              <!-- login instead -->
              <VCol
                cols="12"
                class="text-center text-base"
              >
                <span>회원가입이 되어있으신가요?</span>
                <RouterLink
                  class="text-primary ms-2"
                  :to="{ name: 'login' }"
                >
                  로그인
                </RouterLink>
              </VCol>
            </VRow>
          </VForm>
        </VCardText>
      </VCard>
    </div>
  </div>
</template>

<style lang="scss">
@use "@core/scss/template/pages/page-auth.scss";
</style>

<route lang="yaml">
meta:
  layout: blank
</route>
