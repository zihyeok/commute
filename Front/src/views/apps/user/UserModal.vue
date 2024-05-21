<script lang="ts" setup>
import { PostPosisionUpdate } from '@/@api/user';
import type { UserListModel, UserPositionModel } from '@/@api/user/type';

const props = defineProps<Props>()
const emit = defineEmits<Emit>()

interface Props {
  isDialogVisible: boolean
  target: UserListModel
}
interface Emit {
  (e: 'update:isDialogVisible', value: boolean): void
  (e: 'target', value: UserListModel): void
  (e: 'userList'): void
}

// console.log(props.target.idx, '=======')

const positions = ref<number>(1)

const Update = async () => {
  const req: UserPositionModel = {
    idx: props.target.idx,
    position: positions.value,
  }

  try {
    const result = await PostPosisionUpdate(req)

    if (result.data.res !== 'OK') {
      // eslint-disable-next-line no-alert
      alert(result.data.msg)

      return
    }
    // eslint-disable-next-line no-alert
    alert(result.data.msg)
    emit('userList')
    emit('update:isDialogVisible', false)
  }
  catch (e) {
    console.error(e)
  }
}

const states = [
  { title: '신입', value: 1 },
  { title: '사원', value: 2 },
  { title: '팀장', value: 3 },
]
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
    <VCard title="직급수정">
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
                label="사번"
                readonly
              />
            </VCol>

            <VCol cols="12">
              <VSelect
                v-model="positions"
                label="직급"
                :items="states"
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
