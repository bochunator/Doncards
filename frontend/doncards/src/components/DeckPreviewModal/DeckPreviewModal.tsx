import { useEffect, useState } from "react";
import type { DeckDetailsResponse } from "../../types/deck/DeckDetailsResponse";
import "./DeckPreviewModal.css";
import axios from "axios";
import { Link } from "react-router-dom";

const API_URL = import.meta.env.VITE_DONCARDS_BACKEND_URL

type Props = {
    deckId: number;
    onClose: () => void;
    showAppUser: boolean;
}

function DeckPreviewModal({ deckId, onClose, showAppUser }: Props) {
    const [deck, setDeck] = useState<DeckDetailsResponse>();
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    useEffect(() => {
        const previousOverflow = document.body.style.overflow;
        document.body.style.overflow = "hidden";

        const handleKeyDown = (event: KeyboardEvent) => {
            if (event.key === "Escape") {
                onClose();
            }
        };

        window.addEventListener("keydown", handleKeyDown);

        const getDeck = async () => {
            setLoading(true);
            setError(false);
            try {
                const response = await axios.get(
                    `${API_URL}/decks/${deckId}`
                );

                setDeck(response.data);
            } catch {
                setError(true);
            } finally {
                setLoading(false);
            }
        };

        getDeck();

        return () => {
            document.body.style.overflow = previousOverflow;
            window.removeEventListener("keydown", handleKeyDown);
        }
    }, [deckId, onClose]);

    return (
        <>
            {!loading && !error &&
                <div className="deck-preview-modal-backdrop" onClick={onClose}>
                    <section
                        className="deck-preview-modal"
                        role="dialog"
                        aria-modal="true"
                        aria-labelledby="deck-preview-modal-title"
                        onClick={(event) => event.stopPropagation()}
                    >
                        <header className="deck-preview-modal-header">
                            <h2 id="deck-preview-modal-title">{deck.name}</h2>
                            <button
                                className="deck-preview-modal-close"
                                onClick={onClose}
                                aria-label="Close"
                            >
                                x
                            </button>
                        </header>
                        {showAppUser &&
                        <p className="deck-preview-modal-author">
                            Author:{" "}
                            <Link
                                to={`/users/${deck.appUserSummaryResponse.id}`}
                                onClick={(event) => event.stopPropagation()}
                            >
                                {deck.appUserSummaryResponse.username}
                            </Link>
                        </p>}
                        <p className="deck-preview-modal-description">
                            {deck.description}
                        </p>
                        <div className="deck-preview-modal-cards">

                            {deck.cardResponses.map(card => (
                                <li key={card.id} className="deck-preview-modal-card">
                                    <span>{card.term}</span>
                                    <span>{card.translation}</span>
                                </li>
                            ))}
                        </div>
                        <footer className="deck-preview-modal-footer">
                            <Link
                                className="deck-preview-modal-link"
                                to={`/deck/${deck.id}`}
                            >
                                Go to the Deck
                            </Link>
                            <span>Number of cards: {deck.cardResponses.length}</span>
                        </footer>
                    </section>
                </div>
            }
        </>
    )
}

export default DeckPreviewModal;