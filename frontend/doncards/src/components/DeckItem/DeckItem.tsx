import { Link } from "react-router-dom";
import type { DeckDetailsResponse } from "../../types/deck/DeckDetailsResponse";
import './DeckItem.css'

type Props = {
    deck: DeckDetailsResponse;
    showAppUser: boolean;
    onPreview: () => void;
}

function DeckItem({ deck, showAppUser, onPreview }: Props) {
    const PREVIEW_LIMIT = 5;
    const hasMoreCards = deck.cardCount > PREVIEW_LIMIT;

    return (
        <article
            className="deck-item"
            onClick={onPreview}
            onKeyDown={(event) => {
                if (
                    event.target === event.currentTarget &&
                    (event.key === "Enter" || event.key === " ")
                ) {
                    event.preventDefault();
                    onPreview();
                }
            }}
            key={deck.id}
        >
            <h2>{deck.name}</h2>
            {showAppUser &&
                <p className="deck-item__deck-author">
                    Author:{" "}
                    <Link
                        to={`/users/${deck.appUserSummaryResponse.id}`}
                        onClick={(event) => event.stopPropagation()}
                    >
                        {deck.appUserSummaryResponse.username}
                    </Link>
                </p>}
            <p className="deck-item__deck-description">{deck.description}</p>
            <ul className="deck-item__deck-preview-cards">
                {deck.cardResponses.map(card => (
                    <li key={card.id}>
                        <span>{card.term}</span>
                        <span>{card.translation}</span>
                    </li>
                ))}
            </ul>
            <p className="deck-item__card-count">
                {deck.cardCount} cards
                {hasMoreCards && ` · Showing ${PREVIEW_LIMIT} of ${deck.cardCount}`}
            </p>
            <Link
                to={`/deck/${deck.id}`}
                className="deck-item__deck-details-link"
                onClick={(event) => event.stopPropagation()}
            >
                {hasMoreCards ? `See all ${deck.cardCount} cards` : "Go to deck"}
            </Link>
        </article>
    );
}

export default DeckItem;