<!-- eslint-disable sonarjs/no-all-duplicated-branches -->
<!-- eslint-disable no-alert -->
<script setup lang="ts">
import { getDayOffApproval, getDayOffCheckList, getDayOffDetail, getDayOffList, getDayOffRefuse } from '@/@api/dayOff/index';
import type { RequestCheckDayOffListModel, RequestDayOffListModel, ResponseDayOffDetailModel, ResponseDayOffListModel } from '@/@api/dayOff/type';

const router = useRouter()
const isDialogVisible = ref(false)
const selectedStatus = ref<string>('전체')

const searchOption = {
  active: [
    { title: '전체', value: '4' },
    { title: '신청', value: '1' },
    { title: '승인', value: '2' },
    { title: '거절', value: '3' },
    { title: '취소', value: '0' },
  ],
}

const resDayOffList = ref<ResponseDayOffListModel[]>([])
const resDayOffDetail = ref<ResponseDayOffDetailModel>()

const reqModel = ref<RequestDayOffListModel>({
  idx: 0,
  active: searchOption.active[0].value,
  size: 10,
  page: 1,
  position: 0,
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

// 연차리스트
const dayoffList = async (val1: string, val2: number) => {
  try {
    req.active = val1
    req.page = val2

    const result = await getDayOffList(req)

    resDayOffList.value = result.data.list.content
    totalPage.value = result.data.list.totalPages
    if (result.data.res !== 'OK') {
      alert(result.data.msg)
      router.push('/')
    }
  }
  catch (e) {
    console.error(e)
  }
}

// 상세페이지
const dayOffDetail = async (idx: number) => {
  try {
    const result = await getDayOffDetail(idx)

    resDayOffDetail.value = result.data.list

    // 카테고리 값 가공
    if (resDayOffDetail.value) {
      if (resDayOffDetail.value.category === '1')
        resDayOffDetail.value.category = '연차'
      else if (resDayOffDetail.value.category === '2')
        resDayOffDetail.value.category = '반차'
      else if (resDayOffDetail.value.category === '3')
        resDayOffDetail.value.category = '기타'
    }
  }
  catch (e) {
    console.error(e)
  }
}

// 상세페이지 버튼 클릭시
const detailClick = (idx: number) => {
  isDialogVisible.value = !isDialogVisible.value
  dayOffDetail(idx)
}

const checkReqModel = ref<RequestCheckDayOffListModel>({
  size: 3,
  page: 1,
})

const checkTotalPage = ref(1)
const checkResDayOffList = ref<ResponseDayOffListModel[]>([])
const checkResDayOffDetail = ref<ResponseDayOffDetailModel>()
const checkReq: RequestCheckDayOffListModel = { ...checkReqModel.value }

// 관리자 체크(승인 거절) 리스트
const dayoffCheckList = async (page: number) => {
  try {
    checkReq.page = page

    const result = await getDayOffCheckList(checkReq)

    checkResDayOffList.value = result.data.list.content
    checkTotalPage.value = result.data.list.totalPages

    // 카테고리 값 가공
    if (checkResDayOffDetail.value) {
      if (checkResDayOffDetail.value.category === '1')
        checkResDayOffDetail.value.category = '연차'
      else if (checkResDayOffDetail.value.category === '2')
        checkResDayOffDetail.value.category = '반차'
      else if (checkResDayOffDetail.value.category === '3')
        checkResDayOffDetail.value.category = '기타'
    }
  }
  catch (e) {
    console.error(e)
  }
}

// 연차 승인
const dayOffApproval = async (idx: number) => {
  console.log(
    '승인', idx,
  )
  try {
    const result = await getDayOffApproval(idx)
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

// 연차 거절
const dayOffRefuse = async (idx: number) => {
  console.log('거절', idx)
  try {
    const result = await getDayOffRefuse(idx)
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

// 카테고리 검색시
watch(
  () => selectedStatus.value,
  (newVal, oldVal) => {
    console.log(newVal)
    dayoffList(newVal, reqModel.value.page)
  },
)

// 연차내역 페이지 클릭시
watch(
  () => reqModel.value.page,
  (newVal, oldVal) => {
    dayoffList(reqModel.value.active, newVal)
  },
)

// 관리자 승인 거절 페이지
watch(
  () => checkReqModel.value.page,
  (newVal, oldVal) => {
    dayoffCheckList(newVal)
  },
)

onMounted(() => {
  dayoffList(reqModel.value.active, reqModel.value.page)
  dayoffCheckList(checkReqModel.value.page)

  // 3분에 한 번씩 연차 신청 리스트 호출
  setInterval(() => {
    dayoffCheckList(checkReqModel.value.page)
  }, 10 * 60 * 1000)
})
</script>

<template>
  <template v-if="req.position === 4">
    <VCard id="invoice-list">
      <VCardText class="d-flex align-center flex-wrap gap-4">
        <span class="font-weight-bold">연차 신청 리스트</span>
      </VCardText>
      <VDivider />
      <VTable class="text-no-wrap invoice-list-table">
        <thead class="text-uppercase">
          <tr>
            <th
              scope="col"
              class="text-center"
              style="width: 10%;"
            >
              번호
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 13%;"
            >
              아이디
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 13%;"
            >
              이름
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 20%;"
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
              style="width: 12%;"
            />
            <th
              scope="col"
              class="text-center"
              style="width: 12%;"
            >
              상세보기
            </th>
          </tr>
        </thead>
        <template v-if="checkResDayOffList.length !== 0">
          <tbody
            v-for="(item, idx) in checkResDayOffList"
            :key="idx"
          >
            <tr style="height: 3.75rem;">
              <!-- 👉 Id -->
              <td class="text-center">
                {{ item.idx }}
              </td>

              <!-- 👉 lgnId -->
              <td
                v-if="req.position === 4"
                class="text-center"
              >
                {{ item.lgnId }}
              </td>

              <!-- 👉 lgnName -->
              <td

                class="text-center"
              >
                {{ item.lgnName }}
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
                <VBtn
                  variant="outlined"
                  color="success"
                  @click="dayOffApproval(item.idx)"
                >
                  승인
                </VBtn>
                <VBtn
                  variant="outlined"
                  style="margin-left: 24px;"
                  color="warning"
                  @click="dayOffRefuse(item.idx)"
                >
                  거절
                </VBtn>
              </td>

              <td class="text-center">
                <VBtn
                  icon
                  variant="text"
                  color="default"
                  size="x-small"
                  @click="detailClick(item.idx)"
                >
                  <VIcon
                    :size="22"
                    icon="tabler-eye"
                    variant="popout"
                  />
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
          v-model="checkReqModel.page"
          size="small"
          :total-visible="5"
          :length="checkTotalPage"
          @next="selectedRows = []"
          @prev="selectedRows = []"
        />
      </VCardText>
    </VCard>
  </template>

  <br>
  <VDivider />

  <VCard id="invoice-list">
    <VCardText class="d-flex align-center flex-wrap gap-4">
      <span
        v-if="req.position === 4"
        class="font-weight-bold"
      >연차 전체 리스트</span>
      <!-- !!!!!!! 시간 남으면 진행!!!!!!!!! -->
      <!-- 👉 Rows per page -->
      <!--
        <div class="d-flex align-center w-25">
        <div class="d-inline-block w-50">
        <AppDateTimePicker label="시작일"
        v-model="startDate"/>
        </div>
        <span class="ml-3 mr-3">~</span>
        <div class="d-inline-block w-50">
        <AppDateTimePicker label="종료일"
        v-model="endDate"/>
        </div>
        </div>
      -->
      <!--
        <div class="d-inline-block">
        <AppDateTimePicker
        v-model="startDate"
        label="시작일"
        />
        </div>
      -->
      <!--
        <div class="d-inline-block">
        <AppDateTimePicker
        v-model="endDate"
        label="종료일"
        />
        </div>
      -->

      <!--
        <div class="me-3">
        <VBtn>검색</VBtn>
        </div>
      -->

      <VSpacer />

      <div class="d-flex align-center flex-wrap gap-4">
        <!-- !!!!!!!시간 남으면 진행!!!!!!! -->
        <!-- 👉 Search  -->
        <!--
          <div class="invoice-list-filter">
          <span class="mr-3">3 / 15</span>
          <span class="font-weight-bold"> 잔여연차 : 7</span>
          </div>
        -->

        <!-- 👉 Select status -->
        <div class="invoice-list-filter">
          <VSelect
            v-model="selectedStatus"
            label="전체"
            single-line
            :items="searchOption.active"
          />
        </div>
      </div>
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
              style="width: 30%;"
            >
              사용 날짜
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 30%;"
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
              상세보기
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
              style="width: 10%;"
            >
              번호
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 13%;"
            >
              아이디
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 13%;"
            >
              이름
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 20%;"
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
              style="width: 12%;"
            >
              진행상황
            </th>
            <th
              scope="col"
              class="text-center"
              style="width: 12%;"
            >
              상세보기
            </th>
          </tr>
        </thead>
      </template>

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

            <!-- 👉 lgnId -->
            <td
              v-if="req.position === 4"
              class="text-center"
            >
              {{ item.lgnId }}
            </td>

            <!-- 👉 lgnName -->
            <td
              v-if="req.position === 4"
              class="text-center"
            >
              {{ item.lgnName }}
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
                icon
                variant="text"
                color="default"
                size="x-small"
                @click="detailClick(item.idx)"
              >
                <VIcon
                  :size="22"
                  icon="tabler-eye"
                  variant="popout"
                />
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

    <!-- 상세페이지 -->
    <template v-if="isDialogVisible === true && resDayOffDetail">
      <VDialog
        v-model="isDialogVisible"
        width="500"
      >
        <!-- Dialog close btn -->
        <DialogCloseBtn @click="isDialogVisible = !isDialogVisible" />

        <!-- Dialog Content -->
        <VCard title="연차사용내역 상세보기">
          <VCardText>
            <VTimeline
              side="end"
              align="start"
              truncate-line="both"
              density="compact"
              class="v-timeline-density-compact"
            >
              <VTimelineItem
                dot-color="primary"
                size="x-small"
              >
                <div class="d-flex justify-space-between">
                  <h6 class="text-base font-weight-semibold me-3">
                    {{ resDayOffDetail.startDt }}
                  </h6>
                  <span class="text-sm text-disabled">사용날짜</span>
                </div>

                <span class="mb-1">
                  {{ resDayOffDetail.content }} ({{ resDayOffDetail.category }})
                </span>
              </VTimelineItem>

              <VTimelineItem
                dot-color="info"
                size="x-small"
              >
                <div class="d-flex justify-space-between">
                  <span>
                    {{ resDayOffDetail.dayoffCount }}일
                  </span>
                  <span class="text-sm text-disabled">사용개수</span>
                </div>
              </VTimelineItem>

              <VTimelineItem
                dot-color="secondary"
                size="x-small"
              >
                <div class="d-flex justify-space-between">
                  <span v-if="resDayOffDetail.offCheck === '0'">
                    미확인
                  </span>
                  <span v-if="resDayOffDetail.offCheck === '1'">
                    확인
                  </span>
                  <span class="text-sm text-disabled">관리자 체크상황</span>
                </div>
              </VTimelineItem>

              <VTimelineItem
                dot-color="warning"
                size="x-small"
              >
                <div class="d-flex justify-space-between">
                  <span v-if="resDayOffDetail.active === 1">진행중</span>
                  <span v-if="resDayOffDetail.active === 2">승인</span>
                  <span v-if="resDayOffDetail.active === 3">거절</span>
                  <span v-if="resDayOffDetail.active === 0">취소</span>
                  <span class="text-sm text-disabled">신청상황</span>
                </div>
              </VTimelineItem>
            </VTimeline>
          </VCardText>
        </VCard>
      </VDialog>
    </template>

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
