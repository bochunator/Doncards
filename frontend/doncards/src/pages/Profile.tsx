import React, { useEffect } from 'react'
import { Link, useParams } from 'react-router-dom'
import { AppDispatch, RootState } from '../redux/Store'
import { useDispatch, useSelector } from 'react-redux'
import { useOnScreen } from '../hooks/useOnScreen'
import { fetchApplicationUserWithDecksByUserId, fetchNextDecksByUserId, increaseProfilePage } from '../redux/Slices/DeckSlice'


const Profile: React.FC = () => {
    const { userId } = useParams()
    const dispatch: AppDispatch = useDispatch()
    const { profile, profileDecksPage } = useSelector((state: RootState) => state.deck)
    const { measureRef, isIntersecting, observer } = useOnScreen()

    useEffect(() => {
        if (userId && Number(userId) !== profile?.userId) {
            dispatch(fetchApplicationUserWithDecksByUserId(Number(userId)))
        }
    }, [])

    useEffect(() => {
        if (isIntersecting && profile) {
            dispatch(fetchNextDecksByUserId({ userId: Number(userId), page: profileDecksPage + 1 }))
            dispatch(increaseProfilePage())
            observer?.disconnect()
        }
    }, [isIntersecting])

    useEffect(() => {
    }, [])

    const showDeckCards = () => {
        if (!profile) {
            return <></>
        }
        return profile.decks.map(({ deckId, name, description, cards }, deckIndex) => (
            <section key={deckIndex}>
                <h3><Link to={`/Doncards/learning/${deckId}`} style={{ color: 'black' }}>{name}</Link></h3>
                <h5>{description}</h5>
                <ol>
                    {cards.map(({ term, translation }, cardIndex) => (
                        <li key={cardIndex} ref={cardIndex === cards.length - 1 && deckIndex === profile.decks.length - 1 ? (
                            measureRef
                        ) : (
                            null
                        )}>
                            {term} | {translation}
                        </li>
                    ))}
                </ol>
            </section>
        ))
    }

    return (
        <>
            <h2>Profile</h2>
            <h3>Username: {profile ? profile.username : 'Not found'}</h3>
            <p>ID: {profile ? profile.userId : 'Not found'}</p>
            <p>Email: {profile ? profile.email : 'Not found'}</p>
            {showDeckCards()}
        </>
    )
}

export default Profile
