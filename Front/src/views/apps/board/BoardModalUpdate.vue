<!-- eslint-disable vue/no-mutating-props -->
<script lang="ts" setup>
import { boardUpdate } from '@/@api/board/index';
import type { BoardUpdate, ResponseboardlistModel } from '@/@api/board/type';

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

const password = ref('')

const Update = async () => {
  const req: BoardUpdate = {
    idx: props.target.idx,
    title: props.target.title,
    content: props.target.content,
    boardPw: password.value,
  }

  if (req.title === '') {
    alert('제목을 작성해주세요.')

    return
  }

  if (req.content === '') {
    alert('내용를 작성해주세요.')

    return
  }

  try {
    const result = await boardUpdate(req)

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
      수정
      </VBtn>
      </template>
    -->

    <!-- Dialog close btn -->
    <DialogCloseBtn @click="$emit('update:isDialogVisible', false)" />

    <!-- Dialog Content -->
    <VCard title="게시글">
      <VCardText>
        <VForm @submit.prevent="Update">
          <VRow>
            <VCol
              cols="12"
              sm="6"
              md="4"
            >
              <VTextField
                v-model="$props.target.idx"
                label="번호"
                readonly
              />
            </VCol>

            <VCol
              cols="12"
              sm="6"
              md="4"
            >
              <VTextField
                v-model="$props.target.view"
                label="조회수"
                readonly
              />
            </VCol>

            <VCol cols="12">
              <VTextField
                v-model="$props.target.title"
                label="제목"
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
              <VTextField
                v-model="$props.target.regDt"
                label="생성일"
                readonly
              />
            </VCol>
            <VCol cols="12">
              <VTextField
                v-model="$props.target.udtDt"
                label="수정일"
                readonly
              />
            </VCol>

            <VCol
              cols="12"
            >
              <VTextarea
                v-model="$props.target.content"
                label="내용"
              />
            </VCol>

            <VCol cols="12">
              <VBtn
                type="submit"
                class="me-3"
              >
                수정
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
