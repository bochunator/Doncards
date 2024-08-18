export interface CreateCard {
    term: string
    translation: string
}

export const defaultCreateCard: CreateCard = {
    term: '',
    translation: ''
}

export interface CreateDeckPayload {
    name: string
    description: string
    cardDTOs: CreateCard[]
}

export const defaultCreateDeckPayload: CreateDeckPayload = {
    name: '',
    description: '',
    cardDTOs: []
}

export interface CreateDeckData {
    createDeckPayload: CreateDeckPayload
}

export const defaultCreateDeckData: CreateDeckData = {
    createDeckPayload: defaultCreateDeckPayload
}

export interface Card {
    cardId: number
    term: string
    translation: string
}

export interface Deck {
    deckId: number
    name: string
    description: string
    createdAt: string
    version: number
    cards: Card[]
}

export interface DeckDTO {
    authorName: string
    name: string
    description: string
    cards: Card[]
}

export interface DeckSliceState {
    error: string | null
    loading: boolean
    createdDeck: Deck | null
    page: number
    deckDTOs: DeckDTO[]
}

export const initialDeckState: DeckSliceState = {
    error: null,
    loading: false,
    createdDeck: null,
    page: 0,
    deckDTOs: []
}
