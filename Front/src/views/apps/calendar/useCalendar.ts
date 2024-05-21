/* eslint-disable import/order */
import { useCalendarStore } from '@/views/apps/calendar/useCalendarStore'
import { useThemeConfig } from '@core/composable/useThemeConfig'
import dayGridPlugin from '@fullcalendar/daygrid'
import interactionPlugin from '@fullcalendar/interaction'
import listPlugin from '@fullcalendar/list'
import timeGridPlugin from '@fullcalendar/timegrid'
import type { CalendarApi, CalendarOptions, EventApi, EventSourceFunc } from '@fullcalendar/vue3'
import type { Ref } from 'vue'
import type { Event, NewEvent } from './types'

export const blankEvent = {
  // id: 0,
  title: '',
  start: '',
  end: '',
  allDay: true,
}

export const useCalendar = (event: Ref<Event | NewEvent>, isEventHandlerSidebarActive: Ref<boolean>, isLeftSidebarOpen: Ref<boolean>) => {
  // 👉 themeConfig
  const { isAppRtl } = useThemeConfig()

  // 👉 Store
  const store = useCalendarStore()

  // 👉 Calendar template ref
  const refCalendar = ref()

  // 👉 Calendar colors
  const calendarsColor = {
    Business: 'primary',
    Holiday: 'success',
    Personal: 'error',
    Family: 'warning',
    ETC: 'info',
  }

  // ℹ️ Extract event data from event API
  const extractEventDataFromEventApi = (eventApi: EventApi) => {
    // @ts-expect-error EventApi has extendProps type Dictionary (Record<string, any>) and we have fully typed extended props => Type conflict
    const {
      id,
      title,
      start,
      end,
      allDay,
      udtActive,

    }: Event = eventApi

    return {
      id,
      title,
      start,
      end,
      allDay,
      udtActive,

    }
  }

  const callbackTest = (e: Event) => {
    console.log(e)

    return {
      ...e,

      // Convert string representation of date to Date object
      start: new Date(e.start),
      end: new Date(e.end),
      allDay: true,
      title: e.title,
    }
  }

  // 👉 Fetch events
  const fetchEvents: EventSourceFunc = (info, successCallback) => {
  // If there's no info => Don't make useless API call
    if (!info)
      return

    store.fetchList()
      .then(r => {
        successCallback(r.data.list.map((e: Event) => (callbackTest(e))))
      })
      .catch(e => {
        console.error('Error occurred while fetching calendar events', e)
      })
  }

  // 👉 Calendar API
  const calendarApi = ref<null | CalendarApi>(null)

  // 👉 Update event in calendar [UI]
  const updateEventInCalendar = (updatedEventData: Event, propsToUpdate: (keyof Event)[], extendedPropsToUpdate: (keyof Event['extendedProps'])[]) => {
    const existingEvent = calendarApi.value?.getEventById(updatedEventData.id)

    if (!existingEvent) {
      console.warn('Can\'t found event in calendar to update')

      return
    }

    // ---Set event properties except date related
    // Docs: https://fullcalendar.io/docs/Event-setProp
    // dateRelatedProps => ['start', 'end', 'allDay']
    for (let index = 0; index < propsToUpdate.length; index++) {
      const propName = propsToUpdate[index]

      existingEvent.setProp(propName, updatedEventData[propName])
    }

    // --- Set date related props
    // ? Docs: https://fullcalendar.io/docs/Event-setDates
    existingEvent.setDates(updatedEventData.start, updatedEventData.end, { allDay: updatedEventData.allDay })

    // --- Set event's extendedProps
    // ? Docs: https://fullcalendar.io/docs/Event-setExtendedProp
    for (let index = 0; index < extendedPropsToUpdate.length; index++) {
      const propName = extendedPropsToUpdate[index]

      existingEvent.setExtendedProp(propName, updatedEventData.extendedProps[propName])
    }
  }

  // 👉 Remove event in calendar [UI]
  const removeEventInCalendar = (eventId: number) => {
    const _event = calendarApi.value?.getEventById(eventId)

    if (_event)
      _event.remove()
  }

  // 👉 refetch events
  const refetchEvents = () => {
    calendarApi.value?.refetchEvents()
  }

  watch(() => store.selectedCalendars, refetchEvents)

  // 👉 Add event
  const addEvent = (_event: NewEvent) => {
    store.addEvent(_event)
      .then(() => {
        refetchEvents()
      })
  }

  // 👉 Update event
  const updateEvent = (_event: Event) => {
    store.updateEvent(_event)
      .then(r => {
        const propsToUpdate = ['id', 'title'] as (keyof Event)[]
        const extendedPropsToUpdate = ['calendar', 'guests', 'location', 'description'] as (keyof Event['extendedProps'])[]

        updateEventInCalendar(r.data.event, propsToUpdate, extendedPropsToUpdate)
      })
  }

  // 👉 Remove event
  const removeEvent = (eventId: number) => {
    store.removeEvent(eventId).then(() => {
      removeEventInCalendar(eventId)
    })
  }

  // 👉 Calendar options
  const calendarOptions = {
    plugins: [dayGridPlugin, interactionPlugin, timeGridPlugin, listPlugin],
    initialView: 'dayGridMonth',
    headerToolbar: {
      start: 'drawerToggler,prev,next title',
      end: 'dayGridMonth,timeGridWeek,timeGridDay,listMonth',
    },
    events: fetchEvents,

    // ❗ We need this to be true because when its false and event is allDay event and end date is same as start data then Full calendar will set end to null
    forceEventDuration: true,

    /*
    Enable dragging and resizing event
    Docs: https://fullcalendar.io/docs/editable
  */
    editable: true,

    /*
    Enable resizing event from start
    Docs: https://fullcalendar.io/docs/eventResizableFromStart
  */
    eventResizableFromStart: true,

    /*
    Automatically scroll the scroll-containers during event drag-and-drop and date selecting
    Docs: https://fullcalendar.io/docs/dragScroll
  */
    dragScroll: true,

    /*
    Max number of events within a given day
    Docs: https://fullcalendar.io/docs/dayMaxEvents
  */
    dayMaxEvents: 2,

    /*
    Determines if day names and week names are clickable
    Docs: https://fullcalendar.io/docs/navLinks
  */
    navLinks: true,

    eventClassNames({ event: calendarEvent }) {
      const colorName = calendarsColor[calendarEvent._def.extendedProps.calendar as keyof typeof calendarsColor]

      return [
        // Background Color
        `bg-light-${colorName} text-${colorName}`,
      ]
    },

    eventClick({ event: clickedEvent }) {
      // * Only grab required field otherwise it goes in infinity loop
      // ! Always grab all fields rendered by form (even if it get `undefined`) otherwise due to Vue3/Composition API you might get: "object is not extensible"
      event.value = extractEventDataFromEventApi(clickedEvent)

      isEventHandlerSidebarActive.value = true
    },

    // customButtons
    dateClick(info) {
      event.value = { ...event.value, start: String(new Date(info.date)) }

      isEventHandlerSidebarActive.value = true
    },

    /*
      Handle event drop (Also include dragged event)
      Docs: https://fullcalendar.io/docs/eventDrop
      We can use `eventDragStop` but it doesn't return updated event so we have to use `eventDrop` which returns updated event
    */
    eventDrop({ event: droppedEvent }) {
      updateEvent(extractEventDataFromEventApi(droppedEvent))
    },

    /*
      Handle event resize
      Docs: https://fullcalendar.io/docs/eventResize
    */
    eventResize({ event: resizedEvent }) {
      if (resizedEvent.start && resizedEvent.end)
        updateEvent(extractEventDataFromEventApi(resizedEvent))
    },

    customButtons: {
      drawerToggler: {
        text: 'calendarDrawerToggler',
        click() {
          isLeftSidebarOpen.value = true
        },
      },
    },
  } as CalendarOptions

  // 👉 onMounted
  onMounted(() => {
    calendarApi.value = refCalendar.value.getApi()
  })

  // 👉 Jump to date on sidebar(inline) calendar change
  const jumpToDate = (currentDate: string) => {
    calendarApi.value?.gotoDate(new Date(currentDate))
  }

  watch(isAppRtl, val => {
    calendarApi.value?.setOption('direction', val ? 'rtl' : 'ltr')
  }, { immediate: true })

  return {
    refCalendar,
    calendarOptions,
    refetchEvents,
    fetchEvents,
    addEvent,
    updateEvent,
    removeEvent,
    jumpToDate,
  }
}
