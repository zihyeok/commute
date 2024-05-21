<script setup lang="ts">
// eslint-disable-next-line import/order
import { getAuthPosition } from '@/libs/auth'
import { requiredValidator } from '@validators'
import type { Options } from 'flatpickr'
import { PerfectScrollbar } from 'vue3-perfect-scrollbar'
import { VForm } from 'vuetify/components'
import type { Event, NewEvent } from './types'
import { useCalendarStore } from './useCalendarStore'

const props = defineProps<Props>()

const emit = defineEmits<{
  (e: 'update:isDrawerOpen', val: boolean): void
  (e: 'addEvent', val: NewEvent): void
  (e: 'updateEvent', val: Event): void
  (e: 'removeEvent', eventId: string): void

  // (e: 'schedule', val: scheduleUpload): void
}>()

interface Props {
  isDrawerOpen: boolean
  event: (Event | NewEvent)
}

// 👉 store
const store = useCalendarStore()
const refForm = ref<VForm>()
const router = useRouter()

const UserRole = getAuthPosition()

// 👉 Event
const event = ref<Event | NewEvent>(JSON.parse(JSON.stringify(props.event)))

const resetEvent = () => {
  event.value = JSON.parse(JSON.stringify(props.event))
  nextTick(() => {
    refForm.value?.resetValidation()
  })
}

watch(() => props.isDrawerOpen, resetEvent)

// 삭제
const removeEvent = () => {
  emit('removeEvent', event.value.id)

  // Close drawer
  emit('update:isDrawerOpen', false)
}

const handleSubmit = async () => {
  refForm.value?.validate()
    .then(({ valid }) => {
      if (valid) {
        // If id exist on id => Update event
        if ('id' in event.value) {
          if (event.value.title.length > 20) {
            alert('제목은 20자를 넘을 수 없습니다.')

            return
          }
          const modifiedEvent = { ...event.value }

          // const formattedStartDate = format(new Date(event.value.start), 'yyyy-MM-dd')
          // const formattedEndDate = format(new Date(event.value.end), 'yyyy-MM-dd')

          // modifiedEvent.start = formattedStartDate
          // modifiedEvent.end = formattedEndDate

          emit('updateEvent', modifiedEvent)
        }

        // Else => add new event
        else {
          if (event.value.title.length > 20) {
            alert('제목은 20자를 초과할 수 없습니다.')

            return
          }
          emit('addEvent', event.value)
        }

        location.reload()

        // Close drawer
        emit('update:isDrawerOpen', false)
      }
    })
}

// const guestsOptions = [
//   { avatar: avatar1, name: 'Jane Foster' },
//   { avatar: avatar3, name: 'Donna Frank' },
//   { avatar: avatar5, name: 'Gabrielle Robertson' },
//   { avatar: avatar7, name: 'Lori Spears' },
//   { avatar: avatar6, name: 'Sandy Vega' },
//   { avatar: avatar2, name: 'Cheryl May' },
// ]

// 👉 Form

const onCancel = () => {
  emit('update:isDrawerOpen', false)

  nextTick(() => {
    refForm.value?.reset()
    resetEvent()
    refForm.value?.resetValidation()
  })
}

const startDateTimePickerConfig = computed(() => {
  const config: Options = { enableTime: true, dateFormat: 'Y-m-d' }

  if (event.value.end)
    config.maxDate = event.value.end

  return config
})

const endDateTimePickerConfig = computed(() => {
  const config: Options = { enableTime: true, dateFormat: 'Y-m-d' }

  if (event.value.start)
    config.minDate = event.value.start

  return config
})

const dialogModelValueUpdate = (val: boolean) => {
  emit('update:isDrawerOpen', val)
}

// onMounted(async () => {
//   await userCheck()
// })
</script>

<template>
  <VNavigationDrawer
    temporary
    location="end"
    :model-value="props.isDrawerOpen"
    width="420"
    class="scrollable-content"
    @update:model-value="dialogModelValueUpdate"
  >
    <!-- 👉 Header -->
    <div class="d-flex align-center pa-6 pb-1">
      <h6 class="text-h6">
        {{ event.id ? '수정' : '등록' }}
      </h6>

      <VSpacer />

      <VBtn
        v-if="UserRole === '4'"
        v-show="event.id"
        icon
        variant="tonal"
        size="32"
        class="rounded me-3"
        color="default"
        @click="removeEvent"
      >
        <VIcon
          size="18"
          icon="tabler-trash"
        />
      </VBtn>

      <VBtn
        variant="tonal"
        color="default"
        icon
        size="32"
        class="rounded"
        @click="$emit('update:isDrawerOpen', false)"
      >
        <VIcon
          size="18"
          icon="tabler-x"
        />
      </VBTn>
    </div>

    <PerfectScrollbar :options="{ wheelPropagation: false }">
      <VCard flat>
        <VCardText>
          <!-- SECTION Form -->
          <VForm
            ref="refForm"
            @submit.prevent="handleSubmit"
          >
            <VRow>
              <!-- 👉 content -->
              <VCol cols="12">
                <VTextField
                  v-model="event.title"
                  label="내용"
                  :rules="[requiredValidator]"
                />
              </VCol>

              <!-- 👉 Calendar -->
              <!--
                <VCol cols="12">
                <VSelect
                v-model="event.extendedProps.calendar"
                label="선택"
                :rules="[requiredValidator]"
                :items="store.availableCalendars"
                :item-title="item => item.label"
                :item-value="item => item.label"
                >
                <template #selection="{ item }">
                <div
                v-show="event.extendedProps.calendar"
                class="align-center"
                :class="event.extendedProps.calendar ? 'd-flex' : ''"
                >
                <VBadge
                :color="item.raw.color"
                inline
                dot
                class="pa-1"
                />
                <span>{{ item.raw.label }}</span>
                </div>
                </template>
                </VSelect>
                </VCol>
              -->

              <!-- 👉 Start date -->
              <VCol cols="12">
                <AppDateTimePicker
                  :key="JSON.stringify(startDateTimePickerConfig)"
                  v-model="event.start"
                  :rules="[requiredValidator]"
                  label="시작일"
                  :config="startDateTimePickerConfig"
                />
              </VCol>

              <!-- 👉 End date -->
              <VCol cols="12">
                <AppDateTimePicker
                  :key="JSON.stringify(endDateTimePickerConfig)"
                  v-model="event.end"
                  :rules="[requiredValidator]"
                  label="종료일"
                  :config="endDateTimePickerConfig"
                />
              </VCol>

              <!-- 👉 Form buttons -->
              <VCol cols="12">
                <VBtn
                  v-if="UserRole === '4'"
                  type="submit"
                  class="me-3"
                >
                  업로드
                </VBtn>
                <VBtn
                  variant="tonal"
                  color="secondary"
                  @click="onCancel"
                >
                  닫기
                </VBtn>
              </VCol>
            </VRow>
          </VForm>
        <!-- !SECTION -->
        </VCardText>
      </VCard>
    </PerfectScrollbar>
  </VNavigationDrawer>
</template>
