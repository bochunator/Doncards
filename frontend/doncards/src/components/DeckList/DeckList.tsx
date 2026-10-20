
import { useState } from 'react';
import type { DeckDetailsResponse } from '../../types/deck/DeckDetailsResponse';
import './DeckList.css'
import DeckPreviewModal from '../DeckPreviewModal/DeckPreviewModal';
import DeckItem from '../DeckItem/DeckItem';

type Props = {
    decks: DeckDetailsResponse[];
    showAppUser?: boolean;
}

function DeckList({ decks, showAppUser = true }: Props) {
    const [selectedDeckId, setSelectedDeckId] = useState<number | null>(null);

    return (
        <>
            <ul className="deck-list">
                {decks.map(deck => (
                    <li key={deck.id}>
                        <DeckItem
                            deck={deck}
                            showAppUser={showAppUser}
                            onPreview={() => setSelectedDeckId(deck.id)}
                        />
                    </li>
                ))}
            </ul>

            {selectedDeckId !== null && (
                <DeckPreviewModal
                    deckId={selectedDeckId}
                    onClose={() => setSelectedDeckId(null)}
                    showAppUser={showAppUser}
                />
            )}
        </>
    )
}

export default DeckList;
