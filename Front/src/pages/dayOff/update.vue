<!-- eslint-disable no-alert -->
<script lang="ts" setup>
import { getDayOffList } from '@/@api/dayOff/index'
import type { RequestDayOffListModel, ResponseDayOffListModel } from '@/@api/dayOff/type'

const router = useRouter()
const resDayOffList = ref<ResponseDayOffListModel[]>([])

const reqModel = ref<RequestDayOffListModel>({
  idx: 0,
  active: '1',
  position: 0,
  size: 10,
  page: 1,
})

const totalPage = ref(1)
const selectedRows = ref<string[]>([])
const req: RequestDayOffListModel = { ...reqModel.value }

// session에서 userIdx, position 추출
const loginUser = sessionStorage.getItem('loginUser')
if (loginUser) {
  const userInfo = JSON.parse(loginUser)

  req.idx = userInfo.idx
  req.position = userInfo.position
}
else {
  alert('다시 시도해주세요')
  console.error('loginUser 정보가 없습니다.')
  router.push('/login')
}

const dayoffList = async (val1: number, val2: number) => {
  try {
    req.page = val2
    req.active = '1'

    const result = await getDayOffList(req)

    resDayOffList.value = result.data.list.content
    totalPage.value = result.data.list.totalPages

    if (result.data.res !== 'OK') {
      alert(result.data.msg)
      router.push('/dayoff/dayofflist')

      return
    }
  }
  catch (e) {
    console.error(e)
  }
}

watch(
  () => reqModel.value.page,
  (val1, oldV) => {
    dayoffList(req.idx, val1)
  },
)

onMounted(() => {
  dayoffList(req.idx, reqModel.value.page)
})
</script>

<template>
  <VCard id="invoice-list">
    <VCardText class="d-flex align-center flex-wrap gap-4">
      <VSpacer />
      <div class="d-flex align-center flex-wrap gap-4">
        <!-- 👉 Select status -->
        <div class="invoice-list-filter" />
      </div>
    </VCardText>

    <VDivider />

    <!-- SECTION Table -->
    <VTable class="text-no-wrap invoice-list-table">
      <!-- 👉 Table head -->
      <thead class="text-uppercase">
        <tr>
          <th
            scope="col"
            class="text-center"
            style="width: 13%;"
          >
            번호
          </th>

          <th
            scope="col"
            class="text-center"
            style="width: 40%;"
          >
            사용 날짜
          </th>

          <th
            scope="col"
            class="text-center"
            style="width: 20%;"
          >
            신청일
          </th>

          <th
            scope="col"
            class="text-center"
            style="width: 13%;"
          >
            진행상황
          </th>

          <th
            scope="col"
            class="text-center"
            style="width: 13%;"
          >
            수정
          </th>
        </tr>
      </thead>

      <!-- 👉 Table Body -->
      <template v-if="resDayOffList.length !== 0">
        <tbody
          v-for="(item, idx) in resDayOffList"
          :key="idx"
        >
          <tr style="height: 3.75rem;">
            <!-- 👉 Id -->
            <td class="text-center">
              {{ item.idx }}
            </td>

            <!-- 👉 startDt -->
            <td class="text-center">
              {{ item.startDt }}
            </td>

            <!-- 👉 udtDt -->
            <td class="text-center">
              {{ item.udtDt }}
            </td>

            <!-- 👉 active -->
            <td class="text-center">
              <VAlert
                v-if="item.active === '1'"
                class="w-75 h-25"
                style="margin-left: 20px;"
                color="primary"
                variant="outlined"
              >
                진행중
              </VAlert>
              <VAlert
                v-if="item.active === '2'"
                class="w-75 h-25"
                style="margin-left: 20px;"
                color="success"
                variant="outlined"
              >
                승인
              </VAlert>
              <VAlert
                v-if="item.active === '3'"
                class="w-75 h-25"
                style="margin-left: 20px;"
                color="warning"
                variant="outlined"
              >
                거절
              </VAlert>
              <VAlert
                v-if="item.active === '0'"
                class="w-75 h-25"
                style="margin-left: 20px;"
                color="error"
                variant="outlined"
              >
                취소
              </VAlert>
            </td>

            <td class="text-center">
              <VBtn
                variant="text"
                color="secondary"
              >
                <RouterLink :to="{ name: 'dayOff-updateSet-idx', params: { idx: item.idx } }">
                  수정
                </RouterLink>
              </VBtn>
            </td>
          </tr>
        </tbody>
      </template>

      <!-- 👉 table footer  -->
      <template v-else>
        <tfoot>
          <tr>
            <td
              colspan="8"
              class="text-center text-body-1"
            >
              사용 내역이 없습니다.
            </td>
          </tr>
        </tfoot>
      </template>
    </VTable>

    <VDivider />

    <!-- SECTION Pagination -->
    <VCardText class="d-flex align-center flex-wrap gap-4 py-3">
      <!-- 👉 Pagination meta -->
      <span class="text-sm text-disabled">
        <!-- {{ paginationData }} -->
      </span>

      <VSpacer />

      <!-- 👉 Pagination -->
      <VPagination
        v-model="reqModel.page"
        size="small"
        :total-visible="5"
        :length="totalPage"
        @next="selectedRows = []"
        @prev="selectedRows = []"
      />
    </VCardText>
  </VCard>
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
