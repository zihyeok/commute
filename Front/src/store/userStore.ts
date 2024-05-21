import { defineStore } from 'pinia'

export const userStore = defineStore('user', {
  state: () => ({ role: 1 }),
  actions: {
    setRole(role: number) {
      // console.log('권한담을거야~1')
      this.role = role
    },
  },

})
