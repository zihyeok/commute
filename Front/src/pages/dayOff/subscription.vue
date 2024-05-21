<!-- eslint-disable no-alert -->
<script lang="ts" setup>
import { getDayoffCreate } from '@/@api/dayOff/index'
import type { RequestDayoffModel } from '@/@api/dayOff/type'
import AppDateTimePicker from '@core/components/AppDateTimePicker.vue'

const router = useRouter()

const category = [
  { value: 1, label: '연차' },
  { value: 2, label: '반차' },
  { value: 3, label: '기타' },
]

const reqModel = ref<RequestDayoffModel>({
  idx: 0,
  userIdx: 0,
  startDt: '',
  category: 1,
  content: '',
  dayoffCount: 0,
})

// // 이전 날짜 막기
// const disabledDate = (date) => {
//     // 오늘 날
//     // const today = new Date()
//     const today = new Date().toISOString().substring(0, 10);
//     return date < today;
// };
// onMounted(() => {
//   const choiceDate = reqModel.value.startDt;
//   disabledDate(choiceDate);
// });

// 연차신청시
const dayoffCreate = async () => {
  const req: RequestDayoffModel = { ...reqModel.value }

  // 신청날짜 미입력시 return
  if (req.startDt === '') {
    alert('신청 날짜를 클릭해주세요.')

    return
  }

  // 사유 미입력시 return
  const content = req.content.trim()
  if (content.length === 0) {
    alert('사유를 입력해주세요')

    return
  }

  // session에서 userIdx 추출
  const loginUser = sessionStorage.getItem('loginUser')
  if (loginUser) {
    const userInfo = JSON.parse(loginUser)

    req.userIdx = userInfo.idx
  }
  else {
    console.error('loginUser 정보가 없습니다.')
  }

  try {
    const result = await getDayoffCreate(req)
    if (result.data.res !== 'OK') {
      alert(result.data.msg)

      return
    }
    alert(result.data.msg)
    router.push('/dayoff/dayofflist')
  }
  catch (e) {
    console.error(e)
  }
}
</script>

<template>
  <VCard id="invoice-list">
    <VForm
      class="pt-12 pb-10"
      style="padding-right: 64px;"
      @submit.prevent="dayoffCreate"
    >
      <VRow>
        <VCol cols="12">
          <VRow no-gutters>
            <!-- 👉 Email -->
            <VCol
              class="text-center"
              cols="12"
              md="3"
            >
              <label for="email">카테고리</label>
            </VCol>

            <VCol
              cols="12"
              md="9"
            >
              <VRadioGroup
                v-model="reqModel.category"
                inline
              >
                <!-- @change="categoryClick"> -->
                <div>
                  <VRadio
                    v-for="item in category"
                    :key="item.label"
                    :label="item.label"
                    :value="item.value"
                  />
                </div>
              </VRadioGroup>
            </VCol>
          </VRow>
        </VCol>

        <VCol cols="12">
          <VRow no-gutters>
            <!-- 👉 date -->
            <VCol
              class="text-center"
              cols="12"
              md="3"
            >
              <label for="date">신청날짜</label>
            </VCol>

            <VCol
              cols="12"
              md="9"
            >
              <AppDateTimePicker
                v-model="reqModel.startDt"
                label="신청날짜"
                :config="{ mode: 'multiple', dateFormat: 'Y-m-d' }"
              />
              <!-- 날짜 막기 -->
              <!-- :allowed-dates = 'disabledDate' -->
            </VCol>
          </VRow>
        </VCol>

        <VCol cols="12">
          <VRow no-gutters>
            <!-- 👉 content -->
            <VCol
              class="text-center"
              cols="12"
              md="3"
            >
              <label for="content">사유</label>
            </VCol>

            <VCol
              cols="12"
              md="9"
            >
              <VTextField
                id="content"
                v-model="reqModel.content"
                type="text"
              />
            </VCol>
          </VRow>
        </VCol>

        <!-- 👉 submit and reset button -->
        <VCol
          offset-md="3"
          cols="12"
          md="9"
          class="d-flex gap-4 text-center"
        >
          <VBtn
            type="submit"
            class="w-25"
            variant="outlined"
            color="#01579B"
          >
            신청
          </VBtn>
          <VBtn
            class="w-25"
            type="button"
            variant="outlined"
            color="#B0BEC5"
          >
            <RouterLink to="dayOffList">
              취소
            </RouterLink>
          </VBtn>
        </VCol>
      </VRow>
    </VForm>
  </VCard>
</template>
