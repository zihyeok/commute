<script lang="ts" setup>
import { boardDelete } from '@/@api/board/index'
import type { BoardDelete, ResponseboardlistModel } from '@/@api/board/type'

const props = defineProps<Props>()
const emit = defineEmits<Emit>()

interface Props {
  isDialogVisible: boolean
  target: ResponseboardlistModel
}
interface Emit {
  (e: 'update:isDialogVisible', value: boolean): void
  (e: 'target', value: ResponseboardlistModel): void
  (e: 'boardList'): void
}

// console.log(props.target.idx, '=======')

const password = ref('')

const Delete = async () => {
  const req: BoardDelete = {
    idx: props.target.idx,
    boardPw: password.value,
    state: 1,
  }

  // session에서 userIdx 추출
  // const loginUser = sessionStorage.getItem('loginUser')

  try {
    const result = await boardDelete(req)

    if (result.data.res !== 'OK') {
      // eslint-disable-next-line no-alert
      alert(result.data.msg)

      return
    }
    // eslint-disable-next-line no-alert
    alert(result.data.msg)
    password.value = ''
    emit('boardList')
    emit('update:isDialogVisible', false)
  }
  catch (e) {
    console.error(e)
  }
}
</script>

<template>
  <VDialog
    v-model="$props.isDialogVisible"
    max-width="600"
  >
    <!-- Dialog Activator -->
    <!--
      <template #activator="{ props }">
      <VBtn v-bind="props">
      삭제
      </VBtn>
      </template>
    -->

    <!-- Dialog close btn -->
    <DialogCloseBtn @click="$emit('update:isDialogVisible', false)" />

    <!-- Dialog Content -->
    <VCard title="삭제">
      <VCardText>
        <VForm @submit.prevent="Delete">
          <VRow>
            <VCol
              cols="12"
              sm="6"
              md="4"
            >
              <VTextField
                v-model="$props.target.idx"
                label="게시글 번호"
                readonly
              />
            </VCol>

            <VCol cols="12">
              <VTextField
                v-model="password"
                label="비밀번호"
                type="password"
              />
            </VCol>

            <VCol cols="12">
              <VBtn
                type="submit"
                class="me-3"
              >
                삭제
              </VBtn>

              <VBtn
                type="reset"
                color="secondary"
                variant="tonal"
                @click="$emit('update:isDialogVisible', false)"
              >
                닫기
              </VBtn>
            </VCol>
          </VRow>
        </VForm>
      </VCardText>
    </VCard>
  </VDialog>
</template>
