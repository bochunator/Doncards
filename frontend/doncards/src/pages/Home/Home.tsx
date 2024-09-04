import { useEffect } from "react"
import { useDispatch, useSelector } from "react-redux"

import './Home.css'
import { AppDispatch, RootState } from "../../redux/Store"
import { fetchDecksForHome, increaseHomePage } from "../../redux/Slices/DeckSlice"
import { useOnScreen } from "../../hooks/useOnScreen"
import { Link } from "react-router-dom"
import { Alert } from "react-bootstrap"


function Home() {
    const { homeDecks, homeDecksPage } = useSelector((state: RootState) => state.deck)
    const dispatch: AppDispatch = useDispatch()
    const { measureRef, isIntersecting, observer } = useOnScreen()

    useEffect(() => {
        if (homeDecks.length === 0) {
            dispatch(fetchDecksForHome(0))
        }
    }, [])

    useEffect(() => {
        if (isIntersecting) {
            dispatch(fetchDecksForHome(homeDecksPage + 1))
            dispatch(increaseHomePage())
            observer?.disconnect()
        }
    }, [isIntersecting])

    const showInfoIfBackendIsWakingUp = () => {
        return (
            <div className="info-alert">
                <Alert variant='info' dismissible >
                    <Alert.Heading>xD</Alert.Heading>
                    <p>Due to inactivity, our server may take a moment to wake up, causing a delay in loading data by 50 seconds or more.
                        Thank you for your patience! If the delay persists, please try refreshing the page periodically.</p>
                </Alert>
            </div>
        )
    }

    const showDeckCards = () => {
        return homeDecks.map(({ deckId, authorId, authorName, name, description, cards }, deckIndex) => (
            <section key={deckIndex}>
                <h3><Link to={`/Doncards/learning/${deckId}`} style={{ color: 'black' }}>{name}</Link></h3>
                <Link to={`/Doncards/profile/${authorId}`} style={{ color: 'dodgerblue' }}>{authorName}</Link>
                <h5>{description}</h5>
                <ol>
                    {cards.map(({ term, translation }, cardIndex) => (
                        <li key={cardIndex} ref={cardIndex === cards.length - 1 && deckIndex === homeDecks.length - 1 ? (
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
            {homeDecks.length === 0 && showInfoIfBackendIsWakingUp()}
            <h2>Home</h2>
            {showDeckCards()}
        </>
    )
}

export default Home
