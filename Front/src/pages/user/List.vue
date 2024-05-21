<!-- eslint-disable sonarjs/no-all-duplicated-branches -->
<!-- eslint-disable @typescript-eslint/indent -->
<script setup lang="ts">
import { getActivelist, getlist, postUserwait } from '@/@api/user/index';
import type { UserListModel } from '@/@api/user/type';
import UserModal from '@/views/apps/user/UserModal.vue';

// 👉 Store
const totalPage = ref(1)
const router = useRouter()
const activeusers = ref<UserListModel[]>([])
const users = ref<UserListModel[]>([])
const positions = ref(1)

// const userCheck = () => {
//   const loginUser = sessionStorage.getItem('loginUser')

//   if (loginUser) {
//     const userInfo = JSON.parse(loginUser)

//     positions.value = userInfo.position
//   }
//   else {
//     alert('다시 시도해주세요')
//     console.error('loginUser 정보가 없습니다.')
//     router.push('/login')
//   }
// }

const UserList = async () => {
  try {
    const result = await getlist()

    users.value = result.data.list.content

    // totalPage.value = result.data.list.totalPages

    if (result.data.res !== 'OK') {
      // eslint-disable-next-line no-alert
      alert(result.data.msg)
      router.push('/')

      return
    }
  }
  catch (e) {
    console.error(e)
  }
}

const ActiveList = async () => {
  try {
    const result = await getActivelist()

    if (result.data.res !== 'OK') {
      // alert(result.data.msg)

      // router.push('/')

      return
    }
    else {
      activeusers.value = result.data.lists.content
    }
  }
  catch (e) {
    console.error(e)
  }
}

const PostionType = (type: number) => {
  let typeName

  switch (type) {
  case 1:
    typeName = '신입'
    break
  case 2:
    typeName = '사원'
    break
  case 3:
    typeName = '팀장'
    break
  case 4:
    typeName = '관리자'
    break
  }

  return typeName
}

const active1 = ref(1)
const active0 = ref(0)

// 승인대개, 삭제
const Userwait = async (idx: any, active: any) => {
  const wait = {
    idx,
    active,
  }

  try {
    const result = await postUserwait(wait)

    if (result.data.res !== 'OK')
      alert(result.data.msg)
    else

      alert(result.data.msg)

    location.reload()
  }
  catch (e) {
    console.error(e)
  }
}

onMounted(async () => {
  await UserList()
  await ActiveList()

  // await userCheck()
  // if (positions.value === 4) {
  //   await UserList()
  //   await ActiveList()
  // }
  // else {
  //   // eslint-disable-next-line no-alert
  //   alert('권한이 없습니다.')
  //   router.go(-1)
  // }
})

const target = ref({})

const editUpdate = items => {
  // eslint-disable-next-line @typescript-eslint/no-use-before-define
  isBoardVisible.value = true
  target.value = { ...items }
}

const isBoardVisible = ref(false)
</script>

<template>
  <section>
    <!-- 👉 Add New User -->
    <VRow>
      <VCol cols="12">
        <VCard title="직원 승인 대기">
          <!-- 👉 Filters -->

          <VCardText class="d-flex flex-wrap py-4 gap-4" />

          <VDivider />

          <VTable class="text-no-wrap">
            <!-- 👉 table head -->
            <thead>
              <tr>
                <th scope="col">
                  이름
                </th>
                <!--
                  <th scope="col">
                  아이디
                  </th>
                -->
                <th scope="col">
                  핸드폰
                </th>
                <th scope="col">
                  입사일
                </th>
                <th scope="col">
                  이메일
                </th>
                <th scope="col" />
              </tr>
            </thead>
            <!-- 👉 table body -->
            <tbody v-if="activeusers.length !== 0">
              <tr
                v-for="(activeUs, id) in activeusers"
                :key="id"
                style="height: 3.75rem;"
              >
                <!-- 👉 이름 -->
                <td>
                  {{ activeUs.name }}
                </td>
                <!-- 👉 아이디 -->
                <!--
                  <td>
                  {{ activeUs.lgnId }}
                  </td>
                -->
                <!-- 👉 핸드폰 -->
                <td>
                  {{ activeUs.phone }}
                </td>

                <!-- 👉 입사일 -->
                <td>
                  {{ activeUs.regDt }}
                </td>

                <!-- 👉 이메일 -->
                <td>
                  {{ activeUs.email }}
                </td>

                <!-- 👉 Actions -->

                <td style="width: 250px;">
                  <VBtn
                    variant="outlined"
                    color="success"
                    @click="() => Userwait(activeUs.idx, active1)"
                  >
                    승인
                  </VBtn>
                  <VBtn
                    variant="outlined"
                    color="warning"
                    style="margin-left: 20px;"
                    @click="() => Userwait(activeUs.idx, active0)"
                  >
                    거절
                  </VBtn>
                </td>
              </tr>
            </tbody>

            <!-- 👉 table footer  -->
          </VTable>

          <VDivider />

          <VCardText class="d-flex align-center flex-wrap justify-space-between gap-4 py-3 px-5">
            <span class="text-sm text-disabled" />

            <!-- 👉 Pagination -->
            <!--
              <VPagination
              v-model="reqModel.number"
              size="small"
              :total-visible="5"
              :length="totalPage"
              @next="selectedRows = []"
              @prev="selectedRows = []"
              />
            -->
          </VCardText>
        </VCard>
      </VCol>
    </VRow>
  </section>
  <section>
    <VRow>
      <VCol cols="12">
        <VCard title="직원 목록">
          <!-- 👉 Filters -->

          <VCardText class="d-flex flex-wrap py-4 gap-4" />

          <VDivider />

          <VTable class="text-no-wrap">
            <!-- 👉 table head -->
            <thead>
              <tr>
                <th scope="col">
                  이름
                </th>
                <!--
                  <th scope="col">
                  아이디
                  </th>
                -->
                <th scope="col">
                  직급
                </th>
                <th scope="col">
                  핸드폰
                </th>
                <th scope="col">
                  입사일
                </th>
                <th scope="col">
                  이메일
                </th>
                <th scope="col">
                  설정
                </th>
              </tr>
            </thead>
            <!-- 👉 table body -->
            <tbody v-if="users.length !== 0">
              <tr
                v-for="(user, id) in users"
                :key="id"
                style="height: 3.75rem;"
              >
                <!-- 👉 이름 -->
                <td>
                  {{ user.name }}
                </td>
                <!-- 👉 아이디 -->
                <!--
                  <td>
                  {{ user.lgnId }}
                  </td>
                -->

                <!-- 👉 직급 -->
                <td>
                  {{ PostionType(user.position) }}
                </td>

                <!-- 👉 핸드폰 -->
                <td>
                  {{ user.phone }}
                </td>

                <!-- 👉 입사일 -->
                <td>
                  {{ user.regDt }}
                </td>

                <!-- 👉 이메일 -->
                <td>
                  {{ user.email }}
                </td>

                <!-- 👉 Actions -->
                <td
                  class="text-center"
                  style="width: 5rem;"
                >
                  <VBtn
                    icon
                    size="x-small"
                    color="default"
                    variant="text"
                  >
                    <VIcon
                      size="22"
                      icon="tabler-dots-vertical"
                    />
                    <VMenu activator="parent">
                      <VList>
                        <VListItem
                          title="퇴직"
                          @click="() => Userwait(user.idx, active0)"
                        />
                      </VList>
                      <VList>
                        <VListItem
                          title="직급수정"
                          @click="editUpdate(user)"
                        />
                      </VList>
                    </VMenu>
                  </VBtn>
                </td>
              </tr>
            </tbody>

            <!-- 👉 table footer  -->
          </VTable>

          <VDivider />

          <VCardText class="d-flex align-center flex-wrap justify-space-between gap-4 py-3 px-5">
            <span class="text-sm text-disabled" />

            <!-- 👉 Pagination -->
            <!--
              <VPagination
              v-model="reqModel.number"
              size="small"
              :total-visible="5"
              :length="totalPage"
              @next="selectedRows = []"
              @prev="selectedRows = []"
              />
            -->
          </VCardText>
        </VCard>
      </VCol>
    </VRow>

    <!-- 👉 Add New User -->
    <UserModal
      v-model:isDialogVisible="isBoardVisible"
      :target="target"
      @UserList="UserList"
    />
  </section>
</template>

<style lang="scss">
#invoice-list {
  .invoice-list-actions {
    inline-size: 8rem;
  }

  .invoice-list-filter {
    inline-size: 12rem;
  }
}
</style>
