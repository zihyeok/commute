<!-- eslint-disable no-alert -->
<script lang="ts" setup>
import navItems from '@/navigation/vertical'
import managerNavItems from '@/navigation/vertical/managerIndex'

import { useThemeConfig } from '@core/composable/useThemeConfig'

// Components
import Footer from '@/layouts/components/Footer.vue'
import NavBarNotifications from '@/layouts/components/NavBarNotifications.vue'
import NavbarThemeSwitcher from '@/layouts/components/NavbarThemeSwitcher.vue'
import UserProfile from '@/layouts/components/UserProfile.vue'

// @layouts plugin
import { VerticalNavLayout } from '@layouts'

import { getAttendanceIn, getAttendanceInCheck } from '@/@api/attendance/index'
import { getUserInfo } from '@/@api/user/index'

const { appRouteTransition, isLessThanOverlayNavBreakpoint } = useThemeConfig()
const { width: windowWidth } = useWindowSize()
const router = useRouter()
const attendanceCheck = ref(0)

// 출근확인
const attendanceInCheck = async () => {
  try {
    const result = await getAttendanceInCheck()
    if (result.data.res === 'OK') {
      attendanceCheck.value = result.data.data

      return
    }
  }
  catch (e) {
    console.error(e)
  }
}

// 출근 및 퇴근
const workInOut = async () => {
  try {
    const result = await getAttendanceIn(req)
    if (result.data.res !== 'OK') {
      alert(result.data.msg)

      return
    }
    else {
      alert(result.data.msg)
      location.reload()
    }
  }
  catch (e) {
    console.error(e)
  }
}

interface UserInfoModel {
  position: number
}

const reqUserInfo = ref<UserInfoModel>({
  position: 0,
})

// 유저정보가져오기 (position)
const userInfo = async () => {
  try {
    const result = await getUserInfo()
    if (result.data.res === 'OK') {
      reqUserInfo.value = result.data.data

      return
    }
  }
  catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  attendanceInCheck()
  userInfo()
})
</script>

<template>
  <VerticalNavLayout
    :nav-items="reqUserInfo.position === 4 ? managerNavItems : navItems"
  >
    <!-- 👉 navbar -->
    <template #navbar="{ toggleVerticalOverlayNavActive }">
      <div class="d-flex h-100 align-center">
        <VBtn
          v-if="isLessThanOverlayNavBreakpoint(windowWidth)"
          icon
          variant="text"
          color="default"
          class="ms-n3"
          size="small"
          @click="toggleVerticalOverlayNavActive(true)"
        >
          <VIcon
            icon="tabler-menu-2"
            size="24"
          />
        </VBtn>

        <!-- 좌우 결정 -->
        <VSpacer />

        <VBtn
          v-if="attendanceCheck === 0"
          type="button"
          variant="outlined"
          style="margin-right: 12px;"
          @click="workInOut"
        >
          출근
        </VBtn>
        <VBtn
          v-else
          type="button"
          variant="outlined"
          style="margin-right: 12px;"
          @click="workInOut"
        >
          퇴근
        </VBtn>

        <NavbarThemeSwitcher />
        <NavBarNotifications class="me-2" />
        <UserProfile />
      </div>
    </template>

    <!-- 👉 Pages -->
    <RouterView v-slot="{ Component }">
      <Transition
        :name="appRouteTransition"
        mode="out-in"
      >
        <div>
          <Component :is="Component" />
        </div>
      </Transition>
    </RouterView>

    <!-- 👉 Footer -->
    <template #footer>
      <Footer />
    </template>

    <!-- 👉 Customizer -->
    <!-- <TheCustomizer /> -->
  </VerticalNavLayout>
</template>
