import { Card } from "../../types/cardTypes"


export interface LearningSessionData {
    currentCard: Card
    showTranslation: boolean
    usedCardsId: number[]
    showResult: boolean
}

export const defaultLearningSessionData: LearningSessionData = {
    currentCard: {
        cardId: 0,
        term: '',
        translation: ''
    },
    showTranslation: false,
    usedCardsId: [],
    showResult: false
}