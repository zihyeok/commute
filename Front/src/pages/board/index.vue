<!-- eslint-disable import/order -->
<!-- eslint-disable no-alert -->
<script setup lang="ts">
import { getBoardList } from '@/@api/board/index'
import type { ResponseboardlistModel, pageModel } from '@/@api/board/type'
import { getAuthPosition } from '@/libs/auth'
import BoardModal from '@/views/apps/board/BoardModal.vue'
import BoardModalUpdate from '@/views/apps/board/BoardModalUpdate.vue'
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import BoardAdd from './BoardAdd.vue'

const router = useRouter()
const totalPage = ref(1)
const isRedText = ref(false)

// board 타입
const boards = ref<ResponseboardlistModel[]>([])

// const selectedRows = ref<string[]>([])

const reqModel = ref<pageModel>({
  size: 3,
  page: 1,
})

const paging = ref({
  page: 1,
  total: 0,
  count: 0,
})

const get_position = () => {
  try {
    return getAuthPosition()
  }
  catch (e) {
    console.error(e)
  }
}

const boardList = async () => {
  const req: pageModel = { ...reqModel.value }

  req.page = req.page - 1

  // req.size = req.size - 1
  try {
    const result = await getBoardList(req)

    boards.value = result.data.list.content

    // console.log('+++++++++++', result.data.list)

    paging.value.total = result.data.list.totalPages
    paging.value.page = result.data.list.number + 1

    if (result.data.res !== 'OK') {
      // eslint-disable-next-line no-alert
      alert(result.data.msg)
      router.push('/')

      return
    }
  }
  catch (e) {
    console.error(e)
  }
}

const target = ref({})

const edit = item => {
  // eslint-disable-next-line @typescript-eslint/no-use-before-define
  isBoardUpdateVisible.value = true
  item.view += 1
  target.value = { ...item }
}

const editdelete = items => {
  // eslint-disable-next-line @typescript-eslint/no-use-before-define
  isBoardVisible.value = true
  target.value = { ...items }
}

onMounted(async () => {
  await get_position()
  await boardList()
})

const isAddNewUserDrawerVisible = ref(false)
const isBoardVisible = ref(false)
const isBoardUpdateVisible = ref(false)

const nextPage = async () => {
  if (paging.value.page < paging.value.total) {
    reqModel.value.page += 1
    await boardList()
  }
}

const prevPage = async () => {
  if (paging.value.page > 1) {
    reqModel.value.page -= 1
    await boardList()
  }
}

const setRedText = (value: boolean) => {
  isRedText.value = value
}
</script>

<template>
  <BoardModalUpdate
    v-model:isDialogVisible="isBoardUpdateVisible"
    :target="target"
    @boardList="boardList"
  />
  <section>
    <VRow>
      <VCol cols="12">
        <VCard
          title="공지사항"
        >
          <!-- 👉 Filters -->

          <VDivider />

          <VTable class="text-no-wrap">
            <!-- 👉 table head -->
            <thead>
              <tr>
                <th scope="col">
                  제목
                </th>
                <th scope="col">
                  내용
                </th>
                <th scope="col">
                  조회수
                </th>
                <th scope="col">
                  등록일
                </th>
                <th scope="col">
                  수정일
                </th>
                <th scope="col">
                  설정
                </th>
              </tr>
            </thead>
            <!-- 👉 table body -->
            <tbody v-if="boards.length !== 0">
              <template
                v-for="(board, id) in boards"
                :key="id"
              >
                <tr
                  v-if="board.category === 1"
                  style="height: 3.75rem;"
                >
                  <!-- 👉 idx -->

                  <!-- 👉 title 글자수 제한 -->
                  <!-- :class="{ 'red-text': board.category === 1 }" -->
                  <td>
                    {{ board.title.slice(0, 15) }}
                  </td>

                  <!-- 👉 content 글자수 제한 -->
                  <td
                    :class="{ 'orange-text': isRedText }"
                    @mouseover="setRedText(true)"
                    @mouseleave="setRedText(false)"
                    @click="edit(board)"
                  >
                    {{ board.content.slice(0, 20) }}
                  </td>

                  <!-- 👉 view -->
                  <td>
                    {{ board.view }}
                  </td>

                  <!-- 👉 regDt  date 포맷 -->
                  <td>
                    {{ board.regDt }}
                  </td>

                  <!-- 👉 udtDt date 포맷 -->
                  <td>
                    {{ board.udtDt }}
                  </td>
                  <!-- 👉 설정 -->
                  <td
                    class="text-center"
                    style="width: 5rem;"
                  >
                    <VBtn
                      icon
                      size="x-small"
                      color="default"
                      variant="text"
                    >
                      <VIcon
                        size="22"
                        icon="tabler-dots-vertical"
                      />

                      <VMenu activator="parent">
                        <VList>
                          <!--
                            <VListItem
                            title="수정"
                            @click="edit(board)"
                            />
                          -->

                          <VListItem
                            title="삭제"
                            @click="editdelete(board)"
                          />
                        </VList>
                      </VMenu>
                    </VBtn>
                  </td>
                </tr>
              </template>
            </tbody>

            <!-- 👉 table footer  -->
          </VTable>

          <VDivider />
          <!-- SECTION Pagination -->
          <VCardText class="d-flex align-center flex-wrap justify-space-between gap-4 py-3 px-5">
            <!-- 👉 Pagination meta -->
            <span class="text-sm text-disabled" />

            <!-- 👉 Pagination -->
            <!--
              <VPagination
              v-model="reqModel.number"
              size="small"
              :total-visible="5"
              :length="totalPage"
              @next="selectedRows = []"
              @prev="selectedRows = []"
              />
            -->
          </VCardText>
        </VCard>
      </VCol>
    </VRow>
  </section>
  <section>
    <VRow>
      <VCol cols="12">
        <VCard title="게시판">
          <!-- 👉 Filters -->

          <VCardText class="d-flex flex-wrap py-4 gap-4">
            <div class="app-user-search-filter d-flex align-center flex-wrap gap-4">
              <!-- 👉 Search  -->

              <!-- 👉 Add user button -->
              <VBtn
                block
                prepend-icon="tabler-plus"
                @click="isAddNewUserDrawerVisible = true"
              >
                게시글 등록
              </VBtn>
            </div>
          </VCardText>

          <VDivider />

          <VTable class="text-no-wrap">
            <!-- 👉 table head -->
            <thead>
              <tr>
                <th scope="col">
                  제목
                </th>
                <th scope="col">
                  내용
                </th>
                <th scope="col">
                  조회수
                </th>
                <th scope="col">
                  등록일
                </th>
                <th scope="col">
                  수정일
                </th>
                <th scope="col">
                  설정
                </th>
              </tr>
            </thead>
            <!-- 👉 table body -->
            <tbody v-if="boards.length !== 0">
              <template
                v-for="(board, id) in boards"
                :key="id"
              >
                <tr
                  v-if="board.category === 0"
                  style="height: 3.75rem;"
                >
                  <!-- 👉 idx -->

                  <!-- 👉 title 글자수 제한 -->
                  <td>
                    {{ board.title.slice(0, 15) }}
                  </td>

                  <!-- 👉 content 글자수 제한 -->
                  <td
                    :class="{ 'orange-text': isRedText }"
                    @mouseover="setRedText(true)"
                    @mouseleave="setRedText(false)"
                    @click="edit(board)"
                  >
                    {{ board.content.slice(0, 20) }}
                  </td>

                  <!-- 👉 view -->
                  <td>
                    {{ board.view }}
                  </td>

                  <!-- 👉 regDt  date 포맷 -->
                  <td>
                    {{ board.regDt }}
                  </td>

                  <!-- 👉 udtDt date 포맷 -->
                  <td>
                    {{ board.udtDt }}
                  </td>
                  <!-- 👉 설정 -->
                  <td
                    class="text-center"
                    style="width: 5rem;"
                  >
                    <VBtn
                      icon
                      size="x-small"
                      color="default"
                      variant="text"
                    >
                      <VIcon
                        size="22"
                        icon="tabler-dots-vertical"
                      />

                      <VMenu activator="parent">
                        <VList>
                          <!--
                            <VListItem
                            title="수정"
                            @click="edit(board)"
                            />
                          -->

                          <VListItem
                            title="삭제"
                            @click="editdelete(board)"
                          />
                        </VList>
                      </VMenu>
                    </VBtn>
                  </td>
                </tr>
              </template>
            </tbody>

            <!-- 👉 table footer  -->
          </VTable>

          <VDivider />
          <!-- SECTION Pagination -->
          <VCardText class="d-flex align-center flex-wrap justify-space-between gap-4 py-3 px-5">
            <!-- 👉 Pagination meta -->
            <span class="text-sm text-disabled" />

            <!-- 👉 Pagination -->

            <VPagination
              v-model="paging.page"
              size="small"
              :length="paging.total"
              @next="nextPage"
              @prev="prevPage"
            />
          </VCardText>
        </VCard>
      </VCol>
    </VRow>

    <!-- 👉 Add New Board -->
    <BoardAdd
      v-model:isDrawerOpen="isAddNewUserDrawerVisible"
      @boardList="boardList"
    />
    <BoardModal
      v-model:isDialogVisible="isBoardVisible"
      :target="target"
      @boardList="boardList"
    />
  </section>
</template>

<style lang="scss">
#invoice-list {
  .invoice-list-actions {
    inline-size: 8rem;
  }

  .invoice-list-filter {
    inline-size: 12rem;
  }
}

.red-text {
  color: red;
}

.highlighted {
  background-color: purple;
}

.orange-text:hover {
  color: orange;
}
</style>
