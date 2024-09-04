import { PayloadAction, createAsyncThunk, createSlice, isPending, isRejected } from "@reduxjs/toolkit"
import { CreateDeckPayload, DeckDetailsDTO, DeckSummaryDTO, initialDeckState, mapApplicationUserWithDecks } from "../../types/deckTypes"
import axios, { AxiosError } from "axios"
import { Card } from "../../types/cardTypes"

const URL = import.meta.env.VITE_DONCARDS_BACKEND_URL

export const createDeck = createAsyncThunk(
    'decks',
    async ({ body, token }: { body: CreateDeckPayload, token: string }, thunkAPI) => {
        try {
            const response = await axios.post(`${URL}/decks`, body, {
                headers: {
                    'Authorization': `Bearer ${token} `
                }
            })
            return response.data
        } catch (e) {
            return thunkAPI.rejectWithValue((e as AxiosError).response?.data)
        }
    }
)

export const fetchDecksForHome = createAsyncThunk(
    'decks?page',
    async (page: number, thunkAPI) => {
        try {
            console.log('URL', URL)
            const response = await axios.get(`${URL}/decks?page=${page}`)
            return response.data
        } catch (e) {
            return thunkAPI.rejectWithValue((e as AxiosError).response?.data)
        }
    }
)

export const fetchApplicationUserWithDecksByUserId = createAsyncThunk(
    'users/userId/with-decks',
    async (userId: number, thunkAPI) => {
        try {
            const response = await axios.get(`${URL}/users/${userId}/with-decks`)
            return response.data
        } catch (e) {
            return thunkAPI.rejectWithValue((e as AxiosError).response?.data)
        }
    }
)

export const fetchNextDecksByUserId = createAsyncThunk(
    'users/userId/decks',
    async ({ userId, page }: { userId: number, page: number }, thunkAPI) => {
        try {
            const response = await axios.get(`${URL}/users/${userId}/decks?page=${page}`)
            return response.data
        } catch (e) {
            return thunkAPI.rejectWithValue((e as AxiosError).response?.data)
        }
    }
)

export const fetchDeckByDeckId = createAsyncThunk(
    'decks/{deckId}',
    async (deckId: number, thunkAPI) => {
        try {
            const response = await axios.get(`${URL}/decks/${deckId}`)
            return response.data
        } catch (e) {
            return thunkAPI.rejectWithValue((e as AxiosError).response?.data)
        }
    }
)

export const DeckSlice = createSlice({
    name: 'deck',
    initialState: initialDeckState,
    reducers: {
        increaseHomePage(state) {
            return {
                ...state,
                homeDecksPage: state.homeDecksPage + 1
            }
        },
        increaseProfilePage(state) {
            return {
                ...state,
                profileDecksPage: state.profileDecksPage + 1
            }
        },
        updateLearningDeck(state, action: PayloadAction<DeckDetailsDTO | DeckSummaryDTO>) {
            return {
                ...state,
                learningDeck: action.payload
            }
        }
    },
    extraReducers: (builder) => {
        builder.addCase(createDeck.fulfilled, (state, action) => {
            state.error = null
            state.loading = false
            state.createdDeck = {
                deckId: action.payload.deckId,
                name: action.payload.name,
                description: action.payload.description,
                createdAt: action.payload.createdAt,
                version: action.payload.version,
                cards: action.payload.cards.map((card: Card) => ({
                    cardId: card.cardId,
                    term: card.term,
                    translation: card.translation
                })),
            }
        })
        builder.addCase(fetchDecksForHome.fulfilled, (state, action) => {
            state.error = null
            state.loading = false
            console.log('action.payload.content', action.payload.content)
            state.homeDecks = [...state.homeDecks, ...action.payload.content]
        })

        builder.addCase(fetchApplicationUserWithDecksByUserId.fulfilled, (state, action) => {
            state.error = null
            state.loading = false
            state.profile = mapApplicationUserWithDecks(action.payload)
            state.profileDecksPage = 0
        })
        builder.addCase(fetchNextDecksByUserId.fulfilled, (state, action) => {
            state.error = null
            state.loading = false
            if (state.profile)
                state.profile.decks = [...state.profile.decks, ...action.payload.content]
        })
        builder.addCase(fetchDeckByDeckId.fulfilled, (state, action) => {
            state.error = null
            state.loading = false
            state.learningDeck = action.payload
            console.log('action.payload', action.payload)
        })
        builder.addMatcher(isRejected, (state, action) => {
            state.error = action.payload as string
            state.loading = false
        })
        builder.addMatcher(isPending, (state) => {
            state.error = null
            state.loading = true
        })
    },
})

export const { increaseHomePage, increaseProfilePage, updateLearningDeck } = DeckSlice.actions

export default DeckSlice.reducer
