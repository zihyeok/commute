<script setup lang="ts">
import { PostBoardCreate } from '@/@api/board/index';
import { getAuthPosition } from '@/libs/auth';

const props = defineProps<Props>()
const emit = defineEmits<Emit>()

interface Props {
  isDrawerOpen: boolean
}

interface Emit {
  (e: 'update:isDrawerOpen', value: boolean): void
  (e: 'boardList'): void
}

const reqModel = ref<any>({
  title: '',
  content: '',
  boardPw: '',
  category: '',
  state: '',
  view: 1,
})

const UserRole = getAuthPosition()

// session
// const adminPosition = ref(1)
// if (loginUser) {
//   // const userInfo = JSON.parse(loginUser)

//   adminPosition = loginUser
// }

const req: any = reqModel.value

const BoardCreate = async () => {
  // 제목 미입력시 return
  if (req.title === '') {
    alert('제목을 작성해주세요.')

    return
  }
  else if (req.title.length > 20) { // 20자로 가정
    alert('제목은 20자를 넘을 수 없습니다.')

    return
  }

  if (req.category === '') {
    alert('카테고리를 선택해주세요.')

    return
  }

  if (req.state === '') {
    alert('상태를 선택해주세요.')

    return
  }

  if (req.content === '') {
    alert('글을 작성해주세요.')

    return
  }

  try {
    const result = await PostBoardCreate(req)
    if (result.data.res !== 'OK') {
      // eslint-disable-next-line no-alert
      alert(result.data.msg)

      return
    }
    // eslint-disable-next-line no-alert
    alert(result.data.msg)
    for (const key in reqModel.value)
      reqModel.value[key] = ''
    emit('boardList')
    emit('update:isDrawerOpen', false)
  }
  catch (e) {
    console.error(e)
  }
}

const options = {
  admin: [
    { title: '게시판', value: 0 },
    { title: '공지사항', value: 1 },
  ],
  user: [
    { title: '게시판', value: 0 },
  ],
}

const states = [
  { title: '정상', value: 0 },
]

// onMounted(async () => {
//   await get_position()
// })
</script>

<template>
  <VNavigationDrawer
    temporary
    location="end"
    :width="400"
    :model-value="props.isDrawerOpen"
    class="scrollable-content"
  >
    <!-- 👉 Header -->
    <div class="d-flex align-center pa-6 pb-1">
      <h6 class="text-h6">
        게시판 등록
      </h6>

      <VSpacer />

      <VBtn
        icon
        size="32"
        color="default"
        variant="tonal"
        class="rounded"
      >
        <VIcon
          size="18"
          icon="tabler-x"
          @click="$emit('update:isDrawerOpen', false)"
        />
      </VBtn>
    </div>

    <PerfectScrollbar :options="{ wheelPropagation: false }">
      <VCard flat>
        <VCardText>
          <VForm @submit.prevent="BoardCreate">
            <VRow>
              <VCol cols="12">
                <VTextField
                  v-model="reqModel.title"
                  label="제목"
                />
              </VCol>

              <VCol cols="12">
                <VTextField
                  v-model="reqModel.boardPw"
                  label="비밀번호"
                  type="password"
                />
              </VCol>

              <VCol cols="12">
                <VSelect
                  v-model="reqModel.category"
                  label="카테고리"
                  :items="UserRole === '4' ? options.admin : options.user"
                />
              </VCol>

              <VCol cols="12">
                <VSelect
                  v-model="reqModel.state"
                  label="상태"
                  :items=" states"
                />
              </VCol>

              <VCol
                cols="12"
              >
                <VTextarea
                  v-model="reqModel.content"
                  label="내용"
                />
              </VCol>

              <VCol cols="12">
                <VBtn
                  type="submit"
                  class="me-3"
                >
                  등록
                </VBtn>

                <VBtn
                  type="reset"
                  color="secondary"
                  variant="tonal"
                  @click="$emit('update:isDrawerOpen', false)"
                >
                  닫기
                </VBtn>
              </VCol>
            </VRow>
          </VForm>
        </VCardText>
      </VCard>
    </PerfectScrollbar>
  </VNavigationDrawer>
</template>
