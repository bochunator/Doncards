import { useState, type FormEvent } from "react";
import { Link } from "react-router-dom";
import type { DeckDetailsResponse } from "../../types/deck/DeckDetailsResponse";
import "./DeckOverview.css";

type Props = {
    deck: DeckDetailsResponse;
    isOwner: boolean;
    onUpdateDeck: (request: {
        name: string;
        description: string;
    }) => Promise<void>;
    onDeleteDeck: (deckId: number) => Promise<void>;
};

function DeckOverview({ deck, isOwner, onUpdateDeck, onDeleteDeck }: Props) {
    const author = deck.appUserSummaryResponse;

    const [isEditing, setIsEditing] = useState(false);
    const [name, setName] = useState(deck.name);
    const [description, setDescription] = useState(deck.description ?? "");
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState("");

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();

        const trimmedName = name.trim();

        if (!trimmedName) {
            setError("Deck name cannot be empty.");
            return;
        }

        setSaving(true);
        setError("");

        try {
            await onUpdateDeck({
                name: trimmedName,
                description: description.trim(),
            });

            setIsEditing(false);
        } catch {
            setError("Unable to save changes.");
        } finally {
            setSaving(false);
        }
    };



    const handleDeleteDeck = async (deckId: number) => {
        const confirmed = window.confirm(
            "Are you sure you want to delete this deck?"
        );

        if (!confirmed) return;

        setError("");

        try {
            await onDeleteDeck(deckId);
        } catch {
            setError("Unable to delete the card. Please try again.");
        }
    };


    return (
        <section className="deck-overview">
            {isEditing ? (
                <form
                    className="deck-overview__edit-form"
                    onSubmit={handleSubmit}
                >
                    <label htmlFor="deck-name">Deck name</label>
                    <input
                        id="deck-name"
                        value={name}
                        onChange={(event) => setName(event.target.value)}
                        maxLength={100}
                        required
                        disabled={saving}
                    />

                    <label htmlFor="deck-description">Description</label>
                    <textarea
                        id="deck-description"
                        value={description}
                        onChange={(event) => setDescription(event.target.value)}
                        maxLength={1000}
                        rows={3}
                        disabled={saving}
                    />

                    {error && <p role="alert">{error}</p>}

                    <div className="deck-overview__edit-actions">
                        <button type="submit" disabled={saving}>
                            {saving ? "Saving..." : "Save changes"}
                        </button>
                        <button
                            type="button"
                            onClick={() => {
                                setName(deck.name);
                                setDescription(deck.description ?? "");
                                setError("");
                                setIsEditing(false);
                            }}
                            disabled={saving}
                        >
                            Cancel
                        </button>
                    </div>
                </form>
            ) : (
                <>
                    <div className="deck-overview__title-row">
                        <h1 className="deck-overview__title">
                            {deck.name}
                        </h1>

                        {isOwner && (
                            <div className="deck-overview__actions">
                                <button
                                    type="button"
                                    className="deck-overview__edit-button"
                                    onClick={() => setIsEditing(true)}
                                    aria-label="Edit deck details"
                                    title="Edit deck details"
                                >
                                    Edit details
                                </button>
                                <button
                                    type="button"
                                    className="deck-overview__edit-button"
                                    onClick={() => handleDeleteDeck(deck.id)}
                                >
                                    Delete deck
                                </button>
                            </div>
                        )}
                    </div>

                    {deck.description && (
                        <p className="deck-overview__description">
                            {deck.description}
                        </p>
                    )}
                </>
            )}

            <div className="deck-overview__author">
                <div className="deck-overview__avatar" aria-hidden="true">
                    {author.username.charAt(0).toUpperCase()}
                </div>

                <div className="deck-overview__author-info">
                    <span className="deck-overview__author-name">
                        {author.username}
                    </span>
                    <span className="deck-overview__author-date">
                        Member of Doncards
                    </span>
                </div>
            </div>

            <div className="deck-overview__footer">
                <div className="deck-overview__count">
                    <span className="deck-overview__count-value">
                        {deck.cardResponses.length}
                    </span>
                    <span className="deck-overview__count-label">
                        Cards in deck
                    </span>
                </div>

                <Link
                    className="deck-overview__start"
                    to={`/deck/${deck.id}/learn`}
                >
                    Start Learning
                </Link>
            </div>
        </section>
    );
}

export default DeckOverview;