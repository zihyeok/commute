<!-- eslint-disable no-alert -->
<script setup lang="ts">
import { getDayOffDetail, getDayoffCancel, getDayoffCreate } from '@/@api/dayOff/index'
import type { RequestDayoffModel } from '@/@api/dayOff/type'

const route = useRoute()
const router = useRouter()

const category = [
  { value: 1, label: '연차' },
  { value: 2, label: '반차' },
  { value: 3, label: '기타' },
]

const reqModel = reactive<RequestDayoffModel>({
  idx: 0,
  userIdx: 0,
  startDt: '',
  category: 1,
  content: '',
  dayoffCount: 0,
})

const req: RequestDayoffModel = reqModel

// 상세페이지
const dayOffDetail = async (idx: number) => {
  try {
    const result = await getDayOffDetail(idx)

    req.idx = result.data.list.idx
    req.userIdx = result.data.list.userIdx
    req.category = result.data.list.category
    req.startDt = result.data.list.startDt
    req.content = result.data.list.content
  }
  catch (e) {
    console.error(e)
  }
}

// 연차수정시
const dayoffCreate = async () => {
  // 사유 미입력시 return
  const content = req.content.trim()
  if (content.length === 0) {
    // eslint-disable-next-line no-alert
    alert('사유를 입력해주세요')

    return
  }
  try {
    const result = await getDayoffCreate(req)
    if (result.data.res !== 'OK') {
      // eslint-disable-next-line no-alert
      alert(result.data.msg)

      return
    }
    // eslint-disable-next-line no-alert
    alert(result.data.msg)

    // location.reload()
    router.push('/dayoff/dayofflist')
  }
  catch (e) {
    console.error(e)
  }
}

// 연차취소
const dayOffCancel = async () => {
  try {
    const result = await getDayoffCancel(req.idx)
    if (result.data.res !== 'OK') {
      alert(result.data.msg)

      return
    }
    alert(result.data.msg)
    router.push('/dayoff/update')
  }
  catch (e) {
    console.error(e)
  }
}

onMounted(() => {
  dayOffDetail(Number(route.params.idx))
})
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
                v-model="req.category"
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
                v-model="req.startDt"
                label="신청날짜"
                :config="{ mode: 'multiple', dateFormat: 'Y-m-d' }"
              />
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
                v-model="req.content"
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
            수정
          </VBtn>
          <VBtn
            class="w-25"
            type="reset"
            variant="outlined"
            color="#B0BEC5"
            @click="dayOffCancel"
          >
            연차 사용 취소
          </VBtn>
        </VCol>
      </VRow>
    </VForm>
  </VCard>
</template>
