<!-- eslint-disable no-alert -->
<script setup lang="ts">
import { getUserInfo } from '@/@api/user/index';
import { removeAuthPosition, removeAuthToken } from '@/libs/auth';

const router = useRouter()
const userIdx = 0
interface UserInfoModel {
  name: string
  position: number
}

const reqUserInfo = ref<UserInfoModel>({
  name: '',
  position: 0,
})

// 유저정보가져오기
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

// 로그아웃
const logout = async () => {
  console.log('로그아웃 진행, 쿠키 토큰 삭제')
  removeAuthToken()
  removeAuthPosition()
  alert('로그아웃되셨습니다.')
  router.push('/login')
}

onMounted(() => {
  userInfo()
})
</script>

<template>
  <VBadge
    dot
    location="bottom right"
    offset-x="3"
    offset-y="3"
    bordered
    color="success"
  >
    <VAvatar
      class="cursor-pointer"
      color="primary"
      variant="tonal"
    >
      <VIcon
        v-if="reqUserInfo.position !== 4"
        icon="tabler-brand-baidu"
        size="24"
      />
      <VIcon
        v-else
        icon="tabler-alien"
        size="24"
      />

      <!-- SECTION Menu -->
      <VMenu
        activator="parent"
        width="230"
        location="bottom end"
        offset="14px"
      >
        <VList>
          <!-- 👉 User Avatar & Name -->
          <VListItem>
            <template #prepend>
              <VListItemAction start>
                <VBadge
                  dot
                  location="bottom right"
                  offset-x="3"
                  offset-y="3"
                  color="success"
                >
                  <VAvatar
                    color="primary"
                    variant="tonal"
                  >
                    <!-- <VImg :src="avatar1" /> -->
                    <VIcon
                      v-if="reqUserInfo.position !== 4"
                      icon="tabler-brand-baidu"
                      size="24"
                    />
                    <VIcon
                      v-else
                      icon="tabler-alien"
                      size="24"
                    />
                  </VAvatar>
                </VBadge>
              </VListItemAction>
            </template>

            <VListItemTitle class="font-weight-semibold">
              {{ reqUserInfo.name }} 님
            </VListItemTitle>
          </VListItem>

          <VDivider class="my-2" />

          <!-- 👉 Profile -->
          <VListItem link>
            <template #prepend>
              <VIcon
                class="me-2"
                icon="tabler-user"
                size="22"
              />
            </template>

            <VListItemTitle>
              <VBtn
                type="button"
                variant="text"
              >
                <RouterLink

                  style="color: #bdbdbd;"
                  :to="{ name: 'user-mypageSet-idx', params: { idx: userIdx } }"
                >
                  Mypage
                </RouterLink>
              </VBtn>
            </VListItemTitle>
          </VListItem>

          <!-- 👉 Settings -->

          <!-- 👉 Pricing -->

          <!-- 👉 FAQ -->

          <!-- Divider -->

          <!-- 👉 Logout -->
          <!-- <VListItem to="/login"> -->
          <VListItem>
            <template #prepend>
              <VIcon
                class="me-2"
                icon="tabler-logout"
                size="22"
              />
            </template>
            <VListItemTitle>
              <VBtn
                type="button"
                variant="text"
                color="secondary"
                @click="logout"
              >
                Logout
              </VBtn>
            </VListItemTitle>
          </VListItem>
        </VList>
      </VMenu>
      <!-- !SECTION -->
    </VAvatar>
  </VBadge>
</template>
