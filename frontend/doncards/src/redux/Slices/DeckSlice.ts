import { PayloadAction, createAsyncThunk, createSlice, isPending } from "@reduxjs/toolkit"
import { Card, CreateDeckPayload, Deck, DeckDTO, initialDeckState } from "../../types/deckTypes"
import axios, { AxiosError } from "axios"

const URL = import.meta.env.VITE_DONCARDS_BACKEND_URL

export const createDeck = createAsyncThunk(
    'user/deck/create',
    async ({ body, token }: { body: CreateDeckPayload, token: string }, thunkAPI) => {
        try {
            const response = await axios.post(`${URL}/user/deck/create`, body, {
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

export const getDecks = createAsyncThunk(
    'auth/decks/cards',
    async (page: number, thunkAPI) => {
        try {
            const response = await axios.get(`${URL}/auth/decks/cards?page=${page}`)
            return response.data
        } catch (e) {
            return thunkAPI.rejectWithValue((e as AxiosError).response?.data)
        }
    }
)

export const DeckSlice = createSlice({
    name: 'authentication',
    initialState: initialDeckState,
    reducers: {
        increasePage(state) {
            return {
                ...state,
                page: state.page + 1
            }
        }
    },
    extraReducers: (builder) => {
        builder.addCase(createDeck.fulfilled, (state, action: PayloadAction<Deck>) => {
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
        builder.addCase(getDecks.fulfilled, (state, action: PayloadAction<DeckDTO[]>) => {
            state.error = null
            state.loading = false
            state.deckDTOs = [...state.deckDTOs, ...action.payload]
        })
        builder.addCase(createDeck.rejected, (state, action) => {
            state.error = action.payload as string
            state.loading = false
        })
        builder.addCase(getDecks.rejected, (state, action) => {
            state.error = action.payload as string
            state.loading = false
        })
        builder.addMatcher(isPending, (state) => {
            state.error = null
            state.loading = true
        })
    },
})



export const { increasePage } = DeckSlice.actions

export default DeckSlice.reducer
