import { configureStore } from "@reduxjs/toolkit"

import authReducer from './Slices/AuthSlice'
import deckReducer from './Slices/DeckSlice'


export const store = configureStore({
    reducer: {
        auth: authReducer,
        deck: deckReducer
    }
})

export type RootState = ReturnType<typeof store.getState>

export type AppDispatch = typeof store.dispatch
