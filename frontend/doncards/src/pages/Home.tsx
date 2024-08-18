import { useEffect } from "react"
import { useDispatch, useSelector } from "react-redux"

import { AppDispatch, RootState } from "../redux/Store"
import { getDecks, increasePage } from "../redux/Slices/DeckSlice"
import { useOnScreen } from "../hooks/useOnScreen"
import { Link } from "react-router-dom"


function Home() {
    const { deckDTOs, page } = useSelector((state: RootState) => state.deck)
    const dispatch: AppDispatch = useDispatch()
    const { measureRef, isIntersecting, observer } = useOnScreen()

    useEffect(() => {
        if (deckDTOs.length === 0) {
            dispatch(getDecks(0))
        }
    }, [])

    useEffect(() => {
        if (isIntersecting) {
            dispatch(getDecks(page + 1))
            dispatch(increasePage())
            observer?.disconnect()
        }
    }, [isIntersecting])

    const showDeckCards = () => {
        return deckDTOs.map(({ name, authorName, description, cards }, deckIndex) => (
            <section key={deckIndex}>
                <h3>{name}</h3>
                <Link to={`/Doncards/auth/profile/${authorName}`} style={{ color: 'dodgerblue' }}>{authorName}</Link>
                <h5>{description}</h5>
                <ol>
                    {cards.map(({ term, translation }, cardIndex) => (
                        <li key={cardIndex} ref={cardIndex === cards.length - 1 && deckIndex === deckDTOs.length - 1 ? (
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
            <h2>Home</h2>
            {showDeckCards()}
        </>
    )
}

export default Home
