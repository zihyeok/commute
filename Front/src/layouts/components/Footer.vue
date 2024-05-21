<template>
  <div class="h-100 d-flex align-center justify-space-between">
    <!-- 👉 Footer: left content -->
    <span class="d-flex align-center">
      &copy;
      {{ new Date().getFullYear() }}
      With
      <VIcon
        icon="tabler-heart"
        color="error"
        size="1.25rem"
        class="mx-1"
      />
      Acorns
    </span>
    <!-- 👉 Footer: right content -->
    ☎️
    &nbsp;
    02-336-4568
  </div>
</template>
