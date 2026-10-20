import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { useAuth } from "../../authentication/AuthContext";
import DeckCardList from "../../components/DeckCardList/DeckCardList";
import DeckOverview from "../../components/DeckOverview/DeckOverview";
import API_URL from "../../config/api";
import type { CardRequest } from "../../types/card/CardRequest";
import type { DeckDetailsResponse } from "../../types/deck/DeckDetailsResponse";
import type { UpdateDeckRequest } from "../../types/deck/UpdateDeckRequest";
import "./DeckPage.css";
import { getErrorMessage } from "../../utils/getErrorMessage";
import LoadingIndicator from "../../components/LoadingIndicator/LoadingIndicator";
import CustomAlert from "../../components/CustomAlert/CustomAlert";

function DeckPage() {
    const { deckId } = useParams();
    const { token, username, isAuthenticated } = useAuth();

    const [deck, setDeck] = useState<DeckDetailsResponse>();
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const [actionError, setActionError] = useState<string | null>(null);
    const navigate = useNavigate();

    useEffect(() => {
        async function fetchDeck() {
            try {
                setLoading(true);
                setError(null);
                const response = await axios.get<DeckDetailsResponse>(
                    `${API_URL}/decks/${deckId}`
                );

                setDeck(response.data);
            } catch (error: unknown) {
                setError(getErrorMessage(error));
                console.log(getErrorMessage(error));
            } finally {
                setLoading(false);
            }
        };

        if (deckId) {
            fetchDeck();
        }
    }, [deckId]);

    const isOwner =
        isAuthenticated &&
        !!username &&
        deck?.appUserSummaryResponse.username === username;

    const getAuthHeaders = () => {
        if (!token) {
            throw new Error("You must be signed in to perform this action.");
        }

        return {
            Authorization: `Bearer ${token}`,
        };
    };

    const updateDeck = async (request: UpdateDeckRequest) => {
        if (!deckId || !deck)
            return;

        try {
            setActionError(null);
            await axios.put(
                `${API_URL}/decks/${deckId}`,
                request,
                { headers: getAuthHeaders() }
            );

            setDeck({
                ...deck,
                name: request.name,
                description: request.description,
            });
        } catch (error: unknown) {
            setError(getErrorMessage(error));
        }
    };

    const createCard = async (request: CardRequest) => {
        if (!deckId || !deck)
            return;

        try {
            setActionError(null);
            const response = await axios.post(
                `${API_URL}/decks/${deckId}/cards`,
                request,
                { headers: getAuthHeaders() }
            );

            setDeck({
                ...deck,
                cardResponses: [...deck.cardResponses, response.data],
            });
        } catch (error: unknown) {
            setError(getErrorMessage(error));
        }
    };

    const updateCard = async (cardId: number, request: CardRequest) => {
        if (!deckId || !deck) return;


        try {
            setActionError(null);
            await axios.put(
                `${API_URL}/decks/${deckId}/cards/${cardId}`,
                request,
                { headers: getAuthHeaders() }
            );

            setDeck({
                ...deck,
                cardResponses: deck.cardResponses.map((card) =>
                    card.id === cardId
                        ? { ...card, ...request }
                        : card
                ),
            });
        } catch (error: unknown) {
            setError(getErrorMessage(error));
        }
    };

    const deleteCard = async (cardId: number) => {
        if (!deckId || !deck) return;

        try {
            setActionError(null);
            await axios.delete(
                `${API_URL}/decks/${deckId}/cards/${cardId}`,
                { headers: getAuthHeaders() }
            );

            setDeck({
                ...deck,
                cardResponses: deck.cardResponses.filter(
                    (card) => card.id !== cardId
                ),
            });
        } catch (error: unknown) {
            setError(getErrorMessage(error));
        }
    };

    const deleteDeck = async () => {
        if (!deckId || !deck) return;

        try {
            setActionError(null);
            await axios.delete(
                `${API_URL}/decks/${deckId}`,
                { headers: getAuthHeaders() }
            );

            navigate("/profile");
        } catch (error: unknown) {
            setError(getErrorMessage(error));
        }
    };

    return (
        <main className="deck-page">

            {loading && (
                <LoadingIndicator message="Loading deck..." />
            )}

            {error && (
                <CustomAlert
                    title="Failed to load deck"
                    description={error || "Deck not found."}
                />
            )}

            {actionError && (
                <p className="deck-page__error" role="alert">
                    {actionError}
                </p>
            )}

            {!loading && !error && !actionError && (
                <>
                    <div className="deck-page__component">
                        <DeckOverview
                            deck={deck}
                            isOwner={isOwner}
                            onUpdateDeck={updateDeck}
                            onDeleteDeck={deleteDeck}
                        />
                    </div>

                    <div className="deck-page__component">
                        <DeckCardList
                            deck={deck}
                            isOwner={isOwner}
                            onCreateCard={createCard}
                            onUpdateCard={updateCard}
                            onDeleteCard={deleteCard}
                        />
                    </div>
                </>
            )}
        </main>
    );
}

export default DeckPage;
