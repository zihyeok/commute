<!-- eslint-disable sonarjs/no-duplicated-branches -->
<!-- eslint-disable no-alert -->
<script setup lang="ts">
import { getLogin } from '@/@api/user/index'
import type { RequestLoginModel } from '@/@api/user/type'
import { userStore } from '@/store/userStore'
import authV1BottomShape from '@images/svg/auth-v1-bottom-shape.svg'
import authV1TopShape from '@images/svg/auth-v1-top-shape.svg'

const router = useRouter()
const userRole = userStore()

const reqModel = ref<RequestLoginModel>({
  lgnId: '',
  lgnPw: '',
})

// 로그인
const login = async () => {
  const req: RequestLoginModel = { ...reqModel.value }

  if (req.lgnId.length === 0) {
    alert('아이디를 입력해주세요')

    return
  }
  else if (req.lgnPw.length === 0) {
    alert('비밀번호를 입력해주세요')

    return
  }
  try {
    const result = await getLogin(req)
    if (result.data.res !== 'OK') {
      alert(result.data.msg)

      return
    }

    // const data = result.data.data

    // if (data.positon === 2) {
    //   // 사용자 로그인 후 동작
    //   // 예: 사용자 페이지로 이동
    //   router.push('/')
    // }
    // else if (data.positon === 3) {
    //   // 관리자 로그인 후 동작
    //   // 예: 관리자 페이지로 이동
    //   router.push('/')
    // }
    // else if (data.positon === 4) {
    //   // 관리자 로그인 후 동작
    //   // 예: 관리자 페이지로 이동
    //   router.push('/')
    // }

    // // 세션 생성
    // useSessionStorage('loginUser', data)

    // 세션에 담기 position
    // userRole.setRole(data.position)
    router.push('/')
  }
  catch (e) {
    console.error(e)
  }
}

// 비밀번호 보여주기
const isPasswordVisible = ref(false)
</script>

<template>
  <div class="auth-wrapper d-flex align-center justify-center pa-4">
    <div class="position-relative my-sm-16 w-25">
      <!-- 👉 Top shape -->
      <VImg
        class="auth-v1-top-shape d-none d-sm-block"
        :src="authV1TopShape"
      />

      <!-- 👉 Bottom shape -->
      <VImg
        class="auth-v1-bottom-shape d-none d-sm-block"
        :src="authV1BottomShape"
      />

      <!-- 👉 Auth Card -->
      <VCard
        class="auth-card pa-4"
        max-width="448"
      >
        <VCardText class="pt-1">
          <h5 class="text-h5 font-weight-semibold mb-1">
            Welcome to Acorns!
          </h5>
        </VCardText>

        <VCardText>
          <VForm @submit.prevent="login">
            <VRow>
              <!-- id -->
              <VCol cols="12">
                <VTextField
                  v-model="reqModel.lgnId"
                  type="text"
                  label="ID"
                  maxlength="12"
                />
              </VCol>

              <!-- password -->
              <VCol cols="12">
                <VTextField
                  v-model="reqModel.lgnPw"
                  class="mb-3"
                  label="Password"
                  maxlength="18"
                  auto-complete="off"
                  :type="isPasswordVisible ? 'text' : 'password'"
                  :append-inner-icon="isPasswordVisible ? 'tabler-eye-off' : 'tabler-eye'"
                  @click:append-inner="isPasswordVisible = !isPasswordVisible"
                />

                <!-- login button -->
                <VBtn
                  block
                  class="mt-7"
                  type="submit"
                >
                  로그인
                </VBtn>
              </VCol>

              <!-- create account -->
              <VCol
                class="text-center text-base"
                cols="12"
              >
                <RouterLink
                  class="text-primary ms-2"
                  :to="{ name: 'register' }"
                >
                  회원가입
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
