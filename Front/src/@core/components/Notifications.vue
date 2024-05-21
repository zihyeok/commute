<script lang="ts" setup>
import type { Notification } from '@layouts/types'
import type { Anchor } from 'vuetify/lib/components'

interface Props {
  notifications: Notification[]
  notificationsOriginal: Notification[]
  badgeProps?: unknown
  location?: Anchor
}

const props = withDefaults(defineProps<Props>(), {
  location: 'bottom end',
  badgeProps: undefined,
})

defineEmits<{
  (e: 'click:readAllNotifications'): void
}>()

const router = useRouter()

const checkGo = () => {
  router.push('/dayoff/dayofflist')

  // location.reload()
}
</script>

<template>
  <VBadge
    :model-value="!!props.badgeProps"
  >
    <VBtn
      icon
      variant="text"
      color="default"
      size="small"
    >
      <VBadge
        :model-value="!!props.notifications.length"
        color="error"
        :content="props.notificationsOriginal.length"
      >
        <VIcon
          icon="tabler-bell"
          size="24"
        />
      </VBadge>

      <VMenu
        activator="parent"
        width="380px"
        offset="14px"
      >
        <VList class="py-0">
          <!-- 👉 Header -->
          <VListItem
            title="알림"
            class="notification-section"
            height="48px"
          >
            <template #append>
              <VChip
                v-if="props.notifications.length"
                color="primary"
                size="small"
              >
                {{ props.notificationsOriginal.length }} New
              </VChip>
            </template>
          </VListItem>

          <VDivider />

          <!-- 👉 Notifications list -->
          <template
            v-for="notification in props.notifications"
            :key="notification.idx"
          >
            <VListItem
              :subtitle="notification.subtitle"
              link
              lines="one"
              min-height="66px"
              @click="checkGo"
            >
              <!-- Slot: Prepend -->
              <template #prepend>
                <VListItemAction start>
                  <VAvatar
                    color="primary"
                    size="13"
                    variant="tonal"
                  />
                </VListItemAction>
              </template>
              <!-- Slot: Append -->
            </VListItem>
            <VDivider />
          </template>
          <template v-if="notifications.length === 0">
            <div
              class="text-center mt-5"
              style="height: 2.3rem;"
            >
              <span style="color: #bdbdbd;">알람 내역이 없습니다.</span>
            </div>
          </template>
          <template v-if="notificationsOriginal.length > 5">
            <div
              class="text-center"
              style="height: 2.3rem;"
            >
              <span style="color: #bdbdbd;">...</span>
            </div>
          </template>
        </VList>
      </VMenu>
    </VBtn>
  </VBadge>
</template>

<style lang="scss">
.notification-section {
  padding: 14px !important;
}
</style>
