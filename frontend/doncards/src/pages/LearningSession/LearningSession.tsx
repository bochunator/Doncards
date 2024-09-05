import React, { useEffect, useState } from 'react'
import { useDispatch, useSelector } from 'react-redux'
import { useParams } from 'react-router-dom'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCheck, faTimes, faRotateRight, faHouse } from '@fortawesome/free-solid-svg-icons'

import './LearningSession.css'
import { AppDispatch, RootState } from '../../redux/Store'
import { cleanCreatedDeck, fetchDeckByDeckId, updateLearningDeck } from '../../redux/Slices/DeckSlice'
import { LearningSessionData, defaultLearningSessionData } from './LearningSessionTypes'


const LearningSession: React.FC = () => {
    const { deckId } = useParams()
    const { homeDecks, profile, createdDeck, learningDeck } = useSelector((state: RootState) => state.deck)
    const dispatch: AppDispatch = useDispatch()
    const [learningSessionData, setLearningSessionData] = useState<LearningSessionData>(defaultLearningSessionData)
    const { currentCard, showTranslation, usedCardsId, showResult } = learningSessionData

    useEffect(() => {
        const deckIdNumber = Number(deckId)
        const foundDeck = homeDecks.find(deck => deck.deckId === deckIdNumber)
            || profile?.decks.find(deck => deck.deckId === deckIdNumber)
            || createdDeck
        if (createdDeck) {
            dispatch(cleanCreatedDeck())
        }
        dispatch(foundDeck ? updateLearningDeck(foundDeck) : fetchDeckByDeckId(deckIdNumber))
    }, [])

    useEffect(() => {
        learningDeck && getRandomCard()
    }, [learningDeck])

    const toggleTranslation = () => {
        setLearningSessionData(prevState => ({
            ...prevState,
            showTranslation: !prevState.showTranslation
        }))
    }

    const getRandomCard = (): boolean => {
        if (!learningDeck || learningDeck.cards.length === 0) {
            return false
        }
        const unusedIndices = learningDeck.cards
            .map((_, index) => index)
            .filter(index => !usedCardsId.includes(index))
        if (unusedIndices.length === 0) {
            return false
        }
        const randomIndex = Math.floor(Math.random() * unusedIndices.length)
        const selectedIndex = unusedIndices[randomIndex]
        const selectedCard = learningDeck.cards[selectedIndex]
        setLearningSessionData(prevState => ({
            ...prevState,
            currentCard: selectedCard,
            usedCardsId: [...usedCardsId, selectedIndex]
        }));

        return true;
    }

    const handleClickNextCard = (callback: () => void) => {
        showTranslation && setLearningSessionData(prevState => ({
            ...prevState,
            showTranslation: false
        }))
        callback()
        !getRandomCard() && console.log('skonczyl sie Deck!')
        if (usedCardsId.length === learningDeck?.cards.length) {
            setLearningSessionData(prevState => ({
                ...prevState,
                showResult: true
            }))
        }
    }

    const clickedYes = () => {
    }

    const clickedNo = () => {
    }

    const clickedRestart = () => {
        
    }

    return (
        <div>
            <h1>LearningSession</h1>
            <h2>{learningDeck ? learningDeck.name : 'loading...'}</h2>
            <section className='learning-session-section'>
                {showResult ? (
                    <>
                        <h3 className='learning-session-result-h3'>You have completted deck!</h3>
                        <div className='learning-session-result-h3'>Not implemented yet!</div>
                        <div className="learning-session-icons">
                            <FontAwesomeIcon
                                icon={faRotateRight}
                                className='learning-session-result'
                                onClick={clickedRestart}
                            />
                            <FontAwesomeIcon
                                icon={faHouse}
                                className='learning-session-result'
                                onClick={() => handleClickNextCard(clickedNo)}
                            />
                        </div>
                    </>
                ) : (
                    <>
                        <div className='learning-session-number-of-cards'>{usedCardsId.length} / {learningDeck?.cards.length}</div>
                        <div className="learning-session-card" onClick={toggleTranslation}>
                            {showTranslation ? currentCard.translation : currentCard.term}
                        </div>
                        <div className="learning-session-icons">
                            <FontAwesomeIcon
                                icon={faTimes}
                                className='learning-card-red-icon'
                                onClick={() => handleClickNextCard(clickedYes)}
                            />
                            <FontAwesomeIcon
                                icon={faCheck}
                                className='learning-card-green-icon'
                                onClick={() => handleClickNextCard(clickedNo)}
                            />
                        </div>
                    </>
                )
                }

            </section >
        </div >
    )
}

export default LearningSession
