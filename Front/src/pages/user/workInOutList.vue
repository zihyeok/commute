<!-- eslint-disable no-alert -->
<script setup lang="ts">
import { getAttendanceList } from '@/@api/attendance/index';
import type { ReqUserCheckModel, ResAttendanceListModel } from '@/@api/attendance/type';

const router = useRouter()

const userCheck = ref<ReqUserCheckModel>({
  userIdx: 0,
  position: 0,
  size: 10,
  page: 1,
})

const resAttendanceList = ref<ResAttendanceListModel[]>([])

const totalPage = ref(1)
const selectedRows = ref<string[]>([])
const req: ReqUserCheckModel = { ...userCheck.value }

// session에서 userIdx, porition 추출
const loginUser = sessionStorage.getItem('loginUser')
if (loginUser) {
  const userInfo = JSON.parse(loginUser)

  req.userIdx = userInfo.idx
  req.position = userInfo.position
}
else {
  alert('다시 시도해주세요')
  console.error('loginUser 정보가 없습니다.')
  router.push('/login')
}

const attendanceList = async (val: number) => {
  try {
    req.page = val

    const result = await getAttendanceList(req)

    resAttendanceList.value = result.data.list.content
    totalPage.value = result.data.list.totalPages
    if (result.data.res !== 'OK') {
      alert(result.data.msg)
      router.push('/')

      return
    }
  }
  catch (e) {
    console.error(e)
  }
}

// 출퇴근 리스트 엑셀출력
const workInOutListExcel = () => {
  alert('수정중입니다.')

  // try {
  //   await getAttendanceListExcel()
  //   alert('엑셀 다운로드를 완료하였습니다.')
  // }
  // catch (e) {
  //   console.error('Excel 생성 실패', e)
  // }
}

watch(
  () => userCheck.value.page,
  (newVal, oldVal) => {
    attendanceList(newVal)
  },
)
onMounted(() => {
  attendanceList(userCheck.value.page)
})
</script>

<template>
  <VCard id="invoice-list">
    <VCardText class="d-flex align-center flex-wrap gap-4">
      <VSpacer />
      <VBtn
        v-if="req.position === 4"
        variant="plain"
        @click="workInOutListExcel"
      >
        엑셀출력
      </VBtn>
    </VCardText>

    <VDivider />

    <!-- SECTION Table -->
    <VTable class="text-no-wrap invoice-list-table">
      <!-- 👉 Table head -->
      <template v-if="req.position !== 4">
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
              style="width: 29%;"
            >
              날짜
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 29%;"
            >
              출근
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 29%;"
            >
              퇴근
            </th>
          </tr>
        </thead>
      </template>
      <template v-if="req.position === 4">
        <thead class="text-uppercase">
          <tr>
            <th
              scope="col"
              class="text-center"
              style="width: 8%;"
            >
              번호
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 10%;"
            >
              아이디
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 16%;"
            >
              이름
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 18%;"
            >
              날짜
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 24%;"
            >
              출근
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 24%;"
            >
              퇴근
            </th>
          </tr>
        </thead>
      </template>

      <!-- 👉 Table Body -->
      <template v-if="resAttendanceList.length !== 0 && req.position !== 4">
        <tbody
          v-for="(item, idx) in resAttendanceList"
          :key="idx"
        >
          <tr style="height: 3.75rem;">
            <!-- 👉 Idx -->
            <td class="text-center">
              {{ item.idx }}
            </td>

            <!-- 👉 regDt -->
            <td class="text-center">
              {{ (item.regDt).substring(0, 10) }}
            </td>

            <!-- 👉 workIn -->
            <td class="text-center">
              {{ item.workIn }}
            </td>

            <!-- 👉 workOut -->
            <td class="text-center">
              {{ item.workOut }}
            </td>
          </tr>
        </tbody>
      </template>
      <template v-if="req.position === 4">
        <tbody
          v-for="(item, idx) in resAttendanceList"
          :key="idx"
        >
          <tr style="height: 3.75rem;">
            <!-- 👉 Idx -->
            <td class="text-center">
              {{ item.idx }}
            </td>

            <!-- 👉 lgnId -->
            <td class="text-center">
              {{ item.lgnId }}
            </td>

            <!-- 👉 name -->
            <td class="text-center">
              {{ item.lgnName }}
            </td>

            <!-- 👉 regDt -->
            <td class="text-center">
              {{ (item.regDt).substring(0, 10) }}
            </td>

            <!-- 👉 workIn -->
            <td class="text-center">
              {{ item.workIn }}
            </td>

            <!-- 👉 workOut -->
            <td class="text-center">
              {{ item.workOut }}
            </td>
          </tr>
        </tbody>
      </template>

      <!-- 👉 table footer  -->
      <template v-if="resAttendanceList.length === 0">
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
        v-model="userCheck.page"
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
