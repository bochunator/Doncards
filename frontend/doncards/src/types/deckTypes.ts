import { Role } from "./applicationUserTypes"
import { Card } from "./cardTypes"

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

export interface Deck {
    deckId: number
    name: string
    description: string
    createdAt: string
    version: number
    cards: Card[]
}

export interface DeckDetailsDTO {
    deckId: number
    authorId: number
    authorName: string
    name: string
    description: string
    cards: Card[]
}

export interface DeckSummaryDTO {
    deckId: number
    name: string
    description: string
    cards: Card[]
}

export interface ApplicationUserWithDecksDTO {
    userId: number
    authorities: Role[]
    decks: DeckSummaryDTO[]
    email: string
    username: string
    enabled: boolean
    credentialsNonExpired: boolean
    accountNonExpired: boolean
    accountNonLocked: boolean
}

export const mapApplicationUserWithDecks = (user: any): ApplicationUserWithDecksDTO => ({
    userId: user.userId,
    authorities: user.authorities.map((role: Role) => ({
        roleId: role.roleId,
        authority: role.authority
    })),
    decks: [...user.deckSummaryDTOs.content],
    email: user.email,
    username: user.username,
    accountNonExpired: user.accountNonExpired,
    accountNonLocked: user.accountNonLocked,
    credentialsNonExpired: user.credentialsNonExpired,
    enabled: user.enabled
})

export interface DeckSliceState {
    error: string | null
    loading: boolean
    createdDeck: Deck | null
    homeDecksPage: number
    homeDecks: DeckDetailsDTO[]
    profileDecksPage: number
    profile: ApplicationUserWithDecksDTO | null
    learningDeck: Deck | DeckDetailsDTO | DeckSummaryDTO | null
}

export const initialDeckState: DeckSliceState = {
    error: null,
    loading: false,
    createdDeck: null,
    homeDecksPage: 0,
    homeDecks: [],
    profileDecksPage: 0,
    profile: null,
    learningDeck: null
}
