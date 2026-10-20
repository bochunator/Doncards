import axios from "axios";
import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import CustomAlert from "../../components/CustomAlert/CustomAlert";
import Flashcard from "../../components/FlashCard/FlashCard";
import API_URL from "../../config/api";
import type { CardResponse } from "../../types/card/CardResponse";
import type { DeckDetailsResponse } from "../../types/deck/DeckDetailsResponse";
import { getErrorMessage } from "../../utils/getErrorMessage";
import "./LearnPage.css";

function LearnPage() {
    const { deckId } = useParams<{ deckId: string }>();

    const [deck, setDeck] = useState<DeckDetailsResponse | null>(null);
    const [cards, setCards] = useState<CardResponse[]>([]);
    const [currentIndex, setCurrentIndex] = useState(0);
    const [knownCards, setKnownCards] = useState<CardResponse[]>([]);
    const [unknownCards, setUnknownCards] = useState<CardResponse[]>([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        async function fetchDeck() {
            try {
                setLoading(true);
                setError(null);

                const response = await axios.get<DeckDetailsResponse>(
                    `${API_URL}/decks/${deckId}`
                );

                setDeck(response.data);
                setCards(response.data.cardResponses);
                setCurrentIndex(0);
                setKnownCards([]);
                setUnknownCards([]);
            } catch (error: unknown) {
                setError(getErrorMessage(error));
            } finally {
                setLoading(false);
            }
        }

        if (deckId) {
            fetchDeck();
        }
    }, [deckId]);

    function handleAnswer(knewAnswer: boolean) {
        const currentCard = cards[currentIndex];

        if (!currentCard) {
            return;
        }

        if (knewAnswer) {
            setKnownCards((previous) => [...previous, currentCard]);
        } else {
            setUnknownCards((previous) => [...previous, currentCard]);
        }

        setCurrentIndex((previous) => previous + 1);
    }

    function handleRepeatUnknown() {
        setCards(unknownCards);
        setCurrentIndex(0);
        setKnownCards([]);
        setUnknownCards([]);
    }

    if (loading) {
        return (
            <CustomAlert
                title="Just a moment..."
                description="We're getting your deck ready."
            />
        );
    }

    if (error || !deck) {
        return (
            <CustomAlert
                title="No cards to study"
                description={error}
                actionLabel="Go to homepage"
                actionUrl={`/`}
            />
        );
    }

    if (cards.length === 0) {
        return (
            <CustomAlert
                title="No cards to study"
                description="This deck doesn't contain any cards."
                actionLabel="Back to deck"
                actionUrl={`/deck/${deck.id}`}
            />
        );
    }

    const isFinished = currentIndex >= cards.length;

    if (isFinished) {
        return (
            <main className="learn-page">
                <section className="learn-page__summary">
                    <span className="learn-page__eyebrow">
                        SESSION COMPLETED
                    </span>

                    <h1>Great job!</h1>

                    <p className="learn-page__summary-description">
                        You've completed the "{deck.name}" study session.
                    </p>

                    <div className="learn-page__results">
                        <div className="learn-page__result learn-page__result--known">
                            <span>{knownCards.length}</span>
                            <span>Known</span>
                        </div>

                        <div className="learn-page__result learn-page__result--unknown">
                            <span>{unknownCards.length}</span>
                            <span>To review</span>
                        </div>
                    </div>

                    {unknownCards.length > 0 && (
                        <button
                            className="learn-page__primary-button"
                            onClick={handleRepeatUnknown}
                        >
                            Review difficult cards
                        </button>
                    )}

                    <Link
                        className="learn-page__secondary-button"
                        to={`/deck/${deck.id}`}
                    >
                        Back to deck
                    </Link>
                </section>
            </main>
        );
    }

    const progress = (currentIndex / cards.length) * 100;

    return (
        <main className="learn-page">
            <div className="learn-page__content">
                <header className="learn-page__header">
                    <div>
                        <span className="learn-page__eyebrow">
                            STUDY SESSION
                        </span>

                        <h1>{deck.name}</h1>
                    </div>

                    <Link
                        className="learn-page__exit"
                        to={`/deck/${deck.id}`}
                        aria-label="exit learn"
                        title="exit learn"
                    >
                        ×
                    </Link>
                </header>

                <section className="learn-page__progress">
                    <div className="learn-page__progress-labels">
                        <span>Session progress</span>
                        <span>
                            {currentIndex} z {cards.length}
                        </span>
                    </div>

                    <div
                        className="learn-page__progress-track"
                        role="progressbar"
                        aria-label="Session progress"
                        aria-valuemin={0}
                        aria-valuemax={cards.length}
                        aria-valuenow={currentIndex}
                    >
                        <div
                            className="learn-page__progress-fill"
                            style={{ width: `${progress}%` }}
                        />
                    </div>
                </section>

                <div className="learn-page__card-area">
                    <Flashcard
                        key={cards[currentIndex].id}
                        card={cards[currentIndex]}
                        index={currentIndex}
                        total={cards.length}
                        onAnswer={handleAnswer}
                    />
                </div>

                <p className="learn-page__instruction">
                    Click the card to reveal the translation, or swipe left
                    or right.
                </p>

                <div className="learn-page__actions">
                    <button
                        className="learn-page__answer-button learn-page__answer-button--unknown"
                        onClick={() => handleAnswer(false)}
                    >
                        <span aria-hidden="true">×</span>
                        Don't know
                    </button>

                    <button
                        className="learn-page__answer-button learn-page__answer-button--known"
                        onClick={() => handleAnswer(true)}
                    >
                        <span aria-hidden="true">✓</span>
                        Know it
                    </button>
                </div>

                <p className="learn-page__remaining">
                    Cards remaining: {cards.length - currentIndex}
                </p>
            </div>
        </main>
    );
}

export default LearnPage;