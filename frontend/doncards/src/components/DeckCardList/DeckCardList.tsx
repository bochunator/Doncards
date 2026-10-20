import { useState, type FormEvent } from "react";
import { Link } from "react-router-dom";
import "./DeckCardList.css";
import type { DeckDetailsResponse } from "../../types/deck/DeckDetailsResponse";
import CustomAlert from "../CustomAlert/CustomAlert";

type DeckCard = DeckDetailsResponse["cardResponses"][number];

type CardForm = {
    term: string;
    translation: string;
};

type Props = {
    deck: DeckDetailsResponse;
    isOwner: boolean;
    onCreateCard: (request: CardForm) => Promise<void>;
    onUpdateCard: (cardId: number, request: CardForm) => Promise<void>;
    onDeleteCard: (cardId: number) => Promise<void>;
};

function DeckCardList({
    deck,
    isOwner,
    onCreateCard,
    onUpdateCard,
    onDeleteCard,
}: Props) {
    const cards = deck.cardResponses;

    const [editingCardId, setEditingCardId] = useState<number | null>(null);
    const [isCreating, setIsCreating] = useState(false);
    const [term, setTerm] = useState("");
    const [translation, setTranslation] = useState("");
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");

    const resetForm = () => {
        setEditingCardId(null);
        setIsCreating(false);
        setTerm("");
        setTranslation("");
        setError("");
    };

    const startEditing = (card: DeckCard) => {
        setEditingCardId(card.id);
        setIsCreating(false);
        setTerm(card.term);
        setTranslation(card.translation);
        setError("");
    };

    const startCreating = () => {
        setEditingCardId(null);
        setIsCreating(true);
        setTerm("");
        setTranslation("");
        setError("");
    };

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const request = {
            term: term.trim(),
            translation: translation.trim(),
        };

        if (!request.term || !request.translation) {
            setError("Both term and translation are required.");
            return;
        }

        setSaving(true);
        setError("");

        try {
            if (isCreating) {
                await onCreateCard(request);
            } else if (editingCardId !== null) {
                await onUpdateCard(editingCardId, request);
            }

            resetForm();
        } catch {
            setError("Unable to save the card. Please try again.");
        } finally {
            setSaving(false);
        }
    };

    const handleDelete = async (cardId: number) => {
        const confirmed = window.confirm(
            "Are you sure you want to delete this card?"
        );

        if (!confirmed) return;

        setError("");

        try {
            await onDeleteCard(cardId);
        } catch {
            setError("Unable to delete the card. Please try again.");
        }
    };

    const renderCardForm = () => (
        <form className="deck-card-list__edit-form" onSubmit={handleSubmit}>
            <label htmlFor="card-term">Term</label>
            <input
                id="card-term"
                value={term}
                onChange={(event) => setTerm(event.target.value)}
                maxLength={500}
                required
                disabled={saving}
                autoFocus
            />

            <label htmlFor="card-translation">Translation</label>
            <input
                id="card-translation"
                value={translation}
                onChange={(event) => setTranslation(event.target.value)}
                maxLength={500}
                required
                disabled={saving}
            />

            {error && (
                <p className="deck-card-list__error" role="alert">
                    {error}
                </p>
            )}

            <div className="deck-card-list__edit-actions">
                <button type="submit" disabled={saving}>
                    {saving ? "Saving..." : "Save"}
                </button>
                <button
                    type="button"
                    onClick={resetForm}
                    disabled={saving}
                >
                    Cancel
                </button>
            </div>
        </form>
    );

    return (
        <section className="deck-card-list">
            <div className="deck-card-list__header">
                <h2>Cards</h2>
                <span>{cards.length} items</span>
            </div>

            {error && !isCreating && editingCardId === null && (
                <p className="deck-card-list__error" role="alert">
                    {error}
                </p>
            )}

            {cards.length === 0 && (
                <CustomAlert
                title="No cards yet 📚"
                description="This deck doesn't have any cards yet."
                />
            )}

            {cards.length > 0 && (
                <ul className="deck-card-list__items">
                    {cards.map((card, index) => (
                        <li
                            className="deck-card-list__item"
                            key={card.id}
                        >
                            {editingCardId === card.id ? (
                                <div className="deck-card-list__editor">
                                    {renderCardForm()}
                                </div>
                            ) : (
                                <>
                                    <span className="deck-card-list__number">
                                        {index + 1}
                                    </span>

                                    <div className="deck-card-list__content">
                                        <span className="deck-card-list__term">
                                            {card.term}
                                        </span>
                                        <span className="deck-card-list__translation">
                                            {card.translation}
                                        </span>
                                    </div>

                                    {isOwner && (
                                        <div className="deck-card-list__actions">
                                            <button
                                                type="button"
                                                onClick={() => startEditing(card)}
                                                aria-label={`Edit ${card.term}`}
                                                title="Edit card"
                                            >
                                                Edit
                                            </button>
                                            <button
                                                type="button"
                                                onClick={() => handleDelete(card.id)}
                                                aria-label={`Delete ${card.term}`}
                                                title="Delete card"
                                            >
                                                Delete
                                            </button>
                                        </div>
                                    )}
                                </>
                            )}
                        </li>
                    ))}
                </ul>
            )}

            {isCreating && (
                <div className="deck-card-list__new-card">
                    <h3>New card</h3>
                    {renderCardForm()}
                </div>
            )}

            <div className="deck-card-list__footer">
                {isOwner && !isCreating && (
                    <button
                        type="button"
                        className="deck-card-list__new-button"
                        onClick={startCreating}
                    >
                        + New card
                    </button>
                )}

                <Link
                    className="deck-card-list__start"
                    to={`/deck/${deck.id}/learn`}
                >
                    Start Learning
                </Link>
            </div>
        </section>
    );
}

export default DeckCardList;